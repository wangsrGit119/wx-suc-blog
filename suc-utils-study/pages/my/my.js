// pages/my/my.js
const app = getApp()
import Toast from '../../components/vant/dist/toast/toast';
import Dialog from '../../components/vant/dist/dialog/dialog';

Page({

  /**
   * 页面的初始数据
   */
  data: {
    userInfo: null,//用户信息
    sessionTimeout:false,// 
    loginStatus:null, //登录状态
    buttonLoading:false,//登录按钮状态
    emailDshow:false,//邮箱绑定对话框,
    userEmail:null,//邮箱
    emailCode:null,//邮箱验证码
    userId:null,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    const that = this;
    
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    const that = this;
    that.setData({
      userEmail: app.globalData.userEmail,
      userInfo: app.globalData.userInfo,
      loginStatus: app.globalData.loginStatus,
      userId:app.globalData.userId
    });
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },
  callback:function(e){
   
   console.log(e)
  },
  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
     console.log("下拉刷新");
      const that = this;
      console.log(app.globalData.userInfo)
      
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },
  /**登录接口 */
  toLogin:function(){
    const that = this;
    let JSCODE = '';
    let baseUserInfo = JSON.stringify(app.globalData.userInfo)
    wx.login({
      success: res => {
        console.log("wx.login")
        console.log(res)
        if (res.errMsg == 'login:ok') {
          JSCODE = res.code;
          //后端接口调用
          wx.request({
            url: app.globalData.baseUrl+'/user/wxLogin',
            method:'POST',
            data:{
              code: JSCODE,
              baseUserInfo: baseUserInfo
            },
            header: {
              "Content-Type": "application/x-www-form-urlencoded"
            },
            success:function(res){
              console.log("wx.server.login")
              console.log(res)
              if (res.statusCode==200){
                let result = JSON.parse(res.data.data);
                app.globalData.userId = result.id;
                if (result.email != undefined){
                  app.globalData.userEmail = result.email;
                  that.setData({ userEmail: result.email,});
                }
                app.globalData.loginStatus = true;
                that.setData({
                  userId: result.id,
                  loginStatus:true
                });
              }else{
                Toast.loading({
                  mask: false,
                  duration: 2000,
                  message: '登录失败!'
                });
                app.globalData.loginStatus = false;
              }
            },
            fail:function(res){
              Toast.loading({
                mask: false,
                duration: 2000,
                message: '系统异常!'
              });
              app.globalData.loginStatus = false;
            }
          })
         
          setTimeout(function(){
            that.setData({
              loginStatus: app.globalData.loginStatus,
              buttonLoading: false
            });
          },3000)
        }
      }
    })
   
  },
  /** 授权 */
  bindGetUserInfo:function(e){
    const that = this;
    console.log(e)
      // 同意授权
    if (JSON.stringify(e.detail.errMsg).indexOf("getUserInfo:ok") != -1 ){
       //1.授权信息
      app.globalData.userInfo = e.detail.userInfo;
       //2.登录
         that.setData({buttonLoading:true});
         that.toLogin();
       
    }
    //更新当前js 变量
    that.setData({ 
      userInfo: app.globalData.userInfo ,
      });
  },
  /** 积分问号图标 */
  onClickIconForVip:function(){
    Dialog.alert({
      title: '温馨提示',
      message: '该积分为星球活跃积分，初始积分均为100，不会有任何实质性含义，仅在当前知识普及星球有效。'
    }).then(() => {
      // on close
    });
  },
  /** 邮箱设置对话框展示 */
  onEmailToShow:function(){
    const that = this;
    that.setData({ 
      emailDshow:true,
      emailCode:null //重置验证码
    })
  },
  onEmailClose:function(e){
    const that = this;
    if(e.detail == "confirm"){
      //登录校验
      if (app.globalData.userId == null || app.globalData.loginStatus == false) {
        that.notifyCommon("请先登录!")
        return false;
      }
     //参数校验
      if (that.data.emailCode == null){
        that.notifyCommon("验证码为空!");
        return false;
      }
      if (app.globalData.userEmail == null) {
        that.notifyCommon("邮箱为空!");
        return false;
      }
     wx.request({
       url: app.globalData.baseUrl+'/user/validateEmailCode',
       method:'POST',
       data:{
          userId:app.globalData.userId,
          code:that.data.emailCode,
          email:app.globalData.userEmail
       },
       header:{
         "Content-Type": "application/x-www-form-urlencoded"
       },
       success:function(res){
         if(res.data.data.indexOf("failed") !=-1){
           that.notifyCommon("验证码错误!");
         } else if (res.data.data.indexOf("out") != -1){
           that.notifyCommon("验证码过期!");
         }else{
           that.notifyCommon("绑定成功!");
         }
         console.log(res)
       }
     })
    }
    console.log(e)
    //验证验证码的合法性

  },
  /** 邮箱问号图标 */
  onClickIconForEmail:function(){
    Dialog.alert({
      title: '温馨提示',
      message: '该邮箱为想在电脑端发布文章用户提供入口校验所需，markdown语法编辑文章在这里依然可以显示'
    }).then(() => {
      // on close
    });
  },
  /** 监听邮箱填写 */
  onEmailChange(e){
    app.globalData.userEmail = e.detail;
  },
  /**监听验证码填写 */
  onEmailCodeChange(e){
    this.setData({emailCode:e.detail})
  },
  /** 获取邮箱验证码 */
  onBindingEmail:function(res){
    const that = this;
    if (app.globalData.userId == null || app.globalData.loginStatus == false) {
      that.notifyCommon("请先登录!")
      return false;
    }
    if (app.globalData.userEmail ==null){
      return false;
    }

  //获取邮箱验证码
    wx.request({
      url: app.globalData.baseUrl+'/user/bindEmail',
      method:'POST',
      data:{
         userId:app.globalData.userId,
         email:app.globalData.userEmail
      },
      header:{
        "Content-Type": "application/x-www-form-urlencoded"
      },
      success:function(res){
        console.log(res)
        if(res.data.code==200){
          that.notifyCommon("邮件已发送!")
        }else{
          that.notifyCommon(res.data.message);
        }
      }
    })

  },
  /**公用提醒函数 Toast */
  notifyCommon: function (content) {
    Toast.loading({
      mask: false,
      duration: 1000,
      message: content
    });
  },
})
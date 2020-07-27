//app.js
const Towxml = require('./components/towxml/main');     //引入towxml库

App({
  towxml: new Towxml(),
  onLaunch: function () {
    wx.cloud.init();
    const that = this;
    wx.getSystemInfo({
      success: e => {
        this.globalData.StatusBar = e.statusBarHeight;
        let custom = wx.getMenuButtonBoundingClientRect();
        this.globalData.Custom = custom;
        this.globalData.CustomBar = custom.bottom + custom.top - e.statusBarHeight;
      }
    })
   
   // 初始化wechat server token（小程序后端服务接口 token）
    // 登录
    // wx.login({
    //   success: res => {
    //     console.log(res)
    //     // 发送 res.code 到后台换取 openId, sessionKey, unionId
    //   }
    // })

    wx.checkSession({
      success: function (res) {
        if (JSON.stringify(res).indexOf("checkSession:ok") != -1){
          that.globalData.loginStatus = true
        }
        console.log(res, '未过期');
      },
      fail: function (res) {
        if (JSON.stringify(res).indexOf("checkSession:fail") != -1) { 
          that.globalData.loginStatus = false
        }
        console.log(res, '登录过期');
      }
    })

    // 判断是否授权 获取用户信息
    wx.getSetting({
      success: res => {
        if (res.authSetting['scope.userInfo']) {
          // 已经授权，可以直接调用 getUserInfo 获取头像昵称，不会弹框
          wx.getUserInfo({
            success: res => {
              // 可以将 res 发送给后台解码出 unionId
              this.globalData.userInfo = res.userInfo
              console.log("已授权");
              // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
              // 所以此处加入 callback 以防止这种情况
              if (this.userInfoReadyCallback) {
                this.userInfoReadyCallback(res)
              }
            }
          })
        }
      }
    })
  },
  globalData: {
    userInfo: null,
    // baseUrl: 'http://127.0.0.1:8081',
    loginStatus:false, //默认false
    userId:null,//用户唯一id  数据库中关联,
    userEmail:null
  }
})
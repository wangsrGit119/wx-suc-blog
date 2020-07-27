// pages/feedback/feedback.js
const app = getApp()
import Toast from '../../components/vant/dist/toast/toast';
Page({

  /**
   * 页面的初始数据
   */
  data: {
    feedbacks:null,//已回复反馈
    userId:null,
    content:null,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    const that = this;
    that.setData({userId:app.globalData.userId});

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
   this.getHistoryFeedBack();
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

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
  /**监听文本框 */
  onTextChange:function(e){
    const that = this;
    that.setData({content:e.detail.value})
  },
  onSubmitFeedBack(e){
   const that = this;
   let content = that.data.content==null ? '':that.data.content.replace(/\s+/g, '');
    if ( content == '') {
      that.notifyCommon('内容为空！');
      return false;
    }
   if(app.globalData.userId ==null){
     that.notifyCommon("请登录!")
     return false;
   }
   //内容校验
    wx.request({
      url: app.globalData.baseUrl + '/sys/msgCheck',
      method: 'POST',
      header: {
        "Content-Type": "application/x-www-form-urlencoded;charset=utf-8"
      },
      data: { content: content },
      success: function (res) {
        console.log(res)
        let resTep = JSON.parse(res.data.data)
        if (resTep.errcode != 0) {
          Toast.loading({
            mask: false,
            duration: 1000,
            message: '非法词汇!'
          });
        } else {
         //校验通过
          that.addFeedBack(content)
        }
      }
    })
  },
  /**校验通过提交回馈 */
  addFeedBack:function(content){
    const that =this;
    let requestInfo = {details: content,userId:app.globalData.userId}
    wx.request({
      url: app.globalData.baseUrl +'/notice/commitFeedBack',
      method:'POST',
      data:JSON.stringify(requestInfo),
      success:function(res){
        console.log(res)
        if(res.statusCode ==200){
          that.notifyCommon("提交成功!")
        }
      }
    })
  },
  /**获取历史记录 */
  getHistoryFeedBack:function(res){
    const that = this;
    wx.request({
      url: app.globalData.baseUrl +'/notice/getFeedBacks',
      method:'GET',
      success:function(res){
        console.log(res)
        if(res.statusCode==200){
          that.setData({feedbacks:res.data.data})
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
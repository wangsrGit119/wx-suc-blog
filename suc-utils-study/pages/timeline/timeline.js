// pages/timeline/timeline.js
const app = getApp()
import Toast from '../../components/vant/dist/toast/toast';

Page({

  /**
   * 页面的初始数据
   */
  data: {
   timerLineMap:null,//时间线集合
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

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
    const that  = this;
    let userId = app.globalData.userId;
    if(userId !=null){
      that.onLoadingTimerLine(userId)
    }else{
     that.notifyCommon("请先登录!")
    }
  },
  /** 加载时间线 */
  onLoadingTimerLine: function (userId){
    const that = this;
   wx.request({
     url: app.globalData.baseUrl +'/user/getUserTimerLineById?userId='+userId,
     method:'GET',
     success:function(res){
       console.log(res)
       if(res.statusCode==200){
         that.setData({ timerLineMap: res.data.data });
       }
     }
   })
    
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
  /**公用提醒函数 Toast */
  notifyCommon: function (content) {
    Toast.loading({
      mask: false,
      duration: 1000,
      message: content
    });
  },

})
// pages/excellent/excellent.js
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    articleList: [],
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
    const that = this;
    that.getExcellentArticle();

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
  /**获取文章接口 */
  getExcellentArticle:function(){
    const that = this;
    wx.request({
      url: app.globalData.baseUrl +'/article/getExcellentArticles',
      method:'GET',
      success:function(res){
        console.log(res)
        if (res.statusCode == 200) {
          that.setData({ articleList: res.data.data });
        } else {
          console.log("请求异常")
        }
      }
    })
  },
  /**文章详情页面 */
  onToArticlePage: function (e) {
    const articleId = e.currentTarget.dataset.articleId;
    const nickName = e.currentTarget.dataset.nickName;
    const time = (e.currentTarget.dataset.time);
    const avatarUrl = e.currentTarget.dataset.avatarUrl;
    const uid = e.currentTarget.dataset.uid;
    wx.navigateTo({
      url: '../articles/articles?articleId=' + articleId + '&nickName=' + nickName + '&time=' + time + '&avatarUrl=' + avatarUrl + '&uid=' + uid,
    })
    console.log(e)
  }
})
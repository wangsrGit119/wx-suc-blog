// pages/myarticle/myarticle.js
const app = getApp()
import Toast from '../../components/vant/dist/toast/toast';
Page({

  /**
   * 页面的初始数据
   */
  data: {
    myAllArticle:null,//我的所有文章
    userId:null,//用户id
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    const that = this;
    let userId= app.globalData.userId;
    if(userId !=null){
      that.loadMyAllArticle(userId)
    }
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },
  /**加载文章列表 */
  loadMyAllArticle:function(userId){
    const that = this;
    wx.request({
      url: app.globalData.baseUrl + '/article/getAllArticleByUserId?userId=' + userId,
      method: 'GET',
      success: function (res) {
        console.log(res)
        if (res.statusCode == 200) {
          that.setData({
            myAllArticle: res.data.data
          });
        }
      }
    })
  },
/**删除文章 */
deleteArticle:function(e){
  const that = this;
  console.log(e)
  const articleId = e.currentTarget.dataset.articleId;
  wx.request({
    url: app.globalData.baseUrl +'/article/deleteArticle/'+articleId,
    method:'DELETE',
    success:function(res){
     console.log(res)
     if(res.statusCode==200){
       that.loadMyAllArticle(app.globalData.userId);
     }
    }
  })
},
  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    
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
  /**前往详情页面 */
  onToDetailPage:function(e){
    console.log(e)
    const articleId = e.currentTarget.dataset.articleId;
    const nickName = e.currentTarget.dataset.nickName;
    const time = (e.currentTarget.dataset.time);
    const avatarUrl = e.currentTarget.dataset.avatarUrl;
    const uid = e.currentTarget.dataset.uid;
    wx.navigateTo({
      url: '../articles/articles?articleId=' + articleId + '&nickName=' + nickName + '&time=' + time + '&avatarUrl=' + avatarUrl + '&uid=' + uid,
    })
  },
})
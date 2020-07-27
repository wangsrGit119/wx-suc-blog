// pages/community/community.js
const app = getApp()
import Toast from '../../components/vant/dist/toast/toast';
const util = require('../../utils/util.js')

Page({

  /**
   * 页面的初始数据
   */
  data: {
    value: "",//搜索
    hidden: true,									//隐藏表单控件
    page: 1,										//当前请求数据是第几页
    pageSize: 10,									//每页数据条数
    hasMoreData: true,								//上拉时是否继续请求数据，即是否还有更多数据
    articleList:[],
    noticeCommunity:"", //社区通告信息
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
  onShow: function (obj) {
    const that = this;
    that.data.page = 1
    that.onLoadAllInfo("正在加载数据")
   



  },
  /**加载数据 */
  onLoadAllInfo(message){
    const that = this;
    wx.showNavigationBarLoading()					//在当前页面显示导航条加载动画
    wx.showLoading({								//显示 loading 提示框
      title: message,
    })
    wx.request({
      url: app.globalData.baseUrl + '/article/getArticleCoverInfo',
      method: 'POST',
      data: {
        pageNo: that.data.page,
        pageSize: that.data.pageSize
      },
      header: {
        "Content-Type": "application/x-www-form-urlencoded"
      },
      success: function (res) {
        console.log(res);
        wx.hideNavigationBarLoading()
        wx.hideLoading()
        var allPageArticleList = that.data.articleList;
        if (that.data.page == 1) {
          allPageArticleList = []
        }
        if (res.statusCode == 200) {
          var list = res.data.data.records;
        
          if (list.length < that.data.pageSize || list.length ==0) {
            that.setData({
              articleList: allPageArticleList.concat(list),
              hasMoreData: false
            })
          } else {
            that.setData({
              articleList: allPageArticleList.concat(list),
              hasMoreData: true,
              page: that.data.page + 1
            })
          }
          // that.setData({ articleList: list });
        } else {
          console.log("请求异常")
        }
      },
      fail: function (res) {
        wx.hideNavigationBarLoading()
        wx.hideLoading()
        console.log("请求异常")
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
    this.data.page = 1
    this.onLoadAllInfo('正在刷新')
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    const that = this;
    if (that.data.hasMoreData) {
      that.onLoadAllInfo('加载更多数据')
    } else {
      wx.showToast({
        icon:'loading',
        title: '没有更多数据',
        duration: 2000
      })
    }
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },
    /**文章详情页面 */
  onToArticlePage:function(e){
    const articleId = e.currentTarget.dataset.articleId;
    const nickName = e.currentTarget.dataset.nickName;
    const time = (e.currentTarget.dataset.time);
    const avatarUrl = e.currentTarget.dataset.avatarUrl;
    const uid = e.currentTarget.dataset.uid;
    wx.navigateTo({
      url: '../articles/articles?articleId=' + articleId + '&nickName=' + nickName + '&time=' + time + '&avatarUrl=' + avatarUrl+'&uid='+uid,
    })
  }
})
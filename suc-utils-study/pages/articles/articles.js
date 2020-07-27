// pages/articles/articles.js
const app = getApp()
const util = require('../../utils/util.js')
import Toast from '../../components/vant/dist/toast/toast';
Page({

  /**
   * 页面的初始数据
   */
  data: {
    article: {  //文章详情
      id:'',
      title:'',
      content:'',
      coverImageUrl:'',
    },
    articleId:null,//文章id
    loading:false,//按钮加载状态
    praiseStatus:0,//点赞状态
    comments:null,//评论列表
    commentSize: 0,//评论列表大小
    childComments: null,//子评论列表
    scrollTop:0,//距离顶部距离  快速回到顶部或者评论框
    floorstatus:false,//回到顶部显示
    myComments:null,//我的评论
    currentTime: util.formatTime(new Date()),//当前时间
    userInfo: null,//用户信息
    authInfo:null,//作者信息
    userId:null,//用户id
    placeholderForComments:'说点什么呢？',//评论框默认显示
    commentId:'',//评论id（对评论回复使用）
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    const that = this;
    //获取文章id
    const articleId = options.articleId;
    that.setData({
       userId: app.globalData.userId,
       articleId: articleId,
       userInfo:app.globalData.userInfo,
       authInfo:{
         uid:options.uid,
         nickName: options.nickName,
         time:options.time,
         avatarUrl:options.avatarUrl
       }
       });
    that.articleDetail(articleId);
    that.loadComments(articleId);//加载评论
    that.toReadCount(articleId);//统计阅读量
    that.checkPraiseOrNot(articleId);//初始化点赞状态
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
    let temp1 = wx.getStorageSync("content").html; //获取临时保存内容
    //初始化评论id
    that.setData({commentId:''});
  
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
  /**获取文章详情 */
  articleDetail: function (articleId) {
    const that = this;
    wx.showNavigationBarLoading()					//在当前页面显示导航条加载动画
    wx.showLoading({								//显示 loading 提示框
      title: '文章加载中',
    })
    wx.request({
      url: app.globalData.baseUrl + '/article/getArticleDetailById?articleId=' + articleId,
      method: 'GET',
      success: function (res) {
        wx.hideNavigationBarLoading()
        wx.hideLoading()
        console.log(res);
        let jsonResult = JSON.parse(res.data.data)
        let type = jsonResult.parseType == 1 ? 'markdown':'html';
        that.setData({
          article: {
            id: jsonResult.id,
            title: jsonResult.title,
            content: app.towxml.toJson(jsonResult.content, type),
            coverImageUrl: jsonResult.coverImageUrl
          }
        });
      },
      fail: function (res) {
        wx.hideNavigationBarLoading()
        wx.hideLoading()
        console.log("请求异常")
      }

    })
  },
  /** 文章浏览量统计接口 */
  toReadCount:function(articleId){
    const that = this;
    if (app.globalData.userId == null || app.globalData.loginStatus == false) {
      return false;
    }
    let userId = app.globalData.userId;
    wx.request({
      url: app.globalData.baseUrl + '/article/toReadCount?userId=' + userId + '&articleId=' + articleId,
      method:'GET',
      success:function(res){
        console.log("流量统计接口调用成功");
      }
    })
  },
  /**点赞接口 */
  toPraiseCount: function (articleId, isPraise){
    const that = this;
    let userId= app.globalData.userId;
    if (app.globalData.userId == null || app.globalData.loginStatus == false) {
      that.notifyCommon("请先登录!")
      return false;
    }
    wx.request({
      url: app.globalData.baseUrl+'/article/toPraiseCount?userId='+userId+'&articleId='+articleId+'&isPraise='+isPraise,
      method: 'GET',
      success: function (res) {
        console.log("点赞接口调用成功");
      }
    })


  },
  /** 保存临时评论 */
  saveTempComments:function(e){
    const that = this;
    that.setData({ myComments: e.detail.value.replace(/\s+/g, '')});
  },
  /**验证评论 */
  validateComment:function(e){
    const that = this;
    if (app.globalData.userId == null || app.globalData.loginStatus == false) {
      that.notifyCommon("请先登录!")
      return false;
    }
    let com = that.data.myComments;
    if (com == null || com == ''){
      Toast.fail('不能为空');
      return;
    }
    
    wx.request({
      url: app.globalData.baseUrl + '/sys/msgCheck' ,
      method: 'POST',
      header:{
        "Content-Type": "application/x-www-form-urlencoded"
      },
      data:{content:com},
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
          that.submitComments(com)
       }
      }
    })
    
    
  },
  /**提交评论 */
  submitComments:function(comments){
    const that = this;
    that.setData({ loading: true })
    //1.提交评论
    wx.request({
      url: app.globalData.baseUrl +'/comment/addComment',
      method:'POST',
      data:{
        articleId:that.data.articleId,
        content: comments,
        userId:app.globalData.userId,
        commentId: that.data.commentId
      },
      header:{
        "Content-Type": "application/x-www-form-urlencoded"
      },
      success:function(res){
        console.log(res)
       if(res.data.code == 200){
         //2.延时loading 
         setTimeout(function () {
           //恢复禁止加载
           that.setData({ loading: false });
           //提醒用户评论结果
           wx.showToast({
             title: '评论成功',
           })
           that.setData({ myComments:''});
           //刷新接口 获取最新评论
           that.loadComments(that.data.articleId);//加载评论
         }, 2000)
       }else{
         console.log("评论失败")
       }
      }
    })
  },
  /** 分享 */
  onSharing:function(e){
    const that = this;
    wx.hideShareMenu();
    that.notifyCommon("分享")
    console.log(e)
  },

  
  /** 点赞 */
  onPraising:function(e){
    const that = this;
    let status = e.target.dataset.praiseStatus == 1 ? 0 : 1;
    that.setData({ praiseStatus: status })
    that.toPraiseCount(that.data.articleId, status)
    console.log(e)
  },
  /** 回到顶部 */
  onToTop:function(e){
    this.onToTargetArea(0);
  },
  /** 监听页面滚动 设置顶部图标出现与否 */
  onPageScroll:function(e){
    const that = this;
    if(e.scrollTop >= 500){
      that.setData({ floorstatus: true })
    }else{
      that.setData({ floorstatus: false })
    }
  },
  /**定位到指定高度 */
  onToTargetArea: function (param) {
    wx.pageScrollTo({
      scrollTop: param
    })
  },
  /**加载评论列表 */
  loadComments:function(articleId){
    const that = this;
    wx.request({
      url: app.globalData.baseUrl +'/comment/getCommentsById?articleId='+articleId,
      method:'GET',
      success:function(res){
        var commentList = JSON.parse(res.data.data);
        that.setData({
          comments: commentList,
          commentSize: that.getCommentsJsonLength(commentList),
        })
      }
    })
  },
  /**加载当前查看用户是否点赞 */
  checkPraiseOrNot:function(articleId){
    const that = this;
     if(app.globalData.userId == null){
       return false;
     }
     let userId = app.globalData.userId;
     wx.request({
       url: app.globalData.baseUrl + '/article/checkPraiseStatus?userId=' + userId +'&articleId='+articleId,
       method:'GET',
       success:function(res){
         that.setData({ praiseStatus: res.data.data.code })
       }
     })
  },
  /**删除评论接口 */
  onDeleteComments:function(e){
    const that = this;
    console.log(e)
    let commentId = e.currentTarget.dataset.commentId;
    let type = e.currentTarget.dataset.type
    wx.request({
      url: app.globalData.baseUrl + '/comment/deleteCommentById/' + commentId + '/' + type,
      method:'DELETE',
      success:function(res){
        if(res.data.code==200){
          that.loadComments(that.data.articleId)
          that.notifyCommon("删除成功!")
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
  /**获取评论大小 */
  getCommentsJsonLength:function(jsonArray){
    const that  = this;
    var length = 0;
    for (var item in jsonArray) {
      length++;
    }
    return length;
  },
  /** 对评论进行回复 */
  onToCommentFirstLevel:function(e){
    const that = this;
    console.log(e)
    that.setData({
      placeholderForComments:"回复"+e.currentTarget.dataset.parentUserInfo+":",
      commentId:e.currentTarget.dataset.commentId
      })
  }
})
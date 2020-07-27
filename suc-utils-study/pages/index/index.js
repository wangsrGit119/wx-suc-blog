//index.js
//获取应用实例
const app = getApp()
const util = require('../../utils/util.js')
import wxCharts from '../../components/wx-charts/wxcharts-min.js'
var lineChart = null;
Page({
  data: {
    motto: 'Hello World',
    value: [],
    elements: [
    //   {
    //   title: '知识星球',
    //   name: 'knowloage',
    //   color: 'cyan',
    //   icon: 'discover'
    // },
    {
      title: '我的足迹',
      name: 'timeline',
      color: 'blue',
      icon: 'footprint'
    },
    {
      title: '苏克简介',
      name: 'newstart',
      color: 'purple',
      icon: 'upstage'
    },
      {
        title: '问题反馈',
        name: 'feedback',
        color: 'green',
        icon: 'creative'
      },
    ],
    alldateSet: ["11-03", "11-04", "11-05", "11-05"],//图表时间集合
    allArticleTotal: [3, 4, 5,3],//所有文章集合（当天）
    allRegisterUser: [3, 5, 6,2],//当天注册用户
    toplineForTotal: [12, 12,12,12],
  },
  onLoad: function () {
    const that  = this;
  },
  /**初始化图表 */
  initChart:function(){
    const that = this;
    let windowWidth = 310;
    let windowHeight = 180;

    try {
      var res = wx.getSystemInfoSync();
      windowWidth = res.windowWidth-15;
      // windowHeight = res.windowHeight/3.5;
    } catch (e) {
      console.error('getSystemInfoSync failed!');
    }
    lineChart = new wxCharts({
      canvasId: 'lineCanvas',
      type: 'line',
      categories: that.data.alldateSet,
      animation: true,
      background: '#f5f5f5',
      series: [{
        name: '注册用户',
        data: that.data.allRegisterUser,
        format: function (val) {
          return val.toFixed(0) + '人';
        }
      }, {
        name: '发表文章数量',
        data: that.data.allArticleTotal,
        format: function (val) {
          return val.toFixed(0) + '篇';
        }
        },{
          name: 'standard line',
          data: that.data.toplineForTotal,
          format: function (val) {
            return val.toFixed(0);
          }
        }],
      yAxis: {
        title: '',
        format: function (val) {
          return val.toFixed(0);
        },
        min: 0
      },
      width: windowWidth,
      height: windowHeight,
      dataPointShape: true,
      extra: {
        lineStyle: 'curve'
      }
    });


  },
  /**加载统计数据 */
  onLoadStatisticResult:function(){
    const that = this;
    var alldateSet = [];
    var allArticleTotal = [];
    var allRegisterUser = [];
    var toplineForTotal = [12,12,12,12,12,12,12];
   
    wx.request({
      url: app.globalData.baseUrl +'/user/getStatisticResultForDay',
      method:'GET',
      success:function(res){
        console.log(res)
        if(res.statusCode == 200){
         let originList = res.data.data;
          for (var index in originList) {
            alldateSet.push(originList[index].dayEach.substring(5,10));
              allArticleTotal.push(originList[index].articleTotal);
              allRegisterUser.push(originList[index].userTotal);
            
          }
          that.setData({
            alldateSet: alldateSet.reverse(),
            allArticleTotal: allArticleTotal.reverse(),
            allRegisterUser: allRegisterUser.reverse(),
            toplineForTotal: toplineForTotal
          })
        }
      }
    })

  },
  /**点击chart响应 */
  touchHandler(e) {
    console.log(lineChart.getCurrentDataIndex(e));
    lineChart.showToolTip(e, {
      background: '#7cb5ec',
      format: function (item, category) {
        return category + ' ' + item.name + ':' + item.data
      }
    });   
  },
  onShow: function () {
    const that = this;
    //加载数据
    that.onLoadStatisticResult();
    setTimeout(function(){
      //初始化图表统计
      that.initChart()
    },1000)
  },
  
})

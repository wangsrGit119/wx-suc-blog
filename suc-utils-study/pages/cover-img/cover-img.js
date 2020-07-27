// pages/cover-img/cover-img.js
import Toast from '../../components/vant/dist/toast/toast';

//获取应用实例
var app = getApp()
Page({
  data: {
    allImagesInfo: null,
    userInfo: {},
    fileList: [],
  },
  onLoad: function () {
    var that = this
    that.getAllCoverImage();

  },
  onShow:function(){
    
  },
  /**上传校验 */
  beforeUpload(event) {
    const that = this;
    console.log(event)
    const { file, callback } = event.detail;
    if(file.type !='image'){
      that.notifyCommon("非图片文件!");
      return false;
    }
   //敏感图校验
    wx.uploadFile({
      url: app.globalData.baseUrl+'/sys/imgCheck',
      filePath: file.path,
      name: 'file',
      header: {
        "Content-Type": "multipart/form-data"
      },
      success:function(res){
        let resTep = JSON.parse(res.data)
        console.log(resTep)
        let errcode = JSON.parse(resTep.data).errcode;
        if (errcode != 0){
          that.notifyCommon("违规图片!");
        }else{
          that.setData({ fileList:[{url:file.path,name:file.name}]})
          callback(true)
        }
      }
    })

  },
  afterUpload(event){
    console.log(event)
  },
  /**保存上传文件 */
  onClickRight: function (fsPath) {
    const that = this;
    wx.uploadFile({
      url: app.globalData.baseUrl + "/web/upload",
      filePath: fsPath,
      name: 'file',
      formData: {
        'userId': app.globalData.userId
      },
      header: {
        "Content-Type": "multipart/form-data"
      },
      success: function (res) {
        console.log("原始数据")
        console.log(res)
        if (res.statusCode = 200) {
        
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
  /**获取所有封面图片url从数据库 */
  getAllCoverImage: function () {
    const that = this;
    wx.request({
      url: app.globalData.baseUrl + '/web/getCoverImages',
      method: 'GET',
      success: function (res) {
        let temp = JSON.parse(res.data.data)
        that.setData({
          allImagesInfo: temp
        });
        console.log(res)
      }
    })
  }
})

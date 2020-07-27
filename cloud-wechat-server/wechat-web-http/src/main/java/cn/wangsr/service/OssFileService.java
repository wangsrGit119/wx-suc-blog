package cn.wangsr.service;

import api.oss.OssFileManagerServiceApi;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;


/**
 * @author WJL
 */
@Service
public class OssFileService {
    @Reference(version = "1.0.0")
    OssFileManagerServiceApi ossFileManagerServiceApi;

    public String  uploadFile(String fileName, byte[] bytes,Integer userId){
        return ossFileManagerServiceApi.uploadFile(fileName, bytes,userId);
    }

    public String getCoverImage(){
      return   ossFileManagerServiceApi.getCoverImage();
    }

}

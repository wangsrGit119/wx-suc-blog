package cn.wangsr.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.PutObjectResult;
import model.oss.OssParamDTO;

import java.io.InputStream;

/**
 * @author WJL
 */
public class AliOssUploadFileUtil {

    /**
     * oss上传
     * @param ossParamDTO
     * @param inputStream
     * @return
     */
    public static PutObjectResult uploadFile(OssParamDTO ossParamDTO,InputStream inputStream){
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(ossParamDTO.getEndpoint(), ossParamDTO.getAccessKeyId(), ossParamDTO.getAccessKeySecret());
        PutObjectResult putObjectResult = null;
        // 上传文件流。
        try {
            putObjectResult = ossClient.putObject(ossParamDTO.getBucketName(), ossParamDTO.getObjectName(), inputStream);
            //权限设置
            ossClient.setBucketAcl(ossParamDTO.getBucketName(), CannedAccessControlList.PublicRead);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            // 关闭OSSClient。
            ossClient.shutdown();
        }
      return  putObjectResult;
    }


    public static String getUrl(OssParamDTO ossParamDTO){
        String url = "https://"+ossParamDTO.getBucketName()+"."+ossParamDTO.getEndpoint()+"/"+ossParamDTO.getObjectName();
        return url;
    }

    public static void   deleteFileFromOss(OssParamDTO ossParamDTO){
        OSS ossClient = new OSSClientBuilder().build(ossParamDTO.getEndpoint(), ossParamDTO.getAccessKeyId(), ossParamDTO.getAccessKeySecret());
        // 删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
        ossClient.deleteObject(ossParamDTO.getBucketName(), ossParamDTO.getObjectName());
        // 关闭OSSClient。
        ossClient.shutdown();
    }
}

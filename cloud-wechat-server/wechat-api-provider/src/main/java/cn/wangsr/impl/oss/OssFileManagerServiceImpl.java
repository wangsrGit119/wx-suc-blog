package cn.wangsr.impl.oss;

import api.oss.OssFileManagerServiceApi;
import cn.wangsr.dao.mapper.SysFileInfoMapper;
import cn.wangsr.model.po.SysFileInfo;
import cn.wangsr.utils.AliOssUploadFileUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.aliyun.oss.model.PutObjectResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import enums.CommonEnum;
import model.oss.OssParamDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

/**
 * @author WJL
 */
@Service(version = "1.0.0")
@Transactional(rollbackFor = {Exception.class})
public class OssFileManagerServiceImpl implements OssFileManagerServiceApi {
    private static Logger logger = LoggerFactory.getLogger(OssFileManagerServiceImpl.class);

    @Autowired
    SysFileInfoMapper sysFileInfoMapper;
    @Value("${oss.endpoint}")
    private String endpoint;
    @Value("${oss.accessKeyId}")
    private String accessKeyId;
    @Value("${oss.accessKeySecret}")
    private String accessKeySecret;
    @Value("${oss.bucketName}")
    private String bucketName;
    @Value("${oss.folder}")
    private String folder;

    @Override
    public String uploadFile(String fileName, byte[] bytes,Integer userId) {
        OssParamDTO ossParamDTO = OssParamDTO.builder()
                .endpoint(endpoint)
                .accessKeyId(accessKeyId)
                .accessKeySecret(accessKeySecret)
                .bucketName(bucketName)
                .objectName(folder+fileName).build();
        InputStream inputStream = new ByteArrayInputStream(bytes);
        PutObjectResult putObjectResult = AliOssUploadFileUtil.uploadFile(ossParamDTO,inputStream);
        logger.info(" putObjectResult {}", JSON.toJSONString(putObjectResult));
        if(putObjectResult != null){
            String url =  AliOssUploadFileUtil.getUrl(ossParamDTO);
            SysFileInfo sysFileInfo = new SysFileInfo();
            sysFileInfo.setFileUrl(url);
            sysFileInfo.setFileName(fileName);
            sysFileInfo.setUserId(userId);
            // 默认 文章内图片
            sysFileInfo.setType(CommonEnum.FILE_TYPE_ARTICLE_CONTENT.getKey());
            sysFileInfoMapper.insert(sysFileInfo);
            return JSON.toJSONString(sysFileInfo);
        }
        return null;
    }

    @Override
    public void deleteFile(String fileName) {
        OssParamDTO ossParamDTO = OssParamDTO.builder()
                .endpoint(endpoint)
                .accessKeyId(accessKeyId)
                .accessKeySecret(accessKeySecret)
                .bucketName(bucketName)
                .objectName(folder+fileName).build();
        AliOssUploadFileUtil.deleteFileFromOss(ossParamDTO);
    }

    @Override
    public String getCoverImage() {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("type",CommonEnum.FILE_TYPE_COVER.getKey());
        List list = sysFileInfoMapper.selectList(queryWrapper);
        return JSON.toJSONString(list, SerializerFeature.DisableCircularReferenceDetect);
    }
}

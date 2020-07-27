package api.oss;


/**
 * @author WJL
 */
public interface OssFileManagerServiceApi {
    /**
     * 文件上传
     * @param fileName
     * @param bytes
     * @param userId
     * @return
     */
    String uploadFile(String fileName, byte[] bytes,Integer userId);

    /**
     * 删除
     * @param fileName
     * @return
     */
    void deleteFile(String fileName);

    /**
     * 获取封面图片
     * @return
     */
    String getCoverImage();

}

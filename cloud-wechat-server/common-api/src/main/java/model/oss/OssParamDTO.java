package model.oss;


import lombok.Builder;
import lombok.Data;

/**
 * @author WJL
 */
@Data
@Builder
public class OssParamDTO {

    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;
    private String folder;
    /**
     * objectName = folder + fileName
     */
    private String objectName;

}

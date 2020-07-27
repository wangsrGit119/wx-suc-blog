package model.mail;

import lombok.Builder;
import lombok.Data;

/**
 * @author WJL
 */
@Data
@Builder
public class MailParamDTO {
    /**
     * 发送用户--目标
     */
    private String[] toUsers;
    /**
     * 主题
     */
    private String title;
    /**
     * 内容
     */
    private String context;
}

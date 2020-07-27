package api.mail;

import model.mail.MailParamDTO;

/**
 * @author WJL
 */
public interface MailServiceApi {

    /**
     * 邮件发送
     * @param mailParamDTO
     */
    void sendMessage(MailParamDTO mailParamDTO);

}

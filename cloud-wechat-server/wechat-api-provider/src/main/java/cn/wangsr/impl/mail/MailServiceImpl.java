package cn.wangsr.impl.mail;

import api.mail.MailServiceApi;
import model.mail.MailParamDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

/**
 * @author WJL
 */
@Service
public class MailServiceImpl implements MailServiceApi {
    private static Logger logger = LoggerFactory.getLogger(MailServiceApi.class);

    @Autowired
    JavaMailSenderImpl javaMailSender;
    @Value("${spring.mail.username}")
    private String fromMailAddress;
    @Value("${spring.mail.nickname}")
    private String nickName;

    @Override
    public void  sendMessage(MailParamDTO mailParamDTO)  {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message,  true, "utf-8");
            try {
                helper.setFrom(new InternetAddress(fromMailAddress,nickName));
            } catch (UnsupportedEncodingException e) {
                logger.error("message {}",e.getMessage());
                logger.error("cause  {}",e.getCause());
                e.printStackTrace();
            }
            helper.setTo(mailParamDTO.getToUsers());
            helper.setSubject(mailParamDTO.getTitle());
            helper.setText(mailParamDTO.getContext());
        } catch (MessagingException e) {
            logger.error("message {}",e.getMessage());
            logger.error("cause  {}",e.getCause());
            e.printStackTrace();
        }
        javaMailSender.send(message);
    }

}

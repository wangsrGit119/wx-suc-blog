package enums;

import lombok.Getter;

/**
 * 邮箱内容枚举设定值
 * @author WJL
 */
@Getter
public enum  EmailTypeEnum {
    /**
     * 邮箱标题
     */
    EMAIL_TYPE_TITLE_ONE("title_one","[苏克社区]邮箱验证"),
    EMAIL_TYPE_TITLE_TWO("title_two","[苏克社区]通知"),

    /**
     * 邮箱内容前半部分
     */
    EMAIL_TYPE_CONTENT_ONE("content_one","[苏克社区]您的邮箱验证码为："),

    EMAIL_TYPE_CONTENT_TWO("content_two","，验证码有效期为："),

    /**
     * 自定义中间内容
     * 邮箱内容后半部分
     */
    EMAIL_TYPE_TAIL_ONE("tail_one","，请勿告诉他人，苏克社区欢迎您！");

    private String type;
    private String msg;

    EmailTypeEnum(String type, String msg){
        this.type = type;
        this.msg = msg;
    }

}

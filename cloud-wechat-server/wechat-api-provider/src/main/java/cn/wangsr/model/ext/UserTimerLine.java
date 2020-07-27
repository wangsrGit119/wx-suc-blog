package cn.wangsr.model.ext;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
/**
 * 扩展
 * 用户时间线结果
 * @author WJL
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserTimerLine implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 用户id
     */
    private Integer uid;
    /**
     * 用户注册时间
     */
    private String registerTime;
    /**
     * 第一篇文章标题
     */
    private String articleTitle;
    /**
     *第一篇文章发表时间
     */
    private String firstArticlePubTime;
    /**
     *第一次评论
     */
    private String comments;
    /**
     * 第一次评论时间
     */
    private String firstCommentTime;

}

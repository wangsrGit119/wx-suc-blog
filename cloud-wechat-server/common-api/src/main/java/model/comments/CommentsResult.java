package model.comments;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author WJL
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentsResult implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 评论id
     */
    private Integer id;

    /**
     * 文章id
     */
    private Integer articleId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论用户id
     */
    private Integer fromUid;

    /**
     * 昵称
     */
    private String nickName;
    /**
     * 性别
     */
    private Integer gender;

    /**
     * 头像地址
     */
    private String avatarUrl;

    /**
     * 子评论
     */
    private List<CommentsResult> commentsResultChilds;

    private LocalDateTime createTime;

    private LocalDateTime modifyTime;


}

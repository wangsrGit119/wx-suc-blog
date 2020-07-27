package model.article;


import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 文章封面
 * </p>
 *
 * @author wjl
 * @since 2019-11-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleCoverDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文章id
     */
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户头像url
     */
    private String  avatarUrl;

    /**
     * 标题
     */
    private String title;


    /**
     * 点赞数量
     */
    private Integer praiseCount;

    /**
     * 阅读量
     */
    private Integer readCount;


    /**
     * 评论数量
     */
    private Integer commentCount;

    /**
     * 封面url
     */
    private String coverImageUrl;

    /**
     * 文章审核状态
     */
    private Integer auditStatus;

    /**
     * 解析类型  1.markdown 2.html 
     */
    private Integer parseType;

    /**
     * 所属类型 1.精选 2.普通
     */
    private Integer belongsType;


    private LocalDateTime createTime;


    private LocalDateTime modifyTime;


    private Integer deleteStatus;


}

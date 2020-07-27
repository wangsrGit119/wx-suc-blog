package model.article;


import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * RPC添加文章信息
 * </p>
 *
 * @author wjl
 * @since 2019-11-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleInfoDTO implements Serializable {

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
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

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

package model.notice;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 社区通告
 * </p>
 *
 * @author wjl
 * @since 2019-11-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommunityNoticeDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 通告详情
     */
    private String details;

    /**
     * 通告类型 1.首页通告 2.社区通告 3.其他的通告
     */
    private Integer type;

    private LocalDateTime createTime;

    private LocalDateTime modifyTime;

    private Integer deleteStatus;


}

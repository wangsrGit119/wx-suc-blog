package model.notice;


import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 社区反馈信息
 * </p>
 *
 * @author wjl
 * @since 2019-11-24
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityFeedBackDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Integer id;

    /**
     * 反馈详情
     */
    private String details;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 用户名称
     */
    private String nickName;

    /**
     * 用户头像
     */
    private String avatarUrl;

    /**
     * 用户性别
     */
    private Integer gender;

    /**
     * 回复反馈详情
     */
    private String replayContent;

    /**
     * 是否恢复 0未回复 1已回复
     */
    private Integer replay;

    private LocalDateTime createTime;

    private LocalDateTime modifyTime;


    private Integer deleteStatus;



}

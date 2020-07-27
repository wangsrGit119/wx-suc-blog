package cn.wangsr.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 社区反馈信息
 * </p>
 *
 * @author wjl
 * @since 2019-11-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CommunityFeedBack extends Model<CommunityFeedBack> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 反馈详情
     */
    @TableField("details")
    private String details;

    /**
     * 用户id
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 回复反馈详情
     */
    @TableField("replay_content")
    private String replayContent;

    /**
     * 是否恢复 0未回复 1已回复
     */
    @TableField("replay")
    private Integer replay;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(value = "modify_time", fill = FieldFill.UPDATE)
    private LocalDateTime modifyTime;

    @TableField(value = "delete_status", fill = FieldFill.INSERT)
    @TableLogic
    private Integer deleteStatus;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}

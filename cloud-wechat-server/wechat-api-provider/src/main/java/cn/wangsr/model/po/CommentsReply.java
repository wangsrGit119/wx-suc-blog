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
 * 评论回复
 * </p>
 *
 * @author wjl
 * @since 2019-11-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CommentsReply extends Model<CommentsReply> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 评论id
     */
    @TableField("comments_id")
    private Integer commentsId;

    /**
     * 回复目标id  对评论的回复 comments_id
     */
    @TableField("reply_id")
    private Integer replyId;

    /**
     * 类型 comment 对评论的回复  reply对回复的回复 
     */
    @TableField("reply_type")
    private String replyType;

    /**
     * 回复内容
     */
    @TableField("content")
    private String content;

    /**
     * 回复用户id
     */
    @TableField("from_uid")
    private Integer fromUid;

    /**
     * 目标用户id
     */
    @TableField("to_uid")
    private Integer toUid;

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

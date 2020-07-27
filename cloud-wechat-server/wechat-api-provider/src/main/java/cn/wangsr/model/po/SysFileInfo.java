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
 * 上传文件信息
 * </p>
 *
 * @author wjl
 * @since 2019-12-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysFileInfo extends Model<SysFileInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 文件url
     */
    @TableField("file_url")
    private String fileUrl;

    /**
     * 文件名称
     */
    @TableField("file_name")
    private String fileName;

    /**
     * 0-文章图片 1-封面图片 2-其他
     */
    @TableField("type")
    private Integer type;

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

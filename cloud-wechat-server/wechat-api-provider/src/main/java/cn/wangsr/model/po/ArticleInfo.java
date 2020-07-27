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
 * 文章信息
 * </p>
 *
 * @author wjl
 * @since 2019-12-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ArticleInfo extends Model<ArticleInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 文章id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 标题
     */
    @TableField("title")
    private String title;

    /**
     * 内容
     */
    @TableField("content")
    private String content;

    /**
     * 封面url
     */
    @TableField("cover_image_url")
    private String coverImageUrl;

    /**
     * 文章审核状态
     */
    @TableField("audit_status")
    private Integer auditStatus;

    /**
     * 解析类型  1.markdown 2.html 
     */
    @TableField("parse_type")
    private Integer parseType;

    /**
     * 浏览量，redis同步
     */
    @TableField("read_count")
    private Integer readCount;

    /**
     *  文章点赞数量，redis同步
     */
    @TableField("praise_count")
    private Integer praiseCount;

    /**
     * 所属类型 1.置顶 2.普通
     */
    @TableField("belongs_type")
    private Integer belongsType;

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

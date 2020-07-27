package cn.wangsr.model.ext;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * 扩展
 * 统计结果
 * @author WJL
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class StatisticInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 天
     */
    private String dayEach;
    /**
     * 文章数量
     */
    private  int articleTotal;
    /**
     * 用户注册数量
     */
    private int userTotal;


}

package cn.wangsr.dao.mapper.ext;

import cn.wangsr.model.ext.ArticleExtInfo;
import cn.wangsr.model.ext.StatisticInfo;
import cn.wangsr.model.ext.UserTimerLine;
import org.apache.ibatis.annotations.Param;


import java.util.List;

/**
 * <p>
 * 文章信息 Mapper 扩展 接口
 * </p>
 *
 * @author wjl
 * @since 2019-11-28
 */
public interface ArticleInfoExtMapper {

    /**
     * 统计优质文章
     * @return
     */
    List<ArticleExtInfo> queryExcellentArticle();

    /**
     * 获取统计结果 近几天用户发表文章数量 用户注册统计结果
     * @return
     */
    List<StatisticInfo> getStatisticResult();

    /**
     * 获取用户时间线
     * @param userId
     * @return
     */
    UserTimerLine getTimerLineById(@Param("userId") Integer userId);

}

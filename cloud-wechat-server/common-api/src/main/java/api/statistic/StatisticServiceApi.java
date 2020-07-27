package api.statistic;

import model.ext.StatisticInfoDTO;
import model.ext.UserTimerLineDTO;

import java.util.List;

/**
 * @author WJL
 */
public interface StatisticServiceApi  {

    /**
     * 近几天用户发表文章数量 用户注册统计结果
     * 访问统计
     * @return
     */
    List<StatisticInfoDTO> getStatisticResultForDay();

    /**
     * 获取用户社区时间线
     * @param uid
     * @return
     */
     UserTimerLineDTO getTimeLineById(Integer uid);

}

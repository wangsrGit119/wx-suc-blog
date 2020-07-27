package cn.wangsr.service;

import api.statistic.StatisticServiceApi;
import com.alibaba.dubbo.config.annotation.Reference;
import model.ext.StatisticInfoDTO;
import model.ext.UserTimerLineDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author WJL
 */
@Service
public class StatisticService {
    @Reference(version = "1.0.0")
    StatisticServiceApi statisticServiceApi;

    public List<StatisticInfoDTO> getStatisticResultForDay(){
        return statisticServiceApi.getStatisticResultForDay();
    }

    public UserTimerLineDTO getTimerLineById(Integer uid){
       return statisticServiceApi.getTimeLineById(uid);
    }



}

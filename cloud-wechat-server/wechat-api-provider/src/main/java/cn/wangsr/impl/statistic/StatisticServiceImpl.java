package cn.wangsr.impl.statistic;

import api.statistic.StatisticServiceApi;
import cn.wangsr.dao.mapper.ext.ArticleInfoExtMapper;
import cn.wangsr.model.ext.StatisticInfo;
import cn.wangsr.model.ext.UserTimerLine;
import com.alibaba.dubbo.config.annotation.Service;
import model.ext.StatisticInfoDTO;
import model.ext.UserTimerLineDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WJL
 */
@Service(version = "1.0.0")
public class StatisticServiceImpl implements StatisticServiceApi {
    @Autowired
    ArticleInfoExtMapper articleInfoExtMapper;
    @Override
    public List<StatisticInfoDTO> getStatisticResultForDay() {
        List<StatisticInfoDTO> list = new ArrayList();
        List<StatisticInfo> statisticInfos = articleInfoExtMapper.getStatisticResult();
        statisticInfos.forEach(statisticInfo -> {
            System.out.println(statisticInfo.toString());
            StatisticInfoDTO statisticInfoDto = new StatisticInfoDTO();
            BeanUtils.copyProperties(statisticInfo,statisticInfoDto);
            list.add(statisticInfoDto);
        });
        return list;
    }

    @Override
    public UserTimerLineDTO getTimeLineById(Integer uid) {
        UserTimerLineDTO userTimerLineDTO = new UserTimerLineDTO();
        UserTimerLine userTimerLine = articleInfoExtMapper.getTimerLineById(uid);
        if(userTimerLine !=null){
            BeanUtils.copyProperties(userTimerLine,userTimerLineDTO);
        }
        return userTimerLineDTO;
    }
}

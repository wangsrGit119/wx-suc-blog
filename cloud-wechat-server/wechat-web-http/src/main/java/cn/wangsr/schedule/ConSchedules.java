package cn.wangsr.schedule;

import cn.wangsr.service.HttpClientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author WJL
 */
@Component
@EnableScheduling
public class ConSchedules {
    @Autowired
    HttpClientsService httpClientsService;

    /**
     * 每隔半小时 生成一次
     * 用于生成微信官方api token
     */
    @Scheduled(cron = "0 0/30 * * * ?")
    public void generateWxToken(){
        httpClientsService.generateWxToken();
    }

}

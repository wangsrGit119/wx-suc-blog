package cn.wangsr.redis;

import cn.wangsr.impl.article.ArticleOperationServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author: wjl
 * @description:
 * @time: 2019/12/2 0002 13:15
 */
@SpringBootTest
public class RedisTest {
    @Autowired
    ArticleOperationServiceImpl articleOperationService;
    @Test
    void contextLoads() {
//        articleOperationService.toReadCount(2,4,"192.168.2.3");
//        articleOperationService.toReadCount(3,5,"192.168.2.2");
        System.out.println(articleOperationService.checkCurrentPraiseStatus(1,6).toString());
    }
}

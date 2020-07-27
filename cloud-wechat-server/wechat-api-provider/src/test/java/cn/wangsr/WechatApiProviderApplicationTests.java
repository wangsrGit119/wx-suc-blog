package cn.wangsr;

import cn.wangsr.dao.mapper.ArticleInfoMapper;
import cn.wangsr.impl.article.ArticleServiceImpl;
import cn.wangsr.impl.mail.MailServiceImpl;
import cn.wangsr.impl.oss.OssFileManagerServiceImpl;
import cn.wangsr.impl.statistic.StatisticServiceImpl;
import cn.wangsr.model.po.ArticleInfo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import enums.EmailTypeEnum;
import model.mail.MailParamDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class WechatApiProviderApplicationTests {

    @Autowired
    OssFileManagerServiceImpl ossFileManagerService;

    @Autowired
    ArticleInfoMapper articleInfoMapper;

    @Autowired
    ArticleServiceImpl articleService;

    @Autowired
    StatisticServiceImpl statisticService;
    @Test
    void contextLoads() {
    }

    /**
     * oss  delete api
     */
    @Test
    void contextLoadsForDeleteFile() {
        ossFileManagerService.deleteFile("wx98e9bd4331328790.o6zAJs3bwYYsgftu6UZhFmLDT6_U.ywlc9ibdxZZJe3d47f3004658de3158ff1d46e52d43c.jpg");
    }
    @Autowired
    MailServiceImpl mailService;
    @Test
    void contextLoadsToSendMessage() {
        String[] users = {"1215618342@qq.com"};
        StringBuffer buffer = new StringBuffer();
        buffer.append(EmailTypeEnum.EMAIL_TYPE_CONTENT_ONE.getMsg())
                .append(828395)
                .append(EmailTypeEnum.EMAIL_TYPE_CONTENT_TWO.getMsg())
                .append(20)
                .append(EmailTypeEnum.EMAIL_TYPE_TAIL_ONE.getMsg());
        MailParamDTO mailParamDTO = MailParamDTO.builder()
                .toUsers(users)
                .title("[苏克社区]"+"邮箱验证")
                .context("你好")
//                .context("您的邮箱验证码为 897302，请勿告诉任何人")
                .build();
        mailService.sendMessage(mailParamDTO);
    }



    /**
     * 文章信息测试
     */
    @Test
    void contextLoadsForArticle() {
//        System.out.println(articleInfoMapper.selectById(1).toString());
        ArticleInfo articleInfo = new ArticleInfo();
        articleInfo.setId(1);
        articleInfo.setTitle("测试1");
        articleInfo.setContent("<p></p>");
        articleInfo.setCoverImageUrl(null);
//        articleInfoMapper.updateById(articleInfo);
        articleInfo.deleteById(1);

    }

    /**精选文章查询测试**/

    @Test
    void contextLoadsExcellentArticle() {

        List list =articleService.getExcellentArticle();
        System.out.println(list.size());
    }
    /**统计结果测试**/
    @Test
    public void  getStatistic(){
        List list = statisticService.getStatisticResultForDay();
        list.forEach(ele->{
            System.out.println(ele.toString());
        });
    }

    /**时间线测试**/
    @Test
    public void  getTimerLine(){
        System.out.println(statisticService.getTimeLineById(1).toString());

    }
}

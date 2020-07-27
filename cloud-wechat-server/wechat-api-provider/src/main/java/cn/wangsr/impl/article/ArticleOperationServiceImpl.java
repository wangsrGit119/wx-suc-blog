package cn.wangsr.impl.article;

import api.article.ArticleOperationServiceApi;
import cn.wangsr.dao.mapper.ArticleInfoMapper;
import cn.wangsr.model.po.ArticleInfo;
import com.alibaba.dubbo.config.annotation.Service;
import enums.RedisKeyTypeEnum;
import model.common.CommonStatusDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: wjl
 */
@Service(version = "1.0.0")
public class ArticleOperationServiceImpl implements ArticleOperationServiceApi {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    ArticleInfoMapper articleInfoMapper;


    @Override
    public void toPraiseCount(Integer userId, Integer articleId, Integer isPraise) {
        redisTemplate.opsForHash().put(RedisKeyTypeEnum.PRAISE_ARTICLE_ID.getValue()+articleId,"uid:"+userId,isPraise);
    }


    @Override
    public void updateArticleCount(Integer articleId){
        ArticleInfo articleInfo = articleInfoMapper.selectById(articleId);
        //init data
        Map<Object,Integer> map = new HashMap<>();
        map.put(RedisKeyTypeEnum.PRAISE_ARTICLE_ID.getValue(),0);
        map.put(RedisKeyTypeEnum.SCAN_ARTICLE_ID.getValue(),0);
        //update praise
        redisTemplate.opsForHash().entries(RedisKeyTypeEnum.PRAISE_ARTICLE_ID.getValue()+articleId).forEach((k,v)->{
            if((int)v !=0){
                int count = map.get(RedisKeyTypeEnum.PRAISE_ARTICLE_ID.getValue())+1;
                map.put(RedisKeyTypeEnum.PRAISE_ARTICLE_ID.getValue(),count);
            }
        });

        //update read
        redisTemplate.opsForHash().entries(RedisKeyTypeEnum.SCAN_ARTICLE_ID.getValue()+articleId).forEach((k,v)->{
            if((int)v !=0){
                int count = map.get(RedisKeyTypeEnum.SCAN_ARTICLE_ID.getValue())+1;
                map.put(RedisKeyTypeEnum.SCAN_ARTICLE_ID.getValue(),count);
            }
        });
        articleInfo.setPraiseCount(map.get(RedisKeyTypeEnum.PRAISE_ARTICLE_ID.getValue()));
        articleInfo.setReadCount(map.get(RedisKeyTypeEnum.SCAN_ARTICLE_ID.getValue()));
        articleInfoMapper.updateById(articleInfo);
    }

    @Override
    public void toReadCount(Integer userId, Integer articleId, String ip) {
        redisTemplate.opsForHash().put(RedisKeyTypeEnum.SCAN_ARTICLE_ID.getValue()+articleId,ip+userId,1);
    }

    @Override
    public CommonStatusDto checkCurrentPraiseStatus(Integer userId, Integer articleId) {
        CommonStatusDto commonStatusDto = new CommonStatusDto();
        commonStatusDto.setType("praise");
        commonStatusDto.setCode(0);
        Object res = redisTemplate.opsForHash().get(RedisKeyTypeEnum.PRAISE_ARTICLE_ID.getValue()+articleId,"uid:"+userId);
        if(res != null){
            commonStatusDto.setCode((int)res);
            return commonStatusDto;
        }
        return commonStatusDto;
    }


}

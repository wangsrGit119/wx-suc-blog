package cn.wangsr.service;

import api.article.ArticleOperationServiceApi;
import api.article.ArticleServiceApi;
import com.alibaba.dubbo.config.annotation.Reference;
import model.article.ArticleCoverDTO;
import model.article.ArticleInfoDTO;
import model.common.CommonStatusDto;
import model.page.PageResultDto;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author WJL
 */
@Service
public class ArticleService {

    @Reference(version = "1.0.0")
    ArticleServiceApi articleServiceApi;

    @Reference(version = "1.0.0")
    ArticleOperationServiceApi articleOperationServiceApi;

    public String insertArticle(ArticleInfoDTO articleInfoDTO){
       return articleServiceApi.insertArticle(articleInfoDTO);
    }


    public String getArticleById( Integer articleId){
        return articleServiceApi.loadArticleByUserIdAndArticleId(articleId);
    }

    public PageResultDto getArticleCoverInfo(Integer pageNo,Integer pageSize){
        PageResultDto<ArticleCoverDTO> pageResultDto = articleServiceApi.getArticles(pageNo,pageSize);
        pageResultDto.getRecords().forEach(e->{
            //从redis同步更新点赞 阅读数量
            articleOperationServiceApi.updateArticleCount(e.getId());
        });
        return pageResultDto;
    }


    public void toPraiseCount(Integer userId,Integer articleId,Integer isPraise){
        articleOperationServiceApi.toPraiseCount(userId, articleId, isPraise);
    }

    public void toReadCount(Integer userId,Integer articleId,String  ip){
        articleOperationServiceApi.toReadCount(userId, articleId, ip);
    }

    public CommonStatusDto checkCurrentPraiseStatus(Integer userId,Integer articleId){
        return articleOperationServiceApi.checkCurrentPraiseStatus(userId, articleId);
    }


    public List<ArticleCoverDTO> getAllArticleByUserId(Integer userId){
       return articleServiceApi.getAllArticleByUserId(userId);
    }


    public String deleteArticleById(Integer articleId){
       return articleServiceApi.deleteArticleById(articleId);
    }


    public List<ArticleCoverDTO> getExcellentArticles(){
        return articleServiceApi.getExcellentArticle();
    }


}

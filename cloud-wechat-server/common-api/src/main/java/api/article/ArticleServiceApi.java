package api.article;

import model.article.ArticleCoverDTO;
import model.article.ArticleInfoDTO;
import model.page.PageResultDto;

import java.util.List;

/**
 * @author WJL
 */
public interface ArticleServiceApi {

    /**
     * 文章插入
     * @param articleInfoDTO
     * @return
     */
    String insertArticle(ArticleInfoDTO articleInfoDTO);

    /**
     * 获取文章详情
     * @param articleId
     * @return
     */
    String loadArticleByUserIdAndArticleId(Integer articleId);

    /**
     * 获取所有文章封面信息
     * @param pageNo
     * @param pageSize
     * @return
     */
    PageResultDto<ArticleCoverDTO> getArticles(Integer pageNo, Integer pageSize);

    /**
     * 获取当前用户所有文章
     * @param userId
     * @return
     */
    List<ArticleCoverDTO> getAllArticleByUserId(Integer userId);


    /**
     * 删除文章
     * @param articleId
     * @return
     */
    String deleteArticleById(Integer articleId);

    /**
     * 获取点赞 阅读量 排名前三的文章
     * @return
     */
    List<ArticleCoverDTO> getExcellentArticle();


}

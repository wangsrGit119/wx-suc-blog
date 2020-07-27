package api.article;

import model.common.CommonStatusDto;

/**
 * @author WJL
 */
public interface ArticleOperationServiceApi {


    /**
     * 点赞  0未赞 1已赞
     * @param userId
     * @param articleId
     * @param isPraise
     * @return
     */
    void toPraiseCount(Integer userId,Integer articleId,Integer isPraise);



    /**
     * redis统计同步到sql
     * @param articleId
     * @return
     */
    void updateArticleCount(Integer articleId);

    /**
     * 浏览
     * @param userId
     * @param articleId
     * @param ip
     * @return
     */
    void toReadCount(Integer userId,Integer articleId,String ip);


    /**
     * 获取当前用户是否点赞 状态
     * @param userId
     * @param articleId
     * @return
     */
    CommonStatusDto checkCurrentPraiseStatus(Integer userId, Integer articleId);




}

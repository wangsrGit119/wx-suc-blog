package cn.wangsr.controller.wechat;

import cn.wangsr.service.ArticleService;
import ip.HttpIpUtils;
import model.article.ArticleCoverDTO;
import model.article.ArticleInfoDTO;
import model.common.CommonStatusDto;
import model.result.ResultMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author WJL
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    ArticleService articleService;
    private static Logger logger = LoggerFactory.getLogger(ArticleController.class);

    /**
     * 插入文章
     * @param articleInfoDTO
     * @return
     */
    @PostMapping("/add")
    public ResultMessage addArticle(@RequestBody ArticleInfoDTO articleInfoDTO){
        return ResultMessage.success(articleService.insertArticle(articleInfoDTO));
    }


    /**
     * 获取文章详情
     * @param articleId
     * @return
     */
    @GetMapping("/getArticleDetailById")
    public ResultMessage getArticleDetailById( @RequestParam Integer articleId){
        return ResultMessage.success(articleService.getArticleById(articleId));
    }

    /**
     * 获取所有文章封面
     * @param pageNo
     * @param pageSize
     * @return
     */
    @PostMapping("/getArticleCoverInfo")
    public ResultMessage getArticleCoverInfo(@RequestParam Integer pageNo, @RequestParam Integer pageSize){
        return ResultMessage.success(articleService.getArticleCoverInfo(pageNo, pageSize));
    }

    /**
     * 点赞
     * @param userId
     * @param articleId
     * @param isPraise
     * @return
     */
    @GetMapping("/toPraiseCount")
    public ResultMessage toPraiseCount(@RequestParam Integer userId, @RequestParam Integer articleId,@RequestParam Integer isPraise){
        articleService.toPraiseCount(userId, articleId, isPraise);
        return ResultMessage.success(null);
    }

    /**
     * 浏览
     * @param userId
     * @param articleId
     * @return
     */
    @GetMapping("/toReadCount")
    public ResultMessage toReadCount(HttpServletRequest request, @RequestParam Integer userId, @RequestParam Integer articleId){
        articleService.toReadCount(userId, articleId,HttpIpUtils.getClientIp(request));
        return ResultMessage.success(null);
    }

    /**
     * 获取当前用户在当前文章的点赞状态
     * @param userId
     * @param articleId
     * @return
     */
    @GetMapping("/checkPraiseStatus")
    public ResultMessage checkPraiseStatus( @RequestParam  Integer  userId, @RequestParam  Integer articleId){
        CommonStatusDto commonStatusDto = articleService.checkCurrentPraiseStatus(userId, articleId);
        return ResultMessage.success(commonStatusDto);
    }

    /**
     * 获取当前用户文章
     * @param userId
     * @return
     */
    @GetMapping("/getAllArticleByUserId")
    public ResultMessage getAllArticleByUserId(@RequestParam Integer userId){
        List<ArticleCoverDTO> articleCoverDTOList = articleService.getAllArticleByUserId(userId);
        return ResultMessage.success(articleCoverDTOList);
    }

    /**
     * 删除文章
     * @param articleId
     * @return
     */
    @DeleteMapping("/deleteArticle/{articleId}")
    public ResultMessage deleteArticleById(@PathVariable Integer articleId){
        String result = articleService.deleteArticleById(articleId);
        return ResultMessage.success(result);
    }
    /**
     * 获取优质文章
     * @return
     */
    @GetMapping("/getExcellentArticles")
    public ResultMessage getExcellentArticles(){
        List result = articleService.getExcellentArticles();
        return ResultMessage.success(result);
    }

}

package cn.wangsr.service;

import api.comments.CommentsServiceApi;
import com.alibaba.dubbo.config.annotation.Reference;
import model.comments.CommentsResult;
import org.springframework.stereotype.Service;

/**
 * @author WJL
 */
@Service
public class CommentsService {

    @Reference(version = "1.0.0")
    CommentsServiceApi commentsServiceApi;

    public String addComment(Integer articleId,String content,Integer userId,Integer commentId){
        return  commentsServiceApi.addComment(articleId,content,userId,commentId);
    }


    public String getCommentsById(Integer articleId){
        return commentsServiceApi.getCommentsById(articleId);
    }

    public String deleteCommentById(Integer commentId,String type){
        return commentsServiceApi.deleteCommentById(commentId,type);
    }

}

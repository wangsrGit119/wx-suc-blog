package cn.wangsr.controller.wechat;

import cn.wangsr.service.CommentsService;
import model.result.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author WJL
 */
@RestController
@RequestMapping("/comment")
public class CommentsController {

    @Autowired
    CommentsService commentsService;

    /**
     * 添加评论
     * @param articleId, content, userId
     * @return model.result.ResultMessage
     */
    @PostMapping("/addComment")
    public ResultMessage addComment(@RequestParam Integer articleId,@RequestParam String content,
                                    @RequestParam Integer userId,@RequestParam(defaultValue = "") Integer commentId){
        String result =  commentsService.addComment(articleId, content, userId,commentId);
        return ResultMessage.success(result);
    }

    /**
     * 获取评论
     * @param articleId
     * @return model.result.ResultMessage
     */
    @GetMapping("/getCommentsById")
    public ResultMessage getCommentsById(Integer articleId){
        String result = commentsService.getCommentsById(articleId);
        return ResultMessage.success(result);
    }

    /**
     * 删除评论
     * @param commentId
     * @return model.result.ResultMessage
     */
    @DeleteMapping("/deleteCommentById/{commentId}/{type}")
    public ResultMessage deleteCommentById(@PathVariable Integer commentId,@PathVariable String type){

        String result = commentsService.deleteCommentById(commentId,type);
        return ResultMessage.success(result);
    }

}

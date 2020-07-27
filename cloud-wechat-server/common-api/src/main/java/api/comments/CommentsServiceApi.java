package api.comments;

/**
 * @author WJL
 */
public interface CommentsServiceApi {

    /**
     * 添加评论
     * @param articleId
     * @param content
     * @param userId
     * @param userId
     * @return commentId
     */
      String addComment(Integer articleId,String content,Integer userId,Integer commentId);


    /**
     * 获取评论
     * @param articleId
     * @return
     */
      String getCommentsById(Integer articleId);

    /**
     * 删除评论
     * @param commentId
     *  @param type
     * @return
     */
      String deleteCommentById(Integer commentId,String type);
}

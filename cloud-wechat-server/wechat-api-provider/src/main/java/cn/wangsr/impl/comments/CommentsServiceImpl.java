package cn.wangsr.impl.comments;

import api.comments.CommentsServiceApi;
import cn.wangsr.dao.mapper.ArticleCommentsMapper;
import cn.wangsr.dao.mapper.BaseUserInfoMapper;
import cn.wangsr.dao.mapper.CommentsReplyMapper;
import cn.wangsr.model.po.ArticleComments;
import cn.wangsr.model.po.BaseUserInfo;
import cn.wangsr.model.po.CommentsReply;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import enums.DaoResultEnum;
import model.comments.CommentsResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WJL
 */
@Service(version = "1.0.0")
@Transactional(rollbackFor = {Exception.class})
public class CommentsServiceImpl implements CommentsServiceApi {

    @Autowired
    ArticleCommentsMapper articleCommentsMapper;
    @Autowired
    CommentsReplyMapper commentsReplyMapper;
    @Autowired
    BaseUserInfoMapper baseUserInfoMapper;
    @Override
    public String addComment(Integer articleId, String content, Integer userId,Integer commentId) {
        //对文章评论
        ArticleComments articleComments = new ArticleComments();
        articleComments.setArticleId(articleId);
        articleComments.setContent(content);
        articleComments.setFromUid(userId);
        //对评论回复
        CommentsReply commentsReply = new CommentsReply();
        commentsReply.setCommentsId(commentId);
        commentsReply.setContent(content);
        commentsReply.setFromUid(userId);
        int result;
        if(null != commentId){
            result = commentsReplyMapper.insert(commentsReply);
        }else {
            result = articleCommentsMapper.insert(articleComments);
        }
        return DaoResultEnum.getValueByKey(result);
    }

    @Override
    public String getCommentsById(Integer articleId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        List<CommentsResult> list = new ArrayList();
        queryWrapper.eq("article_id",articleId);
        queryWrapper.orderByDesc("create_time");
        List<ArticleComments> articleComments = articleCommentsMapper.selectList(queryWrapper);
        articleComments.forEach(ele -> {
            //基础评论获取
            CommentsResult commentsResult = new CommentsResult();
            BeanUtils.copyProperties(ele,commentsResult);
            BaseUserInfo baseUserInfo = baseUserInfoMapper.selectById(ele.getFromUid());
            commentsResult.setNickName(baseUserInfo.getNickName());
            commentsResult.setGender(baseUserInfo.getGender());
            commentsResult.setAvatarUrl(baseUserInfo.getAvatarUrl());
            //获取子评论--对基础评论的回复
            QueryWrapper<CommentsReply> commentsReplyQueryWrapper = new QueryWrapper<>();
            commentsReplyQueryWrapper.eq("comments_id",ele.getId());
            List<CommentsReply> commentsReplies = commentsReplyMapper.selectList(commentsReplyQueryWrapper);
            List<CommentsResult> commentsResultChildsList = new ArrayList<>();
            commentsReplies.forEach(commentsReply -> {
                CommentsResult commentsResultChild = new CommentsResult();
                //评论信息
                commentsResultChild.setId(commentsReply.getId());
                commentsResultChild.setFromUid(commentsReply.getFromUid());
                commentsResultChild.setContent(commentsReply.getContent());
                commentsResultChild.setCreateTime(commentsReply.getCreateTime());
                //用户信息
                BaseUserInfo userInfo = baseUserInfoMapper.selectById(commentsReply.getFromUid());
                commentsResultChild.setNickName(userInfo.getNickName());
                commentsResultChild.setGender(userInfo.getGender());
                commentsResultChild.setAvatarUrl(userInfo.getAvatarUrl());
                commentsResultChildsList.add(commentsResultChild);
            });
            commentsResult.setCommentsResultChilds(commentsResultChildsList);
            list.add(commentsResult);
        });
        return JSON.toJSONString(list, SerializerFeature.DisableCircularReferenceDetect);
    }

    @Override
    public String deleteCommentById(Integer commentId,String type) {
        int result ;
        if("parent".equals(type)){
            result = articleCommentsMapper.deleteById(commentId);
        }else {
            result = commentsReplyMapper.deleteById(commentId);
        }
        return DaoResultEnum.getValueByKey(result);
    }
}

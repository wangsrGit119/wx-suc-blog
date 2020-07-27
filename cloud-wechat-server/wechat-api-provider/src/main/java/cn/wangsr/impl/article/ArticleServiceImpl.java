package cn.wangsr.impl.article;

import api.article.ArticleServiceApi;
import cn.wangsr.dao.mapper.ArticleCommentsMapper;
import cn.wangsr.dao.mapper.ArticleInfoMapper;
import cn.wangsr.dao.mapper.BaseUserInfoMapper;
import cn.wangsr.dao.mapper.ext.ArticleInfoExtMapper;
import cn.wangsr.model.ext.ArticleExtInfo;
import cn.wangsr.model.po.ArticleInfo;
import cn.wangsr.model.po.BaseUserInfo;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import enums.CommonEnum;
import enums.DaoResultEnum;
import model.article.ArticleCoverDTO;
import model.article.ArticleInfoDTO;
import model.page.PageResultDto;
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
public class ArticleServiceImpl implements ArticleServiceApi {

    @Autowired
    ArticleInfoMapper articleInfoMapper;

    @Autowired
    ArticleCommentsMapper articleCommentsMapper;

    @Autowired
    BaseUserInfoMapper baseUserInfoMapper;

    @Autowired
    ArticleInfoExtMapper articleInfoExtMapper;



    @Override
    public String insertArticle(ArticleInfoDTO articleInfoDTO) {
        ArticleInfo articleInfo = new ArticleInfo();
        BeanUtils.copyProperties(articleInfoDTO, articleInfo);
        //初始化点赞 收藏 阅读数量
        articleInfo.setReadCount(0);
        articleInfo.setPraiseCount(0);
        //设置文章类型 初始均为普通类型
        articleInfo.setBelongsType(CommonEnum.ARTICLE_TYPE_ORDINARY.getKey());
        int result = articleInfoMapper.insert(articleInfo);
        return DaoResultEnum.getValueByKey(result);
    }

    @Override
    public String loadArticleByUserIdAndArticleId(Integer articleId) {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",articleId);
        ArticleInfo articleInfo = articleInfoMapper.selectOne(queryWrapper);
        return JSON.toJSONString(articleInfo);
    }

    @Override
    public PageResultDto<ArticleCoverDTO> getArticles(Integer pageNo, Integer pageSize) {
        Page page = new Page(pageNo,pageSize);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.orderByDesc("create_time");
        IPage<ArticleInfo> iPage = articleInfoMapper.selectPage(page,queryWrapper);
        List list = new ArrayList();
        iPage.getRecords().forEach(ele->{
            ArticleCoverDTO articleCoverDTO = new ArticleCoverDTO();
            BeanUtils.copyProperties(ele,articleCoverDTO);
            QueryWrapper eleQuery = new QueryWrapper();
            //统计文章评论
            eleQuery.eq("article_id",ele.getId());
            int commentCount = articleCommentsMapper.selectCount(eleQuery);
            articleCoverDTO.setCommentCount(commentCount);
            //关联用户昵称 头像
            BaseUserInfo baseUserInfo =baseUserInfoMapper.selectById(ele.getUserId());
            articleCoverDTO.setNickName(baseUserInfo.getNickName());
            articleCoverDTO.setAvatarUrl(baseUserInfo.getAvatarUrl());
            list.add(articleCoverDTO);
        });

        PageResultDto pageResultDto = PageResultDto.builder()
                .current(iPage.getCurrent())
                .records(list)
                .size(iPage.getSize())
                .total(iPage.getTotal()).build();
        return pageResultDto;
    }

    @Override
    public List<ArticleCoverDTO> getAllArticleByUserId(Integer userId) {
        List<ArticleCoverDTO> articleCoverDTOList = new ArrayList();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.orderByDesc("create_time");
        queryWrapper.eq("user_id",userId);
        List<ArticleInfo> articleInfos = articleInfoMapper.selectList(queryWrapper);
        articleInfos.forEach(articleInfo -> {
            ArticleCoverDTO articleCoverDTO = new ArticleCoverDTO();
            BeanUtils.copyProperties(articleInfo,articleCoverDTO);
            //关联用户昵称 头像
            BaseUserInfo baseUserInfo =baseUserInfoMapper.selectById(articleInfo.getUserId());
            articleCoverDTO.setNickName(baseUserInfo.getNickName());
            articleCoverDTO.setAvatarUrl(baseUserInfo.getAvatarUrl());
            articleCoverDTOList.add(articleCoverDTO);
        });
        return articleCoverDTOList;
    }

    @Override
    public String deleteArticleById(Integer articleId) {
        int result = articleInfoMapper.deleteById(articleId);
        return DaoResultEnum.getValueByKey(result);
    }

    @Override
    public List<ArticleCoverDTO> getExcellentArticle() {
        List<ArticleCoverDTO> res = new ArrayList();
        List<ArticleExtInfo> articleExtInfoList = articleInfoExtMapper.queryExcellentArticle();
        articleExtInfoList.forEach(articleExtInfo -> {
            ArticleCoverDTO articleCoverDTO = new ArticleCoverDTO();
            BeanUtils.copyProperties(articleExtInfo,articleCoverDTO);
            //统计文章评论
            QueryWrapper comQuery = new QueryWrapper();
            comQuery.eq("article_id",articleExtInfo.getId());
            int commentCount = articleCommentsMapper.selectCount(comQuery);
            articleCoverDTO.setCommentCount(commentCount);
            //关联用户昵称 头像
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("article_id",articleExtInfo.getId());
            BaseUserInfo baseUserInfo =baseUserInfoMapper.selectById(articleExtInfo.getUserId());
            articleCoverDTO.setNickName(baseUserInfo.getNickName());
            articleCoverDTO.setAvatarUrl(baseUserInfo.getAvatarUrl());
            res.add(articleCoverDTO);
        });
        res.forEach(articleCoverDTO -> {
            System.out.println(articleCoverDTO.toString());
        });
        return res;
    }
}

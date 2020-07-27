package cn.wangsr.impl.notice;

import api.notice.CommunityFeedBackServiceApi;
import cn.wangsr.dao.mapper.BaseUserInfoMapper;
import cn.wangsr.dao.mapper.CommunityFeedBackMapper;
import cn.wangsr.model.po.BaseUserInfo;
import cn.wangsr.model.po.CommunityFeedBack;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import enums.DaoResultEnum;
import model.notice.CommunityFeedBackDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: wjl
 * @description:
 * @time: 2019/12/11 0011 14:20
 */
@Service(version = "1.0.0")
public class CommunityFeedBackServiceImpl implements CommunityFeedBackServiceApi {

    @Autowired
    CommunityFeedBackMapper communityFeedBackMapper;

    @Autowired
    BaseUserInfoMapper baseUserInfoMapper;

    @Override
    public String commitFeedBack(CommunityFeedBackDto communityFeedBackDto) {
        CommunityFeedBack communityFeedBack = new CommunityFeedBack();
        BeanUtils.copyProperties(communityFeedBackDto,communityFeedBack);
        int result = communityFeedBackMapper.insert(communityFeedBack);
        return DaoResultEnum.getValueByKey(result);
    }

    @Override
    public List<CommunityFeedBackDto> getAllFeedBackReply() {
        List result  = new ArrayList();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.orderByDesc("create_time");
        //是否恢复 0未回复 1已回复
        queryWrapper.eq("replay",1);
        queryWrapper.last("LIMIT 0,10");
        List<CommunityFeedBack> list = communityFeedBackMapper.selectList(queryWrapper);
        list.forEach(communityFeedBack -> {
            CommunityFeedBackDto communityFeedBackDto = new CommunityFeedBackDto();
            BeanUtils.copyProperties(communityFeedBack,communityFeedBackDto);
            //绑定用户信息
            BaseUserInfo baseUserInfo = baseUserInfoMapper.selectById(communityFeedBack.getUserId());
            communityFeedBackDto.setNickName(baseUserInfo.getNickName());
            communityFeedBackDto.setAvatarUrl(baseUserInfo.getAvatarUrl());
            communityFeedBackDto.setGender(baseUserInfo.getGender());
            result.add(communityFeedBackDto);
        });
        return result;
    }
}

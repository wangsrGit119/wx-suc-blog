package cn.wangsr.impl.notice;

import api.notice.NoticeServiceApi;
import cn.wangsr.dao.mapper.CommunityNoticeMapper;
import cn.wangsr.model.po.CommunityNotice;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import model.notice.CommunityNoticeDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author: wjl
 * @time: 2019/12/9 0009 11:28
 */
@Service(version = "1.0.0",retries = 1)
public class NoticeServiceImpl implements NoticeServiceApi {

    @Autowired
    CommunityNoticeMapper communityNoticeMapper;
    @Override
    public CommunityNoticeDto getNoticeByType(Integer type) {
        CommunityNoticeDto communityNoticeDto = new CommunityNoticeDto();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("type",type);
        queryWrapper.orderByDesc("create_time");
        List<CommunityNotice> list = communityNoticeMapper.selectList(queryWrapper);
        if(list.size() > 0){
           CommunityNotice communityNotice = list.get(0);
            BeanUtils.copyProperties(communityNotice,communityNoticeDto);
        }
        return communityNoticeDto;
    }
}

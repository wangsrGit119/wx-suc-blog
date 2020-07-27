package cn.wangsr.service;

import api.notice.CommunityFeedBackServiceApi;
import api.notice.NoticeServiceApi;
import com.alibaba.dubbo.config.annotation.Reference;
import model.notice.CommunityFeedBackDto;
import model.notice.CommunityNoticeDto;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: wjl
 * @description:
 * @time: 2019/12/9 0009 13:17
 */
@Service
public class NoticeService {
    @Reference(version = "1.0.0",retries = 1)
    NoticeServiceApi noticeServiceApi;

    @Reference(version = "1.0.0")
    CommunityFeedBackServiceApi communityFeedBackServiceApi;
    /**
     * 获取通告类型    通告类型 1.首页通告 2.社区通告 3.其他的通告
     * @param type
     * @return
     */
    public CommunityNoticeDto getNoticeByType(Integer type){
      return   noticeServiceApi.getNoticeByType(type);
    }


    public String addCommunityFeedBack(CommunityFeedBackDto communityFeedBackDto){
        return communityFeedBackServiceApi.commitFeedBack(communityFeedBackDto);
    }

    public List<CommunityFeedBackDto> getFeedBack(){
        return  communityFeedBackServiceApi.getAllFeedBackReply();
    }

}

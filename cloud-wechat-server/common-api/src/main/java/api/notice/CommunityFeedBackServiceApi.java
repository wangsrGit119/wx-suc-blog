package api.notice;

import model.notice.CommunityFeedBackDto;

import java.util.List;

/**
 * @author: wjl
 * @description:
 * @time: 2019/12/11 0011 13:59
 */
public interface CommunityFeedBackServiceApi {

    /**
     * 用户回馈添加
     * @param communityFeedBackDto
     * @return
     */
    String commitFeedBack(CommunityFeedBackDto communityFeedBackDto);

    /**
     *获取最新已近恢复的
     * @return
     */
    List<CommunityFeedBackDto> getAllFeedBackReply();

}

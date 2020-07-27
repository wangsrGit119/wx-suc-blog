package api.notice;

import model.notice.CommunityNoticeDto;

/**
 * @author WJL
 */
public interface NoticeServiceApi {

    /**
     * 获取通告类型    通告类型 1.首页通告 2.社区通告 3.其他的通告
     * @param type
     * @return
     */
   CommunityNoticeDto getNoticeByType(Integer type);


}

package cn.wangsr.controller.wechat;

import cn.wangsr.service.NoticeService;
import model.notice.CommunityFeedBackDto;
import model.notice.CommunityNoticeDto;
import model.result.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: wjl
 * @description: 通告
 * @time: 2019/12/9 0009 13:20
 */
@RestController
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    NoticeService noticeService;

    /**
     * 获取通告 通告类型 1.首页通告 2.社区通告 3.其他的通告
     * @param type
     * @time: 2019/12/9 0009 13:20
     */
    @GetMapping("/getNoticeByType")
    public ResultMessage getNoticeByType(@RequestParam Integer type){
        CommunityNoticeDto communityNoticeDto = noticeService.getNoticeByType(type);
        return ResultMessage.success(communityNoticeDto);
    }

    /**
     *  社区回馈添加
     * @date 2019/12/12 0012
     * @param communityFeedBackDto
     * @return
     */
    @PostMapping("/commitFeedBack")
    public ResultMessage commitFeedBack(@RequestBody CommunityFeedBackDto communityFeedBackDto){
        ResultMessage resultMessage = new ResultMessage();
        String result = noticeService.addCommunityFeedBack(communityFeedBackDto);
        return resultMessage.success(result);
    }

    /**
     *  社区回馈获取
     * @date 2019/12/12 0012
     * @return
     */
    @GetMapping("/getFeedBacks")
    public ResultMessage getFeedBacks(){
        ResultMessage resultMessage = new ResultMessage();
        List list = noticeService.getFeedBack();
        return resultMessage.success(list);
    }

}

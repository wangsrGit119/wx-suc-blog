package cn.wangsr.controller.wechat;

import cn.wangsr.service.StatisticService;
import cn.wangsr.service.UserManagerService;
import model.ext.UserTimerLineDTO;
import model.result.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author WJL
 */
@RestController
@RequestMapping("/user")
public class UserManagerController {

    @Autowired
    UserManagerService userManagerService;
    @Autowired
    StatisticService statisticService;

    /**
     * 小程序登录
     * @param code
     * @param baseUserInfo
     * @return
     */
    @PostMapping("/wxLogin")
    public ResultMessage wxLogin(@RequestParam String code, @RequestParam String baseUserInfo){
        String result = userManagerService.userLogin(code,baseUserInfo);
        return ResultMessage.success(result);
    }

    /**
     * 小程序绑定邮箱
     * @param userId
     * @param email
     * @return
     */
    @PostMapping("/bindEmail")
    public ResultMessage bindEmail(@RequestParam Integer userId, @RequestParam String email){
        userManagerService.bindEmail(userId,email);
        return ResultMessage.success("邮件已发送，请注意查收!");

    }

    /**
     * 小程序绑定邮箱验证
     * @param userId
     * @param code
     * @param email
     * @return
     */
    @PostMapping("/validateEmailCode")
    public ResultMessage validateEmailCode(@RequestParam Integer userId,  @RequestParam String code,@RequestParam String email){

        String result = userManagerService.validateEmailCode(userId,code,email);
        return ResultMessage.success(result);

    }

    /**
     * 近几天用户访问 注册统计接口
     * @return
     */
    @GetMapping("/getStatisticResultForDay")
    public ResultMessage getStatisticResultForDay(){
        List result = statisticService.getStatisticResultForDay();
        return ResultMessage.success(result);
    }

    /**
     * 用户时间线接口
     * @param userId
     * @return
     */
    @GetMapping("/getUserTimerLineById")
    public ResultMessage getUserTimerLineById(@RequestParam Integer userId){
        UserTimerLineDTO userTimerLineDTO = statisticService.getTimerLineById(userId);
        return ResultMessage.success(userTimerLineDTO);
    }

}

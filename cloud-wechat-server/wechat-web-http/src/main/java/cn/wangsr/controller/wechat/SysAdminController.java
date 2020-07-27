package cn.wangsr.controller.wechat;

import api.user.AdminManagerServiceApi;
import cn.wangsr.service.HttpClientsService;
import com.alibaba.dubbo.config.annotation.Reference;
import model.result.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author WJL
 */
@RestController
@RequestMapping("/sys")
public class SysAdminController {
    @Reference(version = "1.0.0")
    AdminManagerServiceApi adminManagerServiceApi;

    @Autowired
    HttpClientsService httpClientsService;



    /**
     * 获取token
     * @return
     */
    @GetMapping("/getWxApiToken")
    public ResultMessage getWxApiToken(){
        String token = adminManagerServiceApi.getWxApiToken();
        return ResultMessage.success(token);
    }

    @PostMapping("/msgCheck")
    public ResultMessage msgCheck(@RequestParam String content){
        String reg = "[^\u4e00-\u9fa5]";
        content = content.replaceAll(reg, "");
        content = String.format(content,"UTF-8");
        String token = adminManagerServiceApi.getWxApiToken();
        String result = httpClientsService.msgCheck(content,token);
        return ResultMessage.success(result);
    }

    @PostMapping("/imgCheck")
    public ResultMessage imgCheck(MultipartFile file){
        String token = adminManagerServiceApi.getWxApiToken();
        String result = null;
        try {
            result = httpClientsService.imgCheck(file.getBytes(),token);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultMessage.success(result);
    }

}

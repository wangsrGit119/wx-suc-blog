package cn.wangsr.controller.wechat;

import cn.wangsr.service.HttpClientsService;
import model.result.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author WJL
 */
@RestController
@RequestMapping("/three")
public class HttpClientsController {

    @Autowired
    HttpClientsService httpClientsService;

    /**
     * 用于主动更新wx 官方 api token
     * @return
     */
    @GetMapping("/generateToken")
    public ResultMessage generateWxToken(){

        httpClientsService.generateWxToken();
      return ResultMessage.success("token generate success !");
    }

}

package cn.wangsr.service;

import api.http.HttpClientServiceApi;
import api.user.AdminManagerServiceApi;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

/**
 * @author WJL
 */
@Service
public class HttpClientsService {

    @Reference(version = "1.0.0")
    HttpClientServiceApi httpClientServiceApi;
    @Reference(version = "1.0.0")
    AdminManagerServiceApi adminManagerServiceApi;

    public void generateWxToken(){
        String token = (String)httpClientServiceApi.getWechatToken();
        adminManagerServiceApi.generateWxApiToken(token,1);
    }

    public String msgCheck(String content,String token){
       return httpClientServiceApi.checkMsg(content,token);
    }

    public String imgCheck(byte[] bytes,String token){
      return  httpClientServiceApi.checkImage(bytes, token);
    }

}

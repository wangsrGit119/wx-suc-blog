package cn.wangsr.service;

import api.http.HttpClientServiceApi;
import api.user.UserManagerServiceApi;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import model.user.BaseUserInfoDto;
import org.springframework.stereotype.Service;

/**
 * @author WJL
 */
@Service
public class UserManagerService {

    @Reference(version = "1.0.0")
    UserManagerServiceApi userManagerServiceApi;
    @Reference(version = "1.0.0")
    HttpClientServiceApi httpClientServiceApi;

    public  String userLogin(String code, String baseUserInfo){
        //获取openId
        String openId = httpClientServiceApi.getOpenIdByCode(code);
        BaseUserInfoDto baseUserInfoDto = JSONObject.parseObject(baseUserInfo,new TypeReference<BaseUserInfoDto>(){});
        baseUserInfoDto.setOpenId(openId);
        return  userManagerServiceApi.login(baseUserInfoDto);
    }


    public void bindEmail(Integer userId, String email){
         userManagerServiceApi.bindEmail(userId,email);
    }

    public String validateEmailCode(Integer userId,String code,String email){
        return userManagerServiceApi.validateEmailCode(userId,code,email);
    }




}

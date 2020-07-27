package cn.wangsr.impl.http;

import api.http.HttpClientServiceApi;
import cn.wangsr.utils.HttpClientUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.entity.StringEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;

/**
 * @author WJL
 */
@Service(version = "1.0.0")
public class HttpClientServiceImpl implements HttpClientServiceApi {

    @Value("${wx.appId}")
    private String appId;
    @Value("${wx.secret}")
    private String secret;


    @Override
    public Object getWechatToken() {
        String url ="https://api.weixin.qq.com/cgi-bin/token?grant_type="+"client_credential"+"&appid="+appId+"&secret="+secret;
        String result = HttpClientUtils.getRequest(url,null);
        String token = (String)JSONObject.parseObject(result).get("access_token");
        if(!StringUtils.isEmpty(token)){
            return token;
        }
        return null;
    }

    @Override
    public String getOpenIdByCode(String code) {

        String ErrorCode = "errcode";
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid="+appId+"&secret="+secret+"&js_code="+code+"&grant_type=authorization_code";
        String result = HttpClientUtils.getRequest(url,null);
        if(result.contains(ErrorCode)){
            return "get openId failed !";
        }
        String openId = (String)JSONObject.parseObject(result).get("openid");
        return openId;
    }

    @Override
    public String checkImage(byte[] bytes,String token) {
        String url = "https://api.weixin.qq.com/wxa/img_sec_check?access_token="+token;
        String result = HttpClientUtils.httpClientPostForWx(url,bytes);
        return result;
    }

    @Override
    public String checkMsg(String content,String token) {
        String url = "https://api.weixin.qq.com/wxa/msg_sec_check?access_token="+token;
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("content",content);
        StringEntity entity =new StringEntity(JSON.toJSONString(jsonObj),"UTF-8");
        String result = HttpClientUtils.postRequest(url,entity);
        return result;
    }


}

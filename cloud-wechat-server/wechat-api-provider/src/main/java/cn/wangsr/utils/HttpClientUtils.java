package cn.wangsr.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


/**
 * httpClient utils
 * @author WJL
 */
public class HttpClientUtils {

    private static RestTemplate restTemplate=null;
    private static Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);


    public static String getRequest(String url, RequestEntity entity){
        restTemplate=new RestTemplate();
        ResponseEntity<String> responseEntity=null;
        String responseInfo=null;
        //校验接口可能超时
        try {
            responseEntity=restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            responseInfo=responseEntity.getBody();
        } catch (Exception e) {
            logger.error("Exception message: {}",e.getMessage());
            logger.error("Exception cause: {}",e.getCause());
        }
        return responseInfo;
    }


    public static String postRequest(String url,HttpEntity entity){
        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        String responseInfo=null;
        HttpResponse response = null;
        //校验接口可能超时
        try {
            httpPost.addHeader("Content-Type", "application/json");
            httpPost.setEntity(entity);
            response = httpclient.execute(httpPost);
            HttpEntity httpEntity =  response.getEntity();
            responseInfo = EntityUtils.toString(httpEntity, "UTF-8");
            return responseInfo;
        } catch (Exception e) {
            logger.error("Exception message: {}",e.getMessage());
            logger.error("Exception cause: {}",e.getCause());
        }
        return null;
    }


    /**
     * 微信小程序敏感图校验专用接口
     * @param url
     * @param bytes
     * @return
     */
    public static  String httpClientPostForWx(String url ,byte[] bytes){
        HttpClient httpclient = HttpClients.createDefault();

        HttpPost request = new HttpPost(url);
        request.addHeader("Content-Type", "application/octet-stream");
        request.setEntity(new ByteArrayEntity(bytes, ContentType.create("image/jpg")));
        HttpResponse response = null;
        try {
            response = httpclient.execute(request);
            HttpEntity httpEntity =  response.getEntity();
            String result = EntityUtils.toString(httpEntity, "UTF-8");
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {

        }
      return null;
    }

    public static void main(String[] args) {
        String token = "28_oQrrGJZuCCGWzGKgb7NYMYGb757nxOtpsrFTz9b4FlMFiAQ5hwhe0NTacE6ovqyfNAoS4bi_5wKFr_xLXoPWeiTRcuP4fZ1lZ_qQ8Tda4J0vzVIxHmHhstYUALCsvSyv4fFaYsSuCe_kdgZURQPfACAXWV";
        String url = "https://api.weixin.qq.com/wxa/img_sec_check?access_token="+token;
        String url2 = "https://api.weixin.qq.com/wxa/msg_sec_check?access_token="+token;

        try {
            //文件上传 验证
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            File file = new File("D:\\admin-workspace\\picture\\ceshi.png");
            InputStream fis = new FileInputStream(file);
            byte[] bytes = FileCopyUtils.copyToByteArray(fis);

//            HttpEntity httpEntity = new HttpEntity(null,headers);

            //文本提交 post验证
            HttpHeaders headers2 = new HttpHeaders();
            headers2.setContentType(MediaType.APPLICATION_JSON);
            JSONObject jsonObj2 = new JSONObject();
            jsonObj2.put("content","你麻痹");
            StringEntity entity =new StringEntity(JSON.toJSONString(jsonObj2),"UTF-8");
            System.out.println(postRequest(url2,entity));
        } catch (Exception e) {
            e.printStackTrace();
        }finally {

        }
    }

}

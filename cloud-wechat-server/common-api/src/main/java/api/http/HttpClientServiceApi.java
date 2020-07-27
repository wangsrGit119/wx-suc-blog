package api.http;

/**
 * 第三方接口 api 封装
 * @author WJL
 */
public interface HttpClientServiceApi<T> {

    /**
     * 获取微信小程序接口token
     * @return
     */
    T  getWechatToken();

    /**
     * 获取用户openId
     * @param code
     * @return
     */
    String getOpenIdByCode(String code);

    /**
     * 图片校验
     * @param bytes
     * @param token
     * @return
     */
    String checkImage(byte[] bytes,String token);

    /**
     * 内容校验
     * @param content
     * @param token
     * @return
     */
    String checkMsg(String content,String token);


}

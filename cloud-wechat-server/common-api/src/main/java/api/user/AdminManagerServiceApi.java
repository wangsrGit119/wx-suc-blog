package api.user;

/**
 * api token 为小程序调用官方接口所需的token
 * @author WJL
 */
public interface AdminManagerServiceApi {

    /**
     * 生成官方api token 一般由定时器执行
     * @param token
     * @param userId
     */
    void generateWxApiToken(String token,Integer userId);

    /**
     * 获取官方api token
     * @return
     */
    String getWxApiToken();


}

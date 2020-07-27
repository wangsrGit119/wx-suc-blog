package api.user;

import model.user.BaseUserInfoDto;

/**
 * @author WJL
 */
public interface UserManagerServiceApi {

    /**
     * 绑定邮箱
     * @param userId
     * @param email
     * @return
     */
    void bindEmail(Integer userId,String email);

    /**
     * 邮箱验证
     * @param userId
     * @param code
     * @param email
     * @return
     */
    String validateEmailCode(Integer userId,String code,String email);


    /**
     * 用户登录
     * @param baseUserInfoDto
     * @return
     */
    String login(BaseUserInfoDto baseUserInfoDto);



}

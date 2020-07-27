package cn.wangsr.impl.user;

import api.user.UserManagerServiceApi;
import cn.wangsr.dao.mapper.BaseUserInfoMapper;
import cn.wangsr.impl.mail.MailServiceImpl;
import cn.wangsr.model.po.BaseUserInfo;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import enums.DaoResultEnum;
import enums.EmailTypeEnum;
import exception.GlobalException;
import model.mail.MailParamDTO;
import model.user.BaseUserInfoDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import random.RandomCodeUtils;
import java.util.concurrent.TimeUnit;

/**
 * @author WJL
 */
@Service(version = "1.0.0")
@Transactional(rollbackFor = {Exception.class})
public class UserManagerServiceImpl implements UserManagerServiceApi {

    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    MailServiceImpl mailService;

    @Autowired
    BaseUserInfoMapper baseUserInfoMapper;


    @Override
    public void bindEmail(Integer userId, String email) {
        String code = RandomCodeUtils.generateValidateCode();
        String[] user = {email};
        String key = userId+email;
        if(redisTemplate.hasKey(key)){
            throw new GlobalException(400,"请勿重复获取");
        }
        int timer = 30;
        redisTemplate.opsForValue().set(key, code,timer, TimeUnit.MINUTES);
        StringBuffer buffer = new StringBuffer();
        buffer.append(EmailTypeEnum.EMAIL_TYPE_CONTENT_ONE.getMsg())
                .append(code)
                .append(EmailTypeEnum.EMAIL_TYPE_CONTENT_TWO.getMsg())
                .append(timer+"分钟")
                .append(EmailTypeEnum.EMAIL_TYPE_TAIL_ONE.getMsg());
        MailParamDTO mailParamDTO = MailParamDTO.builder()
                .title(String.format(EmailTypeEnum.EMAIL_TYPE_TITLE_ONE.getMsg(),"UTF-8"))
                //[苏克社区]您的邮箱验证码为：+ code +，验证码有效期为：+timer +，请勿告诉他人，苏克社区欢迎您！
                .context(String.format(buffer.toString(),"UTF-8"))
                .toUsers(user)
                .build();
        mailService.sendMessage(mailParamDTO);
    }

    @Override
    public String validateEmailCode(Integer userId, String code,String email) {
        String key = userId+email;
        if(!redisTemplate.hasKey(key)){
            return "code time out!";
        }
        if(!redisTemplate.opsForValue().get(key).equals(code) ){
            return "bind  failed !";
        }
        BaseUserInfo baseUserInfo = new BaseUserInfo();
        baseUserInfo.setId(userId);
        baseUserInfo.setEmail(email);
        baseUserInfoMapper.updateById(baseUserInfo);
        return "bind success!";
    }

    @Override
    public String login(BaseUserInfoDto baseUserInfoDto) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("open_id",baseUserInfoDto.getOpenId());
        BaseUserInfo baseUserInfo = baseUserInfoMapper.selectOne(queryWrapper);
        //老用户
        if(baseUserInfo != null){
            return JSON.toJSONString(baseUserInfo);
        }
        //新用户
        baseUserInfo = new BaseUserInfo();
        BeanUtils.copyProperties(baseUserInfoDto,baseUserInfo);
        int result = baseUserInfoMapper.insert(baseUserInfo);
        return JSON.toJSONString(baseUserInfo);
    }
}

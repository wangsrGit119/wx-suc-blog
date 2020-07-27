package cn.wangsr.impl.user;

import api.user.AdminManagerServiceApi;
import cn.wangsr.dao.mapper.SysAdminMapper;
import cn.wangsr.model.po.SysAdmin;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author WJL
 */
@Service(version = "1.0.0")
@Transactional(rollbackFor = {Exception.class})
public class AdminManagerServiceImpl implements AdminManagerServiceApi {

    @Autowired
    SysAdminMapper sysAdminMapper;

    @Override
    public void generateWxApiToken(String token, Integer userId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id",userId);
        SysAdmin sysAdminOld = sysAdminMapper.selectOne(queryWrapper);
        if( null == sysAdminOld ){
            sysAdminOld = new SysAdmin();
            sysAdminOld.setUserId(userId);
            sysAdminOld.setAdminWxToken(token);
            sysAdminMapper.insert(sysAdminOld);
        }else {
            sysAdminOld.setAdminWxToken(token);
            sysAdminMapper.updateById(sysAdminOld);
        }
    }

    @Override
    public String getWxApiToken() {
        SysAdmin sysAdmin = sysAdminMapper.selectById(1);
        return sysAdmin.getAdminWxToken();
    }


}

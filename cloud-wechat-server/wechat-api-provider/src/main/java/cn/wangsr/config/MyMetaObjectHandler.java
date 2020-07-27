package cn.wangsr.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * mybatis plus 自定义填充器
 * @author WJL
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        //默认填充字段及其值设置
        setFieldValByName("createTime", LocalDateTime.now(),metaObject);
        setFieldValByName("modifyTime",LocalDateTime.now(),metaObject);
        setFieldValByName("deleteStatus", 0,metaObject);

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        //更新字段默认填充值
        setFieldValByName("modifyTime",LocalDateTime.now(),metaObject);
    }
}

package cn.wangsr.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis plus  配置
 * @author WJL
 */
@Configuration
@MapperScan("cn.wangsr.dao.mapper")
public class MyBatisPlusConfig {

    /**
     * @Description   分页
     * @date 2019/11/17 0001
     * @return com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

}

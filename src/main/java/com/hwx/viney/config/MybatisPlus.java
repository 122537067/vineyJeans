package com.hwx.viney.config;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author one
 */
@EnableTransactionManagement
@Configuration
@MapperScan("com.hwx.viney.mapper")
public class MybatisPlus {

    /**
     * mybatis-plus 分页插件
     */

    @Bean
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }
}

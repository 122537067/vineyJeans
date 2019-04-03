package com.hwx.viney.config;
//
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author onee
 * @date 2018/12/3 0003 上午 8:59
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    /**
     * 注册拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MyInterceptor()).addPathPatterns("/admin/**.html").excludePathPatterns("/login.html");
    }
}

package com.sss.restaurant.common.interceptor;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * 配置拦截器
 */
@SpringBootConfiguration
public class CommonAdapter extends WebMvcConfigurationSupport {

    @Bean
    public AccessInterceptor accessInterceptor(){
        return new AccessInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截器
        registry.addInterceptor(accessInterceptor())
                .excludePathPatterns(AccessInterceptor.doNotCheckAccessUriList);
        super.addInterceptors(registry);
    }
}

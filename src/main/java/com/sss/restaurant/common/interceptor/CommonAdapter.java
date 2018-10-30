package com.sss.restaurant.common.interceptor;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * 配置拦截器
 */
@SpringBootConfiguration
public class CommonAdapter extends WebMvcConfigurationSupport {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截器
//        registry.addInterceptor(new AccessInterceptor())
//                .excludePathPatterns(AccessInterceptor.doNotCheckAccessUriList);
//        super.addInterceptors(registry);
    }
}

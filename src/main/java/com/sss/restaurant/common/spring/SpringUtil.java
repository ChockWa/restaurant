package com.sss.restaurant.common.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringUtil implements ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(SpringUtil.class);

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public Object getBean(String beanName){
        return applicationContext.getBean(beanName);
    }

    public <T> T getBean(Class<T> clazz){
        try{
            return applicationContext.getBean(clazz);
        }catch (Exception e){
            logger.error("获取bean失败:{}",clazz.getName());
        }
        return null;
    }
}

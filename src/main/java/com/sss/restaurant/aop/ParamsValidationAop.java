package com.sss.restaurant.aop;

import com.sss.restaurant.exception.CommonException;
import org.apache.commons.collections.MapUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.web.context.support.WebApplicationContextUtils.getWebApplicationContext;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;

@Aspect
@Component
public class ParamsValidationAop {

    @Pointcut("execution(* com.sss.restaurant.controller.*Controller.*(..))")
    public void validation(){}

    @Before("validation()")
    public void doBefore(JoinPoint joinPoint) throws IllegalAccessException, InstantiationException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // 获取参数map
        Map<String,String[]> params = request.getParameterMap();
        if(MapUtils.isEmpty(params)){
            return;
        }
        String methodRequestMapperName = request.getRequestURI().contains("/") ?
                request.getRequestURI().split("/")[2] : request.getRequestURI();

        WebApplicationContext wc = getWebApplicationContext(request.getSession().getServletContext());
        RequestMappingHandlerMapping rmhp = wc.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> map = rmhp.getHandlerMethods();
        for(RequestMappingInfo requestMappingInfo : map.keySet()){
            Method method = map.get(requestMappingInfo).getMethod();
            RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
            if(methodRequestMapperName.equals(requestMapping.name())){
                Parameter[] parameters = method.getParameters();
                for(Parameter parameter : parameters){
                    RequestParam requestParam = parameter.getAnnotation(RequestParam.class);
                    if(requestParam != null){
                        if(requestParam.required()){
                            Object o = params.get(requestParam.name());
                            if(o == null)
                                throw CommonException.PARAMS_NOT_NULL.format(requestParam.name());
                        }
                    }
                }
            }
        }
    }
}

package com.sss.restaurant.common.interceptor;

import com.sss.restaurant.common.info.TableUseInfo;
import com.sss.restaurant.common.redis.RedisUtil;
import com.sss.restaurant.exception.CommonException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * 检查会话拦截器
 */
@Component
public class AccessInterceptor implements HandlerInterceptor{

    public static List<String> doNotCheckAccessUriList = Arrays.asList("/table/openTable");

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private TableUseInfo tableUseInfo;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 检查会话
        checkAccessToken(request.getParameter("accessToken"));
        // 保存桌子使用记录信息
        tableUseInfo.saveInfo(request.getParameter(request.getParameter("accessToken")));
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    }

    /**
     * 检查会话
     * @param accessToken
     */
    private void checkAccessToken(String accessToken){
        if(StringUtils.isBlank(accessToken)){
            throw CommonException.TOKEN_EXPIRE;
        }

        String uid = (String) redisUtil.get(accessToken);
        if(uid == null){
            throw CommonException.TOKEN_EXPIRE;
        }
    }
}

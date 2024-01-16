package com.heyqing.disk.server.common.aspect;

import com.heyqing.disk.cache.core.constants.CacheConstants;
import com.heyqing.disk.core.response.ResponseCode;
import com.heyqing.disk.core.response.Result;
import com.heyqing.disk.core.utils.JWTUtil;
import com.heyqing.disk.server.common.annotation.LoginIgnore;
import com.heyqing.disk.server.common.utils.UserIdUtil;
import com.heyqing.disk.server.modules.user.constants.UserConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * ClassName:CommonLoginAspect
 * Package:com.heyqing.disk.server.common.aspect
 * Description:
 * 统一的登录拦截校验器的逻辑实现类
 *
 * @Date:2024/1/16
 * @Author:Heyqing
 */
@Component
@Aspect
@Slf4j
public class CommonLoginAspect {

    /**
     * 登录认证参数名称
     */
    private static final String LOGIN_AUTH_PARAM_NAME = "authorization";

    /**
     * 请求头登录认证key
     */
    private static final String LOGIN_AUTH_REQUEST_HEADER_NAME = "Authorization";

    /**
     * 切点表达式
     */
    private static final String POINT_CUT = "execution(* com.heyqing.disk.server.modules.*.controller..*(..))";

    @Autowired
    private CacheManager cacheManager;

    /**
     * 切点模板的方法
     */
    @Pointcut(value = POINT_CUT)
    public void loginAuth() {

    }

    /**
     * 切点的环绕增强逻辑
     * 1、需要判断需不需要校验登录信息
     * 2、校验登录信息
     * a、获取token，从请求头或参数
     * b、从缓存中获取token，进行比对
     * c、解析token
     * d、解析的userId存入线程上下文中
     *
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("loginAuth()")
    public Object loginAuthAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        if (checkNeedCheckLoginInfo(proceedingJoinPoint)) {
            //校验登录信息
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = servletRequestAttributes.getRequest();
            String requestURI = request.getRequestURI();
            log.info("成功拦截到请求，URL为： {}", requestURI);
            if (!checkAndSaveUserId(request)) {
                log.warn("成功拦截到请求，URL为：{}。检测到用户未登录，将跳转至登录页面。", requestURI);
                return Result.fail(ResponseCode.NEED_LOGIN);
            }
            log.info("成功拦截到请求，URL为： {}，请求通过。", requestURI);
        }
        return proceedingJoinPoint.proceed();

    }

    /**
     * 校验token并提取userId
     *
     * @param request
     * @return
     */
    private boolean checkAndSaveUserId(HttpServletRequest request) {
        String accessToken = request.getHeader(LOGIN_AUTH_REQUEST_HEADER_NAME);
        if (StringUtils.isBlank(accessToken)) {
            accessToken = request.getParameter(LOGIN_AUTH_PARAM_NAME);
        }
        if (StringUtils.isBlank(accessToken)) {
            return false;
        }
        Object userId = JWTUtil.analyzeToken(accessToken, UserConstants.LOGIN_USER_ID);
        if (Objects.isNull(userId)) {
            return false;
        }
        Cache cache = cacheManager.getCache(CacheConstants.HEY_DISK_CACHE_NAME);
        Object redisAccessToken = cache.get(UserConstants.USER_LOGIN_PREFIX + userId);
        if (Objects.isNull(redisAccessToken)) {
            return false;
        }
        if (Objects.equals(accessToken, redisAccessToken)) {
            saveUserId(userId);
            return true;
        }
        return false;
    }

    /**
     * 保存用户id到线程上下文中
     *
     * @param userId
     */
    private void saveUserId(Object userId) {
        UserIdUtil.set(Long.valueOf(String.valueOf(userId)));
    }

    /**
     * 校验是否需要校验登录信息
     *
     * @param proceedingJoinPoint
     * @return
     */
    private boolean checkNeedCheckLoginInfo(ProceedingJoinPoint proceedingJoinPoint) {
        Signature signature = proceedingJoinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        return !method.isAnnotationPresent(LoginIgnore.class);
    }


}












package com.fionapet.business.aop;

import cn.fiona.pet.account.entity.User;
import cn.fiona.pet.account.exception.ApiException;
import cn.fiona.pet.account.exception.AuthorizationException;
import cn.fiona.pet.account.service.AccountService;
import com.fionapet.business.service.Auth;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.dubbo.x.facade.RestServiceBase;
import org.dubbo.x.service.CURDService;
import org.reflections.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.util.Set;

/**
 * 身份认证 切面
 * Created by tom on 16/6/28.
 */
@Aspect
public class AuthInterceptor {
    private final static Logger LOGGER = LoggerFactory.getLogger(AuthInterceptor.class);
    @Autowired
    private Auth auth;
    @Autowired
    private AccountService accountService;

    @Pointcut("execution(* org.dubbo.x.facade.RestServiceBase+.*(..))")
    private void anyMethod(){}//定义一个切入点

//    @Around("anyMethod()")
//    public Object process(ProceedingJoinPoint point) throws Throwable {
//
//        Object[] args = point.getArgs();
//        if (args != null && args.length > 0 && args[0].getClass() == String.class) {
//            String token = point.getArgs()[0]+"";
//            RestServiceBase restServiceBase = (RestServiceBase) point.getTarget();
//            restServiceBase.getService().setToken(token);
//
//            User currentUser = accountService.getByToken(token);
//
//            restServiceBase.getService().setCurrentUser(currentUser);
//        }
//
//        return point.proceed(args);
//    }

    @Before(value = "anyMethod() && args(token,..)", argNames = "point, token")
    public void doAccessCheck(JoinPoint point, String token) throws AuthorizationException {
        LOGGER.debug("check token:{}", token);

        try {
            boolean validateToken = true;//accountService.validateToken(token);

            if (!validateToken){
                throw new AuthorizationException("token 验证失败!");
            }else{
                RestServiceBase restServiceBase = (RestServiceBase) point.getTarget();
                restServiceBase.getService().setToken(token);

                User currentUser = accountService.getByToken(token);

                restServiceBase.getService().setCurrentUser(currentUser);

                setCurrentUser(restServiceBase.getService(), currentUser);

                auth.setCurrentUser(currentUser);
            }
        } catch (Exception e) {
            LOGGER.warn("Request filter invoked:Token validate fail!",e);
            throw new AuthorizationException("token 验证失败!");
        }
    }

    private void setCurrentUser(CURDService service, User user){
        Class c = service.getClass();
        Set<Field> fs = ReflectionUtils.getAllFields(c);
        for (Field f :fs){
            if (f.getType().getInterfaces().length>0) {
                if (f.getType().getInterfaces()[0].isAssignableFrom(CURDService.class)) {
                    try {
                        f.setAccessible(true);
                        BeanUtilsBean.getInstance().setProperty(f,"setCurrentUser", user);
                    } catch (Exception e) {
                        LOGGER.warn("set current user is error!", e);
//                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public AccountService getAccountService() {
        return accountService;
    }

    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }
}

package com.fionapet.business.service;

import com.alibaba.dubbo.config.annotation.Service;
import org.dubbo.x.entity.IdEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * Created by tom on 2017/8/6.
 */
@Component
public class Auth {
    private static final Logger LOGGER = LoggerFactory.getLogger(Auth.class);
    private static ThreadLocal<IdEntity> CURRENT_USER_THREADLOCAL = new ThreadLocal<IdEntity>();

    public void setCurrentUser(IdEntity currentUser){
        LOGGER.debug("{}, set current user:{}", Thread.currentThread().getName(), currentUser);
        CURRENT_USER_THREADLOCAL.set(currentUser);
    }
    public IdEntity getCurrentUser(){
        IdEntity currentUser = CURRENT_USER_THREADLOCAL.get();
        LOGGER.debug("{}, get current user:{}", Thread.currentThread().getName(), currentUser);
        if (null == currentUser){
            throw new RuntimeException("当前用户为空!");
        }

        return currentUser;
    }
}

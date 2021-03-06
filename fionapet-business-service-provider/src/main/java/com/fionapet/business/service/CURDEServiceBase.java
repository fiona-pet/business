package com.fionapet.business.service;

import org.dubbo.x.entity.Idable;
import org.dubbo.x.service.CURDServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

/**
 * Created by tom on 2017/8/6.
 */
public abstract class CURDEServiceBase<T extends Idable> extends CURDServiceBase<T>
        implements ApplicationEventPublisherAware {

    @Autowired
    private Auth auth;

    private ApplicationEventPublisher publisher;

    public ApplicationEventPublisher getPublisher() {
        return publisher;
    }

    public void setPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    public void publishEvent(ApplicationEvent event) {
        auth.setCurrentUser(this.getCurrentUser());
        this.getPublisher().publishEvent(event);
    }
}

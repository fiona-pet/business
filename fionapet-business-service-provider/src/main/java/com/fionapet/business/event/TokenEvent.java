package com.fionapet.business.event;

import org.springframework.context.ApplicationEvent;

/**
 * Created by tom on 2017/7/16.
 */
public class TokenEvent extends ApplicationEvent {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public TokenEvent(Object source) {
        super(source);
    }

    public TokenEvent(Object source, String token) {
        super(source);
        this.token = token;
    }
}

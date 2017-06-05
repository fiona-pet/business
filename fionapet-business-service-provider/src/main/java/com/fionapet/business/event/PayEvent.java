package com.fionapet.business.event;

import org.springframework.context.ApplicationEvent;

/**
 * Created by tom on 2017/6/4.
 */
public class PayEvent extends ApplicationEvent {
    private String type;
    private String id;

    public PayEvent(Object source, String type, String id) {
        super(source);
        this.type = type;
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

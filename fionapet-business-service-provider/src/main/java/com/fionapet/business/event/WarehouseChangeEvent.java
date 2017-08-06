package com.fionapet.business.event;

import com.fionapet.business.jms.WarehouseOpType;
import org.springframework.context.ApplicationEvent;

/**
 * Created by tom on 2017/6/4.
 */
public class WarehouseChangeEvent extends ApplicationEvent {
    private WarehouseOpType opType;
    private String id;

    public WarehouseChangeEvent(Object source, WarehouseOpType type, String id) {
        super(source);
        this.opType = type;
        this.id = id;
    }

    public WarehouseOpType getOpType() {
        return opType;
    }

    public void setOpType(WarehouseOpType opType) {
        this.opType = opType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

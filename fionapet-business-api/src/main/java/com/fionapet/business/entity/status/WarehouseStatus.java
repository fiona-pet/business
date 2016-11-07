package com.fionapet.business.entity.status;

/**
 * 库存 状态
 * Created by tom on 16/7/19.
 */
public enum WarehouseStatus {
    NOT_FOUND("无库存"),
    NOT_ENOUGH("库存不足"),
    NOT_RECORD("不计库存"),
    ENOUGH("库存足够");

    WarehouseStatus(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}

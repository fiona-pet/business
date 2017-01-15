package com.fionapet.business.jms;

import org.dubbo.x.entity.IdEntity;

import java.io.Serializable;

/**
 * Created by tom on 16/10/20.
 */
public class WarehouseNoticeInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 库存记录
     */
    private String warehouseRecordId;
    /**
     * 库存操作类型
     */
    private WarehouseOpType warehouseOpType;

    private String currentUserId;

    public String getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(String currentUserId) {
        this.currentUserId = currentUserId;
    }

    public String getWarehouseRecordId() {
        return warehouseRecordId;
    }

    public void setWarehouseRecordId(String warehouseRecordId) {
        this.warehouseRecordId = warehouseRecordId;
    }

    public String getWarehouseOpType() {
        return warehouseOpType.toString();
    }

    public void setWarehouseOpType(String warehouseOpType) {
        this.warehouseOpType = WarehouseOpType.valueOf(warehouseOpType);
    }

    @Override
    public String toString() {
        return "WarehouseNoticeInfo{" +
                "warehouseRecordId='" + warehouseRecordId + '\'' +
                ", warehouseOpType=" + warehouseOpType +
                '}';
    }
}

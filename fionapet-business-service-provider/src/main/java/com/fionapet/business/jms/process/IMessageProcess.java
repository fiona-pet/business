package com.fionapet.business.jms.process;

import com.fionapet.business.jms.WarehouseNoticeInfo;


/**
 * Created by tom on 16/10/20.
 */
public interface IMessageProcess {
    void process(WarehouseNoticeInfo warehouseNoticeInfo);
}

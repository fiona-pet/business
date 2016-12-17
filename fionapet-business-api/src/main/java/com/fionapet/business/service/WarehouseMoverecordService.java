package com.fionapet.business.service;

import com.fionapet.business.entity.WarehouseMoverecord;
import org.dubbo.x.service.CURDService;
/**
 * 移库记录
* Created by tom on 2016-07-18 11:56:08.
 */
public interface WarehouseMoverecordService extends CURDService<WarehouseMoverecord> {
    /**
     * 审计入库
     * @param uuid
     * @return
     */
    Boolean audit(String uuid);
}

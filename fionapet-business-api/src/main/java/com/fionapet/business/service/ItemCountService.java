package com.fionapet.business.service;

import com.fionapet.business.entity.*;
import com.fionapet.business.exception.ApiException;
import org.dubbo.x.service.CURDService;

import java.util.List;

/**
 * 种类数量更改原因表
* Created by tom on 2016-07-25 09:32:32.
 */
public interface ItemCountService extends CURDService<ItemCount> {
    void update(String warehouseInrecordId);

    /**
     * 消除库存
     *
     * @param medicPrescriptionDetail
     */
    void decrease(final MedicPrescriptionDetail medicPrescriptionDetail) throws ApiException;

    /**
     * 消除库存
     *
     * @param inHospitalPrescriptionDetail
     */
    void decrease(InHospitalPrescriptionDetail inHospitalPrescriptionDetail);

    /**
     * 缺货订单
     * @return
     */
    List<OrderVO> order();

    void decrease(int itemNum, ItemCountChangeReason itemCountChangeReason);
}

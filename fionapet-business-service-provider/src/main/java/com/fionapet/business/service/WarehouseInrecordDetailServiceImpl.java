package com.fionapet.business.service;

import com.fionapet.business.entity.WarehouseInrecord;
import com.fionapet.business.entity.WarehouseInrecordDetail;
import com.fionapet.business.repository.WarehouseInrecordDao;
import com.fionapet.business.repository.WarehouseInrecordDetailDao;
import org.dubbo.x.repository.DaoBase;
import org.dubbo.x.service.CURDServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * 进库记录明细 Created by tom on 2016-07-18 11:56:10.
 */

@Service
public class WarehouseInrecordDetailServiceImpl extends CURDServiceBase<WarehouseInrecordDetail>
        implements WarehouseInrecordDetailService {

    @Autowired
    private WarehouseInrecordDetailDao warehouseInrecordDetailDao;
    @Autowired
    private WarehouseInrecordDao warehouseInrecordDao;


    @Override
    public DaoBase<WarehouseInrecordDetail> getDao() {
        return warehouseInrecordDetailDao;
    }

    @Override
    @Transactional
    public synchronized WarehouseInrecordDetail createOrUpdte(WarehouseInrecordDetail entity) {
        //更新入库信息
        WarehouseInrecord
                warehouseInrecord =
                warehouseInrecordDao.findByInWarehouseCode(entity.getInWarehouseCode());
        if (null != warehouseInrecord) {
            if (warehouseInrecord.getRemark() == null) {
                warehouseInrecord.setRemark(entity.getItemName());
            } else if (warehouseInrecord.getRemark().indexOf(entity.getItemName()) == -1) {
                warehouseInrecord
                        .setRemark(warehouseInrecord.getRemark() + "," + entity.getItemName());
            }
            warehouseInrecordDao.save(warehouseInrecord);
        }

        return super.createOrUpdte(entity);
    }

    @Override
    @Transactional
    // TODO 消息
    public void delete(String uuid) {
        WarehouseInrecordDetail warehouseInrecordDetail = warehouseInrecordDetailDao.findOne(uuid);
        if (null != warehouseInrecordDetail) {
            WarehouseInrecord
                    warehouseInrecord =
                    warehouseInrecordDao
                            .findByInWarehouseCode(warehouseInrecordDetail.getInWarehouseCode());
            if (null != warehouseInrecord) {
                warehouseInrecord.setInWarehouseTotalCost(
                        warehouseInrecord.getInWarehouseTotalCost() - warehouseInrecordDetail
                                .getInputCost());
                warehouseInrecord.setTotalCount(warehouseInrecord.getTotalCount() - 1);
                warehouseInrecordDao.save(warehouseInrecord);
            }
        }
        super.delete(uuid);
    }
}

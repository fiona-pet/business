package com.fionapet.business.service;

import cn.fiona.pet.account.entity.User;
import com.fionapet.business.entity.DictTypeDetail;
import com.fionapet.business.entity.WarehouseInrecord;
import com.fionapet.business.event.WarehouseChangeEvent;
import com.fionapet.business.jms.WarehouseOpType;
import com.fionapet.business.repository.DictTypeDetailDao;
import com.fionapet.business.repository.WarehouseInrecordDao;
import com.fionapet.business.repository.WarehouseInrecordDetailDao;
import org.apache.commons.lang3.StringUtils;
import org.dubbo.x.repository.DaoBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

import javax.transaction.Transactional;

/**
 * 进库记录 Created by tom on 2016-07-18 11:56:10.
 */

@Service
public class WarehouseInrecordServiceImpl extends CURDEServiceBase<WarehouseInrecord>
        implements WarehouseInrecordService {

    @Autowired
    private WarehouseInrecordDao warehouseInrecordDao;
    @Autowired
    private WarehouseInrecordDetailDao warehouseInrecordDetailDao;
    @Autowired
    private DictTypeDetailDao dictTypeDetailDao;

    @Override
    public DaoBase<WarehouseInrecord> getDao() {
        return warehouseInrecordDao;
    }

    @Override
    public WarehouseInrecord createOrUpdte(WarehouseInrecord entity) {
        if (entity.getId() == null) {
            DictTypeDetail inStatus = dictTypeDetailDao.findByDictDetailCode("SM00042");
            entity.setStatus(inStatus);
        }
        return super.createOrUpdte(entity);
    }

    @Override
    @Transactional
    public Boolean audit(String uuid) {
        WarehouseInrecord warehouseInrecord = warehouseInrecordDao.findOne(uuid);

        if (null != warehouseInrecord) {
            DictTypeDetail checkStatus = dictTypeDetailDao.findByDictDetailCode("SM00043");
            warehouseInrecord.setStatus(checkStatus);
            warehouseInrecord.setCheckDate(new Date());

            User user = (User) this.getCurrentUser();
            warehouseInrecord.setCheckMan(user.getName());
            warehouseInrecord.setRemark(StringUtils.join(warehouseInrecordDetailDao
                                                                 .findTitleByRecoreCode(
                                                                         warehouseInrecord
                                                                                 .getInWarehouseCode()),
                                                         ","));

            createOrUpdte(warehouseInrecord);
        }

        // 发送 审核 更新库存消息
        this.publishEvent(new WarehouseChangeEvent(warehouseInrecord, WarehouseOpType.CHECK,
                                                   warehouseInrecord.getId()));

        return true;
    }

    @Override
    public Boolean paystatus(String uuid) {
        WarehouseInrecord warehouseInrecord = warehouseInrecordDao.findOne(uuid);
        if (null != warehouseInrecord) {
            warehouseInrecord.setPaidStatus("已付款");
            createOrUpdte(warehouseInrecord);
        }
        return true;
    }
}

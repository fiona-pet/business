package com.fionapet.business.service;

import cn.fiona.pet.account.entity.User;
import com.fionapet.business.entity.DictTypeDetail;
import com.fionapet.business.entity.WarehouseOutrecord;
import com.fionapet.business.repository.DictTypeDetailDao;
import com.fionapet.business.repository.WarehouseOutrecordDao;
import org.dubbo.x.repository.DaoBase;
import org.dubbo.x.service.CURDServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 出库记录 Created by tom on 2016-07-18 11:56:10.
 */

@Service
public class WarehouseOutrecordServiceImpl extends CURDServiceBase<WarehouseOutrecord>
        implements WarehouseOutrecordService {

    @Autowired
    private WarehouseOutrecordDao warehouseOutrecordDao;
    @Autowired
    private DictTypeDetailDao dictTypeDetailDao;

    @Override
    public DaoBase<WarehouseOutrecord> getDao() {
        return warehouseOutrecordDao;
    }

    @Override
    public WarehouseOutrecord createOrUpdte(WarehouseOutrecord entity) {
        if (entity.getId() == null) {

            DictTypeDetail inStatus = dictTypeDetailDao.findByDictDetailCode("SM00045");
            entity.setStatus(inStatus);
        }
        return super.createOrUpdte(entity);
    }

    @Override
    public Boolean audit(String uuid) {
        WarehouseOutrecord warehouseOutrecord = warehouseOutrecordDao.findOne(uuid);

        if (null != warehouseOutrecord) {
            DictTypeDetail checkStatus = dictTypeDetailDao.findByDictDetailCode("SM00043");
            warehouseOutrecord.setStatus(checkStatus);
            warehouseOutrecord.setCheckDate(new Date());

            User user = (User) this.getCurrentUser();
            warehouseOutrecord.setCheckMan(user.getName());

            createOrUpdte(warehouseOutrecord);
        }

        return true;
    }
}

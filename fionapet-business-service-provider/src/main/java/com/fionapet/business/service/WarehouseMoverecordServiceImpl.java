package com.fionapet.business.service;

import cn.fiona.pet.account.entity.User;
import com.fionapet.business.entity.DictTypeDetail;
import com.fionapet.business.entity.WarehouseMoverecord;
import com.fionapet.business.repository.DictTypeDetailDao;
import com.fionapet.business.repository.WarehouseMoverecordDao;
import org.dubbo.x.repository.DaoBase;
import org.dubbo.x.service.CURDServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 移库记录 Created by tom on 2016-07-18 11:56:08.
 */

@Service
public class WarehouseMoverecordServiceImpl extends CURDServiceBase<WarehouseMoverecord>
        implements WarehouseMoverecordService {

    @Autowired
    private WarehouseMoverecordDao warehouseMoverecordDao;
    @Autowired
    private DictTypeDetailDao dictTypeDetailDao;

    @Override
    public DaoBase<WarehouseMoverecord> getDao() {
        return warehouseMoverecordDao;
    }

    @Override
    public WarehouseMoverecord createOrUpdte(WarehouseMoverecord entity) {
        if (entity.getId() == null) {

            DictTypeDetail inStatus = dictTypeDetailDao.findByDictDetailCode("SM00044");
            entity.setStatus(inStatus);
        }
        return super.createOrUpdte(entity);
    }

    @Override
    public Boolean audit(String uuid) {
        WarehouseMoverecord warehouseMoverecord = warehouseMoverecordDao.findOne(uuid);

        if (null != warehouseMoverecord) {
            DictTypeDetail checkStatus = dictTypeDetailDao.findByDictDetailCode("SM00043");
            warehouseMoverecord.setStatus(checkStatus);
            warehouseMoverecord.setCheckDate(new Date());

            User user = (User) this.getCurrentUser();
            warehouseMoverecord.setCheckMan(user.getName());

            createOrUpdte(warehouseMoverecord);
        }

        return true;
    }
}

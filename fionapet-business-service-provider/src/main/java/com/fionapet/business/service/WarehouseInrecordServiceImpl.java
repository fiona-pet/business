package com.fionapet.business.service;

import cn.fiona.pet.account.entity.User;
import com.fionapet.business.entity.DictTypeDetail;
import com.fionapet.business.entity.ItemCount;
import com.fionapet.business.entity.WarehouseInrecord;
import com.fionapet.business.entity.WarehouseInrecordDetail;
import com.fionapet.business.event.PayEvent;
import com.fionapet.business.event.WarehouseChangeEvent;
import com.fionapet.business.jms.NoticeInfoBuilder;
import com.fionapet.business.jms.QueueMessageProducer;
import com.fionapet.business.jms.WarehouseNoticeInfo;
import com.fionapet.business.jms.WarehouseOpType;
import com.fionapet.business.repository.*;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.lang3.StringUtils;
import org.dubbo.x.entity.IdEntity;
import org.dubbo.x.entity.PageSearch;
import org.dubbo.x.repository.DaoBase;
import org.dubbo.x.service.CURDServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.data.domain.Page;

import javax.transaction.Transactional;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

/**
 *  进库记录
* Created by tom on 2016-07-18 11:56:10.
 */
public class WarehouseInrecordServiceImpl extends CURDEServiceBase<WarehouseInrecord> implements WarehouseInrecordService{
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

        if (null != warehouseInrecord){
            DictTypeDetail checkStatus = dictTypeDetailDao.findByDictDetailCode("SM00043");
            warehouseInrecord.setStatus(checkStatus);
            warehouseInrecord.setCheckDate(new Date());

            User user = (User) this.getCurrentUser();
            warehouseInrecord.setCheckMan(user.getName());
            warehouseInrecord.setRemark(StringUtils.join(warehouseInrecordDetailDao.findTitleByRecoreCode(warehouseInrecord.getInWarehouseCode()),","));

            createOrUpdte(warehouseInrecord);
        }

        // 发送 审核 更新库存消息
        this.publishEvent(new WarehouseChangeEvent(warehouseInrecord, WarehouseOpType.CHECK,warehouseInrecord.getId()));

        return true;
    }

    @Override
    public Boolean paystatus(String uuid) {
        WarehouseInrecord warehouseInrecord = warehouseInrecordDao.findOne(uuid);
        if (null != warehouseInrecord){
            warehouseInrecord.setPaidStatus("已付款");
            createOrUpdte(warehouseInrecord);
        }
        return true;
    }
}

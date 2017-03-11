package com.fionapet.business.service;

import com.fionapet.business.entity.*;
import com.fionapet.business.entity.status.WarehouseStatus;
import com.fionapet.business.exception.ApiException;
import com.fionapet.business.repository.ItemTypeDao;
import com.fionapet.business.repository.WarehouseInrecordDao;
import com.fionapet.business.repository.WarehouseInrecordDetailDao;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;
import org.apache.commons.lang3.StringUtils;
import org.dubbo.x.repository.DaoBase;
import org.dubbo.x.service.CURDServiceBase;
import com.fionapet.business.repository.ItemCountDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 *  库存信息
* Created by tom on 2016-07-25 09:32:32.
 */
public class ItemCountServiceImpl extends CURDServiceBase<ItemCount> implements ItemCountService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ItemCountServiceImpl.class);
    @Autowired
    private ItemCountDao itemCountDao;
    @Autowired
    private WarehouseInrecordDetailDao warehouseInrecordDetailDao;
    @Autowired
    private WarehouseInrecordDao warehouseInrecordDao;

    @Override
    public DaoBase<ItemCount> getDao() {
        return itemCountDao;
    }

    @Transactional
    public void update(String warehouseInrecordId) {
        WarehouseInrecord warehouseInrecord = warehouseInrecordDao.findOne(warehouseInrecordId);

        List<WarehouseInrecordDetail> warehouseInrecordDetails = warehouseInrecordDetailDao.findByInWarehouseCode(warehouseInrecord.getInWarehouseCode());

        for (WarehouseInrecordDetail warehouseInrecordDetail: warehouseInrecordDetails){
            ItemCount itemCount = itemCountDao.findByItemCode(warehouseInrecordDetail.getItemCode());

            if (null == itemCount){
                itemCount = new ItemCount();
                itemCount.setWarehouseId(warehouseInrecord.getWarehouseId());
                itemCount.setWarehouseName(warehouseInrecord.getInWarehouse());

                try {
                    ConvertUtils.register(new DateLocaleConverter(Locale.SIMPLIFIED_CHINESE,"yyyy-MM-dd hh:mm:ss"), Date.class);

                    BeanUtilsBean.getInstance().copyProperties(itemCount, warehouseInrecordDetail);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }else{
                //商品进价是否有变化
                if (Double.compare(itemCount.getInputPrice(), warehouseInrecordDetail.getInputPrice()) != 0){
                    //新单价=((原进价*库存+原进价/零散比*散装数量)+新进价*数量)/((原库存+新进数量)*零散比+散装数量)*零散比
                    //原成本
                    double oldTotal = itemCount.getInputPrice() * itemCount.getItemCountNum() + itemCount.getInputPrice()/itemCount.getItemBulk()*itemCount.getScatteredCountNum();
                    //新进成本
                    double newTotal = warehouseInrecordDetail.getInputPrice() * warehouseInrecordDetail.getInputCount();
                    //总散装数量
                    double count = (itemCount.getItemCountNum() + warehouseInrecordDetail.getInputCount())*itemCount.getItemBulk() + itemCount.getScatteredCountNum();

                    double newInputPrice = (oldTotal + newTotal)/count*itemCount.getItemBulk();

                    itemCount.setInputPrice(newInputPrice);
                }

            }

            itemCount.setItemCountNum(itemCount.getItemCountNum() + warehouseInrecordDetail.getInputCount());

            createOrUpdte(itemCount);
        }

    }

    @Override
    @Transactional
    public void decrease(final MedicPrescriptionDetail medicPrescriptionDetail) throws ApiException{
        String itemCountStatus = null;
        ItemCount itemCount = itemCountDao.findByItemCode(medicPrescriptionDetail.getItemCode());
        //无库存
        if (null == itemCount){
            itemCountStatus = WarehouseStatus.NOT_FOUND+"";

            LOGGER.error("{}[{}]: 商品没有库存！", medicPrescriptionDetail.getItemCode(), medicPrescriptionDetail.getItemName());

            return;
        }else {
            if (null == itemCount.getItemBulk()|| itemCount.getItemBulk()==0){
                itemCount.setItemBulk(1);
            }

            //库存不足
            if (medicPrescriptionDetail.getItemNum() != null && itemCount.getScatteredCountNum() < medicPrescriptionDetail.getItemNum()) {
                int count = (medicPrescriptionDetail.getItemNum()-(int)itemCount.getScatteredCountNum()) / itemCount.getItemBulk() + 1;
                itemCount.setItemCountNum(itemCount.getItemCountNum() - count);

                itemCount.setScatteredCountNum(count * itemCount.getItemBulk() + itemCount.getScatteredCountNum() - medicPrescriptionDetail.getItemNum());
            } else {
                itemCount.setScatteredCountNum(itemCount.getScatteredCountNum() - medicPrescriptionDetail.getItemNum());
            }

            if (itemCount.getItemCountNum() < 0) {
                itemCountStatus = WarehouseStatus.NOT_ENOUGH + "";
            } else {
                itemCountStatus = WarehouseStatus.ENOUGH + "";
            }
        }

        medicPrescriptionDetail.setItemCountStatus(itemCountStatus);

        itemCountDao.save(itemCount);
    }

    @Override
    public void decrease(InHospitalPrescriptionDetail inHospitalPrescriptionDetail) {
        MedicPrescriptionDetail medicPrescriptionDetail = new MedicPrescriptionDetail();
        try {
            BeanUtilsBean.getInstance().copyProperties(medicPrescriptionDetail, inHospitalPrescriptionDetail);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        decrease(medicPrescriptionDetail);
    }
}

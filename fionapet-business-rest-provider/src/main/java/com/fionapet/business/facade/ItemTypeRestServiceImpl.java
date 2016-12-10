package com.fionapet.business.facade;

import com.fionapet.business.entity.ItemType;
import com.fionapet.business.facade.vo.ItemTypeVO;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.dubbo.x.service.CURDService;
import org.dubbo.x.facade.RestServiceBase;
import com.fionapet.business.service.ItemTypeService;
import org.dubbo.x.util.ConstantVariable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.HeaderParam;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品类型
* Created by tom on 2016-07-31 16:36:26.
 */
public class ItemTypeRestServiceImpl extends RestServiceBase<ItemType> implements ItemTypeRestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemTypeRestServiceImpl.class);

    private ItemTypeService itemTypeService;

    public ItemTypeService getItemTypeService() {
        return itemTypeService;
    }

    public void setItemTypeService(ItemTypeService itemTypeService) {
        this.itemTypeService = itemTypeService;
    }

    @Override
    public CURDService<ItemType> getService() {
        return itemTypeService;
    }

    @Override
    public List<ItemTypeVO> search(@HeaderParam(ConstantVariable.HEADER_AUTHORIZATION_KEY) String token, String key) {
        return itemTypeService.search(key);
    }
}

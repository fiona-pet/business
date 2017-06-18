package com.fionapet.business.facade;

import com.fionapet.business.entity.ItemCount;
import com.fionapet.business.entity.OrderVO;
import org.dubbo.x.facade.RestResult;
import org.dubbo.x.service.CURDService;
import org.dubbo.x.facade.RestServiceBase;
import com.fionapet.business.service.ItemCountService;
import org.dubbo.x.util.ConstantVariable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.HeaderParam;
import java.util.List;

/**
 * 种类数量更改原因表
* Created by tom on 2016-07-25 09:32:32.
 */
public class ItemCountRestServiceImpl extends RestServiceBase<ItemCount> implements ItemCountRestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemCountRestServiceImpl.class);

    private ItemCountService itemCountService;

    public ItemCountService getItemCountService() {
        return itemCountService;
    }

    public void setItemCountService(ItemCountService itemCountService) {
        this.itemCountService = itemCountService;
    }

    @Override
    public CURDService<ItemCount> getService() {
        return itemCountService;
    }

    @Override
    public RestResult<List<OrderVO>> order(@HeaderParam(ConstantVariable.HEADER_AUTHORIZATION_KEY) String token) {
        return RestResult.OK(((ItemCountService)getService()).order());
    }
}

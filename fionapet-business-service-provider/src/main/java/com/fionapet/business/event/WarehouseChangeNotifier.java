package com.fionapet.business.event;

import com.fionapet.business.service.ItemCountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Created by tom on 2017/6/4.
 */
//@Component
public class WarehouseChangeNotifier implements ApplicationListener<WarehouseChangeEvent> {
    @Autowired
    private ItemCountService itemCountService;

    private static final Logger LOGGER = LoggerFactory.getLogger(WarehouseChangeNotifier.class);

    @Override
    public void onApplicationEvent(WarehouseChangeEvent warehouseChangeEvent) {
        LOGGER.debug("WarehouseChangeNotifier:{}", warehouseChangeEvent);

        if (null != warehouseChangeEvent.getId()){
            this.itemCountService.update(warehouseChangeEvent.getId());
        }
    }
}

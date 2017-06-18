package com.fionapet.business.repository;

import com.fionapet.business.entity.ItemCount;
import com.fionapet.business.entity.OrderVO;
import org.dubbo.x.repository.DaoBase;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 种类数量更改原因表
* Created by tom on 2016-07-25 09:32:32.
 **/
public interface ItemCountDao extends DaoBase<ItemCount> {
    ItemCount findByItemCode(String itemCode);

    @Query(value = "select * from v_input_order_by_item_count", nativeQuery = true)
    List<OrderVO> genOrder();
}

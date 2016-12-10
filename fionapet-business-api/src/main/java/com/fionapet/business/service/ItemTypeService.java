package com.fionapet.business.service;

import com.fionapet.business.entity.ItemType;
import com.fionapet.business.facade.vo.ItemTypeVO;
import org.dubbo.x.service.CURDService;

import java.util.List;

/**
 * 商品类型
* Created by tom on 2016-07-31 16:36:26.
 */
public interface ItemTypeService extends CURDService<ItemType> {

    List<ItemTypeVO> search(String key);
}

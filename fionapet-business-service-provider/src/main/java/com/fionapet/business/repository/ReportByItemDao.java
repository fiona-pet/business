package com.fionapet.business.repository;

import com.fionapet.business.entity.BillVO;
import com.fionapet.business.entity.ReportByItemVO;
import org.dubbo.x.repository.DaoBase;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;
import java.util.List;

/**
 * 商品销售 报表
* Created by tom on 2016-07-25 09:32:34.
 **/
public interface ReportByItemDao extends DaoBase<ReportByItemVO> {
    List<ReportByItemVO> findByCreateDateOrderByTotalDesc(String start);
}

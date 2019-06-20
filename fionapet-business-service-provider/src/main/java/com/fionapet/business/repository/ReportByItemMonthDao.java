package com.fionapet.business.repository;

import com.fionapet.business.entity.ReportByItemDayVO;
import com.fionapet.business.entity.ReportByItemMonthVO;
import com.fionapet.business.entity.ReportByItemVO;
import org.dubbo.x.repository.DaoBase;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 商品销售 报表
* Created by tom on 2016-07-25 09:32:34.
 **/
public interface ReportByItemMonthDao extends DaoBase<ReportByItemMonthVO> {
    List<ReportByItemMonthVO> findByCreateDateOrderByTotalDesc(String start);

    @Query("from ReportByItemMonthVO where createDate >= ?1 and createDate <= ?2")
    List<ReportByItemMonthVO> findCreateDateBetween(String start, String end);
}

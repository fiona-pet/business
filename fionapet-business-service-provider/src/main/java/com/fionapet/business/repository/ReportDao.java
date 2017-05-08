package com.fionapet.business.repository;

import com.fionapet.business.entity.ReportByPersonVO;
import org.dubbo.x.repository.DaoBase;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 报表统计
* Created by tom on 2016-07-18 11:56:10.
 **/
public interface ReportDao extends DaoBase<ReportByPersonVO> {
    @Query("select new ReportByPersonVO(name,type,sum(total)) from ReportByPersonVO where createDate >= ?1 and createDate <= ?2 group by name, type")
    List<ReportByPersonVO> findCreateDateBetween(String start, String end);

    List<ReportByPersonVO> findByNameAndCreateDateLike(String user, String date);
}

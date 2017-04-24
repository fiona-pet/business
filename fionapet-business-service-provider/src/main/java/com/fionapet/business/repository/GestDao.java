package com.fionapet.business.repository;

import com.fionapet.business.entity.Gest;
import org.dubbo.x.repository.DaoBase;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 会员
* Created by tom on 2016-07-22 14:21:59.
 **/
public interface GestDao extends DaoBase<Gest> {
    List<Gest> findByPaidStatusNot(String sm00051);

    @Query(value = "SELECT count(1) from t_gest JOIN t_gest_level on t_gest_level.level_code=gest_style where t_gest_level.level_name in ?1", nativeQuery = true)
    Long countByLevel(String[] gestLevels);

    @Query(value = "SELECT sum(prepay_money) from t_gest JOIN t_gest_level on t_gest_level.level_code=gest_style where t_gest_level.level_name in ?1", nativeQuery = true)
    Long countVipMoneyByLevel(String[] strings);
}

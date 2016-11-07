package com.fionapet.business.repository;

import com.fionapet.business.entity.UserDictDetail;
import org.dubbo.x.repository.DaoBase;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

/**
 * 用户字典
 * Created by tom on 16/7/19.
 */
public interface UserDictDetailDao extends DaoBase<UserDictDetail> {
    List<UserDictDetail> findByDictTypeId(String id);

    List<UserDictDetail> findByValueNameCn(String name);

    @Query("from UserDictDetail where dictDetailCode=?1 or id=?2")
    List<UserDictDetail> findDictDetailCodeOrId(String name, String recipeUnit);
}

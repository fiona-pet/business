package com.fionapet.business.service;

import com.fionapet.business.entity.Gest;
import com.fionapet.business.facade.vo.RechargeVO;
import org.dubbo.x.exception.ApiException;
import org.dubbo.x.repository.DaoBase;
import org.dubbo.x.service.CURDServiceBase;
import com.fionapet.business.repository.GestDao;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *  会员
* Created by tom on 2016-07-22 14:21:59.
 */
public class GestServiceImpl extends CURDServiceBase<Gest> implements GestService {
    @Autowired
    private GestDao gestDao;

    @Override
    public DaoBase<Gest> getDao() {
        return gestDao;
    }


    @Override
    public RechargeVO recharge(String id, Double money) throws ApiException{
        Gest gest = gestDao.findOne(id);

        if (null == gest){
            throw new ApiException("会员不存在");
        }

        RechargeVO rechargeVO = new RechargeVO();

        rechargeVO.setOldMoney(gest.getPrepayMoney());
        rechargeVO.setMoney(gest.getPrepayMoney() + money);
        rechargeVO.setGestId(id);

        gest.setPrepayMoney(rechargeVO.getMoney());

        gestDao.save(gest);

        return rechargeVO;
    }
}

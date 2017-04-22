package com.fionapet.business.service;

import cn.fiona.pet.account.entity.Account;
import cn.fiona.pet.account.entity.Role;
import cn.fiona.pet.account.entity.User;
import cn.fiona.pet.account.exception.ApiException;
import cn.fiona.pet.account.service.AccountService;
import com.fionapet.business.entity.DictTypeDetail;
import com.fionapet.business.entity.MedicRegisterRecord;
import com.fionapet.business.entity.Persons;
import com.fionapet.business.entity.UserDict;
import com.fionapet.business.facade.vo.BillItemVO;
import com.fionapet.business.repository.DictTypeDetailDao;
import com.fionapet.business.repository.MedicMedictreatRecordDao;
import org.dubbo.x.entity.PageSearch;
import org.dubbo.x.entity.SearchFilter;
import org.dubbo.x.repository.DaoBase;
import org.dubbo.x.service.CURDServiceBase;
import com.fionapet.business.repository.MedicRegisterRecordDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 *  医生登记记录明细
* Created by tom on 2016-07-18 11:56:10.
 */
public class MedicRegisterRecordServiceImpl extends CURDServiceBase<MedicRegisterRecord> implements MedicRegisterRecordService {
    @Autowired
    private MedicRegisterRecordDao medicRegisterRecordDao;
    @Autowired
    private MedicMedictreatRecordDao medicMedictreatRecordDao;
    @Autowired
    private DictTypeDetailDao dictTypeDetailDao;
    @Autowired
    private PersonsService personsService;
    @Autowired
    private AccountService accountService;

    public AccountService getAccountService() {
        return accountService;
    }

    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public DaoBase<MedicRegisterRecord> getDao() {
        return medicRegisterRecordDao;
    }

    @Override
    public List<MedicRegisterRecord> listAll() {
        User user = (User)getCurrentUser();
        return medicRegisterRecordDao.findByDoctor(user.getName());
    }

//    @Override
//    public Page<MedicRegisterRecord> page(PageSearch pageSearch) {
//        SearchFilter searchFilter = new SearchFilter();
//
////        try {
////            if (accountService.hasRole("doctor", getToken())){
////                searchFilter.setFieldName("doctorId");
////                searchFilter.setOperator(SearchFilter.Operator.EQ.toString());
////                searchFilter.setValue(getCurrentUser().getId());
////                pageSearch.getFilters().add(searchFilter);
////            }
////        } catch (ApiException e) {
////            e.printStackTrace();
////        }
//
////        User user = (User) getCurrentUser();
////        if (null != user){
////            for (Role role: user.getRoles()){
////                if ("doctor".equals(role.getCode())){
////                    searchFilter.setFieldName("doctorId");
////                    searchFilter.setOperator(SearchFilter.Operator.EQ.toString());
////                    searchFilter.setValue(getCurrentUser().getId());
////                    pageSearch.getFilters().add(searchFilter);
////                }
////            }
//////                searchFilter = new SearchFilter();
////
//////                searchFilter.setFieldName("statusDictDetailCode");
//////                searchFilter.setOperator(SearchFilter.Operator.NOT_EQ.toString());
//////                searchFilter.setValue("SM00036");
//////                pageSearch.getFilters().add(searchFilter);
////        }
//
//
//
//        return super.page(pageSearch);
//    }

    @Override
    @Transactional
    public MedicRegisterRecord createOrUpdte(MedicRegisterRecord entity) {
        if (entity.getId() == null){
            DictTypeDetail dictTypeDetail = dictTypeDetailDao.findByDictDetailCode("SM00034");
            entity.setStatus(dictTypeDetail);
        }else{
            MedicRegisterRecord medicRegisterRecordOld = super.detail(entity.getId());
            if (null != medicRegisterRecordOld && "SM00051".equalsIgnoreCase(medicRegisterRecordOld.getPaidStatus())) {
                entity.setPaidStatus("SM00051");
            }
        }

        return super.createOrUpdte(entity);
    }

    @Override
    public List<BillItemVO> billDetail(String id) {
        List<BillItemVO> result = new ArrayList<BillItemVO>();

        List<MedicRegisterRecord> medicRegisterRecords = medicRegisterRecordDao.findByPetIdAndPaidStatusNot(id, "SM00051");

        for (MedicRegisterRecord medicRegisterRecord: medicRegisterRecords){
            BillItemVO billItemVO = new BillItemVO();

            billItemVO.setId(medicRegisterRecord.getId());
            billItemVO.setCount(1);
            billItemVO.setOldPrice(medicRegisterRecord.getRegisterPrice());
            billItemVO.setPrice(medicRegisterRecord.getRegisterPrice());

            result.add(billItemVO);
        }

        return result;
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public void over() {
        medicRegisterRecordDao.over();
        medicMedictreatRecordDao.over();
    }
}

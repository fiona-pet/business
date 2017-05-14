package com.fionapet.business.facade;

import com.fionapet.business.entity.Pet;
import com.fionapet.business.entity.PetView;
import com.fionapet.business.service.PetViewService;
import org.dubbo.x.entity.PageSearch;
import org.dubbo.x.facade.RestResult;
import org.dubbo.x.service.CURDService;
import org.dubbo.x.facade.RestServiceBase;
import com.fionapet.business.service.PetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import javax.ws.rs.HeaderParam;

/**
 * 宠物
* Created by tom on 2016-07-19 10:31:06.
 */
public class PetRestServiceImpl extends RestServiceBase<Pet> implements PetRestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PetRestServiceImpl.class);

    private PetService petService;

    private PetViewService petViewService;

    public PetService getPetService() {
        return petService;
    }


    public void setPetService(PetService petService) {

        this.petService = petService;
    }

    public PetViewService getPetViewService() {
        return petViewService;
    }

    public void setPetViewService(PetViewService petViewService) {
        this.petViewService = petViewService;
    }

    @Override
    public CURDService<Pet> getService() {
        return petService;
    }

    public RestResult<Page<PetView>> pageView(@HeaderParam("authorization") String token, PageSearch pageSearch) {
        return RestResult.OK(petViewService.page(pageSearch));
    }
}

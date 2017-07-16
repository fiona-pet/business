package com.fionapet.business.event;

import com.fionapet.business.entity.MedicMedictreatRecord;
import com.fionapet.business.entity.Pet;
import com.fionapet.business.entity.SettleAccountsView;
import com.fionapet.business.service.FosterRecordDetailService;
import com.fionapet.business.service.FosterRecordService;
import com.fionapet.business.service.PetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Created by tom on 2017/6/4.
 */
@Component
public class PetNotifier implements ApplicationListener<PetEvent> {
    @Autowired
    private PetService petService;

    private static final Logger LOGGER = LoggerFactory.getLogger(PetNotifier.class);

    @Override
    public void onApplicationEvent(PetEvent petEvent) {
        LOGGER.info("onApplicationEvent, update pet weight:{}", petEvent);

        MedicMedictreatRecord medicMedictreatRecord = (MedicMedictreatRecord)petEvent.getSource();

        Pet pet = petService.detail(petEvent.getPetId());

        if (null != pet && null != medicMedictreatRecord){
            pet.setPetWeight(medicMedictreatRecord.getWeight());
            petService.createOrUpdte(pet);
        }
    }
}

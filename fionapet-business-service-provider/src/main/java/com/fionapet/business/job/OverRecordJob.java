package com.fionapet.business.job;

import com.fionapet.business.service.MedicRegisterRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by tom on 16/8/18.
 */
@Service
public class OverRecordJob {
    private static final Logger LOGGER = LoggerFactory.getLogger(OverRecordJob.class);
    private static final String JOB_NAME = "Over Record";

    private JobEnv jobEnv;

    @Autowired
    private MedicRegisterRecordService medicRegisterRecordService;


    public void sync(){
        LOGGER.info("sync {} start...",JOB_NAME);
        try {
            medicRegisterRecordService.over();
        }catch (Exception e){
            LOGGER.warn("sync {} event error!", JOB_NAME, e);
        }
        LOGGER.info("sync {} end.",JOB_NAME);
    }


    public JobEnv getJobEnv() {
        return jobEnv;
    }

    public void setJobEnv(JobEnv jobEnv) {
        this.jobEnv = jobEnv;
    }

    public MedicRegisterRecordService getMedicRegisterRecordService() {
        return medicRegisterRecordService;
    }

    public void setMedicRegisterRecordService(MedicRegisterRecordService medicRegisterRecordService) {
        this.medicRegisterRecordService = medicRegisterRecordService;
    }
}

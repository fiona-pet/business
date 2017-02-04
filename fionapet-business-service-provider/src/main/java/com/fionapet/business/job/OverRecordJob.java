package com.fionapet.business.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by tom on 16/8/18.
 */
public class OverRecordJob {
    private static final Logger LOGGER = LoggerFactory.getLogger(OverRecordJob.class);
    private static final String JOB_NAME = "Over Record";


    private JobEnv jobEnv;

    public void sync(){
        LOGGER.info("sync {} start...",JOB_NAME);
        try {


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
}

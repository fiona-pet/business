
package com.fionapet.business.restclient;


import com.alibaba.fastjson.JSON;
import com.fionapet.business.entity.MedicMedictreatRecord;
import junit.framework.Assert;
import org.dubbo.x.consumer.Assertable;
import org.dubbo.x.consumer.RestClientTestCase;
import org.dubbo.x.entity.RestResultEnum;
import org.dubbo.x.facade.RestResult;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.ProcessingException;
import java.util.List;

/**
* Created by tom on 2016-07-18 11:09:24.
 */
public class MedicMedictreatRecordRestClient extends RestClientTestCase {
    private static final Logger LOGGER = LoggerFactory.getLogger(MedicMedictreatRecordRestClient.class);
    private static final String TOKEN = "fc5db3b3-4063-4a12-a511-880ba19e4b58";
    private static final String URL = "http://localhost:8818/api/v2/medicmedictreatrecords";

    @Test(expected = ProcessingException.class)
    public void testList(){
         assertRequest(new Assertable() {
            @Override
            public void assertBlack(RestResult restResult) throws Exception {
                RestResult<List<MedicMedictreatRecord>> result = (RestResult<List<MedicMedictreatRecord>>)restResult;
                Assert.assertEquals(restResult.getCode(), RestResultEnum.OK.getCode());

                Assert.assertNotNull(result.getData());

                Assert.assertEquals(result.getData().size(), 0);

                LOGGER.info("MedicMedictreatRecord list:{}", JSON.toJSON(restResult));
            }
        }, URL, MedicMedictreatRecordRestClient.TOKEN);
    }


//    @Test(expected = ProcessingException.class)
//    public void testCreate(){
//        final MedicMedictreatRecord medicMedictreatRecord = new MedicMedictreatRecord();
////        medicMedictreatRecord.set*("*");
//
//        Response response = getBuilder(URL, medicMedictreatRecordRestClient.TOKEN).post(Entity.entity(medicMedictreatRecord, MediaType.APPLICATION_JSON));
//
//        assertRequest(new Assertable() {
//            @Override
//            public void assertBlack(RestResult restResult) throws Exception {
//                LOGGER.info("restResult:{}", restResult);
//                RestResult<HashMap> result = (RestResult<HashMap>)restResult;
//                Assert.assertEquals(restResult.getCode(), RestResultEnum.OK.getCode());
//
//                Assert.assertNotNull(result.getData());
//
//                LOGGER.info("result.getData():{}", result.getData().getClass());
//
//                Assert.assertNotNull(result.getData().get("id"));
//
//                LOGGER.info("list:{}", JSON.toJSON(restResult));
//            }
//        }, response);
//    }



}

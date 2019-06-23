package com.fionapet.business.facade;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.fionapet.business.entity.InHospitalHealth;
import com.fionapet.business.entity.InHospitalHealth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.dubbo.x.facade.CURDRestService;
import org.dubbo.x.facade.RestResult;
import org.dubbo.x.util.ConstantVariable;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * 住院健康状况表 接口
 *
* Created by tom on 2016-07-25 09:32:32.
 */

@Path("inhospitalhealths")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({ContentType.APPLICATION_JSON_UTF_8})
@Api(value="inhospitalhealths", description = "住院健康状况表接口")
public interface InHospitalHealthRestService extends CURDRestService<InHospitalHealth>{
    /**
     * 住院健康状况表
     * @return
     */
    @GET
    @Path("")
    @ApiOperation(value = "住院健康状况表",
            notes = "住院健康状况表列表.")
    RestResult<List<InHospitalHealth>> list(@HeaderParam(ConstantVariable.HEADER_AUTHORIZATION_KEY) String token);

    /**
     * 住院健康状况表 详细信息
     *
     * @return
     */
    @GET
    @Path("/{id}")
    @ApiOperation(value = "详细信息",
            notes = "住院健康状况表详细信息.")
    RestResult<InHospitalHealth> detail(@HeaderParam(ConstantVariable.HEADER_AUTHORIZATION_KEY) String token, @ApiParam("id") @PathParam("id") String uuid);

    @POST
    @Path("")
    @ApiOperation(value = "添加/修改住院健康状况表", notes = "添加/修改住院健康状况表")
    RestResult<InHospitalHealth> create(@HeaderParam(ConstantVariable.HEADER_AUTHORIZATION_KEY) String token, InHospitalHealth InHospitalHealth);

    @DELETE
    @Path("/{id}")
    @ApiOperation(value = "删除住院健康状况表", notes = "删除住院健康状况表")
    RestResult<String> delete(@HeaderParam(ConstantVariable.HEADER_AUTHORIZATION_KEY) String token, @ApiParam("id") @PathParam("id") String uuid);
}

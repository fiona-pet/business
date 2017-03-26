package com.fionapet.business.facade;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.fionapet.business.entity.ReportByItemVO;
import com.fionapet.business.entity.ReportByPersonVO;
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
 * 统计报表 接口
 *
* Created by tom on 2016-07-18 11:56:10.
 */

@Path("reports")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({ContentType.APPLICATION_JSON_UTF_8})
@Api(value="reports", description = "统计报表接口")
public interface ReportRestService extends CURDRestService<ReportByPersonVO>{

    /**
     * 统计报表
     * @return
     */
    @GET
    @Path("/person")
    @ApiOperation(value = "统计报表",
            notes = "统计报表列表.")
    RestResult<List<ReportByPersonVO>> person(@HeaderParam(ConstantVariable.HEADER_AUTHORIZATION_KEY) String token, @ApiParam("month") @QueryParam("month") String month);
    /**
     * 统计报表
     * @return
     */
    @GET
    @Path("/item")
    @ApiOperation(value = "统计报表",
            notes = "统计报表列表.")
    RestResult<List<ReportByItemVO>> item(@HeaderParam(ConstantVariable.HEADER_AUTHORIZATION_KEY) String token, @ApiParam("month") @QueryParam("month") String month);

    /**
     * 统计报表
     * @return
     */
    @GET
    @Path("")
    @ApiOperation(value = "统计报表",
            notes = "统计报表列表.")
    RestResult<List<ReportByPersonVO>> list(@HeaderParam(ConstantVariable.HEADER_AUTHORIZATION_KEY) String token);

    /**
     * 统计报表 详细信息
     *
     * @return
     */
    @GET
    @Path("/{id}")
    @ApiOperation(value = "详细信息",
            notes = "统计报表详细信息.")
    RestResult<ReportByPersonVO> detail(@HeaderParam(ConstantVariable.HEADER_AUTHORIZATION_KEY) String token, @ApiParam("id") @PathParam("id") String uuid);

    @POST
    @Path("")
    @ApiOperation(value = "添加/修改统计报表", notes = "添加/修改统计报表")
    RestResult<ReportByPersonVO> create(@HeaderParam(ConstantVariable.HEADER_AUTHORIZATION_KEY) String token, ReportByPersonVO ReportByPersonVO);

    @DELETE
    @Path("/{id}")
    @ApiOperation(value = "删除统计报表", notes = "删除统计报表")
    RestResult<String> delete(@HeaderParam(ConstantVariable.HEADER_AUTHORIZATION_KEY) String token, @ApiParam("id") @PathParam("id") String uuid);
}

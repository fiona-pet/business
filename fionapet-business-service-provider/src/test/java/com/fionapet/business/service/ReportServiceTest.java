package com.fionapet.business.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.util.Calendar;

/**
 * Created by tom on 2017/6/11.
 */
public class ReportServiceTest {

    @Test
    public void testGetLastDayOfMonth(){
        System.out.println(getLastDayOfMonth("5"));
    }

    private int getLastDayOfMonth(String month) {
        int m = Integer.parseInt(month);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, m-1);
        // 某年某月的最后一天
        return cal.getActualMaximum(Calendar.DATE);
    }

    @Test
    public void processJson(){
        JSONObject jsonObject = JSON.parseObject("{\"id\":1,\"jsonrpc\":\"2.0\",\"result\":{\"data\":[{\"owner\":{\"name\":\"demo-张工\",\"old_id\":856707,\"id\":\"ea7c32e5-391f-4b87-ad59-47c642dabf8e\",\"type\":\"fa09796e-762d-4240-be51-a7e1ba3d23b7\",\"display_name\":\"张工\"},\"miaoshu\":\"支持教室远程视频监控、设备监控、故障预警/告警，远程管理，教学排课，统计报表；\\n支持与原有排课系统对接；\\n支持对接学校原有一卡通系统。\\n\\n\",\"permission\":\"W\",\"id\":\"0433445d-0163-245c-d094-818ceb667992\",\"type\":{\"id\":\"b1115acc-4e8d-11e8-86ab-9f88791ca1d7\",\"display_name\":\"智慧教室\"},\"display_name\":\"RG-SCP 智慧教室综合管理平台\",\"over_time\":1576598400000,\"asset_status\":{\"id\":\"USING\",\"display_name\":\"运行中\"}},{\"owner\":{\"name\":\"demo-张工\",\"old_id\":856707,\"id\":\"ea7c32e5-391f-4b87-ad59-47c642dabf8e\",\"type\":\"fa09796e-762d-4240-be51-a7e1ba3d23b7\",\"display_name\":\"张工\"},\"miaoshu\":\"声透镜球面波扩散技术；\\n绿色、清晰、饱满，专业的声音体验。\\n\\n\",\"permission\":\"W\",\"id\":\"01aa17da-0163-245c-81ba-7a9187fdf530\",\"type\":{\"id\":\"b1115acc-4e8d-11e8-86ab-9f88791ca1d7\",\"display_name\":\"智慧教室\"},\"display_name\":\"RG-Sounder100 智能音箱\",\"over_time\":1576598400000,\"asset_status\":{\"id\":\"USING\",\"display_name\":\"运行中\"}},{\"owner\":{\"name\":\"demo-张工\",\"old_id\":856707,\"id\":\"ea7c32e5-391f-4b87-ad59-47c642dabf8e\",\"type\":\"fa09796e-762d-4240-be51-a7e1ba3d23b7\",\"display_name\":\"张工\"},\"miaoshu\":\"声透镜球面波扩散技术\\n体验专业的广播级音质\\n打造教室绿色、清晰、饱满，一流的声音体验\\n\\n\",\"permission\":\"W\",\"id\":\"0023390b-0163-245c-366a-673ff7419c94\",\"type\":{\"id\":\"b1115acc-4e8d-11e8-86ab-9f88791ca1d7\",\"display_name\":\"智慧教室\"},\"display_name\":\"M600-AMP 智能功放\",\"over_time\":1576598400000,\"asset_status\":{\"id\":\"USING\",\"display_name\":\"运行中\"}},{\"owner\":{\"name\":\"demo-张工\",\"old_id\":856707,\"id\":\"ea7c32e5-391f-4b87-ad59-47c642dabf8e\",\"type\":\"fa09796e-762d-4240-be51-a7e1ba3d23b7\",\"display_name\":\"张工\"},\"miaoshu\":\"利用云终端模块获得完整PC体验的同时可实现统一管理；\\n内置的音视频切换矩阵，实现了声音、视频的集中切换控制；\\n\",\"permission\":\"W\",\"id\":\"00c63187-0163-245b-f562-35fd6ab830c1\",\"type\":{\"id\":\"b1115acc-4e8d-11e8-86ab-9f88791ca1d7\",\"display_name\":\"智慧教室\"},\"display_name\":\"M600-CAV 云终端&音视频矩阵模块\",\"over_time\":1576598400000,\"asset_status\":{\"id\":\"USING\",\"display_name\":\"运行中\"}},{\"owner\":{\"name\":\"demo-张工\",\"old_id\":856707,\"id\":\"ea7c32e5-391f-4b87-ad59-47c642dabf8e\",\"type\":\"fa09796e-762d-4240-be51-a7e1ba3d23b7\",\"display_name\":\"张工\"},\"miaoshu\":\"提供12口千兆交换接口；\\n提供PoE/PoE+供电功能；\\n可实现物联网控制\\n\",\"permission\":\"W\",\"id\":\"0439e4e9-0163-245b-9e32-2f666ee7b385\",\"type\":{\"id\":\"b1115acc-4e8d-11e8-86ab-9f88791ca1d7\",\"display_name\":\"智慧教室\"},\"display_name\":\"RG-SCC600 智能控制中心主机\",\"over_time\":1576598400000,\"asset_status\":{\"id\":\"IN_INVENTORY\",\"display_name\":\"库存中\"}}],\"pageIndex\":1,\"pageSize\":12,\"totalPage\":1,\"totalRecord\":5}}");
        JSONArray jsonArray = jsonObject.getJSONObject("result").getJSONArray("data");
        for (int i = 0; i < jsonArray.size(); i++){
            JSONObject o = jsonArray.getJSONObject(i);
            System.out.println("http://101.201.146.190:86/relax/#/utilization/assetDetail/" + o.getString("id") + "/b1115acc-4e8d-11e8-86ab-9f88791ca1d7" + "\t" + o.getString("display_name"));
        }

    }
}

package com.yunda.gzjx.module.hvTest.entry;

import com.blankj.utilcode.util.TimeUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 项目:  JX_GZ_PAD <br>
 * 描述:  (别名:Ticket)<br>
 * 创建人: 邹旭<br>
 * 创建时间: 2019/1/24 09:33<br>
 */
public class FaultTask {
    public String ticketIdx = "";
    public String handleEmpId = "";//处理人id
    public String handleEmpName = "";//处理人名称
    public String repairMode="";//作业方式
    public String handleTime="";//处理时间
    public String ticketWorkStationIdx = "";//提票工位idx
    public String ticketWorkStationName = "";//提票工位名称
    public String handleWorkStationName = "";//处理工位名称
    public String ticketStaionName = "";//提票工位名称
    public String createrName = "";
    public String ticketContent = "";//报活内容
    public String ticketLocation = "";//报活地点
    public String remarks = "";//备注
    public String status="";// 修改:3
    public List<Quality> qualityList = null;
    /**
     * createTime : 2019-01-21
     * workPlanIdx : 5d706e628dd6435aa8553df43590423c
     * qualityList : []
     * creater : wangdajun
     * workStationName : 高压试验
     */

    public String createTime="";
    public String workPlanIdx="";
    public String creater="";
    public String workStationName="";//责任工位
    public String workStationIdx = "";//处理工位idx

    public String updateTime = "";//更新时间
    public String updator = "";//更新人Id
    public String updatorName = "";//更新人名称

    public FaultTask(String userid, String empname) {
        this.creater = userid;
        this.createrName = empname;
        this.createTime = TimeUtils.date2String(new Date(), new SimpleDateFormat("yyyy-MM-dd"));

        this.handleEmpId = userid;
        this.handleEmpName = empname;

        this.updator = userid;
        this.updatorName = empname;
        this.updateTime = this.createTime;
    }

    public FaultTask() {
    }

    public static class Quality {
        public String qualityEmpId="";
        public String qualityUpdateTime="";
        public String qualityEmpName="";
        public String qualityIdx="";
        public String qualityType="";
        public String qualityResult="";
        public String createTime="";
        public String creater="";
        public String createrName="";
        public String updator="";
        public String updatorName="";
    }
}
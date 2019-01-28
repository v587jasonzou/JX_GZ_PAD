package com.yunda.gzjx.module.hvTest.entry;

import com.blankj.utilcode.util.TimeUtils;
import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 项目:  JX_GZ_PAD <br>
 * 描述:  物料<br>
 * 创建人: 邹旭<br>
 * 创建时间: 2019/1/21 11:11<br>
 */
public class Material {
    public String repairActivityIdx = "";//
    public String workPlanIdx = "";//机车idx
    public String repairResult = "";
    public String matUpdateTime = "";
    public String remarks = "";
    public String matName = "";//物料名称
    public String hanleUserId = "";
    public String partsNo = "";
    public String modelsSpecifications = "";//规格
    public String identificationCode = "";
    public String matCode = "";//物资编号
    public String price = "";
    public String source = "";
    public String matIdx = "";//主键
    public String isSend = "";//是否配送（0否 1是）
    public String aboardPlace = "";
    public String isNeedChange = "";//必换/偶换 (1:必换  2:偶换)
    public String supplier = "";
    public String partsSource = "";
    public String hanleUserName = "";
    public List<Quality> qualityList;
    /**
     * status : 1
     * creater : wangdajun
     * createrName : wangdajun
     * createTime : 2019-01-23
     * updator : wangdajun
     * updatorName : wangdajun
     * qty : 0
     */

    public String status = "1";
    public String creater = "";
    public String createrName = "";
    public String createTime = "";
    public String updator = "";
    public String updatorName = "";
    @SerializedName("qty")
    public int qty = 0;//数量
    public String requisitionStatus="";//领料状态(,1,领料审批,2,审批通过,3已领取)

    public Material() {
    }

    public Material(String repairActivityIdx, String workPlanIdx, String createrId, String createrName) {
        this.repairActivityIdx = repairActivityIdx;
        this.workPlanIdx = workPlanIdx;
        this.creater = this.updator = createrId;
        this.createrName = this.updatorName = createrName;
        this.createTime = TimeUtils.date2String(new Date(), new SimpleDateFormat("yyyy-MM-dd"));
        this.qualityList = new ArrayList<>();

    }

    public static class Quality {
        public String qualityEmpId;
        public String qualityUpdateTime;
        public String qualityEmpName;
        public String qualityIdx;
        public String qualityType;
        public String qualityResult;
        public String createTime;
        public String creater;
        public String createrName;
        public String updator;
        public String updatorName;
    }
}

package com.yunda.gzjx.module.hvTest.entry;

import java.util.List;

/**
 * 项目:  JX_GZ_PAD <br>
 * 描述:  物料<br>
 * 创建人: 邹旭<br>
 * 创建时间: 2019/1/21 11:11<br>
 */
public class Material {
    // TODO: 2019/1/21 匹配服务器接口数据
//    public String name;//物料名称
//    public String count;//数量
//    public String spec;//规格
//    public String isOptionPart;//必换/偶换
//    public String idx;//主键

    /**
     * repairResult : 合格
     * matUpdateTime : 2018-12-19
     * remarks : 测试1
     * qty : 1
     * matName : 物料名称
     * hanleUserId : wangdajun
     * partsNo : 111
     * modelsSpecifications : 测试1
     * identificationCode : 4232424
     * matCode : 111
     * price : 40
     * source : 1
     * matIdx : de2d4a1bb7224dafb38ea4c7cfc466e9
     * isSend : 1
     * aboardPlace :
     * isNeedChange : 2
     * supplier : 测试1
     * partsSource : 2
     * hanleUserName : 王大军
     * qualityListData : [{"qualityEmpId":"wangdajun","qualityUpdateTime":"2018-12-19","qualityEmpName":"王大军","qualityIdx":"b5a17fecea084515a11b24fb111bf3ef","qualityType":"工长","qualityResult":"合格"},{"qualityEmpId":"wangdajun","qualityUpdateTime":"2018-12-19","qualityEmpName":"王大军","qualityIdx":"b5a17fecea084515a11b24fb111bf3ef","qualityType":"质检","qualityResult":"合格"}]
     */

    public String repairResult;
    public String matUpdateTime;
    public String remarks;
    public String qty;//数量
    public String matName;//物料名称
    public String hanleUserId;
    public String partsNo;
    public String modelsSpecifications;//规格
    public String identificationCode;
    public String matCode;
    public String price;
    public String source;
    public String matIdx;//主键
    public String isSend;//是否配送（0否 1是）
    public String aboardPlace;
    public String isNeedChange;//必换/偶换 (1:必换  2:偶换)
    public String supplier;
    public String partsSource;
    public String hanleUserName;
    public List<Material.Quality> qualityList;

    public static class Quality {
        /**
         * qualityEmpId : wangdajun
         * qualityUpdateTime : 2018-12-19
         * qualityEmpName : 王大军
         * qualityIdx : b5a17fecea084515a11b24fb111bf3ef
         * qualityType : 工长
         * qualityResult : 合格
         */

        public String qualityEmpId;
        public String qualityUpdateTime;
        public String qualityEmpName;
        public String qualityIdx;
        public String qualityType;
        public String qualityResult;
        /**
         * createTime : 2019-01-22
         * creater : wangdajun
         * createrName : 王大军
         * updator : wangdajun
         * updatorName : 王大军
         */

        public String createTime;
        public String creater;
        public String createrName;
        public String updator;
        public String updatorName;
    }
}

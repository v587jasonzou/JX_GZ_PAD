package com.yunda.gzjx.module.jcyj.entity;

import java.util.List;

/**
 * 项目:  JX_GZ_PAD <br>
 * 描述:  略<br>
 * 创建人: 邹旭<br>
 * 创建时间: 2019/2/15 10:01<br>
 */
public class PerformanceTestEntity {
    /**
     * workEmpId : null
     * status : 1
     * seqNo : 1
     * itemList : [{"unit":null,"technicalStatusDefIdx":"08dfbfd0d739458386db6d57b830797a","dictItemCode":"6","detectItemContent":"良好"}]
     * item : NJWY3A型机车卫生间已改造（保留）
     * workEmpName : null
     * badStateDesc : AS DFSDA FSDA
     * remarks : null
     * bookIdx : 33C3AD5FE817439CBE125043A245E53A
     * workEmpTime : null
     */

    public String workEmpId;
    public String status;
    public int seqNo;
    public String item;
    public String workEmpName;
    public String badStateDesc;//其它  - 备注
    public String badStateResult;//选中项
    public String remarks;
    public String bookIdx;
    public String workEmpTime;
    public List<ItemList> itemList;
    public String updatorName;
    public String updateTime;
    public String updator;

    public static class ItemList {
        /**
         * unit : null
         * technicalStatusDefIdx : 08dfbfd0d739458386db6d57b830797a
         * dictItemCode : 6
         * detectItemContent : 良好
         */

        public String unit;
        public String itemIdx;
        public String dictItemCode;
        public String detectItemContent;
//        public String detectResult;
    }
}

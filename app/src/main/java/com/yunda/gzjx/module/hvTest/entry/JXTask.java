package com.yunda.gzjx.module.hvTest.entry;

import java.util.List;

/**
 * 项目:  JX_GZ_PAD <br>
 * 描述:  "巡检记录" - 作业项目列表 - 巡检任务列表(列表项)<br>
 *        (别名：作业任务，检修记录)
 * 创建人: 邹旭<br>
 * 创建时间: 2019/1/14 14:31<br>
 */
public class JXTask {

    /**
     * idx : B7A7365512C14C1B9D8D5F3D7680184C
     * workEmpId :
     * status : 10
     * qualityList : []
     * workEmpName :
     * repairContent : 制动监测子系统试验：
     * remarks :
     * detectResultList : [{"detectResult":" ","unit":" ","idx":"4BCC48B599464CEF85E9C841E99479F7","dictItemCode":"2","workTaskIdx":"B7A7365512C14C1B9D8D5F3D7680184C","detectItemContent":"其它"},{"detectResult":" ","unit":" ","idx":"8F325EF991AF4CE099BC04F3AE300B85","dictItemCode":"5","workTaskIdx":"B7A7365512C14C1B9D8D5F3D7680184C","detectItemContent":"各传感器关闭"}]
     */

    public String idx;
    public String workEmpId;
    public String status;
    public String workEmpName;
    public String repairContent;
    public String remarks;
    public List<Quality> qualityList;
    public List<DetectResult> detectResultList;

    /*业务属性 - UI相关*/
    public boolean isSaveSingleChecked = false;
    public boolean isSaveLaterChecked = false;

    /**
     * 检修记录列表项 - 作业情况属性
     */
    public static class DetectResult {
        /**
         * detectResult :
         * unit :
         * idx : 4BCC48B599464CEF85E9C841E99479F7
         * dictItemCode : 2
         * workTaskIdx : B7A7365512C14C1B9D8D5F3D7680184C
         * detectItemContent : 其它
         */

        public String detectResult;
        public String unit;
        public String idx;
        public String dictItemCode;//字典项编码 :如果是0,则为输入项;其它数字则为选择项
        public String workTaskIdx;
        public String detectItemContent;
    }

    public static class Quality{
        public String checkEmpId;
        public String checkEmpName;
        public String updateTime;
        public String qualityIdx;
        public String qualityItem;
        public String repairResult;
        public String workTaskIdx;

        public Quality(String checkEmpId, String checkEmpName, String updateTime, String qualityIdx, String qualityItem, String repairResult, String workTaskIdx) {
            this.checkEmpId = checkEmpId;
            this.checkEmpName = checkEmpName;
            this.updateTime = updateTime;
            this.qualityIdx = qualityIdx;
            this.qualityItem = qualityItem;
            this.repairResult = repairResult;
            this.workTaskIdx = workTaskIdx;
        }
    }
}

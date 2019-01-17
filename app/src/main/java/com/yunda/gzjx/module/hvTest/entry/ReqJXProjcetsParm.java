package com.yunda.gzjx.module.hvTest.entry;

import com.google.gson.annotations.SerializedName;

/**
 * 项目:  JX_GZ_PAD <br>
 * 描述:  略<br>
 * 创建人: 邹旭<br>
 * 创建时间: 2019/1/11 13:33<br>
 */
public class ReqJXProjcetsParm {
    @SerializedName("workPlanIdx")
    private String trainId;
    @SerializedName("workStationIdx")
    private String relationIdx;
    @SerializedName("pageNumber")
    private int pageNumber;
    @SerializedName("pageSize")
    private int pageSize;
    @SerializedName("findKey")
    private String keywords;

    public ReqJXProjcetsParm(String trainId, String relationIdx, int pageNumber, int pageSize, String keywords) {
        this.trainId = trainId;
        this.relationIdx = relationIdx;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.keywords = keywords;
    }

    public ReqJXProjcetsParm(String trainId, String relationIdx, int pageNumber, int pageSize) {
        this(trainId, relationIdx, pageNumber, pageSize, null);
    }

    public ReqJXProjcetsParm(String trainId, String relationIdx, String keywords) {
        this(trainId, relationIdx, 0, 20, keywords);
    }

    public String getTrainId() {
        return trainId;
    }

    public void setTrainId(String trainId) {
        this.trainId = trainId;
    }

    public String getRelationIdx() {
        return relationIdx;
    }

    public void setRelationIdx(String relationIdx) {
        this.relationIdx = relationIdx;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
}

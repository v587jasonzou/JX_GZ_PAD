package com.yunda.gzjx.module.login.repository.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MenuSimpleBean implements Serializable {

    /**
     * menu : 配件下车登记
     * loginClient : PDAClient
     */
    @SerializedName("menuName")
    private String menu;
    private String loginClient;
    @SerializedName("idx")
    private String relationIdx;

    public String getRelationIdx() {
        return relationIdx;
    }

    public void setRelationIdx(String relationIdx) {
        this.relationIdx = relationIdx;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getLoginClient() {
        return loginClient;
    }

    public void setLoginClient(String loginClient) {
        this.loginClient = loginClient;
    }
}

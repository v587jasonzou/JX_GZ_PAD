package com.yunda.gzjx.module.login.repository.entity;

import java.io.Serializable;

public class MenuSimpleBean implements Serializable {

    /**
     * menu : 配件下车登记
     * loginClient : PDAClient
     */

    private String menu;
    private String loginClient;

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

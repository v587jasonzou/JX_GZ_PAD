package com.yunda.gzjx.module.login.repository.entity;

import java.io.Serializable;


public class LoginRes implements Serializable {


    /**
     * acOperator : {"authmode":"local","ipaddress":"192.168.10.140","lastlogin":1541558510000,"menutype":"default","operatorid":6,"operatorname":"王治明","password":"ZwsUcorZkCrsujLiL6T2vQ==","status":"running","userid":"2051"}
     * acOperatorSession : {"authmode":"local","ipaddress":"192.168.10.140","lastlogin":1541558510000,"menutype":"default","operatorid":6,"operatorname":"王治明","password":"ZwsUcorZkCrsujLiL6T2vQ==","status":"running","userid":"2051"}
     * emp : {"empcode":"2051","empid":5,"empname":"王治明","empstatus":"on","gender":"n","lastmodytime":1539585432000,"mobileno":"1399999999","operatorid":6,"orgid":2,"userid":"2051"}
     * org : {"createtime":1539248957000,"isleaf":"y","lastupdate":1539248957000,"orgcode":"0101","orgdegree":"plant","orgid":2,"orglevel":2,"orgname":"生产调度中心","orgseq":".1.2.","orgtype":"车间","parentorgid":1,"port":0,"sortno":0,"status":"running","subcount":0}
     */

    private AcOperator acOperator;
    private AcOperatorSession acOperatorSession;
    private Emp emp;
    private Org org;

    public AcOperator getAcOperator() {
        return acOperator;
    }

    public void setAcOperator(AcOperator acOperator) {
        this.acOperator = acOperator;
    }

    public AcOperatorSession getAcOperatorSession() {
        return acOperatorSession;
    }

    public void setAcOperatorSession(AcOperatorSession acOperatorSession) {
        this.acOperatorSession = acOperatorSession;
    }

    public Emp getEmp() {
        return emp;
    }

    public void setEmp(Emp emp) {
        this.emp = emp;
    }

    public Org getOrg() {
        return org;
    }

    public void setOrg(Org org) {
        this.org = org;
    }
}

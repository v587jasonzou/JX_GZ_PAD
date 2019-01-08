package com.yunda.gzjx.app;

import com.yunda.gzjx.module.login.repository.entity.AcOperator;
import com.yunda.gzjx.module.login.repository.entity.AcOperatorSession;
import com.yunda.gzjx.module.login.repository.entity.Emp;
import com.yunda.gzjx.module.login.repository.entity.MenuSimpleBean;
import com.yunda.gzjx.module.login.repository.entity.Org;

import java.util.ArrayList;
import java.util.List;

public class SysInfo {
//    public static UserInfo user;
//    public static String empname;// 人员姓名
//    public static Long empid; // 人员编号
//    public static Long orgid; // 主机构编号
//    public static Long operatorid; // 操作员编号
//    public static String uuid;
//    public static List<MenuBean> menus = new ArrayList<>();
    public static AcOperator acOperator;
    public static AcOperatorSession acOperatorSession;
    public static Emp emp;
    public static Org org;
    public static List<MenuSimpleBean> menus = new ArrayList<>();
}

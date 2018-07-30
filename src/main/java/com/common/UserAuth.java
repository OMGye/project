package com.common;

/**
 * Created by upupgogogo on 2018/7/29.下午3:05
 */
public enum  UserAuth {

    BOSS(0,"老板"),
    MANAGER(1,"项目经理"),
    FINANCIAL(2,"公司财务管理员"),
    ACCOUNT_UPLOAD(3,"记账员"),
    ACCOUNT_CHECKED(4,"财务审核员"),
    MATERIAL_UPLOAD(5,"材料员"),
    MATERIAL_CHECKED(6,"材料审核员"),
    EMPLOYEE(7,"普通员工");

    private final int code;
    private final String desc;
    UserAuth(int code,String desc){
        this.code = code;
        this.desc = desc;
    }

    public int getCode(){
        return code;
    }
    public String getDesc(){
        return desc;
    }
}

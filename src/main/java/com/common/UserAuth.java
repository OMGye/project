package com.common;

/**
 * Created by upupgogogo on 2018/7/29.下午3:05
 */
public enum  UserAuth {

    BOSS(0,"老板"),
    FINANCIAL(1,"公司财务管理员"),
    MANAGER(2,"项目经理"),
    ITEM_UPLOAD(3,"项目记录员");
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

package com.common;

/**
 * Created by upupgogogo on 2018/9/28.下午1:47
 */
public enum RecordAuth {

    MATERIAL(0,"材料"),
    PEOPLE(2,"人工完工费"),
    MACHINE(1,"机械完工费"),
    OTHER(3,"其他");
    private final int code;
    private final String desc;
    RecordAuth(int code,String desc){
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

package com.common;

/**
 * Created by upupgogogo on 2018/7/29.下午10:28
 */
public class Const {

    public static final String CURRENT_USER = "currentUser";

    public interface User{
        int ACTIVATE = 1;
        int UNACTIVATE = 0;
    }

    public interface Item{
        int WORKING = 0;
        int FINISHED = 1;
    }
}
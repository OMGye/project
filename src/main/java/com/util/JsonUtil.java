package com.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vo.ItemIndexVo;

import java.util.List;

/**
 * Created by upupgogogo on 2018/11/9.下午2:54
 */
public class JsonUtil {
    private static Gson gson = new Gson();

    public static String toJonSting(List<ItemIndexVo> list){

        String str = gson.toJson(list);
        return str;

    }

    public static List<ItemIndexVo> toJsonList(String json){
        List<ItemIndexVo> list = gson.fromJson(json, new TypeToken<List<ItemIndexVo>>(){}.
                getType());

        return list;
    }
}

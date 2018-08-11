package com.vo;

import com.pojo.Item;
import com.pojo.MaterialStock;

import java.util.List;

/**
 * Created by upupgogogo on 2018/8/11.下午12:23
 */
public class ItemMaterialDetailVo {

    private Item item;

    private List<MaterialStock> list;

    public ItemMaterialDetailVo(Item item, List<MaterialStock> list) {
        this.item = item;
        this.list = list;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void setList(List<MaterialStock> list) {
        this.list = list;
    }

    public Item getItem() {

        return item;
    }

    public List<MaterialStock> getList() {
        return list;
    }
}

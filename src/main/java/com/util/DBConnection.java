package com.util;

import com.vo.MaterialListVo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by upupgogogo on 2018/8/9.上午12:05
 */
public class DBConnection {

    /**
     * 驱动类名称
     */
    private static final String DRIVER_CLASS = "com.mysql.jdbc.Driver";

    /**
     * 数据库连接字符串   jdbc:mysql://localhost:3306/test 这种方式只对本地的数据库有用       test为数据库名称
     */
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/project?characterEncoding=utf-8";

    /**
     * 数据库用户名
     */
    private static final String USER_NAME = "root";

    /**
     * 数据库密码
     */
    private static final String PASSWORD = "147258";
    /**
     * 数据库连接类
     */
    private static Connection conn;

    /**
     * 数据库操作类
     */
    private static Statement stmt;



    // 加载驱动
    static{
        try {
            Class.forName(DRIVER_CLASS);
        } catch (Exception e) {
            System.out.println("加载驱动错误");
            System.out.println(e.getMessage());
        }
    }

    // 取得连接
    private static Connection getConnection(){

        try {
            conn = DriverManager.getConnection(DATABASE_URL, USER_NAME, PASSWORD);
        } catch (Exception e) {
            System.out.println("取得连接错误");
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static PageBean<MaterialListVo> getMaterialVo(int pageNum, int pageSize) {
        PageBean pageBean = new PageBean();
        try {
            stmt = getConnection().createStatement();
        } catch (Exception e) {
            System.out.println("statement取得错误");
            System.out.println(e.getMessage());
            return null;
        }
        try {
            String sql = "select count(*) rec from material_buy_info ";
            ResultSet rs = stmt.executeQuery(sql);
            int rowCount = 0;
            while (rs.next()) {
                rowCount = rs.getInt("rec");
            }
            String sql2 = "select count(*) rec from material_use_info ";
            ResultSet rs2 = stmt.executeQuery(sql2);
            int rowCount2 = 0;
            while (rs2.next()) {
                rowCount2 = rs2.getInt("rec");
            }
            int totalCount = rowCount + rowCount2;
            pageBean.setTotalCount(totalCount);
            int totalPage = 0;
            if (totalCount % pageSize == 0) {
                totalPage = totalCount / pageSize;
            } else {
                totalPage = totalCount / pageSize + 1;
            }
            pageBean.setTotalPage(totalPage);
            pageBean.setPageNum(pageNum);
            pageBean.setPageSize(pageSize);
            int begin = (pageNum - 1) * pageSize;
            String sql3 = "SELECT material_info_id as id,item_id,user_id,category_name,check_user_name,number,create_time,last_edit_time,state from material_buy_info union all select material_use_id as id,item_id,user_id,category_name,check_user_name,number,create_time,last_edit_time ,state from material_use_info where state = 1 order by last_edit_time DESC limit " +  begin + "," + pageSize;
            ResultSet rs3 = stmt.executeQuery(sql3);
            List<MaterialListVo> listVos = new ArrayList<>();
            while (rs3.next()) {
                MaterialListVo materialListVo = new MaterialListVo();
                materialListVo.setId(rs3.getInt(1));
                materialListVo.setItemId(rs3.getInt(2));
                materialListVo.setUserId(rs3.getInt(3));
                materialListVo.setCategoryName(rs3.getString(4));
                materialListVo.setCheckUserName(rs3.getString(5));
                materialListVo.setNumber(rs3.getInt(6));
                materialListVo.setCreateTime(rs3.getDate(7));
                materialListVo.setLastEditTime(rs3.getDate(8));
                materialListVo.setType(materialListVo.getNumber() > 0 ? 1 : 0);
                listVos.add(materialListVo);
            }
            pageBean.setList(listVos);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return pageBean;
    }

    public static void main(String[] args) {
        PageBean pageBean = getMaterialVo(2,5);
        System.out.println(pageBean.getList().size());
    }
}
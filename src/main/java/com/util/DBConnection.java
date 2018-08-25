package com.util;

import com.vo.AccountItemVo;
import com.vo.MaterialListVo;

import java.math.BigDecimal;
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
    private static final String DATABASE_URL = "jdbc:mysql://193.112.26.167:3306/project?characterEncoding=utf-8";

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
            String sql3 = "SELECT material_info_id as id,item_id,user_id,category_name,check_user_name,number,create_time,last_edit_time,state from material_buy_info where state = 1  union all select material_use_id as id,item_id,user_id,category_name,check_user_name,number,create_time,last_edit_time ,state from material_use_info where state = 1 order by last_edit_time DESC limit " +  begin + "," + pageSize;
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
                java.util.Date createTime = rs3.getTimestamp(7);
                java.util.Date lastEidtTime = rs3.getTimestamp(8);
                materialListVo.setCreateTime(DateTimeUtil.dateToStr(createTime));
                materialListVo.setLastEditTime(DateTimeUtil.dateToStr(lastEidtTime));
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
    public static PageBean<AccountItemVo> getItemAccount(int pageNum, int pageSize) {
        PageBean<AccountItemVo> pageBean = new PageBean();
        try {
            stmt = getConnection().createStatement();
        } catch (Exception e) {
            System.out.println("statement取得错误");
            return null;
        }
        try {
            String sql = "select count(*) rec from project_item ";
            ResultSet rs = stmt.executeQuery(sql);
            int rowCount = 0;
            while (rs.next()) {
                rowCount = rs.getInt("rec");
            }
            int totalCount = rowCount;
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
            List<AccountItemVo> list = new ArrayList<>();
            if (pageNum == 1){
                AccountItemVo accountItemVo = new AccountItemVo();
                accountItemVo.setItemId(null);
                accountItemVo.setItemName("公司财务");
                list.add(accountItemVo);
            }
            String sql2 = "select item_id,item_name from project_item order by create_time desc limit " +  begin + "," + pageSize ;
            ResultSet rs2 = stmt.executeQuery(sql2);
            while (rs2.next()) {
                AccountItemVo accountItemVo = new AccountItemVo();
                accountItemVo.setItemId(rs2.getInt(1));
                accountItemVo.setItemName(rs2.getString(2));
                list.add(accountItemVo);
            }

            for (int i = 0; i < list.size(); i++) {
                String sql3 = "";
                if (list.get(i).getItemId() == null)
                     sql3 = "select sum(account_rel_price) he from account_info where account_rel_price > 0 and item_id is null ";
                else
                    sql3 = "select sum(account_rel_price) he from account_info where account_rel_price > 0 and item_id = " + list.get(i).getItemId();
                ResultSet rs3 = stmt.executeQuery(sql3);
                while (rs3.next()) {
                    if (rs3.getBigDecimal("he") != null)
                        list.get(i).setIncomeAccount(rs3.getBigDecimal("he"));
                    else
                        list.get(i).setIncomeAccount(new BigDecimal(0));
                }

                String sql4 = "";
                if (list.get(i).getItemId() == null)
                    sql4 = "select sum(account_rel_price) he from account_info where account_rel_price < 0 and item_id is null ";
                else
                    sql4 = "select sum(account_rel_price) he from account_info where account_rel_price < 0 and item_id = " + list.get(i).getItemId();
                ResultSet rs4 = stmt.executeQuery(sql4);
                while (rs4.next()) {
                    if (rs4.getBigDecimal("he") != null)
                        list.get(i).setPayAccount(rs4.getBigDecimal("he"));
                    else
                        list.get(i).setPayAccount(new BigDecimal(0));
                }
            }
            pageBean.setPageNum(list.size());
            pageBean.setList(list);

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


    public static List<MaterialListVo> getMaterialByTime(String startTime, String endTime,Integer itemId) {
        List<MaterialListVo> listVos = new ArrayList<>();
        try {
            stmt = getConnection().createStatement();
        } catch (Exception e) {

        }
        try {

            String sql3 = "SELECT material_info_id as id,item_id,user_id,category_name,check_user_name,number,create_time,last_edit_time,state from material_buy_info where state = 1 and last_edit_time > "+"'"+startTime+"'"+" and "+"'"+endTime+"'"+" > last_edit_time and item_id = "+itemId+" union all select material_use_id as id,item_id,user_id,category_name,check_user_name,number,create_time,last_edit_time ,state from material_use_info where last_edit_time > "+"'"+startTime+"'"+" and  "+"'"+endTime+"'"+" > last_edit_time and state = 1 and item_id = "+itemId+" order by last_edit_time DESC ";
            ResultSet rs3 = stmt.executeQuery(sql3);
            while (rs3.next()) {
                MaterialListVo materialListVo = new MaterialListVo();
                materialListVo.setId(rs3.getInt(1));
                materialListVo.setItemId(rs3.getInt(2));
                materialListVo.setUserId(rs3.getInt(3));
                materialListVo.setCategoryName(rs3.getString(4));
                materialListVo.setCheckUserName(rs3.getString(5));
                materialListVo.setNumber(rs3.getInt(6));
                java.util.Date createTime = rs3.getTimestamp(7);
                java.util.Date lastEidtTime = rs3.getTimestamp(8);
                materialListVo.setCreateTime(DateTimeUtil.dateToStr(createTime));
                materialListVo.setLastEditTime(DateTimeUtil.dateToStr(lastEidtTime));
                materialListVo.setType(materialListVo.getNumber() > 0 ? 1 : 0);
                listVos.add(materialListVo);
            }

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
        return listVos;
    }
    public static void main(String[] args) {
       List list = getMaterialByTime("2018-08-10","2018-08-14",27);
        System.out.println(list.size());

    }
}
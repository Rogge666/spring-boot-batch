package com.rogge.batch.common.sql;

import com.rogge.batch.common.utils.BeanUtils;

import java.beans.IntrospectionException;
import java.util.Arrays;

/**
 * [Description]
 * <p>
 * [How to use]
 * <p>
 * [Tips]
 *
 * @author Created by Rogge on 2018-01-21.
 * @since 1.0.0
 */
public class SQLUtils {

    public static final String GET_STOCK_SQL = "select * from stock where code = ? and date = ?";
    public static final String GET_EXCEPT_STOCK_SQL = "select * from stock where pe < 20";
    //使用getInsertSQL()生成
    public static final String INSERT_STOCK_SQL = "insert into stock (code,name,date,yEndPrice,startPrice,highPrice,lowPrice,endPrice,volume,amount,rof,changePercent,averagePrice,turnoverRate,acmv,bcmv,amv,afoe,bfoe,foe,pe,pb,ptsr,pcf,status) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    //使用getUpdateSQL()生成
    public static final String UPDATE_STOCK_SQL = "update stock set code = ?, name = ?, date = ?, yEndPrice = ?, startPrice = ?, highPrice = ?, lowPrice = ?, endPrice = ?, volume = ?, amount = ?, rof = ?, changePercent = ?, averagePrice = ?, turnoverRate = ?, acmv = ?, bcmv = ?, amv = ?, afoe = ?, bfoe = ?, foe = ?, pe = ?, pb = ?, ptsr = ?, pcf = ?, status = ? where id = ?";
    public static final String DELETE_STOCK_SQL = "delete from stock where id = ?";

    public static final String GET_ALL_STOCK_EXCEPT_SQL = "select * from stockExcept";
    public static final String GET_STOCK_EXCEPT_SQL = "select * from stockExcept where code = ? and date = ?";
    public static final String INSERT_STOCK_EXCEPT_SQL = "insert into stockExcept (code,name,date,yEndPrice,startPrice,highPrice,lowPrice,endPrice,volume,amount,rof,changePercent,averagePrice,turnoverRate,acmv,bcmv,amv,afoe,bfoe,foe,pe,pb,ptsr,pcf,status) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    public static final String UPDATE_STOCK_EXCEPT_SQL = "update stockExcept set code = ?, name = ?, date = ?, yEndPrice = ?, startPrice = ?, highPrice = ?, lowPrice = ?, endPrice = ?, volume = ?, amount = ?, rof = ?, changePercent = ?, averagePrice = ?, turnoverRate = ?, acmv = ?, bcmv = ?, amv = ?, afoe = ?, bfoe = ?, foe = ?, pe = ?, pb = ?, ptsr = ?, pcf = ?, status = ? where id = ?";
    public static final String DELETE_STOCK_EXCEPT_SQL = "delete from stockExcept where id = ?";

    public static void main(String[] args) throws ClassNotFoundException, IntrospectionException {
//        getCreateSQL();
//        getUpdateSQL();
//        getInsertSQL();
//        getSetMethodsByBean();
//        getInsertArgs();
        getInsertArgsV2();

    }

    //拼写建表语句
    private static String getCreateSQL() throws ClassNotFoundException {

        StringBuilder lCreateSB = new StringBuilder();
        for (String lS : BeanUtils.getDeclaredFields()) {
            lCreateSB.append("`").append(lS).append("`").append("CHAR(20) NULL DEFAULT NULL,");
        }
        System.out.println(lCreateSB.toString());
        return lCreateSB.toString();
    }

    //拼写更新语句
    private static String getUpdateSQL() throws ClassNotFoundException {

        StringBuilder lUpdateSB = new StringBuilder();
        for (String lS : BeanUtils.getDeclaredFields()) {
            lUpdateSB.append(lS).append(",");
        }
        System.out.println(lUpdateSB.toString());
        return lUpdateSB.toString();
    }

    //拼写插入语句
    private static String getInsertSQL() throws ClassNotFoundException {

        StringBuilder lInsertSB = new StringBuilder();
        for (String lS : BeanUtils.getDeclaredFields()) {
            lInsertSB.append(lS).append(" = ?, ");
        }
        System.out.println(lInsertSB.toString());
        return lInsertSB.toString();
    }

    //拼写插入语句的Object...
    private static String getInsertArgs() throws ClassNotFoundException, IntrospectionException {
        StringBuilder lInsertArgs = new StringBuilder();
        String[] lReadMethods = BeanUtils.getReadMethods();
        for (int lI = 0; lI < lReadMethods.length; lI++) {
            lInsertArgs.append("lCSVStockBean.").append(lReadMethods[lI]).append("(),");
        }
        System.out.println(lInsertArgs.toString());
        return lInsertArgs.toString();
    }

    //拼写批量插入语句的Object[]
    private static String[] getInsertArgsV2() throws ClassNotFoundException, IntrospectionException {
        String[] lReadMethods = BeanUtils.getReadMethods();
        String[] args = new String[lReadMethods.length];
        for (int lI = 0; lI < lReadMethods.length; lI++) {
            args[lI] = "lCSVStockBean." + lReadMethods[lI] + "()";
        }
        System.out.println(Arrays.toString(args));
        return args;
    }

    /**
     * 拼写 {@link com.rogge.batch.module3.C2dItemWrite#write}中的set语句
     * */
    private static String getSetMethodsByBean() throws IntrospectionException, ClassNotFoundException {
        //拼写Set语句
        StringBuilder lSetString = new StringBuilder();
        String[] lWriteMethods = BeanUtils.getWriteMethods();
        for (int lI = 0; lI < lWriteMethods.length; lI++) {
            lSetString.append("lDBStockBean.").append(lWriteMethods[lI]).append("(resultSet.getString(").append(lI+2).append(")) ; \n");
        }
        String lX = lSetString.toString();
        System.out.println(lX);
        return lX;
    }
}

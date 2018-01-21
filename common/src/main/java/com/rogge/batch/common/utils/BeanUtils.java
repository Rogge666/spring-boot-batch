package com.rogge.batch.common.utils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * [Description]
 * <p>
 * [How to use]
 * <p>
 * [Tips]
 *
 * @author Created by Rogge on 2018-01-20.
 * @since 1.0.0
 */
public class BeanUtils {
    public static String[] getDeclaredFields() throws ClassNotFoundException {
        Class c = Class.forName("com.rogge.batch.common.bean.CSVStockBean");
        //获得该对象的公共成员变量
        Field[] f = c.getDeclaredFields();
        String[] names = new String[f.length];
        for (int lI = 0; lI < f.length; lI++) {
            names[lI] = f[lI].getName();
        }
        return names;
    }

    /**
    * 获取DBStockBean超类的所有的set方法
    */
    public static String[] getWriteMethods() throws IntrospectionException, ClassNotFoundException {
        Class lClass = Class.forName("com.rogge.batch.common.bean.DBStockBean");
        Field[] fields = lClass.getSuperclass().getDeclaredFields();
        String[] writeMethods = new String[fields.length];
        for (int lI = 0; lI < fields.length; lI++) {
            PropertyDescriptor pd = new PropertyDescriptor(fields[lI].getName(), lClass);
            Method getMethod = pd.getWriteMethod();
            writeMethods[lI] = getMethod.getName();
        }
        return writeMethods;
    }

    /**
     * 获取DBStockBean超类的所有的get方法
     */
    public static String[] getReadMethods() throws IntrospectionException, ClassNotFoundException {
        Class lClass = Class.forName("com.rogge.batch.common.bean.DBStockBean");
        Field[] fields = lClass.getSuperclass().getDeclaredFields();
        String[] readMethods = new String[fields.length];
        for (int lI = 0; lI < fields.length; lI++) {
            PropertyDescriptor pd = new PropertyDescriptor(fields[lI].getName(), lClass);
            Method getMethod = pd.getReadMethod();
            readMethods[lI] = getMethod.getName();
        }
        return readMethods;
    }

    /*public static void main(String[] args) throws IntrospectionException, ClassNotFoundException {
        for (String lS : getWriteMethods()) {
            System.out.println(lS);
        }

    }*/

}

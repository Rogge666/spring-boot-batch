package com.rogge.batch.common.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

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

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CSVStockBean implements Serializable{
    //代码
    private String code;
    //简称
    private String name;
    //日期
    private String date;
    //前收盘价(元)
    private String yEndPrice;
    //开盘价(元)
    private String startPrice;
    //最高价(元)
    private String highPrice;
    //最低价(元)
    private String lowPrice;
    //收盘价(元)
    private String endPrice;
    //成交量(股)
    private String volume;
    //成交金额(元)
    private String amount;
    //涨跌(元)
    private String rof;
    //涨跌幅(%)
    private String changePercent;
    //均价(元)
    private String averagePrice;
    //换手率(%)
    private String turnoverRate;
    //A股流通市值(元)
    private String acmv;
    //B股流通市值(元)
    private String bcmv;
    //总市值(元)
    private String amv;
    //A股流通股本(股)
    private String afoe;
    //B股流通股本(股)
    private String bfoe;
    //总股本(股)
    private String foe;
    //市盈率
    private String pe;
    //市净率
    private String pb;
    //市销率
    private String ptsr;
    //市现率
    private String pcf;
    //处理状态  ''未合并   1已合并   2已存数据库  3经过筛选的数据
    private String status;
}

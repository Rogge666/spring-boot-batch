package com.rogge.batch.common.listener;

import org.springframework.batch.item.file.FlatFileHeaderCallback;

import java.io.IOException;
import java.io.Writer;

/**
 * 文件头回调方法
 */
public class DefaultFlatFileHeaderCallback implements FlatFileHeaderCallback {

	@Override
	public void writeHeader(Writer writer) throws IOException {
		writer.write("代码,简称,日期,前收盘价(元),开盘价(元),最高价(元),最低价(元),收盘价(元),成交量(股),成交金额(元),涨跌(元),涨跌幅(%),均价(元),换手率(%),A股流通市值(元),B股流通市值(元),总市值(元),A股流通股本(股),B股流通股本(股),总股本(股),市盈率,市净率,市销率,市现率,处理状态");
	}

}

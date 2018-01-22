package com.rogge.batch.common.row_mapper;

import com.rogge.batch.common.bean.DBStockBean;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * [Description]
 * <p>
 * [How to use]
 * <p>
 * [Tips]
 *
 * @author Created by Rogge on 2018/1/22.
 * @since 1.0.0
 */
public class DbStockRowMapper implements RowMapper<DBStockBean>{

    /**
     * 以下对Bean的操作使用  {@link com.rogge.batch.common.sql.SQLUtils}生成
     */
    @Override
    public DBStockBean mapRow(ResultSet resultSet, int i) throws SQLException {
        DBStockBean lDBStockBean = new DBStockBean();
        lDBStockBean.setId(resultSet.getLong(1));
        lDBStockBean.setCode(resultSet.getString(2));
        lDBStockBean.setName(resultSet.getString(3));
        lDBStockBean.setDate(resultSet.getString(4));
        lDBStockBean.setYEndPrice(resultSet.getString(5));
        lDBStockBean.setStartPrice(resultSet.getString(6));
        lDBStockBean.setHighPrice(resultSet.getString(7));
        lDBStockBean.setLowPrice(resultSet.getString(8));
        lDBStockBean.setEndPrice(resultSet.getString(9));
        lDBStockBean.setVolume(resultSet.getString(10));
        lDBStockBean.setAmount(resultSet.getString(11));
        lDBStockBean.setRof(resultSet.getString(12));
        lDBStockBean.setChangePercent(resultSet.getString(13));
        lDBStockBean.setAveragePrice(resultSet.getString(14));
        lDBStockBean.setTurnoverRate(resultSet.getString(15));
        lDBStockBean.setAcmv(resultSet.getString(16));
        lDBStockBean.setBcmv(resultSet.getString(17));
        lDBStockBean.setAmv(resultSet.getString(18));
        lDBStockBean.setAfoe(resultSet.getString(19));
        lDBStockBean.setBfoe(resultSet.getString(20));
        lDBStockBean.setFoe(resultSet.getString(21));
        lDBStockBean.setPe(resultSet.getString(22));
        lDBStockBean.setPb(resultSet.getString(23));
        lDBStockBean.setPtsr(resultSet.getString(24));
        lDBStockBean.setPcf(resultSet.getString(25));
        lDBStockBean.setStatus(resultSet.getString(26));
        return lDBStockBean;
    }
}

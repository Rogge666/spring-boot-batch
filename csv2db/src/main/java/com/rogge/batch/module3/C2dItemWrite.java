package com.rogge.batch.module3;

import com.rogge.batch.common.bean.CSVStockBean;
import com.rogge.batch.common.bean.DBStockBean;
import com.rogge.batch.common.sql.SQLUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

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

@Component
@Slf4j
public class C2dItemWrite implements ItemWriter<CSVStockBean> {

    @Resource
    private JdbcTemplate jdbcTemplate;

    /**
     * 以下对Bean的操作使用  {@link com.rogge.batch.common.sql.SQLUtils}生成
     */
    @Override
    public void write(List<? extends CSVStockBean> list) throws Exception {
        long startTime = System.currentTimeMillis();
        if (null != list && list.size() > 0) {
            for (CSVStockBean lCSVStockBean : list) {
                List<DBStockBean> lDBStockBeans = jdbcTemplate.query(SQLUtils.GET_SQL, new Object[]{lCSVStockBean.getCode(), lCSVStockBean.getDate()},
                        (resultSet, i) -> {
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
                        });
                //如果写入之前查询到数据库已存在该条记录则更新  反之插入
                if (lDBStockBeans != null && lDBStockBeans.size() > 0) {
                    for (int lI = 0; lI < lDBStockBeans.size(); lI++) {
                        DBStockBean lDBStockBean = lDBStockBeans.get(lI);
                        //重复数据只保留一条
                        if (lI == 0) {
                            int updateResult = jdbcTemplate.update(SQLUtils.UPDATE_SQL,
                                    lDBStockBean.getCode(), lDBStockBean.getName(), lDBStockBean.getDate(),
                                    lDBStockBean.getYEndPrice(), lDBStockBean.getStartPrice(), lDBStockBean.getHighPrice(),
                                    lDBStockBean.getLowPrice(), lDBStockBean.getEndPrice(), lDBStockBean.getVolume(), lDBStockBean.getAmount(),
                                    lDBStockBean.getRof(), lDBStockBean.getChangePercent(), lDBStockBean.getAveragePrice(),
                                    lDBStockBean.getTurnoverRate(), lDBStockBean.getAcmv(), lDBStockBean.getBcmv(), lDBStockBean.getAmv(),
                                    lDBStockBean.getAfoe(), lDBStockBean.getBfoe(), lDBStockBean.getFoe(), lDBStockBean.getPe(),
                                    lDBStockBean.getPb(), lDBStockBean.getPtsr(), lDBStockBean.getPcf(), lDBStockBean.getStatus(), lDBStockBean.getId());
                            if (updateResult != 1) {
                                log.error("更新失败，数据为" + lDBStockBean.toString());
                            }
                        } else {
                            int deleteResult = jdbcTemplate.update(SQLUtils.DELETE_SQL, lDBStockBean.getId());
                            if (deleteResult != 1) {
                                log.error("删除失败，数据为" + lDBStockBean.toString());
                            }
                        }
                    }
                } else {
                    int insertResult = jdbcTemplate.update(SQLUtils.INSERT_SQL,
                            lCSVStockBean.getCode(), lCSVStockBean.getName(), lCSVStockBean.getDate(),
                            lCSVStockBean.getYEndPrice(), lCSVStockBean.getStartPrice(), lCSVStockBean.getHighPrice(),
                            lCSVStockBean.getLowPrice(), lCSVStockBean.getEndPrice(), lCSVStockBean.getVolume(), lCSVStockBean.getAmount(),
                            lCSVStockBean.getRof(), lCSVStockBean.getChangePercent(), lCSVStockBean.getAveragePrice(),
                            lCSVStockBean.getTurnoverRate(), lCSVStockBean.getAcmv(), lCSVStockBean.getBcmv(), lCSVStockBean.getAmv(),
                            lCSVStockBean.getAfoe(), lCSVStockBean.getBfoe(), lCSVStockBean.getFoe(), lCSVStockBean.getPe(),
                            lCSVStockBean.getPb(), lCSVStockBean.getPtsr(), lCSVStockBean.getPcf(), lCSVStockBean.getStatus());
                    if (insertResult != 1) {
                        log.error("插入失败，数据为" + lCSVStockBean.toString());
                    }
                }
            }
        }
        long endTime = System.currentTimeMillis();
        log.info("插入" + list.size() + "条数据需要" + (endTime - startTime) + "ms");
    }

}

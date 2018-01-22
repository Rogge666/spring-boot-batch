package com.rogge.batch.module1;

import com.rogge.batch.common.bean.DBStockBean;
import com.rogge.batch.common.row_mapper.DbStockRowMapper;
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
public class D2dItemWrite implements ItemWriter<DBStockBean> {

    @Resource
    private JdbcTemplate jdbcTemplate;

    /**
     * 以下对Bean的操作使用  {@link SQLUtils}生成
     */
    @Override
    public void write(List<? extends DBStockBean> list) throws Exception {
        long startTime = System.currentTimeMillis();
        if (null != list && list.size() > 0) {
            for (DBStockBean dBStockBean : list) {
                List<DBStockBean> lDBStockBeans = jdbcTemplate.query(SQLUtils.GET_STOCK_EXCEPT_SQL, new Object[]{dBStockBean.getCode(), dBStockBean.getDate()}, new DbStockRowMapper());
                //如果写入之前查询到数据库已存在该条记录则更新  反之插入
                if (lDBStockBeans != null && lDBStockBeans.size() > 0) {
                    for (int lI = 0; lI < lDBStockBeans.size(); lI++) {
                        DBStockBean lDBStockBean = lDBStockBeans.get(lI);
                        //重复数据只保留一条
                        if (lI == 0) {
                            int updateResult = jdbcTemplate.update(SQLUtils.UPDATE_STOCK_EXCEPT_SQL,
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
                            int deleteResult = jdbcTemplate.update(SQLUtils.DELETE_STOCK_EXCEPT_SQL, lDBStockBean.getId());
                            if (deleteResult != 1) {
                                log.error("删除失败，数据为" + lDBStockBean.toString());
                            }
                        }
                    }
                } else {
                    int insertResult = jdbcTemplate.update(SQLUtils.INSERT_STOCK_EXCEPT_SQL,
                            dBStockBean.getCode(), dBStockBean.getName(), dBStockBean.getDate(),
                            dBStockBean.getYEndPrice(), dBStockBean.getStartPrice(), dBStockBean.getHighPrice(),
                            dBStockBean.getLowPrice(), dBStockBean.getEndPrice(), dBStockBean.getVolume(), dBStockBean.getAmount(),
                            dBStockBean.getRof(), dBStockBean.getChangePercent(), dBStockBean.getAveragePrice(),
                            dBStockBean.getTurnoverRate(), dBStockBean.getAcmv(), dBStockBean.getBcmv(), dBStockBean.getAmv(),
                            dBStockBean.getAfoe(), dBStockBean.getBfoe(), dBStockBean.getFoe(), dBStockBean.getPe(),
                            dBStockBean.getPb(), dBStockBean.getPtsr(), dBStockBean.getPcf(), dBStockBean.getStatus());
                    if (insertResult != 1) {
                        log.error("插入失败，数据为" + dBStockBean.toString());
                    }
                }
            }
        }
        long endTime = System.currentTimeMillis();
        log.info("插入" + list.size() + "条数据需要" + (endTime - startTime) + "ms");
    }

}

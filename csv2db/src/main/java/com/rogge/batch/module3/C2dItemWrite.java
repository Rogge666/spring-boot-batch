package com.rogge.batch.module3;

import com.rogge.batch.common.bean.CSVStockBean;
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
public class C2dItemWrite implements ItemWriter<CSVStockBean> {

    @Resource
    private JdbcTemplate jdbcTemplate;

    /**
     * 以下对Bean的操作使用  {@link com.rogge.batch.common.sql.SQLUtils}生成
     */
    @Override
    public void write(List<? extends CSVStockBean> list) throws Exception {
        if (null != list && list.size() > 0) {
            long startTime = System.currentTimeMillis();
            for (CSVStockBean lCSVStockBean : list) {
                List<DBStockBean> lDBStockBeans = jdbcTemplate.query(SQLUtils.GET_STOCK_SQL, new Object[]{lCSVStockBean.getCode(), lCSVStockBean.getDate()}, new DbStockRowMapper());
                //如果写入之前查询到数据库已存在该条记录则更新  反之插入
                if (lDBStockBeans != null && lDBStockBeans.size() > 0) {
                    for (int lI = 0; lI < lDBStockBeans.size(); lI++) {
                        DBStockBean lDBStockBean = lDBStockBeans.get(lI);
                        //重复数据只保留一条
                        if (lI == 0) {
                            int updateResult = jdbcTemplate.update(SQLUtils.UPDATE_STOCK_SQL,
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
                            int deleteResult = jdbcTemplate.update(SQLUtils.DELETE_STOCK_SQL, lDBStockBean.getId());
                            if (deleteResult != 1) {
                                log.error("删除失败，数据为" + lDBStockBean.toString());
                            }
                        }
                    }
                } else {
                    int insertResult = jdbcTemplate.update(SQLUtils.INSERT_STOCK_SQL,
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

           /* List<Object[]> lObjects = new ArrayList<>();
            for (CSVStockBean lCSVStockBean : list) {
                Object[] lObject = {lCSVStockBean.getCode(), lCSVStockBean.getName(),
                        lCSVStockBean.getDate(), lCSVStockBean.getYEndPrice(),
                        lCSVStockBean.getStartPrice(), lCSVStockBean.getHighPrice(),
                        lCSVStockBean.getLowPrice(), lCSVStockBean.getEndPrice(),
                        lCSVStockBean.getVolume(), lCSVStockBean.getAmount(),
                        lCSVStockBean.getRof(), lCSVStockBean.getChangePercent(),
                        lCSVStockBean.getAveragePrice(), lCSVStockBean.getTurnoverRate(),
                        lCSVStockBean.getAcmv(), lCSVStockBean.getBcmv(),
                        lCSVStockBean.getAmv(), lCSVStockBean.getAfoe(),
                        lCSVStockBean.getBfoe(), lCSVStockBean.getFoe(),
                        lCSVStockBean.getPe(), lCSVStockBean.getPb(), lCSVStockBean.getPtsr(),
                        lCSVStockBean.getPcf(), lCSVStockBean.getStatus()};
                lObjects.add(lObject);
            }
            jdbcTemplate.batchUpdate(SQLUtils.INSERT_STOCK_SQL, lObjects);*/
            long endTime = System.currentTimeMillis();
            log.info("插入" + list.size() + "条数据需要" + (endTime - startTime) + "ms");
        }
    }


    /**
     * for循环插入耗时
    *插入1000条数据需要293ms
     插入1000条数据需要212ms
     插入1000条数据需要219ms
     插入1000条数据需要214ms
     插入1000条数据需要206ms
     插入1000条数据需要232ms
     插入1000条数据需要231ms
     插入199条数据需要38ms
    * */

    /**
     * batchUpdate   耗时
     插入1000条数据需要155ms
     插入1000条数据需要121ms
     插入1000条数据需要123ms
     插入1000条数据需要127ms
     插入1000条数据需要121ms
     插入1000条数据需要125ms
     插入1000条数据需要132ms
     插入199条数据需要37ms
     * */

}

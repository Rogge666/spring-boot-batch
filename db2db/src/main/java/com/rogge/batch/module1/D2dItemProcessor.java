package com.rogge.batch.module1;

import com.rogge.batch.common.bean.DBStockBean;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

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
public class D2dItemProcessor implements ItemProcessor<DBStockBean, DBStockBean> {

    @Override
    public DBStockBean process(DBStockBean dbStockBean) throws Exception {
        if (dbStockBean.getCode().startsWith("60")) {
            dbStockBean.setStatus("3");
//            System.out.println(dbStockBean.toString());
            return dbStockBean;
        }
        return null;
    }
}

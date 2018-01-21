package com.rogge.batch.module1;

import com.rogge.batch.common.bean.CSVStockBean;
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
public class CsvItemProcessor implements ItemProcessor<CSVStockBean, CSVStockBean> {

    @Override
    public CSVStockBean process(CSVStockBean CSVStockBean) throws Exception {
        return null;
    }
}

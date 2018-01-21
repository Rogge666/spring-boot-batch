package com.rogge.batch.module4;

import com.rogge.batch.common.bean.CSVStockBean;
import com.rogge.batch.common.listener.DefaultFlatFileHeaderCallback;
import com.rogge.batch.common.utils.BeanUtils;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

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
public class C2cItemWrite implements ItemWriter<CSVStockBean> {

    @Override
    public void write(List<? extends CSVStockBean> list) throws Exception {
        if (list != null && list.size() > 0) {
            FlatFileItemWriter<CSVStockBean> lItemWriter = new FlatFileItemWriter<>();
            DelimitedLineAggregator<CSVStockBean> lLineAggregator = new DelimitedLineAggregator<>();
            BeanWrapperFieldExtractor<CSVStockBean> lFieldExtractor = new BeanWrapperFieldExtractor<>();
            lFieldExtractor.setNames(BeanUtils.getDeclaredFields());
            lLineAggregator.setFieldExtractor(lFieldExtractor);
            //true 往后添加写入  false 覆盖写入
            lItemWriter.setAppendAllowed(true);
            lItemWriter.setEncoding("UTF-8");
            lItemWriter.setResource(new ClassPathResource("output_csv/all.csv"));
            lItemWriter.setLineAggregator(lLineAggregator);
            //文件头回调    这里用来加上标题字段
            lItemWriter.setHeaderCallback(new DefaultFlatFileHeaderCallback());
            lItemWriter.open(new ExecutionContext());
            lItemWriter.write(list);
            lItemWriter.close();
        }
    }
}

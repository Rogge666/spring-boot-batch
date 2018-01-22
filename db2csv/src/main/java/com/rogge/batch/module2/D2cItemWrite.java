package com.rogge.batch.module2;

import com.rogge.batch.common.bean.CSVStockBean;
import com.rogge.batch.common.listener.DefaultFlatFileHeaderCallback;
import com.rogge.batch.common.sql.SQLUtils;
import com.rogge.batch.common.utils.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
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
@Slf4j
public class D2cItemWrite implements ItemWriter<CSVStockBean> {

    /**
     * 以下对Bean的操作使用  {@link SQLUtils}生成
     */
    @Override
    public void write(List<? extends CSVStockBean> list) throws Exception {
        if (list != null && list.size() > 0) {
            long startTime = System.currentTimeMillis();
            FlatFileItemWriter<CSVStockBean> lItemWriter = new FlatFileItemWriter<>();
            DelimitedLineAggregator<CSVStockBean> lLineAggregator = new DelimitedLineAggregator<>();
            BeanWrapperFieldExtractor<CSVStockBean> lFieldExtractor = new BeanWrapperFieldExtractor<>();
            lFieldExtractor.setNames(BeanUtils.getDeclaredFields());
            lLineAggregator.setFieldExtractor(lFieldExtractor);
            //true 往后添加写入  false 覆盖写入
            lItemWriter.setAppendAllowed(true);
            lItemWriter.setEncoding("UTF-8");
            //输出到项目运行时的classPath下
//            lItemWriter.setResource(new ClassPathResource("output_csv/except.csv"));
            //输出项目的根路径下
//            lItemWriter.setResource(new FileSystemResource("output_csv/except.csv"));
            //输出到本机的某个位置
//            lItemWriter.setResource(new UrlResource("file:C:/Users/Administrator/Desktop/except.csv"));
            lItemWriter.setLineAggregator(lLineAggregator);
            //文件头回调    这里用来加上标题字段
            lItemWriter.setHeaderCallback(new DefaultFlatFileHeaderCallback());
            lItemWriter.open(new ExecutionContext());
            lItemWriter.write(list);
            lItemWriter.close();
            long endTime = System.currentTimeMillis();
            log.info("写入" + list.size() + "条数据到CSV需要" + (endTime - startTime) + "ms");
        }
    }

}

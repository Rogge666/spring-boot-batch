package com.rogge.batch.module2;

import com.rogge.batch.common.bean.DBStockBean;
import com.rogge.batch.common.listener.StepCheckingListener;
import com.rogge.batch.common.row_mapper.DbStockRowMapper;
import com.rogge.batch.common.sql.SQLUtils;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;


@Configuration
public class BatchConfiguration {

    @Resource
    private JobBuilderFactory jobBuilderFactory;

    @Resource
    private StepBuilderFactory stepBuilderFactory;

    @Resource
    private D2dItemProcessor mD2DItemProcessor;

    @Resource
    private DataSource mDataSource;

    @Bean
    public ItemReader<DBStockBean> getMultiResourceItemReader() throws Exception {
        JdbcCursorItemReader<DBStockBean> lItemReader = new JdbcCursorItemReader<>();
        lItemReader.setDataSource(mDataSource);
        lItemReader.setFetchSize(200);
        lItemReader.setRowMapper(new DbStockRowMapper());
        lItemReader.setSql(SQLUtils.GET_ALL_STOCK_EXCEPT_SQL);
        lItemReader.afterPropertiesSet();
        return lItemReader;
    }

    @Bean
    public ItemProcessor<DBStockBean, DBStockBean> itemProcessor() throws Exception {
        List<ItemProcessor<DBStockBean, DBStockBean>> delegates = new ArrayList<>();
        //ItemProcessor需要用注入的方式才能被Spring管理   不能使用new的形式
        delegates.add(mD2DItemProcessor);
        CompositeItemProcessor<DBStockBean, DBStockBean> compositeItemProcessor = new CompositeItemProcessor<>();
        compositeItemProcessor.setDelegates(delegates);
        compositeItemProcessor.afterPropertiesSet();
        return compositeItemProcessor;
    }

    @Bean
    public D2dItemWrite crmBatchWriter() {
        return new D2dItemWrite();
    }

    @Bean
    public Job readFromCsvJob() throws Exception {
        return this.jobBuilderFactory.get("d2cDataSendJob").start(chunkBasedStep()).build();
    }

    @Bean
    public TaskletStep chunkBasedStep() throws Exception {
        return stepBuilderFactory.get("asyncChunkBasedStep")
                //设置job结束之后是否重新开始
                .allowStartIfComplete(true)
                .listener(new StepCheckingListener())
                .<DBStockBean, DBStockBean>chunk(50)
                .reader(getMultiResourceItemReader())
                .processor(itemProcessor())
                .writer(crmBatchWriter())
                .build();
    }

}

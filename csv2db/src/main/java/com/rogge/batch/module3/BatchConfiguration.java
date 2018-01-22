package com.rogge.batch.module3;

import com.rogge.batch.common.bean.CSVStockBean;
import com.rogge.batch.common.listener.StepCheckingListener;
import com.rogge.batch.common.utils.BeanUtils;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Configuration
public class BatchConfiguration {

    @Resource
    private JobBuilderFactory jobBuilderFactory;

    @Resource
    private StepBuilderFactory stepBuilderFactory;

    @Resource
    private C2dItemProcessor mC2dItemProcessor;

    @Bean
    public ItemReader<CSVStockBean> getMultiResourceItemReader() throws ClassNotFoundException {
        FlatFileItemReader<CSVStockBean> lItemReader = new FlatFileItemReader<>();
        DefaultLineMapper<CSVStockBean> lLineMapper = new DefaultLineMapper<>();
        BeanWrapperFieldSetMapper<CSVStockBean> lBeanBeanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
        DelimitedLineTokenizer lDelimitedLineTokenizer = new DelimitedLineTokenizer();
        lBeanBeanWrapperFieldSetMapper.setTargetType(CSVStockBean.class);
        lDelimitedLineTokenizer.setNames(BeanUtils.getDeclaredFields());
        lLineMapper.setFieldSetMapper(lBeanBeanWrapperFieldSetMapper);
        lLineMapper.setLineTokenizer(lDelimitedLineTokenizer);
        lItemReader.setEncoding("UTF-8");
        lItemReader.setLineMapper(lLineMapper);
        //跳过第一行   第一行是标题
        lItemReader.setLinesToSkip(1);
        lItemReader.setResource(new ClassPathResource("/input_csv/all.csv"));
        return lItemReader;
    }

    @Bean
    public ItemProcessor<CSVStockBean, CSVStockBean> itemProcessor() throws Exception {
        List<ItemProcessor<CSVStockBean, CSVStockBean>> delegates = new ArrayList<>();
        //ItemProcessor需要用注入的方式才能被Spring管理   不能使用new的形式
        delegates.add(mC2dItemProcessor);
        CompositeItemProcessor<CSVStockBean, CSVStockBean> compositeItemProcessor = new CompositeItemProcessor<>();
        compositeItemProcessor.setDelegates(delegates);
        compositeItemProcessor.afterPropertiesSet();
        return compositeItemProcessor;
    }

    @Bean
    public C2dItemWrite crmBatchWriter() {
        return new C2dItemWrite();
    }

    @Bean
    public Job readFromCsvJob() throws Exception {
        return this.jobBuilderFactory.get("c2dDataSendJob").start(chunkBasedStep()).build();
    }

    @Bean
    public TaskletStep chunkBasedStep() throws Exception {
        return stepBuilderFactory.get("asyncChunkBasedStep")
                //设置job结束之后是否重新开始
                .allowStartIfComplete(true)
                .listener(new StepCheckingListener())
                .<CSVStockBean, CSVStockBean>chunk(1000)
                .reader(getMultiResourceItemReader())
                .processor(itemProcessor())
                .writer(crmBatchWriter())
                .build();
    }

}

package com.rogge.batch.module4;

import com.rogge.batch.common.bean.CSVStockBean;
import com.rogge.batch.common.listener.JobExecutionTimeListener;
import com.rogge.batch.common.listener.StepCheckingListener;
import com.rogge.batch.common.reader.ExtendedMultiResourceItemReader;
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
    private C2cItemProcessor mC2cItemProcessor;

    @Bean
    public ItemReader<CSVStockBean> getMultiResourceItemReader() throws ClassNotFoundException {
        ExtendedMultiResourceItemReader<CSVStockBean> lReader = new ExtendedMultiResourceItemReader<>();
        lReader.setResourcesLocationPattern("/input_csv/*.SH.CSV");
        //使用原生MultiResourceItemReader通配符不起作用  如想使用原生打开下面一行即可(可自己写一个文件名读取的方法把文件集合加入Resource[]中)
        //lReader.setResources(new org.springframework.core.io.Resource[]{new ClassPathResource("/input_csv/600000.SH.CSV")});
        FlatFileItemReader<CSVStockBean> lFlatFileItemReader = new FlatFileItemReader<>();
        DefaultLineMapper<CSVStockBean> lLineMapper = new DefaultLineMapper<>();
        BeanWrapperFieldSetMapper<CSVStockBean> lBeanBeanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
        DelimitedLineTokenizer lDelimitedLineTokenizer = new DelimitedLineTokenizer();
        lBeanBeanWrapperFieldSetMapper.setTargetType(CSVStockBean.class);
        lDelimitedLineTokenizer.setNames(BeanUtils.getDeclaredFields());
        lLineMapper.setFieldSetMapper(lBeanBeanWrapperFieldSetMapper);
        lLineMapper.setLineTokenizer(lDelimitedLineTokenizer);
        lFlatFileItemReader.setEncoding("GBK");
        lFlatFileItemReader.setLineMapper(lLineMapper);
        //跳过第一行   第一行是标题
        lFlatFileItemReader.setLinesToSkip(1);
        lReader.setDelegate(lFlatFileItemReader);
        return lReader;
    }

    @Bean
    public ItemProcessor<CSVStockBean, CSVStockBean> itemProcessor() throws Exception {
        List<ItemProcessor<CSVStockBean, CSVStockBean>> delegates = new ArrayList<>();
        //ItemProcessor需要用注入的方式才能被Spring管理   不能使用new的形式
        delegates.add(mC2cItemProcessor);
        CompositeItemProcessor<CSVStockBean, CSVStockBean> compositeItemProcessor = new CompositeItemProcessor<>();
        compositeItemProcessor.setDelegates(delegates);
        compositeItemProcessor.afterPropertiesSet();
        return compositeItemProcessor;
    }

    @Bean
    public C2cItemWrite c2cBatchWriter() {
        return new C2cItemWrite();
    }

    @Bean
    public Job readFromCsvJob() throws Exception {
        return this.jobBuilderFactory.get("c2cDataSendJob")
                .start(chunkBasedStep())
                .listener(new JobExecutionTimeListener())
                .build();
    }

    @Bean
    public TaskletStep chunkBasedStep() throws Exception {
        return stepBuilderFactory.get("asyncChunkBasedStep")
                //设置job结束之后是否重新开始
                .allowStartIfComplete(false)
                .listener(new StepCheckingListener())
                .<CSVStockBean, CSVStockBean>chunk(1000)
                .reader(getMultiResourceItemReader())
                .processor(itemProcessor())
                .writer(c2cBatchWriter())
                .build();
    }

}

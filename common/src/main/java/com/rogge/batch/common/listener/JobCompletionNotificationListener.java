package com.rogge.batch.common.listener;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {
    private static final Logger logger = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

    private static final String HEADER = "stock,open,close,low,high";

    private static final String LINE_DILM = ",";

    //@Autowired
    //private FxMarketPricesStore fxMarketPricesStore;

    // @Override
    /*public void afterJob(JobExecution jobExecution)
    {
        if(jobExecution.getStatus() == BatchStatus.COMPLETED)
        {
            logger.info("update the db result success!");
            Path path = Paths.get("price.csv");
            //Charset charset = Charset.forName("UTF-8");
            //String text = "d:";
            try
            {
                BufferedWriter fileWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path.toString()),"UTF-8"));
                fileWriter.write(HEADER);
                fileWriter.newLine();
                for(StockPriceDetails pd : fxMarketPricesStore.values())
                {
                    fileWriter.write(new StringBuilder().append(pd.getStock())
                            .append(LINE_DILM).append(pd.getOpen())
                            .append(LINE_DILM).append(pd.getClose())
                            .append(LINE_DILM).append(pd.getLow())
                            .append(LINE_DILM).append(pd.getHigh()).toString());
                    fileWriter.newLine();
                }
                
                
            }
            catch (Exception e)
            {
                logger.error("Fetal error: error occurred while writing {} file",path.getFileName());
            }
        }
    }*/

}

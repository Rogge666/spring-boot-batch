package com.rogge.batch.common.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;

/**
 * 类名称：RoggeChunkListener
 * 类描述：
 * 创建人：jiangdong_kzx
 * 创建时间：2018年1月6日 上午11:41:46
 * 修改人：jiangdong_kzx
 * 修改时间：2018年1月6日 上午11:41:46
 * 修改备注：
 */
@Slf4j
public class RoggeChunkListener implements ChunkListener {

    @Override
    public void beforeChunk(ChunkContext context) {
        log.info("开始执行CHUNK{}", context.getStepContext().getStepName());
    }

    @Override
    public void afterChunk(ChunkContext context) {
        log.info("结束执行CHUNK{}", context.getStepContext().getStepName());
    }

    @Override
    public void afterChunkError(ChunkContext context) {
        log.error("执行CHUNK{}时出现异常", context.getStepContext().getStepName());
    }
}
package com.rogge.batch.common.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;

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
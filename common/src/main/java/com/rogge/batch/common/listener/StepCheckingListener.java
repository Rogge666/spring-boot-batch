package com.rogge.batch.common.listener;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;

public class StepCheckingListener extends StepExecutionListenerSupport {

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        String exitCode = stepExecution.getExitStatus().getExitCode();
        if (!exitCode.equals(ExitStatus.FAILED.getExitCode())
                && stepExecution.getSkipCount() > 0) {
            return new ExitStatus("COMPLETED WITH SKIPS");
        } else if (!exitCode.equals(ExitStatus.FAILED.getExitCode())
                && stepExecution.getRollbackCount() > 0) {
            return new ExitStatus("COMPLETED WITH ROLLBACK");
        } else {
            return null;
        }
    }
}
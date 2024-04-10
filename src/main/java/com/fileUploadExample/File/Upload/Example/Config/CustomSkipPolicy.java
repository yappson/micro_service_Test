package com.fileUploadExample.File.Upload.Example.Config;

import com.fileUploadExample.File.Upload.Example.Config.ProcessingException;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.scope.context.StepSynchronizationManager;
import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.batch.repeat.exception.ExceptionHandler;
import org.springframework.batch.repeat.policy.SimpleCompletionPolicy;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomSkipPolicy implements SkipPolicy {

    @Override
    public boolean shouldSkip(Throwable t, long skipCount) throws SkipLimitExceededException {
        if (t instanceof ProcessingException) {
            JobExecution jobExecution = StepSynchronizationManager.getContext().getStepExecution().getJobExecution();
            if (jobExecution != null) {
                jobExecution.addFailureException(t);
            }
            return true; // Skip the item
        }

        // Continue processing without skipping for other exceptions
        return false;
    }
}
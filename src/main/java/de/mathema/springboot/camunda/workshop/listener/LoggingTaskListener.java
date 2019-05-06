package de.mathema.springboot.camunda.workshop.listener;

import static de.mathema.springboot.camunda.workshop.utils.LoggingUtils.logInfo;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * LoggingTaskListener.
 *
 * @author wflat
 */
@Component
public class LoggingTaskListener implements TaskListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingTaskListener.class);

    @Override
    public void notify(final DelegateTask delegateTask) {
        logInfo(LOGGER, delegateTask);
    }
}

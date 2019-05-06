package de.mathema.springboot.camunda.workshop.delegate;

import static de.mathema.springboot.camunda.workshop.utils.LoggingUtils.logInfo;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * LoggerDelegate.
 *
 * @author wflat
 */
@Component
public class LoggingDelegate implements JavaDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingDelegate.class);

    @Override
    public void execute(final DelegateExecution execution) {
        logInfo(LOGGER, execution);
    }
}

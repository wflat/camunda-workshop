package de.mathema.springboot.camunda.workshop.delegate.cfd;

import static de.mathema.springboot.camunda.workshop.utils.LoggingUtils.logInfo;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * ProzessVariablenSetzenDelegate.
 *
 * @author wflat
 */
@Component
public class ProzessVariablenSetzenDelegate implements JavaDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProzessVariablenSetzenDelegate.class);

    @Value("${cfd.process.dreissig-sekunden-timer}")
    private String dreissigSekundenTimer;

    @Value("${cfd.process.ein-tag-timer}")
    private String einTagTimer;

    @Override
    public void execute(final DelegateExecution execution) {
        // TODO: Timer Variablen setzen
        logInfo(LOGGER, execution);
    }
}

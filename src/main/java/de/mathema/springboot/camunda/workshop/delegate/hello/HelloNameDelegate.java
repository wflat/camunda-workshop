package de.mathema.springboot.camunda.workshop.delegate.hello;

import static de.mathema.springboot.camunda.workshop.utils.LoggingUtils.logInfo;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * HelloWorldDelegate.
 *
 * @author wflat
 */
@Component
public class HelloNameDelegate implements JavaDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelloNameDelegate.class);

    @Override
    public void execute(final DelegateExecution execution) {
        // Prozess-Variable auslesen
        final String deinName = (String) execution.getVariable("DeinName");

        // Verarbeitung
        final String halloName = String.format("Hallo %s!!!", deinName);

        // Prozess-Variable setzen
        execution.setVariable("HalloName", halloName);

        logInfo(LOGGER, execution);
    }
}

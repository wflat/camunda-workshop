package de.mathema.springboot.camunda.workshop.jms.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ErrorHandler;

/**
 * JmsErrorHandler.
 *
 * @author wflat
 */
@Component
public class JmsErrorHandler implements ErrorHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(JmsErrorHandler.class);

    @Override
    public void handleError(final Throwable t) {
        LOGGER.error("Exception was thrown: {}", t.getMessage(), t);
    }
}

package de.mathema.springboot.camunda.workshop.jms;

import static de.mathema.springboot.camunda.workshop.jms.message.JmsMessageHeadersUtils.getCorrelationId;

import java.util.Optional;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.Execution;
import org.camunda.bpm.engine.runtime.MessageCorrelationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import de.mathema.springboot.camunda.workshop.model.KontoumsatzBestaetigung;

/**
 * PartnerkontoBestaetigungEmpfangen.
 *
 * @author wflat
 */
@Component
public class PartnerkontoBestaetigungEmpfangen {

    private static final Logger LOGGER = LoggerFactory.getLogger(PartnerkontoBestaetigungEmpfangen.class);

    @Autowired
    private RuntimeService runtimeService;

    @JmsListener(destination = "${cfd.queue.partnerkonto-empfangen}", containerFactory = "jmsListenerContainerFactory")
    public void bestaetigungEmpfangen(final Message<KontoumsatzBestaetigung> msg) {
        LOGGER.info("Umsatz-Bestaetigung mit correlationID {} empfangen", getCorrelationId(msg.getHeaders()));

        // TODO: MessageEvengt an die Prozessinstanz senden
    }

    private String getProcessId(final MessageCorrelationResult correlationResult) {
        return Optional.ofNullable(correlationResult).map(MessageCorrelationResult::getExecution).
                map(Execution::getProcessInstanceId).orElse(null);
    }
}

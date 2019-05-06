package de.mathema.springboot.camunda.workshop.jms;

import static de.mathema.springboot.camunda.workshop.jms.message.JmsMessageHeadersUtils.getCorrelationId;
import static org.camunda.bpm.engine.variable.Variables.SerializationDataFormats.JSON;
import static org.camunda.bpm.engine.variable.Variables.objectValue;

import java.util.Optional;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.Execution;
import org.camunda.bpm.engine.runtime.MessageCorrelationResult;
import org.camunda.bpm.engine.variable.value.ObjectValue;
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
        final KontoumsatzBestaetigung bestaetigung = msg.getPayload();
        final ObjectValue bestaetigungObjectValue = objectValue(bestaetigung).serializationDataFormat(JSON).create();
        final String correlationId = getCorrelationId(msg.getHeaders());

        LOGGER.info("Umsatz-Bestaetigung mit correlationID {} empfangen", correlationId);

        final MessageCorrelationResult correlationResult = runtimeService
                .createMessageCorrelation("Message_BestaetigungAngekommen")
                .processInstanceBusinessKey(correlationId)
                .setVariable("bestaetigung", bestaetigungObjectValue)
                .correlateWithResult();

        LOGGER.info("Prozess mit ID {} aufgeweckt", getProcessId(correlationResult));
    }

    private String getProcessId(final MessageCorrelationResult correlationResult) {
        return Optional.ofNullable(correlationResult).map(MessageCorrelationResult::getExecution).
                map(Execution::getProcessInstanceId).orElse(null);
    }
}

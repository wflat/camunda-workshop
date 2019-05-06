package de.mathema.springboot.camunda.workshop.jms;

import static de.mathema.springboot.camunda.workshop.jms.message.JmsMessageHeadersUtils.getMessageId;
import static org.camunda.bpm.engine.variable.Variables.SerializationDataFormats.JSON;
import static org.camunda.bpm.engine.variable.Variables.objectValue;

import java.util.Optional;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import de.mathema.springboot.camunda.workshop.model.Kontoumsatz;

/**
 * NeuenUmsatzEmpfangen.
 *
 * @author wflat
 */
@Component
public class NeuenUmsatzEmpfangen {

    private static final Logger LOGGER = LoggerFactory.getLogger(NeuenUmsatzEmpfangen.class);

    @Autowired
    private RuntimeService runtimeService;

    @JmsListener(destination = "${cfd.queue.neuer-umsatz}", containerFactory = "jmsListenerContainerFactory")
    public void neuerUmsatzEmpfangen(final Message<Kontoumsatz> msg) {
        LOGGER.info("Neuen Umsatz empfangen mit messageId {}", getMessageId(msg.getHeaders()));

        // TODO: Neuen Prozess starten
    }
}

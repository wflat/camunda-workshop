package de.mathema.springboot.camunda.workshop.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.mathema.springboot.camunda.workshop.jms.message.JmsMessage;
import de.mathema.springboot.camunda.workshop.jms.message.JmsMessageSender;
import de.mathema.springboot.camunda.workshop.model.Kontoumsatz;

/**
 * UmsatzAnsPartnerkontoSenden.
 *
 * @author wflat
 */
@Component
public class UmsatzAnsPartnerkontoSenden {

    private static final Logger LOG = LoggerFactory.getLogger(UmsatzAnsPartnerkontoSenden.class);

    @Autowired
    private JmsMessageSender jmsMessageSender;

    public void sendeUmsatz(final Kontoumsatz kontoumsatz, final String correlationId) {
        // TODO: Umsatz an das Partnerkonto

        LOG.info("Umsatz mit correlationId {} ans Partnerkonto gesendet", correlationId);
    }
}

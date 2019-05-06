package de.mathema.springboot.camunda.workshop.jms.message;

import javax.jms.JMSException;
import javax.jms.Message;

/**
 * JmsMessage.
 *
 * @author wflat
 */
public class JmsMessage {

    private final Message message;

    public JmsMessage(final Message message) {
        this.message = message;
    }

    public String getMessageId() {
        try {
            return this.message.getJMSMessageID();
        } catch (final JMSException e) {
            throw new RuntimeException(e);
        }
    }

    public String getCorrelationId() {
        try {
            return this.message.getJMSCorrelationID();
        } catch (final JMSException e) {
            throw new RuntimeException(e);
        }
    }
}
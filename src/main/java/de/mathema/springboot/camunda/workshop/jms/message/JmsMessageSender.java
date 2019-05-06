package de.mathema.springboot.camunda.workshop.jms.message;

import java.util.Objects;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.support.converter.MessageConverter;

/**
 * Encapsulates {@code JmsTemplate} and {@code queue-name}. <br/>
 * Provides access to the sent JMS message.
 *
 * @author wflat
 */
public class JmsMessageSender {

    private JmsTemplate jmsTemplate;

    private String queueName;

    /**
     * @param jmsTemplate
     * @param queueName
     */
    public JmsMessageSender(final JmsTemplate jmsTemplate, final String queueName) {
        Objects.requireNonNull(jmsTemplate);
        Objects.requireNonNull(queueName);

        this.jmsTemplate = jmsTemplate;
        this.queueName = queueName;
    }

    /**
     * @param objToSend
     * @return The sent JMS message.
     * @throws IllegalStateException @see {@link JmsMessageCreator#getMessage()}
     */
    public JmsMessage convertAndSend(final Object objToSend) {
        return convertAndSend(objToSend, null);
    }

    /**
     * @param objToSend
     * @param correlationId
     * @return The sent JMS message.
     * @throws IllegalStateException @see {@link JmsMessageCreator#getMessage()}
     */
    public JmsMessage convertAndSend(final Object objToSend, final String correlationId) {
        final JmsMessageCreator jmsMessageCreator = new JmsMessageCreator(objToSend, correlationId, this.jmsTemplate.getMessageConverter());
        this.jmsTemplate.send(this.queueName, jmsMessageCreator);
        return new JmsMessage(jmsMessageCreator.getMessage());
    }

    /**
     * @return {@code queueName}
     */
    public String getQueueName() {
        return queueName;
    }

    /**
     * @return {@code jmsTemplate}
     */
    public JmsTemplate getJmsTemplate() {
        return jmsTemplate;
    }


    /**
     * Enables the access to the generated JMS message object.
     *
     * @author wflat
     */
    private static class JmsMessageCreator implements MessageCreator {

        private Object objToSend;

        private MessageConverter messageConverter;

        private String correlationId;

        private Message message;

        /**
         * @param objToSend
         * @param correlationId
         * @param messageConverter
         */
        public JmsMessageCreator(final Object objToSend, final String correlationId, final MessageConverter messageConverter) {
            Objects.requireNonNull(objToSend);
            Objects.requireNonNull(messageConverter);

            this.objToSend = objToSend;
            this.correlationId = correlationId;
            this.messageConverter = messageConverter;
        }

        @Override
        public Message createMessage(final Session session) throws JMSException {
            this.message = messageConverter.toMessage(this.objToSend, session);
            if (StringUtils.isNotEmpty(this.correlationId)) {
                this.message.setJMSCorrelationID(this.correlationId);
            }
            return this.message;
        }

        /**
         * @return Created JMSMessage if available.
         * @throws IllegalStateException If no message available.
         */
        public Message getMessage() {
            if (this.message == null) {
                throw new IllegalStateException("No JMS message available.");
            }
            return this.message;
        }
    }
}

package de.mathema.springboot.camunda.workshop.jms.config;

import java.util.HashMap;
import java.util.Map;

import javax.jms.ConnectionFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import de.mathema.springboot.camunda.workshop.jms.error.JmsErrorHandler;
import de.mathema.springboot.camunda.workshop.jms.message.JmsMessageSender;
import de.mathema.springboot.camunda.workshop.model.Kontoumsatz;
import de.mathema.springboot.camunda.workshop.model.KontoumsatzBestaetigung;

/**
 * JmsConfig.
 *
 * @author wflat
 */
@Configuration
@EnableTransactionManagement
@EnableJms
public class JmsConfig {

    @Value("${cfd.queue.partnerkonto-senden}")
    private String partnerkontoSendenQueue;

    @Bean
    public JmsMessageSender jmsMessageSender(final JmsTemplate jmsTemplate) {
        return new JmsMessageSender(jmsTemplate, partnerkontoSendenQueue);
    }

    @Bean
    public JmsTemplate jmsTemplate(final ConnectionFactory jmsConnectionFactory,
                                   final MessageConverter messageConverter) {
        final JmsTemplate jmsTemplate = new JmsTemplate(jmsConnectionFactory);
        jmsTemplate.setMessageConverter(messageConverter);
        return jmsTemplate;
    }

    @Bean
    public JmsListenerContainerFactory jmsListenerContainerFactory(final DefaultJmsListenerContainerFactoryConfigurer configurer,
                                                                   final ConnectionFactory connectionFactory,
                                                                   final JmsErrorHandler errorHandler,
                                                                   final MessageConverter messageConverter) {
        final DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        factory.setErrorHandler(errorHandler);
        factory.setMessageConverter(messageConverter);
        return factory;
    }

    @Bean
    public MessageConverter messageConverter() {
        final MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("cfdContentType");

        final Map<String, Class<?>> typeIdMappings = new HashMap<>();
        typeIdMappings.put(Kontoumsatz.class.getSimpleName(), Kontoumsatz.class);
        typeIdMappings.put(KontoumsatzBestaetigung.class.getSimpleName(), KontoumsatzBestaetigung.class);
        converter.setTypeIdMappings(typeIdMappings);
        return converter;
    }
}

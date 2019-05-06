package de.mathema.springboot.camunda.workshop.config;

import static org.mockito.Mockito.mock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import de.mathema.springboot.camunda.workshop.delegate.hello.HelloNameDelegate;
import de.mathema.springboot.camunda.workshop.jms.UmsatzAnsPartnerkontoSenden;
import de.mathema.springboot.camunda.workshop.jms.message.JmsMessageSender;
import de.mathema.springboot.camunda.workshop.delegate.LoggingDelegate;
import de.mathema.springboot.camunda.workshop.delegate.cfd.UmsatzSendenDelegate;
import de.mathema.springboot.camunda.workshop.listener.LoggingTaskListener;

/**
 * CfdAppTestConfig.
 *
 * @author wflat
 */
@Configuration
@ComponentScan(basePackageClasses = {UmsatzSendenDelegate.class, HelloNameDelegate.class})
public class AppTestConfig {

    @Bean
    public LoggingTaskListener loggingTaskListener() {
        return new LoggingTaskListener();
    }

    @Bean
    public LoggingDelegate loggingDelegate() {
        return new LoggingDelegate();
    }

    @Bean
    public UmsatzAnsPartnerkontoSenden umsatzAnsPartnerkontoSenden() {
        return mock(UmsatzAnsPartnerkontoSenden.class);
    }

    @Bean
    public JmsMessageSender jmsMessageSender() {
        return mock(JmsMessageSender.class);
    }
}

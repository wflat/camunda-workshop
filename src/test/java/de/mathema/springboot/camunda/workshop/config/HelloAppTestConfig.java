package de.mathema.springboot.camunda.workshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.mathema.springboot.camunda.workshop.delegate.LoggingDelegate;
import de.mathema.springboot.camunda.workshop.delegate.hello.HelloNameDelegate;
import de.mathema.springboot.camunda.workshop.listener.LoggingTaskListener;

/**
 * HelloAppTestConfig.
 *
 * @author wflat
 */
@Configuration
public class HelloAppTestConfig {

    @Bean
    public LoggingTaskListener loggingTaskListener() {
        return new LoggingTaskListener();
    }

    @Bean
    public LoggingDelegate loggingDelegate() {
        return new LoggingDelegate();
    }

    @Bean
    public HelloNameDelegate helloNameDelegate() {
        return new HelloNameDelegate();
    }
}

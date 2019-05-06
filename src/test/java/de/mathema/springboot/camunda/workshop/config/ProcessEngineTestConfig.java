/*
 * Camunda Workshop
 *
 * (C) 2017 PENTASYS AG
 */
package de.mathema.springboot.camunda.workshop.config;

import org.camunda.bpm.engine.ManagementService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngineConfiguration;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.impl.cfg.StandaloneInMemProcessEngineConfiguration;
import org.camunda.bpm.engine.spring.ProcessEngineFactoryBean;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.spin.plugin.impl.SpinProcessEnginePlugin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ProcessEngineTestConfig.
 *
 * @author wflat
 */
@Configuration
public class ProcessEngineTestConfig {

    @Bean
    public ProcessEngineFactoryBean processEngineFactoryBean() {
        final StandaloneInMemProcessEngineConfiguration configuration = new StandaloneInMemProcessEngineConfiguration();
        configuration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE)
                .setJobExecutorActivate(false);
        configuration.getProcessEnginePlugins().add(new SpinProcessEnginePlugin());
        final ProcessEngineFactoryBean result = new ProcessEngineFactoryBean();
        result.setProcessEngineConfiguration(configuration);
        return result;
    }

    @Bean
    public ProcessEngineRule processEngineRule(final ProcessEngine processEngine) {
        return new ProcessEngineRule(processEngine);
    }

    @Bean
    public RuntimeService runtimeService(final ProcessEngine processEngine) {
        return processEngine.getRuntimeService();
    }

    @Bean
    public TaskService taskService(final ProcessEngine processEngine) {
        return processEngine.getTaskService();
    }

    @Bean
    public ManagementService managementService(final ProcessEngine processEngine) {
        return processEngine.getManagementService();
    }
}

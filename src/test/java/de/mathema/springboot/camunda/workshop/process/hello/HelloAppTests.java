package de.mathema.springboot.camunda.workshop.process.hello;

import static org.assertj.core.data.MapEntry.entry;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.assertThat;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.complete;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.task;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.withVariables;

import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import de.mathema.springboot.camunda.workshop.config.ProcessEngineTestConfig;
import de.mathema.springboot.camunda.workshop.config.HelloAppTestConfig;

import io.digitalstate.camunda.coverage.bpmn.CoverageBuilderJavaBridge;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ProcessEngineTestConfig.class, HelloAppTestConfig.class})
public class HelloAppTests {

    @Rule
    @Autowired
    public ProcessEngineRule processEngineRule;

    private CoverageBuilderJavaBridge coverageBuilder;

    @Before
    public void setUp() {
        coverageBuilder = new CoverageBuilderJavaBridge();
    }

    @After
    public void tearDown() {
        coverageBuilder.saveCoverageSnapshots();
        coverageBuilder.getCoverageSnapshots().clear();
    }

    @Test
    @Deployment(resources = "hello-app.bpmn")
    public void helloApp_happyPath() {
        final ProcessInstance processInstance = processEngineRule.getRuntimeService().startProcessInstanceByKey("Process_HelloApp");
        assertThat(processInstance).isNotNull();

        assertThat(processInstance).task().hasName("Enter Name"); // User-Task
        complete(task(), withVariables("DeinName", "Test"));      // Name eingeben

        assertThat(processInstance).isEnded();
        assertThat(processInstance).variables().
                contains(entry("DeinName", "Test"), entry("HalloName", "Hallo Test!!!"));

        coverageBuilder.coverageSnapshot(processInstance);
    }
}

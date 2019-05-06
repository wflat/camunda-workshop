package de.mathema.springboot.camunda.workshop.process.cfd;

import static de.mathema.springboot.camunda.workshop.util.ProcessTestUtils.executeAvailableJobs;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.assertThat;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.complete;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.task;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.withVariables;
import static org.camunda.bpm.engine.variable.Variables.SerializationDataFormats.JSON;
import static org.camunda.bpm.engine.variable.Variables.objectValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

import java.util.Map;

import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import de.mathema.springboot.camunda.workshop.jms.UmsatzAnsPartnerkontoSenden;
import de.mathema.springboot.camunda.workshop.config.AppTestConfig;
import de.mathema.springboot.camunda.workshop.config.ProcessEngineTestConfig;
import de.mathema.springboot.camunda.workshop.model.Kontoumsatz;

import io.digitalstate.camunda.coverage.bpmn.CoverageBuilderJavaBridge;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ProcessEngineTestConfig.class, AppTestConfig.class})
@TestPropertySource(
        properties = {
                "cfd.process.dreissig-sekunden-timer=PT1S",
                "cfd.process.ein-tag-timer=PT2S"
        }
)
public class CfdProcessTests {

    @Rule
    @Autowired
    public ProcessEngineRule processEngineRule;

    @Autowired
    private UmsatzAnsPartnerkontoSenden umsatzAnsPartnerkontoSenden;

    private CoverageBuilderJavaBridge coverageBuilder;

    @Before
    public void setUp() {
        coverageBuilder = new CoverageBuilderJavaBridge();
        doNothing().when(umsatzAnsPartnerkontoSenden).sendeUmsatz(any(Kontoumsatz.class), anyString());
    }

    @After
    public void tearDown() {
        coverageBuilder.saveCoverageSnapshots();
        coverageBuilder.getCoverageSnapshots().clear();
    }

    @Test
    @Deployment(resources = "cfd-process.bpmn")
    public void cfdProcess_deployment() {
    }

    @Test
    @Deployment(resources = "cfd-process.bpmn")
    public void cfdProcess_keinCfdUmsatz() {
        final ProcessInstance processInstance = startProzessMit("Test");
        assertThat(processInstance).isNotNull();

        assertThat(processInstance).isEnded();

        coverageBuilder.coverageSnapshot(processInstance);
    }

    @Test
    @Deployment(resources = "cfd-process.bpmn")
    public void cfdProcess_fehlerBeimUmsatzSenden() {
        doThrow(new RuntimeException()).when(umsatzAnsPartnerkontoSenden).sendeUmsatz(any(Kontoumsatz.class), anyString());

        final ProcessInstance processInstance = startProzessMit("CFD");
        assertThat(processInstance).isNotNull();

        assertThat(processInstance).task().hasName("Umsatz manuell buchen");
        complete(task(), withVariables("wurde_manuell_ueberwiesen", true));

        assertThat(processInstance).isEnded();

        coverageBuilder.coverageSnapshot(processInstance);
    }

    @Test
    @Deployment(resources = "cfd-process.bpmn")
    public void cfdProcess_erfolgreichVerarbeitet() {
        final ProcessInstance processInstance = startProzessMit("CFD");
        assertThat(processInstance).isNotNull();

        assertThat(processInstance).isEnded();

        coverageBuilder.coverageSnapshot(processInstance);
    }

    private ProcessInstance startProzessMit(final String kontotyp) {
        return processEngineRule.getRuntimeService().startProcessInstanceByKey(
                "Prozess_Cfd", "businessKey", prozessVariablen(kontotyp));
    }

    private Map<String, Object> prozessVariablen(final String kontotyp) {
        return withVariables("kontoumsatz", kontoumsatzMit(kontotyp));
    }

    private ObjectValue kontoumsatzMit(final String kontentyp) {
        final Kontoumsatz kontoumsatz = Kontoumsatz.builder().iban("DE 1234 5678 1234 5678 90").kontentyp(kontentyp).betrag(10.0).build();
        return objectValue(kontoumsatz).serializationDataFormat(JSON).create();
    }
}

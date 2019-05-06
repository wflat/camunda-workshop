package de.mathema.springboot.camunda.workshop.delegate.cfd;

import static de.mathema.springboot.camunda.workshop.utils.LoggingUtils.logInfo;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.mathema.springboot.camunda.workshop.jms.UmsatzAnsPartnerkontoSenden;
import de.mathema.springboot.camunda.workshop.model.Kontoumsatz;


/**
 * UmsatzSendenDelegate.
 *
 * @author wflat
 */
@Component
public class UmsatzSendenDelegate implements JavaDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(UmsatzSendenDelegate.class);

    private static final String ERROR_CODE = "Error_FehlerBeimUmsatzSenden_Code";

    @Autowired
    private UmsatzAnsPartnerkontoSenden umsatzAnsPartnerkontoSenden;

    @Override
    public void execute(final DelegateExecution execution) {
        // TODO: Umsatz senden und im Fehler BpmnError werfen

        logInfo(LOGGER, execution);
    }
}

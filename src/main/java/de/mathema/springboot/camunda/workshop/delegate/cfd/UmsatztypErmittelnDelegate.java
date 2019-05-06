package de.mathema.springboot.camunda.workshop.delegate.cfd;

import static de.mathema.springboot.camunda.workshop.utils.LoggingUtils.logInfo;

import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import de.mathema.springboot.camunda.workshop.model.Kontoumsatz;

/**
 * UmsatztypErmittelnDelegate.
 *
 * @author wflat
 */
@Component
public class UmsatztypErmittelnDelegate implements JavaDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(UmsatztypErmittelnDelegate.class);

    @Override
    public void execute(final DelegateExecution execution) {
        // TODO: Ermitteln ob CFD Umsatz und Prozessvariable setzen

        logInfo(LOGGER, execution);
    }

    private boolean istCfdUmsatz(final Kontoumsatz kontoumsatz) {
        return StringUtils.equalsIgnoreCase(kontoumsatz.getKontentyp(), "CFD") &&
                kontoumsatz.getBetrag() != null && kontoumsatz.getBetrag() > 0.0;
    }
}

package de.mathema.springboot.camunda.workshop.utils;

import java.util.stream.Collectors;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.VariableScope;
import org.camunda.bpm.engine.variable.value.SerializableValue;
import org.camunda.bpm.engine.variable.value.TypedValue;
import org.slf4j.Logger;

/**
 * LoggingUtils.
 *
 * @author wflat
 */
public final class LoggingUtils {

    private LoggingUtils() {
    }

    public static void logInfo(final Logger logger, final DelegateExecution execution) {
        if (logger.isInfoEnabled()) {
            logger.info(String.format("%s: %s - %s -> %s  [%s] (%s)",
                    execution.getProcessInstanceId(), execution.getEventName(), execution.getCurrentActivityId(),
                    execution.getCurrentTransitionId(), variablesToString(execution),
                    execution.getProcessDefinitionId()));
        }
    }

    public static void logInfo(final Logger logger, final DelegateTask delegateTask) {
        if (logger.isInfoEnabled()) {
            logger.info(String.format("%s: %s - %s [%s] (%s)",
                    delegateTask.getProcessInstanceId(), delegateTask.getEventName(), delegateTask.getName(),
                    variablesToString(delegateTask), delegateTask.getProcessDefinitionId()));
        }
    }

    public static String variablesToString(final VariableScope variableScope) {
        return variableScope.getVariableNames().stream().map(v ->
                String.format("%s=%s", v, valueToString(v, variableScope))).collect(Collectors.joining(", "));
    }

    public static String valueToString(final String variableName, final VariableScope variableScope) {
        final TypedValue typedValue = variableScope.getVariableTyped(variableName);
        if (typedValue instanceof SerializableValue) {
            return ((SerializableValue) typedValue).getValueSerialized();
        }
        return String.valueOf(typedValue.getValue());
    }
}

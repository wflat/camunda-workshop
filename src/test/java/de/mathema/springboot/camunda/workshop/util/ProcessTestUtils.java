package de.mathema.springboot.camunda.workshop.util;

import static org.awaitility.Awaitility.await;
import static org.camunda.bpm.engine.impl.util.ClockUtil.getCurrentTime;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.execute;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.jobQuery;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.managementService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.awaitility.Duration;
import org.camunda.bpm.engine.management.JobDefinition;
import org.camunda.bpm.engine.runtime.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ProcessTestUtils.
 *
 * @author wflat
 */
public final class ProcessTestUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessTestUtils.class);

    private ProcessTestUtils() {
    }

    public static List<String> executeAvailableJobs(final int expectedExecutions) {
        return executeAvailableJobs(expectedExecutions, 0);
    }

    public static List<String> executeAvailableJobs(final int expectedExecutions, final long timeoutInSeconds) {
        final ExecutedJobsCounter executedJobsCounter = new ExecutedJobsCounter();
        final Duration pollIntervalDuration =
                timeoutInSeconds <= 0 ? Duration.ONE_MILLISECOND : Duration.ONE_HUNDRED_MILLISECONDS;
        final Duration atMostDuration =
                timeoutInSeconds <= 1 ? Duration.ONE_SECOND : new Duration(timeoutInSeconds, TimeUnit.SECONDS);

        await().atMost(atMostDuration).pollInterval(pollIntervalDuration).until(() -> {
            for (final Job job : getAvailableJobs()) {
                final JobDefinition jobDefinition = jobDefinition(job);
                LOGGER.info(String.format("Execute job: %s, %s", job.getId(), jobDefinition.getActivityId()));

                execute(job);
                executedJobsCounter.addExecutedJob(jobDefinition);
            }
            return executedJobsCounter.executedCounter() >= expectedExecutions;
        });

        return executedJobsCounter.executedJobs();
    }

    public static JobDefinition jobDefinition(final Job job) {
        return managementService().createJobDefinitionQuery().jobDefinitionId(job.getJobDefinitionId()).singleResult();
    }

    public static List<Job> getAvailableJobs() {
        return jobQuery().withRetriesLeft().list().stream()
                .filter(job -> !job.isSuspended() && (job.getDuedate() == null || getCurrentTime().after(job.getDuedate())))
                .collect(Collectors.toList());
    }

    static class ExecutedJobsCounter {
        private List<String> executed = new ArrayList<>();

        int addExecutedJob(final JobDefinition jobDefinition) {
            this.executed.add(jobDefinition.getActivityId());
            return this.executed.size();
        }

        int executedCounter() {
            return this.executed.size();
        }

        List<String> executedJobs() {
            return Collections.unmodifiableList(this.executed);
        }
    }
}

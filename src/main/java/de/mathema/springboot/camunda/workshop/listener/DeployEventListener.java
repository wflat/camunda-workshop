package de.mathema.springboot.camunda.workshop.listener;

import org.apache.commons.lang3.ClassUtils;
import org.camunda.bpm.spring.boot.starter.event.PostDeployEvent;
import org.camunda.bpm.spring.boot.starter.event.PreUndeployEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * DeployEventListener.
 *
 * @author wflat
 */
@Component
public class DeployEventListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeployEventListener.class);

    @EventListener
    public void onPostDeploy(final PostDeployEvent event) {
        LOGGER.info(String.format("%s received in %s", ClassUtils.getSimpleName(event), ClassUtils.getSimpleName(this)));
        event.getProcessEngine().getRepositoryService().createProcessDefinitionQuery().latestVersion().list().stream().
                forEach(pd -> LOGGER.info(String.format("ProcessDefinition: name=%s, id=%s, version=%d, versionTag=%s",
                        pd.getName(), pd.getKey(), pd.getVersion(), pd.getVersionTag())));
    }

    @EventListener
    public void onPreUndeploy(final PreUndeployEvent event) {
        LOGGER.info(String.format("%s received in %s", ClassUtils.getSimpleName(event), ClassUtils.getSimpleName(this)));
    }
}

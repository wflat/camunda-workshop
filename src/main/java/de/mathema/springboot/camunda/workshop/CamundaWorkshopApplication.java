package de.mathema.springboot.camunda.workshop;

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * CamundaWorkshopApplication.
 *
 * @author wflat
 */
@SpringBootApplication
@EnableProcessApplication("CamundaWorkshop")
public class CamundaWorkshopApplication {

    public static void main(final String[] args) {
        SpringApplication.run(CamundaWorkshopApplication.class, args);
    }
}

package de.mathema.springboot.camunda.workshop.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * KontoumsatzBestaetigung.
 *
 * @author wflat
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class KontoumsatzBestaetigung {

    private boolean erfolgreich;
}

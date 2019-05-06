package de.mathema.springboot.camunda.workshop.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Kontoumsatz.
 *
 * @author wflat
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Kontoumsatz {

    private String iban;

    private String kontentyp;

    private Double betrag;
}

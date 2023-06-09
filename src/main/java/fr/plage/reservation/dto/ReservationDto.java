package fr.plage.reservation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ReservationDto {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("dateDebut")
    private LocalDate dateDebut;
    @JsonProperty("dateFin")
    private LocalDate dateFin;
    @JsonProperty("montantAReglerEnEuros")
    private double montantAReglerEnEuros;
    @JsonProperty("remarques")
    private String remarques;
    @JsonProperty("numeroCarte")
    private String numeroCarte;
    @JsonProperty("moisExpiration")
    private byte moisExpiration;
    @JsonProperty("anneeExpiration")
    private int anneeExpiration;
    private String cryptogramme;
}

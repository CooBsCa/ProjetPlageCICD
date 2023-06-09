package fr.plage.reservation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ClientDto {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("nom")
    private String nom;
    @JsonProperty("prenom")
    private String prenom;
    @JsonProperty("email")
    private String email;
    @JsonProperty("motDePasse")
    private String motDePasse;
    @JsonProperty("pays")
    private String pays;
    @JsonProperty("lienDeParente")
    private String lienDeParente;
}

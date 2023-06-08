package fr.plage.reservation.business;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="Id")
    private Long id;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private double montantAReglerEnEuros;
    private String remarques;
    private String numeroCarte;
    private byte moisExpiration;
    private int anneeExpiration; // changement de type de byte à int car par exemple impossible de cast 2024 en byte
    private String cryptogramme;
    @JsonIgnore
    @ManyToOne
    private Client client;
    @JsonIgnore
    @ManyToOne
    private Statut statut;
    @JsonIgnore
    @ManyToOne
    private Concessionnaire concessionnaire;
    @JsonIgnore
    @ManyToMany
    private List<Parasol> parasols;

    public Reservation(LocalDate dateDebut, LocalDate dateFin, String numeroCarte, byte moisExpiration, int anneeExpiration, String cryptogramme) {
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.numeroCarte = numeroCarte;
        this.moisExpiration = moisExpiration;
        this.anneeExpiration = anneeExpiration;
        this.cryptogramme = cryptogramme;
    }

    //calcul du montant à régler en fonction du coefficient de lien de parenté
    public void setMontantAReglerEnEuros(){
        this.parasols.forEach(parasol ->
                this.montantAReglerEnEuros =+ parasol.getFile().getPrixJournalier() * this.client.getLienDeParente().getCoefficient())
        ;
    }
}

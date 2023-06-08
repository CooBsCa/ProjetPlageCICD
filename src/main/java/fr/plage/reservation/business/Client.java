package fr.plage.reservation.business;


import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.plage.reservation.dao.PaysDao;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class Client extends Utilisateur{
    private LocalDateTime dateHeureInscription;
    @JsonIgnore
    @ManyToOne
    private Pays pays;
    @JsonIgnore
    @OneToMany(mappedBy = "client")
    private List<Reservation> reservations;
    @JsonIgnore
    @ManyToOne
    private LienDeParente lienDeParente;

    public Client(String nom, String prenom, String email, String motDePasse) {
        super(nom, prenom, email, motDePasse);
        LocalDateTime date = LocalDateTime.now();
        this.dateHeureInscription = date;
    }
}

package fr.plage.reservation.dao;

import fr.plage.reservation.business.Pays;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaysDao extends JpaRepository<Pays, Long> {
    Pays findByNom(String nom);
}

package fr.plage.reservation.dao;

import fr.plage.reservation.business.Statut;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatutDao extends JpaRepository<Statut, Long> {
    Statut findByNom(String nom);
}

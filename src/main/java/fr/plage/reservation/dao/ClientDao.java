package fr.plage.reservation.dao;

import fr.plage.reservation.business.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientDao extends JpaRepository<Client, Long> {
    Client findByNom(String nom);
}

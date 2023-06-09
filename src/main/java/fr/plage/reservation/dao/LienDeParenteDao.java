package fr.plage.reservation.dao;

import fr.plage.reservation.business.LienDeParente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LienDeParenteDao extends JpaRepository<LienDeParente, Long> {
    LienDeParente findByNom(String nom);
}

package fr.plage.reservation.dao;

import fr.plage.reservation.business.Concessionnaire;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcessionnaireDao extends JpaRepository<Concessionnaire, Long> {
}

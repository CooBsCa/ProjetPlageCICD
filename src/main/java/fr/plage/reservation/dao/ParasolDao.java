package fr.plage.reservation.dao;

import fr.plage.reservation.business.File;
import fr.plage.reservation.business.Parasol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParasolDao extends JpaRepository<Parasol, Long> {
    Parasol findByFileAndNumEmplacement(File file, byte numEmplacement);
}

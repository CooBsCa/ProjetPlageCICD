package fr.plage.reservation.dao;

import fr.plage.reservation.business.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileDao extends JpaRepository<File, Long> {
}

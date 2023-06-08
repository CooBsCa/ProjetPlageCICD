package fr.plage.reservation.dao;

import fr.plage.reservation.business.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationDao extends JpaRepository<Reservation, Long> {
}

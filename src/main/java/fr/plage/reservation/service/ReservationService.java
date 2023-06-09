package fr.plage.reservation.service;

import fr.plage.reservation.business.Client;
import fr.plage.reservation.business.Reservation;
import fr.plage.reservation.dto.ReservationDto;

import java.util.List;

public interface ReservationService {
    List<Reservation> recupererReservations();

    Reservation recupererReservation(Long id);

    Reservation enregistrerReservation( String dateD,  String dateF,
                                         String numeroCarte,  byte moisExpiration,  int anneeExpiration,
                                         String cryptogramme,  byte parasolNumber, long numeroFileEmplacement, Long idClient);

    boolean supprimerReservation(Long id);


    Reservation mettreAJourReservation(ReservationDto reservationDto);

    Reservation mettreAJourReservation(Reservation reservation);
}

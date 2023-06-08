package fr.plage.reservation.service.impl;


import fr.plage.reservation.business.Client;
import fr.plage.reservation.business.Parasol;
import fr.plage.reservation.business.Reservation;
import fr.plage.reservation.dao.*;
import fr.plage.reservation.dto.ClientDto;
import fr.plage.reservation.dto.ReservationDto;
import fr.plage.reservation.exception.ClientInexistantException;
import fr.plage.reservation.exception.ErreurDateParasol;
import fr.plage.reservation.exception.ParasolInexistant;
import fr.plage.reservation.exception.ReservationInexistante;
import fr.plage.reservation.service.ReservationService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    @NonNull
    private ReservationDao reservationDao;
    @NonNull
    private ParasolDao parasolDao;
    @NonNull
    private final FileDao fileDao;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MM-yyyy");
    private final LienDeParenteDao lienDeParenteDao;
    private final ClientDao clientDao;

    @Override
    public List<Reservation> recupererReservations() {return reservationDao.findAll();}

    @Override
    public Reservation recupererReservation(Long id) {return reservationDao.findById(id).orElse(null);}

    @Override
    public Reservation enregistrerReservation(String dateDebut, String dateFin,
        String numeroCarte, byte moisExpiration, int anneeExpiration, String cryptogramme, byte parasolNumber,
        long numeroFileEmplacement, Long idClient) {
        LocalDate dateD = LocalDate.parse(dateDebut, formatter);
        LocalDate dateF = LocalDate.parse(dateFin, formatter);
        List<Parasol> parasols = new ArrayList<Parasol>();
        Parasol parasol = parasolDao.findByFileAndNumEmplacement(fileDao.getReferenceById(numeroFileEmplacement), parasolNumber);
        if (parasol == null){
            throw new ParasolInexistant("Ce parasol n'existe pas ou est introuvable");
        }
        if (dateD.isBefore(LocalDate.parse("01-06-"+dateD.getYear(), formatter))
                || dateD.isAfter(LocalDate.parse("15-09-"+dateD.getYear(), formatter))
                    || dateF.isBefore(LocalDate.parse("01-06-"+dateD.getYear(), formatter))
                        || dateF.isAfter(LocalDate.parse("15-09-"+dateD.getYear(), formatter))){
            throw new ErreurDateParasol("Les dates sont hors saison");
        }
        //verifier si le parasol est deja reserve
        parasol.getReservation().forEach(reservation -> {
            if (reservation.getDateDebut().equals(dateF) || reservation.getDateFin().equals(dateD)
            || (dateD.isAfter(reservation.getDateDebut())
                    || dateF.isBefore(reservation.getDateFin()))){
                throw new ErreurDateParasol("Ce parasol est deja reserve à ses dates");
            }
        });

        if (clientDao.findById(idClient) == null){
            throw new ClientInexistantException("Ce client n'existe pas ou est introuvable");
        }

        Reservation reservation1 = new Reservation();
        reservation1.setClient(clientDao.getReferenceById(idClient));
        reservation1.setDateDebut(dateD);
        reservation1.setDateFin(dateF);
        reservation1.setNumeroCarte(numeroCarte);
        reservation1.setMoisExpiration(moisExpiration);
        reservation1.setAnneeExpiration(anneeExpiration);
        reservation1.setCryptogramme(cryptogramme);
        parasols.add(parasol);
        reservation1.setParasols(parasols);
        reservation1.setMontantAReglerEnEuros();
        return reservationDao.save(reservation1);
    }

    @Override
    public boolean supprimerReservation(Long id) {
        Reservation reservation = recupererReservation(id);
        if (reservation != null) {
            if (reservation.getParasols() != null) {
                reservation.getParasols().clear();
            }
            reservationDao.delete(reservation);
            return true;
        }else {
            return false;
        }
    }
    @Override
    public Reservation mettreAJourReservation(ReservationDto reservationDto){
        Reservation reservation = new Reservation(reservationDto.getDateDebut(), reservationDto.getDateFin(), reservationDto.getNumeroCarte(), reservationDto.getMoisExpiration(), reservationDto.getAnneeExpiration(), reservationDto.getCryptogramme());
        reservation.setId(reservationDto.getId());
        return mettreAJourReservation(reservation);
    }
    @Override
    public Reservation mettreAJourReservation(Reservation reservation) {
        if (reservation.getId() == null) {
            throw new ReservationInexistante("Il manque l'id de la reservation");
        }
        Reservation reservationAModifier = reservationDao.findById(reservation.getId()).orElseThrow(() -> new ReservationInexistante("Cette reservation n'existe pas"));

        if (reservation.getId().equals(reservationAModifier.getId())) {
            return reservationDao.save(reservation);
        }
        else {
            return enregistrerReservation(reservation.getDateDebut().toString(), reservation.getDateFin().toString(), reservation.getNumeroCarte(), reservation.getMoisExpiration(), reservation.getAnneeExpiration(), reservation.getCryptogramme(), reservation.getParasols().get(0).getNumEmplacement(), reservation.getParasols().get(0).getFile().getId(), reservation.getClient().getId());
        }
    }
}

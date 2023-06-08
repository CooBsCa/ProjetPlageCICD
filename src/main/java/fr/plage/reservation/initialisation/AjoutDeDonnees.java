package fr.plage.reservation.initialisation;

import fr.plage.reservation.business.*;
import fr.plage.reservation.controller.ReservationRestController;
import fr.plage.reservation.dao.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.w3c.dom.Node;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Component
public class AjoutDeDonnees implements CommandLineRunner {

    private final FileDao fileDao;
    private final ParasolDao parasolDao;
    private final LienDeParenteDao lienDeParenteDao;
    private final StatutDao statutDao;
    private final PaysDao paysDao;
    private final ClientDao clientDao;
    private final ReservationDao reservationDao;
    private final ConcessionnaireDao concessionnaireDao;

    @Override
    public void run(String... args) throws Exception {
        for (int i = 0; i < 7; i++) {
            File file = new File();
            file.setNumero((byte) (i +1));
            file.setPrixJournalier((float) 100 - ((i+1)*10));
            fileDao.save(file);
            for (int j = 0; j < 8; j++){
                Parasol parasol = new Parasol();
                parasol.setFile(file);
                parasol.setNumEmplacement((byte) (j+1));
                parasolDao.save(parasol);
            }
        }
        lienDeParenteDao.save(new LienDeParente("Frere-Soeur",(float)0.5));
        lienDeParenteDao.save(new LienDeParente("Cousin-Cousine",(float)0.75));
        lienDeParenteDao.save(new LienDeParente("Aucun",(float)1));

        paysDao.save(new Pays("France"));
        paysDao.save(new Pays("Espagne"));
        paysDao.save(new Pays("Italie"));
        paysDao.save(new Pays("Allemagne"));
        paysDao.save(new Pays("Angleterre"));
        
        statutDao.save(new Statut("A traiter"));
        statutDao.save(new Statut("Refusee"));
        statutDao.save(new Statut("Confirmee"));

        LocalDateTime date = LocalDateTime.now();

        Client client1 = new Client();
        client1.setNom("DUPONT");
        client1.setPrenom("Jean");
        client1.setEmail("dupontjean@gmail.com");
        client1.setDateHeureInscription(date);
        client1.setLienDeParente(lienDeParenteDao.findByNom("Aucun"));
        clientDao.save(client1);
        Client client2 = new Client();
        client2.setNom("DURAND");
        client2.setPrenom("Pierre");
        client2.setEmail("durandpierre@gmail.com");
        client2.setDateHeureInscription(date.plusDays(1));
        client2.setLienDeParente(lienDeParenteDao.findByNom("Frere-Soeur"));
        clientDao.save(client2);

        Concessionnaire concessionnaire = new Concessionnaire();
        concessionnaire.setNom("EDGARD");
        concessionnaire.setPrenom("Jean");
        concessionnaire.setEmail("edgardjean@gmail.com");
        concessionnaire.setNumeroDeTelephone("0912567864");
        concessionnaireDao.save(concessionnaire);

        List<Parasol> parasols = new ArrayList<Parasol>();

        Reservation reservation = new Reservation();
        reservation.setClient(client1);
        reservation.setStatut(statutDao.findByNom("Confirmee"));
        reservation.setDateDebut(LocalDate.now());
        reservation.setDateFin(LocalDate.now().plusDays(1));
        reservation.setConcessionnaire(concessionnaire);
        reservation.setNumeroCarte("1244567891234567");
        reservation.setMoisExpiration((byte)11);
        reservation.setAnneeExpiration(2025);
        reservation.setCryptogramme("124");
        parasols.add(parasolDao.findByFileAndNumEmplacement(fileDao.getReferenceById(1L),(byte) 1));
        reservation.setParasols(parasols);
        reservation.setMontantAReglerEnEuros();
        reservationDao.save(reservation);

        Reservation reservation2 = new Reservation();
        reservation2.setClient(client2);
        reservation2.setStatut(statutDao.findByNom("Confirmee"));
        reservation2.setDateDebut(LocalDate.now());
        reservation2.setDateFin(LocalDate.now().plusDays(2));
        reservation2.setConcessionnaire(concessionnaire);
        reservation2.setNumeroCarte("1234567891234567");
        reservation2.setMoisExpiration((byte)12);
        reservation2.setAnneeExpiration(2024);
        reservation2.setCryptogramme("123");
        parasols.clear();
        parasols.add(parasolDao.findByFileAndNumEmplacement(fileDao.getReferenceById(1L),(byte) 2));
        reservation2.setParasols(parasols);
        reservation2.setMontantAReglerEnEuros();
        reservationDao.save(reservation2);
    }
}

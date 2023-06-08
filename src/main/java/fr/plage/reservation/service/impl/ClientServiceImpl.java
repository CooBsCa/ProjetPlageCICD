package fr.plage.reservation.service.impl;

import fr.plage.reservation.business.Client;
import fr.plage.reservation.business.Pays;
import fr.plage.reservation.dao.ClientDao;
import fr.plage.reservation.dao.LienDeParenteDao;
import fr.plage.reservation.dao.PaysDao;
import fr.plage.reservation.dao.ReservationDao;
import fr.plage.reservation.dto.ClientDto;
import fr.plage.reservation.exception.ClientDejaPresentException;
import fr.plage.reservation.exception.ClientInexistantException;
import fr.plage.reservation.exception.LienInconnuException;
import fr.plage.reservation.exception.PaysInconnuException;
import fr.plage.reservation.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {
    private ClientDao clientDao;
    private ReservationDao reservationDao;
    private LienDeParenteDao lienDeParenteDao;
    private PaysDao paysDao;

    @Override
    public List<Client> recupererClients() {
        return clientDao.findAll();
    }
    @Override
    public Client recupererClient(Long id) {
        return clientDao.findById(id).orElse(null);
    }

    @Override
    public Client enregistrerClient(String nom, String prenom, String email, String motDePasse, String pays, String lienDeParente) {
        Client client = new Client(nom, prenom, email, motDePasse);
        if (paysDao.findByNom(pays) == null) {
            throw new PaysInconnuException("Ce pays n'existe pas");
        }
        client.setPays(paysDao.findByNom(pays));
        if (lienDeParenteDao.findByNom(lienDeParente) == null) {
            throw new LienInconnuException("Ce lien de parenté n'existe pas");
        }
        client.setLienDeParente(lienDeParenteDao.findByNom(lienDeParente));
        Client search = clientDao.findByNom(client.getNom());
        if ((search==null)|| search.getPrenom()!=client.getPrenom() || search.getEmail() != client.getEmail()){
            return clientDao.save(client);
        }
        else {
            throw new ClientDejaPresentException("Ce client est déja présent");
        }
    }

    @Override
    public Client mettreAJourClient(ClientDto clientDto) {
        Client client = new Client(clientDto.getNom(), clientDto.getPrenom(), clientDto.getEmail(), clientDto.getMotDePasse());
        client.setId(clientDto.getId());
        return mettreAJourClient(client);
    }

    @Override
    public Client mettreAJourClient(Client client) {
        if (client.getId() == null) {
            throw new ClientInexistantException("Il manque l'id du client");
        }
        Client clientAModifier = clientDao.findById(client.getId()).orElseThrow(() -> new ClientInexistantException("Cet éditeur n'existe pas"));

        if (client.getId().equals(clientAModifier.getId())) {
            return clientDao.save(client);
        }
        else {
            return enregistrerClient(client.getNom(), client.getPrenom(), client.getEmail(), client.getMotDePasse(), client.getPays().getNom(), client.getLienDeParente().getNom());
        }
    }

    @Override
    public boolean supprimerClient(Long id) {
        Client client = recupererClient(id);
        if (client!=null) {
            if (client.getReservations() != null) {
                client.getReservations().forEach(reservation -> reservationDao.delete(reservation));
            }

            clientDao.delete(client);
            return true;
        }else{
            return false;
        }
    }
}

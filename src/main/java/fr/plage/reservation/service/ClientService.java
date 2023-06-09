package fr.plage.reservation.service;

import fr.plage.reservation.business.Client;
import fr.plage.reservation.dto.ClientDto;

import java.util.List;

public interface ClientService {
    List<Client> recupererClients();

    Client recupererClient(Long id);

    Client enregistrerClient(String nom, String prenom, String email, String motDePasse, String pays, String lienDeParente);

    Client mettreAJourClient(ClientDto clientDto);

    Client mettreAJourClient(Client client);

    boolean supprimerClient(Long id);
}

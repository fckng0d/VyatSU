package ru.fckng0d.boot.loan.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.fckng0d.boot.loan.entities.Client;
import ru.fckng0d.boot.loan.entities.User;
import ru.fckng0d.boot.loan.repositories.ClientRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }   

    public Client getClientById(Long id) {
        return clientRepository.findById(id).orElse(null);
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Client getClientByUser (User user) {
        return clientRepository.findClientByUser(user);
    }

    public void add(Client client) {
        clientRepository.save(client);
    }

    public void delete(Long id) {
        clientRepository.delete(getClientById(id));
    }

    public void update(Long id, Client updatedClient) {
        Client client = getClientById(id);
        if (client != null) {
            client.setFirstName(updatedClient.getFirstName());
            client.setLastName(updatedClient.getLastName());
            client.setPatronymic(updatedClient.getPatronymic());
            client.setPassport(updatedClient.getPassport());
            client.setBirthDate(updatedClient.getBirthDate());
            clientRepository.save(client);
        }
    }

    public List<Client> filter(String lastName, String birthDate, String passport) {
        List<Client> filteredList = getAllClients();

        if (lastName != null && !lastName.isBlank()) {
            filteredList.removeIf(c -> !c.getLastName().toLowerCase().contains(lastName.toLowerCase()));
        }
        if (birthDate != null && !birthDate.isBlank()) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            try {
                formatter.parse(birthDate);
                for (Client c : filteredList) {
                    if (!c.getBirthDate().equals(birthDate)) {
                        filteredList.remove(c);
                    }
                }
            } catch (ParseException ignored) {
            }
        }
        if (passport != null && !passport.isBlank()) {
            filteredList.removeIf(c -> !c.getPassport().contains(passport));
        }

        return filteredList;
    }

    public Client isThereSuchPassport(Client client, Long id, int mode) {
        if (mode == 0) {
            for (Client c : getAllClients()) {
                if (c.getPassport().equals(client.getPassport())) {
                    return c;
                }
            }
        } else if (mode == 1) {
            for (Client c : getAllClients()) {
                if (c.getPassport().equals(client.getPassport())
                        && !Objects.equals(c.getClientId(), id)) {
                    return c;
                }
            }
        }
        return null;
    }
}

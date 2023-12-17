package ru.fckng0d.boot.loan.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.fckng0d.boot.loan.entities.Client;
import ru.fckng0d.boot.loan.entities.User;
import ru.fckng0d.boot.loan.repositories.ClientRepository;

import javax.sql.DataSource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;

@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final DataSource dataSource;


    @Autowired
    public ClientService(ClientRepository clientRepository, DataSource dataSource) {
        this.clientRepository = clientRepository;
        this.dataSource = dataSource;
    }

    public Client getClientById(Long id) {
        return clientRepository.findById(id).orElse(null);
    }

    public String getClientIdByUsername(String username) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);;

        String clientIdQuery = "SELECT c.client_id FROM client c WHERE c.username = ?";
        return String.valueOf(jdbcTemplate.queryForObject(clientIdQuery, Long.class, username));
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Client getClientByUser(User user) {
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

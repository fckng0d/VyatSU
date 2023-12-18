package ru.fckng0d.boot.loan.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.fckng0d.boot.loan.entities.Client;
import ru.fckng0d.boot.loan.entities.User;
import ru.fckng0d.boot.loan.repositories.ClientRepository;
import ru.fckng0d.boot.loan.specifications.ClientSpecification;

import javax.sql.DataSource;
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

    public void incrementLoginCount(Long clientId) {
        Client client = getClientById(clientId);
        client.setCountOfLogins(client.getCountOfLogins() + 1);
        clientRepository.save(client);
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Page<Client> getAllClients(Pageable pageable) {
        return clientRepository.findAll(pageable);
    }

    public Page<Client> getAllClients(String lastName, String birthDate, String passport, Pageable pageable) {
        Specification<Client> specification = Specification
                .where(ClientSpecification.likeLastName(lastName))
                .and(ClientSpecification.hasBirthDate(birthDate))
                .and(ClientSpecification.likePassport(passport));
        return clientRepository.findAll(specification, pageable);
    }

    public List<Client> getTopClients() {
        Pageable topClients = PageRequest.of(0, 3,
                Sort.by(Sort.Order.desc("countOfLogins")));
        Page<Client> topClientsPage = clientRepository.findAll(topClients);
        return topClientsPage.getContent();
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

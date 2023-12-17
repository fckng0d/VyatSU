package ru.fckng0d.boot.loan.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.fckng0d.boot.loan.entities.Client;
import ru.fckng0d.boot.loan.entities.User;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long>, JpaSpecificationExecutor<Client> {
    Client findClientByUser(User user);
}

package ru.fckng0d.boot.loan.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.fckng0d.boot.loan.entities.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}

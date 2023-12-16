package ru.fckng0d.boot.loan.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fckng0d.boot.loan.entities.Authority;
import ru.fckng0d.boot.loan.entities.User;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, String> {
    Authority findAuthorityByUser(User user);
}

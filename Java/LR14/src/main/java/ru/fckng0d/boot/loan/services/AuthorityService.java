package ru.fckng0d.boot.loan.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.fckng0d.boot.loan.entities.Authority;
import ru.fckng0d.boot.loan.entities.User;
import ru.fckng0d.boot.loan.repositories.AuthorityRepository;

@Service
public class AuthorityService {
    private final AuthorityRepository authorityRepository;

    @Autowired
    public AuthorityService(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    public Authority getAuthorityByUser(User user) {
        return authorityRepository.findAuthorityByUser(user);
    }

    public String getRoleByUser(User user) {
        return getAuthorityByUser(user).getRole();
    }

    public void save(Authority authority) {
        authorityRepository.save(authority);
    }
}

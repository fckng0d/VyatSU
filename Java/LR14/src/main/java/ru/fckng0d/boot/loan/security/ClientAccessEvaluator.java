package ru.fckng0d.boot.loan.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import ru.fckng0d.boot.loan.services.ClientService;

@Component
public class ClientAccessEvaluator {

    private final ClientService clientService;

    @Autowired
    public ClientAccessEvaluator(ClientService clientService) {
        this.clientService = clientService;
    }

    public boolean canAccessClient(Authentication authentication, Long clientId) {
        if (authentication.getAuthorities().stream().anyMatch(auth ->
                auth.getAuthority().equals("ROLE_ADMIN"))) {
            return true;
        }
        return Long.parseLong(clientService.getClientIdByUsername(authentication.getName())) == clientId;
    }
}

package ru.fckng0d.boot.loan.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.fckng0d.boot.loan.entities.Authority;
import ru.fckng0d.boot.loan.entities.Client;
import ru.fckng0d.boot.loan.entities.User;
import ru.fckng0d.boot.loan.services.AuthorityService;
import ru.fckng0d.boot.loan.services.ClientService;
import ru.fckng0d.boot.loan.services.UserService;

import javax.validation.Valid;

@Controller
public class AuthController {
    private final UserService userService;
    private final ClientService clientService;
    private final AuthorityService authorityService;
    SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();

    @Autowired
    public AuthController(UserService userService, ClientService clientService, AuthorityService authorityService) {
        this.userService = userService;
        this.clientService = clientService;
        this.authorityService = authorityService;
    }

    @GetMapping("/hello")
    public String hello() {
//        userService.encode("user1", new BCryptPasswordEncoder());
//        userService.encode("user2", new BCryptPasswordEncoder());
//        userService.encode("user3", new BCryptPasswordEncoder());
//        userService.encode("user4", new BCryptPasswordEncoder());
//        userService.encode("user5", new BCryptPasswordEncoder());
//        userService.encode("user6", new BCryptPasswordEncoder());
//        userService.encode("user7", new BCryptPasswordEncoder());
//        userService.encode("user8", new BCryptPasswordEncoder());
//        userService.encode("user9", new BCryptPasswordEncoder());
//        userService.encode("user10", new BCryptPasswordEncoder());
//        userService.encode("user11", new BCryptPasswordEncoder());
//        userService.encode("user12", new BCryptPasswordEncoder());
//        userService.encode("user13", new BCryptPasswordEncoder());
//        userService.encode("admin1", new BCryptPasswordEncoder());
//        userService.encode("admin2", new BCryptPasswordEncoder());
//        userService.encode("admin3", new BCryptPasswordEncoder());
        return "auth/hello";
    }

    @GetMapping("/login")
    public String login() {
        return "redirect:/hello";
    }

    @GetMapping("/logout")
    public String logout(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
        this.logoutHandler.logout(request, response, authentication);
        return "redirect:/hello";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "auth/access-denied";
    }

    @GetMapping("/registration")
    public String newClient(@ModelAttribute("client") Client client) {
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String create(@ModelAttribute("client") @Valid Client client, BindingResult bindingResult,
                         @RequestParam("username") String username,
                         @RequestParam("password") String password) {
        if (bindingResult.hasErrors()) {
            return "auth/registration";
        }
        User user = new User();
        user.setUsername(username);
        user.setRealName(client.getFirstName());
        user.setPassword(password);
        userService.save(user);
        userService.encode(username, new BCryptPasswordEncoder());

        Authority authority = new Authority();
        authority.setUser(user);
        authority.setRole("ROLE_USER");
        authorityService.save(authority);

        client.setUser(user);
        client.setCountOfLogins(0);
        clientService.add(client);
        return "redirect:" + "/clients/" + clientService.getClientIdByUsername(username);
    }
}

package ru.fckng0d.boot.loan.controllers;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;
import ru.fckng0d.boot.loan.entities.Client;
import ru.fckng0d.boot.loan.services.ClientService;
import ru.fckng0d.boot.loan.security.ClientAccess;
import ru.fckng0d.boot.loan.util.ClientValidator;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/clients")
public class ClientController {
    @Getter
    private static int mode;
    @Getter
    private static Long tempId;
    private final ClientService clientService;
    private final ClientValidator clientValidator;

    @Autowired
    public ClientController(ClientService clientService, ClientValidator clientValidator) {
        this.clientService = clientService;
        this.clientValidator = clientValidator;
    }

    @GetMapping
    public String index(Model model, @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Client> clientPage = clientService.getAllClients(pageable);
        model.addAttribute("clients", clientPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", clientPage.getTotalPages());

        List<Client> topClients = clientService.getTopClients();
        model.addAttribute("topClients", topClients);

        return "client/index";
    }

    @ClientAccess
    @GetMapping("/{clientId}")
    public String show(@PathVariable("clientId") Long clientId, Model model) {
        model.addAttribute("client", clientService.getClientById(clientId));
        return "client/show";
    }

    @GetMapping("/filter")
    public String filter(@RequestParam(value = "lastName", required = false) String lastName,
                         @RequestParam(value = "birthDate", required = false) String birthDate,
                         @RequestParam(value = "passport", required = false) String passport,
                         @RequestParam(defaultValue = "0") int page,
                         Model model) {
        Client client = new Client();
        Pageable pageable = PageRequest.of(page, 5);
        Page<Client> clientPage = clientService.getAllClients(lastName, birthDate, passport, pageable);

        model.addAttribute("clients", clientPage.getContent());
        model.addAttribute("client", client);
        model.addAttribute("lastName", lastName);
        model.addAttribute("birthDate", birthDate);
        model.addAttribute("passport", passport);

        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", clientPage.getTotalPages());

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/clients/filter");
        if (lastName != null && !lastName.isEmpty()) uriBuilder.queryParam("lastName", lastName);
        if (birthDate != null && !birthDate.isEmpty()) uriBuilder.queryParam("birthDate", birthDate);
        if (passport != null) uriBuilder.queryParam("passport", passport);
        model.addAttribute("filterUrl", uriBuilder.build().toString());

        List<Client> topClients = clientService.getTopClients();
        model.addAttribute("topClients", topClients);

        return "client/index";
    }

    @GetMapping("/new")
    public String newClient(@ModelAttribute("client") Client client) {
        return "client/change";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute("client") @Valid Client client, BindingResult bindingResult) {
        mode = 0;
        clientValidator.validate(client, bindingResult);
        if (bindingResult.hasErrors()) {
            return "client/change";
        }
        clientService.add(client);
        return "redirect:/clients";
    }

    @ClientAccess
    @GetMapping("/{clientId}/edit")
    public String edit(Model model, @PathVariable("clientId") Long clientId) {
        model.addAttribute("client", clientService.getClientById(clientId));
        mode = 1;
        tempId = clientId;
        return "client/change";
    }

    @PatchMapping("/{clientId}")
    public String update(@ModelAttribute("client") @Valid Client client, BindingResult bindingResult,
                         @PathVariable("clientId") Long clientId, RedirectAttributes redirectAttributes) {
        clientValidator.validate(client, bindingResult);
        redirectAttributes.addAttribute("id", clientId);

        if (bindingResult.hasErrors()) {
            return "client/change";
        }

        clientService.update(clientId, client);
        mode = 0;
        tempId = null;
        return "redirect:/clients/{id}";
    }

    @DeleteMapping("/{clientId}")
    public String delete(@PathVariable("clientId") Long clientId) {
        clientService.delete(clientId);
        return "redirect:/clients";
    }
}

package ru.fckng0d.boot.loan.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.fckng0d.boot.loan.entities.Loan;
import ru.fckng0d.boot.loan.services.ClientService;
import ru.fckng0d.boot.loan.services.LoanService;
import ru.fckng0d.boot.loan.security.ClientAccess;

import javax.validation.Valid;
import java.util.List;

@Controller
public class LoanController {
    private final ClientService clientService;
    private final LoanService loanService;

    @Autowired
    public LoanController(ClientService clientService, LoanService loanService) {
        this.clientService = clientService;
        this.loanService = loanService;
    }

    @ClientAccess
    @GetMapping("/clients/{clientId}/loans")
    public String showLoans(@PathVariable("clientId") Long clientId,
                            @RequestParam(defaultValue = "0") int page,
                            Model model) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Loan> loanPage = loanService.getAllLoans(clientId, pageable);
        model.addAttribute("loans", loanPage.getContent());
        model.addAttribute("clientId", clientId);
        model.addAttribute("client", clientService.getClientById(clientId));
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", loanPage.getTotalPages());

        List<Loan> topLoans = loanService.getTopLoans(clientId);
        model.addAttribute("topLoans", topLoans);

        return "loan/loans";
    }

    @ClientAccess
    @GetMapping("/clients/{clientId}/loans/{id}")
    public String show(@PathVariable("clientId") Long clientId, @PathVariable("id") Long id, Model model) {
        model.addAttribute("loan", loanService.getLoanById(id));
        model.addAttribute("clientId", clientId);
        return "loan/show";
    }

    @ClientAccess
    @GetMapping("/clients/{clientId}/loans/new")
    public String newLoan(@PathVariable("clientId") Long clientId, @ModelAttribute("loan") Loan loan, Model model) {
        model.addAttribute("clientId", clientId);
        return "loan/change";
    }

    @ClientAccess
    @PostMapping("/clients/{clientId}/loans")
    public String saveLoan(@PathVariable("clientId") Long clientId, @ModelAttribute("loan") Loan loan) {
        loan.setClient(clientService.getClientById(clientId));
        loanService.add(loan);
        return "redirect:/clients/{clientId}/loans";
    }

    @ClientAccess
    @GetMapping("/clients/{clientId}/loans/{id}/edit")
    public String edit(@PathVariable("clientId") Long clientId, @PathVariable("id") Long id, Model model) {
        model.addAttribute("clientId", clientId);
        model.addAttribute("loan", loanService.getLoanById(id));
        return "loan/change";
    }

    @ClientAccess
    @PatchMapping("/clients/{clientId}/loans/{id}")
    public String update(@ModelAttribute("loan") @Valid Loan loan,
                         @PathVariable("clientId") Long clientId,
                         @PathVariable("id") Long id) {
        loanService.update(id, loan);
        return "redirect:/clients/{clientId}/loans/{id}";
    }

    @DeleteMapping("/clients/{clientId}/loans/{id}")
    public String delete(@PathVariable("clientId") Long clientId, @PathVariable("id") Long id, Model model) {
        System.out.println("DELETE");
        model.addAttribute("clientId", clientId);
        loanService.delete(id);
        return "redirect:/clients/{clientId}/loans";
    }

    @ClientAccess
    @GetMapping("/clients/{clientId}/loans/filter")
    public String filter(@PathVariable("clientId") Long clientId,
                         @RequestParam(value = "from", required = false) String from,
                         @RequestParam(value = "to", required = false) String to,
                         @RequestParam(value = "loanTerm", required = false) String loanTerm,
                         @RequestParam(value = "dateOfGive", required = false) String dateOfGive,
                         @RequestParam(defaultValue = "0") int page,
                         Model model) {
        Loan loan = new Loan();
        Pageable pageable = PageRequest.of(page, 5);
        Page<Loan> loanPage = loanService.getAllLoans(clientId, from, to, loanTerm, dateOfGive, pageable);

        model.addAttribute("client", clientService.getClientById(clientId));
        model.addAttribute("clientId", clientId);
        model.addAttribute("loans", loanPage.getContent());
        model.addAttribute("loan", loan);
        model.addAttribute("from", from);
        model.addAttribute("to", to);
        model.addAttribute("loanTerm", loanTerm);
        model.addAttribute("dateOfGive", dateOfGive);

        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", loanPage.getTotalPages());

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/clients/" + clientId + "/loans/filter");
        if (from != null && !from.isEmpty()) uriBuilder.queryParam("from", from);
        if (to != null && !to.isEmpty()) uriBuilder.queryParam("to", to);
        if (loanTerm != null && !loanTerm.isEmpty()) uriBuilder.queryParam("loanTerm", loanTerm);
        if (dateOfGive != null && !dateOfGive.isEmpty()) uriBuilder.queryParam("dateOfGive", dateOfGive);
        uriBuilder.queryParam("page", page);
        model.addAttribute("filterUrl", uriBuilder.build().toString());

        List<Loan> topLoans = loanService.getTopLoans(clientId);
        model.addAttribute("topLoans", topLoans);

        return "loan/loans";
    }
}

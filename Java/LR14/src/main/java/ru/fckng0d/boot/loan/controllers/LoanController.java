package ru.fckng0d.boot.loan.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.fckng0d.boot.loan.entities.Client;
import ru.fckng0d.boot.loan.entities.Loan;
import ru.fckng0d.boot.loan.repositories.LoanRepository;
import ru.fckng0d.boot.loan.services.ClientService;
import ru.fckng0d.boot.loan.services.LoanService;

import javax.validation.Valid;

@Controller
//@RequestMapping("/loans")
public class LoanController {
    private final ClientService clientService;
    private final LoanService loanService;
    private final LoanRepository loanRepository;

    @Autowired
    public LoanController(ClientService clientService, LoanService loanService, LoanRepository loanRepository) {
        this.clientService = clientService;
        this.loanService = loanService;
        this.loanRepository = loanRepository;
    }

    @GetMapping("/clients/{id}/loans")
    public String showLoans(@PathVariable("id") Long id, Model model) {
        model.addAttribute("loans", loanService.getAllLoansByClientId(id));
        model.addAttribute("clientId", id);
        model.addAttribute("client", clientService.getClientById(id));
        return "loan/loans";
    }

    @GetMapping("/clients/{clientId}/loans/{id}")
    public String show(@PathVariable("clientId") Long clientId, @PathVariable("id") Long id, Model model) {
        model.addAttribute("loan", loanService.getLoanById(id));
        model.addAttribute("clientId", clientId);
        return "loan/show";
    }

    @GetMapping("/clients/{id}/loans/new")
    public String newLoan(@PathVariable("id") Long clientId, @ModelAttribute("loan") Loan loan, Model model) {
        model.addAttribute("clientId", clientId);
        return "loan/change";
    }

    @PostMapping("/clients/{id}/loans")
    public String saveLoan(@PathVariable("id") Long id, @ModelAttribute("loan") Loan loan) {
        loan.setClient(clientService.getClientById(id));
        loanService.add(loan);
        return "redirect:/clients/{id}/loans";
    }

    @GetMapping("/clients/{clientId}/loans/{id}/edit")
    public String edit(@PathVariable("clientId") Long clientId, @PathVariable("id") Long id,  Model model) {
        model.addAttribute("clientId", clientId);
        model.addAttribute("loan", loanService.getLoanById(id));
        return "loan/change";
    }

    @PatchMapping("/clients/{clientId}/loans/{id}")
    public String update(@ModelAttribute("loan") @Valid Loan loan, @PathVariable("id") Long id) {
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
}

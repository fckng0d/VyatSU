package ru.fckng0d.boot.loan.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.fckng0d.boot.loan.entities.Client;
import ru.fckng0d.boot.loan.entities.Loan;
import ru.fckng0d.boot.loan.repositories.LoanRepository;

import java.util.List;

@Service
public class LoanService {
    private final ClientService clientService;
    private final LoanRepository loanRepository;

    @Autowired
    public LoanService(ClientService clientService, LoanRepository loanRepository) {
        this.clientService = clientService;
        this.loanRepository = loanRepository;
    }

    public List<Loan> getAllLoansByClientId(Long clientId) {
//        if (clientService.getClientById(clientId) != null) {
            return clientService.getClientById(clientId).getLoanList();
//        } else {
//            return null;
//        }
    }

    public Loan getLoanById(Long id) {
        return loanRepository.findById(id).orElse(null);
    }

    public void add(Loan loan) {
        loanRepository.save(loan);
    }

    public void update(Long id, Loan updatedLoan) {
        Loan loan = getLoanById(id);
        if (loan != null) {
            loan.setLoanAmount(updatedLoan.getLoanAmount());
            loan.setLoanTerm(updatedLoan.getLoanTerm());
            loan.setInterestRate(updatedLoan.getInterestRate());
            loan.setDateOfGive(updatedLoan.getDateOfGive());
            loan.setDateOfTotalRepayment(updatedLoan.getDateOfTotalRepayment());
            loanRepository.save(loan);
        }
    }

    public void delete(Long id) {
        loanRepository.delete(getLoanById(id));
    }
}

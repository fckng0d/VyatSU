package ru.fckng0d.boot.loan.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.fckng0d.boot.loan.entities.Client;
import ru.fckng0d.boot.loan.entities.Loan;
import ru.fckng0d.boot.loan.repositories.LoanRepository;
import ru.fckng0d.boot.loan.specifications.ClientSpecification;
import ru.fckng0d.boot.loan.specifications.LoanSpecification;

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

    public Page<Loan> getAllLoans(Long clientId, Pageable pageable) {
        Specification<Loan> specification = Specification
                .where(LoanSpecification.hasClientId(clientId));
        return loanRepository.findAll(specification, pageable);
    }

    public Page<Loan> getAllLoans(Long clientId, String from, String to, String loanTerm, String dateOfGive, Pageable pageable) {
        Specification<Loan> specification = Specification
                .where(LoanSpecification.amountBetween(from, to))
                .and(LoanSpecification.hasClientId(clientId))
                .and(LoanSpecification.hasLoanTerm(loanTerm))
                .and(LoanSpecification.hasDateOfGive(dateOfGive));
        return loanRepository.findAll(specification, pageable);
    }

    public List<Loan> getTopLoans(Long clientId) {
        Pageable topLoans = PageRequest.of(0, 3,
                Sort.by(Sort.Order.desc("loanAmount")));
        Specification<Loan> specification = Specification
                .where(LoanSpecification.hasClientId(clientId));
        return loanRepository.findAll(specification, topLoans).getContent();
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

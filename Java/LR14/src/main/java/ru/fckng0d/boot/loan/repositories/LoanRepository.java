package ru.fckng0d.boot.loan.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fckng0d.boot.loan.entities.Loan;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
}

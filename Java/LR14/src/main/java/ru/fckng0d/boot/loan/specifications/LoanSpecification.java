package ru.fckng0d.boot.loan.specifications;

import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ru.fckng0d.boot.loan.entities.Loan;

@Component
public class LoanSpecification {
    public static Specification<Loan> hasClientId(Long clientId) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("client").get("clientId"), clientId));
    }

    public static Specification<Loan> amountBetween(String from, String to) {
        return ((root, query, criteriaBuilder) -> {
            if ((from == null || from.isBlank()) && (to == null || to.isBlank())) {
                return criteriaBuilder.conjunction();
            }

            Path<Double> loanAmount = root.get("loanAmount");
            Predicate predicate = criteriaBuilder.conjunction();

            if (from != null && !from.isBlank()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(
                        loanAmount, Double.parseDouble(from)));
            }

            if (to != null && !to.isBlank()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(
                        loanAmount, Double.parseDouble(to)));
            }

            return predicate;
        });
    }

    public static Specification<Loan> hasLoanTerm(String loanTerm) {
        return ((root, query, criteriaBuilder) -> {
            if (loanTerm == null || loanTerm.isBlank()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("loanTerm"), Integer.parseInt(loanTerm));
        });
    }

    public static Specification<Loan> hasDateOfGive(String dateOfGive) {
        return ((root, query, criteriaBuilder) -> {
            if (dateOfGive == null || dateOfGive.isBlank()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("dateOfGive"), dateOfGive);
        });
    }
}

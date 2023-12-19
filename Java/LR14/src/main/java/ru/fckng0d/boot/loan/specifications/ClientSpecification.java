package ru.fckng0d.boot.loan.specifications;

import jakarta.persistence.criteria.Expression;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ru.fckng0d.boot.loan.entities.Client;

@Component
public class ClientSpecification {
    public static Specification<Client> likeLastName(String lastName) {
        return ((root, query, criteriaBuilder) -> {
            if (lastName == null || lastName.isBlank()) {
                return criteriaBuilder.conjunction();
            }
            Expression<String> lowerLastName = criteriaBuilder.lower(root.get("lastName"));
            Expression<String> lowerSearchTerm = criteriaBuilder
                    .lower(criteriaBuilder.literal("%" + lastName + "%"));
            return criteriaBuilder.like(lowerLastName, lowerSearchTerm);
        });
    }

    public static Specification<Client> hasBirthDate(String birthDate) {
        return ((root, query, criteriaBuilder) -> {
            if (birthDate == null || birthDate.isBlank()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(root.get("birthDate"), "%" + birthDate + "%");
        });
    }

    public static Specification<Client> likePassport(String passport) {
        return ((root, query, criteriaBuilder) -> {
            if (passport == null || passport.isBlank()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(root.get("passport"), "%" + passport + "%");
        });
    }
}

package ru.fckng0d.boot.loan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class LoanApplication {
	public static void main(String[] args) {
		SpringApplication.run(LoanApplication.class, args);
	}
}

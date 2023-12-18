package ru.fckng0d.boot.loan.security;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("@clientAccessEvaluator.canAccessClient(authentication, #clientId)")
public @interface ClientAccess {
}

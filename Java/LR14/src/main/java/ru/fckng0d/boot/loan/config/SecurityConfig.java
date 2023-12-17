package ru.fckng0d.boot.loan.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.fckng0d.boot.loan.entities.User;
import ru.fckng0d.boot.loan.services.UserService;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Collection;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfiguration {
    private final UserService userService;
    private final DataSource dataSource;

    @Autowired
    public SecurityConfig(UserService userService, DataSource dataSource) {
        this.userService = userService;
        this.dataSource = dataSource;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        CookieClearingLogoutHandler cookies = new CookieClearingLogoutHandler("our-custom-cookie");
        http.authorizeHttpRequests( authorize -> authorize
                        .requestMatchers("/clients").hasAnyRole("ADMIN")
                        .requestMatchers("/clients/{id:\\d+}").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/clients/new").hasAnyRole("ADMIN")
                        .requestMatchers("/clients/filter**").hasAnyRole("ADMIN")
                        .requestMatchers("/clients/{id:\\d+}/loans/filter**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/clients/{id:\\d+}/loans").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/clients/{id}/loans/{loanId}").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/clients/{id}/loans/{loanId}/new").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/clients/{clientId}/loans/{loanId}/delete").hasAnyRole("ADMIN")
                        .requestMatchers("/clients/{clientId}/loans/{loanId}/edit").hasAnyRole("ADMIN")
                        .anyRequest().permitAll())
                .formLogin((form) -> form
                        .loginPage("/login")
                        .successHandler(new CustomAuthenticationSuccessHandler())
//                        .loginProcessingUrl("/")
                        .permitAll())
                .logout((logout) -> logout
                        .invalidateHttpSession(true)
                        .logoutSuccessUrl("/logout")
                        .deleteCookies("JSESSIONID")
                        .clearAuthentication(true)
                        .permitAll());
        return http.build();
    }

    public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                if (authority.getAuthority().equals("ROLE_ADMIN")) {
                    response.sendRedirect("/clients");
                    return;
                } else if (authority.getAuthority().equals("ROLE_USER")) {
                    Long clientId = userService.getClientIdByAuthentication(authentication, dataSource);
                    response.sendRedirect("/clients/" + clientId);
                    return;
                }
            }
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource);
    }


//    @Bean
//    public UserDetailsService userDetailsService(UserService userService) {
//        return new UserDetailsService() {
//            @Override
//            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//                User user = userService.getUserByUserName(username);
//                if (user == null) {
//                    System.out.println("User not found");
//                    throw new UsernameNotFoundException("User not found");
//                }
//                userService.encode(username);
//                return org.springframework.security.core.userdetails.User
//                        .withUsername(username)
//                        .password(user.getPassword())
//                        .roles(userService.getRoleByUsername(username))
//                        .build();
//            }
//        };
//    }
}


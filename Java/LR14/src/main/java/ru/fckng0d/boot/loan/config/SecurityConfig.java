package ru.fckng0d.boot.loan.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import ru.fckng0d.boot.loan.entities.User;
import ru.fckng0d.boot.loan.services.UserService;

import java.io.IOException;
import java.util.Collection;


@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfiguration {
    private final UserService userService;

    @Autowired
    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/clients").hasAnyRole("USER")
                        .requestMatchers("/clients/{id}").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/clients/new").hasAnyRole("USER")
//                        .requestMatchers("/clients/{clientId}/loans/{loanId}/delete").hasAnyRole("ADMIN")
//                        .requestMatchers("/clients/{clientId}/loans/{loanId}/edit").hasAnyRole("ADMIN")
                        .anyRequest().permitAll())
                .formLogin((form) -> form
                        .loginPage("/hello")
                        .loginProcessingUrl("/")
//                        .successHandler(new AuthenticationSuccessHandler() {
//                            @Override
//                            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException, IOException {
//                                Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//                                for (GrantedAuthority authority : authorities) {
//                                    System.out.println(authority.getAuthority());
//                                    if (authority.getAuthority().equals("ADMIN")) {
//                                        response.sendRedirect("/clients");
//                                        return;
//                                    } else if (authority.getAuthority().equals("USER")) {
//                                        Long clientId = userService.clientIdByUserName(authentication.getName());
//                                        response.sendRedirect("/clients/" + clientId);
//                                        return;
//                                    }
//                                }
//                            }
//                        })
                        .permitAll())
                .logout((logout) -> logout
                        .logoutSuccessUrl("/hello").permitAll());
        return http.build();
    }

//    @Bean
//    public UserDetailsService userDetailsService(DataSource dataSource) {
//        JdbcUserDetailsManager manager = new JdbcUserDetailsManager();
//        manager.setDataSource(dataSource);
//        manager.setUsersByUsernameQuery("SELECT username, password FROM user_t WHERE username = ?");
//        manager.setAuthoritiesByUsernameQuery("SELECT username, authority FROM authorities WHERE username = ?");
//        return manager;
//    }

    @Bean
    public UserDetailsService userDetailsService(UserService userService) {
//        System.out.println("userDetails");
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                User user = userService.getUserByUserName(username);
                if (user == null) {
                    System.out.println("User not found");
                    throw new UsernameNotFoundException("User not found");
                }
                return org.springframework.security.core.userdetails.User
                        .withUsername(username)
                        .password(userService.getPasswordByUserName(username))
                        .roles(userService.getRoleByUsername(username))
                        .build();
//                        .withUsername("admin")
//                        .password("pass")
//                        .roles("ADMIN")
//                        .build();
            }
        };
    }
}


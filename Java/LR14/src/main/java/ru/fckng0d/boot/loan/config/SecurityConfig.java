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
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.jdbc.core.JdbcTemplate;
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
        http.authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/clients").hasAnyRole("ADMIN")
//                        .requestMatchers("/clients/{id}").hasAnyRole("ADMIN")
                        .requestMatchers("/clients/new").hasAnyRole("ADMIN")
                        .requestMatchers("/clients/{clientId}/loans/{loanId}/delete").hasAnyRole("ADMIN")
                        .requestMatchers("/clients/{clientId}/loans/{loanId}/edit").hasAnyRole("ADMIN")
                        .anyRequest().permitAll())
                .formLogin((form) -> form
                        .loginPage("/login")
                        .successHandler(new CustomAuthenticationSuccessHandler())
//                        .loginProcessingUrl("/")
//                        .defaultSuccessUrl("/clients")
                        .permitAll())
                .logout((logout) -> logout
                        .logoutSuccessUrl("/logout").permitAll());
        return http.build();
    }

    public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                if (authority.getAuthority().equals("ROLE_ADMIN")) {
                    response.sendRedirect("/clients");
                    System.out.println("GBPLFFG");
                    return;
                } else if (authority.getAuthority().equals("ROLE_USER")) {
                    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
                    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                    String username = userDetails.getUsername();

                    String clientIdQuery = "SELECT c.client_id FROM client c WHERE c.username = ?";
                    Long clientId = jdbcTemplate.queryForObject(clientIdQuery, Long.class, username);

                    response.sendRedirect("/clients/" + clientId);
                    return;
                }
            }
        }
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userService);
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
////        System.out.println("userDetails");
//        return new UserDetailsService() {
//            @Override
//            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//                User user = userService.getUserByUserName(username);
//                if (user == null) {
//                    System.out.println("User not found");
//                    throw new UsernameNotFoundException("User not found");
//                }
//                return org.springframework.security.core.userdetails.User
//                        .withUsername(username)
//                        .password(userService.getPasswordByUserName(username))
//                        .roles(userService.getRoleByUsername(username))
//                        .build();
////                        .withUsername("admin")
////                        .password("pass")
////                        .roles("ADMIN")
////                        .build();
//            }
//        };
//    }
}


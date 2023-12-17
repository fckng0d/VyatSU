package ru.fckng0d.boot.loan.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.fckng0d.boot.loan.entities.User;
import ru.fckng0d.boot.loan.repositories.UserRepository;

import javax.sql.DataSource;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final AuthorityService authorityService;

    @Autowired
    public UserService(UserRepository userRepository, AuthorityService authorityService) {
        this.userRepository = userRepository;
        this.authorityService = authorityService;
    }

    public User getUserByUserName(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public String getPasswordByUserName(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.map(User::getPassword).orElse(null);
    }

//    public Long clientIdByUserName(String username) {
//        User user = getUserByUserName(username);
//        return clientService.getClientByUser(user).getClientId();
//    }

    public String getRoleByUsername(String username) {
        return authorityService.getRoleByUser(getUserByUserName(username));
    }

    public String getRealNameByUsername(String username) {
        return getUserByUserName(username).getRealName();
    }

    public Long getClientIdByAuthentication(Authentication authentication, DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        String clientIdQuery = "SELECT c.client_id FROM client c WHERE c.username = ?";
        return jdbcTemplate.queryForObject(clientIdQuery, Long.class, username);
    }

    public void encode(String username, PasswordEncoder passwordEncoder) {
        Optional<User> user2 = userRepository.findByUsername(username);
        if (user2.isPresent()) {
            User user = user2.get();
            String encrypt = passwordEncoder.encode(user.getPassword());
            user.setPassword(encrypt);
            userRepository.save(user);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUserByUserName(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
//        encode(username);

        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(),
                user.getAuthorityList()
                        .stream()
                        .map(role -> new SimpleGrantedAuthority(role.getRole()))
                        .collect(Collectors.toList())
        );

    }

    //    public UserDetailsService getUserDetailsServiceByUsername(String username) {
    //        return new UserDetailsService() {
    //            @Override
    //            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    //                User user = getUserByUserName(username);
    //                if (user == null) {
    //                    throw new UsernameNotFoundException("User not found");
    //                }
    //                return org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
    //                        .password(user.getPassword())
    //                        .roles(user.getAuthority().getRole())
    //                        .build();
    //            }
    //        };
    //    }
}

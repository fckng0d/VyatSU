package ru.fckng0d.boot.loan.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.fckng0d.boot.loan.entities.User;
import ru.fckng0d.boot.loan.repositories.UserRepository;

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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUserByUserName(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

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

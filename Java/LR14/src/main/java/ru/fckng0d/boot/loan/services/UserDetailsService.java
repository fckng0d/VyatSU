//package ru.fckng0d.boot.loan.services;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import ru.fckng0d.boot.loan.entities.User;
//import ru.fckng0d.boot.loan.repositories.UserRepository;
//
//import java.util.Optional;
//
//@Service
//public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
//    private final UserRepository userRepository;
//
//    @Autowired
//    public UserDetailsService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional<User> user = userRepository.findByUserName(username);
//
//        if (user.isEmpty())
//            throw new UsernameNotFoundException("User not found!");
//
//        return new User(user.get());
//    }
//}

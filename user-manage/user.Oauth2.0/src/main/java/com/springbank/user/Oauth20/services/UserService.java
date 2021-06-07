package com.springbank.user.Oauth20.services;

import com.springbank.user.Oauth20.repositories.UserRepository;
import com.springbank.user.core.model.Account;
import com.springbank.user.core.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(userName).orElseThrow(() -> new UsernameNotFoundException("Incorrect userName / password supplied"));
        Account account = user.getAccount();
        return org.springframework.security.core.userdetails.User
                .withUsername(userName)
                .password(account.getPassword())
                .authorities(account.getRole())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}

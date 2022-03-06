package com.sjarno.norascoffeeshop.services;

import java.util.Optional;
import java.util.stream.Collectors;

import com.sjarno.norascoffeeshop.models.UserAccount;
import com.sjarno.norascoffeeshop.repositories.UserAccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService{

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserAccount> user = userAccountRepository.findByUsername(username);
        if (user.isPresent()) {
            return new User(
                user.get().getUsername(),
                user.get().getPassword(),
                true,
                true,
                true,
                true,
                user.get().getRoles().stream()
                    .map(r ->
                        new SimpleGrantedAuthority(r.getRoleType().name())
                    )
                    .collect(Collectors.toList()));
        }
        throw new UsernameNotFoundException("No such username: "+username);
    }
    
}

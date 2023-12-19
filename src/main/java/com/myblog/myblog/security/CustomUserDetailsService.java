package com.myblog.myblog.security;

import com.myblog.myblog.entity.Role;
import com.myblog.myblog.entity.User;
import com.myblog.myblog.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepo) {
        this.userRepository = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        // Fetch user from the database or any other data source based on the usernameOrEmail
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail).
                orElseThrow(() ->
                        new UsernameNotFoundException("User Not Found with username or Email:"+usernameOrEmail)
                );
        // Return a UserDetails object (can be your custom UserDetails implementation)
        return new org.springframework.security.core.userdetails.User(  //require three things to login
                user.getEmail(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role>roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}

package com.accenture.test.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.accenture.test.domain.AccentureUser;
import com.accenture.test.repository.UserH2Repository;

import static java.util.Collections.emptyList;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserH2Repository userH2Repository;

    public UserDetailsServiceImpl(UserH2Repository userH2Repository) {
        this.userH2Repository = userH2Repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AccentureUser applicationUser = userH2Repository.findById(Long.valueOf(username)).get();
        if (applicationUser == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(applicationUser.getId().toString(), applicationUser.getPassword(), emptyList());
    }
}
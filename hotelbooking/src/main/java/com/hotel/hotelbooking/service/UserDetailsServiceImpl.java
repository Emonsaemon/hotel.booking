package com.hotel.hotelbooking.service;

import com.hotel.hotelbooking.model.User;
import com.hotel.hotelbooking.security.UserDetailsImpl;
import com.hotel.hotelbooking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.AccountNotFoundException;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserService userService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String login)
            throws UsernameNotFoundException {
        User user = userService.findByEmail(login);
        if (user == null) {
             throw new UsernameNotFoundException("User with login '"
                     + login + "' not found.");
        }
        return UserDetailsImpl.create(user, user.getRole());
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        User user = userService.findById(id);
        if (user == null) {
            throw new UsernameNotFoundException(
                    "User with id '" + id + "' not found."
            );
        }

        return UserDetailsImpl.create(user, user.getRole());
    }
}

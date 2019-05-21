package com.hotel.hotelbooking.service;

import com.hotel.hotelbooking.model.User;
import com.hotel.hotelbooking.model.UserRole;
import com.hotel.hotelbooking.security.JwtTokenProvider;
import com.hotel.hotelbooking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthenticationServiceImpl {

    private AuthenticationManager authenticationManager;
    private JwtTokenProvider tokenProvider;
    private UserService userService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationServiceImpl(AuthenticationManager authenticationManager,
                                     JwtTokenProvider tokenProvider,
                                     UserService userService,
                                     PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    public String login(User user) {
        return authenticate(user.getEmail(), user.getPassword());
    }

    public String register(User user) throws Exception {
        try {

            if (userService.emailExists(user.getEmail())) {
                throw new Exception("User e-mail already in "
                        + "use!");
            }

            String login = user.getEmail();
            String password = user.getPassword();
            user.setRole(UserRole.USER);
            user.setPassword(passwordEncoder.encode(password));
            userService.create(user);

            return authenticate(login, password);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    private String authenticate(String login, String password) {
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(
                        login, password
                );

        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(token);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);

        return "Bearer " + jwt;
    }
}

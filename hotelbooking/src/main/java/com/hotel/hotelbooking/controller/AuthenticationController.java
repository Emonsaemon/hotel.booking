package com.hotel.hotelbooking.controller;

import com.hotel.hotelbooking.dto.Token;
import com.hotel.hotelbooking.dto.UserDTO;
import com.hotel.hotelbooking.dto.converter.UserConverter;
import com.hotel.hotelbooking.model.User;
import com.hotel.hotelbooking.security.AuthenticationConstant;
import com.hotel.hotelbooking.service.AuthenticationServiceImpl;
import com.hotel.hotelbooking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/authentication")
public class AuthenticationController {

    @Value("${app.cookieExpirationInS}")
    private int cookieExpiration;

    @Autowired
    AuthenticationServiceImpl authenticationService;

    @Autowired
    UserService userService;

    @Autowired
    UserConverter userConverter;

    @PostMapping("/signin")
    public ResponseEntity<Token> authenticateUser(HttpServletResponse response,
            @Valid @RequestBody UserDTO signInFormDTO) {
        User user = userConverter.convertToEntity(signInFormDTO);
        String token = authenticationService.login(user);
        Token authToken = new Token(token);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(AuthenticationConstant
                .AUTHENTICATION_TOKEN_HEADER, token);
        response.addCookie(createCookie(token));
        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(authToken);
    }

    @PostMapping("/signup")
    public ResponseEntity<Token> registerUser(HttpServletResponse response,
            @Valid @RequestBody UserDTO signUpFormDTO)
                throws Exception {

        User user = userConverter.convertToEntity(signUpFormDTO);
        String token = authenticationService.register(user);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(AuthenticationConstant
                .AUTHENTICATION_TOKEN_HEADER, token);
        response.addCookie(createCookie(token));
        Token authToken = new Token(token);
        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(authToken);
    }

    private Cookie createCookie(String token) {
        final Cookie cookie = new Cookie(AuthenticationConstant
                .AUTHENTICATION_TOKEN_HEADER, token);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(cookieExpiration);
        return cookie;
    }
}

package com.hotel.hotelbooking.dto;

public class Token {
    String token;
    String prefix;

    public Token(String token) {
        this.token = token;
        this.prefix = "Bearer ";
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}

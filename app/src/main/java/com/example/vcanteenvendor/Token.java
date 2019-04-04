package com.example.vcanteenvendor;

public class Token {

    private String email;
    private String token;

    public Token(String email, String token) {
        this.email = email;
        this.token = token;
    }

    @Override
    public String toString() {
        return "Token{" +
                "email='" + email + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}

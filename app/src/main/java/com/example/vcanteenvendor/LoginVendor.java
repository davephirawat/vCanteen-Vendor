package com.example.vcanteenvendor;

public class LoginVendor {
    private String email;
    private String password;

    public LoginVendor(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginVendor{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

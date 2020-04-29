package com.corvid.tulip.admin.service.admin;

public class LoginObject {

    private String email;

    private String password;

    public LoginObject() {
    }

    /**
     * @param email
     * @param password
     */
    public LoginObject(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

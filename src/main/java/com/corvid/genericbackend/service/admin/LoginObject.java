package com.corvid.genericbackend.service.admin;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class LoginObject {
    private String username;

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

    public String getUsername() {
        return username;
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

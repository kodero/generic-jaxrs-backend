package com.corvid.genericbackend.service.admin;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created with IntelliJ IDEA.
 * User: user
 * Date: 2/28/14
 * Time: 2:33 PM
 * To change this template use File | Settings | File Templates.
 */
@XmlRootElement(name = "loginResult")
public class LoginResult {
    private String encryptionKey;

    private String userGuid;

    private String status;

    public LoginResult(String encryptionKey, String userGuid, String status) {
        this.encryptionKey = encryptionKey;
        this.userGuid = userGuid;
        this.status = status;
    }

    public LoginResult() {
    }

    public String getEncryptionKey() {
        return encryptionKey;
    }

    public void setEncryptionKey(String encryptionKey) {
        this.encryptionKey = encryptionKey;
    }

    public String getUserGuid() {
        return userGuid;
    }

    public void setUserGuid(String userGuid) {
        this.userGuid = userGuid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "LoginResult{" +
                "encryptionKey='" + encryptionKey + '\'' +
                ", userGuid='" + userGuid + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}

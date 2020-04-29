package com.corvid.tulip.admin.model.admin;

import com.corvid.tulip.common.model.TulipModelBase;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Stores authentication tokens. These will be device specific
 */

@Entity
@Table(name = "auth_tokens")
public class AuthToken extends TulipModelBase {

    @JoinColumn(name = "user", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(name = "issued_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date issuedAt;

    @Column(name = "expiry")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiry;

    @NotNull
    @Column(name = "access_token")
    private String accessToken;

    @NotNull
    @Column(name = "refresh_token")
    private String refreshToken;

    @NotNull
    @Column(name = "client_device")
    private String clientDevice;

    @Column(name = "valid", columnDefinition = "TINYINT", length = 1)
    private Boolean valid;

    public AuthToken(){

    }

    public AuthToken(User user, Date issuedAt, Date expiry, String accessToken, String refreshToken, String clientDevice){
        setUser(user);
        setIssuedAt(issuedAt);
        setExpiry(expiry);
        setAccessToken(accessToken);
        setRefreshToken(refreshToken);
        setClientDevice(clientDevice);
        setValid(true);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(Date issuedAt) {
        this.issuedAt = issuedAt;
    }

    public Date getExpiry() {
        return expiry;
    }

    public void setExpiry(Date expiry) {
        this.expiry = expiry;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getClientDevice() {
        return clientDevice;
    }

    public void setClientDevice(String clientDevice) {
        this.clientDevice = clientDevice;
    }

    public Boolean isValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }
}

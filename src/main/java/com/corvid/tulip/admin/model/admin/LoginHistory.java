package com.corvid.tulip.admin.model.admin;

import com.corvid.tulip.admin.model.base.ModelBase;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "login_history")
public class LoginHistory extends ModelBase {

    @JoinColumn(name = "user", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(name = "current_login_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date currentLoginAt;

    @Size(max = 255)
    @Column(name = "current_login_ip")
    private String currentLoginIp;

    @Size(max = 255)
    @Column(name = "last_login_ip")
    private String lastLoginIp;

    //TODO
    /* capture device ID & user agent string */

    public Date getCurrentLoginAt() {
        return currentLoginAt;
    }

    public void setCurrentLoginAt(Date currentLoginAt) {
        this.currentLoginAt = currentLoginAt;
    }

    public String getCurrentLoginIp() {
        return currentLoginIp;
    }

    public void setCurrentLoginIp(String currentLoginIp) {
        this.currentLoginIp = currentLoginIp;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

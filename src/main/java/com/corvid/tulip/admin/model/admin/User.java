package com.corvid.tulip.admin.model.admin;

import com.corvid.tulip.admin.model.enums.PasswordResetStatus;
import org.hibernate.annotations.Filter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * @author mokua
 */
@Entity
@Table(name = "users")
@DiscriminatorValue("ADMIN_MODULE")
@Filter(name = "filterByDeleted")
public class User extends com.corvid.tulip.common.model.User {

    public static final String[] USER_SESSION_ATTRIBUTES = new String[]{"id", "firstName", "lastName", "email"};

    @Size(max = 400)
    private String salutation;

    @Size(max = 20)
    private String prefix;

    @Size(max = 40)
    @Column(name = "time_zone")
    private String timeZone;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "password")
    private String password;

    @Column(name = "single_access_token")
    @Temporal(TemporalType.TIMESTAMP)
    private Date singleAccessToken;

    @Column(name = "failed_login_count")
    private Integer failedLoginCount;

    @NotNull
    @Column(name = "mobile")
    private String mobile;

    /**
     * flag to check if user is active
     */
    @Column(name = "active", nullable = false)
    private Boolean active = false;

    /**
     * has the use activated this account?
     */
    @Column(name = "account_confirmed", nullable = false)
    private Boolean accountConfirmed = false;

    @Column(name = "activation_code", length = 255, nullable = true)
    private String activationCode;

    @Column(name = "password_reset_status")
    @Enumerated(EnumType.STRING)
    private PasswordResetStatus passwordResetStatus;

    @Column(name = "password_reset_last_state_change_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date passwordResetLastStateChangeAt;

    @JoinColumn(name = "user_profile", referencedColumnName = "id")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Profile profile;

    @JoinColumn(name = "current_org", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Organization currentOrganization;

    @JoinColumn(name = "current_profile", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Profile currentProfile;

    public User() {
    }

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public PasswordResetStatus getPasswordResetStatus() {
        return passwordResetStatus;
    }

    public void setPasswordResetStatus(PasswordResetStatus passwordResetStatus) {
        this.passwordResetStatus = passwordResetStatus;
    }

    public Date getPasswordResetLastStateChangeAt() {
        return passwordResetLastStateChangeAt;
    }

    public void setPasswordResetLastStateChangeAt(Date passwordResetLastStateChangeAt) {
        this.passwordResetLastStateChangeAt = passwordResetLastStateChangeAt;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSalutation() {
        return salutation;
    }

    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public Date getSingleAccessToken() {
        return singleAccessToken;
    }

    public void setSingleAccessToken(Date singleAccessToken) {
        this.singleAccessToken = singleAccessToken;
    }

    public Integer getFailedLoginCount() {
        return failedLoginCount;
    }

    public void setFailedLoginCount(Integer failedLoginCount) {
        this.failedLoginCount = failedLoginCount;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profileId) {
        this.profile = profileId;
    }

    public Boolean getAccountConfirmed() {
        return accountConfirmed;
    }

    public void setAccountConfirmed(Boolean accountConfirmed) {
        this.accountConfirmed = accountConfirmed;
    }

    public Organization getCurrentOrganization() {
        return currentOrganization;
    }

    public void setCurrentOrganization(Organization currentOrganization) {
        this.currentOrganization = currentOrganization;
    }

    public Profile getCurrentProfile() {
        return currentProfile;
    }

    public void setCurrentProfile(Profile currentProfile) {
        this.currentProfile = currentProfile;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "User{" +
                "activationCode='" + activationCode + '\'' +
                ", active=" + active +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}

package com.corvid.genericbackend.model.admin;

import com.corvid.genericbackend.model.base.ModelBase;
import org.codehaus.jackson.annotate.JsonCreator;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.Filters;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * @author mokua
 */
@Entity
@Table(name = "users",
        uniqueConstraints =
        @UniqueConstraint(columnNames = {"email"}))
@XmlRootElement
@Filters({
        @Filter(name = "filterByDeleted")
})

@NamedQueries({})
public class User extends ModelBase {

    public static final String[] USER_SESSION_ATTRIBUTES = new String[]{"firstName", "lastName", "email", "id", "lastLoginAt", "lastLoginIp", "userCategory", "userProfile:id", "userProfile:profileType"};

    public static final String TOKEN = "token";

    public static final String CURRENT_USER_JSON_SESSION_ATTRIBUTE = "currentUser";

    public static final String CURRENT_USER_OBJECT_SESSION_ATTRIBUTE = "currentUserObject";


    //TODO
    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }

    public static enum UserCategory {
        ADMIN("Admin"),
        APPLICANT("Applicant"),
        REVIEWER("Reviewer");

        private String name;

        UserCategory(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }

        @JsonCreator
        public static UserCategory create(String value) {
            if (value == null) {
                throw new IllegalArgumentException();
            }
            for (UserCategory v : values()) {
                if (value.equals(v.getName())) {
                    return v;
                }
            }
            throw new IllegalArgumentException();
        }

        public String getName() {
            return name;
        }
    }


    @Column(name = "user_category")
    @Enumerated(EnumType.STRING)
    @NotNull
    private UserCategory userCategory;

    @Lob
    @Size(max = 65535)
    @Column(name = "roles_text")
    private String rolesText;


    @Size(max = 400)
    @Column(name = "first_name", nullable = false)
    @NotNull
    private String firstName;

    @Size(max = 400)
    @Column(name = "last_name", nullable = false)
    @NotNull
    private String lastName;

    /**
     * this is also the username, should be unique
     */
    //@Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message = "Invalid email")
    @Size(max = 250)
    @NotNull
    @Column(name = "email", length = 250, nullable = false)
    private String email;

    @Size(max = 400)
    @Column(name = "personal_email")
    private String personalEmail;

    @Size(max = 400)
    private String salutation;

    @Size(max = 400)
    private String prefix;

    @Column(name = "last_logged_in_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLoggedInAt;

    @Size(max = 40)
    @Column(name = "time_zone")
    private String timeZone;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "crypted_password")
    private String cryptedPassword;

    @Size(max = 255)
    @Column(name = "persistence_token")
    private String persistenceToken;

    @Column(name = "single_access_token")
    @Temporal(TemporalType.TIMESTAMP)
    private Date singleAccessToken;

    @Column(name = "confirmation_sent_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date confirmationSentAt;

    @Column(name = "login_count")
    private Integer loginCount;

    @Column(name = "failed_login_count")
    private Integer failedLoginCount;

    @Column(name = "current_login_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date currentLoginAt;

    @Column(name = "last_login_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLoginAt;

    @Size(max = 255)
    @Column(name = "current_login_ip")
    private String currentLoginIp;

    @Size(max = 255)
    @Column(name = "last_login_ip")
    private String lastLoginIp;

    @Column(name = "mobile")
    private String mobile;

    @Transient
    private String currentPassword;

    @Transient
    private String newPassword;

    @Transient
    private String confirmPassword;

    /**
     * flag to check if user is active
     */
    @Column(name = "active", nullable = false)
    private Boolean active = false;

    /**
     * has the account been approved by the manager?
     */
    @Column(name = "approved", nullable = false)
    private Boolean approved = false;

    /**
     * has the use activated this account?
     */
    @Column(name = "confirmed", nullable = false)
    private Boolean confirmed = false;

    @Column(name = "activation_code", length = 255, nullable = true)
    private String activationCode;

    //some temp fields
    @Transient
    String password;

    @Transient
    String passwordControl;

    @Transient
    String requestedUsername;

    //email verification stuff
    public static enum EmailVerificationStatus {
        SENT("Sent"),//an email was sent in response to a verification initiate request
        RESENT("Resent"),//multiple emails were sent in response to verification initiate requests
        DELIVERED("Delivered"), //the email was successfully delivered to the user's mailbox (as reported by the remote server)
        BOUNCE("Bounce"),//an email previously sent was bounced back by the remote server and couldn't be delivered
        DEFERRED("Deferred"),//the mail system ran into transient problems delivering the email and deferred further attempts
        DROPPED("Dropped"),//the mail system dropped the email because of errors
        CONFIRMED("Confirmed");// the user opened the email and clicked on the link enclosed in the email

        private String name;


        EmailVerificationStatus(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }

        @JsonCreator
        public static EmailVerificationStatus create(String value) {
            if (value == null) {
                throw new IllegalArgumentException();
            }
            for (EmailVerificationStatus v : values()) {
                if (value.equals(v.getName())) {
                    return v;
                }
            }
            throw new IllegalArgumentException();
        }

        public String getName() {
            return name;
        }
    }


    //*******************************
    // password reset
    //****************************
    public static enum PasswordResetStatus {
        IN_PROGRESS("In Progress"),
        DONE("Done");

        private String name;

        PasswordResetStatus(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }

        @JsonCreator
        public static PasswordResetStatus create(String value) {
            if (value == null) {
                throw new IllegalArgumentException();
            }
            for (PasswordResetStatus v : values()) {
                if (value.equals(v.getName())) {
                    return v;
                }
            }
            throw new IllegalArgumentException();
        }

        public String getName() {
            return name;
        }
    }

    @Column(name = "password_reset_status")
    @Enumerated(EnumType.STRING)
    private PasswordResetStatus passwordResetStatus;

    @Column(name = "password_reset_last_state_change_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date passwordResetLastStateChangeAt;


    @JoinColumn(name = "user_role", referencedColumnName = "id")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Role role;

    @JoinColumn(name = "user_profile", referencedColumnName = "id")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private UserProfile userProfile;

    @OneToMany(/*cascade = CascadeType.ALL,*/mappedBy = "user")
    @org.hibernate.annotations.OrderBy(clause = "id asc")
    @Filter(name = "filterByDeleted")
    private Collection<UserPermission> userPermissions = new ArrayList<>();

    public User() {
    }

    public User(String firstName, String lastName, String email, String cryptedPassword) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.cryptedPassword = cryptedPassword;
    }

    public User(String firstName, String lastName, String email, String cryptedPassword, UserCategory userCategory) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.cryptedPassword = cryptedPassword;
        this.userCategory = userCategory;
    }

    public Collection<UserPermission> getUserPermissions() {
        return userPermissions;
    }

    public void setUserPermissions(Collection<UserPermission> userPermissions) {
        this.userPermissions = userPermissions;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public UserCategory getUserCategory() {
        return userCategory;
    }

    public void setUserCategory(UserCategory userCategory) {
        this.userCategory = userCategory;
    }

    public String getRolesText() {
        return rolesText;
    }

    public void setRolesText(String rolesText) {
        this.rolesText = rolesText;
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

    public Date getLastLoginAt() {
        return lastLoginAt;
    }

    public void setLastLoginAt(Date lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRequestedUsername() {
        return requestedUsername;
    }

    public PasswordResetStatus getPasswordResetStatus() {
        return passwordResetStatus;
    }

    public void setPasswordResetStatus(PasswordResetStatus passwordResetStatus) {
        this.passwordResetStatus = passwordResetStatus;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Date getPasswordResetLastStateChangeAt() {
        return passwordResetLastStateChangeAt;
    }

    public void setPasswordResetLastStateChangeAt(Date passwordResetLastStateChangeAt) {
        this.passwordResetLastStateChangeAt = passwordResetLastStateChangeAt;
    }

    public void setRequestedUsername(String requestedUsername) {
        this.requestedUsername = requestedUsername;
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

    public String getPasswordControl() {
        return passwordControl;
    }

    public void setPasswordControl(String passwordControl) {
        this.passwordControl = passwordControl;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPersonalEmail() {
        return personalEmail;
    }

    public void setPersonalEmail(String personalEmail) {
        this.personalEmail = personalEmail;
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

    public Date getLastLoggedInAt() {
        return lastLoggedInAt;
    }

    public void setLastLoggedInAt(Date lastLoggedInAt) {
        this.lastLoggedInAt = lastLoggedInAt;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getCryptedPassword() {
        return cryptedPassword;
    }

    public void setCryptedPassword(String cryptedPassword) {
        this.cryptedPassword = cryptedPassword;
    }

    public String getPersistenceToken() {
        return persistenceToken;
    }

    public void setPersistenceToken(String persistenceToken) {
        this.persistenceToken = persistenceToken;
    }

    public Date getSingleAccessToken() {
        return singleAccessToken;
    }

    public void setSingleAccessToken(Date singleAccessToken) {
        this.singleAccessToken = singleAccessToken;
    }

    public Date getConfirmationSentAt() {
        return confirmationSentAt;
    }

    public void setConfirmationSentAt(Date confirmationSentAt) {
        this.confirmationSentAt = confirmationSentAt;
    }

    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }

    public Integer getFailedLoginCount() {
        return failedLoginCount;
    }

    public void setFailedLoginCount(Integer failedLoginCount) {
        this.failedLoginCount = failedLoginCount;
    }

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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfileId) {
        this.userProfile = userProfileId;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
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
                ", approved=" + approved +
                ", firstName='" + firstName + '\'' +
                ", lastLoginAt=" + lastLoginAt +
                ", lastLoginIp='" + lastLoginIp + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

}

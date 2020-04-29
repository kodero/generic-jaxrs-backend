package com.corvid.tulip.admin.model.admin;

import com.corvid.tulip.admin.model.base.ModelBase;
import com.corvid.tulip.admin.model.enums.AccessGrantType;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "organization_access")
public class OrganizationAccess extends ModelBase {

    @JoinColumn(name = "user", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name = "organization", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Organization organization;

    @JoinColumn(name = "profile", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Profile profile;

    @Enumerated(EnumType.STRING)
    @Column(name = "access_grant_type", length = 30)
    private AccessGrantType accessGrantType;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "access_date_from")
    private Date accessDateFrom;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "access_date_to")
    private Date accessDateTo;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public AccessGrantType getAccessGrantType() {
        return accessGrantType;
    }

    public void setAccessGrantType(AccessGrantType accessGrantType) {
        this.accessGrantType = accessGrantType;
    }

    public Date getAccessDateFrom() {
        return accessDateFrom;
    }

    public void setAccessDateFrom(Date accessDateFrom) {
        this.accessDateFrom = accessDateFrom;
    }

    public Date getAccessDateTo() {
        return accessDateTo;
    }

    public void setAccessDateTo(Date accessDateTo) {
        this.accessDateTo = accessDateTo;
    }
}

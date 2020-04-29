package com.corvid.tulip.admin.model.admin;

import com.corvid.tulip.admin.model.base.ModelBase;
import com.corvid.tulip.common.model.TulipModelBase;
import org.hibernate.annotations.Filter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author mokua
 */
@Entity
@Table(name = "profile_permissions")
@Filter(name = "filterByDeleted")
public class ProfilePermission extends TulipModelBase {

    @JoinColumn(name = "permission", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Permission permission;

    @JoinColumn(name = "user_profile", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Profile profile;

    @NotNull
    @Column(name = "allowed")
    private Boolean allowed;

    public ProfilePermission() {
    }

    public ProfilePermission(Permission permission, Profile profile, Boolean allowed) {
        setPermission(permission);
        setProfile(profile);
        setAllowed(allowed);
    }

    public boolean getAllowed() {
        return allowed;
    }

    public void setAllowed(boolean allowed) {
        this.allowed = allowed;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profileId) {
        this.profile = profileId;
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
        if (!(object instanceof ProfilePermission)) {
            return false;
        }
        ProfilePermission other = (ProfilePermission) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public boolean isAllowed() {
        return allowed;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    @Override
    public String toString() {
        return "ProfilePermission[ id=" + id + " ]";
    }
}

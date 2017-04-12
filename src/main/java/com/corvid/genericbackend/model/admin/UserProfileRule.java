package com.corvid.genericbackend.model.admin;

import com.corvid.genericbackend.model.base.ModelBase;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author mokua
 */
@Entity
@Table(name = "user_profile_rules", schema = "")
@XmlRootElement
@org.hibernate.annotations.Filter(name = "filterByDeleted")
public class UserProfileRule extends ModelBase {

    @Size(max = 255)
    @Column(name = "permission_name")
    private String permissionName;

    @Basic(optional = false)
    @NotNull
    private boolean allowed;

    @Size(max = 255)
    @Column(name = "model_type")
    private String modelType;

    @JoinColumn(name = "permission", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Permission permission;

    @JoinColumn(name = "user_profile", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private UserProfile userProfile;

    public UserProfileRule() {
    }

    public UserProfileRule(Permission permission, UserProfile profile, Boolean allowed) {
        setPermission(permission);
        setUserProfile(profile);
        setAllowed(allowed);
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public boolean getAllowed() {
        return allowed;
    }

    public void setAllowed(boolean allowed) {
        this.allowed = allowed;
    }

    public String getModelType() {
        return modelType;
    }

    public void setModelType(String modelType) {
        this.modelType = modelType;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfileId) {
        this.userProfile = userProfileId;
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
        if (!(object instanceof UserProfileRule)) {
            return false;
        }
        UserProfileRule other = (UserProfileRule) object;
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
        return "UserProfileRule[ id=" + id + " ]";
    }
}

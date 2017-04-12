package com.corvid.genericbackend.model.admin;

import com.corvid.genericbackend.model.base.ModelBase;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author mokua
 */
@Entity
@Table(name = "user_permissions", schema = "")
@XmlRootElement
@org.hibernate.annotations.Filter(name = "filterByDeleted")
public class UserPermission extends ModelBase {

    @Column(name = "status")
    @NotNull
    private Boolean status = Boolean.FALSE;

    @JoinColumn(name = "user_id",/* insertable = false, updatable = false,*/ referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @JoinColumn(name = "permission_id", /*insertable = false, updatable = false,*/ referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Permission permission;

    public UserPermission() {
    }

    public UserPermission(Boolean status, User user, Permission permission) {
        this.status = status;
        this.user = user;
        this.permission = permission;


        // Guarantee referential integrity
        user.getUserPermissions().add(this);
        permission.getUserPermissions().add(this);
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserPermission)) return false;
        if (!super.equals(o)) return false;

        UserPermission that = (UserPermission) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }
}

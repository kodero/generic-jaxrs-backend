package com.corvid.genericbackend.model.admin;

import com.corvid.genericbackend.model.base.ModelBase;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Collection;

/**
 * @author mokua
 */
@Entity
@Table(name = "roles", schema = "")
@XmlRootElement
@org.hibernate.annotations.Filter(name = "filterByDeleted")
@NamedQueries({
        @NamedQuery(name = "Role.findAll", query = "SELECT r FROM Role r"),
        @NamedQuery(name = "Role.findById", query = "SELECT r FROM Role r WHERE r.id = :id"),
        @NamedQuery(name = "Role.findByCreatedAt", query = "SELECT r FROM Role r WHERE r.createdAt = :createdAt"),
        @NamedQuery(name = "Role.findByUpdatedAt", query = "SELECT r FROM Role r WHERE r.updatedAt = :updatedAt"),
        @NamedQuery(name = "Role.findByName", query = "SELECT r FROM Role r WHERE r.name = :name"),
        @NamedQuery(name = "Role.findByRoleableType", query = "SELECT r FROM Role r WHERE r.roleableType = :roleableType"),
        @NamedQuery(name = "Role.findByDeletedAt", query = "SELECT r FROM Role r WHERE r.deletedAt = :deletedAt")})
public class Role extends ModelBase {

    @Size(max = 255)
    private String name;

    @Size(max = 255)
    @Column(name = "roleable_type")
    private String roleableType;

    @Column(name = "update_days")
    private int updateDays;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    @org.hibernate.annotations.Filter(name = "filterByDeleted")
    private Collection<User> users;

    public Role() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoleableType() {
        return roleableType;
    }

    public void setRoleableType(String roleableType) {
        this.roleableType = roleableType;
    }

    public int getUpdateDays() {
        return updateDays;
    }

    public void setUpdateDays(int updateDays) {
        this.updateDays = updateDays;
    }

    @XmlTransient
    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> roleUserCollection) {
        this.users = roleUserCollection;
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
        if (!(object instanceof Role)) {
            return false;
        }
        Role other = (Role) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Role[ id=" + id + " ]";
    }

}

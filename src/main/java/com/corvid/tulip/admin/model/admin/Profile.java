package com.corvid.tulip.admin.model.admin;

import org.hibernate.annotations.Filter;

import javax.persistence.*;

/**
 * @author mokua
 */
@Entity
@Table(name = "profiles")
@DiscriminatorValue("ADMIN_MODULE")
@Filter(name = "filterByDeleted")
public class Profile extends com.corvid.tulip.common.model.Profile {

    @Column(name = "update_days")
    private Integer updateDays;

    public Profile() {
    }

    public Profile(String id, String name) {
        setId(id);
        setName(name);
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
        if (!(object instanceof Profile)) {
            return false;
        }
        Profile other = (Profile) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Profile[ id=" + id + " ]";
    }

    public Integer getUpdateDays() {
        return updateDays;
    }

    public void setUpdateDays(Integer updateDays) {
        this.updateDays = updateDays;
    }
}

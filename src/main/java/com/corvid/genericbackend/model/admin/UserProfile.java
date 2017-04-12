package com.corvid.genericbackend.model.admin;

import com.corvid.genericbackend.model.base.ModelBase;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author mokua
 */
@Entity
@Table(name = "user_profiles", schema = "", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@XmlRootElement
@org.hibernate.annotations.Filter(name = "filterByDeleted")
@NamedQueries({
        @NamedQuery(name = "UserProfile.findByName", query = "SELECT u FROM UserProfile u WHERE u.name = :name")
})
public class UserProfile extends ModelBase {

    @NotNull
    @Size(max = 255)
    private String name;

    @Column(name = "description")
    @Size(max = 255)
    @Length(max = 255)
    private String description;

    @Column(name = "update_days")
    private Integer updateDays;

    public UserProfile() {
    }

    public UserProfile(String id, String name) {
        setId(id);
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        if (!(object instanceof UserProfile)) {
            return false;
        }
        UserProfile other = (UserProfile) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "UserProfile[ id=" + id + " ]";
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getUpdateDays() {
        return updateDays;
    }

    public void setUpdateDays(Integer updateDays) {
        this.updateDays = updateDays;
    }
}

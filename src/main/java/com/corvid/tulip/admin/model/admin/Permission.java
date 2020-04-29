package com.corvid.tulip.admin.model.admin;

import com.corvid.tulip.admin.model.base.ModelBase;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
//import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author mokua
 */
@Entity
@Table(name = "permissions", schema = "")
@org.hibernate.annotations.Filter(name = "filterByDeleted")
public class Permission extends ModelBase {

     /**
     * Operation name e.g view,edit.create,delete etc
     */
    @Size(max = 255)
    @Column(name = "name")
    @NotNull
    private String name;

    @Size(max = 255)
    @Column(name = "description")
    private String description;

    @OneToMany(/*cascade = CascadeType.ALL,*/mappedBy = "permission")
    @org.hibernate.annotations.Filter(name = "filterByDeleted")
    private Collection<ProfilePermission> profilePermissions = new ArrayList<>();

    public Permission() {
    }

    public Permission(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Collection<ProfilePermission> getProfilePermissions() {
        return profilePermissions;
    }

    public void setProfilePermissions(Collection<ProfilePermission> profilePermissions) {
        this.profilePermissions = profilePermissions;
    }

    @Override
    public String toString() {
        return "Permission{" +
                ", permissionName='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

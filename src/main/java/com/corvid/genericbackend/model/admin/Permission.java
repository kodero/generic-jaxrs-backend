package com.corvid.genericbackend.model.admin;

import com.corvid.genericbackend.model.base.ModelBase;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author mokua
 */
@Entity
@Table(name = "permissions", schema = "")
@XmlRootElement
@org.hibernate.annotations.Filter(name = "filterByDeleted")
public class Permission extends ModelBase {

    /**
     * Entity FQN e.g com.lemr.model.admin.Permission
     */
    @Size(max = 255)
    @Column(name = "model_type")
    //@NotNull
    private String modelType;

    /**
     * Operation name e.g view,edit.create,delete etc
     */
    @Size(max = 255)
    @Column(name = "permission_name")
    @NotNull
    private String permissionName;

    @Size(max = 255)
    @Column(name = "description")
    private String description;

    /**
     * Grouping mechanism etc
     */
    @Size(max = 255)
    @Column(name = "namespace")
    //@NotNull
    private String namespace;

    @OneToMany(/*cascade = CascadeType.ALL,*/mappedBy = "permission")
    @org.hibernate.annotations.OrderBy(clause = "id asc")
    @org.hibernate.annotations.Filter(name = "filterByDeleted")
    private Collection<UserPermission> userPermissions = new ArrayList<>();

    @OneToMany(/*cascade = CascadeType.ALL,*/mappedBy = "permission")
    //@org.hibernate.annotations.OrderBy(clause = "id asc")
    @org.hibernate.annotations.Filter(name = "filterByDeleted")
    private Collection<UserProfileRule> userProfileRules = new ArrayList<>();

    public Permission() {
    }

    public Permission(String permissionName, String description) {
        this.permissionName = permissionName;
        this.description = description;
    }

    public Permission(String modelType, String permissionName, String description, String namespace) {
        this.modelType = modelType;
        this.permissionName = permissionName;
        this.description = description;
        this.namespace = namespace;
    }

    public Permission(String modelType, String permissionName, String namespace) {
        this.modelType = modelType;
        this.permissionName = permissionName;
        this.namespace = namespace;
    }

    public Collection<UserPermission> getUserPermissions() {
        return userPermissions;
    }

    public void setUserPermissions(Collection<UserPermission> userPermissions) {
        this.userPermissions = userPermissions;
    }

    public String getModelType() {
        return modelType;
    }

    public void setModelType(String modelType) {
        this.modelType = modelType;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public Collection<UserProfileRule> getUserProfileRules() {
        return userProfileRules;
    }

    public void setUserProfileRules(Collection<UserProfileRule> userProfileRules) {
        this.userProfileRules = userProfileRules;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "modelType='" + modelType + '\'' +
                ", permissionName='" + permissionName + '\'' +
                ", description='" + description + '\'' +
                ", namespace='" + namespace + '\'' +
                '}';
    }
}

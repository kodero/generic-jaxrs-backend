package com.corvid.tulip.admin.model.admin;

import com.corvid.bes.callbacks.EntityCallbackClass;
import com.corvid.tulip.admin.callbacks.OrganizationCallback;
import com.corvid.tulip.admin.model.base.ModelBase;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "organizations")
@DiscriminatorValue("ADMIN_MODULE")
@EntityCallbackClass(OrganizationCallback.class)
public class Organization extends com.corvid.tulip.common.model.Organization {

    @NotNull
    @Column(name = "name")
    private String name;

    @JoinColumn(name = "parent", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Organization parent;

    @JoinColumn(name = "organization_type", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private OrganizationType organizationType;

    //TODO
    /* add more details of an organization */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Organization getParent() {
        return parent;
    }

    public void setParent(Organization parent) {
        this.parent = parent;
    }

    public OrganizationType getOrganizationType() {
        return organizationType;
    }

    public void setOrganizationType(OrganizationType organizationType) {
        this.organizationType = organizationType;
    }
}

package com.corvid.tulip.admin.model.admin;

import com.corvid.tulip.common.model.TulipModelBase;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "organization_types")
public class OrganizationType extends TulipModelBase {

    @NotNull
    @Column(name = "name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

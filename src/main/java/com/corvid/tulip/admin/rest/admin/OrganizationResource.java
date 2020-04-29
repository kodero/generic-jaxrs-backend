package com.corvid.tulip.admin.rest.admin;

import com.corvid.tulip.admin.model.admin.Organization;
import com.corvid.tulip.common.rest.TulipBaseEntityResource;

import javax.ejb.Stateless;
import javax.ws.rs.Path;

@Stateless
@Path("/organizations")
public class OrganizationResource extends TulipBaseEntityResource<Organization> {

    public OrganizationResource (){
        this.entityClass = Organization.class;
    }
}

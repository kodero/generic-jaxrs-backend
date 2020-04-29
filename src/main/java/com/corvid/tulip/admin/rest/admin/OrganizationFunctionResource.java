package com.corvid.tulip.admin.rest.admin;

import com.corvid.tulip.admin.model.admin.OrganizationType;
import com.corvid.tulip.common.rest.TulipBaseEntityResource;

import javax.ejb.Stateless;
import javax.ws.rs.Path;

@Stateless
@Path("/organizationFunctions")
public class OrganizationFunctionResource extends TulipBaseEntityResource<OrganizationType> {
    public OrganizationFunctionResource(){
        this.entityClass = OrganizationType.class;
    }
}

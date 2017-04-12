package com.corvid.genericbackend.rest.admin;

import com.corvid.bes.rest.BaseEntityResource;
import com.corvid.genericbackend.model.admin.Role;

import javax.ws.rs.Path;

/**
 * @author mokua
 */
@Path("/roles")
@javax.ejb.Stateless
public class RoleResource extends BaseEntityResource<Role> {

    public RoleResource() {
        super(Role.class);
    }
}

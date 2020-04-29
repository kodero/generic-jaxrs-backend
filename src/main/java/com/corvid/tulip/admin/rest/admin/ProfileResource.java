package com.corvid.tulip.admin.rest.admin;

import com.corvid.bes.rest.BaseEntityResource;
import com.corvid.tulip.admin.model.admin.Profile;
import com.corvid.tulip.common.rest.TulipBaseEntityResource;

import javax.ejb.Stateless;
import javax.ws.rs.Path;

/**
 * Created by kodero on 4/5/14.
 */
@Stateless
@Path("/profiles")
public class ProfileResource extends TulipBaseEntityResource<Profile> {

    public ProfileResource() {
        this.entityClass = Profile.class;
    }
}

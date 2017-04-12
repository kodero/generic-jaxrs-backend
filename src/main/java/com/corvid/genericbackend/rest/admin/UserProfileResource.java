package com.corvid.genericbackend.rest.admin;

import com.corvid.bes.rest.BaseEntityResource;
import com.corvid.genericbackend.model.admin.UserProfile;

import javax.ejb.Stateless;
import javax.ws.rs.Path;

/**
 * Created by kodero on 4/5/14.
 */
@Stateless
@Path("/profiles")
public class UserProfileResource extends BaseEntityResource<UserProfile> {

    public UserProfileResource() {
        super(UserProfile.class);
    }
}

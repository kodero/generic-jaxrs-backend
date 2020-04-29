package com.corvid.tulip.admin.callbacks;

import com.corvid.bes.callbacks.During;
import com.corvid.bes.callbacks.EntityCallbackMethod;
import com.corvid.bes.service.GService;
import com.corvid.tulip.admin.model.admin.Organization;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class OrganizationCallback {

    @Inject
    GService gService;

    @EntityCallbackMethod(when = During.CREATE)
    public void onCreate(Organization organization){
        // create profiles, associate admin profile with org access
    }
}

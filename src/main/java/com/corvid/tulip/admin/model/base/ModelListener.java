package com.corvid.tulip.admin.model.base;

import com.corvid.tulip.admin.service.admin.AdminUserService;
import com.corvid.tulip.common.model.TulipModelBase;

import javax.inject.Inject;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import java.util.Date;

/**
 * @author mokua
 */

public class ModelListener {

    @Inject
    AdminUserService adminUserService;

    @PrePersist
    public void createEntity(Object entity) {
        //set the create attributes
        TulipModelBase modelBase = (TulipModelBase) entity;
        modelBase.setCreatedAt(new Date());
        if(adminUserService.currentUser() != null){
            modelBase.setCreatedBy(adminUserService.currentUser());
            /* only set at creation time, and is never changed */
            modelBase.setTenant(adminUserService.currentUser().getCurrentOrganization());
        }
    }

    @PreUpdate
    public void updateEntity(Object entity) {
        //set the update attributes
        TulipModelBase modelBase = (TulipModelBase) entity;
        modelBase.setUpdatedAt(new Date());
        if(adminUserService.currentUser() != null){
            modelBase.setUpdatedBy(adminUserService.currentUser());
        }
    }

    @PreRemove
    public void removeEntity(Object entity) {
        //set the delete attributes
        TulipModelBase modelBase = (TulipModelBase) entity;
        modelBase.setDeletedAt(new Date());
        if(adminUserService.currentUser() != null){
            modelBase.setDeletedBy(adminUserService.currentUser());
        }
    }
}
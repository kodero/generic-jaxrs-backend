package com.corvid.genericbackend.model.base;

import com.corvid.genericbackend.service.admin.RequestContextUser;

import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
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
    BeanManager beanManager; //Workaround WFLY-2387

    public RequestContextUser getCurrentUserService(){
        Bean<RequestContextUser> bean = (Bean<RequestContextUser>) beanManager.getBeans(RequestContextUser.class).iterator().next();
        RequestContextUser requestContextUser = beanManager.getContext(bean.getScope()).get(bean, beanManager.createCreationalContext(bean));
        return requestContextUser;
    }

    @PrePersist
    public void createEntity(Object entity) {
        System.out.println("================entity create detected======================" + getCurrentUserService().getCurrentUser());
        System.out.println("================current user in create is======================" + getCurrentUserService().getCurrentUser() == null ? "" : getCurrentUserService().getCurrentUser().getId());
        //set the create attributes
        ModelBase modelBase = (ModelBase) entity;
        modelBase.setCreatedAt(new Date());
        if(getCurrentUserService().getCurrentUser() != null){
            modelBase.setCreatedByUserId(getCurrentUserService().getCurrentUser().getId());
        }
    }

    @PreUpdate
    public void updateEntity(Object entity) {
        System.out.println("================entity update detected======================");
        System.out.println("================current user in update is======================" + getCurrentUserService().getCurrentUser());
        //set the create attributes
        ModelBase modelBase = (ModelBase) entity;
        modelBase.setUpdatedAt(new Date());
        modelBase.setUpdatedByUserId(getCurrentUserService().getCurrentUser() == null ? null : getCurrentUserService().getCurrentUser().getId());
    }

    @PreRemove
    public void removeEntity(Object entity) {
        System.out.println("================entity update detected======================");
        System.out.println("================current user in update is======================" + getCurrentUserService().getCurrentUser());
        //set the create attributes
        ModelBase modelBase = (ModelBase) entity;
        modelBase.setDeletedAt(new Date());
        modelBase.setDeletedById(getCurrentUserService().getCurrentUser() == null ? null : getCurrentUserService().getCurrentUser().getId());
    }
}
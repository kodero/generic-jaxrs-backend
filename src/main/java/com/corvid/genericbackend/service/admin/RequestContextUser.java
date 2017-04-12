package com.corvid.genericbackend.service.admin;

import com.corvid.genericbackend.model.admin.User;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by kodero on 10/15/15.
 */
@RequestScoped
@Named("currentUserService2")
public class RequestContextUser {

    @Inject
    private HttpServletRequest req;

    public User getCurrentUser() {
        return (User) req.getAttribute("currentUser");
    }
}

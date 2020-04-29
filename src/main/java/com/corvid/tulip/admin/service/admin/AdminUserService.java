package com.corvid.tulip.admin.service.admin;

import com.corvid.bes.service.GService;
import com.corvid.tulip.admin.model.admin.AuthToken;
import com.corvid.tulip.admin.model.admin.User;
import com.corvid.tulip.admin.model.enums.Tuple;
import com.corvid.tulip.admin.rest.admin.UserResource;
import com.corvid.tulip.admin.service.annotations.CurrentUser;
import com.corvid.genericdto.util.TokenGenerator;

import javax.ejb.Stateful;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Date;
import java.util.logging.Logger;

@SuppressWarnings("CdiUnproxyableBeanTypesInspection")
@Stateful
@ConversationScoped
public class AdminUserService implements Serializable {

    //@Inject
    private Logger log = Logger.getLogger("AdminUserService");

    @Inject
    private HttpServletRequest req;

    @Inject
    UserRepository userRepository;

    @Inject
    GService gService;

    @CurrentUser
    @RequestScoped
    @Produces@Named("currentUser")
    public User currentUser() {
        return gService.find(req.getAttribute("userId"), User.class);
    }

    public boolean isLoggedIn(final String token) throws Exception {
        log.info("check if user is logged in ");
        User user = userRepository.findUserByToken(token);
        //TODO fake
        req.setAttribute("currentUser", user);//todo, hack, improve this
        return user != null;
    }

    public User updateUserLogin(User user, String ip) {
        //return gService.edit(updateLastLogins(user, ip));
        return user;
    }

    public boolean checkLoggedIn(String token){
        User u = userRepository.findUserByToken(token);
        if (u == null) return false;
        return true;
    }

    private String generateToken() {
        return new TokenGenerator().nextSessionId();
    }

    public Tuple<AuthToken, User> validateAuthToken(String accessToken) {
        AuthToken authToken = userRepository.findAuthToken(accessToken);
        if(!authToken.isValid()) return new Tuple<>(authToken, null);
        if(authToken.getExpiry().after(new Date())) new Tuple<>(authToken, null);
        System.out.println("Org: " + authToken.getUser().getCurrentOrganization().getName());
        return new Tuple<>(authToken, authToken.getUser());
    }

    public void saveAuthToken(AuthToken authToken) {
        gService.persist(authToken);
    }

    public void invalidateAccessToken(String accessToken) {
        gService.find(accessToken, AuthToken.class).setValid(false);
    }
}

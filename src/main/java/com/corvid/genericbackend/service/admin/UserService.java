package com.corvid.genericbackend.service.admin;

import com.corvid.bes.service.GService;
import com.corvid.genericbackend.model.admin.User;
import com.corvid.genericbackend.rest.admin.UserResource;
import com.corvid.genericbackend.service.annotations.CurrentUser;
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
public class UserService implements Serializable {

    //@Inject
    private Logger log = Logger.getLogger("UserService");

    @Inject
    private HttpServletRequest req;

    @Inject
    UserRepository userRepository;

    @Inject
    GService gService;

    @Inject
    UserResource userResource;

    @CurrentUser
    @RequestScoped
    @Produces@Named("currentUser")
    public User currentUser() {
        final String token = req.getHeader(User.TOKEN);
        log.info("the user token from the request ' " + token + "'");
        User currentUser = userRepository.findUserByToken(token);
        return currentUser;
    }

    public boolean isLoggedIn(final String token) throws Exception {
        log.info("check if user is logged in ");
        User user = userRepository.findUserByToken(token);
        //TODO fake
        req.setAttribute("currentUser", user);//todo, hack, improve this
        return user != null;
    }

    public User updateUserLogin(User user, String ip) {
        return gService.edit(updateLastLogins(user, ip));
    }

    public User updateLastLogins(User user, String ip) {
        log.info("updating login times for user ' " + user.getEmail() + " '");
        user.setLastLoggedInAt(user.getLastLoginAt());
        user.setLastLoginAt(new Date());
        user.setCurrentLoginAt(new Date());
        user.setLastLoginIp(user.getCurrentLoginIp());
        user.setCurrentLoginIp(ip);
        user.setLoginCount(user.getLoginCount() == null ? 1 : user.getLoginCount() + 1);

        if(user.getPersistenceToken() == null ) {//only update the persistence token when its not been set, otherwise return the persistent token
            final String encryptionToken = generateToken();
            user.setPersistenceToken(encryptionToken);
        }
        //update the database
        //user = userRepository.updateAfterSuccessfulLogin(user);
        //setRolesAndAccessLevels(user);
        return user;
    }

    private String generateToken() {
        return new TokenGenerator().nextSessionId();
    }
}

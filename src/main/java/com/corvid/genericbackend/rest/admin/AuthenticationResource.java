/**
 *
 */
package com.corvid.genericbackend.rest.admin;

import com.corvid.bes.validation.AuthenticationNotRequired;

import com.corvid.genericbackend.model.admin.User;
import com.corvid.genericbackend.service.admin.LoginObject;
import com.corvid.genericbackend.service.admin.LoginResult;
import com.corvid.genericbackend.service.admin.UserRepository;
import com.corvid.genericbackend.service.admin.UserService;
import com.corvid.genericdto.data.gdto.GenericDTO;
import com.corvid.genericdto.util.BCrypt;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

/**
 * @author kodero
 */
@Path("/authentication")
@javax.ejb.Stateless
public class AuthenticationResource {

    //@Inject
    private transient Logger log = Logger.getLogger("AuthenticationResource");

    @Inject
    private UserRepository userDAO;

    @Context
    private HttpServletRequest req;

    @Inject
    private UserService userService;

    @Inject
    private UserResource userResource;

    /**
     * This must be accessed behind https
     * Check t
     *
     * @param loginObject@return
     */
    @POST
    @Path("/login")
    @AuthenticationNotRequired
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response authenticateUser(@Context HttpHeaders headers, LoginObject loginObject) {
        User user = getUserForCredentials(loginObject.getEmail(), loginObject.getPassword());
        if (user == null) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(new LoginResult(null, null, "NOK"))
                    .type(MediaType.APPLICATION_JSON_TYPE)
                    .build();
        }
        final String ip = getIp();
        user = userService.updateUserLogin(user, ip);

        req.getSession().setAttribute(User.TOKEN, user.getPersistenceToken());
        req.getSession().setAttribute(User.CURRENT_USER_OBJECT_SESSION_ATTRIBUTE, user);
        return Response.status(Response.Status.OK)
                .entity(new LoginResult(user.getPersistenceToken(), user.getId().toString(), "OK"))
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();

    }

    private String getIp() {
        String remoteHost = req.getRemoteHost();
        String remoteAddr = req.getRemoteAddr();
        int remotePort = req.getRemotePort();
        final String ip = remoteHost + " (" + remoteAddr + ":" + remotePort + ")";
        return ip;
    }

    private User getUserForCredentials(String username, String password) {
        User user = userDAO.findUser(username, true, true);
        if (user == null || password == null || !BCrypt.checkpw(password, user.getCryptedPassword())) {
            log.info("Invalid authentication credentials for username '" + username + "'");
            return null;
        }
        log.info("Successfully authenticated user: " + user.getEmail());
        return user;
    }

    @POST
    @Path("/logout")
    public Response logout() {
        final String token = req.getHeader("token");
        User currentUser = userDAO.findUserByToken(token);
        //TODO set some values
        userDAO.updateUser(currentUser);
        req.getSession().setAttribute(User.TOKEN, null);
        req.getSession().invalidate();
        return Response.ok().build();
    }

    @GET
    @Path("/loggedinUser")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLoggedInUser() throws Exception {
        final String token = req.getHeader(User.TOKEN);
        if(token == null) return Response.status(401).build();
        User u = userService.currentUser();
        GenericDTO d = userResource.toDTO(u, User.USER_SESSION_ATTRIBUTES, User.class);
        return Response.ok(d).build();
    }
}

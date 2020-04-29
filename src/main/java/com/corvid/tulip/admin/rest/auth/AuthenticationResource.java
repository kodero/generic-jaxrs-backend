/**
 *
 */
package com.corvid.tulip.admin.rest.auth;

import com.corvid.bes.util.GenericDTOUtil;
import com.corvid.bes.validation.AuthenticationNotRequired;

import com.corvid.tulip.admin.model.admin.AuthToken;
import com.corvid.tulip.admin.model.admin.Organization;
import com.corvid.tulip.admin.model.admin.Profile;
import com.corvid.tulip.admin.model.admin.User;
import com.corvid.tulip.admin.service.admin.LoginObject;
import com.corvid.tulip.admin.service.admin.UserRepository;
import com.corvid.tulip.admin.service.admin.AdminUserService;
import com.corvid.genericdto.data.gdto.GenericDTO;
import com.corvid.genericdto.util.BCrypt;
import com.corvid.tulip.admin.util.KeyGenerator;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.joda.time.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.security.Key;
import java.util.Date;
import java.util.UUID;
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
    private AdminUserService adminUserService;

    @Inject
    private KeyGenerator keyGenerator;

    @Inject
    private GenericDTOUtil genericDTOUtil;

    @Context
    protected UriInfo uriInfo;

    /**
     *
     * @param loginObject @return
     */
    @POST
    @Path("/login")
    @AuthenticationNotRequired
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response authenticateUser(@Context HttpHeaders headers, LoginObject loginObject) {
        User user = getUserForCredentials(loginObject.getEmail(), loginObject.getPassword());
        if (user == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        final String ip = getIp();
        user = adminUserService.updateUserLogin(user, ip);
        // Issue a token for the user
        AuthToken authToken = generateNewAuthToken(user);
        adminUserService.saveAuthToken(authToken);

        // Return the token on the response
        return Response.ok()
                .header("Authorization", "Bearer " + issueToken(authToken))
                //.header("Access-Control-Expose-Headers","Authorization")
                .build();
    }

    private String issueToken(AuthToken authToken) {
        Key key = keyGenerator.generateKey();

        String jwtToken = Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setSubject(authToken.getUser().getEmail())
                .setIssuer(uriInfo.getAbsolutePath().toString())
                .setIssuedAt(new Date())
                .setExpiration(authToken.getExpiry())
                .claim("firstName", authToken.getUser().getFirstName())
                .claim("lastName", authToken.getUser().getLastName())
                .claim("accessToken", authToken.getAccessToken())
                .claim("refreshToken", authToken.getRefreshToken())
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
        return jwtToken;
    }

    private AuthToken generateNewAuthToken(User user) {
        return new AuthToken(
            user,
            new Date(),
            LocalDateTime.now().plusHours(24).toDate(),
            UUID.randomUUID().toString().replaceAll("-", ""),
            UUID.randomUUID().toString().replaceAll("-", ""),
            "-"
        );
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
        if (user == null || password == null || !BCrypt.checkpw(password, user.getPassword())) {
            log.info("Invalid authentication credentials for username '" + username + "'");
            return null;
        }
        log.info("Successfully authenticated user: " + user.getEmail());
        return user;
    }

    @POST
    @Path("/logout")
    public Response logout() {
        adminUserService.invalidateAccessToken((String) req.getAttribute("accessToken"));
        return Response.ok().build();
    }

    @GET
    @Path("/currentUser")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLoggedInUser() throws Exception {
        User u = adminUserService.currentUser();
        GenericDTO user = genericDTOUtil.toDTO(u, User.USER_SESSION_ATTRIBUTES, User.class, "com.corvid.tulip.common.model.User");
        GenericDTO profile = genericDTOUtil.toDTO(u.getCurrentProfile(), new String[]{"id", "name"}, Profile.class, "com.corvid.tulip.common.model.Profile");
        GenericDTO organization = genericDTOUtil.toDTO(u.getCurrentOrganization(), new String[]{"id", "name"}, Organization.class, "com.corvid.tulip.common.model.Organization");
        user.addRelation("currentProfile", profile);
        user.addRelation("currentOrganization", organization);
        return Response.ok(user).build();
    }
}

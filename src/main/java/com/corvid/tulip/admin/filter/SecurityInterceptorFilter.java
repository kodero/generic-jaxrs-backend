/**
 *
 */
package com.corvid.tulip.admin.filter;

import com.corvid.bes.validation.AuthenticationNotRequired;
import com.corvid.tulip.admin.model.admin.AuthToken;
import com.corvid.tulip.admin.model.admin.User;
import com.corvid.tulip.admin.model.enums.Tuple;
import com.corvid.tulip.admin.service.admin.AdminUserService;
import com.corvid.tulip.admin.util.KeyGenerator;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.jboss.resteasy.core.ResourceMethodInvoker;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.lang.reflect.Method;
import java.security.Key;

/**
 * @author kodero
 */
@Provider
@Priority(Priorities.AUTHENTICATION)
public class SecurityInterceptorFilter implements ContainerRequestFilter {

    public static final String ORG_JBOSS_RESTEASY_CORE_RESOURCE_METHOD_INVOKER = "org.jboss.resteasy.core.ResourceMethodInvoker";

    public static final String TOKEN = "token";

    @Context
    private HttpServletRequest servletRequest;

    //@Inject
    //private transient Logger log;

    @Inject
    private AdminUserService adminUserService;

    @Inject
    private KeyGenerator keyGenerator;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        // Establish we need to filter stuff
        ResourceMethodInvoker methodInvoker = (ResourceMethodInvoker) requestContext.getProperty(ORG_JBOSS_RESTEASY_CORE_RESOURCE_METHOD_INVOKER);
        Method method = methodInvoker.getMethod();
        if(method.isAnnotationPresent(AuthenticationNotRequired.class)) return;

        // Get the HTTP Authorization header from the request
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        //do not continue if bearer token is not supplied
        if(authorizationHeader == null || authorizationHeader == ""){
            abort(requestContext);
        }

        // Extract the token from the HTTP Authorization header
        String token = authorizationHeader.substring("Bearer".length()).trim();

        try {
            // Validate the token
            Key key = keyGenerator.generateKey();
            Jws<Claims> claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            //check validity of access token
            Tuple<AuthToken, User> valResult = adminUserService.validateAuthToken((String) claims.getBody().get("accessToken"));
            if(!valResult.getFirst().isValid()){
                abort(requestContext);
            }

            //stuff user in servlet request
            servletRequest.setAttribute("userId", valResult.getSecond().getId());
            servletRequest.setAttribute("accessToken", valResult.getFirst().getId());
            System.out.println("OrgName: " + valResult.getSecond().getCurrentOrganization());
            servletRequest.setAttribute("currentUser", new com.corvid.tulip.common.model.User(
                    valResult.getSecond().getId(),
                    valResult.getSecond().getFirstName(),
                    valResult.getSecond().getLastName(),
                    valResult.getSecond().getEmail(),
                    valResult.getSecond().getCurrentOrganization()
            ));
        } catch (Exception e) {
            e.printStackTrace();
            //log.severe("#### invalid token : " + token);
            abort(requestContext);
        }
    }

    private void abort(ContainerRequestContext requestContext){
        requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        return;
    }
}

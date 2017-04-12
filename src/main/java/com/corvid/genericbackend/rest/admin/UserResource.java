package com.corvid.genericbackend.rest.admin;

import com.corvid.bes.rest.BaseEntityResource;
import com.corvid.genericbackend.model.admin.User;
import com.corvid.genericdto.data.gdto.GenericDTO;
import com.corvid.genericdto.util.BCrypt;
import com.corvid.genericdto.util.StackTraceUtil;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;


@Path("/users")
@javax.ejb.Stateless
public class UserResource extends BaseEntityResource<User> implements Serializable {

    public UserResource() {
        super(User.class);
    }

    /**
     * Creates a new user from the values provided. Performs validation, and will return a JAX-RS response with either 200 ok,
     * or with a map of fields, and related errors.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response createState(@Context HttpHeaders headers, GenericDTO entityDTO) {
        Response.ResponseBuilder builder = null;
        log.info(" creating a new user , details " + entityDTO);

        try {
            //get instance
            //make the entity from the dto
            log.info("the dto " + entityDTO);
            User entity = fromDTO(entityDTO, User.class);
            final String salt = BCrypt.gensalt();
            entity.setCryptedPassword(BCrypt.hashpw(entity.getEmail(), salt));

            log.info("set the hashed pwd " + entity.getCryptedPassword());
            User createdState = null;
            createdState = getEntityManager().merge(entity);
            builder = Response.created(URI.create(uriInfo.getAbsolutePath() + "/" + createdState.getId().toString()))
                    .entity(toDTO(createdState, User.USER_SESSION_ATTRIBUTES, User.class));

            //we raise event
            entityEventSrc.fire(createdState);
        } catch (ConstraintViolationException ce) {
            ce.printStackTrace();
            // Handle bean validation issues
            builder = createViolationResponse(ce.getConstraintViolations());
        } catch (ValidationException e) {
            e.printStackTrace();
            // Handle the unique constrain violation
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put(e.getMessage(), StackTraceUtil.getStackTrace(e));
            builder = Response.status(Response.Status.CONFLICT).entity(responseObj);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle generic exceptions
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }
        return builder.build();
    }

    @POST
    @Path("/{id}/changePassword")
    public Response changePassword(@Context HttpHeaders headers, @PathParam("id") String id,
                                   @QueryParam("currentPassword") String currentPassword,
                                   @QueryParam("newPassword") String newPassword,
                                   @QueryParam("confirmPassword") String confirmPassword){
        //change password for the goon
        log.info("Current password : " + currentPassword);
        log.info("new password : " + currentPassword);
        log.info("Confirm password : " + currentPassword);
        User u = gService.find(id, User.class);
        if(u == null){
            log.info("User is null....");
            return Response.status(Response.Status.fromStatusCode(404)).build();
        }
        if(!newPassword.equals(confirmPassword) || !BCrypt.checkpw(currentPassword, u.getCryptedPassword())){
            log.info("passwords do not conform or current password not correct...." + !newPassword.equals(confirmPassword) + ", " + !BCrypt.checkpw(currentPassword, u.getCryptedPassword()));
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        //now change the password
        final String salt = BCrypt.gensalt();
        u.setCryptedPassword(BCrypt.hashpw(newPassword, salt));
        gService.edit(u);
        return Response.ok().build();
    }

    @POST
    @Path("/{id}/resetPassword")
    public Response resetPassword(@Context HttpHeaders headers, @PathParam("id") String id){
        User u = gService.find(id, User.class);
        if(u == null) return Response.status(Response.Status.fromStatusCode(404)).build();
        //now change the password
        final String salt = BCrypt.gensalt();
        String newHashedPassword = BCrypt.hashpw(u.getEmail(), salt);
        getEntityManager().createNativeQuery("update users set crypted_password = '" + newHashedPassword + "' where id = '" + u.getId() + "'").executeUpdate();
        return Response.ok().build();
    }

    @POST
    @Path("/{id}/activate")
    public Response activate(@Context HttpHeaders headers, @PathParam("id") String id){
        User u = gService.find(id, User.class);
        if(u == null) return Response.status(Response.Status.fromStatusCode(404)).build();
        getEntityManager().createNativeQuery("update users set active = 1 where id = '" + u.getId() + "'").executeUpdate();
        return Response.ok().build();
    }

    @POST
    @Path("/{id}/deactivate")
    public Response deactivate(@Context HttpHeaders headers, @PathParam("id") String id){
        User u = gService.find(id, User.class);
        if(u == null) return Response.status(Response.Status.fromStatusCode(404)).build();
        getEntityManager().createNativeQuery("update users set active = 0, persistence_token = null where id = '" + u.getId() + "'").executeUpdate();
        return Response.ok().build();
    }
}

package com.corvid.genericbackend.rest.admin;

import com.corvid.bes.rest.BaseEntityResource;
import com.corvid.genericbackend.model.admin.Permission;
import com.corvid.genericbackend.model.admin.UserPermission;
import com.corvid.genericdto.data.gdto.GenericDTO;
import org.reflections.Reflections;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.*;
import java.util.logging.Logger;

/**
 * @author mokua
 */
@Path("/permissions")
@javax.ejb.Stateless
public class UserPermissionResource extends BaseEntityResource<UserPermission> {

    public UserPermissionResource() {
        super(UserPermission.class);
    }

    //@Inject
    private Logger log = Logger.getLogger("UserPermissionResource");

    @Inject
    private Validator validator;

    @Context
    protected UriInfo uriInfo;

    @Inject
    private Event<UserPermission> entityEventSrc;

    private static final String COM_SB_MODEL = "com.corvid.inventory.model";

    @Override
    protected Predicate[] extractPredicates(MultivaluedMap<String, String> queryParameters, CriteriaBuilder criteriaBuilder, Root<UserPermission> root) {
        List<Predicate> predicates = new ArrayList<>();
        if (queryParameters.containsKey("permission.namespace")) {
            String namespace = queryParameters.getFirst("permission.namespace");
            predicates.add(criteriaBuilder.equal(root.get("permission.namespace"), namespace));
        }
        return predicates.toArray(new Predicate[predicates.size()]);
    }

    private List<GenericDTO> toDTO(List<UserPermissionRequest> list) {
        List<GenericDTO> genericDTOList = new ArrayList<>(list.size());
        for (UserPermissionRequest request : list) {
            genericDTOList.add(request.genericDTO());
        }
        return genericDTOList;
    }

    private void validateResource(Collection<UserPermissionRequest> requests) throws ConstraintViolationException, ValidationException {
        // Create a bean validator and check for issues.
        for (UserPermissionRequest request : requests) {
            Set<ConstraintViolation<UserPermissionRequest>> violations = validator.validate(request);

            if (!violations.isEmpty()) {
                throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
            }
        }
    }

    protected Response.ResponseBuilder createViolationResponse(Set<ConstraintViolation<?>> violations) {
        log.fine("Validation completed. violations found: " + violations.size());

        Map<String, String> responseObj = new HashMap<>();

        for (ConstraintViolation<?> violation : violations) {
            responseObj.put(violation.getPropertyPath().toString(), violation.getMessage());
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
    }

    @POST
    @Path("/createPermissions")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Permission> createPermissions() throws IOException {
        Reflections reflections = new Reflections(COM_SB_MODEL);
        Set<Class<?>> entityClasses = reflections.getTypesAnnotatedWith(Entity.class);
        List<Permission> permissions = new ArrayList<>();
        for (Class<?> s : entityClasses) {
            Set<Annotation> classAnnotations = new HashSet<>(Arrays.asList(s.getAnnotations()));
            if(!classAnnotations.contains(MappedSuperclass.class)){
                permissions.add(new Permission(s.getName() + ".save", "Save/edit " + s.getSimpleName()));
                permissions.add(new Permission(s.getName() + ".view", "View " + s.getSimpleName()));
                permissions.add(new Permission(s.getName() + ".delete", "Delete " + s.getSimpleName()));
                permissions.add(new Permission(s.getName() + ".list", "List " + s.getSimpleName()));
            }
        }
        //now save the shit
        for(Permission p : permissions){
            try {
                gService.makePersistent(p);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return permissions;
    }
}

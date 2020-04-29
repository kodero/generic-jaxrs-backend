package com.corvid.tulip.admin.rest.admin;

import com.corvid.tulip.admin.model.admin.*;
import com.corvid.tulip.admin.service.admin.AdminUserService;
import com.corvid.genericdto.data.gdto.GenericDTO;
import com.corvid.tulip.common.rest.TulipBaseEntityResource;

import javax.inject.Inject;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author mokua
 */
@Path("/userProfileRules")
@javax.ejb.Stateless
public class ProfilePermissionResource extends TulipBaseEntityResource<ProfilePermission> {

    @Inject
    AdminUserService adminUserService;

    public ProfilePermissionResource() {
        this.entityClass = ProfilePermission.class;
    }

    @GET
    @Override
    @Produces(MediaType.APPLICATION_JSON)
    public List<GenericDTO> getAll(@Context UriInfo uriInfo,
                                   @QueryParam("pageSize") @DefaultValue("25") int pageSize,
                                   @QueryParam("page") @DefaultValue("1") int pageNum,
                                   @QueryParam("orderBy") List<String> orderBy,
                                   @QueryParam("fields") String fieldList,
                                   @QueryParam("where") List<String> where) {

        //check if all permissions for this profile have been added
        //TODO, put this code in a service class
        final String profileId = uriInfo.getQueryParameters().getFirst("profileId");
        if (profileId == null || profileId.trim().isEmpty()) {
            log.info("No profile ID specified");
            return new ArrayList<>();
        }
        Profile profile = getEntityManager().find(Profile.class, profileId);
        List<Permission> newPermissions = getEntityManager().createQuery("select p from Permission p where p.id not in (select upr.permission.id from ProfilePermission upr where upr.profile.id = '" + profileId + "')", Permission.class)
                .getResultList();
        for(Permission p : newPermissions){
            //create UserProfileRule and save it
            ProfilePermission upr = new ProfilePermission(p, profile, false);
            getEntityManager().merge(upr);
        }
        return super.getAll(uriInfo, pageSize, pageNum, orderBy, fieldList, where);
    }

    @POST
    @Path("/{id}/toggleStatus")
    public Response toggleStatus(@PathParam("id") String id) throws Exception {
        ProfilePermission upr = getEntityManager().find(ProfilePermission.class, id);
        upr.setAllowed(!upr.getAllowed());
        getEntityManager().merge(upr);
        String[] fields = {"id","allowed","permission:permissionName","permission:description","userProfile:name"};
        return Response.ok(genericDTOUtil.toDTO(upr, fields, ProfilePermission.class)).build();
    }

    @GET
    @Path("/{id}/allowedPermissions")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserPermissions(@PathParam("id") String id){
        final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<Tuple> cq = cb.createTupleQuery();
        Root<ProfilePermission> root = cq.from(ProfilePermission.class);
        String[] fields = {"id", "allowed", "permission:permissionName"};
        cq.multiselect(getSelections(fields, root));
        cq.where(cb.equal(getPath(root, "userProfile.id"), id), cb.equal(root.get(ProfilePermission_.allowed), true));
        List<Tuple> allowedPermissions = getEntityManager().createQuery(cq).getResultList();
        List<GenericDTO> dtos = Collections.EMPTY_LIST;
        try {
            dtos = genericDTOUtil.getGenericDTOs(fields, allowedPermissions, entityClass);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File |//TODO| File Templates.
        }
        return Response.ok(dtos).build();
    }

    @GET
    @Path("/loadUserPermissions/{permissionFilter}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response loadUserPermissions(@PathParam("permissionFilter") String permissionFilter){
        Query q = getEntityManager().createQuery("select r.permission.name from ProfilePermission r where r.allowed = :allowed and r.profile.id = :profileId and r.permission.name like :permissionFilter");
        q.setParameter("allowed", true);
        //q.setParameter("profileId", userService.currentUser().getUserProfile().getId());
        q.setParameter("profileId", adminUserService.currentUser().getProfile().getId());
        q.setParameter("permissionFilter", "%" + permissionFilter + "%");
        List<String> allowedPermissions = q.getResultList();
        String outPut = "[";
        for(String s : allowedPermissions){
            outPut = outPut + "\"" + s + "\",";
        }
        outPut = outPut + "]";
        outPut = outPut.replaceAll(",]", "]");
        return Response.ok(outPut).build();
    }
}

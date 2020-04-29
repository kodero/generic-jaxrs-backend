package com.corvid.tulip.admin.rest.admin;

import com.corvid.bes.rest.BaseEntityResource;
import com.corvid.tulip.admin.model.admin.Permission;

import javax.ws.rs.Path;

/**
 * @author mokua
 */
@Path("/permissions")
@javax.ejb.Stateless
public class PermissionResource extends BaseEntityResource<Permission> {
    public PermissionResource() {
        super(Permission.class);
    }

    /*@Override
    protected Predicate[] extractPredicates(
            MultivaluedMap<String, String> queryParameters,
            CriteriaBuilder criteriaBuilder,
            Root<Permission> root, MultivaluedMap<String, String> pathParameters) {
        List<Predicate> predicates = new ArrayList<>();

        if (queryParameters.containsKey("modelType")) {
            String taskableType = queryParameters.getFirst("modelType");
            predicates.add(criteriaBuilder.equal(root.get("modelType"), taskableType));
        }


        return predicates.toArray(new Predicate[predicates.size()]);
    }*/
}

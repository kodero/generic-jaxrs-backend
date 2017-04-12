package com.corvid.genericbackend.rest.admin;

import com.corvid.bes.rest.BaseEntityResource;
import com.corvid.genericbackend.model.admin.Config;
import com.corvid.genericbackend.service.admin.ConfigService;
import com.corvid.genericdto.data.gdto.GenericDTO;

import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

/**
 * Created by kodero on 12/30/14.
 */
@Stateful
@Path("/config")
public class ConfigResource extends BaseEntityResource<Config> {

    @Inject
    private ConfigService configService;

    public ConfigResource(){
        super(Config.class);
    }

    @GET
    @Path("/getCurrentConfig")
    public Response getConfig(@QueryParam("fields") String fieldList) throws Exception {
        String[] fields = fieldList == null ? makeDefaultSelectionFields(entityClass) : fieldList.split(",");
        Config c = configService.getCurrentConfig();
        log.info("Get config : " + c);
        if(c != null) return Response.ok(toDTO(c, fields, Config.class)).build();
        return Response.ok(new GenericDTO(entityClass.getCanonicalName())).build();
    }
}

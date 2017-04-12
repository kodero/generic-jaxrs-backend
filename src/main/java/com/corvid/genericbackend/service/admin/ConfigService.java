package com.corvid.genericbackend.service.admin;

import com.corvid.bes.service.GService;
import com.corvid.genericbackend.model.admin.Config;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

/**
 * Created by kodero on 12/30/14.
 */
@Stateless
public class ConfigService implements Serializable {

    @Inject
    private GService gService;

    public Config getCurrentConfig(){
        List<Config> l = gService.createQuery("from Config c").getResultList();
        if(l.size() == 1) return l.get(0);
        return null;
    }
}

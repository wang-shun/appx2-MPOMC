package com.dreawer.appxauth.service;

import com.dreawer.appxauth.domain.Application;
import com.dreawer.appxauth.persistence.AppDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <CODE>ApplicationService</CODE>
 *
 * @author fenrir
 * @Date 18-7-13
 */

@Service
public class ApplicationService {

    @Autowired
    private AppDao appDao;


    public Application findByAppidAndOrganizationId(String appid, String organzationId) {
        return appDao.findByAppidAndOrganizationId(appid, organzationId);
    }

    public void save(Application application) {
        appDao.save(application);
    }

    public void update(Application application) {
        appDao.update(application);
    }

}
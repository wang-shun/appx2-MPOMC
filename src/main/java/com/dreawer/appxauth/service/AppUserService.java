package com.dreawer.appxauth.service;

import com.dreawer.appxauth.domain.ApplicationUser;
import com.dreawer.appxauth.persistence.AppUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <CODE>ApplicationService</CODE>
 *
 * @author fenrir
 * @Date 18-7-13
 */

@Service
public class AppUserService {

    @Autowired
    private AppUserDao appUserDao;


    public ApplicationUser findByAppidAndOrganizationId(String appid, String applicationId) {
        return appUserDao.findByAppidAndApplicationId(appid, applicationId);
    }

    public void save(ApplicationUser application) {
        appUserDao.save(application);
    }

    public void update(ApplicationUser application) {
        appUserDao.update(application);
    }

}
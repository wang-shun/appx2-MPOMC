package com.dreawer.appxauth.service;

import com.dreawer.appxauth.domain.Application;
import com.dreawer.appxauth.persistence.AppDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <CODE>AppService</CODE>
 *
 * @author fenrir
 * @Date 18-8-14
 */

@Service
public class AppService {

    @Autowired
    private AppDao appDao;


    public void save(Application application) {
        appDao.save(application);
    }

    public void update(Application application) {
        appDao.update(application);
    }

    public Application findById(String id) {
        return appDao.findById(id);
    }
}

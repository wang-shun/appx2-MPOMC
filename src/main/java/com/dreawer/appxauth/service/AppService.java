package com.dreawer.appxauth.service;

import com.dreawer.appxauth.domain.Application;
import com.dreawer.appxauth.manager.ServiceManager;
import com.dreawer.appxauth.persistence.AppDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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

    @Autowired
    private ServiceManager serviceManager;


    public void save(Application application) {
        appDao.save(application);
    }

    public void update(Application application) {
        appDao.update(application);
    }

    public Application findById(String id) {
        return appDao.findById(id);
    }

    public Application findByAppid(String appid) {
        return appDao.findByAppId(appid);
    }

    public void updateApplication(String appid, String principal_name) throws Exception {
        Map<String, Object> param = new HashMap<>();
        if (!StringUtils.isBlank(principal_name)) {
            param.put("name", principal_name);
        }
        //创建或更新应用
        Application application = appDao.findByAppId(appid);
        if (application == null) {
            application = new Application();
            application.setId(UUID.randomUUID().toString().replace("-", ""));
            //获取组织ID
            //如果没有组织则创建,有则返回组织ID
            param.put("appId", application.getId());
            String organizationId = serviceManager.addOrganzation(param);
            application.setOrganizationId(organizationId);
            application.setAppId(appid);
            appDao.save(application);
        } else {
            param.put("appId", application.getId());
            String organizationId = serviceManager.addOrganzation(param);
            application.setOrganizationId(organizationId);
            appDao.update(application);
        }
    }

}

package com.dreawer.appxauth.persistence;

import com.dreawer.appxauth.domain.Application;
import com.dreawer.persistence.mybatis.MyBatisBaseDao;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * <CODE>AppDao</CODE>
 *
 * @author fenrir
 * @Date 18-7-13
 */

@Repository
public class AppDao extends MyBatisBaseDao<Application> {
    public Application findByAppidAndOrganizationId(String appid, String organizationId) {
        Map<String, String> param = new HashMap<>();
        param.put("appid", appid);
        param.put("organizationId", organizationId);
        return selectOne("findByAppidAndOrganizationId", appid);
    }

    public void save(Application application) {
        insert("save", application);
    }

    public void update(Application application) {
        update("update", application);
    }
}

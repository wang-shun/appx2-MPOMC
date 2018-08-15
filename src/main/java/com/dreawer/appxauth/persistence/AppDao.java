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
 * @Date 18-8-14
 */

@Repository
public class AppDao extends MyBatisBaseDao<Application> {


    public void save(Application application) {
        insert("save", application);
    }

    public void update(Application application) {
        update("update", application);
    }

    public Application findById(String id) {
        Map<String, Object> param = new HashMap<>();
        param.put("id", id);
        return selectOne("findById", param);
    }
}

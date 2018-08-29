package com.dreawer.appxauth.persistence;

import com.dreawer.appxauth.domain.ApplicationUser;
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
public class AppUserDao extends MyBatisBaseDao<ApplicationUser> {
    public ApplicationUser findByOpenIdAndApplicationId(String openId, String applicationId) {
        Map<String, String> param = new HashMap<>();
        param.put("openId", openId);
        param.put("applicationId", applicationId);
        return selectOne("findByOpenIdAndApplicationId", param);
    }

    public void save(ApplicationUser applicationUser) {
        insert("save", applicationUser);
    }

    public void update(ApplicationUser applicationUser) {
        update("update", applicationUser);
    }
}

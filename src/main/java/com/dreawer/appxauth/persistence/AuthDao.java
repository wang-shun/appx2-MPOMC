package com.dreawer.appxauth.persistence;

import com.dreawer.appxauth.domain.AuthInfo;
import com.dreawer.persistence.mybatis.MyBatisBaseDao;
import org.springframework.stereotype.Repository;

/**
 * <CODE>AuthDao</CODE>
 *
 * @author fenrir
 * @Date 18-7-3
 */

@Repository
public class AuthDao extends MyBatisBaseDao<AuthInfo> {
    public AuthInfo findByAppid(String appid) {
        return selectOne("findByAppid", appid);
    }

    public void save(AuthInfo authInfo) {
        insert("save", authInfo);
    }

    public void update(AuthInfo authInfo) {
        update("update", authInfo);
    }
}

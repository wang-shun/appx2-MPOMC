package com.dreawer.appxauth.service;

import com.dreawer.appxauth.domain.AuthInfo;
import com.dreawer.appxauth.persistence.AuthDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <CODE>AuthService</CODE>
 *
 * @author fenrir
 * @Date 18-7-3
 */

@Service
public class AuthService {


    @Autowired
    private AuthDao authDao;

    public AuthInfo findByAppid(String appid) {
        return authDao.findByAppid(appid);
    }

    public void save(AuthInfo authInfo) {
        authDao.save(authInfo);
    }

    public void update(AuthInfo authInfo) {
        authDao.update(authInfo);
    }


}

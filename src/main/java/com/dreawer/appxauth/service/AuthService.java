package com.dreawer.appxauth.service;

import com.dreawer.appxauth.domain.AuthInfo;
import com.dreawer.appxauth.lang.AppType;
import com.dreawer.appxauth.model.AuthorizeInfo;
import com.dreawer.appxauth.persistence.AuthDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;

import static com.dreawer.appxauth.utils.BaseUtils.getNow;

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

    public void updateAuthInfo(String authorizationCode, String expiresIn, String userId, AuthorizeInfo authorizeInfo, String appid) {
        AuthInfo authInfo = authDao.findByAppid(appid);
        if (authInfo == null) {
            authInfo = new AuthInfo();
            authInfo.setAuthorizationCode(authorizationCode);
            authInfo.setAccessToken(authorizeInfo.getAuthorization_info().getAuthorizer_access_token());
            authInfo.setRefreshToken(authorizeInfo.getAuthorization_info().getAuthorizer_refresh_token());
            authInfo.setAppid(appid);
            authInfo.setAppType(AppType.WXAPP);
            //获取有效期
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.SECOND, Integer.parseInt(expiresIn));

            authInfo.setExpireIn(calendar.getTime());
            authInfo.setCreaterId(userId);
            authInfo.setCreateTime(getNow());
            authInfo.setUpdateTime(getNow());
            authDao.save(authInfo);

        } else {
            authInfo.setAccessToken(authorizeInfo.getAuthorization_info().getAuthorizer_access_token());
            authInfo.setRefreshToken(authorizeInfo.getAuthorization_info().getAuthorizer_refresh_token());
            authInfo.setAuthorizationCode(authorizationCode);
            //获取有效期
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.SECOND, Integer.parseInt(expiresIn));

            authInfo.setExpireIn(calendar.getTime());
            authInfo.setAppType(AppType.WXAPP);
            authInfo.setCreaterId(userId);
            authInfo.setCreateTime(getNow());
            authInfo.setUpdateTime(getNow());
            authDao.update(authInfo);
        }
    }

}

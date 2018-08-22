package com.dreawer.appxauth.manager;

import com.dreawer.appxauth.AppxAuthApplication;
import com.dreawer.appxauth.consts.ThirdParty;
import com.dreawer.appxauth.model.AuthorizeInfo;
import com.dreawer.appxauth.model.Component_access_token;
import com.dreawer.appxauth.model.Pre_auth_code_info;
import com.dreawer.appxauth.utils.Okhttp;
import com.dreawer.appxauth.utils.RedisUtils;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.dreawer.appxauth.consts.ThirdParty.*;

/**
 * <CODE>GeneralTokenManager</CODE>
 * 统一Token管理类
 *
 * @author fenrir
 * @Date 18-7-3
 */

@Slf4j
public class TokenManager {


    private static Logger logger = Logger.getLogger(TokenManager.class); // 日志记录器


    private static final String REDIS_PREFIX = "redis_";

    private static RedisUtils redisUtils = AppxAuthApplication.applicationContext.getBean(RedisUtils.class);

    public TokenManager() {

    }

    public static String getVerifyTicket() {
        return (String) redisUtils.get(REDIS_PREFIX + COMPONENT_VERIFY_TICKET);
    }

    public static void setVerifyTicket(String verifyTicket) {
        redisUtils.set(REDIS_PREFIX + COMPONENT_VERIFY_TICKET, verifyTicket);
    }


    public static String getComponentAccessToken() throws IOException {
        if (redisUtils.get(REDIS_PREFIX + COMPONENT_ACCESS_TOKEN) == null) {
            return initComponentAccessToken();
        } else {
            return (String) redisUtils.get(REDIS_PREFIX + COMPONENT_ACCESS_TOKEN);
        }
    }

    public static String getPreAuthCode() throws IOException {
        /**
         * 预授权码只能使用一次,所以无需缓存,先注释掉
         */
//        if (redisUtils.get(REDIS_PREFIX + PRE_AUTH_CODE) == null) {
//            return initPre_auth_code();
//        } else {
//            return (String) redisUtils.get(REDIS_PREFIX + PRE_AUTH_CODE);
//        }

        return initPre_auth_code();
    }


    public static String initComponentAccessToken() throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put(COMPONENT_APPID, ThirdParty.APPID());
        params.put(COMPONENT_APPSECRET, ThirdParty.APPSECRT());
        params.put(COMPONENT_VERIFY_TICKET, getVerifyTicket());
        String response = Okhttp.postSyncJson(ThirdParty.URL_GET_BASIC_TOKEN(), params);
        logger.info(response);
        Component_access_token token = new Gson().fromJson(response, Component_access_token.class);
        String accessToken = token.getComponent_access_token();
        Integer expiresIn = Integer.parseInt(token.getExpires_in());
        //防止刷新时间差,在token失效前半小时删除该记录
        expiresIn = expiresIn - 60 * 30;
        logger.info("到期时间" + expiresIn);
        //accessToken失效时间为1.5小时,之后会重新刷新
        redisUtils.set(REDIS_PREFIX + COMPONENT_ACCESS_TOKEN, accessToken, expiresIn);
        logger.info("刷新token成功" + COMPONENT_ACCESS_TOKEN);
        return accessToken;
    }

    public static String initPre_auth_code() throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put(COMPONENT_APPID, ThirdParty.APPID());
        String response = Okhttp.postSyncJson(ThirdParty.URL_GET_PRE_AUTH_CODE(), params);
        Pre_auth_code_info auth_code_info = new Gson().fromJson(response, Pre_auth_code_info.class);
        String pre_auth_code = auth_code_info.getPre_auth_code();
        //预授权码一次扫码只能使用一次 不用缓存到redis
//        Integer expiresIn = Integer.parseInt(auth_code_info.getExpires_in());
//        //防止刷新时间差,在token失效前半小时删除该记录
//        expiresIn = expiresIn - 60 * 5;
//        //accessToken失效时间为5分钟,之后会重新刷新
//        logger.info("到期时间" + expiresIn);
//        redisUtils.set(REDIS_PREFIX + PRE_AUTH_CODE, pre_auth_code, expiresIn);
        return pre_auth_code;
    }

    public static AuthorizeInfo getAuthorizeInfo(String authorizationCode) throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put(COMPONENT_APPID, ThirdParty.APPID());
        params.put(AUTHORIZATION_CODE, authorizationCode);
        String response = Okhttp.postSyncJson(ThirdParty.URL_GET_AUTHORIZER_INFO(), params);
        return new Gson().fromJson(response, AuthorizeInfo.class);
    }

}


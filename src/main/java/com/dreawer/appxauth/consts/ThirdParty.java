package com.dreawer.appxauth.consts;

import com.dreawer.appxauth.manager.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * <CODE>thirdParty</CODE>
 *
 * @author fenrir
 * @Date 18-7-3
 */
@Configuration
@ConfigurationProperties(prefix = "thirdParty")
@Component
public class ThirdParty {

    @Autowired
    private TokenManager tokenManager;

    //参数名称
    public static final String COMPONENT_APPID = "component_appid";
    public static final String COMPONENT_ACCESS_TOKEN = "component_access_token";
    public static final String COMPONENT_APPSECRET = "component_appsecret";
    public static final String COMPONENT_VERIFY_TICKET = "component_verify_ticket";
    public static final String AUTHORIZATION_CODE = "authorization_code";
    public static final String PRE_AUTH_CODE = "pre_auth_code";
    public static final String REDIRECT_URL = "redirect_uri";
    public static final String AUTH_TYPE = "auth_type";
    public static final String AUTHORIZER_APPID = "authorizer_appid";
    public static final String PERSIONAL = "个人";


    //开放平台授权相关
    public static String APPX_THIRDPARTY_APPID = null;
    public static String APPX_APP_SECRET = null;
    public static String PROGRAM_VALIDATE_TOKEN = null;
    public static String PROGRAM_ENCODING_AES_KEY = null;

    //接口URL
    public String COMPONENT_ACCESS_TOKEN_QUERY = null;
    public String PRE_AUTH_CODE_QUERY = null;
    public String API_QUERY_AUTH = null;
    public String HTTP_WXAPP_AUTH_PAGE = null;
    public String APPX_REDIRECT_URL = null;
    public String MODIFY_DOMAIN_QUERY = null;
    public String COMMIT_CODE_QUERY = null;
    public String QR_CODE_QUERY = null;
    public String CATEGORY_QUERY = null;
    public String GET_PAGE_QUERY = null;
    public String SUBMIT_AUDIT_QUERY = null;
    public String LATEST_AUDIT_STATUS_QUERY = null;
    public String RELEASE_QUERY = null;
    public String APP_INFO = null;
    public String AUTHORIZER_INFO_QUERY = null;
    public String WX_LOGIN = null;
    public String GET_LATEST_AUDITSTATUS = null;

    //其他服务调用
    //用户中心调用小程序用户首次注册
    public String REQ_SIGNUP_WXAPP = null;
    //小程序登录
    public String REQ_LOGIN_WXAPP = null;


    public String APPID() {
        return APPX_THIRDPARTY_APPID;
    }

    public String APPSECRT() {
        return APPX_APP_SECRET;
    }


    public String URL_GET_LATEST_AUDITSTATUS(String authorizerAccessToken) {
        return GET_LATEST_AUDITSTATUS + authorizerAccessToken;
    }

    public String URL_WX_LOGIN(String appid, String jsCode) throws IOException {
        return WX_LOGIN + "appid" + "=" + appid + "&" + "js_code" + "=" + jsCode + "&"
                + "grant_type=authorization_code&" +
                COMPONENT_APPID + "=" + APPID() + "&" + COMPONENT_ACCESS_TOKEN + "=" + tokenManager.getComponentAccessToken();
    }

    public String URL_GET_BASIC_TOKEN() {
        return COMPONENT_ACCESS_TOKEN_QUERY;
    }

    public String URL_GET_PRE_AUTH_CODE() throws IOException {
        return PRE_AUTH_CODE_QUERY + tokenManager.getComponentAccessToken();
    }

    public String URL_GET_API_QUERY_AUTH() throws IOException {
        return API_QUERY_AUTH + tokenManager.getComponentAccessToken();
    }

    public String URL_AUTH_PAGE() throws IOException {
        return HTTP_WXAPP_AUTH_PAGE + "?"
                + COMPONENT_APPID
                + "="
                + APPID() + "&"
                + PRE_AUTH_CODE
                + "="
                + tokenManager.getPreAuthCode() + "&"
                + REDIRECT_URL
                + "="
                + APPX_REDIRECT_URL + "&"
                /** 要授权的帐号类型，
                 * 1则商户扫码后，手机端仅展示公众号、
                 * 2表示仅展示小程序，3表示公众号和小程序都展示。如果为未制定，
                 * 则默认小程序和公众号都展示。第三方平台开发者可以使用本字段来控制授权的帐号类型。**/
                + AUTH_TYPE + "=" + 3;
    }

    public String URL_MODIFY_DOMAIN(String authorizerAccessToken) throws IOException {
        return MODIFY_DOMAIN_QUERY + authorizerAccessToken;
    }

    public String URL_COMMIT_CODE(String authorizerAccessToken) throws IOException {
        return COMMIT_CODE_QUERY + authorizerAccessToken;
    }

    public String URL_QR_CODE(String authorizerAccessToken) throws IOException {
        return QR_CODE_QUERY + authorizerAccessToken;
    }

    public String URL_CATEGORY_QUERY(String authorizerAccessToken) throws IOException {
        return CATEGORY_QUERY + authorizerAccessToken;
    }

    public String URL_GET_PAGE(String authorizerAccessToken) throws IOException {
        return GET_PAGE_QUERY + authorizerAccessToken;
    }

    public String URL_SUBMIT_AUDIT(String authorizerAccessToken) throws IOException {
        return SUBMIT_AUDIT_QUERY + authorizerAccessToken;
    }

    public String URL_RELEASE_QUERY(String authorizerAccessToken) {
        return RELEASE_QUERY + authorizerAccessToken;
    }

    public String URL_GET_APP_INFO(String authorizerAccessToken) {
        return APP_INFO + authorizerAccessToken;
    }

    public String URL_GET_AUTHORIZER_INFO() throws IOException {
        return AUTHORIZER_INFO_QUERY + tokenManager.getComponentAccessToken();
    }

    /**
     * @ConfigurationProperties 只会调用非静态的set方法
     */
    public void setAppxRedirectUrl(String appxRedirectUrl) {
        APPX_REDIRECT_URL = appxRedirectUrl;
    }

    public void setAppxthirdPartyAppid(String appxthirdPartyAppid) {
        APPX_THIRDPARTY_APPID = appxthirdPartyAppid;
    }

    public void setAppxAppSecret(String appxAppSecret) {
        APPX_APP_SECRET = appxAppSecret;
    }

    public void setComponentAccessTokenQuery(String componentAccessTokenQuery) {
        COMPONENT_ACCESS_TOKEN_QUERY = componentAccessTokenQuery;
    }

    public void setPreAuthCodeQuery(String preAuthCodeQuery) {
        PRE_AUTH_CODE_QUERY = preAuthCodeQuery;
    }

    public void setApiQueryAuth(String apiQueryAuth) {
        API_QUERY_AUTH = apiQueryAuth;
    }

    public void setHttpWxappAuthPage(String httpWxappAuthPage) {
        HTTP_WXAPP_AUTH_PAGE = httpWxappAuthPage;
    }

    public void setProgramValidateToken(String programValidateToken) {
        PROGRAM_VALIDATE_TOKEN = programValidateToken;
    }

    public void setProgramEncodingAesKey(String programEncodingAesKey) {
        PROGRAM_ENCODING_AES_KEY = programEncodingAesKey;
    }

    public void setModifyDomainQuery(String modifyDomainQuery) {
        MODIFY_DOMAIN_QUERY = modifyDomainQuery;
    }

    public void setCommitCodeQuery(String commitCodeQuery) {
        COMMIT_CODE_QUERY = commitCodeQuery;
    }

    public void setQrCodeQuery(String qrCodeQuery) {
        QR_CODE_QUERY = qrCodeQuery;
    }

    public void setCategoryQuery(String categoryQuery) {
        CATEGORY_QUERY = categoryQuery;
    }

    public void setGetPageQuery(String getPageQuery) {
        GET_PAGE_QUERY = getPageQuery;
    }

    public void setSubmitAuditQuery(String submitAuditQuery) {
        SUBMIT_AUDIT_QUERY = submitAuditQuery;
    }

    public void setLatestAuditStatusQuery(String latestAuditStatusQuery) {
        LATEST_AUDIT_STATUS_QUERY = latestAuditStatusQuery;
    }

    public void setReleaseQuery(String releaseQuery) {
        RELEASE_QUERY = releaseQuery;
    }

    public void setAppInfo(String appInfo) {
        APP_INFO = appInfo;
    }

    public void setAuthorizerInfoQuery(String authorizerInfoQuery) {
        AUTHORIZER_INFO_QUERY = authorizerInfoQuery;
    }

    public void setWxLogin(String wxLogin) {
        WX_LOGIN = wxLogin;
    }

    public void setGetLatestAuditstatus(String getLatestAuditstatus) {
        GET_LATEST_AUDITSTATUS = getLatestAuditstatus;
    }

    public void setReqSignupWxapp(String reqSignupWxapp) {
        REQ_SIGNUP_WXAPP = reqSignupWxapp;
    }

    public void setReqLoginWxapp(String reqLoginWxapp) {
        REQ_LOGIN_WXAPP = reqLoginWxapp;
    }
}


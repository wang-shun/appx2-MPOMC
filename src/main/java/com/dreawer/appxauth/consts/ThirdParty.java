package com.dreawer.appxauth.consts;

import com.dreawer.appxauth.manager.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * <CODE>thirdParty</CODE>
 *
 * @author fenrir
 * @Date 18-7-3
 */
@Configuration
@ConfigurationProperties(prefix = "thirdParty")
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
    public static final String AUTHORIZER_REFRESH_TOKEN = "authorizer_refresh_token";
    public static final String PERSIONAL = "个人";


    //开放平台授权相关
    @Value("${thirdParty.APPX_THIRDPARTY_APPID}")
    public String APPX_THIRDPARTY_APPID = null;
    @Value("${thirdParty.APPX_APP_SECRET}")
    public String APPX_APP_SECRET = null;
    @Value("${thirdParty.PROGRAM_VALIDATE_TOKEN}")
    public String PROGRAM_VALIDATE_TOKEN = null;
    @Value("${thirdParty.PROGRAM_ENCODING_AES_KEY}")
    public String PROGRAM_ENCODING_AES_KEY = null;
    @Value("${thirdParty.APPX_REDIRECT_URL}")
    public String APPX_REDIRECT_URL = null;

    //接口URL
    public String COMPONENT_ACCESS_TOKEN_QUERY = "https://api.weixin.qq.com/cgi-bin/component/api_component_token";
    public String PRE_AUTH_CODE_QUERY = "https://api.weixin.qq.com/cgi-bin/component/api_create_preauthcode?component_access_token=";
    public String API_QUERY_AUTH = "https://api.weixin.qq.com/cgi-bin/component/api_query_auth?component_access_token=";
    public String HTTP_WXAPP_AUTH_PAGE = "https://mp.weixin.qq.com/cgi-bin/componentloginpage";
    public String MODIFY_DOMAIN_QUERY = "https://api.weixin.qq.com/wxa/modify_domain?access_token=";
    public String COMMIT_CODE_QUERY = "https://api.weixin.qq.com/wxa/commit?access_token=";
    public String QR_CODE_QUERY = "https://api.weixin.qq.com/wxa/get_qrcode?access_token=";
    public String CATEGORY_QUERY = "https://api.weixin.qq.com/wxa/get_category?access_token=";
    public String GET_PAGE_QUERY = "https://api.weixin.qq.com/wxa/get_page?access_token=";
    public String SUBMIT_AUDIT_QUERY = "https://api.weixin.qq.com/wxa/submit_audit?access_token=";
    public String LATEST_AUDIT_STATUS_QUERY = "https://api.weixin.qq.com/wxa/get_latest_auditstatus?access_token=";
    public String RELEASE_QUERY = "https://api.weixin.qq.com/wxa/release?access_token=";
    public String APP_INFO = "https://api.weixin.qq.com/cgi-bin/account/getaccountbasicinfo?access_token=";
    public String AUTHORIZER_INFO_QUERY = "https://api.weixin.qq.com/cgi-bin/component/api_get_authorizer_info?component_access_token=";
    public String WX_LOGIN = "https://api.weixin.qq.com/sns/component/jscode2session?";
    public String GET_LATEST_AUDITSTATUS = "https://api.weixin.qq.com/wxa/get_latest_auditstatus?access_token=";
    public String REFRESH_TOKEN = "https://api.weixin.qq.com/cgi-bin/component/api_authorizer_token?component_access_token=";

    //其他服务调用
    //用户中心调用小程序用户首次注册
    @Value("${thirdParty.REQ_SIGNUP_WXAPP}")
    public String REQ_SIGNUP_WXAPP = null;
    //小程序登录
    @Value("${thirdParty.REQ_LOGIN_WXAPP}")
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

    public String URL_REFRESH_TOKEN() throws IOException {
        return REFRESH_TOKEN + tokenManager.getComponentAccessToken();
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

    public String URL_AUTH_PAGE(String id) throws IOException {
        return HTTP_WXAPP_AUTH_PAGE + "?"
                + COMPONENT_APPID
                + "="
                + APPID() + "&"
                + PRE_AUTH_CODE
                + "="
                + tokenManager.getPreAuthCode() + "&"
                + REDIRECT_URL
                + "="
                + APPX_REDIRECT_URL + "?id=" + id + "&"
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

    public static String getComponentAppid() {
        return COMPONENT_APPID;
    }

    public static String getComponentAccessToken() {
        return COMPONENT_ACCESS_TOKEN;
    }

    public static String getComponentAppsecret() {
        return COMPONENT_APPSECRET;
    }

    public static String getComponentVerifyTicket() {
        return COMPONENT_VERIFY_TICKET;
    }

    public static String getAuthorizationCode() {
        return AUTHORIZATION_CODE;
    }

    public static String getPreAuthCode() {
        return PRE_AUTH_CODE;
    }

    public static String getRedirectUrl() {
        return REDIRECT_URL;
    }

    public static String getAuthType() {
        return AUTH_TYPE;
    }

    public static String getAuthorizerAppid() {
        return AUTHORIZER_APPID;
    }

    public static String getPERSIONAL() {
        return PERSIONAL;
    }

    /**
     * @ConfigurationProperties 只会调用非静态的set方法
     */

    public TokenManager getTokenManager() {
        return tokenManager;
    }

    public void setTokenManager(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }



    public String getAPPX_APP_SECRET() {
        return APPX_APP_SECRET;
    }

    public void setAPPX_APP_SECRET(String APPX_APP_SECRET) {
        this.APPX_APP_SECRET = APPX_APP_SECRET;
    }

    public String getPROGRAM_VALIDATE_TOKEN() {
        return PROGRAM_VALIDATE_TOKEN;
    }

    public void setPROGRAM_VALIDATE_TOKEN(String PROGRAM_VALIDATE_TOKEN) {
        this.PROGRAM_VALIDATE_TOKEN = PROGRAM_VALIDATE_TOKEN;
    }

    public String getPROGRAM_ENCODING_AES_KEY() {
        return PROGRAM_ENCODING_AES_KEY;
    }

    public void setPROGRAM_ENCODING_AES_KEY(String PROGRAM_ENCODING_AES_KEY) {
        this.PROGRAM_ENCODING_AES_KEY = PROGRAM_ENCODING_AES_KEY;
    }

    public String getCOMPONENT_ACCESS_TOKEN_QUERY() {
        return COMPONENT_ACCESS_TOKEN_QUERY;
    }

    public void setCOMPONENT_ACCESS_TOKEN_QUERY(String COMPONENT_ACCESS_TOKEN_QUERY) {
        this.COMPONENT_ACCESS_TOKEN_QUERY = COMPONENT_ACCESS_TOKEN_QUERY;
    }

    public String getPRE_AUTH_CODE_QUERY() {
        return PRE_AUTH_CODE_QUERY;
    }

    public void setPRE_AUTH_CODE_QUERY(String PRE_AUTH_CODE_QUERY) {
        this.PRE_AUTH_CODE_QUERY = PRE_AUTH_CODE_QUERY;
    }

    public String getAPI_QUERY_AUTH() {
        return API_QUERY_AUTH;
    }

    public void setAPI_QUERY_AUTH(String API_QUERY_AUTH) {
        this.API_QUERY_AUTH = API_QUERY_AUTH;
    }

    public String getHTTP_WXAPP_AUTH_PAGE() {
        return HTTP_WXAPP_AUTH_PAGE;
    }

    public void setHTTP_WXAPP_AUTH_PAGE(String HTTP_WXAPP_AUTH_PAGE) {
        this.HTTP_WXAPP_AUTH_PAGE = HTTP_WXAPP_AUTH_PAGE;
    }

    public String getAPPX_REDIRECT_URL() {
        return APPX_REDIRECT_URL;
    }

    public void setAPPX_REDIRECT_URL(String APPX_REDIRECT_URL) {
        this.APPX_REDIRECT_URL = APPX_REDIRECT_URL;
    }

    public String getMODIFY_DOMAIN_QUERY() {
        return MODIFY_DOMAIN_QUERY;
    }

    public void setMODIFY_DOMAIN_QUERY(String MODIFY_DOMAIN_QUERY) {
        this.MODIFY_DOMAIN_QUERY = MODIFY_DOMAIN_QUERY;
    }

    public String getCOMMIT_CODE_QUERY() {
        return COMMIT_CODE_QUERY;
    }

    public void setCOMMIT_CODE_QUERY(String COMMIT_CODE_QUERY) {
        this.COMMIT_CODE_QUERY = COMMIT_CODE_QUERY;
    }

    public String getQR_CODE_QUERY() {
        return QR_CODE_QUERY;
    }

    public void setQR_CODE_QUERY(String QR_CODE_QUERY) {
        this.QR_CODE_QUERY = QR_CODE_QUERY;
    }

    public String getCATEGORY_QUERY() {
        return CATEGORY_QUERY;
    }

    public void setCATEGORY_QUERY(String CATEGORY_QUERY) {
        this.CATEGORY_QUERY = CATEGORY_QUERY;
    }

    public String getGET_PAGE_QUERY() {
        return GET_PAGE_QUERY;
    }

    public void setGET_PAGE_QUERY(String GET_PAGE_QUERY) {
        this.GET_PAGE_QUERY = GET_PAGE_QUERY;
    }

    public String getSUBMIT_AUDIT_QUERY() {
        return SUBMIT_AUDIT_QUERY;
    }

    public void setSUBMIT_AUDIT_QUERY(String SUBMIT_AUDIT_QUERY) {
        this.SUBMIT_AUDIT_QUERY = SUBMIT_AUDIT_QUERY;
    }

    public String getLATEST_AUDIT_STATUS_QUERY() {
        return LATEST_AUDIT_STATUS_QUERY;
    }

    public void setLATEST_AUDIT_STATUS_QUERY(String LATEST_AUDIT_STATUS_QUERY) {
        this.LATEST_AUDIT_STATUS_QUERY = LATEST_AUDIT_STATUS_QUERY;
    }

    public String getRELEASE_QUERY() {
        return RELEASE_QUERY;
    }

    public void setRELEASE_QUERY(String RELEASE_QUERY) {
        this.RELEASE_QUERY = RELEASE_QUERY;
    }

    public String getAPP_INFO() {
        return APP_INFO;
    }

    public void setAPP_INFO(String APP_INFO) {
        this.APP_INFO = APP_INFO;
    }

    public String getAUTHORIZER_INFO_QUERY() {
        return AUTHORIZER_INFO_QUERY;
    }

    public void setAUTHORIZER_INFO_QUERY(String AUTHORIZER_INFO_QUERY) {
        this.AUTHORIZER_INFO_QUERY = AUTHORIZER_INFO_QUERY;
    }

    public String getWX_LOGIN() {
        return WX_LOGIN;
    }

    public void setWX_LOGIN(String WX_LOGIN) {
        this.WX_LOGIN = WX_LOGIN;
    }

    public String getGET_LATEST_AUDITSTATUS() {
        return GET_LATEST_AUDITSTATUS;
    }

    public void setGET_LATEST_AUDITSTATUS(String GET_LATEST_AUDITSTATUS) {
        this.GET_LATEST_AUDITSTATUS = GET_LATEST_AUDITSTATUS;
    }

    public String getREQ_SIGNUP_WXAPP() {
        return REQ_SIGNUP_WXAPP;
    }

    public void setREQ_SIGNUP_WXAPP(String REQ_SIGNUP_WXAPP) {
        this.REQ_SIGNUP_WXAPP = REQ_SIGNUP_WXAPP;
    }

    public String getREQ_LOGIN_WXAPP() {
        return REQ_LOGIN_WXAPP;
    }

    public void setREQ_LOGIN_WXAPP(String REQ_LOGIN_WXAPP) {
        this.REQ_LOGIN_WXAPP = REQ_LOGIN_WXAPP;
    }
}


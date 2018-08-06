package com.dreawer.appxauth.manager;

import com.dreawer.appxauth.consts.ThirdParty;
import com.dreawer.appxauth.domain.AuthInfo;
import com.dreawer.appxauth.lang.ResultType;
import com.dreawer.appxauth.model.AuthorizeInfo;
import com.dreawer.appxauth.model.func_info;
import com.dreawer.appxauth.service.AuthService;
import com.dreawer.appxauth.utils.Okhttp;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <CODE>AppManager</CODE>
 *
 * @author fenrir
 * @Date 18-7-6
 */
@Slf4j
@Component
public class AppManager {

    private Logger logger = Logger.getLogger(this.getClass()); // 日志记录器


    @Autowired
    private AuthService authService;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 获取用户小程序authorizerAccessToken
     *
     * @param appid 小程序appid
     * @return
     */
    public String getAccessToken(String appid) {
        AuthInfo authInfo = authService.findByAppid(appid);
        if (authInfo != null) {
            return authInfo.getAccessToken();
        } else {
            return null;
        }
    }

    /**
     * 授权流程判断,判断用户小程序是否具备部署条件
     *
     * @param appid 小程序appid
     * @return
     * @throws IOException
     */
    public List<ResultType> checkAuthorCondition(String appid) throws IOException {
        List<ResultType> list = new ArrayList<>();
        //首先判断主体信息
        String authorizerInfo = getAuthorizerInfo(appid);
        AuthorizeInfo authorizeInfo = new Gson().fromJson(authorizerInfo, AuthorizeInfo.class);
        if (authorizeInfo.getAuthorizer_info().getPrincipal_name().equals(ThirdParty.PERSIONAL)) {
            list.add(ResultType.PRINCIPAL);
        }
        //然后判断权限
        Boolean fun17 = false;
        Boolean fun18 = false;
        Boolean fun25 = false;
        List<func_info> func_info = authorizeInfo.getAuthorization_info().getFunc_info();
        for (func_info funcInfo : func_info) {
            if (funcInfo.getFuncscope_category().getId() == 17) {
                fun17 = true;
            }
            if (funcInfo.getFuncscope_category().getId() == 18) {
                fun18 = true;
            }
            if (funcInfo.getFuncscope_category().getId() == 25) {
                fun25 = true;
            }
        }
        if (!fun17 || !fun18 || !fun25) {
            list.add(ResultType.PERMISSIONDENIED);
        }
        //再判断名称是否填写
        if (StringUtils.isBlank(authorizeInfo.getAuthorizer_info().getNick_name())) {
            list.add(ResultType.NAME);
        }
        //最后判断类目是否填写
        String category = getCategory(appid);
        JSONObject jsonObject = new JSONObject(category);
        if (jsonObject.getJSONArray("category_list").length() == 0) {
            list.add(ResultType.CATEGORY);
        }
        return list;
    }


    /**
     * 修改小程序域名
     *
     * @param appid   小程序appid
     * @param domains 小程序域名
     * @throws IOException
     */
    public String modifyDomain(String appid, String... domains) throws IOException {
        StringBuilder domainList = new StringBuilder();
        domainList.append("[");
        for (String domain : domains) {
            domainList.append(domain + ",");
        }
        domainList.deleteCharAt(domainList.length() - 1);
        domainList.append("]");
        String domain = domainList.toString();
        Map<String, String> params = new HashMap<>();
        params.put("action", "add");
        params.put("requestdomain", domain);
        params.put("wsrequestdomain", domain);
        params.put("uploaddomain", domain);
        params.put("downloaddomain", domain);
        String response = Okhttp.postSyncJson(ThirdParty.URL_MODIFY_DOMAIN(getAccessToken(appid)), params);
        return response;
    }

    /**
     * 提交小程序代码
     *
     * @param appid 小程序appid
     * @return
     * @throws IOException
     */
    public String commit(String appid, String templateId, String storeId, String domain) throws IOException {
        Map<String, Object> param = new HashMap<>();
        param.put("template_id", templateId);
        param.put("user_version", "V1.0");
        param.put("user_desc", "小程序体验");

        Map<String, Object> ext = new HashMap<>();
        Map<String, Object> extJson = new HashMap<>();
        extJson.put("name", "极乐科技");
        extJson.put("appid", appid);
        extJson.put("storeId", storeId);
        if (domain.endsWith("/")) {
            extJson.put("domain", domain);
        } else {
            extJson.put("domain", domain + "/");
        }
        extJson.put("account", "https://account.dreawer.com/");
        extJson.put("domainImg", "https://image.dreawer.com/");
        extJson.put("vipmain", "https://member.dreawer.com/");
        extJson.put("domainBase", "https://basedata.dreawer.com/");
        extJson.put("domainImgUpload", "https://imageupload.dreawer.com/");
        //extJson.put("indexType", "NOVIP");
//            JSONObject ald = new JSONObject();
//            ald.put("app_key", appKey);
//            ald.put("getLocation", 0);
//            ald.put("getUserinfo", 0);
//            extJson.put("ald_config", ald);
        ext.put("extEnable", true);
        ext.put("extAppid", appid);
        ext.put("ext", extJson);
        param.put("ext_json", ext.toString());
        String response = Okhttp.postSyncJson(ThirdParty.URL_COMMIT_CODE(getAccessToken(appid)), param);
        return response;
    }

    /**
     * 获取小程序预览二维码
     *
     * @param appid 小程序appid
     * @return
     * @throws IOException
     */
    public String getPreviewQR(String appid) throws IOException {
        String response = Okhttp.getSync(ThirdParty.URL_QR_CODE(getAccessToken(appid)));
        return response;
    }

    /**
     * 获取小程序可选类目
     *
     * @param appid 小程序appid
     * @return
     * @throws IOException
     */
    public String getCategory(String appid) throws IOException {
        return Okhttp.getSync(ThirdParty.URL_CATEGORY_QUERY(getAccessToken(appid)));
    }

    /**
     * 获取小程序页面配置
     *
     * @param appid 小程序appid
     * @return
     * @throws IOException
     */
    public String getPage(String appid) throws IOException {
        return Okhttp.getSync(ThirdParty.URL_GET_PAGE(getAccessToken(appid)));
    }


    /**
     * 将第三方提交的代码包提交审核 (请确保accessToken已刷新)
     *
     * @param appid
     * @return 提交审核号
     */
    public String submitAudit(String appid, String tags) throws JSONException, IOException {
        String pageInfo = getPage(appid);
        JSONObject page = new JSONObject(pageInfo);
        JSONArray page_list = page.getJSONArray("page_list");

        String category = getCategory(appid);
        JSONObject jsonObject = new JSONObject(category);
        JSONArray category_list = jsonObject.getJSONArray("category_list");

        Map<String, Object> items = new HashMap<>();
        String indexPage = page_list.get(0).toString();
        String firstClass = category_list.getJSONObject(0).get("first_class").toString();
        String secondClass = category_list.getJSONObject(0).get("second_class").toString();
        Integer firstId = (Integer) category_list.getJSONObject(0).get("first_id");
        Integer secondId = (Integer) category_list.getJSONObject(0).get("second_id");
        //目前这里为了通过审核先固定写成首页,日后再根据情况填写具体页面
        items.put("address", indexPage);
        items.put("tag", tags);
        items.put("first_class", firstClass);
        items.put("second_class", secondClass);
        items.put("first_id", firstId);
        items.put("second_id", secondId);
        items.put("title", "首页");
        ArrayList<Map> array = new ArrayList<>();
        array.add(items);
        Map<String, Object> result = new HashMap<>();
        result.put("item_list", array);
        log.debug(new Gson().toJson(result));
        String response = Okhttp.postSyncJson(ThirdParty.URL_SUBMIT_AUDIT(getAccessToken(appid)), result);
        //判断返回结果
        //TODO
        JSONObject auditResult = new JSONObject(response);
        String auditId = auditResult.get("auditid").toString();
        return auditId;
    }

    /**
     * 查询最新一次提交的审核状态
     *
     * @param appid 小程序appid
     * @return
     * @throws IOException
     */
    public String getLatestAuditStatus(String appid) throws IOException {
        return Okhttp.getSync(ThirdParty.URL_SUBMIT_AUDIT(getAccessToken(appid)));
    }


    /**
     * 发布已通过审核的小程序
     *
     * @param appid 小程序appid
     * @return
     * @throws IOException
     */
    public String release(String appid) throws IOException {
        Map<String, Object> items = new HashMap<>();
        return Okhttp.postSyncJson(ThirdParty.URL_RELEASE_QUERY(getAccessToken(appid)), items);
    }

    /**
     * 获取帐号基本信息
     *
     * @param appid 小程序appid
     * @return
     * @throws IOException
     */
    public String getAppInfo(String appid) throws IOException {
        String response = Okhttp.getSync(ThirdParty.URL_GET_APP_INFO(getAccessToken(appid)));
        return response;
    }

    /**
     * 获取授权小程序帐号主体和权限信息
     *
     * @param appid 小程序appid
     * @return
     * @throws IOException
     */
    public String getAuthorizerInfo(String appid) throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put(ThirdParty.COMPONENT_APPID, ThirdParty.APPX_THIRDPARTY_APPID);
        params.put(ThirdParty.AUTHORIZER_APPID, appid);
        String response = Okhttp.postSyncJson(ThirdParty.URL_GET_AUTHORIZER_INFO(), params);
        return response;
    }

    /**
     * 微信登录获取openId
     *
     * @param appid  小程序appid
     * @param jsCode 前端码
     * @return
     * @throws IOException
     */
    public String wxLogin(String appid, String jsCode) throws IOException {
        return Okhttp.getSync(ThirdParty.URL_WX_LOGIN(appid, jsCode));
    }

    /**
     * 注册小程序用户到用户中心
     *
     * @param petName
     * @param appid
     * @param mugshot
     * @return
     */
    public String signUp(String petName, String appid, String mugshot) throws IOException {
        Map<String, Object> param = new HashMap<>();
        param.put("mugshot", mugshot);
        param.put("petName", petName);
        param.put("appId", appid);
        //return Okhttp.postSyncJson(ThirdParty.REQ_SIGNUP_WXAPP, param);
        return restPost(ThirdParty.REQ_SIGNUP_WXAPP, param, null);
    }

    /**
     * 注册小程序用户到用户中心
     *
     * @return
     */
    public String getAuthorization(Map map) throws IOException {
        //return Okhttp.postSyncJson(ThirdParty.REQ_SIGNUP_WXAPP, param);
        return restPost(ThirdParty.REQ_LOGIN_WXAPP, map, null);
    }


//    public String signIn(String userId){
//        Map<String,Object> param  = new HashMap<>();
//        param.put("userId",userId);
//        Okhttp.postSyncJson()
//    }


    public String restPost(String url, Object data, String userId) {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.set("userId", userId);
        Gson gson = new Gson();
        String json = gson.toJson(data);
        logger.info(json);
        HttpEntity<String> entity = new HttpEntity<String>(json, headers);
        String response = restTemplate.postForObject(url, entity, String.class);
        logger.info(response);
        return response;
    }

}

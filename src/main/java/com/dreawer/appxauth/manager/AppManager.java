package com.dreawer.appxauth.manager;

import com.dreawer.appxauth.consts.ThirdParty;
import com.dreawer.appxauth.domain.AuthInfo;
import com.dreawer.appxauth.lang.ResultType;
import com.dreawer.appxauth.model.AuthorizeInfo;
import com.dreawer.appxauth.model.func_info;
import com.dreawer.appxauth.service.AuthService;
import com.dreawer.appxauth.utils.JsonFormatUtil;
import com.dreawer.appxauth.utils.Okhttp;
import com.dreawer.responsecode.rcdt.ResponseCode;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    private Okhttp okhttp;

    @Autowired
    private ThirdParty thirdParty;


    /**
     * 获取用户小程序authorizerAccessToken
     *
     * @param appid 小程序appid
     * @return
     */
    public String getAccessToken(String appid) throws IOException {
        AuthInfo authInfo = authService.findByAppid(appid);
        log.info("小程序授权信息查询结果为:" + JsonFormatUtil.formatJson(authInfo));
        if (authInfo != null) {
            String accessToken = authInfo.getAccessToken();
            ResponseCode responseCode = okhttp.testToken(accessToken, appid);
            return responseCode.getData().toString();
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
        AuthorizeInfo authorizerInfo = getAuthorizerInfo(appid);
        if (authorizerInfo.getAuthorizer_info().getPrincipal_name().equals(thirdParty.PERSIONAL)) {
            list.add(ResultType.PRINCIPAL);
        }
        //然后判断权限
        Boolean fun17 = false;
        Boolean fun18 = false;
        List<func_info> func_info = authorizerInfo.getAuthorization_info().getFunc_info();
        for (func_info funcInfo : func_info) {
            if (funcInfo.getFuncscope_category().getId() == 17) {
                fun17 = true;
            }
            if (funcInfo.getFuncscope_category().getId() == 18) {
                fun18 = true;
            }

        }
        //如果未提供开发权限,则无法继续调用其他接口,直接返回
        if (!fun17 || !fun18) {
            list.add(ResultType.PERMISSIONDENIED);
            log.info("未提供开发权限");
            return list;
        }
        //再判断名称是否填写
        if (StringUtils.isBlank(authorizerInfo.getAuthorizer_info().getNick_name())) {
            list.add(ResultType.NAME);
        }
        //最后判断类目是否填写
        String category = getCategory(appid);
        JSONObject jsonObject = new JSONObject(category);
        if (jsonObject.getJSONArray("category_list").length() == 0) {
            list.add(ResultType.CATEGORY);
        }
        log.info("检查权限结果:");
        list.forEach(x -> {
            log.info("权限：" + x);
        });
        log.info("总数:" + list.size());
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
        Map<String, Object> params = new HashMap<>();
        params.put("action", "set");
        params.put("requestdomain", domains);
        log.info("添加域名:" + JsonFormatUtil.formatJson(params));
        String response = okhttp.postSyncJson(thirdParty.URL_MODIFY_DOMAIN(getAccessToken(appid)), params);
        return response;
    }

    /**
     * 提交小程序代码
     *
     * @param appid 小程序appid
     * @return
     * @throws IOException
     */
    public String commit(String appid, String templateId, String storeId) throws IOException {
        Map<String, Object> param = new HashMap<>();
        param.put("template_id", templateId);
        param.put("user_version", "V1.0");
        param.put("user_desc", "小程序体验");

        Map<String, Object> ext = new HashMap<>();
        Map<String, Object> extJson = new HashMap<>();
        extJson.put("name", "极乐科技");
        extJson.put("appid", appid);
        extJson.put("storeId", storeId);
        extJson.put("domain", thirdParty.APP_REQ_DOMAIN);
        extJson.put("domainImg", thirdParty.IMG_DOMAIN);
        extJson.put("domainBase", thirdParty.BASE_DOMAIN);
        extJson.put("domainImgUpload", thirdParty.IMG_TEST);
        ext.put("extEnable", true);
        ext.put("extAppid", appid);
        ext.put("ext", extJson);
        String ext_json = new Gson().toJson(ext);
        param.put("ext_json", ext_json);
        log.info("提交ext文件为:" + JsonFormatUtil.formatJson(param));
        String response = okhttp.postSyncJson(thirdParty.URL_COMMIT_CODE(getAccessToken(appid)), param);
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
        String response = okhttp.getSync(thirdParty.URL_QR_CODE(getAccessToken(appid)));
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
        return okhttp.getSync(thirdParty.URL_CATEGORY_QUERY(getAccessToken(appid)));
    }

    /**
     * 获取小程序页面配置
     *
     * @param appid 小程序appid
     * @return
     * @throws IOException
     */
    public String getPage(String appid) throws IOException {
        return okhttp.getSync(thirdParty.URL_GET_PAGE(getAccessToken(appid)));
    }


    /**
     * 将第三方提交的代码包提交审核 (请确保accessToken已刷新)
     *
     * @param appid
     * @return 提交审核号
     */
    public String submitAudit(String appid) throws JSONException, IOException {


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
        items.put("tag", "APPX");
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
        String response = okhttp.postSyncJson(thirdParty.URL_SUBMIT_AUDIT(getAccessToken(appid)), result);
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
        return okhttp.getSync(thirdParty.URL_SUBMIT_AUDIT(getAccessToken(appid)));
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
        return okhttp.postSyncJson(thirdParty.URL_RELEASE_QUERY(getAccessToken(appid)), items);
    }

    /**
     * 获取帐号基本信息
     *
     * @param appid 小程序appid
     * @return
     * @throws IOException
     */
    public String getAppInfo(String appid) throws IOException {
        String response = okhttp.getSync(thirdParty.URL_GET_APP_INFO(getAccessToken(appid)));
        return response;
    }

    /**
     * 获取授权小程序帐号主体和权限信息
     *
     * @param appid 小程序appid
     * @return
     * @throws IOException
     */
    public AuthorizeInfo getAuthorizerInfo(String appid) throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put(thirdParty.COMPONENT_APPID, thirdParty.APPX_THIRDPARTY_APPID);
        params.put(thirdParty.AUTHORIZER_APPID, appid);
        String response = okhttp.postSyncJson(thirdParty.URL_GET_AUTHORIZER_INFO(), params);
        return new Gson().fromJson(response, AuthorizeInfo.class);
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
        return okhttp.getSync(thirdParty.URL_WX_LOGIN(appid, jsCode));
    }


}

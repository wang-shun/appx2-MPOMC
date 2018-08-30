package com.dreawer.appxauth.manager;

import com.dreawer.appxauth.RibbonClient.form.ViewGoods;
import com.dreawer.appxauth.consts.ThirdParty;
import com.dreawer.appxauth.utils.CallRequest;
import com.dreawer.appxauth.utils.JsonFormatUtil;
import com.dreawer.responsecode.rcdt.ResponseCode;
import com.google.gson.Gson;
import com.google.gson.JsonPrimitive;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * <CODE>ServiceManager</CODE>
 *
 * @author fenrir
 * @Date 18-8-6
 */

@Component
public class ServiceManager {

    private Logger logger = Logger.getLogger(this.getClass()); // 日志记录器

    @Autowired
    private CallRequest callRequest;

    @Autowired
    private ThirdParty thirdParty;

    private String addOrganizationUrl = "http://cc/orgaize/add";

    private String goodDetail = "http://gc/goods/detail";

    private String addEnterprise = "http://sc/enterprise/add";

    private String initAccount = "http://bsmc/init";

    /**
     * 注册小程序用户到用户中心
     *
     * @param petName
     * @param appid
     * @param mugshot
     * @return
     */
    public ResponseCode signUp(String petName, String appid, String mugshot) throws Exception {
        Map<String, Object> param = new HashMap<>();
        param.put("mugshot", mugshot);
        param.put("petName", petName);
        param.put("appId", appid);
        ResponseCode responseCode = callRequest.restPost(thirdParty.REQ_SIGNUP_WXAPP, param, null);
        logger.info("注册小程序用户到用户中心返回:" + JsonFormatUtil.formatJson(responseCode));
        return responseCode;
    }

    /**
     * 小程序登录
     *
     * @return
     */
    public ResponseCode getAuthorization(Map map) throws Exception {
        ResponseCode responseCode = callRequest.restPost(thirdParty.REQ_LOGIN_WXAPP, map, null);
        logger.info("小程序登录返回:" + JsonFormatUtil.formatJson(responseCode));
        return responseCode;
    }


    /**
     * 调用CC获取组织ID
     *
     * @return
     */
    public String addOrganzation(Map<String, Object> param) throws Exception {
        ResponseCode responseCode = callRequest.restPost(addOrganizationUrl, param, null);
        logger.info("获取组织ID返回:" + JsonFormatUtil.formatJson(responseCode));
        JsonPrimitive data = (JsonPrimitive) responseCode.getData();
        return data.getAsString();

    }


    /**
     * 调用GC获取商品详情
     * * @return
     */
    public ViewGoods goodDetail(String spuId, String userId) {
        ResponseCode responseCode = callRequest.restGet(goodDetail + "?id=" + spuId, userId);
        logger.info("商品详情返回:" + JsonFormatUtil.formatJson(responseCode));
        ViewGoods viewGoods = new Gson().fromJson(responseCode.getData().toString(), ViewGoods.class);
        return viewGoods;
    }


    /**
     * 创建管理员帐号返回
     * * @return
     */
    public ResponseCode initAccount(String applicationId, String type, String phoneNumber) throws Exception {
        Map<String, Object> param = new HashMap<>();
        param.put("appId", applicationId);
        param.put("type", type);
        param.put("phoneNumber", phoneNumber);
        ResponseCode responseCode = callRequest.restPost(goodDetail, param, null);
        logger.info("创建管理员帐号返回:" + JsonFormatUtil.formatJson(responseCode));
        return responseCode;
    }

    /**
     * 调用店铺中心生成店铺
     * @return
     * @throws Exception
     */
    public ResponseCode addStore(String appid, String nick_name, String principal_name, String head_img, String signature, String userId) throws Exception {
        Map<String, Object> addStoreParam = new HashMap<>();
        if (!StringUtils.isBlank(principal_name)) {
            addStoreParam.put("name", principal_name);
        }
        addStoreParam.put("appName", nick_name);
        addStoreParam.put("logo", head_img);
        addStoreParam.put("intro", signature);
        addStoreParam.put("appid", appid);
        ResponseCode responseCode = callRequest.restPost(addEnterprise, addStoreParam, userId);
        logger.info("生成店铺返回:" + JsonFormatUtil.formatJson(responseCode));
        return responseCode;
    }


}

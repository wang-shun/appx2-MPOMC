package com.dreawer.appxauth.manager;

import com.dreawer.appxauth.RibbonClient.form.ViewGoods;
import com.dreawer.appxauth.consts.ThirdParty;
import com.dreawer.appxauth.utils.CallRequest;
import com.dreawer.responsecode.rcdt.ResponseCode;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
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

    private String addOrganizationUrl = "http://cc/orgaize/add";

    private String goodDetail = "http://gc/goods/detail";

    /**
     * 注册小程序用户到用户中心
     *
     * @param petName
     * @param appid
     * @param mugshot
     * @return
     */
    public ResponseCode signUp(String petName, String appid, String mugshot) throws IOException {
        Map<String, Object> param = new HashMap<>();
        param.put("mugshot", mugshot);
        param.put("petName", petName);
        param.put("appId", appid);
        return callRequest.restPost(ThirdParty.REQ_SIGNUP_WXAPP, param, null);
    }

    /**
     * 注册小程序用户到用户中心
     *
     * @return
     */
    public ResponseCode getAuthorization(Map map) {
        return callRequest.restPost(ThirdParty.REQ_LOGIN_WXAPP, map, null);
    }


    /**
     * 调用CC获取组织ID
     *
     * @return
     */
    public String addOrganzation(Map<String, Object> param) {
        ResponseCode responseCode = callRequest.restPost(addOrganizationUrl, param, null);
        return responseCode.getData().toString();

    }


    /**
     * 调用GC获取商品详情
     * * @return
     */
    public ViewGoods goodDetail(String spuId, String userId) {
        Map<String, Object> param = new HashMap<>();
        param.put("id", spuId);
        ResponseCode responseCode = callRequest.restGet(goodDetail, param, userId);
        ViewGoods viewGoods = new Gson().fromJson(responseCode.getData().toString(), ViewGoods.class);
        return viewGoods;
    }




}

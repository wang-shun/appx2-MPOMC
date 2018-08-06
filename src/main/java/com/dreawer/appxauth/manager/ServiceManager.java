package com.dreawer.appxauth.manager;

import com.dreawer.appxauth.consts.ThirdParty;
import com.dreawer.appxauth.service.AuthService;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

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
    private RestTemplate restTemplate;

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

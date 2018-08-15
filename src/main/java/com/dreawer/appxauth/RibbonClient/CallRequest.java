package com.dreawer.appxauth.RibbonClient;

import com.dreawer.appxauth.RibbonClient.form.ViewGoods;
import com.dreawer.appxauth.RibbonClient.form.ViewPurchaseDetail;
import com.dreawer.appxauth.exception.ResponseCodeException;
import com.dreawer.responsecode.rcdt.ResponseCode;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <CODE>CallRequest</CODE>
 *
 * @author fenrir
 * @Date 18-8-14
 */
@Component
@Slf4j
public class CallRequest {

    @Autowired
    RestTemplate restTemplate;

    private String addOrganzationUrl = "http://cc/orgaize/add";

    private String goodDetail = "http://gc/goods/detail";


    /**
     * 调用CC获取组织ID
     *
     * @return
     */
    public String addOrganzation(Map<String, Object> param) throws ResponseCodeException {

        String response = restPost(addOrganzationUrl, param, null);
        log.info("CC返回数据:" + response);
        ResponseCode code = ResponseCode.instanceOf(response);
        if (!code.getCode().equals("000000")) {
            throw new ResponseCodeException(code);
        }

        return code.getData().toString();

    }


    public String restPost(String url, Object data, String userId) {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.set("userId", userId);
        Gson gson = new Gson();
        String json = gson.toJson(data);
        HttpEntity<String> entity = new HttpEntity<String>(json, headers);
        String response = restTemplate.postForObject(url, entity, String.class);
        System.out.println(response);
        return response;
    }


    public String restGet(String url, Object data, String userId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("userId", userId);
        String response = restTemplate.getForObject(url, String.class, data);
        System.out.println(response);
        return response;
    }

    /**
     * 调用GC获取商品详情
     * * @return
     */
    public ViewGoods goodDetail(String spuId, String userId) throws ResponseCodeException {
        Map<String, Object> param = new HashMap<>();
        param.put("id", spuId);
        String response = restGet(goodDetail, param, userId);
        log.info("商品返回数据:" + response);
        ResponseCode code = ResponseCode.instanceOf(response);
        if (!code.getCode().equals("000000")) {
            throw new ResponseCodeException(code);
        }
        ViewGoods viewGoods = new Gson().fromJson(code.getData().toString(), ViewGoods.class);
        return viewGoods;
    }

}

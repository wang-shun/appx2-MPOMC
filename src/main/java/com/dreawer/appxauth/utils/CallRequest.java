package com.dreawer.appxauth.utils;

import com.dreawer.responsecode.rcdt.ResponseCode;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

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

    public ResponseCode restPost(String url, Object data, String userId) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.set("userid", userId);
        Gson gson = new Gson();
        String json = gson.toJson(data);
        HttpEntity<String> entity = new HttpEntity<String>(json, headers);
        String response = restTemplate.postForObject(url, entity, String.class);
        return ResponseCode.instanceOf(response);

    }

    public ResponseCode restGet(String url, String userId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("userid", userId);
        String response = restTemplate.getForObject(url, String.class);
        return ResponseCode.instanceOf(response);
    }


}

package com.dreawer.appxauth.utils;

import com.google.gson.Gson;
import okhttp3.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * <CODE>Okhttp</CODE>
 *
 * @author fenrir
 * @Date 18-7-3
 */
@Component
public class Okhttp {

    private Logger logger = Logger.getLogger(Okhttp.class); // 日志记录器


    public final MediaType JSON = MediaType.parse("application/json; charset=utf-8");


    /**
     * 同步的Get请求
     *
     * @param url url
     */
    public String getSync(String url) throws IOException {
        // 创建OKHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        // 创建一个Request
        final Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        // 返回值为response
        Response response = call.execute();
        // 将response转化成String
        String responseStr = response.body().string();
        return responseStr;
    }

    /**
     * 异步的Get请求
     *
     * @param url url
     */
    public void getAsyn(String url) {
        // 创建OKHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        // 创建一个Request
        final Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        // 请求加入调度
        call.enqueue(new Callback() {
            // 请求失败的回调
            @Override
            public void onFailure(Call call, IOException e) {
                logger.error("请求失败", e);
            }

            // 请求成功的回调
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // 将response转化成String
                String responseStr = response.body().string();
            }
        });
    }

    /**
     * 同步的Post请求
     *
     * @param url    url
     * @param params params
     * @return responseStr
     * @throws IOException
     */
    public String postSync(String url, Map<String, String> params)
            throws IOException {
        // RequestBody
        RequestBody requestBody;
        if (params == null) {
            params = new HashMap<>();
        }
        // 创建OKHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        /**
         * 在这对添加的参数进行遍历
         */
        for (Map.Entry<String, String> map : params.entrySet()) {
            String key = map.getKey();
            String value;
            /**
             * 判断值是否是空的
             */
            if (map.getValue() == null) {
                value = "";
            } else {
                value = map.getValue();
            }
            /**
             * 把key和value添加到formBody中
             */
            builder.add(key, value);
        }
        requestBody = builder.build();
        // 创建一个Request
        final Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Call call = okHttpClient.newCall(request);
        // 返回值为response
        Response response = call.execute();
        // 将response转化成String
        String responseStr = response.body().string();
        return responseStr;
    }

    /**
     * 同步的Json格式POST
     *
     * @param url
     * @param RequestJsonbean
     * @return
     * @throws IOException
     */
    public String postSyncJson(String url, Object RequestJsonbean) throws IOException {

        Gson gson = new Gson();
        String json = gson.toJson(RequestJsonbean);
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, json);

        // 创建一个Request
        final Request request = new Request.Builder().url(url).post(body).build();
        Call call = okHttpClient.newCall(request);
        // 返回值为response
        Response response = call.execute();
        // 将response转化成String
        String responseStr = response.body().string();
        return responseStr;

    }

    /**
     * 同步的Json格式POST
     *
     * @param url
     * @return
     * @throws IOException
     */
    public String SimplepostSyncJson(String url, String json) throws IOException {

        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, json);

        // 创建一个Request
        final Request request = new Request.Builder().url(url).post(body).build();
        Call call = okHttpClient.newCall(request);
        // 返回值为response
        Response response = call.execute();
        // 将response转化成String
        String responseStr = response.body().string();
        return responseStr;

    }

    public String test(String key) {
        Map<String, Object> param = new HashMap<>();
        param.put("errcode", 41004);
        param.put("errmsg", "hahahah");
        return new Gson().toJson(param);
    }


}

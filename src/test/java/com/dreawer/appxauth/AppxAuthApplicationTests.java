package com.dreawer.appxauth;

import com.dreawer.appxauth.consts.ThirdParty;
import com.dreawer.appxauth.controller.AuthController;
import com.dreawer.appxauth.lang.ResultType;
import com.dreawer.appxauth.model.AuthorizeInfo;
import com.google.gson.Gson;
import org.apache.http.entity.StringEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@PropertySource("classpath:returnCode.properties")
public class AppxAuthApplicationTests {

    @Autowired
    private Environment env;


    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;

    @Autowired
    private ThirdParty thirdParty;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();//建议使用这种
    }


    @Test
    public void testTicket() throws Exception {
        String content = "<xml>    <AppId><![CDATA[wx4a24d6eb9c085429]]></AppId>    <Encrypt><![CDATA[CMoO6A0C6MIdLBE9LVepVFAcRflDNVzK/+n9gknUj1ovu1NjO2t5/GBK8JJz5tjb1Ym4ZnJf/BONVcvNn+FCzR3RyAXonBqMTDj+3JqjAejwed4sSLs3aWf084CIqg2cLxKXUCSzuI6QyY2EjRTjeHTS+f6+5ZWkYL7VBdfW+pOgwAqnrDqTc6TVRNOhjehlCuwxnsct2PNT+XfQ7xrmwJl+dd0wIWG88HnkubK8YwN6Rkv2MyzU+UFXuNLVOWnCn+H6szQRFTmMY5R8TfZ0B2IxoITZTM4NqtXIwY3DsnSJvejgzJiz+93NY0KshZ+sYq/9okxswBfkTHWlUXN83/gEF02UKkd4EeQYxKB3AGEOqkyCGwVZj6eCvY26Ek5gcByV5NhPh/TJalg8X/QOhGJI+BH2lIdAuJuWEtkv9Wn/0tiDpB1e7PYf4uICXGomernndNWuyXfQ29ooPuBJSQ==]]></Encrypt></xml>";
        StringEntity entity = new StringEntity(content);
//        mvc.perform(post("/auth/wxAppx").setEntity(entity).addParameter("timestamp","1530850678")
//        .addParameter("nonce","2139992682")
//        .addParameter("msg_signature","88555ea86d6e45af09b36ab26070bc059537a947")).andExpect(status().isOk());
//    }
        mvc.perform(MockMvcRequestBuilders.post("/event/ticket")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param("timestamp", "1530865042")
                .param("nonce", "928589160")
                .param("msg_signature", "2265e99a52671929b91882129b6bfae30d08b743")
                .content(content)


        );

    }




    @Test
    public void test3() {
        String json = "{\"authorization_info\":" +
                "{\"authorizer_appid\":\"wxa7d029a1ca729921\"" +
                ",\"authorizer_access_token\"" +
                ":\"11_I4eJBaej5yfAO_hMEU7rgoL8hnr1qM5O_tdOK5sW07G7h4EnJIvqGv3Jwn6nvuZYEC1WJLNWw66KWSc6-fljK9Q2uPbOmD8Pd9GCBAeHjXTzf305iXm_wP2ZsbUmenyBz6ynY47T_mbgU6mSWNMcAJDQHG\",\"expires_in\":7200,\"authorizer_refresh_token\":\"refreshtoken@@@G95ytEaVF_Sy5Ly1LE0h3hYFYCRSFCUitMS05I3uQE4\",\"func_info\":[{\"funcscope_category\":{\"id\":17}}," +
                "{\"funcscope_category\":{\"id\":19}},{\"funcscope_category\":" +
                "{\"id\":30},\"confirm_info\":{\"need_confirm\":0,\"already_confirm\":0,\"can_confirm\":0}},{\"funcscope_category\":{\"id\":31}," +
                "\"confirm_info\":{\"need_confirm\":0,\"already_confirm\":0,\"can_confirm\":0}}," +
                "{\"funcscope_category\":{\"id\":36}},{\"funcscope_category\":{\"id\":37}},{\"funcscope_category\":{\"id\":40}}]}}";
        AuthorizeInfo authorizeInfo = new Gson().fromJson(json, AuthorizeInfo.class);
        System.out.println(authorizeInfo.toString());
        System.out.println(authorizeInfo.getAuthorization_info().getAuthorizer_access_token());
    }

    @Autowired
    private AuthController authController;

    @Test
    public void test4() {
        List<ResultType> list = new ArrayList<>();
        list.add(ResultType.PRINCIPAL);
        StringBuilder auditResult = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            ResultType type = list.get(i);
            if (type.equals(ResultType.PERMISSIONDENIED)) {
                auditResult.append(";用户未提供开发权限");
            }
            //如果非ECS小程序为个人主体
            if (type.equals(ResultType.PRINCIPAL)) {
                auditResult.append(";小程序主体需为企业");
            }
            if (type.equals(ResultType.NAME)) {
                auditResult.append(";小程序名称未填写");
            }
            if (type.equals(ResultType.CATEGORY)) {
                auditResult.append(";小程序类目未填写");
            }
        }
        String result = auditResult.substring(1, auditResult.length());
        System.out.println(result);
    }

}

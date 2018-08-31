package com.dreawer.appxauth.controller;

import com.dreawer.appxauth.consts.ThirdParty;
import com.dreawer.appxauth.domain.Application;
import com.dreawer.appxauth.domain.ApplicationUser;
import com.dreawer.appxauth.domain.UserCase;
import com.dreawer.appxauth.form.WxLoginForm;
import com.dreawer.appxauth.lang.ResultType;
import com.dreawer.appxauth.manager.TokenManager;
import com.dreawer.appxauth.model.AuthorizeInfo;
import com.dreawer.appxauth.model.Authorizer_info;
import com.dreawer.appxauth.model.CategoryList;
import com.dreawer.appxauth.utils.JsonFormatUtil;
import com.dreawer.responsecode.rcdt.Error;
import com.dreawer.responsecode.rcdt.*;
import com.google.gson.Gson;
import com.google.gson.JsonPrimitive;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.dreawer.appxauth.utils.BaseUtils.getNow;

/**
 * <CODE>AuthController</CODE>
 * 基本授权控制器
 * 用于处理扫码授权后用户信息录入
 *
 * @author fenrir
 * @Date 18-7-3
 */
@Controller
@RequestMapping("/auth")
@Slf4j
public class AuthController extends BaseController {
    private Logger logger = Logger.getLogger(this.getClass()); // 日志记录器

    @Autowired
    private TokenManager tokenManager;

    @Autowired
    private ThirdParty thirdParty;

    /**
     * 跳转小程序授权页面
     *
     * @return
     * @throws IOException
     */
    @RequestMapping("/getAuthPage")
    public ModelAndView getAuthPage(@RequestParam("id") String id) throws IOException {
        ModelAndView mv = new ModelAndView();
        mv.addObject("authUrl", thirdParty.URL_AUTH_PAGE(id));
        mv.setViewName("authorize");
        return mv;
    }

    /**
     * 获取小程序component access token
     *
     * @return
     * @throws IOException
     */
    @RequestMapping("/getToken")
    @ResponseBody
    public String getToken() throws IOException {
        return tokenManager.getComponentAccessToken();
    }


    /**
     * 传递小程序登录密钥
     *
     * @return
     * @throws IOException
     */
    @PostMapping("/sns")
    @ResponseBody
    public ResponseCode getOpenId(@RequestBody @Valid WxLoginForm form, BindingResult result
    ) throws Exception {
        if (result.hasErrors()) {
            return ResponseCodeRepository.fetch(result.getFieldError().getDefaultMessage(), result.getFieldError().getField(), Error.ENTRY);
        }
        String encryptedData = form.getEncryptedData();
        String appid = form.getAppid();
        String code = form.getCode();
        String iv = form.getIv();
        String oid = form.getOid();
        String response = appManager.wxLogin(appid, code);
        JSONObject jsonObject = new JSONObject(response);
        String openId = (String) jsonObject.get("openid");
        String sessionKey = (String) jsonObject.get("session_key");
        log.info("请求登录:" + "appid:" + appid + "\r\n" + "code:" + code + "\r\n" + "oid" + oid);
        ApplicationUser applicationUser = appUserService.findByOpenIdAndApplicationId(openId, oid);
        log.info("查询结果:" + JsonFormatUtil.formatJson(applicationUser));
        Map<String, Object> responseParam = new HashMap<>();
        //如果该小程序用户在该企业未注册
        if (applicationUser == null) {

            //获取用户昵称和头像
            String data = decrypt(encryptedData, sessionKey, iv);
            JSONObject userObject = new JSONObject(data);
            if (!userObject.has("nickName")) {
                return MessageError.UNPACK;
            }

            String petName = getPetName(userObject.getString("nickName"));
            String mugshot = userObject.getString("avatarUrl");


            //在用户中心注册
            ResponseCode responseCode = serviceManager.signUp(petName, oid, mugshot);
            if (!responseCode.getCode().equals("000000")) {
                return Error.APPSERVER;
            }
            JSONObject JsonData = new JSONObject(responseCode.getData().toString());
            //获取id保存为应用用户主键
            String id = (String) JsonData.get("id");
            String token = (String) JsonData.get("token");
            applicationUser = new ApplicationUser();
            applicationUser.setAppid(appid);
            applicationUser.setOpenid(openId);
            applicationUser.setApplicationId(oid);
            applicationUser.setSessionKey(sessionKey);
            applicationUser.setCreateTime(getNow());
            applicationUser.setId(id);
            appUserService.save(applicationUser);
            responseParam.put("token", token);
            responseParam.put("openId", openId);
            return Success.SUCCESS(responseParam);
        } else {
            //调用用户中心获得令牌
            String id = applicationUser.getId();
            Map<String, Object> param = new HashMap<>();
            param.put("userId", id);
            ResponseCode authorization = serviceManager.getAuthorization(param);
            if (!authorization.getCode().equals("000000")) {
                return Error.APPSERVER;
            }
            JSONObject JsonData = new JSONObject(authorization.getData().toString());
            String token = (String) JsonData.get("token");

            responseParam.put("token", token);
            responseParam.put("openId", openId);
            return Success.SUCCESS(responseParam);
        }

    }


    /**
     * 获取微信小程序授权码和到期时间
     *
     * @param authorizationCode 授权码
     * @param expiresIn         到期时间
     * @param id                用户解决方案ID
     * @return
     */
    @GetMapping("/wxApp")
    @ResponseBody
    public ResponseCode WxAppAuth(@RequestParam("auth_code") String authorizationCode,
                                  @RequestParam("expires_in") String expiresIn,
                                  @RequestParam("id") String id) throws Exception {
        UserCase userCase = userCaseService.findById(id);
        if (userCase == null) {
            return Error.DB("解决方案不存在");
        }
        String userId = userCase.getCreaterId();
        AuthorizeInfo authorizeInfo = tokenManager.getAuthorizeInfo(authorizationCode);
        log.info("小程序授权成功!授权人信息:" + authorizeInfo.toString());
        //小程序appid
        String appid = authorizeInfo.getAuthorization_info().getAuthorizer_appid();

        //更新授权信息(令牌和刷新令牌)
        authService.updateAuthInfo(authorizationCode, expiresIn, userId, authorizeInfo, appid);

        //获取授权详情
        AuthorizeInfo appDetail = appManager.getAuthorizerInfo(appid);

        Authorizer_info authorizer_info = appDetail.getAuthorizer_info();

        //授权基本信息
        //小程序昵称
        String nick_name = authorizer_info.getNick_name();
        //公司名称
        String principal_name = authorizer_info.getPrincipal_name();
        //小程序logo
        String head_img = authorizer_info.getHead_img();
        //小程序简介
        String signature = authorizer_info.getSignature();


        //更新应用并创建应用组织
        Application application = appService.updateApplication(appid, principal_name);
        String applicationId = application.getId();
        //创建店铺
        ResponseCode responseCode = serviceManager.addStore(applicationId, appid, nick_name, principal_name, head_img, signature, userId);
        JsonPrimitive data = (JsonPrimitive) responseCode.getData();
        String storeId = data.getAsJsonObject().get("id").getAsString();

        //创建管理员
        serviceManager.initAccount(applicationId, "RETAIL", userCase.getClientContact());

        //授权流程判断,判断用户小程序是否具备部署条件
        List<ResultType> list = appManager.checkAuthorCondition(appid);

        //获取小程序类目
        String category = appManager.getCategory(appid);


        userCase.setLogo(head_img);
        userCase.setAppCategory(category);
        userCase.setAppName(nick_name);
        userCase.setAppId(appid);
        userCase.setStoreId(storeId);
        //更新用户授权结果和昵称头像,APPID
        userCase = userCaseService.updateAuditResult(userCase, list);
        return Success.SUCCESS(userCase);

    }


    /**
     * 获取微信头像昵称
     *
     * @param storeId
     * @return
     * @throws IOException
     */
    @GetMapping("/appInfo")
    @ResponseBody
    public ResponseCode getAppInfo(@RequestParam("storeId") String storeId) throws IOException {
        Application application = appService.findById(storeId);
        if (application == null) {
            return Error.DB("未找到该应用");
        }
        String appId = application.getAppId();
        AuthorizeInfo authorizerInfo = appManager.getAuthorizerInfo(appId);
        Authorizer_info authorizer_info = authorizerInfo.getAuthorizer_info();
        Map<String, Object> params = new HashMap<>();
        params.put("nickName", authorizer_info.getNick_name());
        params.put("logo", authorizer_info.getHead_img());
        return Success.SUCCESS(authorizer_info);
    }


    /**
     * 获取小程序类目信息
     *
     * @param storeId
     * @return
     * @throws IOException
     */
    @GetMapping("/appCategory")
    @ResponseBody
    public ResponseCode getAppCategory(@RequestParam("storeId") String storeId) throws IOException {
        Application application = appService.findById(storeId);
        if (application == null) {
            return Error.DB("未找到该应用");
        }
        String appId = application.getAppId();
        String category = appManager.getCategory(appId);
        CategoryList categoryList = new Gson().fromJson(category, CategoryList.class);
        return Success.SUCCESS(categoryList);
    }


}

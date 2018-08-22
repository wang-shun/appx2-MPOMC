package com.dreawer.appxauth.controller;

import com.dreawer.appxauth.consts.ThirdParty;
import com.dreawer.appxauth.domain.Application;
import com.dreawer.appxauth.domain.ApplicationUser;
import com.dreawer.appxauth.domain.AuthInfo;
import com.dreawer.appxauth.domain.UserCase;
import com.dreawer.appxauth.form.WxLoginForm;
import com.dreawer.appxauth.lang.AppType;
import com.dreawer.appxauth.lang.PublishStatus;
import com.dreawer.appxauth.lang.ResultType;
import com.dreawer.appxauth.manager.TokenManager;
import com.dreawer.appxauth.model.AuthorizeInfo;
import com.dreawer.appxauth.model.Authorizer_info;
import com.dreawer.responsecode.rcdt.Error;
import com.dreawer.responsecode.rcdt.*;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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



    /**
     * 跳转小程序授权页面
     *
     * @return
     * @throws IOException
     */
    @RequestMapping("/getAuthPage")
    public ModelAndView getAuthPage() throws IOException {
        ModelAndView mv = new ModelAndView();
        mv.addObject("authUrl", ThirdParty.URL_AUTH_PAGE());
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
        return TokenManager.getComponentAccessToken();
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

        ApplicationUser applicationUser = appUserService.findByAppidAndOrganizationId(appid, oid);
        //如果该小程序用户在该企业未注册
        if (applicationUser == null) {
            String response = appManager.wxLogin(appid, code);
            JSONObject jsonObject = new JSONObject(response);
            String openId = (String) jsonObject.get("openid");
            String sessionKey = (String) jsonObject.get("session_key");

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
            applicationUser = new ApplicationUser();
            applicationUser.setAppid(appid);
            applicationUser.setOpenid(openId);
            applicationUser.setApplicationId(oid);
            applicationUser.setSessionKey(sessionKey);
            applicationUser.setCreateTime(getNow());
            applicationUser.setId(id);
            appUserService.save(applicationUser);
            return Success.SUCCESS(id);
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
            return Success.SUCCESS(token);
        }

    }


    /**
     * 获取微信小程序授权码和到期时间
     *
     * @param authorizationCode 授权码
     * @param expiresIn         到期时间
     * @return
     */
    @GetMapping("/wxApp")
    @ResponseBody
    public ResponseCode WxAppAuth(@RequestParam("auth_code") String authorizationCode,
                                  @RequestParam("expires_in") String expiresIn) throws Exception {

        AuthorizeInfo authorizeInfo = TokenManager.getAuthorizeInfo(authorizationCode);
        log.info("小程序授权成功!授权人信息:" + authorizeInfo.toString());
        String appid = authorizeInfo.getAuthorization_info().getAuthorizer_appid();

        AuthInfo authInfo = authService.findByAppid(appid);
        if (authInfo == null) {
            authInfo = new AuthInfo();
            authInfo.setAuthorizationCode(authorizationCode);
            authInfo.setAccessToken(authorizeInfo.getAuthorization_info().getAuthorizer_access_token());
            authInfo.setRefreshToken(authorizeInfo.getAuthorization_info().getAuthorizer_refresh_token());
            authInfo.setAppid(appid);
            authInfo.setAppType(AppType.WXAPP);
            //获取有效期
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.SECOND, Integer.parseInt(expiresIn));

            authInfo.setExpireIn(calendar.getTime());
            authInfo.setCreaterId(null);
            authInfo.setCreateTime(getNow());
            authInfo.setUpdateTIme(getNow());
            authService.save(authInfo);

        } else {
            authInfo.setAccessToken(authorizeInfo.getAuthorization_info().getAuthorizer_access_token());
            authInfo.setRefreshToken(authorizeInfo.getAuthorization_info().getAuthorizer_refresh_token());
            authInfo.setAuthorizationCode(authorizationCode);
            //获取有效期
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.SECOND, Integer.parseInt(expiresIn));

            authInfo.setExpireIn(calendar.getTime());
            authInfo.setAppType(AppType.WXAPP);
            authInfo.setCreaterId(null);
            authInfo.setCreateTime(getNow());
            authInfo.setUpdateTIme(getNow());
            authService.update(authInfo);
        }


        //创建应用组织
        Map<String, Object> param = new HashMap<>();
        param.put("appId", appid);
        if (authorizeInfo.getAuthorizer_info().getNick_name().length() > 2) {
            param.put("name", authorizeInfo.getAuthorizer_info().getNick_name());
        }
        //获取组织ID

        String organizationId = serviceManager.addOrganzation(param);

        //创建应用
        Application application = new Application();
        application.setOrganizationId(organizationId);
        application.setAppId(appid);
        appService.save(application);

        //更新解决方案
        List<ResultType> list = appManager.checkAuthorCondition(appid);
        String category = appManager.getCategory(appid);

        UserCase userCase = userCaseService.findUserCaseByAppId(appid);
        if (userCase == null) {
            return Error.DB("解决方案不存在");
        }

        userCase.setLogo(authorizeInfo.getAuthorizer_info().getHead_img());
        userCase.setAppCategory(category);
        userCase.setAppName(authorizeInfo.getAuthorizer_info().getNick_name());

        //无失败原因则授权条件具备
        if (list.size() == 0) {
            userCaseService.updateUserCase(userCase);
            userCase.setPublishStatus(PublishStatus.AUTHORIZED);
            return Success.SUCCESS(userCase);
        }
        //如果个人用户购买ECS则通过授权
        if (list.size() == 1) {
            ResultType type = list.get(0);
            if (type.equals(ResultType.PRINCIPAL) && userCase.getDomain().equals("https://ecs.dreawer.com/")) ;
            userCaseService.updateUserCase(userCase);
            userCase.setPublishStatus(PublishStatus.AUTHORIZED);
            return Success.SUCCESS(userCase);
        }

        StringBuilder auditResult = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            ResultType type = list.get(i);
            if (type.equals(ResultType.PERMISSIONDENIED)) {
                auditResult.append(";用户未提供开发权限");
            }
            //如果非ECS小程序为个人主体
            if (type.equals(ResultType.PRINCIPAL) && !userCase.getDomain().equals("https://ecs.dreawer.com/")) {
                auditResult.append(";小程序主体需为企业");
            }
            if (type.equals(ResultType.NAME)) {
                auditResult.append(";小程序名称未填写");
            }
            if (type.equals(ResultType.CATEGORY)) {
                auditResult.append(";小程序类目未填写");
            }
        }
        String result = auditResult.substring(1, auditResult.length() - 1);
        userCase.setAuditResult(result);
        userCase.setPublishStatus(PublishStatus.MISSINGCONDITION);
        userCaseService.updateUserCase(userCase);
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
        String authorizerInfo = appManager.getAuthorizerInfo(appId);
        AuthorizeInfo authorizeInfo = new Gson().fromJson(authorizerInfo, AuthorizeInfo.class);
        Authorizer_info authorizer_info = authorizeInfo.getAuthorizer_info();
        Map<String, Object> params = new HashMap<>();
        params.put("nickName", authorizer_info.getNick_name());
        params.put("logo", authorizer_info.getHead_img());
        return Success.SUCCESS(authorizer_info);
    }


}

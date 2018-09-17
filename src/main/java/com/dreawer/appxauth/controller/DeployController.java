package com.dreawer.appxauth.controller;

import com.dreawer.appxauth.RibbonClient.form.ViewGoods;
import com.dreawer.appxauth.domain.UserCase;
import com.dreawer.appxauth.lang.CommitType;
import com.dreawer.appxauth.lang.PublishStatus;
import com.dreawer.appxauth.lang.ResultType;
import com.dreawer.responsecode.rcdt.Error;
import com.dreawer.responsecode.rcdt.ResponseCode;
import com.dreawer.responsecode.rcdt.Success;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * <CODE>DeployController</CODE>
 *
 * @author fenrir
 * @Date 18-7-11
 */

@Controller
@RequestMapping("/deploy")
@Slf4j
public class DeployController extends BaseController {


    /**
     * 提交小程序模板
     */
    @GetMapping("/commitCode")
    @ResponseBody
    protected ResponseCode commitCode(
            @RequestParam("templateId") String templateId,
            @RequestParam("appid") String appid,
            @RequestParam("storeId") String storeId) throws IOException {
        String response = appManager.commit(appid, templateId, storeId);
        //判断返回结果
        return Success.SUCCESS(response);
    }

    /**
     * 将第三方提交的代码包提交审核
     * @param id
     * @return 提交审核号
     */

    @GetMapping("/submit")
    @ResponseBody
    public ResponseCode submitAudit(HttpServletRequest req,
                                    @RequestParam("id") String id) throws JSONException, IOException {
        String userid = req.getHeader("userid");
        UserCase userCase = userCaseService.findById(id);
        if (userCase == null) {
            return Error.DB("未查询到解决方案");
        }

        PublishStatus publishStatus = userCase.getPublishStatus();
        if (publishStatus.equals(PublishStatus.AUTHORIZED)) {
            //权限判断
            List<ResultType> list = appManager.checkAuthorCondition(userCase.getAppId());
            if (list.contains(ResultType.PERMISSIONDENIED)) {
                userCase.setAuditResult("用户未提供开发权限");
                userCase.setPublishStatus(PublishStatus.SUBMITFAILED);
                userCaseService.updateUserCase(userCase);
                return Error.PERMISSION("提交失败");
            } else {
                StringBuilder auditResult = new StringBuilder();
                if (list.size() >= 1) {
                    for (ResultType resultType : list) {
                        if (resultType.equals(ResultType.CATEGORY)) {
                            auditResult.append(";小程序类目未填写");
                        }
                        if (resultType.equals(ResultType.NAME)) {
                            auditResult.append(";小程序名称未填写");
                        }
                    }
                    String result = auditResult.substring(1, auditResult.length());
                    userCase.setPublishStatus(PublishStatus.SUBMITFAILED);
                    userCase.setAuditResult(result);
                    userCaseService.updateUserCase(userCase);
                    return Error.PERMISSION("提交失败");
                }
            }
            ViewGoods viewGoods = serviceManager.goodDetail(userCase.getSpuId(), userid);
            String templetId = viewGoods.getViewApp().getTempletId();
            String appId = userCase.getAppId();
            String storeId = userCase.getStoreId();
            //提交代码
            commitCode(templetId, appId, storeId);
            String auditId = appManager.submitAudit(id);
            return Success.SUCCESS(auditId);
        } else {
            return Error.PERMISSION("审核条件不足");
        }


    }

    /**
     * 获取小程序最新一次提交的审核状态
     *
     * @param appid 小程序appid
     * @return
     * @throws IOException
     */
    @GetMapping("/auditResult")
    @ResponseBody
    public ResponseCode getLatestAuditstatus(@RequestParam("appid") String appid) throws IOException {
        String auditStatus = appManager.getLatestAuditStatus(appid);
        JSONObject jsonObject = new JSONObject(auditStatus);
        return Success.SUCCESS(jsonObject);
    }

    /**
     * 发布已通过审核的小程序
     *
     * @param appid 小程序appid
     * @return
     * @throws IOException
     */
    @GetMapping("/release")
    @ResponseBody
    public ResponseCode release(@RequestParam("appid") String appid) throws IOException {
        String auditStatus = appManager.release(appid);
        JSONObject jsonObject = new JSONObject(auditStatus);
        return Success.SUCCESS(jsonObject);
    }


    @GetMapping("/shortcut")
    @ResponseBody
    public String shortcut(@RequestParam("appId") String appid,
                           @RequestParam("tempId") String templateId,
                           @RequestParam("storeId") String storeId,
                           @RequestParam("type") CommitType commitType) throws IOException {
        String response;
        switch (commitType) {
            case PREVIEW:
                response = appManager.commit(appid, templateId, storeId);
                log.info("小程序上传结果:" + response);
                return appManager.getPreviewQR(appid);
            case SUBMIT:
                response = appManager.submitAudit(appid);
                log.info("小程序提交审核结果:" + response);
                return response;
            case BOTH:
                response = appManager.commit(appid, templateId, storeId);
                log.info("小程序上传结果:" + response);
                response = appManager.submitAudit(appid);
                log.info("小程序提交审核结果:" + response);
                return appManager.getPreviewQR(appid);
        }
        return null;
    }

}



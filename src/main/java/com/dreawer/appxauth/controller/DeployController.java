package com.dreawer.appxauth.controller;

import com.dreawer.appxauth.RibbonClient.form.ViewGoods;
import com.dreawer.appxauth.domain.UserCase;
import com.dreawer.appxauth.lang.PublishStatus;
import com.dreawer.responsecode.rcdt.Error;
import com.dreawer.responsecode.rcdt.ResponseCode;
import com.dreawer.responsecode.rcdt.Success;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * <CODE>DeployController</CODE>
 *
 * @author fenrir
 * @Date 18-7-11
 */

@RestController
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
            @RequestParam("storeId") String storeId,
            //@RequestParam(value = "appKey", required = false) String appKey,
            @RequestParam("domain") String domain) throws JSONException, IOException {

        String response = appManager.commit(appid, templateId, storeId, domain);
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
        if (publishStatus.equals(PublishStatus.AUDITFAILED) ||
                publishStatus.equals(PublishStatus.UNAUTHORIZED) ||
                publishStatus.equals(PublishStatus.MISSINGCONDITION) ||
                publishStatus.equals(PublishStatus.SUBMITFAILED)) {

            ViewGoods viewGoods = serviceManager.goodDetail(userCase.getSpuId(), userid);
            String templetId = viewGoods.getViewApp().getTempletId();
            String appId = userCase.getAppId();
            String storeId = userCase.getStoreId();
            String domain = userCase.getDomain();
            //提交代码
            commitCode(templetId, appId, storeId, domain);
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
    public ResponseCode release(@RequestParam("appid") String appid) throws IOException {
        String auditStatus = appManager.release(appid);
        JSONObject jsonObject = new JSONObject(auditStatus);
        return Success.SUCCESS(jsonObject);
    }
}



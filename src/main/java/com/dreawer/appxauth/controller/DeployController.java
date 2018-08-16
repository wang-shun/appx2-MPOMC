package com.dreawer.appxauth.controller;

import com.dreawer.responsecode.rcdt.ResponseCode;
import com.dreawer.responsecode.rcdt.Success;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    protected String commitCode(
            @RequestParam("templateId") String templateId,
            @RequestParam("appid") String appid,
            @RequestParam("storeId") String storeId,
            //@RequestParam(value = "appKey", required = false) String appKey,
            @RequestParam("domain") String domain) throws JSONException, IOException {

        String response = appManager.commit(appid, templateId, storeId, domain);
        //判断返回结果
        return response;
    }

    /**
     * 将第三方提交的代码包提交审核
     *
     * @param appid
     * @return 提交审核号
     */

    @GetMapping("/submit")
    public String submitAudit(HttpServletRequest req,
                              @RequestParam("appid") String appid,
                              @RequestParam(value = "tag", required = false) String tag) throws JSONException, IOException {
        String response = appManager.submitAudit(appid, tag);
        return response;
    }

    /**
     * 获取小程序最新一次提交的审核状态
     *
     * @param appid 小程序appid
     * @return
     * @throws IOException
     */
    @GetMapping("/auditResult")
    public ResponseCode getLatestAuditstatus(String appid) throws IOException {
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
    public ResponseCode release(String appid) throws IOException {
        String auditStatus = appManager.release(appid);
        JSONObject jsonObject = new JSONObject(auditStatus);
        return Success.SUCCESS(jsonObject);
    }
}



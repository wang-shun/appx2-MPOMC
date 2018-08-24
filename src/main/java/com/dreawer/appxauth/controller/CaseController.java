package com.dreawer.appxauth.controller;

import com.dreawer.appxauth.RibbonClient.form.ViewGoods;
import com.dreawer.appxauth.RibbonClient.form.ViewSku;
import com.dreawer.appxauth.domain.AppCase;
import com.dreawer.appxauth.domain.CaseCountForm;
import com.dreawer.appxauth.domain.UserCase;
import com.dreawer.appxauth.exception.ResponseCodeException;
import com.dreawer.appxauth.exception.WxAppException;
import com.dreawer.appxauth.form.CaseQueryForm;
import com.dreawer.appxauth.form.CreateUserCaseForm;
import com.dreawer.appxauth.form.UserCaseCountForm;
import com.dreawer.appxauth.lang.PublishStatus;
import com.dreawer.appxauth.lang.QueryType;
import com.dreawer.appxauth.lang.SaleMode;
import com.dreawer.responsecode.rcdt.Error;
import com.dreawer.responsecode.rcdt.ResponseCode;
import com.dreawer.responsecode.rcdt.ResponseCodeRepository;
import com.dreawer.responsecode.rcdt.Success;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.*;

import static com.dreawer.appxauth.consts.ControllerConstants.*;
import static com.dreawer.appxauth.consts.MessageConstants.ERR_OTHER;

/**
 * <CODE>CaseController</CODE>
 * 小程序方案控制器
 *
 * @author fenrir
 * @Date 17-10-25
 */

@Controller
@RequestMapping(REQ_CASE)
public class CaseController extends BaseController {

    private Logger logger = Logger.getLogger(this.getClass()); // 日志记录器



    /**
     * 请求展示小程序方案
     *
     * @return 查询结果
     */
    @RequestMapping(value = LIST, method = RequestMethod.GET)
    public @ResponseBody
    ResponseCode list() {
        try {
            List<AppCase> caseList = caseService.findAll();
            if (caseList == null) {
                return Error.DB("记录不存在");

            }
            return Success.SUCCESS(caseList);
        } catch (Exception e) {
            String log = ERR_OTHER;
            logger.error(log, e);
            // 返回失败标志及信息
            return Error.APPSERVER;
        }
    }

    /**
     * 请求展示小程序方案
     *
     * @return 查询结果
     */
    @GetMapping(REQ_BY_ID)
    public @ResponseBody
    ResponseCode findById(@RequestParam("id") String id) {
        try {
            UserCase userCase = userCaseService.findById(id);
            return Success.SUCCESS(userCase);
        } catch (Exception e) {
            String log = ERR_OTHER;
            logger.error(log, e);
            // 返回失败标志及信息
            return Error.APPSERVER;
        }
    }



    /**
     * 生成小程序订单
     *
     * @param form
     * @param result
     * @return
     * @throws WxAppException
     */
    @PostMapping(CREATE)
    public @ResponseBody
    ResponseCode createUserCase(HttpServletRequest req, @Valid CreateUserCaseForm form, BindingResult result) throws WxAppException, ResponseCodeException {
        String userid = req.getHeader("userid");
        if (result.hasErrors()) {
            return ResponseCodeRepository.fetch(result.getFieldError().getDefaultMessage(), result.getFieldError().getField(), Error.ENTRY);
        }
        String number = form.getNumber();
        String orderId = form.getOrderId();
        String skuId = form.getSkuId();
        String userId = form.getUserId();
        String spuId = form.getSpuId();

        String remark = null;
        //调用商品服务查询商品信息
        ViewSku sku = null;
        ViewGoods viewGoods = serviceManager.goodDetail(spuId, userId);
        List<ViewSku> skus = viewGoods.getSkus();
        for (ViewSku viewSku : skus) {
            String id = viewSku.getId();
            if (id.equals(skuId)) {
                sku = viewSku;
                remark = viewSku.getRemark();
                break;
            }
        }

        if (sku == null) {
            return Error.EXT_RESPONSE("未查询到该商品对应sku");
        }
        if (remark == null) {
            return Error.EXT_RESPONSE("未查询到该商品有效期");
        }

        String period = "0";
        JsonObject returnData = new JsonParser().parse(remark).getAsJsonObject();
        if (returnData.has("period")) {
            JsonElement jsonElement = returnData.get("period");
            period = jsonElement.getAsString();
        }
        Date createDate = getNow();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(createDate);
        //增加使用期限
        //calendar.add(Calendar.MONTH,Integer.parseInt(duration));
        calendar.add(Calendar.MONTH, Integer.parseInt(period));
        //增加15天
        calendar.add(Calendar.DATE, 15);
        Timestamp invalidTime = new Timestamp(calendar.getTimeInMillis());

        //封装用户小程序信息
        UserCase userCase = new UserCase();
        userCase.setSaleMode(SaleMode.DEFAULT);
        //获取后台url
        //设置类目名和ID
        userCase.setBackendUrl("https://appx.dreawer.com/management");
        userCase.setName(viewGoods.getName());
        userCase.setAppName(null);
        userCase.setDurationType(period + "月");
        userCase.setCategoryId(viewGoods.getGroups().get(0).getId());
        userCase.setClientName("");
        userCase.setClientContact("");
        userCase.setExpireDate(invalidTime);
        userCase.setOrderIds(orderId);
        userCase.setPublishStatus(PublishStatus.UNAUTHORIZED);
        userCase.setCreaterId(userid);
        userCase.setDomain("http://api.dreawer.com");
        userCase.setCreateTime(new Timestamp(System.currentTimeMillis()));
        userCaseService.addUserCase(userCase);
        return Success.SUCCESS(userCase);
    }

    /**
     * 分页查询解决方案列表
     *
     * @param form
     * @param result
     * @return 返回结果
     */
    @ApiOperation(value = "分页查询解决方案列表")
    @ApiImplicitParam(name = "status", value = "ONGING进行中,EXPIRED已失效,ALL全部")
    @PostMapping(value = "/query/{status}")
    public @ResponseBody
    ResponseCode query(@PathVariable("status") String status, @RequestBody @Valid CaseQueryForm form, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseCodeRepository.fetch(result.getFieldError().getDefaultMessage(), result.getFieldError().getField(), Error.ENTRY);
        }
        try {
            UserCase userCase = new UserCase();
            QueryType type = form.getType();
            if (type.equals(QueryType.BACKEND)) {
                //后台查询
                if (StringUtils.isBlank(form.getStoreId())) {
                    return Error.RECEIVE("请输入店铺ID");
                }

            } else if (type.equals(QueryType.CUSTOMER)) {
                userCase.setCreaterId(form.getUserId());
            } else {
                return Error.RECEIVE("用户身份无效");
            }
            Timestamp startTime = null;
            Timestamp endTime = null;
            if (form.getStartTime() != null) {
                startTime = new Timestamp(form.getStartTime());
            }
            if (form.getEndTime() != null) {
                endTime = new Timestamp(form.getEndTime());
            }
            int pageNo = 1;
            int pageSize = 5;
            if (form.getPageNo() != null && form.getPageNo() > 0) {
                pageNo = form.getPageNo();
            }
            if (form.getPageSize() != null && form.getPageSize() > 0) {
                pageSize = form.getPageSize();
            }
            int start = (pageNo - 1) * pageSize;

            String contact = null;
            if (form.getContact() != null) {
                contact = form.getContact();
                /*if (Pattern.matches("^1(3[0-9]|4[57]|5[0-35-9]|7[0135678]|8[0-9])\\d{8}$",contact)){
                }else if (Pattern.matches("^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$\n",contact)){
                }*/
            }
            userCase.setClientName(form.getCustomerName());
            userCase.setClientContact(form.getContact());
            userCase.setAppId(form.getAppid());
            userCase.setPublishStatus(form.getPublishStatus());
            userCase.setCategoryId(form.getCategoryId());
            userCase.setName(form.getName());
            userCase.setAppName(form.getAppName());

            List<UserCase> list = userCaseService.findAllUserCaseByCondition(userCase, contact, startTime, endTime, start, pageSize, status);

            for (UserCase node : list) {
                if (node.getPublishStatus().equals(PublishStatus.PENDING)) {
                    String appId = node.getAppId();
                    if (appId != null) {
                        String response = appManager.getLatestAuditStatus(appId);
                        JSONObject auditStatus = new JSONObject(response);
                        if (auditStatus.has("status")) {
                            if (auditStatus.getInt("status") == 0) {
                                node.setPublishStatus(PublishStatus.UNPUBLISHED);
                                node.setAuditResult("审核通过");
                                userCaseService.updateUserCase(node);
                            } else if (auditStatus.getInt("status") == 1) {
                                node.setPublishStatus(PublishStatus.AUDITFAILED);
                                String reason = auditStatus.getString("reason");
                                node.setAuditResult(reason);
                                userCaseService.updateUserCase(node);
                            }
                        } else {
                            //如果客户取消授权或其他微信问题
                            String errmsg = auditStatus.getString("errmsg");
                            node.setAuditResult(errmsg);
                            node.setPublishStatus(PublishStatus.AUDITFAILED);
                            userCaseService.updateUserCase(node);
                        }
                    }
                }
            }

            Map<String, Object> pageParam = new HashMap<>();
            int totalSize = userCaseService.getUserCaseByConditionCount(userCase, contact, startTime, endTime, status);
            int totalPage = totalSize % pageSize == 0 ? totalSize / pageSize : (totalSize / pageSize + 1);
            pageParam.put("totalSize", totalSize);
            pageParam.put("totalPage", totalPage);
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("userCases", list);
            resultMap.put("pageParam", pageParam);
            return Success.SUCCESS(resultMap);

        } catch (Exception e) {
            logger.error(e);
            // 返回失败标志及信息
            return Error.APPSERVER;
        }

    }


    /**
     * 分页查询客户解决方案数量
     *
     * @param form
     * @param result
     * @return 返回结果
     */
    @ApiOperation(value = "分页查询解决方案列表")
    @RequestMapping(value = COUNT_BY_ID, method = RequestMethod.POST)
    public @ResponseBody
    ResponseCode queryByUserId(@RequestBody @Valid UserCaseCountForm form, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseCodeRepository.fetch(result.getFieldError().getDefaultMessage(), result.getFieldError().getField(), Error.ENTRY);
        }
        try {
            List<String> userIds = form.getUserIds();
            Calendar calendar = Calendar.getInstance();
            //获取10天后后的时间
            calendar.add(Calendar.DATE, 10);
            //获取20天后后的时间
            Timestamp redAlert = new Timestamp(calendar.getTimeInMillis());
            calendar.add(Calendar.DATE, 10);
            Timestamp yellowAlert = new Timestamp(calendar.getTimeInMillis());
            List<CaseCountForm> list = userCaseService.getUserCaseByIdCount(userIds, yellowAlert, redAlert);
            return Success.SUCCESS(list);
        } catch (Exception e) {
            logger.error(e);
            // 返回失败标志及信息
            return Error.APPSERVER;
        }
    }


    /**
     * 查询指定时间内到期的解决方案
     *
     * @param form
     * @param result
     * @return 返回结果
     */
    @ApiOperation(value = "查询指定时间内到期的解决方案")
    @RequestMapping(value = QUERY_EXPIRE, method = RequestMethod.POST)
    public @ResponseBody
    ResponseCode queryExpire(@RequestBody @Valid CaseQueryForm form, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseCodeRepository.fetch(result.getFieldError().getDefaultMessage(), result.getFieldError().getField(), Error.ENTRY);
        }
        try {
            Timestamp startTime = new Timestamp(form.getStartTime());
            Timestamp endTime = new Timestamp(form.getEndTime());
            List<UserCase> list = userCaseService.findAllByExpireTime(startTime, endTime);
            return Success.SUCCESS(list);
        } catch (Exception e) {
            logger.error(e);
            // 返回失败标志及信息
            return Error.APPSERVER;
        }
    }
}


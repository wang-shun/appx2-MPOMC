package com.dreawer.appxauth.controller;

import com.dreawer.appxauth.domain.AppCase;
import com.dreawer.appxauth.domain.UserCase;
import com.dreawer.appxauth.exception.WxAppException;
import com.dreawer.appxauth.form.CaseQueryForm;
import com.dreawer.appxauth.form.CreateUserCaseForm;
import com.dreawer.appxauth.lang.PublishStatus;
import com.dreawer.appxauth.lang.QueryType;
import com.dreawer.appxauth.lang.SaleMode;
import com.dreawer.appxauth.manager.AppManager;
import com.dreawer.appxauth.service.CaseService;
import com.dreawer.appxauth.service.UserCaseService;
import com.dreawer.responsecode.rcdt.Error;
import com.dreawer.responsecode.rcdt.ResponseCode;
import com.dreawer.responsecode.rcdt.ResponseCodeRepository;
import com.dreawer.responsecode.rcdt.Success;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private CaseService caseService; //方案信息服务

    @Autowired
    private AppManager appManager;

    @Autowired
    private UserCaseService userCaseService;

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
     * 生成小程序订单
     *
     * @param form
     * @param result
     * @return
     * @throws WxAppException
     */
    @PostMapping(CREATE)
    public @ResponseBody
    ResponseCode createUserCase(@Valid CreateUserCaseForm form, BindingResult result) throws WxAppException {

        if (result.hasErrors()) {
            return ResponseCodeRepository.fetch(result.getFieldError().getDefaultMessage(), result.getFieldError().getField(), Error.ENTRY);
        }
        String number = form.getNumber();
        String orderId = form.getOrderId();
        String skuId = form.getSkuId();
        String userId = form.getUserId();


        //获得商品Id
        //String merchandiseId = Okhttp.getSync(skuId);
//         AppCaseTheme theme = caseThemeService.findThemeBySpuId(merchandiseId);
//        if (theme==null){
//            throw new WxAppException(MSG_THEME_NOTFOUND);
//        }
//        AppCase appCase = caseService.findById(theme.getCaseId());
//        if (appCase==null) {
//            throw new WxAppException(MSG_CASE_NOTFOUND);
//        }


        //获得商品属性

//        JSONResponse merchandiseDetail = merchandiseController.getMerchandiseDetail(null, null, skuId);
//        if (!merchandiseDetail.isStatus()){
//            logger.error(merchandiseDetail.getErrors().get(0).getMessage());
//            throw new WxAppException(merchandiseDetail.getErrors().get(0).getMessage());
//        }
//        ViewMerchandiseDetail detail = (ViewMerchandiseDetail) merchandiseDetail.getData();
//        List<MerchandiseSku> skus = detail.getSkus();
//        MerchandiseSku sku = null;
//        //主题描述
//        String themeDesc = null;
//        //查询商品信息获得商品使用期限
//        String duration = null;
//        for (MerchandiseSku node : skus) {
//            if (node.getId().equals(skuId)){
//                sku = node;
//                List<ViewProperty> properties = sku.getProperties();
//                for (ViewProperty property : properties) {
//                    if (property.getName().equals("使用期限")){
//                        List<ViewPropertyValue> values = property.getPropertyValues();
//                        duration = values.get(0).getName();
//                    }
//                    if (property.getName().equals("主题色")){
//                        List<ViewPropertyValue> values = property.getPropertyValues();
//                        themeDesc = values.get(0).getName();
//                        String[] split = themeDesc.split(";");
//                        themeDesc = split[0];
//                    }
//                }
//            }
//        }
//        if (duration==null){
//            logger.error("未查询到有效期限信息");
//            throw new WxAppException("未查询到有效期限信息");
//        }
        //Date createDate = order.getCreateTime();
        Date createDate = getNow();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(createDate);
        //增加使用期限
        //calendar.add(Calendar.MONTH,Integer.parseInt(duration));
        calendar.add(Calendar.MONTH, Integer.parseInt("15"));
        //增加15天
        calendar.add(Calendar.DATE, 15);
        Timestamp invalidTime = new Timestamp(calendar.getTimeInMillis());

        //封装用户小程序信息
        UserCase userCase = new UserCase();
        userCase.setSaleMode(SaleMode.DEFAULT);
        //获取后台url
        userCase.setBackendUrl("1111");
        userCase.setName("测试用户" + Math.random() * 10);
        userCase.setAppName(null);
        userCase.setThemeName("测试主题" + Math.random() * 10);
        userCase.setDurationType("1" + "个月");
        userCase.setThemeId(UUID.randomUUID().toString().replace("-", ""));
        userCase.setClientName("测试用户" + Math.random() * 10);
        userCase.setClientContact("测试地址");
        userCase.setExpireDate(invalidTime);
        if (!StringUtils.isEmpty(number)) {
            userCase.setDelegatePhone(number);
        }
        userCase.setOrderIds(orderId);
        userCase.setPublishStatus(PublishStatus.UNAUTHORIZED);
        userCase.setCreaterId(UUID.randomUUID().toString().replace("-", ""));
        userCase.setDomain("http://retail.dreawer.com");
        userCase.setCreateTime(new Timestamp(System.currentTimeMillis()));
        userCaseService.addUserCase(userCase);
        return Success.SUCCESS(userCase);
    }

    /**
     * 分页查询行业方案列表
     *
     * @param form
     * @param result
     * @return 返回结果
     */
    @RequestMapping(value = REQ_QUERY, method = RequestMethod.POST)
    public @ResponseBody
    ResponseCode query(@RequestBody @Valid CaseQueryForm form, BindingResult result) {
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
            userCase.setThemeId(form.getThemeId());
            userCase.setName(form.getName());
            userCase.setAppName(form.getAppName());

            List<UserCase> list = userCaseService.findAllUserCaseByCondition(userCase, contact, startTime, endTime, start, pageSize);

            for (UserCase node : list) {
                if (node.getPublishStatus().equals(PublishStatus.PENDING)) {
                    String appId = node.getAppId();
                    if (appId != null) {
                        String response = appManager.getLatestAuditStatus(appId);
                        JSONObject auditStatus = new JSONObject(response);
                        if (auditStatus == null) {
                            continue;
                        }
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
            int totalSize = userCaseService.getUserCaseByConditionCount(userCase, contact, startTime, endTime);
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


}


package com.dreawer.appxauth.aspect;

import com.dreawer.appxauth.exception.ResponseCodeException;
import com.dreawer.appxauth.exception.WxAppException;
import com.dreawer.appxauth.utils.PropertiesUtils;
import com.dreawer.responsecode.rcdt.Error;
import com.dreawer.responsecode.rcdt.ResponseCode;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <CODE>GeneralConditionHandler</CODE>
 * 统一授权处理器
 * 用于判断小程序部署条件是否具备
 *
 * @author fenrir
 * @Date 18-7-3
 */

@Aspect
@Component
@Slf4j
public class GeneralConditionHandler {

    @Autowired
    private PropertiesUtils propertiesUtils;

    private static Logger logger = LoggerFactory.getLogger(GeneralConditionHandler.class);

    /**
     * 处理微信API切面
     */
    @Pointcut("execution(java.lang.String com.dreawer.appxauth.utils.Okhttp.*(..))")
    public void errcode() {
    }

    /**
     * 控制器切面
     */
    @Pointcut("execution(com.dreawer.responsecode.rcdt.ResponseCode com.dreawer.appxauth.controller.*.*(..))")
    public void response() {
    }

    /**
     * 其他服务调用切面
     */
    @Pointcut("execution(com.dreawer.responsecode.rcdt.ResponseCode com.dreawer.appxauth.utils.CallRequest.*(..))")
    public void eurekaResponse() {
    }

    /**
     * 在调用微信接口前后判断是否返回错误码,记录并抛出异常.
     *
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("errcode()")
    public Object CheckCode(ProceedingJoinPoint pjp) throws Throwable {
        log.info("外部调用开始");
        Object[] args = pjp.getArgs();
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            log.info("外部请求传递参数" + (i + 1) + ":" + new Gson().toJson(arg));
        }
        String proceed = (String) pjp.proceed();
        log.info("请求结果:" + proceed);
        JSONObject jsonObject = new JSONObject(proceed);
        log.info("外部调用结束");
        String message = null;
        String errcode = null;
        //如果微信返回报错信息
        if (jsonObject.has("errcode")) {
            errcode = jsonObject.get("errcode") + "";
            if (!errcode.equals("0")) {
            //遍历返回码获得错误信息文本
            message = propertiesUtils.getProperties(errcode);
            log.info("微信错误信息:" + message);
            //其他错误信息
            if (StringUtils.isEmpty(message)) {
                message = (String) jsonObject.get("errmsg");
            }

            //抛出微信错误码异常
            throw new WxAppException(errcode, message);
            }
        }

        return proceed;
    }

    /**
     * 统一处理微信返回错误信息并封装返回相应码
     *
     * @param pjp
     * @return
     */
    @Around("response()")
    public Object handlerException(ProceedingJoinPoint pjp) {

        ResponseCode result;

        try {
            result = (ResponseCode) pjp.proceed();

        } catch (Throwable e) {
            if (e instanceof WxAppException) {
                log.info("微信错误码异常:" + new Gson().toJson(e));
                result = Error.EXT_REQUEST(((WxAppException) e).getErrMsg());
            } else if (e instanceof ResponseCodeException) {
                log.info("服务调用异常:" + new Gson().toJson(((ResponseCodeException) e).getResponseCode()));
                result = ((ResponseCodeException) e).getResponseCode();
            } else if (e instanceof Exception) {
                log.info("异常", e);
                result = Error.APPSERVER;
            } else {
                log.info("异常", e);
                result = Error.APPSERVER;
            }

        }
        return result;
    }

    /**
     * 处理其他服务调用请求,如果结果不为成功则抛出异常
     *
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("eurekaResponse()")
    public Object handleEurekaException(ProceedingJoinPoint pjp) throws Throwable {
        ResponseCode result;
        log.info("Eureka服务调用开始");
        Object[] args = pjp.getArgs();
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            log.info("Eureka服务请求传递参数" + i + 1 + ":" + new Gson().toJson(arg));
        }
        result = (ResponseCode) pjp.proceed();
        log.info("请求结果:" + new Gson().toJson(result));
        log.info("Eureka服务调用结束");
        if (!result.getCode().equals("000000")) {
            throw new ResponseCodeException(result);
        }
        return result;


    }
}



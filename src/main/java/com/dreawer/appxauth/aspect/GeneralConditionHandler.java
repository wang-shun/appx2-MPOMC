package com.dreawer.appxauth.aspect;

import com.dreawer.appxauth.exception.WxAppException;
import com.dreawer.responsecode.rcdt.Error;
import com.dreawer.responsecode.rcdt.ResponseCode;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

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
public class GeneralConditionHandler {

    private static Logger logger = LoggerFactory.getLogger(GeneralConditionHandler.class);


    @Pointcut("execution(* com.dreawer.appxauth.manager.AppManager.*(..))")
    public void errcode() {
    }

    @Pointcut("execution(com.dreawer.responsecode.rcdt.ResponseCode com.dreawer.appxauth.controller.*.*(..))")
    public void response() {
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
        String proceed = (String) pjp.proceed();
        JSONObject jsonObject = new JSONObject(proceed);

        String message = null;
        String errcode = null;


        //如果微信返回报错信息
        if (jsonObject.has("errcode")) {
            errcode = (String) jsonObject.get("errcode");
            //遍历返回码获得错误信息文本
            Properties pro = new Properties();
            InputStream inStr = ClassLoader.getSystemResourceAsStream("returnCode.properties");
            pro.load(inStr);
            Enumeration<?> propertyNames = pro.propertyNames();
            //遍历所有的key
            while (propertyNames.hasMoreElements()) {
                String propertyName = (String) propertyNames.nextElement();
                if (StringUtils.equals(propertyName, errcode)) {
                    message = pro.getProperty(propertyName);
                    break;
                }
            }
            //其他错误信息
            if (message == null) {
                message = (String) jsonObject.get("errmsg");
            }

            //抛出微信错误码异常
            throw new WxAppException(errcode, message);

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
            logger.error("error on", e);
            if (e instanceof WxAppException) {
                result = Error.EXT_REQUEST(((WxAppException) e).getErrMsg());
            } else if (e instanceof Exception) {
                result = Error.APPSERVER;
            } else {
                result = Error.APPSERVER;
            }

        }
        return result;
    }
}



package com.dreawer.appxauth.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * <CODE>HttpLogAspect</CODE>
 *
 * @author fenrir
 * @Date 18-7-9
 */

@Aspect
@Component
@Slf4j
public class HttpLogAspect {

//     @Pointcut("execution(* com.dreawer.appxauth.utils.*.*(..))")
//   // @Pointcut("execution(* okhttp3.ResponseBody.string())")
//    public void afterExecute(){}
//
//    @AfterReturning(returning = "ret", pointcut = "execution(* com.dreawer.appxauth.utils.Okhttp.*(..))")
//    public void doAfterReturning(Object ret){
//        // 处理完请求，返回内容
//        logger.info(ret+"+===");
//        log.debug("========================\r\n"+"http response : " + ret+"\r\n========================");
//    }
}

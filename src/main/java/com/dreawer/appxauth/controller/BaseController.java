package com.dreawer.appxauth.controller;


import com.dreawer.appxauth.manager.AppManager;
import com.dreawer.appxauth.manager.ServiceManager;
import com.dreawer.appxauth.service.*;
import com.dreawer.appxauth.utils.CallRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.Arrays;
import org.codehaus.xfire.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.AlgorithmParameters;
import java.security.Security;

/**
 * <code>BaseController</code> 它是本系统中所有控制器的基类，提供控制器通用方法的实现。
 *
 * @author David Dai
 * @version 1.0
 * @since Dreawer 1.0
 */

@Controller
public class BaseController {

    @Autowired
    protected UserCaseService userCaseService;

    @Autowired
    protected AuthService authService;

    @Autowired
    protected AppManager appManager;

    @Autowired
    protected ServiceManager serviceManager;

    @Autowired
    protected CallRequest callRequest;

    @Autowired
    protected AppUserService appUserService;

    @Autowired
    protected AppService appService;

    @Autowired
    protected CaseService caseService; //方案信息服务


    protected Logger logger = Logger.getLogger(this.getClass()); // 日志记录器


    /**
     * 解密微信加密的数据。
     *
     * @param data           加密数据。
     * @param key            密钥。
     * @param iv             偏移量。
     * @return 解密后得数据。
     * @throws Exception 异常信息。
     */
    protected String decrypt(String data, String key, String iv) {
        // 被加密的数据
        byte[] dataByte = Base64.decode(data);
        // 加密秘钥
        byte[] keyByte = Base64.decode(key);
        // 偏移量
        byte[] ivByte = Base64.decode(iv);
        try {
            // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            // 初始化
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, "UTF-8");
                logger.debug(result);
                return result;
            }
        } catch (Exception e) {
            logger.error("error", e);
        }
        return null;
    }


    /**
     * 获取用户昵称。
     *
     * @param petName 昵称字符串。
     * @return 若昵称为空，返回默认昵称，否则返回原昵称。
     */
    protected String getPetName(String petName) {
        String reg = "[^0-9a-zA-Z\u4e00-\u9fa5]+";
        petName = petName.replaceAll(reg, "");
        if (StringUtils.isBlank(petName)) {
            petName = "极乐用户";
        }
        return petName;
    }
}



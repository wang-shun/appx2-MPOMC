package com.dreawer.appxauth.controller;


import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.AlgorithmParameters;
import java.sql.Timestamp;

import static com.dreawer.appxauth.consts.MessageConstants.ERR_OTHER;

/**
 * <code>BaseController</code> 它是本系统中所有控制器的基类，提供控制器通用方法的实现。
 *
 * @author David Dai
 * @version 1.0
 * @since Dreawer 1.0
 */

@Controller
public class BaseController {

    protected Logger logger = Logger.getLogger(this.getClass()); // 日志记录器


    /**
     * 获取当前系统时间。
     *
     * @return 当前系统时间。
     * @author David Dai
     * @since 2.0
     */
    protected Timestamp getNow() {
        return new Timestamp(System.currentTimeMillis());
    }


    /**
     * 解密微信加密的数据。
     *
     * @param data           加密数据。
     * @param key            密钥。
     * @param iv             偏移量。
     * @param encodingFormat 编码格式。
     * @return 解密后得数据。
     * @throws Exception 异常信息。
     */
    protected String decrypt(String data, String key, String iv, String encodingFormat) {
        try {
            //被加密的数据
            byte[] dataByte = Base64.decodeBase64(data);
            //加密秘钥
            byte[] keyByte = Base64.decodeBase64(key);
            //偏移量
            byte[] ivByte = Base64.decodeBase64(iv);

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, encodingFormat);
                return result;
            }
        } catch (Exception e) {
            String log = ERR_OTHER;
            logger.error(log, e);
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



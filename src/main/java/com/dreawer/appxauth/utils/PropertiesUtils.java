package com.dreawer.appxauth.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

/**
 * <CODE>PropertiesUtils</CODE>
 *
 * @author fenrir
 * @Date 18-8-24
 */
@Component
@PropertySource("classpath:returnCode.properties")
public class PropertiesUtils {

    @Autowired
    Environment env;


    public String getProperties(String key) throws UnsupportedEncodingException {
        return env.getProperty(key) == null ? "" : new String(env.getProperty(key).getBytes("ISO-8859-1"), "UTF-8");
    }
}

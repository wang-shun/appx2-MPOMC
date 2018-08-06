package com.dreawer.appxauth.exception;

/**
 * <CODE>WxAppException</CODE>
 * 微信小程序自定义异常类
 *
 * @author fenrir
 * @Date 17-11-22
 */
public class WxAppException extends Exception {

    private static final long serialVersionUID = -665549282813032095L;

    private String errCode;
    private String errMsg;
    private String message;

    public WxAppException() {
    }

    public WxAppException(String errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public WxAppException(String errMsg) {
        this.errMsg = errMsg;
    }


    public WxAppException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public WxAppException(String message, Throwable cause) {
        super(message, cause);
    }

    public WxAppException(Throwable cause) {
        super(cause);
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return WxAppException.class.getCanonicalName() + message;
    }
}

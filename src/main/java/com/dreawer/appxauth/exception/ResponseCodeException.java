package com.dreawer.appxauth.exception;


import com.dreawer.responsecode.rcdt.ResponseCode;

public class ResponseCodeException extends Exception {
    private ResponseCode responseCode;

    protected ResponseCodeException() {
    }

    protected ResponseCodeException(String message) {
        super(message);
    }


    protected ResponseCodeException(Throwable cause) {
        super(cause);
    }

    protected ResponseCodeException(String message, Throwable cause) {
        super(message, cause);
    }

    protected ResponseCodeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ResponseCodeException(ResponseCode responseCode) {
        this.setResponseCode(responseCode);
    }

    public ResponseCodeException(ResponseCode responseCode, String message) {
        super(message);
        this.setResponseCode(responseCode);
    }

    public ResponseCodeException(String message, ResponseCode responseCode) {
        super(message);
        this.setResponseCode(responseCode);
    }

    public ResponseCodeException(ResponseCode responseCode, Throwable cause) {
        super(cause);
        this.setResponseCode(responseCode);
    }

    public ResponseCodeException(ResponseCode responseCode, String message, Throwable cause) {
        super(message, cause);
        this.setResponseCode(responseCode);
    }

    protected ResponseCodeException(ResponseCode responseCode, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.setResponseCode(responseCode);
    }

    public ResponseCode getResponseCode() {
        return this.responseCode;
    }

    public void setResponseCode(ResponseCode responseCode) {
        this.responseCode = responseCode;
    }
}

package com.android.basiclib.net.exception;

public class ApiException extends Exception{

    public static final int ERROR_NET_SERVICE_NOT_FOUNT = -1;
    public static final int ERROR_NET_SERVICE_404 = -2;

    private int code;
    private String message;

    public ApiException(int code, String message) {
        this.code = code;
        this.message = message;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

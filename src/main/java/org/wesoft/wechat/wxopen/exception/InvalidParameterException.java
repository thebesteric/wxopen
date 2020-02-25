package org.wesoft.wechat.wxopen.exception;

public class InvalidParameterException extends Exception {

    public InvalidParameterException() {
        super("InvalidParameterException");
    }

    public InvalidParameterException(String message) {
        super(message);
    }

}

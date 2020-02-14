package org.wesoft.wechat.wxopen.exception;

public class NullParameterException extends Exception {

    public NullParameterException() {
        super("NullParameterException");
    }

    public NullParameterException(String message) {
        super(message);
    }

}

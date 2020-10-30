package com.fate.core.exception;

/**
 * @ProjectName: fate-core
 * @Package: com.fate.core.exception
 * @ClassName: BaseException
 * @Author: LJP
 * @Description: 基类异常
 * @Date: 2020/10/29 10:21
 * @Version: 1.0
 */
public class BaseException extends RuntimeException{

    public BaseException() {
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

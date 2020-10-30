package com.fate.core.utils;
import com.fate.core.exception.BaseException;

/**
 * @ProjectName: has-pss-cw-local
 * @Package: cn.hsa.pss.cw.utils
 * @ClassName: GetExceptionUtil
 * @Author: LJP
 * @Description: 异常工具类
 * @Date: 2020/7/12 10:05
 * @Version: 1.0
 */
public class ExceptionUtil {

    public static BaseException getBaseException(Throwable throwable){
        if (throwable instanceof BaseException){
            return (BaseException)throwable;
        }else {
            Throwable cause = throwable.getCause();
            if (cause == null){
                return null;
            }
            return getBaseException(cause);
        }
    }
}

package com.fate.core.framework.web.exception;

import lombok.extern.slf4j.Slf4j;

/**
 * @ProjectName: fate-core
 * @Package: com.fate.core.framework.web.exception
 * @ClassName: FateUncaughtExceptionHandler
 * @Author: LJP
 * @Description:
 * @Date: 2020/10/30 20:24
 * @Version: 1.0
 */
@Slf4j
public class FateUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        log.error("捕获到异常 : 线程名[{}], 异常名[{}]",t.getName(),e.getMessage(),e);
    }
}

package com.fate.core.framework.web.exception;

import com.fate.core.exception.BaseException;
import com.fate.core.response.ResultResponse;
import com.fate.core.utils.ExceptionUtil;
import com.fate.core.utils.ModelAndViewUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: fate-core
 * @Package: com.fate.core.framework.web.exception
 * @ClassName: FateHandlerExceptionResolver
 * @Author: LJP
 * @Description: 全局异常处理
 * @Date: 2020/10/30 16:50
 * @Version: 1.0
 */
@Slf4j
@Order(0)
public class FateHandlerExceptionResolver implements HandlerExceptionResolver {

    private String defaultErrorMessage="系统出现非业务类型异常，异常为：{}";

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        BaseException baseException = ExceptionUtil.getBaseException(e);
        String message;
        if (baseException == null){
            message = e.getMessage();
            log.info(defaultErrorMessage,message);
        } else {
            message = baseException.getMessage();
        }
        ResultResponse.ResponseType typeError = ResultResponse.ResponseType.TYPE_ERROR;
        return ModelAndViewUtil.setModelAndView(typeError.getDefaultCode(), typeError.getType(), message, null);
    }

    public void setDefaultErrorMessage(String defaultErrorMessage) {
        this.defaultErrorMessage = defaultErrorMessage;
    }
}

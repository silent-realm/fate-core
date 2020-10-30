package com.fate.core.framework.web;

import com.fate.core.annotation.FateRequestMapping;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Predicate;

/**
 * @ProjectName: fate-core
 * @Package: com.fate.core.framework.web
 * @ClassName: FateRequestMapperHandler
 * @Author: LJP
 * @Description:
 * @Date: 2020/4/1 23:38
 * @Version: 1.0
 */
@Slf4j
@Data
public class FateRequestMapperHandlerMapping extends RequestMappingHandlerMapping {

    private int order = 5;


    @Override
    protected boolean isHandler(Class<?> beanType) {
        return AnnotatedElementUtils.hasAnnotation(beanType, FateRequestMapping.class);
    }

    @Override
    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
        FateRequestMapping mfrm = method.getAnnotation(FateRequestMapping.class);
        FateRequestMapping cfrm = handlerType.getAnnotation(FateRequestMapping.class);
        if (mfrm == null){
            return null;
        }
        String cfrmPath = cfrm.value();
        String mfrmPath = mfrm.value();
        RequestMethod[] requestMethods = mfrm.method();
        String path = null;
        if (cfrmPath.equals("/") && mfrmPath.startsWith("/")) {
            path = mfrmPath;
        } else {
            path = cfrmPath + mfrmPath;
        }
        logger.info("加载了mapping= "+path+"method= "+method);
        RequestMappingInfo.Builder builder = RequestMappingInfo.paths(path);
        if (requestMethods != null && requestMethods.length>0){
            builder.methods(requestMethods);
        }else {
            builder.methods(RequestMethod.POST,RequestMethod.GET);
        }
        return builder.build();
    }
}

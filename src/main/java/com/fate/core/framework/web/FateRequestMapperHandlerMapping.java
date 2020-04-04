package com.fate.core.framework.web;

import com.fate.core.annotation.FateRequestMapping;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;

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
        // 检查class 存不存在 FateRequestMapping 这个注解
        FateRequestMapping hp_clz = AnnotatedElementUtils.findMergedAnnotation(handlerType, FateRequestMapping.class);
        // 检查method 存不存在 FateRequestMapping 这个注解
        FateRequestMapping hp_method = AnnotatedElementUtils.findMergedAnnotation(method, FateRequestMapping.class);
        if (hp_clz != null && hp_method != null) {
            String clz_path = hp_clz.value() == null ? "" : hp_clz.value().trim();
            String method_path = hp_method.value() == null ? "" : hp_method.value().trim();
            RequestMethod[] rm = hp_method.method();
            String url;
            if (clz_path.equals("/") && method_path.startsWith("/")) {
                url = method_path;
            } else {
                url = clz_path + method_path;
            }

            logger.info("registerHandler,url=" + url + ";method=" + method);
            RequestMappingInfo.Builder builder = RequestMappingInfo.paths(new String[]{url});
            if (rm != null && rm.length > 0) {
                builder.methods(rm);
            } else {
                builder.methods(new RequestMethod[]{RequestMethod.GET, RequestMethod.POST});
            }

            return builder.build();
        } else {
            return null;
        }
    }

}

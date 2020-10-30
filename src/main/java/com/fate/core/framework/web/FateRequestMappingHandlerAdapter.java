package com.fate.core.framework.web;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fate.core.annotation.FateRequestMapping;
import com.fate.core.exception.BaseException;
import com.fate.core.response.ResultResponse;
import com.fate.core.utils.ModelAndViewUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.MethodParameter;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.validation.DataBinder;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
/**
 * @ProjectName: fate-core
 * @Package: com.fate.core.framework.web
 * @ClassName: FateRequestMappingHandlerAdapter
 * @Author: LJP
 * @Description: 处理程序适配器
 * @Date: 2020/4/2 22:53
 * @Version: 1.0
 */
@Slf4j
@Data
public class FateRequestMappingHandlerAdapter extends RequestMappingHandlerAdapter {

    /**
     * 请求方式
     */
    enum methodType {
        // post请求
        POST,
        // get请求
        GET
    }

    private ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();

    @Override
    public void setParameterNameDiscoverer(ParameterNameDiscoverer parameterNameDiscoverer) {
        super.setParameterNameDiscoverer(parameterNameDiscoverer);
        this.parameterNameDiscoverer = parameterNameDiscoverer;
    }

    @Override
    protected boolean supportsInternal(HandlerMethod handlerMethod) {
        return handlerMethod.getMethodAnnotation(FateRequestMapping.class) != null;
    }

    @Override
    protected ModelAndView handleInternal(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod) throws Exception {
        this.checkRequest(request);
        String httpMethod = request.getMethod();
        Object bean = handlerMethod.getBean();
        ModelAndView mav = new ModelAndView();
        Method method = handlerMethod.getMethod();
        MethodParameter[] methodParameters = handlerMethod.getMethodParameters();

        if (methodType.POST.name().equalsIgnoreCase(httpMethod)) {
            if (methodParameters != null && methodParameters.length > 1) {
                ModelAndViewUtil.setModelAndView(-1, "error",
                        "post请求只允许有一个参数，且参数需要是DTO", null);
            }
            String jsonStr = getJSONStringFromRequest(request);
            log.info("==========请求入参为：{}", jsonStr);
            JSONObject jsonObject = JSONObject.parseObject(jsonStr);
            Class<?>[] parameterTypes = method.getParameterTypes();
            Object o = JSONObject.toJavaObject(jsonObject, parameterTypes[0]);
            Object[] args = {o};
            mav = doInvoke(bean, method, args);
        } else if (methodType.GET.name().equalsIgnoreCase(httpMethod.toUpperCase())) {
            Object[] parameter = getParameter(methodParameters, request);
            mav = doInvoke(bean, method, parameter);
        }
        return mav;
    }

    /**
     * 执行controller方法
     *
     * @param bean   controller对应的bean
     * @param method controller方法
     * @param args   controller方法入参
     * @return ModelAndView 视图层
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public ModelAndView doInvoke(Object bean, Method method, Object[] args) throws IllegalAccessException, InvocationTargetException {
        Object invoke = method.invoke(bean, args);
        log.info("===========方法返回值：{}", JSONObject.toJSONString(invoke));
        return dealResponse(invoke);
    }

    /**
     * 处理返回值
     *
     * @param invoke
     * @return
     */
    private ModelAndView dealResponse(Object invoke) {
        if (invoke instanceof ResultResponse) {
            ResultResponse resultResponse = (ResultResponse) invoke;
            return ModelAndViewUtil.setModelAndViewJson(resultResponse);
        }
        return ModelAndViewUtil.setModelAndView(-1, "error",
                "返回值非法,返回值必须使用ResultResponse对象返回", null);
    }

    /**
     * get方法获取参数
     *
     * @param methodParameters
     */
    private Object[] getParameter(MethodParameter[] methodParameters, HttpServletRequest request) {
        Object[] args = new Object[methodParameters.length];
        for (int i = 0; methodParameters.length > i; i++) {
            MethodParameter methodParameter = methodParameters[i];
            methodParameter.initParameterNameDiscovery(parameterNameDiscoverer);
            String parameterName = methodParameter.getParameterName();
            String requestParameter = request.getParameter(parameterName);
            Class<?> parameterType = methodParameter.getParameterType();
            DataBinder dataBinder = new DataBinder(null);
            Object parameter = dataBinder.convertIfNecessary(requestParameter, parameterType, methodParameter);
            args[i] = parameter;
        }
        log.info("==========请求入参为：{}", JSONArray.toJSONString(args));
        return args;
    }

    private String getJSONStringFromRequest(HttpServletRequest request) {
        try (ServletInputStream in = request.getInputStream();) {
            ByteArrayOutputStream result = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];

            int length;
            while ((length = in.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            in.close();
            String jsonStr = result.toString("UTF-8");
            result.close();
            return jsonStr;
        } catch (IOException e) {
            logger.error("解析前端request入参异常", e);
            throw new BaseException("解析前端request入参异常,post请求后端入参只能用DTO");
        }

    }
}

package com.fate.core.utils;

import com.alibaba.fastjson.JSONObject;
import com.fate.core.response.ResultResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.xml.transform.Result;

/**
 * @ProjectName: fate-core
 * @Package: com.fate.core.utils
 * @ClassName: ModelAndViewUtil
 * @Author: LJP
 * @Description:
 * @Date: 2020/10/30 16:53
 * @Version: 1.0
 */
@Slf4j
public class ModelAndViewUtil {

    public static ModelAndView setModelAndView(int code, String type, String message, Object data) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", code);
        jsonObject.put("type", type);
        jsonObject.put("message", message);
        jsonObject.put("data", data);
        return new ModelAndView(new MappingJackson2JsonView()).addAllObjects(jsonObject);
    }

    public static ModelAndView setModelAndViewJson( ResultResponse resultResponse ) {
        String jsonString = JSONObject.toJSONString(resultResponse);
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        return new ModelAndView(new MappingJackson2JsonView()).addAllObjects(jsonObject);
    }
}

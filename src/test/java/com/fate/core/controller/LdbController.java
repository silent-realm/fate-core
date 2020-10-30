package com.fate.core.controller;

import com.fate.core.annotation.FateRequestMapping;
import com.fate.core.dto.LdbDTO;
import com.fate.core.response.ResultResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ProjectName: fate-core
 * @Package: com.fate.core.controller
 * @ClassName: LdbController
 * @Author: LJP
 * @Description:
 * @Date: 2020/10/28 16:24
 * @Version: 1.0
 */
@RestController
//@RequestMapping("ldb")
@FateRequestMapping("ldb")
public class LdbController {

//    @RequestMapping("/ldb")
    @FateRequestMapping("/ldb")
    public ResultResponse ldb(@RequestBody LdbDTO ldbDTO) {
        return ResultResponse.info(0,"测试通过",ldbDTO);
    }

//    @RequestMapping("/ldb1")
    @FateRequestMapping("/ldb1")
    public ResultResponse ldb1(String age,String name,int aaa) {
        return ResultResponse.info(0,"测试通过","name="+name+",age="+age+",aaa="+aaa);
    }
}

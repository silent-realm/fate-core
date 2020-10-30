package mvc;

import com.alibaba.fastjson.JSON;
import com.fate.core.dto.LdbDTO;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * @ProjectName: fate-core
 * @Package: mvc
 * @ClassName: MvcTest
 * @Author: LJP
 * @Description:
 * @Date: 2020/10/28 16:33
 * @Version: 1.0
 */

@RunWith(SpringJUnit4ClassRunner.class) //使用junit4进行测试
@WebAppConfiguration
@ContextConfiguration(locations={"classpath:spring.xml"}) //加载配置文件
public class MvcTest {


    MockMvc mockMvc;

    //传入springMvc的IOC
    @Autowired
    WebApplicationContext context;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @SneakyThrows
    @Test
    public void test(){
//        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
//        //模拟请求拿到返回值
//        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/ldb/ldb")).andReturn();
//        Object user = result.getModelAndView().getModel().get("data");
//        System.out.println(user);

        mockMvc.perform(MockMvcRequestBuilders.get("/ldb/ldb1")
                .param("name","ldb")
                .param("aaa","13")
                .param("age","11"))
                .andDo(MockMvcResultHandlers.print());

//        String jsonStr= JSON.toJSONString(new LdbDTO(1,"ldb",23));
//        RequestBuilder request = MockMvcRequestBuilders.post("/ldb/ldb")
//                .accept(MediaType.APPLICATION_JSON_UTF8)
//                .contentType(MediaType.APPLICATION_JSON_UTF8)
//                .content(jsonStr);
//        mockMvc.perform(request)
//                .andDo(MockMvcResultHandlers.print());
    }
}

package com.baidu.aopcont.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * 模拟controller的测试
 *    可以获取请求中的数据
 *    可以获取响应的数据
 */
@WebMvcTest
class TestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testController() throws Exception{
        String url = "http://localhost:8080/test?id=2";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(url)).andReturn();
        MockHttpServletRequest request = mvcResult.getRequest();

        // 非 json格式的请求，获取参数
        String queryString = request.getQueryString();
        System.out.println("queryString: " + queryString);

        // json 格式的请求要解析inputStream
        // Note that: inputStream 作为流只能读一次

        // 获取响应
        MockHttpServletResponse response = mvcResult.getResponse();
        String contentOfResult = response.getContentAsString();
        System.out.println("contentOfResult: " + contentOfResult);
    }
}
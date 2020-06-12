package com.baidu.aopcont.aspectjController;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * 实现springMVC框架中Handler的拦截
 *    实现接口的方法都可以进行相关的操作
 *
 *   1、获取请求中 json 格式的数据
 *   2、获取请求中非 json 格式的数据
 */
public class MyHandlerInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyHandlerInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 直接根据name获取
        String id = request.getParameter("id");
        System.out.println(id);

        // 获取请求中非 json格式的数据，截取请求中？ 后的数据
        String queryString = request.getQueryString();
        System.out.println(queryString);
        try {
            String utf8Str = URLDecoder.decode(queryString,"utf-8");//将中文转码
            System.out.println(utf8Str);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        LOGGER.info("这是前置~~~~~~~~-----");

        // return true 继续向controller执行。return false 不向controller执行
        return true;
    }
    /*@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String value = "";
        try {
            // Note that 获取请求中 json格式的数据，但是request中的流只能读一次
            InputStream in = request.getInputStream();
            InputStreamReader is = new InputStreamReader(in);
            BufferedReader br = new BufferedReader(is);
            StringBuffer sb = new StringBuffer();

            String s = "";
            while ((s = br.readLine()) != null) {
                sb.append(s);
            }
            value = sb.toString();
        } catch (Exception ex) {
            LOGGER.info(ex.getMessage(), ex);
        }
        return true;
    }*/

    // 静态内部配置类，将实现了HandlerInterceptord的类注册到WebMVCConfigurer
    // 注册后才能生效
    @Configuration
    static class WebAppConfig implements WebMvcConfigurer {
        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            // 注释掉这行，则拦截不生效
            registry.addInterceptor(new MyHandlerInterceptor());
        }
    }
}

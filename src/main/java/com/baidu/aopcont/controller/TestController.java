package com.baidu.aopcont.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class TestController {

    @GetMapping("/test")
    public String testController(@RequestParam Map<String, Object> map){
        System.out.println(map);
        return "good";
    }
}

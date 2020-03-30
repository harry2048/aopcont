package com.baidu.aopcont.service.impl;

import com.baidu.aopcont.service.ITestService;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements ITestService {
    @Override
    public void testPointCut() {
        System.out.println("this is testPointCut");
    }
}

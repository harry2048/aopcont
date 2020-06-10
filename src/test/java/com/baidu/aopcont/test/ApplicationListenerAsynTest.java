package com.baidu.aopcont.test;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.SimpleApplicationEventMulticaster;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * spring事件的demo
 */
public class ApplicationListenerAsynTest {

    public static void main(String[] args) {
        final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.addApplicationListener(new ApplicationListener<ApplicationEvent>() {
            @Override
            public void onApplicationEvent(ApplicationEvent event) {
                println(event.getSource().toString());
            }
        });
        context.register(MyConfiguration.class);
        context.refresh();
        context.close();
    }

    @Configuration
    public static class MyConfiguration {

        @Bean
        public SimpleApplicationEventMulticaster applicationEventMulticaster() {
            SimpleApplicationEventMulticaster multicaster = new SimpleApplicationEventMulticaster();
            multicaster.setTaskExecutor(new ThreadPoolExecutor(5, 10, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10)));
            return multicaster;
        }
    }

    private static void println(String message) {
        System.out.printf("[线程 : %s] %s\n",
                Thread.currentThread().getName(), // 当前线程名称
                message);
    }
}
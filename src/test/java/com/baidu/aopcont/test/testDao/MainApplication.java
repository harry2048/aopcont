package com.baidu.aopcont.test.testDao;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class AppConfig {

	@Bean
	public UserDao userDao(){
		// 会被打印几次 ？？
		System.out.println("注入UserDao");
		return new UserDao();
	}

	@Bean
	public OrderDao orderDao(){
		// 在这里调用userDao()方法
		userDao();
		return new OrderDao();
	}

}
// 启动容器
public class MainApplication {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
	}

}
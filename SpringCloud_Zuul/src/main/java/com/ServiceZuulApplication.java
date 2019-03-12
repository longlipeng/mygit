package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
//注册到服务中心
@EnableEurekaClient
//路由网关Zuul
@EnableZuulProxy
public class ServiceZuulApplication {

	public static void main(String[] args){
		SpringApplication.run(ServiceZuulApplication.class, args);
	}
	
	//定义一个bean对象
	@Bean
	public DemoFilter demoFilter() {
		return new DemoFilter();
	}
	
}

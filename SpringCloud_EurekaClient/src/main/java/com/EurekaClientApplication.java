package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@SpringBootApplication
//注册到服务中心
@EnableEurekaClient
public class EurekaClientApplication {

	public static void main(String[] args){
		SpringApplication.run(EurekaClientApplication.class, args);
	}
	
}

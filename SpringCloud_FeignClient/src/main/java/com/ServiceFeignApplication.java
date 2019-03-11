package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringBootApplication
//开启服务自动发现
@EnableDiscoveryClient
//Feign服务由于内置断路器支持,所以不需要@EnableCircuitBreaker注解
//@EnableCircuitBreaker
//使用Feign
@EnableFeignClients
public class ServiceFeignApplication {

	public static void main(String[] args){
		SpringApplication.run(ServiceFeignApplication.class, args);
	}
	
}

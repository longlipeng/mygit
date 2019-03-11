package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;
 
/**
 * 注意: 因为Springboot默认加载启动类同级或者子级目录中的文件,如果HelloService和HelloController类没有
 *      和Springboot启动类在同级或者子级目录中,会加载不到HelloService和HelloController类,访问则会出现
 *      404;
 * 解决办法: 1.把两类放在启动类的同级或者子级目录中; 2.否则启动类增加注解扫描包@ComponentScan("包的目录")
 * @author 
 *
 */

@SpringBootApplication
//向服务中心注册，并且注册了一个叫restTemplate的bean,开启服务自动发现
@EnableDiscoveryClient
//@ComponentScan("service")
public class ServiceRibbonApplication {

	public static void main(String[] args){
		SpringApplication.run(ServiceRibbonApplication.class, args);
	}
	
	@Bean
	//注册表明这个restRemplate是需要做负载均衡的
	@LoadBalanced
	RestTemplate restTemplate(){
		return new RestTemplate();
	}
	
}

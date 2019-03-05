package com;

//import org.springframework.boot.env.PropertiesPropertySourceLoader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * SpringCloud Config服务配置中心
 * @author 名字居然用了
 *
 */
@SpringBootApplication
@EnableEurekaClient
//服务配置中心注解
@EnableConfigServer
public class ConfigServerApplication {

	public static void main(String[] args){
		SpringApplication.run(ConfigServerApplication.class, args);
	}
	
}

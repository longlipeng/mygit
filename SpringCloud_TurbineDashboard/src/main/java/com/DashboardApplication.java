package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

@SpringBootApplication
//@EnableHystrixDashboard和@EnableTurbine修饰主类， 分别用于支持Hystrix Dashboard和Turbine
@EnableHystrixDashboard
@EnableTurbine
public class DashboardApplication {

	public static void main(String[] args){
		SpringApplication.run(DashboardApplication.class, args);
	}
	
}

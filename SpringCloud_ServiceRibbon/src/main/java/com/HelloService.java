package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class HelloService {

	@Autowired
	RestTemplate restTemplate;
	
	//该注解定义一个断路器,它封装了getHelloContant()方法， 当它访问的SERVICE-HELLOWORLD失败达到阀值后，
	//将不会再调用SERVICE-HELLOWORLD， 取而代之的是返回由fallbackMethod定义的方法serviceFailure()
	@HystrixCommand(fallbackMethod = "serviceFailure")
	public String getHelloContent(){
		return restTemplate.getForObject("http://SERVICE-HELLOWORLD/", String.class);
	}
	
	/**
	 * 错误处理方法
	 * @return
	 */
	public String serviceFailure() {
        return "hello world service is not available !";
    }
	
}

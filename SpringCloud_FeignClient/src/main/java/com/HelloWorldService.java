package com;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//通过value定义了需要调用的SERVICE-HELLOWORLD服务（通过服务中心自动发现机制会定位具体URL）
@FeignClient(value = "SERVICE-HELLOWORLD")
public interface HelloWorldService {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	String sayHello();
	
}

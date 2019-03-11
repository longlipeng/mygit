package com;

import org.springframework.stereotype.Service;

@Service("HelloWorldService")
public class HelloWorldServiceFailure implements HelloWorldService {

	/**
	 * 错误处理方法
	 */
	public String sayHello() {
		// TODO Auto-generated method stub
		System.out.println("hello world service is not available !");
		return "hello world service is not available !";
	}

}

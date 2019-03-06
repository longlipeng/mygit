package com;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class UserController {
	
	@Value("${user.userName}")
	private String userName;
	
	@Value("${user.sex}")
	private String sex;
	
	@Value("${user.age}")
	private String age;
	
	@RequestMapping(value = "/hello")
	public String hello(){
		return "姓名:" + userName + "性别:" + sex + "年龄:" + age;
	}
	
}

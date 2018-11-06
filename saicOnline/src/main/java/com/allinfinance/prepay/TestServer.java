package com.allinfinance.prepay;

import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestServer
{

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception
	{
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext("application-context.xml");
		//RabbitTemplate template = ctx.getBean(RabbitTemplate.class);
		RabbitAdmin admin = ctx.getBean(RabbitAdmin.class);
		admin.purgeQueue("icspReply", true);
		System.out.println("server started");
		
	}

}

package com.allinfinance.prepay;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.remoting.client.AmqpProxyFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestClient
{

	private static RabbitTemplate template;
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		TestClient client = new TestClient();
		client.init();
		client.send(1);
		
	}
	
	public void send(int threads)
	{
		for(int i=0; i<threads; i++)
		{
			Thread thread = new Thread(new Job(i));
			thread.run();
		}
	}
	
	private class Job implements Runnable
	{
		private int id;
		public Job(int id)
		{
			this.id = id;
		}
		@Override
		public void run()
		{
			MessageProperties prop = new MessageProperties();
			prop.setCorrelationId((id+"").getBytes());
			Message message = new Message(("hello,I am thread "+id).getBytes(), new MessageProperties());
			System.out.println("线程"+id+"发送消息:" + new String(message.getBody()));
			Message resp = template.sendAndReceive(message);
			System.out.println("线程"+id+"收到响应：" + new String(resp.getBody()));
		}
	}
	public void init()
	{
		ApplicationContext ctx = new ClassPathXmlApplicationContext("application-context.xml");
		template = ctx.getBean("amqpTemplate", RabbitTemplate.class);
	}

}

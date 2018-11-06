package com.webservice.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.webservice.util.ServiceSocket;



public class SocketListener implements ServletContextListener{
	
	 public void contextDestroyed(ServletContextEvent e) {   
	    } 
	 
	    public void contextInitialized(ServletContextEvent e) {  
//	            System.out.println("------------web start-------");
                 new Thread(new ServiceSocket()).start();
	    } 
	

}

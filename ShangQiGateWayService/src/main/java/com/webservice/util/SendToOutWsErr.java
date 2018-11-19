package com.webservice.util;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**   
 * @Title: SendToOutWsErr.java
 *
 * @Description: TODO
 *
 * @date: 2014年7月23日 下午11:24:33 
 *
 * @Company: Copyright © WuHan TianYu Information Industry CO., LTD
 *
 * @author: zhangyudong  
 *
 * @version: V1.0   
 */
@XmlRootElement(name = "Envelope")
@XmlType(propOrder = {"body"})
public class SendToOutWsErr {

	public SendToOutWsErr() {
		// TODO Auto-generated constructor stub
	}

	private BodySendToOutWsErr body;

	/**
	 * @return the body
	 */
	@XmlElement(name = "Body")
	public BodySendToOutWsErr getBody() {
		return body;
	}

	/**
	 * @param body the body to set
	 */
	public void setBody(BodySendToOutWsErr body) {
		this.body = body;
	}
	
	
}

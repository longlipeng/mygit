package com.huateng.framework.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

public class SendShortMessageThread  extends Thread{
	
Logger logger = Logger.getLogger(this.getClass());
	
	private String entityId;
	private String telephone;
	private String templet_No;
	private String comment;
	
	public SendShortMessageThread(String telephone ,String templet_No){
		this.telephone = telephone;
		this.templet_No = templet_No;
	}
	public SendShortMessageThread(String telephone ,String templet_No,String comment){
		this(telephone,templet_No);
		this.comment = comment;
	}
	
	
	
	
	public void run() {
		String rsp = toShortMessageService(telephone ,templet_No,comment);
		if(rsp!=null&&!rsp.equals("")){
			String rspFlag="<RESP_FLAG>";
			int index=rsp.indexOf(rspFlag);
			String rspCode=rsp.substring(index+rspFlag.length(), index+rspFlag.length()+2);
			logger.info("调用短息接口返回码："+rspCode);
		}
	}

	
	
	/**
	 * 发送短信
	 * @param 
	 * @return
	 */
	public String toShortMessageService(String telephone ,String templet_No,String comment){
		String responseMessage="";
		Socket socket =null;
		InputStream is =null;
		DataInputStream dataIs = null;
		logger.debug("短息IP"+ConfigMessage.getIp());
		logger.debug("短信端口"+ConfigMessage.getPort());
		try {  
			socket = new Socket(ConfigMessage.getIp(),Integer.parseInt(ConfigMessage.getPort()));  
            // 向服务器端发送数据  
            OutputStream os =  socket.getOutputStream();  
            DataOutputStream bos = new DataOutputStream(os);  
            
            DownNoteXML downNoteXML = new DownNoteXML();
        	downNoteXML.setSmsType("0");//短信即刻发
        	downNoteXML.setMobile(telephone);//客户手机号码
	        downNoteXML.setSms(templet_No);//短信模版号
	        //downNoteXML.setRequestTime(Long.toString(System.currentTimeMillis()));
	        Date now = new Date(); 
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			String nowDate = dateFormat.format(now); 
			downNoteXML.setRequestTime(nowDate);
	        if(comment!=null||comment!=""){
	        	downNoteXML.setCv(comment);
	        }
//	        if(customerName!=null||customerName!=""){
//	        	downNoteXML.setClient(customerName);
//	        }
            String xml = JaxbUtil.convertToXml(downNoteXML).trim();
            byte [] content = xml.getBytes("UTF-8");
            int len = content.length;
            String str = JaxbUtil.codeAddOne(String.valueOf(len), 4);
            String con = str + new String(content,"UTF-8");//拼接报文，最前是长度
            //转换编码
            logger.info("报文内容"+con);
            bos.write(con.getBytes("UTF-8")); 
            bos.flush();  
//            接收服务器端数据  
           is = socket.getInputStream();  
           dataIs = new DataInputStream(is);
           byte[] header = new byte[4];
           dataIs.read(header);
           int msgLen = Integer.parseInt(new String(header));
//           logger.info("报文长度："+msgLen);
           byte[] body = new byte[msgLen];
           dataIs.read(body);
           responseMessage = new String(body);
           logger.info("收到的回复"+responseMessage);  
           is.close();
           socket.close();
        } catch (UnknownHostException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally{
        	
        	
        		try {
        			if(dataIs!=null){
        				dataIs.close();
        			}
        			if(is!=null){
        				is.close();
        			}
					if(socket!=null){
						socket.close();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
		return responseMessage;
	}
	
	
	
	

	public String getEntityId() {
		return entityId;
	}



	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}



	public String getTelephone() {
		return telephone;
	}



	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}



	public String getTemplet_No() {
		return templet_No;
	}



	public void setTemplet_No(String templet_No) {
		this.templet_No = templet_No;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}

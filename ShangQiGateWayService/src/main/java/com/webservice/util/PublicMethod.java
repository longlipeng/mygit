package com.webservice.util;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;

import com.huateng.framework.exception.BizServiceException;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class PublicMethod {
	private String data="";
	public void task(final String data,final String method,final ChannelHandlerContext ctx){
		  
		Runnable run = new Runnable() {
			private Logger logger = Logger.getLogger(PublicMethod.class);
			@Override
			public void run() {
				
				boolean flag = true;
				
				//Singleton sin=Singleton.getInstance();
				for(int i=0;i<3;i++){
					
					String jsonStr="";
					try {
						jsonStr = HttpPostData.getRemoteInfo(data, method);
					} catch (BizServiceException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if(jsonStr.length()>0){
						//jsonStr=JsonUtil.convertToPospJson(jsonStr);
						
						String sendXmlByteLengthToSd = null;
						try {
							sendXmlByteLengthToSd = CommonFunction.fillString(
									jsonStr.getBytes(Constants.XMLENCODING).length + "",
									'0', 4, false);
						//sendXmlByteLengthToSd+jsonStr;
						String datas = sendXmlByteLengthToSd+jsonStr;
						logger.debug("接受扣款返回给posp的数据："+datas);
						ByteBuf encoded = ctx.alloc().buffer(4 * datas.length());
						encoded.writeBytes(datas.getBytes());
						ctx.write(encoded);
						ctx.flush();
				    	//sin.getMapStr().remove(biaoshi);
						flag = false;
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					//jsonStr=jsonStr.length()+jsonStr;
					//if(!sin.getMapStr().get(biaoshi).equals("")){
						//String jsonStr=sin.getMapStr().get(biaoshi);
						
					//}
					}
					long millis =5*1000;
					if(i==2){
						millis = 50 *1000;
					}
					try {
						if(flag){
							Thread.sleep(millis);
							}else{
								break;
							}
						
						
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		Thread thread = new Thread(run);
		thread.start();
		
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
	


}

package com.webservice.util;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONObject;

//import antlr.StringUtils;


import com.ibm.db2.jcc.t4.ob;
import com.webservice.service.SendToPospServiceImpl;



public class HttpServerInboundHandler extends ChannelInboundHandlerAdapter {

	private Logger logger = Logger.getLogger(HttpServerInboundHandler.class);
	/**
	 * 加密后的方法
	 */
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		
		PublicMethod publicMethod = new PublicMethod();
		ByteBuf buf = (ByteBuf) msg;//接收到的数据
		StringBuffer sb=new StringBuffer("");
		for (int i = 0; i < buf.capacity(); i ++) {
		       byte  b =buf.getByte(i);
		       System.out.print( (char) b);
		       sb.append((char) b);
		   }
		   System.out.println(sb.toString());
		    //posp端发送的数据	
		    logger.info("监听posp发送过来的数据："+sb.toString());
		    String length=sb.toString().substring(0,4);
		    String data=sb.toString().substring(4);
			String dataContent=data.substring(0,Integer.parseInt(length)-4);
			String methodId=data.substring(Integer.parseInt(length)-4);
			String method="";
			if(methodId.trim().equals("9001")){//预充值
				method=Constants.Method_GetOrderInfo;
			}else if(methodId.trim().equals("9011")){//充值	
				method=Constants.Method_CardSpareMoneyRecharge;
			}else if(methodId.trim().equals("9021")){//充值状态
				method=Constants.Method_OrederQuery;
			}else if(methodId.trim().equals("1021")){//余额查询
				method=Constants.Method_GetCardNoInfo;
			}else if(methodId.trim().equals("9031")){//对账文件通知
				method=Constants.Method_ReconQuery;
			}else if(methodId.trim().equals("003A")){//解绑
				method=Constants.Method_DELCARDS;
			}
			
			//向中石油发送数据
			//判断是否是接受扣款交易
			if(methodId.equals("9011")){
				logger.info("接受扣款发送给中石油的数据："+dataContent);
				publicMethod.task(dataContent,method,ctx);
			}else{
						logger.info("发送给中石油的数据："+dataContent);
						String redata=HttpPostData.getRemoteInfo(dataContent, method);
						logger.info("中石油返回的数据："+redata);
						String sendXmlByteLengthToSd = CommonFunction.fillString(
									redata.getBytes(Constants.XMLENCODING).length + "",
									'0', 4, false);	
						String datas=sendXmlByteLengthToSd+redata;
						logger.info("返回给posp的数据："+datas);
						ByteBuf encoded = ctx.alloc().buffer(4 * datas.length());
						encoded.writeBytes(datas.getBytes());
						ctx.write(encoded);
						ctx.flush();
				}
			
			}
	
	/**
	 * 加密前的方法
	 */
//	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//		
//		PublicMethod publicMethod = new PublicMethod();
//		ByteBuf buf = (ByteBuf) msg;//接收到的数据
//		StringBuffer sb=new StringBuffer("");
//		for (int i = 0; i < buf.capacity(); i ++) {
//		       byte  b =buf.getByte(i);
//		       System.out.print( (char) b);
//		       sb.append((char) b);
//		   }
//		   System.out.println(sb.toString());
//		    //posp端发送的数据	
//		    logger.debug("监听posp发送过来的数据："+sb.toString());
//			String data=sb.toString().substring(4);
//			Map<String, Object> map=JsonConvert.parseJSON2Map(data);
//			String method="";
//			if(map.get("Fied1").equals("9001")){//预充值
//				method=Constants.Method_GetOrderInfo;
//			}else if(map.get("Fied1").equals("9011")){//充值	
//				method=Constants.Method_CardSpareMoneyRecharge;
//			}else if(map.get("Fied1").equals("9021")){//充值状态
//				method=Constants.Method_OrederQuery;
//			}else if(map.get("Fied1").equals("1021")){//余额查询
//				method=Constants.Method_GetCardNoInfo;
//			}else if(map.get("Fied1").equals("9031")){//对账文件通知
//				method=Constants.Method_ReconQuery;
//			}
//			//data=ParseToXML.converterPayPalm(map, method);
//			//获取服务代码
//			//String method=JsonUtil.getMethodId(data);
//			
//			
//			//向中石油发送数据
//			//判断是否是接受扣款交易
//			if(map.get("Fied1").equals("9011")){
//				logger.debug("接受扣款发送给中石油的数据："+data);
//				publicMethod.task(map,method,ctx);
////				if(!StringUtils.isEmpty(publicMethod.getData()))
////				{
////					ByteBuf encoded = ctx.alloc().buffer(4 * publicMethod.getData().length());
////					encoded.writeBytes(publicMethod.getData().getBytes());
////					ctx.write(encoded);
////					ctx.flush();
////				}
//			}else{
//						
//						String redata=HttpPostData.getRemoteInfo(map, method);
//						logger.debug("返回给posp的数据："+redata);
//						
//						redata=JsonUtil.convertToPospJson(redata);
//						String sendXmlByteLengthToSd = CommonFunction.fillString(
//									redata.getBytes(Constants.XMLENCODING).length + "",
//									'0', 4, false);
//						
//						String datas=sendXmlByteLengthToSd+redata;
//						ByteBuf encoded = ctx.alloc().buffer(4 * datas.length());
//						encoded.writeBytes(datas.getBytes());
//						ctx.write(encoded);
//						ctx.flush();
//				    
//					
//			
//				
//				}
//			
//			}
	
	 
}

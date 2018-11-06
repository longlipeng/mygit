package com.huateng.hstserver.communicate.mina.comm.common.filter.protocolcodec.app.biz;



import java.lang.reflect.Method;
import java.math.BigDecimal;
import org.apache.log4j.Logger;
import com.huateng.hstserver.communicate.mina.comm.common.filter.protocolcodec.app.IAppObjectProcessor;
import com.huateng.hstserver.frameworkUtil.CConstant;
import com.huateng.hstserver.frameworkUtil.StringUtils;

public class BizMsgProcessor implements IAppObjectProcessor{
Logger logger=Logger.getLogger(BizAppObjectProcessor.class);

public static final byte[] msgTop1=new byte[]{0,0,0,0,0,0,0,0};
public static final byte[] msgTop2=new byte[]{4,0,0,0,0,0,0,0,17,0,0,0,-1,-1,-1,-1,0,0,-5,30};
//acqdom   97,99,113,100,111,109,
//vCardBatInq  118,67,97,114,100,113,
public static final byte[] msgTop3=new byte[]{0,0};
public static final String len="000";
public static final String otherDataLen="000000";
static final String sep = "\r\n";
	@Override
	public Object msg2obj(byte[] messageBytes) throws Exception{
		BizMessageObj message = new BizMessageObj();
		Class classType = BizMessageObj.class;                                          
		                                                                                    
		//报文头 长度104 报文域长度的长度为10                             
		byte [] bodyLenByte=new byte[10];
		System.arraycopy(messageBytes,104, bodyLenByte, 0, 10);
		int bodyLen=Integer.parseInt(new String(bodyLenByte).trim());                        
		logger.info("bodyLen:"+bodyLen);                                                    
		if(bodyLen>0){
			byte [] bodyByte=new byte[bodyLen];
			System.arraycopy(messageBytes, 114, bodyByte, 0, bodyLen);
//			String body = new String(bodyByte);                                    
			int fieldsIndex=0;                                                                
			for(int i=0;i<CConstant.fields.length;i++){				                                                
				//普通域长度的长度为3     otherdata域长度的长度为6                              
				int len=0;				
				if(i==CConstant.fields.length-1){  
					byte [] lenByte=new byte[6];
					System.arraycopy(bodyByte, fieldsIndex, lenByte, 0, 6);
					len=Integer.parseInt(new BigDecimal(new String(lenByte)).toString()); 
					fieldsIndex+=6;                                                                         
				}else{     
					byte [] lenByte=new byte[3];
					System.arraycopy(bodyByte, fieldsIndex, lenByte, 0, 3);
					len=Integer.parseInt(new BigDecimal(new String(lenByte)).toString());
					fieldsIndex+=3;                                                                         
				}                                                                               
				//当前域set方法名                                                               
				String methodName="set"+CConstant.fields[i];                          
				Method m=classType.getMethod(methodName, String.class);                         
				if(len==0){                                                                     
					m.invoke(message, "");                                                        
				}else{
					byte[] msgByte=new byte[len];
					System.arraycopy(bodyByte, fieldsIndex, msgByte, 0, len);
					m.invoke(message,new String(msgByte,"gbk"));                                    
					logger.info(CConstant.fields[i]+":"+new String(msgByte,"gbk"));       
					fieldsIndex+=len;                                                                       
				}                                                                               
				if(fieldsIndex==CConstant.fields.length-1){                                     
					return message;                                                               
				}                                                                               
			}                                                                                 
		}                                                                            
		   
		return message;
	}

	@Override
	public byte[] obj2msg(Object messageObject) throws Exception{
		BizMessageObj message = (BizMessageObj)messageObject;
		Class classType = BizMessageObj.class;                                                  
		String msg="";
        logger.debug("##########fields.length#################");
		if(null!=message){
			for(int i=0;i<CConstant.fields.length;i++){
				//当前域set方法名                                                               
				String methodName="get"+CConstant.fields[i];
				Method m=classType.getMethod(methodName);                                                                                            
				String value=(String)m.invoke(message);
				if(i==CConstant.fields.length-1){
					if(null==value || "".equals(value)){
						msg+=otherDataLen;
					}else{
						msg+=StringUtils.fillString(String.valueOf(value.length()),6)+value;
					} 
				}
				if(null==value || "".equals(value)){
					msg+=len;
				}else{
					msg+=StringUtils.fillString(String.valueOf(value.length()),3)+value;
				} 
				logger.debug("CConstant.fields[i] "+ value);
			}
		}
        //拼接报文头
        byte[] msgTop=this.combineMsgTop(message.getServiceName(),msg);
        //拼接报文
        byte[] msgBag=this.combineMsg(msg,msgTop);
		
		return msgBag;
	}
	
	/**
	 * 组装报文
	 * @param message
	 * 报文域数据
	 * @param msgTop
	 * 报文头
	 * @return
	 */
	public byte[] combineMsg(String message,byte[] msgTop){
		//报文域数据
		byte[] a=message.getBytes();
		/**
		 * 报文格式
		 * 报文头
		 * 10位报文域长度
		 * 报文域数据
		 */
		//报文总长度
		int totalLen=msgTop.length+10+a.length;

		//10位报文域长度（包含自己）
		byte[] dataLen=StringUtils.fillString(String.valueOf(a.length+10), 10).getBytes();
		//报文
		byte[] msg=new byte[totalLen];
		System.arraycopy(msgTop, 0,msg, 0, msgTop.length);
		System.arraycopy(dataLen, 0,msg, msgTop.length, 10);
		System.arraycopy(a, 0,msg, msgTop.length+10, a.length);
		return msg;
	}
	
	//组装报文头
	public byte[] combineMsgTop(String serviceName,String message){
		String domanid="acqdom";
		//报文域总长度（包含10位报文域长度）
		byte[] msgTotalLen=StringUtils.StringTobyte(message.getBytes().length+10);
		byte[] doman=StringUtils.fillByteArray(domanid.getBytes(),33);
		byte[] service=StringUtils.fillByteArray(serviceName.getBytes(),33);
		/**
		 * 报文头(总长度111)
		 * msgTop1+报文域总长度8位+msgTop2+domanid+serviceName+msgTop3
		 */
		byte[] msgTop=new byte[104];
		System.arraycopy(msgTop1, 0,msgTop, 0, msgTop1.length);
		System.arraycopy(msgTotalLen, 0,msgTop, msgTop1.length, msgTotalLen.length);
		System.arraycopy(msgTop2, 0,msgTop, msgTop1.length+msgTotalLen.length, msgTop2.length);
		System.arraycopy(doman, 0,msgTop, msgTop1.length+msgTotalLen.length+msgTop2.length, doman.length);
		System.arraycopy(service, 0,msgTop, msgTop1.length+msgTotalLen.length+msgTop2.length+doman.length, service.length);
		System.arraycopy(msgTop3, 0,msgTop, msgTop1.length+msgTotalLen.length+msgTop2.length+doman.length+service.length, msgTop3.length);
		logger.info(StringUtils.bytesToHexString(msgTop));
		return msgTop;
	}
	
	
/*	public static void main(String[] args) throws Exception{                                                                                                                                          	                                                     
//		String msg="002";                                                                                                                                                                           
//		logger.info(Integer.parseInt(new BigDecimal(msg.trim()).toString()));                                                                                                                
		                                                                                                                                                                                              
//		String a="set"+CConstant.ACC_TYPE;                                                                                                                                                          
//		Method m=BizMessageObj.class.getMethod(a, String.class);                                                                                                                                    
//		m.invoke(BizMessageObj.class.newInstance(), "a");                                                                                                                                           
		                                                                                                                                                                                              
//		String msg="....................................acqdom...........................vCardBatInq........................       277000000004G0000000000000087000000100000000000000000000000000000"+
//				   "00000000000000000000000000000000000000020000000000000000000000000000001930009102910000000430000000000000000963000910291000000043^1010^........^1^20120625^201806"+                    
//				   "30^1^0^0^0^0^0^^10000000#....#9703#1#100000@|.................";                                                                                                                      
//		byte[] m=msg.getBytes();                                                                                                                                                                      
//		BizAppObjectProcessor b=new BizAppObjectProcessor();                                                                                                                                          
//		b.msg2obj(m);                                                                                                                                                                                 
//		            
//		BizAppObjectProcessor a=new BizAppObjectProcessor();
	
		//String methodName="get"+CConstant.fields[i];  
//		BizMessageObj biz=new BizMessageObj();
//		biz.setMsgHead("aa");
//		logger.info("a");
//		Class classType = BizMessageObj.class;                                                  
//		Object invoker = classType.newInstance();  
//		Method method=classType.getMethod("getMsgHead"); 
//		logger.info(method.invoke(classType));
		
		
		
		
	//	BizMessageObj message = new BizMessageObj();
		//message.setTxnType("G000");
		//message.setServiceName("vCardBatInq");
		//message.setChannel("70000001");
//		Map map=new HashMap();
//		map.put(CConstant.TXN_TYPE, "G000");
//		map.put(CConstant.SERVICENAME, "vCardBatInq");
//		// 渠道号
//		map.put(CFunctionConstant.CHANNEL, "70000001");
//		map.put(CFunctionConstant.RESV1, "1111111111111111111");
		//a.obj2msg( message);                                                                                                                                                                                              
		                                                                                                                                                                                            
		   byte[] bytee=new byte[]{-78,-69, -68, -57,-61, -5 ,-93,  -87};
		   logger.info(new String(bytee,"gbk"));
		   byte[] by=new byte[]{32, 32, 32, 32, 32, 32 ,32 ,52, 48 ,57};
		   logger.info(Integer.parseInt(new String(by).trim()));
		   BizAppObjectProcessor biz=new BizAppObjectProcessor();
		   biz.combineMsgTop("vCardBatInq", "12345672345678909876543234567898765432345678987654345678765456787456785456786543456785434567865434567865432345678","acqdom");
		   logger.info(StringUtils.bytesToHexString(StringUtils.StringTobyte(213)));
				
	}    */ 

}

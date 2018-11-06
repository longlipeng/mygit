package com.huateng.hstserver.communicate.mina.comm.common.filter.logging;

import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.filter.keepalive.KeepAliveRequestTimeoutHandler;

import com.huateng.hstserver.communicate.mina.comm.common.DataTrans.DataTransTools;
import com.huateng.hstserver.communicate.mina.comm.common.entity.CommMessage;




public class LogMessGetterImpl implements ILogMessGetter {
	
	// outMessType: "HEX"-whole HEX string which is not seperate by tow letter, "HEXSEP"-HEX string which is seperate by tow letter, "STR"-origin string
	public static final String outMessTypeHEX = "HEX";
	
//	public static final String outMessTypeHEXSEP = "HEXSEP";
	
	public static final String outMessTypeSTR = "STR";
	
	private String outMessType = outMessTypeHEX;  
	

	public String getOutMessType() {
		return outMessType;
	}


	public void setOutMessType(String outMessType) {
		if (outMessType.equalsIgnoreCase(outMessTypeHEX)/* || outMessType.equalsIgnoreCase(outMessTypeHEXSEP)*/ || outMessType.equalsIgnoreCase(outMessTypeSTR))
		{
			this.outMessType = outMessType;
			return;
		}
		throw new IllegalArgumentException("Input out message type is not surpported");
	}


	public Object getMessFromObj(Object message) {
		String messageStr = null;
		byte[] messBytes = null;
//		StringBuffer
		if (message instanceof CommMessage)
		{
			CommMessage outmessage=(CommMessage) message;
			messBytes = outmessage.getMessagbody();
		}
		else if (message instanceof IoBuffer)
		{
			IoBuffer messBuffer = (IoBuffer) message;
			int messLen = messBuffer.remaining();
			messBytes = new byte[messLen];
			messBuffer.get(messBytes, 0, messLen);
//			messageStr = DataTransTools.bytesToHexString(messBytes);
			messBuffer.position(0);
		}
		else
		{
			return "Unknown original message type";
		}
		
		KeepAliveRequestTimeoutHandler my = null;
		
		/*
		if (outMessType.equals(outMessTypeHEXSEP))
		{
			return messBytes;
		}
		else */
		if (outMessType.equals(outMessTypeHEX))
		{
			messageStr = "[len=" + messBytes.length + ", content=" + DataTransTools.bytesToHexString(messBytes) + "]";
			return messageStr;
		}
		else if (outMessType.equals(outMessTypeSTR))
		{
			messageStr = "[len=" + messBytes.length + ", content=" + new String(messBytes, Charset.forName("us-ascii")) + "]"; 
			return messageStr;
		}
		else
		{
			return "Unknown out message type:" + outMessType;
		}
	}

}

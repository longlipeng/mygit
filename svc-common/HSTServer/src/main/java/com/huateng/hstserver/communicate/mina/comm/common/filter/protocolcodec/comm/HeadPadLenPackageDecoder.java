package com.huateng.hstserver.communicate.mina.comm.common.filter.protocolcodec.comm;


import java.net.InetSocketAddress;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import com.huateng.hstserver.communicate.mina.comm.common.entity.CommMessage;






@Deprecated
public class HeadPadLenPackageDecoder extends CumulativeProtocolDecoder  {
	private Logger logger = Logger.getLogger(HeadPadLenPackageDecoder.class);
	//length type: "N"-number string, "BCD"-number string with bcd compress, "Bi"-binary data
	private String lenFieldType = ProtocolCodecConst.lenTypeNumStr;
	private int lenFieldLen = 4;
	private boolean includeLenField = false;

	public String getLenFieldType() {
		return lenFieldType;
	}

	public void setLenFieldType(String lenFieldType) {
		this.lenFieldType = lenFieldType;
	}

	public int getLenFieldLen() {
		return lenFieldLen;
	}

	public void setLenFieldLen(int lenFieldLen) {
		this.lenFieldLen = lenFieldLen;
	}

	public boolean isIncludeLenField() {
		return includeLenField;
	}

	public void setIncludeLenField(boolean includeLenField) {
		this.includeLenField = includeLenField;
	}

	@Override
	protected boolean doDecode(IoSession Session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
		 
		int x=in.remaining();
        
		in.mark();
		
		if (x > lenFieldLen){
			int length = getPackLenFromIoBuffer(in);
			
			if (includeLenField)
			{
				length = length - lenFieldLen;
			}
			
			if (x >= length + lenFieldLen){
				byte[] body=new byte[length];
				in.get(body, 0, length);
			
				CommMessage inmessage=new CommMessage();
				inmessage.setLength(length);
				inmessage.setMessagbody(body);
				InetSocketAddress address  = (InetSocketAddress) Session.getServiceAddress();
                
				inmessage.setRemoteip(address.getAddress().getHostAddress());
				inmessage.setRemoteport(address.getPort());
				out.write(inmessage);
				
//				in.compact();
//				in.limit(in.position());
//				in.flip();
				return true;
			}
			
		}  
		in.reset();
        return false;
	}
	
	private int getPackLenFromIoBuffer(IoBuffer in) throws Exception
	{
		int length = -1;
		if (lenFieldType.equals(ProtocolCodecConst.lenTypeNumStr))
		{
			Charset set = Charset.forName("us-ascii");   
	        CharsetDecoder dec = set.newDecoder();
	         
			String Slength;
			try {
				Slength = in.getString(lenFieldLen, dec);
				length=Integer.valueOf(Slength);
			} catch (CharacterCodingException e) {
				
				logger.error(e.getMessage());
			}
			return length;
		}
		else if (lenFieldType.equals(ProtocolCodecConst.lenTypeBCD) || lenFieldType.equals(ProtocolCodecConst.lenTypeBinary))
		{
			byte lenByte = 0;
			length = 0;
			for (int i = 0; i < lenFieldLen; i ++)
			{
				if (lenFieldType.equals(ProtocolCodecConst.lenTypeBinary))
				{
					length = length * 256;
				}
				else
				{
					length = length * 100;
				}
				 
				lenByte = in.get();
				if (lenFieldType.equals(ProtocolCodecConst.lenTypeBCD))
				{
					lenByte = (byte)(((lenByte & 0xF0) / 16) * 10 + (lenByte & 0x0F));
				}
				length =  length + lenByte;
			}
		}
		return length;
	}

}

package com.huateng.hstserver.communicate.mina.comm.common.filter.protocolcodec;


import java.net.InetSocketAddress;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import com.huateng.hstserver.communicate.mina.comm.common.entity.CommMessage;
import com.huateng.hstserver.communicate.mina.comm.common.filter.protocolcodec.app.DefaultNullAppObjectProcessor;
import com.huateng.hstserver.communicate.mina.comm.common.filter.protocolcodec.app.IAppObjectProcessor;
import com.huateng.hstserver.communicate.mina.comm.common.filter.protocolcodec.comm.ICommPackageProcessor;
import com.huateng.hstserver.communicate.mina.comm.common.filter.protocolcodec.security.IPackageSecurityProcessor;






public class PackageDecoder extends CumulativeProtocolDecoder  {
	
	
	IAppObjectProcessor objMsgProcessor = new DefaultNullAppObjectProcessor();
	
	ICommPackageProcessor commPackageProcessor;
	
	IPackageSecurityProcessor packageSecurityProcessor = null;
	
	//length type: "N"-number string, "BCD"-number string with bcd compress, "Bi"-binary data
//	private String lenFieldType = ProtocolCodecConst.lenTypeNumStr;
//	private int lenFieldLen = 4;
//	private boolean includeLenField = false;
//
//	public String getLenFieldType() {
//		return lenFieldType;
//	}
//
//	public void setLenFieldType(String lenFieldType) {
//		this.lenFieldType = lenFieldType;
//	}
//
//	public int getLenFieldLen() {
//		return lenFieldLen;
//	}
//
//	public void setLenFieldLen(int lenFieldLen) {
//		this.lenFieldLen = lenFieldLen;
//	}
//
//	public boolean isIncludeLenField() {
//		return includeLenField;
//	}
//
//	public void setIncludeLenField(boolean includeLenField) {
//		this.includeLenField = includeLenField;
//	}

	@Override
	protected boolean doDecode(IoSession Session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
		 
		in.mark();
		CommMessage commMessage = commPackageProcessor.commDecode(in);
		if (commMessage == null)
		{
			in.reset();
			return false;
		}
		
		// set message object for comm message
		if (objMsgProcessor != null)
		{
			commMessage.setMessageObject(objMsgProcessor.msg2obj(commMessage.getMessagbody()));
		}
		
		// set ip and port for comm message 
		InetSocketAddress address  = (InetSocketAddress) Session.getServiceAddress();
		commMessage.setRemoteip(address.getAddress().getHostAddress());
		commMessage.setRemoteport(address.getPort());
		
		if (packageSecurityProcessor != null)
		{
			boolean checkMac = packageSecurityProcessor.isValidMac(commMessage);
			if (checkMac == false)
			{
				throw new Exception("Mac check error");
			}
		}
		
		out.write(commMessage);
        return true;
	}
//	
//	private int getPackLenFromIoBuffer(IoBuffer in) throws Exception
//	{
//		int length = -1;
//		if (lenFieldType.equals(ProtocolCodecConst.lenTypeNumStr))
//		{
//			Charset set = Charset.forName("us-ascii");   
//	        CharsetDecoder dec = set.newDecoder();
//	         
//			String Slength;
//			try {
//				Slength = in.getString(lenFieldLen, dec);
//				length=Integer.valueOf(Slength);
//			} catch (CharacterCodingException e) {
//				
//				logger.error(e.getMessage());
//			}
//			return length;
//		}
//		else if (lenFieldType.equals(ProtocolCodecConst.lenTypeBCD) || lenFieldType.equals(ProtocolCodecConst.lenTypeBinary))
//		{
//			byte lenByte = 0;
//			length = 0;
//			for (int i = 0; i < lenFieldLen; i ++)
//			{
//				if (lenFieldType.equals(ProtocolCodecConst.lenTypeBinary))
//				{
//					length = length * 256;
//				}
//				else
//				{
//					length = length * 100;
//				}
//				 
//				lenByte = in.get();
//				if (lenFieldType.equals(ProtocolCodecConst.lenTypeBCD))
//				{
//					lenByte = (byte)(((lenByte & 0xF0) / 16) * 10 + (lenByte & 0x0F));
//				}
//				length =  length + lenByte;
//			}
//		}
//		return length;
//	}

	public IAppObjectProcessor getObjMsgProcessor() {
		return objMsgProcessor;
	}

	public void setObjMsgProcessor(IAppObjectProcessor objMsgProcessor) {
		this.objMsgProcessor = objMsgProcessor;
	}

	public ICommPackageProcessor getCommPackageProcessor() {
		return commPackageProcessor;
	}

	public void setCommPackageProcessor(ICommPackageProcessor commPackageProcessor) {
		this.commPackageProcessor = commPackageProcessor;
	}

	public IPackageSecurityProcessor getPackageSecurityProcessor() {
		return packageSecurityProcessor;
	}

	public void setPackageSecurityProcessor(
			IPackageSecurityProcessor packageSecurityProcessor) {
		this.packageSecurityProcessor = packageSecurityProcessor;
	}

}

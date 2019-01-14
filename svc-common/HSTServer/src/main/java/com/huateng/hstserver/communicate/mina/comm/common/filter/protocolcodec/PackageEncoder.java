package com.huateng.hstserver.communicate.mina.comm.common.filter.protocolcodec;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import com.huateng.hstserver.communicate.mina.comm.common.entity.CommMessage;
import com.huateng.hstserver.communicate.mina.comm.common.filter.protocolcodec.app.DefaultNullAppObjectProcessor;
import com.huateng.hstserver.communicate.mina.comm.common.filter.protocolcodec.app.IAppObjectProcessor;
import com.huateng.hstserver.communicate.mina.comm.common.filter.protocolcodec.comm.ICommPackageProcessor;
import com.huateng.hstserver.communicate.mina.comm.common.filter.protocolcodec.security.IPackageSecurityProcessor;




public class PackageEncoder extends ProtocolEncoderAdapter {

	
	IAppObjectProcessor objMsgProcessor = new DefaultNullAppObjectProcessor();
	
	ICommPackageProcessor commPackageProcessor;
	
	IPackageSecurityProcessor packageSecurityProcessor = null;

	public void encode(IoSession Session, Object message, ProtocolEncoderOutput out)
			throws Exception {
		
		CommMessage commMessage = (CommMessage)message;
		if (objMsgProcessor != null)
		{
			byte[] outBytes = objMsgProcessor.obj2msg(commMessage);
			if (outBytes != null)
			{
				commMessage.setMessagbody(outBytes);
				commMessage.setLength(outBytes.length);
			}
			
		}
		
		IoBuffer outBuffer = commPackageProcessor.commEncode(commMessage);
		
		if (packageSecurityProcessor != null)
		{
			packageSecurityProcessor.generateMac(commMessage);
			outBuffer.clear();
			outBuffer.put(commMessage.getOriginMessag());
		}

		outBuffer.flip();
		
		out.write(outBuffer);
	}

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

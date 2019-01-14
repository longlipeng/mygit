package com.huateng.hstserver.communicate.mina.comm.common.filter.protocolcodec.comm;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import com.huateng.hstserver.communicate.mina.comm.common.entity.CommMessage;

public interface ICommPackageProcessor {
	
	
	CommMessage commDecode(IoBuffer in) throws Exception;
	
	
	IoBuffer commEncode(CommMessage message) throws Exception;
}

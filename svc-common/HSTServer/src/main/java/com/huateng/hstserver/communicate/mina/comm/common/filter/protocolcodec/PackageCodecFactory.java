package com.huateng.hstserver.communicate.mina.comm.common.filter.protocolcodec;




import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

public class PackageCodecFactory implements ProtocolCodecFactory {
  	private final PackageEncoder encoder;
  	private final PackageDecoder decoder;
  

	public PackageCodecFactory(PackageEncoder encoder,PackageDecoder decoder) {
		this.encoder=encoder;
		this.decoder=decoder;
	}
	
  	
//	public PackageCodecFactory(
//			int lenFieldLen, String lenFieldType, boolean includeLenField) {
//		this();
//		encoder.setLenFieldType(lenFieldType);
//		encoder.setLenFieldLen(lenFieldLen);
//		encoder.setIncludeLenField(includeLenField);
//		
//		decoder.setLenFieldType(lenFieldType);
//		decoder.setLenFieldLen(lenFieldLen);
//		decoder.setIncludeLenField(includeLenField);
//	}

	

	public ProtocolDecoder getDecoder(IoSession arg0) throws Exception {

		return decoder;
	}

	public ProtocolEncoder getEncoder(IoSession arg0) throws Exception {

		return encoder;
	}

}

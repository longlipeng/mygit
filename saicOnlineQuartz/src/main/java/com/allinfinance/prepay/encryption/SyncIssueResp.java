package com.allinfinance.prepay.encryption;

import org.apache.commons.codec.binary.Hex;

import com.allinfinance.prepay.utils.BytesTransUtil;
import com.allinfinance.prepay.utils.ErrorCodeUtil;


public class SyncIssueResp extends CommonResp{

	
	private String digestData;

	public SyncIssueResp(byte[] resp) {
		super(resp);
		if("00".equals(errCode)){
			digestData = String.valueOf(Hex.encodeHex(BytesTransUtil.cutByteArray(resp, 14, resp.length - 14), false));
			respString = "arqcУ��ɹ�:[" + respHead + "|" + msgHeadResp + "|" + respCode + "|" + errCode + "|" + digestData + "]";
		}else{
			respString = "arqcУ��ɹ�:[" + respHead + "|" + msgHeadResp + "|" + respCode + "|" + errCode+ "],����ԭ��"+ErrorCodeUtil.getErrString4(errCode);
		}
	}

	public String getDigestData() {
		return digestData;
	}

}

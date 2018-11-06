package com.allinfinance.prepay.processor.ipos;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allinfinance.prepay.dto.Product;
import com.allinfinance.prepay.mapper.svc_mng.ProductMapper;
import com.allinfinance.prepay.message.BasicMessage;
import com.allinfinance.prepay.message.MessageSyncP027Req;
import com.allinfinance.prepay.message.MessageSyncP027Resp;
import com.allinfinance.prepay.processor.IProcessor;
import com.allinfinance.prepay.utils.ResponseCode;



/**
 * ��̨����Ʒ��ѯ
 * @author 
 *
 */

@Service
public class SyncP027Processor implements IProcessor {
	private static Logger logger = Logger.getLogger(SyncP027Processor.class);
	@Autowired
	private ProductMapper productMapper;
	
	public BasicMessage process(BasicMessage req) throws Exception {
		MessageSyncP027Req reqData = (MessageSyncP027Req)req;
		MessageSyncP027Resp resp =(MessageSyncP027Resp) reqData.createResp() ;
		try {
			List<Product> result = productMapper.selectProductAll(reqData.getISSUER_ID());
			resp.setList(result);
			resp.setRESP_CODE(ResponseCode.SUCCESS_IPOS);
			logger.info("��ѯ����Ʒ�ɹ�");
		} catch (Exception e) {
			e.printStackTrace();
			resp.setRESP_CODE(ResponseCode.SYSTEM_ERROR);
			logger.info("ϵͳ�쳣"+e);
		}
		
		return resp;

	}
	
}

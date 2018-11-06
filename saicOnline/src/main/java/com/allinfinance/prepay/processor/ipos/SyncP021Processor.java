package com.allinfinance.prepay.processor.ipos;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allinfinance.prepay.mapper.svc_mng.AccAccountInfoMapper;
import com.allinfinance.prepay.mapper.svc_mng.AccCardInfoMapper;
import com.allinfinance.prepay.mapper.svc_mng.EntityDeliveryMapper;
import com.allinfinance.prepay.mapper.svc_mng.EntityStockMapper;
import com.allinfinance.prepay.message.BasicMessage;
import com.allinfinance.prepay.message.MessageSyncP011Req;
import com.allinfinance.prepay.message.MessageSyncP021Req;
import com.allinfinance.prepay.message.MessageSyncP021Resp;
import com.allinfinance.prepay.model.AccAccountInfo;
import com.allinfinance.prepay.model.AccAccountInfoExample;
import com.allinfinance.prepay.model.AccCardInfo;
import com.allinfinance.prepay.model.AccCardInfoExample;
import com.allinfinance.prepay.model.CardHolder;
import com.allinfinance.prepay.model.CardHolderExample;
import com.allinfinance.prepay.model.Customer;
import com.allinfinance.prepay.model.CustomerExample;
import com.allinfinance.prepay.model.EntityDelivery;
import com.allinfinance.prepay.model.EntityDeliveryExample;
import com.allinfinance.prepay.model.EntityStock;
import com.allinfinance.prepay.model.EntityStockExample;
import com.allinfinance.prepay.processor.IProcessor;
import com.allinfinance.prepay.service.AccAccountInfoService;
import com.allinfinance.prepay.service.CardholderService;
import com.allinfinance.prepay.service.CustomerService;
import com.allinfinance.prepay.socket.SocketSend;
import com.allinfinance.prepay.utils.ParseToXML;
import com.allinfinance.prepay.utils.XmlDom;

@Service
public class SyncP021Processor implements IProcessor
{
	private Logger logger = Logger.getLogger(getClass());
	@Autowired
	private CardholderService cardholderService;
	@Autowired
	private AccCardInfoMapper accCardInfoMapper;
	@Autowired
	private EntityDeliveryMapper entityDeliveryMapper;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private AccAccountInfoMapper accAccountInfoMapper;
	@Autowired
	private AccAccountInfoService accAccountInfoService;
	@Autowired
	private  EntityStockMapper entityStockMapper;
	
	@Override
	public BasicMessage process(BasicMessage req) throws Exception {
		logger.info("卡查持卡人信息");
		MessageSyncP021Req reqData = (MessageSyncP021Req)req;
		MessageSyncP021Resp resp = (MessageSyncP021Resp) reqData.createResp();
		
	
		/*AccCardInfoExample accCardInfoExample = new AccCardInfoExample();
		accCardInfoExample.createCriteria().andCardNoEqualTo(reqData.getCARD_NO());
		int count = accCardInfoMapper.countByExample(accCardInfoExample);
		if(count==0){
			resp.setRESP_CODE("0014");
			return resp;
		}*/
		
		EntityStockExample entityStockExample = new EntityStockExample();
		entityStockExample.createCriteria().andEntityIdEqualTo(reqData.getISSUER_ID())
					.andCardNoEqualTo(reqData.getCARD_NO());
		List<EntityStock> selectByExample = entityStockMapper.selectByExample(entityStockExample);
		if(selectByExample == null || selectByExample.size() ==0){
			resp.setRESP_CODE("0014");
			return resp;
		}else{
			EntityStock entityStock = selectByExample.get(0);
			String stockState  = entityStock.getStockState().trim();
			String functionRoleId = entityStock.getFunctionRoleId().trim();
			
			if(stockState.equals("1") || !functionRoleId.equals("3")){
				resp.setRESP_CODE("0022");
				return resp;
			}
			
			if(!stockState.equals("3")){
				 resp.setRESP_CODE("0021");
				 return resp;
			}
		}
		
		
		boolean checkCardPwdResult = checkCardPwd(reqData);
		if(!checkCardPwdResult){
			resp.setRESP_CODE("0055");
			return resp;
		}
		
		
		
		CardHolder cardHolder = cardholderService.selectByCardNo(reqData.getCARD_NO());
		if(cardHolder == null){
			resp.setRESP_CODE("0111");
			return resp;
		}
		String idType = cardHolder.getIdType();
		String idNo = cardHolder.getIdNo().trim();
		if(idType != null && !idType.equals("") && idType.equals("1")){
			int len = idNo.length();
			StringBuffer  buffer = new StringBuffer();
			if(len == 15){
				buffer.append(idNo.substring(0,6));
				buffer.append("******");
				buffer.append(idNo.substring(12));
				idNo = buffer.toString();
			}else if(len == 18){
				buffer.append(idNo.substring(0,6));
				buffer.append("********");
				buffer.append(idNo.substring(14));
				idNo = buffer.toString();
			}
		}
		resp.setID_NO(idNo);
		resp.setID_TYPE(idType);
		resp.setFIRST_NAME(cardHolder.getFirstName());
		resp.setCARDHOLDER_MOBILE(cardHolder.getCardholderMobile());
		resp.setCARDHOLDER_GENDER(cardHolder.getCardholderGender());
		resp.setPLATE_NUMBER(cardHolder.getPlateNumber());
		resp.setDRIVER_LICENCE(cardHolder.getDriverLicence());
		

		EntityDeliveryExample EntityDeliveryExample = new EntityDeliveryExample();
		EntityDeliveryExample.createCriteria().andEntityIdEqualTo(cardHolder.getEntityId());
		List<EntityDelivery> entityDeliverys = entityDeliveryMapper.selectByExample(EntityDeliveryExample);
		if(entityDeliverys!=null&&entityDeliverys.size()!=0){
			for(EntityDelivery entityDelivery : entityDeliverys){
				if(entityDelivery.getDefaultFlag().trim().equals("1")){
					resp.setDELIVERY_ADDRESS(entityDelivery.getDeliveryAddress());
				}
			}
			if(resp.getDELIVERY_ADDRESS()==null||resp.getDELIVERY_ADDRESS().trim().equals("")){
				resp.setDELIVERY_ADDRESS(entityDeliverys.get(0).getDeliveryAddress());
			}
		}
		
		
		CustomerExample customerExample = new CustomerExample();
		customerExample.createCriteria().andEntityIdEqualTo(cardHolder.getEntityId());
		List<Customer> customers = customerService.selectByExample(customerExample);
		
		resp.setCUSTOMER_NAME(customers.get(0).getCustomerName());
		resp.setCUSTOMER_TPYE(customers.get(0).getCustomerType());
		
		

		AccAccountInfo accAccountInfo = accAccountInfoService.selectByCardNo(reqData.getCARD_NO());		
		resp.setBALANCE(String.valueOf(String.valueOf(Double.parseDouble(accAccountInfo.getAccBal())/100)));
		
		resp.setRESP_CODE("0000");
		
		return resp;
	}
	
	
	public boolean checkCardPwd(MessageSyncP021Req reqData){
		//不验证密码直接return true
	/*	Map<String, String> map=new LinkedHashMap<String, String>();
		map.put("TxnCode",reqData.getTXN_CODE());
		map.put("cardNo",reqData.getCARD_NO());
		map.put("channel",reqData.getCHANNEL());
		map.put("cardPwd",reqData.getCARD_PWD());
		String xml=ParseToXML.converterXML(map);
		String msg=SocketSend.SendToCore(xml);
		Map<String, String> Xmlmap=XmlDom.parse2(msg);
		String rspCode=Xmlmap.get("RESP_CODE");
		if(rspCode.trim().equals("00")){
			return true;
		}
		
		return false;*/
		return true ;
	}

	
	
}

package com.allinfinance.prepay.processor.ipos;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


















import com.allinfinance.prepay.dao.CommonsDAO;
import com.allinfinance.prepay.exception.BizServiceException;
import com.allinfinance.prepay.mapper.svc_mng.AccCardInfoMapper;
import com.allinfinance.prepay.message.BasicMessage;
import com.allinfinance.prepay.message.MessageSyncP001Req;
import com.allinfinance.prepay.message.MessageSyncP011Req;
import com.allinfinance.prepay.message.MessageSyncP011Resp;
import com.allinfinance.prepay.message.MessageSyncP021Req;
import com.allinfinance.prepay.message.MessageSyncP021Resp;
import com.allinfinance.prepay.model.AccCardInfo;
import com.allinfinance.prepay.model.AccCardInfoExample;
import com.allinfinance.prepay.model.CardHolder;
import com.allinfinance.prepay.model.Customer;
import com.allinfinance.prepay.processor.IProcessor;
import com.allinfinance.prepay.service.CardholderService;
import com.allinfinance.prepay.service.CustomerService;
import com.allinfinance.prepay.socket.SocketSend;
import com.allinfinance.prepay.utils.ParseToXML;
import com.allinfinance.prepay.utils.XmlDom;
import com.allinfinance.univer.seller.customer.CustomerDTO;

/**
 * 
 * @author joesun
 *
 */
@Service
public class SyncP011Processor implements IProcessor
{
	private Logger logger = Logger.getLogger(getClass());
	@Autowired
	private CardholderService cardholderService;
	@Autowired
	private AccCardInfoMapper accCardInfoMapper;
	
	@Override
	public BasicMessage process(BasicMessage req) throws Exception
	{
		
		logger.info("持卡人信息更新");
		MessageSyncP011Req reqData = (MessageSyncP011Req)req;
		MessageSyncP011Resp resp = (MessageSyncP011Resp) reqData.createResp();
		
		AccCardInfoExample accCardInfoExample = new AccCardInfoExample();
		accCardInfoExample.createCriteria().andCardNoEqualTo(reqData.getCARD_NO());
		List<AccCardInfo> accCardInfos = accCardInfoMapper.selectByExample(accCardInfoExample);
		if(accCardInfos==null||accCardInfos.size()==0){
			resp.setRESP_CODE("0015");//卡未找到
			return resp;
		}
//		if(accCardInfos.get(0).getCardStat().trim().equals("0")){
//			resp.setRESP_CODE("0016");//卡未激活
//			return resp;
//		}
		if(!accCardInfos.get(0).getLockStat().trim().equals("0")){
			resp.setRESP_CODE("0036");//卡已锁定
			return resp;
		}
		if(!accCardInfos.get(0).getLostStat().trim().equals("0")){
			resp.setRESP_CODE("0041");//卡已挂失
			return resp;
		}
		if(!accCardInfos.get(0).getChangeStat().trim().equals("0")){
			resp.setRESP_CODE("0044");//卡被注销
			return resp;
		}

		//校验持卡人信息
		CardHolder cardHolder = getCardholder(reqData);
		cardHolder.setDataState("1");
		boolean checkCardHolderResult = false;
		checkCardHolderResult= checkCardHolderInfo(cardHolder);
		if(!checkCardHolderResult){
			resp.setRESP_CODE("0043");//无效客户
			return resp;
		}
		
		//校验卡号与持卡人关联
		boolean checkCardResult = cardholderService.checkCardByIdNo(req);
		if(!checkCardResult){
			resp.setRESP_CODE("0017");//与原交易卡号不符
			return resp;
		}
		
		
		//TODO校验卡片交易密码
		boolean checkCardPwdResult = checkCardPwd(reqData);
		if(!checkCardPwdResult){
			resp.setRESP_CODE("0055");// 密码错
			return resp;
		}
		
		//更新持卡人相关信息
		cardholderService.updateCardholderByReq(req);
		resp.setRESP_CODE("00");//更新成功
		
		
		return resp;
	}
	
	/**
	 * 校验卡片交易密码
	 * @return
	 */
	public boolean checkCardPwd(MessageSyncP011Req reqData){
		Map<String, String> map=new LinkedHashMap<String, String>();
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
		
		return false;
	}
	
	/**
	 * 校验持卡人信息
	 * @return
	 * @throws BizServiceException 
	 */
	public boolean checkCardHolderInfo(CardHolder cardHolder) {
		int count = cardholderService.countBySomeExample(cardHolder);
		if(count>0){
			return true;
		}
		
		return false;
	}
	
	/**
	 * 
	 * @param req
	 * @return
	 */
	public CardHolder getCardholder(MessageSyncP011Req req){
		CardHolder cardHolder = new CardHolder();
		cardHolder.setIdNo(req.getID_NO());
		cardHolder.setIdType(req.getID_TYPE());
		cardHolder.setFirstName(req.getFIRST_NAME());
		return cardHolder;
	}
	
	
	
	

	
}

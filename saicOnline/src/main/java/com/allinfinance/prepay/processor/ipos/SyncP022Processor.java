package com.allinfinance.prepay.processor.ipos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allinfinance.prepay.mapper.svc_mng.CardHolderTmpMapper;
import com.allinfinance.prepay.message.BasicMessage;
import com.allinfinance.prepay.message.MessageSyncP022Req;
import com.allinfinance.prepay.message.MessageSyncP022Resp;
import com.allinfinance.prepay.model.CardHolderTmp;
import com.allinfinance.prepay.model.CardHolderTmpExample;
import com.allinfinance.prepay.processor.IProcessor;
import com.allinfinance.prepay.utils.CheckDataMethod;
import com.allinfinance.prepay.utils.IDCardCheck;
import com.allinfinance.prepay.utils.ResponseCode;
@Service
public class SyncP022Processor implements IProcessor{

	@Autowired
	private CardHolderTmpMapper cardHolderTmpMapper;
	@Autowired
	private CheckDataMethod checkDataMethod;
	@Override
	public BasicMessage process(BasicMessage req) throws Exception {
		// TODO Auto-generated method stub
		MessageSyncP022Req reqData = (MessageSyncP022Req)req;
		MessageSyncP022Resp resp =(MessageSyncP022Resp) reqData.createResp() ;
		CardHolderTmp cardHolderTmp=new CardHolderTmp();
		if(checkDataMethod.checkIssueId(reqData.getISSUER_ID().trim())){
			cardHolderTmp.setEntityId(reqData.getISSUER_ID().trim());
		}else{
			//û���ҵ�������
			//resp.setRESP_TEXT("����������");
			resp.setRESP_CODE(ResponseCode.ISSUEID_ERROR);
			return resp;
		}
		if(reqData.getID_TYPE()!=null&&!reqData.getID_TYPE().trim().equals("")){
			if(reqData.getID_TYPE().trim().equals("1")){
				cardHolderTmp.setIdType("1");
			}else if(reqData.getID_TYPE().trim().equals("2")){
				cardHolderTmp.setIdType("2");
			}else if(reqData.getID_TYPE().trim().equals("3")){
				cardHolderTmp.setIdType("3");
			}else{
				//û�����֤������
				resp.setRESP_CODE(ResponseCode.ID_TYPE_ERROR);
				//resp.setRESP_TEXT("֤����������");
				return resp;
			}
		}else{
			//resp.setRESP_TEXT("֤�����Ͳ���Ϊ�գ�");
			resp.setRESP_CODE(ResponseCode.ISNULL);
			return resp;
		}
		
		/*֤����*/
		if(reqData.getID_NO()!=null&&!reqData.getID_NO().trim().equals("")){
			if(reqData.getID_TYPE().trim().equals("1")){
				//���֤
				String errMsg=IDCardCheck.IDCardValidate(reqData.getID_NO().toUpperCase());
				if(errMsg==null||errMsg.equals("")){
					cardHolderTmp.setIdNo(reqData.getID_NO().toUpperCase());
				}else{
					//resp.setRESP_TEXT("֤��������");
					resp.setRESP_CODE(ResponseCode.ID_NO_ERROR);
					return resp;
				}
				
			}else if(reqData.getID_TYPE().equals("2")){
				//����
				boolean isTrue = reqData.getID_NO().trim().matches("([0-9a-zA-Z]*)");
	    		 if(isTrue==true){
	    			 cardHolderTmp.setIdNo(reqData.getID_NO().trim());
	    		 }else{
	    			 //resp.setRESP_TEXT("֤��������");
	    			 resp.setRESP_CODE(ResponseCode.ID_NO_ERROR);
	    			 return resp;
	    		 }
			}else if(reqData.getID_TYPE().trim().equals("3")){
				//����
				cardHolderTmp.setIdNo(reqData.getID_NO().trim());
			}
		}else{
			//resp.setRESP_TEXT("֤���Ų���Ϊ�գ�");
			resp.setRESP_CODE(ResponseCode.ISNULL);
			return resp;
		}
		CardHolderTmpExample ex=new CardHolderTmpExample();
		ex.createCriteria().andIdNoEqualTo(cardHolderTmp.getIdNo())
			.andIdTypeEqualTo(cardHolderTmp.getIdType()).andDataStateEqualTo("1");
		List<CardHolderTmp>  list=cardHolderTmpMapper.selectByExample(ex);
		if(list.size()==0){
			resp.setRESP_CODE(ResponseCode.CARDHOLDER_INFO_ISNULL);
			return resp;
		}else{
			resp.setRESP_CODE(ResponseCode.SUCCESS);
			resp.setID_NO(list.get(0).getIdNo());
			resp.setID_TYPE(list.get(0).getIdType());
			resp.setTXN_TYPE(reqData.getTXN_TYPE());
			resp.setFIRST_NAME(list.get(0).getFirstName());
			resp.setCARDHOLDER_MOBILE(list.get(0).getCardholderMobile());
			resp.setCARDHOLDER_GENDER(list.get(0).getCardholderGender());
			resp.setDELIVERY_ADDRESS(list.get(0).getMailingAddress());
			resp.setPLATE_NUMBER(list.get(0).getPlateNumber());
			resp.setDRIVER_LICENCE(list.get(0).getDriverLicence());
			resp.setACTIVITY_SECTOR(list.get(0).getActivitySector());
			resp.setCUSTOMER_NAME(list.get(0).getCustomerName());
			resp.setCUSTOMER_TYPE(list.get(0).getCustomerType());
			resp.setREMARK(list.get(0).getCardholderComment());
			return resp;
		}
		
		
	}

}

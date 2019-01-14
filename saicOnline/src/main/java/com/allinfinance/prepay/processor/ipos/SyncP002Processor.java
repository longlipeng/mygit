package com.allinfinance.prepay.processor.ipos;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allinfinance.prepay.mapper.svc_mng.CardHolderTmpMapper;
import com.allinfinance.prepay.message.BasicMessage;
import com.allinfinance.prepay.message.MessageSyncP002Req;
import com.allinfinance.prepay.message.MessageSyncP002Resp;
import com.allinfinance.prepay.model.CardHolderTmp;
import com.allinfinance.prepay.model.CardHolderTmpExample;
import com.allinfinance.prepay.processor.IProcessor;
import com.allinfinance.prepay.utils.CheckDataMethod;
import com.allinfinance.prepay.utils.Config;
import com.allinfinance.prepay.utils.DateUtil;
import com.allinfinance.prepay.utils.IDCardCheck;
import com.allinfinance.prepay.utils.ResponseCode;

@Service
public class SyncP002Processor implements IProcessor{
	
	@Autowired
	private CardHolderTmpMapper cardHolderTmpMapper;
	
	@Autowired
	private CheckDataMethod checkDataMethod;

	private Logger logger = Logger.getLogger(SyncP002Processor.class);
	@Override
	public BasicMessage process(BasicMessage req) throws Exception {
		// TODO Auto-generated method stub
		MessageSyncP002Req reqData = (MessageSyncP002Req)req;
		MessageSyncP002Resp resp =(MessageSyncP002Resp) reqData.createResp() ;
		CardHolderTmp cardHolderTmp=new CardHolderTmp();
		if(reqData.getCUSTOMER_TPYE()!=null&&!reqData.getCUSTOMER_TPYE().equals("")){
			if(!reqData.getCUSTOMER_TPYE().equals("0")){
				resp.setRESP_CODE(ResponseCode.ORDER_TYPE_ERROR);
				return resp;
			}
		}
		String channel=null;//������
		String branch_Id=null;//����
		
		
		if(reqData.getCHANNEL()!=null&&!reqData.getCHANNEL().trim().equals("")){
			channel=reqData.getCHANNEL().trim();
		}else{
			resp.setRESP_CODE(ResponseCode.ISNULL);
			return resp;
		}
		if(reqData.getBRANCH_ID()!=null&&!reqData.getBRANCH_ID().trim().equals("")){
			branch_Id=reqData.getBRANCH_ID().trim();
		}else{
			resp.setRESP_CODE(ResponseCode.ISNULL);
			return resp;
		}
		
		
		if(reqData.getISSUER_ID()!=null&&!reqData.getISSUER_ID().trim().equals("")){
			if(checkDataMethod.checkIssueId(reqData.getISSUER_ID().trim())){
				cardHolderTmp.setEntityId(reqData.getISSUER_ID().trim());
			}else{
				//û���ҵ�������
				//resp.setRESP_TEXT("����������");
				resp.setRESP_CODE(ResponseCode.ISSUEID_ERROR);
				return resp;
			}
		}else{
			//resp.setRESP_TEXT("�����Ų���Ϊ�գ�");
			resp.setRESP_CODE(ResponseCode.ISNULL);
			return resp;
		}
		cardHolderTmp.setCreateUser(Config.getUserId());
		cardHolderTmp.setCreateTime(DateUtil.getCurrentTime());
		cardHolderTmp.setModifyTime(DateUtil.getCurrentTime());
		cardHolderTmp.setModifyUser(Config.getUserId());
		/*����*/
		if(reqData.getFIRST_NAME()!=null&&!reqData.getFIRST_NAME().trim().equals("")){
			cardHolderTmp.setFirstName(reqData.getFIRST_NAME().trim());
		}else{
			//resp.setRESP_TEXT("�ֿ�����������Ϊ�գ�");
			resp.setRESP_CODE(ResponseCode.ISNULL);
			return resp;
		}
		
		
		
		/*�绰*/
		if(reqData.getCARDHOLDER_MOBILE()!=null&&!reqData.getCARDHOLDER_MOBILE().trim().equals("")){
			boolean isTrue=reqData.getCARDHOLDER_MOBILE().trim().matches("([1][3,4,5,8,7][0-9]{9})");
			if(isTrue){
				cardHolderTmp.setCardholderMobile(reqData.getCARDHOLDER_MOBILE().trim());
			}else{
				//�ֻ������ʽ����ȷ
				resp.setRESP_CODE(ResponseCode.MOBILE_ERROR);
				return resp;
			}
			
		}else{
			//resp.setRESP_TEXT("�ֻ����벻��Ϊ�գ�");
			resp.setRESP_CODE(ResponseCode.ISNULL);
			return resp;
		}
		/*֤������*/
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
		/*�Ա�*/
		if(reqData.getCARDHOLDER_GENDER()!=null&&!reqData.getCARDHOLDER_GENDER().trim().equals("")){
			if(reqData.getCARDHOLDER_GENDER().trim().endsWith("1")||reqData.getCARDHOLDER_GENDER().trim().endsWith("2")){
				cardHolderTmp.setCardholderGender(reqData.getCARDHOLDER_GENDER().trim());
			}else{
				resp.setRESP_CODE(ResponseCode.GENDER_ERROR);
				return resp;
			}
		}else{
			//����Ա�Ϊ�գ��ж�֤������
			if(reqData.getID_TYPE().trim().equals("1")){
				//18λ���֤
				if(reqData.getID_NO().trim().length()==18){
					//��ȡ���֤�ĵ�17λ�����Ա�����Ϊ�У�ż��ΪŮ
					int num=Integer.parseInt(reqData.getID_NO().trim().substring(16,17));
					if(num%2==0){
						//ż��
						cardHolderTmp.setCardholderGender("2");
					}else{
						cardHolderTmp.setCardholderGender("1");
					}
				}
				//15λ���֤
				if(reqData.getID_NO().length()==15){
					//��ȡ���֤�ĵ�15λ�����Ա�����Ϊ�У�ż��ΪŮ
					int num=Integer.parseInt(reqData.getID_NO().trim().substring(14,15));
					if(num%2==0){
						//ż��
						cardHolderTmp.setCardholderGender("2");
					}else{
						cardHolderTmp.setCardholderGender("1");
					}
				}
				
			}else{
				//���ش�����д�Ա�
				//resp.setRESP_TEXT("����д�Ա�");
				resp.setRESP_CODE(ResponseCode.ISNULL);
				return resp;
			}
		}
		
		/*��ν*/
		if(cardHolderTmp.getCardholderGender().trim().equals("1")){
			cardHolderTmp.setCardholderSalutation("1");
		}
		else if(cardHolderTmp.getCardholderGender().trim().equals("2")){
			cardHolderTmp.setCardholderSalutation("3");//Ůʿ
		}
		/*��ַ*/
		
		cardHolderTmp.setMailingAddress(reqData.getDELIVERY_ADDRESS().trim());
//		/*����*/
//		cardholderDTO.setCardholderBirthday("");
		/*���ƺ�*/
		cardHolderTmp.setPlateNumber(reqData.getPLATE_NUMBER().trim());
		/*���ܺ�*/
		//cardholderDTO.setVId();
		/*��ʻ֤��*/
		cardHolderTmp.setDriverLicence(reqData.getDRIVER_LICENCE().trim());
		/*�ֿ��˷���*/
		cardHolderTmp.setCardholderSegment("0");
		
		//�ֿ��˱�ע
		cardHolderTmp.setCardholderComment(reqData.getREMARK().trim());
		/*�ֿ�����ϢĬ���ֶ�*/
		cardHolderTmp.setCardholderFunction("1");
		cardHolderTmp.setCardholderState("1");
		cardHolderTmp.setChannel(channel);//������
		cardHolderTmp.setTermId(reqData.getTERM_ID());//�ն˺�
		cardHolderTmp.setBranchId(branch_Id);//���������
		cardHolderTmp.setMchntCd(reqData.getMCHNT_CD());//�̻���
		cardHolderTmp.setDataState("1");
		CardHolderTmpExample ex=new CardHolderTmpExample();
		ex.createCriteria().andIdNoEqualTo(cardHolderTmp.getIdNo())
			.andIdTypeEqualTo(cardHolderTmp.getIdType()).andDataStateEqualTo("1");
		try {
			List<CardHolderTmp>  list=cardHolderTmpMapper.selectByExample(ex);
			if(list.size()>0){
				cardHolderTmpMapper.updateByExampleSelective(cardHolderTmp, ex);
			}else{
				cardHolderTmpMapper.insertSelective(cardHolderTmp);
			}
		
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("["+reqData.getRESPSEQNO()+"]"+"�������޸�ʧ�ܣ�");
			resp.setRESP_CODE(ResponseCode.SYSTEM_ERROR);
			return resp;
		}
		//�ɹ�����
		resp.setRESP_CODE(ResponseCode.SUCCESS);
		return resp;
	}
	
	

}

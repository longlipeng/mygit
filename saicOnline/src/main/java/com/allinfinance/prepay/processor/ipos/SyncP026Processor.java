package com.allinfinance.prepay.processor.ipos;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.allinfinance.prepay.dao.BatchInfoDAO;
import com.allinfinance.prepay.dao.CommonDAOImpl;
import com.allinfinance.prepay.dao.CommonsDAO;
import com.allinfinance.prepay.exception.BizServiceException;
import com.allinfinance.prepay.mapper.svc_mng.EntityDictInfoMapper;
import com.allinfinance.prepay.mapper.svc_mng.EntityStockMapper;
import com.allinfinance.prepay.mapper.svc_mng.ProductMapper;
import com.allinfinance.prepay.message.BasicMessage;
import com.allinfinance.prepay.message.MessageSyncP026Req;
import com.allinfinance.prepay.message.MessageSyncP026Resp;
import com.allinfinance.prepay.model.EntityDictInfoExample;
import com.allinfinance.prepay.model.Product;
import com.allinfinance.prepay.model.ProductExample;
import com.allinfinance.prepay.model.SaicOnlineBatchInfo;
import com.allinfinance.prepay.model.SaicOnlineBatchInfoDTO;
import com.allinfinance.prepay.processor.IProcessor;
import com.allinfinance.prepay.utils.DataBaseConstant;
import com.allinfinance.prepay.utils.DateUtil;
import com.allinfinance.prepay.utils.IDCardCheck;
import com.allinfinance.prepay.utils.ResponseCode;
import com.allinfinance.prepay.utils.StringUtil;
import com.allinfinance.univer.seller.order.dto.OrderReadyDTO;
/**
 * ������̨������Ϣ¼��
 * @author 
 *
 */
@Service
public class SyncP026Processor implements IProcessor {
	private static Logger logger = Logger.getLogger(SyncP026Processor.class);
	@Autowired
	private EntityDictInfoMapper entityDictInfoMapper;
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private BatchInfoDAO batchInfoDAO;
	@Autowired
	private CommonsDAO commonsDAO;
	@Autowired
	private EntityStockMapper entityStockMapper;
	@Override
	public BasicMessage process(BasicMessage req) throws Exception {
		
		MessageSyncP026Req reqData = (MessageSyncP026Req)req;
		MessageSyncP026Resp resp =(MessageSyncP026Resp) reqData.createResp() ;
		
		
		if(reqData.getSaicOnlineBatchInfoList()==null||reqData.getSaicOnlineBatchInfoList().size()==0){
			resp.setRESP_CODE(ResponseCode.ISNULL);
			logger.error("����Ϊ�ա���["+reqData.getRESPSEQNO()+"]");
			return resp;
		}
		
		
		String batchNo = commonsDAO.getNextValueOfSequence("TBL_SAICONLINE_BATCH_NO");
		
		List<SaicOnlineBatchInfo> list=new ArrayList<SaicOnlineBatchInfo>();
		for (SaicOnlineBatchInfoDTO dto:reqData.getSaicOnlineBatchInfoList()) {
			SaicOnlineBatchInfo info=new SaicOnlineBatchInfo();
			info.setEntityId(reqData.getISSUER_ID());
			if(StringUtil.isNotEmpty(dto.getUser_id())){
				if(dto.getUser_id().length()>20){
					resp.setRESP_CODE(ResponseCode.ISNULL);
					logger.error("��ԱIDΪ�ա���["+reqData.getRESPSEQNO()+"]");
					return resp;
				}
				info.setLeaguerId(dto.getUser_id());
			}else{
				resp.setRESP_CODE(ResponseCode.ISNULL);
				logger.error("��ԱIDΪ�ա���["+reqData.getRESPSEQNO()+"]");
				return resp;
			}
			
			if(StringUtil.isNotEmpty(dto.getUser_name())){
				info.setLeaguerName(dto.getUser_name());
			}else{
				resp.setRESP_CODE(ResponseCode.ISNULL);
				logger.error("��Ա����Ϊ�ա���["+reqData.getRESPSEQNO()+"]");
				return resp;
			}
			
			if(StringUtil.isNotEmpty(dto.getMobile())){
				info.setPhoneNumber(dto.getMobile());
			}else{
				resp.setRESP_CODE(ResponseCode.ISNULL);
				logger.error("��Ա�ֻ���Ϊ�ա���["+reqData.getRESPSEQNO()+"]");
				return resp;
			}
			
			if(StringUtil.isNotEmpty(dto.getEmail())){
				info.setCardholderEmail(dto.getEmail());
			}

			
			if(StringUtils.isNotEmpty(dto.getId_type())){
				if(dto.getId_type().trim().equals("1")||dto.getId_type().equals("2")
						||dto.getId_type().trim().equals("3")){
					info.setIdType(dto.getId_type());
				}else{
					//û�����֤������
					resp.setRESP_CODE(ResponseCode.ID_TYPE_ERROR);
					//resp.setRESP_TEXT("֤����������");
					logger.error("֤����������["+reqData.getRESPSEQNO()+"]");
					return resp;
				}
			}else{
				//resp.setRESP_TEXT("֤�����Ͳ���Ϊ�գ�");
				resp.setRESP_CODE(ResponseCode.ISNULL);
				logger.error("֤�����Ͳ���Ϊ�գ�["+reqData.getRESPSEQNO()+"]");
				return resp;
			}
			
			if(StringUtil.isNotEmpty(dto.getId_no())){
				if(dto.getId_type().trim().equals("1")){
					//���֤
					String errMsg=IDCardCheck.IDCardValidate(dto.getId_no().toUpperCase());
					if(errMsg==null||errMsg.equals("")){
						info.setIdNo(dto.getId_no());
					}else{
						//resp.setRESP_TEXT("֤��������");
						resp.setRESP_CODE(ResponseCode.ID_NO_ERROR);
						logger.error("֤�����������֤����["+reqData.getRESPSEQNO()+"]");
						return resp;
					}
					
				}else if(dto.getId_type().equals("2")){
					//����
					boolean isTrue = dto.getId_no().trim().matches("([0-9a-zA-Z]*)");
		    		 if(isTrue==true){
		    			 info.setIdNo(dto.getId_no());
		    		 }else{
		    			 //resp.setRESP_TEXT("֤��������");
		    			 resp.setRESP_CODE(ResponseCode.ID_NO_ERROR);
		    			 logger.error("֤�������󣨻��գ���["+reqData.getRESPSEQNO()+"]");
		    			 return resp;
		    		 }
				}else if(dto.getId_type().trim().equals("3")){
					//����
					 info.setIdNo(dto.getId_no());
				}
				
			}else{
				resp.setRESP_CODE(ResponseCode.ISNULL);
				logger.error("֤����Ϊ�ա���["+reqData.getRESPSEQNO()+"]");
				return resp;
			}
			
			
			/*�ͻ�֤��ʧЧ����*/
			if(StringUtil.isEmpty(dto.getId_validity())){
				resp.setRESP_CODE(ResponseCode.ISNULL);
				logger.error("֤��ʧЧ����Ϊ�գ�["+reqData.getRESPSEQNO()+"]");
				return resp;
			}else if(DateUtil.isValidDate(dto.getId_validity())==false){
				resp.setRESP_CODE(ResponseCode.VALIDITY_ERROR);
				logger.error("���ڸ�ʽ��["+reqData.getRESPSEQNO()+"]");
				return resp;
			}else{
				info.setValidity(dto.getId_validity());
			}
			
			/*����*/
			if(StringUtil.isEmpty(dto.getBirthday())){
				resp.setRESP_CODE(ResponseCode.ISNULL);
				logger.error("����Ϊ�գ�["+reqData.getRESPSEQNO()+"]");
				return resp;
			}else if(DateUtil.isValidDate(dto.getBirthday())==false){
				resp.setRESP_CODE(ResponseCode.VALIDITY_ERROR);
				logger.error("�������ڸ�ʽ��["+reqData.getRESPSEQNO()+"]");
				return resp;
			}else{
				info.setCardholderBirthday(dto.getBirthday());
			}
			
			//�Ա�
			if(StringUtil.isNotEmpty(dto.getGender())){
				if(dto.getGender().trim().endsWith("1")||dto.getGender().trim().endsWith("2")){
					info.setGender(dto.getGender());
				}else{
					resp.setRESP_CODE(ResponseCode.GENDER_ERROR);
					logger.error("�Ա�����["+reqData.getRESPSEQNO()+"]");
					return resp;
				}
			}else{
				resp.setRESP_CODE(ResponseCode.ISNULL);
				logger.error("�Ա�Ϊ�գ�["+reqData.getRESPSEQNO()+"]");
				return resp;
			}
			
			//ְҵ
			if(StringUtil.isNotEmpty(dto.getOccupation())){
				EntityDictInfoExample ex=new EntityDictInfoExample();
				//�����ֶ�type 145 �ͻ�ְҵ
				ex.createCriteria().andEntityIdEqualTo(reqData.getISSUER_ID())
					.andDictStateEqualTo("1")
					.andDictTypeCodeEqualTo("145")
					.andDictCodeEqualTo(dto.getOccupation());
				int count=entityDictInfoMapper.countByExample(ex);
				if(count==0){
					resp.setRESP_CODE(ResponseCode.OCCUPATION_ERROR);
					logger.error("ְҵ����["+reqData.getRESPSEQNO()+"]");
					return resp;
				}
				
				info.setAwareness(dto.getOccupation());
			}else{
				resp.setRESP_CODE(ResponseCode.ISNULL);
				logger.error("ְҵΪ�գ�["+reqData.getRESPSEQNO()+"]");
				return resp;
			}
			
			//����
			if(StringUtil.isNotEmpty(dto.getNationality())){
				
				info.setCountry(dto.getNationality());
			}else{
				resp.setRESP_CODE(ResponseCode.ISNULL);
				logger.error("����Ϊ�գ�["+reqData.getRESPSEQNO()+"]");
				return resp;
			}
			//��ַ
			if(StringUtil.isNotEmpty(dto.getAddress())){
				
				info.setDeliveryAddress(dto.getAddress());
			}else{
				resp.setRESP_CODE(ResponseCode.ISNULL);
				logger.error("��ַΪ�գ�["+reqData.getRESPSEQNO()+"]");
				return resp;
			}
			
			//�������
			if(StringUtil.isNotEmpty(reqData.getArea_code())){
				
				info.setCustArea(reqData.getArea_code());
			}else{
				resp.setRESP_CODE(ResponseCode.ISNULL);
				logger.error("�������Ϊ�գ�["+reqData.getRESPSEQNO()+"]");
				return resp;
			}
			
			//��Ʒ
			if(StringUtil.isNotEmpty(dto.getCard_product())){
				ProductExample productExample=new ProductExample();
				productExample.createCriteria().andProductIdEqualTo(dto.getCard_product())
					.andDataStateEqualTo("1").andEntityIdEqualTo(reqData.getISSUER_ID());
				List<Product> products = productMapper.selectByExample(productExample);
				if(products==null||products.size()==0){
					resp.setRESP_CODE(ResponseCode.PRODUCT_ERROR);
					logger.error("����ƷΪ�գ�["+reqData.getRESPSEQNO()+"]");
					return resp;
				}
				
				if(products.get(0).getOnymousStat().endsWith("2")){
					resp.setRESP_CODE(ResponseCode.PRODUCT_ERROR);
					logger.error("�ýӿڲ������۲���������Ʒ��["+reqData.getRESPSEQNO()+"]");
					return resp;
				}
				if(products.get(0).getOnymousStat().endsWith("1")){
					resp.setRESP_CODE(ResponseCode.PRODUCT_ERROR);
					logger.error("�ýӿڲ������۸��Ի�����Ʒ��["+reqData.getRESPSEQNO()+"]");
					return resp;
				}
				if(products.get(0).getOnymousStat().endsWith("3")&&!products.get(0).getCardType().endsWith("3")){
					//����ѯ  ������������ǹ̶�  ���ֵ0
					OrderReadyDTO orderReadyDTO=new OrderReadyDTO();
					orderReadyDTO.setProductId(dto.getCard_product());
					orderReadyDTO.setFaceValue("0");
					orderReadyDTO.setFaceValueType("1");
					orderReadyDTO.setDefaultEntityId(reqData.getISSUER_ID());
					List<HashMap<String, Object>> cardNum=entityStockMapper.selectStockByProductId(orderReadyDTO);
					if(cardNum==null||cardNum.size()==0){
						resp.setRESP_CODE(ResponseCode.CARDNO_ISNULL);
						logger.error("����Ʒ��治�㣡["+reqData.getRESPSEQNO()+"]");
						return resp;
					}
					int num=(Integer) cardNum.get(0).get("stockNumber");
					if(num==0){
						resp.setRESP_CODE(ResponseCode.CARDNO_ISNULL);
						logger.error("����Ʒ��治�㣡["+reqData.getRESPSEQNO()+"]");
						return resp;
					}
				}
				info.setProductId(dto.getCard_product());
			}else{
				resp.setRESP_CODE(ResponseCode.ISNULL);
				logger.error("��ƷΪ�գ�["+reqData.getRESPSEQNO()+"]");
				return resp;
			}
			
			info.setEntityId(reqData.getISSUER_ID());
			
			info.setCardholderEmail(dto.getEmail());
			
			
			String batchId = commonsDAO.getNextValueOfSequence("TBL_SAICONLINE_BATCH_INFO");
			info.setBatchId(batchId);
			info.setBatchNo(batchNo);
			//����״̬ 00��������   01������   02����ɹ� 03����ʧ��
			info.setBatchStat("00");
			//�������� 01 �ۿ�
			info.setBatchType("01");
			info.setDataState(DataBaseConstant.DATA_TYPE_YES);
			info.setCreateTime(DateUtil.getCurrentTime());
			info.setUpdateTime(DateUtil.getCurrentTime());
			list.add(info);
		}
				
		batchInfoDAO.batchInsertInfo("saicBatchInsert", list);
		resp.setRESP_CODE(ResponseCode.SUCCESS_IPOS);
		return resp;
		
	}
	
	
	
	
		
	

}

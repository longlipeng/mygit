package com.allinfinance.prepay.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allinfinance.prepay.dao.CommonsDAO;
import com.allinfinance.prepay.exception.BizServiceException;
import com.allinfinance.prepay.mapper.svc_mng.AccCardInfoMapper;
import com.allinfinance.prepay.mapper.svc_mng.CardHolderMapper;
import com.allinfinance.prepay.mapper.svc_mng.CustomerMapper;
import com.allinfinance.prepay.mapper.svc_mng.EntityDeliveryMapper;
import com.allinfinance.prepay.model.AccCardInfo;
import com.allinfinance.prepay.model.AccCardInfoExample;
import com.allinfinance.prepay.model.CardHolder;
import com.allinfinance.prepay.model.CardHolderExample;
import com.allinfinance.prepay.model.Customer;
import com.allinfinance.prepay.model.CustomerExample;
import com.allinfinance.prepay.model.EntityDelivery;
import com.allinfinance.prepay.model.EntityDeliveryExample;
import com.allinfinance.prepay.utils.Config;
import com.allinfinance.prepay.utils.DateUtil;
import com.allinfinance.prepay.utils.ReflectionUtil;
import com.allinfinance.univer.seller.cardholder.dto.CardholderDTO;

@Service
public class CardholderServiceImpl implements CardholderService {

	@Autowired
	private CommonsDAO commonsDAO;
	@Autowired
	private CardHolderMapper cardHolderMapper;
	@Autowired
	private CustomerMapper customerMapper;
	@Autowired
	private EntityDeliveryMapper entityDeliveryMapper;
	@Autowired
	private AccCardInfoMapper accCardInfoMapper;
	
	
	@Override
	public CardHolder insert(CardholderDTO cardholderDTO) throws BizServiceException {
		
		CardHolder cardholder = new CardHolder();
		ReflectionUtil.copyProperties(cardholderDTO, cardholder);
		cardholder.setCardholderId(commonsDAO
				.getNextValueOfSequence("TB_CARDHOLDER"));
		cardholder.setCreateUser(cardholderDTO.getLoginUserId());
		cardholder.setCreateTime(DateUtil.getCurrentTime());
		cardholder.setModifyUser(cardholderDTO.getLoginUserId());
		cardholder.setModifyTime(DateUtil.getCurrentTime());
		cardholder.setDataState("1");
		
		cardHolderMapper.insert(cardholder);
		return cardholder;
	}
	
	@Override
	public int countBySomeExample(CardHolder cardholder) {
		CardHolderExample cardHolderExample = new CardHolderExample();
		cardHolderExample.createCriteria().andIdNoEqualTo(cardholder.getIdNo())
		                                  .andIdTypeEqualTo(cardholder.getIdType())
		                                  .andFirstNameEqualTo(cardholder.getFirstName())
		                                  .andDataStateEqualTo(cardholder.getDataState());
		int count = cardHolderMapper.countByExample(cardHolderExample);
		
		return count;
	}
	
	
	/*
	CARDHOLDER_MOBILE	手机号
	CARDHOLDER_GENDER	性别
	DELIVERY_ADDRESS	邮寄地址
	PLATE_NUMBER	车牌号
	DRIVER_LICENCE	驾照号
	ACTIVITY_SECTOR	职业
	CUSTOMER_TPYE	购卡人类型
	CUSTOMER_NAME	购卡人所属企业名称
	*/
	/*@Override
	public void updateCardholderByReq(BasicMessage basicReq)
			throws BizServiceException {
		MessageSyncP011Req req = (MessageSyncP011Req)basicReq;
		
		*//*****更新持卡人信息*****//*
		
		CARDHOLDER_MOBILE	手机号
		CARDHOLDER_GENDER	性别
		PLATE_NUMBER	车牌号
		DRIVER_LICENCE	驾照号
		 
		CardHolderExample cardHolderExample = new CardHolderExample();
		cardHolderExample.createCriteria().andIdNoEqualTo(req.getID_NO())
		                                  .andIdTypeEqualTo(req.getID_TYPE())
		                                  .andFirstNameEqualTo(req.getFIRST_NAME())
		                                  .andDataStateEqualTo("1");
		CardHolder record = new CardHolder();
		record.setCardholderMobile(req.getCARDHOLDER_MOBILE());
		record.setCardholderGender(req.getCARDHOLDER_GENDER());
		record.setPlateNumber(req.getPLATE_NUMBER());
		record.setDriverLicence(req.getDRIVER_LICENCE());
		cardHolderMapper.updateByExampleSelective(record, cardHolderExample);
		
		*//*****更新邮寄地址*****//*
		CustomerExample customerExample = new CustomerExample();
		customerExample.createCriteria().andCorpCredIdEqualTo(req.getID_NO());
		List<Customer> customers = customerMapper.selectByExample(customerExample);
		String entityId = customers.get(0).getEntityId();
		
		if(req.getDELIVERY_ADDRESS()!=null&&!req.getDELIVERY_ADDRESS().equals("")){
			EntityDeliveryExample entityDeliveryExample = new EntityDeliveryExample();
			entityDeliveryExample.createCriteria().andEntityIdEqualTo(entityId);
			List<EntityDelivery> entityDeliverys = entityDeliveryMapper.selectByExample(entityDeliveryExample);
			if(entityDeliverys == null||entityDeliverys.size()==0){
				EntityDelivery entityDelivery = new EntityDelivery();
				String id = commonsDAO.getNextValueOfSequence("TB_ENTITY_DELIVERY");
				entityDelivery.setDeliveryId(id);
				entityDelivery.setDataState("1");
				entityDelivery.setEntityId(entityId);
				entityDelivery.setCreateTime(DateUtil.getCurrentTime());
				entityDelivery.setModifyTime(DateUtil.getCurrentTime());
				entityDelivery.setDeliveryAddress(req.getDELIVERY_ADDRESS());
				entityDelivery.setDeliveryName(req.getCUSTOMER_NAME());
				entityDelivery.setCreateUser(Config.getUserId());
				entityDelivery.setModifyUser(Config.getUserId());
				entityDelivery.setDefaultFlag("1");
				entityDelivery.setDeliveryState("1");
				
				entityDeliveryMapper.insert(entityDelivery);
			}else{
				boolean isUpdate = false;
				for(EntityDelivery entityDelivery : entityDeliverys){
					if(entityDelivery.getDefaultFlag().trim().equals("1")){
						entityDelivery.setDeliveryAddress(req.getDELIVERY_ADDRESS());
						EntityDeliveryExample entityDeliveryExampleUpdate = new EntityDeliveryExample();
						entityDeliveryExampleUpdate.createCriteria().andDeliveryIdEqualTo(entityDelivery.getDeliveryId());
						entityDeliveryMapper.updateByExampleSelective(entityDelivery, entityDeliveryExampleUpdate);
						isUpdate = true;
					}
				}
				if(!isUpdate){
					EntityDelivery entityDelivery = entityDeliverys.get(0);
					entityDelivery.setDeliveryAddress(req.getDELIVERY_ADDRESS());
					EntityDeliveryExample entityDeliveryExampleUpdate = new EntityDeliveryExample();
					entityDeliveryExampleUpdate.createCriteria().andDeliveryIdEqualTo(entityDelivery.getDeliveryId());
					entityDeliveryMapper.updateByExampleSelective(entityDelivery, entityDeliveryExampleUpdate);
				}
			}
		}
		
		
		*//*******更新客户信息
		ACTIVITY_SECTOR	职业
		CUSTOMER_TPYE	购卡人类型
		CUSTOMER_NAME	购卡人所属企业名称
		*******//*
		Customer customer = customers.get(0);
		customer.setActivitySector(req.getACTIVITY_SECTOR());
		customer.setCustomerType(req.getCUSTOMER_TPYE());
		customer.setCustomerName(req.getCUSTOMER_NAME());
		CustomerExample customerExampleUpdate = new CustomerExample();
		customerExampleUpdate.createCriteria().andEntityIdEqualTo(customer.getEntityId());
		customerMapper.updateByExampleSelective(customer, customerExampleUpdate);
		
	}

	@Override
	public boolean checkCardByIdNo(BasicMessage basicReq) throws BizServiceException {
		MessageSyncP011Req req = (MessageSyncP011Req)basicReq;
		CardHolderExample cardHolderExample = new CardHolderExample();
		cardHolderExample.createCriteria().andIdNoEqualTo(req.getID_NO());
		List<CardHolder> cardHolders = cardHolderMapper.selectByExample(cardHolderExample);
		CardHolder cardHolder = cardHolders.get(0);
		
		
		AccCardInfoExample accCardInfoExample = new AccCardInfoExample();
		accCardInfoExample.createCriteria().andCardholderIdEqualTo(cardHolder.getCardholderId());
		List<AccCardInfo> accCardInfos = accCardInfoMapper.selectByExample(accCardInfoExample);
		if(accCardInfos!=null){
			for(AccCardInfo accCardInfo : accCardInfos){
				if(accCardInfo.getCardNo().trim().equals(req.getCARD_NO().trim())){
					return true;
				}
			}
		}
		
		
		return false;
	}*/

	@Override
	public CardHolder selectByCardNo(String cardNo) {
		AccCardInfoExample accCardInfoExample = new AccCardInfoExample();
		accCardInfoExample.createCriteria().andCardNoEqualTo(cardNo);
		List<AccCardInfo> accCardInfos = accCardInfoMapper.selectByExample(accCardInfoExample);
		if(accCardInfos==null||accCardInfos.size()==0){
			return null;
		}
		AccCardInfo accCardInfo = accCardInfos.get(0);
		
		CardHolderExample cardHolderExample = new CardHolderExample();
		cardHolderExample.createCriteria().andCardholderIdEqualTo(accCardInfo.getCardholderId());
		List<CardHolder> cardHolders = cardHolderMapper.selectByExample(cardHolderExample);
		if(cardHolders==null||cardHolders.size()==0){
			return null;
		}
		CardHolder cardHolder = cardHolders.get(0);
		
		
		return cardHolder;
	}

	@Override
	public List<CardHolder> selectByExample(CardHolderExample example) {
		return cardHolderMapper.selectByExample(example);
	}
	
	

}

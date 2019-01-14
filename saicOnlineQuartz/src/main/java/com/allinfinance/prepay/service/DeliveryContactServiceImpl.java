package com.allinfinance.prepay.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allinfinance.prepay.dao.CommonsDAO;
import com.allinfinance.prepay.exception.BizServiceException;
import com.allinfinance.prepay.mapper.svc_mng.DeliveryContactMapper;
import com.allinfinance.prepay.model.DeliveryContact;
import com.allinfinance.prepay.model.DeliveryContactExample;
import com.allinfinance.prepay.utils.DateUtil;
import com.allinfinance.prepay.utils.ReflectionUtil;
import com.allinfinance.univer.entity.dto.DeliveryRecipientDTO;

@Service
public class DeliveryContactServiceImpl implements DeliveryContactService {
	Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private CommonsDAO commonsDAO;
	@Autowired
	private DeliveryContactMapper deliveryContactMapper;
	@Override
	public void insert(DeliveryRecipientDTO deliveryRecipientDTO)
			throws BizServiceException {
		// TODO Auto-generated method stub
		try {

			this.updateDefaultFlag(deliveryRecipientDTO);

			DeliveryContact deliveryContact = new DeliveryContact();
			ReflectionUtil
					.copyProperties(deliveryRecipientDTO, deliveryContact);

			String id = commonsDAO
					.getNextValueOfSequence("TB_DELIVERY_CONTACT");
			deliveryContact.setDeliveryContactId(id);
			deliveryContact.setCreateTime(DateUtil.getCurrentTime());
			deliveryContact.setModifyTime(DateUtil.getCurrentTime());
			deliveryContact.setDataState("1");

			deliveryContactMapper.insert(deliveryContact);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("添加快递点联系人失败！");
		}

	}
	
	private void updateDefaultFlag(DeliveryRecipientDTO deliveryRecipientDTO) {
		if ("1".equals(deliveryRecipientDTO.getDefaultFlag())) {
			DeliveryContactExample example = new DeliveryContactExample();
			example.createCriteria().andDefaultFlagEqualTo("1")
					.andDeliveryPointIdEqualTo(
							deliveryRecipientDTO.getDeliveryPointId())
					.andDataStateEqualTo("1");

			DeliveryContact contact = new DeliveryContact();
			contact.setDefaultFlag("0");

			deliveryContactMapper.updateByExampleSelective(contact, example);
		}
	}

}

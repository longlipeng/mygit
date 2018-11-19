package com.allinfinance.prepay.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allinfinance.prepay.dao.CommonsDAO;
import com.allinfinance.prepay.exception.BizServiceException;
import com.allinfinance.prepay.mapper.svc_mng.EntityDeliveryMapper;
import com.allinfinance.prepay.model.EntityDelivery;
import com.allinfinance.prepay.model.EntityDeliveryExample;
import com.allinfinance.prepay.utils.DateUtil;
import com.allinfinance.prepay.utils.ReflectionUtil;
import com.allinfinance.univer.entity.dto.DeliveryPointDTO;

@Service
public class EntityDeliveryServiceImpl implements EntityDeliveryService {

	Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private EntityDeliveryMapper entityDeliveryMapper;
	@Autowired
	private CommonsDAO commonsDAO;
	@Override
	public DeliveryPointDTO insert(DeliveryPointDTO deliveryPointDTO)
			throws BizServiceException {
		try {

			this.updateDefaultFlag(deliveryPointDTO);

			EntityDelivery entityDelivery = new EntityDelivery();
			ReflectionUtil.copyProperties(deliveryPointDTO, entityDelivery);

			String id = commonsDAO.getNextValueOfSequence("TB_ENTITY_DELIVERY");
			entityDelivery.setDeliveryId(id);
			entityDelivery.setDataState("1");
			entityDelivery.setCreateTime(DateUtil.getCurrentTime());
			entityDelivery.setModifyTime(DateUtil.getCurrentTime());

			entityDeliveryMapper.insert(entityDelivery);
			ReflectionUtil.copyProperties(entityDelivery, deliveryPointDTO);
			return deliveryPointDTO;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("Ìí¼Ó¿ìµÝµãÊ§°Ü£¡");
		}

	}
	
	private void updateDefaultFlag(DeliveryPointDTO deliveryPointDTO) {
		if ("1".equals(deliveryPointDTO.getDefaultFlag())) {
			EntityDeliveryExample example = new EntityDeliveryExample();
			example.createCriteria().andEntityIdEqualTo(
					deliveryPointDTO.getEntityId()).andDefaultFlagEqualTo("1")
					.andDataStateEqualTo("1");

			EntityDelivery delivery = new EntityDelivery();
			delivery.setDefaultFlag("0");

			entityDeliveryMapper.updateByExampleSelective(delivery, example);
		}
	}

}

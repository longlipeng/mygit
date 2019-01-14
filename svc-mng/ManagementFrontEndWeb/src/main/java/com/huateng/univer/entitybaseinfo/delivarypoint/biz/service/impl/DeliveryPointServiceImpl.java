package com.huateng.univer.entitybaseinfo.delivarypoint.biz.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.allinfinance.univer.entity.dto.DeliveryPointDTO;
import com.allinfinance.univer.entity.dto.DeliveryRecipientDTO;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.EntityDeliveryDAO;
import com.huateng.framework.ibatis.model.EntityDelivery;
import com.huateng.framework.ibatis.model.EntityDeliveryExample;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.ReflectionUtil;
import com.huateng.univer.entitybaseinfo.delivarypoint.biz.service.DeliveryPointService;
import com.huateng.univer.entitybaseinfo.deliverycontact.biz.service.DeliveryContactService;

/**
 * 快递点service
 * 
 * @author xxl
 * 
 */
public class DeliveryPointServiceImpl implements DeliveryPointService {

	Logger logger = Logger.getLogger(this.getClass());
	private CommonsDAO commonsDAO;
	private EntityDeliveryDAO deliveryDAO;

	private DeliveryContactService deliveryContactService;

	public List<DeliveryPointDTO> inquery(String entityId)
			throws BizServiceException {
		try {
			EntityDeliveryExample example = new EntityDeliveryExample();
			example.createCriteria().andEntityIdEqualTo(entityId)
					.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
			example.setOrderByClause("default_flag desc");
			List<EntityDelivery> list = deliveryDAO.selectByExample(example);
			List<DeliveryPointDTO> dtoList = new ArrayList<DeliveryPointDTO>();

			for (EntityDelivery e : list) {
				DeliveryPointDTO dto = new DeliveryPointDTO();
				ReflectionUtil.copyProperties(e, dto);
				dto.setRecipientList(deliveryContactService.inquery(e
						.getDeliveryId()));
				dtoList.add(dto);
			}
			return dtoList;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询快递点信息失败！");
		}
	}

	public DeliveryPointDTO view(DeliveryPointDTO deliveryPointDTO)
			throws BizServiceException {
		try {
			EntityDelivery delivery = deliveryDAO
					.selectByPrimaryKey(deliveryPointDTO.getDeliveryId());
			ReflectionUtil.copyProperties(delivery, deliveryPointDTO);

			// 加载快递点联系人列表
			List<DeliveryRecipientDTO> recipientList = deliveryContactService
					.inquery(deliveryPointDTO.getDeliveryId());

			deliveryPointDTO.setRecipientList(recipientList);

			return deliveryPointDTO;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查看快递点失败！");
		}
	}

	private void updateDefaultFlag(DeliveryPointDTO deliveryPointDTO) {
		if ("1".equals(deliveryPointDTO.getDefaultFlag())) {
			EntityDeliveryExample example = new EntityDeliveryExample();
			example.createCriteria().andEntityIdEqualTo(
					deliveryPointDTO.getEntityId()).andDefaultFlagEqualTo("1")
					.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);

			EntityDelivery delivery = new EntityDelivery();
			delivery.setDefaultFlag("0");

			deliveryDAO.updateByExampleSelective(delivery, example);
		}
	}

	public DeliveryPointDTO insert(DeliveryPointDTO deliveryPointDTO)
			throws BizServiceException {
		try {

			this.updateDefaultFlag(deliveryPointDTO);

			EntityDelivery entityDelivery = new EntityDelivery();
			ReflectionUtil.copyProperties(deliveryPointDTO, entityDelivery);

			String id = commonsDAO.getNextValueOfSequence("TB_ENTITY_DELIVERY");
			entityDelivery.setDeliveryId(id);
			entityDelivery.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
			entityDelivery.setCreateTime(DateUtil.getCurrentTime());
			entityDelivery.setModifyTime(DateUtil.getCurrentTime());

			deliveryDAO.insert(entityDelivery);
			ReflectionUtil.copyProperties(entityDelivery, deliveryPointDTO);
			return deliveryPointDTO;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("添加快递点失败！");
		}

	}

	public void update(DeliveryPointDTO deliveryPointDTO)
			throws BizServiceException {
		try {
			this.updateDefaultFlag(deliveryPointDTO);

			EntityDelivery entityDelivery = new EntityDelivery();
			ReflectionUtil.copyProperties(deliveryPointDTO, entityDelivery);

			entityDelivery.setModifyTime(DateUtil.getCurrentTime());
			entityDelivery.setModifyUser(deliveryPointDTO.getLoginUserId());

			deliveryDAO.updateByPrimaryKeySelective(entityDelivery);

		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("更新快递点失败！");
		}
	}

	public void delete(DeliveryPointDTO deliveryPointDTO)
			throws BizServiceException {
		try {
			List<DeliveryPointDTO> dtoList = deliveryPointDTO
					.getDeliveryPointDTOs();
			List<EntityDelivery> deliveryList = new ArrayList<EntityDelivery>();
			for (DeliveryPointDTO dto : dtoList) {
				EntityDelivery entityDelivery = new EntityDelivery();
				ReflectionUtil.copyProperties(dto, entityDelivery);
				entityDelivery.setDataState(DataBaseConstant.DATA_STATE_DELETE);
				entityDelivery.setModifyUser(deliveryPointDTO.getLoginUserId());
				entityDelivery.setModifyTime(DateUtil.getCurrentTime());

				deliveryList.add(entityDelivery);

			}
			commonsDAO
					.batchUpdate(
							"TB_ENTITY_DELIVERY.abatorgenerated_updateByPrimaryKeySelective",
							deliveryList);

		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("删除快递点失败！");
		}
	}

	public CommonsDAO getCommonsDAO() {
		return commonsDAO;
	}

	public void setCommonsDAO(CommonsDAO commonsDAO) {
		this.commonsDAO = commonsDAO;
	}

	public EntityDeliveryDAO getDeliveryDAO() {
		return deliveryDAO;
	}

	public void setDeliveryDAO(EntityDeliveryDAO deliveryDAO) {
		this.deliveryDAO = deliveryDAO;
	}

	public DeliveryContactService getDeliveryContactService() {
		return deliveryContactService;
	}

	public void setDeliveryContactService(
			DeliveryContactService deliveryContactService) {
		this.deliveryContactService = deliveryContactService;
	}

}

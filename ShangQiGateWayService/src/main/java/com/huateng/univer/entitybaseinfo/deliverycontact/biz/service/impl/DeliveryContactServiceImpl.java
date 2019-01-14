package com.huateng.univer.entitybaseinfo.deliverycontact.biz.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.allinfinance.univer.entity.dto.DeliveryRecipientDTO;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.DeliveryContactDAO;
import com.huateng.framework.ibatis.model.DeliveryContact;
import com.huateng.framework.ibatis.model.DeliveryContactExample;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.ReflectionUtil;
import com.huateng.univer.entitybaseinfo.deliverycontact.biz.service.DeliveryContactService;

/**
 * 快递点联系人service
 * 
 * @author xxl
 * 
 */
public class DeliveryContactServiceImpl implements DeliveryContactService {

	Logger logger = Logger.getLogger(this.getClass());
	private CommonsDAO commonsDAO;
	private DeliveryContactDAO deliveryContractDAO;

	public List<DeliveryRecipientDTO> inquery(String deliveryPointId)
			throws BizServiceException {
		try {
			DeliveryContactExample example = new DeliveryContactExample();
			example.createCriteria().andDataStateEqualTo(
					DataBaseConstant.DATA_STATE_NORMAL)
					.andDeliveryPointIdEqualTo(deliveryPointId);
			example.setOrderByClause("default_flag desc");
			List<DeliveryContact> deliveryContactList = deliveryContractDAO
					.selectByExample(example);
			List<DeliveryRecipientDTO> DeliveryRecipientDTOList = new ArrayList<DeliveryRecipientDTO>();
			for (DeliveryContact deliveryContact : deliveryContactList) {
				DeliveryRecipientDTO dto = new DeliveryRecipientDTO();
				ReflectionUtil.copyProperties(deliveryContact, dto);
				DeliveryRecipientDTOList.add(dto);
			}
			return DeliveryRecipientDTOList;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询快递点联系人失败");
		}
	}

	public DeliveryRecipientDTO view(DeliveryRecipientDTO deliveryRecipientDTO)
			throws BizServiceException {
		try {
			DeliveryContact delivery = deliveryContractDAO
					.selectByPrimaryKey(deliveryRecipientDTO
							.getDeliveryContactId());

			ReflectionUtil.copyProperties(delivery, deliveryRecipientDTO);
			return deliveryRecipientDTO;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查看快递点联系人失败！");
		}

	}

	private void updateDefaultFlag(DeliveryRecipientDTO deliveryRecipientDTO) {
		if ("1".equals(deliveryRecipientDTO.getDefaultFlag())) {
			DeliveryContactExample example = new DeliveryContactExample();
			example.createCriteria().andDefaultFlagEqualTo("1")
					.andDeliveryPointIdEqualTo(
							deliveryRecipientDTO.getDeliveryPointId())
					.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);

			DeliveryContact contact = new DeliveryContact();
			contact.setDefaultFlag("0");

			deliveryContractDAO.updateByExampleSelective(contact, example);
		}
	}

	public void insert(DeliveryRecipientDTO deliveryRecipientDTO)
			throws BizServiceException {
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
			deliveryContact.setDataState(DataBaseConstant.DATA_STATE_NORMAL);

			deliveryContractDAO.insert(deliveryContact);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("添加快递点联系人失败！");
		}
	}

	public void delete(DeliveryRecipientDTO deliveryRecipientDTO)
			throws BizServiceException {
		try {
			List<DeliveryRecipientDTO> dtoList = deliveryRecipientDTO
					.getDeliveryRecipientDTO();
			List<DeliveryContact> contactList = new ArrayList<DeliveryContact>();
			for (DeliveryRecipientDTO dto : dtoList) {
				DeliveryContact deliveryContact = new DeliveryContact();
				ReflectionUtil.copyProperties(dto, deliveryContact);
				deliveryContact
						.setDataState(DataBaseConstant.DATA_STATE_DELETE);
				deliveryContact.setModifyUser(deliveryRecipientDTO
						.getLoginUserId());
				deliveryContact.setModifyTime(DateUtil.getCurrentTime());

				contactList.add(deliveryContact);
			}

			commonsDAO
					.batchUpdate(
							"TB_DELIVERY_CONTACT.abatorgenerated_updateByPrimaryKeySelective",
							contactList);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("删除快递点联系人失败！");
		}
	}

	public CommonsDAO getCommonsDAO() {
		return commonsDAO;
	}

	public void setCommonsDAO(CommonsDAO commonsDAO) {
		this.commonsDAO = commonsDAO;
	}

	public DeliveryContactDAO getDeliveryContractDAO() {
		return deliveryContractDAO;
	}

	public void setDeliveryContractDAO(DeliveryContactDAO deliveryContractDAO) {
		this.deliveryContractDAO = deliveryContractDAO;
	}

}

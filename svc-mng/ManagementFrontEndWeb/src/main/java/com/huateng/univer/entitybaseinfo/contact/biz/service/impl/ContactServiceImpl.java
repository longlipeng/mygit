package com.huateng.univer.entitybaseinfo.contact.biz.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.allinfinance.univer.entity.dto.ContactDTO;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.model.EntityContact;
import com.huateng.framework.ibatis.model.EntityContactExample;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.ReflectionUtil;
import com.huateng.univer.entitybaseinfo.contact.biz.service.ContactService;
import com.huateng.univer.entitybaseinfo.contact.integration.dao.ContactServiceDAO;

/**
 * 联系人管理服务实现
 * 
 * @author wangjiaomiao
 * 
 */
public class ContactServiceImpl implements ContactService {

	/**
	 * Log操作
	 */
	Logger logger = Logger.getLogger(ContactServiceImpl.class);

	/**
	 * 联系人管理DAO
	 */
	private ContactServiceDAO contactServiceDAO;

	private CommonsDAO commonsDAO;

	public ContactServiceDAO getContactServiceDAO() {
		return contactServiceDAO;
	}

	public void setContactServiceDAO(ContactServiceDAO contactServiceDAO) {
		this.contactServiceDAO = contactServiceDAO;
	}

	public CommonsDAO getCommonsDAO() {
		return commonsDAO;
	}

	public void setCommonsDAO(CommonsDAO commonsDAO) {
		this.commonsDAO = commonsDAO;
	}

	/*
	 * 查询联系人
	 */
	public List<ContactDTO> inqueryContact(String entityId)
			throws BizServiceException {
		try {
			EntityContactExample example = new EntityContactExample();
			example.createCriteria().andEntityIdEqualTo(entityId)
					.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
			List<EntityContact> queryList = contactServiceDAO
					.selectByExample(example);
			List<ContactDTO> resultList = new ArrayList<ContactDTO>();
			for (EntityContact contact : queryList) {
				ContactDTO contactDTO = new ContactDTO();
				ReflectionUtil.copyProperties(contact, contactDTO);
				resultList.add(contactDTO);
			}
			return resultList;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询联系人失败！");
		}
	}

	public void insert(ContactDTO contactDTO) throws BizServiceException {
		try {
			if (checkRepeat(contactDTO) > 0) {
				throw new BizServiceException("新增的联系人和已有的联系人重复，不能追加！");
			}
			updateDefaultFlag(contactDTO);
			EntityContact contact = new EntityContact();
			ReflectionUtil.copyProperties(contactDTO, contact);

			contact.setContactId(commonsDAO
					.getNextValueOfSequence("TB_ENTITY_CONTACT"));
			contact.setCreateTime(DateUtil.getCurrentTime());
			contact.setModifyTime(DateUtil.getCurrentTime());
			contact.setDataState(DataBaseConstant.DATA_STATE_NORMAL);

			contactServiceDAO.insert(contact);

		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("添加联系人失败！");
		}
	}

	/*
	 * 新增联系人
	 */
	public void insertContact(ContactDTO dto) throws BizServiceException {
		if (checkRepeat(dto) > 0) {
			throw new BizServiceException("新增的联系人和已有的联系人重复，不能追加！");
		}
		try {
			// 如果新增的商户为默认联系人，设置其他商户联系人
			updateDefaultFlag(dto);

			// 注意：dto中的客户ID应该来自前台
			EntityContact contact = new EntityContact();
			ReflectionUtil.copyProperties(dto, contact);
			contact.setCreateUser(dto.getLoginUserId());
			contact.setCreateTime(DateUtil.getCurrentTime());
			contact.setModifyUser(dto.getLoginUserId());
			contact.setModifyTime(DateUtil.getCurrentTime());
			contact.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
			contact.setContactId(commonsDAO
					.getNextValueOfSequence("TB_ENTITY_CONTACT"));
			contactServiceDAO.insert(contact);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("添加联系人失败！");
		}
	}

	/*
	 * 更新联系人
	 */
	public void updateContact(ContactDTO dto) throws BizServiceException {
		// if (checkRepeat(dto) > 0) {
		// throw new BizServiceException("更新的联系人和已有的联系人重复，不能更新！");
		// }
		try {
			// 如果更新的商户为默认联系人
			updateDefaultFlag(dto);

			EntityContact contact = new EntityContact();
			ReflectionUtil.copyProperties(dto, contact);
			contact.setModifyUser(dto.getLoginUserId());
			contact.setModifyTime(DateUtil.getCurrentTime());
			contactServiceDAO.updateByPrimaryKeySelective(contact);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("更新联系人失败！");
		}
	}

	/*
	 * 查看联系人
	 */
	public ContactDTO viewContact(ContactDTO dto) throws BizServiceException {
		try {
			EntityContact contact = contactServiceDAO.selectByPrimaryKey(dto
					.getContactId());
			ContactDTO resultDto = new ContactDTO();
			if (contact != null) {
				ReflectionUtil.copyProperties(contact, resultDto);
			}
			return resultDto;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查看联系人失败！");
		}
	}

	/*
	 * 删除联系人
	 */
	public void deleteContact(ContactDTO contactDTO) throws BizServiceException {
		try {
			List<ContactDTO> list = contactDTO.getContactDTOList();
			List<EntityContact> updateList = new ArrayList<EntityContact>();
			for (ContactDTO dto : list) {
				EntityContact contact = new EntityContact();
				ReflectionUtil.copyProperties(dto, contact);
				contact.setModifyUser(dto.getLoginUserId());
				contact.setModifyTime(DateUtil.getCurrentTime());
				contact.setDataState(DataBaseConstant.DATA_STATE_DELETE);
				updateList.add(contact);
			}
			commonsDAO
					.batchUpdate(
							"TB_ENTITY_CONTACT.abatorgenerated_updateByPrimaryKeySelective",
							updateList);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("删除联系人失败！");
		}
	}

	/*
	 * 初始化联系人信息
	 */
	public ContactDTO initContact(String refId, String refType)
			throws BizServiceException {

		// 初始化联系人信息，注意该客户是否有默认联系人
		return new ContactDTO();
	}

	/**
	 * 所更新或新增的联系人如果是默认的，则把其他联系人设为非默认
	 * 
	 * @param dto
	 * @return
	 * @throws BizServiceException
	 */
	public void updateDefaultFlag(ContactDTO dto) {
		// 如果提交的联系人是设为默认的，则把已有的默认联系人更新
		if (dto.getDefaultFlag() != null && "1".equals(dto.getDefaultFlag())) {
			EntityContactExample example = new EntityContactExample();
			example.createCriteria().andDefaultFlagEqualTo("1")
					.andEntityIdEqualTo(dto.getEntityId()).andDataStateEqualTo(
							DataBaseConstant.DATA_STATE_NORMAL);
			EntityContact contact = new EntityContact();
			contact.setDefaultFlag("0");
			contactServiceDAO.updateByExampleSelective(contact, example);
		}
	}

	/**
	 * 判断添加的联系人是否重复
	 * 
	 * @param dto
	 * @return
	 * @throws BizServiceException
	 */
	public int checkRepeat(ContactDTO dto) {
		int ret = 0;
		EntityContactExample example = new EntityContactExample();
		// 新增时
		if (dto.getContactId() == null) {
			example.createCriteria().andEntityIdEqualTo(dto.getEntityId())
					.andContactTypeEqualTo(dto.getContactType())
					.andContactGenderEqualTo(dto.getContactGender())
					.andContactNameEqualTo(dto.getContactName())
					.andDataStateEqualTo("1");
		} else {
			// 更新时
			example.createCriteria().andEntityIdEqualTo(dto.getEntityId())
					.andContactTypeEqualTo(dto.getContactType())
					.andContactGenderEqualTo(dto.getContactGender())
					.andContactNameEqualTo(dto.getContactName())
					.andContactIdEqualTo(dto.getContactId().toString())
					.andDataStateEqualTo("1");
		}
		List<EntityContact> contactList = contactServiceDAO
				.selectByExample(example);
		if (contactList != null && contactList.size() != 0) {
			ret = contactList.size();
		}
		return ret;
	}
}

package com.allinfinance.prepay.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allinfinance.prepay.dao.CommonsDAO;
import com.allinfinance.prepay.exception.BizServiceException;
import com.allinfinance.prepay.mapper.svc_mng.EntityContactMapper;
import com.allinfinance.prepay.model.EntityContact;
import com.allinfinance.prepay.model.EntityContactExample;
import com.allinfinance.prepay.utils.Config;
import com.allinfinance.prepay.utils.DateUtil;
import com.allinfinance.prepay.utils.ReflectionUtil;
import com.allinfinance.univer.entity.dto.ContactDTO;

@Service
public class EntityContactServiceImpl implements EntityContactService {

	/**
	 * Log����
	 */
	Logger logger = Logger.getLogger(EntityContactServiceImpl.class);
	@Autowired
	private EntityContactMapper entityContactMapper;
	@Autowired
	private CommonsDAO commonsDAO;
	@Override
	public void insert(ContactDTO contactDTO) throws BizServiceException {
		// TODO Auto-generated method stub
		updateDefaultFlag(contactDTO);
		EntityContact contact = new EntityContact();
		ReflectionUtil.copyProperties(contactDTO, contact);

		contact.setContactId(commonsDAO
				.getNextValueOfSequence("TB_ENTITY_CONTACT"));
		contact.setCreateTime(DateUtil.getCurrentTime());
		contact.setModifyTime(DateUtil.getCurrentTime());
		contact.setDataState("1");

		entityContactMapper.insert(contact);

	}

	@Override
	public void updateContact(ContactDTO dto) throws BizServiceException {
		// TODO Auto-generated method stub

		try {
			// ������µ��̻�ΪĬ����ϵ��
			updateDefaultFlag(dto);

			EntityContact contact = new EntityContact();
			EntityContactExample ex=new EntityContactExample();
			ex.createCriteria().andContactIdEqualTo(dto.getContactId());
			ReflectionUtil.copyProperties(dto, contact);
			contact.setModifyUser(Config.getUserId());
			contact.setModifyTime(DateUtil.getCurrentTime());
			entityContactMapper.updateByExampleSelective(contact, ex);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("������ϵ��ʧ�ܣ�");
		}
	}
	
	public void updateDefaultFlag(ContactDTO dto) {
		// ����ύ����ϵ������ΪĬ�ϵģ�������е�Ĭ����ϵ�˸���
		if (dto.getDefaultFlag() != null && "1".equals(dto.getDefaultFlag())) {
			EntityContactExample example = new EntityContactExample();
			example.createCriteria().andDefaultFlagEqualTo("1")
					.andEntityIdEqualTo(dto.getEntityId()).andDataStateEqualTo(
							"1");
			EntityContact contact = new EntityContact();
			contact.setDefaultFlag("0");
			entityContactMapper.updateByExampleSelective(contact, example);
		}
	}

	@Override
	public List<ContactDTO> inqueryContact(String entityId)
			throws BizServiceException {
		try {
			EntityContactExample example = new EntityContactExample();
			example.createCriteria().andEntityIdEqualTo(entityId)
					.andDataStateEqualTo("1");
			List<EntityContact> queryList = entityContactMapper
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
			throw new BizServiceException("��ѯ��ϵ��ʧ�ܣ�");
		}
	}

}

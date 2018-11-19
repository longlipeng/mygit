package com.allinfinance.prepay.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allinfinance.prepay.exception.BizServiceException;
import com.allinfinance.prepay.mapper.svc_mng.AttachInfoMapper;
import com.allinfinance.prepay.model.AttachInfo;
import com.allinfinance.prepay.model.AttachInfoExample;
import com.allinfinance.prepay.processor.ipos.SyncP001Processor;
import com.allinfinance.prepay.utils.DateUtil;
import com.allinfinance.univer.seller.customer.CustomerDTO;
@Service
public class AttachInfoDAOImpl implements AttachInfoDAO {
	private static Logger logger = Logger.getLogger(AttachInfoDAOImpl.class);
	@Autowired
	private AttachInfoMapper attachInfoMapper;
	/**
	 * ����
	 * ʵ��ͻ���
	 * ����   00����ͻ�  01����ֿ���
	 * �Ա�
	 * ID �ֿ��˻�ͻ�ID
	 */
	@Override
	public void insertAttachInfoDTO(CustomerDTO customerDTO, String type,
			String sex,String id) throws BizServiceException {
		// TODO Auto-generated method stub
		
		try {
			//���Ӹ�����Ϣ
			AttachInfo attachInfo=new AttachInfo();
			attachInfo.setPeopleNo(id);
			attachInfo.setPeopleType(type);//00����ͻ���01����ֿ���
			attachInfo.setIndustry(customerDTO.getActivitySector());//��ҵ
			attachInfo.setProfession(customerDTO.getAwareness());//ְҵ
			attachInfo.setValidity(DateUtil.StringDate(customerDTO.getCorpCredEndValidity()));//ʧЧ��
			attachInfo.setCountyr(customerDTO.getNationality());//����
			attachInfo.setCity(customerDTO.getCity());//����
			attachInfo.setUpdateDate(DateUtil.getCurrentTime());
			attachInfo.setEntityId(customerDTO.getFatherEntityId());//������
			attachInfo.setDataStat("1");
			attachInfoMapper.insert(attachInfo);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage());
			throw new BizServiceException("����ʧ�ܣ�");
		}
	}
	/**
	 * ����
	 * ʵ��ͻ���
	 * ����   00����ͻ�  01����ֿ���
	 * �Ա�
	 * ID �ֿ��˻�ͻ�ID
	 */
	@Override
	public void updateAttachInfoDTO(CustomerDTO customerDTO, String type,
			String sex,String id) throws BizServiceException {
		// TODO Auto-generated method stub
		try {
			//������Ϣ
			AttachInfo attachInfo=new AttachInfo();
			attachInfo.setPeopleNo(id);
			attachInfo.setPeopleType(type);//00����ͻ���01����ֿ���
			attachInfo.setIndustry(customerDTO.getActivitySector());//��ҵ
			attachInfo.setProfession(customerDTO.getAwareness());//ְҵ
			attachInfo.setValidity(DateUtil.StringDate(customerDTO.getCorpCredEndValidity()));//ʧЧ��
			attachInfo.setCountyr(customerDTO.getNationality());//����
			attachInfo.setCity(customerDTO.getCity());//����
			attachInfo.setUpdateDate(DateUtil.getCurrentTime());
			attachInfo.setEntityId(customerDTO.getFatherEntityId());//������
			attachInfo.setDataStat("1");
			attachInfo.setRs1(sex);
			AttachInfoExample example=new AttachInfoExample();
			example.createCriteria().andPeopleNoEqualTo(id).andPeopleTypeEqualTo(type)
				.andDataStatEqualTo("1").andEntityIdEqualTo(attachInfo.getEntityId());
			attachInfoMapper.updateByExampleSelective(attachInfo, example);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage());
			throw new BizServiceException("�޸�ʧ�ܣ�");
		}
	}

	@Override
	public int selectAttachInfoDTO(CustomerDTO customerDTO, String type,
			String id) throws BizServiceException {
		// TODO Auto-generated method stub
		try {
			//������Ϣ
			AttachInfoExample example=new AttachInfoExample();
			example.createCriteria().andPeopleNoEqualTo(id).andPeopleTypeEqualTo(type)
				.andDataStatEqualTo("1").andEntityIdEqualTo(customerDTO.getFatherEntityId());
			List<AttachInfo> list=attachInfoMapper.selectByExample(example);
			return list.size();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage());
			throw new BizServiceException("��ѯʧ�ܣ�");
		}
	}

}

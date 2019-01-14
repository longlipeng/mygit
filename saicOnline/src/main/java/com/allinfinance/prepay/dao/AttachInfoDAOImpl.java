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
	 * 参数
	 * 实体客户类
	 * 类型   00代表客户  01代表持卡人
	 * 性别
	 * ID 持卡人或客户ID
	 */
	@Override
	public void insertAttachInfoDTO(CustomerDTO customerDTO, String type,
			String sex,String id) throws BizServiceException {
		// TODO Auto-generated method stub
		
		try {
			//增加附加信息
			AttachInfo attachInfo=new AttachInfo();
			attachInfo.setPeopleNo(id);
			attachInfo.setPeopleType(type);//00代表客户，01代表持卡人
			attachInfo.setIndustry(customerDTO.getActivitySector());//行业
			attachInfo.setProfession(customerDTO.getAwareness());//职业
			attachInfo.setValidity(DateUtil.StringDate(customerDTO.getCorpCredEndValidity()));//失效期
			attachInfo.setCountyr(customerDTO.getNationality());//国家
			attachInfo.setCity(customerDTO.getCity());//城市
			attachInfo.setUpdateDate(DateUtil.getCurrentTime());
			attachInfo.setEntityId(customerDTO.getFatherEntityId());//机构号
			attachInfo.setDataStat("1");
			attachInfoMapper.insert(attachInfo);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage());
			throw new BizServiceException("增加失败！");
		}
	}
	/**
	 * 参数
	 * 实体客户类
	 * 类型   00代表客户  01代表持卡人
	 * 性别
	 * ID 持卡人或客户ID
	 */
	@Override
	public void updateAttachInfoDTO(CustomerDTO customerDTO, String type,
			String sex,String id) throws BizServiceException {
		// TODO Auto-generated method stub
		try {
			//附加信息
			AttachInfo attachInfo=new AttachInfo();
			attachInfo.setPeopleNo(id);
			attachInfo.setPeopleType(type);//00代表客户，01代表持卡人
			attachInfo.setIndustry(customerDTO.getActivitySector());//行业
			attachInfo.setProfession(customerDTO.getAwareness());//职业
			attachInfo.setValidity(DateUtil.StringDate(customerDTO.getCorpCredEndValidity()));//失效期
			attachInfo.setCountyr(customerDTO.getNationality());//国家
			attachInfo.setCity(customerDTO.getCity());//城市
			attachInfo.setUpdateDate(DateUtil.getCurrentTime());
			attachInfo.setEntityId(customerDTO.getFatherEntityId());//机构号
			attachInfo.setDataStat("1");
			attachInfo.setRs1(sex);
			AttachInfoExample example=new AttachInfoExample();
			example.createCriteria().andPeopleNoEqualTo(id).andPeopleTypeEqualTo(type)
				.andDataStatEqualTo("1").andEntityIdEqualTo(attachInfo.getEntityId());
			attachInfoMapper.updateByExampleSelective(attachInfo, example);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage());
			throw new BizServiceException("修改失败！");
		}
	}

	@Override
	public int selectAttachInfoDTO(CustomerDTO customerDTO, String type,
			String id) throws BizServiceException {
		// TODO Auto-generated method stub
		try {
			//附加信息
			AttachInfoExample example=new AttachInfoExample();
			example.createCriteria().andPeopleNoEqualTo(id).andPeopleTypeEqualTo(type)
				.andDataStatEqualTo("1").andEntityIdEqualTo(customerDTO.getFatherEntityId());
			List<AttachInfo> list=attachInfoMapper.selectByExample(example);
			return list.size();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage());
			throw new BizServiceException("查询失败！");
		}
	}

}

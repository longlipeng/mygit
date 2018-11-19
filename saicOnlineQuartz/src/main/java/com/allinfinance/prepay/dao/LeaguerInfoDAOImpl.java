package com.allinfinance.prepay.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allinfinance.prepay.exception.BizServiceException;
import com.allinfinance.prepay.mapper.svc_mng.LeaguerInfoMapper;
import com.allinfinance.prepay.model.LeaguerInfo;
import com.allinfinance.prepay.utils.Config;
import com.allinfinance.prepay.utils.DataBaseConstant;
import com.allinfinance.prepay.utils.DateUtil;

@Service
public class LeaguerInfoDAOImpl implements LeaguerInfoDAO {
	private static Logger logger = Logger.getLogger(LeaguerInfoDAOImpl.class);
	@Autowired
	private LeaguerInfoMapper leaguerInfoMapper;
	
	@Override
	public void insertLeaguerInfo(LeaguerInfo leaguerInfo)
			throws BizServiceException {
		// TODO Auto-generated method stub
		try {
//			leaguerInfo.setCusNo(leaguerInfoList.get(i).getCusNo());
			//  00 代表客户  01代表持卡人
//			leaguerInfo.setCusType(leaguerInfoList.get(i).getCusType());
//			leaguerInfo.setCusArea(leaguerInfoList.get(i).getCusArea());
			leaguerInfo.setCreateTime(DateUtil.getCurrentTime());
			leaguerInfo.setCreateUser(Config.getUserId());
//			leaguerInfo.setLeaguerId(leaguerInfoList.get(i).getLeaguerId());
			leaguerInfo.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
			leaguerInfo.setUpdateTime(DateUtil.getCurrentTime());
			leaguerInfo.setUpdateUser(Config.getUserId());
			leaguerInfoMapper.insert(leaguerInfo);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage());
			throw new BizServiceException("受理地区增加失败！");
		}
		
		
		

	}

	@Override
	public LeaguerInfo selectLeaguerInfo(LeaguerInfo leaguerInfo)
			throws BizServiceException {
		try {
			LeaguerInfo info=leaguerInfoMapper.selectByPrimaryKey(leaguerInfo);
			return info;
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage());
			throw new BizServiceException("查询受理地区失败！");
		}
		
		
	}

	@Override
	public void updateLeaguerInfo(LeaguerInfo leaguerInfo)
			throws BizServiceException {
		// TODO Auto-generated method stub
		try {
			leaguerInfo.setUpdateTime(DateUtil.getCurrentTime());
			leaguerInfo.setUpdateUser(Config.getUserId());
			leaguerInfoMapper.updateByPrimaryKeySelective(leaguerInfo);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage());
			throw new BizServiceException("修改受理地区失败！");
		}
	}

}

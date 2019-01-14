package com.allinfinance.prepay.dao;

import java.util.List;

import com.allinfinance.prepay.exception.BizServiceException;
import com.allinfinance.prepay.model.LeaguerInfo;

public interface LeaguerInfoDAO {
	public void insertLeaguerInfo(LeaguerInfo leaguerInfo) throws BizServiceException;
	
	public LeaguerInfo selectLeaguerInfo(LeaguerInfo leaguerInfo) throws BizServiceException;
	
	public void updateLeaguerInfo(LeaguerInfo leaguerInfo) throws BizServiceException;

}

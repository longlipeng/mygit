package com.huateng.univer.synch.blacklistandriskservice;

import com.allinfinance.framework.dto.JudgeInforDTO;

/**
 * 获取用户或持卡人风险等级和黑名单校验
 * 
 * @author jason
 *
 */
public interface BlacklistAndRiskService {
	public void judge(JudgeInforDTO dto) throws Exception ;
	
	public void judgeBlackList(JudgeInforDTO dto);
	
	public void judgeRiskGrade(JudgeInforDTO dto);
}

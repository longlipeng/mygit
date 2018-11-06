package com.allinfinance.shangqi.gateway.service;



import java.util.List;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.shangqi.gateway.dto.ApplyAndBindCardDTO;
import com.huateng.framework.exception.BizServiceException;

public interface ApplyAndBindService {
	/*门户绑卡 201505070*/
	public ApplyAndBindCardDTO bindCard(ApplyAndBindCardDTO stockCountDTO) throws BizServiceException;
	/*门户卡申请 201505071*/
	public void applyCardMessage(ApplyAndBindCardDTO stockCountDTO) throws BizServiceException;
	/*门户卡审核信息查询 2015050702*/
	public PageDataDTO checkApplyMsg(ApplyAndBindCardDTO stockCountDTO) throws BizServiceException;
	/*门户用户自己查看申请状态 201505073*/
	public String lookApplyStatus(ApplyAndBindCardDTO stockCountDTO) throws BizServiceException;
	/*控制台新增审核 201505074*/
	public void checkMsg(ApplyAndBindCardDTO stockCountDTO)throws BizServiceException;
	/*门户邮寄信息查询 201505075*/
	public PageDataDTO mailMessages(ApplyAndBindCardDTO stockCountDTO)throws BizServiceException;
	/*邮寄确认信息 201505076*/
	public void MakeSureMail(ApplyAndBindCardDTO stockCountDTO) throws BizServiceException;
	/*审核拒绝 201505077*/
	public void applyRefuse(ApplyAndBindCardDTO stockCountDTO) throws BizServiceException;
	/*查询持卡人信息 201505078*/
	public ApplyAndBindCardDTO getCardHolder(ApplyAndBindCardDTO stockCountDTO ) throws BizServiceException;
	/*查询线上还是线下 201505079*/
	public ApplyAndBindCardDTO onlineOrInline(ApplyAndBindCardDTO stockCountDTO)throws BizServiceException;
	/*查询持卡人下的卡号201505080*/
	public List<String> cardNos(ApplyAndBindCardDTO stockCountDTO) throws BizServiceException;
	/*查询指定用户的申请信息201505121*/
	public ApplyAndBindCardDTO singlePersonMsg(ApplyAndBindCardDTO stockCountDTO) throws BizServiceException; 
	/*生成excel文件 邮寄信息 */
	public ApplyAndBindCardDTO exportExcel(ApplyAndBindCardDTO stockCountDTO) throws BizServiceException; 
	/*清算文件下载 201505200*/
	public ApplyAndBindCardDTO readClearFile(ApplyAndBindCardDTO stockCountDTO) throws BizServiceException; 
}

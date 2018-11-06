package com.huateng.univer.cardmanage.biz.service;

import java.util.List;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.cardmanagement.dto.BatchCardActionDTO;
import com.allinfinance.univer.cardmanagement.dto.CardManagementDTO;
import com.allinfinance.univer.cardmanagement.dto.CardManagementQueryDTO;
import com.allinfinance.univer.manager.transactionQuery.dto.StanStifQueryDTO;
import com.allinfinance.univer.manager.transactionQuery.dto.TransactionQueryDTO;
import com.allinfinance.univer.seller.member.MemberQueryDTO;
import com.huateng.framework.exception.BizServiceException;

public interface CardManageService {
	public void insertCardManagement(CardManagementDTO dto)
			throws BizServiceException;

	public CardManagementDTO queryCard(CardManagementDTO dto)
			throws BizServiceException;

	public PageDataDTO cardSecurityInit(CardManagementQueryDTO queryDTO)
			throws BizServiceException;

	public PageDataDTO cardSelect(CardManagementQueryDTO dto)
			throws BizServiceException;

	public CardManagementDTO cardSelectDetail(CardManagementDTO dto)
			throws BizServiceException;

	public PageDataDTO selectCardManagement(CardManagementQueryDTO dto)
			throws BizServiceException;

	public void cardManageOperation(CardManagementDTO dto)
			throws BizServiceException;

	public PageDataDTO inqueryMemberInfo(MemberQueryDTO dto)
			throws BizServiceException;

	public MemberQueryDTO inqueryMemberDetailInfo(MemberQueryDTO dto)
			throws BizServiceException;

	public List<MemberQueryDTO> inqueryMemberRltCardNo(MemberQueryDTO dto)
			throws BizServiceException;

	public MemberQueryDTO inqueryCardBalance(MemberQueryDTO dto)
			throws BizServiceException;

	public PageDataDTO selectSingleCardManagement(CardManagementQueryDTO dto)
			throws BizServiceException;

	public CardManagementDTO queryCardForTranfer(CardManagementDTO dto)
			throws BizServiceException;

	public CardManagementDTO queryInCardForTranfer(CardManagementDTO dto)
			throws BizServiceException;

	public String redemptionCard(CardManagementDTO dto)
			throws BizServiceException;

	public CardManagementDTO viewOrderByCardNo(
			CardManagementDTO cardManagementDTO) throws BizServiceException;

	public CardManagementDTO queryNewCardForChange(CardManagementDTO dto)
			throws BizServiceException;

	public String setCardSecurityInfo(CardManagementDTO dto)
			throws BizServiceException;

	public CardManagementDTO cardSeuriyQuery(CardManagementDTO dto)
			throws BizServiceException;
	//判断卡片是否出库
	public void checkCardIsAct(CardManagementDTO cardManagementDTO) throws BizServiceException;
	//判断卡片是否处于库存状态中
	public int checkCardIsStock(BatchCardActionDTO dto) throws BizServiceException;
	//营销机构单卡作废
	public void submitInvalid(BatchCardActionDTO dto)throws BizServiceException;
	
	//卡片激活
	public String activateCard(CardManagementDTO dto)throws BizServiceException;
	//交易记录查询
	public PageDataDTO transactionQuery(TransactionQueryDTO dto)throws BizServiceException;
	//备付金查询
	public PageDataDTO queryProvisionsBanlance(TransactionQueryDTO dto)throws BizServiceException;
	
	//反洗钱流水查询
	public PageDataDTO stanStifQuery(StanStifQueryDTO dto)
			throws BizServiceException;
	//反洗钱流水查询明细
	public StanStifQueryDTO stanStifQueryInfo(StanStifQueryDTO dto)
			throws BizServiceException;
	
}

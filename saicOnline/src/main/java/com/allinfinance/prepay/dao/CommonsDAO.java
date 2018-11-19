package com.allinfinance.prepay.dao;

import java.util.List;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.prepay.exception.BizServiceException;
import com.allinfinance.univer.cardmanagement.dto.CardManagementDTO;
import com.allinfinance.univer.consumer.merchant.dto.MerchantDTO;
import com.allinfinance.univer.consumer.pos.dto.posParameterValueDTO;
import com.allinfinance.univer.issuer.dto.consumer.ConsumerDTO;
import com.allinfinance.univer.issuer.dto.issuer.IssuerDTO;
import com.allinfinance.univer.refund.dto.RefundDTO;
import com.allinfinance.univer.seller.member.MemberQueryDTO;
import com.allinfinance.univer.seller.seller.dto.SellerDTO;


/**
 * The Interface CommonsDAO.
 */
public interface CommonsDAO {

	/**
	 * Gets the next value of sequence.
	 * 
	 * @param tableName
	 *            the table name
	 * 
	 * @return the next value of tableSerialNum
	 * @throws BizServiceException
	 */
	public String getNextValueOfSequence(String tableName)
			throws BizServiceException;

	/**
	 * Gets the next value of sequence.
	 * 
	 * @param sequenceName
	 *            the sequence name
	 * 
	 * @return the next value of sequence
	 * @throws BizServiceException
	 */
	public String getNextValueOfSequenceBySequence(String sequence)
			throws BizServiceException;

	/**
	 * �ṩ����update.
	 * 
	 * @param statementId
	 *            The name of the statement to execute
	 * @param parameters
	 *            The parameter object (e.g. JavaBean, Map, XML etc.) List
	 * 
	 * @return the number of rows updated in the batch
	 */
	/*@SuppressWarnings("unchecked")
	public int batchUpdate(final String statementId, final List parameters);

	*//**
	 * �ṩ����insert.
	 * 
	 * @param statementId
	 *            The name of the statement to execute
	 * @param parameters
	 *            The parameter object (e.g. JavaBean, Map, XML etc.) List
	 * 
	 * @return the number of rows inserted in the batch
	 *//*

	@SuppressWarnings("unchecked")
	public int batchInsert(final String statementId, final List parameters);

	*//**
	 * �ṩ����delete.
	 * 
	 * @param statementId
	 *            The name of the statement to execute
	 * @param parameters
	 *            The parameter object (e.g. JavaBean, Map, XML etc.) List
	 * 
	 * @return the number of rows deleted in the batch
	 *//*
	@SuppressWarnings("unchecked")
	public int batchDelete(final String statementId, final List parameters);

	*//**
	 * Query current date.
	 * 
	 * @return the string
	 *//*
	public String queryCurrentDate();

	public int isDeleteIssuer(IssuerDTO issuerDTO) throws BizServiceException;

	public int isDeleteConsumer(ConsumerDTO consumerDTO)
			throws BizServiceException;

	public int isDeleteSeller(SellerDTO sellerDTO) throws BizServiceException;

	public void consumer(String id) throws BizServiceException;

	public void seller(String id) throws BizServiceException;

	public void merchant(String id) throws BizServiceException;

	public void issystem(String id) throws BizServiceException;

	public int isDeleteMerchant(MerchantDTO merchantDTO)
			throws BizServiceException;

	public MemberQueryDTO selectMemberDetailInf(MemberQueryDTO dto)
			throws BizServiceException;

	public List<MemberQueryDTO> selectMemberRltCard(MemberQueryDTO dto)
			throws BizServiceException;

	public PageDataDTO selectProductInfByCardNo(
			CardManagementDTO cardManagementDTO);

	public List<posParameterValueDTO> queryCardBinList(
			posParameterValueDTO posParameterValueDTO)
			throws BizServiceException;*/
	
//	public List<?> queryForList(final String statementName, final Object parameterObject);
	
//	/**
//	 * ͨ����ˮ�ź�������־��ѯ�˻�������Ϣ
//	 */
//	public RefundDTO selectBySeqIdFlag(RefundDTO refundDTO) throws BizServiceException;
//	//����WEB�˻����״̬
//	public int updateHsaRefundState(RefundDTO refundDTO) throws BizServiceException;
//	//����POS�˻����״̬
//	public int updateTxnRefundState(RefundDTO refundDTO) throws BizServiceException;
//	public String getNextValue(String tableName)throws BizServiceException;
//	//���ֿ�����Ϣ
//	public void updateCardInfo(SellOrderCardList sellOrderCardList) throws BizServiceException;
//	public void updateCardList(SellOrderCardList  SellOrderCardList) throws BizServiceException;
//	public String getLastestOrderId(SellOrderCardList  SellOrderCardList) throws BizServiceException;
}
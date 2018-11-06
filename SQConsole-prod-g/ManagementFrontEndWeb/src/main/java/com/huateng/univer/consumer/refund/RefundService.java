package com.huateng.univer.consumer.refund;

import java.util.List;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.consumer.merchant.dto.MerchantDTO;
import com.allinfinance.univer.refund.dto.RefundDTO;
import com.allinfinance.univer.refund.dto.RefundQueryDTO;
import com.huateng.framework.exception.BizServiceException;

public interface RefundService {

	/**
	 * 退货申请列表
	 * 
	 * @param dto
	 * @return
	 * @throws BizServiceException
	 */
	public PageDataDTO refundList(RefundQueryDTO dto)
			throws BizServiceException;

	/**
	 * 根据seqId取出退货申请详细信息
	 * 
	 * @param seqId
	 * @return
	 * @throws BizServiceException
	 */
	public RefundDTO refundDetail(RefundDTO refundDTO) throws BizServiceException;

	/**
	 * 查询收单机构下所有的商户
	 * 
	 * @param entityId
	 * @return
	 * @throws BizServiceException
	 */
	public List<MerchantDTO> findAllMerchant(String entityId)
			throws BizServiceException;

	/**
	 * 退货审核(第一步:将状态改成已通过)
	 * 
	 * @param dto
	 * @return
	 * @throws BizServiceException
	 */
	public RefundDTO refundVerify1(RefundDTO dto) throws BizServiceException;

	/**
	 * 退货审核(第二步:调用接口来访问核心进行退货)
	 * 
	 * @param dto
	 * @return
	 * @throws BizServiceException
	 */
	public RefundDTO refundVerify2(RefundDTO dto) throws BizServiceException;

//	/**
//	 * 退货审核(第三步:根据第二部的返回来更改状态)
//	 * 
//	 * @param dto
//	 * @return
//	 * @throws BizServiceException
//	 */
//	public RefundDTO refundVerify3(RefundDTO dto) throws BizServiceException;

	/**
	 * 退货拒绝(修改状态和审核意见)
	 * 
	 * @param dto
	 * @return
	 * @throws BizServiceException
	 */
	public String refundRefuse(RefundDTO dto) throws BizServiceException;

	/**
	 * 通过接口(根据订单号)查询是否退货成功
	 * 
	 * @param orderId
	 * @return
	 * @throws BizServiceException
	 */
	public RefundDTO refundTransLogQuery(RefundDTO dto)
			throws BizServiceException;

	/**
	 * 更新记录
	 * 
	 * @param dto
	 * @return
	 * @throws BizServiceException
	 */
	public String refundUpdate(RefundDTO dto) throws BizServiceException;

	/**
	 * 退货审核
	 * 
	 * @param dto
	 * @return
	 * @throws BizServiceException
	 */
	public RefundDTO refundVerify(RefundDTO dto) throws BizServiceException;
}

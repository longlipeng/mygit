package com.huateng.univer.businessbatch.impl;


import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import com.allinfinance.univer.businessbatch.dto.BatchFileDTO;
import com.huateng.framework.ibatis.dao.BatchSumDAO;
import com.huateng.framework.ibatis.dao.TblOrderBatchConstrainDAO;
import com.huateng.framework.ibatis.model.BatchSum;
import com.huateng.univer.businessbatch.BusinessBatchService;

/**
 * 批量任务的抽象类
 * @author flypal
 *
 */
public abstract class BasicBatchServiceImpl implements BusinessBatchService {
	private Logger logger = Logger.getLogger(BasicBatchServiceImpl.class);
	/**
	 * 批次类型对应批次service
	 */
//	protected static ConcurrentHashMap<String, BusinessBatchService> serviceMap = new ConcurrentHashMap<String, BusinessBatchService>();
	private BatchSumDAO batchSumDAO;
	private TblOrderBatchConstrainDAO tblOrderBatchConstrainDAO;
	
	public BatchSumDAO getBatchSumDAO() {
		return batchSumDAO;
	}

	public void setBatchSumDAO(BatchSumDAO batchSumDAO) {
		this.batchSumDAO = batchSumDAO;
	}
	
	public TblOrderBatchConstrainDAO getTblOrderBatchConstrainDAO() {
		return tblOrderBatchConstrainDAO;
	}

	public void setTblOrderBatchConstrainDAO(
			TblOrderBatchConstrainDAO tblOrderBatchConstrainDAO) {
		this.tblOrderBatchConstrainDAO = tblOrderBatchConstrainDAO;
	}

	/**
	 * 该方法会被轮询
	 */
	@Override
	public boolean checkBatchState(String batchNo) {
		BatchSum sum = batchSumDAO.selectByPrimaryKey(batchNo);
		try {
			/**
			 * 批次状态为结束,则更新订单状态，进行后续操作。
			 */
			if (BatchFileDTO.BATCH_STATE_END.equals(sum.getBatchState())) {
				if (orderStateChange(sum)) {
					tblOrderBatchConstrainDAO.deleteByPrimaryKey(sum.getOrderId());
					return true;
				}
				return false;
			}
			/**
			 * 批次状态仍然为初始状态，则重新发送报文通知。
			 */
			if (BatchFileDTO.BATCH_STATE_INIT.equals(sum.getBatchState())) {
				BatchFileDTO batchFileDto = new BatchFileDTO();
				BeanUtils.copyProperties(batchFileDto, sum);
				commMessage(batchFileDto);
				return false;
			}
			/**
			 * 批次状态为批次进行中则不处理
			 */
			return false;
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			return false;
		}

	}

	/**
	 * 这个方法主要用来处理相关的订单信息；
	 * 该方法也会被重复调用，需要考虑重复调用的时候，会不会出现问题；
	 * 该方法不存在并发性问题。
	 * @param sum
	 * @return
	 */
	protected abstract boolean orderStateChange(BatchSum sum);
	
	/**
	 * 默认需要进行约束
	 */
	public boolean isContrains() {
		return true;
	}

}

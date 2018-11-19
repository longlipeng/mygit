package com.huateng.univer.batchfile.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import com.allinfinance.service.BatchParamInterface;
import com.allinfinance.univer.businessbatch.dto.BatchFileDTO;
import com.allinfinance.univer.businessbatch.dto.BatchFileDetailDTO;
import com.huateng.framework.common.service.CommonService;
import com.huateng.framework.dao.DataBaseDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.BatchSumDAO;
import com.huateng.framework.ibatis.dao.TblOrderBatchConstrainDAO;
import com.huateng.framework.ibatis.model.BatchSum;
import com.huateng.framework.ibatis.model.TblOrderBatchConstrain;
import com.huateng.framework.ibatis.model.TblOrderBatchConstrainExample;
import com.huateng.framework.util.DateUtil;
import com.huateng.univer.batchfile.BatchFileTransactionServiceInterface;
import com.huateng.univer.businessbatch.BusinessBatchService;

public class BatchFileTransactionServiceImpl implements
		BatchFileTransactionServiceInterface {
	private Logger logger = Logger.getLogger(BatchFileTransactionServiceImpl.class);
	private BatchSumDAO batchSumDAO;
	private DataBaseDAO databaseDAO;
	private TblOrderBatchConstrainDAO tblOrderBatchConstrainDAO;
	
	private CommonService commonService;

	public BatchFileDTO insertBatchInfo(String orderId,
			List<? extends BatchParamInterface> batchList,
			BusinessBatchService business) throws BizServiceException {
		BatchFileDTO batchFileDTO = new BatchFileDTO();
		try {
			batchFileDTO.setBatchType(business.getBatchType());
			batchFileDTO.setOrderId(orderId);
			batchFileDTO.setBatchNo(commonService
					.getNextValueOfSequence("TB_BATCH_FILE"));
			batchFileDTO.setSmltDate(DateUtil.getCurrentDateStr());
			batchFileDTO.setBatchState(BatchFileDTO.BATCH_STATE_INIT);
			long totAmt = 0;

			List<BatchFileDetailDTO> batchFiles = new ArrayList<BatchFileDetailDTO>();
			for (BatchParamInterface obj : batchList) {
				BatchFileDetailDTO batchFileDetailDTO = new BatchFileDetailDTO();
				batchFileDetailDTO.setTxnSeq(commonService
						.getNextValueOfSequence("TB_TXN_SEQ"));
				batchFileDetailDTO.setBatchNO(batchFileDTO.getBatchNo());
				batchFileDetailDTO.setSmltDate(batchFileDTO.getSmltDate());
				batchFileDetailDTO
						.setTxnState(BatchFileDetailDTO.TXN_STATE_INIT);
				batchFileDetailDTO.setBatchState(BatchFileDTO.BATCH_STATE_INIT);
				batchFileDetailDTO.setTxnAmt(obj.calcAmt()); // 通过接口获取金额字段
				obj.setSeq(batchFileDetailDTO.getTxnSeq()); // 通过接口设置流水号字段
				batchFileDetailDTO.setBatchType(batchFileDTO.getBatchType());
				batchFiles.add(batchFileDetailDTO);

				long amt = Long.parseLong(batchFileDetailDTO.getTxnAmt());
				totAmt += amt;

			}
			business.insertBatch(batchList);
			databaseDAO.batchInsert("BATCH_DETAIL.insertBatchDetail",
					batchFiles);

			// batchFileDTO.setTotAmt("");
			batchFileDTO.setTotCnt(batchList.size() + "");
			batchFileDTO.setSucAmt("0");
			batchFileDTO.setSucCnt("0");
			batchFileDTO.setFailAmt("0");
			batchFileDTO.setFailCnt("0");
			batchFileDTO.setTotAmt(totAmt + "");
			batchFileDTO.setCreateTime(DateUtil.getCurrentTime());
			insertBatchFileSum(batchFileDTO);
			
			/**
			 * 是否需要进行约束
			 */
			if (business.isContrains()) {
				TblOrderBatchConstrain record = null;
				record = tblOrderBatchConstrainDAO.selectByPrimaryKey(orderId);
				if(record==null){
					record = new TblOrderBatchConstrain();
					record.setOrderId(orderId);
					record.setBatchNo(batchFileDTO.getBatchNo());
					record.setCreateTime(DateUtil.getCurrentDateStr());
					tblOrderBatchConstrainDAO.insert(record);
				}else{
					record.setBatchNo(batchFileDTO.getBatchNo());
					record.setCreateTime(DateUtil.getCurrentDateStr());
					tblOrderBatchConstrainDAO.updateByPrimaryKey(record);
				}
				
				
			}

		} catch (BizServiceException b) {
			logger.error(b.getMessage());
			throw b;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("批量充值失败");
		}
		return batchFileDTO;
	}
	
	private void insertBatchFileSum(BatchFileDTO batchFileDto)
			throws BizServiceException {
		try {
			BatchSum sum = new BatchSum();
			BeanUtils.copyProperties(sum, batchFileDto);
			batchSumDAO.insert(sum);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("批量充值失败");
		}
	}

	public BatchSumDAO getBatchSumDAO() {
		return batchSumDAO;
	}

	public void setBatchSumDAO(BatchSumDAO batchSumDAO) {
		this.batchSumDAO = batchSumDAO;
	}

	public DataBaseDAO getDatabaseDAO() {
		return databaseDAO;
	}

	public void setDatabaseDAO(DataBaseDAO databaseDAO) {
		this.databaseDAO = databaseDAO;
	}

	public CommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

	public TblOrderBatchConstrainDAO getTblOrderBatchConstrainDAO() {
		return tblOrderBatchConstrainDAO;
	}

	public void setTblOrderBatchConstrainDAO(
			TblOrderBatchConstrainDAO tblOrderBatchConstrainDAO) {
		this.tblOrderBatchConstrainDAO = tblOrderBatchConstrainDAO;
	}

}

package com.huateng.univer.batchfile.action;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.allinfinance.service.BatchParamInterface;
import com.allinfinance.univer.businessbatch.dto.BatchFileDTO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.listener.batch.BatchCheckQuartz;
import com.huateng.univer.batchfile.BatchFileTransactionServiceInterface;
import com.huateng.univer.businessbatch.BusinessBatchService;

/**
 * 批量文件框架
 * 
 * @author flypal
 * 
 */
public class BatchFileActionImpl implements BatchFileActionInterface {
	private static final Logger log = Logger.getLogger(BatchFileActionImpl.class);
	private Map<String, BusinessBatchService> businessMap;
	private BatchCheckQuartz batchQuartz;
	private BatchFileTransactionServiceInterface batchFileTransactionService;

	public Map<String, BusinessBatchService> getBusinessMap() {
		return businessMap;
	}

	public void setBusinessMap(Map<String, BusinessBatchService> businessMap) {
		this.businessMap = businessMap;
	}

	public String fileBatch(String serviceName, String orderId,
			List<? extends BatchParamInterface> batchList) {
		BatchFileDTO batchFileDTO = null;
		BusinessBatchService business = businessMap.get(serviceName);
		if (business == null) {
//			throw new BizServiceException("can not find service{" + serviceName
//					+ "}");
			log.error("can not find service{" + serviceName+ "}");
			return null;
		}
		try {
			batchFileDTO = batchFileTransactionService.insertBatchInfo(orderId,
					batchList, business);
			if (batchFileDTO == null || batchFileDTO.getBatchNo() == null
					|| batchFileDTO.getBatchNo().length() == 0) {
				return null;
			}
			commMessage(batchFileDTO, business);
			batchQuartz.addEvent(batchFileDTO.getBatchNo(), business);

		} catch (BizServiceException b) {
			log.error(b.getMessage());
//			throw b;
		} catch (Exception e) {
			log.error(e.getMessage());
//			this.logger.error(e.getMessage());
//			throw new BizServiceException("批量充值失败");
		}
		return batchFileDTO.getBatchNo();
	}
    /**
     * 
     * 功能描述: <br>
     * JOB任务调用的批量文件处理任务
     *
     * @param serviceName
     * @param orderId
     * @param batchList
     * @return
     */
	 public String fileBatchTask(String serviceName, String orderId,
	            List<? extends BatchParamInterface> batchList) {
	        BatchFileDTO batchFileDTO = null;
	        BusinessBatchService business = businessMap.get(serviceName);
	        if (business == null) {
	            log.error("can not find service{" + serviceName+ "}");
	            return null;
	        }
	        try {
	            batchFileDTO = batchFileTransactionService.insertBatchInfo(orderId,
	                    batchList, business);
	            if (batchFileDTO == null || batchFileDTO.getBatchNo() == null
	                    || batchFileDTO.getBatchNo().length() == 0) {
	                return null;
	            }
	            commMessage(batchFileDTO, business);

	        } catch (BizServiceException b) {
	            log.error(b.getMessage());
	        } catch (Exception e) {
	            log.error(e.getMessage());
	        }
	        return batchFileDTO.getBatchNo();
	    }
	
	public BatchCheckQuartz getBatchQuartz() {
		return batchQuartz;
	}

	public void setBatchQuartz(BatchCheckQuartz batchQuartz) {
		this.batchQuartz = batchQuartz;
	}

	private void commMessage(BatchFileDTO batchFileDTO,
			BusinessBatchService business) throws BizServiceException {
		try {
			business.commMessage(batchFileDTO);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	public BatchFileTransactionServiceInterface getBatchFileTransactionService() {
		return batchFileTransactionService;
	}

	public void setBatchFileTransactionService(
			BatchFileTransactionServiceInterface batchFileTransactionService) {
		this.batchFileTransactionService = batchFileTransactionService;
	}

}

package com.huateng.univer.businessbatch.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.allinfinance.service.BatchParamInterface;
import com.allinfinance.univer.businessbatch.dto.BatchFileDTO;
import com.allinfinance.univer.businessbatch.dto.BatchFileDetailDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderListDTO;
import com.huateng.framework.constant.Const;
import com.huateng.framework.constant.OrderConst;
import com.huateng.framework.dao.DataBaseDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.SellOrderDAO;
import com.huateng.framework.ibatis.model.BatchSum;
import com.huateng.framework.ibatis.model.SellOrder;
import com.huateng.framework.ibatis.model.SellOrderExample;
import com.huateng.framework.util.DateUtil;
import com.huateng.hstserver.gatewayService.Java2ACCBusinessServiceImpl;
import com.huateng.hstserver.model.AccPackageDTO;
import com.huateng.univer.order.business.bo.OrderBO;

public class RechargeActBatchServiceImpl extends BasicBatchServiceImpl {
	
	private Logger logger = Logger.getLogger(RechargeActBatchServiceImpl.class);
	/**
	 * 公共工具类DAO
	 */

	private DataBaseDAO databaseDAO;
	private SellOrderDAO sellOrderDAO;
	// 类型：激活充值
	private static final String BATCH_TYPE = "03";
	private Java2ACCBusinessServiceImpl java2ACCBusinessService;
	private OrderBO orderBO;

	@Override
	public String getBatchType() {
		return BATCH_TYPE;
	}

	@Override
	public void commMessage(BatchFileDTO batchFileDTO)
			throws BizServiceException {
		try {
			AccPackageDTO accPackageDTO = new AccPackageDTO();
			accPackageDTO.setTxnCode(Const.TXN_TYPE_CODE_RECHARGE);
			accPackageDTO.setTxnDate(DateUtil.getCurrentDateStr());
			accPackageDTO.setTxnTime(DateUtil.getCurrenTime24());
			accPackageDTO.setBatchNo(batchFileDTO.getBatchNo());
			// 批次信息 批次类型|总比数+'|'+总金额+'|'+剩余笔数+'|'+剩余金额
			accPackageDTO.setBatchFileInfo(BATCH_TYPE + "|"
					+ batchFileDTO.getTotCnt() + "|" + batchFileDTO.getTotAmt()
					+ "|" + 0 + "|" + 0);
			accPackageDTO = java2ACCBusinessService
					.sendRechargeMessage(accPackageDTO);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("");
		}
	}

	public Java2ACCBusinessServiceImpl getJava2ACCBusinessService() {
		return java2ACCBusinessService;
	}

	public void setJava2ACCBusinessService(
			Java2ACCBusinessServiceImpl java2accBusinessService) {
		java2ACCBusinessService = java2accBusinessService;
	}

	public DataBaseDAO getDatabaseDAO() {
		return databaseDAO;
	}

	public void setDatabaseDAO(DataBaseDAO databaseDAO) {
		this.databaseDAO = databaseDAO;
	}

	/**
	 * 批次完成，更改订单状态
	 * 用于更改订单状态，进行相应的业务处理
	 */
	//FIXME 以后需要考虑是否加入Exception机制，比如说获取到的批次号跟所对应的批次类型不符
	//遇到这种情况，直接抛出exception
	@Override
	protected boolean orderStateChange(BatchSum sum) {
		//查询订单信息
		SellOrder sellOrder = sellOrderDAO.selectByPrimaryKey(sum.getOrderId());
		//如果订单状态已经为完成状态,直接返回true
		if (OrderConst.ORDER_STATE_ORDER_PROCESS_FAIL.equals(sellOrder.getOrderState())||
				OrderConst.ORDER_STATE_ORDER_SEND_CONFIRM.equals(sellOrder.getOrderState())) {
			return true;
		}
		//检查批次状态
		//失败总数 > 0 或者 成功总数 + 失败总数 != 总记录数, 将订单状态置为失败
		if (Long.parseLong(sum.getFailCnt().trim())> 0 ||
				Long.parseLong(sum.getSucCnt().trim()) + Long.parseLong(sum.getFailCnt().trim()) != Long.parseLong(sum.getTotCnt().trim())||
				Long.parseLong(sum.getSucAmt().trim()) + Long.parseLong(sum.getFailAmt().trim()) != Long.parseLong(sum.getTotAmt().trim())) {
			
			sellOrder.setOrderState(OrderConst.ORDER_STATE_ORDER_PROCESS_FAIL);
		} else {
			sellOrder.setOrderState(OrderConst.ORDER_STATE_ORDER_SEND_CONFIRM);
		}
		
		//获取订单激活状态
		String actFlag="0";
		List<String> actFlags = (List<String>)databaseDAO.queryForList("RECHARGE_ACT.getOrderActFlag", sum.getBatchNo());
		if(null != actFlags && actFlags.size()>0){
			actFlag = actFlags.get(0);
		}
		//更新订单状态
		SellOrderExample updateDTO = new SellOrderExample();
		updateDTO.createCriteria().andOrderIdEqualTo(sellOrder.getOrderId());
		sellOrder.setInitActStat(actFlag);
		sellOrder.setModifyTime(DateUtil.getCurrentTime());
		sellOrderDAO.updateByExampleSelective(sellOrder, updateDTO);
		//添加流程节点
		insertOrderFlow(sellOrder);
		return true;
	}
	
	
	private void insertOrderFlow(SellOrder sellOrder){
		try {
			//订单激活时添加激活流程节点
			if(null !=sellOrder.getInitActStat() && "1".equals(sellOrder.getInitActStat().trim())){
				orderBO.orderFlow(sellOrder.getOrderId(),
						sellOrder.getProcessEntityId(),
						OrderConst.ORDER_FLOW_OPRATION_CONFIRMATION,
						OrderConst.ORDER_FLOW_ORDER_ACTIVE, "", "0000");
			}
		} catch (BizServiceException e) {
			this.logger.error(e.getMessage());
		}
	}

	@Override
	public void insertBatch(List<? extends BatchParamInterface> businessList)
			throws BizServiceException {
		List<BatchFileDetailDTO> list = new ArrayList<BatchFileDetailDTO>();
		for (BatchParamInterface obj : businessList) {
			SellOrderListDTO dto = (SellOrderListDTO) obj;
			BatchFileDetailDTO batchFileDetailDTO = new BatchFileDetailDTO();
			batchFileDetailDTO.setTxnSeq(dto.fetchSeq());
			batchFileDetailDTO.setSmltDate(DateUtil.getCurrentDateStr());
			batchFileDetailDTO.setBatchType(BATCH_TYPE);
			batchFileDetailDTO.setTxnAmt(dto.getFaceValue());
			batchFileDetailDTO.setTxnState(BatchFileDetailDTO.TXN_STATE_INIT);
			batchFileDetailDTO.setCardNo(dto.getCardNo());
			batchFileDetailDTO.setAccType(dto.getServiceId());
			batchFileDetailDTO.setExpDate(dto.getValidityPeriod());
			batchFileDetailDTO.setActFlag(dto.getActFlag());
			batchFileDetailDTO.setRecCrtTs(DateUtil.getCurrentTime());
			batchFileDetailDTO.setRecUpdTs(DateUtil.getCurrentTime());
			list.add(batchFileDetailDTO);
		}
		databaseDAO.batchInsert("RECHARGE_ACT.insertRechargeActTxn", list);
	}

	public SellOrderDAO getSellOrderDAO() {
		return sellOrderDAO;
	}

	public void setSellOrderDAO(SellOrderDAO sellOrderDAO) {
		this.sellOrderDAO = sellOrderDAO;
	}

	public OrderBO getOrderBO() {
		return orderBO;
	}

	public void setOrderBO(OrderBO orderBO) {
		this.orderBO = orderBO;
	}
	
	
}

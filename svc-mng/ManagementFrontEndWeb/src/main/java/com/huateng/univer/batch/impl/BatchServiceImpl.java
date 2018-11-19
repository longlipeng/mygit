package com.huateng.univer.batch.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.allinfinance.framework.dto.BatchActivateDTO;
import com.allinfinance.framework.dto.BatchRechargeDTO;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.businessbatch.dto.BatchFileDetailDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderQueryDTO;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.constant.OrderConst;
import com.huateng.framework.dao.BaseDAO;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.dao.DataBaseDAO;
import com.huateng.framework.dao.PageQueryDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.BatchSumDAO;
import com.huateng.framework.ibatis.dao.SellOrderDAO;
import com.huateng.framework.ibatis.dao.TableSerialNumberDAO;
import com.huateng.framework.ibatis.model.SellOrder;
import com.huateng.framework.ibatis.model.SellOrderExample;
import com.huateng.framework.ibatis.model.TableSerialNumber;
import com.huateng.framework.ibatis.model.TableSerialNumberExample;
import com.huateng.hstserver.gatewayService.Java2ACCBusinessServiceImpl;
import com.huateng.hstserver.model.AccPackageDTO;
import com.huateng.univer.batch.BatchService;

public class BatchServiceImpl implements BatchService {
	private BatchSumDAO batchSumDAO;
	private SellOrderDAO sellOrderDAO;
	private Logger logger = Logger.getLogger(BatchServiceImpl.class);
//	private SubmitOrderService submitOrderService;
	private PageQueryDAO pageQueryDAO;
	private BaseDAO baseDAO;
	private CommonsDAO commonsDAO;
	private DataBaseDAO databaseDAO;

	//	private BusinessBatchService rechargeActBatchService;
//	private BusinessBatchService rechargeBatchService;
	private Java2ACCBusinessServiceImpl java2ACCBusinessService;

	@Override
	public String getOrderState(SellOrderDTO sellOrderDTO) throws BizServiceException{
		List<String> states = new ArrayList<String>();
		states.add(OrderConst.ORDER_STATE_ORDER_PROCESS_FAIL);
		String result = null;
		if("act".equals(sellOrderDTO.getBatchType())){
			result = getActState(sellOrderDTO);
			return result;
		}else if("rechargeAct".equals(sellOrderDTO.getBatchType())){
			states.add(OrderConst.ORDER_STATE_ORDER_SEND_CONFIRM);
		}else if ("recharge".equals(sellOrderDTO.getBatchType())){
			states.add(OrderConst.ORDER_STATE_CREDIT_SUCCESS);
		}else if("makeCard".equals(sellOrderDTO.getBatchType())){
			states.add(OrderConst.ORDER_STATE_CARDGFILE_MAKING);
		}else if ("ransom".equals(sellOrderDTO.getBatchType())){
			states.add(OrderConst.ORDER_STATE_ORDER_RANSOM_STOCK);
		}
		sellOrderDTO.setOrderStates(states);
		result = checkOrderState(sellOrderDTO);
		return result;
	}

	@Override
	public PageDataDTO rechargeActBatchDetailGet(
			BatchFileDetailDTO batchFileDetailDTO) throws BizServiceException {
		try {

			PageDataDTO pageDataDTO = pageQueryDAO.query(
					"RECHARGE_ACT.getRechargeActState", batchFileDetailDTO);
			return pageDataDTO;
		} catch (Exception e) {

			this.logger.error(e.getMessage());
			throw new BizServiceException("查询批量明细失败");
		}
	}

	@Override
	public PageDataDTO rechargeBatchDetailGet(
			BatchFileDetailDTO batchFileDetailDTO) throws BizServiceException {
		try {

			PageDataDTO pageDataDTO = pageQueryDAO.query(
					"RECHARGE.getRechargeState", batchFileDetailDTO);
			return pageDataDTO;
		} catch (Exception e) {

			this.logger.error(e.getMessage());
			throw new BizServiceException("查询批量明细失败");
		}
	}
	
	@Override
	public PageDataDTO makeCardBatchDetailGet(
			BatchFileDetailDTO batchFileDetailDTO) throws BizServiceException {
		try {

			PageDataDTO pageDataDTO = pageQueryDAO.query(
					"MAKE_CARD.getMakeCardState", batchFileDetailDTO);
			return pageDataDTO;
		} catch (Exception e) {

			this.logger.error(e.getMessage());
			throw new BizServiceException("查询批量明细失败");
		}
	}
	@Override
	public PageDataDTO ransomBatchDetailGet(
			BatchFileDetailDTO batchFileDetailDTO) throws BizServiceException {
		try {

			PageDataDTO pageDataDTO = pageQueryDAO.query(
					"RANSOM.getRansomState", batchFileDetailDTO);
			return pageDataDTO;
		} catch (Exception e) {

			this.logger.error(e.getMessage());
			throw new BizServiceException("查询批量明细失败");
		}
	}
	
	//获取激活状态
	private String getActState(SellOrderDTO sellOrderDTO){
		SellOrder sellOrder = sellOrderDAO.selectByPrimaryKey(sellOrderDTO
				.getOrderId());
		if (DataBaseConstant.ORDER_ACT_STATE_ACT.equals(sellOrder
				.getInitActStat())
				|| DataBaseConstant.ORDER_ACT_STATE_ACT_FAILED
						.equals(sellOrder.getInitActStat())) {
			return "success";
		}
		return null;
	}

	//检查订单状态
	private String checkOrderState(SellOrderDTO sellOrderDTO){
		SellOrderExample example = new SellOrderExample();
		example.createCriteria().andOrderIdEqualTo(sellOrderDTO.getOrderId()).andOrderStateIn(sellOrderDTO.getOrderStates());
		List<SellOrder> sellOrders =sellOrderDAO.selectByExample(example);
		if(null==sellOrders || sellOrders.size()<=0){
			return null;
		}
		return "success";
	}
	/**
	 * 批量充值 失败后换卡
	 */
	public int changeCardNo(SellOrderQueryDTO sellOrderQueryDTO)
			throws BizServiceException {
		int state ;
		try {
			state = baseDAO.update("RECHARGE.CHANGE_CARD", sellOrderQueryDTO);
		} catch (Exception e) {
			throw new BizServiceException("更新失败!");
		}
		return state;
	}
	
	//查询批量充值
	public PageDataDTO getBatchRechargeInfo(BatchRechargeDTO batchRechargeDTO)throws BizServiceException{
		try {

			PageDataDTO pageDataDTO = pageQueryDAO.query(
					"RANSOM.getBatchRechargeInfo", batchRechargeDTO);
		return pageDataDTO;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询列表失败");
		}
		
	}
	
	//批量充值
	public void toBatchRechargeInfo(BatchRechargeDTO batchRechargeDTO)throws BizServiceException{
		StringBuffer data = new StringBuffer();
		for (int i = 0; i < batchRechargeDTO.getList().size(); i++) {
			data.append(batchRechargeDTO.getList().get(i));
		}
			try {
				AccPackageDTO accPackageDTO=java2ACCBusinessService.batchRecharge(data.toString(),batchRechargeDTO.getCreateUser());
				if(!accPackageDTO.getRespCode().equals("00")){
					throw new BizServiceException(accPackageDTO.getCallBack());
				}
			} catch (com.huateng.hstserver.exception.BizServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new BizServiceException("批量充值失败！");
				
			}
	}
	
	//查询所有激活批次
	@Override
	public PageDataDTO getBatchActivateList(BatchActivateDTO batchActivateDTO)
			throws BizServiceException {
		try {

			PageDataDTO pageDataDTO = pageQueryDAO.query(
					"RANSOM.getBatchActivateList", batchActivateDTO);
		return pageDataDTO;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询列表失败");
		}
	}

	//查询该批次下卡
	@Override
	public PageDataDTO getBatchActivateDetail(BatchActivateDTO batchActivateDTO)
			throws BizServiceException {
		try {

			PageDataDTO pageDataDTO = pageQueryDAO.query(
					"RANSOM.getBatchActivateDetail", batchActivateDTO);
		return pageDataDTO;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询列表失败");
		}
	}
	
	//查询该批次下卡
	@Override
	public PageDataDTO getBatchActivateCardDetail(BatchActivateDTO batchActivateDTO)
			throws BizServiceException {
		try {

			PageDataDTO pageDataDTO = pageQueryDAO.query(
					"RANSOM.getBatchActivateCardDetail", batchActivateDTO);
		return pageDataDTO;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询列表失败");
		}
	}
	
	private TableSerialNumberDAO tableSerialNumberDAO;
	
	
	public TableSerialNumberDAO getTableSerialNumberDAO() {
		return tableSerialNumberDAO;
	}

	public void setTableSerialNumberDAO(TableSerialNumberDAO tableSerialNumberDAO) {
		this.tableSerialNumberDAO = tableSerialNumberDAO;
	}

	@Override
	public void toBatchActivate(BatchActivateDTO batchActivateDTO)
			throws BizServiceException {
		try {
			int batNo = this.updateBatchNo();
			//插入批次数据到TBL_RECHARGE_BATCH_DETAIL表
			List<BatchRechargeDTO> batchRechargeDTOs = batchActivateDTO.getRecList();
			List<BatchRechargeDTO> rechargeDTOs = new ArrayList<BatchRechargeDTO>();
			for(BatchRechargeDTO batchRechargeDTO:batchRechargeDTOs){
				batchRechargeDTO.setBacthNo(String.valueOf(batNo));
				batchRechargeDTO.setRechargeTxnSeqNo("000000000000");
				rechargeDTOs.add(batchRechargeDTO);
			}
			databaseDAO.batchInsert("RANSOM.batchInsertRechargeDTO", rechargeDTOs);
			
			batchActivateDTO.setActivateState("01");
			batchActivateDTO.setBatchNo(String.valueOf(batNo));
			baseDAO.insert("RANSOM.insertActivate", batchActivateDTO);
			java2ACCBusinessService.batchActivate();
		} catch (com.huateng.hstserver.exception.BizServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BizServiceException("批量激活失败！");
		}
	}
	
	private int updateBatchNo(){
		synchronized(this){
			int serialNumber = 0;
			int batNo = 0;
			try {
				batNo = Integer.parseInt(commonsDAO.getNextValue("TB_VALID_RULE_DSP"));
				serialNumber = batNo+1;
				TableSerialNumberExample tableSerialNumber = new TableSerialNumberExample();
				tableSerialNumber.createCriteria().andTableNameEqualTo("TB_VALID_RULE_DSP");
				List<TableSerialNumber> list=tableSerialNumberDAO.selectByExample(tableSerialNumber);
				TableSerialNumber tableSerial = list.get(0);
				tableSerial.setTableName("TB_VALID_RULE_DSP");
				tableSerial.setSerialNumber(String.valueOf(serialNumber));
				baseDAO.update("TB_TABLE_SERIAL_NUMBER.abatorgenerated_updateByPrimaryKey", tableSerial);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BizServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return batNo;
		}
	}
	
	public BatchSumDAO getBatchSumDAO() {
		return batchSumDAO;
	}

	public void setBatchSumDAO(BatchSumDAO batchSumDAO) {
		this.batchSumDAO = batchSumDAO;
	}

	public SellOrderDAO getSellOrderDAO() {
		return sellOrderDAO;
	}

	public void setSellOrderDAO(SellOrderDAO sellOrderDAO) {
		this.sellOrderDAO = sellOrderDAO;
	}

	public PageQueryDAO getPageQueryDAO() {
		return pageQueryDAO;
	}

	public void setPageQueryDAO(PageQueryDAO pageQueryDAO) {
		this.pageQueryDAO = pageQueryDAO;
	}

	public BaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	public Java2ACCBusinessServiceImpl getJava2ACCBusinessService() {
		return java2ACCBusinessService;
	}

	public void setJava2ACCBusinessService(
			Java2ACCBusinessServiceImpl java2accBusinessService) {
		java2ACCBusinessService = java2accBusinessService;
	}
	
	public CommonsDAO getCommonsDAO() {
		return commonsDAO;
	}

	public void setCommonsDAO(CommonsDAO commonsDAO) {
		this.commonsDAO = commonsDAO;
	}

	public DataBaseDAO getDatabaseDAO() {
		return databaseDAO;
	}

	public void setDatabaseDAO(DataBaseDAO databaseDAO) {
		this.databaseDAO = databaseDAO;
	}

	
//	public BusinessBatchService getRechargeActBatchService() {
//		return rechargeActBatchService;
//	}
//
//	public void setRechargeActBatchService(
//			BusinessBatchService rechargeActBatchService) {
//		this.rechargeActBatchService = rechargeActBatchService;
//	}
//
//	public BusinessBatchService getRechargeBatchService() {
//		return rechargeBatchService;
//	}
//
//	public void setRechargeBatchService(
//			BusinessBatchService rechargeBatchService) {
//		this.rechargeBatchService = rechargeBatchService;
//	}
//
//	public SubmitOrderService getSubmitOrderService() {
//		return submitOrderService;
//	}
//
//	public void setSubmitOrderService(SubmitOrderService submitOrderService) {
//		this.submitOrderService = submitOrderService;
//	}

}

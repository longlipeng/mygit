package com.huateng.univer.order.business.service.impl;

import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.ibatis.dao.OrderBatchFileDAO;
import com.huateng.framework.ibatis.model.OrderBatchFile;
import com.huateng.framework.util.DateUtil;
import com.huateng.univer.order.business.service.OrderBatchFileService;

public class OrderBatchFileServiceImpl implements OrderBatchFileService {

	private OrderBatchFileDAO orderBatchFileDAO;

	public void insertOrderFile(String fileName, SellOrderDTO sellOrderDTO) {
		OrderBatchFile orderBatchFile = new OrderBatchFile();
		orderBatchFile.setBatchNumber(fileName);
		orderBatchFile.setOrderId(sellOrderDTO.getOrderId());
		orderBatchFile.setCreateTime(DateUtil.getCurrentTime());
		orderBatchFile.setCreateUser(sellOrderDTO.getLoginUserId());
		orderBatchFile.setModifyTime(DateUtil.getCurrentTime());
		orderBatchFile.setModifyUser(sellOrderDTO.getLoginUserId());
		orderBatchFile.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
		orderBatchFileDAO.insert(orderBatchFile);
	}

	public OrderBatchFileDAO getOrderBatchFileDAO() {
		return orderBatchFileDAO;
	}

	public void setOrderBatchFileDAO(OrderBatchFileDAO orderBatchFileDAO) {
		this.orderBatchFileDAO = orderBatchFileDAO;
	}

}

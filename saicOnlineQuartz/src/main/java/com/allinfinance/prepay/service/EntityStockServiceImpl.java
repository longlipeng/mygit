package com.allinfinance.prepay.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allinfinance.prepay.dao.CommonsDAO;
import com.allinfinance.prepay.mapper.svc_mng.EntityStockOperaterMapper;
import com.allinfinance.prepay.model.EntityStockOperater;
import com.allinfinance.prepay.model.EntityStockOperaterExample;
import com.allinfinance.prepay.utils.DateUtil;
@Service
public class EntityStockServiceImpl implements EntityStockService {

	@Autowired
	private CommonsDAO commonsDAO;
	@Autowired
	private EntityStockOperaterMapper entityStockOperaterMapper;
	
	@Override
	public void addStockOperaterRecord(String cardNo, String orderId,
			String entityId, String functionRoleId, String orderFlowNode,
			String operaterType, String stockState, String operateUser)
			throws Exception {
		// TODO Auto-generated method stub
	
			EntityStockOperater stockOperater = new EntityStockOperater();
			stockOperater.setOperaterId(commonsDAO
					.getNextValueOfSequence("TB_ENTITY_STOCK_OPERATER"));
			stockOperater.setEntityId(entityId);
			stockOperater.setFuncationRoleId(functionRoleId);
			stockOperater.setOperaterType(operaterType);
			stockOperater.setOrderFlowNode(orderFlowNode);
			stockOperater.setStockState(stockState);
			stockOperater.setCardNo(cardNo);
			stockOperater.setOrderId(orderId);
			stockOperater.setCreateUser(operateUser);
			stockOperater.setCreateTime(DateUtil.getCurrentTime());
			stockOperater.setModifyUser(operateUser);
			stockOperater.setModifyTime(DateUtil.getCurrentTime());
			stockOperater.setDataState("1");

			entityStockOperaterMapper.insert(stockOperater);
		/*commonsDAO.batchInsert(
				"TB_ENTITY_STOCK_OPERATER.abatorgenerated_insert",
				stockOperaterList);*/

	}

	@Override
	public void delStockOperaterRecord(String orderId) throws Exception {
		EntityStockOperaterExample ex =new EntityStockOperaterExample();
		ex.createCriteria().andOrderIdEqualTo(orderId);
		entityStockOperaterMapper.deleteByExample(ex);
		
	}
	
	

}

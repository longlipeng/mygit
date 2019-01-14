package com.allinfinance.prepay.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allinfinance.prepay.dao.CommonsDAO;
import com.allinfinance.prepay.mapper.svc_mng.SellOrderFlowMapper;
import com.allinfinance.prepay.model.SellOrderFlow;
import com.allinfinance.prepay.utils.DateUtil;
import com.allinfinance.univer.seller.order.dto.SellOrderDTO;

@Service
public class StockOrderCommonServiceImpl implements StockOrderCommonService {

	@Autowired
	private CommonsDAO commonsDAO;
	@Autowired
	private SellOrderFlowMapper sellOrderFlowMapper;
	@Override
	public void addNewOrderFlow(SellOrderDTO sellOrderDTO,
			String orderFlowNode, String operateType) throws Exception {
		// 生称sellOrderFlow对象
				SellOrderFlow orderFlow = new SellOrderFlow();
				// 取SellOrderFlow的流水号
				orderFlow.setOrderFlowId(commonsDAO
						.getNextValueOfSequence("TB_SELL_ORDER_FLOW"));
				orderFlow.setOrderId(sellOrderDTO.getOrderId());
				orderFlow.setEntityId(sellOrderDTO.getDefaultEntityId());
				orderFlow.setOrderFlowNode(orderFlowNode);
				orderFlow.setOperateType(operateType);
				orderFlow.setCreateUser(sellOrderDTO.getLoginUserId());
				orderFlow.setModifyUser(sellOrderDTO.getLoginUserId());
				orderFlow.setCreateTime(DateUtil.getCurrentTime());
				orderFlow.setModifyTime(DateUtil.getCurrentTime());
				orderFlow.setDataState("1");
				sellOrderFlowMapper.insert(orderFlow);

	}

}

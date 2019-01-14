package com.huateng.service.issueOperation.cardRechargeAndChargeback.service;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import com.allinfinance.service.issueOperation.cardRechargeAndChargeback.CardRechargeAndChargebackDTO;
import com.huateng.service.BizBaseService;
import com.huateng.service.BizService;

/**
 * 预付卡充值充退报表
 * 
 */
public class CardRechargeAndChargebackService extends BizBaseService implements
		BizService {
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getList(JSONObject jsonDto) {
		CardRechargeAndChargebackDTO cracd = (CardRechargeAndChargebackDTO) JSONObject
				.toBean(jsonDto, CardRechargeAndChargebackDTO.class);
		// 查询充值充退信息
		List<CardRechargeAndChargebackDTO> cracdList = baseDao
				.queryForList("cardRechargeAndChargeback",
						"cardRechargeAndChargeback", cracd);
		// 查询日期内的所有bankCard支付过的的订单号和卡号
		List<CardRechargeAndChargebackDTO> cardNoList = baseDao.queryForList(
				"cardRechargeAndChargeback", "GET_CARD_NO", cracd);
		List<Object> objList = new ArrayList<Object>();
		for (CardRechargeAndChargebackDTO crResult : cracdList) {
			CardRechargeAndChargebackDTO cr = new CardRechargeAndChargebackDTO();
			cr.setAccountBalance(crResult.getAccountBalance());
			cr.setAdditionalFee(crResult.getAdditionalFee());
			cr.setBankCard(crResult.getBankCard());
			cr.setCardAmount(crResult.getCardAmount());
			cr.setCash(crResult.getCash());
			cr.setCheque(crResult.getCheque());
			cr.setEndDate(crResult.getEndDate());
			cr.setOrderDate(crResult.getOrderDate());
			cr.setOrderId(crResult.getOrderId());
			cr.setOrderType(crResult.getOrderType());
			cr.setSaleMan(crResult.getSaleMan());
			cr.setStartDate(crResult.getStartDate());
			cr.setTelegraph(crResult.getTelegraph());
			cr.setTotalPrice(crResult.getTotalPrice());
			cr.setIssueName(crResult.getIssueName());
			// 如果查询返回结果 bankCard 不为空，那么遍历
			if (crResult.getBankCard() != null
					//&& !"".equals(crResult.getBankCard())
					&& crResult.getOrderId() != null
					&& !"".equals(crResult.getOrderId())) {
				// 遍历返回的订单号和卡号信息
				for (CardRechargeAndChargebackDTO cardInfo : cardNoList) {
					// 如果订单号相等，那么将查询返回的卡号赋值给objList
					if (cardInfo.getOrderId().equals(cr.getOrderId())) {
						if (cr.getCardNo() != null
								&& !"".equals(cr.getCardNo())) {
							String cardNum = cr.getCardNo() + ","
									+ cardInfo.getCardNo();
							cr.setCardNo(cardNum);
						} else {
							cr.setCardNo(cardInfo.getCardNo());
						}
					}
				}
			}
			objList.add(cr);
		}
		return objList;

	}

}

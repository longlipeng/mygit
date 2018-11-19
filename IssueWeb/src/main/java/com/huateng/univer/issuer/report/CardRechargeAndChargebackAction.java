package com.huateng.univer.issuer.report;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.univer.report.dto.CardRechargeAndChargebackDTO;
import com.allinfinance.univer.seller.seller.dto.SellerDTO;
import com.allinfinance.univer.system.user.dto.UserDTO;
import com.huateng.univer.report.action.NewIreportAction;

/**
 * 预付卡充值充退报表
 * 
 * @author Administrator
 * 
 */
@SuppressWarnings("unchecked")
public class CardRechargeAndChargebackAction extends NewIreportAction {
	private static final long serialVersionUID = -8574933850016226080L;
	private CardRechargeAndChargebackDTO cardRechargeAndChargebackDTO = new CardRechargeAndChargebackDTO();
	private SellerDTO sellerDTO = new SellerDTO();
	List<UserDTO> userDTOs = new ArrayList<UserDTO>();
	List<SellerDTO> sellerDTOs = new ArrayList<SellerDTO>();

	/**
	 * @throws Exception
	 */
	public String inQuery() throws Exception {
		cardRechargeAndChargebackDTO
				.setReportName("card_recharge_and_chargeback");
		cardRechargeAndChargebackDTO.setReportType("xls");
		cardRechargeAndChargebackDTO.setReportFileName("预付卡充值充退报表");
		sellerDTO.setEntityId(this.getUser().getEntityId());
		cardRechargeAndChargebackDTO.setFatherEntityId(getUser().getEntityId());
		//根据发行机构号查找发行机构下所有营销机构
		sellerDTOs = (List<SellerDTO>) this.sendService(
				ConstCode.GET_OTHER_SELLERS, sellerDTO).getDetailvo();
		//如果前台选择了营销机构，则查找机构下所有的销售人员
		if (cardRechargeAndChargebackDTO.getIssuerId() != null
				&& cardRechargeAndChargebackDTO != null
				&& !"".equals(cardRechargeAndChargebackDTO.getIssuerId())) {
			sellerDTO.setEntityId(cardRechargeAndChargebackDTO.getIssuerId());
			userDTOs = (List<UserDTO>) this.sendService(
					ConstCode.GET_SALE_MAN_OF_SELLERS, sellerDTO).getDetailvo();
		}
		return "list";
	}

	protected JSONObject getJSONOBJect() {
		return JSONObject.fromObject(cardRechargeAndChargebackDTO);
	}

	public CardRechargeAndChargebackDTO getCardRechargeAndChargebackDTO() {
		return cardRechargeAndChargebackDTO;
	}

	public void setCardRechargeAndChargebackDTO(
			CardRechargeAndChargebackDTO cardRechargeAndChargebackDTO) {
		this.cardRechargeAndChargebackDTO = cardRechargeAndChargebackDTO;
	}

	public SellerDTO getSellerDTO() {
		return sellerDTO;
	}

	public void setSellerDTO(SellerDTO sellerDTO) {
		this.sellerDTO = sellerDTO;
	}

	public List<SellerDTO> getSellerDTOs() {
		return sellerDTOs;
	}

	public void setSellerDTOs(List<SellerDTO> sellerDTOs) {
		this.sellerDTOs = sellerDTOs;
	}

	public List<UserDTO> getUserDTOs() {
		return userDTOs;
	}

	public void setUserDTOs(List<UserDTO> userDTOs) {
		this.userDTOs = userDTOs;
	}

}

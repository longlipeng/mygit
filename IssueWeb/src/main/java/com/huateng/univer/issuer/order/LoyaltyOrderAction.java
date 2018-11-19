package com.huateng.univer.issuer.order;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.univer.issuer.dto.issuerContract.LoyaltyContractDTO;
import com.allinfinance.univer.issuer.dto.product.ProductDTO;
import com.allinfinance.univer.seller.order.dto.SellBuyOrderDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderListDTO;
import com.allinfinance.univer.seller.sellercontract.dto.SellerContractDTO;
import com.huateng.framework.constant.OrderConst;

/**
 * 待发卡订单
 * 
 * @author xxl
 * 
 */
public class LoyaltyOrderAction extends StockOrderBaseAction {

	private static final long serialVersionUID = -1954181998453401664L;

	private SellBuyOrderDTO sellBuyOrderDTO = new SellBuyOrderDTO();

	private String[] choose;

	private ProductDTO productDTO;

	private List<LoyaltyContractDTO> loyaltyContractDTOs = new ArrayList<LoyaltyContractDTO>();

	private List<SellOrderListDTO> sellOrderListDTOs = new ArrayList<SellOrderListDTO>();

	private int loyaltyOrderList_totalRows = 0;

	private String[] cardLayoutIdList;
	
	private String[] faceValueTypeList;

	private String[] faceValueList;
	
	private String[] cardAmountList;
	
	public String[] getCardAmountList() {
		return cardAmountList;
	}

	public void setCardAmountList(String[] cardAmountList) {
		this.cardAmountList = cardAmountList;
	}

	
	public String[] getFaceValueList() {
		return faceValueList;
	}

	public void setFaceValueList(String[] faceValueList) {
		this.faceValueList = faceValueList;
	}

	public String[] getCardLayoutIdList() {
		return cardLayoutIdList;
	}

	public String[] getFaceValueTypeList() {
		return faceValueTypeList;
	}

	public void setFaceValueTypeList(String[] faceValueTypeList) {
		this.faceValueTypeList = faceValueTypeList;
	}

	public void setCardLayoutIdList(String[] cardLayoutIdList) {
		this.cardLayoutIdList = cardLayoutIdList;
	}

	public int getLoyaltyOrderList_totalRows() {
		return loyaltyOrderList_totalRows;
	}

	public void setLoyaltyOrderList_totalRows(int loyaltyOrderListTotalRows) {
		loyaltyOrderList_totalRows = loyaltyOrderListTotalRows;
	}

	public ProductDTO getProductDTO() {
		return productDTO;
	}

	public List<LoyaltyContractDTO> getLoyaltyContractDTOs() {
		return loyaltyContractDTOs;
	}

	public void setLoyaltyContractDTOs(List<LoyaltyContractDTO> loyaltyContractDTOs) {
		this.loyaltyContractDTOs = loyaltyContractDTOs;
	}

	public List<SellOrderListDTO> getSellOrderListDTOs() {
		return sellOrderListDTOs;
	}

	public void setSellOrderListDTOs(List<SellOrderListDTO> sellOrderListDTOs) {
		this.sellOrderListDTOs = sellOrderListDTOs;
	}

	public void setProductDTO(ProductDTO productDTO) {
		this.productDTO = productDTO;
	}

	public String[] getChoose() {
		return choose;
	}

	public void setChoose(String[] choose) {
		this.choose = choose;
	}

	public SellBuyOrderDTO getSellBuyOrderDTO() {
		return sellBuyOrderDTO;
	}

	public void setSellBuyOrderDTO(SellBuyOrderDTO sellBuyOrderDTO) {
		this.sellBuyOrderDTO = sellBuyOrderDTO;
	}

	@Override
	protected void initActionName() {
		actionName = "loyaltyOrderAction";
	}

	@Override
	protected void inqueryInit() {
		sellOrderQueryDTO
				.setOrderTypeArray(OrderConst.ORDER_TYPE_ORDER_BUY_ISSUER_ISSUER_SIGN
						+ ","
						+ OrderConst.ORDER_TYPE_ORDER_BUY_ISSUER_ISSUER_UNSIGN);

	}

	
}

package com.huateng.univer.report.cardRechargeAndChargeback.impl;

import java.util.ArrayList;
import java.util.List;

import com.allinfinance.univer.seller.seller.dto.SellerDTO;
import com.allinfinance.univer.seller.seller.dto.SellerQueryDTO;
import com.allinfinance.univer.system.user.dto.UserDTO;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.SellerDAO;
import com.huateng.framework.ibatis.model.Seller;
import com.huateng.framework.ibatis.model.SellerExample;
import com.huateng.framework.ibatis.model.User;
import com.huateng.framework.ibatis.model.UserExample;
import com.huateng.univer.report.cardRechargeAndChargeback.CardRechargeAndChargebackService;
import com.huateng.univer.system.user.integration.dao.UserServiceDAO;

/**
 * 卡充值充退报表
 */
public class CardRechargeAndChargebackServiceImpl implements
		CardRechargeAndChargebackService {
	private SellerDAO sellerDAO;
	private CommonsDAO commonsDAO;
	List<Seller> sellers = new ArrayList<Seller>();
	protected UserServiceDAO userServiceDAO;

	@SuppressWarnings("unchecked")
    public List<SellerDTO> getAllSeller(SellerDTO sellerDTO)
			throws BizServiceException {
//		List<SellerDTO> sellerDTOs = new ArrayList<SellerDTO>();
//		try {
//			//查询发行机构下的所有营销机构
//		    List<SellerDTO> sellerList = (List<SellerDTO>) commonsDAO
//	        .queryForList("SELLER.selectSellerDTOList", sellerDTO);
//			for (SellerDTO sell : sellerList) {
//				SellerDTO sellDTO = new SellerDTO();
//				sellDTO.setEntityId(sell.getEntityId());
//				sellDTO.setSellerName(sell.getSellerName());
//				sellerDTOs.add(sellDTO);
//			}
//		} catch (Exception e) {
//			throw new BizServiceException("查询营销机构失败!");
//		}
//		return sellerDTOs;
	    List<SellerDTO> sellerList = (List<SellerDTO>) commonsDAO
	            .queryForList("SELLER.selectSellerDTOList", sellerDTO);
	    
	    return sellerList;
	}
	/**
	 * 获取营销机构下所有销售人员
	 */
	public List<UserDTO> getSaleManOfSeller(SellerDTO sellerDTO)
			throws BizServiceException {
		UserExample example = new UserExample();
		example.createCriteria().andDataStateEqualTo(
				DataBaseConstant.DATA_STATE_NORMAL).andIsSaleFlageEqualTo(
				DataBaseConstant.DATA_TYPE_YES).andEntityIdEqualTo(
				sellerDTO.getEntityId());
		List<User> userList = userServiceDAO.selectByExample(example);
		List<UserDTO> userDTOList = new ArrayList<UserDTO>();
		for (User user : userList) {
			UserDTO userDTO = new UserDTO();
			userDTO.setUserId(user.getUserId());
			userDTO.setUserName(user.getUserName());
			userDTOList.add(userDTO);
		}
		return userDTOList;
	}

	public SellerDAO getSellerDAO() {
		return sellerDAO;
	}

	public void setSellerDAO(SellerDAO sellerDAO) {
		this.sellerDAO = sellerDAO;
	}

	public List<Seller> getSellers() {
		return sellers;
	}

	public void setSellers(List<Seller> sellers) {
		this.sellers = sellers;
	}

	public UserServiceDAO getUserServiceDAO() {
		return userServiceDAO;
	}

	public void setUserServiceDAO(UserServiceDAO userServiceDAO) {
		this.userServiceDAO = userServiceDAO;
	}
    public CommonsDAO getCommonsDAO() {
        return commonsDAO;
    }
    public void setCommonsDAO(CommonsDAO commonsDAO) {
        this.commonsDAO = commonsDAO;
    }

	
}

package com.huateng.univer.cardmanage.biz.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.framework.dto.PageQueryDTO;
import com.allinfinance.univer.cardmanagement.dto.BatchCardActionDTO;
import com.allinfinance.univer.cardmanagement.dto.BatchCardInfoDTO;
import com.allinfinance.univer.cardmanagement.dto.CardBalanceDTO;
import com.allinfinance.univer.cardmanagement.dto.CardManagementDTO;
import com.allinfinance.univer.cardmanagement.dto.CardManagementQueryDTO;
import com.allinfinance.univer.consumer.merchant.dto.MerchantDTO;
import com.allinfinance.univer.consumer.shop.dto.ShopDTO;
import com.allinfinance.univer.issuer.dto.issuerContract.LoyaltyContractQueryDTO;
import com.allinfinance.univer.manager.transactionQuery.dto.StanStifQueryDTO;
import com.allinfinance.univer.manager.transactionQuery.dto.TransactionQueryDTO;
import com.allinfinance.univer.seller.cardholder.dto.CardholderDTO;
import com.allinfinance.univer.seller.cardholder.dto.CardholderQueryDTO;
import com.allinfinance.univer.seller.member.MemberQueryDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.constant.OrderConst;
import com.huateng.framework.dao.BaseDAO;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.dao.PageQueryDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.AccCardInfoDao;
import com.huateng.framework.ibatis.dao.CardManagementDAO;
import com.huateng.framework.ibatis.dao.CardholderCardlistDAO;
import com.huateng.framework.ibatis.dao.CardholderDAO;
import com.huateng.framework.ibatis.dao.EntityStockDAO;
import com.huateng.framework.ibatis.dao.EntityStockOperaterDAO;
import com.huateng.framework.ibatis.dao.IssuerDAO;
import com.huateng.framework.ibatis.dao.OrderAndCountDAO;
import com.huateng.framework.ibatis.dao.ProductDAO;
import com.huateng.framework.ibatis.dao.SellOrderCardListDAO;
import com.huateng.framework.ibatis.dao.SellOrderDAO;
import com.huateng.framework.ibatis.dao.SellOrderListDAO;
import com.huateng.framework.ibatis.dao.SellerDAO;
import com.huateng.framework.ibatis.dao.ServiceDAO;
import com.huateng.framework.ibatis.model.CardManagement;
import com.huateng.framework.ibatis.model.EntityStock;
import com.huateng.framework.ibatis.model.EntityStockExample;
import com.huateng.framework.ibatis.model.EntityStockOperater;
import com.huateng.framework.ibatis.model.Issuer;
import com.huateng.framework.ibatis.model.IssuerExample;
import com.huateng.framework.ibatis.model.Merchant;
import com.huateng.framework.ibatis.model.MerchantExample;
import com.huateng.framework.ibatis.model.Product;
import com.huateng.framework.ibatis.model.ProductExample;
import com.huateng.framework.ibatis.model.SellOrder;
import com.huateng.framework.ibatis.model.SellOrderCardList;
import com.huateng.framework.ibatis.model.SellOrderList;
import com.huateng.framework.ibatis.model.Seller;
import com.huateng.framework.ibatis.model.SellerExample;
import com.huateng.framework.ibatis.model.Shop;
import com.huateng.framework.ibatis.model.ShopExample;
import com.huateng.framework.util.Amount;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.ReflectionUtil;
import com.huateng.framework.util.StringUtil;
import com.huateng.framework.util.StringUtils;
import com.huateng.hstserver.constants.RspCodeMap;
import com.huateng.hstserver.gatewayService.Java2ACCBusinessServiceImpl;
import com.huateng.hstserver.gatewayService.Java2TXNBusinessServiceImpl;
import com.huateng.hstserver.model.AccPackageDTO;
import com.huateng.hstserver.model.TxnPackageDTO;
import com.huateng.univer.cardmanage.biz.service.CardManageService;
import com.huateng.univer.cardmanage.integration.dao.CardManageServiceDAO;
import com.huateng.univer.consumer.merchant.biz.service.MerchantService;
import com.huateng.univer.consumer.merchant.integration.dao.MerchantServiceDAO;
import com.huateng.univer.consumer.shop.biz.service.ShopService;
import com.huateng.univer.consumer.shop.integration.dao.ShopServiceDAO;
import com.huateng.univer.order.business.action.OrderActActionInterface;
import com.huateng.univer.seller.cardholder.biz.service.CardholderService;

public class CardManageServiceImpl implements CardManageService {
	Logger logger = Logger.getLogger(CardManageServiceImpl.class);
	private CardManagementDAO cardManagementDAO;
	private CommonsDAO commonsDAO;
	private CardholderCardlistDAO crdholderCardlistDAO;
	private CardholderDAO cardholderDAO;
	private ServiceDAO serviceDAO;
	private CardManagementDTO cardManagementDTO;
	private CardManageServiceDAO cardManageServiceDAO;
	private CardholderDTO cardholderDTO;
	private MerchantDTO merchantDTO;
	private MerchantService merchantService;
	private ShopDTO shopDTO;
	private ShopService shopService;
	private ShopExample shopExample;
	private ShopServiceDAO shopServiceDAO;
	private MerchantServiceDAO merchantServiceDAO;
	private PageDataDTO pageDataDTO = new PageDataDTO();
	private PageQueryDAO pageQueryDAO;
	private CardManagementQueryDTO cardManagementQueryDTO = new CardManagementQueryDTO();
	private IssuerDAO issuerDAO;
	private EntityStockDAO entityStockDAO;
	private SellerDAO sellerDAO;
	private BaseDAO baseDAO;
	private ProductDAO productDAO;
	private OrderAndCountDAO orderAndCountDAO;
	 /** 卡信息表DAO */
    private AccCardInfoDao accCardInfoDao;
    /** 订单DAO */
    private SellOrderDAO sellOrderDAO;
    /** 订单卡详细DAO */
    private SellOrderCardListDAO sellOrderCardListDAO;
    /** 订单详细DAO */
    private SellOrderListDAO sellOrderListDAO;
    private OrderActActionInterface orderActActionImpl;

	String respCode = "";
	String allRecord = "";
	private EntityStockOperaterDAO entityStockOperaterDAO;
	private CardholderCardlistDAO cardholderCardlistDAO;

	private Java2ACCBusinessServiceImpl java2ACCBusinessService;

	private Java2TXNBusinessServiceImpl java2TXNBusinessService;

	
	public OrderAndCountDAO getOrderAndCountDAO() {
		return orderAndCountDAO;
	}

	public void setOrderAndCountDAO(OrderAndCountDAO orderAndCountDAO) {
		this.orderAndCountDAO = orderAndCountDAO;
	}

	public OrderActActionInterface getOrderActActionImpl() {
		return orderActActionImpl;
	}

	public void setOrderActActionImpl(OrderActActionInterface orderActActionImpl) {
		this.orderActActionImpl = orderActActionImpl;
	}

	public Java2TXNBusinessServiceImpl getJava2TXNBusinessService() {
		return java2TXNBusinessService;
	}

	public void setJava2TXNBusinessService(
			Java2TXNBusinessServiceImpl java2txnBusinessService) {
		java2TXNBusinessService = java2txnBusinessService;
	}

	public Java2ACCBusinessServiceImpl getJava2ACCBusinessService() {
		return java2ACCBusinessService;
	}

	public void setJava2ACCBusinessService(
			Java2ACCBusinessServiceImpl java2accBusinessService) {
		java2ACCBusinessService = java2accBusinessService;
	}

	public CardholderCardlistDAO getCardholderCardlistDAO() {
		return cardholderCardlistDAO;
	}

	public void setCardholderCardlistDAO(
			CardholderCardlistDAO cardholderCardlistDAO) {
		this.cardholderCardlistDAO = cardholderCardlistDAO;
	}

	public EntityStockOperaterDAO getEntityStockOperaterDAO() {
		return entityStockOperaterDAO;
	}

	public void setEntityStockOperaterDAO(
			EntityStockOperaterDAO entityStockOperaterDAO) {
		this.entityStockOperaterDAO = entityStockOperaterDAO;
	}

	public ProductDAO getProductDAO() {
		return productDAO;
	}

	public void setProductDAO(ProductDAO productDAO) {
		this.productDAO = productDAO;
	}

	public BaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	public SellerDAO getSellerDAO() {
		return sellerDAO;
	}

	public void setSellerDAO(SellerDAO sellerDAO) {
		this.sellerDAO = sellerDAO;
	}

	public EntityStockDAO getEntityStockDAO() {
		return entityStockDAO;
	}

	public void setEntityStockDAO(EntityStockDAO entityStockDAO) {
		this.entityStockDAO = entityStockDAO;
	}

	public IssuerDAO getIssuerDAO() {
		return issuerDAO;
	}

	public void setIssuerDAO(IssuerDAO issuerDAO) {
		this.issuerDAO = issuerDAO;
	}

	public MerchantServiceDAO getMerchantServiceDAO() {
		return merchantServiceDAO;
	}

	public void setMerchantServiceDAO(MerchantServiceDAO merchantServiceDAO) {
		this.merchantServiceDAO = merchantServiceDAO;
	}

	private CardholderService cardholderService;

	public CardholderService getCardholderService() {
		return cardholderService;
	}

	public void setCardholderService(CardholderService cardholderService) {
		this.cardholderService = cardholderService;
	}

	public PageQueryDAO getPageQueryDAO() {
		return pageQueryDAO;
	}

	public void setPageQueryDAO(PageQueryDAO pageQueryDAO) {
		this.pageQueryDAO = pageQueryDAO;
	}

	public CardManagementQueryDTO getCardManagementQueryDTO() {
		return cardManagementQueryDTO;
	}

	public void setCardManagementQueryDTO(
			CardManagementQueryDTO cardManagementQueryDTO) {
		this.cardManagementQueryDTO = cardManagementQueryDTO;
	}

	public CardManageServiceDAO getCardManageServiceDAO() {
		return cardManageServiceDAO;
	}

	public void setCardManageServiceDAO(
			CardManageServiceDAO cardManageServiceDAO) {
		this.cardManageServiceDAO = cardManageServiceDAO;
	}

	public ShopServiceDAO getShopServiceDAO() {
		return shopServiceDAO;
	}

	public void setShopServiceDAO(ShopServiceDAO shopServiceDAO) {
		this.shopServiceDAO = shopServiceDAO;
	}

	public PageDataDTO query(String statement, PageQueryDTO parameter,
			boolean count) {

		return null;
	}

	public PageDataDTO query(String statement, PageQueryDTO parameter) {

		return null;
	}

	public PageDataDTO query(String statement, Object parameter) {

		return null;
	}

	/**
     * @return the accCardInfoDao
     */
    public AccCardInfoDao getAccCardInfoDao() {
        return accCardInfoDao;
    }

    /**
     * @param accCardInfoDao the accCardInfoDao to set
     */
    public void setAccCardInfoDao(AccCardInfoDao accCardInfoDao) {
        this.accCardInfoDao = accCardInfoDao;
    }

    /**
     * @return the sellOrderDAO
     */
    public SellOrderDAO getSellOrderDAO() {
        return sellOrderDAO;
    }

    /**
     * @param sellOrderDAO the sellOrderDAO to set
     */
    public void setSellOrderDAO(SellOrderDAO sellOrderDAO) {
        this.sellOrderDAO = sellOrderDAO;
    }

    /**
     * @return the sellOrderCardListDAO
     */
    public SellOrderCardListDAO getSellOrderCardListDAO() {
        return sellOrderCardListDAO;
    }

    /**
     * @param sellOrderCardListDAO the sellOrderCardListDAO to set
     */
    public void setSellOrderCardListDAO(SellOrderCardListDAO sellOrderCardListDAO) {
        this.sellOrderCardListDAO = sellOrderCardListDAO;
    }

    /**
     * @return the sellOrderListDAO
     */
    public SellOrderListDAO getSellOrderListDAO() {
        return sellOrderListDAO;
    }

    /**
     * @param sellOrderListDAO the sellOrderListDAO to set
     */
    public void setSellOrderListDAO(SellOrderListDAO sellOrderListDAO) {
        this.sellOrderListDAO = sellOrderListDAO;
    }

    /**
	 * 记录每一次对卡的操作
	 * 
	 * @param dto
	 * @throws BizServiceException
	 */
	public void insertCardManagement(CardManagementDTO dto)
			throws BizServiceException {
		try {
			CardManagement cardManagement = new CardManagement();
			ReflectionUtil.copyProperties(dto, cardManagement);
			cardManagement.setCardManagementId(commonsDAO
					.getNextValueOfSequence("TB_ENT_CARD_MANAGEMENT"));
			cardManagement.setCardNo(dto.getTransferOutCard().trim());
			cardManagement.setOperationType(dto.getOperationType());
			if (null != dto.getCardValidityPeriod()
					&& !"".equals(dto.getCardValidityPeriod())) {
				cardManagement.setCardValidityPeriod(DateUtil.getFormatTime(dto
						.getCardValidityPeriod()));
			}
			if (null != dto.getExtensionMonth()
					&& !"".equals(dto.getExtensionMonth())) {
				cardManagement.setExtensionMonth(dto.getExtensionMonth());
			}
			if (null != dto.getNewCardValidityPeriod()
					&& !"".equals(dto.getNewCardValidityPeriod())) {
				cardManagement.setNewCardValidityPeriod(dto
						.getNewCardValidityPeriod());
			}
			if (null != dto.getServiceFee() && !"".equals(dto.getServiceFee())) {
				cardManagement.setServiceFee(Amount
						.getDataBaseAmountForTwoFloat(dto.getServiceFee()));
			}
			// 流水号
			if (null != dto.getTxnId() && !"".equals(dto.getTxnId())) {
				cardManagement.setTxnId(dto.getTxnId());
			}
			// 余额
			if (null != dto.getBalance() && !"".equals(dto.getBalance())) {
				cardManagement.setBalance(dto.getBalance());
			}
			// 原订单
			if (null != dto.getOldOrder() && !"".equals(dto.getOldOrder())) {
				cardManagement.setOldOrder(dto.getOldOrder());
			}
			// 成本费
			if (null != dto.getCostPrice() && !"".equals(dto.getCostPrice())) {
				cardManagement.setCostPrice(dto.getCostPrice());
			}
			// 赎回金额
			if (null != dto.getRedemptionMoney()
					&& !"".equals(dto.getRedemptionMoney())) {
				cardManagement.setRedemptionMoney(dto.getRedemptionMoney());
				/*卡赎回调整金额就是赎回金额 modify by wanglei */
				if(dto.getOperationType().equals("S60")){
//					cardManagement.setAdjustAmount((Double.parseDouble(dto.getRedemptionMoney())*100)+"");
					cardManagement.setAdjustAmount(Amount.getDataBaseAmountForTwoFloat(dto.getRedemptionMoney()));
				}
				
			}
			// 赎回标记 1、入库 2.销户
			if (null != dto.getFlag() && !"".equals(dto.getFlag())) {
				cardManagement.setFlag(dto.getFlag());
			}
			// 代理人姓名
			if (null != dto.getAgentrName() && !"".equals(dto.getAgentrName())) {
				cardManagement.setAgentrName(dto.getAgentrName());
			}
			// 代理人证件类型
			if (null != dto.getAgentrCertType()
					&& !"".equals(dto.getAgentrCertType())) {
				cardManagement.setAgentrCertType(dto.getAgentrCertType());
			}
			// 代理人证件号
			if (null != dto.getAgentrCertTypeNo()
					&& !"".equals(dto.getAgentrCertTypeNo())) {
				cardManagement.setAgentrCertTypeNo(dto.getAgentrCertTypeNo());
			}
			// 代理人证件开始日期
			if (null != dto.getStartDate() && !"".equals(dto.getStartDate())) {
				cardManagement.setStartDate(DateUtil.StringDate(dto
						.getStartDate()));
			}
			// 代理人证件结束日期
			if (null != dto.getEndDate() && !"".equals(dto.getEndDate())) {
				cardManagement
						.setEndDate(DateUtil.StringDate(dto.getEndDate()));
			}

			// cardManagement.setServiceFee(dto.getServiceFee());
			cardManagement.setCreateUser(dto.getLoginUserId());
			cardManagement.setCreateTime(DateUtil.getCurrentTime());
			cardManagement.setModifyUser(dto.getLoginUserId());
			cardManagement.setModifyTime(DateUtil.getCurrentTime());
			cardManagement.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
			cardManagementDAO.insert(cardManagement); 
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("记录卡操作失败");
		}

	}

	/**
	 * 卡片操作查询
	 */

	public PageDataDTO selectCardManagement(CardManagementQueryDTO dto)
			throws BizServiceException {
		try {
			SellerExample sellerExample = new SellerExample();
			sellerExample.createCriteria().andEntityIdEqualTo(
					dto.getDefaultEntityId());
			List<Seller> sellerList = sellerDAO.selectByExample(sellerExample);
			if (null != sellerList && sellerList.size() > 0) {
				/* 营销机构查询本机构所有用户操作过的卡记录 */
				pageDataDTO = pageQueryDAO.query(
						"CARDMANAGEMENT.selectCardOperateForSeller", dto);
				return pageDataDTO;
			}
			IssuerExample issuerExample = new IssuerExample();
			issuerExample.createCriteria().andEntityIdEqualTo(
					dto.getDefaultEntityId());
			List<Issuer> issuerList = issuerDAO.selectByExample(issuerExample);
			if (null != issuerList && issuerList.size() > 0) {
				/* 发卡机构查询本机构定义的和合同给定的产品的操作记录 */
				pageDataDTO = pageQueryDAO.query(
						"CARDMANAGEMENT.selectCardOperateForIssuer", dto);
				return pageDataDTO;
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询卡片操作失败");
		}
		return pageDataDTO;
	}

	/**
	 *单张卡操作查询
	 * 
	 */
	public PageDataDTO selectSingleCardManagement(CardManagementQueryDTO dto)
			throws BizServiceException {
		try {
			SellerExample sellerExample = new SellerExample();
			sellerExample.createCriteria().andEntityIdEqualTo(
					dto.getDefaultEntityId());
			List<Seller> sellerList = sellerDAO.selectByExample(sellerExample);
			if (null != sellerList && sellerList.size() > 0) {
				/* 营销机构查询本机构所有用户操作过的卡记录 */
				pageDataDTO = pageQueryDAO.query(
						"CARDMANAGEMENT.selectSingleCardOperate", dto);
				//return pageDataDTO;
			}
			
			// FIXME 这里能这样判断么？判断为空应该还要判断Listsize是否是0
			if (pageDataDTO == null || pageDataDTO.getData() == null){
				/**
				 * 通过营销机构没查到结果
				 */
			} else{
				return pageDataDTO;
			}
			
			IssuerExample issuerExample = new IssuerExample();
			issuerExample.createCriteria().andEntityIdEqualTo(
					dto.getDefaultEntityId());
			List<Issuer> issuerList = issuerDAO.selectByExample(issuerExample);
			if (null != issuerList && issuerList.size() > 0) {
				/* 发卡机构查询本机构定义的和合同给定的产品的操作记录 */
				pageDataDTO = pageQueryDAO.query(
						"CARDMANAGEMENT.selectSingleCardOperateIssuer", dto);
				return pageDataDTO;
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询卡片操作失败");
		}
		return pageDataDTO;
	}

	/**
	 * 查询会员信息
	 */
	public PageDataDTO inqueryMemberInfo(MemberQueryDTO dto)
			throws BizServiceException {
		PageDataDTO pageData = null;
		try {
			pageData = pageQueryDAO.query("MEMBER_INF.selectAllMember", dto);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return pageData;
	}

	/** 查询会员详细信息 **/
	public MemberQueryDTO inqueryMemberDetailInfo(MemberQueryDTO dto)
			throws BizServiceException {
		MemberQueryDTO memberQueryDTO;
		memberQueryDTO = commonsDAO.selectMemberDetailInf(dto);
		return memberQueryDTO;
	}

	/** 查询会员关联的卡号 **/
	public List<MemberQueryDTO> inqueryMemberRltCardNo(MemberQueryDTO dto)
			throws BizServiceException {
		List<MemberQueryDTO> list =new ArrayList<MemberQueryDTO>();
		try {
			Map<String, String> rspCodeMap = RspCodeMap.getRspCodeMap();			
			List<MemberQueryDTO> memberQueryDTOs = commonsDAO.selectMemberRltCard(dto);
			if(null!=memberQueryDTOs && memberQueryDTOs.size()>0){
				String[] cardNos = new String[memberQueryDTOs.size()];
				for(int i =0 ; i<memberQueryDTOs.size();i++){
					cardNos[i]=memberQueryDTOs.get(i).getCardNo();
				}
				//卡号列表
				String cardNoString=StringUtils.getStr(cardNos);
				logger.debug("find out card:" + cardNoString);
				AccPackageDTO accPackageDTO = new AccPackageDTO();
				accPackageDTO.setTxnCode("S040");
				accPackageDTO.setCardNo(cardNoString);
				accPackageDTO.setBegin_row(String
						.valueOf(dto.getFirstCursorPosition()));
				accPackageDTO.setEnd_row(String
						.valueOf(dto.getLastCursorPosition()));
				accPackageDTO=java2ACCBusinessService.queryCardByCardHolder(accPackageDTO);
				String rspCode = accPackageDTO.getRespCode();
				logger.debug("vCardHolderBatInq return:" + rspCode);
				if (rspCode.equals("00")) {
					/** 报文返回状态为成功 */
					logger.debug("vCardHolderBatInq success!");
					String result = accPackageDTO.getOtherData();
					String[] records = result.split("\\|");
					
					if (records != null && records.length > 0) {
						for (int i = 0; i < records.length; i++) {
							String[] fields = records[i].split("\\^");
							MemberQueryDTO memberQueryDTO = new MemberQueryDTO();
							memberQueryDTO.setCardNo(fields[0]);
							memberQueryDTO.setProductNm(fields[1]);
							memberQueryDTO.setCardValidityPeriod(DateUtil
									.formatStringDate(fields[2]));
							memberQueryDTO.setTotalBalance(Amount
									.getReallyAmount(fields[3]));
							memberQueryDTO.setBalance(Amount
									.getReallyAmount(fields[4]));
							memberQueryDTO.setCongeal(Amount
									.getReallyAmount(fields[5]));
							list.add(memberQueryDTO);
						}
					}
//					String totalRow = accPackageDTO.getTotalRow();
//					String realRow = accPackageDTO.getRealRow();
//					cardholderDTO.getCardholderCardList().setData(list);
//					cardholderDTO.getCardholderCardList().setTotalRecord(
//							Integer.parseInt(totalRow));
//					cardholderDTO.getCardholderCardList().setPageSize(
//							Integer.parseInt(realRow));
//					logger.debug("find out card count:" + totalRow
//							+ " and real display count:" + realRow);
				} else {
					if (rspCodeMap.containsKey(rspCode)) {
						throw new BizServiceException(rspCodeMap.get(rspCode));
					}
				}
			}

		} catch (BizServiceException b) {
			logger.error(b.getMessage());
			throw b;
		}catch(Exception e){
			this.logger.error(e.getMessage());
			throw new BizServiceException("查看持卡人信息失败！");
		}

		return list;
	}

	/** 查询卡的总金额，可用金额，冻结金额 **/
	public MemberQueryDTO inqueryCardBalance(MemberQueryDTO dto)
			throws BizServiceException {
		MemberQueryDTO memberQueryDTO = new MemberQueryDTO();
		try {
			CardManagementDTO cadto = new CardManagementDTO();
			// 用卡片查询的方法查询金额
			cadto.setCardNo(dto.getCardNo());
			cadto.setDefaultEntityId(dto.getDefaultEntityId());
			cadto = this.queryCard(cadto);
			memberQueryDTO.setBalance(String.valueOf(cadto.getBalance()));
			memberQueryDTO.setCongeal(String.valueOf(cadto.getCongeal()));
			memberQueryDTO.setTotalBalance(String.valueOf(cadto
					.getTotalBalance()));
			memberQueryDTO.setCardValidityPeriod(DateUtil
					.formatStringDate(cadto.getCardValidityPeriod()));

			// 以前的代码
			/*
			 * accPackageDTO.setTxnCode("S000"); HashMap map = new HashMap();
			 * 
			 * map.put(CFunctionConstant.TXN_TYPE, "S000");
			 * map.put(CFunctionConstant.CHANNEL, Const.JAVA2C_CHANNEL);
			 * map.put(CFunctionConstant.CARD_NO, dto.getCardNo());
			 * 
			 * OperationResult or = java2C.sendTpService("Switch", map,
			 * Const.JAVA2C_NORMAL, false); HashMap map1 = (HashMap)
			 * or.getDetailvo(); String rspCode = (String)
			 * map1.get(CFunctionConstant.RESP_CODE); List<CardBalanceDTO>
			 * balanceDTOList = new ArrayList<CardBalanceDTO>(); Map<String,
			 * String> rspCodeMap = RspCodeMap.getRspCodeMap(); if
			 * ("00".equals(cadto.getr)) {
			 * 
			 * String result = (String) map1.get(CFunctionConstant.RESV2);
			 * String cardValidityPeriod = result.substring(0, 8);
			 * logger.debug("有效期：" + cardValidityPeriod);
			 *//**
			 * Balance 账户数量 2 账户服务类型 8 真实余额 12 冻结金额 12
			 */
			/*
			 * String allBalance = (String) map1.get(CFunctionConstant.BALANCE);
			 * String balance = allBalance.substring(2, allBalance.length());
			 * double totalBalance = 0, lessBalance = 0, congeal = 0;
			 * 
			 * for (int i = 0; i < balance.length();) { lessBalance +=
			 * Double.parseDouble(Amount .getReallyAmount(new
			 * BigDecimal(balance.substring( i + 8, i + 20)).toString()));
			 * congeal += Double.parseDouble(Amount .getReallyAmount(new
			 * BigDecimal(balance.substring( i + 20, i + 32)).toString())); i =
			 * i + 32; } totalBalance = lessBalance + congeal;
			 * memberQueryDTO.setBalance(String.valueOf(lessBalance));
			 * memberQueryDTO.setCongeal(String.valueOf(congeal));
			 * memberQueryDTO.setTotalBalance(String.valueOf(totalBalance));
			 * memberQueryDTO.setCardValidityPeriod(DateUtil
			 * .formatStringDate(cardValidityPeriod)); } else if
			 * (rspCodeMap.containsKey(rspCode)) { throw new
			 * BizServiceException(rspCodeMap.get(rspCode)); } else { throw new
			 * BizServiceException("系统异常");
			 */

		} catch (Exception e) {

			this.logger.error(e.getMessage());
		}

		return memberQueryDTO;

	}

	/**
	 * 
	 * 转账或换卡时查询转出卡或原卡的信息
	 */
	public CardManagementDTO queryCardForTranfer(CardManagementDTO dto)
			throws BizServiceException {
		try {
			// 用卡片查询的方法查询金额
			dto.setCardNo(dto.getTransferOutCard());
			dto = this.queryCard(dto);

			/*
			 * HashMap map = new HashMap(); map.put(CFunctionConstant.TXN_TYPE,
			 * "S000"); map.put(CFunctionConstant.CHANNEL,
			 * Const.JAVA2C_CHANNEL); map.put(CFunctionConstant.CARD_NO,
			 * dto.getTransferOutCard());
			 * 
			 * OperationResult or = java2C.sendTpService("Switch",
			 * map,Const.JAVA2C_NORMAL, false); HashMap map1 = (HashMap)
			 * or.getDetailvo(); String rspCode = (String)
			 * map1.get(CFunctionConstant.RESP_CODE); Map<String, String>
			 * rspCodeMap = RspCodeMap.getRspCodeMap(); Map<String, String>
			 * accountMap = new HashMap(); List accoutList = new ArrayList();
			 * 
			 * Map<String, String> accountRltBalance = new HashMap<String,
			 * String>(); if ("00".equals(rspCode)) { String serviceNames =
			 * (String) map1 .get(CFunctionConstant.RESV4); String[] serviceName
			 * = serviceNames.split("\\|"); String allBalance = (String) map1
			 * .get(CFunctionConstant.BALANCE);
			 * 
			 * String balance = allBalance.substring(2, allBalance.length());
			 * int j = 0; for (int i = 0; i < balance.length();) {
			 * accountRltBalance.put(serviceName[j], new BigDecimal(
			 * balance.substring(i + 8, i + 20)).divide( new
			 * BigDecimal(100)).toString()); accountMap.put(serviceName[j], new
			 * BigDecimal(balance .substring(i, i + 8)).toString());
			 * accoutList.add(serviceName[j]); i = i + 32; j++; }
			 * 
			 * } else if (rspCodeMap.containsKey(rspCode)) { throw new
			 * BizServiceException(rspCodeMap.get(rspCode)); } else { throw new
			 * BizServiceException("系统异常"); }
			 * 
			 * dto.setAccountMap(accountMap);
			 * dto.setAccountRltBalance(accountRltBalance);
			 * dto.setAccountList(accoutList);
			 */
			return dto;
		} catch (BizServiceException e1) {
			throw e1;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("卡查询失败");
		}
	}

	/**
	 * 
	 * 转账查询转入卡信息
	 */
	@SuppressWarnings("unchecked")
	public CardManagementDTO queryInCardForTranfer(CardManagementDTO dto)
			throws BizServiceException {
		try {
			// 用卡片查询的方法查询金额
			dto = this.queryCard(dto);

			/*
			 * HashMap map = new HashMap(); map.put(CFunctionConstant.TXN_TYPE,
			 * "S000"); map.put(CFunctionConstant.CHANNEL,
			 * Const.JAVA2C_CHANNEL); map.put(CFunctionConstant.CARD_NO,
			 * dto.getTransferInCard()); OperationResult or =
			 * java2C.sendTpService("Switch", map, Const.JAVA2C_NORMAL, false);
			 * HashMap map1 = (HashMap) or.getDetailvo(); String rspCode =
			 * (String) map1.get(CFunctionConstant.RESP_CODE); Map<String,
			 * String> rspCodeMap = RspCodeMap.getRspCodeMap(); List accountList
			 * = new ArrayList(); Map<String, String> accountMap = new
			 * HashMap(); Map<String, String> accountRltBalance = new
			 * HashMap<String, String>();
			 */

			if (dto != null) {

				// String cardHolderId = (String)
				// map1.get(CFunctionConstant.RESV3);
				String cardHolderId = dto.getCardholderId();
				logger.debug("CardHolderId:" + dto.getCardholderId());

				Map<String, String> cardHolders = new HashMap<String, String>();
				Map<String, String> productInf = new HashMap<String, String>();

				if (null != cardHolderId && !cardHolderId.equals("")) {
					CardholderQueryDTO queryDTO = new CardholderQueryDTO();
					queryDTO.setCardholderId(cardHolderId);
					PageDataDTO cardDataDTO = cardholderService
							.queryCardHolder(queryDTO);
					cardHolders = (HashMap<String, String>) cardDataDTO
							.getData().get(0);

				} else {
					List<CardholderQueryDTO> cardholders = cardholderService
					.getCardHolderByCardNo(dto.getCardNo());
					if(null != cardholders && cardholders.size()>0){
						dto.setCardholderName(cardholders.get(0).getFirstName());
						dto.setIdNo(cardholders.get(0).getIdNo());
						dto.setMobile(cardholders.get(0).getCardholderMobile());
					}
				}
				List productList = new ArrayList();
				PageDataDTO productDataDTO = commonsDAO
						.selectProductInfByCardNo(dto);
				if (productDataDTO != null
						&& productDataDTO.getData().size() > 0) {
					productInf = (HashMap<String, String>) productDataDTO
							.getData().get(0);
					productInf.putAll(cardHolders);
					productList.add(productInf);
					productDataDTO.setData(productList);
					dto.setPageDataDTO(productDataDTO);
				} else if (productDataDTO == null) {
					throw new BizServiceException("卡查询失败");
				}
				
				/*
				 * String serviceNames = (String)
				 * map1.get(CFunctionConstant.RESV4); String[] serviceName =
				 * serviceNames.split("\\|");
				 * 
				 * String allBalance = (String) map1
				 * .get(CFunctionConstant.BALANCE); String balance =
				 * allBalance.substring(2, allBalance.length());
				 * 
				 * int j = 0; for (int i = 0; i < balance.length();) {
				 * accountMap.put(serviceName[j], (balance.substring(i, i +
				 * 8)).toString()); accountList.add(serviceName[j]); i = i + 32;
				 * j++; }
				 * 
				 * dto.setPageDataDTO(productDataDTO); } /*else if
				 * (rspCodeMap.containsKey(rspCode)) { throw new
				 * BizServiceException(rspCodeMap.get(rspCode)); } else { throw
				 * new BizServiceException("系统异常"); }
				 */
				// dto.setAccountMap(accountMap);
				dto.setInCardAccounts(dto.getAccountList());
			}
			return dto;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("卡查询失败");
		}
	}

	/**
	 * 
	 * 换卡时查询新卡信息
	 */
	@SuppressWarnings("unchecked")
	public CardManagementDTO queryNewCardForChange(CardManagementDTO dto)
			throws BizServiceException {
		Map<String, String> rspCodeMap = RspCodeMap.getRspCodeMap();
		try {
			// 新卡激活          2013-2-19 修改查询卡片信息时不激活卡片

			//cardSendManage("M100", "Switch", dto);
			EntityStock entityStock = new EntityStock();
			entityStock.setStockState("3");// 新卡出库
			EntityStockExample entityStockExample = new EntityStockExample();
			entityStockExample.createCriteria().andCardNoEqualTo(
					dto.getTransferInCard());
			entityStockDAO.updateByExampleSelective(entityStock,
					entityStockExample);
			// 查询新卡信息
			AccPackageDTO accPackageDTO = new AccPackageDTO();

			// 修改的代码
			accPackageDTO.setTxnCode("S000");
			accPackageDTO.setCardNo(dto.getTransferInCard());
			accPackageDTO = java2ACCBusinessService.queryCard(accPackageDTO);

			/*
			 * HashMap map = new HashMap(); map.put(CFunctionConstant.TXN_TYPE,
			 * "S000"); map.put(CFunctionConstant.CHANNEL,
			 * Const.JAVA2C_CHANNEL); map.put(CFunctionConstant.CARD_NO,
			 * dto.getTransferInCard()); OperationResult or =
			 * java2C.sendTpService("Switch", map, Const.JAVA2C_NORMAL, false);
			 * HashMap map1 = (HashMap) or.getDetailvo(); String rspCode =
			 * (String) map1.get(CFunctionConstant.RESP_CODE);
			 */
			String rspCode = accPackageDTO.getRespCode();

			// Map<String, String> rspCodeMap = RspCodeMap.getRspCodeMap();
			Map<String, String> accountMap = new HashMap();
			Map<String, String> cardHolders = new HashMap<String, String>();
			Map<String, String> productInf = new HashMap<String, String>();

			if ("00".equals(rspCode)) {
				EntityStock entityStock1 = entityStockDAO
						.selectByPrimaryKey(dto.getTransferInCard());
				dto.setStockState(entityStock1.getStockState());
				Product product = productDAO.selectByPrimaryKey(entityStock1
						.getProductId());
				dto.setOnymusState(product.getOnymousStat());

				// String cardHolderId = (String)
				// map1.get(CFunctionConstant.RESV3);
				String cardHolderId = accPackageDTO.getCardHolder();
				logger.debug("CardHolderId:" + cardHolderId);

				if (null != cardHolderId && !cardHolderId.equals("")) {
					CardholderQueryDTO queryDTO = new CardholderQueryDTO();
					queryDTO.setCardholderId(cardHolderId);
					PageDataDTO cardDataDTO = cardholderService
							.queryCardHolder(queryDTO);
					cardHolders = (HashMap<String, String>) cardDataDTO
							.getData().get(0);
					dto.setIncardholderName(cardHolders.get("firstName"));
					dto.setIdNo(cardHolders.get("idNo"));
					dto.setIdType(cardHolders.get("idType"));
					dto.setMobile(cardHolders.get("cardholderMobile"));

				} else {
					List<CardholderQueryDTO> cardholders = cardholderService
					.getCardHolderByCardNo(dto.getCardNo());
					if(null != cardholders && cardholders.size()>0){
						dto.setIncardholderName(cardholders.get(0).getFirstName());
						dto.setIdNo(cardholders.get(0).getIdNo());
						dto.setIdType(cardholders.get(0).getIdType());
						dto.setMobile(cardholders.get(0).getCardholderMobile());
					}
				}

				List newCardAccList = new ArrayList();

				PageDataDTO accountDataDTO = new PageDataDTO();
				PageDataDTO productDataDTO = commonsDAO
						.selectProductInfByCardNo(dto);
				if (productDataDTO != null
						&& productDataDTO.getData().size() > 0) {
					productInf = (HashMap<String, String>) productDataDTO
							.getData().get(0);
					dto.setProdName(productInf.get("productName").toString());
					dto.setProdType(productInf.get("productType").toString());
				}

				/*
				 * String serviceNames = (String)
				 * map1.get(CFunctionConstant.RESV4); String[] serviceName =
				 * serviceNames.split("\\|");
				 */

				String[] serviceName = accPackageDTO.getServiceNames();
				String[] serviceIds = accPackageDTO.getServiceIDs();
				String[] balances = accPackageDTO.getBalances();
				// String allBalance = (String)
				// map1.get(CFunctionConstant.BALANCE);

				// String balance = allBalance.substring(2,
				// allBalance.length());

				for (int i = 0; i < Integer.parseInt(accPackageDTO
						.getAccountNum()); i++) {
					Map newCardAccMap = new HashMap();
					accountMap.put(serviceName[i], serviceIds[i]);
					newCardAccMap.put("accName", serviceName[i]);
					newCardAccMap.put("accId", serviceIds[i]);
					newCardAccMap.put("balace", new BigDecimal(balances[i])
							.divide(new BigDecimal(100)).toString());
					newCardAccList.add(newCardAccMap);
				}

				accountDataDTO.setData(newCardAccList);
				dto.setPageDataDTO(accountDataDTO);

				/*
				 * int j = 0; for (int i = 0; i < balance.length();) { Map
				 * newCardAccMap = new HashMap();
				 * accountMap.put(serviceName[j],(balance.substring(i, i +
				 * 8)).toString()); newCardAccMap.put("accName",
				 * serviceName[j]); newCardAccMap.put("accId",
				 * (balance.substring(i, i + 8)).toString());
				 * newCardAccMap.put("balace", new
				 * BigDecimal(balance.substring(i + 8, i + 20)).divide(new
				 * BigDecimal(100)).toString());
				 * newCardAccList.add(newCardAccMap); i = i + 32; j++; }
				 * 
				 * accountDataDTO.setData(newCardAccList);
				 * dto.setPageDataDTO(accountDataDTO);
				 */
			} else {
				if (rspCodeMap.containsKey(rspCode)) {
					throw new BizServiceException(rspCodeMap.get(rspCode));
				}
			}
			dto.setAccountMap(accountMap);
			return dto;
		} catch (BizServiceException e1) {
			throw e1;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("卡查询失败");
		}
	}

	/**
	 * 查询卡片
	 * 
	 * @param dto
	 * @return
	 * @throws BizServiceException
	 */
	@SuppressWarnings("unchecked")
	public CardManagementDTO queryCard(CardManagementDTO dto)
			throws BizServiceException {
		Map<String, String> rspCodeMap = RspCodeMap.getRspCodeMap();
		try {
			AccPackageDTO accPackageDTO = new AccPackageDTO();
			authForCardQuery(dto.getCardNo(), dto.getDefaultEntityId());
			// 之前的代码
			/*
			 * HashMap map = new HashMap(); map.put(CFunctionConstant.TXN_TYPE,
			 * "S000"); map.put(CFunctionConstant.CHANNEL,
			 * Const.JAVA2C_CHANNEL); map.put(CFunctionConstant.CARD_NO,
			 * dto.getCardNo());
			 */
			// 修改的代码
			accPackageDTO.setTxnCode("S000");
			accPackageDTO.setCardNo(dto.getCardNo());
			accPackageDTO = java2ACCBusinessService.queryCard(accPackageDTO);
			if (!"00".equals(accPackageDTO.getRespCode())) {
				if (rspCodeMap.containsKey(accPackageDTO.getRespCode())) {
					throw new BizServiceException(rspCodeMap.get(accPackageDTO
							.getRespCode()));
				}
			}

			// 一些封装数据
			List<CardBalanceDTO> balanceDTOList = new ArrayList<CardBalanceDTO>();
			EntityStock entityStock = entityStockDAO.selectByPrimaryKey(dto
					.getCardNo());
			dto.setStockState(entityStock.getStockState());
			Product product = productDAO.selectByPrimaryKey(entityStock
					.getProductId());
			dto.setOnymusState(product.getOnymousStat());
			
			//库存状态为入库的卡片 不查询持卡人信息
			if("3".equals(dto.getStockState())){
				dto=getCardHolder(dto,accPackageDTO);
			}

			// 设置卡状态
			dto.setCardValidityPeriod(DateUtil.formatStringDate(accPackageDTO
					.getValideTime()));
			dto.setActive(accPackageDTO.getActiveState());
			dto.setLock(accPackageDTO.getLockState());
			dto.setOff(accPackageDTO.getOff());
			dto.setHang(accPackageDTO.getLossState());
			dto.setTrade(accPackageDTO.getTrade());
			dto.setCallBack(accPackageDTO.getCallBack());

			// 账户数量
			int accnum = Integer.valueOf(accPackageDTO.getAccountNum());

			// 账号名称
			String[] serviceName = accPackageDTO.getServiceNames();
			// 账号ID
			String[] serviceId = accPackageDTO.getServiceIDs();
			// 账号总金额
			String[] balances = accPackageDTO.getBalances();
			// 冻结金额
			String[] congeal = accPackageDTO.getCongeal();
			//

			String total = "";
			BigDecimal useBalance = new BigDecimal("0");
			BigDecimal totalcongeal= new BigDecimal("0");
			BigDecimal totalBalance= new BigDecimal("0");

			Map<String, String> accountMap = new HashMap();
			List accoutList = new ArrayList();
			Map<String, String> accountRltBalance = new HashMap<String, String>();

			for (int i = 0; i < accnum; i++) {
				CardBalanceDTO balanceDTO = new CardBalanceDTO();
				// 账号id
				balanceDTO
						.setAccountId(new BigDecimal(serviceId[i]).toString());
				// 账号名称
				balanceDTO.setAccountType(serviceName[i]);
				// 可用余额
				
				balanceDTO.setBalance(String
						.valueOf(new BigDecimal(balances[i]).subtract(new BigDecimal(congeal[i]))));
				total = String.valueOf(new BigDecimal(balances[i]));
				// 可用余额总额
				useBalance = useBalance.add(new BigDecimal(balanceDTO.getBalance()));
				// 总余额
				totalBalance  = totalBalance.add(new BigDecimal(balances[i]));
				// 冻结余额总额
				totalcongeal = totalcongeal.add(new BigDecimal(congeal[i]));
				balanceDTO.setCongeal(String.valueOf(new BigDecimal(congeal[i])));
				balanceDTO.setTotal(total);
				logger.debug(balanceDTO.getAccountType());
				logger.debug(balanceDTO.getBalance());
				logger.debug(balanceDTO.getCongeal());
				balanceDTOList.add(balanceDTO);

				// 查询转出卡信息时需要
				accountRltBalance.put(serviceName[i], new BigDecimal(
						balances[i]).divide(new BigDecimal(100)).toString());
				accountMap.put(serviceName[i], serviceId[i]);
				accoutList.add(serviceName[i]);
			}
			dto.setAccountRltBalance(accountRltBalance);
			dto.setAccountList(accoutList);
			dto.setAccountMap(accountMap);
			dto.setBalance(String.valueOf(useBalance));
			dto.setTotalBalance(String.valueOf(totalBalance));
			dto.setCongeal(String.valueOf(totalcongeal));
			dto.setCardBalanceDTOs(balanceDTOList);
			return dto;
		} catch (BizServiceException e1) {
			throw e1;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("卡查询失败");
		}

		// 之前的代码
		/*
		 * OperationResult or = java2C.sendTpService("Switch", map,
		 * Const.JAVA2C_NORMAL, false); HashMap map1 = (HashMap)
		 * or.getDetailvo(); String rspCode = (String)
		 * map1.get(CFunctionConstant.RESP_CODE); List<CardBalanceDTO>
		 * balanceDTOList = new ArrayList<CardBalanceDTO>(); Map<String, String>
		 * rspCodeMap = RspCodeMap.getRspCodeMap(); String total; int
		 * totalBalance = 0; if ("00".equals(rspCode)) { EntityStock entityStock
		 * = entityStockDAO.selectByPrimaryKey(dto .getCardNo());
		 * dto.setStockState(entityStock.getStockState()); Product product =
		 * productDAO.selectByPrimaryKey(entityStock .getProductId());
		 * dto.setOnymusState(product.getOnymousStat()); String cardHolderId =
		 * (String) map1 .get(CFunctionConstant.RESV3);
		 * logger.debug("CardHolderId:" + cardHolderId); String cardHolderName =
		 * ""; Map<String, String> cardHolders = new HashMap<String, String>();
		 * 
		 * if (null != cardHolderId && !cardHolderId.equals("")) {
		 * CardholderQueryDTO queryDTO = new CardholderQueryDTO();
		 * queryDTO.setCardholderId(cardHolderId); PageDataDTO cardDataDTO =
		 * cardholderService .queryCardHolder(queryDTO); cardHolders =
		 * (HashMap<String, String>) cardDataDTO .getData().get(0);
		 * cardHolderName = cardHolders.get("firstName");
		 * dto.setCardholderId(cardHolderId);
		 * dto.setCardholderName(cardHolderName); } else { Cardholder cardholder
		 * = cardholderService .getCardHolderByCardNo(dto.getCardNo()); if (null
		 * != cardholder && cardholder.getFirstName() != null) {
		 * dto.setCardholderId(cardholder.getCardholderId());
		 * logger.debug("CardHolderName:" + cardholder.getFirstName());
		 * dto.setCardholderName(cardholder.getFirstName()); } }
		 * 
		 * String result = (String) map1.get(CFunctionConstant.RESV2); String
		 * cardValidityPeriod = result.substring(0, 8); logger.debug("有效期：" +
		 * cardValidityPeriod);
		 * dto.setCardValidityPeriod(DateUtil.formatStringDate
		 * (cardValidityPeriod)); dto.setActive(result.substring(8, 9));激活
		 * activeState dto.setLock(result.substring(9, 10));锁定 lockState
		 * dto.setOff(result.substring(10, 11));
		 * dto.setHang(result.substring(11, 12));loss
		 * dto.setTrade(result.substring(12, 13)); // String //
		 * cardState=result.substring(result.length()-1,result.length()); //
		 * logger.debug("卡状态："+cardState); // dto.setCardstate(cardState);
		 * String serviceNames = (String) map1 .get(CFunctionConstant.RESV4);
		 * logger.debug("帐户Names：" + serviceNames); String[] serviceName =
		 * serviceNames.split("\\|"); String allBalance = (String) map1
		 * .get(CFunctionConstant.BALANCE); String balance =
		 * allBalance.substring(2, allBalance.length()); logger.debug(balance);
		 * logger.debug(balance.length());
		 * logger.debug("========================================="); int j = 0;
		 * for (int i = 0; i < balance.length();) { CardBalanceDTO balanceDTO =
		 * new CardBalanceDTO(); logger.debug("帐户：" + serviceName[j]);
		 * balanceDTO.setAccountId(new BigDecimal(balance.substring(i, i +
		 * 8)).toString()); balanceDTO.setAccountType(serviceName[j]);
		 * balanceDTO.setBalance(String.valueOf(new BigDecimal(balance
		 * .substring(i + 8, i + 20)).intValue() - new
		 * BigDecimal(balance.substring(i + 20, i + 32)) .intValue())); total =
		 * new BigDecimal(balance.substring(i + 8, i + 20)) .toString();
		 * totalBalance += new BigDecimal(balance.substring(i + 8, i +
		 * 20)).intValue(); balanceDTO.setCongeal(new
		 * BigDecimal(balance.substring( i + 20, i + 32)).toString());
		 * balanceDTO.setTotal(total);
		 * logger.debug(balanceDTO.getAccountType());
		 * logger.debug(balanceDTO.getBalance());
		 * logger.debug(balanceDTO.getCongeal());
		 * balanceDTOList.add(balanceDTO); i = i + 32; j++; } } else if
		 * (rspCodeMap.containsKey(rspCode)) { throw new
		 * BizServiceException(rspCodeMap.get(rspCode)); } else { throw new
		 * BizServiceException("系统异常"); }
		 * dto.setTotalBalance(String.valueOf(totalBalance));
		 * dto.setCardBalanceDTOs(balanceDTOList); return dto; } catch
		 * (BizServiceException e1) { throw e1; } catch (Exception e) {
		 * this.logger.error(e.getMessage()); throw new
		 * BizServiceException("卡查询失败"); }
		 */
	}

	//获取持卡人信息
	private CardManagementDTO getCardHolder(CardManagementDTO dto,AccPackageDTO accPackageDTO)throws BizServiceException{
		try {
			String cardHolderId = accPackageDTO.getCardHolder();
			logger.debug("CardHolderId:" + cardHolderId);
			String cardHolderName = "";
			Map<String, String> cardHolders = new HashMap<String, String>();

			if (!"".equals(cardHolderId) && cardHolderId != null) {
				CardholderQueryDTO queryDTO = new CardholderQueryDTO();
				queryDTO.setCardholderId(cardHolderId);
				PageDataDTO cardDataDTO = cardholderService
						.queryCardHolder(queryDTO);
				cardHolders = (HashMap<String, String>) cardDataDTO.getData()
						.get(0);
				cardHolderName = cardHolders.get("firstName");
				dto.setCardholderId(cardHolderId);
				dto.setCardholderName(cardHolderName);
			} else {
				List<CardholderQueryDTO> cardholders = cardholderService
						.getCardHolderByCardNo(dto.getCardNo());
				if(null != cardholders && cardholders.size()>0){
					dto.setCardholderId(cardholders.get(0).getCardholderId());
					logger.debug("CardHolderName:" + cardholders.get(0).getFirstName());
					dto.setCardholderName(cardholders.get(0).getFirstName());
				}
			}
			return dto;
		} catch (BizServiceException b) {
			// TODO Auto-generated catch block
			b.printStackTrace();
			throw b;
		}catch (Exception e){
			e.printStackTrace();
			throw new BizServiceException("查询持卡人信息失败");
		}
	}
	
	
	/**
	 * 卡操作类型operationType 1 挂失 ， 2 解挂，3 锁定，4 解锁，5 冻结 ，6 解冻 ,7 卡密重置，8 卡片延期 9
	 * 卡安全信息设置， 10 卡磁道重写，11 卡账户调整
	 * 
	 */
	public void cardManageOperation(CardManagementDTO dto)
			throws BizServiceException {
		try {
			String type = dto.getOperationType();
			String rspCode="";
			if ("S51".equals(type)) {
				rspCode=cardHang(dto);
			} else if ("S53".equals(type)) {
				rspCode=noHang(dto);
			} else if ("S34".equals(type)) {
				rspCode=transferAccount(dto);
				dto.setAdjustAmount(Amount.getDataBaseAmount(dto.getTransAmount()));
			} else if ("S41".equals(type) || "S42".equals(type)) {

				rspCode=cardActiveOrDistory(dto);

			}

			else if ("S43".equals(type)) {
				rspCode=lock(dto);
			} else if ("S45".equals(type)) {
				rspCode=noLock(dto);
			} else if ("S46".equals(type)) {
				rspCode=frzzn(dto);
			} else if ("S47".equals(type)) {
				rspCode=nofrzzn(dto);
			}

			else if ("S59".equals(type)) {
				rspCode=modifyPassword(dto);
			} else if ("S60".equals(type)) {
				rspCode=redemptionCard(dto);
			} else if ("S58".equals(type)) {
				rspCode=cardDelay(dto);
			} else if ("S55".equals(type) || "S66".equals(type)) {
				rspCode=adjustmentAccounts(dto);
			} else if ("S32".equals(type)) {
				rspCode=singleCardRecharge(dto);
			} else if ("S57".equals(type)) {
				rspCode=setCardSecurityInfo(dto);
			} else if ("ChangeCard".equals(type)){
				dto.setOperationType("S34");
				rspCode=changeCard(dto);
				dto.setAdjustAmount(Amount.getDataBaseAmount(dto.getTransAmount()));
			}
			dto.setMemo(rspCode);
			// 记录一下此次卡片操作
			// 单卡充值
			if(null != dto.getCreditAmonts() && dto.getCreditAmonts().size()>0){
				for(int i =0;i<dto.getCreditAmonts().size();i++){
					dto.setAdjustAmount(Amount.getDataBaseAmount(dto.getCreditAmonts().get(i)));
					insertCardManagement(dto);
				}
			}else{
				insertCardManagement(dto);
			}
		} catch (BizServiceException e1) {
			throw e1;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("卡操作失败");
		}
	}

	// 卡激活

	public String cardActiveOrDistory(CardManagementDTO dto)
			throws BizServiceException {
		try {
		   return cardSendManage("M100", "Switch", dto);
		} catch (BizServiceException e1) {
			throw e1;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("卡激活失败");
		}
	}

	// 卡挂失
	public String cardHang(CardManagementDTO dto) throws BizServiceException {
		try {
			return cardSendManage("S510", "Switch", dto);
		} catch (BizServiceException e1) {
			throw e1;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("卡挂失失败");
		}
	}

	// 解挂
	public String noHang(CardManagementDTO dto) throws BizServiceException {
		try {
		  return cardSendManage("S530", "Switch", dto);
		} catch (BizServiceException e1) {
			throw e1;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("卡解挂失败");
		}
	}

	// 卡锁定
	public String lock(CardManagementDTO dto) throws BizServiceException {
		try {
		   return cardSendManage("S430", "Switch", dto);
		} catch (BizServiceException e1) {
			throw e1;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("卡锁定失败");
		}
	}

	// 卡解锁
	public String noLock(CardManagementDTO dto) throws BizServiceException {
		try {
		 return	cardSendManage("S450", "Switch", dto);
		} catch (BizServiceException e1) {
			throw e1;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("卡解锁失败");
		}
	}

	// 卡冻结
	public String frzzn(CardManagementDTO dto) throws BizServiceException {
		try {
		 return	cardSendManage("S460", "Switch", dto);
		} catch (BizServiceException e1) {
			throw e1;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("卡冻结失败");
		}
	}

	// 卡解冻
	public String nofrzzn(CardManagementDTO dto) throws BizServiceException {
		try {
			return cardSendManage("S470", "Switch", dto);
		} catch (BizServiceException e1) {
			throw e1;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("卡解冻失败");
		}
	}

	// 卡转账

	public String transferAccount(CardManagementDTO dto)
			throws BizServiceException {
		try {
			Map<String, String> rspCodeMap = RspCodeMap.getRspCodeMap();
			// 转出
			TxnPackageDTO txnPackageDto = new TxnPackageDTO();
			// 转出卡
			txnPackageDto.setTxnType("M340");
			// 商户号
			txnPackageDto.setMerchantCode("999999999999999");
			/** 转出卡号 **/
			txnPackageDto.setOutCard(dto.getTransferOutCard().trim());
			/** 转出卡账户 **/
			txnPackageDto.setOutAccount(dto.getTransferOutAccount());
			/** 转账金额 **/
			dto.setTransAmount(new BigDecimal(dto.getTransAmount()).multiply(
					new BigDecimal(100)).toString());
			txnPackageDto.setAmount(dto.getTransAmount());
			/** 转出卡密码 **/
			txnPackageDto.setPin(dto.getTransferOutCardPassword().trim());
			txnPackageDto.setServiceFee(Amount.getDataBaseAmount(dto.getServiceFee()));

			try {
				txnPackageDto = java2TXNBusinessService
						.CardTransferOut(txnPackageDto);
			} catch (Exception e) {
				
				this.logger.error(e.getMessage());
				throw new BizServiceException("C后台未启动！！");
			}
			// 交易流水号
			dto.setTransferOutTxnNo(txnPackageDto.getTxnNo());
			logger.debug("respCode：" + txnPackageDto.getRspCode());
			respCode = txnPackageDto.getRspCode();
			if (!"00".equals(respCode)) {
				if (rspCodeMap.containsKey(respCode)) {
					throw new BizServiceException(rspCodeMap.get(respCode));
				}
			}
			// 转入
			txnPackageDto.setTxnType("M330");
			/** 转出卡号 **/
			txnPackageDto.setOutCard(dto.getTransferOutCard().trim());
			/** 转出卡账户 **/
			txnPackageDto.setOutAccount(dto.getTransferOutAccount());
			/** 转入卡号 **/
			txnPackageDto.setIntoCard(dto.getTransferInCard());
			/** 转入卡账户 **/
			txnPackageDto.setIntoAccount(dto.getTransferInAccount());
			/** 转账金额 **/
			txnPackageDto.setAmount(dto.getTransAmount());
			/** 商户号 **/
			txnPackageDto.setMerchantCode("999999999999999");
			/** 原转出交易流水 **/
			txnPackageDto.setTxnNo(dto.getTransferOutTxnNo());
			txnPackageDto.setServiceFee(Amount.getDataBaseAmount(dto.getServiceFee()));
			try {
				txnPackageDto = java2TXNBusinessService
						.CardTransferIn(txnPackageDto);
			} catch (Exception e) {
				
				this.logger.error(e.getMessage());
				throw new BizServiceException("C后台未启动！！");
			}

			logger.debug("respCode：" + txnPackageDto.getRspCode());

			// //之前的代码
			// /*
			// Map map = new HashMap();
			// Map mapRecord = new HashMap();
			//
			// /** 转出卡 **/
			// // 组装报文
			// map.put(CFunctionConstant.TXN_TYPE, "M340");
			// /** 渠道号 **/
			// map.put(CFunctionConstant.CHANNEL, "80000001");
			// /** 商户号 **/
			// map.put(CFunctionConstant.INNER_MERCHANT_NO, "999999999999999");
			//
			// /** 转出卡号 **/
			// map.put(CFunctionConstant.CARD_NO,
			// dto.getTransferOutCard().trim());
			// /** 转出卡账户 **/
			// map.put(CFunctionConstant.ACC_TYPE, dto.getTransferOutAccount());
			// /** 转账金额 **/
			// dto.setTransAmount(new
			// BigDecimal(dto.getTransAmount()).multiply(new
			// BigDecimal(100)).toString());
			// map.put(CFunctionConstant.TXN_AMOUNT, dto.getTransAmount());
			// /** 转出卡密码 **/
			// map.put(CFunctionConstant.PIN_TXN,
			// dto.getTransferOutCardPassword().trim());
			// OperationResult or;
			// try {
			// or = java2C.sendTpService("vTxn", map, Const.JAVA2C_NORMAL,
			// false);
			// } catch (Exception e) {
			// this.logger.error(e.getMessage());
			// throw new BizServiceException("C后台未启动！！");
			// }
			// HashMap map1 = (HashMap) or.getDetailvo();
			//			
			// String rspCode = (String) map1.get(CFunctionConstant.RESP_CODE);
			// Map<String, String> rspCodeMap = RspCodeMap.getRspCodeMap();
			// mapRecord = (HashMap) or.getDetailvo();
			// respCode = (String) mapRecord.get(CFunctionConstant.RESP_CODE);
			// logger.debug("respCode：" + respCode);
			// allRecord = (String)
			// mapRecord.get(CFunctionConstant.SWT_FLOW_NO);
			// logger.debug("allRecord:" + allRecord);
			//
			// if (!"".equals(allRecord)) {
			// if ("00".equals(respCode)) {
			// dto.setTransferOutTxnNo(allRecord);
			// }
			// }
			// if (!"00".equals(respCode)) {
			// if (!rspCodeMap.containsKey(respCode)) {
			// throw new BizServiceException("系统异常！！");
			// } else
			// throw new BizServiceException(rspCodeMap.get(respCode));
			// }
			// /** 转入卡 **/
			// // 组装报文
			// Map map2 = new HashMap();
			// map2.put(CFunctionConstant.TXN_TYPE, "M330");
			// /** 渠道号 **/
			// map2.put(CFunctionConstant.CHANNEL, "80000001");
			// /** 转出卡号 **/
			// map2.put(CFunctionConstant.TRACK2,
			// dto.getTransferOutCard().trim());
			// /** 转出卡账户 **/
			// map2.put(CFunctionConstant.INNER_POS_NO,
			// dto.getTransferOutAccount());
			// /** 转入卡号 **/
			// map2.put(CFunctionConstant.CARD_NO, dto.getTransferInCard());
			// /** 转入卡账户 **/
			// map2.put(CFunctionConstant.ACC_TYPE, dto.getTransferInAccount());
			// /** 转账金额 **/
			// map2.put(CFunctionConstant.TXN_AMOUNT, dto.getTransAmount());
			// /** 商户号 **/
			// map.put(CFunctionConstant.INNER_MERCHANT_NO, "999999999999999");
			// /** 原转出交易流水 **/
			// map2.put(CFunctionConstant.REFERENCE_NO,
			// dto.getTransferOutTxnNo());
			// OperationResult orIn;
			// try {
			// orIn = java2C.sendTpService("vTxn", map2, Const.JAVA2C_NORMAL,
			// false);
			// } catch (Exception e) {
			// this.logger.error(e.getMessage());
			// throw new BizServiceException("C后台未启动！！");
			// }
			// mapRecord = (HashMap) orIn.getDetailvo();
			// respCode = (String) mapRecord.get(CFunctionConstant.RESP_CODE);
			// if ("00".equals(respCode)) {
			// dto.setTxnSeqNo(allRecord);
			// } else
			//
			// {
			// if ("1".equals(dto.getFlag())) {
			//
			// if (!rspCodeMap.containsKey(respCode)) {
			// throw new BizServiceException("换卡失败！！");
			// } else
			// throw new BizServiceException(rspCodeMap.get(respCode));
			//
			// } else {
			// if (!rspCodeMap.containsKey(respCode)) {
			// throw new BizServiceException("卡转账失败");
			// } else
			// throw new BizServiceException(rspCodeMap.get(respCode));
			//
			// }
			//
			// }
			// } catch (BizServiceException e) {
			// this.logger.error(e.getMessage());
			// throw e;
			// 获得返回值
			respCode = txnPackageDto.getRspCode();
			if ("00".equals(respCode)) {
				dto.setTxnSeqNo(txnPackageDto.getTxnNo());
			} else {
				if (rspCodeMap.containsKey(respCode)) {
					throw new BizServiceException(rspCodeMap.get(respCode));
				}
			}
			return respCode;
		} catch (BizServiceException b) {
			throw b;
		} catch (Exception e1) {
			logger.error(e1.getMessage());
			throw new BizServiceException("卡转账失败");
		}
	}

	// 卡密重置
	public String modifyPassword(CardManagementDTO dto)
			throws BizServiceException {
		Map<String, String> rspCodeMap = RspCodeMap.getRspCodeMap();
		String rspCode = "";
		try {
			AccPackageDTO accPackageDTO = new AccPackageDTO();
			accPackageDTO.setTxnCode("S590");
			accPackageDTO.setCardNo(dto.getCardNo());
			accPackageDTO.setPassword(dto.getPassword());
			accPackageDTO.setNewPin(dto.getNewPassword());
			accPackageDTO.setServiceFee(Amount.getDataBaseAmount(dto.getServiceFee()));
			accPackageDTO = java2ACCBusinessService
					.reSetCardPayPassWord(accPackageDTO);
			rspCode = accPackageDTO.getRespCode();

			// 之前的代码
			/*
			 * HashMap map = new HashMap(); map.put(CFunctionConstant.TXN_TYPE,
			 * "S590"); map.put(CFunctionConstant.CHANNEL,
			 * Const.JAVA2C_CHANNEL); map.put(CFunctionConstant.CARD_NO,
			 * dto.getCardNo()); map.put(CFunctionConstant.PIN_QUIRY_NEW,
			 * getFormatStr(dto.getNewPassword(), " ", 6, false)); //
			 * map.put(CFunctionConstant.TXN_AMOUNT, getFormatStr(dto //
			 * .getServiceFee(), " ", 12, false)); OperationResult or =
			 * java2C.sendTpService("Switch", map,Const.JAVA2C_NORMAL, false);
			 * HashMap map1 = (HashMap) or.getDetailvo(); String rspCode =
			 * (String) map1.get(CFunctionConstant.RESP_CODE);
			 * logger.debug("++++++++++++++" + rspCode+
			 * "+++++++++++++++++++++++"); Map<String, String> rspCodeMap =
			 * RspCodeMap.getRspCodeMap();
			 */

			if ("00".equals(rspCode)) {
				logger.debug("卡重置密码成功");
			} else {
				if (rspCodeMap.containsKey(rspCode)) {
					throw new BizServiceException(rspCodeMap.get(rspCode));
				}
			}
		} catch (BizServiceException b) {
			throw b;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException(rspCodeMap.get(rspCode));
		}
	/*	modify by wanglei 卡密重置把原pinoffset 复制进去
		String queryPin = accCardInfoDao.getQueryPin(dto);
		if(queryPin==null||queryPin.trim().equals("")){
			throw new BizServiceException("原始密码为空不可修改！");
		}else{
			dto.setPassword(queryPin);
			accCardInfoDao.update(dto);
		}*/
		return rspCode;
	}

	// 卡片账务调整
	public String adjustmentAccounts(CardManagementDTO dto)
			throws BizServiceException, Exception {
		String rspCode="";
		try {
			Map<String, String> rspCodeMap = RspCodeMap.getRspCodeMap();
			TxnPackageDTO txnPackageDTO = new TxnPackageDTO();
			/* 1.偿还，S550;2.扣款,S660 */
			txnPackageDTO.setTxnCode(dto.getAdjustType().equals("1") ? "S550"
					: "S660");
			// 调账金额
			txnPackageDTO.setAmount(dto.getAdjustAmount());
			// 原交易流水号
			txnPackageDTO.setOrigTxnNo(dto.getSysSeqNum());
			// 卡号
			txnPackageDTO.setCardNO(dto.getCardNo());
			// 服务费
			txnPackageDTO.setSrvFee(dto.getServiceFee());
			// 调整类型
			txnPackageDTO.setAdjustType(dto.getAdjustType());
			// 原交易类型
			txnPackageDTO.setOldTxnType(dto.getTxnType() + "0");
			// 调账标志
			txnPackageDTO.setFlag(dto.getCardFlag());
			// 网关消费渠道
			txnPackageDTO.setPayChnl(dto.getPayChnl());
			txnPackageDTO = java2TXNBusinessService
					.adjustmentAccounts(txnPackageDTO);

			/*
			 * HashMap map = new HashMap(); 1.偿还，S550;2.扣款,S660
			 * map.put(CFunctionConstant
			 * .TXN_TYPE,dto.getAdjustType().equals("1") ? "S550" : "S660");
			 * map.put(CFunctionConstant.TXN_AMOUNT, dto.getAdjustAmount());
			 * map.put(CFunctionConstant.ORI_SWTFLOW_NO, dto.getSysSeqNum());
			 * map.put(CFunctionConstant.CARD_NO, dto.getCardNo());
			 * 
			 * map.put(CFunctionConstant.CARD_HOLDER_FEE, dto.getServiceFee());
			 * 
			 * map.put(CFunctionConstant.PIN_QUIRY_NEW, dto.getAdjustType());
			 * map.put(CFunctionConstant.ACC_TYPE, dto.getTxnType() + "0");
			 * map.put(CFunctionConstant.CHANNEL, "80000001");
			 * map.put(CFunctionConstant.PIN_QUIRY, dto.getCardFlag());
			 * 
			 * OperationResult or = java2C.sendTpService("vTxn",
			 * map,Const.JAVA2C_NORMAL, false); HashMap map1 = (HashMap)
			 * or.getDetailvo();
			 */
			rspCode = txnPackageDTO.getRspCode();
			dto.setServiceFee(Amount.getReallyAmount(dto.getServiceFee()));
			logger
					.debug("++++++++++++++" + rspCode
							+ "+++++++++++++++++++++++");
			if ("00".equals(rspCode)) {
				logger.debug("卡片账务调整成功");
			} else {
				if (rspCodeMap.containsKey(rspCode)) {
					throw new BizServiceException(rspCodeMap.get(rspCode));
				}
			}
		} catch (BizServiceException e) {
			throw e;
		} catch (Exception ex) {
			throw new BizServiceException("卡账务调整失败");
		}
		return rspCode;
	}

	// 卡片延期
	public String cardDelay(CardManagementDTO dto) throws BizServiceException {
		Map<String, String> rspCodeMap = RspCodeMap.getRspCodeMap();
		String rspCode="";
		try {
			AccPackageDTO accPackageDTO = new AccPackageDTO();
			accPackageDTO.setTxnCode("S580");
			// 卡号
			accPackageDTO.setCardNo(dto.getTransferOutCard());
			// 延期月数
			accPackageDTO.setMonth(dto.getExtensionMonth());
			// 服务费
			accPackageDTO.setServiceFee(Amount.getDataBaseAmount(dto.getServiceFee()));

			accPackageDTO = java2ACCBusinessService.cardDelay(accPackageDTO);
			/*
			 * HashMap map = new HashMap(); map.put(CFunctionConstant.TXN_TYPE,
			 * "S580"); map.put(CFunctionConstant.CHANNEL,
			 * Const.JAVA2C_CHANNEL); map.put(CFunctionConstant.CARD_NO,
			 * dto.getTransferOutCard());
			 * map.put(CFunctionConstant.PIN_QUIRY_NEW,
			 * dto.getExtensionMonth()); //
			 * map.put(CFunctionConstant.TXN_AMOUNT, getFormatStr(dto //
			 * .getServiceFee(), " ", 12, false)); OperationResult or =
			 * java2C.sendTpService("Switch", map, Const.JAVA2C_NORMAL, false);
			 * HashMap map1 = (HashMap) or.getDetailvo(); String rspCode =
			 * (String) map1.get(CFunctionConstant.RESP_CODE);
			 */
			rspCode = accPackageDTO.getRespCode();

			logger
					.debug("++++++++++++++" + rspCode
							+ "+++++++++++++++++++++++");
			if (!"00".equals(rspCode)) {
				if (rspCodeMap.containsKey(rspCode)) {
					throw new BizServiceException(rspCodeMap.get(rspCode));
				}
			}
		} catch (BizServiceException b) {
			throw b;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("卡延期失败");
		}
		return rspCode;
	}

	// 单张卡充值
	public String singleCardRecharge(CardManagementDTO dto)
			throws BizServiceException {
		Map<String, String> rspCodeMap = RspCodeMap.getRspCodeMap();
		String rspCode="";
		try {

			for (int i = 0; i < dto.getAccountList().size(); i++) {
				AccPackageDTO accPackageDTO = new AccPackageDTO();
				
				/*上汽门户的交易流水号*/
				if(dto.getTxnNo()!=null&&dto.getOrderNo()!=null){
					accPackageDTO.setTxnNo(dto.getTxnNo());
					accPackageDTO.setOrderNo(dto.getOrderNo());
				}
				// TXN
				accPackageDTO.setTxnCode("M320");
				// 卡号
				accPackageDTO.setCardNo(dto.getTransferOutCard());
				accPackageDTO.setCreditAmonts(Amount.getDataBaseAmount(dto
						.getCreditAmonts().get(i)));
				accPackageDTO
						.setAccType(dto.getAccountList().get(i).toString());
				accPackageDTO.setServiceFee(Amount.getDataBaseAmount(dto.getServiceFee()));
				accPackageDTO = java2ACCBusinessService
						.singleCardRecharge(accPackageDTO);

				/*
				 * HashMap map = new HashMap();
				 * map.put(CFunctionConstant.TXN_TYPE, "M320");
				 * map.put(CFunctionConstant.CHANNEL, Const.JAVA2C_CHANNEL);
				 * map.put(CFunctionConstant.CARD_NO, dto.getTransferOutCard());
				 * map.put(CFunctionConstant.TXN_AMOUNT,
				 * StringUtils.strToBigDeByMuti(dto.getCreditAmonts().get(i)));
				 * // if(i==0){ //
				 * if(null!=dto.getServiceFee()&&!"".equals(dto.getServiceFee
				 * ())){ //
				 * map.put(CFunctionConstant.CARD_HOLDER_FEE,StringUtils
				 * .strToBigDeByMuti(dto // .getServiceFee())); // } // }
				 * map.put(CFunctionConstant.ACC_TYPE,
				 * dto.getAccountList().get(i)); OperationResult or =
				 * java2C.sendTpService("Switch", map,Const.JAVA2C_NORMAL,
				 * false); HashMap map1 = (HashMap) or.getDetailvo(); String
				 * rspCode = (String) map1.get(CFunctionConstant.RESP_CODE);
				 */
				rspCode = accPackageDTO.getRespCode();
				logger.debug("++++++++++++++" + rspCode
						+ "+++++++++++++++++++++++");

				if (!"00".equals(rspCode)) {
					if (rspCodeMap.containsKey(rspCode)) {
						throw new BizServiceException(rspCodeMap.get(rspCode));
					}
				}
			}
		} catch (BizServiceException e) {
			this.logger.error(e.getMessage());
			throw e;
		} catch (Exception e) {

			this.logger.error(e.getMessage());
		}
		return rspCode;
	}

	public String cardSendManage(String txnCode, String serverName,
			CardManagementDTO dto) throws BizServiceException {
		Map<String, String> rspCodeMap = RspCodeMap.getRspCodeMap();
		AccPackageDTO accPackageDTO = new AccPackageDTO();
		try {
			accPackageDTO.setTxnCode(txnCode);
			accPackageDTO.setCardNo(dto.getCardNo());
			accPackageDTO.setServiceFee(Amount.getDataBaseAmount(dto.getServiceFee()));
			// 按以前代码改写，以防出错
			accPackageDTO.setAdjustType(dto.getAdjustType());

			accPackageDTO = java2ACCBusinessService.cardOperate(accPackageDTO);

			// 以前代码
			/*
			 * HashMap map = new HashMap(); map.put(CFunctionConstant.TXN_TYPE,
			 * txnCode); map.put(CFunctionConstant.CHANNEL,
			 * Const.JAVA2C_CHANNEL); if (txnCode.equals("M100")) {
			 * map.put(CFunctionConstant.RESV2, dto.getCardNo()); if
			 * (dto.getAdjustType().equals("1")) {// 卡激活
			 * map.put(CFunctionConstant.PIN_QUIRY, "1"); } else {
			 * map.put(CFunctionConstant.PIN_QUIRY, "3");// 卡销毁 } } else
			 * map.put(CFunctionConstant.CARD_NO, dto.getCardNo()); // if (null
			 * != dto.getServiceFee() // ||
			 * !"".equals(dto.getServiceFee().trim())) { //
			 * map.put(CFunctionConstant.TXN_AMOUNT, getFormatStr(dto //
			 * .getServiceFee(), " ", 12, false)); // } OperationResult or =
			 * java2C.sendTpService(serverName, map, Const.JAVA2C_NORMAL,
			 * false); HashMap map1 = (HashMap) or.getDetailvo(); String rspCode
			 * = (String) map1.get(CFunctionConstant.RESP_CODE); logger
			 * .debug("++++++++++++++" + rspCode + "+++++++++++++++++++++++");
			 * Map<String, String> rspCodeMap = RspCodeMap.getRspCodeMap();
			 */
			if ("00".equals(accPackageDTO.getRespCode())) {
				// 卡激活成功后，如果卡的库存状态是在库状态，则将在库状态改为出库状态并记录库存操作信息
				if (null != dto.getStockState()
						&& !"3".equals(dto.getStockState())) {

					EntityStock stock = new EntityStock();
					EntityStockExample entityStockExample = new EntityStockExample();
					entityStockExample.createCriteria().andCardNoEqualTo(
							dto.getCardNo());
					stock.setStockState(OrderConst.CARD_STOCK_OUT);
					stock.setModifyUser(dto.getLoginUserId());
					stock.setModifyTime(DateUtil.getCurrentTime());
					entityStockDAO.updateByExampleSelective(stock,
							entityStockExample);
					EntityStockOperater stockOperater = new EntityStockOperater();

					stockOperater
							.setOperaterId(commonsDAO
									.getNextValueOfSequence("TB_ENTITY_STOCK_OPERATER"));
					stockOperater.setEntityId(dto.getDefaultEntityId());
					stockOperater.setFuncationRoleId("000000");
					stockOperater
							.setOperaterType(OrderConst.SINGLE_CARD_ACTIVE);
					stockOperater.setOrderFlowNode("0000");
					stockOperater.setStockState(OrderConst.CARD_STOCK_OUT);
					stockOperater.setCardNo(dto.getCardNo());
					stockOperater.setOrderId("0000000");
					stockOperater.setCreateUser(dto.getLoginUserId());
					stockOperater.setCreateTime(DateUtil.getCurrentTime());
					stockOperater.setModifyUser(dto.getLoginUserId());
					stockOperater.setModifyTime(DateUtil.getCurrentTime());
					stockOperater
							.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
					entityStockOperaterDAO.insert(stockOperater);
				}

			} else {
				if (rspCodeMap.containsKey(accPackageDTO.getRespCode())) {
					throw new BizServiceException(rspCodeMap.get(accPackageDTO.getRespCode()));
				}
			}
		} catch (BizServiceException e1) {
			throw e1;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("卡片操作失败");
		}
		return accPackageDTO.getRespCode();
	}

	// 卡安全信息查询
	@SuppressWarnings("unchecked")
	public CardManagementDTO cardSeuriyQuery(CardManagementDTO dto)
			throws BizServiceException {
		Map<String, String> rspCodeMap = RspCodeMap.getRspCodeMap();
		try {
			AccPackageDTO accPackageDTO = new AccPackageDTO();
			// TXN
			accPackageDTO.setTxnCode("S560");
			// 卡号
			accPackageDTO.setCardNo(dto.getTransferOutCard());

			accPackageDTO = java2ACCBusinessService
					.cardSeuriyQuery(accPackageDTO);

			/*
			 * HashMap map = new HashMap(); map.put(CFunctionConstant.TXN_TYPE,
			 * "S560"); map.put(CFunctionConstant.CHANNEL,
			 * Const.JAVA2C_CHANNEL); map.put(CFunctionConstant.CARD_NO,
			 * dto.getTransferOutCard()); OperationResult or =
			 * java2C.sendTpService("Switch", map, Const.JAVA2C_NORMAL, false);
			 * HashMap map1 = (HashMap) or.getDetailvo(); // 响应码 String rspCode
			 * = (String) map1.get(CFunctionConstant.RESP_CODE);
			 */
			String rspCode = accPackageDTO.getRespCode();
			logger.debug("++++++++++++++++++++++" + rspCode
					+ "++++++++++++++++=");
			if ("00".equals(rspCode)) {
				List<Map<?, ?>> list = new ArrayList<Map<?, ?>>();
				// String result = (String) map1.get(CFunctionConstant.RESV2);
				String result = accPackageDTO.getCardSeuriyStr();
				// 每条记录
				String[] records = result.split("\\|");
				if (null != result && !"".equals(result) && records != null && records.length > 0) {
					for (int i = 0; i < records.length; i++) {
						// 每个字段
						String[] fields = records[i].split("\\^");
						Map map2 = new HashMap();
						map2.put("serviceName", fields[2]);
						map2.put("posSingleAmount", Amount
								.getReallyAmount(fields[3]));
						map2.put("posDailyAmount", Amount
								.getReallyAmount(fields[4]));
						map2.put("webSingleAmount", Amount
								.getReallyAmount(fields[5]));
						map2.put("webDailyAmount", Amount
								.getReallyAmount(fields[6]));
						list.add(map2);
					}
				}

				dto.setCardSeuriyInfos(list);
				dto.setWithoutPinAmountHis(Amount
						.getReallyAmount((String) accPackageDTO
								.getWithoutPinAmount()));
			} else {
				if (rspCodeMap.containsKey(rspCode)) {
					throw new BizServiceException(rspCodeMap.get(rspCode));
				}
			}
		} catch (BizServiceException b) {
			throw b;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("卡账户安全信息查询失败");
		}
		return dto;

	}

	// 卡安全信息设置
	public String setCardSecurityInfo(CardManagementDTO dto)
			throws BizServiceException {
		Map<String, String> rspCodeMap = RspCodeMap.getRspCodeMap();
		String rspCode="";
		try {
			for (int i = 0; i < dto.getAccountList().size(); i++) {
				AccPackageDTO accPackageDTO = new AccPackageDTO();
				// TXN
				accPackageDTO.setTxnCode("S570");
				// 卡号
				accPackageDTO.setCardNo(dto.getTransferOutCard());
				// 密码
				accPackageDTO.setPassword(dto.getPassword());
				// Cvv2
				accPackageDTO.setCvv2(dto.getCvv2());

				accPackageDTO
						.setAccType(dto.getAccountList().get(i).toString());
				accPackageDTO.setServiceFee(Amount.getDataBaseAmount(dto.getServiceFee()));
				// 无pin限额，pos单笔交易限额，pos单日交易限额，网上单笔限额，网上单日限额以“|“隔开，作为一个字符串
				String updateAmount = StringUtils.strToBigDeByMuti(dto
						.getWithoutPinAmount())
						+ "|"
						+ StringUtils.strToBigDeByMuti(dto
								.getPosSingleAmounts().get(i))
						+ "|"
						+ StringUtils.strToBigDeByMuti(dto.getPosDailyAmounts()
								.get(i))
						+ "|"
						+ 0
						+ "|"
						+ 0;
				accPackageDTO.setCardSeuriyStr(updateAmount);
				accPackageDTO = java2ACCBusinessService
						.setCardSecurityInfo(accPackageDTO);
				// map.put(CFunctionConstant.RESV2, updateAmount);
				/*
				 * OperationResult or = java2C.sendTpService("Switch", map,
				 * Const.JAVA2C_NORMAL, false);
				 * 
				 * 
				 * 
				 * 
				 * HashMap map = new HashMap();
				 * 
				 * map.put(CFunctionConstant.TXN_TYPE, "S570");
				 * map.put(CFunctionConstant.CHANNEL, Const.JAVA2C_CHANNEL);
				 * map.put(CFunctionConstant.PIN_TXN, dto.getPassword());
				 * map.put(CFunctionConstant.CVV2, dto.getCvv2());
				 * map.put(CFunctionConstant.CARD_NO, dto.getTransferOutCard());
				 * 
				 * map.put(CFunctionConstant.ACC_TYPE,
				 * dto.getAccountList().get(i)); // if(i==0){ //
				 * if(null!=dto.getServiceFee
				 * ()&&!"".equals(dto.getServiceFee())){ //
				 * map.put(CFunctionConstant
				 * .TXN_AMOUNT,StringUtils.strToBigDeByMuti
				 * (dto.getServiceFee())); // } // } //
				 * 无pin限额，pos单笔交易限额，pos单日交易限额，网上单笔限额，网上单日限额以“|“隔开，作为一个字符串 String
				 * updateAmount = StringUtils.strToBigDeByMuti(dto
				 * .getWithoutPinAmount()) + "|" +
				 * StringUtils.strToBigDeByMuti(dto
				 * .getPosSingleAmounts().get(i)) + "|" +
				 * StringUtils.strToBigDeByMuti(dto.getPosDailyAmounts()
				 * .get(i)) + "|" + StringUtils.strToBigDeByMuti(dto
				 * .getWebSingleAmounts().get(i)) + "|" +
				 * StringUtils.strToBigDeByMuti(dto.getWebDailyAmounts()
				 * .get(i)); map.put(CFunctionConstant.RESV2, updateAmount);
				 * OperationResult or = java2C.sendTpService("Switch", map,
				 * Const.JAVA2C_NORMAL, false); HashMap map1 = (HashMap)
				 * or.getDetailvo(); // 响应码
				 */
				rspCode = accPackageDTO.getRespCode();

				if (!"00".equals(rspCode)) {
					if (rspCodeMap.containsKey(rspCode)) {
						throw new BizServiceException(rspCodeMap.get(rspCode));
					}
				}
			}
		} catch (BizServiceException e1) {
			throw e1;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("卡安全信息设置失败!");
		}
		return rspCode;
	}

	public CardManagementDAO getCardManagementDAO() {
		return cardManagementDAO;
	}

	public void setCardManagementDAO(CardManagementDAO cardManagementDAO) {
		this.cardManagementDAO = cardManagementDAO;
	}

	public CommonsDAO getCommonsDAO() {
		return commonsDAO;
	}

	public void setCommonsDAO(CommonsDAO commonsDAO) {
		this.commonsDAO = commonsDAO;
	}

	public CardholderCardlistDAO getCrdholderCardlistDAO() {
		return crdholderCardlistDAO;
	}

	public void setCrdholderCardlistDAO(
			CardholderCardlistDAO crdholderCardlistDAO) {
		this.crdholderCardlistDAO = crdholderCardlistDAO;
	}

	public CardholderDAO getCardholderDAO() {
		return cardholderDAO;
	}

	public void setCardholderDAO(CardholderDAO cardholderDAO) {
		this.cardholderDAO = cardholderDAO;
	}

	public ServiceDAO getServiceDAO() {
		return serviceDAO;
	}

	public void setServiceDAO(ServiceDAO serviceDAO) {
		this.serviceDAO = serviceDAO;
	}

	// 安全
	@SuppressWarnings("unchecked")
	public PageDataDTO cardSecurityInit(CardManagementQueryDTO queryDTO)
			throws BizServiceException {
		Map<String, String> rspCodeMap = RspCodeMap.getRspCodeMap();
		try {
			// 之前写的代码调用的是S570接口，S570只有RespCode返回，应该是之前写错了，特改成S560：卡安全信息查询
			AccPackageDTO accPackageDTO = new AccPackageDTO();
			// TXN
			accPackageDTO.setTxnCode("S560");
			// 卡号
			accPackageDTO.setCardNo(queryDTO.getCardNo());

			accPackageDTO = java2ACCBusinessService
					.cardSeuriyQuery(accPackageDTO);
			// 响应码
			String rspCode = accPackageDTO.getRespCode();
			logger.debug("++++++++++++++++++++++" + rspCode
					+ "++++++++++++++++=");
			if ("00".equals(rspCode)) {
				List<Map<?, ?>> list = new ArrayList<Map<?, ?>>();
				String result = accPackageDTO.getCardSeuriyStr();
				String[] records = result.split("\\|");
				if (null != result && !"".equals(result) && records != null && records.length > 0) {
					for (int i = 0; i < records.length; i++) {
						String[] fields = records[i].split("\\^");
						Map map2 = new HashMap();
						map2.put("accountNo", fields[0]);
						map2.put("accountType", fields[1]);
						map2.put("serviceName", fields[2]);
						map2.put("posDailyAmount", Amount
								.getReallyAmount(fields[3]));
						map2.put("posSingleAmount", Amount
								.getReallyAmount(fields[4]));
						map2.put("webDailyAmount", Amount
								.getReallyAmount(fields[5]));
						map2.put("webSingleAmount", Amount
								.getReallyAmount(fields[6]));
						map2.put("withoutPinAmount", Amount
								.getReallyAmount(accPackageDTO
										.getWithoutPinAmount()));
						list.add(map2);
					}
				}
				pageDataDTO.setData(list);
			} else {
				if (rspCodeMap.containsKey(rspCode)) {
					throw new BizServiceException(rspCodeMap.get(rspCode));
				}
			}

			// 之前写的代码调用的是S570接口，S570只有RespCode返回，应该是之前写错了，特改成S560：卡安全信息查询

			// 之前的代码
			/*
			 * HashMap map = new HashMap(); map.put(CFunctionConstant.TXN_TYPE,
			 * "S570"); map.put(CFunctionConstant.CHANNEL,
			 * Const.JAVA2C_CHANNEL); map.put(CFunctionConstant.CARD_NO,
			 * queryDTO.getCardNo()); OperationResult or =
			 * java2C.sendTpService("Switch", map, Const.JAVA2C_NORMAL, false);
			 * HashMap map1 = (HashMap) or.getDetailvo(); String rspCode =
			 * (String) map1.get(CFunctionConstant.RESP_CODE);
			 * logger.debug("++++++++++++++++++++++" + rspCode +
			 * "++++++++++++++++="); if ("00".equals(rspCode)) { List<Map<?, ?>>
			 * list = new ArrayList<Map<?, ?>>(); String result = (String)
			 * map1.get(CFunctionConstant.RESV2); String[] records =
			 * result.split("\\|"); if (result != "" && records != null &&
			 * records.length > 0) { for (int i = 0; i < records.length; i++) {
			 * String[] fields = records[i].split("\\^"); Map map2 = new
			 * HashMap(); map2.put("accountNo", fields[0]);
			 * map2.put("accountType", fields[1]); map2.put("serviceName",
			 * fields[2]); map2.put("posDailyAmount",
			 * Amount.getReallyAmount(fields[3])); map2.put("posSingleAmount",
			 * Amount.getReallyAmount(fields[4])); map2.put("webDailyAmount",
			 * Amount.getReallyAmount(fields[5])); map2.put("webSingleAmount",
			 * Amount.getReallyAmount(fields[6])); map2.put("withoutPinAmount",
			 * Amount.getReallyAmount((String)
			 * map1.get(CFunctionConstant.PIN_TXN))); list.add(map2); } }
			 * pageDataDTO.setData(list); }
			 */
		} catch (BizServiceException b) {
			throw b;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("卡账户安全信息查询失败");
		}
		return pageDataDTO;
	}

	/**
	 * 赎回
	 * 
	 * @param dto
	 * @throws BizServiceException
	 */
	public String redemptionCard(CardManagementDTO dto)
			throws BizServiceException {
		Map<String, String> rspCodeMap = RspCodeMap.getRspCodeMap();
		try {
			AccPackageDTO accPackageDTO = new AccPackageDTO();
			accPackageDTO.setTxnCode("M410");
			accPackageDTO.setCardNo(dto.getTransferOutCard());
			accPackageDTO.setCallBack(dto.getCallBack());

			accPackageDTO
					.setCardState(OrderConst.RANSON_ORIG_CARD_STAT_CHECK);
			accPackageDTO.setServiceFee(Amount.getDataBaseAmount(dto.getServiceFee()));
			accPackageDTO = java2ACCBusinessService
					.oneCardDestroy(accPackageDTO);
			
			respCode = accPackageDTO.getRespCode();
			dto.setBalance(accPackageDTO.getServiceFee());
			if ("00".equals(respCode)) {
				oneCardStockState(dto, OrderConst.CARD_STOCK_DESTORYED);
				logger.debug("卡片操作成功!!");
			} else {
				if (rspCodeMap.containsKey(respCode)) {
					throw new BizServiceException(rspCodeMap.get(respCode));
				} else {
					logger.error("卡赎回失败!错误码:" + respCode);
					throw new BizServiceException("卡赎回失败!错误码:" + respCode);
				}
			}
			
		} catch (BizServiceException e1) {
			logger.debug("卡片操作失败！！");
			throw e1;
		} catch (Exception e) {
			logger.debug("卡片操作失败！！");
			logger.error(e.getMessage(), e);
		}
		return respCode;
	}

	/**
	 * 单卡库存状态修改
	 * 
	 * @param dto
	 * @param CardState
	 *            卡状态
	 */
	private void oneCardStockState(CardManagementDTO dto, String CardState) {
		EntityStock entityStock = new EntityStock();
		entityStock.setStockState(CardState);
		EntityStockExample entityStockExample = new EntityStockExample();
		entityStockExample.createCriteria().andCardNoEqualTo(
				dto.getTransferOutCard());
		entityStockDAO
				.updateByExampleSelective(entityStock, entityStockExample);
	}

	public CardManagementDTO viewOrderByCardNo(
			CardManagementDTO cardManagementDTO) throws BizServiceException {
		CardManagementDTO cardManagementDto;
		try {
			cardManagementDto = cardManageServiceDAO
					.viewOrderByCardNo(cardManagementDTO);
			cardManagementDto.setBalance(cardManagementDTO.getBalance());
		} catch (BizServiceException e1) {
			throw e1;
		} catch (Exception e) {

			this.logger.error(e.getMessage());
			throw new BizServiceException("查询原订单失败");
		}
		return cardManagementDto;
	}

	// 安全信息设置
	public String updateCardSecurity(CardManagementDTO dto)
			throws BizServiceException {
		Map<String, String> rspCodeMap = RspCodeMap.getRspCodeMap();
		String rspCode="";
		try {
			AccPackageDTO accPackageDTO = new AccPackageDTO();
			// TXN
			accPackageDTO.setTxnCode("S570");
			// 卡号
			accPackageDTO.setCardNo(dto.getCardNo());
			// 密码
			// accPackageDTO.setPassword(dto.getPassword());
			// Cvv2
			// accPackageDTO.setCvv2(dto.getCvv2());

			accPackageDTO.setAccType(getFormatStr(dto.getAccountNo(), " ", 8,
					false));
			accPackageDTO.setServiceFee(dto.getServiceFee());
			accPackageDTO = java2ACCBusinessService
					.setCardSecurityInfo(accPackageDTO);

			/*
			 * HashMap map = new HashMap(); map.put(CFunctionConstant.TXN_TYPE,
			 * "S570"); map.put(CFunctionConstant.CHANNEL,
			 * Const.JAVA2C_CHANNEL); map.put(CFunctionConstant.CARD_NO,
			 * dto.getCardNo()); map.put(CFunctionConstant.ACC_TYPE,
			 * getFormatStr(dto.getAccountNo(), " ", 8, false));
			 * map.put(CFunctionConstant.ORI_CARD_HOLDER_FEE,
			 * dto.getServiceFee()); //以前也被注释的代码 // String updateAmount =
			 * Amount.getDataBaseAmount(getFormatStr(dto //
			 * .getWithoutPinAmount(), " ", 12, false)) // + "|" // +
			 * Amount.getDataBaseAmount(getFormatStr(dto //
			 * .getPosSingleAmount(), " ", 12, false)) // + "|" // +
			 * Amount.getDataBaseAmount(getFormatStr(dto //
			 * .getPosDailyAmount(), " ", 12, false)) // + "|" // +
			 * Amount.getDataBaseAmount(getFormatStr(dto //
			 * .getWebSingleAmount(), " ", 12, false)) // + "|" // +
			 * Amount.getDataBaseAmount(getFormatStr(dto //
			 * .getWebDailyAmount(), " ", 12, false)); //
			 * map.put(CFunctionConstant.RESV2, updateAmount); OperationResult
			 * or = java2C.sendTpService("Switch", map, Const.JAVA2C_NORMAL,
			 * false);
			 * 
			 * HashMap map1 = (HashMap) or.getDetailvo();
			 */
			rspCode = accPackageDTO.getRespCode();
			logger
					.debug("+++++++++++++++++++" + rspCode
							+ "++++++++++++++====");
			if ("00".equals(rspCode)) {
				logger.debug("卡帐户安全信息设置成功");
			} else {
				if (rspCodeMap.containsKey(rspCode)) {
					throw new BizServiceException(rspCodeMap.get(rspCode));
				}
			}
		} catch (BizServiceException b) {
			throw b;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("卡账户安全信息设置失败");
		}
		return rspCode;
	}

	// 卡交易查询
	public PageDataDTO cardSelect(CardManagementQueryDTO dto)
			throws BizServiceException {
		try {
			Map<String, String> rspCodeMap = RspCodeMap.getRspCodeMap();
			/** 卡账务调整检验，卡交易查询则跳过检验 */
			if (dto.getIsBo().equals("1")) {
				boolean checkResult = authForAccountAdjust(dto.getCardNo(), dto
						.getDefaultEntityId());
				if (checkResult == false) {
					logger.warn("cardSelect faild!");
					throw new BizServiceException("卡查询失败");
				}
			}

			TxnPackageDTO txnPackageDTO = new TxnPackageDTO();
			// TXN
			txnPackageDTO.setTxnCode("S010");
			// 卡号
			txnPackageDTO.setCardNO(dto.getCardNo());
			// 查询标志0当天 1历史
			txnPackageDTO.setFlag(dto.getCardFlag().toString());
			// 交易类型
			txnPackageDTO.setTxnType(dto.getTxnType());
			// 起始日期
			txnPackageDTO.setStart_dt(dto.getStarDate());
			// 结束日期
			txnPackageDTO.setEnd_dt(dto.getEnDate());
			// 起始行
			txnPackageDTO.setStart_row(String.valueOf(dto
					.getFirstCursorPosition()));
			// 结束行
			txnPackageDTO.setEnd_row(String
					.valueOf(dto.getLastCursorPosition()));

			txnPackageDTO = java2TXNBusinessService
					.cardTradeQuery(txnPackageDTO);

			String respCode = txnPackageDTO.getRspCode();
			if ("00".equals(respCode)) {
				pageDataDTO.setData(txnPackageDTO.getList());
				pageDataDTO.setTotalRecord(Integer.parseInt(txnPackageDTO
						.getTotalRow()));
				pageDataDTO.setPageSize(Integer.parseInt(txnPackageDTO
						.getRealRow()));
				logger.debug("查看卡交易成功");
			} else {
				if (rspCodeMap.containsKey(respCode)) {
					throw new BizServiceException(rspCodeMap.get(respCode));
				}
			}

			/* 卡账务调整检验，卡交易查询则跳过检验 以前的代码 */
			/*
			 * if (dto.getIsBo().equals("1")) { boolean checkResult =
			 * authForAccountAdjust(dto.getCardNo(), dto.getDefaultEntityId());
			 * if (checkResult == false) { logger.warn("cardSelect faild!");
			 * throw new BizServiceException("卡查询失败"); } }
			 * 
			 * HashMap map = new HashMap(); String requestResv2 = "S010" + "|" +
			 * dto.getCardNo() + "|" + dto.getCardFlag() + "|" +
			 * dto.getTxnType() + "|" + dto.getStarDate() + "|" +
			 * dto.getEnDate() + "|" + dto.getFirstCursorPosition() + "|" +
			 * dto.getLastCursorPosition(); map.put(CFunctionConstant.RESV2,
			 * requestResv2); OperationResult or =
			 * java2C.sendTpService("vCardTxnInq", map,Const.JAVA2C_BIG_AMT,
			 * false); HashMap map1 = (HashMap) or.getDetailvo(); String
			 * respCode = (String) map1.get(CFunctionConstant.RESP_CODE); if
			 * ("00".equals(respCode)) { List<Map<?, ?>> list = new
			 * ArrayList<Map<?, ?>>(); String result = (String)
			 * map1.get(CFunctionConstant.OTHERDATA); String[] records =
			 * result.split("\\|"); if (result != "" && records != null &&
			 * records.length > 0) { for (int i = 0; i < records.length; i++) {
			 * String[] fields = records[i].split("\\^"); HashMap map2 = new
			 * HashMap(); map2.put("txnId", fields[0]); map2.put("sysSeqNum",
			 * fields[1]); map2.put("cardNo", fields[2]); map2.put("merchantId",
			 * fields[3]); map2.put("merchantName", fields[4]);
			 * map2.put("shopId", fields[5]); map2.put("shopName", fields[6]);
			 * map2.put("txnType", fields[7]); map2.put("txnTypeString",
			 * ReflectionUtil.getMap().get(fields[7])); map2.put("txnAmt", new
			 * BigDecimal(fields[8]).divide(new BigDecimal(100)).toString());
			 * map2.put("txnState", fields[9]); map2.put("txnDate", fields[10]);
			 * map2.put("txnFee", fields[11]); map2.put("cardAccpTermId",
			 * fields[12]); logger.debug("fields 13:"+fields[13]);
			 * logger.debug("fields 14:"+fields[14]); map2.put("totalBalance",
			 * Amount.getReallyAmount(fields[13])); map2.put("balance",
			 * Amount.getReallyAmount(fields[14])); list.add(map2); }
			 * 
			 * } pageDataDTO.setData(list); String totalRow = (String)
			 * map1.get(CFunctionConstant.REC_TXN_DATE); String realRow =
			 * (String) map1.get(CFunctionConstant.REC_TXN_TIME);
			 * pageDataDTO.setTotalRecord(Integer.parseInt(totalRow));
			 * pageDataDTO.setPageSize(Integer.parseInt(realRow));
			 * logger.debug("查看卡交易成功"); } else { throw new
			 * BizServiceException("查询交易失败,错误码:" + respCode); }
			 */
		} catch (BizServiceException ex) {
			throw ex;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查看卡交易失败");
		}
		return pageDataDTO;

	}

	// 查看卡交易明细
	@SuppressWarnings("unchecked")
	public CardManagementDTO cardSelectDetail(CardManagementDTO dto)
			throws BizServiceException {
		try {
			cardManagementDTO = queryCard(dto);
			logger.debug(cardManagementDTO.getCardNo());
			PageDataDTO pagequery = pageQueryDAO.query(
					"CARDHOLDERCARDLIST.selectCardholderCardlist",
					cardManagementDTO);
			cardholderDTO = new CardholderDTO();
			if (pagequery != null && pagequery.getData() != null
					&& pagequery.getData().size() > 0) {
				HashMap map = (HashMap) pagequery.getData().get(0);
				cardManagementDTO
						.setCustomerId(map.get("customerId") == null ? ""
								: (String) map.get("customerId"));
				cardManagementDTO
						.setCustomerName(map.get("customerName") == null ? ""
								: (String) map.get("customerName"));
				cardholderDTO.setIdNo((map.get("idNo") == null ? ""
						: (String) map.get("idNo")));
				cardholderDTO
						.setFirstName((map.get("cardholderName") == null ? ""
								: (String) map.get("cardholderName")));
				cardholderDTO.setIdType(map.get("idType") == null ? ""
						: (String) map.get("idType"));
				cardholderDTO
						.setCardholderMobile(map.get("cardholderMobile") == null ? ""
								: (String) map.get("cardholderMobile"));
				cardholderDTO.setExternalId(map.get("externalId") == null ? ""
						: (String) map.get("externalId"));
			}
			cardManagementDTO.setCardholderDTO(cardholderDTO);
			merchantDTO = dto.getMerchantDTO();
			MerchantExample merchantExample = new MerchantExample();
			merchantExample.createCriteria().andEntityIdEqualTo(
					merchantDTO.getEntityId());
			List<Merchant> merchants = merchantServiceDAO
					.selectByExample(merchantExample);
			if (null != merchants) {
				if (merchants.size() > 0)
					ReflectionUtil.copyProperties(merchantServiceDAO
							.selectByExample(merchantExample).get(0),
							merchantDTO);

			}
			// merchantDTO=merchantService.viewMerchant(merchantDTO);
			cardManagementDTO.setMerchantDTO(merchantDTO);
			shopDTO = dto.getShopDTO();
			shopExample = new ShopExample();
			shopExample.createCriteria().andShopIdEqualTo(shopDTO.getShopId());
			List<Shop> shopList = shopServiceDAO.selectByExample(shopExample);
			Shop shop = shopList.get(0);
			ShopDTO resultDto = new ShopDTO();
			ReflectionUtil.copyProperties(shop, resultDto);
			cardManagementDTO.setShopDTO(shopDTO);
			cardManagementDTO.setSysSeqNum(dto.getSysSeqNum());
			cardManagementDTO.setTxnAmt(dto.getTxnAmt());
			cardManagementDTO.setTxnDate(dto.getTxnDate());
			cardManagementDTO.setTxnType(dto.getTxnType());
			cardManagementDTO.setServiceFee(dto.getServiceFee());
			logger.debug("view card txn detail success!");
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查看卡交易明细失败");
		}
		return cardManagementDTO;
	}

	private String getFormatStr(String str, String appendStr, int length,
			boolean appendBefore) throws Exception {
		if (null == str) {
			str = "";
		}
		if (null == appendStr) {
			appendStr = "0";
		}
		int circleNum = length - str.length();
		StringBuffer buffer = new StringBuffer(str);
		if (circleNum < 0) {
			str = str.substring(length);
		} else {
			for (int i = 0; i < circleNum; i++) {
				if (appendBefore) {
					buffer.insert(0, appendStr);
				} else {
					buffer.append(appendStr);
				}
			}
		}
		// logger.debug(str.length());
		// logger.debug(str);
		return buffer.toString();

	}

	public MerchantService getMerchantService() {
		return merchantService;
	}

	public void setMerchantService(MerchantService merchantService) {
		this.merchantService = merchantService;
	}

	public ShopService getShopService() {
		return shopService;
	}

	public void setShopService(ShopService shopService) {
		this.shopService = shopService;
	}

	/**
	 * 卡账务调整中，check该用户是否被允许操作该卡
	 * 
	 * @author Yifeng.Shi
	 * @serialData 2011-07-21
	 * @param cardNo
	 *            ,entityId
	 * @return true,false
	 * */
	@SuppressWarnings("unchecked")
	private boolean authForAccountAdjust(String cardNo, String entityId)
			throws Exception, BizServiceException {
		logger.debug("into isAllowOperateCardForAccountAdjust");
		/* 空值则直接返回失败 */
		if (StringUtil.isEmpty(cardNo) || StringUtil.isEmpty(entityId)) {
			logger.warn("cardNo or entityId is empty!");
			throw new BizServiceException("查询失败!");
		}
		EntityStock currentCardStock = entityStockDAO
				.selectByPrimaryKey(cardNo);
		if (null == currentCardStock
				|| (currentCardStock.getCardNo() == null || currentCardStock
						.getCardNo().equals(""))) {
			logger.warn("can't find the card from entityStock!");
			throw new BizServiceException("找不到该卡!");
		}
		IssuerExample issuerExample = new IssuerExample();
		issuerExample.createCriteria().andEntityIdEqualTo(entityId);
		List<Issuer> issuerList = issuerDAO.selectByExample(issuerExample);
		if (null != issuerList && issuerList.size() > 0) {
			/* 发卡机构取本机构定义和拥有、代发合同关联产品 */
			List<HashMap<String, String>> productList = baseDAO.queryForList(
					"LOYALTYCONTRACT", "selProdFromContractAndProduct",
					entityId);
			if (null == productList || productList.size() == 0) {
				logger.warn("can't find product from sellContract!");
				throw new BizServiceException("只能操作本机构所属或合同给定的产品!");
			}
			for (HashMap<String, String> tempMap : productList) {
				if (tempMap.get("productId").equals(
						currentCardStock.getProductId())) {
					logger.warn("find out product from contract!");
					return true;
				}
			}
			logger.warn("can't find product from sellContract!");
			throw new BizServiceException("只能操作本机构所属或合同给定的产品!");

		} else {
			return sellerAllowOperate(entityId, currentCardStock);
		}
	}

	/**
	 * 卡操作管理的查询中，check该用户是否被允许操作该卡
	 * 
	 * @author Yifeng.Shi
	 * @serialData 2011-07-21
	 * @param cardNo
	 *            ,entityId
	 * @return true,false
	 * */
	@SuppressWarnings("unchecked")
	private boolean authForCardQuery(String cardNo, String entityId)
			throws Exception, BizServiceException {
		logger.debug("into isAllowOperateCardForAccountAdjust");
		/* 空值则直接返回失败 */
		if (StringUtil.isEmpty(cardNo) || StringUtil.isEmpty(entityId)) {
			logger.warn("cardNo or entityId is empty!");
			throw new BizServiceException("查询失败!");
		}
		EntityStock currentCardStock = entityStockDAO
				.selectByPrimaryKey(cardNo);
		if (null == currentCardStock
				|| (currentCardStock.getCardNo() == null || currentCardStock
						.getCardNo().equals(""))) {
			logger.warn("can't find the card from entityStock!");
			throw new BizServiceException("找不到该卡!");
		}
		IssuerExample issuerExample = new IssuerExample();
		issuerExample.createCriteria().andEntityIdEqualTo(entityId);
		List<Issuer> issuerList = issuerDAO.selectByExample(issuerExample);
		if (null != issuerList && issuerList.size() > 0) {
			/* 发卡机构取本机构定义和拥有、代发合同关联产品 */
			ProductExample productExample = new ProductExample();
			productExample.createCriteria().andProductDefineIssuerEqualTo(
					entityId).andProductIdEqualTo(
					currentCardStock.getProductId());
			List<Product> productList = productDAO
					.selectByExample(productExample);
			if (null != productList && productList.size() > 0) {
				/* 本机构定义产品 */
				if (productList.get(0).getEntityId().equals(entityId)) {
					return true;
				} else {
					return false;
				}
			} else {
				LoyaltyContractQueryDTO queryDTO = new LoyaltyContractQueryDTO();
				queryDTO.setContractBuyer(entityId);
				List<HashMap<String, String>> ProdConList = baseDAO
						.queryForList("LOYALTYCONTRACT",
								"selectContractAndProduct", queryDTO);
				if (null == productList || productList.size() <= 0) {
					logger.warn("can't find product from sellContract!");
					throw new BizServiceException("只能操作本机构发行或合同给定的产品!");
				} else {
					for (HashMap<String, String> tempMap : ProdConList) {
						if (tempMap.get("productId").equals(
								currentCardStock.getProductId())) {
							logger.warn("find out product from contract!");
							return true;
						}
					}
					logger.warn("can't find product from sellContract!");
					throw new BizServiceException("只能操作本机构发行或合同给定的产品!");
				}
			}
		} else {
			return sellerAllowOperate(entityId, currentCardStock);
		}
	}

	/*
	 * 营销机构是否被允许操作卡
	 */
	@SuppressWarnings("unchecked")
	private boolean sellerAllowOperate(String entityId,
			EntityStock currentCardStock) throws BizServiceException, Exception {
		SellerExample sellerExample = new SellerExample();
		sellerExample.createCriteria().andEntityIdEqualTo(entityId);
		List<Seller> sellerList = sellerDAO.selectByExample(sellerExample);
		if (null != sellerList && sellerList.size() > 0) {
			/* 营销机构取营销营销合同、营销发卡合同关联产品 */
			List<HashMap<String, String>> productList = baseDAO.queryForList(
					"SELLERCONTRACT", "selProdFromContractForCardOperate",
					entityId);
			if (null == productList || productList.size() == 0) {
				logger.warn("can't find product from sellContract!");
				throw new BizServiceException("只能操作本机构合同给定的产品!");
			}
			for (HashMap<String, String> tempMap : productList) {
				if (tempMap.get("productId").equals(
						currentCardStock.getProductId())) {
					logger.warn("find out product from sellContract!");
					return true;
				}
			}
			logger.warn("can't find product from sellContract!");
			throw new BizServiceException("只能操作本机构合同给定的产品!");
		} else {
			logger.warn("can't find product from sellContract!");
			throw new BizServiceException("只能操作本机构合同给定的产品!");
		}
	}
	
	
	//判断卡片是否出库
	public void checkCardIsAct(CardManagementDTO cardManagementDTO) throws BizServiceException{
		try {
			String cardNO=null;
			if(null!=cardManagementDTO.getTransferOutCard() && !"".equals(cardManagementDTO.getTransferOutCard())){
				cardNO = (String)baseDAO.queryForObject("CARDMANAGEMENT.checkCardStock", cardManagementDTO.getTransferOutCard());
			}
			if(null == cardNO || "".equals(cardNO.trim())){
				throw new BizServiceException("该卡未出库不能激活");
			}
		} catch (BizServiceException b){
			throw b;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			this.logger.error(e.getMessage());
			throw new BizServiceException("卡片激活失败");
		}
	}
	
	//换卡   换卡时先激活新卡再转账
	public String changeCard(CardManagementDTO dto) throws BizServiceException{
		try {
			String serviceFee = dto.getServiceFee();
			//卡片激活  不扣手续费
			dto.setServiceFee("0");
			dto.setAdjustType("1");
			dto.setCardNo(dto.getTransferInCard().trim());
			cardSendManage("M100", "Switch", dto);
			//转账
			dto.setServiceFee(serviceFee);
			String rspCode = transferAccount(dto);
			return rspCode;
		} catch (BizServiceException b){
			throw b;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			this.logger.error(e.getMessage());
			throw new BizServiceException("卡片换卡失败");
		}
	}

    /* (non-Javadoc)
     * @see com.huateng.univer.cardmanage.biz.service.CardManageService#checkCardIsStock(com.huateng.univer.cardmanagement.dto.BatchCardActionDTO)
     */
    @Override
    public int checkCardIsStock(BatchCardActionDTO dto) throws BizServiceException {
       Integer num=(Integer)baseDAO.
       queryForObject("TB_ENTITY_STOCK.abatorgenerated_selectIsStock", dto);
        return num.intValue();
    }

    /* (non-Javadoc)
     * @see com.huateng.univer.cardmanage.biz.service.CardManageService#submitInvalid(com.huateng.univer.cardmanagement.dto.BatchCardActionDTO)
     */
    @Override
    public void submitInvalid(BatchCardActionDTO batchCardDTO) throws BizServiceException {
        int count=checkCardIsStock(batchCardDTO);
        if(count==0){
            logger.error("作废的卡片不在本机构库存中！");
            throw new BizServiceException("作废的卡片不在本机构库存中！");
        }
        else{
            doOprater(batchCardDTO);
        }
    }
    /*
     * (non-Javadoc)
     * @see com.huateng.univer.cardmanage.biz.service.BatchCardService#doOprater(com.huateng.univer.cardmanagement.dto.BatchCardActionDTO)
     */
    public void doOprater(BatchCardActionDTO batchCardDTO)throws BizServiceException{
	 // 在卡片状态未更新前取出来。
        List<BatchCardInfoDTO> list=new ArrayList<BatchCardInfoDTO>();
        if(batchCardDTO!=null){
            list = accCardInfoDao.getAccCardInfoBySell(batchCardDTO);
        }else{
            throw new BizServiceException("参数传递失败！");
        }
        logger.info("作废的卡号为"+batchCardDTO.getCardNoArray()[0].toString());
        try {
            accCardInfoDao.updateStatusBySeller(batchCardDTO);
        } catch (Exception e) {
            logger.error("更新卡状态失败！!");
            logger.info(e.getStackTrace());
            logger.info(e.getMessage());
            throw new BizServiceException("更新卡状态失败！");
        }
        BatchCardInfoDTO bean = (BatchCardInfoDTO)list.get(0);
        // 得到产品号和机构号
        SellOrder sellOrder = new SellOrder();
        sellOrder.setOrderId(commonsDAO.getNextValueOfSequence("TB_SELL_ORDER"));
        sellOrder.setOrderType(OrderConst.ORDER_TYPE_CARD_INVOICE);
        sellOrder.setProductId(bean.getProductId().trim());
        // 将机构号设置成订单发起方
        sellOrder.setFirstEntityId(batchCardDTO.getIssuerId().trim());
        sellOrder.setCardQuantity(DataBaseConstant.ONE_CARD);
        sellOrder.setRealCardQuantity(DataBaseConstant.ONE_CARD);
        String date = DateUtil.getCurrentTime();
        sellOrder.setOrderDate(date.substring(0, 8));
        sellOrder.setCreateTime(date);
        sellOrder.setModifyTime(date);
        sellOrder.setCreateUser(batchCardDTO.getUser());
        sellOrder.setModifyUser(batchCardDTO.getUser());
        sellOrder.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
        sellOrder.setMemo(batchCardDTO.getMemo());
        logger.info("开始插入作废订单信息！");
        try {
            sellOrderDAO.insert(sellOrder);
        } catch (Exception e) {
            logger.error("插入作废订单失败！");
            logger.error(e.getMessage());
        }
        logger.info("结束插入作废订单信息！");
        SellOrderList sellOrderList = new SellOrderList();
        sellOrderList.setOrderListId(commonsDAO.getNextValueOfSequence("TB_SELL_ORDER_LIST"));
        sellOrderList.setOrderId(sellOrder.getOrderId());
        sellOrderList.setProductId(bean.getProductId().trim());
        sellOrderList.setCreateTime(date);
        sellOrderList.setModifyTime(date);
        sellOrderList.setCreateUser(batchCardDTO.getUser());
        sellOrderList.setModifyUser(batchCardDTO.getUser());
        sellOrderList.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
        logger.info("开始插入作废订单详细信息！");
        try {
            sellOrderListDAO.insert(sellOrderList);
        } catch (Exception e) {
            logger.error("插入订单信息失败");
            logger.error(e.getMessage());
        }
        logger.info("结束插入作废订单详细信息！");
        logger.info("开始插入作废订单卡信息！");
        for (BatchCardInfoDTO record : list) {
            SellOrderCardList sellOrderCardList = new SellOrderCardList();
            sellOrderCardList.setOrderCardListId(commonsDAO
                    .getNextValueOfSequenceBySequence("SEQ_SELL_ORDER_CARD_LIST"));
            sellOrderCardList.setOrderId(sellOrder.getOrderId());
            sellOrderCardList.setOrderListId(sellOrderList.getOrderListId());
            sellOrderCardList.setCardNo(record.getCardNo());
            sellOrderCardList.setCreateTime(date);
            sellOrderCardList.setModifyTime(date);
            sellOrderCardList.setCreateUser(batchCardDTO.getUser());
            sellOrderCardList.setModifyUser(batchCardDTO.getUser());
            sellOrderCardList.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
            try{
            sellOrderCardListDAO.insert(sellOrderCardList);
            }catch(Exception e){
                logger.info(e.getMessage());
            }
            EntityStock entityStock=new EntityStock();
            entityStock=entityStockDAO.selectByPrimaryKey(record.getCardNo());
            if(entityStock!=null&&entityStock.getCardNo()!=null){
                EntityStockExample example=new EntityStockExample();
                example.createCriteria().andCardNoEqualTo(record.getCardNo());
                entityStock.setFldResData(OrderConst.CARD_STOCK_INVALID);
                entityStock.setModifyTime(date);
                entityStock.setModifyUser(batchCardDTO.getUser());
                entityStockDAO.updateByExample(entityStock, example);   
            }
        }
        logger.info("结束插入作废订单卡信息！");
    }
/*	public static void main(String[] args) {
		String a = "9999999.99";
		logger.info(String.valueOf(new BigDecimal(a)));
	}*/

    /**
     * 卡片激活
     */
	@Override
	public String activateCard(CardManagementDTO dto) throws BizServiceException {
		/*Map<String, String> rspCodeMap = RspCodeMap.getRspCodeMap();
		try {
			AccPackageDTO accPackageDTO = new AccPackageDTO();
			accPackageDTO.setTxnCode("S410");
			accPackageDTO.setCardNo(dto.getTransferOutCard());

			accPackageDTO
					.setActiveState(dto.getActive());
			accPackageDTO.setServiceFee(Amount.getDataBaseAmount(dto.getServiceFee()));	
			accPackageDTO = java2ACCBusinessService.OprateOrigCard(accPackageDTO);
			
			respCode = accPackageDTO.getRespCode();
			dto.setBalance(accPackageDTO.getServiceFee());	//余额
			if ("00".equals(respCode)) {
				oneCardStockState(dto, OrderConst.ORDER_FLOW_OPRATION_ACTIVATE);
				logger.debug("卡片操作成功!!");
			} else {
				if (rspCodeMap.containsKey(respCode)) {
					throw new BizServiceException(rspCodeMap.get(respCode));
				} else {
					logger.error("卡激活失败!错误码:" + respCode);
					throw new BizServiceException("卡激活失败!错误码:" + respCode);
				}
			}
			
		} catch (BizServiceException e1) {
			logger.debug("卡片操作失败！！");
			throw e1;
		} catch (Exception e) {
			logger.debug("卡片操作失败！！");
			logger.error(e.getMessage(), e);
		}
		return respCode;*/
		
		String rspCode="00";
		try {
			SellOrderDTO sellOrder=null;
			List<SellOrderDTO> list =  orderAndCountDAO.orderIdAndCount(dto);
			//logger.debug("orderType is"+sellOrder.getOrderType());
			for(int i=0;i<list.size();i++){
				SellOrderDTO	selldto = list.get(i);
				if(i==0){
					sellOrder = selldto;
				}else{
					if(Integer.parseInt(selldto.getOrderId())>Integer.parseInt(sellOrder.getOrderId())){
						sellOrder = selldto;
					}
				}
				
			}
			if (Integer.parseInt(sellOrder.getCardQuantity()) > 1 ){
				throw new BizServiceException("该卡属于批量卡，不能单卡激活！");
			}else{
				orderActActionImpl.submitOrderForAct(sellOrder);
			}
		} catch (BizServiceException b){
			throw b;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			this.logger.error(e.getMessage());
			throw new BizServiceException("激活失败");
		}
	
		return rspCode;
	}
	
	@Override
	public PageDataDTO transactionQuery(TransactionQueryDTO dto)
			throws BizServiceException {
		// TODO Auto-generated method stub
		try {

			PageDataDTO pageDataDTO = pageQueryDAO.query(
					"CARDMANAGEMENT.selectTransactionInfo", dto);
			return pageDataDTO;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询失败");
		}
	}
	
	@Override
	public PageDataDTO stanStifQuery(StanStifQueryDTO dto)
			throws BizServiceException {
		// TODO Auto-generated method stub
		try {

			PageDataDTO pageDataDTO = pageQueryDAO.query(
					"CARDMANAGEMENT.selectStanStifList", dto);
			return pageDataDTO;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询失败");
		}
	}
	@Override
	public StanStifQueryDTO stanStifQueryInfo(StanStifQueryDTO dto)
			throws BizServiceException {
		// TODO Auto-generated method stub
		try {
			
			StanStifQueryDTO stanStifQueryDTO= (StanStifQueryDTO) baseDAO.queryForObject(
					"CARDMANAGEMENT.selectStanStifInfo", dto);
			return stanStifQueryDTO;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询失败");
		}
	}
	
	@Override
	public PageDataDTO queryProvisionsBanlance(TransactionQueryDTO dto)
			throws BizServiceException {
		// TODO Auto-generated method stub
		try {

			PageDataDTO pageDataDTO = pageQueryDAO.query(
					"CARDMANAGEMENT.selectProvisionsBanlance", dto);
			return pageDataDTO;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询失败");
		}
	}
}

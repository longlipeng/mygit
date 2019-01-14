/**
 * Classname RansomOrderServiceImpl.java
 *
 * Version information
 *
 * Licensed Property to HuaTeng Data Co., Ltd.
 * 
 * (C) Copyright of HuaTeng Data Co., Ltd. 2010
 *     All Rights Reserved.
 *
 * Project Info: HuaTeng Data Internet Acquiring  Project
 * 
 * Modification History:
 * =============================================================================
 *		Author		Date		Description
 *   ------------ ---------- ---------------------------------------------------
 *		wpf		2012-12-17
 * =============================================================================
 */

package com.huateng.univer.order.business.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.seller.cardholder.dto.CardholderQueryDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderOrigCardListDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderOrigCardListQueryDTO;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.constant.OrderConst;
import com.huateng.framework.constant.RansomOrderConst;
import com.huateng.framework.dao.BaseDAO;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.dao.PageQueryDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.CardholderDAO;
import com.huateng.framework.ibatis.dao.EntityStockDAO;
import com.huateng.framework.ibatis.dao.ProductDAO;
import com.huateng.framework.ibatis.dao.SellOrderDAO;
import com.huateng.framework.ibatis.dao.SellOrderOrigCardListDAO;
import com.huateng.framework.ibatis.model.Product;
import com.huateng.framework.ibatis.model.SellOrder;
import com.huateng.framework.ibatis.model.SellOrderOrigCardList;
import com.huateng.framework.ibatis.model.SellOrderOrigCardListExample;
import com.huateng.framework.ibatis.model.SystemLog;
import com.huateng.framework.util.DateUtil;
import com.huateng.hstserver.gatewayService.Java2ACCBusinessServiceImpl;
import com.huateng.hstserver.model.AccPackageDTO;
import com.huateng.univer.batchfile.action.BatchFileActionInterface;
import com.huateng.univer.order.business.bo.OrderBO;
import com.huateng.univer.order.business.bo.OrderBaseQueryBO;
import com.huateng.univer.order.business.bo.OrderCountTotalPrice;
import com.huateng.univer.order.business.service.RansomOrderService;
import com.huateng.univer.seller.cardholder.biz.service.CardholderService;
import com.huateng.univer.system.syslog.integration.dao.SystemLogServiceDAO;

/**
 * @author wpf
 * 
 */
public class RansomOrderServiceImpl implements RansomOrderService {
	private Logger logger = Logger.getLogger(RansomOrderServiceImpl.class);
	private BaseDAO baseDAO;
	private PageQueryDAO pageQueryDAO;
	private OrderBaseQueryBO orderBaseQueryBO;
	private OrderBO orderBO;
	private BatchFileActionInterface fileBatchService;
	private SellOrderDAO sellOrderDAO;
	private CommonsDAO commonsDAO;
	private SellOrderOrigCardListDAO sellOrderOrigCardListDAO;
	private OrderCountTotalPrice orderCountTotalPrice;
	private ProductDAO productDAO;
	private CardholderService cardholderService;	
	private Java2ACCBusinessServiceImpl java2ACCBusinessService;
	private SystemLogServiceDAO systemLogDAO;
	/**
	 * 持卡人信息DAO
	 */
	private CardholderDAO cardholderDAO;
	/**
	 * 机构库存表DAO
	 */
	private EntityStockDAO entityStockDAO;

	@Override
	public SellOrderOrigCardListDTO inqueryPageList(
			SellOrderOrigCardListDTO sellOrderOrigCardListDTO)
			throws BizServiceException {
		PageDataDTO pageData = pageQueryDAO.query(
				"ORDER.selectRansomOrderList", sellOrderOrigCardListDTO);
		sellOrderOrigCardListDTO.setOrderList(pageData);
		return sellOrderOrigCardListDTO;
	}

	
	@Override
	public PageDataDTO inqueryOrderCardList(
			SellOrderOrigCardListDTO sellOrderOrigCardListDTO)
			throws BizServiceException {
		return pageQueryDAO.query("ORDER.selectRansomOrderCardList",
				sellOrderOrigCardListDTO);
	}

	@Override
	public SellOrderOrigCardListDTO insertCheckCard(
			SellOrderOrigCardListDTO sellOrderOrigCardListDTO)
			throws BizServiceException {
		SellOrderOrigCardListQueryDTO sellOrderOrigCardListQueryDTO = new SellOrderOrigCardListQueryDTO();
		sellOrderOrigCardListQueryDTO.setOrderId(sellOrderOrigCardListDTO
				.getOrderId());
		sellOrderOrigCardListQueryDTO.setCardNo(sellOrderOrigCardListDTO
				.getCardNo());
		// 查询卡是否已售出
		List<String> cardNoList = orderBaseQueryBO
				.getOrigCardList(sellOrderOrigCardListQueryDTO);
		// 需要在C端验卡.同时没有插入明细
		if (null == cardNoList || cardNoList.size() <= 0) {
			throw new BizServiceException("该卡未销售或者不是此机构的卡片,不能被添加!");
		}

		AccPackageDTO accPackageDTO = new AccPackageDTO();
		accPackageDTO.setCardNo(sellOrderOrigCardListDTO.getCardNo());
		accPackageDTO.setCvv(sellOrderOrigCardListDTO.getCvv());
		try {
			accPackageDTO = java2ACCBusinessService
					.setCardInfoInquery(accPackageDTO);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new BizServiceException("卡片验证失败");
		}

		Map<String, String> map = new HashMap<String, String>();
		if ("00".equals(accPackageDTO.getRespCode())) {
			String result = accPackageDTO.getOtherData();
			if (result.length() < 2) {
				throw new BizServiceException("卡片验证失败");
			}
			// 编辑明细信息
			SellOrderOrigCardList origCard = new SellOrderOrigCardList();
			List<CardholderQueryDTO> cardholders = cardholderService
			.getCardHolderByCardNo(accPackageDTO.getCardNo());
			if(null != cardholders && cardholders.size()>0){
				origCard.setCardholderId(cardholders.get(0).getCardholderId());
				logger.info("CardHolderName:" + cardholders.get(0).getFirstName());
				origCard.setFirstName(cardholders.get(0).getFirstName());
			}
			
			result = result.substring(0, result.length() - 1);
			String[] records = result.split("\\^");
			
			// 报文内容请参考 <<卡片管理.xls>>
			origCard.setCardNo(records[0]);
			origCard.setProductId(records[2]);
			origCard.setProductName(records[3]);
			origCard.setValidityPeriod(records[6].trim());
			origCard.setAmount(records[7].trim());
			origCard.setOrigcardListId(commonsDAO
					.getNextValueOfSequence("TB_SELL_ORDER_ORIGCARD_LIST"));
			origCard.setCreateUser(sellOrderOrigCardListQueryDTO
					.getLoginUserId());
			origCard.setCreateTime(DateUtil.getCurrentTime24());
			origCard.setModifyUser(sellOrderOrigCardListQueryDTO
					.getLoginUserId());
			origCard.setModifyTime(DateUtil.getCurrentTime24());

			origCard.setOrderId(sellOrderOrigCardListQueryDTO.getOrderId());
			origCard.setDataState(DataBaseConstant.DATA_STATE_NORMAL);

			// 拼装页面信息
		
			map.put("origCardListId", origCard.getOrigcardListId());
			map.put("cardNo", sellOrderOrigCardListDTO.getCardNo());
			map.put("productName", origCard.getProductName());
			map.put("amount", origCard.getAmount());
			if(origCard.getFirstName() == null || "null".equals(origCard.getFirstName()) || "".equals(origCard.getFirstName())){
				map.put("firstName", "");
			}else{
				map.put("firstName", origCard.getFirstName());
			}
			map.put("validityPeriod", origCard.getValidityPeriod());
			map.put("cardState", sellOrderOrigCardListDTO.getCardNo());
			// 订单卡状态: 1,未激活选择销毁, 2,未激活入库 ,3,已激活销毁
			if ("1".equals(records[1])) {
				// 卡已激活必需销毁
				origCard.setCardSate(OrderConst.RANSON_ORIG_CARD_STAT_DESTORY);
				map.put("cardState", "验收通过-只允许注销");
			} else {
				//记名个性化卡 必须销毁
				Product product = productDAO.selectByPrimaryKey(records[2]);
				if("1".equals(product.getOnymousStat())){
					origCard.setCardSate(OrderConst.RANSON_ORIG_CARD_STAT_DESTORY);
					map.put("cardState", "验收通过-只允许注销");
				}else{
					// 卡未激活可以入库
					origCard.setCardSate(OrderConst.RANSON_ORIG_CARD_STAT_ENTSTOCK);
					map.put("cardState", "验收通过-允许入库");
				}
			}
			sellOrderOrigCardListDAO.insert(origCard);

			sellOrderOrigCardListDTO.setCheckedOneCard(map);

		} else {
			throw new BizServiceException("卡片验证失败错误码:"
					+ accPackageDTO.getRespCode());
		}

		/*
		 * 计算金额
		 */
		try {
			Map<String,String> mapStr = orderCountTotalPrice.countPriceForRansomOrder(sellOrderOrigCardListDTO
					.getOrderId());
			map.putAll(mapStr);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new BizServiceException("添加卡片信息失败");
		}

		return sellOrderOrigCardListDTO;
	}

	public void modifyOrigCardListCardState(
			SellOrderOrigCardListDTO sellOrderOrigCardListDTO)
			throws BizServiceException {
		// 页面是否修改列表
		String[] origcardListIds = sellOrderOrigCardListDTO
				.getOrigcardListIds();
		String cardState = null;
		// 返回卡片状态
		if (OrderConst.RANSON_ORIG_CARD_STAT_ENTSTOCK
				.equals(sellOrderOrigCardListDTO.getDataState())) {
			cardState = OrderConst.RANSON_ORIG_CARD_STAT_ENTSTOCK;
		} else {
			cardState = OrderConst.RANSON_ORIG_CARD_STAT_CHECK;
		}

		if (origcardListIds != null && origcardListIds.length > 0) {
			List<SellOrderOrigCardList> origcardList = new ArrayList<SellOrderOrigCardList>();

			for (String origcardListId : origcardListIds) {
				SellOrderOrigCardList origCard = new SellOrderOrigCardList();
				origCard.setCardSate(cardState);
				origCard.setOrigcardListId(origcardListId);
				origcardList.add(origCard);
			}
			try {
				baseDAO.batchUpdate("TB_SELL_ORDER_ORIGCARD_LIST",
						"abatorgenerated_updateByExampleSelective",
						origcardList);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				throw new BizServiceException("修改卡片状态失败");
			}
		} else {
			SellOrderOrigCardList origCard = new SellOrderOrigCardList();
			origCard.setCardSate(cardState);
			SellOrderOrigCardListExample ex = new SellOrderOrigCardListExample();
			ex.createCriteria().andOrigcardListIdEqualTo(
					sellOrderOrigCardListDTO.getOrigcardListId());

			sellOrderOrigCardListDAO.updateByExampleSelective(origCard, ex);

		}
	}

	@Override
	public void submitOrder(SellOrderDTO sellOrderDTO)
			throws BizServiceException {
		try {
			List<SellOrderOrigCardListDTO> list = ready(sellOrderDTO);
			// String batchNo = rechargeBatchFileService.fileBatch(
			// sellOrderDTO.getOrderId(), list);
			String batchNo = fileBatchService.fileBatch("businessRansom",
					sellOrderDTO.getOrderId(), list);
			SellOrderDTO sellOrder = new SellOrderDTO();
			sellOrder.setOrderId(sellOrderDTO.getOrderId());
			if (null != batchNo && !"".equals(batchNo)) {
				sellOrder.setBatchNo(batchNo);
				// 更新订单状态
				updateOrderState(sellOrder);
			} else {
				sellOrder.setOrderState(OrderConst.ORDER_STATE_ORDER_ACCEPT);
				// 回滚订单状态
				updateOrderState(sellOrder);
			}
		} catch (BizServiceException ex) {
			throw ex;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("提交订单失败 ");
		}
	}

	// 获取订单信息及更新订单状态为处理中
	@Override
	public void submitOrderForUpdate(SellOrderDTO sellOrderDTO)
			throws BizServiceException {
		try {
			// 锁定订单
			SellOrderDTO dto = orderBO.selectForUpdate(sellOrderDTO
					.getOrderId());
			// FIXME 订单状态不确定是哪个
			if (!OrderConst.ORDER_STATE_ORDER_ACCEPT.equals(dto.getOrderState()) && !"34".equals(dto.getOrderState())) {
				throw new BizServiceException("订单已提交");
			}
			orderBO.orderFlowNexNode(sellOrderDTO.getOrderId(),
					OrderConst.ORDER_STATE_ORDER_PROCESSING, sellOrderDTO
							.getLoginUserId(), sellOrderDTO
							.getDefaultEntityId(),
					OrderConst.ORDER_FLOW_OPRATION_CONFIRMATION, sellOrderDTO
							.getOperationMemo(),
					OrderConst.ORDER_FLOW_RANSOM_STOCK);
		} catch (BizServiceException ex) {
			throw ex;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("提交订单失败 ");
		}
	}

	private List<SellOrderOrigCardListDTO> ready(SellOrderDTO sellOrderDTO)
			throws BizServiceException {
		// FIXME 准备卡片语句未写
		try {
			List<SellOrderOrigCardListDTO> list = orderBaseQueryBO
					.getOrigCardListByOrderId(sellOrderDTO.getOrderId());
			return list;
		} catch (BizServiceException b) {
			throw b;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("赎回失败！！");
		}
	}

	/**
	 * 更新订单批次号
	 * 
	 * @param sellOrderDTO
	 */
	public void updateOrderState(SellOrderDTO sellOrderDTO)
			throws BizServiceException {
		try {
			SellOrder sellOrder = new SellOrder();
			sellOrder.setOrderId(sellOrderDTO.getOrderId());
			sellOrder.setBatchNo(sellOrderDTO.getBatchNo());
			sellOrder.setInitActStat(sellOrderDTO.getInitActStat());
			if (null != sellOrderDTO.getOrderState()
					&& !"".equals(sellOrderDTO.getOrderState())) {
				sellOrder.setOrderState(sellOrderDTO.getOrderState());
			}
			sellOrderDAO.updateByPrimaryKeySelective(sellOrder);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("更新订单状态失败");
		}
	}

	/**
	 * 根据卡号查询卡的信息
	 */
    @SuppressWarnings("unchecked")
    @Override
    public PageDataDTO checkQueryPageList(SellOrderOrigCardListDTO sellOrderOrigCardListDTO)
            throws BizServiceException {
        String errorMessage = RansomOrderConst.RANSOM_OREDER_CARD_NO_SELL_FAIL_ERROR;
        try {
           //判断是否是本机构
            String entryId = entityStockDAO.selectByPrimaryKey(sellOrderOrigCardListDTO.getCardNo()).getEntityId();
            if(!entryId.equals(sellOrderOrigCardListDTO.getEntityId())) {
                errorMessage = RansomOrderConst.RANSOM_OREDER_ENTITY_ERROR;
                throw new BizServiceException(RansomOrderConst.RANSOM_OREDER_ENTITY_ERROR);
            }
            String orderId = sellOrderOrigCardListDTO.getOrderId();
            SellOrderOrigCardListQueryDTO sellOrderOrigCardListQueryDTO = new SellOrderOrigCardListQueryDTO();
            sellOrderOrigCardListQueryDTO.setOrderId(orderId);
            sellOrderOrigCardListQueryDTO.setCardNo(sellOrderOrigCardListDTO
                    .getCardNo());
            // 查询卡是否已售出
            List<String> cardNoList = orderBaseQueryBO
                    .getOrigCardList(sellOrderOrigCardListQueryDTO);
            if (null == cardNoList || cardNoList.size() <= 0) {
                errorMessage = RansomOrderConst.RANSOM_OREDER_CARD_NO_SELL_ERROR;
                throw new BizServiceException(RansomOrderConst.RANSOM_OREDER_CARD_NO_SELL_ERROR);
            }
            
            PageDataDTO pageData = pageQueryDAO.query(
                    "RANSOM_CHECK_CARD_QUQERY.ransomCheckCard", sellOrderOrigCardListDTO);
            if (null != pageData) {
                List<Map<String, Object>> list = (List<Map<String, Object>>) pageData.getData();
                int size = list.size();
                List<Map<String, Object>> newList = new ArrayList<Map<String, Object>>();
                for(int i = 0; i < size; i++) {
                    Map<String, Object> newMap = new HashMap<String, Object>();
                    Map<String, Object> map = list.get(i);
                    Iterator it = map.entrySet().iterator(); 
                    while (it.hasNext()) { 
                        Entry entry = (Entry) it.next(); 
                        String key = (String) entry.getKey() ; 
                        Object value = entry.getValue();
                        //卡状态   CARD_STAT 1：激活 0：未激活3：销户
                        if(RansomOrderConst.ACC_CARD_INFO_STATE_KEY.equals(key)) {
                            if(RansomOrderConst.ACC_CARD_INFO_STATE_VALUE.equals(value)) {
                                errorMessage = RansomOrderConst.ACC_CARD_INFO_STATE_ERROR;
                                throw new BizServiceException(RansomOrderConst.ACC_CARD_INFO_STATE_ERROR);
                            }
                        }
                        //卡锁定
                        if(RansomOrderConst.ACC_CARD_INFO_LOCK_KEY.equals(key)) {
                            if(RansomOrderConst.ACC_CARD_INFO_LOCK_VALUE.equals(value)) {
                                errorMessage = RansomOrderConst.ACC_CARD_INFO_LOCK_ERROR;
                                throw new BizServiceException(RansomOrderConst.ACC_CARD_INFO_LOCK_ERROR);
                            }
                        }
                        //卡注销
                        if(RansomOrderConst.ACC_CARD_INFO_CANCEL_KEY.equals(key)) {
                            if(RansomOrderConst.ACC_CARD_INFO_CANCEL_VALUE.equals(value)) {
                                errorMessage = RansomOrderConst.ACC_CARD_INFO_CANCEL_ERROR;
                                throw new BizServiceException(RansomOrderConst.ACC_CARD_INFO_CANCEL_ERROR);
                            }
                        }
                        //卡挂失
                        if(RansomOrderConst.ACC_CARD_INFO_LOST_KEY.equals(key)) {
                            if(RansomOrderConst.ACC_CARD_INFO_LOST_VALUE.equals(value)) {
                                errorMessage = RansomOrderConst.ACC_CARD_INFO_LOST_ERROR;
                                throw new BizServiceException(RansomOrderConst.ACC_CARD_INFO_LOST_ERROR);
                            }
                        }
                        if(RansomOrderConst.ACC_CARD_INFO_PRODUCT_ID.equals(key)) {
                            String productName = productDAO.selectByPrimaryKey(String.valueOf(value)).getProductName();
                            newMap.put(RansomOrderConst.ACC_CARD_INFO_PRODUCT_NAME, productName);
                        }
                        if(RansomOrderConst.ACC_CARD_INFO_CARDHOLDER_ID.equals(key)) {
                            if(null == value || "".equals(value)) {
                                newMap.put(RansomOrderConst.ACC_CARD_INFO_CARDHOLDER_NAME, "");
                            }
                            else {
                                String hId = String.valueOf(value);
                                String firstName = cardholderDAO.selectByPrimaryKey(hId).getFirstName();
                                newMap.put(RansomOrderConst.ACC_CARD_INFO_CARDHOLDER_NAME, firstName); 
                            } 
                        }
                        
                        newMap.put(key, value);
                    } 
                    newList.add(newMap);
                } 
                pageData.setData(newList);
            }
            
            this.logRansom(ConstCode.RANSOM_CHECK_CARD_QUQERY, sellOrderOrigCardListDTO.getOperator(), 
                    "查询的卡号：" + sellOrderOrigCardListDTO.getCardNo(),"查询的卡号："+sellOrderOrigCardListDTO.getCardNo());
            return pageData;
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException(errorMessage);
        }
    }
    
    /**
     * 
     * 插入原卡信息表
     *
     * @param sellOrderOrigCardListDTO
     * @return
     * @throws BizServiceException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @Override
    public SellOrderOrigCardListDTO insertCheckCardNew (
            SellOrderOrigCardListDTO sellOrderOrigCardListDTO)
            throws BizServiceException {
        String orderId = sellOrderOrigCardListDTO.getOrderId();
        SellOrderOrigCardListQueryDTO sellOrderOrigCardListQueryDTO = new SellOrderOrigCardListQueryDTO();
        sellOrderOrigCardListQueryDTO.setOrderId(orderId);
        sellOrderOrigCardListQueryDTO.setCardNo(sellOrderOrigCardListDTO
                .getCardNo());
        // 查询卡是否已售出
        List<String> cardNoList = orderBaseQueryBO
                .getOrigCardList(sellOrderOrigCardListQueryDTO);
        // 需要在C端验卡.同时没有插入明细
        if (null == cardNoList || cardNoList.size() <= 0) {
            throw new BizServiceException("该卡未销售或者不是此机构的卡片,不能被添加!");
        }

        Map<String, String> map = new HashMap<String, String>();
      
        // 编辑明细信息
        SellOrderOrigCardList origCard = new SellOrderOrigCardList();
        List<CardholderQueryDTO> cardholders = cardholderService
        .getCardHolderByCardNo(sellOrderOrigCardListDTO
                .getCardNo());
        if(null != cardholders && cardholders.size()>0){
            origCard.setCardholderId(cardholders.get(0).getCardholderId());
            logger.info("CardHolderName:" + cardholders.get(0).getFirstName());
            origCard.setFirstName(cardholders.get(0).getFirstName());
        }

        origCard.setCardNo(sellOrderOrigCardListDTO.getCardNo());
        origCard.setProductId(sellOrderOrigCardListDTO.getProductId());
        
        
        Product productNew = productDAO.selectByPrimaryKey(
                sellOrderOrigCardListDTO.getProductId());
        //产品名称
        String productName = productNew.getProductName();
        logger.info("产品名称：" + productName);
        origCard.setProductName(productName);
        origCard.setValidityPeriod(sellOrderOrigCardListDTO.getValidityPeriod());
        String amount = sellOrderOrigCardListDTO.getAmount().trim();
        origCard.setAmount(amount);
        origCard.setOrigcardListId(commonsDAO
                .getNextValueOfSequence("TB_SELL_ORDER_ORIGCARD_LIST"));
        
        SellOrder sellOrder = sellOrderDAO.selectByPrimaryKey(orderId);
        String createUser = sellOrder.getCreateUser();
        origCard.setCreateUser(createUser);
        origCard.setCreateTime(DateUtil.getCurrentTime24());
        origCard.setModifyUser(createUser);
        origCard.setModifyTime(DateUtil.getCurrentTime24());

        origCard.setOrderId(orderId);
        origCard.setDataState(DataBaseConstant.DATA_STATE_NORMAL);

        // 拼装页面信息
    
        map.put("origCardListId", origCard.getOrigcardListId());
        map.put("cardNo", sellOrderOrigCardListDTO.getCardNo());
        map.put("productName", productName);
        map.put("amount", amount);
        if(origCard.getFirstName() == null || "".equals(origCard.getFirstName()) || "null".equals(origCard.getFirstName())){
            map.put("firstName", "");
        }else{
            map.put("firstName", origCard.getFirstName());
        }
        map.put("validityPeriod", DateUtil.formatStringDate(origCard.getValidityPeriod()));
        //map.put("cardState", sellOrderOrigCardListDTO.getCardSate());
        // 订单卡状态: 1,未激活选择销毁, 2,未激活入库 ,3,已激活销毁
        String cState = sellOrderOrigCardListDTO.getCardSate();
        if ("1".equals(cState.trim())) {
            // 卡已激活必需销毁
            origCard.setCardSate(OrderConst.RANSON_ORIG_CARD_STAT_DESTORY);
            map.put("cardState", "验收通过-只允许注销");
        } else {
           //记名个性化卡 必须销毁
            Product product = productDAO.selectByPrimaryKey(sellOrderOrigCardListDTO.getProductId());
            if("1".equals(product.getOnymousStat())){
                origCard.setCardSate(OrderConst.RANSON_ORIG_CARD_STAT_DESTORY);
                map.put("cardState", "验收通过-只允许注销");
            }else{
                // 卡未激活可以入库
                origCard.setCardSate(OrderConst.RANSON_ORIG_CARD_STAT_ENTSTOCK);
                map.put("cardState", "验收通过-允许入库");
            }
        }
       sellOrderOrigCardListDAO.insert(origCard);
       sellOrderOrigCardListDTO.setCheckedOneCard(map);

    
        /*
         * 计算金额
         */
        try {
            Map<String,String> mapStr = orderCountTotalPrice.countPriceForRansomOrder(orderId);
            map.putAll(mapStr);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BizServiceException("添加卡片信息失败");
        }

        return sellOrderOrigCardListDTO;
    }

 
    /**
     * 记录日志
     * @param txnCode
     * @param userId
     * @param txnContent
     * @param operationMemo
     * @throws Exception
     */
    public void logRansom(String txnCode, String userId, String txnContent, String operationMemo)
            throws Exception {
        SystemLog systemLog = new SystemLog();
        String logId = commonsDAO.getNextValueOfSequenceBySequence("SEQ_SYSTEM_LOG");
        systemLog.setLogId(logId);
        systemLog.setTxnCode(txnCode);
        systemLog.setTxnContent(txnContent);
        systemLog.setOperationUser(userId);
        systemLog.setSuccessFlag("1");
        systemLog.setOperationTime(DateUtil.getCurrentTime());
        systemLog.setOperationMemo(operationMemo);
        systemLogDAO.insert(systemLog);
    }
    
	public BaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	public PageQueryDAO getPageQueryDAO() {
		return pageQueryDAO;
	}

	public void setPageQueryDAO(PageQueryDAO pageQueryDAO) {
		this.pageQueryDAO = pageQueryDAO;
	}

	public OrderBaseQueryBO getOrderBaseQueryBO() {
		return orderBaseQueryBO;
	}

	public void setOrderBaseQueryBO(OrderBaseQueryBO orderBaseQueryBO) {
		this.orderBaseQueryBO = orderBaseQueryBO;
	}

	public OrderBO getOrderBO() {
		return orderBO;
	}

	public void setOrderBO(OrderBO orderBO) {
		this.orderBO = orderBO;
	}

	public BatchFileActionInterface getFileBatchService() {
		return fileBatchService;
	}

	public void setFileBatchService(BatchFileActionInterface fileBatchService) {
		this.fileBatchService = fileBatchService;
	}

	public SellOrderDAO getSellOrderDAO() {
		return sellOrderDAO;
	}

	public void setSellOrderDAO(SellOrderDAO sellOrderDAO) {
		this.sellOrderDAO = sellOrderDAO;
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

	public SellOrderOrigCardListDAO getSellOrderOrigCardListDAO() {
		return sellOrderOrigCardListDAO;
	}

	public void setSellOrderOrigCardListDAO(
			SellOrderOrigCardListDAO sellOrderOrigCardListDAO) {
		this.sellOrderOrigCardListDAO = sellOrderOrigCardListDAO;
	}

	public OrderCountTotalPrice getOrderCountTotalPrice() {
		return orderCountTotalPrice;
	}

	public void setOrderCountTotalPrice(OrderCountTotalPrice orderCountTotalPrice) {
		this.orderCountTotalPrice = orderCountTotalPrice;
	}

	public ProductDAO getProductDAO() {
		return productDAO;
	}

	public void setProductDAO(ProductDAO productDAO) {
		this.productDAO = productDAO;
	}
	public CardholderService getCardholderService() {
		return cardholderService;
	}

	public void setCardholderService(CardholderService cardholderService) {
		this.cardholderService = cardholderService;
	}


    public SystemLogServiceDAO getSystemLogDAO() {
        return systemLogDAO;
    }


    public void setSystemLogDAO(SystemLogServiceDAO systemLogDAO) {
        this.systemLogDAO = systemLogDAO;
    }


    public CardholderDAO getCardholderDAO() {
        return cardholderDAO;
    }


    public void setCardholderDAO(CardholderDAO cardholderDAO) {
        this.cardholderDAO = cardholderDAO;
    }


    public EntityStockDAO getEntityStockDAO() {
        return entityStockDAO;
    }


    public void setEntityStockDAO(EntityStockDAO entityStockDAO) {
        this.entityStockDAO = entityStockDAO;
    }

	

}

package com.huateng.univer.issuer.order.biz.bo;

import java.util.List;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.entity.stock.dto.EntityStockDTO;
import com.allinfinance.univer.issuer.dto.product.ProductCardBinDTO;
import com.allinfinance.univer.issuer.dto.product.ProductDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderCardListQueryDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderFlowDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderFlowQueryDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderListQueryDTO;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.dao.BaseDAO;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.dao.PageQueryDAO;
import com.huateng.framework.ibatis.dao.EntityStockDAO;
import com.huateng.framework.ibatis.dao.OrderBatchFileDAO;
import com.huateng.framework.ibatis.dao.SellOrderCardListDAO;
import com.huateng.framework.ibatis.dao.SellOrderDAO;
import com.huateng.framework.ibatis.dao.SellOrderFlowDAO;
import com.huateng.framework.ibatis.dao.SellOrderListDAO;
import com.huateng.framework.ibatis.dao.UnionOrderDAO;
import com.huateng.framework.ibatis.model.EntityStock;
import com.huateng.framework.ibatis.model.EntityStockExample;
import com.huateng.framework.ibatis.model.OrderBatchFile;
import com.huateng.framework.ibatis.model.OrderBatchFileExample;
import com.huateng.framework.ibatis.model.OrderBatchFileKey;
import com.huateng.framework.ibatis.model.SellOrder;
import com.huateng.framework.ibatis.model.SellOrderCardList;
import com.huateng.framework.ibatis.model.SellOrderFlow;
import com.huateng.framework.ibatis.model.SellOrderList;
import com.huateng.framework.ibatis.model.SellOrderListExample;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.ReflectionUtil;

/**
 * 库存订单通用service
 * 
 * @author xxl
 * 
 */
public class StockOrderCommonService {

	private BaseDAO baseDAO;
	private CommonsDAO commonsDAO;
	private SellOrderDAO sellOrderDAO;
	private SellOrderListDAO sellOrderListDAO;
	private SellOrderFlowDAO sellOrderFlowDAO;
	private EntityStockDAO entityStockDAO;
	private PageQueryDAO pageQueryDAO;
	private UnionOrderDAO unionOrderDAO;
	private SellOrderCardListDAO sellOrderCardListDAO;
	private OrderBatchFileDAO orderBatchFileDAO;

	public SellOrder getSellOrderById(String orderId) throws Exception {
		return sellOrderDAO.selectByPrimaryKey(orderId);
	}

	public SellOrder getSellOrderForUpdate(String orderId) throws Exception {
		return (SellOrder) baseDAO.queryForObject(
				"STOCK_ORDER.getOrderForUpdate", orderId);
	}

	public SellOrderDTO getSellOrderDTOByDto(SellOrderDTO sellOrderDTO)
			throws Exception {
		return (SellOrderDTO) baseDAO.queryForObject(
				"STOCK_ORDER.selectStockOrderForView", sellOrderDTO);
	}

	public SellOrderList getSellOrderListById(String orderListId)
			throws Exception {
		return sellOrderListDAO.selectByPrimaryKey(orderListId);
	}

	public SellOrderCardList getSellOrderCardListById(String orderCardListId)
			throws Exception {
		return sellOrderCardListDAO.selectByPrimaryKey(orderCardListId);
	}

	/**
	 * 查找匿名采购订单中要准备的订单明细
	 */
	public PageDataDTO getOrderList(SellOrderListQueryDTO orderListQueryDTO)
			throws Exception {
		return pageQueryDAO.query("STOCK_ORDER_LIST.selectOrderList",
				orderListQueryDTO);
	}

	/**
	 * 查找匿名采购订单中要准备的订单明细
	 */
	@SuppressWarnings("unchecked")
	public List<SellOrderList> getReadyOrderList(EntityStockDTO entityStockDTO)
			throws Exception {
		return baseDAO.queryForList("STOCK_ORDER_LIST.selectOrderListForReady",
				entityStockDTO);
	}

	public List<SellOrderList> getOrderListByExample(
			SellOrderListExample example) {
		return sellOrderListDAO.selectByExample(example);
	}

	public List<EntityStock> getStockByExample(EntityStockExample example) {
		return entityStockDAO.selectByExample(example);
	}

	@SuppressWarnings("unchecked")
	public List<EntityStock> getStockForUpdate(EntityStockDTO entityStockDTO) {
		return baseDAO.queryForList("STOCK_ORDER.selectCardStockForUpdate",
				entityStockDTO);
	}

	@SuppressWarnings("unchecked")
	public List<ProductCardBinDTO> getProductCardBinDTOs(ProductDTO productDTO) {
		return baseDAO.queryForList("PRODUCT.selectCardBinDTOForProduct",
				productDTO);
	}

	@SuppressWarnings("unchecked")
	public List<String> getStockCardNoList(
			SellOrderCardListQueryDTO cardListQueryDTO) throws Exception {
		return baseDAO
				.queryForList("STOCK_ORDER_CARD_LIST.selectOrderCardNoList",
						cardListQueryDTO);

	}

	/**
	 * 查看订单流程信息
	 * 
	 * @return
	 */
	public SellOrderFlowDTO viewOrderFlow(String orderFlowId) throws Exception {
		SellOrderFlow sellOrderFlow = sellOrderFlowDAO
				.selectByPrimaryKey(orderFlowId);
		SellOrderFlowDTO sellOrderFlowDTO = new SellOrderFlowDTO();
		ReflectionUtil.copyProperties(sellOrderFlow, sellOrderFlowDTO);
		return sellOrderFlowDTO;
	}

	/**
	 * 添加订单流程信息
	 */
	public void addOrderFlow(SellOrderFlow orderFlow) throws Exception {
		orderFlow.setOrderFlowId(commonsDAO
				.getNextValueOfSequence("TB_SELL_ORDER_FLOW"));
		orderFlow.setCreateTime(DateUtil.getCurrentTime());
		orderFlow.setModifyTime(DateUtil.getCurrentTime());
		orderFlow.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
		sellOrderFlowDAO.insert(orderFlow);
	}

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
		orderFlow.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
		sellOrderFlowDAO.insert(orderFlow);
	}

	/**
	 * 查看订单流程信息
	 */
	public PageDataDTO getSellOrderFlowList(String orderId) throws Exception {
		SellOrderFlowQueryDTO sellOrderFlowQueryDTO = new SellOrderFlowQueryDTO();
		sellOrderFlowQueryDTO.setOrderId(orderId);
		return pageQueryDAO.query("STOCK_ORDER_FLOW.selectOrderFlow",
				sellOrderFlowQueryDTO);

	}

	public void insertOrUpdateOrderBatchFile(String batchNumber,
			String orderId, String loginUserId) {
		OrderBatchFileKey key = new OrderBatchFileKey();
		key.setBatchNumber(batchNumber);
		key.setOrderId(orderId);

		OrderBatchFile orderBatchFile;
		OrderBatchFileExample example = new OrderBatchFileExample();
		example.createCriteria().andDataStateEqualTo(
				DataBaseConstant.DATA_STATE_NORMAL).andOrderIdEqualTo(orderId);

		List<OrderBatchFile> orderBatchFiles = orderBatchFileDAO
				.selectByExample(example);

		if (null == orderBatchFiles || orderBatchFiles.size() < 1) {
			orderBatchFile = new OrderBatchFile();
			orderBatchFile.setBatchNumber(batchNumber);
			orderBatchFile.setOrderId(orderId);
			orderBatchFile.setCreateUser(loginUserId);
			orderBatchFile.setCreateTime(DateUtil.getCurrentTime());
			orderBatchFile.setModifyUser(loginUserId);
			orderBatchFile.setModifyTime(DateUtil.getCurrentTime());
			orderBatchFile.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
			orderBatchFileDAO.insert(orderBatchFile);
		} else {
			orderBatchFile = orderBatchFiles.get(0);
			orderBatchFile.setBatchNumber(batchNumber);
			orderBatchFile.setModifyUser(loginUserId);
			orderBatchFile.setModifyTime(DateUtil.getCurrentTime());
			orderBatchFileDAO.updateByExampleSelective(orderBatchFile, example);
		}

	}

	/**
	 * 获取订单文件批次号用于下载制卡反馈文件
	 * 
	 * @param orderId
	 * @return
	 */
	public String getOrderFileBatchNumbert(String orderId) {
		return (String) baseDAO.queryForObject(
				"STOCK_ORDER.selectBatchNumberByOrderId", orderId);
	}

	public BaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	public SellOrderListDAO getSellOrderListDAO() {
		return sellOrderListDAO;
	}

	public void setSellOrderListDAO(SellOrderListDAO sellOrderListDAO) {
		this.sellOrderListDAO = sellOrderListDAO;
	}

	public EntityStockDAO getEntityStockDAO() {
		return entityStockDAO;
	}

	public void setEntityStockDAO(EntityStockDAO entityStockDAO) {
		this.entityStockDAO = entityStockDAO;
	}

	public CommonsDAO getCommonsDAO() {
		return commonsDAO;
	}

	public void setCommonsDAO(CommonsDAO commonsDAO) {
		this.commonsDAO = commonsDAO;
	}

	public SellOrderFlowDAO getSellOrderFlowDAO() {
		return sellOrderFlowDAO;
	}

	public void setSellOrderFlowDAO(SellOrderFlowDAO sellOrderFlowDAO) {
		this.sellOrderFlowDAO = sellOrderFlowDAO;
	}

	public PageQueryDAO getPageQueryDAO() {
		return pageQueryDAO;
	}

	public void setPageQueryDAO(PageQueryDAO pageQueryDAO) {
		this.pageQueryDAO = pageQueryDAO;
	}

	public UnionOrderDAO getUnionOrderDAO() {
		return unionOrderDAO;
	}

	public void setUnionOrderDAO(UnionOrderDAO unionOrderDAO) {
		this.unionOrderDAO = unionOrderDAO;
	}

	public SellOrderCardListDAO getSellOrderCardListDAO() {
		return sellOrderCardListDAO;
	}

	public void setSellOrderCardListDAO(
			SellOrderCardListDAO sellOrderCardListDAO) {
		this.sellOrderCardListDAO = sellOrderCardListDAO;
	}

	public SellOrderDAO getSellOrderDAO() {
		return sellOrderDAO;
	}

	public void setSellOrderDAO(SellOrderDAO sellOrderDAO) {
		this.sellOrderDAO = sellOrderDAO;
	}

	public OrderBatchFileDAO getOrderBatchFileDAO() {
		return orderBatchFileDAO;
	}

	public void setOrderBatchFileDAO(OrderBatchFileDAO orderBatchFileDAO) {
		this.orderBatchFileDAO = orderBatchFileDAO;
	}

}

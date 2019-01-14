package com.allinfinance.prepay.order.business.bo;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allinfinance.prepay.dao.CommonsDAO;
import com.allinfinance.prepay.exception.BizServiceException;
import com.allinfinance.prepay.mapper.svc_mng.SellOrderFlowMapper;
import com.allinfinance.prepay.mapper.svc_mng.SellOrderMapper;
import com.allinfinance.prepay.model.SellOrder;
import com.allinfinance.prepay.model.SellOrderExample;
import com.allinfinance.prepay.model.SellOrderFlow;
import com.allinfinance.prepay.utils.DateUtil;
import com.allinfinance.univer.seller.order.dto.AcceptOrderDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderCardListDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderDTO;

@Service
public class OrderBO {
    private Logger logger = Logger.getLogger(OrderBO.class);
	/**
	 * ���¶���״ ͬʱ��¼���̽ڵ�
	 */

    @Autowired
	private CommonsDAO commonsDAO;
    @Autowired
	private SellOrderMapper sellOrderMapper;
    @Autowired
	private SellOrderFlowMapper sellOrderFlowMapper;

	

	/**
	 * 
	 * @param orderId
	 *            ����ID
	 * @param nextState
	 *            ������һ״̬
	 * @param modifyUser
	 *            �޸���
	 * @param defaultEntityId
	 *            �޸���ʵ��ID
	 * @param operatorType
	 *            ��������:�ύ���˻أ�ȡ��
	 * @param memo
	 *            ����˵��
	 * @param currentNode
	 *            ������ǰ�ڵ�
	 * @throws Exception
	 */
	public void orderFlowNexNode(String orderId, String nextState,
			String modifyUser, String defaultEntityId, String operatorType,
			String memo, String currentNode) throws Exception {

		SellOrder sellOrder = new SellOrder();
		sellOrder.setOrderId(orderId);
		// ������һ״̬
		sellOrder.setOrderState(nextState);

		sellOrder.setModifyTime(DateUtil.getCurrentTime());
		// �����޸���
		sellOrder.setModifyUser(modifyUser);

		SellOrderFlow sellOrderFlow = new SellOrderFlow();
		/**
		 * �򶩵����̼�¼IDû��ʵ������ ��ȡsequenceName
		 */
		sellOrderFlow.setOrderFlowId(commonsDAO
				.getNextValueOfSequence("TB_SELL_ORDER_FLOW"));

		sellOrderFlow.setOrderId(orderId);
		// ��������ʵ��ID
		sellOrderFlow.setEntityId(defaultEntityId);
		// ������������
		sellOrderFlow.setOperateType(operatorType);

		sellOrderFlow.setOrderFlowNode(currentNode);

		// ����˵��
		sellOrderFlow.setMemo(memo);

		sellOrderFlow.setCreateTime(DateUtil.getCurrentTime());
		sellOrderFlow.setCreateUser(modifyUser);

		sellOrderFlow.setModifyTime(DateUtil.getCurrentTime());

		sellOrderFlow.setModifyUser(modifyUser);

		sellOrderFlow.setDataState("1");
		SellOrderExample ex=new SellOrderExample();
		ex.createCriteria().andOrderIdEqualTo(orderId);
		sellOrderMapper.updateByExampleSelective(sellOrder, ex);

		sellOrderFlowMapper.insert(sellOrderFlow);
	}

/*	public void updateEntityStockByPrimaryKey(List<EntityStock> entityStockList)
			throws Exception {
		commonsDAO.batchUpdate(
				"TB_ENTITY_STOCK.abatorgenerated_updateByPrimaryKeySelective",
				entityStockList);
	}

	public void deleteSellOrderCardListByOrderId(String orderId)
			throws Exception {

		SellOrderCardListExample example = new SellOrderCardListExample();

		example.createCriteria().andOrderIdEqualTo(orderId);

		sellOrderCardListDAO.deleteByExample(example);
	}*/

	/*public void batchInsertSellOrderCardList(
			List<SellOrderCardList> sellOrderCardListList) throws Exception {
		commonsDAO.batchInsert(
				"TB_SELL_ORDER_CARD_LIST.abatorgenerated_insert",
				sellOrderCardListList);
	}

	public void batchUpdateSellOrderCardList(
			List<SellOrderCardList> sellOrderCardListList) throws Exception {
		commonsDAO
				.batchInsert(
						"TB_SELL_ORDER_CARD_LIST.abatorgenerated_updateByPrimaryKeySelective",
						sellOrderCardListList);
	}
	public void batchUpdateAccCardInfo(List<SellOrderCardList> sellOrderCardListList) throws Exception{
		if(sellOrderCardListList!=null&&sellOrderCardListList.size()!=0){
			for(int i=0;i<sellOrderCardListList.size();i++){
				commonsDAO.updateCardInfo(sellOrderCardListList.get(i));
			}
		}else{
			throw new BizServiceException("׼�����Ų���Ϊ��");
		}
	}*/
	/*public void updateSellOrderList(SellOrderList sellOrderList)
			throws Exception {
		sellOrderListDAO.updateByPrimaryKeySelective(sellOrderList);
	}

	public void deleteSellOrderCardListByPrimaryKey(String orderCardListId)
			throws Exception {
		sellOrderCardListDAO.deleteByPrimaryKey(orderCardListId);
	}

	public void updateSellOrderListByExample(SellOrderList sellOrderList,
			SellOrderListExample sellOrderListExample) throws Exception {
		sellOrderListDAO.updateByExampleSelective(sellOrderList,
				sellOrderListExample);
	}

	public SellOrderCardList getSellOrderCardListByPrimaryKey(
			String orderCardListId) throws Exception {
		return sellOrderCardListDAO.selectByPrimaryKey(orderCardListId);
	}

	public void updateByPrimaryKeySelective(SellOrder sellOrder)
			throws Exception {
		sellOrderDAO.updateByPrimaryKeySelective(sellOrder);
	}*/

	/*@SuppressWarnings("unchecked")
    public void updateEntityStockOutByOrderId(String orderId) throws Exception {
		SellOrder order = orderBaseQueryBO.getSellOrder(orderId);
        SellOrderDTO sellOrderDTO = new SellOrderDTO();
        sellOrderDTO.setOrderId(orderId);
        List<String> oldOrderIdOrderList = (List<String>)baseDAO.queryForList("STOCK_ORDER_CARD_LIST.selectOrigOrderByBuyOrderId", orderId);
        if(oldOrderIdOrderList.size() == 0){
            sellOrderDTO.setOrderIds(null);
        }
        else{
            sellOrderDTO.setOrderIds((String[])oldOrderIdOrderList.toArray(new String[oldOrderIdOrderList.size()])); 
        }
		if (OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_SELLER_SIGN.equals(order
				.getOrderType())) {
			baseOrderDAO.update("ORDER.updateCardOutBySignOrderId", sellOrderDTO);
			
		} else if (OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_SELLER_UNSIGN
				.equals(order.getOrderType())
				|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_SIGN
						.equals(order.getOrderType())
				|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_UNSIGN
						.equals(order.getOrderType())
				|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_SIGN
						.equals(order.getOrderType())
				|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_UNSIGN
						.equals(order.getOrderType())) {
			baseOrderDAO.update("ORDER.updateCardOutByOrderId", orderId);
		}
	}

	*//***
	 * �ݹ�(���Ҷ���)���¿�� ��Ҫ�ݹ�Ķ����� �������ɹ�����
	 * 
	 * @param orderId
	 * @throws Exception
	 *//*
	@SuppressWarnings("unchecked")
    public void updateEntityStockOutByRecursionOrderCardList(String orderId)
			throws Exception {
	    SellOrderDTO sellOrderDTO = new SellOrderDTO();
        sellOrderDTO.setOrderId(orderId);
        List<String> oldOrderIdOrderList = (List<String>)baseDAO.queryForList("STOCK_ORDER_CARD_LIST.selectOrigOrderByBuyOrderId", orderId);
        if(oldOrderIdOrderList.size() == 0){
            sellOrderDTO.setOrderIds(null);
        }
        else{
            sellOrderDTO.setOrderIds((String[])oldOrderIdOrderList.toArray(new String[oldOrderIdOrderList.size()])); 
        }
		baseOrderDAO.update("ORDER.updateCardOutBySignOrderId", sellOrderDTO);
	}

	*//**
	 * ͨ��������ϸ���¿��
	 * 
	 * @param orderId
	 * @throws Exception
	 *//*
	public void updateEntityStockOutByBuyOrderCardList(String orderId)
			throws Exception {
		baseOrderDAO.update("ORDER.updateCardOutByOrderId", orderId);

	}*/

	/**
	 * �ɹ����������и���ԭʼ�����Ĳɹ�״̬(orderBuyerState)
	 */
	/*public void updateOldOrderStateForBuyOrder(List<String> oldOrderIdList,
			String orderBuyerState) throws Exception {
		SellOrderExample example = new SellOrderExample();
		example.createCriteria().andDataStateEqualTo(
				DataBaseConstant.DATA_STATE_NORMAL)
				.andOrderIdIn(oldOrderIdList);

	}*/

	/**
	 * �����������
	 * 
	 * @param orderId
	 * @throws Exception
	 */
	/*public void orderAcceptInStock(SellOrderDTO sellOrderDTO) throws Exception {
		String orderId = sellOrderDTO.getOrderId();
		// �ֿ����
		List<String> cardNos = orderBaseQueryBO
      .getSuccessCardNoList(sellOrderDTO.getOrderId());
		entityStockService.modifyStockStateAndEntity(cardNos, sellOrderDTO
				.getDefaultEntityId(), OrderConst.CARD_STOCK_OUT,
				OrderConst.CARD_STOCK_IN);

		// ��������־�����
		entityStockService.addStockOperaterRecord(cardNos, orderId,
				sellOrderDTO.getDefaultEntityId(), Const.FUNCTION_ROLE_SELLER,
				OrderConst.ORDER_STATE_ORDER_ACCEPT,
				OrderConst.ORDER_FLOW_OPRATION_CONFIRMATION,
				OrderConst.CARD_STOCK_OPERATE_TYPE_IN, sellOrderDTO
						.getLoginUserId());

	}*/
	/**
     * ���ݿ��Ŷζ����������
     * 
     * @param orderId
	 * @throws Exception 
     * @throws Exception
     */
   /* public void orderAcceptInStock(AcceptOrderDTO acceptOrderDTO) throws Exception {
        String orderId = acceptOrderDTO.getOrderId();
        //���ڱ����ǿ��Բ��ֽ��գ������߼���Ҫ�޸�
//      List<String> cardNos = orderBaseQueryBO
//              .getSuccessCardNoList(sellOrderDTO.getOrderId());
        //���ݿ��Ŷν������
        // �ֿ����
        List<String> cardNos = orderBaseQueryBO
      .getSuccessCardNoList(acceptOrderDTO.getOrderId(),acceptOrderDTO);
        if(cardNos==null){
            throw new BizServiceException("�������ݴ���");
        }
        //���½�������
//        SellOrderExample example=new SellOrderExample();
//        example.createCriteria().andOrderIdEqualTo(acceptOrderDTO.getOrderId());
//        SellOrder sellOrder=sellOrderDAO.selectByExample(example);
        SellOrder sellOrder=sellOrderDAO.selectByPrimaryKey(acceptOrderDTO.getOrderId());
        if(sellOrder.getOrigcardQuantity()!=null&&!"".equals(sellOrder.getOrigcardQuantity())){
            sellOrder.setOrigcardQuantity(String.valueOf(Integer.valueOf(sellOrder.getOrigcardQuantity())+cardNos.size()));
        }else{
        sellOrder.setOrigcardQuantity(String.valueOf(cardNos.size()));
        }
        try{
        sellOrderDAO.updateByPrimaryKey(sellOrder);
        }catch(Exception e){
            logger.info(e.getMessage());
        }
        entityStockService.modifyStockStateAndEntity(cardNos, acceptOrderDTO
                .getDefaultEntityId(), OrderConst.CARD_STOCK_OUT,
                OrderConst.CARD_STOCK_IN);

        // ��������־�����
        entityStockService.addStockOperaterRecord(cardNos, orderId,
                acceptOrderDTO.getDefaultEntityId(), Const.FUNCTION_ROLE_SELLER,
                OrderConst.ORDER_STATE_ORDER_ACCEPT,
                OrderConst.ORDER_FLOW_OPRATION_CONFIRMATION,
                OrderConst.CARD_STOCK_OPERATE_TYPE_IN, acceptOrderDTO
                        .getLoginUserId());

    }*/
    /**
     * �õ��ѽ��ֵ�����
     * */
  /*  public Integer getAcceptNum(SellOrderDTO sellOrderDTO){
        return orderBaseQueryBO.checkNum(sellOrderDTO);
        
    }*/
	public void orderPayment(String orderId, String initActStat, String bankId,
			String payChannel, String payDetails, String modifyUser,
			String defaultEntityId, String operatorType, String memo,
			String currentNode) throws Exception {
		SellOrder sellOrder = new SellOrder();
		sellOrder.setOrderId(orderId);

		// ��������״̬Ϊ��֧��
//		sellOrder.setPaymentState(DictInfoConstants.PAY_STATE_PAID);
		sellOrder.setInitActStat(initActStat);
		/* ����֧����Ϣ�޸� */
		sellOrder.setPayChannel(payChannel);
		sellOrder.setPayDetails(payDetails);

		/* ����������Ϣ�޸� */
		sellOrder.setIntoBankId(bankId);

		// sellOrder.setPaymentDate(DateUtil.getCurrentDateStr());
		sellOrder.setModifyTime(DateUtil.getCurrentTime());

		// �����޸���
		sellOrder.setModifyUser(modifyUser);

		SellOrderFlow sellOrderFlow = new SellOrderFlow();
		/**
		 * �򶩵����̼�¼IDû��ʵ������ ��ȡsequenceName
		 */
		sellOrderFlow.setOrderFlowId(commonsDAO
				.getNextValueOfSequence("TB_SELL_ORDER_FLOW"));

		sellOrderFlow.setOrderId(orderId);
		// ��������ʵ��ID
		sellOrderFlow.setEntityId(defaultEntityId);
		// ������������
		sellOrderFlow.setOperateType(operatorType);

		sellOrderFlow.setOrderFlowNode(currentNode);

		// ����˵��
		sellOrderFlow.setMemo(memo);

		sellOrderFlow.setCreateTime(DateUtil.getCurrentTime());
		sellOrderFlow.setCreateUser(modifyUser);

		sellOrderFlow.setModifyTime(DateUtil.getCurrentTime());

		sellOrderFlow.setModifyUser(modifyUser);

		sellOrderFlow.setDataState("1");

		SellOrderExample ex=new SellOrderExample();
		ex.createCriteria().andOrderIdEqualTo(orderId);
		sellOrderMapper.updateByExampleSelective(sellOrder, ex);


		sellOrderFlowMapper.insert(sellOrderFlow);
	}

	public void orderFlow(String orderId, String defaultEntityId,
			String operatorType, String currentNode, String memo,
			String modifyUser) throws BizServiceException {
		SellOrderFlow sellOrderFlow = new SellOrderFlow();
		/**
		 * �򶩵����̼�¼IDû��ʵ������ ��ȡsequenceName
		 */
		sellOrderFlow.setOrderFlowId(commonsDAO
				.getNextValueOfSequence("TB_SELL_ORDER_FLOW"));

		sellOrderFlow.setOrderId(orderId);
		// ��������ʵ��ID
		sellOrderFlow.setEntityId(defaultEntityId);
		// ������������
		sellOrderFlow.setOperateType(operatorType);

		sellOrderFlow.setOrderFlowNode(currentNode);

		// ����˵��
		sellOrderFlow.setMemo(memo);

		sellOrderFlow.setCreateTime(DateUtil.getCurrentTime());
		sellOrderFlow.setCreateUser(modifyUser);

		sellOrderFlow.setModifyTime(DateUtil.getCurrentTime());

		sellOrderFlow.setModifyUser(modifyUser);

		sellOrderFlow.setDataState("1");
		sellOrderFlowMapper.insert(sellOrderFlow);
	}

	/*public void insertCreditOrderCardList(
			SellOrderCardListDTO sellOrderCardListDTO) throws Exception {
		baseOrderDAO.insert("ORDER.insertCreditOrderCardList",
				sellOrderCardListDTO);
	}
	public SellOrderDTO selectForUpdate(String orderId){
		SellOrderDTO dto =(SellOrderDTO)baseOrderDAO.queryForObject("selectForUpdate", orderId);
		return dto;
	}*/
	
	/*public CommonsDAO getCommonsDAO() {
		return commonsDAO;
	}

	public void setCommonsDAO(CommonsDAO commonsDAO) {
		this.commonsDAO = commonsDAO;
	}

	public SellOrderDAO getSellOrderDAO() {
		return sellOrderDAO;
	}

	public void setSellOrderDAO(SellOrderDAO sellOrderDAO) {
		this.sellOrderDAO = sellOrderDAO;
	}

	public SellOrderFlowDAO getSellOrderFlowDAO() {
		return sellOrderFlowDAO;
	}

	public void setSellOrderFlowDAO(SellOrderFlowDAO sellOrderFlowDAO) {
		this.sellOrderFlowDAO = sellOrderFlowDAO;
	}

	public SellOrderCardListDAO getSellOrderCardListDAO() {
		return sellOrderCardListDAO;
	}

	public void setSellOrderCardListDAO(
			SellOrderCardListDAO sellOrderCardListDAO) {
		this.sellOrderCardListDAO = sellOrderCardListDAO;
	}

	public SellOrderListDAO getSellOrderListDAO() {
		return sellOrderListDAO;
	}

	public void setSellOrderListDAO(SellOrderListDAO sellOrderListDAO) {
		this.sellOrderListDAO = sellOrderListDAO;
	}

	public BaseDAO getBaseOrderDAO() {
		return baseOrderDAO;
	}

	public void setBaseOrderDAO(BaseDAO baseOrderDAO) {
		this.baseOrderDAO = baseOrderDAO;
	}

	public OrderBaseQueryBO getOrderBaseQueryBO() {
		return orderBaseQueryBO;
	}

	public void setOrderBaseQueryBO(OrderBaseQueryBO orderBaseQueryBO) {
		this.orderBaseQueryBO = orderBaseQueryBO;
	}

	public BaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	public EntityStockService getEntityStockService() {
		return entityStockService;
	}

	public void setEntityStockService(EntityStockService entityStockService) {
		this.entityStockService = entityStockService;
	}*/

}

package com.huateng.univer.seller.cardholder.biz.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.entity.dto.DepartmentDTO;
import com.allinfinance.univer.seller.cardholder.dto.CardholderDTO;
import com.allinfinance.univer.seller.cardholder.dto.CardholderQueryDTO;
import com.allinfinance.univer.seller.customer.CustomerDTO;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.dao.BaseDAO;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.dao.PageQueryDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.CardholderCardlistDAO;
import com.huateng.framework.ibatis.dao.CardholderDAO;
import com.huateng.framework.ibatis.dao.EntityStockDAO;
import com.huateng.framework.ibatis.dao.SellOrderCardListDAO;
import com.huateng.framework.ibatis.dao.SellOrderDAO;
import com.huateng.framework.ibatis.model.Cardholder;
import com.huateng.framework.ibatis.model.CardholderCardlist;
import com.huateng.framework.ibatis.model.CardholderExample;
import com.huateng.framework.ibatis.model.EntityStock;
import com.huateng.framework.ibatis.model.SellOrder;
import com.huateng.framework.ibatis.model.SellOrderCardList;
import com.huateng.framework.ibatis.model.CardholderExample.Criteria;
import com.huateng.framework.util.Amount;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.ReflectionUtil;
import com.huateng.hstserver.constants.RspCodeMap;
import com.huateng.hstserver.gatewayService.Java2ACCBusinessServiceImpl;
import com.huateng.hstserver.model.AccPackageDTO;
import com.huateng.univer.entitybaseinfo.department.biz.service.DepartmentService;
import com.huateng.univer.seller.cardholder.biz.service.CardholderService;
import com.huateng.univer.seller.customer.biz.service.CustomerService;

public class CardholderServiceImpl implements CardholderService {

	Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 共通操作DAO
	 */
	private CommonsDAO commonsDAO;
	private PageQueryDAO pageQueryDAO;
	private CardholderDAO cardholderDAO;
	private CustomerService customerService;
	private DepartmentService departmentService;
	private BaseDAO baseDAO;
	private SellOrderCardListDAO sellOrderCardListDAO;
	private EntityStockDAO entityStockDAO;
	private CardholderCardlistDAO cardholderCardlistDAO;
	private SellOrderDAO sellOrderDAO;
	private Java2ACCBusinessServiceImpl java2ACCBusinessService;

	public SellOrderDAO getSellOrderDAO() {
		return sellOrderDAO;
	}

	public void setSellOrderDAO(SellOrderDAO sellOrderDAO) {
		this.sellOrderDAO = sellOrderDAO;
	}

	public CardholderCardlistDAO getCardholderCardlistDAO() {
		return cardholderCardlistDAO;
	}

	public void setCardholderCardlistDAO(
			CardholderCardlistDAO cardholderCardlistDAO) {
		this.cardholderCardlistDAO = cardholderCardlistDAO;
	}

	public EntityStockDAO getEntityStockDAO() {
		return entityStockDAO;
	}

	public void setEntityStockDAO(EntityStockDAO entityStockDAO) {
		this.entityStockDAO = entityStockDAO;
	}

	/**
	 * 查询持卡人
	 */
	public PageDataDTO inquery(CardholderQueryDTO cardholderQueryDTO)
			throws BizServiceException {
		try {
			return pageQueryDAO.query("CARDHOLDER.selectCardholdersByDTO",
					cardholderQueryDTO);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BizServiceException("查询持卡人信息失败！");
		}
	}

	/**
	 * 查询持卡人
	 */
	public PageDataDTO queryCardHolder(CardholderQueryDTO cardholderQueryDTO)
			throws BizServiceException {
		try {
			return pageQueryDAO.query("CARDHOLDER.selectCardholders",
					cardholderQueryDTO);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BizServiceException("查询持卡人信息失败！");
		}
	}

	/**
	 * 查看持卡人
	 */
	@SuppressWarnings("unchecked")
	public CardholderDTO view(CardholderDTO cardholderDTO)
			throws BizServiceException {
		try {
			Map<String, String> rspCodeMap = RspCodeMap.getRspCodeMap();
			Cardholder cardholder = cardholderDAO
					.selectByPrimaryKey(cardholderDTO.getCardholderId());

			ReflectionUtil.copyProperties(cardholder, cardholderDTO);
			// 关联所属客户信息
			CustomerDTO customerDTO = new CustomerDTO();
			customerDTO.setEntityId(cardholder.getEntityId());
			cardholderDTO.setCustomerDTO(customerService
					.viewCustomer(customerDTO));
			/** 关联持卡人持有卡信息 */
			List<Map<String, String>> lstCardNo = cardholderDAO
					.selectCardNoByCardholderId(cardholderDTO.getCardholderId());
			if (null != lstCardNo && lstCardNo.size() > 0) {
				/** 处理卡号列表，以逗号分隔 */
				String cardNoString = lstCardNo.toString().replace("[", "")
						.replace("]", "").replace("{", "").replace("}", "")
						.replace(" ", "").replace("cardNo=", "");
				logger.debug("cardholderId:" + cardholderDTO.getCardholderId()
						+ "find out card:" + cardNoString);
				/** 持卡人卡信息报文 */
				AccPackageDTO accPackageDTO = new AccPackageDTO();
				accPackageDTO.setTxnCode("S040");
				accPackageDTO.setCardNo(cardNoString);
				accPackageDTO.setBegin_row(String
						.valueOf(cardholderDTO.getSellOrderCardListQueryDTO()
								.getFirstCursorPosition()));
				accPackageDTO.setEnd_row(String
						.valueOf(cardholderDTO.getSellOrderCardListQueryDTO()
								.getLastCursorPosition()));
//				Map map = new HashMap();
//				map.put(CFunctionConstant.TXN_TYPE, "S040");
//				map.put(CFunctionConstant.RESV2, cardNoString);
//				map.put(CFunctionConstant.SWT_TXN_DATE, String
//						.valueOf(cardholderDTO.getSellOrderCardListQueryDTO()
//								.getFirstCursorPosition()));
//				map.put(CFunctionConstant.SWT_TXN_TIME, String
//						.valueOf(cardholderDTO.getSellOrderCardListQueryDTO()
//								.getLastCursorPosition()));
				logger.debug("before send port to vCardHolderBatInq");
//				OperationResult or = java2C.sendTpService("vCardHolderBatInq",
//						map, Const.JAVA2C_BIG_AMT, false);
				accPackageDTO=java2ACCBusinessService.queryCardByCardHolder(accPackageDTO);
				String rspCode = accPackageDTO.getRespCode();
				logger.debug("vCardHolderBatInq return:" + rspCode);
				List<Map<?, ?>> list = new ArrayList<Map<?, ?>>();
				if (rspCode.equals("00")) {
					/** 报文返回状态为成功 */
					logger.debug("vCardHolderBatInq success!");
					String result = accPackageDTO.getOtherData();
					String[] records = result.split("\\|");

					if (records != null && records.length > 0) {
						for (int i = 0; i < records.length; i++) {
							String[] fields = records[i].split("\\^");
							HashMap map2 = new HashMap();
							map2.put("cardNo", fields[0]);
							map2.put("productName", fields[1]);
							map2.put("validityPeriod", DateUtil
									.formatStringDate(fields[2]));
							map2.put("totalAmt", Amount
									.getReallyAmount(fields[3]));
							map2.put("avaliableAmt", Amount
									.getReallyAmount(fields[4]));
							map2.put("freezeAmt", Amount
									.getReallyAmount(fields[5]));
							list.add(map2);
						}
					}
					String totalRow = accPackageDTO.getTotalRow();
					String realRow = accPackageDTO.getRealRow();
					cardholderDTO.getCardholderCardList().setData(list);
					cardholderDTO.getCardholderCardList().setTotalRecord(
							Integer.parseInt(totalRow));
					cardholderDTO.getCardholderCardList().setPageSize(
							Integer.parseInt(realRow));
					logger.debug("find out card count:" + totalRow
							+ " and real display count:" + realRow);
				} else {
					if (rspCodeMap.containsKey(rspCode)) {
						throw new BizServiceException(rspCodeMap.get(rspCode));
					}
				}
			}
			return cardholderDTO;
		} catch (BizServiceException ex) {
			throw ex;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BizServiceException("查看持卡人信息失败！");
		}
	}

	/**
	 * 查询没有关联到orderList表的持卡人
	 */
	public CardholderDTO inqueryCardholderWithOrderList(
			CardholderQueryDTO cardholderQueryDTO) throws BizServiceException {
		try {

			return null;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BizServiceException("查询持卡人信息失败！");
		}
	}

	/**
	 * 添加持卡人
	 */
	public void insert(CardholderDTO cardholderDTO) throws BizServiceException {
		// 同一客户下持卡人的 工号+姓名 唯一
		try {
			if (checkRepeat(cardholderDTO) > 0) {
				throw new BizServiceException("客户："
						+ cardholderDTO.getEntityId() + "下该持卡人已存在！");
			}
			Cardholder cardholder = new Cardholder();
			ReflectionUtil.copyProperties(cardholderDTO, cardholder);
			cardholder.setCardholderId(commonsDAO
					.getNextValueOfSequence("TB_CARDHOLDER"));
			cardholder.setCreateUser(cardholderDTO.getLoginUserId());
			cardholder.setCreateTime(DateUtil.getCurrentTime());
			cardholder.setModifyUser(cardholderDTO.getLoginUserId());
			cardholder.setModifyTime(DateUtil.getCurrentTime());
			cardholder.setDataState(DataBaseConstant.DATA_STATE_NORMAL);

			cardholderDAO.insert(cardholder);
		} catch (BizServiceException be) {
			throw be;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BizServiceException("添加持卡人信息失败！");
		}
	}

	public CardholderDTO insertCardholderDTO(CardholderDTO cardholderDTO)
			throws BizServiceException {
		// 同一客户下持卡人的 工号+姓名 唯一
		try {
			// 在订单卡列表里添加持卡人信息
			cardholderDTO.setCardholderId(commonsDAO
					.getNextValueOfSequence("TB_CARDHOLDER"));
			// SellOrderCardList sellOrderCardList = new SellOrderCardList();
			// sellOrderCardList.setOrderCardListId(commonsDAO.getNextValueOfSequence("TB_SELL_ORDER_CARD_LIST"));
			// sellOrderCardList.setOrderId(cardholderDTO.getOrderId());
			// sellOrderCardList.setCardholderId(cardholderDTO.getCardholderId());
			// sellOrderCardList.setLastName(cardholderDTO.getLastName());
			// sellOrderCardList.setFirstName(cardholderDTO.getFirstName());
			// sellOrderCardList.setExternalId(cardholderDTO.getExternalId());
			// sellOrderCardList.setCreateTime(DateUtil.getCurrentTime());
			// sellOrderCardList.setCreateUser(cardholderDTO.getLoginUserId());
			// sellOrderCardList.setModifyTime(DateUtil.getCurrentTime());
			// sellOrderCardList.setModifyUser(cardholderDTO.getLoginUserId());
			// sellOrderCardList.setDataState("1");
			// sellOrderCardListDAO.insert(sellOrderCardList);

			//13-01-22 提出需求快速添加持卡人时不对重名做检验
//			if (checkRepeat(cardholderDTO) > 0) {
//				throw new BizServiceException("客户："
//						+ cardholderDTO.getEntityId() + "下该持卡人已存在！");
//			}
			Cardholder cardholder = new Cardholder();
			ReflectionUtil.copyProperties(cardholderDTO, cardholder);
			cardholder.setCreateUser(cardholderDTO.getLoginUserId());
			cardholder.setCreateTime(DateUtil.getCurrentTime());
			cardholder.setModifyUser(cardholderDTO.getLoginUserId());
			cardholder.setModifyTime(DateUtil.getCurrentTime());
			cardholder.setDataState(DataBaseConstant.DATA_STATE_NORMAL);

			cardholderDAO.insert(cardholder);
			ReflectionUtil.copyProperties(cardholder, cardholderDTO);

			return cardholderDTO;
		} catch (BizServiceException be) {
			throw be;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BizServiceException("添加持卡人信息失败！");
		}
	}

	/**
	 * 记录记名库存卡的持卡人信息，并将持卡人信息与持卡人信息进行关联
	 */
	public void insertSignCardHolder(CardholderDTO cardholderDTO)
			throws BizServiceException {

		// 同一客户下持卡人的 工号+姓名 唯一
		try {
			// 在订单卡列表里添加持卡人与卡关联的信息
			cardholderDTO.setCardholderId(commonsDAO
					.getNextValueOfSequence("TB_CARDHOLDER"));

			String orderId = commonsDAO.getNextValueOfSequence("TB_SELL_ORDER");
			SellOrderCardList sellOrderCardList = new SellOrderCardList();
			sellOrderCardList.setOrderCardListId(commonsDAO
					.getNextValueOfSequence("TB_SELL_ORDER_CARD_LIST"));
			sellOrderCardList.setOrderId(orderId);
			sellOrderCardList.setCardholderId(cardholderDTO.getCardholderId());
			sellOrderCardList.setLastName(cardholderDTO.getLastName());
			sellOrderCardList.setFirstName(cardholderDTO.getFirstName());
			sellOrderCardList.setExternalId(cardholderDTO.getExternalId());
			EntityStock entityStock = entityStockDAO
					.selectByPrimaryKey(cardholderDTO.getCardNo());
			sellOrderCardList.setCardState(entityStock.getStockState());
			sellOrderCardList.setCreditAmount(cardholderDTO.getCreditAmont());
			sellOrderCardList.setCardNo(cardholderDTO.getCardNo());
			sellOrderCardList.setCreateTime(DateUtil.getCurrentTime());
			sellOrderCardList.setCreateUser(cardholderDTO.getLoginUserId());
			sellOrderCardList.setModifyTime(DateUtil.getCurrentTime());
			sellOrderCardList.setModifyUser(cardholderDTO.getLoginUserId());
			sellOrderCardList.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
			sellOrderCardListDAO.insert(sellOrderCardList);
			// 添加个默认的订单号
			SellOrder sellOrder = new SellOrder();
			sellOrder.setOrderId(orderId);
			sellOrder.setOrderType("00000000");
			sellOrder.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
			sellOrder.setCreateTime(DateUtil.getStringDate());
			sellOrder.setCreateUser(cardholderDTO.getLoginUserId());
			sellOrder.setModifyTime(DateUtil.getStringDate());
			sellOrder.setModifyUser(cardholderDTO.getLoginUserId());
			sellOrderDAO.insert(sellOrder);
			// 在持卡人卡关联表里记录持卡人与卡 关联的信息
			CardholderCardlist cardholderCardlist = new CardholderCardlist();
			cardholderCardlist
					.setCardlistId(commonsDAO
							.getNextValueOfSequenceBySequence("SEQ_CARDHOLDER_CARDLIST"));
			cardholderCardlist.setCardholderId(cardholderDTO.getCardholderId());
			cardholderCardlist.setCardNo(cardholderDTO.getCardNo());
			cardholderCardlist.setProductId(entityStock.getProductId());
			cardholderCardlist.setCardState(entityStock.getStockState());
			cardholderCardlist.setCreateUser(cardholderDTO.getLoginUserId());
			cardholderCardlist.setCreateTime(DateUtil.getCurrentTime());
			cardholderCardlist.setModifyUser(cardholderDTO.getLoginUserId());
			cardholderCardlist.setModifyTime(DateUtil.getCurrentTime());
			cardholderCardlist.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
			cardholderCardlistDAO.insert(cardholderCardlist);
			if (checkRepeat(cardholderDTO) > 0) {
				throw new BizServiceException("客户："
						+ cardholderDTO.getEntityId() + "下该持卡人已存在！");
			}
			// 在持卡人信息表里记录持卡人信息

			Cardholder cardholder = new Cardholder();
			ReflectionUtil.copyProperties(cardholderDTO, cardholder);
			cardholder.setCreateUser(cardholderDTO.getLoginUserId());
			cardholder.setCreateTime(DateUtil.getCurrentTime());
			cardholder.setModifyUser(cardholderDTO.getLoginUserId());
			cardholder.setModifyTime(DateUtil.getCurrentTime());
			cardholder.setDataState(DataBaseConstant.DATA_STATE_NORMAL);

			cardholderDAO.insert(cardholder);
			ReflectionUtil.copyProperties(cardholder, cardholderDTO);

		} catch (BizServiceException be) {
			throw be;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BizServiceException("添加持卡人信息失败！");
		}
	}

	/**
	 * 同一客户下持卡人的 工号(externalId)+姓名 唯一
	 */
	private int checkRepeat(CardholderDTO cardholderDTO) throws Exception {
		CardholderExample example = new CardholderExample();

		Criteria criteria = example.createCriteria().andEntityIdEqualTo(
				cardholderDTO.getEntityId()).andFirstNameEqualTo(
				cardholderDTO.getFirstName()).andDataStateEqualTo(
				DataBaseConstant.DATA_STATE_NORMAL);
		if (null != cardholderDTO.getExternalId()
				&& !"".equals(cardholderDTO.getExternalId())) {
			criteria.andExternalIdEqualTo(cardholderDTO.getExternalId());
		}
		// 更新时
		if (null != cardholderDTO.getCardholderId()) {
			criteria.andCardholderIdNotEqualTo(cardholderDTO.getCardholderId());
		}
		return cardholderDAO.countByExample(example);
	}

	/**
	 * 修改更新持卡人
	 */
	public void update(CardholderDTO cardholderDTO) throws BizServiceException {
		try {
			if (checkRepeat(cardholderDTO) > 0) {
				throw new BizServiceException("持卡姓名或工号不能和已有持卡人重复！");
			}
			if (cardholderDTO.getCardholderState().equals("0")) {
				Integer count = (Integer) baseDAO.queryForObject(
						"TB_CARDHOLDER.selectCardHolderOnDelete", cardholderDTO
								.getCardholderId());
				if (count > 0) {
					throw new BizServiceException(
							"当前持卡人已经持有卡或在制卡订单中,不能将其修改为无效状态！");
				}
			}
			Cardholder cardholder = new Cardholder();
			ReflectionUtil.copyProperties(cardholderDTO, cardholder);

			cardholder.setModifyUser(cardholderDTO.getLoginUserId());
			cardholder.setModifyTime(DateUtil.getCurrentTime());

			cardholderDAO.updateByPrimaryKeySelective(cardholder);
		} catch (BizServiceException be) {
			throw be;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BizServiceException("更新持卡人信息失败！");
		}
	}

	public void updateCust(CardholderDTO cardholderDTO)
			throws BizServiceException {
		try {
			Cardholder cardholder = cardholderDAO
					.selectByPrimaryKey(cardholderDTO.getCardholderId());
			if (!cardholder.getEntityId().equals(cardholderDTO.getEntityId())) {
				if (!isExistCustomerByCardholderDTO(cardholderDTO)) {
					throw new BizServiceException("新客户的产品和持卡人已有的产品不符，不能关联到该客户！");
				}
				cardholder.setEntityId(cardholderDTO.getEntityId());
				cardholder.setModifyUser(cardholderDTO.getLoginUserId());
				cardholder.setModifyTime(DateUtil.getCurrentTime());

				cardholderDAO.updateByPrimaryKeySelective(cardholder);
			}
		} catch (BizServiceException be) {
			throw be;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BizServiceException("关联新客户失败！");
		}
	}

	/**
	 * 新客户是否和持卡人有同样的产品
	 * 
	 * @return
	 */
	private boolean isExistCustomerByCardholderDTO(CardholderDTO cardholderDTO)
			throws Exception {

		Integer count = (Integer) baseDAO.queryForObject(
				"CARDHOLDER.isExistCustomerByCardholderDTO", cardholderDTO);
		return count <= 0 ? true : false;
	}

	/**
	 * 删除持卡人
	 */
	public void delete(CardholderDTO cardholderDTO) throws BizServiceException {
		try {

			String[] cardholderIds = cardholderDTO.getCardholderIds();
			List<Cardholder> cardholders = new ArrayList<Cardholder>();
			for (String cardholderId : cardholderIds) {
				Integer count = (Integer) baseDAO.queryForObject(
						"TB_CARDHOLDER.selectCardHolderOnDelete", cardholderId);
				if (count > 0) {
					throw new BizServiceException("持卡人" + cardholderId
							+ "已经持有卡或在制卡订单中,不能删除！");
				}
				Cardholder cardholder = new Cardholder();
				cardholder.setCardholderId(cardholderId);
				cardholder.setModifyUser(cardholderDTO.getLoginUserId());
				cardholder.setModifyTime(DateUtil.getCurrentTime());
				cardholder.setDataState(DataBaseConstant.DATA_STATE_DELETE);
				cardholders.add(cardholder);
			}
			commonsDAO
					.batchDelete(
							"TB_CARDHOLDER.abatorgenerated_updateByPrimaryKeySelective",
							cardholders);
		} catch (BizServiceException be) {
			throw be;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BizServiceException("删除持卡人信息失败！");
		}
	}

	public CardholderDTO insertCardholder(CardholderDTO cardholderDTO)
			throws BizServiceException {
		try {

			String entityId = cardholderDTO.getEntityId();
			/**
			 * 取客户部门信息
			 */
			List<DepartmentDTO> department1s = departmentService
					.inquery(entityId);
			List<DepartmentDTO> departments = cardholderDTO.getDepartmentList();
			for (int i = 0; i < department1s.size(); i++) {
				String departmentName = department1s.get(i).getDepartmentName();
				for (int j = 0; j < departments.size(); j++) {
					if (departmentName.equals(departments.get(j)
							.getDepartmentName())) {
						departments.remove(j);
						j--;
					}
				}
			}

			department1s.addAll(departments);

			// 添加导入文件中新增的客户部门
			for (DepartmentDTO departmentDTO : departments) {
				departmentDTO.setCreateUser(cardholderDTO.getLoginUserId());
				departmentDTO.setModifyUser(cardholderDTO.getLoginUserId());
				departmentService.insert(departmentDTO);
			}

			Map<String, CardholderDTO> map = new HashMap<String, CardholderDTO>();
			for (CardholderDTO dto : cardholderDTO.getCardholderDTOList()) {
				dto.setEntityId(entityId);
				String departmentName = dto.getDepartmentName();
				boolean checkFlag = true;
				if (!"".equals(departmentName)) {
					for (int i = 0; i < department1s.size(); i++) {
						if (departmentName.equals(department1s.get(i)
								.getDepartmentName())) {
							dto.setDepartmentId(department1s.get(i)
									.getDepartmentId());
							checkFlag = false;
							break;
						}
					}
					if (checkFlag) {
						throw new BizServiceException("部门：" + departmentName
								+ "不存在！");
					}
				}

//				if (checkRepeat(dto) > 0) {
//					throw new BizServiceException(
//							"持卡姓名或工号不能和已有持卡人重复！请检查导入文件  ！");
//				}

//				CardholderDTO obj = map.put(dto.getFirstName() + ""
//						+ dto.getExternalId(), dto);
//				if (obj != null) {
//					throw new BizServiceException("持卡人：" + dto.getFirstName()
//							+ "有重复！请检查导入文件");
//				}
			}

			List<Cardholder> cardholderList = new ArrayList<Cardholder>();
			for (CardholderDTO dto : cardholderDTO.getCardholderDTOList()) {
				Cardholder cardholder = new Cardholder();
				ReflectionUtil.copyProperties(dto, cardholder);
				cardholder.setFirstName(dto.getFirstName().trim());
				cardholder.setEntityId(entityId);
				cardholder.setCardholderId(commonsDAO
						.getNextValueOfSequence("TB_CARDHOLDER"));
				cardholder.setCardholderState(DataBaseConstant.STATE_ACTIVE);
				cardholder.setCreateUser(cardholderDTO.getLoginUserId());
				cardholder.setCreateTime(DateUtil.getCurrentTime());
				cardholder.setModifyUser(cardholderDTO.getLoginUserId());
				cardholder.setModifyTime(DateUtil.getCurrentTime());
				cardholder.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
				cardholderList.add(cardholder);
			}
			commonsDAO.batchInsert("TB_CARDHOLDER.abatorgenerated_insert",
					cardholderList);

			return cardholderDTO;
		} catch (BizServiceException be) {
			throw be;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BizServiceException("导入持卡人失败！");
		}
	}

	public CardholderDTO insertCardholderReturnIds(CardholderDTO cardholderDTO)
			throws BizServiceException {
		String[] cardholderIdListStr = new String[cardholderDTO
				.getCardholderDTOList().size()];
		try {

			String entityId = cardholderDTO.getEntityId();
			/**
			 * 取客户部门信息
			 */
			List<DepartmentDTO> department1s = departmentService
					.inquery(entityId);
			List<DepartmentDTO> departments = cardholderDTO.getDepartmentList();
			for (int i = 0; i < department1s.size(); i++) {
				String departmentName = department1s.get(i).getDepartmentName();
				for (int j = 0; j < departments.size(); j++) {
					if (departmentName.equals(departments.get(j)
							.getDepartmentName())) {
						departments.remove(j);
						j--;
					}
				}
			}

			department1s.addAll(departments);

			// 添加导入文件中新增的客户部门
			for (DepartmentDTO departmentDTO : departments) {
				departmentDTO.setCreateUser(cardholderDTO.getLoginUserId());
				departmentDTO.setModifyUser(cardholderDTO.getLoginUserId());
				departmentService.insert(departmentDTO);
			}

			Map<String, CardholderDTO> map = new HashMap<String, CardholderDTO>();
			for (CardholderDTO dto : cardholderDTO.getCardholderDTOList()) {
				dto.setEntityId(entityId);
				String departmentName = dto.getDepartmentName();
				boolean checkFlag = true;
				if (!"".equals(departmentName)) {
					for (int i = 0; i < department1s.size(); i++) {
						if (departmentName.equals(department1s.get(i)
								.getDepartmentName())) {
							dto.setDepartmentId(department1s.get(i)
									.getDepartmentId());
							checkFlag = false;
							break;
						}
					}
					if (checkFlag) {
						throw new BizServiceException("部门：" + departmentName
								+ "不存在！");
					}
				}

//				if (checkRepeat(dto) > 0) {
//					throw new BizServiceException(
//							"持卡姓名或工号不能和已有持卡人重复！请检查导入文件   - -!");
//				}
//
//				CardholderDTO obj = map.put(dto.getFirstName() + ""
//						+ dto.getExternalId(), dto);
//				if (obj != null) {
//					throw new BizServiceException("持卡人：" + dto.getFirstName()
//							+ "有重复！请检查导入文件");
//				}
			}

			// List<Cardholder> cardholderList = new ArrayList<Cardholder>();
			for (int i = 0; i < cardholderDTO.getCardholderDTOList().size(); i++) {
				CardholderDTO dto = cardholderDTO.getCardholderDTOList().get(i);
				Cardholder cardholder = new Cardholder();
				ReflectionUtil.copyProperties(dto, cardholder);
				cardholder.setFirstName(dto.getFirstName().trim());
				cardholder.setEntityId(entityId);
				cardholder.setCardholderId(commonsDAO
						.getNextValueOfSequence("TB_CARDHOLDER"));
				cardholder.setCardholderState(DataBaseConstant.STATE_ACTIVE);
				cardholder.setCreateUser(cardholderDTO.getLoginUserId());
				cardholder.setCreateTime(DateUtil.getCurrentTime());
				cardholder.setModifyUser(cardholderDTO.getLoginUserId());
				cardholder.setModifyTime(DateUtil.getCurrentTime());
				cardholder.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
				// cardholderList.add(cardholder);
				cardholderDAO.insert(cardholder);
				cardholderIdListStr[i] = cardholder.getCardholderId();
			}
			// commonsDAO.batchInsert("TB_CARDHOLDER.abatorgenerated_insert",
			// cardholderList);
			// 返回cardholderId用于在订单编辑中快速导入持卡人信息
			cardholderDTO.setCardholderIds(cardholderIdListStr);

			return cardholderDTO;
		} catch (BizServiceException be) {
			throw be;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BizServiceException("导入持卡人失败！");
		}
	}

	public List<CardholderQueryDTO> getCardHolderByCardNo(String cardNo)
			throws BizServiceException {
		return cardholderDAO.selectCardholderByCardNo(cardNo);
	}

	public CommonsDAO getCommonsDAO() {
		return commonsDAO;
	}

	public void setCommonsDAO(CommonsDAO commonsDAO) {
		this.commonsDAO = commonsDAO;
	}

	public PageQueryDAO getPageQueryDAO() {
		return pageQueryDAO;
	}

	public void setPageQueryDAO(PageQueryDAO pageQueryDAO) {
		this.pageQueryDAO = pageQueryDAO;
	}

	public CardholderDAO getCardholderDAO() {
		return cardholderDAO;
	}

	public void setCardholderDAO(CardholderDAO cardholderDAO) {
		this.cardholderDAO = cardholderDAO;
	}

	public CustomerService getCustomerService() {
		return customerService;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	public BaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	public DepartmentService getDepartmentService() {
		return departmentService;
	}

	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	public SellOrderCardListDAO getSellOrderCardListDAO() {
		return sellOrderCardListDAO;
	}

	public void setSellOrderCardListDAO(
			SellOrderCardListDAO sellOrderCardListDAO) {
		this.sellOrderCardListDAO = sellOrderCardListDAO;
	}
	public Java2ACCBusinessServiceImpl getJava2ACCBusinessService() {
		return java2ACCBusinessService;
	}

	public void setJava2ACCBusinessService(
			Java2ACCBusinessServiceImpl java2accBusinessService) {
		java2ACCBusinessService = java2accBusinessService;
	}

}

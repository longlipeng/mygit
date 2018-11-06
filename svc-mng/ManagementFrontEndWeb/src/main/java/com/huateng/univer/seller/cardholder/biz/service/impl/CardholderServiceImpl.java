package com.huateng.univer.seller.cardholder.biz.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.ibatis.attach.dao.AttachDAO;
import com.allinfinance.univer.entity.dto.DepartmentDTO;
import com.allinfinance.univer.seller.cardholder.dto.AttachInfoDTO;
import com.allinfinance.univer.seller.cardholder.dto.CardholderDTO;
import com.allinfinance.univer.seller.cardholder.dto.CardholderQueryDTO;
import com.allinfinance.univer.seller.cardholder.dto.CheckPeopleInfoDTO;
import com.allinfinance.univer.seller.cardholder.dto.CompanyInfoDTO;
import com.allinfinance.univer.seller.cardholder.dto.IdCardInfoDTO;
import com.allinfinance.univer.seller.cardholder.dto.PictureInfoQueryDTO;
import com.allinfinance.univer.seller.customer.CustomerDTO;
import com.allinfinance.util.TransmitToFileForWebservice;
import com.allinfinance.util.XmlConverter;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.dao.BaseDAO;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.dao.PageQueryDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.CardholderCardlistDAO;
import com.huateng.framework.ibatis.dao.CardholderDAO;
import com.huateng.framework.ibatis.dao.CardholderVerifyDAO;
import com.huateng.framework.ibatis.dao.CheckPeopleInfoDAO;
import com.huateng.framework.ibatis.dao.CompanyInfoDAO;
import com.huateng.framework.ibatis.dao.EntityStockDAO;
import com.huateng.framework.ibatis.dao.PictrueInfoDAO;
import com.huateng.framework.ibatis.dao.SellOrderCardListDAO;
import com.huateng.framework.ibatis.dao.SellOrderDAO;
import com.huateng.framework.ibatis.model.Cardholder;
import com.huateng.framework.ibatis.model.CardholderCardlist;
import com.huateng.framework.ibatis.model.CardholderExample;
import com.huateng.framework.ibatis.model.CardholderExample.Criteria;
import com.huateng.framework.ibatis.model.CardholderVerify;
import com.huateng.framework.ibatis.model.CardholderVerifyExample;
import com.huateng.framework.ibatis.model.CheckPeopleInfo;
import com.huateng.framework.ibatis.model.CheckPeopleInfoExample;
import com.huateng.framework.ibatis.model.CompanyInfo;
import com.huateng.framework.ibatis.model.CompanyInfoExample;
import com.huateng.framework.ibatis.model.CompanyInfoKey;
import com.huateng.framework.ibatis.model.EntityStock;
import com.huateng.framework.ibatis.model.PictrueInfo;
import com.huateng.framework.ibatis.model.PictrueInfoExample;
import com.huateng.framework.ibatis.model.SellOrder;
import com.huateng.framework.ibatis.model.SellOrderCardList;
import com.huateng.framework.util.Amount;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.ReflectionUtil;
import com.huateng.hstserver.constants.RspCodeMap;
import com.huateng.hstserver.gatewayService.Java2ACCBusinessServiceImpl;
import com.huateng.hstserver.model.AccPackageDTO;
import com.huateng.univer.entitybaseinfo.department.biz.service.DepartmentService;
import com.huateng.univer.seller.cardholder.biz.service.CardholderService;
import com.huateng.univer.seller.customer.biz.service.CustomerService;
import com.huateng.univer.synch.CRMSynch.CardholderSynchCRM;

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
	private AttachDAO  attachDAO;
	
	private Java2ACCBusinessServiceImpl java2ACCBusinessService;
	private CheckPeopleInfoDAO checkPeopleInfoDAO;
    private PictrueInfoDAO pictrueInfoDAO;
	
    
    
    
    
	public CheckPeopleInfoDAO getCheckPeopleInfoDAO() {
		return checkPeopleInfoDAO;
	}

	public void setCheckPeopleInfoDAO(CheckPeopleInfoDAO checkPeopleInfoDAO) {
		this.checkPeopleInfoDAO = checkPeopleInfoDAO;
	}

	public PictrueInfoDAO getPictrueInfoDAO() {
		return pictrueInfoDAO;
	}

	public void setPictrueInfoDAO(PictrueInfoDAO pictrueInfoDAO) {
		this.pictrueInfoDAO = pictrueInfoDAO;
	}

	public AttachDAO getAttachDAO() {
		return attachDAO;
	}

	public void setAttachDAO(AttachDAO attachDAO) {
		this.attachDAO = attachDAO;
	}

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
	 * 审核查询持卡人
	 */
	public PageDataDTO inqueryByCheck(CardholderQueryDTO cardholderQueryDTO)
			throws BizServiceException {
		try {
			return pageQueryDAO.query("CARDHOLDER.selectCardholdersByCheckState",
					cardholderQueryDTO);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BizServiceException("查询持卡人信息失败！");
		}
	}
	
	/**
	 * 修改更新持卡人审核状态
	 */
	public void checkUpdate(CardholderDTO cardholderDTO) throws BizServiceException {
		try {
			Cardholder cardholder = new Cardholder();
			ReflectionUtil.copyProperties(cardholderDTO, cardholder);
			baseDAO.update("TB_CARDHOLDER.abatorgenerated_checkUpdateByPrimaryKeySelective", cardholder);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BizServiceException("审核持卡人信息失败！");
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
			PageDataDTO pageDataDTO =pageQueryDAO.query("TB_SELL_ORDER_CARD_LIST.selectCardNoInfoByCardholderId",
					cardholderDTO.getSellOrderCardListQueryDTO());
			cardholderDTO.setCardholderCardList(pageDataDTO);
			/*List<Map<String, String>> lstCardNo = cardholderDAO
					.selectCardNoByCardholderId(cardholderDTO.getCardholderId());
			if (null != lstCardNo && lstCardNo.size() > 0) {
				*//** 处理卡号列表，以逗号分隔 *//*
				String cardNoString = lstCardNo.toString().replace("[", "")
						.replace("]", "").replace("{", "").replace("}", "")
						.replace(" ", "").replace("cardNo=", "");
				logger.debug("cardholderId:" + cardholderDTO.getCardholderId()
						+ "find out card:" + cardNoString);
				*//** 持卡人卡信息报文 *//*
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
					*//** 报文返回状态为成功 *//*
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
			}*/
			//读取图片流
			/*if("1".equals(cardholderDTO.getIdType())&&StringUtils.isNotEmpty(cardholderDTO.getImgfPath())){
				try{
					List<IdCardInfoDTO> cardInfo = cardholderDAO.selectImgInfo(cardholderDTO.getIdNo());
					if(cardInfo.size()!=0){
						IdCardInfoDTO IidCardInfoDTO=cardInfo.get(0);
						TransmitToFileForWebservice  webservice=new TransmitToFileForWebservice();
						String message = "<?xml version='1.0' encoding='UTF-8' ?><DATA><TXN_CODE>I002</TXN_CODE>"
								+ "<PATHF>"+IidCardInfoDTO.getImgfPath()+"</PATHF>"
								+ "<PATHB>"+IidCardInfoDTO.getImgbPath()+"</PATHB>"
								+"<IMGTYPE>01</IMGTYPE>"
								+ "<IDNO>"+IidCardInfoDTO.getIdNo()+"</IDNO></DATA>";
						String result=webservice.toFileService(message);
						if(StringUtils.isNotEmpty(result)){
							XmlConverter imgInfo=(XmlConverter) XmlConverter.parseXml(result);
							logger.info("图片解析应答码："+imgInfo.getRESPCODE()+"，应答描述："+imgInfo.getMARK());
							if("0000".equals(imgInfo.getRESPCODE())){
								//正面图片
								cardholderDTO.setImgfPath("data:image/png;base64,"+imgInfo.getIMGF());
								//背面
								cardholderDTO.setImgbPath("data:image/png;base64,"+imgInfo.getIMGB());
							}
							
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
					this.logger.error(e.getMessage()+"获取证件照失败");
				}
				
			}*/
			AttachInfoDTO attachInfo = new AttachInfoDTO();
                        attachInfo.setPeopleNo(cardholderDTO.getCardholderId());
                        attachInfo.setPeopleType("01");
                        attachInfo.setEntityId(cardholderDTO.getDefaultEntityId());
                        attachInfo.setDataStat("1");
                        List<AttachInfoDTO> attachInfos = attachDAO.getAttachInfos(attachInfo);
                        if(attachInfos!=null && attachInfos.size()!=0){
                                AttachInfoDTO attach = attachInfos.get(0);
                                cardholderDTO.setCountry(attach.getCountry());
                                cardholderDTO.setCity(attach.getCity());
                                cardholderDTO.setValidity(attach.getValidity());
                                cardholderDTO.setIndustry(attach.getIndustry());
                                cardholderDTO.setProfession(attach.getProfession());
                                cardholderDTO.setCardholderEducation(attach.getEducation());
                                cardholderDTO.setCardholderEmail(attach.getEmail());
                                cardholderDTO.setCardholderMarriage(attach.getMarriage());
                                cardholderDTO.setCardholderNation(attach.getNation());
                                cardholderDTO.setIsblacklist(attach.getIsblacklist());
                                cardholderDTO.setRiskGrade(attach.getRiskGrade());
                        }               						
			return cardholderDTO;
		} catch (BizServiceException ex) {
			throw ex;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BizServiceException("查看个人持卡人信息失败！");
		}
	}
	
	public IdCardInfoDTO getIdImageInfo(String  idNo)
			throws BizServiceException{
		try{
			IdCardInfoDTO idCardInfoDTO=new IdCardInfoDTO();
			List<IdCardInfoDTO> cardInfo = cardholderDAO.selectImgInfo(idNo);
			if(cardInfo.size()!=0){
				IdCardInfoDTO IidCardInfoDTO=cardInfo.get(0);
				TransmitToFileForWebservice  webservice=new TransmitToFileForWebservice();
				String message = "<?xml version='1.0' encoding='UTF-8' ?><DATA><TXN_CODE>I002</TXN_CODE>"
						+ "<PATHF>"+IidCardInfoDTO.getImgfPath()+"</PATHF>"
						+ "<PATHB>"+IidCardInfoDTO.getImgbPath()+"</PATHB>"
						+"<IMGTYPE>01</IMGTYPE>"
						+ "<IDNO>"+IidCardInfoDTO.getIdNo()+"</IDNO></DATA>";
				String result=webservice.toFileService(message);
				if(StringUtils.isNotEmpty(result)){
					XmlConverter imgInfo=(XmlConverter) XmlConverter.parseXml(result);
					logger.info("图片解析应答码："+imgInfo.getRESPCODE()+"，应答描述："+imgInfo.getMARK());
					if("0000".equals(imgInfo.getRESPCODE())){
						
						//正面图片
						idCardInfoDTO.setImgfPath("data:image/png;base64,"+imgInfo.getIMGF());
						//背面
						idCardInfoDTO.setImgbPath("data:image/png;base64,"+imgInfo.getIMGB());
					}
					
				}
			}
			return idCardInfoDTO;
		} catch (Exception e) {
			// TODO: handle exception
			this.logger.error(e.getMessage()+"获取证件照失败");
		}
		
		
		
		return null;
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
			//cardholder.setCheckState("0");//0审核中   1已审核    2未审核   3审核未通过

			cardholderDAO.insert(cardholder);
			
			AttachInfoDTO  attachInfo =  new AttachInfoDTO();
			attachInfo.setCity(cardholderDTO.getCity());
			attachInfo.setCountry(cardholderDTO.getCountry());
			attachInfo.setEntityId(cardholderDTO.getDefaultEntityId());
			attachInfo.setIndustry(cardholderDTO.getIndustry());
			attachInfo.setProfession(cardholderDTO.getProfession());
			attachInfo.setValidity(cardholderDTO.getValidity());
			attachInfo.setUpdateDate(DateUtil.getCurrentTime());
			attachInfo.setPeopleNo(cardholder.getCardholderId());
			attachInfo.setPeopleType("01");
			attachInfo.setDataStat(DataBaseConstant.DATA_STATE_NORMAL);
			attachDAO.insertAttachInfo(attachInfo);
			
			
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
			//cardholder.setCheckState("0");

			cardholderDAO.insert(cardholder);
			ReflectionUtil.copyProperties(cardholder, cardholderDTO);

			AttachInfoDTO  attachInfo =  new AttachInfoDTO();
			attachInfo.setCity(cardholderDTO.getCity());
			attachInfo.setCountry(cardholderDTO.getCountry());
			attachInfo.setEntityId(cardholderDTO.getDefaultEntityId());
			attachInfo.setIndustry(cardholderDTO.getIndustry());
			attachInfo.setProfession(cardholderDTO.getProfession());
			attachInfo.setValidity(cardholderDTO.getValidity());
			attachInfo.setUpdateDate(DateUtil.getCurrentTime());
			attachInfo.setPeopleNo(cardholder.getCardholderId());
			attachInfo.setPeopleType("01");
			attachInfo.setDataStat(DataBaseConstant.DATA_STATE_NORMAL);
			attachDAO.insertAttachInfo(attachInfo);
			
			cardholderDTO.setCountry(attachInfo.getCountry());
			cardholderDTO.setCity(attachInfo.getCity());
			cardholderDTO.setValidity(attachInfo.getValidity());
			cardholderDTO.setIndustry(attachInfo.getIndustry());
			cardholderDTO.setProfession(attachInfo.getProfession());
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
	 * 现改为工号(externalId)+身份证 唯一
	 */
	private int checkRepeat(CardholderDTO cardholderDTO) throws Exception {
		CardholderExample example = new CardholderExample();

		Criteria criteria = example.createCriteria().andEntityIdEqualTo(cardholderDTO.getEntityId())
		        .andIdNoEqualTo(cardholderDTO.getIdNo()).andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
		if (null != cardholderDTO.getExternalId()&& !"".equals(cardholderDTO.getExternalId())) {
			criteria.andExternalIdEqualTo(cardholderDTO.getExternalId());
		}
		// 更新时
		if (null != cardholderDTO.getCardholderId()) {
			criteria.andCardholderIdNotEqualTo(cardholderDTO.getCardholderId());
		}
		return cardholderDAO.countByExample(example);
	}

	/**
	 * 修改更新个人持卡人
	 */
	public void update(CardholderDTO cardholderDTO) throws BizServiceException {
		try {
			if (checkRepeat(cardholderDTO) > 0) {
				throw new BizServiceException("持卡人工号和持卡人身份证不能和已有持卡人重复！");
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
			//cardholder.setCheckState("0");

			cardholderDAO.updateByPrimaryKeySelective(cardholder);
			
			AttachInfoDTO  attachInfo =  new AttachInfoDTO();
                        attachInfo.setCity(cardholderDTO.getCity());
                        attachInfo.setCountry(cardholderDTO.getCountry());
                        attachInfo.setEntityId(cardholderDTO.getDefaultEntityId());
                        attachInfo.setIndustry(cardholderDTO.getIndustry());
                        attachInfo.setProfession(cardholderDTO.getProfession());
                        attachInfo.setValidity(cardholderDTO.getValidity());
                        attachInfo.setUpdateDate(DateUtil.getCurrentTime());
                        attachInfo.setPeopleNo(cardholder.getCardholderId());
                        attachInfo.setEducation(cardholderDTO.getCardholderEducation());
                        attachInfo.setNation(cardholderDTO.getCardholderNation());
                        attachInfo.setEmail(cardholderDTO.getCardholderEmail());
                        attachInfo.setMarriage(cardholderDTO.getCardholderMarriage());
                        attachInfo.setIsblacklist(cardholderDTO.getIsblacklist());
                        attachInfo.setRiskGrade(cardholderDTO.getRiskGrade());
                        attachInfo.setPeopleType("01");
                        attachInfo.setDataStat(DataBaseConstant.DATA_STATE_NORMAL);
			List<AttachInfoDTO> attachInfoDTOList=attachDAO.getAttachInfos(attachInfo);
			if(attachInfoDTOList==null||attachInfoDTOList.size()==0){
				attachDAO.insertAttachInfo(attachInfo);
			}
			else{
				attachDAO.updateAttachInfo(attachInfo);
			}
		} catch (BizServiceException be) {
			throw be;
		} catch (Exception e) {
		        e.printStackTrace();
			logger.error(e.getMessage());
			throw new BizServiceException("更新个人持卡人信息失败！");
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
	 * 删除个人持卡人
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
			int row = commonsDAO.batchDelete("TB_CARDHOLDER.abatorgenerated_updateByPrimaryKeySelective",cardholders);
			if(row == 0){
			    throw new BizServiceException("删除持卡人信息失败！");
			}
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
			String mailingAddress = cardholderDTO.getMailingAddress();
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
			List<Cardholder> cardholderListToUpdate = new ArrayList<Cardholder>();
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
				//cardholder.setCheckState("0");
				cardholder.setMailingAddress(mailingAddress);
				List<CardholderDTO> list=cardholderDAO.selectByCardholderIdNo(dto);
				if(list.size()>0){	
					cardholder.setCardholderId(list.get(0).getCardholderId());
					cardholderDAO.updateByPrimaryKeySelective(cardholder);
				}else{
					cardholderDAO.insert(cardholder);
				}
			}
//			if(cardholderListToUpdate.size()!=0){
//				commonsDAO.batchUpdate("TB_CARDHOLDER.abatorgenerated_updateByPrimaryKeySelective", cardholderListToUpdate);
//			}
//			if(cardholderList.size()!=0){
//				commonsDAO.batchInsert("TB_CARDHOLDER.abatorgenerated_insert",
//						cardholderList);
//			}
			

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
			String mailingAddress = cardholderDTO.getMailingAddress();
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
				//cardholder.setCheckState("0");
				cardholder.setMailingAddress(mailingAddress);
				// cardholderList.add(cardholder);
				List<CardholderDTO> list=cardholderDAO.selectByCardholderIdNo(dto);
				if(list.size()>0){
					cardholder.setCardholderId(list.get(0).getCardholderId());
					cardholderDAO.updateByPrimaryKeySelective(cardholder);
				}else{
					cardholderDAO.insert(cardholder);
				}
				
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

	// 根据持卡人身份证查询持卡人
	public List<CardholderDTO> selectByCardholderIdNo(
			CardholderDTO cardholderDTO) throws BizServiceException {
		List<CardholderDTO> cardholders = cardholderDAO
				.selectByCardholderIdNo(cardholderDTO);
		return cardholders;
	}
		
	/**
	 * 查其他持卡人是否有在用此身份证号
	 * 
	 * @param cardholderDTO
	 * @return
	 * @throws BizServiceException
	 */
	public List<CardholderDTO> checkOtherIdNo(CardholderDTO cardholderDTO)
			throws BizServiceException {
		List<CardholderDTO> cardholders = cardholderDAO
				.selectOtherHolderByIdNo(cardholderDTO);
		return cardholders;
	}
	
	
        /***********************************新增区域******************************************/
	/**
         *  查询持卡人列表
         */
        public PageDataDTO inqueryShow(CardholderQueryDTO cardholderQueryDTO)
                        throws BizServiceException {
                try {
                        return pageQueryDAO.query("CARDHOLDER.selectCardholdersByCardholderDTO",
                                        cardholderQueryDTO);
                } catch (Exception e) {
                        logger.error(e.getMessage());
                        throw new BizServiceException("查询持卡人信息失败！");
                }
        }
        
        /**
         * 个人持卡人添加
         */
        public CardholderDTO saveCardholder(CardholderDTO cardholderDTO) throws BizServiceException {
            // 同一客户下持卡人的 工号+姓名 唯一
            try {
                    if (checkRepeat(cardholderDTO) > 0) {
                            throw new BizServiceException("客户："
                                            + cardholderDTO.getEntityId() + "下该持卡人已存在！");
                    }                                  
                    CardholderVerify cardholderVerify = new CardholderVerify();
                    ReflectionUtil.copyProperties(cardholderDTO, cardholderVerify);
                    cardholderVerify.setCardholderId(commonsDAO
                                    .getNextValueOfSequence("TB_CARDHOLDER"));//持卡人编号
                    cardholderVerify.setCountyr(cardholderDTO.getCountry());//国籍
                    cardholderVerify.setVid(cardholderDTO.getV_Id());//车架号
                    cardholderVerify.setCreateUser(cardholderDTO.getLoginUserId());//创建用户
                    cardholderVerify.setCreateTime(DateUtil.getCurrentTime());//创建时间
                    cardholderVerify.setModifyUser(cardholderDTO.getLoginUserId());//更新用户
                    cardholderVerify.setModifyTime(DateUtil.getCurrentTime());//更新时间
                    cardholderVerify.setDataState(DataBaseConstant.DATA_STATE_NORMAL);//数据状态
                    cardholderVerify.setCheckState("2"); //0审核中   1已审核    2未审核   3审核未通过
                    cardholderVerify.setIsblacklist("0");//黑名单标识
                    cardholderVerify.setRiskGrade("O");//风险等级
                    cardholderVerifyDAO.insert(cardholderVerify);    
                    
                    ReflectionUtil.copyProperties(cardholderVerify,cardholderDTO);
                    return cardholderDTO;
            } catch (BizServiceException be) {
                    throw be;
            } catch (Exception e) {
                    e.printStackTrace();
                    logger.error(e.getMessage());
                    throw new BizServiceException("添加个人持卡人信息失败！");
            }
                
        }
        
        /**
         * 企业持卡人信息添加
         */
        public CompanyInfoDTO insertcustomer(CompanyInfoDTO companyInfoDTO) throws BizServiceException {
                try {
                        CompanyInfo companyInfo = new CompanyInfo();
                        ReflectionUtil.copyProperties(companyInfoDTO, companyInfo);
                        companyInfo.setRelationNo(commonsDAO
                                        .getNextValueOfSequence("TB_CARDHOLDER"));//企业持卡人编号
                        companyInfo.setRelationType("01");//类型 00:客户     01:持卡人
                        companyInfo.setCreateUser(companyInfoDTO.getLoginUserId()); //创建用户
                        companyInfo.setCreateTime(DateUtil.getCurrentTime()); //创建时间
                        companyInfo.setModifyUser(companyInfoDTO.getLoginUserId()); //修改用户
                        companyInfo.setModifyTime(DateUtil.getCurrentTime()); //修改时间
                        companyInfo.setDataState(DataBaseConstant.DATA_STATE_NORMAL); //数据状态
                        companyInfo.setVerifyStat("2"); //审核状态 ：0审核中 ,  1已审核  ,  2未审核 ,  3审核未通过
                        companyInfo.setCusState("1"); //客户状态：1有效, 0无效
                        companyInfo.setIsblacklist("0");//黑名单标识
                        companyInfo.setRiskGrade("O");//风险等级
                        companyInfo.setCorpAliasName("@n");//法人别名
                        companyInfo.setCorpProfession("@n");//法人职业
                        companyInfoDAO.insert(companyInfo);       
                        
                        ReflectionUtil.copyProperties(companyInfo,companyInfoDTO);
                        return companyInfoDTO;
                } catch (Exception e) {
                        e.printStackTrace();
                        logger.error(e.getMessage());
                        throw new BizServiceException("添加企业持卡人信息失败！");
                }
        }
        
        /**
         * 根据持卡人身份证查询企业持卡人
         */
        public List<CompanyInfoDTO> selectByCusCardholderIdNo(CompanyInfoDTO companyInfoDTO)
                throws BizServiceException {
               List<CompanyInfoDTO> cardholders = companyInfoDAO.selectByCusCardholderIdNo(companyInfoDTO);
               return cardholders; 
        } 
        
        /**
         * 查其他持卡人是否有在用此身份证号
         */
        public List<CompanyInfoDTO> checkOtherCusIdNo(CompanyInfoDTO companyInfoDTO) throws BizServiceException {
            List<CompanyInfoDTO> cardholders = companyInfoDAO.selectOtherCusCardholderByIdNo(companyInfoDTO);
            return cardholders;
        }
        
        /**
         * 查看企业持卡人信息
         */
        public CompanyInfoDTO cusView(CompanyInfoDTO companyInfoDTO) throws BizServiceException {
            try {
                CompanyInfoKey key=new CompanyInfoKey();
                key.setRelationNo(companyInfoDTO.getRelationNo());
                key.setRelationType("01");//类型 00:客户     01:持卡人
                CompanyInfo companyInfo = companyInfoDAO.selectByPrimaryKey(key);
                ReflectionUtil.copyProperties(companyInfo, companyInfoDTO);
                //关联所属客户信息
                CustomerDTO customerDTO = new CustomerDTO();
                customerDTO.setEntityId(companyInfo.getEntityId());
                companyInfoDTO.setCustomerDTO(customerService.viewCustomer(customerDTO));                
                /** 关联持卡人持有卡信息 */
                PageDataDTO pageDataDTO =pageQueryDAO.query("TB_SELL_ORDER_CARD_LIST.selectCardNoInfoByCardholderId",
                        companyInfoDTO.getSellOrderCardListQueryDTO());
                companyInfoDTO.setCardholderCardList(pageDataDTO); 
                return companyInfoDTO;
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(e.getMessage());
                throw new BizServiceException("查看企业持卡人信息失败！");
            }
        }
        
        
        /**
         * 编辑企业持卡人信息
         */
        public void updateCus(CompanyInfoDTO companyInfoDTO) throws BizServiceException {
            try {
                CompanyInfo companyInfo = new CompanyInfo();
                ReflectionUtil.copyProperties(companyInfoDTO, companyInfo);
                //更新企业持卡人审核表
                companyInfo.setCreateUser(companyInfoDTO.getLoginUserId());//创建用户
                companyInfo.setModifyUser(companyInfoDTO.getLoginUserId());//更新用户
                companyInfo.setCreateTime(DateUtil.getCurrentTime());//创建时间
                companyInfo.setModifyTime(DateUtil.getCurrentTime());//更新时间
                companyInfo.setRelationType("01");//类型 00:客户     01:持卡人
                companyInfo.setDataState(DataBaseConstant.DATA_STATE_NORMAL); //数据状态
                companyInfoDAO.updateByPrimaryKeySelective(companyInfo);
            } catch (Exception e) {
                logger.error(e.getMessage());
                throw new BizServiceException("更新持卡人信息失败！");
            }           
        }
        
        
        /**
         * 查看个人持卡人审核信息
         * @param cardholderDTO
         * @return
         * @throws BizServiceException
         */
        public CardholderDTO checkPerView(CardholderDTO cardholderDTO)throws BizServiceException {
            try {
                CardholderVerify cardholder = cardholderVerifyDAO.selectByPrimaryKey(cardholderDTO.getCardholderId());
                ReflectionUtil.copyProperties(cardholder, cardholderDTO);
                // 关联所属客户信息
                CustomerDTO customerDTO = new CustomerDTO();
                customerDTO.setEntityId(cardholder.getEntityId());
                cardholderDTO.setCustomerDTO(customerService
                                .viewCustomer(customerDTO));
                /** 关联持卡人持有卡信息 */
                PageDataDTO pageDataDTO =pageQueryDAO.query("TB_SELL_ORDER_CARD_LIST.selectCardNoInfoByCardholderId",
                                cardholderDTO.getSellOrderCardListQueryDTO());
                cardholderDTO.setCardholderCardList(pageDataDTO);
                cardholderDTO.setV_Id(cardholder.getVid());//车架号
                cardholderDTO.setCountry(cardholder.getCountyr());//国家                                                
                return cardholderDTO;
            } catch (BizServiceException ex) {
                    throw ex;
            } catch (Exception e) {
                    e.printStackTrace();
                    logger.error(e.getMessage());
                    throw new BizServiceException("查看个人持卡人审核信息失败！");
            }
        } 
        
        
        /**
         * 查看企业持卡人审核信息
         * @param companyInfoDTO
         * @return
         * @throws BizServiceException
         */
        public CompanyInfoDTO checkCusView(CompanyInfoDTO companyInfoDTO)throws BizServiceException {
            try {
                CompanyInfoKey key=new CompanyInfoKey();
                key.setRelationNo(companyInfoDTO.getRelationNo());
                key.setRelationType("01");//类型 00:客户     01:持卡人
                CompanyInfo companyInfo = companyInfoDAO.selectByPrimaryKey(key);
                ReflectionUtil.copyProperties(companyInfo, companyInfoDTO);
                //关联所属客户信息
                CustomerDTO customerDTO = new CustomerDTO();
                customerDTO.setEntityId(companyInfo.getEntityId());
                companyInfoDTO.setCustomerDTO(customerService.viewCustomer(customerDTO));                
                /** 关联持卡人持有卡信息 */
                PageDataDTO pageDataDTO =pageQueryDAO.query("TB_SELL_ORDER_CARD_LIST.selectCardNoInfoByCardholderId",
                        companyInfoDTO.getSellOrderCardListQueryDTO());
                companyInfoDTO.setCardholderCardList(pageDataDTO);
            } catch (Exception e) {
                    e.printStackTrace();
                    logger.error(e.getMessage());
                    throw new BizServiceException("查看企业持卡人审核信息失败！");
            }           
            return companyInfoDTO;
        }
       
        
        /**
         * 更新个人持卡人审核信息
         */
        public void checkPerUpdate(CardholderDTO cardholderDTO) throws BizServiceException {
            try {
                CardholderVerify cardholderVerify = new CardholderVerify();
                ReflectionUtil.copyProperties(cardholderDTO, cardholderVerify);                
                //判断审核状态，如果已审核，开始生成账户并进行绑定 1表示已审核 ,2表示未审核，更新
                if("1".equals(cardholderVerify.getCheckState())){
                    //根据审核状态更新持卡人审核表
                    baseDAO.update("TB_CARDHOLDER_VERIFY.ibatorgenerated_checkUpdateByPrimaryKeySelective", cardholderVerify);
                    //根据审核状态查询已审核的数据
                    CardholderVerify cardholderVerify1 = cardholderVerifyDAO.selectByPrimaryKey(cardholderVerify.getCardholderId());
                    //持卡人model
                    Cardholder cardholder = new Cardholder();
                    cardholder.setV_Id(cardholderVerify1.getVid());//车架号
                    ReflectionUtil.copyProperties(cardholderVerify1, cardholder);               
                    //将审核后的数据添加到持卡人表中
                    cardholderDAO.insert(cardholder);
                    
                    AttachInfoDTO  attachInfo =  new AttachInfoDTO();
                    attachInfo.setCity(cardholderVerify1.getCity());
                    attachInfo.setCountry(cardholderVerify1.getCountyr());
                    attachInfo.setEntityId(cardholderDTO.getDefaultEntityId());
                    attachInfo.setIndustry(cardholderVerify1.getIndustry());
                    attachInfo.setProfession(cardholderVerify1.getProfession());
                    attachInfo.setValidity(cardholderVerify1.getValidity());
                    attachInfo.setUpdateDate(DateUtil.getCurrentTime());
                    attachInfo.setPeopleNo(cardholder.getCardholderId());
                    attachInfo.setPeopleType("01");
                    attachInfo.setDataStat(DataBaseConstant.DATA_STATE_NORMAL);
                    attachInfo.setNation(cardholderVerify1.getCardholderNation());//民族
                    attachInfo.setMarriage(cardholderVerify1.getCardholderMarriage());//婚姻状况
                    attachInfo.setEmail(cardholderVerify1.getCardholderEmail());//邮箱
                    attachInfo.setEducation(cardholderVerify1.getCardholderEducation());//学历
                    attachInfo.setIsblacklist(cardholderVerify1.getIsblacklist());//黑名单
                    attachInfo.setRiskGrade(cardholderVerify1.getRiskGrade());//风险等级
                    List<AttachInfoDTO> attachInfos = attachDAO.getAttachInfos(attachInfo);
                    if (null!=attachInfos&&attachInfos.size()>0){
                         attachDAO.updateAttachInfo(attachInfo);
                    }else{
                        attachDAO.insertAttachInfo(attachInfo);
                      }
                    
                    cardholderVerify1.setDataState(DataBaseConstant.DATA_STATE_DELETE);
                    CardholderVerifyExample example = new CardholderVerifyExample();
                    example.createCriteria().andCardholderIdEqualTo(cardholderVerify1.getCardholderId());
                    cardholderVerifyDAO.updateByExampleSelective(cardholderVerify1, example);
                                       
                    /*//同步到CRM
                    ReflectionUtil.copyProperties(cardholderVerify1, cardholderDTO);
                    cardholderDTO.setCountry(cardholderVerify1.getCountyr());//国家
                    cardholderSynchCRM.syncToPerCRM(cardholderDTO);*/
                }else{
                    //更新持卡人信息(资料更新)审核状态为2时更新，为3时，审核不通过
                    baseDAO.update("TB_CARDHOLDER_VERIFY.ibatorgenerated_checkUpdateByPrimaryKeySelective", cardholderVerify);
                }           
            }catch (Exception e) {
                e.printStackTrace();
                logger.error(e.getMessage());
                throw new BizServiceException("更新个人持卡人审核信息失败！");
            }           
        }
        
        
        /**
         * 更新企业持卡人审核信息
         */
        public void checkCusUpdate(CompanyInfoDTO companyInfoDTO) throws BizServiceException {
            try {
                CompanyInfo companyInfo = new CompanyInfo();
                ReflectionUtil.copyProperties(companyInfoDTO, companyInfo);
                //判断审核状态，如果已审核，开始生成账户并进行绑定 1表示已审核 ,2表示未审核，更新
                if("1".equals(companyInfo.getVerifyStat())){
                    //更新企业持卡人审核表
                    companyInfo.setCreateUser(companyInfoDTO.getLoginUserId());//创建用户
                    companyInfo.setModifyUser(companyInfoDTO.getLoginUserId());//更新用户
                    companyInfo.setCreateTime(DateUtil.getCurrentTime());//创建时间
                    companyInfo.setModifyTime(DateUtil.getCurrentTime());//更新时间
                    companyInfo.setRelationType("01");//类型 00:客户     01:持卡人
                    companyInfo.setDataState(DataBaseConstant.DATA_STATE_NORMAL); //数据状态
                    companyInfoDAO.updateByPrimaryKeySelective(companyInfo);
                    
                    /*CompanyInfoKey key=new CompanyInfoKey();
                    key.setRelationNo(companyInfo.getRelationNo());
                    key.setRelationType(companyInfo.getRelationType());//类型 00:客户     01:持卡人
                    companyInfo = companyInfoDAO.selectByPrimaryKey(key);
                    ReflectionUtil.copyProperties(companyInfo, companyInfoDTO);
                    //同步到CRM
                    cardholderSynchCRM.syncToCusCRM(companyInfoDTO);*/
                }else{
                    //判断审核状态，如果已审核，开始生成账户并进行绑定 1表示已审核 ,2表示更新，3表示审核未通过 
                    companyInfo.setRelationType("01");//类型 00:客户     01:持卡人
                    companyInfoDAO.updateByPrimaryKeySelective(companyInfo);
                }                
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(e.getMessage());
                throw new BizServiceException("更新企业持卡人审核信息失败！");
            }            
        }
        
        
        /**
         * 根据企业证件类型好证件号判断企业是否重复
         * @return
         * @throws BizServiceException 
         */
        @SuppressWarnings("unchecked")
        public void  checkLicense(CompanyInfoDTO companyInfoDTO) throws BizServiceException{
                CompanyInfoExample example = new CompanyInfoExample();
                example.createCriteria().andCompanyIdTypeEqualTo(companyInfoDTO.getCompanyIdType())
                    .andCompanyIdEqualTo(companyInfoDTO.getCompanyId()).andRelationTypeEqualTo("01")
                    .andVerifyStatNotEqualTo("3").andDataStateEqualTo("1");
                List<CompanyInfo> list=companyInfoDAO.selectByExample(example);
                if(list!=null&&list.size()>0) {
                        throw new BizServiceException("已存在相同证件类型的公司!");
                }
        }
        
        
        /**
         * 删除企业持卡人
         */
        public void deleteCus(CompanyInfoDTO companyInfoDTO) throws BizServiceException {
            try {
                String[] cardholderIds = companyInfoDTO.getCardholderIds();
                List<CompanyInfo> cardholders = new ArrayList<CompanyInfo>();
                for (String cardholderId : cardholderIds) {
                    CompanyInfo companyInfo = new CompanyInfo();
                    companyInfo.setRelationNo(cardholderId);//持卡人编号
                    companyInfo.setRelationType("01");//00表示购卡人，01表示持卡人
                    companyInfo.setModifyUser(companyInfoDTO.getLoginUserId());
                    companyInfo.setModifyTime(DateUtil.getCurrentTime());
                    companyInfo.setDataState(DataBaseConstant.DATA_STATE_DELETE);
                    cardholders.add(companyInfo);
                }
                int row = commonsDAO.batchDelete("TB_COMPANY_INFO.ibatorgenerated_updateByPrimaryKeySelective",cardholders);
                if(row == 0){
                    throw new BizServiceException("删除持卡人信息失败！"); 
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
                throw new BizServiceException("删除持卡人信息失败！");
            }
        }
        
        /**
         * 查询客户信息
         */
        public PageDataDTO query(CardholderQueryDTO cardholderQueryDTO) throws BizServiceException {
            try {
                return pageQueryDAO.query("CARDHOLDER.selectCustomerByIdNo",cardholderQueryDTO);
            } catch (Exception e) {
                logger.error(e.getMessage());
                throw new BizServiceException("查询客户信息失败！");        
            }
        }
        
        
        
        /**
    	 * 持卡人补录信息审核查询
    	 */
    	public PageDataDTO informationAudit(PictureInfoQueryDTO pictureInfoQueryDTO)
    			throws BizServiceException{
    		try {
    			return pageQueryDAO.query("CARDHOLDER.selectInformationAudit",
    					pictureInfoQueryDTO);
    		} catch (Exception e) {
    			logger.error(e.getMessage());
    			throw new BizServiceException("查询持卡人补录信息审核失败！");
    		}
    	}
    	/**
    	 * 补录信息更新审核状态
    	 * 先将卡号解锁，然后更改审核状态，
    	 * @param pictureInfoQueryDTO
    	 * @throws BizServiceException
    	 */
    	public void auditCheck(CheckPeopleInfoDTO checkPeopleInfoDTO) throws BizServiceException {
    			String idNo=checkPeopleInfoDTO.getPeopleNo();
    			AccPackageDTO accPackageDTO = new AccPackageDTO();
    				//通过证件号查询对应的图片是否存在
    				PictrueInfoExample infoExample = new PictrueInfoExample();
    				infoExample.createCriteria().andIdNoEqualTo(idNo);
    				List<PictrueInfo> infoList=pictrueInfoDAO.selectByExample(infoExample);
    				if(infoList.size()==0||infoList.isEmpty()) {
    					throw new BizServiceException("证件图片不存在不允许审核");
    				}
    				//1审核通过 2 审核不通过
    				String auditState=checkPeopleInfoDTO.getCheckState();
    				Map<String, Object> map = new HashMap<String, Object>();
    				map.put("idNo", idNo);
    				map.put("auditState",auditState);
    				map.put("updateTime",DateUtil.getCurrentTime());
    				if(auditState.equals("2")) {
    					checkPeopleInfoDAO.updateByIdNo(map);
    					//对审核不通过的客户他在核心系统中上传的照片信息
    					PictrueInfoExample example = new PictrueInfoExample();
    					example.createCriteria().andIdNoEqualTo(idNo);
    					pictrueInfoDAO.deleteByExample(example);
    				}else{
    					CheckPeopleInfoExample queryExample = new CheckPeopleInfoExample();
        				queryExample.createCriteria().andPeopleNoEqualTo(idNo).andDataStateEqualTo("1");
        				List<CheckPeopleInfo> peopleInfoList = checkPeopleInfoDAO.selectByExample(queryExample);
        				CheckPeopleInfo peopleInfoModel=null;
        				for (int i = 0; i < peopleInfoList.size(); i++) {
        					peopleInfoModel=peopleInfoList.get(0);
        				}
        				
        					accPackageDTO.setTxnCode("S450");
        					accPackageDTO.setCardNo(peopleInfoModel.getCardNo());
        					accPackageDTO.setServiceFee(Amount.getDataBaseAmount("0"));
        					try {
    							accPackageDTO = java2ACCBusinessService.cardOperate(accPackageDTO);
    						} catch (Exception e) {
    							this.logger.error(e.getMessage());
    							throw new BizServiceException("卡解锁失败！");
    						}
        					if(!"00".equals(accPackageDTO.getRespCode())){
        						this.logger.error(accPackageDTO.getRespCode());
        						throw new BizServiceException("卡片操作失败");
        					}
        				
        				checkPeopleInfoDAO.updateByIdNo(map);
    				}
    				
    				
    		
    	}
        
        
        
        
        
        
        
        /***********************************新增区域******************************************/
        private CardholderVerifyDAO cardholderVerifyDAO;
        private CompanyInfoDAO companyInfoDAO;
        private CardholderSynchCRM  cardholderSynchCRM;
        
        
        public CardholderSynchCRM getCardholderSynchCRM() {
            return cardholderSynchCRM;
        }

        public void setCardholderSynchCRM(CardholderSynchCRM cardholderSynchCRM) {
            this.cardholderSynchCRM = cardholderSynchCRM;
        }

        public CompanyInfoDAO getCompanyInfoDAO() {
            return companyInfoDAO;
        }

        public void setCompanyInfoDAO(CompanyInfoDAO companyInfoDAO) {
            this.companyInfoDAO = companyInfoDAO;
        }

        public CardholderVerifyDAO getCardholderVerifyDAO() {
            return cardholderVerifyDAO;
        }

        public void setCardholderVerifyDAO(CardholderVerifyDAO cardholderVerifyDAO) {
            this.cardholderVerifyDAO = cardholderVerifyDAO;
        }

              
}

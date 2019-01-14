package com.huateng.univer.consumer.merchant.biz.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.consumer.merchant.dto.MerchantDTO;
import com.allinfinance.univer.consumer.merchant.dto.MerchantQueryDTO;
import com.allinfinance.univer.consumer.merchant.dto.MerchantTxnQueryDTO;
import com.allinfinance.univer.consumer.shop.dto.ShopDTO;
import com.allinfinance.univer.consumercontract.dto.ConsumerContractDTO;
import com.allinfinance.univer.entity.dto.ContactDTO;
import com.allinfinance.univer.system.user.dto.UserDTO;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.dao.BaseDAO;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.dao.PageQueryDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.ConsumerContractDAO;
import com.huateng.framework.ibatis.model.ConsumerContract;
import com.huateng.framework.ibatis.model.ConsumerContractExample;
import com.huateng.framework.ibatis.model.Merchant;
import com.huateng.framework.ibatis.model.MerchantExample;
import com.huateng.framework.ibatis.model.MerchantKey;
import com.huateng.framework.ibatis.model.Shop;
import com.huateng.framework.ibatis.model.ShopExample;
import com.huateng.framework.ibatis.model.User;
import com.huateng.framework.ibatis.model.UserExample;
import com.huateng.framework.ibatis.model.UserExample.Criteria;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.ReflectionUtil;
import com.huateng.hstserver.constants.RspCodeMap;
import com.huateng.hstserver.frameworkUtil.Const;
import com.huateng.hstserver.gatewayService.Java2TXNBusinessServiceImpl;
import com.huateng.hstserver.model.TxnPackageDTO;
import com.huateng.univer.consumer.acctypecontract.biz.service.AccTypeContractService;
import com.huateng.univer.consumer.mchntcontract.biz.service.MchntContractService;
import com.huateng.univer.consumer.merchant.biz.service.MerchantService;
import com.huateng.univer.consumer.merchant.integration.dao.MerchantServiceDAO;
import com.huateng.univer.consumer.merchantacctypecontract.biz.service.MchantAccTypeContractService;
import com.huateng.univer.consumer.shop.integration.dao.ShopServiceDAO;
import com.huateng.univer.entitybaseinfo.bank.biz.service.BankService;
import com.huateng.univer.entitybaseinfo.contact.biz.service.ContactService;
import com.huateng.univer.system.sysparam.biz.service.EntitySystemParameterService;
import com.huateng.univer.system.user.integration.dao.UserServiceDAO;

/**
 * 商户管理服务实现
 * 
 * @author zengfenghua
 * @since 1.0
 */
public class MerchantServiceImpl implements MerchantService {

	/**
	 * Log操作
	 */
	Logger logger = Logger.getLogger(MerchantServiceImpl.class);

	/** 分页DAO */
	private PageQueryDAO pageQueryDAO;

	/** 商户服务DAO */
	private MerchantServiceDAO merchantServiceDAO;

	/** 公共dao */
	private CommonsDAO commonsDAO;
	private ContactService contactService;
	private ShopServiceDAO shopServiceDAO;

	private UserServiceDAO userServiceDAO;
	private ConsumerContractDAO consumerContractDAO;
	private BaseDAO baseDAO;
	private EntitySystemParameterService entitySystemParameterService;
	private BankService bankService;
	private MchntContractService mchntContractService;
	private AccTypeContractService accTypeContractService;
	private MchantAccTypeContractService mchantAccTypeContractService;

	public Java2TXNBusinessServiceImpl getJava2TXNBusinessService() {
		return java2TXNBusinessService;
	}

	public void setJava2TXNBusinessService(
			Java2TXNBusinessServiceImpl java2txnBusinessService) {
		java2TXNBusinessService = java2txnBusinessService;
	}

	private Java2TXNBusinessServiceImpl java2TXNBusinessService;

	public void setAccTypeContractService(
			AccTypeContractService accTypeContractService) {
		this.accTypeContractService = accTypeContractService;
	}

	public AccTypeContractService getAccTypeContractService() {
		return accTypeContractService;
	}

	public MchantAccTypeContractService getMchantAccTypeContractService() {
		return mchantAccTypeContractService;
	}

	public void setMchantAccTypeContractService(
			MchantAccTypeContractService mchantAccTypeContractService) {
		this.mchantAccTypeContractService = mchantAccTypeContractService;
	}

	public BankService getBankService() {
		return bankService;
	}

	public void setBankService(BankService bankService) {
		this.bankService = bankService;
	}

	public EntitySystemParameterService getEntitySystemParameterService() {
		return entitySystemParameterService;
	}

	public void setEntitySystemParameterService(
			EntitySystemParameterService entitySystemParameterService) {
		this.entitySystemParameterService = entitySystemParameterService;
	}

	public MchntContractService getMchntContractService() {
		return mchntContractService;
	}

	public void setMchntContractService(
			MchntContractService mchntContractService) {
		this.mchntContractService = mchntContractService;
	}

	/**
	 * 查询商户信息
	 * 
	 * @param dto
	 * @return
	 * @throws BizServiceException
	 */
	public PageDataDTO inquiryMerchant(MerchantQueryDTO dto)
			throws BizServiceException {
		try {
			// dto.setSortFieldName("createTime");
			// dto.setSort("desc");
			return pageQueryDAO.query("Merchant.selectMerchantByDTO", dto);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询商户失败！");
		}
	}
	
	/**
	 * 查询外部商户信息
	 * 
	 * @param dto
	 * @return
	 * @throws BizServiceException
	 */
	public PageDataDTO inquiryExternalMerchant(MerchantQueryDTO dto)
			throws BizServiceException {
		try {
			// dto.setSortFieldName("createTime");
			// dto.setSort("desc");
			return pageQueryDAO.query("Merchant.selectExternalMerchantByDTO", dto);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询外部商户失败！");
		}
	}

	/**
	 * 查询商户信息（审核）
	 * 
	 * @param dto
	 * @return
	 * @throws BizServiceException
	 */
	public PageDataDTO inquiryMerchantVerifier(MerchantQueryDTO dto)
			throws BizServiceException {
		try {
			// dto.setSortFieldName("createTime");
			// dto.setSort("desc");
			return pageQueryDAO.query("Merchant.selectMerchantVerifierByDTO",
					dto);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询商户失败！");
		}
	}

	public MerchantDTO initMerchant(MerchantDTO dto) throws BizServiceException {
		try {
			// getChannelList(dto);
			// 设置销售人员列表
			getUserList(dto);
			return dto;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("初始化信息失败!");
		}
	}

	public MerchantDTO initWebSiteName(MerchantDTO dto)
			throws BizServiceException {
		try {
			getUserList(dto);
			dto.setWebsiteUserName("a"
					+ String.valueOf(commonsDAO
							.getNextValueOfSequence("TB_MERCHANT")));
			return dto;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("初始化信息失败!");
		}
	}

	/**
	 * 获取销售人员列表
	 * 
	 * @param dto
	 * @return
	 * @throws BizServiceException
	 */
	public void getUserList(MerchantDTO dto) {
		// 取得销售人员以供选择
		UserExample userExample = new UserExample();
		Criteria criteria = userExample.createCriteria().andIsSaleFlageEqualTo(
				"1").andEntityIdEqualTo(dto.getFatherEntityId())
				.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
		List<User> userList = userServiceDAO.selectByExample(userExample);
		List<UserDTO> userDTOList = new ArrayList<UserDTO>();
		if (userList != null && userList.size() != 0) {
			for (User user : userList) {
				UserDTO userDTO = new UserDTO();
				ReflectionUtil.copyProperties(user, userDTO);
				userDTOList.add(userDTO);
			}
		}
		dto.setSalesmanList(userDTOList);
	}

	/**
	 * 新增商户信息
	 * 
	 * @param dto
	 * @return
	 * @throws BizServiceException
	 */
	public MerchantDTO insertMerchant(MerchantDTO dto)
			throws BizServiceException {
		if (chkMchntCode(dto) > 0) {
			throw new BizServiceException("此商户号已存在");
		}
		if (chkMchntName(dto) > 0) {
			throw new BizServiceException("该机构下商户名称已存在");
		}
		try {
			if (dto.getEnableWebsite() == null) {
				dto.setEnableWebsite("0");
				dto.setWebsitePassword("");
				dto.setWebsiteUserName("");
			}
			Merchant merchant = new Merchant();
			ReflectionUtil.copyProperties(dto, merchant);
			merchant.setCreateUser(dto.getLoginUserId());
			merchant.setCreateTime(DateUtil.getCurrentTime());
			merchant.setModifyUser(dto.getLoginUserId());
			merchant.setModifyTime(DateUtil.getCurrentTime());
			merchant.setEntityId(String.valueOf(commonsDAO
					.getNextValueOfSequence("TB_ENTITY")));
			// merchant.setMerchantCode(String.valueOf(commonsDAO.getNextValueOfSequence("MERCHANT_CODE")));
			merchant.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
			// 添加实体系统参数
//			entitySystemParameterService.insertEntitySystemParameter(merchant
//					.getEntityId(), dto.getDefaultEntityId(), dto
//					.getLoginUserId());
			merchantServiceDAO.insert(merchant);

			/**
			 * //往商户结算信息表里插数据 MchntSettleInfo mchntSettleInfo = new
			 * MchntSettleInfo(); //设置商户ID
			 * mchntSettleInfo.setMchntId(merchantId); //初始化结算金额
			 * mchntSettleInfo.setSettleAmt(0L); //置允许结算标志
			 * mchntSettleInfo.setSettlePerFlag((short)1);
			 * mchntSettleInfoDAO.insert(mchntSettleInfo);
			 */
			ReflectionUtil.copyProperties(merchant, dto);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("新增商户失败！");
		}
		return dto;
	}

	/**
	 * 商户名重名校验
	 * 
	 * @param dto
	 * @return
	 * @throws BizServiceException
	 */
	public int chkMchntName(MerchantDTO dto) {
		int ret = 0;
		// 查询在该表里是否有商户重名
		MerchantExample merchantExample = new MerchantExample();
		// 新增时
		if (dto.getEntityId() == null) {
			merchantExample.createCriteria().andDataStateEqualTo(
					DataBaseConstant.DATA_STATE_NORMAL).andMerchantNameEqualTo(
					dto.getMerchantName()).andFatherEntityIdEqualTo(
					dto.getFatherEntityId());
		} else {
			// 更新时
			merchantExample.createCriteria().andDataStateEqualTo(
					DataBaseConstant.DATA_STATE_NORMAL).andMerchantNameEqualTo(
					dto.getMerchantName()).andEntityIdNotEqualTo(
					dto.getEntityId()).andFatherEntityIdEqualTo(
					dto.getFatherEntityId());
		}
		List<Merchant> merchantList = merchantServiceDAO
				.selectByExample(merchantExample);
		if (merchantList != null && merchantList.size() != 0) {
			ret = merchantList.size();
		}
		return ret;
	}

	/**
	 * 商户号重名校验
	 * 
	 * @param dto
	 * @return
	 * @throws BizServiceException
	 */
	public int chkMchntCode(MerchantDTO dto) {
		int ret = 0;
		// 查询在该表里是否有商户重名
		MerchantExample merchantExample = new MerchantExample();
		// 新增时
		if (dto.getEntityId() == null) {
			merchantExample.createCriteria().andDataStateEqualTo(
					DataBaseConstant.DATA_STATE_NORMAL).andMerchantCodeEqualTo(
					dto.getMerchantCode());
		} else {
			// 更新时
			merchantExample.createCriteria().andDataStateEqualTo(
					DataBaseConstant.DATA_STATE_NORMAL).andMerchantCodeEqualTo(
					dto.getMerchantCode()).andEntityIdNotEqualTo(
					dto.getEntityId());
		}
		List<Merchant> merchantList = merchantServiceDAO
				.selectByExample(merchantExample);
		if (merchantList != null && merchantList.size() != 0) {
			ret = merchantList.size();
		}
		return ret;
	}

	public MerchantDTO loadMerchant(MerchantDTO dto) throws BizServiceException {
		try {
			initMerchant(dto);
			return viewMerchant(dto);
		} catch (BizServiceException e) {
			throw e;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("加载商户信息失败");
		}
	}

	/**
	 * 查看商户信息
	 * 
	 * @param dto
	 * @return
	 * @throws BizServiceException
	 */
	public MerchantDTO viewMerchant(MerchantDTO dto) throws BizServiceException {
		try {
			MerchantExample example = new MerchantExample();
			example.createCriteria().andEntityIdEqualTo(dto.getEntityId())
					.andFatherEntityIdEqualTo(dto.getFatherEntityId())
					.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
			List<Merchant> merchantList = merchantServiceDAO
					.selectByExample(example);

			MerchantDTO resultDto = new MerchantDTO();
			ReflectionUtil.copyProperties(dto, resultDto);
			ReflectionUtil.copyProperties(merchantList.get(0), resultDto);

			if (resultDto.getSalesmanId() != null) {
				User user = userServiceDAO.selectByPrimaryKey(resultDto
						.getSalesmanId());
				if (user != null) {
					resultDto.setSalesmanName(user.getUserName());
				}
			}
			if ("0".equals(resultDto.getEnableWebsite())) {
				resultDto.setWebsiteUserName("a" + resultDto.getEntityId());
				resultDto.setWebsitePassword("b111111");
			}
			// 设置创建者
			/**
			 * if(resultDto.getCreateUser() != null) { User user =
			 * userServiceDAO.selectByPrimaryKey(resultDto.getCreateUser());
			 * if(user != null) { resultDto.setCreator(user.getUserName()); } }
			 */
			// 联系人信息
			resultDto.setContactList(contactService.inqueryContact(dto
					.getEntityId()));

			// 银行账户信息
			resultDto.setBankList(bankService.inquery(dto.getEntityId(), "2"));

			List<ConsumerContractDTO> ccList = mchntContractService
					.inqueryContractById(dto.getEntityId());
			// if (null == ccList || ccList.size() <= 0) {
			// throw new BizServiceException("没有生效的商户合同！");
			// }
			if (ccList.size() > 0) {
				// 合同信息

				resultDto.setContractList(ccList);

				// 合同明细信息
				// resultDto.setAccTypeContractDTOList(accTypeContractService
				// .inquery(ccList.get(0).getConsumerContractId()));
				ConsumerContractDTO ccdto = ccList.get(0);
				ccdto.setContractType(DataBaseConstant.CONTRACT_MERCHANT);
				ccdto.setContractSeller(dto.getDefaultEntityId());
				resultDto
						.setAccTypeContractDTOList(mchantAccTypeContractService
//								.selectAccTypeContDTOInVer(ccdto)
								.selectAccTypeContractDTO(ccdto)
								.getAccTypeContractDTOList());
			}

			// 税号信息
			// resultDto.setTaxAccountList(taxAccountService.inquiryTaxAccount(dto.getMerchantId()));
			// 设置门店信息
			// resultDto.setShopList(viewMerchantShop(dto));
			return resultDto;
		} catch (BizServiceException e) {
			this.logger.error(e.getMessage());
			throw e;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查看商户失败！");
		}
	}

	/**
	 * 商户添加成功后查看商户信息
	 * 
	 * @param dto
	 * @return
	 * @throws BizServiceException
	 */
	public MerchantDTO viewMerchantAfterAdd(MerchantDTO dto)
			throws BizServiceException {
		try {
			getUserList(dto);
			MerchantExample example = new MerchantExample();
			example.createCriteria().andEntityIdEqualTo(dto.getEntityId())
					.andFatherEntityIdEqualTo(dto.getFatherEntityId())
					.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
			List<Merchant> merchantList = merchantServiceDAO
					.selectByExample(example);

			MerchantDTO resultDto = new MerchantDTO();
			ReflectionUtil.copyProperties(dto, resultDto);
			ReflectionUtil.copyProperties(merchantList.get(0), resultDto);

			if (resultDto.getSalesmanId() != null) {
				User user = userServiceDAO.selectByPrimaryKey(resultDto
						.getSalesmanId());
				if (user != null) {
					resultDto.setSalesmanName(user.getUserName());
				}
			}
			if ("0".equals(resultDto.getEnableWebsite())) {
				resultDto.setWebsiteUserName("a" + resultDto.getEntityId());
				resultDto.setWebsitePassword("b111111");
			}
			// 联系人信息
			resultDto.setContactList(contactService.inqueryContact(dto
					.getEntityId()));

			// 银行账户信息
			resultDto.setBankList(bankService.inquery(dto.getEntityId(), "2"));
			return resultDto;
		} catch (BizServiceException e) {
			this.logger.error(e.getMessage());
			throw e;
		} catch (Exception e1) {
			logger.error(e1.getMessage());
			throw new BizServiceException("查看商户失败！");
		}
	}

	public void updateMerchant(MerchantDTO dto) throws BizServiceException {
		if (chkMchntName(dto) > 0) {
			throw new BizServiceException("商户名称已存在");
		}
		try {
			if ("0".equals(dto.getMerchantState())) {
				int i = commonsDAO.isDeleteMerchant(dto);
				if (i == 0) {
					throw new BizServiceException("不能修改为无效状态");
				}
			}
			if (dto.getEnableWebsite() == null) {
				dto.setEnableWebsite("0");
				dto.setWebsitePassword("");
				dto.setWebsiteUserName("");
			}
			Merchant merchant = new Merchant();
			MerchantKey merchantKey = new MerchantKey();
			merchantKey.setEntityId(dto.getEntityId());
			merchantKey.setFatherEntityId(dto.getFatherEntityId());
			merchant = merchantServiceDAO.selectByPrimaryKey(merchantKey);
			ReflectionUtil.copyPropertiesNotNull(dto, merchant);
			merchant.setModifyUser(dto.getLoginUserId());
			merchant.setModifyTime(DateUtil.getCurrentTime());
			if (merchant.getBusinessLicenseNumber() == null) {
				merchant.setBusinessLicenseNumber("");
			}
			merchantServiceDAO.updateByPrimaryKey(merchant);
		} catch (BizServiceException b) {
			
			throw b;
		}

		catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("更新商户失败！");
		}
	}

	/**
	 * 删除商户信息
	 * 
	 * @param dto
	 * @return
	 * @throws BizServiceException
	 */
	public void deleteMerchant(MerchantDTO merchantDTO)
			throws BizServiceException {
		try {
			List<MerchantDTO> list = merchantDTO.getMerchantDTOList();
			List<Merchant> updateList = new ArrayList<Merchant>();
			List<String> entityIdList = new ArrayList<String>();
			for (MerchantDTO dto : list) {
				Merchant merchant = new Merchant();
				ReflectionUtil.copyProperties(dto, merchant);
				merchant.setModifyUser(dto.getLoginUserId());
				merchant.setModifyTime(DateUtil.getCurrentTime());
				merchant.setDataState(DataBaseConstant.DATA_STATE_DELETE);
				updateList.add(merchant);
				entityIdList.add(dto.getEntityId());
				merchantDTO.setEntityIdList(entityIdList);
				int i = commonsDAO.isDeleteMerchant(dto);
				if (i == 0) {
					throw new BizServiceException("删除失败");
				}
			}

			// if (chkMchntContract(merchantDTO) > 0) {
			// throw new BizServiceException("所选商户还有合同是可用的，不能删除！");
			// }
			//	
			// if (chkShop(merchantDTO) > 0) {
			// throw new BizServiceException("所选商户还有门店，不能删除！");
			commonsDAO.batchUpdate(
					"TB_MERCHANT.abatorgenerated_deleteByPrimaryKey",
					updateList);

			// 删除商户结算信息表里的相关数据
			/**
			 * MchntSettleInfoExample example = new MchntSettleInfoExample();
			 * example.createCriteria().andMchntIdIn(merchantIdList);
			 * mchntSettleInfoDAO.deleteByExample(example);
			 */
		} catch (BizServiceException e1) {
			throw e1;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("删除商户失败！");
		}
	}

	/**
	 * 判断商户下是否还有可用合同
	 * 
	 * @param dto
	 * @return
	 * @throws BizServiceException
	 */
	public int chkMchntContract(MerchantDTO dto) {
		int ret = 0;
		ConsumerContractExample mchntContractExample = new ConsumerContractExample();
		mchntContractExample.createCriteria().andContractSellerEqualTo(
				dto.getFatherEntityId()).andContractBuyerIn(
				dto.getEntityIdList()).andContractTypeEqualTo(
				DataBaseConstant.CONTRACT_MERCHANT)
				.andContractEndDateIsNotNull()
				.andContractEndDateGreaterThanOrEqualTo(
						DateUtil.date2String(DateUtil.getCurrentDate()))
				.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
		List<ConsumerContract> mchntContractList = consumerContractDAO
				.selectByExample(mchntContractExample);
		if (mchntContractList != null && mchntContractList.size() != 0) {
			ret = mchntContractList.size();
			return ret;
		}
		mchntContractExample = new ConsumerContractExample();
		mchntContractExample.createCriteria().andContractSellerEqualTo(
				dto.getFatherEntityId()).andContractBuyerIn(
				dto.getEntityIdList()).andContractTypeEqualTo(
				DataBaseConstant.CONTRACT_MERCHANT).andContractEndDateIsNull()
				.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
		mchntContractList = consumerContractDAO
				.selectByExample(mchntContractExample);
		if (mchntContractList != null && mchntContractList.size() != 0) {
			ret = mchntContractList.size();
		}
		return ret;
	}

	// 查询是否有有效门店
	public int chkShop(MerchantDTO dto) throws BizServiceException {
		try {
			ShopExample example = new ShopExample();
			example.createCriteria().andEntityIdIn(dto.getEntityIdList())
					.andConsumerIdEqualTo(dto.getFatherEntityId())
					.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
			List<Shop> shopList = shopServiceDAO.selectByExample(example);
			if (shopList != null) {
				return shopList.size();
			} else {
				return 0;
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询商户下的门店失败！");
		}

	}

	/**
	 * 联系人
	 * 
	 * @param dto
	 * @return
	 * @throws BizServiceException
	 */
	public void insertContact(ContactDTO dto) throws BizServiceException {
		// dto.setContactType(DataBaseConstant.CONTACT_REF_TYPE_MERCHANT);
		contactService.insertContact(dto);
	}

	public ContactDTO viewContact(ContactDTO dto) throws BizServiceException {
		return contactService.viewContact(dto);
	}

	public void updateContact(ContactDTO dto) throws BizServiceException {
		// dto.setContactType(DataBaseConstant.CONTACT_REF_TYPE_MERCHANT);
		contactService.updateContact(dto);
	}

	public void deleteContact(ContactDTO dto) throws BizServiceException {
		contactService.deleteContact(dto);
	}

	public PageQueryDAO getPageQueryDAO() {
		return pageQueryDAO;
	}

	public void setPageQueryDAO(PageQueryDAO pageQueryDAO) {
		this.pageQueryDAO = pageQueryDAO;
	}

	public MerchantServiceDAO getMerchantServiceDAO() {
		return merchantServiceDAO;
	}

	public void setMerchantServiceDAO(MerchantServiceDAO merchantServiceDAO) {
		this.merchantServiceDAO = merchantServiceDAO;
	}

	public CommonsDAO getCommonsDAO() {
		return commonsDAO;
	}

	public void setCommonsDAO(CommonsDAO commonsDAO) {
		this.commonsDAO = commonsDAO;
	}

	public ContactService getContactService() {
		return contactService;
	}

	public void setContactService(ContactService contactService) {
		this.contactService = contactService;
	}

	public ShopServiceDAO getShopServiceDAO() {
		return shopServiceDAO;
	}

	public void setShopServiceDAO(ShopServiceDAO shopServiceDAO) {
		this.shopServiceDAO = shopServiceDAO;
	}

	public UserServiceDAO getUserServiceDAO() {
		return userServiceDAO;
	}

	public void setUserServiceDAO(UserServiceDAO userServiceDAO) {
		this.userServiceDAO = userServiceDAO;
	}

	public ConsumerContractDAO getConsumerContractDAO() {
		return consumerContractDAO;
	}

	public void setConsumerContractDAO(ConsumerContractDAO consumerContractDAO) {
		this.consumerContractDAO = consumerContractDAO;
	}

	/**
	 * 验证商户网站登录名是否可用
	 */
	@SuppressWarnings("unchecked")
	public String checkWebName(MerchantDTO merchantDTO)
			throws BizServiceException {
		try {
			List<MerchantDTO> merchantDTOList = (List<MerchantDTO>) baseDAO
					.queryForList("Merchant", "selectWebsiteName", merchantDTO);
			if (merchantDTOList.size() > 0) {
				return merchantDTO.getWebsiteUserName() + "网站登录名已存在!";
			} else {
				return "网站登录名可用";
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询失败!");
		}
	}

	/*
	 * 
	 * 修改商户网站密码
	 */
	public MerchantDTO loadForModifyPassowrd(MerchantDTO merchantDTO)
			throws BizServiceException {
		try {
			MerchantKey key = new MerchantKey();
			key.setEntityId(merchantDTO.getEntityId());
			key.setFatherEntityId(merchantDTO.getFatherEntityId());
			Merchant merchant = merchantServiceDAO.selectByPrimaryKey(key);
			if (("0").equals(merchant.getEnableWebsite())) {
				throw new BizServiceException("商户并没有开通网上管理请先开通");
			} else {
				merchantDTO = new MerchantDTO();
				ReflectionUtil.copyProperties(merchant, merchantDTO);
				return merchantDTO;
			}
		} catch (BizServiceException e1) {
			throw e1;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("加载商户失败!");
		}
	}

	/**
	 * 部分更新用户
	 */

	public void updatePart(MerchantDTO merchantDTO) throws BizServiceException {
		try {
			Merchant merchant = new Merchant();
			ReflectionUtil.copyProperties(merchantDTO, merchant);
			merchantServiceDAO.updateByPrimaryKeySelective(merchant);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("修改失败!");
		}

	}

	public BaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 * 通过MerchantKey来查询这个商户
	 */
	public MerchantDTO selectMerchantByKey(MerchantDTO merchantDTO)
			throws BizServiceException {
		try {
			MerchantKey key = new MerchantKey();
			key.setEntityId(merchantDTO.getEntityId());
			key.setFatherEntityId(merchantDTO.getFatherEntityId());
			Merchant merchant = merchantServiceDAO.selectByPrimaryKey(key);
			ReflectionUtil.copyProperties(merchant, merchantDTO);
			return merchantDTO;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询商户失败!");
		}
	}

	/**
	 * 商户交易查询
	 */
	@SuppressWarnings("unchecked")
	public PageDataDTO merchantTxnQuery(MerchantTxnQueryDTO merchantTxnQueryDTO)
			throws BizServiceException {
		Map<String, String> rspCodeMap = RspCodeMap.getRspCodeMap();
		PageDataDTO pageDataDTO = new PageDataDTO();
		try {
			TxnPackageDTO txnPackageDTO = new TxnPackageDTO();
			// TXN
			txnPackageDTO.setTxnCode("S030");
			// 查询标志 0当天 1历史
			txnPackageDTO.setFlag(merchantTxnQueryDTO.getSelectFlag());
			// 收单机构号
			txnPackageDTO.setConsumerCode(merchantTxnQueryDTO
					.getDefaultEntityId());
			// 商户编号
			txnPackageDTO
					.setMerchantCode(merchantTxnQueryDTO.getMerchantCode());
			// 终端号
			txnPackageDTO.setTermId(merchantTxnQueryDTO.getPosId());
			// 门店号
			txnPackageDTO.setShopCode(merchantTxnQueryDTO.getShopId());
			// 卡号
			txnPackageDTO.setCardNO(merchantTxnQueryDTO.getCardNo());
			// 交易类型
			txnPackageDTO.setTxnType(merchantTxnQueryDTO.getTransType());
			// 起始日期
			txnPackageDTO.setStart_dt(merchantTxnQueryDTO.getStartDate());
			// 结束日期
			txnPackageDTO.setEnd_dt(merchantTxnQueryDTO.getEndDate());
			// 起始行
			txnPackageDTO.setStart_row(String.valueOf(merchantTxnQueryDTO
					.getFirstCursorPosition()));
			// 结束行
			txnPackageDTO.setEnd_row(String.valueOf(String
					.valueOf(merchantTxnQueryDTO.getLastCursorPosition())));
			String selectCondition = txnPackageDTO.getTxnCode() + "|"
            + txnPackageDTO.getFlag() + "|"
            + txnPackageDTO.getConsumerCode() + "|"
            + txnPackageDTO.getMerchantCode() + "|"
            + txnPackageDTO.getShopCode() + "|"
            + txnPackageDTO.getTermId() + "|"
            + txnPackageDTO.getCardNO() + "|"
            + txnPackageDTO.getTxnType() + "|"
            + txnPackageDTO.getStart_dt() + "|"
            + txnPackageDTO.getEnd_dt() + "|" + txnPackageDTO.getStart_row() + "|"
            + txnPackageDTO.getEnd_row();
			logger.info("开始发送报文给C查询");
			logger.info(selectCondition);
			txnPackageDTO = java2TXNBusinessService
					.queryMerchantTxn(txnPackageDTO);

			String rspCode = txnPackageDTO.getRspCode();

			// 以前的代码
			/*
			 * String startRow =
			 * String.valueOf(merchantTxnQueryDTO.getFirstCursorPosition());
			 * String endRow =
			 * String.valueOf(merchantTxnQueryDTO.getLastCursorPosition());
			 * HashMap map = new HashMap(); String selectCondition = "S030" +
			 * "|" + merchantTxnQueryDTO.getSelectFlag() + "|" +
			 * merchantTxnQueryDTO.getDefaultEntityId() + "|" +
			 * merchantTxnQueryDTO.getMerchantCode() + "|" +
			 * merchantTxnQueryDTO.getShopId() + "|" +
			 * merchantTxnQueryDTO.getPosId() + "|" +
			 * merchantTxnQueryDTO.getCardNo() + "|" +
			 * merchantTxnQueryDTO.getTransType() + "|" +
			 * merchantTxnQueryDTO.getStartDate() + "|" +
			 * merchantTxnQueryDTO.getEndDate() + "|" + startRow + "|" + endRow;
			 * logger.debug("Service:vMchntTxnInq & port=" + selectCondition);
			 * map.put(CFunctionConstant.RESV2, selectCondition);
			 * OperationResult or = java2C.sendTpService("vMchntTxnInq", map,
			 * Const.JAVA2C_BIG_AMT, false); HashMap map1 = (HashMap)
			 * or.getDetailvo();
			 * 
			 * String rspCode = (String) map1.get(CFunctionConstant.RESP_CODE);
			 * logger.debug("rspCode:" + rspCode);
			 */
			Map map=new HashMap();
			map.put("tranTypeMap", Const.getMap());
			txnPackageDTO.getList().add(map);
			if ("00".equals(rspCode)) {
				/*
				 * List<Map<?, ?>> list = new ArrayList<Map<?, ?>>(); String
				 * allRecord = (String) map1.get(CFunctionConstant.OTHERDATA);
				 * if (null != allRecord && !allRecord.equals("")) { String[]
				 * records = allRecord.split("\\|"); for (int i = 0; i <
				 * records.length; i++) { String record = records[i]; String[]
				 * fields = record.split("\\^"); Map map2 = new HashMap();
				 * map2.put("tranCode", fields[1]); map2.put("cardNo",
				 * fields[2].substring(2)); map2.put("merchantName", fields[3]);
				 * map2.put("shopName", fields[4]); map2.put("postcode",
				 * fields[5]); map2.put("tranType",
				 * ReflectionUtil.getMap().get(fields[6])); map2.put("tranFee",
				 * fields[7]); map2.put("respCode",fields[8].trim().equals("1")
				 * ? "成功" : fields[8].trim().equals("0") ? "失败" : fields[8]);
				 * map2.put("amtTranFee", fields[9]); map2.put("tranTime",
				 * fields[10]); list.add(map2); } }
				 * pageDataDTO.setTotalRecord(Integer
				 * .parseInt(map1.get(CFunctionConstant
				 * .REC_TXN_DATE).toString()));
				 * pageDataDTO.setPageSize(Integer.parseInt
				 * (map1.get(CFunctionConstant.REC_TXN_TIME).toString()));
				 */
			
				
				// 返回的总行数
				pageDataDTO.setTotalRecord(Integer.parseInt(txnPackageDTO
						.getTotalRow()));
				// 返回的实际行数
				pageDataDTO.setPageSize(Integer.parseInt(txnPackageDTO
						.getRealRow()));
				// 解析返回的报文域
				pageDataDTO.setData(txnPackageDTO.getList());
				
			} else {
				if (rspCodeMap.containsKey(rspCode)) {
					throw new BizServiceException(rspCodeMap.get(rspCode));
				}
			}
		} catch (BizServiceException b) {
			throw b;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询商户交易记录失败!C后台未启动！！");
		}
		return pageDataDTO;
	}
	public MerchantTxnQueryDTO loadAllMerchant(
			MerchantTxnQueryDTO merchantTxnQueryDTO) throws BizServiceException {
		try {
			MerchantExample example = new MerchantExample();
			example.createCriteria().andFatherEntityIdEqualTo(
					merchantTxnQueryDTO.getEntityId()).andDataStateEqualTo(
					DataBaseConstant.DATA_STATE_NORMAL);
			List<Merchant> merchantList = merchantServiceDAO
					.selectByExample(example);
			if (merchantList != null && merchantList.size() > 0) {
				List<MerchantDTO> merchantDTOList = new ArrayList<MerchantDTO>();
				for (Merchant merchant : merchantList) {
					MerchantDTO merchantDTO = new MerchantDTO();
					ReflectionUtil.copyProperties(merchant, merchantDTO);
					merchantDTOList.add(merchantDTO);
				}
				merchantTxnQueryDTO.setMerchantDTOList(merchantDTOList);
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("加载所有商户失败!");
		}
		return merchantTxnQueryDTO;
	}

	@SuppressWarnings("unchecked")
	public List<ShopDTO> getShopListByMerchantId(MerchantTxnQueryDTO merchantDTO)
			throws BizServiceException {
		try {
			return baseDAO.queryForList("Merchant",
					"selectShopDTOByMerchantId", merchantDTO);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询商户下的门店信息失败");
		}

	}

	public MerchantDTO getMerchantDTO(MerchantDTO dto)
			throws BizServiceException {
		try {
			MerchantExample example = new MerchantExample();
			example.createCriteria().andMerchantCodeEqualTo(
					dto.getMerchantCode()).andFatherEntityIdEqualTo(
					dto.getFatherEntityId()).andDataStateEqualTo(
					DataBaseConstant.DATA_STATE_NORMAL);
			List<Merchant> merchantList = merchantServiceDAO
					.selectByExample(example);

			MerchantDTO resultDto = new MerchantDTO();
			ReflectionUtil.copyProperties(dto, resultDto);
			ReflectionUtil.copyProperties(merchantList.get(0), resultDto);
			return resultDto;
		} catch (Exception e) {
			
			throw new BizServiceException("查询商户失败");
		}

	}

	public void updateConMerchant(MerchantDTO dto) throws BizServiceException {
		try {
			MerchantExample example = new MerchantExample();
			example.createCriteria().andEntityIdEqualTo(dto.getEntityId())
					.andFatherEntityIdEqualTo(dto.getFatherEntityId())
					.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
			List<Merchant> merchantList = merchantServiceDAO
					.selectByExample(example);

			MerchantDTO resultDto = new MerchantDTO();
			// ReflectionUtil.copyProperties(dto, resultDto);
			ReflectionUtil.copyProperties(merchantList.get(0), resultDto);
			resultDto.setMerchantState("4");
			//resultDto.setMerchantState("1");
			resultDto.setLoginUserId(dto.getLoginUserId());
			this.updateMerchant(resultDto);
		} catch (BizServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new BizServiceException("修改商户信息失败");
		}
	}

	@Override
	public void frozen(MerchantDTO dto) throws BizServiceException {
		merchantServiceDAO.updateMerchantState(dto);
		
	}

	@Override
	public void unfrozen(MerchantDTO dto) throws BizServiceException {
		merchantServiceDAO.updateMerchantState(dto);
		
	}

}
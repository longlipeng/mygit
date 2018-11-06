package com.huateng.univer.consumer.shop.biz.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.consumer.shop.dto.ShopDTO;
import com.allinfinance.univer.consumer.shop.dto.ShopQueryDTO;
import com.allinfinance.univer.entity.dto.ContactDTO;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.dao.PageQueryDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.PosInfoDAO;
import com.huateng.framework.ibatis.dao.ShopPosDAO;
import com.huateng.framework.ibatis.model.Merchant;
import com.huateng.framework.ibatis.model.MerchantKey;
import com.huateng.framework.ibatis.model.PosInfo;
import com.huateng.framework.ibatis.model.PosInfoExample;
import com.huateng.framework.ibatis.model.Shop;
import com.huateng.framework.ibatis.model.ShopExample;
import com.huateng.framework.ibatis.model.User;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.ReflectionUtil;
import com.huateng.univer.consumer.merchant.integration.dao.MerchantServiceDAO;
import com.huateng.univer.consumer.shop.biz.service.ShopService;
import com.huateng.univer.consumer.shop.integration.dao.ShopServiceDAO;
import com.huateng.univer.entitybaseinfo.contact.biz.service.ContactService;
import com.huateng.univer.system.sysparam.biz.service.EntitySystemParameterService;
import com.huateng.univer.system.user.integration.dao.UserServiceDAO;
import com.suning.svc.ibatis.model.StlShop;

/**
 * 门店管理服务实现
 * 
 * @author zengfenghua
 * @since 1.0
 */
public class ShopServiceImpl implements ShopService {

	/**
	 * Log操作
	 */
	Logger logger = Logger.getLogger(ShopServiceImpl.class);

	/** 分页DAO */
	private PageQueryDAO pageQueryDAO;
	private EntitySystemParameterService entitySystemParameterService;

	/** 门店服务DAO */
	private ShopServiceDAO shopServiceDAO;

	private CommonsDAO commonsDAO;

	private ContactService contactService;

	private UserServiceDAO userServiceDAO;

	private ShopPosDAO shopPosDAO;

	private MerchantServiceDAO merchantServiceDAO;
	private PosInfoDAO posInfoDAO;

	public EntitySystemParameterService getEntitySystemParameterService() {
		return entitySystemParameterService;
	}

	public void setEntitySystemParameterService(
			EntitySystemParameterService entitySystemParameterService) {
		this.entitySystemParameterService = entitySystemParameterService;
	}

	/**
	 * 查询门店信息
	 * 
	 * @param dto
	 * @return
	 * @throws BizServiceException
	 */
	public PageDataDTO inquiryShop(ShopQueryDTO dto) throws BizServiceException {
		try {
			return pageQueryDAO.query("SHOP.selectShopByDTO", dto);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询门店信息失败！");
		}
	}

	/**
	 * 添加门店
	 * 
	 * @param dto
	 * @return
	 * @throws BizServiceException
	 */
	public ShopDTO insertShop(ShopDTO dto) throws BizServiceException {
		if (chkShopName(dto) > 0) {
			throw new BizServiceException("门店名称已存在");
		}
		try {
			Shop shop = new Shop();
			ReflectionUtil.copyProperties(dto, shop);
			shop.setCreateUser(dto.getLoginUserId());
			shop.setCreateTime(DateUtil.getCurrentTime());
			shop.setModifyUser(dto.getLoginUserId());
			shop.setModifyTime(DateUtil.getCurrentTime());
			shop.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
			shop.setShopId(commonsDAO.getNextValueOfSequence("TB_SHOP"));
//			entitySystemParameterService.insertEntitySystemParameter(shop
//					.getShopId(), dto.getDefaultEntityId(), dto
//					.getLoginUserId());
			// 获取商户下最大shopCode
			MerchantKey merchantKey = new MerchantKey();
			merchantKey.setEntityId(shop.getEntityId());
			merchantKey.setFatherEntityId(shop.getConsumerId());
			Merchant merchant = merchantServiceDAO
					.selectByPrimaryKey(merchantKey);
			String shopCode = String.valueOf((Integer.parseInt(merchant
					.getShopMaxCode()) + 1));
			while (shopCode.length() < merchant.getShopMaxCode().length()) {
				shopCode = "0" + shopCode;
			}
			shop.setShopCode(shopCode);

			shopServiceDAO.insert(shop);
			merchant.setShopMaxCode(shop.getShopCode());
			merchantServiceDAO.updateByPrimaryKey(merchant);
			ReflectionUtil.copyProperties(shop, dto);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("新增门店失败！");
		}
		return dto;
	}

	/**
	 * 查看门店信息
	 * 
	 * @param dto
	 * @return
	 * @throws BizServiceException
	 */
	public ShopDTO viewShop(ShopDTO dto) throws BizServiceException {
		try {
			ShopExample example = new ShopExample();
			example.createCriteria().andShopIdEqualTo(dto.getShopId())
					.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
			// example.createCriteria().andMerchantIdEqualTo("").andda
			List<Shop> shopList = shopServiceDAO.selectByExample(example);
			Shop shop = shopList.get(0);
			MerchantKey merchantKey = new MerchantKey();
			merchantKey.setEntityId(shop.getEntityId());
			merchantKey.setFatherEntityId(shop.getConsumerId());
			Merchant merchant = merchantServiceDAO
					.selectByPrimaryKey(merchantKey);
			ShopDTO resultDto = new ShopDTO();
			ReflectionUtil.copyProperties(shop, resultDto);
			resultDto.setEntityName(merchant.getMerchantName());
			resultDto.setMerchantCode(merchant.getMerchantCode());
			// 联系人信息
			resultDto.setContactList(contactService.inqueryContact(dto
					.getShopId().toString()));
			PosInfoExample posInfoExample = new PosInfoExample();
			posInfoExample.createCriteria().andShopIdEqualTo(
					Long.parseLong(dto.getShopId().toString()));
			List<PosInfo> list = posInfoDAO.selectByExample(posInfoExample);
			if (list != null && list.size() > 0) {
				resultDto.setIs(false);
			}
			// resultDto.setAuthorityCardDTOList(queryAuthorityDTOList(dto.getShopId()));
			// resultDto.setPosList(shopPosService.inqueryPos(dto.getShopId()));

			if (resultDto.getCreateUser() != null) {
				User user = userServiceDAO.selectByPrimaryKey(resultDto
						.getCreateUser());
				if (user != null) {
					resultDto.setCreator(user.getUserName());
				}
			}
			/**
			 * StringBuffer s=new StringBuffer(); s.append("门店名称：");
			 * s.append(resultDto.getShopName()); s.append(" ");
			 * s.append("门店地址："); s.append(resultDto.getShopAddress());
			 * s.append("[服务]"); resultDto.setInfo(s.toString());
			 */
			return resultDto;
		} catch (Exception e) {
			logger.error(e.getMessage() , e);
			throw new BizServiceException("查看门店信息失败！");
		}
	}

	/**
	 * 更新门店
	 * 
	 * @param dto
	 * @return
	 * @throws BizServiceException
	 */
	public void updateShop(ShopDTO dto) throws BizServiceException {
		if (chkShopName(dto) > 0) {
			throw new BizServiceException("门店名称已存在");
		}

		try {
			if (dto.getShopState().equals(DataBaseConstant.DATA_TYPE_NO)) {
				PosInfoExample posInfoExample = new PosInfoExample();
				posInfoExample
						.createCriteria()
						.andShopIdEqualTo(
								Long.parseLong(dto.getShopId().toString()))
						.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);

				List<PosInfo> posInfList = posInfoDAO
						.selectByExample(posInfoExample);
				if (null != posInfList && posInfList.size() > 0) {
					throw new BizServiceException("门店：" + dto.getShopName()
							+ "下面还有POS，不能改状态为无效！");
				}
			}
			Shop oldShop = shopServiceDAO.selectByPrimaryKey(dto.getShopId());

			Shop shop = new Shop();
			shop = shopServiceDAO.selectByPrimaryKey(dto.getShopId());
			ReflectionUtil.copyPropertiesNotNull(dto, shop);
			shop.setModifyUser(dto.getLoginUserId());
			shop.setModifyTime(DateUtil.getCurrentTime());
			shopServiceDAO.updateByPrimaryKey(shop);

			/**
			 * 如果门店修改，那么关联下面的POS门店名称修改 这里的MchntName其实就是门店名称，在设计表时名字取的不对
			 * 
			 */
			/**
			 * if(!shop.getShopName().equals(oldShop.getShopName())){
			 * PosInfExample example=new PosInfExample();
			 * example.createCriteria(
			 * ).andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL)
			 * .andShopIdEqualTo(shop.getShopId()); List<PosInf>
			 * posList=posInfDAO.selectByExample(example); for(PosInf
			 * posInf:posList){ posInf.setMchntName(shop.getShopName());
			 * posInf.setPrmChangeFlag1((short)1);
			 * posInfDAO.updateByPrimaryKeySelective(posInf); } }
			 */
		} catch (BizServiceException ex) {
			throw ex;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("更新门店失败！");
		}
	}

	/**
	 * 门店名称重名校验
	 * 
	 * @param dto
	 * @return
	 * @throws BizServiceException
	 */
	public int chkShopName(ShopDTO dto) {
		int ret = 0;
		// 查询在该表里是否有商户重名
		ShopExample shopExample = new ShopExample();
		// 新增时
		if (dto.getShopId() == null) {
			shopExample.createCriteria().andDataStateEqualTo(
					DataBaseConstant.DATA_STATE_NORMAL).andEntityIdEqualTo(
					dto.getEntityId())
					.andConsumerIdEqualTo(dto.getConsumerId())
					.andShopNameEqualTo(dto.getShopName());
		} else {
			shopExample.createCriteria().andDataStateEqualTo(
					DataBaseConstant.DATA_STATE_NORMAL).andEntityIdEqualTo(
					dto.getEntityId())
					.andConsumerIdEqualTo(dto.getConsumerId())
					.andShopNameEqualTo(dto.getShopName()).andShopIdNotEqualTo(
							dto.getShopId());
		}
		List<Shop> shopList = shopServiceDAO.selectByExample(shopExample);
		if (shopList != null && shopList.size() != 0) {
			ret = shopList.size();
		}
		return ret;
	}

	public void deleteShop(ShopDTO shopDTO) throws BizServiceException {
		try {
			List<ShopDTO> list = shopDTO.getShopDTOList();
			List<Shop> updateList = new ArrayList<Shop>();
			// 20131114待删除STL门店列表
			List<StlShop> stlShopList = new ArrayList<StlShop>();
			for (ShopDTO dto : list) {
				Shop shop = new Shop();
				// 20131114待删除STL门店记录
				StlShop stlShop = new StlShop();
				// 20131125仅对数字型门店编号进行检查
				if(StringUtils.isNumeric(dto.getShopId())){
    				PosInfoExample posInfoExample = new PosInfoExample();
    				posInfoExample
    						.createCriteria()
    						.andShopIdEqualTo(
    								Long.parseLong(dto.getShopId().toString()))
    						.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
    
    				List<PosInfo> posInfList = posInfoDAO
    						.selectByExample(posInfoExample);
    				if (null != posInfList && posInfList.size() > 0) {
    					ShopExample example = new ShopExample();
    					example.createCriteria().andShopIdEqualTo(dto.getShopId())
    							.andDataStateEqualTo(
    									DataBaseConstant.DATA_STATE_NORMAL);
    					// example.createCriteria().andMerchantIdEqualTo("").andda
    					List<Shop> shopList = shopServiceDAO
    							.selectByExample(example);
    					Shop shop2 = shopList.get(0);
    					MerchantKey merchantKey = new MerchantKey();
    					merchantKey.setEntityId(shop2.getEntityId());
    					merchantKey.setFatherEntityId(shop2.getConsumerId());
    					Merchant merchant = merchantServiceDAO
    							.selectByPrimaryKey(merchantKey);
    					throw new BizServiceException("商户号"
    							+ merchant.getMerchantCode() + " 门店号："
    							+ shop2.getShopCode() + "下面还有POS，不能删除！");
    				}
				}
				ReflectionUtil.copyProperties(dto, shop);
				shop.setModifyUser(shopDTO.getLoginUserId());
				shop.setModifyTime(DateUtil.getCurrentTime());
				shop.setDataState(DataBaseConstant.DATA_STATE_DELETE);
				updateList.add(shop);
				// 20131114设置STL待删除门店记录主键
				stlShop.setShopId(dto.getShopId());
				// 20131114增加到STL待删除门店列表
				stlShopList.add(stlShop);
			}
			// 20131114批量删除STL门店
			commonsDAO.batchDelete(
                    "TB_STL_SHOP.abatorgenerated_deleteByPrimaryKey",
                    stlShopList);
			// 20131113将批量更新改成批量物理删除
			commonsDAO.batchDelete(
					"TB_SHOP.abatorgenerated_deleteByPrimaryKey",
					updateList);
		} catch (BizServiceException e) {
			throw e;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("删除门店信息失败！");
		}

	}

	public void updateContact(ContactDTO dto) throws BizServiceException {
		PosInfoExample posInfoExample = new PosInfoExample();
		posInfoExample.createCriteria().andShopIdEqualTo(
				Long.parseLong(dto.getEntityId().toString()));
		List<PosInfo> list = posInfoDAO.selectByExample(posInfoExample);
		if (list != null && list.size() > 0) {
			dto.setIs(false);
		}
		contactService.updateContact(dto);
	}

	public ContactDTO viewContact(ContactDTO dto) throws BizServiceException {

		return contactService.viewContact(dto);
	}

	public void insertContact(ContactDTO dto) throws BizServiceException {
		contactService.insertContact(dto);
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

	public ShopServiceDAO getShopServiceDAO() {
		return shopServiceDAO;
	}

	public void setShopServiceDAO(ShopServiceDAO shopServiceDAO) {
		this.shopServiceDAO = shopServiceDAO;
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

	public UserServiceDAO getUserServiceDAO() {
		return userServiceDAO;
	}

	public void setUserServiceDAO(UserServiceDAO userServiceDAO) {
		this.userServiceDAO = userServiceDAO;
	}

	public ShopPosDAO getShopPosDAO() {
		return shopPosDAO;
	}

	public void setShopPosDAO(ShopPosDAO shopPosDAO) {
		this.shopPosDAO = shopPosDAO;
	}

	public MerchantServiceDAO getMerchantServiceDAO() {
		return merchantServiceDAO;
	}

	public void setMerchantServiceDAO(MerchantServiceDAO merchantServiceDAO) {
		this.merchantServiceDAO = merchantServiceDAO;
	}

	public PosInfoDAO getPosInfoDAO() {
		return posInfoDAO;
	}

	public void setPosInfoDAO(PosInfoDAO posInfoDAO) {
		this.posInfoDAO = posInfoDAO;
	}

}

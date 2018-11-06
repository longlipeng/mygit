package com.huateng.univer.issuer.productcardbin.service.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.issuer.dto.product.ProductCardBinDTO;
import com.allinfinance.univer.issuer.dto.product.ProductDTO;
import com.allinfinance.univer.issuer.dto.product.ProductQueryDTO;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.dao.BaseDAO;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.dao.PageQueryDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.AcctypeContractDAO;
import com.huateng.framework.ibatis.dao.ProductCardBinDAO;
import com.huateng.framework.ibatis.dao.ProductServiceDAO;
import com.huateng.framework.ibatis.model.AcctypeContract;
import com.huateng.framework.ibatis.model.ProductCardBin;
import com.huateng.framework.ibatis.model.ProductCardBinExample;
import com.huateng.framework.ibatis.model.ProductCardBinKey;
import com.huateng.framework.ibatis.model.ProductService;
import com.huateng.framework.ibatis.model.ProductServiceExample;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.ReflectionUtil;
import com.huateng.univer.issuer.order.biz.bo.StockOrderCommonService;
import com.huateng.univer.issuer.productcardbin.service.ProductCardBinService;

/**
 * 产品卡BIN service
 * 
 * @author xxl
 * 
 */

public class ProductCardBinServiceImpl implements ProductCardBinService {

	Logger logger = Logger.getLogger(this.getClass());

	private PageQueryDAO pageQueryDAO;
	private BaseDAO baseDAO;
	private ProductCardBinDAO productCardBinDAO;
	private StockOrderCommonService stockOrderCommonService;
	
	private CommonsDAO commonsDAO;
	private ProductServiceDAO productServiceDAO;
	private AcctypeContractDAO acctypeContractDAO;

	@SuppressWarnings("unchecked")
	public List<ProductCardBinDTO> getCardBinByProductId(String productId)
			throws BizServiceException {
		try {
			return baseDAO.queryForList("PRODUCT.selectCardBinByProductId",
					productId);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询产品卡bin失败！");
		}
	}

	public PageDataDTO choiceProductCardBin1(ProductQueryDTO productQueryDTO)
			throws BizServiceException {
		try {
			PageDataDTO pageDataDTO = pageQueryDAO.query(
					"PRODUCT.selectCardBinForProduct", productQueryDTO);
			return pageDataDTO;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询产品卡bin列表失败！");
		}
	}

	public ProductDTO choiceProductCardBin(ProductDTO productDTO)
			throws BizServiceException {
		try {
			List<ProductCardBinDTO> cardBinDTOs = stockOrderCommonService
					.getProductCardBinDTOs(productDTO);
			productDTO.setProductCardBinDTOs(cardBinDTOs);
			return productDTO;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询产品卡bin列表失败！");
		}
	}

	private int checkProductService(ProductCardBinDTO productCardBinDTO) {
		ProductServiceExample example = new ProductServiceExample();
		example.createCriteria().andProductIdEqualTo(productCardBinDTO.getProductId());
		List<ProductService> services = productServiceDAO.selectByExample(example);
		if(services !=null)
			return services.size();
		else
			return 0;
	}
	
	private int checkRepeat(ProductCardBinDTO productCardBinDTO) {
		ProductCardBinExample example = new ProductCardBinExample();
		example.createCriteria().andDataStateEqualTo(
				DataBaseConstant.DATA_STATE_NORMAL).andProductIdEqualTo(
				productCardBinDTO.getProductId());
		// .andCardBinEqualTo(productCardBinDTO.getCardBin());

		return productCardBinDAO.countByExample(example);
	}

	private int checkCardBINRepeat(String cardBIN) {
		ProductCardBinExample example = new ProductCardBinExample();
		example.createCriteria().andDataStateEqualTo(
				DataBaseConstant.DATA_STATE_NORMAL).andCardBinEqualTo(cardBIN);

		return productCardBinDAO.countByExample(example);
	}

	private int getDefault(ProductCardBinDTO productCardBinDTO) {
		ProductCardBinExample example = new ProductCardBinExample();
		example.createCriteria().andDataStateEqualTo(
				DataBaseConstant.DATA_STATE_NORMAL).andProductIdEqualTo(
				productCardBinDTO.getProductId()).andEffectEqualTo(
				DataBaseConstant.DEFAULT_FLAG_NO);

		return productCardBinDAO.countByExample(example);
	}

	private void modifyDefault(ProductCardBinDTO productCardBinDTO) {
		ProductCardBinExample example = new ProductCardBinExample();
		example.createCriteria().andDataStateEqualTo(
				DataBaseConstant.DATA_STATE_NORMAL).andProductIdEqualTo(
				productCardBinDTO.getProductId()).andEffectEqualTo(
				DataBaseConstant.DEFAULT_FLAG_YES);

		ProductCardBin record = new ProductCardBin();
		record.setEffect(DataBaseConstant.DEFAULT_FLAG_NO);
		productCardBinDAO.updateByExampleSelective(record, example);
	}

	public void insert(ProductCardBinDTO productCardBinDTO)
			throws BizServiceException {
		try {
			if (checkProductService(productCardBinDTO) == 0) {
				throw new BizServiceException("该产品还未添加账户，请先添加账户！");
			}			
			if (getDefault(productCardBinDTO) > 0) {
				throw new BizServiceException("该产品已添加了卡BIN，请生效或删除！");
			}
			if (checkRepeat(productCardBinDTO) > 0) {
				// throw new BizServiceException("产品下该卡BIN已存在！");
				throw new BizServiceException("该产品已添加了卡BIN，请不要重复添加！");
			}
			if (checkCardBINRepeat(productCardBinDTO.getCardBin()) > 0) {
				throw new BizServiceException("该卡BIN已经存在,不能重复添加！");
			}

			ProductCardBin productCardBin = new ProductCardBin();
			ReflectionUtil.copyProperties(productCardBinDTO, productCardBin);
			// if (DataBaseConstant.DEFAULT_FLAG_YES.equals(productCardBin
			// .getEffect())) {
			// this.modifyDefault(productCardBinDTO);
			// }
			// // 是否有效（同一产品只能有一个默认有效）
			// if (getDefault(productCardBinDTO) < 0) {
			// productCardBin.setEffect(DataBaseConstant.DEFAULT_FLAG_YES);
			// }
			productCardBin.setSerialNumber("0");
			productCardBin.setEffect(DataBaseConstant.DEFAULT_FLAG_NO);
			productCardBin.setCreateUser(productCardBinDTO.getLoginUserId());
			productCardBin.setCreateTime(DateUtil.getCurrentTime());
			productCardBin.setModifyUser(productCardBinDTO.getLoginUserId());
			productCardBin.setModifyTime(DateUtil.getCurrentTime());
			productCardBin.setDataState(DataBaseConstant.DATA_STATE_NORMAL);

			productCardBinDAO.insert(productCardBin);
		} catch (BizServiceException e) {
			throw e;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("添加卡BIN失败！");
		}
	}

	public void update(ProductCardBinDTO productCardBinDTO)
			throws BizServiceException {
		try {

			ProductCardBin productCardBin = new ProductCardBin();
			ReflectionUtil.copyProperties(productCardBinDTO, productCardBin);
			if (DataBaseConstant.DEFAULT_FLAG_YES.equals(productCardBin
					.getEffect())) {
				this.modifyDefault(productCardBinDTO);
			}
			if (getDefault(productCardBinDTO) < 0) {
				productCardBin.setEffect(DataBaseConstant.DEFAULT_FLAG_YES);
			}
			productCardBin.setModifyUser(productCardBinDTO.getLoginUserId());
			productCardBin.setModifyTime(DateUtil.getCurrentTime());
			productCardBinDAO.updateByPrimaryKeySelective(productCardBin);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("更新卡BIN状态失败！");
		}
	}

	public void updateSerialNumber(ProductCardBin productCardBin)
			throws BizServiceException {
		try {
			productCardBinDAO.updateByPrimaryKeySelective(productCardBin);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("更新卡BIN流水失败！");
		}
	}

	public void modifyDefaultState(ProductCardBinDTO productCardBinDTO)
			throws BizServiceException {
		try {
			//防止把RealCardBin更新为空字符串
			if(productCardBinDTO.getRealCardBin().equals("")){
				productCardBinDTO.setRealCardBin(null);
			}

			ProductCardBin productCardBin = new ProductCardBin();
			
			ReflectionUtil.copyProperties(productCardBinDTO, productCardBin);
			// 设为生效
			productCardBin.setEffect(DataBaseConstant.DEFAULT_FLAG_YES);
			// this.modifyDefault(productCardBinDTO);
			productCardBin.setModifyUser(productCardBinDTO.getLoginUserId());
			productCardBin.setModifyTime(DateUtil.getCurrentTime());
			productCardBinDAO.updateByPrimaryKeySelective(productCardBin);
			
			//获取当前产品的账户ID
			ProductServiceExample example = new ProductServiceExample();
			example.createCriteria().andProductIdEqualTo(productCardBinDTO.getProductId());
			List<ProductService> services = productServiceDAO.selectByExample(example);
			if(services != null){
				String[] acctypeIds = new String[services.size()];
				for(int i=0; i<services.size(); i++){
					ProductService productService = (ProductService)services.get(i);
					acctypeIds[i] = productService.getServiceId();					 
				}

				//准备查询语句的条件
				HashMap<String,Object> queryCond = new HashMap<String,Object>();
				queryCond.put("cardBin", productCardBinDTO.getCardBin());
				queryCond.put("acctypeIds", acctypeIds);
				
				List<?> accTypeContractList = commonsDAO.queryForList("ACCTYPE_CONTRACT.selectAccTypeContractByCardBin", queryCond);
				if(accTypeContractList != null)
					for(int i=0; i<accTypeContractList.size(); i++){
						AcctypeContract contract = new AcctypeContract();
						ReflectionUtil.copyProperties(accTypeContractList.get(i), contract);
						contract.setCardBin(productCardBinDTO.getCardBin());		
						contract.setCreateTime(DateUtil.getCurrentTime());
						contract.setCreateUser(productCardBinDTO.getLoginUserId());
						contract.setModifyTime(DateUtil.getCurrentTime());
						acctypeContractDAO.insert(contract);
					}
			}
			
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("更新卡BIN状态失败！");
		}
	}

	public void delete(ProductCardBinDTO productCardBinDTO)
			throws BizServiceException {
		try {
			ProductCardBin productCardBin = getProductCardBinByKey(productCardBinDTO);
			if (DataBaseConstant.DEFAULT_FLAG_YES.equals(productCardBin
					.getEffect())) {
				throw new BizServiceException("该卡BIN已生效，不能删除！");
			}

			// ReflectionUtil.copyProperties(productCardBinDTO, productCardBin);
			// productCardBin.setDataState(DataBaseConstant.DATA_STATE_DELETE);
			// productCardBinDAO.updateByPrimaryKeySelective(productCardBin);
			productCardBinDAO.deleteByPrimaryKey(productCardBin);
		} catch (BizServiceException be) {
			throw be;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("删除卡Bin失败！");
		}

	}

	private ProductCardBin getProductCardBinByKey(
			ProductCardBinDTO productCardBinDTO) {
		ProductCardBinKey key = new ProductCardBinKey();
		key.setProductId(productCardBinDTO.getProductId());
		key.setCardBin(productCardBinDTO.getCardBin());
		return productCardBinDAO.selectByPrimaryKey(key);
	}

	public ProductCardBinDTO view(ProductCardBinDTO productCardBinDTO)
			throws BizServiceException {
		try {
			ProductCardBin productCardBin = getProductCardBinByKey(productCardBinDTO);
			ReflectionUtil.copyProperties(productCardBin, productCardBinDTO);
			return productCardBinDTO;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查看卡BIN失败！");
		}
	}

	public ProductCardBin getProductCardBinForLock(String productId)
			throws BizServiceException {
		try {
			return (ProductCardBin) baseDAO.queryForObject(
					"PRODUCT.getProductCardBinSerialNumberForLock", productId);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询卡BIN失败！");
		}
	}

	public PageQueryDAO getPageQueryDAO() {
		return pageQueryDAO;
	}

	public void setPageQueryDAO(PageQueryDAO pageQueryDAO) {
		this.pageQueryDAO = pageQueryDAO;
	}

	public BaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	public ProductCardBinDAO getProductCardBinDAO() {
		return productCardBinDAO;
	}

	public void setProductCardBinDAO(ProductCardBinDAO productCardBinDAO) {
		this.productCardBinDAO = productCardBinDAO;
	}

	public StockOrderCommonService getStockOrderCommonService() {
		return stockOrderCommonService;
	}

	public void setStockOrderCommonService(
			StockOrderCommonService stockOrderCommonService) {
		this.stockOrderCommonService = stockOrderCommonService;
	}

	public CommonsDAO getCommonsDAO() {
		return commonsDAO;
	}

	public void setCommonsDAO(CommonsDAO commonsDAO) {
		this.commonsDAO = commonsDAO;
	}

	public ProductServiceDAO getProductServiceDAO() {
		return productServiceDAO;
	}

	public void setProductServiceDAO(ProductServiceDAO productServiceDAO) {
		this.productServiceDAO = productServiceDAO;
	}

	public AcctypeContractDAO getAcctypeContractDAO() {
		return acctypeContractDAO;
	}

	public void setAcctypeContractDAO(AcctypeContractDAO acctypeContractDAO) {
		this.acctypeContractDAO = acctypeContractDAO;
	}
	
	

}

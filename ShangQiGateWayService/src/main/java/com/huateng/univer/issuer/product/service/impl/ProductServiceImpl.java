package com.huateng.univer.issuer.product.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.issuer.dto.cardLayOut.CardLayoutDTO;
import com.allinfinance.univer.issuer.dto.procuctPackage.PackageDTO;
import com.allinfinance.univer.issuer.dto.product.ProdFaceValueDTO;
import com.allinfinance.univer.issuer.dto.product.ProductDTO;
import com.allinfinance.univer.issuer.dto.product.ProductQueryDTO;
import com.allinfinance.univer.issuer.dto.product.ValidPeriodRuleDspDTO;
import com.allinfinance.univer.issuer.dto.service.ServiceDTO;
import com.allinfinance.univer.issuer.dto.service.ServiceQueryDTO;
import com.allinfinance.univer.system.sysparam.dto.EntitySystemParameterDTO;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.constant.OrderConst;
import com.huateng.framework.dao.BaseDAO;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.dao.PageQueryDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.AcctypeContractDAO;
import com.huateng.framework.ibatis.dao.CaclDspDAO;
import com.huateng.framework.ibatis.dao.LoyaltyProdContractDAO;
import com.huateng.framework.ibatis.dao.ProdFaceValueDAO;
import com.huateng.framework.ibatis.dao.ProdLayoutDAO;
import com.huateng.framework.ibatis.dao.ProductCardBinDAO;
import com.huateng.framework.ibatis.dao.ProductDAO;
import com.huateng.framework.ibatis.dao.ProductPackageDAO;
import com.huateng.framework.ibatis.dao.ProductServiceDAO;
import com.huateng.framework.ibatis.dao.SellProdContractDAO;
import com.huateng.framework.ibatis.dao.SerivceFeeRuleDAO;
import com.huateng.framework.ibatis.model.AcctypeContractExample;
import com.huateng.framework.ibatis.model.CaclDsp;
import com.huateng.framework.ibatis.model.CaclDspExample;
import com.huateng.framework.ibatis.model.LoyaltyProdContractExample;
import com.huateng.framework.ibatis.model.ProdFaceValue;
import com.huateng.framework.ibatis.model.ProdFaceValueExample;
import com.huateng.framework.ibatis.model.ProdLayout;
import com.huateng.framework.ibatis.model.ProdLayoutExample;
import com.huateng.framework.ibatis.model.Product;
import com.huateng.framework.ibatis.model.ProductCardBin;
import com.huateng.framework.ibatis.model.ProductCardBinExample;
import com.huateng.framework.ibatis.model.ProductExample;
import com.huateng.framework.ibatis.model.ProductPackage;
import com.huateng.framework.ibatis.model.ProductPackageExample;
import com.huateng.framework.ibatis.model.ProductServiceExample;
import com.huateng.framework.ibatis.model.SellProdContractExample;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.ReflectionUtil;
import com.huateng.univer.batchfile.action.BatchFileActionInterface;
import com.huateng.univer.issuer.account.dao.IssuerServiceDao;
import com.huateng.univer.issuer.entityBaseInfo.dao.EntityBaseInfoServiceDao;
import com.huateng.univer.issuer.product.dao.ProdAccLayPackServiceDAO;
import com.huateng.univer.issuer.product.service.ProductService;
import com.huateng.univer.issuer.productcardbin.service.ProductCardBinService;
import com.huateng.univer.issuer.validPeriodRule.dao.ValidPeriodRuleServiceDAO;

public class ProductServiceImpl implements ProductService {

	Logger logger = Logger.getLogger(ProductServiceImpl.class);
	private EntityBaseInfoServiceDao entityBaseInfoServiceDAO;
	private BaseDAO baseDAO;
	/**
	 * 分页查询DAO
	 */
	private PageQueryDAO pageQueryDAO;
	/**
	 * 产品信息DAO
	 */
	private ProductDAO productDAO;

	private IssuerServiceDao issuerServiceDAO;

	private ProductServiceDAO productServiceDAO;

	private ProdFaceValueDAO prodFaceValueDAO;

	private ProdLayoutDAO prodLayoutDAO;

	private ProductPackageDAO prodPackageDAO;

	private LoyaltyProdContractDAO loyaltyProdContractDAO;

	private SellProdContractDAO sellProdContractDAO;

	private ProductCardBinService productCardBinService;

	private SerivceFeeRuleDAO serviceFeeRuleDAO;

	private CaclDspDAO caclDspDAO;

	private ValidPeriodRuleServiceDAO validPeriodRuleServiceDAO;
	
	private ProductCardBinDAO productCardBinDAO;
	
	private AcctypeContractDAO acctypeContractDAO;
	private BatchFileActionInterface batchFileService;

	public ValidPeriodRuleServiceDAO getValidPeriodRuleServiceDAO() {
		return validPeriodRuleServiceDAO;
	}

	public void setValidPeriodRuleServiceDAO(
			ValidPeriodRuleServiceDAO validPeriodRuleServiceDAO) {
		this.validPeriodRuleServiceDAO = validPeriodRuleServiceDAO;
	}

	public EntityBaseInfoServiceDao getEntityBaseInfoServiceDAO() {
		return entityBaseInfoServiceDAO;
	}

	public void setEntityBaseInfoServiceDAO(
			EntityBaseInfoServiceDao entityBaseInfoServiceDAO) {
		this.entityBaseInfoServiceDAO = entityBaseInfoServiceDAO;
	}

	public CaclDspDAO getCaclDspDAO() {
		return caclDspDAO;
	}

	public void setCaclDspDAO(CaclDspDAO caclDspDAO) {
		this.caclDspDAO = caclDspDAO;
	}

	public SellProdContractDAO getSellProdContractDAO() {
		return sellProdContractDAO;
	}

	public void setSellProdContractDAO(SellProdContractDAO sellProdContractDAO) {
		this.sellProdContractDAO = sellProdContractDAO;
	}

	public LoyaltyProdContractDAO getLoyaltyProdContractDAO() {
		return loyaltyProdContractDAO;
	}

	public void setLoyaltyProdContractDAO(
			LoyaltyProdContractDAO loyaltyProdContractDAO) {
		this.loyaltyProdContractDAO = loyaltyProdContractDAO;
	}

	public ProductPackageDAO getProdPackageDAO() {
		return prodPackageDAO;
	}

	public void setProdPackageDAO(ProductPackageDAO prodPackageDAO) {
		this.prodPackageDAO = prodPackageDAO;
	}

	public ProdLayoutDAO getProdLayoutDAO() {
		return prodLayoutDAO;
	}

	public void setProdLayoutDAO(ProdLayoutDAO prodLayoutDAO) {
		this.prodLayoutDAO = prodLayoutDAO;
	}

	public ProductServiceDAO getProductServiceDAO() {
		return productServiceDAO;
	}

	public void setProductServiceDAO(ProductServiceDAO productServiceDAO) {
		this.productServiceDAO = productServiceDAO;
	}

	private ProdAccLayPackServiceDAO prodAccLayPackServiceDAO;

	public ProdAccLayPackServiceDAO getProdAccLayPackServiceDAO() {
		return prodAccLayPackServiceDAO;
	}

	public void setProdAccLayPackServiceDAO(
			ProdAccLayPackServiceDAO prodAccLayPackServiceDAO) {
		this.prodAccLayPackServiceDAO = prodAccLayPackServiceDAO;
	}

	public IssuerServiceDao getIssuerServiceDAO() {
		return issuerServiceDAO;
	}

	public void setIssuerServiceDAO(IssuerServiceDao issuerServiceDAO) {
		this.issuerServiceDAO = issuerServiceDAO;
	}

	public ProductDAO getProductDAO() {
		return productDAO;
	}

	public void setProductDAO(ProductDAO productDAO) {
		this.productDAO = productDAO;
	}

	public PageQueryDAO getPageQueryDAO() {
		return pageQueryDAO;
	}

	public void setPageQueryDAO(PageQueryDAO pageQueryDAO) {
		this.pageQueryDAO = pageQueryDAO;
	}

	public CommonsDAO getCommonsDAO() {
		return commonsDAO;
	}

	public void setCommonsDAO(CommonsDAO commonsDAO) {
		this.commonsDAO = commonsDAO;
	}

	public ProdFaceValueDAO getProdFaceValueDAO() {
		return prodFaceValueDAO;
	}

	public void setProdFaceValueDAO(ProdFaceValueDAO prodFaceValueDAO) {
		this.prodFaceValueDAO = prodFaceValueDAO;
	}
	
	public AcctypeContractDAO getAcctypeContractDAO() {
		return acctypeContractDAO;
	}

	public void setAcctypeContractDAO(AcctypeContractDAO acctypeContractDAO) {
		this.acctypeContractDAO = acctypeContractDAO;
	}

	/**
	 * 公共工具类DAO
	 */

	private CommonsDAO commonsDAO;	
	
	public ProductCardBinDAO getProductCardBinDAO() {
		return productCardBinDAO;
	}

	public void setProductCardBinDAO(ProductCardBinDAO productCardBinDAO) {
		this.productCardBinDAO = productCardBinDAO;
	}

	public void deleteAcctype(ProductDTO productDTO) throws BizServiceException {
		ProductCardBinExample productCardBin = new ProductCardBinExample();
		productCardBin.createCriteria().andDataStateEqualTo(
				DataBaseConstant.DATA_STATE_NORMAL).andProductIdEqualTo(
						productDTO.getProductId()).andEffectEqualTo(
				DataBaseConstant.DEFAULT_FLAG_YES);
		if( productCardBinDAO.countByExample(productCardBin)>0)
			throw new BizServiceException("该产品卡BIN已经激活，不能删除服务!");
		
		try {
			List<ServiceDTO> acctypeDTOs = productDTO.getServices();
			for (ServiceDTO acctypeDTO : acctypeDTOs) {
				ProductServiceExample example = new ProductServiceExample();
				example.createCriteria().andProductIdEqualTo(
						productDTO.getProductId()).andServiceIdEqualTo(
						acctypeDTO.getServiceId());
				productServiceDAO.deleteByExample(example);
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("删除帐户信息失败~！");
		}

	}

	public void deleteProdLayout(ProductDTO productDTO)
			throws BizServiceException {
		try {
			List<ProdLayout> prodLayouts = new ArrayList<ProdLayout>();
			List<CardLayoutDTO> cardLayoutDTOs = productDTO.getCardLayoutDTOs();
			for (CardLayoutDTO cld : cardLayoutDTOs) {
				ProdLayout plo = new ProdLayout();
				plo.setRelId(cld.getRelId());
				prodLayouts.add(plo);
			}
			commonsDAO.batchDelete(
					"TB_REL_PROD_LAYOUT.abatorgenerated_deleteByPrimaryKey",
					prodLayouts);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("删除卡面信息失败~！");
		}

	}

	public void deleteProdPackage(ProductDTO productDTO)
			throws BizServiceException {
		try {
			List<ProductPackage> prodPackages = new ArrayList<ProductPackage>();
			List<PackageDTO> packageDTOs = productDTO.getPackages();
			for (PackageDTO pkd : packageDTOs) {
				ProductPackage packag = new ProductPackage();
				packag.setRelId(pkd.getRelId());
				prodPackages.add(packag);
			}
			commonsDAO
					.batchDelete(
							"TB_REL_PRODUCT_PACKAGE.abatorgenerated_deleteByPrimaryKey",
							prodPackages);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("删除包装信息失败~！");
		}

	}

	public void deleteProduct(ProductDTO productDTO) throws BizServiceException {
		try {
			for (ProductDTO productDto : productDTO.getProductDTOs()) {
				// 如果产品跟发行机构合同关联，则不能被删除
				LoyaltyProdContractExample example = new LoyaltyProdContractExample();
				example.createCriteria().andDataStateEqualTo(
						DataBaseConstant.DATA_STATE_NORMAL)
						.andProductIdEqualTo(productDto.getProductId());
				if (loyaltyProdContractDAO.selectByExample(example).size() > 0) {
					throw new BizServiceException("产品编号:"
							+ productDto.getProductId() + "与发行机构合同相关联不能被删除!");
				}
				// 如果产品跟营销机构合同关联，则不能被删除
				SellProdContractExample sellExample = new SellProdContractExample();
				sellExample.createCriteria().andDataStateEqualTo(
						DataBaseConstant.DATA_STATE_NORMAL)
						.andProductIdEqualTo(productDto.getProductId());
				if (sellProdContractDAO.selectByExample(sellExample).size() > 0) {
					throw new BizServiceException("产品编号:"
							+ productDto.getProductId() + "与营销机构合同相关联不能被删除!");
				}

				/**
				 * 删除产品与服务的关系
				 * 
				 */
				ProductServiceExample prodAcctypeEx = new ProductServiceExample();
				prodAcctypeEx.createCriteria().andProductIdEqualTo(
						productDto.getProductId());
				productServiceDAO.deleteByExample(prodAcctypeEx);

				/**
				 * 删除产品与卡面关系
				 */
				ProdLayoutExample layoutEx = new ProdLayoutExample();
				layoutEx.createCriteria().andProductIdEqualTo(
						productDto.getProductId());
				prodLayoutDAO.deleteByExample(layoutEx);

				/**
				 * 删除产品与包装的关系
				 * 
				 */
				ProductPackageExample prodPackageEx = new ProductPackageExample();
				prodPackageEx.createCriteria().andProductIdEqualTo(
						productDto.getProductId());
				prodPackageDAO.deleteByExample(prodPackageEx);

				/**
				 * 删除产品与面额的关系
				 * 
				 */
				ProdFaceValueExample prodFaceValueEx = new ProdFaceValueExample();
				prodFaceValueEx.createCriteria().andProductIdEqualTo(
						productDto.getProductId());
				prodFaceValueDAO.deleteByExample(prodFaceValueEx);
				
				/**
				 * 删除账户合同表中与该产品卡BIN关联的记录
				 */
				//查找该产品对应的已生效的卡BIN
				ProductCardBinExample productCardBinExample = new ProductCardBinExample();
				productCardBinExample.createCriteria().andDataStateEqualTo(
						DataBaseConstant.DATA_STATE_NORMAL).andProductIdEqualTo(
								productDto.getProductId()).andEffectEqualTo(
						DataBaseConstant.DEFAULT_FLAG_YES);
				List<ProductCardBin> productCardBinList = productCardBinDAO.selectByExample(productCardBinExample);
				//如果该产品有已生效的卡BIN，则删除账户合同信息表中相关记录
				if(productCardBinList != null && productCardBinList.size()>0){
					ProductCardBin productCardBin = productCardBinList.get(0);
					if(productCardBin != null){
						AcctypeContractExample accTypeContractExample = new AcctypeContractExample();
						accTypeContractExample.createCriteria().andCardBinEqualTo(productCardBin.getCardBin());
						acctypeContractDAO.deleteByExample(accTypeContractExample);
					}
				}

				/**
				 * 更新产品，把状态设为无效
				 */
				Product product = productDAO.selectByPrimaryKey(productDto
						.getProductId());
				product.setDataState(DataBaseConstant.DATA_STATE_DELETE);
				product.setModifyTime(DateUtil.getCurrentTime());
				product.setModifyUser(productDTO.getModifyUser());
				productDAO.updateByPrimaryKey(product);
			}
		} catch (BizServiceException e) {
			throw e;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("删除产品信息失败~！");
		}

	}

	public ProductDTO initProduct() throws BizServiceException {
		
		return null;
	}

	public PageDataDTO inqueryProduct(ProductQueryDTO productQueryDTO)
			throws BizServiceException {
		try {
			 if (productQueryDTO.getEntityId() != null
			 && !productQueryDTO.getEntityId().equals("")) {
			 productQueryDTO.setDefaultEntityId(null);
			 }
	
			productQueryDTO.setSort("desc");
			productQueryDTO.setSortFieldName("productId");
			PageDataDTO pdd = pageQueryDAO.query("PRODUCT.selectProduct",
					productQueryDTO);
			return pdd;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询产品信息失败~！");
		}
	}

	/**
	 * 查询实体下的产品
	 * 
	 * @param entityId
	 * @return
	 */
	public List<ProductDTO> getProductsByEntityId(String entityId)
			throws BizServiceException {
		try {
			ProductExample example = new ProductExample();
			example.createCriteria().andEntityIdEqualTo(entityId)
					.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
			List<ProductDTO> productDTOs = new ArrayList<ProductDTO>();
			List<Product> products = productDAO.selectByExample(example);
			for (Product p : products) {
				ProductDTO dto = new ProductDTO();
				ReflectionUtil.copyProperties(p, dto);
				dto = this.viewProduct(dto);
				productDTOs.add(dto);
			}
			return productDTOs;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询实体下产品信息失败~！");
		}
	}

	public List<ProductDTO> getProductsForStockOrder1(String entityId)
			throws BizServiceException {
		try {
			// 自己的产品，签订合同的代发卡产品
			// 署名状态：可选
			ProductExample example = new ProductExample();
			example.createCriteria().andEntityIdEqualTo(entityId)
					.andProductDefineIssuerEqualTo(entityId)
					.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL)
					.andOnymousStatEqualTo(OrderConst.PRODUCT_ONYMOUS_STAT_CAN);
			List<ProductDTO> productDTOs = new ArrayList<ProductDTO>();
			List<Product> products = productDAO.selectByExample(example);
			for (Product p : products) {
				ProductDTO dto = new ProductDTO();
				ReflectionUtil.copyProperties(p, dto);
				dto = this.viewProduct(dto);
				productDTOs.add(dto);
			}
			return productDTOs;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询产品信息失败~！");
		}
	}

	public List<ProductDTO> getProductsForStockOrder(String entityId)
			throws BizServiceException {
		try {
			List<ProductDTO> productDTOs = baseDAO.queryForList(
					"PRODUCT.selectProductForStockOrder", entityId);
			List<ProductDTO> resultDTOs = new LinkedList<ProductDTO>();
			for (ProductDTO dto : productDTOs) {
				dto = this.viewProduct(dto);
				resultDTOs.add(dto);
			}
			return resultDTOs;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询产品信息失败~！");
		}
	}

	public PageDataDTO inqueryProductC(ProductQueryDTO productQueryDTO)
			throws BizServiceException {
		
		return null;
	}

	public PageDataDTO inqueryProductD(ProductQueryDTO productQueryDTO)
			throws BizServiceException {
		
		return null;
	}

	public List<ProductDTO> inqueryProductList(Long productId)
			throws BizServiceException {
		
		return null;
	}

	public void insertProdLayout(ProductDTO productDTO)
			throws BizServiceException {
		try {
			ProdLayout prodLayout = new ProdLayout();
			ReflectionUtil.copyProperties(productDTO, prodLayout);
			prodLayout.setRelId(commonsDAO
					.getNextValueOfSequence("TB_REL_PROD_LAYOUT"));
			prodLayoutDAO.insert(prodLayout);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("添加卡面信息失败~！");
		}

	}

	public void insertProdPackage(ProductDTO productDTO)
			throws BizServiceException {
		try {
			ProductPackage prodPackage = new ProductPackage();
			ReflectionUtil.copyProperties(productDTO, prodPackage);
			prodPackage.setRelId(commonsDAO
					.getNextValueOfSequence("TB_REL_PRODUCT_PACKAGE"));
			prodPackageDAO.insert(prodPackage);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("添加包装信息失败~！");
		}

	}

	public ProductDTO insertProduct(ProductDTO productDTO)
			throws BizServiceException {
		try {/*
			 * ProductExample productExample = new ProductExample();
			 * productExample.createCriteria().andDataStateEqualTo(
			 * DataBaseConstant.DATA_STATE_NORMAL).andProductNameEqualTo(
			 * productDTO.getProductName()); List<Product> products =
			 * productDAO.selectByExample(productExample); if (products.size() >
			 * 0) { throw new BizServiceException(productDTO.getProductName() +
			 * " 产品名称已存在!"); }
			 */
			Product product = new Product();
			ReflectionUtil.copyProperties(productDTO, product);
			String id = commonsDAO.getNextValueOfSequence("TB_PRODUCT");
			EntitySystemParameterDTO entitySystemParameterDTO = new EntitySystemParameterDTO();
			entitySystemParameterDTO.setEntityId(productDTO
					.getDefaultEntityId());
			entitySystemParameterDTO.setParameterRole("1");
			System.out.println(entityBaseInfoServiceDAO+"@@@@@@@@@@@");
			List<EntitySystemParameterDTO> list = entityBaseInfoServiceDAO
					.getEntitySystemParameter(entitySystemParameterDTO);
			System.out.println("list is"+list.size()+"============");
			if (null != list && list.size() > 0) {
				for (EntitySystemParameterDTO sysparam : list) {
					if ("PWD_KEY_INDEX".equals(sysparam.getParameterCode())) {
						product.setPwdKeyIndex(sysparam.getParameterValue());
					}
					if ("CVV_KEY_INDEX".equals(sysparam.getParameterCode())) {
						product.setCvvKeyIndex(sysparam.getParameterValue());
					}
					if ("BLN_KEY_INDEX".equals(sysparam.getParameterCode())) {
						product.setBlnKeyIndex(sysparam.getParameterValue());
					}
				}
			}

			product.setProductId(id);
			product.setCreateTime(DateUtil.getCurrentTime());
			product.setProductDefineIssuer(productDTO.getDefaultEntityId());
			product.setProdStat("1");
			product.setModifyUser(productDTO.getLoginUserId());
			product.setModifyTime(product.getCreateTime());
			product.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
			productDAO.insert(product);
			productDTO = new ProductDTO();
			ReflectionUtil.copyProperties(product, productDTO);

			return productDTO;
		} catch (BizServiceException e) {
			throw e;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("添加产品信息失败~！");
		}
	}

	public void modifyStateProduct(ProductDTO productDTO)
			throws BizServiceException {
		

	}

	public void updateProduct(ProductDTO productDTO) throws BizServiceException {
		try {
			Product product = new Product();
			ReflectionUtil.copyProperties(productDTO, product);
			product.setModifyTime(DateUtil.getCurrentTime());
			productDAO.updateByPrimaryKeySelective(product);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("修改产品信息失败~！");
		}

	}

	public ProductDTO viewProduct(ProductDTO productDTO)
			throws BizServiceException {
		try {
			Product product = productDAO.selectByPrimaryKey(productDTO
					.getProductId());
			ProductDTO productDto = new ProductDTO();
			ReflectionUtil.copyProperties(product, productDto);
			/**
			 * 加载产品面额
			 */

			ProdFaceValueExample prodFaceValueEx = new ProdFaceValueExample();
			prodFaceValueEx.createCriteria().andProductIdEqualTo(
					productDTO.getProductId()).andDataStateEqualTo(
					DataBaseConstant.DATA_STATE_NORMAL);
			List<ProdFaceValue> prodFaceValues = prodFaceValueDAO
					.selectByExample(prodFaceValueEx);
			List<ProdFaceValueDTO> prodFaceValueDTOs = new ArrayList<ProdFaceValueDTO>();
			for (ProdFaceValue pfv : prodFaceValues) {
				ProdFaceValueDTO prodFaceValueDTO = new ProdFaceValueDTO();
				ReflectionUtil.copyProperties(pfv, prodFaceValueDTO);
				prodFaceValueDTOs.add(prodFaceValueDTO);
			}
			productDto.setProdFaceValueDTO(prodFaceValueDTOs);

			/**
			 * 加载卡面信息
			 */
			productDto.setCardLayoutDTOs(prodAccLayPackServiceDAO
					.getCardLayoutDTOs(productDto));
			/**
			 * 加载服务信息
			 */
			productDto.setServices(prodAccLayPackServiceDAO
					.getProdAcctypeDTOs(productDto));
			/**
			 * 加载包装信息
			 */
			productDto.setPackages(prodAccLayPackServiceDAO
					.getProdPackageDTOs(productDto));

			/**
			 * 添加卡BIN信息
			 */
			productDto.setProductCardBinDTOs(productCardBinService
					.getCardBinByProductId(productDTO.getProductId()));

			// 如果该产品已经有了合同(跟发行机构和营销机构的合同)，那么此时的账户是不能编辑的。
			LoyaltyProdContractExample example = new LoyaltyProdContractExample();
			example.createCriteria().andDataStateEqualTo(
					DataBaseConstant.DATA_STATE_NORMAL).andProductIdEqualTo(
					productDto.getProductId());

			SellProdContractExample sellExample = new SellProdContractExample();
			sellExample.createCriteria().andDataStateEqualTo(
					DataBaseConstant.DATA_STATE_NORMAL).andProductIdEqualTo(
					productDto.getProductId());

			if (loyaltyProdContractDAO.selectByExample(example).size() > 0
					|| sellProdContractDAO.selectByExample(sellExample).size() > 0) {
				productDto.setUpdateFlag("disabled");
			} else {
				productDto.setUpdateFlag("");
			}
			return productDto;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("获取产品信息失败~！");
		}
	}

	public ProductDTO viewProductForContract(ProductDTO productDTO)
			throws BizServiceException {
		try {
			Product product = productDAO.selectByPrimaryKey(productDTO
					.getProductId());
			ProductDTO productDto = new ProductDTO();
			ReflectionUtil.copyProperties(product, productDto);

			/**
			 * 加载服务信息
			 */
			productDto.setServices(prodAccLayPackServiceDAO
					.getProdAcctypeDTOs(productDto));

			/**
			 * 添加卡BIN信息
			 */
			productDto.setProductCardBinDTOs(productCardBinService
					.getCardBinByProductId(productDTO.getProductId()));

			/**
			 * 默认账户计算规则
			 */
			CaclDspExample caclDspExample = new CaclDspExample();

			caclDspExample.createCriteria().andRecUpdUsrIdEqualTo(
					productDTO.getDefaultEntityId()).andDataStatEqualTo(
					DataBaseConstant.RULE_STATE_ENABLE);

			List<CaclDsp> lstCaclDsp = caclDspDAO
					.selectByExample(caclDspExample);

			if (null != lstCaclDsp && lstCaclDsp.size() > 0) {
				productDTO.setDefaultRuleNo(lstCaclDsp.get(0).getDiscCd());
			}

			// SerivceFeeRuleExample example = new SerivceFeeRuleExample();
			// example.createCriteria().andStateEqualTo(
			// DataBaseConstant.RULE_STATE_ENABLE).andEntityIdEqualTo(
			// productDTO.getDefaultEntityId());
			// List<SerivceFeeRule> serivceFeeRules = serviceFeeRuleDAO
			// .selectByExample(example);
			//			
			// if(null!=serivceFeeRules && serivceFeeRules.size()>0){
			// productDto.setDefaultRuleNo(serivceFeeRules.get(0).getRuleNo());
			// }

			return productDto;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("获取产品信息失败~！");
		}
	}

	public List<ProductQueryDTO> getProductIssuerInfoList(
			ProductQueryDTO productQueryDTO) throws BizServiceException {
		try {
			List<ProductQueryDTO> issuerDTO = issuerServiceDAO
					.getIssuerProductInfoList(productQueryDTO);
			return issuerDTO;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询发行机构失败~！");
		}
	}

	public List<ValidPeriodRuleDspDTO> getValidPeriodRuleList(
			ProductQueryDTO productQueryDTO) throws BizServiceException {
		List<ValidPeriodRuleDspDTO> validPeriodRuleDspDTOs = validPeriodRuleServiceDAO
				.getList(productQueryDTO);
		return validPeriodRuleDspDTOs;
	}

	public void inserProdFaceValue(ProdFaceValueDTO prodFaceValueDTO)
			throws BizServiceException {
		try {
			ProdFaceValueExample prodFaceExample = new ProdFaceValueExample();
			if (prodFaceValueDTO.getFaceValueType().equals("0")) {
				prodFaceExample.createCriteria().andDataStateEqualTo(
						DataBaseConstant.DATA_STATE_NORMAL)
						.andFaceValueEqualTo(prodFaceValueDTO.getFaceValue())
						.andProductIdEqualTo(prodFaceValueDTO.getProductId());
				List<ProdFaceValue> productFaces = prodFaceValueDAO
						.selectByExample(prodFaceExample);
				if (productFaces.size() > 0) {
					throw new BizServiceException((new BigDecimal(
							prodFaceValueDTO.getFaceValue())
							.divide(new BigDecimal("100")))
							+ " 固定面额已存在!");
				}
			}

			ProdFaceValue prodFaceValue = new ProdFaceValue();
			ReflectionUtil.copyProperties(prodFaceValueDTO, prodFaceValue);
			String id = commonsDAO.getNextValueOfSequence("TB_PROD_FACE_VALUE");
			prodFaceValue.setFaceValueId(id);
			prodFaceValue.setCreateTime(DateUtil.getCurrentTime());
			prodFaceValue.setModifyTime(DateUtil.getCurrentTime());
			prodFaceValue.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
			prodFaceValueDAO.insert(prodFaceValue);
		} catch (BizServiceException e) {
			throw e;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("添加面额信息失败~！");
		}

	}

	public void deleteProdFaceValue(ProdFaceValueDTO prodFaceValueDTO)
			throws BizServiceException {
		try {
			List<ProdFaceValue> prodFaceValues = new ArrayList<ProdFaceValue>();
			for (ProdFaceValueDTO pfvd : prodFaceValueDTO.getProdFaceValueDTO()) {
				ProdFaceValue prodFaceValue = new ProdFaceValue();
				prodFaceValue.setFaceValueId(pfvd.getFaceValueId());
				prodFaceValue.setDataState(DataBaseConstant.DATA_STATE_DELETE);
				prodFaceValueDAO.deleteByPrimaryKey(prodFaceValue
						.getFaceValueId());

				prodFaceValues.add(prodFaceValue);
			}
			commonsDAO
					.batchUpdate(
							"TB_PROD_FACE_VALUE.abatorgenerated_updateByPrimaryKeySelective",
							prodFaceValues);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("删除面额信息失败~！");
		}

	}

	public String inqueryProductRelAccount(ServiceQueryDTO serviceQueryDTO)
			throws BizServiceException {
		try {
			String count = "";
			ProductServiceExample example = new ProductServiceExample();
			example.createCriteria().andProductIdEqualTo(
					serviceQueryDTO.getProductId());
			if (productServiceDAO.countByExample(example) > 0) {
				count = "1";
			} else {
				count = "0";
			}
			return count;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询产品账户关联信息失败~！");
		}
	}

	public ProductCardBinService getProductCardBinService() {
		return productCardBinService;
	}

	public void setProductCardBinService(
			ProductCardBinService productCardBinService) {
		this.productCardBinService = productCardBinService;
	}

	public SerivceFeeRuleDAO getServiceFeeRuleDAO() {
		return serviceFeeRuleDAO;
	}

	public void setServiceFeeRuleDAO(SerivceFeeRuleDAO serviceFeeRuleDAO) {
		this.serviceFeeRuleDAO = serviceFeeRuleDAO;
	}

	public BaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	public List<ProductDTO> getProductsBySellId(String entityId)
			throws BizServiceException {
		List<ProductDTO> productDTOs = prodAccLayPackServiceDAO
				.getProductDTOs(entityId);
		return productDTOs;
	}

	public BatchFileActionInterface getBatchFileService() {
		return batchFileService;
	}

	public void setBatchFileService(BatchFileActionInterface batchFileService) {
		this.batchFileService = batchFileService;
	}

}

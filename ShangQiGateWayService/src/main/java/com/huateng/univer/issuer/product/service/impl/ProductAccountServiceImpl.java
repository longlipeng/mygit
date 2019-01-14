package com.huateng.univer.issuer.product.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.allinfinance.univer.issuer.dto.product.ProdServiceDTO;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.ProductCardBinDAO;
import com.huateng.framework.ibatis.dao.ProductDAO;
import com.huateng.framework.ibatis.dao.ProductServiceDAO;
import com.huateng.framework.ibatis.model.Product;
import com.huateng.framework.ibatis.model.ProductCardBinExample;
import com.huateng.framework.ibatis.model.ProductService;
import com.huateng.framework.ibatis.model.ProductServiceExample;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.ReflectionUtil;
import com.huateng.univer.issuer.product.dao.ProdAccLayPackServiceDAO;
import com.huateng.univer.issuer.product.service.ProductAccountService;

/**
 * 
 * @author fengfeng.shi
 * 
 */
public class ProductAccountServiceImpl implements ProductAccountService {
	Logger logger = Logger.getLogger(ProductAccountServiceImpl.class);
	/**
	 * 产品信息DAO
	 */
	private ProductDAO productDAO;

	private ProductServiceDAO productServiceDAO;

	private CommonsDAO commonsDAO;

	private ProdAccLayPackServiceDAO prodAccLayPackServiceDAO;
	
	private ProductCardBinDAO productCardBinDAO;

	public ProdAccLayPackServiceDAO getProdAccLayPackServiceDAO() {
		return prodAccLayPackServiceDAO;
	}

	public void setProdAccLayPackServiceDAO(
			ProdAccLayPackServiceDAO prodAccLayPackServiceDAO) {
		this.prodAccLayPackServiceDAO = prodAccLayPackServiceDAO;
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

	public ProductDAO getProductDAO() {
		return productDAO;
	}

	public void setProductDAO(ProductDAO productDAO) {
		this.productDAO = productDAO;
	}	
	
	public ProductCardBinDAO getProductCardBinDAO() {
		return productCardBinDAO;
	}

	public void setProductCardBinDAO(ProductCardBinDAO productCardBinDAO) {
		this.productCardBinDAO = productCardBinDAO;
	}

	public void deleteProductAcctype(ProdServiceDTO prodAcctypeDto)
			throws BizServiceException {
		

	}

	public void inserProductAcctype(ProdServiceDTO prodAcctypeDto)
			throws BizServiceException {
		
		ProductCardBinExample example = new ProductCardBinExample();
		example.createCriteria().andDataStateEqualTo(
				DataBaseConstant.DATA_STATE_NORMAL).andProductIdEqualTo(
				prodAcctypeDto.getProductId()).andEffectEqualTo(
				DataBaseConstant.DEFAULT_FLAG_YES);
		if( productCardBinDAO.countByExample(example)>0)
			throw new BizServiceException("该产品卡BIN已经激活，不能添加新的服务!");
		
		try {
			// Product
			// product=productDAO.selectByPrimaryKey(prodAcctypeDto.getProductId());
			// 判断其是否为充值卡，如果是充值卡那么只能加最多10个服务
			// if(product.getProductType().equals("1")){
			// ProductServiceExample example=new ProductServiceExample();
			// example.createCriteria().andProductIdEqualTo(prodAcctypeDto.getProductId());
			// if(productServiceDAO.selectByExample(example).size()<10){
			//	    			
			// }else{
			// throw new BizServiceException("添加失败,一个充值卡最多只能添加十个服务!");
			// }
			// }else{
			// ProductServiceExample example=new ProductServiceExample();
			// example.createCriteria().andProductIdEqualTo(prodAcctypeDto.getProductId());
			// if(productServiceDAO.selectByExample(example).size()<1){
			//	    			
			// }else{
			// throw new BizServiceException("添加失败,一个礼品卡最多对应一个服务!");
			// }
			// }

			ProductService prodAcctype = new ProductService();
			ReflectionUtil.copyProperties(prodAcctypeDto, prodAcctype);
			prodAcctype.setRelId(commonsDAO
					.getNextValueOfSequence("TB_REL_PRODUCT_SERVICE"));
			productServiceDAO.insert(prodAcctype);
		} catch (BizServiceException e) {
			throw e;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("添加帐户信息失败！");
		}

	}

	public void updateProductAcctype(ProdServiceDTO prodAcctypeDto)
			throws BizServiceException {
		try {
			/*
			 * 更新账户的时候，判断该账户是否是默认账户， 如果是且要修改此默认账户，那么必须先选其他的一个账户作为默认账户，
			 * 一个产品只能有一个默认账户
			 */
			ProductService service = productServiceDAO
					.selectByPrimaryKey(prodAcctypeDto.getRelId());
			if (service.getDefaultFlag().equals("1")) {
				if (!prodAcctypeDto.getDefaultFlag().equals("1")) {
					throw new BizServiceException("该帐户: "
							+ service.getServiceId()
							+ "为默认账户，如需修改此默认账户请先设置其他账户为默认账户！");
				}
			} else {
				ProductServiceExample example = new ProductServiceExample();
				List<String> s = new ArrayList<String>();
				s.add(prodAcctypeDto.getServiceId());
				example.createCriteria().andProductIdEqualTo(
						prodAcctypeDto.getProductId()).andServiceIdNotIn(s);
				ProductService record = new ProductService();
				record.setDefaultFlag("0");
				productServiceDAO.updateByExampleSelective(record, example);
			}
			// 产品信息更新
			Product product = new Product();
			product.setProductId(prodAcctypeDto.getProductId());
			product.setModifyTime(DateUtil.getCurrentTime());
			product.setModifyUser(prodAcctypeDto.getLoginUserId());
			productDAO.updateByPrimaryKeySelective(product);

			ProductService prodAcctype = new ProductService();
			prodAcctype.setDefaultFlag(prodAcctypeDto.getDefaultFlag());
			prodAcctype.setMaxDayTxnAmt(prodAcctypeDto.getMaxDayTxnAmt());
			prodAcctype.setMaxTxnAmt(prodAcctypeDto.getMaxTxnAmt());
			prodAcctype.setWebMaxDayTxnAmt(prodAcctypeDto.getWebMaxDayTxnAmt());
			prodAcctype.setWebMaxTxnAmt(prodAcctypeDto.getWebMaxTxnAmt());
			prodAcctype.setServiceFee(prodAcctypeDto.getServiceFee());
			prodAcctype.setRelId(prodAcctypeDto.getRelId());
			productServiceDAO.updateByPrimaryKeySelective(prodAcctype);
		} catch (BizServiceException e) {
			throw e;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("修改帐户信息失败~！");
		}

	}

	public ProdServiceDTO viewProductAcctype(ProdServiceDTO prodAcctypeDto)
			throws BizServiceException {
		try {
			ProdServiceDTO productService = prodAccLayPackServiceDAO
					.getProductServiceByProIdAndServiceId(prodAcctypeDto);
			return productService;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("获取账户信息失败~！");
		}
	}

}

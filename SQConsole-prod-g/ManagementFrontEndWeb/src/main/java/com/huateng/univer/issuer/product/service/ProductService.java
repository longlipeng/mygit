package com.huateng.univer.issuer.product.service;

import java.util.List;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.issuer.dto.product.ProdFaceValueDTO;
import com.allinfinance.univer.issuer.dto.product.ProdServiceDTO;
import com.allinfinance.univer.issuer.dto.product.ProductDTO;
import com.allinfinance.univer.issuer.dto.product.ProductQueryDTO;
import com.allinfinance.univer.issuer.dto.product.ValidPeriodRuleDspDTO;
import com.allinfinance.univer.issuer.dto.service.ServiceQueryDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderInputDTO;
import com.huateng.framework.exception.BizServiceException;

public interface ProductService {
	/**
	 * 查询产品信息
	 * 
	 * @param productDTO
	 * @return PageDataDTO
	 * @throws BizServiceException
	 */
	public PageDataDTO inqueryProduct(ProductQueryDTO productQueryDTO)
			throws BizServiceException;

	public PageDataDTO inqueryProductC(ProductQueryDTO productQueryDTO)
			throws BizServiceException;

	public PageDataDTO inqueryProductD(ProductQueryDTO productQueryDTO)
			throws BizServiceException;

	/**
	 * 查询实体下所有产品
	 * 
	 * @param entityId
	 * @return
	 * @throws BizServiceException
	 */
	public List<ProductDTO> getProductsByEntityId(String entityId)
			throws BizServiceException;

	/**
	 * 查询营销机构下所有产品
	 * 
	 * @param entityId
	 * @return
	 * @throws BizServiceException
	 */
	public List<ProductDTO> getProductsBySellId(String entityId)
			throws BizServiceException;

	/**
	 * 查询产品信息
	 * 
	 * @param productDTO
	 * @return PageDataDTO
	 * @throws BizServiceException
	 */
	public List<ProductDTO> inqueryProductList(Long productId)
			throws BizServiceException;

	/**
	 * 初始化产品信息
	 * 
	 * @return ProductDTO
	 * @throws BizServiceException
	 */
	public ProductDTO initProduct() throws BizServiceException;

	/**
	 * 调用产品信息
	 * 
	 * @return ProductDTO
	 * @throws BizServiceException
	 */
	public ProductDTO viewProduct(ProductDTO productDTO)
			throws BizServiceException;

	/**
	 * 添加合同时查看产品
	 * 
	 * @param productDTO
	 * @return
	 * @throws BizServiceException
	 */
	public ProductDTO viewProductForContract(ProductDTO productDTO)
			throws BizServiceException;

	/**
	 * 增加产品信息
	 * 
	 * @param ProductDTO
	 * 
	 * @throws BizServiceException
	 */
	public ProductDTO insertProduct(ProductDTO productDTO)
			throws BizServiceException;

	/**
	 * 修改产品信息
	 * 
	 * @param ProductDTO
	 * 
	 * @throws BizServiceException
	 */
	public void updateProduct(ProductDTO productDTO) throws BizServiceException;

	/**
	 * 删除产品信息
	 * 
	 * @param ProductDTO
	 * 
	 * @throws BizServiceException
	 */
	public void deleteProduct(ProductDTO productDTO) throws BizServiceException;

	/**
	 * 修改产品信息状态
	 * 
	 * @param ProductDTO
	 * 
	 * @throws BizServiceException
	 */
	public void modifyStateProduct(ProductDTO productDTO)
			throws BizServiceException;

	/**
	 * 添加产品与卡面信息关系
	 * 
	 * @param productDTO
	 * 
	 * @throws BizServiceException
	 */
	public void insertProdLayout(ProductDTO productDTO)
			throws BizServiceException;

	/**
	 * 添加产品与面额信息关系
	 * 
	 * @param productDTO
	 * @throws BizServiceException
	 */
	public void inserProdFaceValue(ProdFaceValueDTO prodFaceValueDTO)
			throws BizServiceException;

	/**
	 * 删除产品与卡面信息关系
	 * 
	 * @param productDTO
	 * 
	 * @throws BizServiceException
	 */
	public void deleteProdLayout(ProductDTO productDTO)
			throws BizServiceException;

	/**
	 * 添加产品与包装信息关系
	 * 
	 * @param productDTO
	 * 
	 * @throws BizServiceException
	 */
	public void insertProdPackage(ProductDTO productDTO)
			throws BizServiceException;

	/**
	 * 删除产品与包装信息关系
	 * 
	 * @param productDTO
	 * 
	 * @throws BizServiceException
	 */
	public void deleteProdPackage(ProductDTO productDTO)
			throws BizServiceException;

	/**
	 * 删除产品与服务信息关系
	 * 
	 * @param productDTO
	 * @throws BizServiceException
	 */
	public void deleteAcctype(ProductDTO productDTO) throws BizServiceException;

	/**
	 * 获取发行机构
	 * 
	 * @param serviceDTO
	 * @return
	 * @throws BizServiceException
	 */
	public List<ProductQueryDTO> getProductIssuerInfoList(
			ProductQueryDTO productQueryDTO) throws BizServiceException;

	/**
	 * 删除产品与面额信息关系
	 * 
	 * @param productDTO
	 * @throws BizServiceException
	 */
	public void deleteProdFaceValue(ProdFaceValueDTO prodFaceValueDTO)
			throws BizServiceException;

	public String inqueryProductRelAccount(ServiceQueryDTO serviceQueryDTO)
			throws BizServiceException;

	public List<ProductDTO> getProductsForStockOrder(String entityId)
			throws BizServiceException;

	/**
	 * 获取有效期规则
	 */
	public List<ValidPeriodRuleDspDTO> getValidPeriodRuleList(
			ProductQueryDTO productQueryDTO) throws BizServiceException;
	
	public List<ProductDTO> getProductsForUnsignOrder(SellOrderInputDTO sellOrderInputDTO)
			throws BizServiceException ;
}

package com.allinfinance.prepay.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allinfinance.prepay.exception.BizServiceException;
import com.allinfinance.prepay.mapper.svc_mng.CaclDspMapper;
import com.allinfinance.prepay.mapper.svc_mng.ProdFaceValueMapper;
import com.allinfinance.prepay.mapper.svc_mng.ProductMapper;
import com.allinfinance.prepay.model.CaclDsp;
import com.allinfinance.prepay.model.CaclDspExample;
import com.allinfinance.prepay.model.ProdFaceValue;
import com.allinfinance.prepay.model.ProdFaceValueExample;
import com.allinfinance.prepay.model.Product;
import com.allinfinance.prepay.model.ProductExample;
import com.allinfinance.prepay.utils.DataBaseConstant;
import com.allinfinance.prepay.utils.ReflectionUtil;
import com.allinfinance.univer.issuer.dto.product.ProdFaceValueDTO;
import com.allinfinance.univer.issuer.dto.product.ProductDTO;

@Service
public class ProductServiceImpl implements ProductService {

	Logger logger = Logger.getLogger(ProductServiceImpl.class);
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private CaclDspMapper caclDspMapper;
	@Autowired
	private ProdFaceValueMapper prodFaceValueMapper;
	@Override
	public ProductDTO viewProductForContract(ProductDTO productDTO)
			throws BizServiceException {
		try {
			ProductExample productExample=new ProductExample();
			productExample.createCriteria().andProductIdEqualTo(productDTO.getProductId());
			List<Product> product = productMapper.selectByExample(productExample);
			ProductDTO productDto = new ProductDTO();
			ReflectionUtil.copyProperties(product.get(0), productDto);

			/**
			 * 加载服务信息
			 */
			productDto.setServices(productMapper
					.getProdAcctypeDTOs(productDto));
			
			/**
			 * 加载包装信息
			 */
			productDto.setPackages(productMapper
					.getProdPackageDTOs(productDto));
			
			/**
			 * 加载卡面信息
			 */
			productDto.setCardLayoutDTOs(productMapper
					.getCardLayoutDTOs(productDto));

			/**
			 * 添加卡BIN信息
			 */
			productDto.setProductCardBinDTOs(productMapper
					.getCardBinByProductId(productDTO.getProductId()));

			/**
			 * 默认账户计算规则
			 */
			CaclDspExample caclDspExample = new CaclDspExample();

			caclDspExample.createCriteria()
					.andRecUpdUsrIdEqualTo(productDTO.getDefaultEntityId())
					.andDataStatEqualTo("2");

			List<CaclDsp> lstCaclDsp = caclDspMapper
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
	@Override
	public ProductDTO viewProduct(ProductDTO productDTO)
			throws BizServiceException {
		try {
			
			ProductExample productExample=new ProductExample();
			productExample.createCriteria().andProductIdEqualTo(productDTO.getProductId());
			List<Product> products = productMapper.selectByExample(productExample);
			Product product =products.get(0);
			ProductDTO productDto = new ProductDTO();
			ReflectionUtil.copyProperties(product, productDto);
			/**
			 * 加载产品面额
			 */

			ProdFaceValueExample prodFaceValueEx = new ProdFaceValueExample();
			prodFaceValueEx.createCriteria()
					.andProductIdEqualTo(productDTO.getProductId())
					.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
			List<ProdFaceValue> prodFaceValues = prodFaceValueMapper
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
			productDto.setCardLayoutDTOs(productMapper
					.getCardLayoutDTOs(productDto));
			/**
			 * 加载服务信息
			 */
			productDto.setServices(productMapper
					.getProdAcctypeDTOs(productDto));
			/**
			 * 加载包装信息
			 */
			productDto.setPackages(productMapper
					.getProdPackageDTOs(productDto));

			/**
			 * 添加卡BIN信息
			 */
			productDto.setProductCardBinDTOs(productMapper
					.getCardBinByProductId(productDTO.getProductId()));

			
			return productDto;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("获取产品信息失败~！");
		}
	}
	

}

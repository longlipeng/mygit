package com.allinfinance.prepay.service;

import com.allinfinance.prepay.exception.BizServiceException;
import com.allinfinance.univer.issuer.dto.product.ProductDTO;

public interface ProductService {
	public ProductDTO viewProductForContract(ProductDTO productDTO)
			throws BizServiceException;
	
	public ProductDTO viewProduct(ProductDTO productDTO)
			throws BizServiceException;
	
	

}

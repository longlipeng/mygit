package com.allinfinance.univer.issuer.dto.procuctPackage;

import com.allinfinance.framework.dto.PageQueryDTO;

public class PackageQueryDTO extends PageQueryDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String packageFee;

	private String packageId;

	private String packageName;
	
	private String productId;

	public String getPackageFee() {
		return packageFee;
	}

	public String getPackageId() {
		return packageId;
	}


	public String getPackageName() {
		return packageName;
	}

	public String getProductId() {
		return productId;
	}

	public void setPackageFee(String packageFee) {
		this.packageFee = packageFee;
	}

	public void setPackageId(String packageId) {
		if(packageId.equals(""))packageId=null;
		this.packageId = packageId;
	}


	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

}

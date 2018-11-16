package com.huateng.po;

import java.io.Serializable;

public class TblCnpcCardInfPK implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public TblCnpcCardInfPK () {}
	private String customerProfile;
	private String cardLevel;

	public TblCnpcCardInfPK(String customerProfile, String cardLevel) {
		this.customerProfile = customerProfile;
		this.cardLevel = cardLevel;
	}

	public String getCustomerProfile() {
		return customerProfile;
	}

	public void setCustomerProfile(String customerProfile) {
		this.customerProfile = customerProfile;
	}

	public String getCardLevel() {
		return cardLevel;
	}

	public void setCardLevel(String cardLevel) {
		this.cardLevel = cardLevel;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cardLevel == null) ? 0 : cardLevel.hashCode());
		result = prime * result + ((customerProfile == null) ? 0 : customerProfile.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TblCnpcCardInfPK other = (TblCnpcCardInfPK) obj;
		if (cardLevel == null) {
			if (other.cardLevel != null)
				return false;
		} else if (!cardLevel.equals(other.cardLevel))
			return false;
		if (customerProfile == null) {
			if (other.customerProfile != null)
				return false;
		} else if (!customerProfile.equals(other.customerProfile))
			return false;
		return true;
	}

}
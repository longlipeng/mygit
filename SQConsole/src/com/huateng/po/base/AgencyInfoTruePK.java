package com.huateng.po.base;

import java.io.Serializable;

public class AgencyInfoTruePK implements Serializable{
	private String AGEN_ID;
	private String TRAN_TYPE;
	
	
	
	public AgencyInfoTruePK() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AgencyInfoTruePK(String aGEN_ID, String tRAN_TYPE) {
		super();
		AGEN_ID = aGEN_ID;
		TRAN_TYPE = tRAN_TYPE;
	}
	public String getAGEN_ID() {
		return AGEN_ID;
	}
	public void setAGEN_ID(String aGEN_ID) {
		AGEN_ID = aGEN_ID;
	}
	public String getTRAN_TYPE() {
		return TRAN_TYPE;
	}
	public void setTRAN_TYPE(String tRAN_TYPE) {
		TRAN_TYPE = tRAN_TYPE;
	}
}

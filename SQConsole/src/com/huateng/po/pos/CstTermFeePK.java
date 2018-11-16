package com.huateng.po.pos;

import java.io.Serializable;

public class CstTermFeePK implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private String mchtcd;
private String termid;
private String cardtype;
public CstTermFeePK(){}
public CstTermFeePK(String mchtcd,String termid,String cardtype){
	this.mchtcd=mchtcd;
	this.termid=termid;
	this.cardtype=cardtype;
}
public String getMchtcd() {
	return mchtcd;
}
public void setMchtcd(String mchtcd) {
	this.mchtcd = mchtcd;
}
public String getTermid() {
	return termid;
}
public void setTermid(String termid) {
	this.termid = termid;
}
public String getCardtype() {
	return cardtype;
}
public void setCardtype(String cardtype) {
	this.cardtype = cardtype;
}

}

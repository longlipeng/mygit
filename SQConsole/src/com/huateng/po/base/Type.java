package com.huateng.po.base;

import java.io.Serializable;

public class Type implements Serializable{
	private static final long serialVersionUID = 1L;
	public String typeid;
	public String typename;
	public String getTypeid() {
		return typeid;
	}
	public void setTypeid(String typeid) {
		this.typeid = typeid;
	}
	public String getTypename() {
		return typename;
	}
	public void setTypename(String typename) {
		this.typename = typename;
	}
}

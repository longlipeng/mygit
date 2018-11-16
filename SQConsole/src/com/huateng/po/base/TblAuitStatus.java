package com.huateng.po.base;

import java.io.Serializable;

public class TblAuitStatus implements Serializable{
/**
	 * 
	 */
private static final long serialVersionUID = 1L;
public String ID;
public String getID() {
	return ID;
}
public void setID(String iD) {
	ID = iD;
}
public String getTYPE() {
	return TYPE;
}
public void setTYPE(String tYPE) {
	TYPE = tYPE;
}
public String STATUE_ID;
public String STATUE_NAME;
public String TYPE;

public String getSTATUE_ID() {
	return STATUE_ID;
}
public void setSTATUE_ID(String sTATUEID) {
	STATUE_ID = sTATUEID;
}
public String getSTATUE_NAME() {
	return STATUE_NAME;
}
public void setSTATUE_NAME(String sTATUENAME) {
	STATUE_NAME = sTATUENAME;
}

}

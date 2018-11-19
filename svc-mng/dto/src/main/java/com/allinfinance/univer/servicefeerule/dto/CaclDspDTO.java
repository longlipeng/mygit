package com.allinfinance.univer.servicefeerule.dto;

import java.util.Date;
import java.util.List;

import com.allinfinance.framework.dto.BaseDTO;

public class CaclDspDTO extends BaseDTO {
	 
	/**
	 * 
	 */
	private static final long serialVersionUID = -5745966738841888159L;
	private String discCd;
	 private String caclDsp;
	 private String recUpdUsrId;
	 private Date recUpdTs;
	 private Date recCrtTs;
	 private String caclName;
	 private String recCrtTsString;
	 
	 public String getRecCrtTsString() {
		return recCrtTsString;
	}
	public void setRecCrtTsString(String recCrtTsString) {
		this.recCrtTsString = recCrtTsString;
	}
	private String dataStat;
	 private List<CaclInfDTO> caclInfDTOs;
	public String getDiscCd() {
		return discCd;
	}
	public void setDiscCd(String discCd) {
		this.discCd = discCd;
	}
	public String getCaclDsp() {
		return caclDsp;
	}
	public void setCaclDsp(String caclDsp) {
		this.caclDsp = caclDsp;
	}
	public String getRecUpdUsrId() {
		return recUpdUsrId;
	}
	public void setRecUpdUsrId(String recUpdUsrId) {
		this.recUpdUsrId = recUpdUsrId;
	}
	public Date getRecUpdTs() {
		return recUpdTs;
	}
	public void setRecUpdTs(Date recUpdTs) {
		this.recUpdTs = recUpdTs;
	}
	public Date getRecCrtTs() {
		return recCrtTs;
	}
	public void setRecCrtTs(Date recCrtTs) {
		this.recCrtTs = recCrtTs;
	}
	public String getCaclName() {
		return caclName;
	}
	public void setCaclName(String caclName) {
		this.caclName = caclName;
	}
	public String getDataStat() {
		return dataStat;
	}
	public void setDataStat(String dataStat) {
		this.dataStat = dataStat;
	}
	public List<CaclInfDTO> getCaclInfDTOs() {
		return caclInfDTOs;
	}
	public void setCaclInfDTOs(List<CaclInfDTO> caclInfDTOs) {
		this.caclInfDTOs = caclInfDTOs;
	}
	 
}

package com.allinfinance.univer.servicefeerule.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.allinfinance.framework.dto.PageQueryDTO;

public class CaclInfQueryDTO extends PageQueryDTO{

	private static final long serialVersionUID = 1L;
	private String discCd;
	private String caclName;
	private String caclDsp;
	private String dataStat;
	 private Date recUpdTs;
	 private Date recCrtTs;
	 private String recUpdUsrId;  
	 private CaclInfDTO caclInfDTO= new CaclInfDTO();
	 private CaclDspDTO caclDspDTO= new CaclDspDTO();
	 private List<CaclInfDTO> caclInfDTOs = new ArrayList<CaclInfDTO>();
	 private List<CaclInfView> list;
	public String getDiscCd() {
		return discCd;
	}
	public void setDiscCd(String discCd) {
		this.discCd = discCd;
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
	public Date getRecUpdTs() {
		return recUpdTs;
	}
	public void setRecUpdTs(Date recUpdTs) {
		this.recUpdTs = recUpdTs;
	}
	public String getRecUpdUsrId() {
		return recUpdUsrId;
	}
	public void setRecUpdUsrId(String recUpdUsrId) {
		this.recUpdUsrId = recUpdUsrId;
	}
	public String getCaclDsp() {
		return caclDsp;
	}
	public void setCaclDsp(String caclDsp) {
		this.caclDsp = caclDsp;
	}
	public Date getRecCrtTs() {
		return recCrtTs;
	}
	public void setRecCrtTs(Date recCrtTs) {
		this.recCrtTs = recCrtTs;
	}
	public CaclInfDTO getCaclInfDTO() {
		return caclInfDTO;
	}
	public void setCaclInfDTO(CaclInfDTO caclInfDTO) {
		this.caclInfDTO = caclInfDTO;
	}
	public CaclDspDTO getCaclDspDTO() {
		return caclDspDTO;
	}
	public void setCaclDspDTO(CaclDspDTO caclDspDTO) {
		this.caclDspDTO = caclDspDTO;
	}
	public List<CaclInfDTO> getCaclInfDTOs() {
		return caclInfDTOs;
	}
	public void setCaclInfDTOs(List<CaclInfDTO> caclInfDTOs) {
		this.caclInfDTOs = caclInfDTOs;
	}
	public List<CaclInfView> getList() {
		return list;
	}
	public void setList(List<CaclInfView> list) {
		this.list = list;
	}
	
	 
	 
	
}

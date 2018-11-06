package com.allinfinance.univer.consumercontract.dto;

import java.util.List;

import com.allinfinance.framework.dto.BaseDTO;
import com.allinfinance.univer.issuer.dto.service.ServiceDTO;
import com.allinfinance.univer.servicefeerule.dto.CaclDspDTO;
import com.allinfinance.univer.servicefeerule.dto.ServiceFeeRuleDTO;

public class AccTypeContractDTO extends BaseDTO {

    private static final long serialVersionUID = 3488940585917742825L;
    private String acctypeContractId;
    private String consumerContractId;
    private String ruleNo;
    private String acctypeId;
    private String contractBuyer;
    private String ruleName;
    private List<String> serviceIdList;
    private String contractSeller;
    private List<ServiceFeeRuleDTO> serviceFeeRuleDTOList;
    private List<ServiceDTO> serviceDTOList;
    private List<AccTypeContractDTO> accTypeContractDTOList;
    private String constractType;

    private List<CaclDspDTO> caclDspDTOList;

    public List<CaclDspDTO> getCaclDspDTOList() {
        return caclDspDTOList;
    }

    public void setCaclDspDTOList(List<CaclDspDTO> caclDspDTOList) {
        this.caclDspDTOList = caclDspDTOList;
    }

    public String getAcctypeContractId() {
        return acctypeContractId;
    }

    public void setAcctypeContractId(String acctypeContractId) {
        this.acctypeContractId = acctypeContractId;
    }

    public String getConsumerContractId() {
        return consumerContractId;
    }

    public void setConsumerContractId(String consumerContractId) {
        this.consumerContractId = consumerContractId;
    }

    public String getRuleNo() {
        return ruleNo;
    }

    public void setRuleNo(String ruleNo) {
        this.ruleNo = ruleNo;
    }

    public String getAcctypeId() {
        return acctypeId;
    }

    public void setAcctypeId(String acctypeId) {
        this.acctypeId = acctypeId;
    }

    public String getContractBuyer() {
        return contractBuyer;
    }

    public void setContractBuyer(String contractBuyer) {
        this.contractBuyer = contractBuyer;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getContractSeller() {
        return contractSeller;
    }

    public void setContractSeller(String contractSeller) {
        this.contractSeller = contractSeller;
    }

    public List<ServiceFeeRuleDTO> getServiceFeeRuleDTOList() {
        return serviceFeeRuleDTOList;
    }

    public void setServiceFeeRuleDTOList(List<ServiceFeeRuleDTO> serviceFeeRuleDTOList) {
        this.serviceFeeRuleDTOList = serviceFeeRuleDTOList;
    }

    public List<String> getServiceIdList() {
        return serviceIdList;
    }

    public void setServiceIdList(List<String> serviceIdList) {
        this.serviceIdList = serviceIdList;
    }

    public List<ServiceDTO> getServiceDTOList() {
        return serviceDTOList;
    }

    public void setServiceDTOList(List<ServiceDTO> serviceDTOList) {
        this.serviceDTOList = serviceDTOList;
    }

    public List<AccTypeContractDTO> getAccTypeContractDTOList() {
        return accTypeContractDTOList;
    }

    public void setAccTypeContractDTOList(List<AccTypeContractDTO> accTypeContractDTOList) {
        this.accTypeContractDTOList = accTypeContractDTOList;
    }

    public String getConstractType() {
        return constractType;
    }

    public void setConstractType(String constractType) {
        this.constractType = constractType;
    }

}

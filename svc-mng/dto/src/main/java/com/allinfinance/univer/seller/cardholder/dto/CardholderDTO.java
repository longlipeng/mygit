package com.allinfinance.univer.seller.cardholder.dto;

import java.util.List;

import com.allinfinance.framework.dto.BaseDTO;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.entity.dto.DepartmentDTO;
import com.allinfinance.univer.seller.customer.CustomerDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderCardListQueryDTO;

public class CardholderDTO extends BaseDTO {

	private static final long serialVersionUID = 5244603676772474237L;
	private String cardholderId;
	private String orderId;
	// 会员id
	private String memberId;
	private String entityId;
	private String lastName;
	private String firstName;
	private String idType;
	private String idNo;
	private String cardholderMobile;
	private String cardholderEmail;
	private String externalId;
	private String departmentId;
	private String departmentName;
	private String cardholderBirthday;
	private String cardholderGender;
	private String cardholderSalutation;
	private String cardholderFunction;
	private String cardholderSegment;
	private String cardholderState;
	private String closeDate;
	private String cardholderComment;
	private String fatherEntityId;
	private CustomerDTO customerDTO = new CustomerDTO();
	private String[] cardholderIds;
	
	//添加字段
	private String v_Id;//车架号
	private String plateNumber;//车牌号
	private String driverLicence;//驾驶证号
	//邮寄地址
	private String mailingAddress;
	private String checkState;
	
	private String imgfPath;
	private String imgbPath;
	
	private String industry;
	
	private String profession;
	private String validity;
	private String country;
	private String city;
	private String businessLlicense;
	private String organizationCode;
	private String taxRegistration;
	private String updateDate;
	private String createUser;
        private String createTime;
        private String modifyUser;
        private String modifyTime;
	
	//持卡人受理区域
	private String cardholderArea;
	//新增字段
	private String cardholderNation; //民族
	private String cardholderEducation; //学历
	private String cardholderMarriage; //婚姻状况
	private String riskGrade ; // 风险级别(默认是未评级)
    private String isblacklist;// 黑名单标识（默认是未评级）
	// 实际控制人证件有效期
	private String ctidEndValidity;

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	public String getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getRiskGrade() {
		return riskGrade;
	}

	public void setRiskGrade(String riskGrade) {
		this.riskGrade = riskGrade;
	}

	public String getIsblacklist() {
		return isblacklist;
	}

	public void setIsblacklist(String isblacklist) {
		this.isblacklist = isblacklist;
	}

    public String getCardholderNation() {
        return cardholderNation;
    }

    public void setCardholderNation(String cardholderNation) {
        this.cardholderNation = cardholderNation;
    }

    public String getCardholderEducation() {
        return cardholderEducation;
    }

    public void setCardholderEducation(String cardholderEducation) {
        this.cardholderEducation = cardholderEducation;
    }

    public String getCardholderMarriage() {
        return cardholderMarriage;
    }

    public void setCardholderMarriage(String cardholderMarriage) {
        this.cardholderMarriage = cardholderMarriage;
    }

    public String getCardholderArea() {
		return cardholderArea;
	}

	public void setCardholderArea(String cardholderArea) {
		this.cardholderArea = cardholderArea;
	}

	public String getV_Id() {
		return v_Id;
	}

	public void setV_Id(String v_Id) {
		this.v_Id = v_Id;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public String getValidity() {
		return validity;
	}

	public void setValidity(String validity) {
		this.validity = validity;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getBusinessLlicense() {
		return businessLlicense;
	}

	public void setBusinessLlicense(String businessLlicense) {
		this.businessLlicense = businessLlicense;
	}

	public String getOrganizationCode() {
		return organizationCode;
	}

	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	public String getTaxRegistration() {
		return taxRegistration;
	}

	public void setTaxRegistration(String taxRegistration) {
		this.taxRegistration = taxRegistration;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getImgfPath() {
		return imgfPath;
	}

	public void setImgfPath(String imgfPath) {
		this.imgfPath = imgfPath;
	}

	public String getImgbPath() {
		return imgbPath;
	}

	public void setImgbPath(String imgbPath) {
		this.imgbPath = imgbPath;
	}

	public String getCheckState() {
		return checkState;
	}

	public void setCheckState(String checkState) {
		this.checkState = checkState;
	}

	public String getMailingAddress() {
		return mailingAddress;
	}

	public void setMailingAddress(String mailingAddress) {
		this.mailingAddress = mailingAddress;
	}


	public String getPlateNumber() {
		return plateNumber;
	}

	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}

	public String getDriverLicence() {
		return driverLicence;
	}


	public void setDriverLicence(String driverLicence) {
		this.driverLicence = driverLicence;
	}


	private List<DepartmentDTO> departmentList;

	private SellOrderCardListQueryDTO sellOrderCardListQueryDTO;

	private PageDataDTO cardholderCardList = new PageDataDTO();
	private String cardNo;
	private String creditAmont;

	
	
	
	
	public String getCreditAmont() {
		return creditAmont;
	}

	public void setCreditAmont(String creditAmont) {
		this.creditAmont = creditAmont;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public PageDataDTO getCardholderCardList() {
		return cardholderCardList;
	}

	public String getFatherEntityId() {
		return fatherEntityId;
	}

	public void setFatherEntityId(String fatherEntityId) {
		this.fatherEntityId = fatherEntityId;
	}

	public void setCardholderCardList(PageDataDTO cardholderCardList) {
		this.cardholderCardList = cardholderCardList;
	}

	public SellOrderCardListQueryDTO getSellOrderCardListQueryDTO() {
		return sellOrderCardListQueryDTO;
	}

	public void setSellOrderCardListQueryDTO(
			SellOrderCardListQueryDTO sellOrderCardListQueryDTO) {
		this.sellOrderCardListQueryDTO = sellOrderCardListQueryDTO;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	private List<CardholderDTO> cardholderDTOList;

	public List<DepartmentDTO> getDepartmentList() {
		return departmentList;
	}

	public void setDepartmentList(List<DepartmentDTO> departmentList) {
		this.departmentList = departmentList;
	}

	public List<CardholderDTO> getCardholderDTOList() {
		return cardholderDTOList;
	}

	public void setCardholderDTOList(List<CardholderDTO> cardholderDTOList) {
		this.cardholderDTOList = cardholderDTOList;
	}

	public CustomerDTO getCustomerDTO() {
		return customerDTO;
	}

	public void setCustomerDTO(CustomerDTO customerDTO) {
		this.customerDTO = customerDTO;
	}

	public String getCardholderId() {
		return cardholderId;
	}

	public void setCardholderId(String cardholderId) {
		this.cardholderId = cardholderId;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getCardholderMobile() {
		return cardholderMobile;
	}

	public void setCardholderMobile(String cardholderMobile) {
		this.cardholderMobile = cardholderMobile;
	}

	public String getCardholderEmail() {
		return cardholderEmail;
	}

	public void setCardholderEmail(String cardholderEmail) {
		this.cardholderEmail = cardholderEmail;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getCardholderBirthday() {
		return cardholderBirthday;
	}

	public void setCardholderBirthday(String cardholderBirthday) {
		this.cardholderBirthday = cardholderBirthday;
	}

	public String getCardholderGender() {
		return cardholderGender;
	}

	public void setCardholderGender(String cardholderGender) {
		this.cardholderGender = cardholderGender;
	}

	public String getCardholderSalutation() {
		return cardholderSalutation;
	}

	public void setCardholderSalutation(String cardholderSalutation) {
		this.cardholderSalutation = cardholderSalutation;
	}

	public String getCardholderFunction() {
		return cardholderFunction;
	}

	public void setCardholderFunction(String cardholderFunction) {
		this.cardholderFunction = cardholderFunction;
	}

	public String getCardholderSegment() {
		return cardholderSegment;
	}

	public void setCardholderSegment(String cardholderSegment) {
		this.cardholderSegment = cardholderSegment;
	}

	public String getCardholderState() {
		return cardholderState;
	}

	public void setCardholderState(String cardholderState) {
		this.cardholderState = cardholderState;
	}

	public String getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(String closeDate) {
		this.closeDate = closeDate;
	}

	public String getCardholderComment() {
		return cardholderComment;
	}

	public void setCardholderComment(String cardholderComment) {
		this.cardholderComment = cardholderComment;
	}

	public String[] getCardholderIds() {
		return cardholderIds;
	}

	public void setCardholderIds(String[] cardholderIds) {
		this.cardholderIds = cardholderIds;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCtidEndValidity() {
		return ctidEndValidity;
	}

	public void setCtidEndValidity(String ctidEndValidity) {
		this.ctidEndValidity = ctidEndValidity;
	}

}

package com.allinfinance.univer.seller.cardholder.dto;

public class AttachInfoDTO {
        
        //人员编号
        private String peopleNo;
        //人员类型
        private String peopleType;
        //行业
        private String industry;
        //职业
        private String profession;
        //失效日期
        private String validity;
        //国家
        private String country;
        //城市
        private String city;
        //营业执照
        private String businessLlicense;
        //组织机构代码
        private String organizationCode;
        //税务登记证号
        private String taxRegistration;
        private String updateDate;
        private String dataStat;
        private String entityId;
        
        //备用字段
        private String rs1; //个人客户信息性别占用
        private String rs2;
        private String rs3;
        
        private String nation;//民族
        private String education;//学历
        private String marriage;//婚姻状况
        private String email;//邮箱
	private String riskGrade; // 风险等级
	private String isblacklist; // 黑名单标识
                
           
    public String getNation() {
        return nation;
    }
    public void setNation(String nation) {
        this.nation = nation;
    }
    public String getEducation() {
        return education;
    }
    public void setEducation(String education) {
        this.education = education;
    }
    public String getMarriage() {
        return marriage;
    }
    public void setMarriage(String marriage) {
        this.marriage = marriage;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPeopleNo() {
                return peopleNo;
        }
        public void setPeopleNo(String peopleNo) {
                this.peopleNo = peopleNo;
        }
        public String getPeopleType() {
                return peopleType;
        }
        public void setPeopleType(String peopleType) {
                this.peopleType = peopleType;
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
        public String getDataStat() {
                return dataStat;
        }
        public void setDataStat(String dataStat) {
                this.dataStat = dataStat;
        }
        public String getEntityId() {
                return entityId;
        }
        public void setEntityId(String entityId) {
                this.entityId = entityId;
        }
        public String getRs1() {
                return rs1;
        }
        public void setRs1(String rs1) {
                this.rs1 = rs1;
        }
        public String getRs2() {
                return rs2;
        }
        public void setRs2(String rs2) {
                this.rs2 = rs2;
        }
        public String getRs3() {
                return rs3;
        }
        public void setRs3(String rs3) {
                this.rs3 = rs3;
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
        
        
}

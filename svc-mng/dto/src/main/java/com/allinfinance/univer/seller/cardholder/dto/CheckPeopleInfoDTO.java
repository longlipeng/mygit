package com.allinfinance.univer.seller.cardholder.dto;

import com.allinfinance.framework.dto.PageQueryDTO;

/**
 * 
 * @author dawn  
 * 
 */
public class CheckPeopleInfoDTO extends PageQueryDTO {

        /**
         * 
         */
        private static final long serialVersionUID = 1L;


        private String peopleNo;
        private String peopleType;
        private String cardNo;
        private String checkState;
        private String createTime;
        private String updateTime;
        private String dataState;
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
		public String getCardNo() {
			return cardNo;
		}
		public void setCardNo(String cardNo) {
			this.cardNo = cardNo;
		}
		public String getCheckState() {
			return checkState;
		}
		public void setCheckState(String checkState) {
			this.checkState = checkState;
		}
		public String getCreateTime() {
			return createTime;
		}
		public void setCreateTime(String createTime) {
			this.createTime = createTime;
		}
		public String getUpdateTime() {
			return updateTime;
		}
		public void setUpdateTime(String updateTime) {
			this.updateTime = updateTime;
		}
		public String getDataState() {
			return dataState;
		}
		public void setDataState(String dataState) {
			this.dataState = dataState;
		}
      

		

		
      
        
     
        
        

}

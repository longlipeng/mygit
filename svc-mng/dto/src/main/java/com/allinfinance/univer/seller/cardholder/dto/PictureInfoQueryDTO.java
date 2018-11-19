package com.allinfinance.univer.seller.cardholder.dto;

import com.allinfinance.framework.dto.PageQueryDTO;

/**
 * 
 * @author dawn  
 * 
 */
public class PictureInfoQueryDTO extends PageQueryDTO {

        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        private String cardholderId;

        private String firstName;

        private String cardholderMobile;
        
        private String auditState;
        
        private String idType;
        private String idNo;
        private String cardNo;
        private String startTime;
        private String endTime;
        

		public String getCardholderId() {
			return cardholderId;
		}

		public void setCardholderId(String cardholderId) {
			this.cardholderId = cardholderId;
		}

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getCardholderMobile() {
			return cardholderMobile;
		}

		public void setCardholderMobile(String cardholderMobile) {
			this.cardholderMobile = cardholderMobile;
		}

		public String getAuditState() {
			return auditState;
		}

		public void setAuditState(String auditState) {
			this.auditState = auditState;
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

		public String getCardNo() {
			return cardNo;
		}

		public void setCardNo(String cardNo) {
			this.cardNo = cardNo;
		}

		public String getStartTime() {
			return startTime;
		}

		public void setStartTime(String startTime) {
			this.startTime = startTime;
		}

		public String getEndTime() {
			return endTime;
		}

		public void setEndTime(String endTime) {
			this.endTime = endTime;
		}

		

		

		
      
        
     
        
        

}

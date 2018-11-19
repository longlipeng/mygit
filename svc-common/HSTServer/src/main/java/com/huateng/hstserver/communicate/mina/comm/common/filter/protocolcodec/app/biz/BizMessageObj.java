package com.huateng.hstserver.communicate.mina.comm.common.filter.protocolcodec.app.biz;





public class BizMessageObj {
	
	private String packageNo;
	private String serviceName;	
	private String msgHead;
	private String dataHead;
	private String txnType;             
	private String swtTxnDate;        
	private String swtTxnTime;        
	private String swtSettleDate;    
	private String channel;           
	private String swtBatchNo;         
	private String swtFlowNo;          
	private String recTxnDate;         
	private String recTxnTime;         
	private String recBatchNo;         
	private String recFlowNo;         
	private String issChannel;          
	private String innerMerchantNo;    
	private String innerPosNo;         
	private String track2;               
	private String track3;               
	private String cardNo;              
	private String cvv2;                 
	private String expDate;             
	private String accType;             
	private String txnAmount;           
	private String cardHolderFee;      
	private String balance;              
	private String pinQuiry;            
	private String pinQuiryNew;        
	private String pinTxn;              
	private String pinTxnNew;          
	private String respCode;            
	private String authCode;            
	private String referenceNo;         
	private String oriSwtbatchNo;      
	private String oriSwtflowNo;       
	private String oriRecBatchNo;     
	private String oriRecFlowNo;      
	private String oriTxnAmount;       
	private String oriCardHolderFee;  
	private String filePath;            
	private String resv1;                
	private String resv2;
	private String resv3;
	private String resv4;
	private String mac;
	private String resv5;
	private String resv6;
	private String resv7;
	private String otherdata;
	
	

	
	
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getMsgHead() {
		return msgHead;
	}
	public void setMsgHead(String msgHead) {
		this.msgHead = msgHead;
	}
	public String getDataHead() {
		return dataHead;
	}
	public void setDataHead(String dataHead) {
		this.dataHead = dataHead;
	}
	public String getTxnType() {
		return txnType;
	}
	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}
	public String getSwtTxnDate() {
		return swtTxnDate;
	}
	public void setSwtTxnDate(String swtTxnDate) {
		this.swtTxnDate = swtTxnDate;
	}
	public String getSwtTxnTime() {
		return swtTxnTime;
	}
	public void setSwtTxnTime(String swtTxnTime) {
		this.swtTxnTime = swtTxnTime;
	}
	public String getSwtSettleDate() {
		return swtSettleDate;
	}
	public void setSwtSettleDate(String swtSettleDate) {
		this.swtSettleDate = swtSettleDate;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getSwtBatchNo() {
		return swtBatchNo;
	}
	public void setSwtBatchNo(String swtBatchNo) {
		this.swtBatchNo = swtBatchNo;
	}
	public String getSwtFlowNo() {
		return swtFlowNo;
	}
	public void setSwtFlowNo(String swtFlowNo) {
		this.swtFlowNo = swtFlowNo;
	}
	public String getRecTxnDate() {
		return recTxnDate;
	}
	public void setRecTxnDate(String recTxnDate) {
		this.recTxnDate = recTxnDate;
	}
	public String getRecTxnTime() {
		return recTxnTime;
	}
	public void setRecTxnTime(String recTxnTime) {
		this.recTxnTime = recTxnTime;
	}
	public String getRecBatchNo() {
		return recBatchNo;
	}
	public void setRecBatchNo(String recBatchNo) {
		this.recBatchNo = recBatchNo;
	}
	public String getRecFlowNo() {
		return recFlowNo;
	}
	public void setRecFlowNo(String recFlowNo) {
		this.recFlowNo = recFlowNo;
	}
	public String getIssChannel() {
		return issChannel;
	}
	public void setIssChannel(String issChannel) {
		this.issChannel = issChannel;
	}
	public String getInnerMerchantNo() {
		return innerMerchantNo;
	}
	public void setInnerMerchantNo(String innerMerchantNo) {
		this.innerMerchantNo = innerMerchantNo;
	}
	public String getInnerPosNo() {
		return innerPosNo;
	}
	public void setInnerPosNo(String innerPosNo) {
		this.innerPosNo = innerPosNo;
	}
	public String getTrack2() {
		return track2;
	}
	public void setTrack2(String track2) {
		this.track2 = track2;
	}
	public String getTrack3() {
		return track3;
	}
	public void setTrack3(String track3) {
		this.track3 = track3;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getCvv2() {
		return cvv2;
	}
	public void setCvv2(String cvv2) {
		this.cvv2 = cvv2;
	}
	public String getExpDate() {
		return expDate;
	}
	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}
	public String getAccType() {
		return accType;
	}
	public void setAccType(String accType) {
		this.accType = accType;
	}
	public String getTxnAmount() {
		return txnAmount;
	}
	public void setTxnAmount(String txnAmount) {
		this.txnAmount = txnAmount;
	}
	public String getCardHolderFee() {
		return cardHolderFee;
	}
	public void setCardHolderFee(String cardHolderFee) {
		this.cardHolderFee = cardHolderFee;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getPinQuiry() {
		return pinQuiry;
	}
	public void setPinQuiry(String pinQuiry) {
		this.pinQuiry = pinQuiry;
	}
	public String getPinQuiryNew() {
		return pinQuiryNew;
	}
	public void setPinQuiryNew(String pinQuiryNew) {
		this.pinQuiryNew = pinQuiryNew;
	}
	public String getPinTxn() {
		return pinTxn;
	}
	public void setPinTxn(String pinTxn) {
		this.pinTxn = pinTxn;
	}
	public String getPinTxnNew() {
		return pinTxnNew;
	}
	public void setPinTxnNew(String pinTxnNew) {
		this.pinTxnNew = pinTxnNew;
	}
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getAuthCode() {
		return authCode;
	}
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	public String getReferenceNo() {
		return referenceNo;
	}
	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}
	public String getOriSwtbatchNo() {
		return oriSwtbatchNo;
	}
	public void setOriSwtbatchNo(String oriSwtbatchNo) {
		this.oriSwtbatchNo = oriSwtbatchNo;
	}
	public String getOriSwtflowNo() {
		return oriSwtflowNo;
	}
	public void setOriSwtflowNo(String oriSwtflowNo) {
		this.oriSwtflowNo = oriSwtflowNo;
	}
	public String getOriRecBatchNo() {
		return oriRecBatchNo;
	}
	public void setOriRecBatchNo(String oriRecBatchNo) {
		this.oriRecBatchNo = oriRecBatchNo;
	}
	public String getOriRecFlowNo() {
		return oriRecFlowNo;
	}
	public void setOriRecFlowNo(String oriRecFlowNo) {
		this.oriRecFlowNo = oriRecFlowNo;
	}
	public String getOriTxnAmount() {
		return oriTxnAmount;
	}
	public void setOriTxnAmount(String oriTxnAmount) {
		this.oriTxnAmount = oriTxnAmount;
	}
	public String getOriCardHolderFee() {
		return oriCardHolderFee;
	}
	public void setOriCardHolderFee(String oriCardHolderFee) {
		this.oriCardHolderFee = oriCardHolderFee;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getResv1() {
		return resv1;
	}
	public void setResv1(String resv1) {
		this.resv1 = resv1;
	}
	public String getResv2() {
		return resv2;
	}
	public void setResv2(String resv2) {
		this.resv2 = resv2;
	}
	public String getResv3() {
		return resv3;
	}
	public void setResv3(String resv3) {
		this.resv3 = resv3;
	}
	public String getOtherdata() {
		return otherdata;
	}
	public void setOtherdata(String otherdata) {
		this.otherdata = otherdata;
	}
	public String getResv4() {
		return resv4;
	}
	public void setResv4(String resv4) {
		this.resv4 = resv4;
	}
	public String getPackageNo() {
		return packageNo;
	}
	public void setPackageNo(String packageNo) {
		this.packageNo = packageNo;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public String getResv5() {
		return resv5;
	}
	public void setResv5(String resv5) {
		this.resv5 = resv5;
	}
	public String getResv6() {
		return resv6;
	}
	public void setResv6(String resv6) {
		this.resv6 = resv6;
	}
	public String getResv7() {
		return resv7;
	}
	public void setResv7(String resv7) {
		this.resv7 = resv7;
	}
	
	
	
}

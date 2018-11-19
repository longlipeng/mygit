package com.huateng.hstserver.model;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;
/**
 * 接口DTO定义
 * @author fengfeng.shi
 *
 */
public class CommonDTO implements Serializable {
	
	private static final long serialVersionUID = 823937681294301482L;
	// 配置log4j
	private static Logger logger = Logger.getLogger("com.huateng.hstserver.model");
	//交易类型
	private String txnType;
	//应答码
	private String respCode;
	//卡号
	private String cardNo; 
	//卡片密码
	private String password;
	//CVV2
	private String cvv2;
	//卡片类型
    private String cardType;
	//卡片信息 
    private String cardInfo;
    //批量卡信息集合
    private List<CommonDTO> cardsInfo;
    //批量卡状态信息
    private CardInfoDTO infoState;
    //挂失/解挂状态
    private String loss;
    //开通/关闭短信通知 
    private String note;
    //开通/关闭Email通知 
    private String emailInfo;
    //网上支付限额 
    private String payOnline;
    //卡片原密码
    private String oldPassword;
    //卡片新密码
    private String newPassword;
    //商户代码
    private String merchantID;
    //门店代码
    private String shopID;
    //订单号
    private String merOrderNum;
    //交易日期
    private String tranDate;
    //交易时间
    private String tranTime;
    //交易金额
    private String tranAmount;
    //交易币种
    private String curType;
    //订货人姓名
    private String buyCardholder;
    //商品信息
    private String productInfo;
    //附加信息
    private String remark;
    //商户名称
    private String merchantName;
    //收单机构
    private String acqDepartment;
    //商户后台URL
    private String merchantUrl;
    //中心流水号
    private String sequenceNo;
    //清算日期
    private String seltDate;
    //支付渠道
    private String payChannal;
    //交易卡号
    private String tranCard;
    //支付密码
    private String payPassword;
    //交易状态
    private String tranState;
    //原交易日期
    private String oldTranDate;
    //原交易时间
    private String oldTranTime;
    //原订单号
    private String oldMerOrderNum;
    //原交易金额
    private String oldTranAmount;
    //卡片状态
    private String cardState;
    //激活日期
    private String activeTime;
    //有效日期
    private String valideTime;
    //挂失状态
    private String lossState;
    //冻结状态
    private String freezesState;
    //锁定状态
    private String lockState;
    //激活状态
    private String activeState;
    //短信通知状态
    private String smsState;
    //持卡人信息
    private String cardHolder;
    //渠道号
    private String channal;
    //服务id(账户号)
    private String serviceId;
    //网上支付限额状态
    private String epayIn;
    //产品id
    private String productId;
    //产品名称
    private String productName;
    //原交易类型
    private String  oldTxnType;
    //交易账户
    private String tranAcct;
    //转出卡卡号
    private String transferOutCard;
    //转出卡账号
    private String transferOutAccount;
    //转入卡卡号
    private String transferInCard;
    //转入卡账号
    private String transferInAccount;
    //转出卡密码
    private String transferOutCardPassword;
    //转账金额
    private String transAmount;
    //转账交易流水号
    private String txnSeqNo;
    //原转出交易流水
    private String transferOutTxnNo;
    //请求PIN密钥
    private String reqPinKey;
    
    
	public String getTxnType() {
		return txnType;
	}
	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCvv2() {
		return cvv2;
	}
	public void setCvv2(String cvv2) {
		this.cvv2 = cvv2;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getCardInfo() {
		return cardInfo;
	}
	public void setCardInfo(String cardInfo) {
		this.cardInfo = cardInfo;
	}
	public List<CommonDTO> getCardsInfo() {
		return cardsInfo;
	}
	public void setCardsInfo(List<CommonDTO> cardsInfo) {
		this.cardsInfo = cardsInfo;
	}
	public CardInfoDTO getInfoState() {
		return infoState;
	}
	public void setInfoState(CardInfoDTO infoState) {
		this.infoState = infoState;
	}
	public String getLoss() {
		return loss;
	}
	public void setLoss(String loss) {
		this.loss = loss;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getEmailInfo() {
		return emailInfo;
	}
	public void setEmailInfo(String emailInfo) {
		this.emailInfo = emailInfo;
	}
	public String getPayOnline() {
		return payOnline;
	}
	public void setPayOnline(String payOnline) {
		this.payOnline = payOnline;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getMerchantID() {
		return merchantID;
	}
	public void setMerchantID(String merchantID) {
		this.merchantID = merchantID;
	}
	public String getShopID() {
		return shopID;
	}
	public void setShopID(String shopID) {
		this.shopID = shopID;
	}
	public String getMerOrderNum() {
		return merOrderNum;
	}
	public void setMerOrderNum(String merOrderNum) {
		this.merOrderNum = merOrderNum;
	}
	public String getTranDate() {
		return tranDate;
	}
	public void setTranDate(String tranDate) {
		this.tranDate = tranDate;
	}
	public String getTranTime() {
		return tranTime;
	}
	public void setTranTime(String tranTime) {
		this.tranTime = tranTime;
	}
	public String getTranAmount() {
		return tranAmount;
	}
	public void setTranAmount(String tranAmount) {
		this.tranAmount = tranAmount;
	}
	public String getCurType() {
		return curType;
	}
	public void setCurType(String curType) {
		this.curType = curType;
	}
	public String getBuyCardholder() {
		return buyCardholder;
	}
	public void setBuyCardholder(String buyCardholder) {
		this.buyCardholder = buyCardholder;
	}
	public String getProductInfo() {
		return productInfo;
	}
	public void setProductInfo(String productInfo) {
		this.productInfo = productInfo;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getAcqDepartment() {
		return acqDepartment;
	}
	public void setAcqDepartment(String acqDepartment) {
		this.acqDepartment = acqDepartment;
	}
	public String getMerchantUrl() {
		return merchantUrl;
	}
	public void setMerchantUrl(String merchantUrl) {
		this.merchantUrl = merchantUrl;
	}
	public String getSequenceNo() {
		return sequenceNo;
	}
	public void setSequenceNo(String sequenceNo) {
		this.sequenceNo = sequenceNo;
	}
	public String getSeltDate() {
		return seltDate;
	}
	public void setSeltDate(String seltDate) {
		this.seltDate = seltDate;
	}
	public String getPayChannal() {
		return payChannal;
	}
	public void setPayChannal(String payChannal) {
		this.payChannal = payChannal;
	}
	public String getTranCard() {
		return tranCard;
	}
	public void setTranCard(String tranCard) {
		this.tranCard = tranCard;
	}
	public String getPayPassword() {
		return payPassword;
	}
	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}
	public String getTranState() {
		return tranState;
	}
	public void setTranState(String tranState) {
		this.tranState = tranState;
	}
	public String getOldTranDate() {
		return oldTranDate;
	}
	public void setOldTranDate(String oldTranDate) {
		this.oldTranDate = oldTranDate;
	}
	public String getOldTranTime() {
		return oldTranTime;
	}
	public void setOldTranTime(String oldTranTime) {
		this.oldTranTime = oldTranTime;
	}
	public String getOldMerOrderNum() {
		return oldMerOrderNum;
	}
	public void setOldMerOrderNum(String oldMerOrderNum) {
		this.oldMerOrderNum = oldMerOrderNum;
	}
	public String getOldTranAmount() {
		return oldTranAmount;
	}
	public void setOldTranAmount(String oldTranAmount) {
		this.oldTranAmount = oldTranAmount;
	}
	public String getCardState() {
		return cardState;
	}
	public void setCardState(String cardState) {
		this.cardState = cardState;
	}
	public String getActiveTime() {
		return activeTime;
	}
	public void setActiveTime(String activeTime) {
		this.activeTime = activeTime;
	}
	public String getValideTime() {
		return valideTime;
	}
	public void setValideTime(String valideTime) {
		this.valideTime = valideTime;
	}
	public String getLossState() {
		return lossState;
	}
	public void setLossState(String lossState) {
		this.lossState = lossState;
	}
	public String getFreezesState() {
		return freezesState;
	}
	public void setFreezesState(String freezesState) {
		this.freezesState = freezesState;
	}
	public String getLockState() {
		return lockState;
	}
	public void setLockState(String lockState) {
		this.lockState = lockState;
	}
	public String getActiveState() {
		return activeState;
	}
	public void setActiveState(String activeState) {
		this.activeState = activeState;
	}
	public String getSmsState() {
		return smsState;
	}
	public void setSmsState(String smsState) {
		this.smsState = smsState;
	}
	public String getCardHolder() {
		return cardHolder;
	}
	public void setCardHolder(String cardHolder) {
		this.cardHolder = cardHolder;
	}
	public String getChannal() {
		return channal;
	}
	public void setChannal(String channal) {
		this.channal = channal;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	
	public String getEpayIn() {
		return epayIn;
	}
	public void setEpayIn(String epayIn) {
		this.epayIn = epayIn;
	}
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getOldTxnType() {
		return oldTxnType;
	}
	public void setOldTxnType(String oldTxnType) {
		this.oldTxnType = oldTxnType;
	}
	public String getTranAcct() {
		return tranAcct;
	}
	public void setTranAcct(String tranAcct) {
		this.tranAcct = tranAcct;
	}
	public String getTransferOutCard() {
		return transferOutCard;
	}
	public void setTransferOutCard(String transferOutCard) {
		this.transferOutCard = transferOutCard;
	}
	public String getTransferOutAccount() {
		return transferOutAccount;
	}
	public void setTransferOutAccount(String transferOutAccount) {
		this.transferOutAccount = transferOutAccount;
	}
	public String getTransferInCard() {
		return transferInCard;
	}
	public void setTransferInCard(String transferInCard) {
		this.transferInCard = transferInCard;
	}
	public String getTransferInAccount() {
		return transferInAccount;
	}
	public void setTransferInAccount(String transferInAccount) {
		this.transferInAccount = transferInAccount;
	}
	public String getTransferOutCardPassword() {
		return transferOutCardPassword;
	}
	public void setTransferOutCardPassword(String transferOutCardPassword) {
		this.transferOutCardPassword = transferOutCardPassword;
	}
	public String getTransAmount() {
		return transAmount;
	}
	public void setTransAmount(String transAmount) {
		this.transAmount = transAmount;
	}
	public String getTxnSeqNo() {
		return txnSeqNo;
	}
	public void setTxnSeqNo(String txnSeqNo) {
		this.txnSeqNo = txnSeqNo;
	}
	public String getTransferOutTxnNo() {
		return transferOutTxnNo;
	}
	public void setTransferOutTxnNo(String transferOutTxnNo) {
		this.transferOutTxnNo = transferOutTxnNo;
	}	
	public String getReqPinKey() {
		return reqPinKey;
	}
	public void setReqPinKey(String reqPinKey) {
		this.reqPinKey = reqPinKey;
	}
	/**
	 * 打印详细CommonDTO信息.
	 */
	public void printCommonDTOData() {
		logger.info("###############CommonDTO Data Begin###############");
		logger.info("txnType         	=[" + (this.txnType) + "]");
		logger.info("respCode        	=[" + (this.respCode) + "]");
		logger.info("cardNo          	=[" + (this.cardNo) + "]");
		logger.info("password        	=[" + (this.password) + "]");
		logger.info("cvv2            	=[" + (this.cvv2) + "]");
		logger.info("cardType        	=[" + (this.cardType) + "]");
		logger.info("cardInfo        	=[" + (this.cardInfo) + "]");
		logger.info("cardsInfo       	=[" + (this.cardsInfo) + "]");
		logger.info("loss            	=[" + (this.loss) + "]");
		logger.info("note            	=[" + (this.note) + "]");
		logger.info("emailInfo       	=[" + (this.emailInfo) + "]");
		logger.info("payOnline       	=[" + (this.payOnline) + "]");
		logger.info("oldPassword     	=[" + (this.oldPassword) + "]");
		logger.info("newPassword     	=[" + (this.newPassword) + "]");
		logger.info("merchantID      	=[" + (this.merchantID) + "]");
		logger.info("shopID          	=[" + (this.shopID) + "]");
		logger.info("merOrderNum     	=[" + (this.merOrderNum) + "]");
		logger.info("tranDate        	=[" + (this.tranDate) + "]");
		logger.info("tranTime        	=[" + (this.tranTime) + "]");
		logger.info("tranAmount     	=[" + (this.tranAmount) + "]");
		logger.info("curType         	=[" + (this.curType) + "]");
		logger.info("buyCardholder   	=[" + (this.buyCardholder) + "]");
		logger.info("productInfo     	=[" + (this.productInfo) + "]");
		logger.info("remark          	=[" + (this.remark) + "]");
		logger.info("merchantName    	=[" + (this.merchantName) + "]");
		logger.info("acqDepartment   	=[" + (this.acqDepartment) + "]");
		logger.info("merchantUrl     	=[" + (this.merchantUrl) + "]");
		logger.info("sequenceNo      	=[" + (this.sequenceNo) + "]");
		logger.info("seltDate        	=[" + (this.seltDate) + "]");
		logger.info("payChannal     	=[" + (this.payChannal) + "]");
		logger.info("payPassword     	=[" + (this.payPassword) + "]");
		logger.info("tranState       	=[" + (this.tranState) + "]");
		logger.info("oldTranDate     	=[" + (this.oldTranDate) + "]");
		logger.info("oldTranTime     	=[" + (this.oldTranTime) + "]");
		logger.info("oldMerOrderNum  	=[" + (this.oldMerOrderNum) + "]");
		logger.info("oldTranAmount   	=[" + (this.oldTranAmount) + "]");
		logger.info("cardState       	=[" + (this.cardState) + "]");
		logger.info("activeTime      	=[" + (this.activeTime) + "]");
		logger.info("valideTime      	=[" + (this.valideTime) + "]");
		logger.info("lossState       	=[" + (this.lossState) + "]");
		logger.info("freezesState    	=[" + (this.freezesState) + "]");
		logger.info("lockState       	=[" + (this.lockState) + "]");
		logger.info("activeState     	=[" + (this.activeState) + "]");
		logger.info("smsState        	=[" + (this.smsState) + "]");
		logger.info("cardHolder      	=[" + (this.cardHolder) + "]");
		logger.info("channal         	=[" + (this.channal) + "]");
		logger.info("serviceId       	=[" + (this.serviceId) + "]");
		logger.info("epayIn          	=[" + (this.epayIn) + "]");
		logger.info("productId       	=[" + (this.productId) + "]");
		logger.info("productName     	=[" + (this.productName) + "]");
		logger.info("oldTxnType         =[" + (this.oldTxnType) + "]");
		logger.info("tranCard           =[" + (this.tranCard) + "]");
		logger.info("tranAcct           =[" + (this.tranAcct) + "]");
		logger.info("transferOutCard    =[" + (this.transferOutCard) + "]");
		logger.info("transferOutAccount =[" + (this.transferOutAccount) + "]");
		logger.info("transferInCard     =[" + (this.transferInCard) + "]");
		logger.info("transferInAccount  =[" + (this.transferInAccount) + "]");
		logger.info("transferOutCardPas =[" + (this.transferOutCardPassword) + "]");
		logger.info("transAmount        =[" + (this.transAmount) + "]");
		logger.info("txnSeqNo           =[" + (this.txnSeqNo) + "]");
		logger.info("transferOutTxnNo   =[" + (this.transferOutTxnNo) + "]");
		logger.info("reqPinKey          =[" + (this.reqPinKey) + "]");
		logger.info("###############CommonDTO Data End###############");
	}
}

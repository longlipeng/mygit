package beans;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Recharge implements Serializable {
	private String serialNo;
	private String cardID;
	private String cardNo;
	private String cardMon;
	private String cardBalance;
	private String chargeTime;
	private String isOpenAcc;
	private String rev;

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getCardID() {
		return cardID;
	}

	public void setCardID(String cardID) {
		this.cardID = cardID;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCardMon() {
		return cardMon;
	}

	public void setCardMon(String cardMon) {
		this.cardMon = cardMon;
	}

	public String getCardBalance() {
		return cardBalance;
	}

	public void setCardBalance(String cardBalance) {
		this.cardBalance = cardBalance;
	}

	public String getChargeTime() {
		return chargeTime;
	}

	public void setChargeTime(String chargeTime) {
		this.chargeTime = chargeTime;
	}

	public String getIsOpenAcc() {
		return isOpenAcc;
	}

	public void setIsOpenAcc(String isOpenAcc) {
		this.isOpenAcc = isOpenAcc;
	}

	public String getRev() {
		return rev;
	}

	public void setRev(String rev) {
		this.rev = rev;
	}
}

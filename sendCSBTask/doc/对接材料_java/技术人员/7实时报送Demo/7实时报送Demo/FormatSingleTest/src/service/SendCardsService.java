package service;

import beans.Consumption;
import beans.Recharge;
import beans.SendCards;
import net.sf.json.JSONObject;
import util.BASE64Util;
import util.SendCSB;

public class SendCardsService {
	public void card(String uniqueNo, String submissionpass, String api, boolean isFormat) throws Exception {
		String json = null;
		if (api.equals("sendCardsService")) {
			SendCards sendCardData = new SendCards();
			sendCardData.setCardID("123");
			sendCardData.setCardNo("123");
			sendCardData.setCardMon("100");
			sendCardData.setSerialNo("111");
			sendCardData.setCardBalance("1000");
			sendCardData.setApplyTime("20180404000000");
			sendCardData.setBeginDate("20181212");
			sendCardData.setExpiryDate("20991231");
			sendCardData.setIsRegister("0");
			String jsondata = JSONObject.fromObject(sendCardData).toString();
			StringBuffer csbData = new StringBuffer("{\"dataMap\":");
			csbData.append(jsondata).append("}");
			json = csbData.toString();
			System.out.println("jsondata====" + jsondata);
			System.out.println("csbData====" + csbData);
		}
		if (api.equals("addRechargeService")) {
			Recharge recharge = new Recharge();
			recharge.setCardID("123");
			recharge.setCardNo("123");
			recharge.setSerialNo("");
			recharge.setCardBalance("");
			recharge.setCardMon("");
			recharge.setChargeTime("");
			recharge.setIsOpenAcc("0");
			recharge.setRev("");
			String jsondata = JSONObject.fromObject(recharge).toString();
			StringBuffer csbData = new StringBuffer("{\"dataMap\":");
			csbData.append(jsondata).append("}");
			json = csbData.toString();
			System.out.println("jsondata====" + jsondata);
			System.out.println("csbData====" + csbData);
		}
		if (api.equals("addConsumptionService")) {
			Consumption consumption = new Consumption();
			consumption.setCardID("");
			consumption.setCardNo("");
			consumption.setSerialNo("");
			consumption.setCardBalance("");
			consumption.setTranMon("");
			consumption.setTranTime("");
			consumption.setTranType("");
			consumption.setRev("");
			consumption.setTranBalance("");
			consumption.setCardMon("");
		}
		// 获取随机密码
		String symkey = BASE64Util.getRandomkey();
		// 私钥加密随机数
		String symmetricKeyEncrpt = BASE64Util.encryptRSA("" + symkey, BASE64Util.getPrivateKey(submissionpass));
		// //加密json
		String jsonDataEncrypt = BASE64Util.encryptAES("" + symkey, json);
		if (!isFormat) {
			SendCSB.sendCSBTestData(uniqueNo, symmetricKeyEncrpt, jsonDataEncrypt, api);
		} else {
			SendCSB.sendCSBProductData(uniqueNo, symmetricKeyEncrpt, jsonDataEncrypt, api);
		}
	}
}

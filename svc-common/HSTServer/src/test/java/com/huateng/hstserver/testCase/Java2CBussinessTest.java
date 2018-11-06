package com.huateng.hstserver.testCase;

import com.huateng.hstserver.frameworkUtil.Amount;
import com.huateng.hstserver.frameworkUtil.Const;
import com.huateng.hstserver.gatewayService.Java2CCommonServiceImpl;
import com.huateng.hstserver.model.CardInfoDTO;
import com.huateng.hstserver.model.CommonDTO;

import junit.framework.TestCase;

public class Java2CBussinessTest extends TestCase {
	
	
	public void testCardBatchQuery()throws Exception{
		CommonDTO commonDTO=new CommonDTO();
		String cardNo="6226660601005701123";
		commonDTO.setCardNo(cardNo);
		//commonDTO.setPassword("123456");
		commonDTO.setCvv2("123");
		
		Java2CCommonServiceImpl service =new Java2CCommonServiceImpl();
		service.cardBatchQuery(commonDTO);
		
	} 

	
	public static void main(String args[]){
//		String t = "1010#wl1帐户2#100400#0#100000@";
//		String[] cardStateField = t.split("\\@");
//		if (cardStateField != null && cardStateField.length > 0) {
//			for (int tt = 0; tt < cardStateField.length; tt++) {
//				String[] cardInfoField = cardStateField[tt].split("\\#");
//				CardInfoDTO infoDto = new CardInfoDTO();
//				infoDto.setServiceId(cardInfoField[0]);
//				infoDto.setServiceName(cardInfoField[1]);
//				infoDto.setBalance(Amount.getReallyAmount(cardInfoField[2]));
//				infoDto.setEpayIn(cardInfoField[3]);
//				infoDto.setLimitPayMoney(cardInfoField[4]);
//
//			}
//		}
		
		String record="G120^700000000000001^999999^2011101017304101^20111010^173041^1002^156^^^^";
		String[] field = record.split("\\^", -1);
		CommonDTO dTO=new CommonDTO();
		dTO.setRespCode("00");
		dTO.setTxnType(field[0]);
		dTO.setMerchantID(field[1]);
		dTO.setShopID(field[2]);
		dTO.setMerOrderNum(field[3]);
		dTO.setTranDate(field[4]);
		dTO.setTranTime(field[5]);
		dTO.setTranAmount(field[6]);
		dTO.setCurType(field[7]);
		dTO.setBuyCardholder(field[8]);
		dTO.setProductInfo(field[9]);
		dTO.setRemark(field[10]);
		/**只有登记成功的才返回以下几个域**/
		if(Const.RESPONSE_SUCCESS_CODE.equals("00")){
		 dTO.setMerchantName(field[11]);
		 dTO.setAcqDepartment(field[12]);
		 dTO.setMerchantUrl(field[13]);
		 dTO.setSequenceNo(field[14]);
		 dTO.setSeltDate(field[15]);
		}	 
		
		
	}
	
	
}

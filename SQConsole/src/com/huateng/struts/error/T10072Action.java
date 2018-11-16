package com.huateng.struts.error;

import java.io.IOException;
import java.net.UnknownHostException;
import com.huateng.common.Constants;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.SocketConnect;

public class T10072Action extends BaseAction {

	private static final long serialVersionUID = 1L;
	private String pan;//卡号19位
	private String amt;//交易金额12位，单位分
	private String code;//预授权码
	private String mchnNo;//商户编号
	private String term;//终端号
	private String batchId;//批次号
	private String license;//凭证号
	private String transDate;//预授权日期
	
	@Override
	protected String subExecute() throws Exception {
		try {
			 if("cancel".equals(getMethod())) {
				rspCode = cancel();
			 }
		} catch (Exception e) {
			log("操作员编号：" + operator.getOprId()+ "，操作手工预授权撤销失败，失败原因为："+e.getMessage());
		}
		return rspCode;
	}

	private String cancel() {
		String result;
		try {
			result = preAuth();
			System.out.println("手工预授权撤销响应结果："+result);
			
			if("00".equals(result)){
				log("操作员编号：" + operator.getOprId()+"传输成功");
			}else if(!"00".equals(result)){
				log("手工预授权撤销失败");
				return "手工预授权撤销失败：" + result;
			}else{
				log("操作员编号：" + operator.getOprId() + "传输失败");
				return "发送手工预授权撤销的报文失败";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return Constants.SUCCESS_CODE;//手工预授权撤销成功
	}
	
	/**
	 * 发送手工预授权撤销的报文
	 * @param preAuthorization
	 * @return
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	private String preAuth() throws IOException {
		//卡号补成19位		
		String pan1 = addlength(pan,19);
		//交易金额
		String amt1 = toFen(amt);
		
		//组合成字符串
		StringBuffer ss = new StringBuffer("3011");//手工预授权撤销代码
		ss.append(this.getPan().length()).append(pan1).append(amt1).append(this.getCode()).append(this.getTerm()).append(this.getMchnNo())
			.append("016").append(this.getBatchId()).append(this.getLicense()).append(this.getTransDate());
//		SocketConnect socket = new SocketConnect();
		
		try {
			String resp = SocketConnect.sendMessage1("00" + ss.length() + ss.toString());
			if(resp!=null && "00".equals(resp.substring(4, 6))){
				return "00";//成功
			}else{
				return resp.substring(4, 6);//失败
			}
		}catch (Exception e) {
			
		}
//		finally {
//			try {
//				if (null != socket) {
//					socket.close();
//				}
//			} catch (Exception e) {}
//		}
		return "";
	}
	
	//增加长度的方法
	private String addlength(String sss, int length) {
		StringBuffer ss = new StringBuffer();
		if(sss.length()<length){
			int addlength=length-sss.length();
			for(int i=1;i<=addlength;i++){
				ss.append(" ");
			}
		}
//		String ssss=ss.toString()+sss;
		String ssss=sss+ss.toString();
		return ssss;
	}
	
	private String toFen(String amt){//元转化为分
		Double temp = Double.parseDouble(amt);
		String amtNew = String.valueOf(Math.round(temp*100));
		StringBuffer ss = new StringBuffer();
		if(amtNew.length() < 12){
			for(int i=1 ; i<=12-amtNew.length() ; i++){
				ss.append("0");
			}
		}
		return ss.toString() + amtNew;
	}
	
	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public String getAmt() {
		return amt;
	}

	public void setAmt(String amt) {
		this.amt = amt;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMchnNo() {
		return mchnNo;
	}

	public void setMchnNo(String mchnNo) {
		this.mchnNo = mchnNo;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getTransDate() {
		return transDate;
	}

	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}

}

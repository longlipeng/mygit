package com.huateng.struts.base.action;

import com.huateng.common.Constants;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.SocketConnect;

public class T10605Action extends BaseAction{
	private static final long serialVersionUID = 1L;
	private String termid;
	private String status;
	private String mchtid;
	private String instid;
	/**
	 * @return the mchtid
	 */
	public String getMchtid() {
		return mchtid;
	}
	/**
	 * @param mchtid the mchtid to set
	 */
	public void setMchtid(String mchtid) {
		this.mchtid = mchtid;
	}
	/**
	 * @return the instid
	 */
	public String getInstid() {
		return instid;
	}
	/**
	 * @param instid the instid to set
	 */
	public void setInstid(String instid) {
		this.instid = instid;
	}
	/**
	 * @return the termid
	 */
	public String getTermid() {
		return termid;
	}
	/**
	 * @param termid the termid to set
	 */
	public void setTermid(String termid) {
		this.termid = termid;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
		
	@Override
	protected String subExecute() throws Exception {
		try {
		 if("update".equals(getMethod())) {
				rspCode = update();
			}
		} catch (Exception e) {
			log("操作员编号：" + operator.getOprId()+ "，对获取主密钥的维护操作" + getMethod() + "失败，失败原因为："+e.getMessage());
		}
		return rspCode;
	}
	
	private String update() {
		String result = poss();
		System.out.println("获取主密钥响应结果："+result);
		
		String signSql = "update TBL_SIGN_INF set RESP_CODE='" + result+ "' where TERM_ID='" + termid + "'";
		commQueryDAO.excute(signSql);
		log("更新获取主密钥的响应码：" + result);
		
		if("00".equals(result)){
			log("操作员编号：" + operator.getOprId()+"传输成功");
			
			String sqlSign = "update TBL_SIGN_INF set SIGNED_FLAG='1' where TERM_ID='" + termid + "'";
			commQueryDAO.excute(sqlSign);
		}else{
			log("操作员编号：" + operator.getOprId() + "传输失败");
			return "获取主密钥失败";
		}
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 向中行或农行获取主密钥
	 * @param tblAttendanceBrh
	 * @return
	 */
	private String poss() {
		//机构号		
		String instid1=addlength(instid,8);//8位		
		//终端号
		String termid1=addlength(termid,8);
		//商户号
		String mchtid1="";
		if("01025840".equals(instid.trim())){
			mchtid1 = mchtid;
		}else {
			mchtid1=addlength(mchtid,15);
		}
		
		//modify by zxc 20130522 把工行的获取主密钥改成8411  start
		StringBuffer ss = new StringBuffer("");
		if("01025840".equals(instid.trim())){
			ss.append("84110800");
		}else if("03014520".equals(instid.trim())){//交行
			ss.append("85110800");
		}else {
			ss.append("83110800");
		}
		//modify by zxc 20130522 把工行的获取主密钥改成8411  end
		
		//20120921，区分向中行还是向农行获取主密钥
		if("01041000".equals(instid)){
			ss.append("990000");
		}else if("01032900".equals(instid)){
			ss.append("940000");
		}else if("01052900".equals(instid.trim())){	//建行
			ss.append("000000");
		}else if("01025840".equals(instid.trim())){	//工行
			ss.append("900010");
		}else if("03014520".equals(instid.trim())){	//交行
			ss.append("910000");
		}
		ss.append(instid1).append(termid1).append(mchtid1);
		//0045 83110800 000000 01052900 00016814 105290054 510838
//		SocketConnect socket = new SocketConnect();
		try {
//			socket = new SocketConnect("00"+ss.length()+ss.toString(),"pos");
//			if (null != socket) {
//				socket.run();
//				String resp = socket.getRsp();	
//				System.out.print("返回报文:"+resp);
//				
//				resp = CommonFunction.changeCharsetOld(resp,Charset.defaultCharset().toString(),"UTF-8");
//				System.out.print("返回报文1111:"+resp);
//				flag=true;
//			}
			String resp = SocketConnect.sendMessage1("00"+ss.length()+ss.toString());
			if(resp!=null && !resp.equals("失败")){
				if(
//						resp.startsWith("83120810990004")
						resp.startsWith("85120810")//第9到第14这6位是随机的，不能固定值判断
							&& resp.substring(14, 16).equals("00"))
								return "00";
			}else{
				return resp.substring(14,16);
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
		String ssss=ss.toString()+sss;
		return ssss;
	}
}

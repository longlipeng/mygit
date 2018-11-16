package com.huateng.struts.base.action;

import com.huateng.common.Constants;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.SocketConnect;

public class T10600Action extends BaseAction{
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
			log("操作员编号：" + operator.getOprId()+ "，对签到的维护操作" + getMethod() + "失败，失败原因为："+e.getMessage());
		}
		return rspCode;
	}
	
	private String update() {
		String result = poss();
		System.out.println("签到响应结果："+result);
		
		String signSql = "update TBL_SIGN_INF set RESP_CODE='" + result+ "' where TERM_ID='" + termid + "'";
		commQueryDAO.excute(signSql);
		log("更新签到的响应码：" + result);
		
		if("00".equals(result)){
			log("操作员编号：" + operator.getOprId()+"传输成功");
			
			String sqlSign = "update TBL_SIGN_INF set SIGNED_FLAG='1' where TERM_ID='" + termid + "'";
			commQueryDAO.excute(sqlSign);
		}else{
			log("操作员编号：" + operator.getOprId() + "传输失败");
			return "签到失败";
		}
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 向中行或农行签到
	 * @param tblAttendanceBrh
	 * @return
	 */
	private String poss() {
		//机构号		
		String instid1=addlength(instid,8);//8位		
		//终端号
		String termid1=addlength(termid,8);
		//商户号
		String mchtid1=addlength(mchtid,15);
		
		//组合成字符串
		StringBuffer ss = new StringBuffer("83110800");
		//20120921，区分向中行还是向农行签到
//		if("01041000".equals(instid)){
//			ss.append("990000");
//		}
//		if("01032900".equals(instid)){
//			ss.append("940000");
//		}
		ss.append("000000");
		ss.append(instid1).append(termid1).append(mchtid1);
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
			System.out.println(resp);
			if(resp!=null && !resp.equals("失败")){
				if(resp.length()==43 && 
//						resp.startsWith("83120810990004")
						resp.substring(4).startsWith("83120810")//第四位之后判断
							&& resp.substring(18, 20).equals("00"))
								return "00";
			}else{
				return resp.substring(18,20);
			}
		}catch (Exception e) {
			
		} 
		return "01";
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

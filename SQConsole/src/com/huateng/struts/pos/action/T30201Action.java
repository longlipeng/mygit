package com.huateng.struts.pos.action;

import java.io.IOException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Random;

import com.huateng.bo.rout.T11015BO;
import com.huateng.bo.rout.T11018BO;
import com.huateng.bo.term.T3010BO;
import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.common.XmlObject.DuXMLDoc;
import com.huateng.po.TblTermInf;
import com.huateng.po.TblTermInfTmp;
import com.huateng.po.rout.TblTermChannelInf;
import com.huateng.po.rout.TblTermChannelInfTmp;
import com.huateng.struts.pos.TblTermInfConstants;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.BeanUtils;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.SocketConnect;

/**
 * Title:终端信息审核
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-18
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @version 1.0
 */
public class T30201Action extends BaseAction {

	private static final long serialVersionUID = 991596026562130540L;
	private T11015BO t11015BO = (T11015BO)ContextUtil.getBean("T11015BO");
	private T11018BO t11018BO = (T11018BO)ContextUtil.getBean("T11018BO");
	public T11018BO getT11018BO() {
		return t11018BO;
	}

	public void setT11018BO(T11018BO t11018bo) {
		t11018BO = t11018bo;
	}
	public T11015BO getT11015BO() {
		return t11015BO;
	}
	public void setT11015BO(T11015BO t11015bo) {
		t11015BO = t11015bo;
	}
	private T3010BO t3010BO;
	private String termId;

	private String recCrtTs;
	private String termSta;
	private String refuseInfo;
	private String state;
	private static final HashMap<String,String> map = new HashMap<String,String>();
	static{
		map.put("00", TblTermInfConstants.TERM_STA_RUN);
		map.put("01", TblTermInfConstants.TERM_STA_ADD_REFUSE);
		map.put("20", TblTermInfConstants.TERM_STA_RUN);
//		map.put("21", TblTermInfConstants.TERM_STA_MOD_REFUSE);
		map.put("21", TblTermInfConstants.TERM_STA_RUN);
		map.put("30", TblTermInfConstants.TERM_STA_STOP);
		map.put("31", TblTermInfConstants.TERM_STA_RUN);
		map.put("50", TblTermInfConstants.TERM_STA_RUN);
		map.put("51", TblTermInfConstants.TERM_STA_STOP);
		map.put("60", TblTermInfConstants.TERM_STA_CANCEL);
//		map.put("61", TblTermInfConstants.TERM_STA_CANCEL_REFUSE);
		map.put("61", TblTermInfConstants.TERM_STA_RUN);
	}
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @param t3010bo the t3010BO to set
	 */
	public void setT3010BO(T3010BO t3010bo) {
		t3010BO = t3010bo;
	}

	/**
	 * @return the termId
	 */
	public String getTermId() {
		return termId;
	}

	/**
	 * @param termId the termId to set
	 */
	public void setTermId(String termId) {
		this.termId = termId;
	}

	/**
	 * @return the recCrtTs
	 */
	public String getRecCrtTs() {
		return recCrtTs;
	}

	/**
	 * @param recCrtTs the recCrtTs to set
	 */
	public void setRecCrtTs(String recCrtTs) {
		this.recCrtTs = recCrtTs;
	}

	/**
	 * @return the termSta
	 */
	public String getTermSta() {
		return termSta;
	}

	/**
	 * @param termSta the termSta to set
	 */
	public void setTermSta(String termSta) {
		this.termSta = termSta;
	}
	
	/**
	 * 
	 * @return the refuseInfo
	 * 2011-8-23上午10:23:26
	 */
	
	public String getRefuseInfo() {
		return refuseInfo;
	}
	/**
	 * 
	 * @param refuseInfo to set
	 * 2011-8-23上午10:24:12
	 */
	public void setRefuseInfo(String refuseInfo) {
		this.refuseInfo = refuseInfo;
	}
	@Override
	protected String subExecute() throws Exception {
		String subTxnId = getSubTxnId();
		TblTermInfTmp tblTermInfTmp = t3010BO.get(CommonFunction.fillString(termId, ' ', 12, true), recCrtTs);
		TblTermInf tblTermInf = t3010BO.getTermInfo(CommonFunction.fillString(termId, ' ', 12, true));
		
		String mchntNo = tblTermInfTmp.getMchtCd();
		
		//查询环讯6.0 系统商户号start
//		 TblMchtBaseInfTmp  MchtInfTmp = t3010BO.getMchnt(CommonFunction.fillString(mchntNo, ' ', 12, true));
//		 String mchtNoHx = "";
//		if(MchtInfTmp != null){
//			mchtNoHx = MchtInfTmp.getMchtNoHx();
//		}else{
//		  TblMchtBaseInf mchtInf = t3010BO.get(mchntNo);
//		    mchtNoHx = mchtInf.getMchtNoHx();
//		}
		//查询6.0商户号end 
		
		if(tblTermInfTmp == null)
			return TblTermInfConstants.T30201_01;
		log("修改人："+tblTermInfTmp.getRecCrtOpr()+" | 审核人："+operator.getOprId());
//uupdate		
//		if(tblTermInfTmp.getRecCrtOpr().trim().equals(operator.getOprId())){
//			return TblTermInfConstants.T30201_02;
//		}
		String state = map.get(termSta+subTxnId);
		tblTermInfTmp.setRecUpdOpr(operator.getOprId());
		tblTermInfTmp.setRecUpdTs(CommonFunction.getCurrentDateTime());
		//boolean flag = t3010BO.update(tblTermInfTmp,state);
		
		if(operator.getOprId().equals(tblTermInfTmp.getRecCrtOpr().trim())){
			return "同一操作员不能审核！";
		}
		
		if(subTxnId.equals("1")) {//审核拒绝
			rspCode = t3010BO.refuseLog(termId, refuseInfo,termSta);
			if(state.equals(TblTermInfConstants.TERM_STA_ADD_REFUSE)){
				t3010BO.refuse(tblTermInfTmp.getId(), state,operator.getOprId());
			}
//			if(TblTermInfConstants.TERM_STA_CANCEL_UNCHK.equals(termSta)){
//				//恢复原来的数据
//				 tblTermInfTmp=(TblTermInfTmp) tblTermInf.clone(tblTermInfTmp);
//				//BeanUtils.copyProperties(tblTermInfTmp1,tblTermInfTmp);	
//				t3010BO.update(tblTermInfTmp);
//			}
			if(termSta.equals("2") || termSta.equals("3") || termSta.equals("6")){//modify to check & stop to check & logout to check
				tblTermInfTmp=(TblTermInfTmp) tblTermInf.clone(tblTermInfTmp);
				tblTermInfTmp.setTermSta(TblTermInfConstants.TERM_STA_RUN);
				//20120831
				tblTermInfTmp.setRecUpdOpr(operator.getOprId());
				tblTermInfTmp.setRecUpdTs(CommonFunction.getCurrentDateTime());
				t3010BO.update(tblTermInfTmp);
			}else if(termSta.equals("5")){//recover to check
				tblTermInfTmp=(TblTermInfTmp) tblTermInf.clone(tblTermInfTmp);
				tblTermInfTmp.setTermSta(TblTermInfConstants.TERM_STA_STOP);
				//20120831
				tblTermInfTmp.setRecUpdOpr(operator.getOprId());
				tblTermInfTmp.setRecUpdTs(CommonFunction.getCurrentDateTime());
				t3010BO.update(tblTermInfTmp);
			}
			return Constants.SUCCESS_CODE;
		} else {//审核通过
			if(TblTermInfConstants.TERM_STA_CANCEL_UNCHK.equals(termSta)){
				t3010BO.update(tblTermInfTmp,TblTermInfConstants.TERM_STA_CANCEL);
				t3010BO.update(tblTermInf,TblTermInfConstants.TERM_STA_CANCEL);
			}
			t3010BO.update(tblTermInfTmp,state);
			t3010BO.save(tblTermInfTmp, operator.getOprId(),tblTermInf==null?null:tblTermInf.getTermBatchNm(),tblTermInf==null?null:tblTermInf.getTermSignSta());
			//终端新增成功需要向签到表中插入终端信息,向终端主密钥表中插入ZTMK密钥
			if(tblTermInfTmp.getProductCd() == null){
				tblTermInfTmp.setProductCd(" ");
			}
			if(TblTermInfConstants.TERM_STA_INIT.equals(termSta)){
				String sql="insert into  TBL_SIGN_INF (INST_ID,INST_NAME,MCHT_ID,TERM_ID,SIGNED_FLAG,SIGN_TIME,TERMID_VALID)" +
						" values('00010000','中国银联','"+tblTermInfTmp.getMchtCd()+"','"+tblTermInfTmp.getId().getTermId().trim()+"','0','"+CommonFunction.getCurrentDateTime()+"','1')";
				commQueryDAO.excute(sql);
				String tmp = "0001";
				//20121018添加机构编号
				String Sql="insert into tbl_pos_key_inf (MERCH_ID,TERM_ID,MPOS_IDX) values ('"+tblTermInfTmp.getMchtCd().trim()+"','"+tblTermInfTmp.getId().getTermId().trim()+"','"+tmp+"')";
				commQueryDAO.excute(Sql);
			} else if(TblTermInfConstants.TERM_STA_MOD_UNCHK.equals(termSta)){
				String upTMKSql = "";
				//判断终端主密钥是否存在
				String countsql = "select count(*) from tbl_inst_key_inf where MERCH_ID='" + tblTermInfTmp.getMchtCd().trim() +"' and TERM_ID='"+tblTermInfTmp.getId().getTermId().trim()+"' and INST_ID='00010000' ";//20121018添加机构编号
				if("0".equals(commQueryDAO.findCountBySQLQuery(countsql))){
				}else{
				}
			}else if(TblTermInfConstants.TERM_STA_STOP_UNCHK.equals(termSta)){//停用待审核
				String sql="update TBL_SIGN_INF set TERMID_VALID='0' " + "where MCHT_ID='"+tblTermInfTmp.getMchtCd().trim()+"' and TERM_ID='"+tblTermInfTmp.getId().getTermId().trim()+"' and INST_ID='00010000'";
				commQueryDAO.excute(sql);
			}else if(TblTermInfConstants.TERM_STA_REC_UNCHK.equals(termSta)){//启用待审核
				String sql="update TBL_SIGN_INF set TERMID_VALID='1' " + "where MCHT_ID='"+tblTermInfTmp.getMchtCd().trim()+"' and TERM_ID='"+tblTermInfTmp.getId().getTermId().trim()+"' and INST_ID='00010000'";
				commQueryDAO.excute(sql);
			}else if(TblTermInfConstants.TERM_STA_CANCEL_UNCHK.equals(termSta)){//注销待审核
				String delete ="delete from TBL_SIGN_INF " + "where MCHT_ID='"+tblTermInfTmp.getMchtCd().trim()+"' and TERM_ID='"+tblTermInfTmp.getId().getTermId().trim()+"' and INST_ID='00010000'";
				commQueryDAO.excute(delete);
				delete="delete from tbl_pos_key_inf " +//20121018添加机构编号
				"where MERCH_ID='" + tblTermInfTmp.getMchtCd().trim()+"' and TERM_ID='"+tblTermInfTmp.getId().getTermId().trim()+"'";
//				commQueryDAO.excute(delete);
				commQueryDAO.excute(delete);
			}
			
//			long minAmtL = (long) 0.00;
//		    long maxAmtL = (long) 9999999999.99;
			System.out.println("终端状态state："+termSta);
			if(termSta.equals("0")){
				
			
		    TblTermChannelInf tblTermChannelInfTmp = new TblTermChannelInf();
		    tblTermChannelInfTmp.setId(StringUtil.getUUID());
		    tblTermChannelInfTmp.setTermIns("00000002");
		    String mchtId = tblTermInfTmp.getMchtCd().trim();
//		    String sql = "select count(*) from TBL_INST_BDB_BANK_CODE_TMP where TMP_NO='"+tmpNo+"' and state !=1 and BANK_CODE ='"+bankCode+"'";
		    String sql = "select MCC from TBL_MCHT_BASE_INF where MCHT_NO = '" +mchtId+ "'";
			String mcc=commQueryDAO.findCountBySQLQuery(sql);
	 		tblTermChannelInfTmp.setMchtMcc(mcc);//商户MCC码
	 		tblTermChannelInfTmp.setMchtId(tblTermInfTmp.getMchtCd().trim());
	 		tblTermChannelInfTmp.setMchtTermId(tblTermInfTmp.getId().getTermId().trim());
	 		tblTermChannelInfTmp.setInsMcc("*");
	 		tblTermChannelInfTmp.setInsMcht(tblTermInfTmp.getMchtCd().trim());
	 		tblTermChannelInfTmp.setInsTerm(tblTermInfTmp.getId().getTermId().trim());
	 		tblTermChannelInfTmp.setChannelType("02");
	 		tblTermChannelInfTmp.setReserve01("0001");//索引值
	 		tblTermChannelInfTmp.setModiOprId(operator.getOprId());
	 		tblTermChannelInfTmp.setModiTime(CommonFunction.getCurrentDateTime());
			tblTermChannelInfTmp.setStat("0");
	 		tblTermChannelInfTmp.setCreTime(CommonFunction.getCurrentDateTime());
	 		tblTermChannelInfTmp.setCreOprId(operator.getOprId());
			tblTermChannelInfTmp.setCheckTime(CommonFunction.getCurrentDateTime());
			tblTermChannelInfTmp.setCheckOprId(operator.getOprId());
			tblTermChannelInfTmp.setStat("2");	
			
			TblTermChannelInf tblTermChannelInfTmp1 = new TblTermChannelInf();
			tblTermChannelInfTmp1.setId(StringUtil.getUUID());
		    tblTermChannelInfTmp1.setTermIns("12345678");
		    String mchtId1 = tblTermInfTmp.getMchtCd().trim();
//		    String sql1 = "select MCC from TBL_MCHT_BASE_INF where MCHT_NO = '" +mchtId1+ "'";
//			String mcc1=commQueryDAO.findCountBySQLQuery(sql1);
	 		tblTermChannelInfTmp1.setMchtMcc(mcc);//商户MCC码
	 		tblTermChannelInfTmp1.setMchtId(tblTermInfTmp.getMchtCd().trim());
	 		tblTermChannelInfTmp1.setMchtTermId(tblTermInfTmp.getId().getTermId().trim());
	 		tblTermChannelInfTmp1.setInsMcc("*");
	 		tblTermChannelInfTmp1.setInsMcht(tblTermInfTmp.getMchtCd().trim());
	 		tblTermChannelInfTmp1.setInsTerm(tblTermInfTmp.getId().getTermId().trim());
	 		tblTermChannelInfTmp1.setChannelType("02");
	 		tblTermChannelInfTmp1.setReserve01("0001");//索引值
	 		tblTermChannelInfTmp1.setModiOprId(operator.getOprId());
	 		tblTermChannelInfTmp1.setModiTime(CommonFunction.getCurrentDateTime());
			tblTermChannelInfTmp1.setStat("0");
	 		tblTermChannelInfTmp1.setCreTime(CommonFunction.getCurrentDateTime());
	 		tblTermChannelInfTmp1.setCreOprId(operator.getOprId());
			tblTermChannelInfTmp1.setCheckTime(CommonFunction.getCurrentDateTime());
			tblTermChannelInfTmp1.setCheckOprId(operator.getOprId());
			tblTermChannelInfTmp1.setStat("2");	
			
//			tblTermChannelInfTmp.setMaxAmt(maxAmtL);
//			tblTermChannelInfTmp.setMinAmt(minAmtL);
//			t11015BO.add(tblTermChannelInfTmp);
//			TblTermChannelInf tblTermChannelInf = new TblTermChannelInf();
//			BeanUtils.copyProperties(tblTermChannelInf, tblTermChannelInfTmp);
//			rspCode = t11015BO.update(tblTermChannelInfTmp);
				try {
					rspCode = t11018BO.add(tblTermChannelInfTmp);
					rspCode = t11018BO.add(tblTermChannelInfTmp1);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return Constants.SUCCESS_CODE;
			
			
		}
	}
	
	private String statusSwitch(String termStatus){//转换并生成终端状态值,传递给德益富6.0接收
		if(termStatus.equals(TblTermInfConstants.TERM_STA_INIT)            //新增待审核,通过
				||termStatus.equals(TblTermInfConstants.TERM_STA_MOD_UNCHK)//修改待审核,通过
				||termStatus.equals(TblTermInfConstants.TERM_STA_REC_UNCHK))//恢复待审核,通过
			return "0";
		if(termStatus.equals(TblTermInfConstants.TERM_STA_STOP_UNCHK)          //停用待审核,通过
				||termStatus.equals(TblTermInfConstants.TERM_STA_CANCEL_UNCHK))//注销待审核通过
			return "1";
		return "";
	}
	
	public HashMap<String, String> posAdd(TblTermInfTmp tblTermInf,String mchtNoHx,String status){//通知德益富6.0，终端状态发生变化
		HashMap<String, String> map = new LinkedHashMap<String, String>();
		String reqXmlStr ="";//请求xml字符串
		String txnCode = "POS005";//交易码

		String DateTime = CommonFunction.getCurrentDateTimeForShow();              //时间
//		System.out.print(DateTime.substring(0,10)+"...."+DateTime.substring(11,19));
		
		Random r = new Random();
		int c = r.nextInt(9999);                                                   //流水号
		String cstr = CommonFunction.fillString(String.valueOf(c), '0', 4, false);
		String mchno = mchtNoHx;                                                   //商户号
		String poscode = tblTermInf.getId().getTermId().trim();                    //终端号
        String terminal = CommonFunction.getAddress();                             //机器名称
        String posadr ="";                                                         //布设地址
        if(tblTermInf.getTermAddr()!=null && !tblTermInf.getTermAddr().equals("")){
        	 posadr = tblTermInf.getTermAddr().toString();
        }
        
//      pos类型
        String  poskind = tblTermInf.getTermTp().toString();
        
//      status状态:新增/修改/启用审核通过时为1，注销/停用审核通过时为0
//        String status ="1";

		String body =
		   "<body>"+
		        "<cusid>"+mchno+"</cusid>"+
		        "<poscode>"+poscode+"</poscode>"+
		        "<posadr>"+posadr+"</posadr>"+
		        "<poskind>"+poskind+"</poskind>"+
		        "<status>"+status+"</status>"+
           "</body>";
//      MD5
		String  miyao = "01NulpxpmmJVTn7C2sAftIwsGo2goKBjYiLSoBmZ1DTAM41v58fE1eHHe4EXMrb1AAXK2FxRgADFOL5lNgAGEXHT1dNJXonkC1am6GTQko8Q1bStZ2kFGvNCSetySDTR";
		
		String md5 = CommonFunction.Md5(body+miyao);
		
		System.out.print("加密字符串+++++++++:["+body+miyao+"]");
		reqXmlStr = 
	      "<tcp>"+
	        "<head>"+
	           "<trancode>"+txnCode+"</trancode>"+
	           "<terminal>"+terminal+"</terminal>"+
	           "<serseq>"+cstr+"</serseq>"+
	           "<senddate>"+DateTime.substring(0,10)+"</senddate>"+
	           "<sendtime>"+DateTime.substring(11,19)+"</sendtime>"+
	           "<md5>"+md5+"</md5>"+
	           "<msgcode></msgcode>"+
	           "<msg></msg>"+
	        "</head>"+body+
	     "</tcp>#END";
		SocketConnect socket = null;
		
		try {
			DuXMLDoc doc = new DuXMLDoc();
			System.out.println("请求报文:["+reqXmlStr+"]");
//			map = doc.xmlElements(reqXmlStr);
	
			socket = new SocketConnect(reqXmlStr.toString());
			
			if (null != socket) {
				socket.run();
				String resp = socket.getRsp();
//				resp = URLEncoder.encode(resp,"UTF-8");
				System.out.println("返回报文:"+resp);
				resp = CommonFunction.changeCharsetOld(resp,Charset.defaultCharset().toString(),"UTF-8");
				System.out.println("返回报文:"+resp);
				map = doc.posAddXml(resp);
			}
			log("向德益富6.0发送终端状态报文,终端号为:"+poscode+",状态为:"+status);
		} catch (UnknownHostException e) {
			map.put("result", "reeor");//响应码
			map.put("msg", "与6.0通信出错");//响应内容
		} catch (IOException e) {
			map.put("result", "reeor");//响应码
			map.put("msg", "与6.0通信出错");//响应内容
		} catch (Exception e) {
			map.put("result", "reeor");//响应码
			map.put("msg", "与6.0通信出错");//响应内容
		} finally {
			try {
				if (null != socket) {
					socket.close();
				}
			} catch (Exception e) {
				map.put("result", "reeor");//响应码
				map.put("msg", "与6.0通信出错");//响应内容
			}
		}
		return map;
	}
}

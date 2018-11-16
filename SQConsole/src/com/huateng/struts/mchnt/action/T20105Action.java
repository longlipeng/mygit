package com.huateng.struts.mchnt.action;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import com.huateng.bo.base.T10205BO;
import com.huateng.bo.impl.mchnt.TblMchntService;
import com.huateng.bo.mchnt.T20105BO;
import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.common.SysParamConstants;
import com.huateng.common.TblMchntInfoConstants;
import com.huateng.commquery.dao.ICommQueryDAO;
import com.huateng.dao.impl.mchnt.TblMchtBaseInfDAO;
import com.huateng.dao.impl.mchnt.TblMchtBaseInfTmpDAO;
import com.huateng.dwr.mchnt.T20100;
import com.huateng.po.TblTermInfTmp;
import com.huateng.po.TblTermInfTmpPK;
import com.huateng.po.mchnt.TblHisDiscAlgo2Tmp;
import com.huateng.po.mchnt.TblMchtBaseInf;
import com.huateng.po.mchnt.TblMchtBaseInfTmp;
import com.huateng.po.mchnt.TblMchtSettleInfPK;
import com.huateng.po.mchnt.TblMchtSettleInfTmp;

import com.huateng.po.mchnt.TblMchtSupp1Tmp;
import com.huateng.struts.pos.TblTermInfConstants;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.BeanUtils;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.GenerateNextId;
import com.huateng.system.util.SysParamUtil;


@SuppressWarnings("serial")
public class T20105Action extends BaseAction {
	
	T10205BO t10205BO = (T10205BO) ContextUtil.getBean("T10205BO");

	private String ind;
	private String CardTp;
	
	private String insIdCd;
	//一磁偏移量
	private String acc1Offset;	
    //一磁长度
	private String acc1Len;
	//一磁所在磁道号
	private String acc1Tnum;	
	//二磁偏移量
	private String acc2Offset;	
	//二磁长度
	private String acc2Len;	
	//二磁所在磁道号
	private String acc2Tnum;
	//卡bin偏移量
	private String binOffSet;
	//卡bin长度
	private String binLen;
	//卡bin起始值
	private String binStaNo;	
	//卡bin结束值
	private String binEndNo;	
	//卡bin所在磁道号
	private String binTnum;	
	//描述
	private String cardDis;	
	
	private String tblBankBinInfList;
	
	private static	ICommQueryDAO commQueryDAO = (ICommQueryDAO) ContextUtil.getBean("CommQueryDAO");
	
	private T20105BO t20105BO = (T20105BO) ContextUtil.getBean("T20105BO");
	
	private TblMchtBaseInfTmpDAO tblMchtBaseInfTmpDAO = (TblMchtBaseInfTmpDAO) ContextUtil.getBean("tblMchtBaseInfTmpDAO");
	
	private TblMchtBaseInfDAO tblMchtBaseInfDAO = (TblMchtBaseInfDAO) ContextUtil.getBean("tblMchtBaseInfDAO");
	/* (non-Javadoc)
	 * @see com.huateng.struts.system.action.BaseAction#subExecute()
	 */
	@Override
	protected String subExecute(){
		try {
			if("uploadBFZ".equals(method)) {
				rspCode = uploadBFZ();
			}else if("uploadBFS".equals(method)) {
				rspCode = uploadBFS();
			}
		} catch (Exception e) {
			log("操作员编号：" + operator.getOprId()+ "商户批量导入操作" + getMethod() + "失败，失败原因为："+e.getMessage());
		}
		return rspCode;
	}

/**
 * 
 * 
 * 商户信息批量上传
 * 
 * 
 * 
 * 
 * */	
	private String uploadBFS() {
		List<TblMchtBaseInfTmp> MchtBaseInfListAdd=new ArrayList<TblMchtBaseInfTmp>();	
		List<TblMchtSupp1Tmp> MchtSupp1ListAdd=new ArrayList<TblMchtSupp1Tmp>();	
		List<TblHisDiscAlgo2Tmp> HisDiscAlgo2ListAdd=new ArrayList<TblHisDiscAlgo2Tmp>();
		List<TblMchtSettleInfTmp> MchtSettleInfTmpListAdd=new ArrayList<TblMchtSettleInfTmp>();
	
		//审批流程
	

		
		
		List<String> errList=new ArrayList<String>();  //记录在批量导入的过程中出现的错误
		
		int success = 0;
		int fail = 0;
		
		try {
			for(File file : files) {
				BufferedReader reader = 
					new BufferedReader(new InputStreamReader(new FileInputStream(file), "GBK"));
				int disc_idInt=Integer.valueOf(GenerateNextId.getAlgo2Id());
//				reader.readLine();//第一行为表头
				while(reader.ready()){
					String tmp = reader.readLine();
					if (!StringUtil.isNull(tmp)) {
						String[] data = tmp.split(",");
						if(data[0].trim().startsWith("商户名称*")&&data[1].trim().startsWith("所属分公司*")){
							continue;
						}
						TblMchtBaseInfTmp tblMchtBaseInfTmp=new TblMchtBaseInfTmp();
						TblMchtSupp1Tmp tblMchtSupp1Tmp=new TblMchtSupp1Tmp();
						TblHisDiscAlgo2Tmp tblHisDiscAlgo2Tmp=new TblHisDiscAlgo2Tmp();
						TblMchtSettleInfTmp tblMchtSettleInfTmp=new TblMchtSettleInfTmp();
						
						for(int ii=0;ii<data.length;ii++){
							data[ii]=data[ii].replaceAll("\"","").trim();
						}
							tblMchtBaseInfTmp.setMchtLvl("0");//商户类别   (默认值为0)
							tblMchtBaseInfTmp.setRislLvl("-");//风险级别  //by mike
							tblMchtBaseInfTmp.setMchtLvl("0");//商户类别   (默认值为0)  //by mike
							tblMchtBaseInfTmp.setConnType("0");//商户类型   (默认值为0)  //by mike
							tblMchtBaseInfTmp.setSaAction("0");//受控处理动作

							tblMchtBaseInfTmp.setCupMchtFlg("1");//银联卡受控标志
							tblMchtBaseInfTmp.setDebMchtFlg("1");//借记卡受理标志
							tblMchtBaseInfTmp.setCreMchtFlg("1");//贷记卡受理标志
							tblMchtBaseInfTmp.setCdcMchtFlg("1");//一帐通受理标志
							
							tblMchtBaseInfTmp.setMchtStatus(TblMchntInfoConstants.MCHNT_ST_NEW_UNCK); //1：添加待审核
							tblMchtBaseInfTmp.setManuAuthFlag("0");  //0-否 1-是     是否支持人工授权交易
							tblMchtBaseInfTmp.setPassFlag("0");  //是否支持无磁无密交易
							tblMchtBaseInfTmp.setDiscConsFlg("0"); // 是否支持折扣消费
							tblMchtBaseInfTmp.setMchtGroupFlag("0"); //是否集团商户
							
							//申请日期
							tblMchtBaseInfTmp.setApplyDate(CommonFunction.getCurrentDate());
							// 记录修改时间
							tblMchtBaseInfTmp.setRecUpdTs(CommonFunction.getCurrentDateTime());
							// 记录创建时间
							tblMchtBaseInfTmp.setRecCrtTs(CommonFunction.getCurrentDateTime());
							// 记录修改人
							tblMchtBaseInfTmp.setUpdOprId("");
							// 记录创建人
							tblMchtBaseInfTmp.setCrtOprId(operator.getOprId());
							
							
							tblMchtBaseInfTmp.setMchtNm(data[0]);   //商户名称
							tblMchtBaseInfTmp.setAcqInstId(data[1]);  //所属分公司
							tblMchtBaseInfTmp.setMcc(data[2]);  //MCC
							//根据MCC查询出商户组别MchtGroup
							List  listMccGroup=commQueryDAO.findBySQLQuery("select MCHNT_TP_GRP from TBL_INF_MCHNT_TP where MCHNT_TP='"+tblMchtBaseInfTmp.getMcc().trim()+"'",0,10);   //根据mcc(MCHNT_TP)查询出TBL_INF_MCHNT_TP中的MCHNT_TP_GRP
							if(listMccGroup.size()!=0){
								tblMchtBaseInfTmp.setMchtGrp((String)listMccGroup.get(0));  //商户组别
							}
							tblMchtBaseInfTmp.setLicenceNo(data[3]); //营业执照
							if(!StringUtil.isNull(data[4])&&!StringUtil.isEmpty(data[5].trim())){
								tblMchtBaseInfTmp.setFaxNo(data[4]);  //税务登记证
							}else{
								tblMchtBaseInfTmp.setFaxNo("               ");  //税务登记证
							}
							if(!StringUtil.isNull(data[5])&&!StringUtil.isEmpty(data[5].trim())){
								tblMchtBaseInfTmp.setBankLicenceNo(data[5]);  //组织结构代码
							}else{
								tblMchtBaseInfTmp.setBankLicenceNo("");  //组织结构代码
							}
							tblMchtBaseInfTmp.setBusAmt(data[6]);  //注册资金
							tblMchtBaseInfTmp.setRegAddr(data[7]);  //注册地址
							tblMchtBaseInfTmp.setLicenceEndDate(data[8]);  //注册期限
							if(data[10].length()==1){
								tblMchtBaseInfTmp.setEtpsAttr("0"+data[10]);  //商户性质  01,02,03
							}else{
								tblMchtBaseInfTmp.setEtpsAttr(data[10]);
							}
							
							if(!StringUtil.isNull(data[11])&&StringUtil.isEmpty(data[11].trim())){
								tblMchtBaseInfTmp.setHomePage(data[11]);  //企业网址
							}
							
							tblMchtBaseInfTmp.setCommEmail(data[12]); //企业邮箱
							if(!StringUtil.isNull(data[13])&&StringUtil.isEmpty(data[13].trim())){
								tblMchtBaseInfTmp.setFax(data[13]);  //企业传真
							}
							tblMchtBaseInfTmp.setManager(data[14]);  //法人姓名
							if(data[16].length()==1){
								tblMchtBaseInfTmp.setArtifCertifTp("0"+data[16]); //法人证件类型  01,02,03
							}else{
								tblMchtBaseInfTmp.setArtifCertifTp(data[16]);
							}
							
							tblMchtBaseInfTmp.setIdentityNo(data[17]); //法人证件号码
							tblMchtBaseInfTmp.setManagerTel(data[18]);  //法人联系电话
							tblMchtBaseInfTmp.setContact(data[19]); //联系人/授权人姓名
							tblMchtBaseInfTmp.setCommTel(data[21]);  //联系人/授权人电话
							tblMchtBaseInfTmp.setAddr(data[22]);  //商户地址
							tblMchtBaseInfTmp.setOpenTime(data[25]); //商户开始营业时间
							tblMchtBaseInfTmp.setCloseTime(data[26]); //商户结束营业时间
							tblMchtBaseInfTmp.setNetTel(data[35]); //拓展人电话
							tblMchtBaseInfTmp.setOperNo(data[37]);  //客户经理工号
							tblMchtBaseInfTmp.setOperNm(data[38]); //客户经理名字
						//	tblMchtBaseInfTmp.setSignInstId(data[49]);  //签约机构
							
							//根据mcc，city生成15位商户号
							T20100 t2=new T20100();
							HashMap<String, String> MchtMap = new LinkedHashMap<String, String>();
							MchtMap=t2.createMchtNo(data[2], data[32]);
							tblMchtBaseInfTmp.setMchtNo(MchtMap.get("mchtNoInPut"));  //15位商户号
							String xZ = tblMchtBaseInfTmp.getMchtNo().substring(3,7);//行政区域码
							tblMchtBaseInfTmp.setAreaNo(xZ);
							tblMchtBaseInfTmp.setMchtNoHx(MchtMap.get("mchtNoByInPut")); //北京商户号和随行付商户号
							
					
							
							
							
							
							
							
							
							
							tblMchtSupp1Tmp.setBusMain(data[9]);  //主营业务
							tblMchtSupp1Tmp.setLinkTel(data[20]); //联系人/授权人身份证号码
							tblMchtSupp1Tmp.setBusScope(data[30]); //经营店名
							tblMchtSupp1Tmp.setCashierDeskNum(data[23]); //终端数量
							tblMchtSupp1Tmp.setOpenDate(data[24]);  //开业日期
							tblMchtSupp1Tmp.setShopNum(data[27]);  //直营连锁店数量
							tblMchtSupp1Tmp.setAcreage(data[28]);  //经营面积
							tblMchtSupp1Tmp.setEmpNum(data[29]);  //员工人数
							tblMchtSupp1Tmp.setProvince(data[31]); //省
							tblMchtSupp1Tmp.setCity(data[32]) ;   //市
							tblMchtSupp1Tmp.setAcreage(data[33]);//县
							tblMchtSupp1Tmp.setExpander(data[34]);  //拓展人
							tblMchtSupp1Tmp.setNationality(data[15]);//法人/负责人国籍
							if(data[36].startsWith("0")){
								tblMchtSupp1Tmp.setSerLvl(data[36].substring(1,data[36].length()));  //服务级别 1,2,3
							}else{
								tblMchtSupp1Tmp.setSerLvl(data[36]);
							}
							
							
							
							tblMchtSupp1Tmp.setMchtNo(tblMchtBaseInfTmp.getMchtNo());  //商户号
							/*
							tblMchtSupp1Tmp.setDepart(data[3]);
							tblMchtSupp1Tmp.setNationality("86"); //法人代表国籍
							tblMchtSupp1Tmp.setBusMain(data[19]);
							tblMchtSupp1Tmp.setProvince(data[20]);
							tblMchtSupp1Tmp.setCity(data[21]);   //城市
							if(data[22]!=""){
								tblMchtSupp1Tmp.setArea(data[22]);
							}
							tblMchtSupp1Tmp.setBusArea(data[25]);
							tblMchtSupp1Tmp.setBusZone(data[26]);
							tblMchtSupp1Tmp.setAcreage(data[27]);
							tblMchtSupp1Tmp.setAreaType(data[28]);
							if(data[29]==""){
								tblMchtSupp1Tmp.setExpander(data[24]); //如果拓展人为空，则默认填写客户经理名字
							}else{
								tblMchtSupp1Tmp.setExpander(data[29]); 
							}
							tblMchtSupp1Tmp.setMchtNo(data[30]);  //商户号*/
							
							
							
							
							
							
							
							tblMchtSettleInfTmp.setSettleRpt(data[39]);  //结算账户类型
							tblMchtSettleInfTmp.setCurrAccount(data[40]);//商户当前使用帐号
							tblMchtSettleInfTmp.setBankAccountCode(data[41]); //商户法人账号开户行机构代码
							tblMchtSettleInfTmp.setCorpBankName(data[42]);//法人账号开户行名称
							tblMchtSettleInfTmp.setFeeAcctNm(data[43]);  //法人账户名称
							tblMchtSettleInfTmp.setFeeAcct(data[44]);  //法人账户账号
							tblMchtSettleInfTmp.setCompAccountBankCode(data[45]);//商户公司帐号开户行机构代码
							tblMchtSettleInfTmp.setCompAccountBankName(data[46]);//商户公司帐号开户行名称
							if(tblMchtSettleInfTmp.getSettleRpt().trim().equals("2")){  //只有结算账户类型为对私，才去填写公司账户
								tblMchtSettleInfTmp.setSettleAcctNm(data[47]);  //公司账户名称
								tblMchtSettleInfTmp.setSettleAcct(data[48]);  //公司账户账号
							}
							tblMchtSettleInfTmp.setSettleChn(data[49]);  //结算方式
							tblMchtSettleInfTmp.setSettleType(data[50]);//商户结算周期*
							tblMchtSettleInfTmp.setDirFlag(data[51]);//定向委托付款标志
							tblMchtSettleInfTmp.setDirBankCode(data[52]);//定向委托帐号帐号开户行机构代码
							tblMchtSettleInfTmp.setDirBankName(data[53]);//定向委托帐号开户行名称
							tblMchtSettleInfTmp.setDirAccountName(data[54]);//定向委托帐号开户名
							tblMchtSettleInfTmp.setDirAccount(data[55]);//定向委托账号
							tblMchtSettleInfTmp.setAutoFlag(data[56]);//自动提现标志
							tblMchtSettleInfTmp.setHolidaySetFlag(data[57]);//节假日提现标志
							tblMchtSettleInfTmp.setCreFlag(data[58]);//信用卡刷卡功能标志
							
//							tblMchtSettleInfTmp.setMchtNo(tblMchtBaseInfTmp.getMchtNo());  //商户号
							
							TblMchtSettleInfPK id = new TblMchtSettleInfPK();
							id.setMchtNo(tblMchtBaseInfTmp.getMchtNo());
							id.setTermId("*");//默认全部终端
							tblMchtSettleInfTmp.setId(id);
							tblMchtSettleInfTmp.setFeeRate(data[59]);  //计费代码
//							tblMchtSettleInfTmp.setSettleType("0");  //日结  商户结算周期
							tblMchtSettleInfTmp.setRateFlag("-");//手续费结算类型
							tblMchtSettleInfTmp.setFeeType("3"); //手续费类型 ,清算时取的是费率表中的类型 -- 环讯系统
							tblMchtSettleInfTmp.setFeeFixed("-");//该字段无效-- 环讯系统
							tblMchtSettleInfTmp.setFeeMaxAmt("0");//该字段无效-- 环讯系统
							tblMchtSettleInfTmp.setFeeMinAmt("0");//该字段无效-- 环讯系统
							tblMchtSettleInfTmp.setFeeDiv1("1");
							tblMchtSettleInfTmp.setFeeDiv2("2");
							tblMchtSettleInfTmp.setFeeDiv3("3");
							tblMchtSettleInfTmp.setRecCrtTs(CommonFunction.getCurrentDateTime());
							tblMchtSettleInfTmp.setRecUpdTs(CommonFunction.getCurrentDateTime());
							
					
							
							
							
							String DISC_ID=GenerateNextId.getAlgo2Id();
							
//							tblHisDiscAlgo2Tmp.setFEE_VALUE1(data[46]);  //签约费率
							tblHisDiscAlgo2Tmp.setMCHT_NO(tblMchtBaseInfTmp.getMchtNo());  //商户号
							tblHisDiscAlgo2Tmp.setCITY_CODE(data[32]);
							tblHisDiscAlgo2Tmp.setTmpNo(tblMchtBaseInfTmp.getMchtNo());
							tblHisDiscAlgo2Tmp.setDISC_ID(DISC_ID);
							tblHisDiscAlgo2Tmp.setTERM_ID("*");
							tblHisDiscAlgo2Tmp.setTO_BRCH_NO("*");
							tblHisDiscAlgo2Tmp.setFK_BRCH_NO("*");
							tblHisDiscAlgo2Tmp.setCARD_TYPE("*");
							tblHisDiscAlgo2Tmp.setCHANNEL_NO("*");
							tblHisDiscAlgo2Tmp.setBUSINESS_TYPE("*");
							tblHisDiscAlgo2Tmp.setTXN_NUM("*");
							tblHisDiscAlgo2Tmp.setFLAG("0");
							tblHisDiscAlgo2Tmp.setFeeType(data[59]);
							tblHisDiscAlgo2Tmp.setFeeTypeOld(data[59]);
							tblHisDiscAlgo2Tmp.setREC_CRT_TS(CommonFunction.getCurrentDateTime()); //创建时间
							tblHisDiscAlgo2Tmp.setREC_UPD_TS(CommonFunction.getCurrentDateTime());
							tblHisDiscAlgo2Tmp.setREC_UPD_USR_ID(operator.getOprId());
							
							tblHisDiscAlgo2Tmp.setSA_SATUTE(CommonFunction.ADD_TO_CHECK); //0：添加费率   4:修改费率  
							
							tblMchtBaseInfTmp.setMchtCnAbbr(data[60]);//商户简称*
							tblMchtBaseInfTmp.setEngName(data[61]);//商户英文名称
							tblMchtBaseInfTmp.setMchtEnAbbr(data[62]);//商户英文简称
							tblMchtBaseInfTmp.setBusiRangeId(data[63]);//经营范围
							tblMchtBaseInfTmp.setPostalCode(data[64]);//邮政编号*
							tblMchtBaseInfTmp.setInsAddr(data[65]);//装机地址
							tblMchtBaseInfTmp.setBelInd(data[66]);//所属行业
							tblMchtBaseInfTmp.setOwnerBusi(data[67]);//经营场所权属
							tblMchtBaseInfTmp.setICPrecordNo(data[68]);//ICP备案号
							tblMchtBaseInfTmp.setICPcompName(data[69]);//ICP备案公司名称
							tblMchtBaseInfTmp.setMailAddr(data[70]);//合同邮寄地址
							tblMchtBaseInfTmp.setContRate(data[71]);//商户签约费率
							tblMchtBaseInfTmp.setSinDebAmount(data[72]);//客户申请单笔借记卡交易金额
							tblMchtBaseInfTmp.setDayDebAmount(data[73]);//客户申请单日借记卡交易金额
							tblMchtBaseInfTmp.setMonDebAmount(data[74]);//客户申请单月借记卡交易金额
							tblMchtBaseInfTmp.setSinCreAmount(data[75]);//客户申请单笔贷记卡交易金额
							tblMchtBaseInfTmp.setDayCreAmount(data[76]);//客户申请单日贷记卡交易金额
							tblMchtBaseInfTmp.setMonCreAmount(data[77]);//客户申请单月贷记卡交易金额
							tblMchtBaseInfTmp.setContName(data[78]);//商户调单联系人名称
							tblMchtBaseInfTmp.setContTel(data[79]);//商户调单联系人电话
							tblMchtBaseInfTmp.setBusiTel(data[80]);//商户电话
							tblMchtBaseInfTmp.setAppComm(data[81]);//申请人留言
							tblMchtBaseInfTmp.setAgentNo(data[82]);//代理商ID
							tblMchtBaseInfTmp.setRegionBel(data[83]);//所属大区
							
							tblMchtSupp1Tmp.setMonthTotalAmt(data[84]);//月消费总额*
							tblMchtSupp1Tmp.setSingleAmt(data[85]);//单笔消费金额*
							tblMchtSupp1Tmp.setPreAuthor(data[86]);//预授权功能
							tblMchtSupp1Tmp.setReturnFunc(data[87]);//退货功能
							tblMchtSupp1Tmp.setIsWineshop(data[88]);//是否酒店行业
							tblMchtSupp1Tmp.setWineshopLvl(data[89]);//酒店星级
							tblMchtSupp1Tmp.setIsAppOutSide(data[90]);//是否申请外卡
							tblMchtSupp1Tmp.setHasInnerPosExp(data[91]);//是否有POS内卡受理经验
							tblMchtSupp1Tmp.setHasOurPosExp(data[92]);//是否有POS外卡受理经验
							
							
							
							
							
							
							
							
							
							
							
							/*String mYears=(Integer.valueOf(new Date().getYear())+1900)-Integer.valueOf(data[24].substring(0,4))+"";  //根据开业日期算出经营年限
							
							
							String feeValue=data[46];  //签约费率
							double feeValueDouble=Double.valueOf(feeValue);
							String feeRateStr=(String)CommonFunction.getCommQueryDAO().findBySQLQuery("select trim(FEE_RATE) from tbl_mcc_end_rate where mcc='"+data[2]+"'",0,10).get(0);
							double feeRateDouble=Double.valueOf(feeRateStr);
							double feeChaD=feeValueDouble-feeRateDouble;
							String feeChaStr=String.valueOf(feeChaD).trim();
							if(feeChaStr.indexOf(".")!=-1){
								int shuCount=feeChaStr.length()-feeChaStr.indexOf(".")-1;
								if(shuCount>3){
									feeChaStr=feeChaStr.substring(0,feeChaStr.length()-shuCount+3);
								}
							}
							
							
							
							//验证条件
							//1.设置手续费代码
							String rateSql="select disc_id from tbl_his_disc_algo_tmp where MAX_FEE="+tblHisDiscAlgo2Tmp.getMAX_FEE1()
							+" and MIN_FEE="+tblHisDiscAlgo2Tmp.getMIN_FEE1()+" and FLAG="+tblHisDiscAlgo2Tmp.getFLAG()
							+" and FEE_VALUE="+tblHisDiscAlgo2Tmp.getFEE_VALUE1()+" group by disc_id having max(index_num)=0";
							List<Object> listrateCode=commQueryDAO.findBySQLQuery(rateSql);
							if(listrateCode.size()!=0){
								String rateCode=(String)listrateCode.get(0);
								if(rateCode==null||rateCode.trim()==""){ 
									errList.add(data[30]+"--没有找到对象的计费代码");
									fail++;
									continue;
								}
								else{
									tblHisDiscAlgo2Tmp.setFeeType(rateCode);
									tblMchtSettleInfTmp.setFeeRate(rateCode);  //计费代码
								}
							}else{
								errList.add(data[30]+"--没有找到对象的计费代码");
								fail++;
								continue;
							}*/
//							String rateCode=(String) CommonFunction.getCommQueryDAO().findBySQLQuery(rateSql).get(0);
							//2.检查15商户编号是否已存在
							String mchtNoSql="select count(*) from TBL_MCHT_BASE_INF_TMP where  mcht_no='"+tblMchtBaseInfTmp.getMchtNo()+"' and mcht_status<>'8'";
							String result= commQueryDAO.findCountBySQLQuery(mchtNoSql);
							if(!result.equals("0")){
								errList.add(data[30]+"--商户号已经存在");
								fail++;
								continue;
							}
						/*	//3.检查北京商户号是否已存在
							String mchtNoHxSql="select count(*) from TBL_MCHT_BASE_INF_TMP where  mcht_no_hx='"+tblMchtBaseInfTmp.getMchtNoHx()+"' and mcht_status<>'8'";;
							result=commQueryDAO.findCountBySQLQuery(mchtNoHxSql);
							if(!result.equals("0")){
								errList.add(data[40]+"--北京商户号已经存在");
								fail++;
								continue;
							}*/
							//4.检验商户账户账号， 法人身份证，营业执照,税务登记证号码   是否已经存在
						/*	String checkSql = "select count(B.MCHT_NO) from TBL_MCHT_BASE_INF_TMP B,TBL_MCHT_SETTLE_INF_TMP S " +
							"where " +
							//20120823修改为法人身份证不区分大小写
							" upper(IDENTITY_NO)='" + data[17].toUpperCase() + "' And TRIM(LICENCE_NO)='" + data[3] + "' AND B.MCHT_NO=S.MCHT_NO " ; ;
							//税务登记证
							if(data[4]!=null && !"".equals(data[4]))
								checkSql += " AND TRIM(FAX_NO) = '" + data[4] +"'";
							result = commQueryDAO.findCountBySQLQuery(checkSql);
							if(!result.equals("0")){
								errList.add(data[30]+"--商户账户账号， 法人身份证，营业执照,税务登记证号码  已经存在");
								fail++;
								continue;
							}*/
							
							/*String sql = "select B.MCHT_NO,trim(MCHT_NM),TRIM(LICENCE_NO),TRIM(FAX_NO),TRIM(IDENTITY_NO)," +
							"trim(BANK_LICENCE_NO),substr(TRIM(SETTLE_ACCT),2) " +
						"from TBL_MCHT_BASE_INF_TMP B,TBL_MCHT_SETTLE_INF_TMP S where " +
						"(TRIM(SETTLE_ACCT)= '" + data[48] + "' OR " +
						" upper(IDENTITY_NO) = '" + data[17].toUpperCase() + "' OR " + 
						" TRIM(LICENCE_NO) = '" + data[3] + "'";
							
							//税务登记证修改为可选项
							if(data[4]!=null && !"".equals(data[4]))
								sql += " OR TRIM(FAX_NO) = '" +data[10] + "'";
							//组织机构代码在新增商户时有填写，则需要加入判断条件中查看是否有重复
							if(data[5]!=null && !"".equals(data[5]))
								sql += " OR TRIM(BANK_LICENCE_NO) = '" + data[5] + "'";
							sql += ") AND B.MCHT_NO = S.MCHT_NO AND TRIM(ACQ_INST_ID) = '" +data[1] + "'";
							
							List list = commQueryDAO.findBySQLQuery(sql);
							if (null != list && !list.isEmpty() && list.size() > 0) {
								Object[] obj = (Object[]) list.get(0);
								String errMsg = "存在商户" + tblMchtBaseInfTmp.getMchtNo() + "等" 
									+ String.valueOf(list.size()) + "家商户与该商户的";
								if (data[8].equals(obj[2])) {
									errMsg += "营业执照编号";
								} else if (data[4]!=null && data[4].equals(obj[3])) {//税务登记证号码已修改为可选项
									errMsg += "税务登记证号码";
								} else if (data[16].toUpperCase().equals(obj[4])) {
									errMsg += "法人证件编号";
								} else if (data[5]!=null && data[5].equals(obj[5])){//
									errMsg += "组织机构代码";
								} else if (data[41]!=null && data[41].equals(obj[6])) {
									errMsg += "商户收入账户号";
								} else {
									errMsg += "部分关键信息";
								}
								errMsg += "相同,请核实相关商户录入信息.";
								
								errList.add(errMsg);
								fail++;
								continue;
							}*/
							
						/*	//检查新入商户中是否有关键信息与黑名单商户中的关键信息
							String blackMemberSql="select count(1) from (select MANAGER,IDENTITY_NO,MANAGER_TEL from tbl_Ctl_Mcht_Inf a,tbl_mcht_base_inf b where b.mcht_no = a.sa_mer_no and a.sa_state not in ('1','0') "
							+"and  ( b.MANAGER ='"+data[14]+"' or b.MANAGER_TEL ='"+data[17]+"' or b.IDENTITY_NO='"+data[16]+"') )";
							result= commQueryDAO.findCountBySQLQuery(blackMemberSql);
							if(!result.equals("0")){
								errList.add(tblMchtBaseInfTmp.getMchtNo()+"--商户信息已经存在黑名单中");
								fail++;
								continue;
							}*/
							
							
							/*result=commQueryDAO.findCountBySQLQuery("select count(1) from tbl_opr_info where OPR_ID='"+data[37]+"' and OPR_NAME='"+data[38]+"'");  
							if(result.equals("0")){
								errList.add(data[37]+"--客户经理信息不匹配");
								fail++;
								continue;
							}*/
							//有相同的电子邮件，不保存此录入的新商户的信息
						/*	result=commQueryDAO.findCountBySQLQuery("select count(1) from tbl_mcht_base_inf_tmp where COMM_EMAIL='"+data[12]+"'");   //
							if(!result.equals("0")){
								errList.add(data[12]+"--企业邮箱已经存在");
								fail++;
								continue;
							}
							*/
							
							
				
							
						
							//设置商户审批流程号
							tblMchtBaseInfTmp.setReserved("-");
							
							
							
							
						/*	//验证工号和名字是否相匹配
							List  listOper=commQueryDAO.findBySQLQuery("select * from tbl_opr_info where OPR_ID='"+tblMchtBaseInfTmp.getOperNo()+"' and OPR_NAME='"+tblMchtBaseInfTmp.getOperNm()+"'",0,10);  
							if(listOper.size()==0){
								errList.add(data[30]+"--客户经理名字和工号不匹配");
								fail++;
								continue;
							}*/
							
							
							
							
							
							
								
									
							MchtBaseInfListAdd.add(tblMchtBaseInfTmp);
							MchtSupp1ListAdd.add(tblMchtSupp1Tmp);
							HisDiscAlgo2ListAdd.add(tblHisDiscAlgo2Tmp);
							
							MchtSettleInfTmpListAdd.add(tblMchtSettleInfTmp);
					}
				}
				reader.close();
				if(errList.size()!=0){
					String filePath =SysParamUtil.getParam(SysParamConstants.MCHT_BatchFile_ERRInfo);
					filePath=filePath+CommonFunction.getCurrentDateTime()+".txt";
					filePath = filePath.replace("\\", "/");
					File fileBathErr=new File(filePath);
					if (!fileBathErr.getParentFile().exists()) {
						fileBathErr.getParentFile().mkdirs();
					}
					FileOutputStream fo=new FileOutputStream(fileBathErr,true);
					BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(fo));
					Iterator<String> it= errList.iterator();
					while(it.hasNext()){
						String errm=it.next();
						bw.write(errm+"\r\n");
						bw.flush();
					}
				}
				success=+t20105BO.saveUploadForAddBatch(MchtBaseInfListAdd, MchtSupp1ListAdd, HisDiscAlgo2ListAdd,MchtSettleInfTmpListAdd);
			}
			
			return Constants.SUCCESS_CODE_CUSTOMIZE + 
				"成功录入条目：" + String.valueOf(success) + ",已存在的条目：" + String.valueOf(fail);
		} catch (Exception e) {
			e.printStackTrace();
			return "解析文件失败";
		}
	}

	/**
	 * 
	 * 
	 * 
	 * 佰付通终端信息批量上传
	 * 
	 * 
	 * 
	 * */
	private String uploadBFZ() {
		List<TblTermInfTmp> TblTermInfTmpList=new ArrayList<TblTermInfTmp>();
		List<String> errList=new ArrayList<String>();
		
		int success = 0;
		int fail = 0;
		try {
			for(File file : files) {
				BufferedReader reader = 
					new BufferedReader(new InputStreamReader(new FileInputStream(file), "GBK"));
				//自动生成终端号  ==>查询出终端号前两位号段
				String bSql = "SELECT VALUE from cst_sys_param where OWNER = 'BFT_TERM_SEQ' and key = 'all'";
				List<String> bList1 = CommonFunction.getCommQueryDAO().findBySQLQuery(bSql);
				String seq = "";
				if( bList1 == null || bList1.size() == 0 || bList1.get(0) == null ){
					return "终端号前两位号段未配置";
				}else{
					seq = bList1.get(0);
				}
				
				String createTermNoStr="";  //终端号
				//获取最大的终端号3-6位
				String maxTermIdSql36="select max(substr(TERM_ID,3,6)) from TBL_TERM_INF_TMP where substr(TERM_ID,0,2) = '"+seq+"'";
				List<String> maxTermIdList = CommonFunction.getCommQueryDAO().findBySQLQuery(maxTermIdSql36);
				String maxTermId36Str="";
				if( maxTermIdList!= null  && maxTermIdList.size() != 0){
					maxTermId36Str = maxTermIdList.get(0);
				}else{
					maxTermId36Str="0";
				}
				
				int maxTermId36Int=Integer.valueOf(maxTermId36Str);
				
				
				
				while(reader.ready()){
					String tmp = reader.readLine();
					if (!StringUtil.isNull(tmp)) {
						String[] data = tmp.split(",");
						for(int ii=0;ii<data.length;ii++){
							data[ii]=data[ii].replaceAll("\"","").trim();
						}
						TblTermInfTmp tblTermInfTmp=new TblTermInfTmp();
//						String dataFlag=data[0]; //I-新增，U-更新，D-删除
//						if(dataFlag.equals("I")){
							TblTermInfTmpPK pkid=new TblTermInfTmpPK();
							
							//自动生成终端号Id
							maxTermId36Int=maxTermId36Int+1;
							createTermNoStr=seq+addlength(String.valueOf(maxTermId36Int),6);
							
							pkid.setTermId(createTermNoStr);//终端号
							pkid.setRecCrtTs(CommonFunction.getCurrentDateTime());
							tblTermInfTmp.setId(pkid);
							
							tblTermInfTmp.setMchtCd(data[0]);  //商户号
							TblMchtBaseInfTmp tblMchtBaseInfTmp=tblMchtBaseInfTmpDAO.get(data[0]);
							if(tblMchtBaseInfTmp!=null){
								tblTermInfTmp.setTermMcc(tblMchtBaseInfTmp.getMcc()); //终端MCC
								tblTermInfTmp.setTermBranch(tblMchtBaseInfTmp.getAcqInstId());  //获取终端的机构号
								
							}
							
							tblTermInfTmp.setTermIdId(createTermNoStr); //终端号
							tblTermInfTmp.setContTel(data[1]); //联系电话
							if(!StringUtil.isNull(data[2])&&!StringUtil.isEmpty(data[2].trim())){
								tblTermInfTmp.setPropTp(data[2]); //产权属性
							}else{
								tblTermInfTmp.setPropTp("0"); //产权属性
							}
							
							if(!StringUtil.isNull(data[3])&&!StringUtil.isEmpty(data[3].trim())){
								tblTermInfTmp.setConnectMode(data[3]); //连接方式
							}else{
								tblTermInfTmp.setConnectMode("3"); //连接方式
							}
							
							if(!StringUtil.isNull(data[3])&&!StringUtil.isEmpty(data[3].trim())){
								tblTermInfTmp.setTermFactory(data[4]); //终端厂商
							}else{
								tblTermInfTmp.setTermFactory("A1000000"); //终端厂商
							}
							
							if(!StringUtil.isNull(data[3])&&!StringUtil.isEmpty(data[3].trim())){
								tblTermInfTmp.setMisc3(data[5]); //安装维护机构
							}else{
								tblTermInfTmp.setMisc3(""); //安装维护机构
							}
							
							
							tblTermInfTmp.setProvince(data[6]); //省
							tblTermInfTmp.setCity(data[7]);//市
							if(!StringUtil.isNull(data[8])&&!StringUtil.isEmpty(data[8].trim())){
								tblTermInfTmp.setArea(data[8]); //县
							}else{
								tblTermInfTmp.setArea(" ");
							}
							
							if(!StringUtil.isNull(data[9])&&!StringUtil.isEmpty(data[9].trim())){
								tblTermInfTmp.setCityCode(data[9]); //地区代码
							}
							
							tblTermInfTmp.setTermTp(data[10]); //终端类型
							
							
							tblTermInfTmp.setTermSta(TblTermInfConstants.TERM_STA_INIT); //终端状态
							tblTermInfTmp.setRecCrtOpr(operator.getOprId());//修改人
							tblTermInfTmp.setRecCheOpr(operator.getOprId());//创建人
							tblTermInfTmp.setRecUpdTs(CommonFunction.getCurrentDateTime());
							tblTermInfTmp.setTermIdId("-");
//							supportIC这个为默认项，必填不可见。IC卡参数下载标志由IcDownSign标志位控制
							tblTermInfTmp.setKeyDownSign("1");
							tblTermInfTmp.setParamDownSign("1");
							tblTermInfTmp.setSupportIc("1");
							tblTermInfTmp.setIcDownSign("1");
							tblTermInfTmp.setReserveFlag1("1");
							tblTermInfTmp.setRecDelTs(CommonFunction.getCurrentDateTime());//修改时间
							
							

							//检查主键TblSignInfPK是否已存在
							String mchtSql1="select count(*) from TBL_TERM_INF_TMP where  TERM_ID='"+createTermNoStr+"'";
							String result1= CommonFunction.getCommQueryDAO().findCountBySQLQuery(mchtSql1);
							if(!result1.equals("0")){
								errList.add(createTermNoStr+"--终端号已经存在终端表中");
								fail++;
								continue;
							}
							
							//验证输入的商户号对应商户是否为注销状态
							String mchtNoSql = "select count(*) from tbl_mcht_base_inf_tmp where trim(mcht_no)='" + data[0] + "' and mcht_status='8' ";
							String result2 = CommonFunction.getCommQueryDAO().findCountBySQLQuery(mchtNoSql);
							if(!"0".equals(result2)){
								errList.add(data[0]+"该商户已经注销，不能开设终端，请重新输入。");
								fail++;
								continue;
							}
							
							TblTermInfTmpList.add(tblTermInfTmp);
//						}
					}
				}
				reader.close();
				
				
				if(errList.size()!=0){
					String filePath =SysParamUtil.getParam(SysParamConstants.MCHT_BFZBatch_ErrInfo);
					filePath=filePath+CommonFunction.getCurrentDateTime()+".txt";
					filePath = filePath.replace("\\", "/");
					File fileBathErr=new File(filePath);
					if (!fileBathErr.getParentFile().exists()) {
						fileBathErr.getParentFile().mkdirs();
					}
					FileOutputStream fo=new FileOutputStream(fileBathErr,true);
					BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(fo));
					Iterator<String> it= errList.iterator();
					while(it.hasNext()){
						String errm=it.next();
						bw.write(errm+"\r\n");
						bw.flush();
					}
				}
				success=+t20105BO.saveForBFZBatch(TblTermInfTmpList);
			}
			
			return Constants.SUCCESS_CODE_CUSTOMIZE + 
				"成功录入条目：" + String.valueOf(success) + ",已存在的条目：" + String.valueOf(fail);
		} catch (Exception e) {
			e.printStackTrace();
			return "解析文件失败";
		}
	}


	
	
	//增加长度的方法
	private String addlength(String sss, int length) {
		StringBuffer ss = new StringBuffer();
		if(sss.length()<length){
			int addlength=length-sss.length();
			for(int i=1;i<=addlength;i++){
				ss.append("0");
			}
		}
		String ssss=ss.toString()+sss;
		return ssss;
	}
	
	
	
	
	
	
	

	public T10205BO getT10205BO() {
		return t10205BO;
	}

	public void setT10205BO(T10205BO t10205bo) {
		t10205BO = t10205bo;
	}

	public String getCardTp() {
		return CardTp;
	}

	public void setCardTp(String cardTp) {
		CardTp = cardTp;
	}

	public String getInsIdCd() {
		return insIdCd;
	}

	public void setInsIdCd(String insIdCd) {
		this.insIdCd = insIdCd;
	}

	public String getAcc1Offset() {
		return acc1Offset;
	}

	public void setAcc1Offset(String acc1Offset) {
		this.acc1Offset = acc1Offset;
	}

	public String getAcc1Len() {
		return acc1Len;
	}

	public void setAcc1Len(String acc1Len) {
		this.acc1Len = acc1Len;
	}

	public String getAcc1Tnum() {
		return acc1Tnum;
	}

	public void setAcc1Tnum(String acc1Tnum) {
		this.acc1Tnum = acc1Tnum;
	}

	public String getAcc2Offset() {
		return acc2Offset;
	}

	public void setAcc2Offset(String acc2Offset) {
		this.acc2Offset = acc2Offset;
	}

	public String getAcc2Len() {
		return acc2Len;
	}

	public void setAcc2Len(String acc2Len) {
		this.acc2Len = acc2Len;
	}

	public String getAcc2Tnum() {
		return acc2Tnum;
	}

	public void setAcc2Tnum(String acc2Tnum) {
		this.acc2Tnum = acc2Tnum;
	}
	
	public String getBinStaNo() {
		return binStaNo;
	}

	public void setBinStaNo(String binStaNo) {
		this.binStaNo = binStaNo;
	}

	public String getBinEndNo() {
		return binEndNo;
	}

	public void setBinEndNo(String binEndNo) {
		this.binEndNo = binEndNo;
	}

	public String getBinTnum() {
		return binTnum;
	}

	public void setBinTnum(String binTnum) {
		this.binTnum = binTnum;
	}

	public String getCardDis() {
		return cardDis;
	}

	public void setCardDis(String cardDis) {
		this.cardDis = cardDis;
	}

	public String getInd() {
		return ind;
	}

	public void setInd(String ind) {
		this.ind = ind;
	}

	public String getTblBankBinInfList() {
		return tblBankBinInfList;
	}

	public void setTblBankBinInfList(String tblBankBinInfList) {
		this.tblBankBinInfList = tblBankBinInfList;
	}

	public String getBinOffSet() {
		return binOffSet;
	}

	public void setBinOffSet(String binOffSet) {
		this.binOffSet = binOffSet;
	}

	public String getBinLen() {
		return binLen;
	}

	public void setBinLen(String binLen) {
		this.binLen = binLen;
	}
	

	// 文件集合
	private List<File> files;
	// 文件名称集合
	private List<String> filesFileName;


	/**
	 * @return the files
	 */
	public List<File> getFiles() {
		return files;
	}

	/**
	 * @param files the files to set
	 */
	public void setFiles(List<File> files) {
		this.files = files;
	}

	/**
	 * @return the filesFileName
	 */
	public List<String> getFilesFileName() {
		return filesFileName;
	}

	/**
	 * @param filesFileName the filesFileName to set
	 */
	public void setFilesFileName(List<String> filesFileName) {
		this.filesFileName = filesFileName;
	}
	
}


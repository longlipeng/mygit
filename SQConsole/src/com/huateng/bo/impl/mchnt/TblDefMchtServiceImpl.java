package com.huateng.bo.impl.mchnt;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.BeanUtils;

import com.huateng.bo.mchnt.TblDefMchtService;
import com.huateng.common.Constants;
import com.huateng.common.Operator;
import com.huateng.common.StringUtil;
import com.huateng.common.TblMchntInfoConstants;
import com.huateng.commquery.dao.ICommQueryDAO;
import com.huateng.dao.iface.mchnt.CstMchtFeeInfDAO;
import com.huateng.dao.iface.mchnt.ITblMchtBaseInfDAO;
import com.huateng.dao.iface.mchnt.ITblMchtBaseInfTmpDAO;
import com.huateng.dao.iface.mchnt.ITblMchtSettleInfDAO;
import com.huateng.dao.iface.mchnt.ITblMchtSettleInfTmpDAO;
import com.huateng.dao.iface.mchnt.TblDefMchtInfDAO;
import com.huateng.dao.iface.mchnt.TblHisDiscAlgo1DAO;
import com.huateng.dao.iface.mchnt.TblHisDiscAlgo1TmpDAO;
import com.huateng.dao.iface.mchnt.TblMchntLogsDAO;
import com.huateng.dao.iface.risk.TblCtlMchtInfDAO;
import com.huateng.dao.iface.risk.TblRiskRefuseDAO;
import com.huateng.dao.iface.risk.TblWhiteListTmpDAO;
import com.huateng.dao.iface.term.TblTermInfDAO;
import com.huateng.dao.iface.term.TblTermInfTmpDAO;
import com.huateng.dwr.mchnt.T20100;
import com.huateng.po.TblCtlMchtInf;
import com.huateng.po.TblTermInf;
import com.huateng.po.TblTermInfTmp;
import com.huateng.po.TblTermInfTmpPK;
import com.huateng.po.mchnt.CstMchtFeeInf;
import com.huateng.po.mchnt.CstMchtFeeInfPK;
import com.huateng.po.mchnt.CstMchtFeeInfTmp;
import com.huateng.po.mchnt.TblDefMchtInf;
import com.huateng.po.mchnt.TblDefTermInf;
import com.huateng.po.mchnt.TblHisDiscAlgo;
import com.huateng.po.mchnt.TblHisDiscAlgo1;
import com.huateng.po.mchnt.TblHisDiscAlgo2Tmp;
import com.huateng.po.mchnt.TblHisDiscAlgoPK;
import com.huateng.po.mchnt.TblMchntLogs;
import com.huateng.po.mchnt.TblMchntLogsPK;
import com.huateng.po.mchnt.TblMchtBaseInf;
import com.huateng.po.mchnt.TblMchtBaseInfTmp;
import com.huateng.po.mchnt.TblMchtSettleInf;
import com.huateng.po.mchnt.TblMchtSettleInfPK;
import com.huateng.po.mchnt.TblMchtSettleInfTmp;
import com.huateng.po.mchnt.TblMchtSupp1;
import com.huateng.po.mchnt.TblMchtSupp1Tmp;
import com.huateng.po.risk.TblRiskRefuse;
import com.huateng.po.risk.TblWhiteListTmp;
import com.huateng.struts.pos.TblTermInfConstants;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.GenerateNextId;
import com.huateng.system.util.StatusUtil;

public class TblDefMchtServiceImpl implements TblDefMchtService {
	
	public TblDefMchtInfDAO tblDefMchtInfDAO;
	
	public ITblMchtBaseInfTmpDAO tblMchtBaseInfTmpDAO;
	
	public ITblMchtBaseInfDAO tblMchtBaseInfDAO;

	public ITblMchtSettleInfTmpDAO tblMchtSettleInfTmpDAO;

	public ITblMchtSettleInfDAO tblMchtSettleInfDAO;
	
	private TblHisDiscAlgo1DAO tblHisDiscAlgo1DAO;
	
	private TblMchntLogsDAO tblMchntLogsDAO;
	
    public CstMchtFeeInfDAO cstMchtFeeInfDAO;
	
	private TblWhiteListTmpDAO tblWhiteListTmpDAO;
	
	private TblCtlMchtInfDAO tblCtlMchtInfDAO;
	
	private TblHisDiscAlgo1TmpDAO tblHisDiscAlgo1TmpDAO;
	
	private TblTermInfTmpDAO tblTermInfTmpDAO;
	
	private TblTermInfDAO tblTermInfDAO;
	
	private TblRiskRefuseDAO tblRiskRefuseDAO;


	public TblDefMchtInfDAO getTblDefMchtInfDAO() {
		return tblDefMchtInfDAO;
	}

	public void setTblDefMchtInfDAO(TblDefMchtInfDAO tblDefMchtInfDAO) {
		this.tblDefMchtInfDAO = tblDefMchtInfDAO;
	}
	
	

	public ITblMchtBaseInfTmpDAO getTblMchtBaseInfTmpDAO() {
		return tblMchtBaseInfTmpDAO;
	}

	public void setTblMchtBaseInfTmpDAO(ITblMchtBaseInfTmpDAO tblMchtBaseInfTmpDAO) {
		this.tblMchtBaseInfTmpDAO = tblMchtBaseInfTmpDAO;
	}

	public ITblMchtBaseInfDAO getTblMchtBaseInfDAO() {
		return tblMchtBaseInfDAO;
	}

	public void setTblMchtBaseInfDAO(ITblMchtBaseInfDAO tblMchtBaseInfDAO) {
		this.tblMchtBaseInfDAO = tblMchtBaseInfDAO;
	}

	public ITblMchtSettleInfTmpDAO getTblMchtSettleInfTmpDAO() {
		return tblMchtSettleInfTmpDAO;
	}

	public void setTblMchtSettleInfTmpDAO(
			ITblMchtSettleInfTmpDAO tblMchtSettleInfTmpDAO) {
		this.tblMchtSettleInfTmpDAO = tblMchtSettleInfTmpDAO;
	}

	public ITblMchtSettleInfDAO getTblMchtSettleInfDAO() {
		return tblMchtSettleInfDAO;
	}

	public void setTblMchtSettleInfDAO(ITblMchtSettleInfDAO tblMchtSettleInfDAO) {
		this.tblMchtSettleInfDAO = tblMchtSettleInfDAO;
	}

	public TblHisDiscAlgo1DAO getTblHisDiscAlgo1DAO() {
		return tblHisDiscAlgo1DAO;
	}

	public void setTblHisDiscAlgo1DAO(TblHisDiscAlgo1DAO tblHisDiscAlgo1DAO) {
		this.tblHisDiscAlgo1DAO = tblHisDiscAlgo1DAO;
	}

	public TblMchntLogsDAO getTblMchntLogsDAO() {
		return tblMchntLogsDAO;
	}

	public void setTblMchntLogsDAO(TblMchntLogsDAO tblMchntLogsDAO) {
		this.tblMchntLogsDAO = tblMchntLogsDAO;
	}

	public CstMchtFeeInfDAO getCstMchtFeeInfDAO() {
		return cstMchtFeeInfDAO;
	}

	public void setCstMchtFeeInfDAO(CstMchtFeeInfDAO cstMchtFeeInfDAO) {
		this.cstMchtFeeInfDAO = cstMchtFeeInfDAO;
	}

	public TblWhiteListTmpDAO getTblWhiteListTmpDAO() {
		return tblWhiteListTmpDAO;
	}

	public void setTblWhiteListTmpDAO(TblWhiteListTmpDAO tblWhiteListTmpDAO) {
		this.tblWhiteListTmpDAO = tblWhiteListTmpDAO;
	}

	public TblCtlMchtInfDAO getTblCtlMchtInfDAO() {
		return tblCtlMchtInfDAO;
	}

	public void setTblCtlMchtInfDAO(TblCtlMchtInfDAO tblCtlMchtInfDAO) {
		this.tblCtlMchtInfDAO = tblCtlMchtInfDAO;
	}

	public TblHisDiscAlgo1TmpDAO getTblHisDiscAlgo1TmpDAO() {
		return tblHisDiscAlgo1TmpDAO;
	}

	public void setTblHisDiscAlgo1TmpDAO(TblHisDiscAlgo1TmpDAO tblHisDiscAlgo1TmpDAO) {
		this.tblHisDiscAlgo1TmpDAO = tblHisDiscAlgo1TmpDAO;
	}
	
public   ICommQueryDAO commQueryDAO ;
	
	public ICommQueryDAO getCommQueryDAO() {
		return commQueryDAO;
	}

	public void setCommQueryDAO(ICommQueryDAO commQueryDAO) {
		this.commQueryDAO = commQueryDAO;
	}
	
	
	
	public TblTermInfTmpDAO getTblTermInfTmpDAO() {
		return tblTermInfTmpDAO;
	}

	public void setTblTermInfTmpDAO(TblTermInfTmpDAO tblTermInfTmpDAO) {
		this.tblTermInfTmpDAO = tblTermInfTmpDAO;
	}

	public TblTermInfDAO getTblTermInfDAO() {
		return tblTermInfDAO;
	}

	public void setTblTermInfDAO(TblTermInfDAO tblTermInfDAO) {
		this.tblTermInfDAO = tblTermInfDAO;
	}
	
	

	public TblRiskRefuseDAO getTblRiskRefuseDAO() {
		return tblRiskRefuseDAO;
	}

	public void setTblRiskRefuseDAO(TblRiskRefuseDAO tblRiskRefuseDAO) {
		this.tblRiskRefuseDAO = tblRiskRefuseDAO;
	}

	/***
	 * 记录商户操作日志
	 * @param tblMchtBaseInfTmp
	 * @param oprType
	 * @param oprStatus
	 * @param oprInfo
	 */
	private void updateMchntLogs(TblDefMchtInf tblDefMchtInf,
			String oprType, String oprStatus, String oprInfo) {
		Operator opr = (Operator) ServletActionContext.getRequest().getSession().getAttribute(Constants.OPERATOR_INFO);
		
		TblMchntLogsPK id = new TblMchntLogsPK(CommonFunction.getCurrentDateTime(), tblDefMchtInf.getRecId());
		TblMchntLogs tblMchntLogs = new TblMchntLogs(id);
		tblMchntLogs.setMchntId(tblDefMchtInf.getMchtNo());
		tblMchntLogs.setOprId(opr.getOprId());
		tblMchntLogs.setOprType(oprType);
		tblMchntLogs.setOprStatus(oprStatus);
		tblMchntLogs.setOprInfo(oprInfo);
		tblMchntLogs.setCrtOprId(opr.getOprId());
		tblMchntLogs.setUpdOprId(opr.getOprId());
		tblMchntLogs.setRecCrtTs(CommonFunction.getCurrentDateTime());
		tblMchntLogs.setRecUpdTs(CommonFunction.getCurrentDateTime());
		tblMchntLogsDAO.save(tblMchntLogs);
		
	}

	public TblDefMchtInf getDefMchtInf(String recId)
			throws IllegalAccessException, InvocationTargetException {
		// TODO Auto-generated method stub
		return tblDefMchtInfDAO.get(recId);
	}

	public String acceptAdd(String mchntId, String oprInfo,
			CstMchtFeeInfTmp cstMchtFeeInfTmpDebit,
			CstMchtFeeInfTmp cstMchtFeeInfTmpCredit, String isRoute,
			String isWhite) throws IllegalAccessException,
			InvocationTargetException, IOException {
		TblDefMchtInf tblDefMchtInf = tblDefMchtInfDAO.get(mchntId);
		if(null==tblDefMchtInf){
			return "没有找到商户的信息，请重试";
		}
		//根据mcc，city生成15位商户号
		String mcc = tblDefMchtInf.getMcc();
		String city = tblDefMchtInf.getCountryCode();
		if(null==city||"".equals(city)){
			city=tblDefMchtInf.getCityCode();
		}
		T20100 t2=new T20100();
		HashMap<String, String> MchtMap = new LinkedHashMap<String, String>();
		MchtMap=t2.createMchtNo(mcc.trim(), city.trim());
		if(null!=MchtMap.get("err")&&!"".equals(MchtMap.get("err"))){
			return MchtMap.get("err");
			
		}
		String mchtNo = MchtMap.get("mchtNoInPut"); //15位商户号		
		TblMchtBaseInfTmp tmp = buildMchtBaseInf(mchtNo,tblDefMchtInf);//构建商户基本信息
		TblMchtSupp1Tmp suppTmp=buildTmpMchtSupp1Tmp(mchtNo,tblDefMchtInf);//商户补充信息
		TblMchtSettleInfTmp tmpSettle = buildTmpMchtSettleInfo(mchtNo,tblDefMchtInf);//结算信息
		TblHisDiscAlgo2Tmp tblHisDiscAlgo2Tmp= buildTmpHisDiscAlgo2(mchtNo,tblDefMchtInf);//费率
		
//		List<TblTermInfTmp> termInfTmpList = buildTermInfTmp(mchtNo,mchntId); //终端列表
		
	
		
			TblMchtBaseInf inf = new TblMchtBaseInf();
		
		
			TblMchtSettleInf infSettle = new TblMchtSettleInf();
		
		
			TblMchtSupp1 supp = new TblMchtSupp1();
		
		
		
		
		tmp.setRouteFlag(isRoute);
		

		// 更新时间和柜员
		tmp.setRecUpdTs(CommonFunction.getCurrentDateTime());
		Operator opr = (Operator) ServletActionContext.getRequest().getSession().getAttribute(Constants.OPERATOR_INFO);
		tmp.setUpdOprId(opr.getOprId());
		tmpSettle.setRecUpdTs(CommonFunction.getCurrentDateTime());


		tmp.setReserved("");
		
		//记录最后一次的审核人
		tmp.setAuditOprId(opr.getOprId());			
			
		
			// 复制新的信息
		BeanUtils.copyProperties(tmp, inf);
		BeanUtils.copyProperties(tmpSettle, infSettle);
		BeanUtils.copyProperties(suppTmp, supp);
		
			// 更新到数据库
			tblMchtBaseInfTmpDAO.saveOrUpdate(tmp);
			tblMchtBaseInfDAO.saveOrUpdate(inf);
			
			tblMchtSettleInfTmpDAO.saveOrUpdate(tmpSettle);
			tblMchtSettleInfDAO.saveOrUpdate(infSettle);
			
			tblMchtSettleInfTmpDAO.saveOrUpdate(suppTmp);
			tblMchtSettleInfTmpDAO.saveOrUpdate(supp);
			
			tblHisDiscAlgo1TmpDAO.saveAlgo2(tblHisDiscAlgo2Tmp);
			
			//更新费率信息到计费信息表中tbl_his_disc_algo1
			changeDiscAlgo1(tblHisDiscAlgo2Tmp);

			//限额表
			if(cstMchtFeeInfTmpDebit.getDaySingle()!=null||cstMchtFeeInfTmpDebit.getDayAmt()!=null
					||cstMchtFeeInfTmpDebit.getMonAmt()!=null){
				CstMchtFeeInfPK dId = cstMchtFeeInfTmpDebit.getId();
			    dId.setMchtCd(mchtNo);
			    cstMchtFeeInfTmpDebit.setId(dId);
				CstMchtFeeInf cstMchtFeeInfDebit = new CstMchtFeeInf();
				BeanUtils.copyProperties(cstMchtFeeInfTmpDebit,cstMchtFeeInfDebit);
				cstMchtFeeInfDAO.saveOrUpdate(cstMchtFeeInfDebit);
				cstMchtFeeInfDAO.saveOrUpdate(cstMchtFeeInfTmpDebit);
			}
			CstMchtFeeInfPK cId = cstMchtFeeInfTmpCredit.getId();
			cId.setMchtCd(mchtNo);
			cstMchtFeeInfTmpCredit.setId(cId);
			CstMchtFeeInf cstMchtFeeInfCredit = new CstMchtFeeInf();
			
			BeanUtils.copyProperties(cstMchtFeeInfTmpCredit,cstMchtFeeInfCredit);
			
			cstMchtFeeInfDAO.saveOrUpdate(cstMchtFeeInfCredit);
			
			cstMchtFeeInfDAO.saveOrUpdate(cstMchtFeeInfTmpCredit);
			
			
			
			if("1".equals(isWhite)){
				//加入白名单
				TblWhiteListTmp tblWhiteListTmp = new TblWhiteListTmp();
				tblWhiteListTmp.setUuid(StringUtil.getUUID());
				tblWhiteListTmp.setAppRemark(oprInfo.trim());
				tblWhiteListTmp.setMchtNo(mchtNo);
				tblWhiteListTmp.setValidity("30");
				tblWhiteListTmp.setBeginDt(CommonFunction.getCurrentDate());
				tblWhiteListTmp.setInsDt(CommonFunction.getCurrentDate());
				tblWhiteListTmp.setInsOpr(opr.getOprId());
				tblWhiteListTmp.setUpdOpr("");
				tblWhiteListTmp.setState("0");
				tblWhiteListTmp.setAddType("1");
				tblWhiteListTmpDAO.save(tblWhiteListTmp);
			}
			
			
			//更新进件商户表信息
			tblDefMchtInf.setMchtNo(mchtNo);//商户号
			tblDefMchtInf.setStatus(TblMchntInfoConstants.MCHNT_ST_OK);
//			tblDefMchtInf.setOprId(opr.getOprId());
			tblDefMchtInf.setUptDate(CommonFunction.getCurrentDateTime());
			tblDefMchtInfDAO.update(tblDefMchtInf);
			
			String oprType = "进件系统商户新增审核";
			String oprStatus="通过";
			updateMchntLogs(tblDefMchtInf,oprType,oprStatus,oprInfo);
			
			//进件的终端审核通过
			accept4Pos(mchtNo,tblDefMchtInf,tmp);
		return Constants.SUCCESS_CODE;
	}

	private void accept4Pos(String mchtNo, TblDefMchtInf tblDefMchtInf,TblMchtBaseInfTmp baseInfTmp) {
		// TODO Auto-generated method stub
		String mchtRecId = tblDefMchtInf.getRecId();
		
		
		String sqlTerm = "select rec_id from tbl_def_term_inf where STATUS='0' and mcht_rec_id = '" + mchtRecId+"'";
		List<String> recIdList = commQueryDAO.findBySQLQuery(sqlTerm);
		
		if(null!=recIdList&&recIdList.size()>0){//商户下所有终端审核通过，插入终端临时表和正式表，更新终端进件表状态
			List<String> termNos=new ArrayList<String>();			
			
			for(String recId:recIdList){
				String no = getTermNo(termNos);
				termNos.add(no);
				TblDefTermInf defTermInf = tblDefMchtInfDAO.getTermInf(recId);
				TblTermInfTmp tblTermInfTmp = new TblTermInfTmp();
				TblTermInfTmpPK pk = new TblTermInfTmpPK();
				pk.setTermId(no);
				tblTermInfTmp.setId(pk);
				tblTermInfTmp.setTermIdId(no);
				tblTermInfTmp.setMchtCd(mchtNo);//商户号
				tblTermInfTmp.setTermSta(TblTermInfConstants.TERM_STA_RUN);
				tblTermInfTmp.setTermSignSta(TblTermInfConstants.TERM_SIGN_DEFAULT);
				tblTermInfTmp.setTermTp(defTermInf.getTermType().substring(defTermInf.getTermType().length()-1, defTermInf.getTermType().length()));//终端类型
				tblTermInfTmp.setTermFactory(defTermInf.getTermBrand());//终端厂商,终端品牌
				tblTermInfTmp.setTermMachTp(defTermInf.getTermModel());//终端型号
				tblTermInfTmp.setContTel(defTermInf.getPhoneNo());//联系电话
				tblTermInfTmp.setProvince(defTermInf.getProCode());
				tblTermInfTmp.setCity(defTermInf.getCityCode());
				tblTermInfTmp.setArea(defTermInf.getCountry());
				tblTermInfTmp.setTermAddr(defTermInf.getAddress());
				tblTermInfTmp.setTermMcc(tblDefMchtInf.getMcc());
				tblTermInfTmp.setPropTp(defTermInf.getReserve10());//产权属性
				
				tblTermInfTmp.setTermBranch(baseInfTmp.getAcqInstId());//所属分公司
				
				Operator opr = (Operator) ServletActionContext.getRequest().getSession().getAttribute(Constants.OPERATOR_INFO);
				tblTermInfTmp.setRecCrtOpr(opr.getOprId());//修改人
				tblTermInfTmp.setRecCheOpr(opr.getOprId());//创建人
				tblTermInfTmp.setTermStlmDt(CommonFunction.getCurrentDate());
				tblTermInfTmp.setRecDelTs(CommonFunction.getCurrentDateTime());//修改时间
				
				tblTermInfTmp.setKeyDownSign("1");//CA公钥下载标志
				tblTermInfTmp.setParamDownSign("1");//终端参数下载标志
				tblTermInfTmp.setSupportIc("1");
				tblTermInfTmp.setIcDownSign("1");//IC卡参数下载标志
				tblTermInfTmp.setReserveFlag1("0");//绑定电话
				tblTermInfTmp.setTermBatchNm("000001");
				
				String termPara = parseTermPara(tblDefMchtInf);
				tblTermInfTmp.setTermPara(termPara);
				tblTermInfTmp.setProductCd(" ");
				TblTermInf tblTermInf = new TblTermInf();
				tblTermInf = (TblTermInf) tblTermInfTmp.clone();
				
				tblTermInfTmpDAO.save(tblTermInfTmp);
				tblTermInfDAO.save(tblTermInf);
				
				//更新进件终端表
				defTermInf.setTermNo(no);
				defTermInf.setStatus(TblTermInfConstants.TERM_STA_RUN);
//				defTermInf.setOprId(opr.getOprId());
				defTermInf.setUptDate(CommonFunction.getCurrentDateTime());
				tblDefMchtInfDAO.saveTermInf(defTermInf);
				//向签到表中插入终端信息,向终端主密钥表中插入ZTMK密钥
				String sql="insert into  TBL_SIGN_INF (INST_ID,INST_NAME,MCHT_ID,TERM_ID,SIGNED_FLAG,SIGN_TIME,TERMID_VALID)" +
						" values('00010000','中国银联','"+tblTermInfTmp.getMchtCd()+"','"+tblTermInfTmp.getId().getTermId().trim()+"','0','"+CommonFunction.getCurrentDateTime()+"','1')";
				commQueryDAO.excute(sql);
				
				String tmp = "0001";
				String Sql="insert into tbl_pos_key_inf (MERCH_ID,TERM_ID,MPOS_IDX) values ('"//20121018添加机构编号
						+tblTermInfTmp.getMchtCd().trim()+"','"+tblTermInfTmp.getId().getTermId().trim()+"','"+tmp+"')";
					commQueryDAO.excute(Sql);
					
				
			}
		}
		
	}
	
	//终端参数，交易信息等
	public String parseTermPara(TblDefMchtInf tblDefMchtInf)	{
		
		String txn11New = "11";//POS终端应用类型
		String txn12New = "70";//超时时间
		String txn13New = "3";//拨号重拨次数
		String txn14New = "";//绑定电话1
		String txn15New = "";//绑定电话2
		String txn16New = "";//绑定电话3
		String txn17New = "";
		String txn18New = null;//是否支持小费
		String txn19New = null;//小费百分比
		String txn20New = null;//支持手工输入卡号
		String txn21New = null;//是否自动签退
		String txn22New = tblDefMchtInf.getMchtShortName();//商户简称
		String txn23New = "3";//冲正重发次数
		String txn25New1="1";//消费撤销交易是否需要刷卡
		String txn25New2="1";//消费撤销交易是否需要输入密码
		String txn25New3="1";//预授权完成撤销交易是否需要刷卡/手输卡号
		String txn25New4="1";//预授权完成撤销交易是否需要输入密码
		String txn25New5="1";//预授权撤销交易是否需要输入刷卡/手输卡号		
		String txn25New6="1";//预授权撤销交易是否需要输入密码
		String txn25New7="1";//预授权完成交易是否需刷卡/手输卡号
		String txn25New8="1";//预授权完成是否需输入密码
		//20140815添加end
		
		String txn27New = "2";//打印联数
		String txn28New = "1";//是否屏蔽卡号
		String txn29New = null;//是否显示LOGO
		String txn30New = "06";//快捷交易类型
		
		
		
		String param1New = null;//查询
		String param2New = null;//预授权
		String param3New = null;//预授权撤销
		String param4New = null;//预授权完成联机
		String param5New = null;//预授权完成撤销
		if("1".equals(tblDefMchtInf.getFuncPreauth())){
			param2New="1";
			param3New = "1";
			param4New = "1";
			param5New = "1";
		}
		
		String param6New = "1";//消费
		String param7New = "1";//消费撤销
		String param8New = null;//退货
		if("1".equals(tblDefMchtInf.getFuncReturn())){
			param8New="1";
		}
		String param9New = null;//分期付款消费
		String param10New = null;//分期付款撤销
		String param11New = null;//分期付款退货
		String param12New = null;//电子现金消费
		String param13New = null;//电子现金退货
		String param14New = null;//上笔交易查询（财务POS）
		String param15New = null;//交易查询（财务POS）
		String param16New = null;//定向汇款
		String param17New = null;//分期付款
		String param18New = null;//分期付款撤销
		String param19New = null;//代缴费
		String param20New = null;//电子现金
		String param21New = null;//IC现金充值
		String param22New = null;//指定账户圈存
		String param23New = null;//非指定账户圈存
		String param24New = null;//非接快速支付
		String param25New = null;
		String param26New = null;
		String param27New = null;
		String param28New = null;
		String param29New = null;
		String param30New = null;
		String param31New = null;
		String param32New = null;
		
		
		StringBuffer result = new StringBuffer();
		result.append("11").append(CommonFunction.fillString(txn11New, ' ', 2, true))//POS终端应用类型
			  .append("12").append(CommonFunction.fillString(txn12New, ' ', 2, true))//超时时间
			  .append("13").append(CommonFunction.fillString(txn13New, ' ', 1, true))//拨号重拨次数
			  .append("14").append(CommonFunction.fillStringByDB(txn14New.trim(), ' ', 14, true))//绑定电话1
			  .append("15").append(CommonFunction.fillStringByDB(txn15New.trim(), ' ', 14, true))//绑定电话2
			  .append("16").append(CommonFunction.fillStringByDB(txn16New.trim(), ' ', 14, true))//绑定电话3
			  .append("17").append(CommonFunction.fillString(txn17New,' ',14,true))//无
			  .append("18").append(CommonFunction.fillString(txn18New, ' ', 1, true))//是否支持消费
			  .append("19").append(CommonFunction.fillString(txn19New, ' ', 2, false))//小费百分比
			  .append("20").append(isChecked(txn20New))//支持手工输入卡号
			  .append("21").append(isChecked(txn21New))//是否自动签退
			  .append("22").append(CommonFunction.fillStringByDB(txn22New.trim(), ' ', 40, true))//商户名
			  .append("23").append(CommonFunction.fillString(txn23New, '0', 1, true))//冲正重发次数
			  .append("24").append(" ")
			  .append("25");
		
	//	System.out.println(txn25New6);//预授权撤销交易是否需要输入密码
	//	System.out.println(txn25New8);//预授权完成是否需输入密码
		StringBuffer sb=new StringBuffer();
		sb.append(isChecked(txn25New1)).append(isChecked(txn25New2)).append(isChecked(txn25New3)).append(isChecked(txn25New4))
		   .append(isChecked(txn25New5)).append(isChecked(txn25New6))
		   .append(isChecked(txn25New7)).append(isChecked(txn25New8))/*.append("000")*/;
		result.append(CommonFunction.fillString(Integer.toHexString(Integer.valueOf(sb.toString(), 2)),'0',2,false));//将sb中保存的2进制值转换为16进制保存
		
		result.append("26");		
		StringBuffer txnCode = new StringBuffer();
		String txnCode1=isChecked(param1New)+isChecked(param2New)+isChecked(param3New)+isChecked(param4New);
		String txnCode2=isChecked(param5New)+isChecked(param6New)+isChecked(param7New)+isChecked(param8New);
		String txnCode3=isChecked(param9New)+isChecked(param10New)+isChecked(param11New)+isChecked(param12New);
		String txnCode4=isChecked(param13New)+isChecked(param14New)+isChecked(param15New)+isChecked(param16New);
		String txnCode5=isChecked(param17New)+isChecked(param18New)+isChecked(param19New)+isChecked(param20New);
		String txnCode6=isChecked(param21New)+isChecked(param22New)+isChecked(param23New)+isChecked(param24New);
		String txnCode7=isChecked(param25New)+isChecked(param26New)+isChecked(param27New)+isChecked(param28New);
		String txnCode8=isChecked(param29New)+isChecked(param30New)+isChecked(param31New)+isChecked(param32New);
		txnCode.append(Integer.toHexString(Integer.valueOf(txnCode1,2)))
				.append(Integer.toHexString(Integer.valueOf(txnCode2,2)))
				.append(Integer.toHexString(Integer.valueOf(txnCode3,2)))
				.append(Integer.toHexString(Integer.valueOf(txnCode4,2)))
				.append(Integer.toHexString(Integer.valueOf(txnCode5,2)))
				.append(Integer.toHexString(Integer.valueOf(txnCode6,2)))
				.append(Integer.toHexString(Integer.valueOf(txnCode7,2)))
				.append(Integer.toHexString(Integer.valueOf(txnCode8,2)));
		result.append(CommonFunction.fillString(txnCode.toString(),'0',8,false));
		
		result.append("27").append(CommonFunction.fillString(txn27New, ' ', 1, true))//打印联数
			.append("28").append(isChecked(txn28New))//是否屏蔽卡号
			.append("29").append(isChecked(txn29New))//是否显示LOGO
			.append("30").append(CommonFunction.fillString(txn30New, ' ', 2, true));//快捷交易类型
			/*.append("31").append("000001")
			.append("32").append(CommonFunction.fillString(financeCard1New, ' ', 19, true))
			.append("33").append(CommonFunction.fillString(financeCard2New, ' ', 19, true))
			.append("34").append(CommonFunction.fillString(financeCard3New, ' ', 19, true))
			.append("35").append(CommonFunction.fillString(txn35New==null?" ":txn35New, ' ', 2, true))
			.append("36").append(txn36New==null?CommonFunction.fillString(txn36New, ' ', 12, true):translate(txn36New))
			.append("37").append(txn37New==null?CommonFunction.fillString(txn37New, ' ', 12, true):translate(txn37New))
			.append("38").append(txn38New==null?CommonFunction.fillString(txn38New, ' ', 12, true):translate(txn38New))
			.append("39").append(txn39New==null?CommonFunction.fillString(txn39New, ' ', 12, true):translate(txn39New))
			.append("40").append(CommonFunction.fillString(txn40New==null?" ":txn40New, ' ', 2, true));*/
		return result.toString();
	}
	public String isChecked(String param){
		return param==null?"0":"1";
	}
	public int checkTxn(String param) {
		return param==null?0:1;
	}
	public String translate(String money) {
		if(money.contains("."))
			return CommonFunction.fillString(money.replaceAll("\\.", ""), ' ', 12, true);
		else
			money = CommonFunction.fillString(money, '0', money.length()+2, true);
		return CommonFunction.fillString(money, ' ', 12, true);
	}
	
	
	/**
	 * 生成终端号
	 * @return
	 */
	private String getTermNo(List<String> termIds) {
		String no = "10000001";
		String sql = "SELECT substr(term_id,1,8) FROM tbl_term_inf_tmp ORDER BY substr(term_id,1,8)";
		List<String> termNos = commQueryDAO.findBySQLQuery(sql);
		if (termNos != null && termNos.size() > 0 && termNos.get(0) != null) {
			if (termIds != null && termIds.size() > 0 && termIds.get(0) != null){
				termNos.addAll(termIds);
				Collections.sort(termNos);
			}
			no = GenerateNextId.genTermNo(termNos, "", 8);
		}
		return no;
	}

	/**
	 * 根据计费代码获取计费算法信息
	 * @param discId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TblHisDiscAlgo> getDiscAlgoByDiscId(String discId){
		String sql="select disc_id ,min_fee,max_fee,floor_amount,flag,fee_value,INDEX_NUM  from TBL_HIS_DISC_ALGO where disc_id='"+discId+"' order by INDEX_NUM";
		List<Object[]> list=commQueryDAO.findBySQLQuery(sql);
		List<TblHisDiscAlgo> algoList=new ArrayList<TblHisDiscAlgo>();
		for(Object[] obj:list){
			TblHisDiscAlgo algo=new TblHisDiscAlgo();
			algo.setMinFee((BigDecimal)obj[1]);
			algo.setMaxFee((BigDecimal)obj[2]);
			algo.setFloorMount((BigDecimal)obj[3]);
			algo.setFlag(((BigDecimal)obj[4]).intValue());
			algo.setFeeValue((BigDecimal)obj[5]);
			algo.setId(new TblHisDiscAlgoPK(String.valueOf(obj[0]),((BigDecimal)obj[6]).intValue()));
			algoList.add(algo);
		}
		return algoList;
	}
	
	public String getNextId(String id,boolean isNext){
		if(isNext){
			int s=100000001;
			s+=Integer.valueOf(id);
			id=String.valueOf(s).substring(1);
		}
		return id;
	}

	private void changeDiscAlgo1(TblHisDiscAlgo2Tmp tblHisDiscAlgo2Tmp) {
		String disc_id = GenerateNextId.getALGO1Id();
		boolean isNext = false;
				List<TblHisDiscAlgo> algoList=getDiscAlgoByDiscId(tblHisDiscAlgo2Tmp.getFeeType());
				for(TblHisDiscAlgo algo:algoList){
					TblHisDiscAlgo1 algo1=new TblHisDiscAlgo1();
					algo1.setDISC_ID(getNextId(disc_id,isNext));
					algo1.setINDEX_NUM(algo.getId().getIndexNum().intValue());
					algo1.setMCHT_NO(tblHisDiscAlgo2Tmp.getMCHT_NO());
					algo1.setTERM_ID(tblHisDiscAlgo2Tmp.getTERM_ID());
					algo1.setCITY_CODE(tblHisDiscAlgo2Tmp.getCITY_CODE());
					algo1.setTO_BRCH_NO(tblHisDiscAlgo2Tmp.getTO_BRCH_NO());
					algo1.setFK_BRCH_NO(tblHisDiscAlgo2Tmp.getFK_BRCH_NO());
					algo1.setCARD_TYPE(tblHisDiscAlgo2Tmp.getCARD_TYPE());
					algo1.setCHANNEL_NO(tblHisDiscAlgo2Tmp.getCHANNEL_NO());
					algo1.setBUSINESS_TYPE(tblHisDiscAlgo2Tmp.getBUSINESS_TYPE());
					algo1.setTXN_NUM(tblHisDiscAlgo2Tmp.getTXN_NUM());
					algo1.setFLAG(algo.getFlag().toString());
					algo1.setMIN_FEE(algo.getMinFee().doubleValue());
					algo1.setMAX_FEE(algo.getMaxFee().doubleValue());
					algo1.setFEE_VALUE(algo.getFeeValue().doubleValue());
					algo1.setFLOOR_AMOUNT(algo.getFloorMount().doubleValue());
					algo1.setSA_SATUTE("2");//正常状态
					algo1.setREC_CRT_TS(CommonFunction.getCurrentDateTime());
					algo1.setREC_UPD_TS(CommonFunction.getCurrentDateTime());
					algo1.setMisc_1(tblHisDiscAlgo2Tmp.getDISC_ID()+tblHisDiscAlgo2Tmp.getFeeType());
					tblHisDiscAlgo1DAO.saveOrUpdate(algo1);
					isNext=true;
					disc_id=algo1.getDISC_ID();
				}		
	}

	private TblHisDiscAlgo2Tmp buildTmpHisDiscAlgo2(String mchtNo,
			TblDefMchtInf tblDefMchtInf) {
		TblHisDiscAlgo2Tmp tblHisDiscAlgo2Tmp=new TblHisDiscAlgo2Tmp(); 
		String DISC_ID=GenerateNextId.getAlgo2Id();	
		tblHisDiscAlgo2Tmp.setMCHT_NO(mchtNo);
		tblHisDiscAlgo2Tmp.setTmpNo(DISC_ID);
		tblHisDiscAlgo2Tmp.setDISC_ID(DISC_ID);
		tblHisDiscAlgo2Tmp.setTERM_ID("*");
		tblHisDiscAlgo2Tmp.setCITY_CODE("*");
		tblHisDiscAlgo2Tmp.setTO_BRCH_NO("*");
		tblHisDiscAlgo2Tmp.setFK_BRCH_NO("*");
		tblHisDiscAlgo2Tmp.setCARD_TYPE("*");
		tblHisDiscAlgo2Tmp.setCHANNEL_NO("*");
		tblHisDiscAlgo2Tmp.setBUSINESS_TYPE("*");
		tblHisDiscAlgo2Tmp.setTXN_NUM("*");
		tblHisDiscAlgo2Tmp.setFLAG("1");
		tblHisDiscAlgo2Tmp.setFeeType(tblDefMchtInf.getDiscNo());
		tblHisDiscAlgo2Tmp.setFeeTypeOld(tblDefMchtInf.getDiscNo());
		//tblHisDiscAlgo2Tmp.setREC_UPD_USR_ID(operator.getOprId());
		tblHisDiscAlgo2Tmp.setREC_UPD_TS(CommonFunction.getCurrentDateTime());
		tblHisDiscAlgo2Tmp.setREC_CRT_TS(CommonFunction.getCurrentDateTime());
		tblHisDiscAlgo2Tmp.setSA_SATUTE(CommonFunction.NORMAL);
		return tblHisDiscAlgo2Tmp;
	}

	private TblMchtSettleInfTmp buildTmpMchtSettleInfo(String mchtNo,
			TblDefMchtInf tblDefMchtInf) {
		// TODO Auto-generated method stub
		TblMchtSettleInfPK id = new TblMchtSettleInfPK();
		id.setMchtNo(mchtNo);
		id.setTermId(CommonFunction.fillString("*",' ',8,true));//默认全部终端
		TblMchtSettleInfTmp settleInfTmp = new TblMchtSettleInfTmp();
		settleInfTmp.setId(id);
		settleInfTmp.setSettleRpt(tblDefMchtInf.getSettleAccType().trim());//结算账户类型
		if("3".equals(tblDefMchtInf.getSettleAccType())){  //定向委托标志
			settleInfTmp.setDirFlag("1");
		}else{
			settleInfTmp.setDirFlag("0");
		}
		settleInfTmp.setCompAccountBankName(tblDefMchtInf.getPubAccBranchNm());//商户公司账户开户行名称
		settleInfTmp.setCompAccountBankCode(tblDefMchtInf.getPubAccBranch());//商户公司账户开户行机构代码
		settleInfTmp.setSettleAcctNm(tblDefMchtInf.getPubAccName());//商户公司账号开户名
		settleInfTmp.setSettleAcct(tblDefMchtInf.getPubAccNo());//商户公司账号
		settleInfTmp.setCorpBankName(tblDefMchtInf.getLegAccBranchNm());//法人账号开户行名称
		settleInfTmp.setBankAccountCode(tblDefMchtInf.getLegAccBranch());//商户法人账号开户行机构代码
		settleInfTmp.setFeeAcctNm(tblDefMchtInf.getLegAccName());//商户法人账号开户名
		settleInfTmp.setFeeAcct(tblDefMchtInf.getLegAccNo());//商户法人账号
		settleInfTmp.setDirBankName(tblDefMchtInf.getPersAccBranchNm());//定向委托账户开户行名称
		settleInfTmp.setDirBankCode(tblDefMchtInf.getPersAccBranch());//定向委托账户账户开户行机构代码
		settleInfTmp.setDirAccountName(tblDefMchtInf.getPersAccName());//定向委托账户开户名
		settleInfTmp.setDirAccount(tblDefMchtInf.getPersAccNo());//定向委托账号
		settleInfTmp.setAutoFlag(tblDefMchtInf.getFuncAutoWithdraw());//自动提现标志
		settleInfTmp.setHolidaySetFlag(tblDefMchtInf.getFuncHolidayWithdraw());//节假日提现标志
		settleInfTmp.setCreFlag(tblDefMchtInf.getFuncCreditCard());//信用卡刷卡功能标志
		settleInfTmp.setReturnFeeFlag(tblDefMchtInf.getFuncReturnWithFee());//退货返还手续费标志
		settleInfTmp.setSettleChn(tblDefMchtInf.getMchtSettleType().toString());//商户结算方式T+N
		settleInfTmp.setSettleType(tblDefMchtInf.getSettleInterval());//商户结算周期
		// 记录修改时间
		settleInfTmp.setRecUpdTs(CommonFunction.getCurrentDateTime());
		// 记录创建时间
		settleInfTmp.setRecCrtTs(CommonFunction.getCurrentDateTime());
		
		
		//非空字段
		settleInfTmp.setRateFlag("-");//手续费结算类型
		settleInfTmp.setFeeType("0"); 
		settleInfTmp.setFeeFixed("-");//该字段无效
		settleInfTmp.setFeeMaxAmt("0");//该字段无效
		settleInfTmp.setFeeMinAmt("0");//该字段无效

		settleInfTmp.setFeeDiv1("-");//该字段无效
		settleInfTmp.setFeeDiv2("-");//该字段无效
		settleInfTmp.setFeeDiv3("-");//该字段无效
		
		//是否收支2条线
		settleInfTmp.setAutoStlFlg("0");//是否收支2条线（无效）
		
		
		
		return settleInfTmp;
	}
	   

	private TblMchtSupp1Tmp buildTmpMchtSupp1Tmp(String mchtNo,
			TblDefMchtInf tblDefMchtInf) {
		Operator opr = (Operator) ServletActionContext.getRequest().getSession().getAttribute(Constants.OPERATOR_INFO);
		TblMchtSupp1Tmp supp1Tmp = new TblMchtSupp1Tmp();
		supp1Tmp.setMchtNo(mchtNo);
		supp1Tmp.setBusMain(tblDefMchtInf.getBusi());//主营业务
		supp1Tmp.setNationality(tblDefMchtInf.getLegNation());//法人/负责人国籍
		supp1Tmp.setLinkTel(tblDefMchtInf.getAuthorizerId());//授权人身份证号
		supp1Tmp.setProvince(tblDefMchtInf.getProCode());//商户所属省
		supp1Tmp.setCity(tblDefMchtInf.getCityCode());//商户所属市
		supp1Tmp.setArea(tblDefMchtInf.getCountryCode());//商户所属区/县
		supp1Tmp.setAcreage(tblDefMchtInf.getBusiArea().toString());//营业面积/平方米
		supp1Tmp.setEmpNum(tblDefMchtInf.getEmpCount().toString());//员工人数
		supp1Tmp.setPreAuthor(tblDefMchtInf.getFuncPreauth());//预授权功能
		supp1Tmp.setReturnFunc(tblDefMchtInf.getFuncReturn());//退货功能
		supp1Tmp.setExpander(tblDefMchtInf.getExpander().substring(0,10));//拓展人
		supp1Tmp.setHasInnerPosExp(tblDefMchtInf.getFuncPosInExp());//是否有POS内卡受理经验
		supp1Tmp.setHasOurPosExp(tblDefMchtInf.getFuncPosOutExp());//是否有POS外卡受理经验
		supp1Tmp.setIsAppOutSide(tblDefMchtInf.getFuncPosOut());//是否申请外卡
		return supp1Tmp;
	}

	private TblMchtBaseInfTmp buildMchtBaseInf(String mchtNo,
			TblDefMchtInf tblDefMchtInf) {
		Operator opr = (Operator) ServletActionContext.getRequest().getSession().getAttribute(Constants.OPERATOR_INFO);

		TblMchtBaseInfTmp tmp = new TblMchtBaseInfTmp();
		tmp.setMchtNo(mchtNo);
		String sql = "select DISTRICT,BUSI_PERSON from TBL_AGENT_INFO where AGENT_NO = '" + tblDefMchtInf.getAgentNo().trim() + "'";
		List<Object[]> list = commQueryDAO.findBySQLQuery(sql);
		if(null!=list&&list.size()>0){
			tmp.setAcqInstId(list.get(0)[0].toString()); //分公司
			tmp.setBankNo(list.get(0)[0].toString());
			tmp.setOperNm(list.get(0)[1].toString());//所属业务
		}
		tmp.setMchtNm(tblDefMchtInf.getMchtName());    //商户名称
		tmp.setMchtGrp(tblDefMchtInf.getMchntTpGrp());  //商户组别
		tmp.setMcc(tblDefMchtInf.getMcc());            //商户MCC
		tmp.setMchtCnAbbr(tblDefMchtInf.getMchtShortName());//商户简称
		tmp.setEtpsAttr(tblDefMchtInf.getMchtType());       //商户性质
		tmp.setRegAddr(tblDefMchtInf.getRegAddress());//注册地址
		tmp.setPostalCode(tblDefMchtInf.getPostCode());//邮政编号
		tmp.setCommEmail(tblDefMchtInf.getEmail()); //电子邮件
		tmp.setLicenceNo(tblDefMchtInf.getBusiLicNo());//营业执照编号
		tmp.setBankLicenceNo(tblDefMchtInf.getOrgCertNo());//企业组织机构代码
		if(null!=tblDefMchtInf.getTaxRegCertNo()&&!"".equals(tblDefMchtInf.getTaxRegCertNo())){
		tmp.setFaxNo(tblDefMchtInf.getTaxRegCertNo());//税务登记证号码
		}else{
			tmp.setFaxNo("  ");//税务登记证号码
		}
		tmp.setBusiRange(tblDefMchtInf.getBusiScope());//经营范围
		tmp.setBelInd(tblDefMchtInf.getIndustry());//所属行业
		tmp.setLicenceEndDate(tblDefMchtInf.getRegDate());//注册日期
		tmp.setApplyDate(tblDefMchtInf.getRegDate());//注册日期
		tmp.setBusAmt(tblDefMchtInf.getRegCapital().toString());//注册资金
		tmp.setManager(tblDefMchtInf.getLegalName());//法人/负责人
		tmp.setArtifCertifTp(tblDefMchtInf.getLegCert().substring(tblDefMchtInf.getLegCert().length()-2,tblDefMchtInf.getLegCert().length()));//法人/负责人证件类型
		tmp.setIdentityNo(tblDefMchtInf.getLegalCardNo());//法人/负责人证件号码
		if(null!=tblDefMchtInf.getAuthorizerName()&&!"".equals(tblDefMchtInf.getAuthorizerName())){
		tmp.setContact(tblDefMchtInf.getAuthorizerName());//联系人/授权人姓名
		}else{
			tmp.setContact(" ");
		}
		if(null!=tblDefMchtInf.getCountryCode()&&!"".equals(tblDefMchtInf.getCountryCode())){
			tmp.setAreaNo(tblDefMchtInf.getCountryCode());  //地区码
		}else{
			tmp.setAreaNo(tblDefMchtInf.getCityCode());
		}
		tmp.setOwnerBusi(tblDefMchtInf.getBusiPlaceType());//经营场所权属
		tmp.setHomePage(tblDefMchtInf.getWebUrl());//公司网址
		tmp.setICPrecordNo(tblDefMchtInf.getIcpNo());//ICP备案号
		tmp.setICPcompName(tblDefMchtInf.getIcpInsName());//ICP备案公司名称
		tmp.setContName(tblDefMchtInf.getAdjustUser());//商户调单联系人名称
		tmp.setContTel(tblDefMchtInf.getAdjustUserTel());//商户调单联系人电话
		tmp.setBusiTel(tblDefMchtInf.getMchtTel());//商户固定电话
		tmp.setFax(tblDefMchtInf.getMchtFax());//企业传真
		tmp.setManagerTel(tblDefMchtInf.getLegalTel());//法人电话
		tmp.setCommTel(tblDefMchtInf.getContactsTel());//联系人电话
		if(null!=tblDefMchtInf.getdSingleTransLimit()){
		tmp.setSinDebAmount(tblDefMchtInf.getdSingleTransLimit().toString());//客户申请单笔借记卡交易金额
		}
		if(null!=tblDefMchtInf.getdDayTransLimit()){
		tmp.setDayDebAmount(tblDefMchtInf.getdDayTransLimit().toString());//客户申请单日借记卡交易金额
		}
		if(null!=tblDefMchtInf.getdMonTransLimit()){
		tmp.setMonDebAmount(tblDefMchtInf.getdMonTransLimit().toString());//客户申请单月借记卡交易金额
		}
		if(null!=tblDefMchtInf.getcSingleTransLimit()){
		tmp.setSinCreAmount(tblDefMchtInf.getcSingleTransLimit().toString());//客户申请单笔贷记卡交易金额
		}
		if(null!=tblDefMchtInf.getcDayTransLimit()){
		tmp.setDayCreAmount(tblDefMchtInf.getcDayTransLimit().toString());//客户申请单日贷记卡交易金额
		}
		if(null!=tblDefMchtInf.getcMonTransLimit()){
		tmp.setMonCreAmount(tblDefMchtInf.getcMonTransLimit().toString());//客户申请单月贷记卡交易金额
		}
		tmp.setNetTel(tblDefMchtInf.getExpanderTel());//拓展人电话
		tmp.setOpenTime(tblDefMchtInf.getBusiStartTime());//商户营业开始时间
		tmp.setCloseTime(tblDefMchtInf.getBusiEndTime());//商户营业结束时间
		tmp.setAgentNo(tblDefMchtInf.getAgentNo());//代理商
		tmp.setAppComm(tblDefMchtInf.getApplyDesc());//申请人留言	
		
		tmp.setRislLvl("-");//风险级别  //by mike
		tmp.setMchtLvl("0");//商户类别   (默认值为0)  //by mike
		tmp.setConnType("0");//商户类型   (默认值为0)  //by mike
		tmp.setSaAction("0");//受控处理动作

		tmp.setCupMchtFlg("1");//银联卡受控标志
		tmp.setDebMchtFlg("1");//借记卡受理标志
		tmp.setCreMchtFlg("1");//贷记卡受理标志
		tmp.setCdcMchtFlg("1");//一帐通受理标志
		tmp.setManuAuthFlag("0");
		tmp.setDiscConsFlg("0");
		tmp.setPassFlag("0");
		tmp.setMchtGroupFlag("0");
		
		tmp.setMchtStatus(TblMchntInfoConstants.MCHNT_ST_OK);
		
		// 记录修改时间
		tmp.setRecUpdTs(CommonFunction.getCurrentDateTime());
				// 记录创建时间
		tmp.setRecCrtTs(CommonFunction.getCurrentDateTime());
				// 记录修改人
		tmp.setUpdOprId("");
				// 记录创建人
		tmp.setCrtOprId(opr.getOprId());
				
		//记录商户索引（进件系统商户记录ID）
		tmp.setMchntInd(tblDefMchtInf.getRecId());	
		
		//非空字段插入值
		tmp.setMchtNoHx(mchtNo);
		tmp.setAddr("-");
		
		return tmp;
	}

	/*
	 * 商户审核退回
	 * @see com.huateng.bo.mchnt.TblDefMchtService#back(java.lang.String, java.lang.String)
	 */
	public String back(String mchntId, String oprInfo)
			throws IllegalAccessException, InvocationTargetException {
		Operator opr = (Operator) ServletActionContext.getRequest().getSession().getAttribute(Constants.OPERATOR_INFO);
		TblDefMchtInf tblDefMchtInf = tblDefMchtInfDAO.get(mchntId);
		if(null==tblDefMchtInf){
			return "没有找到进件的商户信息，请重试";		
		}
		tblDefMchtInf.setStatus(TblMchntInfoConstants.MCHT_ST_NEW_UNCK_REF);
//		tblDefMchtInf.setOprId(opr.getOprId());
		tblDefMchtInf.setUptDate(CommonFunction.getCurrentDateTime());
		tblDefMchtInfDAO.update(tblDefMchtInf);
		
		String oprType = "进件系统商户新增审核";
		String oprStatus="退回";
		updateMchntLogs(tblDefMchtInf,oprType,oprStatus,oprInfo);
		
		return Constants.SUCCESS_CODE;
		
	}

	/*
	 * 新增审核拒绝
	 * @see com.huateng.bo.mchnt.TblDefMchtService#refuse(java.lang.String, java.lang.String)
	 */
	public String refuse(String mchntId, String oprInfo)
			throws IllegalAccessException, InvocationTargetException {
		Operator opr = (Operator) ServletActionContext.getRequest().getSession().getAttribute(Constants.OPERATOR_INFO);
		TblDefMchtInf tblDefMchtInf = tblDefMchtInfDAO.get(mchntId);
		if(null==tblDefMchtInf){
			return "没有找到进件的商户信息，请重试";		
		}
		tblDefMchtInf.setStatus(TblMchntInfoConstants.MCHT_ST_BLACK);
//		tblDefMchtInf.setOprId(opr.getOprId());
		tblDefMchtInf.setUptDate(CommonFunction.getCurrentDateTime());
		tblDefMchtInfDAO.update(tblDefMchtInf);
		
		//关键字段加入黑名单
				TblCtlMchtInf tblCtlMchtInf = new TblCtlMchtInf();
//				tblCtlMchtInf.setId(mchntId);
				tblCtlMchtInf.setDatePk(CommonFunction.getCurrentDateTime());
				
				tblCtlMchtInf.setSaMerNo("  ");
				
				tblCtlMchtInf.setSaMerChName(tblDefMchtInf.getMchtName().trim());

					tblCtlMchtInf.setSaMerEnName(" ");
				

					tblCtlMchtInf.setSaAction("0");
				
				
					tblCtlMchtInf.setSaLimitAmt("0.0");
				
					tblCtlMchtInf.setSaZoneNo(" ");
				
				tblCtlMchtInf.setSaAdmiBranNo(Constants.DEFAULT);
				if(null!=tblDefMchtInf.getAuthorizerName()&&!"".equals(tblDefMchtInf.getAuthorizerName())){
					tblCtlMchtInf.setSaConnOr(tblDefMchtInf.getAuthorizerName().trim());
				}
					tblCtlMchtInf.setSaConnOr("   ");

				if(StringUtil.isNull(tblDefMchtInf.getContactsTel())){
					tblCtlMchtInf.setSaConnTel(" ");
				}else{
					tblCtlMchtInf.setSaConnTel(tblDefMchtInf.getContactsTel());
				}
				
				tblCtlMchtInf.setLicenceNo(tblDefMchtInf.getBusiLicNo());
				tblCtlMchtInf.setBankLicenceNo(tblDefMchtInf.getOrgCertNo());
				tblCtlMchtInf.setIdentityNo(tblDefMchtInf.getLegalCardNo());
				tblCtlMchtInf.setManagerTel(tblDefMchtInf.getLegalTel());
				tblCtlMchtInf.setAddType("1");
				
				tblCtlMchtInf.setSaInitZoneNo(opr.getOprBrhId());
				tblCtlMchtInf.setSaInitOprId(opr.getOprId());
				tblCtlMchtInf.setSaInitTime(CommonFunction.getCurrentDateTime());
				tblCtlMchtInf.setSaState("2");
				tblCtlMchtInfDAO.save(tblCtlMchtInf);
				
				//商户黑名单审核记录表
				TblRiskRefuse riskRefuse = new TblRiskRefuse();
				riskRefuse.setTxnTime(CommonFunction.getCurrentDateTime());  //审核时间
				riskRefuse.setParam1(tblCtlMchtInf.getSaMerChName().trim());  
				riskRefuse.setOprId(opr.getOprId());  //审核人
				riskRefuse.setRefuseInfo(oprInfo);
				riskRefuse.setFlag("2"); //标志
				riskRefuse.setParam5(tblCtlMchtInf.getSaState());    //当前状态
				riskRefuse.setParam6(tblCtlMchtInf.getAddType());
				
				tblRiskRefuseDAO.save(riskRefuse);
		
		String oprType = "进件系统商户新增审核";
		String oprStatus="拒绝";
		updateMchntLogs(tblDefMchtInf,oprType,oprStatus,oprInfo);
		
		return Constants.SUCCESS_CODE;
		
	}

}

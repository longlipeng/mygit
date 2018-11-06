package com.huateng.univer.consumer.refund;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.consumer.merchant.dto.MerchantDTO;
import com.allinfinance.univer.refund.dto.RefundDTO;
import com.allinfinance.univer.refund.dto.RefundQueryDTO;
import com.huateng.framework.action.BaseAction;
import com.huateng.framework.security.model.User;

public class RefundAction extends BaseAction {

	/***************************************************************************
	 * 类变量
	 **************************************************************************/
	private PageDataDTO pageDataDTO = new PageDataDTO();

	private RefundDTO refundDTO = new RefundDTO();

	private RefundQueryDTO refundQueryDTO = new RefundQueryDTO();

	private List<MerchantDTO> merchants = null;

	private String merchantCode;

	public List<Map> transStates;

	private String authAction;

	/***************************************************************************
	 * action
	 **************************************************************************/

	/**
	 * 退货申请列表
	 */
	public String refundList() throws Exception {
		// 查找该收单机构下的所有商户
		if (getSession().get("merchants") == null) {
			merchants = (List<MerchantDTO>) sendService("201109220002", getUser().getEntityId()).getDetailvo();
			if(merchants!=null){
				getSession().put("merchants", merchants);	
			}else{
				buildTransStates();
				return "input";
			}
		} else {
			merchants = (List<MerchantDTO>) getSession().get("merchants");
		}

		buildTransStates();
		refundQueryDTO.setMchntCd(buildMerchantString());
		refundQueryDTO.setConsumerId(getUser().getEntityId());
		ListPageInit(null, refundQueryDTO);
		pageDataDTO = (PageDataDTO) sendService("201109220000", refundQueryDTO).getDetailvo();

		if (hasErrors()) {
			return "input";
		}
		return "refundList";
	}

	/**
	 * 退货详细信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String refundDetail() throws Exception {
		String refundId = getRequest().getParameter("refundId");
		String flag = getRequest().getParameter("flag");
		if (refundId == null || refundId.trim().length() <= 0) {
			addActionError("找不到相关的退货信息");
			return "input";
		}
		refundDTO.setSeqId(refundId);
		refundDTO.setTransChnlFlag(flag);
		refundDTO = (RefundDTO) sendService(ConstCode.SELECT_REFUND_DETAIL, refundDTO).getDetailvo();
		if (hasErrors()) {
			return refundList();
		}
		formatAmt(refundDTO);
		formatDate(refundDTO);
		
		getSession().put("refundDTO", refundDTO);

		return "refundDetail";
	}

	/**
	 * 退货审核,0-同意 1-拒绝
	 * 
	 * @return
	 * @throws Exception
	 */
	public String refundVerify() throws Exception {
		Object o = getSession().get("refundDTO");
		if (o == null) {
			addActionError("session失效");
			return "input";
		}		
		RefundDTO d = (RefundDTO) o;
		d.setAuthDesc(refundDTO.getAuthDesc());
		refundDTO=d;
		// 同意
		if ("0".equals(authAction)) {
			refundDTO.setAuthDesc(null);
			refundDTO = (RefundDTO) sendService(ConstCode.REFUND_VERIFY, refundDTO).getDetailvo();
			formatAmt(refundDTO);
			formatDate(refundDTO);
			if (null == refundDTO.getRespCode() || !"00".equals(refundDTO.getRespCode())) {
				addActionMessage("联机退货失败");
			}		
			return "refundSuc";
		}
		// 拒绝
		else if ("1".equals(authAction)) {
			String res = (String) sendService(ConstCode.REFUND_REFUSE, refundDTO).getDetailvo();
			if (!"ok".equals(res)) {
				addActionError("更新记录失败");
				return "input";
			}
			addActionMessage("拒绝退货成功");
			return "refuseSuc";
		}
		else{
			return "input";
		}
	}
	
	/**
	 * 调用接口查询和更新
	 * @return
	 * @throws Exception
	 */
	public String refundTransLogQuery()throws Exception
	{
		String refundId = getRequest().getParameter("refundId");
		String flag = getRequest().getParameter("flag");
		if (refundId == null || refundId.trim().length() <= 0) {
			addActionError("找不到相关的退货信息");
			return "input";
		}
		refundDTO.setSeqId(refundId);
		refundDTO.setTransChnlFlag(flag);
		refundDTO=(RefundDTO)sendService("201109220007", refundDTO).getDetailvo();
		
		if (hasErrors()) {
			return "input";
		}
		formatAmt(refundDTO);
		formatDate(refundDTO);
		return "refundTransLogQuery";
	}

	/***************************************************************************
	 * 工具方法
	 **************************************************************************/

	/**
	 * 组装商户的sql语句(IN ...)
	 */
	public String buildMerchantString() {
		StringBuffer sb = new StringBuffer("");
		// 如果选择全部
		if (merchantCode == null || merchantCode.trim().length() <= 0) {
			/**
			 * add by yy, 选择全部，就等于查询条件就没这个字段了
			 * 干嘛搞下面这么复杂，
			 * 看到这段代码的人注意点，不要犯同样的错误！
			 
			sb.append("(");
			for (MerchantDTO dto : merchants) {
				sb.append(dto.getMerchantCode() + ",");
			}
			sb.deleteCharAt(sb.length() - 1);
			sb.append(")");
			*/
			return null;
		} else {
			sb.append("(");
			sb.append(merchantCode + ")");
		}

		return sb.toString();
	}

	/**
	 * 填充交易状态的select标签
	 */
	private void buildTransStates() {
		transStates = new ArrayList<Map>();

		Map m = new HashMap();
		m.put("stateCode", 0);
		m.put("stateName", "未审批");
		transStates.add(m);

		m = new HashMap();
		m.put("stateCode", 1);
		m.put("stateName", "已同意");
		transStates.add(m);

		m = new HashMap();
		m.put("stateCode", 2);
		m.put("stateName", "已拒绝");
		transStates.add(m);
		
		m = new HashMap();
		m.put("stateCode", 3);
		m.put("stateName", "已通过");
		transStates.add(m);
		
//		m = new HashMap();
//		m.put("stateCode", 4);
//		m.put("stateName", "退货成功");
//		transStates.add(m);
//		
//		m = new HashMap();
//		m.put("stateCode", 5);
//		m.put("stateName", "退货失败");
//		transStates.add(m);
	}

	/**
	 * 格式化金额
	 */
	private void formatAmt(RefundDTO dto) {
		dto.setStrOrigTransAt(moneyTransF2Y(dto.getOrigTransAt()));
		dto.setStrRefundAt(moneyTransF2Y(dto.getRefundAt()));
		dto.setStrTransAt(moneyTransF2Y(dto.getTransAt()));
	}

	/**
	 * 格式化日期
	 */
	private void formatDate(RefundDTO dto) throws Exception {
		SimpleDateFormat df1 = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat df3=new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat df4=new SimpleDateFormat("yyyy-MM-dd");
		dto.setReqDate(df2.format(df1.parse(dto.getReqDt() + dto.getReqTm())));
		dto.setTransDate(df2.format(df1.parse(dto.getTransDt() + dto.getTransTm())));
		dto.setSettleDate(df4.format(df3.parse(dto.getSettleDt())));
	}

	/***************************************************************************
	 * getter and setter
	 **************************************************************************/
	public PageDataDTO getPageDataDTO() {
		return pageDataDTO;
	}

	public void setPageDataDTO(PageDataDTO pageDataDTO) {
		this.pageDataDTO = pageDataDTO;
	}

	public RefundDTO getRefundDTO() {
		return refundDTO;
	}

	public void setRefundDTO(RefundDTO refundDTO) {
		this.refundDTO = refundDTO;
	}

	public RefundQueryDTO getRefundQueryDTO() {
		return refundQueryDTO;
	}

	public void setRefundQueryDTO(RefundQueryDTO refundQueryDTO) {
		this.refundQueryDTO = refundQueryDTO;
	}

	public Integer getTotalRows() {
		if (pageDataDTO != null)
			return pageDataDTO.getTotalRecord();
		else
			return 0;
	}

	public List<MerchantDTO> getMerchants() {
		return merchants;
	}

	public void setMerchants(List<MerchantDTO> merchants) {
		this.merchants = merchants;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public List<Map> getTransStates() {
		return transStates;
	}

	public void setTransStates(List<Map> transStates) {
		this.transStates = transStates;
	}

	public String getAuthAction() {
		return authAction;
	}

	public void setAuthAction(String authAction) {
		this.authAction = authAction;
	}

}

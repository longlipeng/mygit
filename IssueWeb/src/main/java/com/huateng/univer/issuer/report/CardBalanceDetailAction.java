package com.huateng.univer.issuer.report;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.univer.report.dto.CardBalanceDatailDTO;
import com.huateng.framework.action.BaseAction;
import com.huateng.framework.constant.Const;
import com.huateng.framework.util.DateUtil;

@SuppressWarnings("unchecked")
public class CardBalanceDetailAction extends BaseAction {
	private Logger logger = Logger.getLogger(CardBalanceDetailAction.class);
	private static final long serialVersionUID = -5392496044781749316L;
	private CardBalanceDatailDTO cardBalanceDatailDTO = new CardBalanceDatailDTO();

	public String inQuery() {
		return "list";
	}

	public String downLoad() {
		logger.info("进入卡余额变动明细报表下载");
		cardBalanceDatailDTO.setIssuerId(this.getUser().getEntityId());
		List<CardBalanceDatailDTO> list = (List<CardBalanceDatailDTO>) this
				.sendService(ConstCode.CARD_BALANCE_DETAIL,
						cardBalanceDatailDTO).getDetailvo();
		if (this.hasErrors()) {
			this.addActionError("下载报表失败");
		}
		// 拼接报表名称
		String txtName = Const.CARD_BALANCE_DETAIL + getUser().getIssuerName()
				+ DateUtil.getCurrentDateStr();
		StringBuffer result = new StringBuffer();
		result.append("   ").append(Const.DATE).append(Const.CARD_NO).append(Const.ACCOUNT)
				.append(Const.TXT_TYPE).append(Const.ACC_AMOUNT).append(
						Const.CARD_TOTAL_BAL).append(Const.DEBT_OR_CREDIT).append(Const.ORIG_CARD_NO).append(Const.MCHNT_CD).append(Const.MCHNT_NAME).append(Const.TERM_ID).append("\r\n");
		if (list != null) {
			for (CardBalanceDatailDTO ca : list) {
				result.append("   ").append(ca.getTxnDate().replace(".0", "") + ",");
				if(ca.getCardNo().trim().equals("")){
					result.append(",");
				}else{
					result.append("'" +ca.getCardNo() + ",");
				}
				
				result.append(ca.getAccount() + ",")
					  .append(ca.getTxnType() + ",")
					  .append(ca.getTxnAmt() + ",")
					  .append(ca.getCardTotalBal() + ",")
					  .append(ca.getDebtOrCredit() + ",");
				if(ca.getOrigCardNo()==null){
					ca.setOrigCardNo("");
					result.append(",");
				}else{
					result.append("'"+ca.getOrigCardNo()+",");
				}
				if(ca.getMchntCd().trim().equals("")){
					result.append(",");
				}else{
					result.append("'" +ca.getMchntCd() + ",");
				}
				result.append(ca.getMchntName() + ",")
					  .append(ca.getTermId() + ",")
					  .append("\r\n");
			}
		}
		HttpServletResponse response = this.getResponse();
		// http响应，生成下载文件
		try {
			response.setContentType("application/msword");
			response.setContentType("text/html;charset=gbk");
			response.addHeader("Content-Disposition", "attachment; filename="
					+ new String(txtName.getBytes("gb2312"), "ISO8859-1")
					+ Const.CSV);
			response.getWriter().print(result.toString());
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			this.logger.error(e.getMessage());
		}
		return null;
	}

	public CardBalanceDatailDTO getCardBalanceDatailDTO() {
		return cardBalanceDatailDTO;
	}

	public void setCardBalanceDatailDTO(
			CardBalanceDatailDTO cardBalanceDatailDTO) {
		this.cardBalanceDatailDTO = cardBalanceDatailDTO;
	}

}

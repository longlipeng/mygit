package com.huateng.univer.consumer.merchantsettle;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.consumer.merchant.dto.MerchantDTO;
import com.allinfinance.univer.settle.dto.SettleDTO;
import com.allinfinance.univer.settle.dto.SettleQueryDTO;
import com.huateng.framework.action.BaseAction;
import com.huateng.framework.constant.Const;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.ObjectConver;

public class MerchantSettleAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(MerchantSettleAction.class);
	private PageDataDTO pageDataDTO = new PageDataDTO();
	private SettleQueryDTO settleQueryDTO = new SettleQueryDTO();
	private int totalRows;
	private List<String> settleIdList;
	private List<String> settleObject2List;
	private String serviceFee;
	private String afterFee;
	private String settleEndDate;
	
	// 1.查询 2生成 3 取消 4手续费修改 5 付款确认 6 修改结算单日期 7.确定
	private int state = 0;
	private SettleDTO settleDTO = new SettleDTO();
	private String settleId;
	private String fileName;

	public String getSettleEndDate() {
		return settleEndDate;
	}

	public void setSettleEndDate(String settleEndDate) {
		this.settleEndDate = settleEndDate;
	}

	public String execute() {
		return "changefeelist";
	}

	public String changeDateDetail() {
		if (null != settleObject2List && settleObject2List.size() > 0) {
			for (int i = 0; i < settleObject2List.size(); i++) {
				String[] object = settleObject2List.get(i).split(",");
				settleDTO.setSettleObject2(object[0]);
				settleDTO.setSettleObject2Name(object[1]);
				settleDTO.setSettleDate(DateUtil.formatStringDate(object[2]));
				settleDTO.setSettleMoney(object[3]);
				settleDTO.setSettleStartDate(DateUtil
						.formatStringDate(object[4]));
				settleDTO.setMerchantCode(object[5]);
				settleDTO.setContractId(object[6]);
			}
		}

		// DateUtil.string2date(settleDTO.getSettleDate());
		return "changeDateDetail";
	}

	public String getSettleId() {
		return settleId;
	}

	public void setSettleId(String settleId) {
		this.settleId = settleId;
	}

	// 结算单查询
	public String query() {
		try {
			logger.info(state);
			if (state == 2) {
				settleQueryDTO.setSettleState(Const.SETTLE_DRAFT_STATE);
			} else if (state == 5) {
				settleQueryDTO.setSettleState(Const.SETTLE_CONFIRM_STATE);
			} else if (state == 4) {
				settleQueryDTO.setSettleState(Const.SETTLE_CONFIRM_STATE + ","
						+ Const.SETTLE_STATE_CONFIRM);
			} else if (state == 7) {
				settleQueryDTO.setSettleState(Const.SETTLE_STATE_CONFIRM);
			} else if (state == 3) {
				settleQueryDTO.setSettleState(Const.SETTLE_DRAFT_STATE + ","
						+ Const.SETTLE_STATE_CONFIRM + ","
						+ Const.SETTLE_CONFIRM_STATE );
			} else if (state == 0) {
				if("".equals(settleQueryDTO.getSettleState())){
					settleQueryDTO.setSettleState(null);
				}else{
					settleQueryDTO.setSettleState(settleQueryDTO.getSettleState());
				}
			}

			ListPageInit("merchantSettleRow", settleQueryDTO);
			
			if (isEmpty(settleQueryDTO.getSortFieldName())) {
			settleQueryDTO.setSort("desc");
			settleQueryDTO.setSortFieldName("settleStartDate");
			}
			
			settleQueryDTO.getFirstCursorPosition();
			settleQueryDTO.setDefaultEntityId(getUser().getEntityId());
			logger.debug(settleQueryDTO.getDefaultEntityId());
			pageDataDTO = (PageDataDTO) sendService(ConstCode.SETTLE_QUERY,
					settleQueryDTO).getDetailvo();
			if (pageDataDTO != null) {
				totalRows = pageDataDTO.getTotalRecord();
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			this.addActionError("结算单查询失败");
		}

//		if (hasActionErrors()) {
//			return "input";
//		}
		if (state == 2) {
			return "generate";
		} else if (state == 3) {
			return "cancel";
		} else if (state == 4) {
			return "changefee";
		} else if (state == 5) {
			return "settlePaymentConfirm";
		} else if (state == 7) {
			return "confirm";
		} else {
			return "list";
		}

	}

	public String settlePreviewInit() {
		settleQueryDTO.setMerchantDTOs((List<MerchantDTO>) sendService(
				ConstCode.SETTLE_PREVIEW_INIT, getUser().getEntityId())
				.getDetailvo());
		pageDataDTO = null;
		return "previewInit";
	}

	// 结算单预览
	public String settlePreview() {
		try {
			ListPageInit(null, settleQueryDTO);
			logger.info(getUser().getEntityId());
			settleQueryDTO.setSettleObject1(getUser().getEntityId());
			logger.info(settleQueryDTO.getFirstCursorPosition());
			logger.info(settleQueryDTO.getSettleObject1());
			// logger.info(settleQueryDTO.getMinAmt());
			settleQueryDTO.setSettleObject2(ObjectConver.conver(settleQueryDTO
					.getSettleObject2()));
			// settleQueryDTO.setSettleObject2Name(ObjectConver
			// .conver(settleQueryDTO.getSettleObject2Name()));
			// settleQueryDTO.setSettleStartDate(ObjectConver
			// .conver(settleQueryDTO.getSettleStartDate()));
			// settleQueryDTO.setSettleEndDate(ObjectConver.conver(settleQueryDTO
			// .getSettleEndDate()));
			// settleQueryDTO.setMaxAmt(ObjectConver.conver(settleQueryDTO
			// .getMaxAmt()));
			// settleQueryDTO.setMinAmt(ObjectConver.conver(settleQueryDTO
			// .getMinAmt()));
			// settleQueryDTO.setSettleState(ObjectConver.conver(settleQueryDTO
			// .getSettleState()));
			pageDataDTO = (PageDataDTO) sendService(ConstCode.SETTLE_PREVIEW,
					settleQueryDTO).getDetailvo();
			settleQueryDTO.setMerchantDTOs((List<MerchantDTO>) sendService(
					ConstCode.SETTLE_PREVIEW_INIT, getUser().getEntityId())
					.getDetailvo());
			logger.info("maxamt:" + settleQueryDTO.getMaxAmt());
			if (pageDataDTO != null) {
				totalRows = pageDataDTO.getTotalRecord();
			}
			if (hasActionErrors()) {
				return "input";
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			this.addActionError("结算单预览失败");
		}
		return "list";
	}

	// public void validateSettlePreview()
	// {
	// if(null==settleQueryDTO.getSettleObject2() ||
	// "".equals(settleQueryDTO.getSettleObject2().trim())){
	// settlePreviewInit();
	// this.addFieldError("settleQueryDTO.getSettleObject2", "商户名称不能为空");
	//		
	// }
	// }

	// 生成结算单
	public String generateSettle() throws BizServiceException {
		try {
			// if (settleObject2List != null || settleObject2List.size() > 0) {
			// for(int i=0;i<settleObject2List.size();i++){
			// String[] object2s= settleObject2List.get(i).split(",");
			// settleDTO.setSettleObject2(object2s[0]);
			// settleDTO.setSettleObject1(getUser().getEntityId());
			// sendService(ConstCode.SETTLE_GENERATE, settleDTO);
			// }
			// }
			Date date = DateUtil.string2Dateyyyymmdd(settleDTO.getSettleDate());
			if (date.after(DateUtil.getCurrentDate())) {
				this.addActionError("结算单截止日期不能大于今天");
			}
			if (hasActionErrors()) {
				this.addActionError("结算单生成失败");
				settlePreview();
				return "preview";
			}
			settleDTO.setSettleObject1(getUser().getEntityId());
			sendService(ConstCode.SETTLE_GENERATE, settleDTO);

		} catch (Exception e) {
			this.logger.error(e.getMessage());
			this.addActionError("结算单生成失败");
		}
		settleQueryDTO.setSettleObject2(settleDTO.getSettleObject2());
		settlePreview();
		if(!hasActionErrors()){
			this.addActionMessage("结算单生成成功");
		}
		return "list";
	}

	// 结算单确认
	public String queryDrafeSettle() {
		try {
			// settleQueryDTO.setSettleState(Const.SETTLE_DRAFT_STATE);
			if (Integer.parseInt(settleQueryDTO.getSettleState()) == 4) {
				state = 2;
			} else {
				state = 7;
			}
			if (settleIdList != null && settleIdList.size() > 0) {
				logger.info("settleIdList:" + settleIdList + " size:"
						+ settleIdList.size());
				for (int i = 0; i < settleIdList.size(); i++) {
					settleQueryDTO.setSettleId(settleIdList.get(i));
					sendService(ConstCode.CONFIRM_SETTLE, settleQueryDTO);
				}
			}
			settleQueryDTO.setSettleId(null);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			this.addActionError("结算单确认失败");
		}
		query();
		if(!hasActionErrors()){
			this.addActionMessage("结算单确认成功");
		}
		if(state==2)
			return "generate";
		else {
			return "confirm";
		}
	}
 
	public String settleDetail(){
		 try {
		 logger.debug(settleIdList);
		 if(null != settleIdList&& settleIdList.size()>0){
		 settleQueryDTO.setSettleId(settleIdList.get(0));
		 }
		 pageDataDTO=(PageDataDTO)sendService(ConstCode.SETTLE_DETAIL,
		 settleQueryDTO).getDetailvo();
		 } catch (Exception e) {
				this.logger.error(e.getMessage());
				this.addActionError("结算单明细查询失败");
			} 
			if (hasActionErrors()) {
				return "input";
			}
			if (state == 4) {
				return "changefeelist";
			} else if (state == 6) {
				return "change";
			} else {
				return "list";
			}
	}
	// 结算单明细
	public String querySettleDetail() {
		// try {
		// logger.info(settleIdList);
		// if(!"".equals(settleIdList)&& settleIdList.size()>0){
		// settleQueryDTO.setSettleId(settleIdList.get(0));
		// }
		// pageDataDTO=(PageDataDTO)sendService(ConstCode.QUERY_SETTLE_DETAIL,
		// settleQueryDTO).getDetailvo();
		// logger.info(pageDataDTO);
//		ZipOutputStream zipos = null;
		
		try {
			settleQueryDTO = (SettleQueryDTO) this.sendService(
					ConstCode.QUERY_SETTLE_DETAIL, settleQueryDTO)
					.getDetailvo();
			fileName = settleQueryDTO.getSettleId()
					+ "detail";
			byte [] bytes =null;
			HttpServletResponse response = getResponse();
			if(settleQueryDTO.getFileData().get(fileName).size()>0){
			for(int i=0;i<settleQueryDTO.getFileData().get(fileName).size();i++){
				bytes=settleQueryDTO.getFileData().get(fileName).get(i);
				response.setContentLength(bytes.length);
			}			
			}else{
				throw new BizServiceException("结算单明细未生成");
			}
			
			response.setContentType("application/txt/plain");

			response.addHeader("Content-Disposition", "attachment;filename="
					+ fileName + ".txt");
			
			ServletOutputStream ouputStream = response.getOutputStream();
			ouputStream.write(bytes, 0, bytes.length);
			ouputStream.flush();
			ouputStream.close();
			response.getWriter().close();
//			zipos = new ZipOutputStream(response.getOutputStream());
//			for (Entry<String, List<byte[]>> entry : settleQueryDTO
//					.getFileData().entrySet()) {
//				ZipEntry zipEntry = new ZipEntry(entry.getKey());
//				zipos.putNextEntry(zipEntry);
//				List<byte[]> byteList = entry.getValue();
//				for (byte[] byteArr : byteList) {
//					zipos.write(byteArr);
//				}
//			}
		} catch (Exception e) {
			
			this.logger.error(e.getMessage());
			query();
			this.addActionError("结算单明细未生成");
			return "input";
		} 
		if (hasActionErrors()) {
			query();
			return "input";
		}
		if (state == 4) {
			return "changefeelist";
		} else if (state == 6) {
			return "change";
		} else {
			return "list";
		}
	}

	// 修改结算单日期
	/**
	 * @return
	 */
	public String changeSettleDate() {
		if(null ==settleEndDate || settleEndDate.equals("")){
			addFieldError("settleQueryDTO.settleEndDate", "请选择新的结算截止日期");
		}else{
			if(DateUtil.string2date(settleEndDate).before(DateUtil.string2date(settleDTO.getSettleStartDate()))){
				addFieldError("settleQueryDTO.settleEndDate", "结算截止日期不能早于结算开始日期");
			}
		}
		if(null == settleDTO.getMemo() || settleDTO.getMemo().trim().equals("")){
			addFieldError("settleDTO.memo", "请输入修改原因");
		}else if(settleDTO.getMemo().length()>200){
			addFieldError("settleDTO.memo", "修改原因不能超过200个字符");
		}
		if(hasErrors()){
			return INPUT;
		}
		String tempString="";
		try {
			logger.debug("new settleDate:"+settleEndDate);
			tempString=settleDTO.getSettleDate();
			settleDTO.setSettleDate(DateUtil.getFormatTime(settleEndDate));
			settleEndDate=tempString;
			settleDTO.setSettleObject1(getUser().getEntityId());
			sendService(ConstCode.CHANGE_SETTLE_DATE, settleDTO);
			if (hasActionErrors()) {
				settleEndDate=DateUtil.formatStringDate(settleDTO.getSettleDate());
				settleDTO.setSettleDate(tempString);
				return "changeDateDetail";
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			this.addActionError("修改结算单日期失败");
			settleEndDate=DateUtil.formatStringDate(settleDTO.getSettleDate());
			settleDTO.setSettleDate(tempString);
			return "changeDateDetail";
		}
		addActionMessage("修改结算日期成功");
		settleQueryDTO.setSettleObject2(settleDTO.getSettleObject2());
		settlePreview();
		return "list";
	}

	// 取消结算单
	public String cancelMerchantSettle() {
		try {
			if (settleIdList != null && settleIdList.size() > 0) {
				for (int i = 0; i < settleIdList.size(); i++) {
					settleDTO.setSettleId(settleIdList.get(i));
					sendService(ConstCode.CANCEL_SETTLE, settleDTO);
				}
			}
		} catch (Exception e) {
			
			this.logger.error(e.getMessage());
			this.addActionError("取消结算单失败");
		}
		state = 3;
		query();
		if(!hasActionErrors()){
			this.addActionMessage("取消结算单成功");
		}
		return "cancel";
	}

	// 结算单付款确认
	public String settlePaymentConfirm() {
		try {
			if (settleIdList != null && settleIdList.size() > 0) {
				for (int i = 0; i < settleIdList.size(); i++) {
					settleDTO.setSettleId(settleIdList.get(i));
					sendService(ConstCode.SETTLE_PAYMENT_CONFIRM, settleDTO);
				}

			}
		} catch (Exception e) {
			
			this.logger.error(e.getMessage());
			this.addActionError("结算单付款确认 失败");
		}
		state = 5;
		query();
		if(!hasActionErrors()){
			this.addActionMessage("结算单付款确认成功");
		}
		return "list";
	}

	// 手续费修改
	public String settleChangeFees() {
		try {
			settleDTO.setSettleId(settleId);
			BigDecimal after = new BigDecimal(afterFee);
			BigDecimal befor = new BigDecimal(serviceFee);
			String settleFeeOffset = String.valueOf(after.subtract(befor));
			settleDTO.setSettleFeeOffset(settleFeeOffset);
			sendService(ConstCode.SETTLE_CHANGE_FEE, settleDTO);
		} catch (Exception e) {
			
			this.logger.error(e.getMessage());
			this.addActionError("手续费修改失败");
		}
		state = 4;
		query();
		if(!hasErrors() && !hasActionErrors()){
			addActionMessage("手续费修改成功");
		}
		return "changefee";
	}

	public PageDataDTO getPageDataDTO() {
		return pageDataDTO;
	}

	public void setPageDataDTO(PageDataDTO pageDataDTO) {
		this.pageDataDTO = pageDataDTO;
	}

	public SettleQueryDTO getSettleQueryDTO() {
		return settleQueryDTO;
	}

	public void setSettleQueryDTO(SettleQueryDTO settleQueryDTO) {
		this.settleQueryDTO = settleQueryDTO;
	}

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public List<String> getSettleObject2List() {
		return settleObject2List;
	}

	public void setSettleObject2List(List<String> settleObject2List) {
		this.settleObject2List = settleObject2List;
	}

	public SettleDTO getSettleDTO() {
		return settleDTO;
	}

	public void setSettleDTO(SettleDTO settleDTO) {
		this.settleDTO = settleDTO;
	}

	public List<String> getSettleIdList() {
		return settleIdList;
	}

	public void setSettleIdList(List<String> settleIdList) {
		this.settleIdList = settleIdList;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(String serviceFee) {
		this.serviceFee = serviceFee;
	}

	public String getAfterFee() {
		return afterFee;
	}

	public void setAfterFee(String afterFee) {
		this.afterFee = afterFee;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}

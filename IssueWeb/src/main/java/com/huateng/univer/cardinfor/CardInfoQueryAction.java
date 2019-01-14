package com.huateng.univer.cardinfor;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.shangqi.gateway.dto.ApplyAndBindCardDTO;
import com.huateng.framework.action.BaseAction;
import com.huateng.framework.util.DateUtil;

/**
 * 
 * @author dawn
 *
 */
public class CardInfoQueryAction extends BaseAction {

	/**
	 * 订单确认
	 */
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(CardInfoQueryAction.class);
	private String startDate;
	protected ApplyAndBindCardDTO applyAndBindCardDTO = new ApplyAndBindCardDTO();

	@SuppressWarnings("rawtypes")
	protected List<?> cardInfos = new ArrayList();

	protected int cardInfo_totalRows = 0;

	protected String actionName;

	protected String actionMethodName;

	/**
	 * 
	 */
	private String message;
	private String buyOrderFlag;
	private String exFlag;
	private String fileName;

	public String getExFlag() {
		return exFlag;
	}

	public void setExFlag(String exFlag) {
		this.exFlag = exFlag;
	}

	protected void addCondition() {

	}

	protected void dealWithSellOrderInputDTO() {

	}

	public String edit() {

		return null;
	}

//	protected void init() {
//		actionName = "cardInfoQueryAction";
//		actionMethodName = "button";
//	}

//	protected void initOrderType() {
//	}

	@SuppressWarnings("unchecked")
	public String list() {
		try {
			ListPageInit("cardInfo", applyAndBindCardDTO);
			// applyAndBindCardDTO.setOrderState(OrderConst.ORDER_STATE_UNCERTAIN);
			if (applyAndBindCardDTO.getStartDate() != null) {
				String startDate = applyAndBindCardDTO.getStartDate();
				String str1 = startDate.replaceAll("-", "");
				applyAndBindCardDTO.setStartDate(str1);
			}
			if (applyAndBindCardDTO.getEndDate() != null) {
				String endDate = applyAndBindCardDTO.getEndDate();
				String str2 = endDate.replaceAll("-", "");
				applyAndBindCardDTO.setEndDate(str2);
			}
//			/***
//			 * 按订单ID的倒序排序
//			 */
//			if (isEmpty(applyAndBindCardDTO.getSortFieldName())) {
//				applyAndBindCardDTO.setSort("desc");
//				applyAndBindCardDTO.setIdType("1");
//				// applyAndBindCardDTO.setApplyStatus("0");
//				applyAndBindCardDTO.setSortFieldName("ID_NO");
//				applyAndBindCardDTO.setEntityId(this.getUser().getEntityId());
//			}
			
			/***
			 * 按时间的排序
			 */
			if (isEmpty(applyAndBindCardDTO.getSortFieldName())) {
				applyAndBindCardDTO.setSort("asc");
				applyAndBindCardDTO.setIdType("1");
				// applyAndBindCardDTO.setApplyStatus("0");
				applyAndBindCardDTO.setSortFieldName("APPLY_DATE_SECONDS");
				applyAndBindCardDTO.setEntityId(this.getUser().getEntityId());
			}
			
			boolean a = isEmpty(applyAndBindCardDTO.getEntityId());
			boolean b = isEmpty(applyAndBindCardDTO.getDefaultEntityId());
			if(isEmpty(applyAndBindCardDTO.getEntityId())&&!isEmpty(applyAndBindCardDTO.getDefaultEntityId())){
				applyAndBindCardDTO.setEntityId(applyAndBindCardDTO.getDefaultEntityId());
			}
			
			// Object detailvo =
			// sendService(ConstCode.CARD_INFO_QUERY_AT_SELL_CONFIRM,
			// applyAndBindCardDTO).getDetailvo();
			PageDataDTO result = (PageDataDTO) sendService(ConstCode.CARD_INFO_QUERY_AT_SELL_CONFIRM, applyAndBindCardDTO).getDetailvo();
			cardInfos = (List<Map<String, Object>>) result.getData();
			cardInfo_totalRows = result.getTotalRecord();
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage());
		}
		return "list";
	}

	public String downcard() {
		try {
			if (applyAndBindCardDTO.getStartDate() == null || applyAndBindCardDTO.getStartDate().length()==0) {
				addActionError("请输入开始日期");
				return this.list();
			}
			if (applyAndBindCardDTO.getEndDate()== null || applyAndBindCardDTO.getEndDate().length()==0) {
				addActionError("请输入结束日期");
				return this.list();
			}
			if (applyAndBindCardDTO.getStartDate() != null) {
			String startDate = applyAndBindCardDTO.getStartDate();
			String str1 = startDate.replaceAll("-", "");
			applyAndBindCardDTO.setStartDate(str1);
			}
			if (applyAndBindCardDTO.getEndDate() != null) {
				String startDate = applyAndBindCardDTO.getEndDate();
				String str1 = startDate.replaceAll("-", "");
				applyAndBindCardDTO.setEndDate(str1);
				}
			if(applyAndBindCardDTO.getApplyStatus()!=null&&applyAndBindCardDTO.getApplyStatus().equals("")){
				applyAndBindCardDTO.setApplyStatus(null);
			}
			fileName=applyAndBindCardDTO.getStartDate()+"-"+applyAndBindCardDTO.getEndDate()+"maillMessage";
//			applyAndBindCardDTO.setStartDate("20150515");
			//applyAndBindCardDTO.setApplyStatus("1");
			applyAndBindCardDTO.setEntityId(this.getUser().getEntityId());
			applyAndBindCardDTO = (ApplyAndBindCardDTO) this.sendService(ConstCode.CARD_FILE_QUERY, applyAndBindCardDTO).getDetailvo();
			//fileName = "mailMessage" + DateUtil.getCurrentDateFormatStr("yyyyMMddhhmmss");
			if (hasErrors()) {
				return this.list();
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage());
		}
		return "downcard";
	}

	public void saveFileZip() throws Exception {
		HttpServletResponse response = getResponse();
		response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename="+ new String((fileName + ".xls").getBytes(), "iso-8859-1"));
        ServletOutputStream out = response.getOutputStream();
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        InputStream  is =null;
        try {
	    for (Entry<String, List<byte[]>> entry : applyAndBindCardDTO.getFileData().entrySet()) {
//       	 ZipEntry zipEntry = new ZipEntry(entry.getKey());
//	         zipos.putNextEntry(zipEntry);
       	List<byte[]> byteList = entry.getValue();
       	for(byte[] byteArr : byteList){
//       		zipos.write(byteArr);
       		is = new ByteArrayInputStream(byteArr);
       	}
       }
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(out);
            byte[] buff = new byte[2048];
            int bytesRead;
            // Simple read/write loop.
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (final IOException e) {
            throw e;
        } finally {
            if (bis != null)
                bis.close();
            if (bos != null)
                bos.close();
        }
//		 ZipOutputStream zipos = null;
//		try{
//			HttpServletResponse response = getResponse();
//	        response.setContentType("application/x-download");
//	       
//	        response.addHeader("Content-Disposition", "attachment;filename=" + fileName + ".xls");
//	        zipos = new ZipOutputStream(response.getOutputStream());
//	        for (Entry<String, List<byte[]>> entry : applyAndBindCardDTO.getFileData().entrySet()) {
//	        	 ZipEntry zipEntry = new ZipEntry(entry.getKey());
//		         zipos.putNextEntry(zipEntry);
//	        	List<byte[]> byteList = entry.getValue();
//	        	for(byte[] byteArr : byteList){
//	        		zipos.write(byteArr);
//	        	}
//	        }
//	        zipos.close();
//		} catch (Exception e) {
//			this.logger.error(e.getMessage());
//		} finally {
//			if (zipos != null) {
//				zipos.close();
//			}
//		}
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getBuyOrderFlag() {
		return buyOrderFlag;
	}

	public void setBuyOrderFlag(String buyOrderFlag) {
		this.buyOrderFlag = buyOrderFlag;
	}

	public ApplyAndBindCardDTO getApplyAndBindCardDTO() {
		return applyAndBindCardDTO;
	}

	public void setApplyAndBindCardDTO(ApplyAndBindCardDTO applyAndBindCardDTO) {
		this.applyAndBindCardDTO = applyAndBindCardDTO;
	}

	public List<?> getCardInfos() {
		return cardInfos;
	}

	public void setCardInfos(List<?> cardInfos) {
		this.cardInfos = cardInfos;
	}

	public int getCardInfo_totalRows() {
		return cardInfo_totalRows;
	}

	public void setCardInfo_totalRows(int cardInfo_totalRows) {
		this.cardInfo_totalRows = cardInfo_totalRows;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getActionMethodName() {
		return actionMethodName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public void setActionMethodName(String actionMethodName) {
		this.actionMethodName = actionMethodName;
	}
}

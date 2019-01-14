package com.huateng.univer.issuer.order;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.univer.seller.order.dto.SellOrderMakeCardDTO;
import com.huateng.framework.constant.OrderConst;
import com.huateng.framework.util.DateUtil;

/**
 * 制卡订单 制卡文件下载 action
 * 
 * @author xxl
 * 
 */
public class StockOrderMakeCardMakeAction extends StockOrderBaseAction {

	private Logger logger = Logger.getLogger(StockOrderMakeCardMakeAction.class);
	private static final long serialVersionUID = -4757093228225053512L;

	private String fileName;
	
	protected void initActionName() {
		actionName = "stockOrderMakeCardMakeAction";
	}
	
	protected void inqueryInit() {
		// 订单类型： 制卡订单+下级机构记名采购订单
		sellOrderQueryDTO
				.setOrderTypeArray(OrderConst.ORDER_TYPE_ORDER_MAKE_CARD + ","
						+ OrderConst.ORDER_TYPE_ORDER_BUY_ISSUER_ISSUER_SIGN + ","
						+ OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_SIGN);
		// 订单状态：6制卡文件待下载
		sellOrderQueryDTO
				.setOrderState(OrderConst.ORDER_STATE_CARDGFILE_MAKING);

	}

	/**
	 * 发送报文下载制卡文件
	 */
	public String makeCardFile() throws Exception {
		try {
			sellOrderMakeCardDTO.setOrderId(sellOrderDTO.getOrderId());
			sellOrderMakeCardDTO = (SellOrderMakeCardDTO) this.sendService(
					ConstCode.MAKECARD_FILE_MAKE, sellOrderMakeCardDTO)
					.getDetailvo();
			fileName = "MakeCardFile" + sellOrderDTO.getOrderId()
					+ DateUtil.getCurrentDateFormatStr("yyyyMMddhhmmss");
			 
			if(hasErrors()){
				return this.list();
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}

		return "makeCardFile";
	}
	
	public String cardPINFile() throws Exception {
		try {
			sellOrderMakeCardDTO.setOrderId(sellOrderDTO.getOrderId());
			sellOrderMakeCardDTO = (SellOrderMakeCardDTO) this.sendService(
					ConstCode.MAKECARD_PIN_FILE_MAKE, sellOrderMakeCardDTO)
					.getDetailvo();
			fileName = "CardPINFile" + sellOrderDTO.getOrderId()
					+ DateUtil.getCurrentDateFormatStr("yyyyMMddhhmmss")
					+ ".txt";

			if(hasActionErrors()){
				return this.list();
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}

		return "cardPINFile";
	}
	
	/**
	 * 保存文件
	 * 
	 * @throws Exception
	 */
	public void saveFile() throws Exception {
		 ZipOutputStream zipos = null;
		try{
			HttpServletResponse response = getResponse();
	        response.setContentType("application/x-download");
	       
	        response.addHeader("Content-Disposition", "attachment;filename=" + fileName + ".zip");
	        zipos = new ZipOutputStream(response.getOutputStream());
	        for (Entry<String, List<byte[]>> entry : sellOrderMakeCardDTO.getFileData().entrySet()) {
	        	 ZipEntry zipEntry = new ZipEntry(entry.getKey());
		         zipos.putNextEntry(zipEntry);
	        	List<byte[]> byteList = entry.getValue();
	        	for(byte[] byteArr : byteList){
	        		zipos.write(byteArr);
	        	}
	        }
	        zipos.close();
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		} finally {
			if (zipos != null) {
				zipos.close();
			}
		}
	}
	
	public void saveFile2() throws Exception{
		HttpServletResponse response = getResponse();
		response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename="+ new String((fileName + ".zip").getBytes()));
        ServletOutputStream out = response.getOutputStream();
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        InputStream  is =null;
        try {
	    for (Entry<String, List<byte[]>> entry : sellOrderMakeCardDTO.getFileData().entrySet()) {
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
		       
		
	}
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}

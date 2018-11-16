package com.huateng.struts.error;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.nio.charset.Charset;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

import com.huateng.bo.error.T100201BO;
import com.huateng.common.Constants;
import com.huateng.common.SysParamConstants;
import com.huateng.po.error.TblChangeAccInf;
import com.huateng.po.error.TblChangeAccInfId;
import com.huateng.po.error.TblChangeAccInfTmp;
import com.huateng.po.error.TblChangeAccInfTmpId;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.SysParamUtil;

public class T20042Action extends BaseAction {
	 
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(T20042Action.class);
	private T100201BO t100201BO = (T100201BO) ContextUtil.getBean("T100201BO");
	Socket socket = null;
	OutputStream outputStream = null;
	InputStream inputstream = null;
	String sales_ip=SysParamUtil.getParam(SysParamConstants.SALES_IP);
	String sales_port=SysParamUtil.getParam(SysParamConstants.SALES_PORT);
	protected String subExecute() throws Exception {
		try {
			if("refuse".equals(getMethod())) {			
				rspCode = refuse();			
			} else if("accept".equals(getMethod())) {
				rspCode = accept();
			} 
		} catch (Exception e) {
			e.printStackTrace();
			log.info("操作员编号：" + operator.getOprId()+ ",线上退货" + getMethod() + "失败，失败原因为："+e.getMessage());
		}
		return rspCode;
	}
	//拒绝
	private String refuse() {
		log.info("refuse操作："+ operator.getOprId());
		jsonBean.parseJSONArrayData(getInfList());
		int len = jsonBean.getArray().size();
		for (int i = 0; i < len; i++) {
			String orderNo = jsonBean.getJSONDataAt(i).getString("orderNo");
			String returnOrderno = jsonBean.getJSONDataAt(i).getString("returnOrderno");//原交易订单号
			String returnAmt = jsonBean.getJSONDataAt(i).getString("returnAmt");
			String pan = jsonBean.getJSONDataAt(i).getString("pan");
			String orderNoSql;
			orderNoSql= "select order_No from tbl_xs_n_txn where trim(order_No) = '" + returnOrderno + "'";
			String orderNoStr = commQueryDAO.findCountBySQLQuery(orderNoSql);
			if (orderNoStr.equals("")) {
				orderNoSql= "select order_No from tbl_xs_n_txn_his where trim(order_No) = '" + returnOrderno + "'";
				orderNoStr = commQueryDAO.findCountBySQLQuery(orderNoSql);
				String upAmtApply = "update tbl_xs_n_txn_his set amt_apply = trim(amt_apply) - '" + returnAmt + "' where trim(order_No) = '" + returnOrderno + "'";
				commQueryDAO.excute(upAmtApply);
			}else{
				String upAmtApply = "update tbl_xs_n_txn set amt_apply = trim(amt_apply) - '" + returnAmt + "' where trim(order_No) = '" + returnOrderno + "'";
				commQueryDAO.excute(upAmtApply);
			}
			/*StringBuilder builder = new StringBuilder();
			builder.append("<?xml version=\"1.0\" encoding=\"GBK\"?><DATA><timestamp>");
			builder.append(CommonFunction.getCurrentDateTime());
			builder.append("</timestamp><txn_num>");
			builder.append("</timestamp><txn_num>5201</txn_num><out_trade_no>");
			builder.append(orderNo + "</out_trade_no><out_refund_no>");
			builder.append(returnOrderno + "</out_refund_no><refund_amount>");
			builder.append(returnAmt + "</refund_amount><card_no>");
			builder.append(pan + "</card_no></DATA>");
			byte[] bytes = null;
			try {
				bytes = new String(builder).getBytes("GBK");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			String codeAddOne = codeAddOne(bytes.toString().length() + "", 4);
			System.out.println("refuse: "+ codeAddOne + "XMLMODEL"+ bytes.toString());*/
			String sqlStr = "update TBL_XS_RETURN_INF set return_flag = '3',pay_date = '"+ CommonFunction.getCurrentDateTime() +"', misc_1 = '审核拒绝', misc_5 = '4' where order_no = '"+ orderNo +"' and pan = '" + pan + "'";
			
        	try {
        		commQueryDAO.excute(sqlStr);
        	} catch (Exception e) {
        		log.error("更新失败："+e.getMessage());
        		e.printStackTrace();
        		return "审核拒绝失败";
        	}
		}
		return Constants.SUCCESS_CODE;
	}
	//通过
	private String accept() {
		log("accept操作："+ operator.getOprId());
		String xsIp = SysParamUtil.getParam(SysParamConstants.XSIP);//线上退货ip
		String xsPort = SysParamUtil.getParam(SysParamConstants.XSPORT);//线上退货端口
		jsonBean.parseJSONArrayData(getInfList());
		int len = jsonBean.getArray().size();
		String msg="";
		for (int i = 0; i < len; i++) {
			String orderNo = jsonBean.getJSONDataAt(i).getString("orderNo");
			String returnOrderno = jsonBean.getJSONDataAt(i).getString("returnOrderno");
			String returnAmt = jsonBean.getJSONDataAt(i).getString("returnAmt");
			String pan = jsonBean.getJSONDataAt(i).getString("pan");
			String mchtId = jsonBean.getJSONDataAt(i).getString("mchtId");
    		String termId = jsonBean.getJSONDataAt(i).getString("termId");
    		
			StringBuilder builder = new StringBuilder();
			builder.append("<?xml version=\"1.0\" encoding=\"GBK\"?><DATA><timestamp>");
			builder.append(CommonFunction.getCurrentDateTime());
			builder.append("</timestamp><txn_num>5201</txn_num><out_trade_no>");
			builder.append( returnOrderno + "</out_trade_no><out_refund_no>");
			builder.append( orderNo + "</out_refund_no><refund_amount>");
			builder.append(returnAmt + "</refund_amount><card_no>");
			builder.append(pan + "</card_no></DATA>");
			String xml ="XMLMODEL" + builder.toString();
			byte[] bytes = null;
			try {
				bytes = xml.getBytes("GBK");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			String codeAddOne = codeAddOne(bytes.length + "", 4);
			log.info("报文accept: "+ codeAddOne + xml);
			try {
				int parseInt = Integer.parseInt(xsPort);
				String strData = codeAddOne + xml;
				log.info("连接地址："+ xsIp + ":" + parseInt);
				socket = new Socket(xsIp, parseInt);
				socket.setSoTimeout(50000);
				outputStream = socket.getOutputStream();
				PrintWriter pw = new PrintWriter(outputStream);
				pw.write(new String(strData.getBytes(),"GBK"));
				pw.flush();
				InputStream is = socket.getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName("GBK")));
				char[] ch = new char[4];
				br.read(ch, 0, 4);
				String string = String.valueOf(ch);
				log.info("接收的前四个字节" + string);
				int parseInt1 = Integer.parseInt(string);
				char[] ch1 = new char[parseInt1];
				br.read(ch1, 0, parseInt1);
				String string1;
				string1 = String.valueOf(ch1).trim();
				log.info("接收的整个报文内容" + string1);
//				String readLine = br.readLine();
				
				/*outputStream.write(strData.getBytes("GBK")); 
				outputStream.flush();
				InputStream is = socket.getInputStream(); 
		        ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();  
		        byte[] buffer = new byte[1024]; 
		        int len1 = -1;  
	            while((len1=is.read(buffer)) != -1){  
	                bytesOut.write(buffer, 0, len);  
	            } */ 
	            if(!socket.isClosed())	{
	    			socket.shutdownInput();
	    			socket.shutdownOutput();
	    			socket.close();
	    		}
//	            String respData = bytesOut.toString();  
//	            String dataStr = string1.replace("GBK", "UTF-8");
//	            log.info("修改后的整个报文内容" + dataStr);
	            StringReader read = new StringReader(string1);
	            InputSource source = new InputSource(read);
	    		SAXBuilder sb=new SAXBuilder();
	    		Document doc = sb.build(source);
	    		Element root = doc.getRootElement();
	    		System.out.println(root.getName());
	    		Namespace ns = root.getNamespace();
	            String result =  root.getChild("result",ns).getText();
	            String resp_code =  root.getChild("resp_code",ns).getText();
	            msg =  root.getChild("msg",ns).getText();
	            String refund_amount =  root.getChild("refund_amount",ns).getText();
	            String channel =  root.getChild("channel",ns).getText();
	            log.info("获取返回结果：" + "result :" + result + " resp_code :" + resp_code + " msg :" + msg);
	            if (result.equals("true")) {
	            	result = "1";
				}else{
					result = "2";
				}
	            if (resp_code.equals("00")) {
	            	String sqlStr = null;
	            	if (result.equals("1")) {
	            		sqlStr = "update TBL_XS_RETURN_INF set return_flag = '" + result + "',pay_date = '"+ CommonFunction.getCurrentDateTime() +"', resp_code = '" + resp_code + "', misc_1 = '"+ msg +"', misc_5 = '1',opr_Id = '"+ operator.getOprId() + "' where order_no = '"+ orderNo +"' and pan = '"+ pan +"'";
	            		log.info("更新TBL_XS_RETURN_INFSQL："+sqlStr);
	            		commQueryDAO.excute(sqlStr);
	            		String instCode = null;
	            		try {
	            			String instCodeSql = "select dest_inst from TBL_XS_RETURN_INF where order_no = '"+ orderNo +"' and pan = '"+ pan +"'";
	            			instCode = commQueryDAO.findCountBySQLQuery(instCodeSql);
	            			log.info("查询INSTCODESQL："+instCodeSql);
	            		} catch (Exception e) {
	            			log.error("查询INSTCODESQL："+e.getMessage());
	            		}
	            		try {
		            		TblChangeAccInfTmp tblChangeAccInfTmp = new TblChangeAccInfTmp();
		            		TblChangeAccInfTmpId tblChangeAccInfTmpId = new TblChangeAccInfTmpId();
		            		try {
		            			tblChangeAccInfTmpId.setMchtNo(mchtId);
								tblChangeAccInfTmpId.setTermId(termId);
								tblChangeAccInfTmpId.setChangeDate(CommonFunction.getCurrentDateTime());
								tblChangeAccInfTmp.setId(tblChangeAccInfTmpId);
								tblChangeAccInfTmp.setChangeFlag("0");// 调账状态
								tblChangeAccInfTmp.setChangeReason("线上退货延迟结算完成，调回退款金额");// 调账理由*
								tblChangeAccInfTmp.setChangeAccount(Double.valueOf(returnAmt )*100);
								tblChangeAccInfTmp.setSt("0");// 状态
								tblChangeAccInfTmp.setInsTs(CommonFunction.getCurrentDateTime());
								tblChangeAccInfTmp.setEnterTp("0");// 录入
								tblChangeAccInfTmp.setComfirmAccount(Double.valueOf(0));
								tblChangeAccInfTmp.setInsOpr(operator.getOprId());
								tblChangeAccInfTmp.setUpdTs(CommonFunction.getCurrentDateTime());
								tblChangeAccInfTmp.setUpdOpr(operator.getOprId());
								tblChangeAccInfTmp.setInstCode(instCode);
								tblChangeAccInfTmp.setChannelId(channel);
								tblChangeAccInfTmp.setOnlineFlag("1");
								t100201BO.addone(tblChangeAccInfTmp);
							} catch (Exception e) {
								log.error("更新失败tblChangeAccInfTmp："+e.getMessage());
							}
		            		try {
								TblChangeAccInf tblChangeAccInf = new TblChangeAccInf();
								TblChangeAccInfId tblChangeAccInfId = new TblChangeAccInfId();
								tblChangeAccInfId.setMchtNo(mchtId);
								tblChangeAccInfId.setTermId(termId);
								tblChangeAccInfId.setChangeDate(CommonFunction.getCurrentDateTime());
								tblChangeAccInf.setId(tblChangeAccInfId);
								tblChangeAccInf.setChangeAccount(tblChangeAccInfTmp.getChangeAccount());
								tblChangeAccInf.setChangeReason(tblChangeAccInfTmp.getChangeReason());
								tblChangeAccInf.setInstCode(tblChangeAccInfTmp.getInstCode());
								tblChangeAccInf.setChangeFlag("0");
								tblChangeAccInf.setAprTs(CommonFunction.getCurrentDateTime());
								tblChangeAccInf.setAprOpr(operator.getOprId());
								tblChangeAccInf.setInsOpr(tblChangeAccInfTmp.getInsOpr());
								tblChangeAccInf.setInsTs(tblChangeAccInfTmp.getInsTs());
								tblChangeAccInf.setUpdOpr(tblChangeAccInfTmp.getUpdOpr());
								tblChangeAccInf.setUpdTs(tblChangeAccInfTmp.getUpdTs());
								tblChangeAccInf.setSt("0");
								tblChangeAccInf.setInstCode(instCode);
								tblChangeAccInf.setEnterTp(tblChangeAccInfTmp.getEnterTp());
								tblChangeAccInf.setChannelId(channel);
								tblChangeAccInf.setOnlineFlag("1");
								t100201BO.saveOrUpdate(tblChangeAccInf);
//								return Constants.SUCCESS_CODE + msg;
							} catch (Exception e) {
								log.error("正式表更新失败TblChangeAccInf："+e.getMessage());
							}
		            	} catch (Exception e) {
		            		log.error("更新失败："+e.getMessage());
		            		e.printStackTrace();
		            		return "审核通过前台失败，请联系管理员";
		            	}
					}
	            	
				}else{
					String sqlStr  = null;
					if (result.equals("1") && !resp_code.equals("00")) {
						sqlStr = "update TBL_XS_RETURN_INF set return_flag = '" + result + "', pay_date = '"+ CommonFunction.getCurrentDateTime() +"',resp_code = '" + resp_code + "', misc_1 = '" + msg + "', misc_5 = '2', opr_Id = '"+ operator.getOprId() + "' where order_no = '"+ orderNo +"' and pan = '"+ pan +"'";
					}else if(result.equals("2")){
						sqlStr = "update TBL_XS_RETURN_INF set return_flag = '" + result + "', pay_date = '"+ CommonFunction.getCurrentDateTime() +"',resp_code = '" + resp_code + "', misc_1 = '" + msg + "', misc_5 = '3', opr_Id = '"+ operator.getOprId() + "' where order_no = '"+ orderNo +"' and pan = '"+ pan +"'";
					}
					log.info("更新SQL："+sqlStr);
					try {
						commQueryDAO.excute(sqlStr);
					} catch (Exception e) {
						log.error("更新失败："+e.getMessage());
						e.printStackTrace();
						return "审核通过前台失败，请联系管理员";
					}
				}
			} catch (Exception e) {
				log.error("accept连接失败："+e.getMessage());
				if(!socket.isClosed())	{
	    			try {
						socket.shutdownInput();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
	    			try {
						socket.shutdownOutput();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
	    			try {
						socket.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
	    		}
				e.printStackTrace();
				return "审核失败";
			}
			
		}
		return msg;
	}

	private String infList;
	
	public String getInfList() {
		return infList;
	}

	public void setInfList(String infList) {
		this.infList = infList;
	}

	 /**
     * 字符左补零
     * @param code
     * @param len
     * @return
     */
    public static String codeAddOne(String code, int len){
        Integer intHao = Integer.parseInt(code);
//        intHao++;
        String strHao = intHao.toString();
        while (strHao.length() < len) {
            strHao = "0" + strHao;
        }
        return strHao;
    }

}

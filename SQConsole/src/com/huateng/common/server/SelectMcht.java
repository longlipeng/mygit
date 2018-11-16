package com.huateng.common.server;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import com.huateng.common.XmlObject.DuXMLDoc;
import com.huateng.common.XmlObject.XmlConverUtil;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.GenerateNextId;

public class SelectMcht {
	@SuppressWarnings("unchecked")
	public String  SelectMchtAll(String param){
		  HashMap<String, String> map = new LinkedHashMap<String, String>();
	      
	      DuXMLDoc doc = new DuXMLDoc();
	      
	      @SuppressWarnings("unused")
		String testString ="<Comm>"+
							"<Txn_Num>9008</Txn_Num>"+
							"<Ips_Mcht_Code>310142254990407</Ips_Mcht_Code>"+
							"<Term_Id>68000360</Term_Id>"+
							"<Begin_Date></Begin_Date>"+
							"<End_Date></End_Date>"+
							"<ReFe_Num>207323028410</ReFe_Num>"+
							"<Order_TrsNum>000085</Order_TrsNum>"+
							"<Txn_Type>P</Txn_Type>"+
							"<Txn_State>6</Txn_State>"+
							"<Pan>4026746271376865</Pan>"+
							"<Min_Amount>1100</Min_Amount>"+
							"<Max_Amount>1300</Max_Amount>"+
							"<Page_Num>0</Page_Num>"+
							"<Count>5</Count>"+
							"</Comm>";
	      
	      map = doc.TradeXml(param,"allData");
	      
	      String Ips_Mcht_Code="";
	      String page_num = "0"; //当前页面显示条数
	      String sum="0";
	      List list = null;
	      if(map!=null){
	    	Ips_Mcht_Code = map.get("Ips_Mcht_Code");//IPS商户号  
	    	sum = GenerateNextId.getTradeCount(map);//查询数据总条数
	    	list = GenerateNextId.getTrade(map);//查询数据内容
	      }

	      String msgcode ="00";
	      String msg = "查询成功";
	      if(list!=null){
	    	  page_num = ""+list.size();
	      }
//	      if(list==null || list.size()==0){
//	    	  msgcode = "01";
//	    	  msg = "没有查询到数据";
//	      }
	      StringBuffer strBuff = new StringBuffer();
	      String xmlhead = "<?xml version=\"1.0\" encoding =\"UTF-8\" ?>";
	      String xmlurl ="<Comm>"+
	  			"<Pub>"+
			      "<Txn_Num>9008</Txn_Num>"+
			      "<Rsp_Code>"+msgcode+"</Rsp_Code>"+
			      "<Rsp_Describe>"+msg+"</Rsp_Describe>"+
			      "<Ips_Mcht_Code>"+Ips_Mcht_Code+"</Ips_Mcht_Code>"+
			      "<Card_Accp_Id>000000000000331</Card_Accp_Id>"+
			      "<Sum>"+sum+"</Sum>"+
			      "<Page_Num>"+page_num+"</Page_Num>"+
			    "</Pub>";
	      strBuff.append(xmlhead);
	      strBuff.append(xmlurl);
	        for(int i=0;i<list.size();i++){
	        	Map xmlMap = new HashMap();
	        	Object[] obj=(Object[])list.get(i);
	        	xmlMap.put("Ips_Mcht_Code",obj[8]);
	        	xmlMap.put("Term_Id",obj[0]);
	        	xmlMap.put("Inst_Date",obj[1]);
	        	xmlMap.put("Txn_Type",obj[2]);
	        	xmlMap.put("Cup_Ssn",obj[3]);
	        	xmlMap.put("ReFe_Num",obj[4]);
	        	xmlMap.put("Order_TrsNum",obj[5]);
	        	
	        	if("1".equals(obj[11])){ //将冲正类原交易的状态置为失败
	        		xmlMap.put("Txn_State", "2");
	        	}else if("1".equals(obj[10])){//已被撤销的原交易，状态置为 撤销状态
	        		xmlMap.put("Txn_State", "3");
	        	}else if("00".equals(obj[6])){
	        		xmlMap.put("Txn_State", "1");
	        	}else{
	        		xmlMap.put("Txn_State", "2");
	        	}
	        	/*else if(obj[6].equals("R")){//将部分退货的原交易重置为成功
	        		xmlMap.put("Txn_State", "1");
	        	}else
	        		xmlMap.put("Txn_State",obj[6]);*/
	        	xmlMap.put("Pan",CommonFunction.cardReplace(obj[7]));
	        	xmlMap.put("Trs_Amount",obj[9]);

	        	String xmlUtil = XmlConverUtil.maptoXml(xmlMap,"Out");
	        	strBuff.append(xmlUtil.replace("\n",""));

	        }
	      strBuff.append("</Comm>");
		return strBuff.toString();
	}
	
	@SuppressWarnings("unchecked")
	public String SelectMchtDetail(String param){
		  HashMap<String, String> map = new LinkedHashMap<String, String>();
		  DuXMLDoc doc = new DuXMLDoc();
		    
		    String testString ="<Comm>"+
							    "<Txn_Num>9009</Txn_Num>"+
							    "<Rsp_Code>00</Rsp_Code>"+
							    "<Rsp_Describe>查询成功</Rsp_Describe>"+
							    "<Ips_Mcht_Code></Ips_Mcht_Code>"+
							    "<Card_Accp_Id></Card_Accp_Id>"+
							    "<Term_Id></Term_Id>"+
							    "<Inst_Date></Inst_Date>"+
							    "<Updt_Date></Updt_Date>"+
							    "<Txn_Type></Txn_Type>"+
							    "<Cup_Ssn></Cup_Ssn>"+
							    "<Sys_Seq_Num></Sys_Seq_Num>"+
							    "<Term_Ssn></Term_Ssn>"+
							    "<ReFe_Num></ReFe_Num>"+
							    "<Order_TrsNum></Order_TrsNum>"+
							    "<Txn_State></Txn_State>"+
							    "<Revsal_Flag></Revsal_Flag>"+
							    "<Revsal_Ssn></Revsal_Ssn>"+
							    "<Cancel_Flag></Cancel_Flag>"+
							    "<Cancel_Ssn></Cancel_Ssn>"+
							    "<Pan></Pan>"+
							    "<Trs_Amount></Trs_Amount>"+
							    "<Date_Settle></Date_Settle>"+
							    "<Authr_Id_Resp></Authr_Id_Resp>"+
							    "<Iss_Code></Iss_Code>"+
							    "<Tlr_Num></Tlr_Num>"+
							    "</Comm>";
		    
		    map = doc.TradeXml(param,"onlyData");
		    String Ips_Mcht_Code="";
		    @SuppressWarnings("unused")
			String page_num = "0"; //当前页面显示条数
		    @SuppressWarnings("unused")
			String sum="0";
		    List<Object[]> list = null;
		    if(map!=null){
		  	Ips_Mcht_Code = map.get("Ips_Mcht_Code");//IPS商户号  
		  	list = GenerateNextId.getTradeAll(map);//查询数据内容
		    }

		    @SuppressWarnings("unused")
			String msgcode ="00";
		    @SuppressWarnings("unused")
		    String msg = "查询成功";
		    if(list!=null){
		  	  page_num = ""+list.size();
		    }
//		    if(list==null || list.size()==0){
//		  	  msgcode = "01";
//		  	  msg = "没有查询到数据";
//		    }
		    StringBuffer strBuff = new StringBuffer();
		    String xmlhead = "<?xml version=\"1.0\" encoding =\"UTF-8\" ?>";
		    strBuff.append(xmlhead);
		    if(list.size()>=1){
		      	Map xmlMap = new HashMap();
		      	Object[] xmlModel=list.get(0);
		      	xmlMap.put("Txn_Num","9009");
		      	xmlMap.put("Rsp_Code","00");
		      	xmlMap.put("Rsp_Describe","查询成功");
		      	xmlMap.put("Ips_Mcht_Code",Ips_Mcht_Code);
		      	xmlMap.put("Card_Accp_Id",xmlModel[0]);
		      	xmlMap.put("Term_Id",xmlModel[1]);
		      	xmlMap.put("Inst_Date",xmlModel[2]);
		      	xmlMap.put("Updt_Date",xmlModel[3]);
		      	xmlMap.put("Txn_Type",xmlModel[4]);
		      	xmlMap.put("Cup_Ssn",xmlModel[5]);
		    	xmlMap.put("Sys_Seq_Num",xmlModel[6]);
		    	xmlMap.put("Term_Ssn",xmlModel[7]);
		      	xmlMap.put("ReFe_Num",xmlModel[8]);
		      	xmlMap.put("Order_TrsNum",xmlModel[9]);
		      	if("1".equals(xmlModel[11])){//将冲正的原交易置为失败
		      		xmlMap.put("Txn_State", "2");
		      	}else if("1".equals(xmlModel[13])){//将撤销的原交易置为撤销状态
		      		xmlMap.put("Txn_State", "3");
		      	}else if("00".equals(xmlModel[10])){
	        		xmlMap.put("Txn_State", "1");
	        	}else{
	        		xmlMap.put("Txn_State", "2");
	        	}
	        	/*else if(xmlModel[10].equals("R")){//将部分退货的原交易重置为成功
	        		xmlMap.put("Txn_State", "1");
	        	}else
	        		xmlMap.put("Txn_State",xmlModel[10]);*/
		      //	xmlMap.put("Txn_State",xmlModel[10]);
		      	xmlMap.put("Revsal_Flag",xmlModel[11]);
		      	xmlMap.put("Revsal_Ssn",xmlModel[12]);
		      	xmlMap.put("Cancel_Flag",xmlModel[13]);
		      	xmlMap.put("Cancel_Ssn",xmlModel[14]);
		      	xmlMap.put("Pan",CommonFunction.cardReplace(xmlModel[15]));
		      	xmlMap.put("Trs_Amount",xmlModel[16]);
		      	xmlMap.put("Date_Settle",xmlModel[18]);
		      	xmlMap.put("Authr_Id_Resp",xmlModel[19]);
		      	xmlMap.put("Iss_Code",xmlModel[17]);
		      	xmlMap.put("Tlr_Num",xmlModel[20]);

		      	String xmlUtil = XmlConverUtil.maptoXml(xmlMap,"Comm");
		      	strBuff.append(xmlUtil.replace("\n",""));
		    }else{
		    	strBuff.append(testString);
		    }
		   System.out.println(strBuff.toString());
		return strBuff.toString();
	}

}

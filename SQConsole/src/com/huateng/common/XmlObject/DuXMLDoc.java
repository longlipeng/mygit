package com.huateng.common.XmlObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

import com.huateng.common.StringUtil;
import com.thoughtworks.xstream.XStream;


import java.lang.reflect.Field;
import java.lang.reflect.Method;




public class DuXMLDoc {
	
	/**
	 *  查询清算信息
	 * 
	 * @return 2012-3-5 上午10:21:38 worden.zou
	 */
	
	 public HashMap<String, String> TradeXml(String xmlDoc,String secType) {
	    	HashMap<String, String> map = new LinkedHashMap<String, String>();
	        //创建一个新的字符串
	        StringReader read = new StringReader(xmlDoc);
	        //创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
	        InputSource source = new InputSource(read);
	        //创建一个新的SAXBuilder
	        SAXBuilder sb = new SAXBuilder();
	        try {
	            //通过输入源构造一个Document
	            Document doc = sb.build(source);
	            //取的根元素
	            Element root = doc.getRootElement();
	            System.out.println(root.getName());//输出根元素的名称（测试）
	            //得到根元素所有子元素的集合
	            List jiedian = root.getChildren();
	            //获得XML中的命名空间（XML中未定义可不写）
	            Namespace ns = root.getNamespace();
	            Element et = null;
	                et = (Element) jiedian.get(0);//循环依次得到子元素
	                /**//*
	                 * 无命名空间定义时
	                 * et.getChild("users_id").getText();
	                 * et.getChild("users_address",ns).getText()
	                 * 得到报文头里元素的值
	                 */
	                if(secType.equals("allData")){//9008 
	                map.put("Txn_Num", StringUtil.replace(root.getChild("Txn_Num",ns).getText(),"'","''"));//交易码
	                map.put("Ips_Mcht_Code", StringUtil.replace(root.getChild("Ips_Mcht_Code",ns).getText(),"'","''"));//商户号
	                map.put("Term_Id", StringUtil.replace(root.getChild("Term_Id",ns).getText(),"'","''"));//终端号
	                map.put("Begin_Date", StringUtil.replace(root.getChild("Begin_Date",ns).getText(),"'","''"));//交易起始日期
	                map.put("End_Date", StringUtil.replace(root.getChild("End_Date",ns).getText(),"'","''"));//交易终止日期
	                map.put("ReFe_Num", StringUtil.replace(root.getChild("ReFe_Num",ns).getText(),"'","''"));//交易参考号
	                map.put("Order_TrsNum", StringUtil.replace(root.getChild("Order_TrsNum",ns).getText(),"'","''"));//商户订单号
	                map.put("Txn_Type", StringUtil.replace(root.getChild("Txn_Type",ns).getText(),"'","''"));//交易类型
	                map.put("Txn_State", StringUtil.replace(root.getChild("Txn_State",ns).getText(),"'","''"));//交易状态
	                map.put("Pan", StringUtil.replace(root.getChild("Pan",ns).getText(),"'","''"));//卡号
	                map.put("Min_Amount", StringUtil.replace(root.getChild("Min_Amount",ns).getText(),"'","''"));//交易金额最小值
	                map.put("Max_Amount", StringUtil.replace(root.getChild("Max_Amount",ns).getText(),"'","''"));//交易金额最大值
	                map.put("Page_Num", StringUtil.replace(root.getChild("Page_Num",ns).getText(),"'","''"));//显示页面
	                map.put("Count", StringUtil.replace(root.getChild("Count",ns).getText(),"'","''"));//每页显示条数
	                map.put("Cup_Ssn", StringUtil.replace(root.getChild("Cup_Ssn",ns).getText(),"'","''"));//银联交易流水号
	                }	                              
	                else if(secType.equals("onlyData")){//9009
	                	map.put("Txn_Num", StringUtil.replace(root.getChild("Txn_Num",ns).getText(),"'","''"));//交易码
	                	map.put("Ips_Mcht_Code", StringUtil.replace(root.getChild("Ips_Mcht_Code",ns).getText(),"'","''"));//商户号
	                	map.put("Term_Id", StringUtil.replace(root.getChild("Term_Id",ns).getText(),"'","''"));//终端号
	                	map.put("Inst_Date", StringUtil.replace(root.getChild("Inst_Date",ns).getText(),"'","''"));//记录产生日期
		                map.put("ReFe_Num", StringUtil.replace(root.getChild("ReFe_Num",ns).getText(),"'","''"));//交易参考号
		                map.put("Txn_Type", StringUtil.replace(root.getChild("Txn_Type",ns).getText(),"'","''"));//交易类型
		                map.put("Pan", StringUtil.replace(root.getChild("Pan",ns).getText(),"'","''"));//卡号
	                }
	                else if(secType.equals("splitSearch")){
	                	 map.put("Txn_Num", StringUtil.replace(root.getChild("Txn_Num",ns).getText(),"'","''"));//交易码
	                }
	               

	        } catch (JDOMException e) {
	            // TODO 自动生成 catch 块
	            e.printStackTrace();
	            return null;
	        } catch (IOException e) {
	            // TODO 自动生成 catch 块
	            e.printStackTrace();
	            return null;
	        }
	        return map;
	    }
	 
	 /**
		 *  新增pos
		 * 
		 * @return 2012-3-5 上午10:21:38 worden.zou
		 */
	  public HashMap<String, String> posAddXml(String xmlDoc) {
	    	HashMap<String, String> map = new LinkedHashMap<String, String>();
	        //创建一个新的字符串
	        StringReader read = new StringReader(xmlDoc);
	        //创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
	        InputSource source = new InputSource(read);
	        //创建一个新的SAXBuilder
	        SAXBuilder sb = new SAXBuilder();
	        try {
	            //通过输入源构造一个Document
	            Document doc = sb.build(source);
	            //取的根元素
	            Element root = doc.getRootElement();
	            System.out.println(root.getName());//输出根元素的名称（测试）
	            //得到根元素所有子元素的集合
	            List jiedian = root.getChildren();
	            //获得XML中的命名空间（XML中未定义可不写）
	            Namespace ns = root.getNamespace();
	            Element et = null;
	                et = (Element) jiedian.get(0);//循环依次得到子元素
	                /**//*
	                 * 无命名空间定义时
	                 * et.getChild("users_id").getText();
	                 * et.getChild("users_address",ns).getText()
	                 * 得到报文头里元素的值
	                 */
	                map.put("trancode", et.getChild("trancode",ns).getText());//交易日期
	                map.put("terminal", et.getChild("terminal",ns).getText());//终端机器名
	                map.put("serseq", et.getChild("serseq",ns).getText());//请求流水号
	                map.put("senddate", et.getChild("senddate",ns).getText());//发送日期
	                map.put("sendtime", et.getChild("sendtime",ns).getText());//发送时间
	                map.put("md5", et.getChild("md5",ns).getText());//校验位
	                map.put("result", et.getChild("msgcode",ns).getText());//响应码
	                map.put("msg", et.getChild("msg",ns).getText());//响应内容
	               
	                //得到报文体里元素的值

	        } catch (JDOMException e) {
	            // TODO 自动生成 catch 块
	            e.printStackTrace();
	        } catch (IOException e) {
	            // TODO 自动生成 catch 块
	            e.printStackTrace();
	        }
	        return map;
	    }
	  
	  /**
		 *  商户号验证
		 * 
		 * @return 2012-3-5 上午10:21:38 worden.zou
		 */
    public HashMap<String, String> xmlElements(String xmlDoc) {
    	HashMap<String, String> map = new LinkedHashMap<String, String>();
        //创建一个新的字符串
        StringReader read = new StringReader(xmlDoc);
        //创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
        InputSource source = new InputSource(read);
        //创建一个新的SAXBuilder
        SAXBuilder sb = new SAXBuilder();
        try {
            //通过输入源构造一个Document
            Document doc = sb.build(source);
            //取的根元素
            Element root = doc.getRootElement();
//            System.out.println(root.getName());//输出根元素的名称（测试）
            //得到根元素所有子元素的集合
            List jiedian = root.getChildren();
            //获得XML中的命名空间（XML中未定义可不写）
            Namespace ns = root.getNamespace();
            Element et = null;
//            for(int i=0;i<jiedian.size();i++){
                et = (Element) jiedian.get(0);//循环依次得到子元素
                /**//*
                 * 无命名空间定义时
                 * et.getChild("users_id").getText();
                 * et.getChild("users_address",ns).getText()
                 * 得到报文头里元素的值
                 */
                map.put("trancode", et.getChild("trancode",ns).getText());//交易日期
                map.put("terminal", et.getChild("terminal",ns).getText());//终端机器名
                map.put("serseq", et.getChild("serseq",ns).getText());//请求流水号
                map.put("senddate", et.getChild("senddate",ns).getText());//发送日期
                map.put("sendtime", et.getChild("sendtime",ns).getText());//发送时间
                map.put("md5", et.getChild("md5",ns).getText());//校验位
                map.put("result", et.getChild("msgcode",ns).getText());//响应码
                map.put("msg", et.getChild("msg",ns).getText());//响应内容
               
                //得到报文体里元素的值
                et = (Element) jiedian.get(1);//循环依次得到子元素
                
                map.put("mchtNo", et.getChild("cusid",ns).getText());//商户号
                map.put("cusKey", et.getChild("cuskey",ns).getText());//商户号校验码
                map.put("settleBankNo", et.getChild("bankcd",ns).getText());//开户行代码
                map.put("settleBankNm", et.getChild("banknm",ns).getText());//开户行名称
                map.put("settleAcct", et.getChild("inacctno",ns).getText());//商户收入账号
                map.put("settleAcctNm", et.getChild("inacctnm",ns).getText());//商户收入账号开户名
                map.put("feeAcct", et.getChild("outacctno",ns).getText());//商户支出账号
                map.put("feeAcctNm", et.getChild("outacctnm",ns).getText());//商户支出账号开户名
                map.put("mchtNm", et.getChild("cusnm",ns).getText());//商户名称
                
//            }
            /**//*
             * 如要取<row>下的子元素的名称
             */
//            et = (Element) jiedian.get(0);
//            List zjiedian = et.getChildren();
//            for(int j=0;j<zjiedian.size();j++){
//                Element xet = (Element) zjiedian.get(j);
//                System.out.println(xet.getName());
//            }
        } catch (JDOMException e) {
            // TODO 自动生成 catch 块
            e.printStackTrace();
        } catch (IOException e) {
            // TODO 自动生成 catch 块
            e.printStackTrace();
        }
        return map;
    }
    public HashMap<String, String> xmlTest(String xmlDoc) {
    	HashMap<String, String> map = new LinkedHashMap<String, String>();
        //创建一个新的字符串
        StringReader read = new StringReader(xmlDoc);
        //创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
        InputSource source = new InputSource(read);
        //创建一个新的SAXBuilder
        SAXBuilder sb = new SAXBuilder();
        try {
            //通过输入源构造一个Document
            Document doc = sb.build(source);
            //取的根元素
            Element root = doc.getRootElement();
            System.out.println(root.getName());//输出根元素的名称（测试）
            //得到根元素所有子元素的集合
            List jiedian = root.getChildren();
            //获得XML中的命名空间（XML中未定义可不写）
            Namespace ns = root.getNamespace();
            Element et = null;
//            for(int i=0;i<jiedian.size();i++){
                et = (Element) jiedian.get(0);//循环依次得到子元素
                /**//*
                 * 无命名空间定义时
                 * et.getChild("users_id").getText();
                 * et.getChild("users_address",ns).getText()
                 * 得到报文头里元素的值
                 */
//                String msg = root.getChild("users_id",ns).getAttributeValue("msg");
                
                String users_id =  root.getChild("users_id",ns).getText();

                String users_name = root.getChild("users_name",ns).getText();
                
//                System.out.print("msg =="+msg);
                System.out.print("users_id =="+users_id);
                System.out.print("users_name =="+users_name);
                
               
  
                //得到报文体里元素的值
                et = (Element) jiedian.get(1);//循环依次得到子元素
                
             
//            }
            /**//*
             * 如要取<row>下的子元素的名称
             */
//            et = (Element) jiedian.get(0);
//            List zjiedian = et.getChildren();
//            for(int j=0;j<zjiedian.size();j++){
//                Element xet = (Element) zjiedian.get(j);
//                System.out.println(xet.getName());
//            }
        } catch (JDOMException e) {
            // TODO 自动生成 catch 块
            e.printStackTrace();
        } catch (IOException e) {
            // TODO 自动生成 catch 块
            e.printStackTrace();
        }
        return map;
    }
    



    public static void main(String[] args) throws UnsupportedEncodingException{
        DuXMLDoc doc = new DuXMLDoc();
        String xml = 
        "<Result>"+
//           "<row>"+
              "<users_id >1001     </users_id>"+
              "<users_name>wangwei   </users_name>"+
              "<users_group>80        </users_group>"+
              "<users_address>1001号   </users_address>"+
//           "</row>"+
//           "<body>"+
              "<users_id2>1002</users_id2>"+
              "<users_name2>wangwei   </users_name2>"+
              "<users_group2>80        </users_group2>"+
              "<users_address2>1002号   </users_address2>"+
//           "</body>"+
        "</Result>";
//        doc.xmlTest(xml);
//        String encoding = System.getProperty("file.encoding");
//         System.out.println(encoding);
   
    }
}

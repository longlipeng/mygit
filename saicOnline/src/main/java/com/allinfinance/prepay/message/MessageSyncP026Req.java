package com.allinfinance.prepay.message;

import java.util.ArrayList;
import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.allinfinance.prepay.annotation.MessageField;
import com.allinfinance.prepay.model.SaicOnlineBatchInfo;
import com.allinfinance.prepay.model.SaicOnlineBatchInfoDTO;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.thoughtworks.xstream.io.xml.XppDriver;
/**
 * 中台批量开户
 * @author 
 *
 */
@XStreamAlias("DATA")
public class MessageSyncP026Req extends BasicMessage {
	
	@XStreamImplicit(itemFieldName="saicOnlineBatchInfoDTO")
	protected List<SaicOnlineBatchInfoDTO> saicOnlineBatchInfoList ;
	@MessageField(maxLength=2)
	protected String area_code;
	
	protected static XStream xs = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")));
	
	
	
	
	


	

	public String getArea_code() {
		return area_code;
	}

	public void setArea_code(String area_code) {
		this.area_code = area_code;
	}

	public List<SaicOnlineBatchInfoDTO> getSaicOnlineBatchInfoList() {
		return saicOnlineBatchInfoList;
	}

	public void setSaicOnlineBatchInfoList(
			List<SaicOnlineBatchInfoDTO> saicOnlineBatchInfoList) {
		this.saicOnlineBatchInfoList = saicOnlineBatchInfoList;
	}

	public static XStream getXs() {
		return xs;
	}

	public static void setXs(XStream xs) {
		MessageSyncP026Req.xs = xs;
	}

	@Override
	public String toXml()
	{
		xs.alias("DATA", this.getClass());
		xs.processAnnotations(this.getClass());
		return xs.toXML(this);
	}
	
	public static Object parseXml(String xml, Class<?> clazz)
	{
		System.out.println(xml);
		xs.processAnnotations(clazz);//显示声明使用注解
		xs.alias("DATA", clazz);
		xs.alias("saicOnlineBatchInfoDTO", SaicOnlineBatchInfoDTO.class);
		
		return xs.fromXML(xml);
		
	}
	/*public static void main(String[] args) {
		SaicOnlineBatchInfo ww=new SaicOnlineBatchInfo();
		SaicOnlineBatchInfo www=new SaicOnlineBatchInfo();
		ww.setActivitySector("qq");
		ww.setAwareness("ww");
		ww.setCity("qq");
		www.setActivitySector("aaa");
		www.setAwareness("aaa");
		www.setCity("aaa");
		List<SaicOnlineBatchInfo> saicOnlineBatchInfoList=new ArrayList<SaicOnlineBatchInfo>();
		saicOnlineBatchInfoList.add(www);
		saicOnlineBatchInfoList.add(ww);
		MessageSyncP026Req mm= new MessageSyncP026Req();
		mm.setSaicOnlineBatchInfoList(saicOnlineBatchInfoList);
		String sss=mm.toXml();
		
		String aa="<?xml version='1.0' encoding='gbk'?>"
				+ "<DATA>"
				+ "<saicOnlineBatchInfo>"
				+ "<leaguerId>9088880000000358011</leaguerId>"
				+ "<name>seee</name>"
				+ "<custidType>1</custidType>"
				+ "<custidNo>234567890</custidNo>"
				+ "<custMobile></custMobile>"
				+ "<custArea>宁缺</custArea>"
				+ "<productId>15894646655</productId>"
				+ "<deliveryAddress>eeee</deliveryAddress>"
				+ "<dlateNumber>eee</dlateNumber>"
				+ "<driverLicence>1222</driverLicence>"
				+ "<activitySector>eee</activitySector>"
				+ "<awareness>eee</awareness>"
				+ "<county>ee</county>"
				+ "<city>ee</city>"
				+ "<validity>ee</validity>"
				+ "<gender>ee</gender>"
				+ "<remark>ee</remark>"
				+ "</saicOnlineBatchInfo>"
				+ "<saicOnlineBatchInfo>"
				+ "<leaguerId>9088880000000358011</leaguerId>"
				+ "<name>seee</name>"
				+ "<custidType>1</custidType>"
				+ "<custidNo>234567890</custidNo>"
				+ "<custMobile></custMobile>"
				+ "<custArea>宁缺</custArea>"
				+ "<productId>15894646655</productId>"
				+ "<deliveryAddress>eeee</deliveryAddress>"
				+ "<dlateNumber>eee</dlateNumber>"
				+ "<driverLicence>1222</driverLicence>"
				+ "<activitySector>eee</activitySector>"
				+ "<awareness>eee</awareness>"
				+ "<county>ee</county>"
				+ "<city>ee</city>"
				+ "<validity>ee</validity>"
				+ "<gender>ee</gender>"
				+ "<remark>ee</remark>"
				+ "</saicOnlineBatchInfo>"
				+ "</DATA><MAC>123</MAC>";
		MessageSyncP026Req m =(MessageSyncP026Req) mm.parseXml(aa, mm.getClass());
		System.out.println(sss);
		
	}*/

}

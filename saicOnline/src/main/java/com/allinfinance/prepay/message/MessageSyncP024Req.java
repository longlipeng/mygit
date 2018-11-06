package com.allinfinance.prepay.message;

import com.allinfinance.prepay.annotation.MessageField;
import com.allinfinance.prepay.annotation.MessageField.FieldType;
//import com.allinfinance.prepay.encryption.ArqcRequestObject;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.thoughtworks.xstream.io.xml.XppDriver;
/**
 * 中台开户
 * @author 
 *
 */
@XStreamAlias("DATA")
public class MessageSyncP024Req extends BasicMessage {
	
	@MessageField(mandetory=true,fieldType=FieldType.CHARACTER,maxLength=20)
	protected  String user_id;//会员ID
	@MessageField(mandetory=true,fieldType=FieldType.CHARACTER,maxLength=60)
	protected  String user_name;//姓名
	@MessageField(mandetory=true,fieldType=FieldType.CHARACTER,maxLength=1)
	protected  String id_type;//证件类型
	@MessageField(mandetory=true,fieldType=FieldType.CHARACTER,maxLength=32)
	protected  String id_no;//证件号
	@MessageField(mandetory=true,fieldType=FieldType.CHARACTER,maxLength=19)
	protected  String mobile;//手机号
	@MessageField(mandetory=true,fieldType=FieldType.CHARACTER,maxLength=2)
	protected  String area_code;//客户受理区域
	@MessageField(mandetory=true,fieldType=FieldType.CHARACTER,maxLength=32)
	protected  String card_product;//卡产品
	@MessageField(mandetory=true,fieldType=FieldType.CHARACTER,maxLength=100)
	protected  String address;//邮寄地址
	@MessageField(mandetory=true,fieldType=FieldType.CHARACTER,maxLength=1)
	protected  String occupation; //职业
	@MessageField(mandetory=true,fieldType=FieldType.CHARACTER,maxLength=8)
	protected  String birthday;//生日
	@MessageField(mandetory=true,fieldType=FieldType.CHARACTER,maxLength=15)
	protected  String nationality; //国籍
	@MessageField(mandetory=true,fieldType=FieldType.CHARACTER,maxLength=8)
	protected  String id_validity;//证件失效日期
	@MessageField(mandetory=true,fieldType=FieldType.CHARACTER,maxLength=1)
	protected  String gender;//性别
	@MessageField(maxLength=64)
	protected  String email;

	private static XStream xs = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")));
	
	
	
	

	
	

	

	

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getId_type() {
		return id_type;
	}

	public void setId_type(String id_type) {
		this.id_type = id_type;
	}

	public String getId_no() {
		return id_no;
	}

	public void setId_no(String id_no) {
		this.id_no = id_no;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getArea_code() {
		return area_code;
	}

	public void setArea_code(String area_code) {
		this.area_code = area_code;
	}

	public String getCard_product() {
		return card_product;
	}

	public void setCard_product(String card_product) {
		this.card_product = card_product;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getId_validity() {
		return id_validity;
	}

	public void setId_validity(String id_validity) {
		this.id_validity = id_validity;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public static XStream getXs() {
		return xs;
	}

	public static void setXs(XStream xs) {
		MessageSyncP024Req.xs = xs;
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
		xs.alias("DATA", clazz);
		return xs.fromXML(xml);
		
	}

}

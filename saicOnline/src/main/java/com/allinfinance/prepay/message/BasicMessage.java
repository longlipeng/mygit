package com.allinfinance.prepay.message;

import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.Arrays;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import java.lang.annotation.Inherited;

import com.allinfinance.prepay.annotation.MessageField;
import com.allinfinance.prepay.annotation.MessageField.FieldType;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("DATA")
public abstract class BasicMessage implements IMessage {
	@MessageField(mandetory=true,fieldType=FieldType.CHARACTER)
	protected String TXN_CODE;//交易代码
	@MessageField(mandetory=true,fieldType=FieldType.CHARACTER)
	protected String MSG_TYPE;//报文类型
	@MessageField(mandetory=true,fieldType=FieldType.CHARACTER)
	protected String TXN_TIME;//交易时间
	@MessageField(mandetory=true,fieldType=FieldType.CHARACTER)
	protected String REQSEQNO;//请求流水号
	@MessageField(mandetory=true,fixedLength=8,fieldType=FieldType.NUMBER)
	protected String RANDOM;//随机数
	@MessageField(mandetory=true,fieldType=FieldType.CHARACTER)
	protected String CHANNEL;//渠道号
	@MessageField(mandetory=true,fieldType=FieldType.CHARACTER)
	protected String ISSUER_ID;//机构ID
	@MessageField(mandetory=true,fieldType=FieldType.CHARACTER)
	protected String TERM_ID;//终端号
	@MessageField(mandetory=true,fieldType=FieldType.CHARACTER)
	protected String MCHNT_CD;//商户号
	@MessageField(mandetory=true,fieldType=FieldType.CHARACTER)
	protected String BRANCH_ID;//服务网点
	
	
//	protected String RESP_TEXT;//返回内容
	protected String RESP_CODE;//返回码
	protected String MAC;//报文校验码
	protected String RESPSEQNO;//返回流水
	protected String KEY;
	
	@Override
	public BasicMessage createResp() throws InstantiationException,
			IllegalAccessException, ClassNotFoundException {
		String selfClazzName = this.getClass().getName();
		String respClazzName = selfClazzName.substring(0, selfClazzName.length() - 3)+ "Resp";
		BasicMessage message = (BasicMessage) Class.forName(respClazzName).newInstance();
		message.TXN_CODE = this.TXN_CODE;
		message.MSG_TYPE = "2";//1：请求报文     2：返回报文
		message.TXN_TIME = this.TXN_TIME;
		message.REQSEQNO = this.REQSEQNO;
		message.RANDOM = this.RANDOM;
		message.CHANNEL = this.CHANNEL;
		message.ISSUER_ID = this.ISSUER_ID;
		message.TERM_ID = this.TERM_ID;
		message.MCHNT_CD = this.MCHNT_CD;
		message.BRANCH_ID=this.BRANCH_ID;
		message.RESPSEQNO = this.RESPSEQNO;
		return message;
	}
	
	public MessageMinResp createMinResp(String[] error) {
		MessageMinResp message = new MessageMinResp();
		message.TXN_CODE = this.TXN_CODE;
		message.MSG_TYPE = "2";//1：请求报文     2：返回报文
		message.TXN_TIME = this.TXN_TIME;
		message.REQSEQNO = this.REQSEQNO;
		message.RANDOM = this.RANDOM;
		message.CHANNEL = this.CHANNEL;
		message.ISSUER_ID = this.ISSUER_ID;
		message.TERM_ID = this.TERM_ID;
		message.MCHNT_CD = this.MCHNT_CD;
		message.RESPSEQNO = this.RESPSEQNO;
		message.BRANCH_ID=this.BRANCH_ID;
//		message.RESP_TEXT = error[1];
		message.RESP_CODE = error[0];
//		String respCode;
//		if((respCode = ResponseCode.CUP_RESPONSE_CODE_MAP.get(error[0])) == null)
//			respCode = ResponseCode.CUP_RESPONSE_CODE_MAP.get(ResponseCode.DEFAULT_ERROR[0]);
//		message.RESPCODE = respCode;
		return message;
	}
	

	public void checkData() throws IllegalFieldDataException
	{
		Field[] fields = this.getClass().getDeclaredFields();
		Field[] fields2 = this.getClass().getSuperclass().getDeclaredFields();//父类字段
		int strLen1 = fields.length;
		int strLen2 = fields2.length;
		fields = Arrays.copyOf(fields, strLen1 + strLen2);// 扩容
		System.arraycopy(fields2, 0, fields, strLen1, strLen2);// 将第二个数组与第一个数组合并
		
		for(Field field : fields)
		{
			if(field.isAnnotationPresent(MessageField.class))
			{
				try
				{
					String value = field.get(this)==null?null:field.get(this).toString();
					//System.out.println("field="+field.getName()+" value="+value);
					MessageField annotation = field.getAnnotation(MessageField.class);
					String msg = "";
					if(annotation.mandetory()&&StringUtils.isEmpty(value))
					{
						msg = MessageFormat.format("字段值检查错误，字段:{0},值:{1}", field.getName(), value);
						throw new IllegalFieldDataException(msg);
					}
					else if(!StringUtils.isEmpty(value))
					{
						if(annotation.fixedLength()!=-1 &&value.length()!=annotation.fixedLength())
						{
							msg = MessageFormat.format("字段长度检查错误，字段:{0},值{1},固定长度{2},实际长度{3}", field.getName(), value, annotation.fixedLength(), value.length());
							throw new IllegalFieldDataException(msg);
						}
						if(annotation.maxLength()!=-1 &&value.length()>annotation.maxLength())
						{
							msg = MessageFormat.format("字段长度检查错误，字段:{0},值{1},最大长度{2},实际长度{3}", field.getName(), value, annotation.maxLength(), value.length());
							throw new IllegalFieldDataException(msg);
						}
						if(annotation.minLength()!=-1 &&value.length()<annotation.minLength())
						{
							msg = MessageFormat.format("字段长度检查错误，字段:{0},值{1},最小长度{2},实际长度{3}", field.getName(), value, annotation.maxLength(), value.length());
							throw new IllegalFieldDataException(msg);
						}
						if(annotation.fieldType().equals(FieldType.NUMBER) && !NumberUtils.isDigits(value))
						{
							msg = MessageFormat.format("非法数字类型，字段:{0},值{1}", field.getName(), value);
							throw new IllegalFieldDataException(msg);
						}
						if(annotation.fieldType().equals(FieldType.HEXSTRING) && !value.matches("[\\da-fA-F]+"))
						{
							msg = MessageFormat.format("非法十六进制数字类型，字段:{0},值{1}", field.getName(), value);
							throw new IllegalFieldDataException(msg);
						}
					}
					
				} catch (IllegalArgumentException e)
				{
					e.printStackTrace();
				} catch (IllegalAccessException e)
				{
					e.printStackTrace();
				}
			}
		}
	}


	
	
	public String getBRANCH_ID() {
		return BRANCH_ID;
	}


	public void setBRANCH_ID(String bRANCH_ID) {
		BRANCH_ID = bRANCH_ID;
	}


	public String getTXN_CODE() {
		return TXN_CODE;
	}


	public void setTXN_CODE(String tXN_CODE) {
		TXN_CODE = tXN_CODE;
	}


	public String getMSG_TYPE() {
		return MSG_TYPE;
	}


	public void setMSG_TYPE(String mSG_TYPE) {
		MSG_TYPE = mSG_TYPE;
	}


	public String getTXN_TIME() {
		return TXN_TIME;
	}


	public void setTXN_TIME(String tXN_TIME) {
		TXN_TIME = tXN_TIME;
	}


	public String getREQSEQNO() {
		return REQSEQNO;
	}


	public void setREQSEQNO(String rEQSEQNO) {
		REQSEQNO = rEQSEQNO;
	}


	public String getRANDOM() {
		return RANDOM;
	}


	public void setRANDOM(String rANDOM) {
		RANDOM = rANDOM;
	}


	public String getCHANNEL() {
		return CHANNEL;
	}


	public void setCHANNEL(String cHANNEL) {
		CHANNEL = cHANNEL;
	}


	public String getISSUER_ID() {
		return ISSUER_ID;
	}


	public void setISSUER_ID(String iSSUER_ID) {
		ISSUER_ID = iSSUER_ID;
	}


	public String getTERM_ID() {
		return TERM_ID;
	}


	public void setTERM_ID(String tERM_ID) {
		TERM_ID = tERM_ID;
	}


	public String getMCHNT_CD() {
		return MCHNT_CD;
	}


	public void setMCHNT_CD(String mCHNT_CD) {
		MCHNT_CD = mCHNT_CD;
	}


//	public String getRESP_TEXT() {
//		return RESP_TEXT;
//	}
//
//
//	public void setRESP_TEXT(String rESP_TEXT) {
//		RESP_TEXT = rESP_TEXT;
//	}


	public String getRESP_CODE() {
		return RESP_CODE;
	}


	public void setRESP_CODE(String rESP_CODE) {
		RESP_CODE = rESP_CODE;
	}


	public String getMAC() {
		return MAC;
	}


	public void setMAC(String mAC) {
		MAC = mAC;
	}


	public String getRESPSEQNO() {
		return RESPSEQNO;
	}


	public void setRESPSEQNO(String rESPSEQNO) {
		RESPSEQNO = rESPSEQNO;
	}

	public String getKEY() {
		return KEY;
	}

	public void setKEY(String kEY) {
		KEY = kEY;
	}
	
	
}

package com.allinfinance.prepay.utils;

import java.util.HashMap;

public class ErrorCodeUtil {
	private static HashMap<String, String> errs = new HashMap<String, String>();
	private static HashMap<String, String> errs2 = new HashMap<String, String>();
	private static HashMap<String, String> errs3 = new HashMap<String, String>();
	private static HashMap<String, String> errs4 = new HashMap<String, String>();

	private static void init() {
		errs.put("00", "ִ�гɹ�!");
		errs.put("01", "ȷ�ϴ���/������Կ��żУ����󾯸档");
		errs.put("02", "��Կ���ȴ���(�������㷨Ҫ��).");
		errs.put("04", "��Ч��Կ���ʹ���.");
		errs.put("05", "��Ч��Կ���ȱ�ʶ.");
		errs.put("10", "Դ��Կ��żУ���.");
		errs.put("11", "Ŀ����Կ��żУ���.");
		errs.put("12", "�û��洢�����������Ч�� ������������д.");
		errs.put("13", "����Կ��żУ���.");
		errs.put("14", "LMK�� 02 -03 �����µ� PINʧЧ.");
		errs.put("15", "��Ч����������(��Ч�ĸ�ʽ,��Ч���ַ�����û���ṩ�㹻������).");
		errs.put("16", "����̨���ӡ��û��׼���û���û������.");
		errs.put("17", "HSM������Ȩ״̬,���������PIN���,���������������.");
		errs.put("18", "û��װ���ĵ���ʽ����.");
		errs.put("19", "ָ����Diebold����Ч.");
		errs.put("20", "PIN��û�а���Чֵ.");
		errs.put("21", "��Ч������ֵ,����/�������������.");
		errs.put("22", "��Ч���˺�.");
		errs.put("23", "��Ч��PIN���ʽ����.");
		errs.put("24", "PIN����С��4�����12.");
		errs.put("25", "ʮ���Ʊ����.");
		errs.put("26", "��Ч����Կ����.");
		errs.put("27", "��ƥ�����Կ����.");
		errs.put("28", "��Ч����Կ����.");
		errs.put("29", "��Կ��������ֹ.");
		errs.put("30", "�ο�����Ч.");
		errs.put("31", "û���㹻������������ṩ��������.");
		errs.put("33", "LMK��Կת���洢�����ƻ�.");
		errs.put("40", "��Ч�Ĺ̼�У���.");
		errs.put("41", "�ڲ���Ӳ��/�����:RAM�ѻ�,��Ч�Ĵ������,�ȵ�.");
		errs.put("42", "DES����.");
		errs.put("46", "�������ģ��.");
		errs.put("47", "DSP����:���������Ա.");
		errs.put("49", "˽Կ����:���������Ա.");
		errs.put("60", "�޴�����.");
		errs.put("62", "���ͱ��ĳ��ȹ���");
		errs.put("74", "��ЧժҪ��Ϣ�﷨(����ϣģʽ).");
		errs.put("75", "��Ч��Կ/˽Կ��.");
		errs.put("76", "��Կ���ȴ���.");
		errs.put("77", "�������ݿ����.");
		errs.put("78", "˽Կ���ȴ���.");
		errs.put("79", "��ϣ�㷨�����ʶ����.");
		errs.put("80", "���ݳ��ȴ���,MAC����(����������)�ĳ��ȴ��ڻ�Сָ���ĳ���.");
		errs.put("81", "֤��ƫ��ֵ�볤�ȴ�.");
		errs.put("82", "�����Ų��ڷ�Χ��.");
		errs.put("85", "��Կ������.");
		errs.put("90", "HSM���յ�������Ϣ��������żУ���.");
		errs.put("91", "��������У��(LRC)�ַ���ƥ��������������������ֵ(��HSM���յ�һ��͸�����첽��ʱ)�� .");
		errs.put("92", "����ֵ(����/������)������Ч��Χ��,���߲���ȷ.(�� HSMHSMHSM���յ�һ��͸�����첽��ʱ)");
	}
	public static void init2(){
		errs2.put("00", "�޴���");
		errs2.put("03", "��Ч��Կ��������");
		errs2.put("04", "���ȴ���");
		errs2.put("05", "��Ч��Կ����");
		errs2.put("06", "����ָ�����ȴ���");
		errs2.put("08", "���ṩ�Ĺ���ָ��ΪżУ��");
		errs2.put("13", "LMK����:���������Ա");
		errs2.put("15", "�������ݴ�");
		errs2.put("17", "������Ȩ״̬");
		errs2.put("47", "DSP����:���������Ա");
		errs2.put("62", "���ͱ��ĳ��ȹ���");
	}
	public static void init3(){
		errs3.put("00", "�޴���");
		errs3.put("03", "��Ч��Կ����");
		errs3.put("04", "˽Կ�洢�ռ䲻��");
		errs3.put("13", "LMK���󣻱��������Ա");
		errs3.put("15", "�������ݴ�");
		errs3.put("49", "˽Կ���󣻱��������Ա");
		errs3.put("78", "˽Կ���ȴ���");	
		errs3.put("62", "���ͱ��ĳ��ȹ���");
	}
	
	//arqc
	public static void init4(){
		errs4.put("00", "�޴���");
		errs4.put("01", "ARQC/TC/AACУ��ʧ��");
		errs4.put("04", "ģʽ��־��");
		errs4.put("05", "δ����ķ���ID");
		errs4.put("10", "MK-AC��żУ���");
		errs4.put("12", "�û��洢��û��װ����Կ");
		errs4.put("13", "˽Կ���ȴ���");	
		errs4.put("15", "LMK����");
		errs4.put("52", "�Ƿ���B/Hѡ��");
		errs4.put("80", "���ݳ��ȴ�");	
		errs4.put("81", "PAN���ȴ�");
	}

	public static String getErrString(String err) {
		if (errs.isEmpty())
			init();
		return errs.get(err) == null ? err : errs.get(err);
	}
	/**
	 * �����ƶ�ָ��RSA����
	 * @param err ������
	 * @return ������Ϣ
	 */
	public static String getErrString2(String err) {
		if (errs2.isEmpty())
			init2();
		return errs2.get(err) == null ? err : errs2.get(err);
	}
	
	/**
	 * װ��RSA˽Կ����
	 * @param err ������
	 * @return ������Ϣ
	 */
	public static String getErrString3(String err) {
		if (errs3.isEmpty())
			init3();
		return errs3.get(err) == null ? err : errs3.get(err);
	}
	
	
	/**
	 * arqc����
	 * @param err ������
	 * @return ������Ϣ
	 */
	public static String getErrString4(String err) {
		if (errs4.isEmpty())
			init4();
		return errs4.get(err) == null ? err : errs4.get(err);
	}
	
	public static void main(String[] args) {
		System.out.println(ErrorCodeUtil.getErrString("00"));
	}
}

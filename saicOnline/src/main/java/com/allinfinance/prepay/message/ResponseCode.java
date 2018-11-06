package com.allinfinance.prepay.message;


public class ResponseCode {
	public static final String[] EMPTY							= {"", ""};
	public static final String[] SUCCESS						= {"00", "���׳ɹ�"};
	public static final String[] ILLEGAL_MESSAGE_FORMAT		= {"0001", "���ĸ�ʽ����"};
	public static final String[] ILLEGAL_KEY					= {"0002", "��Կ��Ϣ����"};
	public static final String[] TRANSACTION_NOT_SUPPORTED	= {"0003", "���ײ�֧��"};
	public static final String[] ARQC_VERIFY_ERROR			= {"0004", "ARQC��֤ʧ��"};
	public static final String[] MAC_CALCULATION_ERROR		= {"0005", "MAC�������"};
	public static final String[] HSM_ERROR					= {"9901", "���ܻ�����"};
	public static final String[] SYSTEM_ERROR					= {"9999", "ϵͳ����"};
}

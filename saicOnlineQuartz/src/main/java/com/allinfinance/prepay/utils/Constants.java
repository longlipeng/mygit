package com.allinfinance.prepay.utils;

public class Constants {
//	public static final String PAD = "80";
	public static final String ENCODE_XML_GBK ="GBK";
	public static final char CZERO = '0';
	public static final String SEP = ";";
	
	public static final String PARAERR = "E1";// ��������
	public static final String SCODEERR = "E2";// ����������
	public static final String SYSERR = "E3";// ϵͳ����
	public static final String XMLENCODING = "GBK";// xml����
	public static final String KEY_SOCKET_IP = "socket.ip";// ͨ��IP
	public static final String KEY_SOCKET_PORT = "socket.port";// ͨ�Ŷ˿�
	public static final String KEY_SOCKET_PORT2 = "socket.port2";// ��ͨ�Ŷ˿�
	public static final String KEY_SOCKET_IP_FORZHXT = "socket.ZHXT_ip";// ͨ��IP
	public static final String KEY_SOCKET_PORT_FORZHXT = "socket.ZHXT_port";// ͨ�Ŷ˿�
	public static final String KEY_HTTP_URL = "postUrl.url";// postIP
	public static final String KEY_SOCKET_LISTENER_PORT = "socket.listener_port";// �����˿�
	public static final String KEY_SOCKET_TIMEOUT = "socket.timeout";// ͨ�ų�ʱ
	public static final String XMLBAOWENHEADER = "<?xml version=\"1.0\" encoding=\"GBK\"?>";// XMLͷ// ������
	public static final String SCODE_CARDTXNSTAQUERY = "1031";// ��״̬��ѯ
	public static final String SCODE_ACCOUNTBALANCEQUERY = "1021";// Ԥ��ֵ�˻�����ѯ
	public static final String SCODE_CASHRECHARGE = "1051";// ����Ǯ���ֽ��ֵ
	public static final String SCODE_CASHRECHARGEFLUSHES = "2051";// ����Ǯ���ֽ��ֵ����
	public static final String SCODE_RECHARGENOTIFY = "1151";// ��ֵ�ɹ�֪ͨ
	public static final String SCODE_ACCOUNTCONSUM = "1101";// Ԥ��ֵ�˻�����
	public static final String SCODE_CASHCONSUM = "1171";// ����Ǯ������
	public static final String SCODE_ACCOUNTCONSUMFLUSHES = "2101";// Ԥ��ֵ�˻����ѳ���
	public static final String SCODE_ACCOUNTCONSUMCANCLE = "3101";// Ԥ��ֵ�˻����ѳ���
	public static final String SCODE_ACCOUNTCONSUMCANCLEFLUSHES = "4101";// Ԥ��ֵ�˻����ѳ�������
	public static final String SCODE_ACCOUNTCONSUMRETURNED = "5151";// Ԥ��ֵ�����˻�
	public static final String SCODE_CASHRETURNED = "5171";// ����Ǯ���˻�
	public static final String SCODE_SPEACCOUNTTRANSFER = "1081";// ����Ǯ��ָ���˻�Ȧ��
	public static final String SCODE_SPEACCOUNTTRANSFERNOTIFY = "1181";// ����Ǯ��ָ���˻�Ȧ��ɹ�֪ͨ
	public static final String SCODE_SPEACCOUNTTRANSFERFLUSHES = "2081";// ����Ǯ��ָ���˻�Ȧ�����
	public static final String SCODE_NOSPEACCOUNTTRANSFER = "1091";// ����Ǯ����ָ���˻�Ȧ��
	public static final String SCODE_NOSPEACCOUNTTRANSFERNOTIFY = "1191";// ����Ǯ����ָ���˻�Ȧ��ɹ�֪ͨ
	public static final String SCODE_NOSPEACCOUNTTRANSFERFLUSHES = "2091";// ����Ǯ����ָ���˻�Ȧ�����
	public static final String SCODE_ACCOUNTPWDCHANGE = "7011";// Ԥ��ֵ�˻������޸�
	public static final String SCODE_ACCOUNTPWDRESET = "1019";//Ԥ��ֵ�˻���������
	public static final String SCODE_CHANLSIGNIN = "8201";// ����ǩ������
	public static final String SCODE_PERINFINQ = "8301";// ������Ϣ��ѯ

	public static final String SCODE_CARDTXNDETAILINQ = "6021";// �˻�������ϸ��ѯ
	public static final String SCODE_BLACKLISTDOWNLOAD = "8701";// IC������������
	
	public static final String SCODE_BLALISTDOWNLOAD = "6001";//IC������������
	public static final String SCODE_PERINF = "6011";//���˼�����Ϣ��ѯ
	public static final String SCODE_CARDACCINFINQ = "6051";//��,�˻���Ϣ��ѯ
	public static final String SCODE_CARDHANGUP = "6061";// ��Ƭ��ʧ/�ӹ�ʧ
	public static final String SCODE_ACCOUNTTXNDETAILINQ = "6021";//�˻�������ϸ��ѯ
	public static final String SCODE_PERSONCUSTINFOADDINPUT = "6071";//���˼�����Ϣ¼��
	public static final String SCODE_PERSONCUSTINFOADDAMEND = "6031";//���˼�����Ϣ�޸�
	public static final String SCODE_PERACCRECHARGE = "1071";//�����˻���ֵ

	public static final String SCODE_ACCTRANSFERMONEY = "1061";// �����˻�ת��
	public static final String SCODE_OPENPERSONACCWITHNAME = "6081";// ���˼�������
	public final static String SCODE_ADDFLOWCARD = "6091";// �����˻��������
	public final static String  SCODE_UNBINDFLOWCARD= "6101";// �����˻���������
	public final static String  SCODE_PERSONCASHRECHARGEFLUSHES= "2071";// �����˻���ֵ����
	public final static String  SCODE_PERFCARDINFINQLIST= "6041";// �����б���Ϣ��ѯ
	public final static String SCODE_REPLACECARD = "6151";// ���˲���\����
	public final static String SCODE_CANCLEACCORRETURNCARD = "6141";// �����˻��˿�����
	public final static String SCODE_CARDFACECHECKCARD = "1801";//���ݿ���Ų鿨��
	public final static String SCODE_CONTACTCHECKINGFILEQUERY = "6111";// �̻������ļ���ѯ
	public final static String SCODE_CONTACTCHECKINGFILEDOWN = "6121";// �̻������ļ�����
	public final static String SCODE_ACCOUNTBALANCEQUERYS = "1021";// ����ѯ  
	public final static String SCODE_ACCOUNTCONSUMS = "1601";// �˻�ת�� 
	
	public static final String BatchNo = "000001";// ���κ�
	public static final String MsgType = "0200";//��Ϣ����
	public static final String ProcessingCode = "310000";// ���״�����
	public static final String PosEntryModeCode = "051";// ��������뷽ʽ��
	public static final String CardSeqId = "000";// ��Ƭ���к�
	public static final String PosCondCode = "00";// �����������
	public static final String CurrcyCodeTrans = "156";// ���׻��Ҵ���
	public static final String SecRelatdCtrlInfo = "2600000000000000";// ��ȫ������Ϣ
	public static final String EmvVal = "0000000000";//IC��������
	
	public static final String Method_BindingCard="BindingCards";//��ʯ�� ��
	public static final String Method_GetCardNoInfo="GetCardInfoByTypeSs";//��ʯ�� ��ȡ����Ҫ��Ϣ
	public static final String Method_GetOrderInfo="GetOrderInfos";//���Ԥ��ֵ��Ϣ
	public static final String Method_CardSpareMoneyRecharge="CardSpareMoneyRecharges";//���Ԥ��ֵ��Ϣ
	public static final String Method_OrederQuery="OrderQuerys";//��ѯ��ֵ״̬�ӿ�
	public static final String Method_ReconQuery="ReconQuerys";//�����ļ�֪ͨ�ӿ�
	
}

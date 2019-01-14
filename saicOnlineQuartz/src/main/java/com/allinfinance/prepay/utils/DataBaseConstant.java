package com.allinfinance.prepay.utils;

/**
 * ���ݿⳣ������.
 * 
 * @author jiagang
 */
public class DataBaseConstant {

    /**
     * ��������
     */
    public final static String DATA_TYPE_YES = "1";

    public final static String DATA_TYPE_NO = "0";
    /** ����״̬���� 0��ɾ��. */
    public final static String DATA_STATE_DELETE = "0";

    /** ����״̬���� 1������. */
    public final static String DATA_STATE_NORMAL = "1";
    
    /** 2��Ӫ������ */
    public final static String SELLER_RESOURCE_RANK = "2";

    /** ��Ӫ�У���Ч��1 */
    public final static String SHOP_DATA_STATE_NORMAL = "1";

    /** �ŵ��л����ŵ�����Ӧ�� 2 */
    public final static String SHOP_DATA_STATE_CHANGE = "2";

    /** �ŵ�ͣ�ã���Ч��3 */
    public final static String SHOP_DATA_STATE_DISABLE = "3";

    /** ��Ч״̬���� 0����Ч. */
    public final static String STATE_INACTIVE = "0";

    /** ��Ч״̬���� 1����Ч. */
    public final static String STATE_ACTIVE = "1";

    /** �̻�����״̬1���Ѹ���. */
    public final static String CHECK_STATE_CHECK = "1";

    /** �̻�����״̬0��δ����. */
    public final static String CHECK_STATE_NONCHECK = "0";

    /** ��ϵ�˹������� 1���ͻ�. */
    public final static String CONTACT_REF_TYPE_CUSTOMER = "1";

    /** ��ϵ�˹������� 2�������̻�. */
    public final static String CONTACT_REF_TYPE_MERCHANTGROUP = "2";

    /** ��ϵ�˹������� 3���̻�. */
    public final static String CONTACT_REF_TYPE_MERCHANT = "3";

    /** ��ϵ�˹������� 4���ŵ�. */
    public final static String CONTACT_REF_TYPE_SHOP = "4";

    /** �ƿ�״̬ 1���޿�. */
    public final static String MAKE_CARD_STATE_NOCARD = "1";

    /** �ƿ�״̬ 2���ƿ���. */
    public final static String MAKE_CARD_STATE_MAKING = "2";

    /** �ƿ�״̬ 3���ƿ��ɹ�. */
    public final static String MAKE_CARD_STATE_SUCCESS = "3";

    /** �ƿ�״̬ 4���ƿ�ʧ��. */
    public final static String MAKE_CARD_STATE_FAILTURE = "4";

    /** PIN״̬ 1����PIN. */
    public final static String PIN_STATE_NOCARD = "1";

    /** PIN״̬ 2����PIN��. */
    public final static String PIN_STATE_MAKING = "2";

    /** PIN״̬ 3����PIN�ɹ�. */
    public final static String PIN_STATE_SUCCESS = "3";

    /** PIN״̬ 4����PINʧ��. */
    public final static String PIN_STATE_FAILTURE = "4";

    /** �������� 1����ֵ������. */
    public final static String ORDER_TYPE_RELOADABLECARD = "1";

    /** �������� 2����ֵ����. */
    public final static String ORDER_TYPE_CREDIT = "2";

    /** �������� 3����Ʒ����涩��. */
    public final static String ORDER_TYPE_GIFTCARDSTOCK = "3";

    /** �������� 4����Ʒ�����۶���. */
    public final static String ORDER_TYPE_GIFTCARDSALE = "4";

    /** �������� 5����Ʒ����������. */
    public final static String ORDER_TYPE_GIFTCARDCHANGE = "5";

    /** �������� 6����ֵ����������. */
    public final static String ORDER_TYPE_RELOADABLECARDCHANGE = "6";

    /** �������� 7����Ʒ�������˵�����. */
    public final static String ORDER_TYPE_GIFTCARDSENDBACK = "7";

    /** ��������ͣ�ò������� 1��¼��. */
    public final static String ORDER_BATCH_DISABLE_OPERATETYPE_INPUT = "1";

    /** ��������ͣ�ò������� 2��ȷ��. */
    public final static String ORDER_BATCH_DISABLE_OPERATETYPE_CONFIRM = "2";

    /** ����������ʧ��Ҳ������� 1����ʧ. */
    public final static String ORDER_BATCH_BLOCK_OPERATETYPE_REPORTLOSS = "1";

    /** ����������ʧ��Ҳ������� 2�����. */
    public final static String ORDER_BATCH_BLOCK_OPERATETYPE_RELIEVEREPORTLOSS = "2";

    /** ����֪ͨ��ˮ��֪ͨ��ʶ 1����֪ͨ. */
    public final static String INFORM_TXN_CALL_FLAG_INFORM = "1";

    /** ����֪ͨ��ˮ��֪ͨ��ʶ 2��δ֪ͨ. */
    public final static String INFORM_TXN_CALL_FLAG_NOT_INFORM = "2";

    /** ����֪ͨ��ˮ��֪ͨ���� 1������. */
    public final static String INFORM_TXN_CALL_TYPE_TRANS = "1";

    /** ����֪ͨ��ˮ��֪ͨ���� 2��������. */
    public final static String INFORM_TXN_CALL_TYPE_BANTH = "2";

    /** ����֪ͨ��ˮ��֪ͨ���� 3���±�. */
    public final static String INFORM_TXN_CALL_TYPE_MONTHINFORM = "3";

    /** ����֪ͨ��ˮ��֪ͨ���� 4������֪ͨ'. */
    public final static String INFORM_TXN_CALL_TYPE_RELAX = "4";

    /** ����֪ͨ��ˮ��֪ͨ���� 5�����ÿ���. */
    public final static String INFORM_TXN_CALL_TYPE_CHANGEPWD = "5";

    /** ���ۺ�ͬ����1: ���л�����Ӫ��������ͬ */
    public final static String CONTRACT_ISSUER = "1";
    /** ���ۺ�ͬ����2: Ӫ��������Ӫ��������ͬ */
    public final static String CONTRACT_SELLER = "2";
    /** ���ۺ�ͬ����3: Ӫ�������Ϳͻ���ͬ */
    public final static String CONTRACT_CUSTOMER = "3";
    /** ���ۺ�ͬ����4: �ͻ���ͬģ�� */
    public final static String CONTRACT_TEMPLATE = "0";
    //
    // /** �ֿ��濨״̬20���ѷ���. */
    // public final static Short ISSURESTOCK_CARD_STOCK_STATE_DISTRIBUTED = 20;
    //
    // /** �ֿ��濨״̬21�����۳�. */
    // public final static Short ISSURESTOCK_CARD_STOCK_STATE_SELDEN = 21;
    //
    // /** �ֿ��濨״̬22���ڿ��. */
    // public final static Short ISSURESTOCK_CARD_STOCK_STATE_STOCK = 22;
    //
    // /** ��Ƭ��Ϣ������״̬1���Ѽ���. */
    // public final static short CARD_ACT_STAT_ACT = 1;
    //
    // /** ��Ƭ��Ϣ������״̬1��δ����. */
    // public final static short CARD_ACT_STAT_NONACT = 0;
    //
    // /** ���ֿ������Ϣ��״̬_101 �����俨. */
    // public final static Integer CARD_STOCK_OPERATER_TYPE_101 = 101;
    //
    // /** ���ֿ������Ϣ��״̬_102 ���俨Ƭ�۳�. */
    // public final static Integer CARD_STOCK_OPERATER_TYPE_102 = 102;
    //
    // /** ���ֿ������Ϣ��״̬_103 ���俨Ƭ��ֵ. */
    // public final static Integer CARD_STOCK_OPERATER_TYPE_103 = 103;
    //
    // /** ���ֿ������Ϣ��״̬_104 ���俨Ƭ����. */
    // public final static Integer CARD_STOCK_OPERATER_TYPE_104 = 104;
    //
    // /** ���ֿ������Ϣ��״̬_105 ���俨Ƭ�˿�. */
    // public final static Integer CARD_STOCK_OPERATER_TYPE_105 = 105;
    //
    // /** ���ֿ������Ϣ��״̬_106 �����̵�. */
    // // public final static Integer CARD_STOCK_OPERATER_TYPE_106 = 106;
    //
    // ����ɾ��
    public final static String RULE_STATE_DELETE = "0";
    // �����½�
    public final static String RULE_STATE_NEW = "1";
    // ��������
    public final static String RULE_STATE_ENABLE = "2";

    // ���Ѻ�ͬ �յ�����
    public final static String CONTRACT_CONSUMER = "1";
    // ���Ѻ�ͬ �̻�
    public final static String CONTRACT_MERCHANT = "2";

    /***
     * Ӫ����ͬ(�뷢������)
     */
    public final static String SELL_CONTRACT_ISSUER = "1";
    /**
     * Ӫ����ͬ(��Ӫ������)
     */
    public final static String SELL_CONTRACT_SELLER = "2";
    /**
     * Ӫ����ͬ(��ͻ�)
     */
    public final static String SELL_CONTRACT_CUSTOMER = "3";
    /**
     * ��������ͬ(���������뷢������)
     */
    public final static String LOYALTY_CONTRACT_SELLER = "4";

    /** Ĭ���Ƿ�Ĭ�� */
    public final static String DEFAULT_FLAG_YES = "1";

    public final static String DEFAULT_FLAG_NO = "0";

    /** ��Ʊ״̬ :0δ��Ʊ */
    public final static String INVOICE_NOT_MAKE = "0";
    /** ��Ʊ״̬ :1�ѿ�Ʊ */
    public final static String INVOICE_HAS_MAKE = "1";
    /** ��Ʊ״̬ :2ȡ����Ʊ */
    public final static String INVOICE_CANCEL_MAKE = "2";
    /** ��ͨ��Ʊ */
    public final static String INVOICE_TYPE_TWO = "2";
    /** ��ֵ˰��Ʊ */
    public final static String INVOICE_TYPE_ONE = "1";

    /** ��Ʊ��ַ���ͷ�ʽ :�ͻ�����1 */
    public final static String DELIVERY_OPERATION_SEND = "1";
    /** ��Ʊ��ַ���ͷ�ʽ :����ȡ��2 */
    public final static String DELIVERY_OPERATION_GET = "2";

    /** Ĭ��ʧЧ���� ���ʱ�� */
    public final static String DEFAULT_EXPIRY_DATE = "29991231";

    /** �������������� ���ֵ */
    public final static String ORDER_CARD_MAXIMUM = "500";

    /** �������������� �������� */
    public final static String ORDER_CARD_MAXIMUM_NAME = "ORDER_CARD_MAXIMUM";

    /** ����״̬ ���� */
    public final static String RULE_STATE_ENABLED = "2";

    /** Ӫ������״̬ 0:��Ч 1:��Ч */
    public final static String SELLER_STATE_INACTIVE = "0";
    public final static String SELLER_STATE_ACTIVE = "1";

    /** Ӫ��������ͬ״̬ 0:��Ч 1:��Ч */
    public final static String CONTRACT_STATE_INACTIVE = "0";
    public final static String CONTRACT_STATE_ACTIVE = "1";

    /** �ͻ�״̬ 0:��Ч 1:��Ч */
    public final static String CUST_STATE_INACTIVE = "0";
    public final static String CUST_STATE_ACTIVE = "1";

    /** �û�״̬ 0:��Ч 1:��Ч */
    public final static String USER_STATE_INACTIVE = "0";
    public final static String USER_STATE_ACTIVE = "1";

    /** ����״̬ 0:δ���� 1���Ѽ��� 2�������� 3������ʧ�� */
    public final static String ORDER_ACT_STATE_INACT = "0";
    public final static String ORDER_ACT_STATE_ACT = "1";
    public final static String ORDER_ACT_STATE_ACTING = "2";
    public final static String ORDER_ACT_STATE_ACT_FAILED = "3";
        /**
     * Manager ��߹���Ա��Ӫ��������
     */
    public final static String MANAGEMENT_RESOURCE_ID = "00000000";
    /**
     * �����б��вɹ���ID
     */
    public final static String PURCHASE_RESOURCE_ID = "410100";
    /**
     * ϵͳ��������
     */
    public final static String SYSTEM_PARAMETER_RESOURCE_ID = "60300";
    /**
     * �����ֵ����
     */
    public final static String DICTINFO_RESOURCE_ID = "60400";
    /**
     * ϵͳ��־��ѯ����
     */
    public final static String SYSTEM_LOG_RESOURCE_ID = "60500";
    /**
     * �������ڹ���
     */
    public final static String SETTLE_RESOURCE_ID = "60600";
    /**
     * ����������
     */
    public final static String CAALLINF_RESOURCE_ID = "60700";
    /**�������ϵ����Ƭ��*/
    public final static int MAX_NUM_INVALID=100;
    /**ÿ�ε�����������*/
    public final static String ONE_CARD="1";

    /** ��Ʊ�ս�Ʊ״̬ 0���ѷ��� 1������Ʊ 2���ѽ�Ʊ */
    public final static String INVOICE_ALREADY_DISTRI = "0";
    public final static String INVOICE_ALREADY_RECEIVE = "1";
    public final static String INVOICE_ALREADY_BAND = "2";

    /** ��Ʊ��Ŀ���������� */
    public final static String INVOICE_PROJECT_ALL = "0";
    /** ��Ʊ��Ŀ�����շ���� */
    public final static String INVOICE_PROJECT_SERVICE = "1";
    /** �ֵ����ʹ��� */
    public final static String DICT_TYPE_CODE = "120";
    
}

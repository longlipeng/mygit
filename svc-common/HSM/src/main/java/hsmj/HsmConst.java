package hsmj;
/**
 * 
 * <p>
 * <strong>
 * This class name was HsmConst
 * </strong>
 * </p>
 * @author Lay
 * @date 2010-5-11 03:43:45
 * @version 1.0
 */
public class HsmConst {
    public static final int T_SUCCESS              = 0;
    public static final int T_FAIL                 = 1;
    public static final int MAX_RSA_KEY            = 50;
    // ************************************
    public static final int REPLY_CODE_LEN         = 2;
    public static final int INDEX_LEN              = 3;
    public static final int MODULE_LEN             = 4;
    public static final int FLAG_LEN               = 2;
    public static final int PWD_LEN                = 8;
    public static final int DATA_LEN               = 4;
    public static final int PUBLIC_LEN             = 516;
    public static final int PRIVATE_LEN            = 1416;

    /* 错误码 */
    public static final int ERR_MODULE_LEN         = 0x01;  // 模长错误
    public static final int ERR_PASSWD             = 0x02;  // 口令错误
    public static final int ERR_DATA_LEN           = 0x03;  // 数据长度错误
    public static final int ERR_KEY_LEN            = 0x04;  // 密钥长度错误

    // *************************************

    public static final int MAX_MAC_ELEMENT_LEN    = 400;
    public static final int MAC_ELEMENT_LEN_LEN    = 3;
    public static final int MAC_LEN                = 8;
    public static final int PIN_LEN                = 8;
    public static final int PAN_LEN                = 19;    // 8*2
    public static final int PIK_LEN                = 24 * 2;
    public static final int MAK_LEN                = 24 * 2;
    public static final int CHV_LEN                = 8 * 2;
    public static final int PIN_M_LEN              = 12;
    public static final int PIK_LEN_LEN            = 2;
    public static final int MAK_LEN_LEN            = 2;
    public static final int PIN_LEN_LEN            = 3;
    public static final int PIN_BLK_LEN            = 8 * 2;
    public static final int PWD_BLK_LEN            = 24 * 2;
    public static final int EKEY_INVALID_BMK_INDEX = 0x0c;
    public static final int EKEY_LENGTH            = 0x32;
    public static final int EPIN_LENGTH            = 0x31;
    public static final int MAX_DATA_LEN           = 5000;
    
    
    
    /*
     * 江南科友加密机消息域长度
     */
    
    //E0
    //报文长度
    public static final int E0_HEADER_LEN             = 4;     //消息头长度（长度可变）
    public static final int E0_COMMAND_CODE_LEN       = 2;     //命令代码
    public static final int E0_MESSAGE_BLOCK_NO_LEN   = 1;     //消息块号
    public static final int E0_KEY_MODEL_LEN          = 1;     //密钥模式
    public static final int E0_KEY_ENCRYPTION_MODE_LEN = 1;    //密钥加密模式
    public static final int E0_KEY_TYPE_LEN           = 1;     //密钥类型
    public static final int E0_KEY_LEN                = 33;    //密钥
    public static final int E0_INPUT_MESSAGE_TYPE_LEN = 1;     //输入消息类型
    public static final int E0_OUTPUT_MESSAGE_TYPE_LEN = 1;    //输出消息类型
    public static final int E0_FILL_PATTERN_LEN       = 1;     //填充模式
    public static final int E0_FILL_CHARACTER_LEN     = 4;     //填充字符
    public static final int E0_FILL_TYPE_LEN          = 1;     //填充类型
    public static final int E0_DATA_LENGTH_LEN        = 3;     //消息数据长度
    
    public static final int E0_RESPONSE_CODE_LEN      = 2;     //响应代码
    public static final int E0_REPLY_CODE_LEN         = 2;     //错误代码
    public static final int E0_OUTPUT_MODE_LEN        = 2;     //输出模式
    
    public static final int E0_OUTPUT_DATE_LEN        = 16;     //返回报文长度
    //报文内容
    
    
    //错误码
    public static final String E0_SUCC                   = "00";     //无错误
    public static final String E0_MESSAGE_HEADER_ERROR   = "91";     //消息头不正确
    public static final String E0_RESPONSE_CODE_ERROR    = "92";     //响应代码不正确
    

    public HsmConst() {}
}
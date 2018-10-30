package com.allinfinance.biz.payment.entity;

/**
 * Created by liuheli on 2017/5/11.
 * 交易支付请求参数
 */
public class PaymentRequestInfo{
    private String app_id;//分配给开发者的应用ID（用作商户号）
    private String terminal_sn;//终端序列号
    private String channel;//用于标识报文源头出处
    private String format;//仅支持JSON
    private String charset;//请求使用的编码格式
    private String sign_type;//商户生成签名字符串所使用的签名算法类型
    private String sign;//商户请求参数的签名串，所有请求参数私钥加签
    private String timestamp;//发送请求的时间，格式"yyyy-MM-dd HH:mm:ss"
    private String notify_url;//主动通知商户服务器里指定的页面http/https路径。
    private String out_trade_no;//商户网站唯一订单号
    private String total_amount;//订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]
    private String out_trade_list;//订单描述（因反洗钱监管要求填写资金用途）
    private String card_no;//礼品卡卡号
    private String card_pwd;//礼品卡密码，md5加密（密钥需约定）
    private String ip_address;//对于预付卡网上交易，填写IP地址和MAC地址，之间使用“@”分隔，如果客户使用手机登录网络进行交易，
                                // 可填写手机号码和设备信 息（IMEI、UDID等），中间使用“@”分割
    private String tran_code;

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getCard_no() {
        return card_no;
    }

    public void setCard_no(String card_no) {
        this.card_no = card_no;
    }

    public String getCard_pwd() {
        return card_pwd;
    }

    public void setCard_pwd(String card_pwd) {
        this.card_pwd = card_pwd;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getTerminal_sn() {
        return terminal_sn;
    }

    public void setTerminal_sn(String terminal_sn) {
        this.terminal_sn = terminal_sn;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getOut_trade_list() {
        return out_trade_list;
    }

    public void setOut_trade_list(String out_trade_list) {
        this.out_trade_list = out_trade_list;
    }

    public String getIp_address() {
        return ip_address;
    }

    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }

    public String getTran_code() {
        return tran_code;
    }

    public void setTran_code(String tran_code) {
        this.tran_code = tran_code;
    }
}

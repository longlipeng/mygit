package com.allinfinance.biz.payment.entity;

/**
 * Created by liuheli on 2017/5/23.
 * 交易退款响应参数
 */
public class RefundRespondInfo {
    private String app_id;//分配给开发者的应用ID
    private String terminal_sn;//终端序列号
    private String channel;//用于标识报文源头出处
    private String out_trade_no;//原支付请求的商户订单号
    private String out_refund_no;//商户网站唯一退款单号
    private String refund_amount;//退款总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]
    private String result;//true、false
    private String msg;//错误信息
    private String card_no;//礼品卡卡号
    private String ip_address;////对于预付卡网上交易，填写IP地址和MAC地址，之间使用“@”分隔，如果客户使用手机登录网络进行交易，可填写手机号码和设备信 息（IMEI、UDID等），中间使用“@”分割
    private String create_time;
    private String tran_code;

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

    public String getOut_refund_no() {
        return out_refund_no;
    }

    public void setOut_refund_no(String out_refund_no) {
        this.out_refund_no = out_refund_no;
    }

    public String getRefund_amount() {
        return refund_amount;
    }

    public void setRefund_amount(String refund_amount) {
        this.refund_amount = refund_amount;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTran_code() {
        return tran_code;
    }

    public void setTran_code(String tran_code) {
        this.tran_code = tran_code;
    }

    public String getCard_no() {
        return card_no;
    }

    public void setCard_no(String card_no) {
        this.card_no = card_no;
    }

    public String getTerminal_sn() {
        return terminal_sn;
    }

    public void setTerminal_sn(String terminal_sn) {
        this.terminal_sn = terminal_sn;
    }

    public String getIp_address() {
        return ip_address;
    }

    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}

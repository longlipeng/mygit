package com.allinfinance.biz.payment.entity;

/**
 * Created by liuheli on 2017/5/11.
 * 交易查询响应参数、交易退款查询响应参数
 */
public class QueryRespondInfo {
    private String app_id;//分配给开发者的应用Id
    private String terminal_sn;//终端序列号
    private String channel;//用于标识报文源头出处
    private String trade_no;//通联交易凭证号
    private String out_trade_no;//原支付请求的商户订单号
    private String out_refund_no;//商户网站唯一退款单号
    private String trade_status;//交易目前所处的状态
    private String total_amount;//本次交易支付的订单金额，单位为人民币（元）
    private String create_time;//该笔交易创建的时间。格式为yyyy-MM-dd HH:mm:ss
    private String payment_time;//该笔交易完成的时间。格式为yyyy-MM-dd HH:mm:ss
    private String result;//true、false
    private String msg;//错误信息
    private String card_no;//礼品卡卡号
    private String tran_code;

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getTrade_no() {
        return trade_no;
    }

    public void setTrade_no(String trade_no) {
        this.trade_no = trade_no;
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

    public String getTrade_status() {
        return trade_status;
    }

    public void setTrade_status(String trade_status) {
        this.trade_status = trade_status;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getPayment_time() {
        return payment_time;
    }

    public void setPayment_time(String payment_time) {
        this.payment_time = payment_time;
    }

    public String getTran_code() {
        return tran_code;
    }

    public void setTran_code(String tran_code) {
        this.tran_code = tran_code;
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

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}

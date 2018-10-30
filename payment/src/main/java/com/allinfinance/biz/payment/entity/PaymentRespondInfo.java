package com.allinfinance.biz.payment.entity;

/**
 * Created by liuheli on 2017/5/11.
 * 交易支付响应参数
 */
public class PaymentRespondInfo{
    private String app_id;//分配给开发者的应用ID
    private String terminal_sn;//终端序列号
    private String channel;//用于标识报文源头出处
    private String out_trade_no;//商户网站唯一订单号
    private String total_amount;//订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]
    private String result;//true、false
    private String msg;//错误信息
    private String card_no;//礼品卡卡号
    private String card_balance;//礼品卡余额
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

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
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

    public String getCard_balance() {
        return card_balance;
    }

    public void setCard_balance(String card_balance) {
        this.card_balance = card_balance;
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

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getTran_code() {
        return tran_code;
    }

    public void setTran_code(String tran_code) {
        this.tran_code = tran_code;
    }
}

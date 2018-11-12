package com.allinfinance.biz.payment.entity;

/**
 * Created by liuheli on 2017/5/11.
 * 异步通知参数
 */
public class AsyncNoticeInfo{
    private String notify_time;//通知的发送时间。格式为yyyy-MM-dd HH:mm:ss
    private String app_id;//分配给开发者的应用Id
    private String charset;//通知使用的编码格式
    private String sign_type;//商户生成签名字符串所使用的签名算法类型
    private String sign;//所有通知参数私钥加签
    private String trade_no;//通联交易凭证号
    private String out_trade_no;//原支付请求的商户订单号
    private String trade_status;//交易目前所处的状态
    private String total_amount;//本次交易支付的订单金额，单位为人民币（元）
    private String create_time;//该笔交易创建的时间。格式为yyyy-MM-dd HH:mm:ss
    private String payment_time;//该笔交易完成的时间。格式为yyyy-MM-dd HH:mm:ss
    private String tran_code;
    private String channel;//渠道信息
    private String terminal_sn;//终端
    private String card_no;//礼品卡卡号

    public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}


    public String getNotify_time() {
        return notify_time;
    }

    public void setNotify_time(String notify_time) {
        this.notify_time = notify_time;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
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

    @Override
	public String toString() {
		return "AsyncNoticeInfo [notify_time=" + notify_time + ", app_id=" + app_id + ", charset=" + charset
				+ ", sign_type=" + sign_type + ", sign=" + sign + ", trade_no=" + trade_no + ", out_trade_no="
				+ out_trade_no + ", trade_status=" + trade_status + ", total_amount=" + total_amount + ", create_time="
				+ create_time + ", payment_time=" + payment_time + ", tran_code=" + tran_code + ", channel=" + channel
				+ ", card_no=" + card_no + "]";
	}
}

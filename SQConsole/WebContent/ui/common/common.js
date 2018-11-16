/**
 * 设置强制更新js文件
 */
var httpCacheUtil = {
            createXHR: function () {
                if (typeof XMLHttpRequest != "undefined") {
                    return new XMLHttpRequest();
                }
                else if (typeof ActiveXObject != "undefined") {
                    if (typeof arguments.callee.activeXString != "string") {
                        var versions = ["MSXML2.XMLHttp.6.0", "MSXML2.XMLHttp.3.0", "MSXML2.XMLHttp"];
                        for (var i = 0, len = versions.length; i < len; ++i) {
                            try {
                                var xhr = new ActiveXObject(versions[i]);
                                arguments.callee.activeXString = versions[i];
                                return xhr;
                            }
                            catch (ex) {
                                // pass
                            }
                        }
                    }
                    return new ActiveXObject(arguments.callee.activeXString);
                }
                else {
                    throw new Error("No XHR object available");
                }
            },
            update: function(url){
                try {
                    var success = function(responseText) {
                    
                    };
                    var error = function(errorStatus) {
                    
                    };
                    var xhr = httpCacheUtil.createXHR();
                    if(typeof xhr != "undefined" && xhr != null) {
                        xhr.onreadystatechange = function (event) {
                            if (xhr.readyState == 4) {
                                if ((xhr.status >= 200 && xhr.status < 300) || xhr.status == 304) {
                                    if (typeof success === "function")
                                        success(xhr.responseText);
                                }
                                else {
                                    if (typeof error === "function")
                                        error(xhr.status);
                                }
                            }
                        };
                        xhr.open("GET", url, false);
                        xhr.setRequestHeader("If-None-Match","\"22426f327b8cd1:0\"");
                        xhr.setRequestHeader("If-Modified-Since", "Sat, 31 Dec 2011 02:51:00 GMT");
                        xhr.send(null);
                    }
                }
                catch(e){
                    // throw no exception
                }
            }
};
/**
	 * 操作员状态
	 */
	function oprState(val) {
		if(val == '0') {
			return '<img src="' + Ext.contextPath + '/ext/resources/images/active_16.png" title="可用"/>正常';
		} else if(val == '1') {
			return '<img src="' + Ext.contextPath + '/ext/resources/images/stop_16.png" title="锁定"/>锁定';
		} else if(val == '2') {
			return '<font color="gray">添加待审查</font>';
		}else if(val == '3') {
			return '<font color="gray">修改待审核</font>';
		}else if(val == '4') {
			return '<font color="red">审核不通过</font>';
		}
		return '<font color="red">状态未知</font>';
	}
	
	function all(val){
		if(val=='*'){
			return '全部支持'; 
		}
		else{
			return val;
		}
	}
	
/**
	 * 操作员性别
	 */
	
	function gender(val) {
		if(val == '0') {
			return '<img src="' + Ext.contextPath + '/ext/resources/images/male.png" />';
		} else if(val == '1') {
			return '<img src="' + Ext.contextPath + '/ext/resources/images/female.png" />';
		}
		return val;
	}

	/**
	 * 商户状态转译
	 */
	function mchntSt(val) {
		if(val == '0') {
			return '<font color="green">正常</font>';
		} else if(val == '1') {
			return '<font color="gray">新增待审核</font>';
		} else if(val == '2') {
			return '<font color="gray">新增审核退回</font>';
		} else if(val == '3') {
			return '<font color="gray">修改待审核</font>';
		} else if(val == '5') {
			return '<font color="gray">冻结待审核</font>';
		} else if(val == '6') {
			return '<font color="red">冻结</font>';
		} else if(val == '7') {
			return '<font color="gray">恢复待审核</font>';
		} else if(val == '8') {
			return '<font color="red">注销</font>';
		} else if(val == '9') {
			return '<font color="gray">注销待审核</font>';
		} else if(val == 'I') {
			return '<font color="red">新增复审</font>';
		} else if(val == 'J') {
			return '<font color="red">修改复审</font>';
		}else if(val == 'K') {
			return '<font color="red">冻结复审</font>';
		} else if(val == 'L') {
			return '<font color="red">注销复审</font>';
		} else if(val =='H'){
			return '<font color="red">黑名单</font>';
		}
		return val;
	}

	
	/**
	     * 银行转换
		 */
	 function brhLvlRender(val) {
		 switch(val) {
			case '0': return '总部';
			case '1': return '分公司';
			case '2': return '办事处';
			default : return '未知';
		}
	 }
	 
	 	/**
	     * 翻译是或者否
		 */
	 function yesOrNo(val) {
		 switch(val) {
			case '0': return '是';
			case '1': return '否';
			default : return '未知';
		}
	 }
	     
     /**
	     * 银行转换
		 */
	 function groupType(val) {
		 switch(val) {
			case '1': return '全国性集团';
			case '2': return '地方性集团（省内）';
			default : return '未知';
		}
	 }
	     
     /**
	     * 交易渠道转换
		 */
	 function channelType(val) {
		 switch(val) {
			case '0': return '间联POS';
			case '1': return '否';
			default : return '未知';
		}
	 }
     /**
	     * 卡类型转换
		 */
	 function cardType(val) {
		 switch(val) {
			case '01': return '借记卡';
			case '00': return '贷记卡';
			case '02': return '准贷记卡';
			case '03': return '预付卡';
			case '*' : return '支持所有类型';
//			case '00': return '他行银联卡';
//			case '01': return '本行借记卡';
//			case '03': return '本行一帐通';
//			case '04': return '本行贷记卡';
			default : return '未知卡';
		}
	 }
	     
     /**
	     * 交易名称转换
		 */
	 function funType(val) {
		 switch(val) {
			case '1101': return   '消费';
			case '1161': return   '查询';
			case '5151': return   '退货';
			case '2101': return   '消费冲正';
			case '3101': return   '消费撤消';
			case '4101': return   '撤消冲正';
			case '1011': return   '预授权';
			case '2011': return   '预授权冲正';
			case '3011': return   '预授权撤消';
			case '4011': return   '预授权撤消冲正';
			case '1091': return   '联机预授权完成';
			case '2091': return   '联机预授权完成冲正';
			case '3091': return   '联机预授权完成撤消';
			case '4091': return   '联机预授权完成撤消冲正';
			case '1111': return   '分期付款';
			case '2111': return   '分期付款冲正';
			case '1171': return   '积分查询';
			case '1121': return   '积分消费';
			case '2121': return   '积分消费冲正';
			case '3121': return   '积分撤消';
			case '4121': return   '积分撤消冲正';
			case '5161': return   '离线预授权完成';
			case '1131': return   '财务转账';
			case '3131': return   '转账撤销';
			case '1001': return   '明细查询';
			case '1000': return   '余额查询';
			default : return '未知交易类型';
		}
	 }
	     
     /**
	     * 服务等级转换
		 */
	 function svrLvlType(val) {
		 switch(val) {
			case '0': return   'VIP';
			case '1': return   '重点';
			case '2': return   '普通';
			default : return '未知卡';
		}
	 }
	 	
	 //终端库存状态
	 function translateState(val) {
//			if(val == '0')
//				return "已入库";
			if(val == '1')
				return "已作废";
//			if(val == '2')
//				return "下发未审核";
//			if(val == '3')
//				return "下发未审核";
			if(val == '4')
				return "已入库";
			if(val == '5')
				return "已出库";
			return val;
		}
	// 终端类型
	function termType(val) {
			if(val == 'P')
				return "POS";
			if(val == 'E')
				return "EPOS";
			if(val == 'A')
				return "ATM";
			return val;
		}
    // 终端状态
    function termSta(val) {
            if(val == '0')
                return "新增未审核";
            if(val == '1')
                return "启用";
            if(val == '2')
                return "修改未审核";
            if(val == '3')
                return "停用未审核";
            if(val == '4')
                return "停用";
            if(val == '5')
                return "恢复未审核";
            if(val == '6')
                return "注销未审核";
            if(val == '7')
                return "注销";
            if(val == '8')
                return "新增审核拒绝";
            if(val == '9')
                return "修改审核拒绝";
            if(val == 'A')
                return "停用审核拒绝";
            if(val == 'B')
                return "恢复审核拒绝";
            if(val == 'C')
                return "注销审核拒绝";
            if(val == 'D')
                return "复制未修改";
            return val;
        }
    // 终端签到状态
	function termSignSta(val) {
			if(val == '0')
				return "未签到";
			if(val == '1')
				return "已签到";
			return val;
		}
    function termState(val) {
        if(val == '0')
            return "已申请";
        if(val == '1')
            return "已审核";
        return val;
    }   
    
    function clcAction(val) {
        if(val == '0')
            return "<font color='green'>警告</font>";
        if(val == '1')
            return "<font color='red''>拒绝</font>";
      /*  if(val == '3')
            return "<font color='blue'>预警</font>";*/
        return val;
    }
    
    
    function settleFlag(val) {
        if(val == '0')
            return "未处理";
        if(val == '1')
            return "<font color='red'>划拨失败</font>";
        if(val == '2')
            return "<font color='green'>划拨成功</font>";
        return val;
    }
    function actState(val) {
        if(val == '0')
            return "<font color='green'>正常</font>";
        if(val == '1')
            return "未审核";
        if(val == '2')
            return "<font color='red'>关闭</font>";
        return val;
    }

    function actOprState(val){
    	if(val == '2')
            return "关闭";
    	if(val == '1')
    		return "修改";
    	if(val == '0')
    		return "新增";
    	return "";
    }
    
    function mchntOprState(val){
    	if(val == '2')
            return "删除";
    	if(val == '1')
    		return "追加";
    	if(val == '0')
    		return "新增";
    	return val;
    }
    
    function riskInfoState(val){
    	if(val == '0')
            return "<font color='gray'>新增待审核</font>";
    	if(val == '1')
            return "<font color='red'>已删除</font>";
    	if(val == '2')
            return "<font color='green'>正常</font>";
    	if(val == '3')
            return "<font color='gray'>修改待审核</font>";
    	if(val == '4')
    		return "<font color='gray'>删除待审核</font>";
    	if(val == '5')
            return "<font color='red'>审核拒绝</font>";
    }
    function riskInfoRefuseState(val){
    	if(val == '0')
            return "<font color='gray'>新增审核拒绝</font>";
    	if(val == '1')
            return "<font color='red'>已删除</font>";
    	if(val == '2')
            return "<font color='green'>正常</font>";
    	if(val == '3')
            return "<font color='gray'>修改审核拒绝</font>";
    	if(val == '4')
    		return "<font color='gray'>删除审核拒绝</font>";
    	if(val == '5')
    		return "<font color='gray'>新增审核通过</font>";
    	if(val == '6')
    		return "<font color='gray'>删除审核通过</font>";
    	if(val == '7')
    		return "<font color='gray'>修改审核通过</font>";
    }
    /**
     * 清分状态
     * @param val
     * @return
     */
    function errTxnStlmFlg(val){
    	switch(val) {
	    	case '0': return   '未清分';
			case '1': return   '已清分';
			case '2': return   '手工处理';
			case '3': return   '手工处理';
			default : return '未知';
    	}
    }
    
    /**
     * 机构状态
     * @param val
     * @return
     */
    function agencyState(val){
    	if(val == '0')
            return "新增审核拒绝";
    	if(val == '2')
            return "修改审核拒绝";
    	if(val == '8')
            return "注销审核拒绝";
    }
   
    /**
     * 追扣的审核状态
     * @param val
     * @return
     */
    function cutSaState(val){
    	switch(val) {
	    	case '0': return   '追扣待审核';
			case '1': return   '已追扣';
			case '2': return   '追扣拒绝';
			default : return '已清算';
    	}
    }
    
    function errFlag(val){
    	switch(val) {
		case 'E05': return   '调单回复';
		case 'E22': return   '请款';
		case 'E23': return   '退单';
		case 'E24': return   '再请款';
		case 'E25': return   '二次退单';
		case 'E32': return   '贷记调整';
		case 'E33': return   '一般转帐转入贷记调整';
		case 'E34': return   '一般转帐转出贷记调整';
		case 'E35': return   '一般转帐转入对贷记调整的请款';
		case 'E36': return   '一般转帐转出请款';
		case 'E73': return   '差错例外';
		case 'E74': return   '能查找到原始交易的手工退货';
		case 'E84': return   '不能查找到原始交易的手工退货';
		case 'E80': return   '贷记调整（存入类）';
		case 'H01': return   '追扣';
		case '2': return   '交易挂账';
		case '3': return   '交易解挂';
		case '5221': return   '补电子现金消费';
		default : return '未知';
    	}
    }
    
    function agencyInfo(val){
    	/*if(val == '1701')
            return "中行通道";*/
    	if(val == '1702')
            return "1702-翰鑫通道";
    	/*if(val == '1703')
            return "农行通道";
    	if(val == '1704')
            return "交行通道";
        if(val == '1705')
            return "建行通道";
    	if(val == '1601')
            return "银联通道";*/
    	if(val == '1708')
    		return "1708-上汽通道";
    }

    function chgFlag(val){
    	if(val == '0')
            return "<font color='green'>不切换</font>";
    	if(val == '1')
            return "<font color='red'>切换</font>";
    }
    
    /*16进制转出10进制*/
	function DecToHex(val) {
		var i1 = parseInt(val.substr(0,2),16);  
		var i2 = parseInt(val.substr(2,4),16);  
		
		if((i1+'').length < 2) {
			i1 = '0' + i1;
		}
		if((i2+'').length < 2) {
			i2 = '0' + i2;
		}
		
		return (i1 + '') + (i2 + '') ;
		
	}
	
	//运行状态
	function runState(val){
    	if(val == '0')
            return "<font color='blue'>待运行</font>";
    	if(val == '1')
            return "<font color='blue'>运行中</font>";
    	if(val == '2')
            return "<font color='green'>运行成功</font>";
    	if(val == '3')
            return "<font color='red'>运行失败</font>";
    	if(val == '4')
    		return "<font color='gray'>节假日不操作</font>";
    }
		
package com.huateng.framework.util;

import com.allinfinance.framework.constant.ConstCode;



/**
 * <p>Title: Accor</p>
 *
 * <p>Description:Accor Project 1nd Edition </p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: </p>
 * @author YY
 * @version 1.0
 */

public class TxnCodeBean {
	private static final String NOTNEED = "0";
	private static final String NEED = "1";

    /**
     * <p>Description: 定义丄1�7个前台交易代码String二维数组，二维数组中每一个元素定义了丄1�7个交易代码已经这个交易代码需要执行的操作〄1�7
     * String数组中，第一元素是交易代码，第二元素标识是否校验用户密码，第三参数标识是否记录操作员操作记录
     * 例如，{"103001", NEED, NEED}表示代码丄1�73001的这个交易，霄1�7要校验密码，霄1�7要在系统数据库中记录操作结果</p>
     * 
     */
    private static String[][] txncodes = {
            {ConstCode.FRTCODE_SERVICE_ADD, NEED, NEED},
            {ConstCode.FRTCODE_SERVICE_MOD, NOTNEED, NOTNEED},
            {ConstCode.FRTCODE_SERVICE_ORDER_LIST, NEED, NEED},
    };
    
    public static String[] getTxnCodeAction(String txnCode){
    	int index = 0;
    	String[] rstNull = {"", "", ""};
    	
    	if (txnCode == null || txnCode.trim().length() == 0)
    		return rstNull;
    		
    	for (; index < txncodes.length; index++){
    		if (txnCode.equals(txncodes[index][0]))
    			return txncodes[index];
    	}
   		return rstNull;
    }

}

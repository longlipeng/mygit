package com.huateng.hstserver.constants;


public class CallcReturnErrorCode {
	public final static String SUCCESS = "00";

	public static String getErrorCode(String repsCode) {
		if (repsCode.equals(MemberConstant.INVALID_MCHNT)) {
			String errorCode = "无效商户！";
			return errorCode;
		}
		if (repsCode.equals(MemberConstant.INVALID_CONTRACT)) {
			String errorCode = "无效合同！";
			return errorCode;
		}
		if (repsCode.equals(MemberConstant.INVALID_TRADE)) {
			String errorCode = "无效交易！";
			return errorCode;
		}
		if (repsCode.equals(MemberConstant.CARD_NOT_ACTIVE)) {
			String errorCode = "卡未激活！";
			return errorCode;
		}
		if (repsCode.equals(MemberConstant.CARDNO_DISAGREE)) {
			String errorCode = "与原交易卡号不符！";
			return errorCode;
		}
		if (repsCode
				.equals(MemberConstant.ACC_MISMATCHING)) {
			String errorCode = "账户不匹配！";
			return errorCode;
		}
		if (repsCode
				.equals(MemberConstant.UNFIND_ORIGINAL_TRADE_MISTAKE)) {
			String errorCode = "未找到原交易！";
			return errorCode;
		}
		if (repsCode.equals(MemberConstant.ORIGINAL_TRADE_NOT_SUCCESSFUL)) {
			String errorCode = "原交易不成功！";
			return errorCode;
		}
		if (repsCode.equals(MemberConstant.PERMISSION_DENIED)) {
			String errorCode = "权限不足！";
			return errorCode;
		}

		if (repsCode.equals(MemberConstant.CARD_LOCKED)) {
			String errorCode = "卡已锁定！";
			return errorCode;
		}
		if (repsCode.equals(MemberConstant.CARD_LOSSED)) {
			String errorCode = "卡已挂失！";
			return errorCode;
		}
		if (repsCode.equals(MemberConstant.INVALID_ACC)) {
			String errorCode = "无效账户！";
			return errorCode;
		}
		if (repsCode.equals(MemberConstant.CARD_OFF)) {
			String errorCode = "卡被注销!";
			return errorCode;
		}
		if (repsCode.equals(MemberConstant.CARD_FREEZED)) {
			String errorCode = "卡被冻结!";
			return errorCode;
		}
		if (repsCode.equals(MemberConstant.BALANCE_LACK)) {
			String errorCode = "余额不足!";
			return errorCode;
		}
		if (repsCode.equals(MemberConstant.EXPIRE_CARD)) {
			String errorCode = "过期的卡!";
			return errorCode;
		}
		if (repsCode.equals(MemberConstant.PASSWORD_ERROR)) {
			String errorCode = "密码错!";
			return errorCode;
		}
		if (repsCode.equals(MemberConstant.UNFIND_CARD_RECORD)) {
			String errorCode = "无此卡记录!";
			return errorCode;
		}
		if (repsCode.equals(MemberConstant.BALANCE_ERROR)) {
			String errorCode = "余额不正确!";
			return errorCode;
		}
		
		if (repsCode.equals(MemberConstant.ORIGTRADE_AMOUNT_DISCREPANT)) {
			String errorCode = "与原交易金额不符!";
			return errorCode;
		}
		if (repsCode.equals(MemberConstant.CONSUMER_TIMES_OVER)) {
			String errorCode = "消费次数超限!";
			return errorCode;
		}
		if (repsCode.equals(MemberConstant.RECHARGE_TIMES_OVER)) {
			String errorCode = "充值次数超限!";
			return errorCode;
		}
		if (repsCode.equals(MemberConstant.WEB_SINGE_AMT_OVER)) {
			String errorCode = "网上单笔交易金额超限!";
			return errorCode;
		}
		if (repsCode.equals(MemberConstant.WEB_DAY_AMT_OVER)) {
			String errorCode = "网上当天累计交易金额超限!";
			return errorCode;
		}
		if (repsCode.equals(MemberConstant.ERR_POSP_INVALID_CVV2)) {
			String errorCode = "cvv2不正确!";
			return errorCode;
		}
		if (repsCode.equals(MemberConstant.PASSWORD_ERR_TIMES_OVER)) {
			String errorCode = "密码错误次数超限!";
			return errorCode;
		}
		if (repsCode.equals(MemberConstant.RECHARGE_TIMES_OVER)) {
			String errorCode = "充值次数超限!";
			return errorCode;
		}
		if (repsCode.equals(MemberConstant.SYSTEM_BATCHING)) {
			String errorCode = "系统正在批处理日切中!";
			return errorCode;
		}
		if (repsCode.equals(MemberConstant.TRADE_REPEAD)) {
			String errorCode = "重复交易!";
			return errorCode;
		}
		if (repsCode.equals(MemberConstant.INVAILD_TERM)) {
			String errorCode = "无效终端!";
			return errorCode;
		}
		if (repsCode.equals(MemberConstant.SYSTEM_ERROR)
				|| repsCode.equals(MemberConstant.RESP_CODE_IS_NULL)) {
			String errorCode = "后台系统错误!";
			return errorCode;
		} else {
			String errorCode = "后台系统错误!";
			return errorCode;
		}
	}

}

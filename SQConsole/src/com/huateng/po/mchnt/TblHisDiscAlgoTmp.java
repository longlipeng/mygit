package com.huateng.po.mchnt;

import com.huateng.po.mchnt.base.BaseTblHisDiscAlgo;



public class TblHisDiscAlgoTmp extends BaseTblHisDiscAlgo {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public TblHisDiscAlgoTmp () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public TblHisDiscAlgoTmp (TblHisDiscAlgoPK id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public TblHisDiscAlgoTmp (
		TblHisDiscAlgoPK id,
		java.math.BigDecimal minFee,
		java.math.BigDecimal maxFee,
		java.math.BigDecimal floorMount,
		java.math.BigDecimal upperMount,
		java.lang.Integer flag,
		java.math.BigDecimal feeValue,
		java.lang.String recUpdUsrId,
		java.lang.String recUpdTs,
		java.lang.String recCrtTs,
		java.lang.String stxnNum) {

		super (
			id,
			minFee,
			maxFee,
			floorMount,
			upperMount,
			flag,
			feeValue,
			recUpdUsrId,
			recUpdTs,
			recCrtTs,
			stxnNum);
	}

/*[CONSTRUCTOR MARKER END]*/


}
package com.huateng.po.settle;

import com.huateng.po.settle.base.BaseTblMchtSumrzInf;



public class TblMchtSumrzInf extends BaseTblMchtSumrzInf {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public TblMchtSumrzInf () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public TblMchtSumrzInf (java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public TblMchtSumrzInf (
		java.lang.Integer id,
		java.lang.String mchtNo) {

		super (
			id,
			mchtNo);
	}

/*[CONSTRUCTOR MARKER END]*/


}
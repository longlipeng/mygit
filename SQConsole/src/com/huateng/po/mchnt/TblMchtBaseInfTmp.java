package com.huateng.po.mchnt;

import com.huateng.po.mchnt.base.BaseTblMchtBaseInfTmp;

public class TblMchtBaseInfTmp extends BaseTblMchtBaseInfTmp {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public TblMchtBaseInfTmp () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public TblMchtBaseInfTmp (java.lang.String mchtNo) {
		super(mchtNo);
	}

	/**
	 * Constructor for required fields
	 */
	public TblMchtBaseInfTmp (
		java.lang.String mchtNo,
		java.lang.String mchtNoHx,
		java.lang.String mchtNm,
		java.lang.String addr,
		java.lang.String mcc,
		java.lang.String licenceNo,
//		java.lang.String licenceEndDate,
		java.lang.String bankLicenceNo,
		java.lang.String faxNo,
		java.lang.String uscCode,
		java.lang.String organizationType,
		java.lang.String isSyncretic,
		java.lang.String contact,
		java.lang.String commEmail,
		java.lang.String manager,
		java.lang.String identityNo,
		java.lang.String managerTel,
		java.lang.String openTime,
		java.lang.String closeTime,
		java.lang.String recUpdTs,
		java.lang.String recCrtTs,
		java.lang.String uscCodeDate,
		java.lang.String licenceNoDate,
		java.lang.String shareholder,
		java.lang.String idshareholderTpmd,
		java.lang.String idshareholder,
		java.lang.String shareholderDate,
		java.lang.String identityNoDate,
		java.lang.String contactmd,
		java.lang.String legalGender,
		java.lang.String legalAddr,
		java.lang.String legalProfession,
		java.lang.String foreignName,
		java.lang.String riskGrade,
		java.lang.String custNo,
		java.lang.String linkTelDate,
		java.lang.String operateAddr) {

		super (
			mchtNo,
			mchtNoHx,
			mchtNm,
			addr,
			mcc,
			licenceNo,
//			licenceEndDate,
			bankLicenceNo,
			faxNo,
			uscCode,
			organizationType,
			isSyncretic,
			contact,
			commEmail,
			manager,
			identityNo,
			managerTel,
			openTime,
			closeTime,
			recUpdTs,
			recCrtTs,
			uscCodeDate,
			licenceNoDate,
			shareholder,
			idshareholderTpmd,
			idshareholder,
			shareholderDate,
			identityNoDate,
			contactmd,
			legalGender,
			legalAddr,
			legalProfession,
			linkTelDate,
			foreignName,
			custNo,
			riskGrade,
			operateAddr);
	}

/*[CONSTRUCTOR MARKER END]*/


}
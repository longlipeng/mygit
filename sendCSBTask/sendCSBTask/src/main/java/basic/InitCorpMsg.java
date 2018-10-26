package basic;

import java.util.ArrayList;
import java.util.HashMap;

import service.CopProPertiesUtil;

/**
 * 初始化企业信息
 * 
 * @author dc
 */
public class InitCorpMsg {

	public ArrayList<HashMap<String, String>> initCopInfo(String uniqueNo) {
		
		//CSB url
		String url = CopProPertiesUtil.getProperties().getProperty("url");
		ArrayList<HashMap<String, String>> corpInfoList = new ArrayList<HashMap<String, String>>();
		// 存放公司信息map
		HashMap<String, String> elemen = new HashMap<String, String>();
		// 联网发行唯一标识
		elemen.put("uniqueNo", CopProPertiesUtil.getProperties().getProperty("uniqueNo"));
		// 调用csb的url
		elemen.put("url", url);
		// 连接csb发布服务的ak sk
		elemen.put("ak", CopProPertiesUtil.getProperties().getProperty("ak"));
		elemen.put("sk", CopProPertiesUtil.getProperties().getProperty("sk"));
		// 私钥
//MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAL1XRG6gjOjl51D/itqzwtYwVRy1MEaSUf7v7n/GVyDbD7knEpvAREbQ/osijSDioNmf+q7KQcmSxtXCsd11LWlXyi7CKyYDVkIguecCg5NQ+FfsaoV1psC7FGGuIIKxLgNeG01+e/Er7GEOV07yfwz5WhtOqJBD6Om8fy8ppnZfAgMBAAECgYEAs5uMtsRl5qfv/c3rEftj33qJwmm+GbPDRGqle1/SVsB28dQ2urhwDB1E2Qc1iPRyUP6I9jW2olgxdaTyxHjbUWaTyH8TKtr8/KoZrAtAZ03kxacPKiqeCU8q5EX1el20UtpsqorLCnZ19oHYxAi701T70ZOlrUf4kCZf0A9BTkECQQD0aIlCVZCQFLH7H7cGn99dt5N6/G+PCViXnjnGFCsEJ/9D784tXSujNUKO5vu09yQYtF/zkJMCcCBOAI/5U6+LAkEAxlIiV25djbXBIwoip5pcflq+qYuIEuwf1xUXduG+8GW2j0BUbWUunbLADRBOGFa98xDmvnPt6fvuscwcNhUu/QJASfiVilERRrosv99yanvag6e66B8cuILQoFrQC4YxGrnsRSiAEzS+r0/ATDXWeofOEfgNjk70X2mGLXV4HbLZTwJBAMYmwItNCYqSxIPHF7MQp0PUVVY/xNjrNjEHsOcmTUoSxqJA61pWU4pbVEp5NR5aZPXcuEAowtkb0HWzc6iR590CQETrDSS1uv1P0njT127xOfnd7O3Au8cHouuuLKMh93PdPsz+qMznBIrQm1fdvSSYdjoprhY3i58K/VdFEysi98Q=
		elemen.put("privateKey",
				CopProPertiesUtil.getProperties().getProperty("privateKey"));
		corpInfoList.add(elemen);
		return corpInfoList;
	}
}

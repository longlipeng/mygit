package basic;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 初始化企业信息
 * 
 * @author dc
 */
public class InitCorpMsg {

	public ArrayList<HashMap<String, String>> initCopInfo(String uniqueNo) {
		//CSB url
		String url = "http://101.132.39.129:8086/CSB";
		ArrayList<HashMap<String, String>> corpInfoList = new ArrayList<HashMap<String, String>>();
		// 存放公司信息map
		HashMap<String, String> elemen = new HashMap<String, String>();
		// 联网发行唯一标识
		elemen.put("uniqueNo", uniqueNo);
		// 调用csb的url
		elemen.put("url", url);
		// 连接csb发布服务的ak sk
		elemen.put("ak", "e7ec2742cad4432d9700ecb8d56d28a0");
		elemen.put("sk", "y8ONI4Ifx4iWreeOJGRBkK+Im2o=");
		// 私钥
		elemen.put("privateKey",
				"MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAK0eGzqy6WLtmrBAzWhU/mgt0/wr/7gu9pCwBCI7XLosXz253erqWQa9Z6boBylDNvcVqtERF9ff9Zs5ybZVcNUAdyfMCC7bllaM5ZXknAgl9ZmOyFMAWwhLGrqKhJoxGPSD0GMlxio3txfh8g3mYVqQCP3y8vlgRqlJAqTbOIVhAgMBAAECgYBG4/44gk0qEqx2ooK6x5tKZAHb0NStwqV7SGjY78AgCM2ThEpIyWBdbzBD294a2ohmk/vYEwzVfMOhpf9g6AoEsgxL1Qli5Z6+JeQtqy5xvXzs2dfEfrcOMdR/PeDu6veUfMNcxJcdpxHLm/GcF70EJTApfp382FIxLeqlGpBFFQJBAOmTa+F07sEEj8mwX1UKlhndjjYSFSpFDKzntt/jTr/PpJsREe+xkrPndI/sLk4nf6YqNvlVr0V/Rme2YsDdkH8CQQC9vM5mXJK2SeRnZpdvqKyDSaxA9pbBP06XAn94c/A4qtDw3dPgvElP1BOiycRz4ZAHqGXl5u+r2UH4IGW6uPofAkEAyq7PLUvPphQ0RXrTrrLJ/1XfApJ8ZTqXvi4v5WWUvyMKnjiYzNrVB2GwJvd2UwE5ZlI5c/Djb7X6vSJp3RwmNwJBALSH8kdZK8tAcY+lANAgSMy+i6RgPD7xUoDCwrDEd7wjY5zbJF+AJ61KF8jbfA3agCWZKCNg4Yi8tlU11jWdDXcCQFvpZWMUIH0NLGcBUMWj0kv3eO9XwlvBMUqFCPmnPfDrdq28ALPVknvCLQVEXr+K0d1YTb9fUmBffYODf+ES0WU=");
		corpInfoList.add(elemen);
		return corpInfoList;
	}
}

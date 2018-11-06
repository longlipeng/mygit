package hsmj;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

public class HsmStruct {
	public HsmStruct() {

	}

	/**
	 * 
	 * <p>
	 * <strong> This class name was GenerateRsaKey </strong>
	 * </p>
	 * 
	 * @author Lay
	 * @date 2010-5-11 03:46:29
	 * @version 1.0
	 */
	public class GenerateRsaKey {
		public class GenerateRsaKeyIn {
			public byte[] modeLen = new byte[HsmConst.MODULE_LEN];

			public byte[] rsaIndex = new byte[HsmConst.INDEX_LEN];

			public byte[] flag = new byte[HsmConst.FLAG_LEN];

			public byte[] passwd = new byte[HsmConst.PWD_LEN];

			public GenerateRsaKeyIn(byte[] modeLen, byte[] rsaIndex, byte[] flag, byte[] passwd) {
				System.arraycopy(modeLen, 0, this.modeLen, 0, HsmConst.MODULE_LEN);
				System.arraycopy(rsaIndex, 0, this.rsaIndex, 0, HsmConst.INDEX_LEN);
				System.arraycopy(flag, 0, this.flag, 0, flag.length);
				System.arraycopy(passwd, 0, this.passwd, 0, passwd.length);
			}
		}

		/**
		 * 
		 * <p>
		 * <strong> This class name was GenerateRsaKeyOut </strong>
		 * </p>
		 * 
		 * @author Lay
		 * @date 2010-5-11 03:46:39
		 * @version 1.0
		 */
		public class GenerateRsaKeyOut {
			public byte[] reply_code = new byte[HsmConst.REPLY_CODE_LEN];

			public byte[] publicKeyLen = new byte[HsmConst.DATA_LEN];

			public byte[] privateKeyLen = new byte[HsmConst.DATA_LEN];

			public byte[] publicKey = new byte[HsmConst.PUBLIC_LEN];

			public byte[] privateKey = new byte[HsmConst.PRIVATE_LEN];
		}
	}

	/**
	 * 
	 * <p>
	 * <strong> This class name was PublicKeyEncrypt </strong>
	 * </p>
	 * 
	 * @author Lay
	 * @date 2010-5-11 03:46:49
	 * @version 1.0
	 */
	public class PublicKeyEncrypt {
		public class PublicKeyEncryptIn {
			public byte[] rsaIndex = new byte[HsmConst.INDEX_LEN];

			public byte[] publicKeyLen = new byte[HsmConst.DATA_LEN];

			public byte[] dataLen = new byte[HsmConst.DATA_LEN];

			public byte[] padFlag = new byte[HsmConst.FLAG_LEN];

			public byte[] athFlag = new byte[HsmConst.FLAG_LEN];

			public byte[] passwd = new byte[HsmConst.PWD_LEN];

			public byte[] publicKey = new byte[HsmConst.PUBLIC_LEN];

			public byte[] encData = new byte[HsmConst.PRIVATE_LEN];

			public PublicKeyEncryptIn(byte[] rsaIndex, byte[] publicKeyLen, byte[] dataLen,
					byte[] padFlag, byte[] athFlag, byte[] passwd, byte[] publicKey, byte[] encData) {
				System.arraycopy(rsaIndex, 0, this.rsaIndex, 0, HsmConst.INDEX_LEN);
				System.arraycopy(publicKeyLen, 0, this.publicKeyLen, 0, HsmConst.DATA_LEN);
				System.arraycopy(dataLen, 0, this.dataLen, 0, HsmConst.DATA_LEN);
				System.arraycopy(padFlag, 0, this.padFlag, 0, padFlag.length);
				System.arraycopy(athFlag, 0, this.athFlag, 0, athFlag.length);
				System.arraycopy(passwd, 0, this.passwd, 0, HsmConst.PWD_LEN);
				System.arraycopy(publicKey, 0, this.publicKey, 0, publicKey.length);
				System.arraycopy(encData, 0, this.encData, 0, encData.length);
			}
		}

		public class PublicKeyEncryptOut {
			public byte[] reply_code = new byte[HsmConst.REPLY_CODE_LEN];

			public byte[] dataLen = new byte[HsmConst.DATA_LEN];

			public byte[] data = new byte[HsmConst.PRIVATE_LEN];
		}

	}

	/**
	 * 
	 * <p>
	 * <strong> This class name was PrivateKeyEncrypt </strong>
	 * </p>
	 * 
	 * @author Lay
	 * @date 2010-5-11 03:46:59
	 * @version 1.0
	 */
	public class PrivateKeyEncrypt {
		public class PrivateKeyEncryptIn {
			public byte[] rsaIndex = new byte[HsmConst.INDEX_LEN];

			public byte[] privateKeyLen = new byte[HsmConst.DATA_LEN];

			public byte[] dataLen = new byte[HsmConst.DATA_LEN];

			public byte[] padFlag = new byte[HsmConst.FLAG_LEN];

			public byte[] athFlag = new byte[HsmConst.FLAG_LEN];

			public byte[] passwd = new byte[HsmConst.PWD_LEN];

			public byte[] privateKey = new byte[HsmConst.PRIVATE_LEN];

			public byte[] encData = new byte[HsmConst.PRIVATE_LEN];

			public PrivateKeyEncryptIn(byte[] rsaIndex, byte[] privateKeyLen, byte[] dataLen,
					byte[] padFlag, byte[] athFlag, byte[] passwd, byte[] privateKey, byte[] decData) {
				System.arraycopy(rsaIndex, 0, this.rsaIndex, 0, HsmConst.INDEX_LEN);
				// System.arraycopy(privateKeyLen, 0, this.privateKey, 0,
				// HsmConst.DATA_LEN)
				System.arraycopy(privateKeyLen, 0, this.privateKeyLen, 0, HsmConst.DATA_LEN);
				System.arraycopy(dataLen, 0, this.dataLen, 0, HsmConst.DATA_LEN);
				System.arraycopy(padFlag, 0, this.padFlag, 0, HsmConst.FLAG_LEN);
				System.arraycopy(athFlag, 0, this.athFlag, 0, athFlag.length);
				System.arraycopy(passwd, 0, this.passwd, 0, HsmConst.PWD_LEN);
				System.arraycopy(privateKey, 0, this.privateKey, 0, privateKey.length);
				System.arraycopy(decData, 0, this.encData, 0, decData.length);
			}
		}

		public class PrivateKeyEncryptOut {
			public byte[] reply_code = new byte[HsmConst.REPLY_CODE_LEN];

			public byte[] dataLen = new byte[HsmConst.DATA_LEN];

			public byte[] data = new byte[HsmConst.PRIVATE_LEN];
		}
	}

	public class Sign {
		public class SignIn {
			public byte[] rsaIndex = new byte[HsmConst.INDEX_LEN];

			public byte[] privateKeyLen = new byte[HsmConst.DATA_LEN];

			public byte[] dataLen = new byte[HsmConst.DATA_LEN];

			public byte[] signFlag = new byte[HsmConst.FLAG_LEN];

			public byte[] athFlag = new byte[HsmConst.FLAG_LEN];

			public byte[] passwd = new byte[HsmConst.PWD_LEN];

			public byte[] privateKey = new byte[HsmConst.PRIVATE_LEN];

			public byte[] data = new byte[HsmConst.PRIVATE_LEN];

			public SignIn(byte[] rsaIndex, byte[] privateKeyLen, byte[] dataLen, byte[] signFlag,
					byte[] athFlag, byte[] passwd, byte[] privateKey, byte[] data) {
				System.arraycopy(rsaIndex, 0, this.rsaIndex, 0, HsmConst.INDEX_LEN);
				System.arraycopy(privateKeyLen, 0, this.privateKeyLen, 0, HsmConst.DATA_LEN);
				System.arraycopy(dataLen, 0, this.dataLen, 0, HsmConst.DATA_LEN);
				System.arraycopy(signFlag, 0, this.signFlag, 0, signFlag.length);
				System.arraycopy(athFlag, 0, this.athFlag, 0, athFlag.length);
				System.arraycopy(passwd, 0, this.passwd, 0, HsmConst.PWD_LEN);
				// System.arraycopy(privateKey, 0, this.privateKey, 0,
				// HsmConst.PRIVATE_LEN);
				// System.arraycopy(data, 0, this.data, 0,
				// HsmConst.PRIVATE_LEN);
				System.arraycopy(privateKey, 0, this.privateKey, 0, privateKey.length);
				System.arraycopy(data, 0, this.data, 0, data.length);
			}
		}

		public class SignOut {
			public byte[] reply_code = new byte[HsmConst.REPLY_CODE_LEN];

			public byte[] dataLen = new byte[HsmConst.DATA_LEN];

			public byte[] data = new byte[HsmConst.PRIVATE_LEN];
		}
	}

	public class VerifySign {
		public class VerifySignIn {
			public byte[] rsaIndex = new byte[HsmConst.INDEX_LEN];

			public byte[] publicKeyLen = new byte[HsmConst.DATA_LEN];

			public byte[] signDataLen = new byte[HsmConst.DATA_LEN];

			public byte[] signCodeLen = new byte[HsmConst.DATA_LEN];

			public byte[] signFlag = new byte[HsmConst.FLAG_LEN];

			public byte[] athFlag = new byte[HsmConst.FLAG_LEN];

			public byte[] passwd = new byte[HsmConst.PWD_LEN];

			public byte[] publicKey = new byte[HsmConst.PUBLIC_LEN];

			public byte[] data = new byte[HsmConst.PRIVATE_LEN];

			public byte[] sign = new byte[HsmConst.PUBLIC_LEN];

			public VerifySignIn(byte[] rsaIndex, byte[] publicKeyLen, byte[] signDataLen,
					// byte[] signCodeLen, byte[] signFlag, byte[] passwd,
					byte[] signCodeLen, byte[] signFlag, byte[] athFlag, byte[] passwd,
					byte[] publicKey, byte[] data, byte[] sign) {
				System.arraycopy(rsaIndex, 0, this.rsaIndex, 0, HsmConst.INDEX_LEN);
				System.arraycopy(publicKeyLen, 0, this.publicKeyLen, 0, HsmConst.DATA_LEN);
				System.arraycopy(signDataLen, 0, this.signDataLen, 0, HsmConst.DATA_LEN);
				System.arraycopy(signCodeLen, 0, this.signCodeLen, 0, HsmConst.DATA_LEN);
				System.arraycopy(signFlag, 0, this.signFlag, 0, signFlag.length);
				System.arraycopy(athFlag, 0, this.athFlag, 0, athFlag.length);
				System.arraycopy(passwd, 0, this.passwd, 0, HsmConst.PWD_LEN);
				System.arraycopy(publicKey, 0, this.publicKey, 0, publicKey.length);
				System.arraycopy(data, 0, this.data, 0, data.length);
				// System.arraycopy(sign, 0, this.sign, 0, sign.length);
				int iSignLen = Integer.parseInt(new String(this.signCodeLen));
				System.arraycopy(sign, 0, this.sign, 0, iSignLen);
			}

		}

		public class VerifySignOut {
			public byte[] reply_code = new byte[HsmConst.REPLY_CODE_LEN];
		}

	}

	public class QueryRSAKey {
		public class QueryRSAKeyIn {
		}

		public class QueryRSAKeyOut {
			public byte[] reply_code = new byte[HsmConst.REPLY_CODE_LEN];

			public byte[] keyNum = new byte[HsmConst.DATA_LEN];

			public byte[] indexList = new byte[HsmConst.PRIVATE_LEN];
		}
	}

	public class DelRSAKey {
		public class DelRSAKeyIn {
			public byte[] rsaIndex = new byte[HsmConst.INDEX_LEN];

			public byte[] passwd = new byte[HsmConst.PWD_LEN];

			public DelRSAKeyIn(byte[] rsaIndex, byte[] passwd) {
				System.arraycopy(rsaIndex, 0, this.rsaIndex, 0, HsmConst.INDEX_LEN);
				System.arraycopy(passwd, 0, this.passwd, 0, HsmConst.PWD_LEN);
			}
		}

		public class DelRSAKeyOut {
			public byte[] reply_code = new byte[HsmConst.REPLY_CODE_LEN];
		}
	}

	public class ImportPrivateKey {
		public class ImportPrivateKeyIn {
			public byte[] rsaIndex = new byte[HsmConst.INDEX_LEN];

			public byte[] privateKeyLen = new byte[HsmConst.DATA_LEN];

			public byte[] athFlag = new byte[HsmConst.FLAG_LEN];

			public byte[] passwd = new byte[HsmConst.PWD_LEN];

			public byte[] privateKey = new byte[HsmConst.PRIVATE_LEN];

			public ImportPrivateKeyIn(byte[] rsaIndex, byte[] privateKeyLen, byte[] athFlag,
					byte[] passwd, byte[] privateKey) {
				System.arraycopy(rsaIndex, 0, this.rsaIndex, 0, HsmConst.INDEX_LEN);
				System.arraycopy(privateKeyLen, 0, this.privateKeyLen, 0, HsmConst.DATA_LEN);
				System.arraycopy(athFlag, 0, this.athFlag, 0, athFlag.length);
				System.arraycopy(passwd, 0, this.passwd, 0, HsmConst.PWD_LEN);
				System.arraycopy(privateKey, 0, this.privateKey, 0, privateKey.length);
			}
		}

		public class ImportPrivateKeyOut {
			public byte[] reply_code = new byte[HsmConst.REPLY_CODE_LEN];
		}
	}

	public class HashInit {
		public class HashInitIn {
			public byte[] hashFlag = new byte[HsmConst.REPLY_CODE_LEN];

			public HashInitIn(byte[] hashFlag) {
				System.arraycopy(hashFlag, 0, this.hashFlag, 0, hashFlag.length);
			}
		}

		public class HashInitOut {
			public byte[] reply_code = new byte[HsmConst.REPLY_CODE_LEN];

			public byte[] dataLen = new byte[HsmConst.DATA_LEN];

			public byte[] data = new byte[HsmConst.PRIVATE_LEN];
		}
	}

	public class HashUpdate {
		public class HashUpdateIn {
			public byte[] hashFlag = new byte[HsmConst.FLAG_LEN];

			public byte[] ivLen = new byte[HsmConst.DATA_LEN];

			public byte[] dataLen = new byte[HsmConst.DATA_LEN];

			public byte[] ivData = new byte[HsmConst.PRIVATE_LEN];

			public byte[] data = new byte[HsmConst.PRIVATE_LEN];

			// public HashUpdateIn(byte[] hashFlag, byte[] ivLen, byte[]
			// dateLen, byte[] ivdata,
			public HashUpdateIn(byte[] hashFlag, byte[] ivLen, byte[] dataLen, byte[] ivdata,
					byte[] data) {
				System.arraycopy(hashFlag, 0, this.hashFlag, 0, hashFlag.length);
				System.arraycopy(ivLen, 0, this.ivLen, 0, HsmConst.DATA_LEN);
				System.arraycopy(dataLen, 0, this.dataLen, 0, HsmConst.DATA_LEN);
				// System.arraycopy(ivData, 0, this.data, 0, ivData.length);
				System.arraycopy(ivdata, 0, this.ivData, 0, ivData.length);
				System.arraycopy(data, 0, this.data, 0, data.length);

			}
		}

		public class HashUpdateOut {
			public byte[] reply_code = new byte[HsmConst.REPLY_CODE_LEN];

			public byte[] dataLen = new byte[HsmConst.DATA_LEN];

			public byte[] data = new byte[HsmConst.PRIVATE_LEN];
		}
	}

	public class HashFinal {
		public class HashFinalIn {
			public byte[] hashFlag = new byte[HsmConst.FLAG_LEN];

			public byte[] ivLen = new byte[HsmConst.DATA_LEN];

			public byte[] ivData = new byte[HsmConst.PRIVATE_LEN];

			public HashFinalIn(byte[] hashFlag, byte[] ivLen, byte[] ivData) {
				System.arraycopy(hashFlag, 0, this.hashFlag, 0, hashFlag.length);
				System.arraycopy(ivLen, 0, this.ivLen, 0, HsmConst.DATA_LEN);
				System.arraycopy(ivData, 0, this.ivData, 0, ivData.length);
			}
		}

		public class HashFinalOut {
			public byte[] reply_code = new byte[HsmConst.REPLY_CODE_LEN];

			public byte[] dataLen = new byte[HsmConst.DATA_LEN];

			public byte[] data = new byte[HsmConst.PRIVATE_LEN];
		}
	}

	public class QueryBMKey {
		public class QueryBMKeyIn {
			public byte[] keyIndex = new byte[HsmConst.INDEX_LEN];

			public QueryBMKeyIn(byte[] keyIndex) {
				System.arraycopy(keyIndex, 0, this.keyIndex, 0, HsmConst.INDEX_LEN);
			}
		}

		public class QueryBMKeyOut {
			public byte[] reply_code = new byte[HsmConst.REPLY_CODE_LEN];
		}
	}

	public class GenerateWorkKey {
		public class GenerateWorkKeyIn {
			public byte[] keyLen = new byte[HsmConst.PIK_LEN_LEN];

			public byte[] keyIndex = new byte[HsmConst.INDEX_LEN];

			public GenerateWorkKeyIn(byte[] keyLen, byte[] keyIndex) {
				System.arraycopy(keyLen, 0, this.keyLen, 0, HsmConst.PIK_LEN_LEN);
				System.arraycopy(keyIndex, 0, this.keyIndex, 0, HsmConst.INDEX_LEN);
			}
		}

		public class GenerateWorkKeyOut {
			public byte[] reply_code = new byte[HsmConst.REPLY_CODE_LEN];

			public byte[] keyLen = new byte[HsmConst.PIK_LEN_LEN];

			public byte[] workKey = new byte[HsmConst.PIK_LEN];

			public byte[] checkvalue = new byte[HsmConst.CHV_LEN];
		}
	}

	public class ConvertPINBlock {
		public class ConvertPINBlockIn {
			public byte[] indexIn = new byte[HsmConst.INDEX_LEN];

			public byte[] indexOut = new byte[HsmConst.INDEX_LEN];

			public byte[] algIn = new byte[HsmConst.FLAG_LEN];

			public byte[] algOut = new byte[HsmConst.FLAG_LEN];

			public byte[] pin = new byte[HsmConst.PIN_BLK_LEN];

			public byte[] pikLenIn = new byte[HsmConst.PIK_LEN_LEN];

			public byte[] pikIn = new byte[HsmConst.PIK_LEN];

			public byte[] accoIn = new byte[HsmConst.PAN_LEN];

			public byte[] pikLenOut = new byte[HsmConst.PIK_LEN_LEN];

			public byte[] pikOut = new byte[HsmConst.PIK_LEN];

			public byte[] accoOut = new byte[HsmConst.PAN_LEN];

			public ConvertPINBlockIn(byte[] indexIn, byte[] indexOut, byte[] algIn, byte[] algOut,
					byte[] pin, byte[] pikLenIn, byte[] pikIn, byte[] accoIn, byte[] pikLenOut,
					byte[] pikOut, byte[] accoOut) {
				System.arraycopy(indexIn, 0, this.indexIn, 0, HsmConst.INDEX_LEN);
				System.arraycopy(indexOut, 0, this.indexOut, 0, HsmConst.INDEX_LEN);
				System.arraycopy(algIn, 0, this.algIn, 0, HsmConst.FLAG_LEN);
				System.arraycopy(algOut, 0, this.algOut, 0, HsmConst.FLAG_LEN);
				System.arraycopy(pin, 0, this.pin, 0, HsmConst.PIN_BLK_LEN);
				System.arraycopy(pikLenIn, 0, this.pikLenIn, 0, HsmConst.PIK_LEN_LEN);
				System.arraycopy(pikIn, 0, this.pikIn, 0, pikIn.length);
				System.arraycopy(accoIn, 0, this.accoIn, 0, accoIn.length);
				System.arraycopy(pikLenOut, 0, this.pikLenOut, 0, HsmConst.PIK_LEN_LEN);
				System.arraycopy(pikOut, 0, this.pikOut, 0, pikOut.length);
				System.arraycopy(accoOut, 0, this.accoOut, 0, accoOut.length);
			}

		}

		public class ConvertPINBlockOut {
			public byte[] reply_code = new byte[HsmConst.REPLY_CODE_LEN];

			public byte[] pin = new byte[HsmConst.PIN_BLK_LEN];
		}
	}

	public class ConvertPWD {
		public class ConvertPWDIn {
			public byte[] indexIn = new byte[HsmConst.INDEX_LEN];

			public byte[] indexOut = new byte[HsmConst.INDEX_LEN];

			public byte[] pin = new byte[HsmConst.PWD_BLK_LEN];

			public byte[] pikLenIn = new byte[HsmConst.PIK_LEN_LEN];

			public byte[] pikIn = new byte[HsmConst.PIK_LEN];

			public byte[] pikLenOut = new byte[HsmConst.PIK_LEN_LEN];

			public byte[] pikOut = new byte[HsmConst.PIK_LEN];

			public ConvertPWDIn(byte[] indexIn, byte[] indexOut, byte[] pin, byte[] pikLenIn,
					byte[] pikIn, byte[] pikLenOut, byte[] pikOut) {
				System.arraycopy(indexIn, 0, this.indexIn, 0, HsmConst.INDEX_LEN);
				System.arraycopy(indexOut, 0, this.indexOut, 0, HsmConst.INDEX_LEN);
				System.arraycopy(pin, 0, this.pin, 0, HsmConst.PWD_BLK_LEN);
				System.arraycopy(pikLenIn, 0, this.pikLenIn, 0, HsmConst.PIK_LEN_LEN);
				System.arraycopy(pikIn, 0, this.pikIn, 0, pikIn.length);
				System.arraycopy(pikLenOut, 0, this.pikLenOut, 0, HsmConst.PIK_LEN_LEN);
				System.arraycopy(pikOut, 0, this.pikOut, 0, pikOut.length);
			}

		}

		public class ConvertPWDOut {
			public byte[] reply_code = new byte[HsmConst.REPLY_CODE_LEN];

			public byte[] pin = new byte[HsmConst.PWD_BLK_LEN];
		}
	}

	public class Bank_GenMac {
		public class Bank_GenMacIN {
			public byte[] bk_inx = new byte[HsmConst.INDEX_LEN]; /* 鍒嗚BMK绱㈠紩鍙� */

			public byte[] mak_len = new byte[HsmConst.MAK_LEN_LEN]; /* MAC瀵嗛挜闀垮害(鍊间负8/16/24) */

			public byte[] mak = new byte[HsmConst.MAK_LEN]; /* MAC瀵嗛挜 */

			public byte[] mac_element_len = new byte[HsmConst.MAC_ELEMENT_LEN_LEN]; /*
																					 * MAC BLOCK闀垮害
																					 */

			public byte[] mac_element = new byte[HsmConst.MAX_MAC_ELEMENT_LEN]; /* 璁＄畻MAC鐨勬暟鎹� */

			public Bank_GenMacIN(byte[] index, byte[] mak_len, byte[] mak, byte[] mac_data_len,
					byte[] mac_data) {
				System.arraycopy(index, 0, this.bk_inx, 0, HsmConst.INDEX_LEN);
				System.arraycopy(mak_len, 0, this.mak_len, 0, HsmConst.MAK_LEN_LEN);
				System.arraycopy(mak, 0, this.mak, 0, mak.length);
				System.arraycopy(mac_data_len, 0, this.mac_element_len, 0,
						HsmConst.MAC_ELEMENT_LEN_LEN);
				System.arraycopy(mac_data, 0, this.mac_element, 0, mac_data.length);
			}
		}

		public class Bank_GenMacOUT {
			public byte[] reply_code = new byte[HsmConst.REPLY_CODE_LEN]; /* 杩斿洖鐮� */

			public byte[] mac = new byte[HsmConst.MAC_LEN + 8]; /*
																 * MAC 鍗佸叚杩涘埗
																 *//* zhangje */
		}
	}

	public class Bank_VerifyMac {
		public class Bank_VerifyMacIN {
			public byte[] bk_inx = new byte[HsmConst.INDEX_LEN]; /* 分行BMK索引号 */

			public byte[] mak_len = new byte[HsmConst.MAK_LEN_LEN]; /*
																	 * MAC密钥长度(值为8 /16/24)
																	 */

			public byte[] mak = new byte[HsmConst.MAK_LEN]; /* MAC密钥(ASCII码字符串) */

			public byte[] mac = new byte[HsmConst.MAC_LEN]; /*
															 * 待校验的MAC 十六进制
															 *//* zhangje */

			public byte[] mac_element_len = new byte[HsmConst.MAC_ELEMENT_LEN_LEN]; /*
																					 * MAC BLOCK长度
																					 */

			public byte[] mac_element = new byte[HsmConst.MAX_MAC_ELEMENT_LEN]; /* 计算MAC的数据 */

			public Bank_VerifyMacIN(byte[] index, byte[] mak_len, byte[] mak, byte[] mac,
					byte[] mac_data_len, byte[] mac_data) {
				System.arraycopy(index, 0, this.bk_inx, 0, HsmConst.INDEX_LEN);
				System.arraycopy(mak_len, 0, this.mak_len, 0, HsmConst.MAK_LEN_LEN);
				System.arraycopy(mak, 0, this.mak, 0, mak.length);
				System.arraycopy(mac, 0, this.mac, 0, mac.length);
				System.arraycopy(mac_data_len, 0, this.mac_element_len, 0,
						HsmConst.MAC_ELEMENT_LEN_LEN);
				System.arraycopy(mac_data, 0, this.mac_element, 0, mac_data.length);
			}
		}

		public class Bank_VerifyMacOUT {
			public byte[] reply_code = new byte[HsmConst.REPLY_CODE_LEN];
		}
	}

	public class EncDec_Data {
		public class EncDec_DataIN {
			public byte[] index = new byte[HsmConst.INDEX_LEN]; /* 分行BMK索引号 */

			public byte[] key_len = new byte[HsmConst.MAK_LEN_LEN]; /*
																	 * 数据密钥长度(值16 )
																	 */

			public byte[] work_key = new byte[HsmConst.MAK_LEN]; /* 密钥数据 */

			public byte[] flag = new byte[HsmConst.FLAG_LEN]; /*
															 * 加解密标志，0--加密，1--解密；
															 */

			public byte[] data_len = new byte[HsmConst.DATA_LEN]; /* 数据长度 */

			public byte[] data = new byte[HsmConst.MAX_DATA_LEN]; /* 参与加解密的数据 */

			public EncDec_DataIN(byte[] index, byte[] key_len, byte[] work_key, byte[] flag,
					byte[] data_len, byte[] data) {
				System.arraycopy(index, 0, this.index, 0, HsmConst.INDEX_LEN);
				System.arraycopy(key_len, 0, this.key_len, 0, HsmConst.MAK_LEN_LEN);
				System.arraycopy(work_key, 0, this.work_key, 0, work_key.length);
				System.arraycopy(flag, 0, this.flag, 0, flag.length);
				System.arraycopy(data_len, 0, this.data_len, 0, data_len.length);
				System.arraycopy(data, 0, this.data, 0, data.length);
			}
		}

		public class EncDec_DataOUT {
			public byte[] reply_code = new byte[HsmConst.REPLY_CODE_LEN]; /* 返回码 */

			public byte[] data_len = new byte[HsmConst.DATA_LEN]; /* 返回数据长度 */

			public byte[] data = new byte[HsmConst.MAX_DATA_LEN]; /* 加解密后的数据 */
		}
	}

	/**
	 * 取回一个索引的区域主密钥,被LMK加密<0X31>
	 * 输入及输出报文结构体定义
	 */
	public class EncDec31_Data {
		public class EncDec31_DataIN {
			public byte[] index = new byte[HsmConst.INDEX_LEN]; /* 区域主密钥索引号ZMK */

			public EncDec31_DataIN(byte[] index) {
				System.arraycopy(index, 0, this.index, 0, HsmConst.INDEX_LEN);
			}
		}

		public class EncDec31_DataOUT {
			public byte[] reply_code = new byte[HsmConst.REPLY_CODE_LEN]; /* 返回码 */

			public byte[] pk_encrypted_bylmk = new byte[16]; /* 用本地主密钥加密后的区域主密钥 */

			public byte[] check_value = new byte[8]; /* 校验值 */
		}
	}

	/**
	 * 将LMK加密的密钥转换为BMK加密<0X0552>
	 * 输入及输出报文结构体定义
	 */
	public class EncDec0552_Data {
		public class EncDec0552_DataIN {
			public byte[] index = new byte[HsmConst.INDEX_LEN]; /* 区域主密钥索引号 */

			public byte[] pk_encrypted_bylmk = new byte[16]; /* 数据密钥*/

			public EncDec0552_DataIN(byte[] index, byte[] pk_encrypted_bylmk) {
				System.arraycopy(index, 0, this.index, 0, HsmConst.INDEX_LEN);
				System.arraycopy(pk_encrypted_bylmk, 0, this.pk_encrypted_bylmk, 0, 16);
			}
		}

		public class EncDec0552_DataOUT {
			public byte[] reply_code = new byte[HsmConst.REPLY_CODE_LEN]; /* 返回码 */

			public byte[] pk_encrypted_byzmk = new byte[16]; /* 数据密钥密文,由区域主密钥加密 */

		}
	}
	
	/**
	 * 用输入密钥对数据加/解密<0X71>
	 * 输入及输出报文结构体定义
	 */
	public class EncDec71_Data {
		public class EncDec71_DataIN {
			public byte[] index = new byte[HsmConst.INDEX_LEN]; /* 银行索引号BMK */

			public byte[] key_len = new byte[HsmConst.MAK_LEN_LEN]; /* 数据密钥长度(值16 )*/

			public byte[] work_key = new byte[HsmConst.MAK_LEN]; /* 密钥数据 */
			
			public byte[] initial_vecotr = new byte[8]; /* CBC加密的初始向量 */

			public byte[] encdec_flag = new byte[HsmConst.FLAG_LEN]; /* 加/解密标识，0--解密，1--加密；*/
			
			public byte[] algorithm_flag = new byte[HsmConst.FLAG_LEN]; /* 算法标识，第0位：ECB＝0，CBC＝1，第1位：3DES＝0，DES＝1；*/

			public byte[] data_len = new byte[HsmConst.DATA_LEN]; /* 数据长度 */

			public byte[] data ; /* 参与加解密的数据 */

			public EncDec71_DataIN(byte[] index, byte[] key_len, byte[] work_key,  byte[] initial_vecotr, byte[] encdec_flag,byte[] algorithm_flag,
					byte[] data_len, byte[] data) {
				System.arraycopy(index, 0, this.index, 0, HsmConst.INDEX_LEN);
				System.arraycopy(key_len, 0, this.key_len, 0, HsmConst.MAK_LEN_LEN);
				System.arraycopy(work_key, 0, this.work_key, 0, work_key.length);
				System.arraycopy(initial_vecotr, 0, this.initial_vecotr, 0, initial_vecotr.length);
				System.arraycopy(encdec_flag, 0, this.encdec_flag, 0, encdec_flag.length);
				System.arraycopy(algorithm_flag, 0, this.algorithm_flag, 0, algorithm_flag.length);
				System.arraycopy(data_len, 0, this.data_len, 0, data_len.length);
				this.data = new byte[data.length];
				System.arraycopy(data, 0, this.data, 0, data.length);
			}
		}

		public class EncDec71_DataOUT {
			public byte[] reply_code = new byte[HsmConst.REPLY_CODE_LEN]; /* 返回码 */

			public byte[] data_len = new byte[2]; /* 返回数据长度 */

			public byte[] data ; /* 加解密后的数据 */
		}
	}
	
	public class UnsymConvertPin {
		public class UnsymConvertPinIn {
			public byte[] rsaIndex = new byte[HsmConst.INDEX_LEN];

			public byte[] privateKeyLen = new byte[HsmConst.DATA_LEN];

			public byte[] dataLen = new byte[HsmConst.DATA_LEN];

			public byte[] padFlag = new byte[HsmConst.FLAG_LEN];

			public byte[] athFlag = new byte[HsmConst.FLAG_LEN];

			public byte[] passwd = new byte[HsmConst.PWD_LEN];

			public byte[] privateKey = new byte[HsmConst.PRIVATE_LEN];

			public byte[] decData = new byte[HsmConst.PRIVATE_LEN];

			public byte[] flagIn = new byte[HsmConst.FLAG_LEN];

			public byte[] accoIn = new byte[HsmConst.PAN_LEN];

			public byte[] bmkIndex = new byte[HsmConst.INDEX_LEN];

			public byte[] pikLen = new byte[HsmConst.PIK_LEN_LEN];

			public byte[] pik = new byte[HsmConst.PIK_LEN];

			public byte[] flagOut = new byte[HsmConst.FLAG_LEN];

			public byte[] accoOut = new byte[HsmConst.PAN_LEN];

			public UnsymConvertPinIn(byte[] rsaIndex, byte[] privateKeyLen, byte[] dataLen,
					byte[] padFlag, byte[] athFlag, byte[] passwd, byte[] privateKey,
					byte[] decData, byte[] flagIn, byte[] accoIn, byte[] bmkIndex, byte[] pikLen,
					byte[] pik, byte[] flagOut, byte[] accoOut) {
				System.arraycopy(rsaIndex, 0, this.rsaIndex, 0, HsmConst.INDEX_LEN);
				System.arraycopy(privateKeyLen, 0, this.privateKeyLen, 0, HsmConst.DATA_LEN);
				System.arraycopy(dataLen, 0, this.dataLen, 0, HsmConst.DATA_LEN);
				System.arraycopy(padFlag, 0, this.padFlag, 0, HsmConst.FLAG_LEN);
				System.arraycopy(athFlag, 0, this.athFlag, 0, athFlag.length);
				System.arraycopy(passwd, 0, this.passwd, 0, HsmConst.PWD_LEN);
				System.arraycopy(privateKey, 0, this.privateKey, 0, privateKey.length);
				System.arraycopy(decData, 0, this.decData, 0, decData.length);
				System.arraycopy(flagIn, 0, this.flagIn, 0, HsmConst.FLAG_LEN);
				System.arraycopy(accoIn, 0, this.accoIn, 0, accoIn.length);
				System.arraycopy(bmkIndex, 0, this.bmkIndex, 0, HsmConst.INDEX_LEN);
				System.arraycopy(pikLen, 0, this.pikLen, 0, HsmConst.PIK_LEN_LEN);
				System.arraycopy(pik, 0, this.pik, 0, pik.length);
				System.arraycopy(flagOut, 0, this.flagOut, 0, HsmConst.FLAG_LEN);
				System.arraycopy(accoOut, 0, this.accoOut, 0, accoOut.length);
			}
		}

		public class UnsymConvertPinOut {
			public byte[] reply_code = new byte[HsmConst.REPLY_CODE_LEN];

			public byte[] pinLen = new byte[HsmConst.DATA_LEN];

			public byte[] pinblock = new byte[HsmConst.PWD_BLK_LEN];
		}
	}
	
	/**
	 * 用ZEK加密数据 E0
	 * 江南科友加密机E0指令
	 * add by lfr
	 */
	public class EncDec_JNKY_E0_Data {
		public class EncDec_JNKY_E0_DataIN {

			public byte[] message_header = new byte[HsmConst.E0_HEADER_LEN]; /* 消息头 */

			public byte[] command_code = new byte[HsmConst.E0_COMMAND_CODE_LEN]; /* 命令代码 */

			public byte[] message_block_no = new byte[HsmConst.E0_MESSAGE_BLOCK_NO_LEN]; /* 消息块号 */
			
			public byte[] key_model = new byte[HsmConst.E0_KEY_MODEL_LEN]; /* 密钥模式 */

			public byte[] key_encryption_mode = new byte[HsmConst.E0_KEY_ENCRYPTION_MODE_LEN]; /* 密钥加密模式*/
			
			public byte[] key_type = new byte[HsmConst.E0_KEY_TYPE_LEN]; /* 密钥类型 */
			
			public byte[] key = new byte[HsmConst.E0_KEY_LEN]; /* 密钥 */
			
			public byte[] input_message_type = new byte[HsmConst.E0_INPUT_MESSAGE_TYPE_LEN]; /* 输入消息类型 */
			
			public byte[] output_message_type = new byte[HsmConst.E0_OUTPUT_MESSAGE_TYPE_LEN]; /* 输出消息类型*/
			
			public byte[] fill_pattern = new byte[HsmConst.E0_FILL_PATTERN_LEN]; /* 填充模式 */
			
			public byte[] fill_character = new byte[HsmConst.E0_FILL_CHARACTER_LEN]; /* 填充字符 */
			
			public byte[] fill_type = new byte[HsmConst.E0_FILL_TYPE_LEN]; /* 填充类型 */
			
			public byte[] data_length = new byte[HsmConst.E0_DATA_LENGTH_LEN]; /* 消息数据长度 */
		
			public byte[] data ; /* 参与加解密的数据 */

			public EncDec_JNKY_E0_DataIN(byte[] message_header, byte[] command_code,
					byte[] message_block_no, byte[] key_model,
					byte[] key_encryption_mode, byte[] key_type, byte[] key,
					byte[] input_message_type, byte[] output_message_type,
					byte[] fill_pattern, byte[] fill_character,
					byte[] fill_type, byte[] data_length, byte[] data) {
				System.arraycopy(message_header, 0, this.message_header, 0, HsmConst.E0_HEADER_LEN);
				System.arraycopy(command_code, 0, this.command_code, 0, HsmConst.E0_COMMAND_CODE_LEN);
				System.arraycopy(message_block_no, 0, this.message_block_no, 0, HsmConst.E0_MESSAGE_BLOCK_NO_LEN);
				System.arraycopy(key_model, 0, this.key_model, 0, HsmConst.E0_KEY_MODEL_LEN);
				System.arraycopy(key_encryption_mode, 0, this.key_encryption_mode, 0, HsmConst.E0_KEY_ENCRYPTION_MODE_LEN);
				System.arraycopy(key_type, 0, this.key_type, 0, HsmConst.E0_KEY_TYPE_LEN);
				System.arraycopy(key, 0, this.key, 0, key.length);
				System.arraycopy(input_message_type, 0, this.input_message_type, 0, HsmConst.E0_INPUT_MESSAGE_TYPE_LEN);
				System.arraycopy(output_message_type, 0, this.output_message_type, 0, HsmConst.E0_OUTPUT_MESSAGE_TYPE_LEN);
				System.arraycopy(fill_pattern, 0, this.fill_pattern, 0, HsmConst.E0_FILL_PATTERN_LEN);
				System.arraycopy(fill_character, 0, this.fill_character, 0, HsmConst.E0_FILL_CHARACTER_LEN);
				System.arraycopy(fill_type, 0, this.fill_type, 0, HsmConst.E0_FILL_TYPE_LEN);
				System.arraycopy(data_length, 0, this.data_length, 0, HsmConst.E0_DATA_LENGTH_LEN);
				this.data = new byte[data.length];
				System.arraycopy(data, 0, this.data, 0, data.length);
			}
		}

		public class EncDec_JNKY_E0_DataOUT {
			
			public byte[] message_header = new byte[HsmConst.E0_HEADER_LEN]; /* 消息头 */
			
			public byte[] response_code = new byte[HsmConst.E0_RESPONSE_CODE_LEN]; /* 响应代码 */

			public byte[] reply_code = new byte[HsmConst.E0_REPLY_CODE_LEN]; /* 错误代码 */
			
			public byte[] output_message_type = new byte[HsmConst.E0_OUTPUT_MESSAGE_TYPE_LEN]; /* 输出消息类型*/
			
			public byte[] data_length = new byte[HsmConst.E0_DATA_LENGTH_LEN]; /* 消息数据长度 */

			public byte[] data ; /* 加解密后的数据 */
		}
	}

}
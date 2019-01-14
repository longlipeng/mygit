package hsmj;

import org.apache.log4j.Logger;

/**
 * 
 * <p>
 * <strong> This class name was HsmApp </strong>
 * </p>
 * 
 * @author Lay
 * @date 2010-5-11 03:43:31
 * @version 1.0
 */
public class HsmApp {
	private static Logger logger = Logger.getLogger(HsmApp.class);
	public static final int SECBUF_MAX_SIZE = 512;
	private byte[] iSecBufferIn = new byte[SECBUF_MAX_SIZE];
	private byte[] iSecBufferOut = new byte[SECBUF_MAX_SIZE];

	public HsmApp() {
	}

	/** Common Method：Print the data with HEX format **/
	public static void OutputDataHex(String sInfo, byte[] byteIn, int nDataLen) {
		int i, j, n, prev;
		StringBuffer sb = new StringBuffer();
		sb.append("[").append(sInfo).append("] length [").append(nDataLen).append("]");
		prev = n = 0;
		for (i = 0; i < nDataLen; i++) {
			if (i == (prev + 16)) {
				sb.append("    ;");
				for (j = prev; j < prev + 16; j++) {
					if (Character.isLetter((char) (byteIn[j] & 0xff)) == true) {
						sb.append((char) byteIn[j]);
					} else {
						sb.append(" ");
					}
				}
				prev += 16;
				sb.append("\n");
			}
			if (Integer.toHexString(byteIn[i] & 0xff).length() == 1) {
				sb.append("0").append(Integer.toHexString(byteIn[i] & 0xff)).append(" ");
			} else {
				sb.append(Integer.toHexString(byteIn[i] & 0xff)).append(" ");
			}
		}
		if (i != prev) {
			n = i;
			for (; i < prev + 16; i++) {
				sb.append("   ");
			}
		}
		sb.append("    ;");
		for (i = prev; i < n; i++) {
			if (Character.isLetter((char) byteIn[i]) == true) {
				sb.append((char) byteIn[i]);
			} else {
				sb.append(" ");
			}
		}
		sb.append("\n");
		logger.info(sb.toString());
	}

	public static boolean Str2Hex(byte[] in, byte[] out, int len) {
		byte[] asciiCode = {0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f};

		if (len > in.length) {
			return false;
		}

		if (len % 2 != 0) {
			return false;
		}

		byte[] temp = new byte[len];

		for (int i = 0; i < len; i++) {
			if (in[i] >= 0x30 && in[i] <= 0x39) {
				temp[i] = (byte) (in[i] - 0x30);
			} else if (in[i] >= 0x41 && in[i] <= 0x46) {
				temp[i] = asciiCode[in[i] - 0x41];
			} else if (in[i] >= 0x61 && in[i] <= 0x66) {
				temp[i] = asciiCode[in[i] - 0x61];
			} else {
				return false;
			}
		}

		for (int i = 0; i < len / 2; i++) {
			out[i] = (byte) (temp[2 * i] * 16 + temp[2 * i + 1]);
		}

		return true;
	}

	public static boolean Hex2Str(byte[] in, byte[] out, int len) {
		byte[] asciiCode = {0x41, 0x42, 0x43, 0x44, 0x45, 0x46};

		if (len > in.length) {
			return false;
		}

		byte[] temp = new byte[2 * len];

		for (int i = 0; i < len; i++) {
			temp[2 * i] = (byte) ((in[i] & 0xf0) / 16);
			temp[2 * i + 1] = (byte) (in[i] & 0x0f);
		}

		for (int i = 0; i < 2 * len; i++) {
			if (temp[i] <= 9 && temp[i] >= 0) {
				out[i] = (byte) (temp[i] + 0x30);
			} else {
				out[i] = asciiCode[temp[i] - 0x0a];
			}
		}

		return true;
	}

	/** Convert bye to HEX **/
	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";

		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;

			}
			if (n < b.length - 1) {
				hs = hs + " ";
			}
		}

		return hs.toUpperCase();
	}

	/**
	 * ========================================================================== =======
	 * 功能描述:产生RSA密钥对 输入结构: 命令类型 1 H 0xE0 命令 1 H 0x01 密钥模长 2 H 可选择512、1024、2048 存储索引号 2 H 可选择0—49
	 * 算法标志 1 H 0：产生密钥，存储但不输出 1：明文输出REF格式的公私钥对 2：输出公钥为明文的REF格式，私钥被本地主密钥加密 3：明文输出DER格式的公私钥对
	 * 4：输出公钥为明文的DER格式，私钥被本地主密钥加密 密钥访问口令 8 H 输出结构: 应答码 1 A “A” 公钥长度 2 H 当算法标志=0时，该项为NULL 私钥长度 2 H
	 * 当算法标志=0时，该项为NULL 公钥数据 N H 当算法标志=0时，该项为NULL 私钥数据 N H 当算法标志=0时，该项为NULL 或 应答码 1 A “E” 错误码 1 H
	 * ================================ ===============================================
	 **/
	public int GenerateRsaKey(HsmSession hSession, HsmStruct.GenerateRsaKey.GenerateRsaKeyIn hsmIn,
			HsmStruct.GenerateRsaKey.GenerateRsaKeyOut hsmOut) {
		int iSndLen = 0, nResult = 0;
		byte[] retCode = new byte[1];

		/** command code **/
		iSecBufferIn[0] = (byte) 0xe0;
		iSecBufferIn[1] = (byte) 0x01;
		iSndLen += 2;

		/** RSA key module length **/
		String temp = new String(hsmIn.modeLen);
		int i = Integer.parseInt(temp);

		if (i > 65536) {
			retCode[0] = (byte) HsmConst.ERR_MODULE_LEN;
			Hex2Str(retCode, hsmOut.reply_code, 1);
			return HsmConst.T_FAIL;
		}

		iSecBufferIn[iSndLen++] = (byte) ((i >> 8) & 0xff);
		iSecBufferIn[iSndLen++] = (byte) (i & 0xff);

		/** RSA key index **/
		temp = new String(hsmIn.rsaIndex);
		i = Integer.parseInt(temp);

		iSecBufferIn[iSndLen++] = (byte) ((i >> 8) & 0xff);
		iSecBufferIn[iSndLen++] = (byte) (i & 0xff);

		/** flag **/
		iSecBufferIn[iSndLen++] = (byte) (hsmIn.flag[0] - '0');

		/** RSA key access password **/
		if (hsmIn.passwd.length < 8) {
			retCode[0] = (byte) HsmConst.ERR_PASSWD;
			Hex2Str(retCode, hsmOut.reply_code, 1);
			return HsmConst.T_FAIL;
		}

		System.arraycopy(hsmIn.passwd, 0, iSecBufferIn, iSndLen, HsmConst.PWD_LEN);
		iSndLen += 8;
		/*
		 * // send message nResult = hSession.SendData(iSecBufferIn, iSndLen); if (nResult != 0) {
		 * retCode[0] = (byte) nResult; Hex2Str(retCode, hsmOut.reply_code, 1); return
		 * HsmConst.T_FAIL; }
		 * 
		 * // receive message nResult = hSession.RecvData(iSecBufferOut); if (nResult != 0) {
		 * retCode[0] = (byte) nResult; Hex2Str(retCode, hsmOut.reply_code, 1); return
		 * HsmConst.T_FAIL; }
		 */
		nResult = hSession.SndAndRcvData(iSecBufferIn, iSndLen, iSecBufferOut);
		if (nResult != 0) {
			retCode[0] = (byte) nResult;
			Hex2Str(retCode, hsmOut.reply_code, 1);
			return HsmConst.T_FAIL;
		}

		hsmOut.reply_code[0] = 0x30;
		hsmOut.reply_code[1] = 0x30;

		if (hsmIn.flag[0] != '0') {
			// byte[] tempChange = new byte[4];
			int publicLen, privateLen;

			// public key length
			// i = secbuf_out[1] * 256 + secbuf_out[2];
			i = ((iSecBufferOut[1] << 8) & 0xff00) | (iSecBufferOut[2] & 0xff);
			String tt = Integer.toString(i);
			if (tt.length() > 4) {
				retCode[0] = (byte) HsmConst.ERR_DATA_LEN;
				Hex2Str(retCode, hsmOut.reply_code, 1);
				return HsmConst.T_FAIL;
			}
			System.arraycopy("0000".getBytes(), 0, hsmOut.publicKeyLen, 0, HsmConst.DATA_LEN);

			System.arraycopy(tt.getBytes(), 0, hsmOut.publicKeyLen,
					HsmConst.DATA_LEN - tt.length(), tt.length());

			/** private key length **/
			i = ((iSecBufferOut[3] << 8) & 0xff00) | (iSecBufferOut[4] & 0xff);
			tt = Integer.toString(i);

			tt = Integer.toString(i);
			if (tt.length() > 4) {
				retCode[0] = (byte) HsmConst.ERR_DATA_LEN;
				Hex2Str(retCode, hsmOut.reply_code, 1);
				return HsmConst.T_FAIL;
			}

			System.arraycopy("0000".getBytes(), 0, hsmOut.privateKeyLen, 0, HsmConst.DATA_LEN);

			System.arraycopy(tt.getBytes(), 0, hsmOut.privateKeyLen,
					HsmConst.DATA_LEN - tt.length(), tt.length());

			/** public key **/
			// publicLen = secbuf_out[1] * 256 + secbuf_out[2];
			publicLen = ((iSecBufferOut[1] << 8) & 0xff00) | (iSecBufferOut[2] & 0xff);
			System.arraycopy(iSecBufferOut, 5, hsmOut.publicKey, 0, publicLen);

			/** private key **/
			// privateLen = secbuf_out[3] * 256 + (secbuf_out[4] & 0xff);
			privateLen = ((iSecBufferOut[3] << 8) & 0xff00) | (iSecBufferOut[4] & 0xff);
			System.arraycopy(iSecBufferOut, 5 + publicLen, hsmOut.privateKey, 0, privateLen);
		}

		return HsmConst.T_SUCCESS;
	}

	/*
	 * ========================================================================== =========== 功能描述:
	 * 公钥加密数据 输入结构: 命令类型 1 H 0xE0 命令 1 H 0x02 索引号 2 H 若使用内部保存的RSA密钥对索引号时可选择0—49；若使用用户公钥则index请填 ffff
	 * 公钥长度 2 H 当用户使用内部保存的索引号（index != ffff）时publen请填0 数据长度 2 H 填充方式 2 H 1：PKCS1填充方式 3：非填充方式 算法标志 1
	 * H 0：REF格式的公钥 1：DER格式的公钥 密钥访问口令 8 H 当用户不使用内部保存的密钥对（index = ffff）时，该项为NULL 公钥数据 N H
	 * 当用户使用内部保存的密钥对（index != ffff）时，该项为NULL 需要加密的数据 N H 输出结构: 应答码 1 A “A” 数据长度 2 H 加密得到的数据 N H 或
	 * 应答码 1 A “E” 错误码 1 H ==============
	 * ========================================================================
	 */

	public int PublicKeyEncrypt(HsmSession hSession,
			HsmStruct.PublicKeyEncrypt.PublicKeyEncryptIn hsmIn,
			HsmStruct.PublicKeyEncrypt.PublicKeyEncryptOut hsmOut) {
		int iSndLen = 0, nResult = 0;
		byte[] retCode = new byte[1];
		int publicKeyLen = 0;
		boolean accessFlag = true;

		/** command code **/
		iSecBufferIn[0] = (byte) 0xe0;
		iSecBufferIn[1] = 0x02;
		iSndLen += 2;

		String temp;
		int i;

		if ((hsmIn.rsaIndex[0] == 0x66 && hsmIn.rsaIndex[1] == 0x66 && hsmIn.rsaIndex[2] == 0x66)
				|| (hsmIn.rsaIndex[0] == 0x46 && hsmIn.rsaIndex[1] == 0x46 && hsmIn.rsaIndex[2] == 0x46)) {
			/** RSA index **/
			iSecBufferIn[iSndLen++] = (byte) 0xff;
			iSecBufferIn[iSndLen++] = (byte) 0xff;

			/** public key length **/
			temp = new String(hsmIn.publicKeyLen);
			publicKeyLen = Integer.parseInt(temp);

			iSecBufferIn[iSndLen++] = (byte) ((publicKeyLen >> 8) & 0xff);
			iSecBufferIn[iSndLen++] = (byte) (publicKeyLen & 0xff);
			accessFlag = false;
		} else {
			/** RSA index **/
			temp = new String(hsmIn.rsaIndex);
			i = Integer.parseInt(temp);

			iSecBufferIn[iSndLen++] = (byte) ((i >> 8) & 0xff);
			iSecBufferIn[iSndLen++] = (byte) (i & 0xff);

			/** public key length **/
			iSecBufferIn[iSndLen++] = (byte) 0x00;
			iSecBufferIn[iSndLen++] = (byte) 0x00;

		}

		/** data length **/
		temp = new String(hsmIn.dataLen);
		int dataLen = Integer.parseInt(temp);

		iSecBufferIn[iSndLen++] = (byte) ((dataLen >> 8) & 0xff);
		iSecBufferIn[iSndLen++] = (byte) (dataLen & 0xff);

		/** padding mode **/
		iSecBufferIn[iSndLen++] = (byte) (hsmIn.padFlag[0] - 0x30);
		iSecBufferIn[iSndLen++] = (byte) (hsmIn.padFlag[1] - 0x30);

		/** encrypt mode **/
		iSecBufferIn[iSndLen++] = (byte) (hsmIn.athFlag[0] - 0x30);

		/** RSA key access password **/
		if (accessFlag == true) {
			if (hsmIn.passwd.length < 8) {
				retCode[0] = (byte) HsmConst.ERR_PASSWD;
				Hex2Str(retCode, hsmOut.reply_code, 1);
				return HsmConst.T_FAIL;
			}

			System.arraycopy(hsmIn.passwd, 0, iSecBufferIn, iSndLen, HsmConst.PWD_LEN);
			iSndLen += HsmConst.PWD_LEN;
		}
		/** public key **/
		if (publicKeyLen > 0) {
			System.arraycopy(hsmIn.publicKey, 0, iSecBufferIn, iSndLen, publicKeyLen);
			iSndLen += publicKeyLen;
		}
		/** data **/
		System.arraycopy(hsmIn.encData, 0, iSecBufferIn, iSndLen, dataLen);
		iSndLen += dataLen;

		/*
		 * // send message nResult = hSession.SendData(iSecBufferIn, iSndLen); if (nResult != 0) {
		 * retCode[0] = (byte) nResult; Hex2Str(retCode, hsmOut.reply_code, 1); return
		 * HsmConst.T_FAIL; }
		 * 
		 * // receive message nResult = hSession.RecvData(iSecBufferOut); if (nResult != 0) {
		 * retCode[0] = (byte) nResult; Hex2Str(retCode, hsmOut.reply_code, 1); return
		 * HsmConst.T_FAIL; }
		 */
		nResult = hSession.SndAndRcvData(iSecBufferIn, iSndLen, iSecBufferOut);
		if (nResult != 0) {
			retCode[0] = (byte) nResult;
			Hex2Str(retCode, hsmOut.reply_code, 1);
			return HsmConst.T_FAIL;
		}

		// OutputDataHex("data return", secbuf_out, 32);
		hsmOut.reply_code[0] = 0x30;
		hsmOut.reply_code[1] = 0x30;

		/** received data length **/
		// byte[] tempChange = new byte[4];

		// i = secbuf_out[1] * 256 + secbuf_out[2];
		i = ((iSecBufferOut[1] << 8) & 0xff00) | (iSecBufferOut[2] & 0xff);
		String tt = Integer.toString(i);
		if (tt.length() > 4) {
			retCode[0] = (byte) HsmConst.ERR_DATA_LEN;
			Hex2Str(retCode, hsmOut.reply_code, 1);
			return HsmConst.T_FAIL;
		}

		System.arraycopy("0000".getBytes(), 0, hsmOut.dataLen, 0, HsmConst.DATA_LEN);
		System.arraycopy(tt.getBytes(), 0, hsmOut.dataLen, HsmConst.DATA_LEN - tt.length(),
				tt.length());

		/** encrypted data **/
		System.arraycopy(iSecBufferOut, 3, hsmOut.data, 0, i);

		return HsmConst.T_SUCCESS;
	}

	/*
	 * ========================================================================== =========== 功能描述:
	 * 公钥解密数据 输入结构: 长度 类型 备注 命令类型 1 H 0xE0 命令 1 H 0x03 索引号 2 H
	 * 若使用内部保存的RSA密钥对索引号时可选择0—49；若使用用户公钥则index请填 ffff 公钥长度 2 H 当用户使用内部保存的索引号（index）0）时publen应为0 数据长度
	 * 2 H 填充方式 2 H 1：PKCS1填充方式 3：非填充方式 算法标志 1 H 0：REF格式的公钥 1：DER格式的公钥 密钥访问口令 8 H
	 * 当用户不使用内部保存的密钥对（index = ffff）时，该项为NULL 公钥数据 N H 当用户使用内部保存的密钥对（index != ffff）时，该项为NULL 需要解密的数据
	 * N H 输出结构: 应答码 1 A “A” 数据长度 2 H 解密得到的数据 N H 或 应答码 1 A “E” 错误码 1 H ==============
	 * ========================================================================
	 */

	public int PublicKeyDecrypt(HsmSession hSession,
			HsmStruct.PublicKeyEncrypt.PublicKeyEncryptIn hsmIn,
			HsmStruct.PublicKeyEncrypt.PublicKeyEncryptOut hsmOut) {
		int iSndLen = 0, nResult = 0;
		byte[] retCode = new byte[1];
		int publicKeyLen = 0;
		boolean accessFlag = true;

		/** command code **/
		iSecBufferIn[0] = (byte) 0xe0;
		iSecBufferIn[1] = 0x03;
		iSndLen += 2;

		String temp;
		int i;

		if ((hsmIn.rsaIndex[0] == 0x66 && hsmIn.rsaIndex[1] == 0x66 && hsmIn.rsaIndex[2] == 0x66)
				|| (hsmIn.rsaIndex[0] == 0x46 && hsmIn.rsaIndex[1] == 0x46 && hsmIn.rsaIndex[2] == 0x46)) {

			/** rsa index **/
			iSecBufferIn[iSndLen++] = (byte) 0xff;
			iSecBufferIn[iSndLen++] = (byte) 0xff;

			/** public key length **/
			temp = new String(hsmIn.publicKeyLen);
			publicKeyLen = Integer.parseInt(temp);

			iSecBufferIn[iSndLen++] = (byte) ((publicKeyLen >> 8) & 0xff);
			iSecBufferIn[iSndLen++] = (byte) (publicKeyLen & 0xff);

			accessFlag = false;
		} else {
			/** rsa index **/
			temp = new String(hsmIn.rsaIndex);
			i = Integer.parseInt(temp);

			iSecBufferIn[iSndLen++] = (byte) ((i >> 8) & 0xff);
			iSecBufferIn[iSndLen++] = (byte) (i & 0xff);

			/** public key length **/
			iSecBufferIn[iSndLen++] = (byte) 0x00;
			iSecBufferIn[iSndLen++] = (byte) 0x00;

		}

		/** data length **/
		temp = new String(hsmIn.dataLen);
		int dataLen = Integer.parseInt(temp);

		iSecBufferIn[iSndLen++] = (byte) ((dataLen >> 8) & 0xff);
		iSecBufferIn[iSndLen++] = (byte) (dataLen & 0xff);

		/** padding mode **/
		iSecBufferIn[iSndLen++] = (byte) (hsmIn.padFlag[0] - 0x30);
		iSecBufferIn[iSndLen++] = (byte) (hsmIn.padFlag[1] - 0x30);

		/** encrypt mode **/
		iSecBufferIn[iSndLen++] = (byte) (hsmIn.athFlag[0] - 0x30);

		/** rsa key access password **/
		if (accessFlag == true) {
			if (hsmIn.passwd.length < 8) {
				retCode[0] = (byte) HsmConst.ERR_PASSWD;
				Hex2Str(retCode, hsmOut.reply_code, 1);
				return HsmConst.T_FAIL;
			}

			System.arraycopy(hsmIn.passwd, 0, iSecBufferIn, iSndLen, HsmConst.PWD_LEN);
			iSndLen += HsmConst.PWD_LEN;
		}

		/** public key **/
		System.arraycopy(hsmIn.publicKey, 0, iSecBufferIn, iSndLen, publicKeyLen);
		iSndLen += publicKeyLen;

		/** data **/
		System.arraycopy(hsmIn.encData, 0, iSecBufferIn, iSndLen, dataLen);
		iSndLen += dataLen;

		/*
		 * // send message nResult = hSession.SendData(iSecBufferIn, iSndLen); if (nResult != 0) {
		 * retCode[0] = (byte) nResult; Hex2Str(retCode, hsmOut.reply_code, 1); return
		 * HsmConst.T_FAIL; }
		 * 
		 * // receive message nResult = hSession.RecvData(iSecBufferOut); if (nResult != 0) {
		 * retCode[0] = (byte) nResult; Hex2Str(retCode, hsmOut.reply_code, 1); return
		 * HsmConst.T_FAIL; }
		 */
		nResult = hSession.SndAndRcvData(iSecBufferIn, iSndLen, iSecBufferOut);
		if (nResult != 0) {
			retCode[0] = (byte) nResult;
			Hex2Str(retCode, hsmOut.reply_code, 1);
			return HsmConst.T_FAIL;
		}

		hsmOut.reply_code[0] = 0x30;
		hsmOut.reply_code[1] = 0x30;
		/** received data length **/
		// byte[] tempChange = new byte[4];

		// i = secbuf_out[1] * 256 + secbuf_out[2];
		i = ((iSecBufferOut[1] << 8) & 0xff00) | (iSecBufferOut[2] & 0xff);
		String tt = Integer.toString(i);
		if (tt.length() > 4) {
			retCode[0] = (byte) HsmConst.ERR_DATA_LEN;
			Hex2Str(retCode, hsmOut.reply_code, 1);
			return HsmConst.T_FAIL;
		}
		System.arraycopy("0000".getBytes(), 0, hsmOut.dataLen, 0, HsmConst.DATA_LEN);
		System.arraycopy(tt.getBytes(), 0, hsmOut.dataLen, HsmConst.DATA_LEN - tt.length(),
				tt.length());

		/** encrypted data **/
		System.arraycopy(iSecBufferOut, 3, hsmOut.data, 0, i);

		return HsmConst.T_SUCCESS;
	}

	/*
	 * ========================================================================== =========== 功能描述:
	 * 私钥加密数据 输入结构： 长度 类型 备注 命令类型 1 H 0xE0 命令 1 H 0x04 索引号 2 H
	 * 若使用内部保存的RSA密钥对索引号时可选择0—49；若使用用户公钥则index请填 ffff 私钥长度 2 H 当用户使用内部保存的索引号（index !=
	 * ffff）时prilen请填0 数据长度 2 H 填充方式 2 H 1：PKCS1填充方式 3：非填充方式 算法标志 1 H 0：REF格式的私钥明文 1：DER格式的私钥明文
	 * 2：私钥密文 密钥访问口令 8 H 当用户不使用内部保存的密钥对（index = ffff）时，该项为NULL 私钥数据 N H 当用户使用内部保存的密钥对（index !=
	 * ffff）时，该项为NULL 要加密数据 N H 输出结构 长度 类型 备注 应答码 1 A “A” 数据长度 2 H 加密得到的数据 N H 应答码 1 A “E” 错误码 1 H
	 * ====================================================== ================================
	 */

	public int PrivateKeyEncrypt(HsmSession hSession,
			HsmStruct.PrivateKeyEncrypt.PrivateKeyEncryptIn hsmIn,
			HsmStruct.PrivateKeyEncrypt.PrivateKeyEncryptOut hsmOut) {
		int iSndLen = 0, nResult = 0;
		byte[] retCode = new byte[1];
		int privateKeyLen = 0;
		boolean accessFlag = true;

		// command code
		iSecBufferIn[0] = (byte) 0xe0;
		iSecBufferIn[1] = 0x04;
		iSndLen += 2;

		String temp;
		int i;

		if ((hsmIn.rsaIndex[0] == 0x66 && hsmIn.rsaIndex[1] == 0x66 && hsmIn.rsaIndex[2] == 0x66)
				|| (hsmIn.rsaIndex[0] == 0x46 && hsmIn.rsaIndex[1] == 0x46 && hsmIn.rsaIndex[2] == 0x46)) {

			// rsa index
			iSecBufferIn[iSndLen++] = (byte) 0xff;
			iSecBufferIn[iSndLen++] = (byte) 0xff;

			// private key length
			temp = new String(hsmIn.privateKeyLen);
			privateKeyLen = Integer.parseInt(temp);

			iSecBufferIn[iSndLen++] = (byte) ((privateKeyLen >> 8) & 0xff);
			iSecBufferIn[iSndLen++] = (byte) (privateKeyLen & 0xff);

			accessFlag = false;
		} else {
			// rsa index
			temp = new String(hsmIn.rsaIndex);
			i = Integer.parseInt(temp);

			iSecBufferIn[iSndLen++] = (byte) ((i >> 8) & 0xff);
			iSecBufferIn[iSndLen++] = (byte) (i & 0xff);

			// private key length
			iSecBufferIn[iSndLen++] = (byte) 0x00;
			iSecBufferIn[iSndLen++] = (byte) 0x00;

			// accessFlag = false;
		}

		// data length
		temp = new String(hsmIn.dataLen);
		int dataLen = Integer.parseInt(temp);

		iSecBufferIn[iSndLen++] = (byte) ((dataLen >> 8) & 0xff);
		iSecBufferIn[iSndLen++] = (byte) (dataLen & 0xff);

		// padding mode
		iSecBufferIn[iSndLen++] = (byte) (hsmIn.padFlag[0] - 0x30);
		iSecBufferIn[iSndLen++] = (byte) (hsmIn.padFlag[1] - 0x30);

		// encrypt mode
		iSecBufferIn[iSndLen++] = (byte) (hsmIn.athFlag[0] - 0x30);

		// access password
		if (accessFlag == true) {
			// rsa key access password
			if (hsmIn.passwd.length < 8) {
				retCode[0] = (byte) HsmConst.ERR_PASSWD;
				Hex2Str(retCode, hsmOut.reply_code, 1);
				return HsmConst.T_FAIL;
			}

			System.arraycopy(hsmIn.passwd, 0, iSecBufferIn, iSndLen, HsmConst.PWD_LEN);
			iSndLen += HsmConst.PWD_LEN;
		}

		// private key
		System.arraycopy(hsmIn.privateKey, 0, iSecBufferIn, iSndLen, privateKeyLen);
		iSndLen += privateKeyLen;

		// data
		System.arraycopy(hsmIn.encData, 0, iSecBufferIn, iSndLen, dataLen);
		iSndLen += dataLen;

		/*
		 * // send message nResult = hSession.SendData(iSecBufferIn, iSndLen); if (nResult != 0) {
		 * retCode[0] = (byte) nResult; Hex2Str(retCode, hsmOut.reply_code, 1); return
		 * HsmConst.T_FAIL; }
		 * 
		 * // receive message nResult = hSession.RecvData(iSecBufferOut); if (nResult != 0) {
		 * retCode[0] = (byte) nResult; Hex2Str(retCode, hsmOut.reply_code, 1); return
		 * HsmConst.T_FAIL; }
		 */
		nResult = hSession.SndAndRcvData(iSecBufferIn, iSndLen, iSecBufferOut);
		if (nResult != 0) {
			retCode[0] = (byte) nResult;
			Hex2Str(retCode, hsmOut.reply_code, 1);
			return HsmConst.T_FAIL;
		}

		hsmOut.reply_code[0] = 0x30;
		hsmOut.reply_code[1] = 0x30;
		// received data length
		// byte[] tempChange = new byte[4];

		// i = secbuf_out[1] * 256 + secbuf_out[2];
		i = ((iSecBufferOut[1] << 8) & 0xff00) | (iSecBufferOut[2] & 0xff);
		String tt = Integer.toString(i);
		if (tt.length() > 4) {
			retCode[0] = (byte) HsmConst.ERR_DATA_LEN;
			Hex2Str(retCode, hsmOut.reply_code, 1);
			return HsmConst.T_FAIL;
		}
		System.arraycopy("0000".getBytes(), 0, hsmOut.dataLen, 0, HsmConst.DATA_LEN);
		System.arraycopy(tt.getBytes(), 0, hsmOut.dataLen, HsmConst.DATA_LEN - tt.length(),
				tt.length());

		// encrypted data
		System.arraycopy(iSecBufferOut, 3, hsmOut.data, 0, i);

		return HsmConst.T_SUCCESS;
	}

	/*
	 * ========================================================================== =========== 功能描述:
	 * 私钥解密数据 输入结构： 长度 类型 备注 命令类型 1 H 0xE0 命令 1 H 0x05 索引号 2 H
	 * 若使用内部保存的RSA密钥对索引号时可选择0—49；若使用用户公钥则index请填 ffff 私钥长度 2 H 当用户使用内部保存的索引号（index !=
	 * ffff）时prilen请填0 数据长度 2 H 填充方式 2 H 1：PKCS1填充方式 3：非填充方式 算法标志 1 H 0：REF格式的私钥明文 1：DER格式的私钥明文
	 * 2：私钥密文 密钥访问口令 8 H 当用户不使用内部保存的密钥对（index = ffff）时，该项为NULL 私钥数据 N H 当用户使用内部保存的密钥对（index !=
	 * ffff）时，该项为NULL 加密得到的数据 N H 输出结构 长度 类型 备注 应答码 1 A “A” 数据长度 2 H 解密得到的数据 N H 应答码 1 A “E” 错误码 1 H
	 * ====================================================== ================================
	 */

	public int PrivateKeyDecrypt(HsmSession hSession,
			HsmStruct.PrivateKeyEncrypt.PrivateKeyEncryptIn hsmIn,
			HsmStruct.PrivateKeyEncrypt.PrivateKeyEncryptOut hsmOut) {
		int iSndLen = 0, nResult = 0;
		byte[] retCode = new byte[1];
		int privateKeyLen = 0;
		boolean accessFlag = true;

		// command code
		iSecBufferIn[0] = (byte) 0xe0;
		iSecBufferIn[1] = 0x05;
		iSndLen += 2;

		String temp;
		int i;

		if ((hsmIn.rsaIndex[0] == 0x66 && hsmIn.rsaIndex[1] == 0x66 && hsmIn.rsaIndex[2] == 0x66)
				|| (hsmIn.rsaIndex[0] == 0x46 && hsmIn.rsaIndex[1] == 0x46 && hsmIn.rsaIndex[2] == 0x46)) {
			// rsa index
			iSecBufferIn[iSndLen++] = (byte) 0xff;
			iSecBufferIn[iSndLen++] = (byte) 0xff;

			// private key length
			temp = new String(hsmIn.privateKeyLen);
			privateKeyLen = Integer.parseInt(temp);

			iSecBufferIn[iSndLen++] = (byte) ((privateKeyLen >> 8) & 0xff);
			iSecBufferIn[iSndLen++] = (byte) (privateKeyLen & 0xff);
			accessFlag = false;
		}

		else {
			// rsa index
			temp = new String(hsmIn.rsaIndex);
			i = Integer.parseInt(temp);

			iSecBufferIn[iSndLen++] = (byte) ((i >> 8) & 0xff);
			iSecBufferIn[iSndLen++] = (byte) (i & 0xff);

			// private key length
			iSecBufferIn[iSndLen++] = (byte) 0x00;
			iSecBufferIn[iSndLen++] = (byte) 0x00;

		}

		// data length
		temp = new String(hsmIn.dataLen);
		int dataLen = Integer.parseInt(temp);

		iSecBufferIn[iSndLen++] = (byte) ((dataLen >> 8) & 0xff);
		iSecBufferIn[iSndLen++] = (byte) (dataLen & 0xff);

		// padding mode
		iSecBufferIn[iSndLen++] = (byte) (hsmIn.padFlag[0] - 0x30);
		iSecBufferIn[iSndLen++] = (byte) (hsmIn.padFlag[1] - 0x30);

		// decrypt mode
		iSecBufferIn[iSndLen++] = (byte) (hsmIn.athFlag[0] - 0x30);

		// access password
		if (accessFlag == true) {
			// rsa key access password
			if (hsmIn.passwd.length < 8) {
				retCode[0] = (byte) HsmConst.ERR_PASSWD;
				Hex2Str(retCode, hsmOut.reply_code, 1);
				return HsmConst.T_FAIL;
			}

			System.arraycopy(hsmIn.passwd, 0, iSecBufferIn, iSndLen, HsmConst.PWD_LEN);
			iSndLen += HsmConst.PWD_LEN;
		}

		// private key
		System.arraycopy(hsmIn.privateKey, 0, iSecBufferIn, iSndLen, privateKeyLen);
		iSndLen += privateKeyLen;

		// data
		System.arraycopy(hsmIn.encData, 0, iSecBufferIn, iSndLen, dataLen);
		iSndLen += dataLen;

		/*
		 * // send message nResult = hSession.SendData(iSecBufferIn, iSndLen); if (nResult != 0) {
		 * retCode[0] = (byte) nResult; Hex2Str(retCode, hsmOut.reply_code, 1); return
		 * HsmConst.T_FAIL; }
		 * 
		 * // receive message nResult = hSession.RecvData(iSecBufferOut); if (nResult != 0) {
		 * retCode[0] = (byte) nResult; Hex2Str(retCode, hsmOut.reply_code, 1); return
		 * HsmConst.T_FAIL; }
		 */
		nResult = hSession.SndAndRcvData(iSecBufferIn, iSndLen, iSecBufferOut);
		if (nResult != 0) {
			retCode[0] = (byte) nResult;
			Hex2Str(retCode, hsmOut.reply_code, 1);
			return HsmConst.T_FAIL;
		}

		hsmOut.reply_code[0] = 0x30;
		hsmOut.reply_code[1] = 0x30;
		// received data length
		// byte[] tempChange = new byte[4];

		// i = secbuf_out[1] * 256 + secbuf_out[2];
		i = ((iSecBufferOut[1] << 8) & 0xff00) | (iSecBufferOut[2] & 0xff);
		String tt = Integer.toString(i);
		if (tt.length() > 4) {
			retCode[0] = (byte) HsmConst.ERR_DATA_LEN;
			Hex2Str(retCode, hsmOut.reply_code, 1);
			return HsmConst.T_FAIL;
		}
		System.arraycopy("0000".getBytes(), 0, hsmOut.dataLen, 0, HsmConst.DATA_LEN);
		System.arraycopy(tt.getBytes(), 0, hsmOut.dataLen, HsmConst.DATA_LEN - tt.length(),
				tt.length());
		// decrypted data
		System.arraycopy(iSecBufferOut, 3, hsmOut.data, 0, i);

		return HsmConst.T_SUCCESS;
	}

	/*
	 * ========================================================================== =========== 功能描述:
	 * 签名 输入结构： 长度 类型 备注 命令类型 1 H 0xE0 命令 1 H 0x06 索引号 2 H 若使用内部保存的RSA密钥对索引号时可选择0—49；若使用用户公钥则index请填
	 * ffff 私钥长度 2 H 当用户使用内部保存的索引号（index != ffff）时prilen请填0 签名数据长度 2 H 签名运算模式 1 H 0：sha1算法模式
	 * 1：md5算法模式 算法标志 1 H 0：REF格式的私钥明文 1：DER格式的私钥明文 2：私钥密文 密钥访问口令 8 H 当用户不使用内部保存的密钥对（index =
	 * ffff）时，该项为NULL 私钥数据 N H 当用户使用内部保存的密钥对（index != ffff）时，该项为NULL 签名数据明文 N H 输出结构 长度 类型 备注 应答码 1
	 * A “A” 数据长度 2 H 签名运算得到的数据 N H 应答码 1 A “E” 错误码 1 H
	 * ==================================================== ==================================
	 */
	public int Sign(HsmSession hSession, HsmStruct.Sign.SignIn hsmIn, HsmStruct.Sign.SignOut hsmOut) {
		int iSndLen = 0, nResult = 0;
		byte[] retCode = new byte[1];
		int privateKeyLen = 0;
		boolean accessFlag = true;

		// command code
		iSecBufferIn[0] = (byte) 0xe0;
		iSecBufferIn[1] = 0x06;
		iSndLen += 2;

		String temp;
		int i;

		if ((hsmIn.rsaIndex[0] == 0x66 && hsmIn.rsaIndex[1] == 0x66 && hsmIn.rsaIndex[2] == 0x66)
				|| (hsmIn.rsaIndex[0] == 0x46 && hsmIn.rsaIndex[1] == 0x46 && hsmIn.rsaIndex[2] == 0x46)) {

			iSecBufferIn[iSndLen++] = (byte) 0xff;
			iSecBufferIn[iSndLen++] = (byte) 0xff;

			// private key length
			temp = new String(hsmIn.privateKeyLen);
			privateKeyLen = Integer.parseInt(temp);

			iSecBufferIn[iSndLen++] = (byte) ((privateKeyLen >> 8) & 0xff);
			iSecBufferIn[iSndLen++] = (byte) (privateKeyLen & 0xff);
			accessFlag = false;
		} else {
			// rsa index
			temp = new String(hsmIn.rsaIndex);
			i = Integer.parseInt(temp);

			iSecBufferIn[iSndLen++] = (byte) ((i >> 8) & 0xff);
			iSecBufferIn[iSndLen++] = (byte) (i & 0xff);

			// private key length
			iSecBufferIn[iSndLen++] = (byte) 0x00;
			iSecBufferIn[iSndLen++] = (byte) 0x00;

		}

		// data length
		temp = new String(hsmIn.dataLen);
		int dataLen = Integer.parseInt(temp);

		iSecBufferIn[iSndLen++] = (byte) ((dataLen >> 8) & 0xff);
		iSecBufferIn[iSndLen++] = (byte) (dataLen & 0xff);

		// digit mode
		iSecBufferIn[iSndLen++] = (byte) (hsmIn.signFlag[0] - 0x30);

		// encrypt mode
		iSecBufferIn[iSndLen++] = (byte) (hsmIn.athFlag[0] - 0x30);

		// access password
		if (accessFlag == true) {
			// rsa key access password
			if (hsmIn.passwd.length < 8) {
				retCode[0] = (byte) HsmConst.ERR_PASSWD;
				Hex2Str(retCode, hsmOut.reply_code, 1);
				return HsmConst.T_FAIL;
			}

			System.arraycopy(hsmIn.passwd, 0, iSecBufferIn, iSndLen, HsmConst.PWD_LEN);
			iSndLen += HsmConst.PWD_LEN;
		}

		// private key
		System.arraycopy(hsmIn.privateKey, 0, iSecBufferIn, iSndLen, privateKeyLen);
		iSndLen += privateKeyLen;

		// data
		System.arraycopy(hsmIn.data, 0, iSecBufferIn, iSndLen, dataLen);
		iSndLen += dataLen;

		/*
		 * // send message nResult = hSession.SendData(iSecBufferIn, iSndLen); if (nResult != 0) {
		 * retCode[0] = (byte) nResult; Hex2Str(retCode, hsmOut.reply_code, 1); return
		 * HsmConst.T_FAIL; }
		 * 
		 * // receive message nResult = hSession.RecvData(iSecBufferOut); if (nResult != 0) {
		 * retCode[0] = (byte) nResult; Hex2Str(retCode, hsmOut.reply_code, 1); return
		 * HsmConst.T_FAIL; }
		 */
		nResult = hSession.SndAndRcvData(iSecBufferIn, iSndLen, iSecBufferOut);
		if (nResult != 0) {
			retCode[0] = (byte) nResult;
			Hex2Str(retCode, hsmOut.reply_code, 1);
			return HsmConst.T_FAIL;
		}

		hsmOut.reply_code[0] = 0x30;
		hsmOut.reply_code[1] = 0x30;
		// received data length
		// byte[] tempChange = new byte[4];

		// i = secbuf_out[1] * 256 + secbuf_out[2];
		i = ((iSecBufferOut[1] << 8) & 0xff00) | (iSecBufferOut[2] & 0xff);
		String tt = Integer.toString(i);
		if (tt.length() > 4) {
			retCode[0] = (byte) HsmConst.ERR_DATA_LEN;
			Hex2Str(retCode, hsmOut.reply_code, 1);
			return HsmConst.T_FAIL;
		}
		System.arraycopy("0000".getBytes(), 0, hsmOut.dataLen, 0, HsmConst.DATA_LEN);
		System.arraycopy(tt.getBytes(), 0, hsmOut.dataLen, HsmConst.DATA_LEN - tt.length(),
				tt.length());

		// sign data
		System.arraycopy(iSecBufferOut, 3, hsmOut.data, 0, i);

		return HsmConst.T_SUCCESS;
	}

	/*
	 * ========================================================================== =========== 功能描述:
	 * 验签 输入结构： 长度 类型 备注 命令类型 1 H 0xE0 命令 1 H 0x07 索引号 2 H 若使用内部保存的RSA密钥对索引号时可选择0—49；若使用用户公钥则index请填
	 * ffff 公钥长度 2 H 当用户使用内部保存的索引号（index != ffff）时publen应为0 签名数据长度 2 H 签名码长度 2 H 签名运算模式 1 H
	 * 0：sha1算法模式 1：md5算法模式 算法标志 1 H 0：REF格式的公钥明文 1：DER格式的公钥明文 密钥访问口令 8 H 当用户不使用内部保存的密钥对（index =
	 * ffff）时，该项为NULL 公钥数据 N H 当用户使用内部保存的密钥对（index != ffff）时，该项为NULL 签名数据 N H 签名运算得到的数据 N H 输出结构 长度
	 * 类型 备注 应答码 1 A “A” 应答码 1 A “E” 错误码 1 H
	 * ============================================================== ========================
	 */
	public int VerifySign(HsmSession hSession, HsmStruct.VerifySign.VerifySignIn hsmIn,
			HsmStruct.VerifySign.VerifySignOut hsmOut) {
		int iSndLen = 0, nResult = 0;
		byte[] retCode = new byte[1];
		int publicKeyLen = 0;
		boolean accessFlag = true;

		/** command code **/
		iSecBufferIn[0] = (byte) 0xe0;
		iSecBufferIn[1] = 0x07;
		iSndLen += 2;

		String temp;
		int i;

		if ((hsmIn.rsaIndex[0] == 0x66 && hsmIn.rsaIndex[1] == 0x66 && hsmIn.rsaIndex[2] == 0x66)
				|| (hsmIn.rsaIndex[0] == 0x46 && hsmIn.rsaIndex[1] == 0x46 && hsmIn.rsaIndex[2] == 0x46)) {

			iSecBufferIn[iSndLen++] = (byte) 0xff;
			iSecBufferIn[iSndLen++] = (byte) 0xff;

			/** public key length **/
			temp = new String(hsmIn.publicKeyLen);
			publicKeyLen = Integer.parseInt(temp);

			iSecBufferIn[iSndLen++] = (byte) ((publicKeyLen >> 8) & 0xff);
			iSecBufferIn[iSndLen++] = (byte) (publicKeyLen & 0xff);

			accessFlag = false;
		} else {
			/** RSA index **/
			temp = new String(hsmIn.rsaIndex);
			i = Integer.parseInt(temp);

			iSecBufferIn[iSndLen++] = (byte) ((i >> 8) & 0xff);
			iSecBufferIn[iSndLen++] = (byte) (i & 0xff);

			/** public key length **/
			iSecBufferIn[iSndLen++] = (byte) 0x00;
			iSecBufferIn[iSndLen++] = (byte) 0x00;

		}

		/** sign length **/
		temp = new String(hsmIn.signDataLen);
		int signDataLen = Integer.parseInt(temp);

		iSecBufferIn[iSndLen++] = (byte) ((signDataLen >> 8) & 0xff);
		iSecBufferIn[iSndLen++] = (byte) (signDataLen & 0xff);

		/** sign code length **/
		temp = new String(hsmIn.signCodeLen);
		int signCodeLen = Integer.parseInt(temp);

		iSecBufferIn[iSndLen++] = (byte) ((signCodeLen >> 8) & 0xff);
		iSecBufferIn[iSndLen++] = (byte) (signCodeLen & 0xff);

		/** sign mode **/
		iSecBufferIn[iSndLen++] = (byte) (hsmIn.signFlag[0] - 0x30);

		/** ath Flag **/
		iSecBufferIn[iSndLen++] = (byte) (hsmIn.athFlag[0] - 0x30);

		/** access password **/
		if (accessFlag == true) {
			int pwdLen = hsmIn.passwd.length;
			logger.debug("加密机的密码长度是：[" + pwdLen + "]");
			/** RSA key access password **/
			if (hsmIn.passwd.length < 8) {
				retCode[0] = (byte) HsmConst.ERR_PASSWD;
				Hex2Str(retCode, hsmOut.reply_code, 1);
				return HsmConst.T_FAIL;
			}

			System.arraycopy(hsmIn.passwd, 0, iSecBufferIn, iSndLen, HsmConst.PWD_LEN);
			iSndLen += HsmConst.PWD_LEN;
		}

		/** private key **/
		System.arraycopy(hsmIn.publicKey, 0, iSecBufferIn, iSndLen, publicKeyLen);
		iSndLen += publicKeyLen;

		/** sign data **/
		System.arraycopy(hsmIn.data, 0, iSecBufferIn, iSndLen, signDataLen);
		iSndLen += signDataLen;

		/** sign code data **/
		System.arraycopy(hsmIn.sign, 0, iSecBufferIn, iSndLen, signCodeLen);
		iSndLen += signCodeLen;

		/*
		 * // send message nResult = hSession.SendData(iSecBufferIn, iSndLen); if (nResult != 0) {
		 * retCode[0] = (byte) nResult; Hex2Str(retCode, hsmOut.reply_code, 1); return
		 * HsmConst.T_FAIL; }
		 * 
		 * // receive message nResult = hSession.RecvData(iSecBufferOut); if (nResult != 0) {
		 * retCode[0] = (byte) nResult; Hex2Str(retCode, hsmOut.reply_code, 1); return
		 * HsmConst.T_FAIL; }
		 */
		nResult = hSession.SndAndRcvData(iSecBufferIn, iSndLen, iSecBufferOut);
		logger.debug("SndAndRcvData 的结果是：[" + nResult + "]");
		if (nResult != 0) {
			retCode[0] = (byte) nResult;
			Hex2Str(retCode, hsmOut.reply_code, 1);
			return HsmConst.T_FAIL;
		}

		hsmOut.reply_code[0] = 0x30;
		hsmOut.reply_code[1] = 0x30;

		return HsmConst.T_SUCCESS;
	}

	/*
	 * ========================================================================== =========== 功能描述:
	 * 查询RSAKey 输入结构： 长度 类型 备注 命令类型 1 H 0xE0 命令 1 H 0x08 输出结构 长度 类型 备注 应答码 1 A “A” 加密机中存在的密钥对数量 2 H
	 * 索引值列表 N H 应答码 1 A “E” 错误码 1 H ======
	 * ==================================================================== ============
	 */
	public int QueryRSAKey(HsmSession hSession, HsmStruct.QueryRSAKey.QueryRSAKeyIn hsmIn,
			HsmStruct.QueryRSAKey.QueryRSAKeyOut hsmOut) {
		int iSndLen = 0, nResult = 0;
		byte[] retCode = new byte[1];
		// int publicKeyLen = 0;
		// boolean accessFlag = true;

		// command code
		iSecBufferIn[0] = (byte) 0xe0;
		iSecBufferIn[1] = 0x08;
		iSndLen += 2;

		/*
		 * // send message nResult = hSession.SendData(iSecBufferIn, iSndLen); if (nResult != 0) {
		 * retCode[0] = (byte) nResult; Hex2Str(retCode, hsmOut.reply_code, 1); return
		 * HsmConst.T_FAIL; }
		 * 
		 * // receive message nResult = hSession.RecvData(iSecBufferOut); if (nResult != 0) {
		 * retCode[0] = (byte) nResult; Hex2Str(retCode, hsmOut.reply_code, 1); return
		 * HsmConst.T_FAIL; }
		 */
		nResult = hSession.SndAndRcvData(iSecBufferIn, iSndLen, iSecBufferOut);
		if (nResult != 0) {
			retCode[0] = (byte) nResult;
			Hex2Str(retCode, hsmOut.reply_code, 1);
			return HsmConst.T_FAIL;
		}

		// received data length
		// byte[] tempChange = new byte[4];

		// int i = secbuf_out[1] * 256 + secbuf_out[2];
		int i = ((iSecBufferOut[1] << 8) & 0xff00) | (iSecBufferOut[2] & 0xff);
		String tt = Integer.toString(i);
		if (tt.length() > 4) {
			retCode[0] = (byte) HsmConst.ERR_DATA_LEN;
			Hex2Str(retCode, hsmOut.reply_code, 1);
			return HsmConst.T_FAIL;
		}

		hsmOut.reply_code[0] = 0x30;
		hsmOut.reply_code[1] = 0x30;

		System.arraycopy("0000".getBytes(), 0, hsmOut.keyNum, 0, HsmConst.DATA_LEN);
		System.arraycopy(tt.getBytes(), 0, hsmOut.keyNum, HsmConst.DATA_LEN - tt.length(),
				tt.length());

		// 按整型输出
		if (i != 0) {
			System.arraycopy(iSecBufferOut, 3, hsmOut.indexList, 0, i * 2);

		}
		return HsmConst.T_SUCCESS;
	}

	/*
	 * ========================================================================== =========== 功能描述:
	 * 删除RSAKey 输入结构： 长度 类型 备注 命令类型 1 H 0xE0 命令 1 H 0x09 索引号 2 H 可选0－49 密钥对访问口令 8 H 输出结构 长度 类型 备注
	 * 应答码 1 A “A” 应答码 1 A “E” 错误码 1 H ========
	 * ================================================================== ============
	 */

	public int DelRSAKey(HsmSession hSession, HsmStruct.DelRSAKey.DelRSAKeyIn hsmIn,
			HsmStruct.DelRSAKey.DelRSAKeyOut hsmOut) {
		int iSndLen = 0, nResult = 0;
		byte[] retCode = new byte[1];

		// command code
		iSecBufferIn[0] = (byte) 0xe0;
		iSecBufferIn[1] = 0x09;
		iSndLen += 2;

		// rsa index
		String temp = new String(hsmIn.rsaIndex);
		int i = Integer.parseInt(temp);

		iSecBufferIn[iSndLen++] = (byte) ((i >> 8) & 0xff);
		iSecBufferIn[iSndLen++] = (byte) (i & 0xff);

		// rsa key access password
		if (hsmIn.passwd.length < 8) {
			retCode[0] = (byte) HsmConst.ERR_PASSWD;
			Hex2Str(retCode, hsmOut.reply_code, 1);
			return HsmConst.T_FAIL;
		}

		System.arraycopy(hsmIn.passwd, 0, iSecBufferIn, iSndLen, HsmConst.PWD_LEN);
		iSndLen += HsmConst.PWD_LEN;

		/*
		 * // send message nResult = hSession.SendData(iSecBufferIn, iSndLen); if (nResult != 0) {
		 * retCode[0] = (byte) nResult; Hex2Str(retCode, hsmOut.reply_code, 1); return
		 * HsmConst.T_FAIL; }
		 * 
		 * // receive message nResult = hSession.RecvData(iSecBufferOut); if (nResult != 0) {
		 * retCode[0] = (byte) nResult; Hex2Str(retCode, hsmOut.reply_code, 1); return
		 * HsmConst.T_FAIL; }
		 */
		nResult = hSession.SndAndRcvData(iSecBufferIn, iSndLen, iSecBufferOut);
		if (nResult != 0) {
			retCode[0] = (byte) nResult;
			Hex2Str(retCode, hsmOut.reply_code, 1);
			return HsmConst.T_FAIL;
		}

		hsmOut.reply_code[0] = 0x30;
		hsmOut.reply_code[1] = 0x30;

		return HsmConst.T_SUCCESS;
	}

	/*
	 * ========================================================================== =========== 功能描述:
	 * 导入私钥 输入结构： 长度 类型 备注 命令类型 1 H 0xE0 命令 1 H 0x0a 索引号 2 H 可选0－49 私钥长度 2 H 算法标志 1 H 0：REF格式的明文
	 * 1：DER格式的明文 2：密文 密钥对访问口令 8 H 私钥数据 N H 输出结构： 长度 类型 备注 应答码 1 A “A” 应答码 1 A “E” 错误码 1 H
	 * ============================ ==========================================================
	 */

	public int ImportPrivateKey(HsmSession hSession,
			HsmStruct.ImportPrivateKey.ImportPrivateKeyIn hsmIn,
			HsmStruct.ImportPrivateKey.ImportPrivateKeyOut hsmOut) {
		int iSndLen = 0, nResult = 0;
		byte[] retCode = new byte[1];

		// command code
		iSecBufferIn[0] = (byte) 0xe0;
		iSecBufferIn[1] = 0x0a;
		iSndLen += 2;

		// rsa index
		String temp = new String(hsmIn.rsaIndex);
		int i = Integer.parseInt(temp);

		iSecBufferIn[iSndLen++] = (byte) ((i >> 8) & 0xff);
		iSecBufferIn[iSndLen++] = (byte) (i & 0xff);

		temp = new String(hsmIn.privateKeyLen);
		i = Integer.parseInt(temp);

		iSecBufferIn[iSndLen++] = (byte) ((i >> 8) & 0xff);
		iSecBufferIn[iSndLen++] = (byte) (i & 0xff);

		iSecBufferIn[iSndLen++] = (byte) (hsmIn.athFlag[0] - 0x30);

		// rsa key access password
		if (hsmIn.passwd.length < 8) {
			retCode[0] = (byte) HsmConst.ERR_PASSWD;
			Hex2Str(retCode, hsmOut.reply_code, 1);
			return HsmConst.T_FAIL;
		}

		System.arraycopy(hsmIn.passwd, 0, iSecBufferIn, iSndLen, HsmConst.PWD_LEN);
		iSndLen += HsmConst.PWD_LEN;

		System.arraycopy(hsmIn.privateKey, 0, iSecBufferIn, iSndLen, i);
		iSndLen += i;

		/*
		 * // send message nResult = hSession.SendData(iSecBufferIn, iSndLen); if (nResult != 0) {
		 * retCode[0] = (byte) nResult; Hex2Str(retCode, hsmOut.reply_code, 1); return
		 * HsmConst.T_FAIL; }
		 * 
		 * // receive message nResult = hSession.RecvData(iSecBufferOut); if (nResult != 0) {
		 * retCode[0] = (byte) nResult; Hex2Str(retCode, hsmOut.reply_code, 1); return
		 * HsmConst.T_FAIL; }
		 */
		nResult = hSession.SndAndRcvData(iSecBufferIn, iSndLen, iSecBufferOut);
		if (nResult != 0) {
			retCode[0] = (byte) nResult;
			Hex2Str(retCode, hsmOut.reply_code, 1);
			return HsmConst.T_FAIL;
		}

		hsmOut.reply_code[0] = 0x30;
		hsmOut.reply_code[1] = 0x30;

		return HsmConst.T_SUCCESS;
	}

	/*
	 * ========================================================================== =========== 功能描述:
	 * HASH初始化 输入结构： 长度 类型 备注 命令类型 1 H 0xE0 命令 1 H 0x10 HASH模式 1 H 0：SHA1模式 1：MD5模式 输出结构： 长度 类型 备注
	 * 应答码 1 A “A” 过程变量缓存长度 2 H 过程变量缓存数据 N H 应答码 1 A “E” 错误码 1 H
	 * ====================================================== ================================
	 */

	public int HashInit(HsmSession hSession, HsmStruct.HashInit.HashInitIn hsmIn,
			HsmStruct.HashInit.HashInitOut hsmOut) {
		int iSndLen = 0, nResult = 0;
		byte[] retCode = new byte[1];

		// command code
		iSecBufferIn[0] = (byte) 0xe0;
		iSecBufferIn[1] = 0x10;
		iSndLen += 2;

		iSecBufferIn[iSndLen++] = (byte) (hsmIn.hashFlag[0] - '0');

		/*
		 * // send message nResult = hSession.SendData(iSecBufferIn, iSndLen); if (nResult != 0) {
		 * retCode[0] = (byte) nResult; Hex2Str(retCode, hsmOut.reply_code, 1); return
		 * HsmConst.T_FAIL; }
		 * 
		 * // receive message nResult = hSession.RecvData(iSecBufferOut); if (nResult != 0) {
		 * retCode[0] = (byte) nResult; Hex2Str(retCode, hsmOut.reply_code, 1); return
		 * HsmConst.T_FAIL; }
		 */
		nResult = hSession.SndAndRcvData(iSecBufferIn, iSndLen, iSecBufferOut);
		if (nResult != 0) {
			retCode[0] = (byte) nResult;
			Hex2Str(retCode, hsmOut.reply_code, 1);
			return HsmConst.T_FAIL;
		}

		hsmOut.reply_code[0] = 0x30;
		hsmOut.reply_code[1] = 0x30;
		// received data length
		// byte[] tempChange = new byte[4];

		// int i = secbuf_out[1] * 256 + secbuf_out[2];
		int i = ((iSecBufferOut[1] << 8) & 0xff00) | (iSecBufferOut[2] & 0xff);
		String tt = Integer.toString(i);
		if (tt.length() > 4) {
			retCode[0] = (byte) HsmConst.ERR_DATA_LEN;
			Hex2Str(retCode, hsmOut.reply_code, 1);
			return HsmConst.T_FAIL;
		}

		System.arraycopy("0000".getBytes(), 0, hsmOut.dataLen, 0, HsmConst.DATA_LEN);
		System.arraycopy(tt.getBytes(), 0, hsmOut.dataLen, HsmConst.DATA_LEN - tt.length(),
				tt.length());

		System.arraycopy(iSecBufferOut, 3, hsmOut.data, 0, i);

		return HsmConst.T_SUCCESS;
	}

	/*
	 * ========================================================================== =========== 功能描述:
	 * 多包HASH运算 输入结构： 长度 类型 备注 命令类型 1 H 0xE0 命令 1 H 0x11 HASH算法 1 H 0：SHA1模式 1：MD5模式 过程变量缓存长度 2 H
	 * 作HASH运算的数据长度 2 H 过程变量缓存数据 N H 作HASH运算的数据 N H 输出结构： 长度 类型 备注 应答码 1 A “A” 过程变量缓存长度 2 H 过程变量缓存数据
	 * N H 应答码 1 A “E” 错误码 1 H ============================================================
	 * ==========================
	 */
	public int HashUpdate(HsmSession hSession, HsmStruct.HashUpdate.HashUpdateIn hsmIn,
			HsmStruct.HashUpdate.HashUpdateOut hsmOut) {
		int iSndLen = 0, nResult = 0;
		byte[] retCode = new byte[1];
		int ivLen = 0, dataLen = 0;

		// command code
		iSecBufferIn[0] = (byte) 0xe0;
		iSecBufferIn[1] = 0x11;
		iSndLen += 2;

		iSecBufferIn[iSndLen++] = (byte) (hsmIn.hashFlag[0] - '0');

		// iv length
		String temp = new String(hsmIn.ivLen);
		ivLen = Integer.parseInt(temp);

		iSecBufferIn[iSndLen++] = (byte) ((ivLen >> 8) & 0xff);
		iSecBufferIn[iSndLen++] = (byte) (ivLen & 0xff);

		// data length
		temp = new String(hsmIn.dataLen);
		dataLen = Integer.parseInt(temp);

		iSecBufferIn[iSndLen++] = (byte) ((dataLen >> 8) & 0xff);
		iSecBufferIn[iSndLen++] = (byte) (dataLen & 0xff);

		// iv data
		// System.arraycopy(hsmIn.ivData, 0, secbuf_in, iSndLen,
		// hsmIn.ivData.length);
		System.arraycopy(hsmIn.ivData, 0, iSecBufferIn, iSndLen, ivLen);
		iSndLen += ivLen;

		// data
		// System.arraycopy(hsmIn.data, 0, secbuf_in, iSndLen,
		// hsmIn.data.length);
		System.arraycopy(hsmIn.data, 0, iSecBufferIn, iSndLen, dataLen);
		iSndLen += dataLen;

		/*
		 * // send message nResult = hSession.SendData(iSecBufferIn, iSndLen); if (nResult != 0) {
		 * retCode[0] = (byte) nResult; Hex2Str(retCode, hsmOut.reply_code, 1); return
		 * HsmConst.T_FAIL; }
		 * 
		 * // receive message nResult = hSession.RecvData(iSecBufferOut); if (nResult != 0) {
		 * retCode[0] = (byte) nResult; Hex2Str(retCode, hsmOut.reply_code, 1); return
		 * HsmConst.T_FAIL; }
		 */
		nResult = hSession.SndAndRcvData(iSecBufferIn, iSndLen, iSecBufferOut);
		if (nResult != 0) {
			retCode[0] = (byte) nResult;
			Hex2Str(retCode, hsmOut.reply_code, 1);
			return HsmConst.T_FAIL;
		}

		hsmOut.reply_code[0] = 0x30;
		hsmOut.reply_code[1] = 0x30;

		// received data length
		// byte[] tempChange = new byte[4];

		// int i = secbuf_out[1] * 256 + secbuf_out[2];
		int i = ((iSecBufferOut[1] << 8) & 0xff00) | (iSecBufferOut[2] & 0xff);
		String tt = Integer.toString(i);
		if (tt.length() > 4) {
			retCode[0] = (byte) HsmConst.ERR_DATA_LEN;
			Hex2Str(retCode, hsmOut.reply_code, 1);
			return HsmConst.T_FAIL;
		}

		System.arraycopy("0000".getBytes(), 0, hsmOut.dataLen, 0, HsmConst.DATA_LEN);
		System.arraycopy(tt.getBytes(), 0, hsmOut.dataLen, HsmConst.DATA_LEN - tt.length(),
				tt.length());

		System.arraycopy(iSecBufferOut, 3, hsmOut.data, 0, i);

		return HsmConst.T_SUCCESS;
	}

	/*
	 * ========================================================================== =========== 功能描述:
	 * 最后HASH运算 输入结构： 长度 类型 备注 命令类型 1 H 0xE0 命令 1 H 0x12 HASH算法 1 H 0：SHA1模式 1：MD5模式 过程变量缓存长度 2 H
	 * 过程变量缓存数据 N H 输出结构： 长度 类型 备注 应答码 1 A “A” HASH值数据长度 2 H HASH值数据 N H 应答码 1 A “E” 错误码 1 H
	 * ==================== ==================================================================
	 */
	public int HashFinal(HsmSession hSession, HsmStruct.HashFinal.HashFinalIn hsmIn,
			HsmStruct.HashFinal.HashFinalOut hsmOut) {
		int iSndLen = 0, nResult = 0;
		byte[] retCode = new byte[1];
		int ivLen = 0;

		// command code
		iSecBufferIn[0] = (byte) 0xe0;
		iSecBufferIn[1] = 0x12;
		iSndLen += 2;

		iSecBufferIn[iSndLen++] = (byte) (hsmIn.hashFlag[0] - '0');

		// iv length
		String temp = new String(hsmIn.ivLen);
		ivLen = Integer.parseInt(temp);

		iSecBufferIn[iSndLen++] = (byte) ((ivLen >> 8) & 0xff);
		iSecBufferIn[iSndLen++] = (byte) (ivLen & 0xff);

		// iv data
		System.arraycopy(hsmIn.ivData, 0, iSecBufferIn, iSndLen, hsmIn.ivData.length);
		iSndLen += ivLen;

		/*
		 * // send message nResult = hSession.SendData(iSecBufferIn, iSndLen); if (nResult != 0) {
		 * retCode[0] = (byte) nResult; Hex2Str(retCode, hsmOut.reply_code, 1); return
		 * HsmConst.T_FAIL; }
		 * 
		 * // receive message nResult = hSession.RecvData(iSecBufferOut); if (nResult != 0) {
		 * retCode[0] = (byte) nResult; Hex2Str(retCode, hsmOut.reply_code, 1); return
		 * HsmConst.T_FAIL; }
		 */
		nResult = hSession.SndAndRcvData(iSecBufferIn, iSndLen, iSecBufferOut);
		if (nResult != 0) {
			retCode[0] = (byte) nResult;
			Hex2Str(retCode, hsmOut.reply_code, 1);
			return HsmConst.T_FAIL;
		}

		hsmOut.reply_code[0] = 0x30;
		hsmOut.reply_code[1] = 0x30;

		// received data length
		// byte[] tempChange = new byte[4];

		// int i = secbuf_out[1] * 256 + secbuf_out[2];
		int i = ((iSecBufferOut[1] << 8) & 0xff00) | (iSecBufferOut[2] & 0xff);
		String tt = Integer.toString(i);
		if (tt.length() > 4) {
			retCode[0] = (byte) HsmConst.ERR_DATA_LEN;
			Hex2Str(retCode, hsmOut.reply_code, 1);
			return HsmConst.T_FAIL;
		}

		System.arraycopy("0000".getBytes(), 0, hsmOut.dataLen, 0, HsmConst.DATA_LEN);
		System.arraycopy(tt.getBytes(), 0, hsmOut.dataLen, HsmConst.DATA_LEN - tt.length(),
				tt.length());

		System.arraycopy(iSecBufferOut, 3, hsmOut.data, 0, i);

		return HsmConst.T_SUCCESS;
	}

	/*
	 * ========================================================================== =======
	 * 功能描述:产生对称工作密钥，由指定的BMK加密后输出 输入结构: 命令类型 1 H 0x04 命令 1 H 0x04 BMK索引号 2 H 可选择0—999 密钥长度 1 H
	 * 可选择8、16、24 输出结构: 应答码 1 A “A” 密钥长度 1 H 密钥数据 N * 2 A(非压缩BCD码) 校验值 8 * 2 A(非压缩BCD码) 或 应答码 1 A
	 * “E” 错误码 2 A(非压缩BCD码) ============
	 * ===================================================================
	 */
	public int GenerateWorkKey(HsmSession hSession,
			HsmStruct.GenerateWorkKey.GenerateWorkKeyIn hsmIn,
			HsmStruct.GenerateWorkKey.GenerateWorkKeyOut hsmOut) {
		int iSndLen = 0, nResult = 0;
		byte[] retCode = new byte[1];
		byte keylen;

		// command code; Cmd0404: generate work key encrypt by BMK
		iSecBufferIn[0] = (byte) 0x04;
		iSecBufferIn[1] = 0x04;
		iSndLen = 2;

		String temp = new String(hsmIn.keyIndex); // BMK Index
		int i = Integer.parseInt(temp);
		iSecBufferIn[iSndLen++] = (byte) ((i >> 8) & 0xff);
		iSecBufferIn[iSndLen++] = (byte) (i & 0xff);

		// key length
		temp = new String(hsmIn.keyLen);
		i = Integer.parseInt(temp);
		keylen = (byte) (i & 0xff);

		switch (keylen) { // alg = 0/1/2
			case 8 :
				iSecBufferIn[iSndLen++] = 0;
				break;
			case 16 :
				iSecBufferIn[iSndLen++] = 1;
				break;
			case 24 :
				iSecBufferIn[iSndLen++] = 2;
				break;
			default :
				retCode[0] = (byte) HsmConst.ERR_KEY_LEN;
				Hex2Str(retCode, hsmOut.reply_code, 1);
				return HsmConst.T_FAIL;
		}

		iSecBufferIn[iSndLen++] = keylen; // key len
		/*
		 * // send message nResult = hSession.SendData(iSecBufferIn, iSndLen); if (nResult != 0) {
		 * retCode[0] = (byte) nResult; Hex2Str(retCode, hsmOut.reply_code, 1); return
		 * HsmConst.T_FAIL; }
		 * 
		 * // receive message,key encrypt by bmk nResult = hSession.RecvData(iSecBufferOut); if
		 * (nResult != 0) { retCode[0] = (byte) nResult; Hex2Str(retCode, hsmOut.reply_code, 1);
		 * return HsmConst.T_FAIL; }
		 */
		nResult = hSession.SndAndRcvData(iSecBufferIn, iSndLen, iSecBufferOut);
		if (nResult != 0) {
			retCode[0] = (byte) nResult;
			Hex2Str(retCode, hsmOut.reply_code, 1);
			return HsmConst.T_FAIL;
		}

		hsmOut.reply_code[0] = 0x30;
		hsmOut.reply_code[1] = 0x30;
		keylen = iSecBufferOut[1];
		temp = Integer.toString(keylen);
		System.arraycopy("000".getBytes(), 0, hsmOut.keyLen, 0, HsmConst.PIK_LEN_LEN);
		System.arraycopy(temp.getBytes(), 0, hsmOut.keyLen, HsmConst.PIK_LEN_LEN - temp.length(),
				temp.length());

		// key encrypted by BMK
		byte[] retVal = new byte[24];
		System.arraycopy(iSecBufferOut, 2, retVal, 0, keylen); // work key
		Hex2Str(retVal, hsmOut.workKey, keylen);
		System.arraycopy(iSecBufferOut, 2 + keylen, retVal, 0, keylen); // Check
		// value
		Hex2Str(retVal, hsmOut.checkvalue, 8);

		return HsmConst.T_SUCCESS;
	}

	/*
	 * ========================================================================== =========== 功能描述:
	 * 查询BMK 输入结构： 长度 类型 备注 命令 1 H 0x03 BMK Index 2 H 0~999
	 * 
	 * 输出结构 长度 类型 备注 应答码 1 A “A” 或 应答码 1 A “E” 错误码 1 H ==========================
	 * ============================================================
	 */
	public int QueryBMKey(HsmSession hSession, HsmStruct.QueryBMKey.QueryBMKeyIn hsmIn,
			HsmStruct.QueryBMKey.QueryBMKeyOut hsmOut) {
		int iSndLen = 0, nResult = 0;
		byte[] retCode = new byte[1];
		// command code
		iSecBufferIn[1] = 0x03;
		iSndLen += 1;

		String temp = new String(hsmIn.keyIndex); // BMK Index
		int i = Integer.parseInt(temp);
		iSecBufferIn[iSndLen++] = (byte) ((i >> 8) & 0xff);
		iSecBufferIn[iSndLen++] = (byte) (i & 0xff);

		/*
		 * // send message nResult = hSession.SendData(iSecBufferIn, iSndLen); if (nResult != 0) {
		 * retCode[0] = (byte) nResult; Hex2Str(retCode, hsmOut.reply_code, 1); return
		 * HsmConst.T_FAIL; }
		 * 
		 * // receive message nResult = hSession.RecvData(iSecBufferOut); if (nResult != 0) {
		 * retCode[0] = (byte) nResult; Hex2Str(retCode, hsmOut.reply_code, 1); return
		 * HsmConst.T_FAIL; }
		 */
		nResult = hSession.SndAndRcvData(iSecBufferIn, iSndLen, iSecBufferOut);
		if (nResult != 0) {
			retCode[0] = (byte) nResult;
			Hex2Str(retCode, hsmOut.reply_code, 1);
			return HsmConst.T_FAIL;
		}

		hsmOut.reply_code[0] = 0x30;
		hsmOut.reply_code[1] = 0x30;

		return HsmConst.T_SUCCESS;
	}

	/*
	 * ========================================================================== =======
	 * 功能描述:转加密互联网支付PinBlock 输入结构: 命令类型 1 H 0x04 命令 1 H 0x85 BMK索引号1 2 H 可选择0—999 BMK索引号2 2 H
	 * 可选择0—999 Pinblock 24H Pik1长度 1 H 可选择0、8、16、24(piklen1 = 0 时用BMK1解密PinBlock) Pik1 N H Pik2长度 1
	 * H 可选择0、8、16、24(piklen2 = 0 时用BMK2加密PinBlock) Pik2 N H 输出结构: 应答码 1 A “A” Pinblock 24 * 2
	 * A(非压缩BCD码) 或 应答码 1 A “E” 错误码 2 A(非压缩BCD码) ================
	 * ===============================================================
	 */
	public int ConvertPWD(HsmSession hSession, HsmStruct.ConvertPWD.ConvertPWDIn hsmIn,
			HsmStruct.ConvertPWD.ConvertPWDOut hsmOut) {
		int iSndLen = 0, nResult = 0;
		byte[] retCode = new byte[1];
		int dataLen = 0;
		// int pinLen = 0;

		// command code
		iSecBufferIn[0] = (byte) 0x04;
		iSecBufferIn[1] = (byte) 0x85;
		iSndLen += 2;

		// Bmk Index (in)
		String temp = new String(hsmIn.indexIn);
		dataLen = Integer.parseInt(temp);

		iSecBufferIn[iSndLen++] = (byte) ((dataLen >> 8) & 0xff);
		iSecBufferIn[iSndLen++] = (byte) (dataLen & 0xff);

		// Bmk Index (out)
		temp = new String(hsmIn.indexOut);
		dataLen = Integer.parseInt(temp);

		iSecBufferIn[iSndLen++] = (byte) ((dataLen >> 8) & 0xff);
		iSecBufferIn[iSndLen++] = (byte) (dataLen & 0xff);

		// arithmetic flag in
		iSecBufferIn[iSndLen++] = (byte) (0x01);

		// arithmetic flag out
		iSecBufferIn[iSndLen++] = (byte) (0x01);

		// pinBlock length
		iSecBufferIn[iSndLen++] = (byte) (0x18);

		// pin
		byte[] pinblock = new byte[24];
		Str2Hex(hsmIn.pin, pinblock, HsmConst.PWD_BLK_LEN);
		System.arraycopy(pinblock, 0, iSecBufferIn, iSndLen, HsmConst.PWD_BLK_LEN / 2);
		iSndLen += HsmConst.PWD_BLK_LEN / 2;

		// pik length in
		temp = new String(hsmIn.pikLenIn);
		dataLen = Integer.parseInt(temp);
		iSecBufferIn[iSndLen++] = (byte) (dataLen & 0xff);

		// pik in
		if (dataLen == 8 || dataLen == 16 || dataLen == 24) {
			byte[] tempPikIn = new byte[dataLen];
			Str2Hex(hsmIn.pikIn, tempPikIn, dataLen * 2);
			System.arraycopy(tempPikIn, 0, iSecBufferIn, iSndLen, dataLen);
			iSndLen += dataLen;
		} else if (dataLen != 0) {
			retCode[0] = (byte) HsmConst.ERR_KEY_LEN;
			Hex2Str(retCode, hsmOut.reply_code, 1);
			return HsmConst.T_FAIL;
		}

		// pik length out
		temp = new String(hsmIn.pikLenOut);
		dataLen = Integer.parseInt(temp);
		iSecBufferIn[iSndLen++] = (byte) (dataLen & 0xff);

		// pik out
		if (dataLen == 8 || dataLen == 16 || dataLen == 24) {
			byte[] tempPikOut = new byte[dataLen];
			Str2Hex(hsmIn.pikOut, tempPikOut, dataLen * 2);
			System.arraycopy(tempPikOut, 0, iSecBufferIn, iSndLen, dataLen);
			iSndLen += dataLen;
		} else if (dataLen != 0) {
			retCode[0] = (byte) HsmConst.ERR_KEY_LEN;
			Hex2Str(retCode, hsmOut.reply_code, 1);
			return HsmConst.T_FAIL;
		}

		/*
		 * // send message nResult = hSession.SendData(iSecBufferIn, iSndLen); if (nResult != 0) {
		 * retCode[0] = (byte) nResult; Hex2Str(retCode, hsmOut.reply_code, 1); return
		 * HsmConst.T_FAIL; }
		 * 
		 * // receive message nResult = hSession.RecvData(iSecBufferOut); if (nResult != 0) {
		 * retCode[0] = (byte) nResult; Hex2Str(retCode, hsmOut.reply_code, 1); return
		 * HsmConst.T_FAIL; }
		 */
		nResult = hSession.SndAndRcvData(iSecBufferIn, iSndLen, iSecBufferOut);
		if (nResult != 0) {
			retCode[0] = (byte) nResult;
			Hex2Str(retCode, hsmOut.reply_code, 1);
			return HsmConst.T_FAIL;
		}

		hsmOut.reply_code[0] = 0x30;
		hsmOut.reply_code[1] = 0x30;

		byte[] hexPin = new byte[24];
		System.arraycopy(iSecBufferOut, 1, hexPin, 0, HsmConst.PWD_BLK_LEN / 2);
		Hex2Str(hexPin, hsmOut.pin, HsmConst.PWD_BLK_LEN / 2);

		return HsmConst.T_SUCCESS;
	}

	/*
	 * ========================================================================== =======
	 * 功能描述:转加密传统PinBlock 输入结构: 命令类型 1 H 0x04 命令 1 H 0x85 BMK索引号1 2 H 可选择0—999 BMK索引号2 2 H 可选择0—999
	 * 算法1 1 H 01 -- 不需主帐号1，02 -- 需主帐号1 算法2 1 H 01 -- 不需主帐号2，02 -- 需主帐号2 Pinblock 8 H Pik1长度 1 H
	 * 可选择0、8、16、24(piklen1 = 0 时用BMK1解密PinBlock) Pik1 N H 主帐号1 N A 13~19位主账号（算法1 = 01 时不存在） Pik2长度
	 * 1 H 可选择0、8、16、24(piklen2 = 0 时用BMK2加密PinBlock) Pik2 N H 主帐号2 N A 13~19位主账号（算法2 = 01 时不存在）
	 * 输出结构: 应答码 1 A “A” Pinblock 8* 2 A(非压缩BCD码) 或 应答码 1 A “E” 错误码 2 A(非压缩BCD码)
	 * ================================================== =============================
	 */
	public int ConvertPINBlock(HsmSession hSession,
			HsmStruct.ConvertPINBlock.ConvertPINBlockIn hsmIn,
			HsmStruct.ConvertPINBlock.ConvertPINBlockOut hsmOut) {
		int iSndLen = 0, nResult = 0;
		byte[] retCode = new byte[1];
		int dataLen = 0;
		// int pinLen = 0;

		// command code
		iSecBufferIn[0] = (byte) 0x04;
		iSecBufferIn[1] = (byte) 0x85;
		iSndLen += 2;

		// Bmk Index (in)
		String temp = new String(hsmIn.indexIn);
		dataLen = Integer.parseInt(temp);

		iSecBufferIn[iSndLen++] = (byte) ((dataLen >> 8) & 0xff);
		iSecBufferIn[iSndLen++] = (byte) (dataLen & 0xff);

		// Bmk Index (out)
		temp = new String(hsmIn.indexOut);
		dataLen = Integer.parseInt(temp);

		iSecBufferIn[iSndLen++] = (byte) ((dataLen >> 8) & 0xff);
		iSecBufferIn[iSndLen++] = (byte) (dataLen & 0xff);

		// arithmetic flag in
		iSecBufferIn[iSndLen++] = (byte) (hsmIn.algIn[1] - '0');

		// arithmetic flag out
		iSecBufferIn[iSndLen++] = (byte) (hsmIn.algOut[1] - '0');

		// pinBlock length
		iSecBufferIn[iSndLen++] = (byte) (0x08);

		// pin
		byte[] pinblock = new byte[HsmConst.PIN_BLK_LEN / 2];
		Str2Hex(hsmIn.pin, pinblock, HsmConst.PIN_BLK_LEN);
		System.arraycopy(pinblock, 0, iSecBufferIn, iSndLen, HsmConst.PIN_BLK_LEN / 2);
		iSndLen += HsmConst.PIN_BLK_LEN / 2;

		// pik length in
		temp = new String(hsmIn.pikLenIn);
		dataLen = Integer.parseInt(temp);
		iSecBufferIn[iSndLen++] = (byte) (dataLen & 0xff);

		// pik in
		if (dataLen == 8 || dataLen == 16 || dataLen == 24) {
			byte[] tempPikIn = new byte[dataLen];
			Str2Hex(hsmIn.pikIn, tempPikIn, dataLen * 2);
			System.arraycopy(tempPikIn, 0, iSecBufferIn, iSndLen, dataLen);
			iSndLen += dataLen;
		} else if (dataLen != 0) {
			retCode[0] = (byte) HsmConst.ERR_KEY_LEN;
			Hex2Str(retCode, hsmOut.reply_code, 1);
			return HsmConst.T_FAIL;
		}

		// acount 1 in
		if (hsmIn.algIn[1] == '2') {
			byte[] acco1 = new byte[12];
			byte[] hexacco1 = new byte[6];
			int i;
			for (i = 0; i < HsmConst.PAN_LEN; i++)
				if (hsmIn.accoIn[i] < '0' || hsmIn.accoIn[i] > '9')
					break;
			System.arraycopy(hsmIn.accoIn, i - 13, acco1, 0, 12);
			Str2Hex(acco1, hexacco1, 12);
			iSecBufferIn[iSndLen++] = (byte) 0x00;
			iSecBufferIn[iSndLen++] = (byte) 0x00;
			System.arraycopy(hexacco1, 0, iSecBufferIn, iSndLen, 6);
			iSndLen += 6;
		}

		// pik length out
		temp = new String(hsmIn.pikLenOut);
		dataLen = Integer.parseInt(temp);
		iSecBufferIn[iSndLen++] = (byte) (dataLen & 0xff);

		// pik out
		if (dataLen == 8 || dataLen == 16 || dataLen == 24) {
			byte[] tempPikOut = new byte[dataLen];
			Str2Hex(hsmIn.pikOut, tempPikOut, dataLen * 2);
			System.arraycopy(tempPikOut, 0, iSecBufferIn, iSndLen, dataLen);
			iSndLen += dataLen;
		} else if (dataLen != 0) {
			retCode[0] = (byte) HsmConst.ERR_KEY_LEN;
			Hex2Str(retCode, hsmOut.reply_code, 1);
			return HsmConst.T_FAIL;
		}

		// acount2 out
		if (hsmIn.algOut[1] == '2') {
			byte[] acco2 = new byte[12];
			byte[] hexacco2 = new byte[6];
			int i;
			for (i = 0; i < HsmConst.PAN_LEN; i++)
				if (hsmIn.accoOut[i] < '0' || hsmIn.accoOut[i] > '9')
					break;
			System.arraycopy(hsmIn.accoOut, i - 13, acco2, 0, 12);
			Str2Hex(acco2, hexacco2, 12);
			iSecBufferIn[iSndLen++] = (byte) 0x00;
			iSecBufferIn[iSndLen++] = (byte) 0x00;
			System.arraycopy(hexacco2, 0, iSecBufferIn, iSndLen, 6);
			iSndLen += 6;
		}

		/*
		 * // send message nResult = hSession.SendData(iSecBufferIn, iSndLen); if (nResult != 0) {
		 * retCode[0] = (byte) nResult; Hex2Str(retCode, hsmOut.reply_code, 1); return
		 * HsmConst.T_FAIL; }
		 * 
		 * // receive message nResult = hSession.RecvData(iSecBufferOut); if (nResult != 0) {
		 * retCode[0] = (byte) nResult; Hex2Str(retCode, hsmOut.reply_code, 1); return
		 * HsmConst.T_FAIL; }
		 */
		nResult = hSession.SndAndRcvData(iSecBufferIn, iSndLen, iSecBufferOut);
		if (nResult != 0) {
			retCode[0] = (byte) nResult;
			Hex2Str(retCode, hsmOut.reply_code, 1);
			return HsmConst.T_FAIL;
		}

		hsmOut.reply_code[0] = 0x30;
		hsmOut.reply_code[1] = 0x30;

		byte[] hexPin = new byte[8];
		System.arraycopy(iSecBufferOut, 1, hexPin, 0, HsmConst.PIN_BLK_LEN / 2);
		Hex2Str(hexPin, hsmOut.pin, HsmConst.PIN_BLK_LEN / 2);

		return HsmConst.T_SUCCESS;
	}

	/*
	 * ========================================================================== ==========// /|*
	 * 函数原型: int Bank_GenMac(T_REQUEST_M1_MSG *p_msg_in, T_REPLY_M1_MSG *p_msg_out); /|* 输入结构: /|*
	 * char bk_inx[INDEX_LEN]; -- 分行BMK索引号,长度: 3字节 /|* char mak_len[MAK_LEN_LEN]; --
	 * MAC密钥长度(值为8/16/24) /|* char mak[MAK_LEN]; -- 计算MAC的密钥(十六进制字符串ASCII码) /|* char
	 * mac_element_len[MAC_ELEMENT_LEN_LEN]; -- Mac data长度 /|* char
	 * mac_element[MAX_MAC_ELEMENT_LEN]; -- 计算MAC的数据 /|* 输出结构: /|* char reply_code[REPLY_CODE_LEN];
	 * -- 返回码,长度为2,如"0b" /|* char mac[MAC_LEN]; -- 计算所得的MAC,8字节ASCII码 /|* 返回值 : 成功返回0, 否则返回1 /|*
	 * 功能描述: 按ANSI9.19方式计算MAC /|* Created : xu.guoyou [2005-07-13] /|* 说 明:
	 * 对于MAC密钥长度为双倍长的情况,只支持ANSI9.19方式计算MAC. //===================================
	 * =================================================
	 */

	/*
	 * public int Bank_GenMac1(HsmSession hSession, HsmStruct.Bank_GenMac.Bank_GenMacIN pmsg_in,
	 * HsmStruct.Bank_GenMac.Bank_GenMacOUT pmsg_out) {
	 * LogWriter.debug("HsmStruct.Bank_GenMac.Bank_GenMacIN:["+pmsg_in+"]");
	 * LogWriter.debug("HsmStruct.Bank_GenMac.Bank_GenMacOUT:["+pmsg_out+"]"); int iSndLen = 0,
	 * nResult = 0; byte[] retCode = new byte[1];
	 * 
	 * iSecBufferIn[0] = 0x04; iSecBufferIn[1] = 0x10; iSndLen += 2;
	 * 
	 * iSecBufferIn[iSndLen++] = 0x01;
	 * 
	 * String temp = new String(pmsg_in.bk_inx); LogWriter.debug("分行BMK索引号 = ["+temp+"]"); int i =
	 * Integer.parseInt(temp); if (i > 999) { retCode[0] = (byte) HsmConst.EKEY_INVALID_BMK_INDEX;
	 * Hex2Str(retCode, pmsg_out.reply_code, 1); return HsmConst.T_FAIL; }
	 * 
	 * iSecBufferIn[iSndLen++] = (byte) ((i >> 8) & 0xff); iSecBufferIn[iSndLen++] = (byte) (i &
	 * 0xff);
	 * 
	 * temp = new String(pmsg_in.mak_len); i = Integer.parseInt(temp); iSecBufferIn[iSndLen++] =
	 * (byte) i;
	 * 
	 * //iSecBufferIn[iSndLen++] = 0x02; if(i == 8){ LogWriter.debug("->0X01");
	 * iSecBufferIn[iSndLen++] = 0x01; //ANSI 9.9 }else{ LogWriter.debug("->0X02");
	 * iSecBufferIn[iSndLen++] = 0x02; } byte[] mak = new byte[i]; Str2Hex(pmsg_in.mak, mak, i * 2);
	 * System.arraycopy(mak, 0, iSecBufferIn, iSndLen, i); iSndLen += i;
	 * 
	 * for (int j = 0; j < 8; j++) { iSecBufferIn[iSndLen++] = 0;
	 * 
	 * } temp = new String(pmsg_in.mac_element_len); i = Integer.parseInt(temp);
	 * iSecBufferIn[iSndLen++] = (byte) ((i >> 8) & 0xff); iSecBufferIn[iSndLen++] = (byte) (i &
	 * 0xff);
	 * 
	 * System.arraycopy(pmsg_in.mac_element, 0, iSecBufferIn, iSndLen, i); iSndLen += i;
	 * 
	 * 
	 * nResult = hSession.SendData(iSecBufferIn, iSndLen); if (nResult != 0) { retCode[0] = (byte)
	 * nResult; Hex2Str(retCode, pmsg_out.reply_code, 1); return HsmConst.T_FAIL; }
	 * 
	 * nResult = hSession.RecvData(iSecBufferOut); if (nResult != 0) { retCode[0] = (byte) nResult;
	 * Hex2Str(retCode, pmsg_out.reply_code, 1); return HsmConst.T_FAIL; }
	 * 
	 * //LogWriter.debug("secbuf_in = ["+new String(iSecBufferIn)+"]"); nResult =
	 * hSession.SndAndRcvData(iSecBufferIn, iSndLen, iSecBufferOut);
	 * LogWriter.debug("hSession.SndAndRcvData = ["+nResult+"]"); if (nResult != 0) { retCode[0] =
	 * (byte) nResult; Hex2Str(retCode, pmsg_out.reply_code, 1); return HsmConst.T_FAIL; }
	 * 
	 * if (iSecBufferOut[0] == (byte) 'A') {
	 * 
	 * System.arraycopy(secbuf_out, 1, pmsg_out.mac, 0, HsmConst.MAC_LEN);
	 * 
	 * byte[] tempMac = new byte[HsmConst.MAC_LEN]; System.arraycopy(iSecBufferOut, 1, tempMac, 0,
	 * HsmConst.MAC_LEN);
	 * 
	 * Hex2Str(tempMac, pmsg_out.mac, 4); pmsg_out.reply_code[0] = 0x30; pmsg_out.reply_code[1] =
	 * 0x30; } else { retCode[0] = iSecBufferOut[1]; Hex2Str(retCode, pmsg_out.reply_code, 1);
	 * return HsmConst.T_FAIL; }
	 * 
	 * return HsmConst.T_SUCCESS; }
	 */

	public int Bank_GenMac(HsmSession hSession, HsmStruct.Bank_GenMac.Bank_GenMacIN pmsg_in,
			HsmStruct.Bank_GenMac.Bank_GenMacOUT pmsg_out) {
		int iSndLen = 0, nResult = 0;
		byte[] retCode = new byte[1];

		iSecBufferIn[0] = 0x04;
		iSecBufferIn[1] = 0x10;
		iSndLen += 2;

		iSecBufferIn[iSndLen++] = 0x01;

		String temp = new String(pmsg_in.bk_inx);
		int i = Integer.parseInt(temp);
		if (i > 999) {
			retCode[0] = (byte) HsmConst.EKEY_INVALID_BMK_INDEX;
			Hex2Str(retCode, pmsg_out.reply_code, 1);
			return HsmConst.T_FAIL;
		}

		iSecBufferIn[iSndLen++] = (byte) ((i >> 8) & 0xff);
		iSecBufferIn[iSndLen++] = (byte) (i & 0xff);

		temp = new String(pmsg_in.mak_len);
		i = Integer.parseInt(temp);
		iSecBufferIn[iSndLen++] = (byte) i;

		/*
		 * �޸� mac flag ��־�� ����9.9�㷨 secbuf_in[iSndLen++] = 0x02;
		 */
		if (i == 8)
			iSecBufferIn[iSndLen++] = 0x01; // ANSI 9.9
		else
			iSecBufferIn[iSndLen++] = 0x02; // ANSI 9.19

		byte[] mak = new byte[i];
		Str2Hex(pmsg_in.mak, mak, i * 2);
		System.arraycopy(mak, 0, iSecBufferIn, iSndLen, i);
		iSndLen += i;

		/* init vector */
		for (int j = 0; j < 8; j++) {
			iSecBufferIn[iSndLen++] = 0;
		}

		temp = new String(pmsg_in.mac_element_len);
		i = Integer.parseInt(temp);
		iSecBufferIn[iSndLen++] = (byte) ((i >> 8) & 0xff);
		iSecBufferIn[iSndLen++] = (byte) (i & 0xff);

		System.arraycopy(pmsg_in.mac_element, 0, iSecBufferIn, iSndLen, i);
		iSndLen += i;

		nResult = hSession.SndAndRcvData(iSecBufferIn, iSndLen, iSecBufferOut);
		if (nResult != 0) {
			retCode[0] = (byte) nResult;
			Hex2Str(retCode, pmsg_out.reply_code, 1);
			return HsmConst.T_FAIL;
		}
		if (iSecBufferOut[0] == (byte) 'A') {

			System.arraycopy(iSecBufferOut, 1, pmsg_out.mac, 0, HsmConst.MAC_LEN);
			byte[] tempMac = new byte[HsmConst.MAC_LEN];
			System.arraycopy(iSecBufferOut, 1, tempMac, 0, HsmConst.MAC_LEN);
			Hex2Str(tempMac, pmsg_out.mac, 8);
			pmsg_out.reply_code[0] = 0x30;
			pmsg_out.reply_code[1] = 0x30;
		} else {
			retCode[0] = iSecBufferOut[1];
			Hex2Str(retCode, pmsg_out.reply_code, 1);
			return HsmConst.T_FAIL;
		}

		return HsmConst.T_SUCCESS;
	}

	/*
	 * ========================================================================== ==========// /|*
	 * 函数原型: int Bank_VerifyMac(T_REQUEST_M2_MSG *p_msg_in, T_REPLY_M2_MSG *p_msg_out); /|* 输入结构:
	 * /|* char bk_inx[INDEX_LEN]; -- 分行BMK索引号,长度: 3字节 /|* char mak_len[MAK_LEN_LEN]; --
	 * MAC密钥长度(值为8/16/24) /|* char mak[MAK_LEN]; -- 计算MAC的密钥(ASCII码字符串) /|* char mac[MAC_LEN]; --
	 * 待校验的MAC(ASCII进制码) /|* char mac_element_len[MAC_ELEMENT_LEN_LEN]; -- Mac data长度 /|* char
	 * mac_element[MAX_MAC_ELEMENT_LEN]; -- 计算MAC的数据 /|* 输出结构: /|* char reply_code[REPLY_CODE_LEN];
	 * -- 返回码,长度为2 /|* 返回值 : 成功返回0, 否则返回1 /|* 功能描述: 按ANSI9.19方式计算并校验MAC /|* Created : xu.guoyou
	 * [2005-07-13] /|* 说 明: 对于MAC密钥长度为双倍长的情况,只支持ANSI9.19方式计算MAC.
	 * //================================= ===================================================
	 */

	public int Bank_VerifyMac(HsmSession hSession,
			HsmStruct.Bank_VerifyMac.Bank_VerifyMacIN pmsg_in,
			HsmStruct.Bank_VerifyMac.Bank_VerifyMacOUT pmsg_out) {
		int iSndLen = 0, nResult = 0;
		byte[] retCode = new byte[1];

		iSecBufferIn[0] = 0x04;
		iSecBufferIn[1] = 0x11;
		iSndLen += 2;

		iSecBufferIn[iSndLen++] = 0x01;

		String temp = new String(pmsg_in.bk_inx);
		int i = Integer.parseInt(temp);
		if (i > 999) {
			retCode[0] = (byte) HsmConst.EKEY_INVALID_BMK_INDEX;
			Hex2Str(retCode, pmsg_out.reply_code, 1);
			return HsmConst.T_FAIL;
		}

		iSecBufferIn[iSndLen++] = (byte) ((i >> 8) & 0xff);
		iSecBufferIn[iSndLen++] = (byte) (i & 0xff);

		/** MAK len **/
		temp = new String(pmsg_in.mak_len);
		i = Integer.parseInt(temp);
		iSecBufferIn[iSndLen++] = (byte) i;

		if (i == 8)
			iSecBufferIn[iSndLen++] = 0x01; // ANSI 9.9
		else
			iSecBufferIn[iSndLen++] = 0x02; // ANSI 9.19

		/** MAK **/
		byte[] mak = new byte[i];
		Str2Hex(pmsg_in.mak, mak, i * 2);
		System.arraycopy(mak, 0, iSecBufferIn, iSndLen, i);
		iSndLen += i;

		/** init vetor **/
		for (int j = 0; j < 8; j++) {
			iSecBufferIn[iSndLen++] = 0;
		}

		/** mac **/
		Str2Hex(pmsg_in.mac, mak, 8);
		System.arraycopy(mak, 0, iSecBufferIn, iSndLen, 4);
		iSndLen = iSndLen + 4;
		/** mac data len **/
		temp = new String(pmsg_in.mac_element_len);
		i = Integer.parseInt(temp);
		iSecBufferIn[iSndLen++] = (byte) ((i >> 8) & 0xff);
		iSecBufferIn[iSndLen++] = (byte) (i & 0xff);

		System.arraycopy(pmsg_in.mac_element, 0, iSecBufferIn, iSndLen, i);
		iSndLen += i;

		/*
		 * nResult = hSession.SendData(iSecBufferIn, iSndLen); if (nResult != 0) { retCode[0] =
		 * (byte) nResult; Hex2Str(retCode, pmsg_out.reply_code, 1); return HsmConst.T_FAIL; }
		 * 
		 * nResult = hSession.RecvData(iSecBufferOut); if (nResult != 0) { retCode[0] = (byte)
		 * nResult; Hex2Str(retCode, pmsg_out.reply_code, 1); return HsmConst.T_FAIL; }
		 */
		nResult = hSession.SndAndRcvData(iSecBufferIn, iSndLen, iSecBufferOut);
		if (nResult != 0) {
			retCode[0] = (byte) nResult;
			Hex2Str(retCode, pmsg_out.reply_code, 1);
			return HsmConst.T_FAIL;
		}

		if (iSecBufferOut[0] == (byte) 'A') {
			pmsg_out.reply_code[0] = 0x30;
			pmsg_out.reply_code[1] = 0x30;
		} else {
			retCode[0] = iSecBufferOut[1];
			Hex2Str(retCode, pmsg_out.reply_code, 1);
			return HsmConst.T_FAIL;
		}
		return HsmConst.T_SUCCESS;
	}

	/**
	 * ========================================================================== ==========// /|*
	 * 函数原型: int EncDec_DATA(T_REQUEST_M1_MSG *p_msg_in, T_REPLY_M1_MSG *p_msg_out); /|* 输入结构: /|*
	 * char index[INDEX_LEN]; -- 分行BMK索引号,长度: 3字节 /|* char key_len[MAK_LEN_LEN]; -- 密钥长度(值为8/16/24)
	 * /|* char work_key[MAK_LEN]; -- 密钥(十六进制字符串ASCII码) /|* char flag[FLAG_LEN]; -- 加解密标志（0：加密，1：解密）
	 * /|* char data_len[DATA_LEN]; -- 数据长度 /|* char data[MAX_DATA_LEN]; -- 数据 /|* 输出结构: /|* char
	 * reply_code[REPLY_CODE_LEN]; -- 返回码,长度为2,如"0b" /|* char data_len[DATA_LEN]; -- 返回数据长度，4byte
	 * /|* char data[MAX_DATA_LEN]; -- 计算所得的数据 /|* 返回值 : 成功返回0, 否则返回1 //===================
	 * =================================================================
	 **/

	public int EncDec_Data(HsmSession hSession, HsmStruct.EncDec_Data.EncDec_DataIN pmsg_in,
			HsmStruct.EncDec_Data.EncDec_DataOUT pmsg_out) {
		int iSndLen = 0, nResult = 0;
		byte[] retCode = new byte[1];

		iSecBufferIn[0] = 0x59;
		iSndLen += 1;

		/** BMK INDEX **/
		String temp = new String(pmsg_in.index);
		int i = Integer.parseInt(temp);
		if (i > 999) {
			retCode[0] = (byte) HsmConst.EKEY_INVALID_BMK_INDEX;
			Hex2Str(retCode, pmsg_out.reply_code, 1);
			return HsmConst.T_FAIL;
		}
		iSecBufferIn[iSndLen++] = (byte) ((i >> 8) & 0xff);
		iSecBufferIn[iSndLen++] = (byte) (i & 0xff);

		/** flag **/
		iSecBufferIn[iSndLen++] = (byte) (pmsg_in.flag[0] - '0');

		/** key **/
		byte tmpKey[] = new byte[16];
		Str2Hex(pmsg_in.work_key, tmpKey, 32);
		System.arraycopy(tmpKey, 0, iSecBufferIn, iSndLen, 16);
		iSndLen += 16;

		/** data len **/
		temp = new String(pmsg_in.data_len);
		i = Integer.parseInt(temp);
		iSecBufferIn[iSndLen++] = (byte) ((i >> 8) & 0xff);
		iSecBufferIn[iSndLen++] = (byte) (i & 0xff);

		/** data **/
		System.arraycopy(pmsg_in.data, 0, iSecBufferIn, iSndLen, i);
		iSndLen += i;

		/*
		 * nResult = hSession.SendData(iSecBufferIn, iSndLen); if (nResult != 0) { retCode[0] =
		 * (byte) nResult; Hex2Str(retCode, pmsg_out.reply_code, 1); return HsmConst.T_FAIL; }
		 * 
		 * nResult = hSession.RecvData(iSecBufferOut); if (nResult != 0) { retCode[0] = (byte)
		 * nResult; Hex2Str(retCode, pmsg_out.reply_code, 1); return HsmConst.T_FAIL; }
		 */
		nResult = hSession.SndAndRcvData(iSecBufferIn, iSndLen, iSecBufferOut);
		if (nResult != 0) {
			retCode[0] = (byte) nResult;
			Hex2Str(retCode, pmsg_out.reply_code, 1);
			return HsmConst.T_FAIL;
		}

		if (iSecBufferOut[0] == (byte) 'A') {
			pmsg_out.reply_code[0] = 0x30;
			pmsg_out.reply_code[1] = 0x30;
			i = ((iSecBufferOut[1]) << 8) | (iSecBufferOut[2] & 0xff);
			String tt = Integer.toString(i);
			System.arraycopy("0000".getBytes(), 0, pmsg_out.data_len, 0, HsmConst.DATA_LEN);
			System.arraycopy(tt.getBytes(), 0, pmsg_out.data_len, HsmConst.DATA_LEN - tt.length(),
					tt.length());
			System.arraycopy(iSecBufferOut, 3, pmsg_out.data, 0, i);
		} else {
			retCode[0] = iSecBufferOut[1];
			Hex2Str(retCode, pmsg_out.reply_code, 1);
			return HsmConst.T_FAIL;
		}
		return HsmConst.T_SUCCESS;
	}

	/**
	 * 取回一个索引的区域主密钥<0X31>
	 * @param hSession
	 * @param pmsg_in
	 * @param pmsg_out
	 * @return
	 */
	public int EncDec31_Data(HsmSession hSession, HsmStruct.EncDec31_Data.EncDec31_DataIN pmsg_in,
			HsmStruct.EncDec31_Data.EncDec31_DataOUT pmsg_out) {
		int iSndLen = 0, nResult = 0;
		byte[] retCode = new byte[1];

		iSecBufferIn[0] = 0x31;
		iSndLen += 1;

		/** ZMK INDEX **/
		String temp = new String(pmsg_in.index);
		int i = 0;
		try{
			i = Integer.parseInt(temp);
		}catch(Exception e) {
			retCode[0] = (byte) HsmConst.EKEY_INVALID_BMK_INDEX;
			Hex2Str(retCode, pmsg_out.reply_code, 1);
			return HsmConst.T_FAIL;
		}
		//将二进制转为十六进制
		iSecBufferIn[iSndLen++] = (byte) ((i >> 8) & 0xff);
		iSecBufferIn[iSndLen++] = (byte) (i & 0xff);

		logger.info("iSecBufferIn:" + HsmApp.byte2hex(iSecBufferIn));
		
		nResult = hSession.SndAndRcvData(iSecBufferIn, iSndLen, iSecBufferOut);
		
		logger.info("iSecBufferOut:" + HsmApp.byte2hex(iSecBufferOut));
		
		if (nResult != 0) {
			retCode[0] = (byte) nResult;
			Hex2Str(retCode, pmsg_out.reply_code, 1);
			return HsmConst.T_FAIL;
		}

		if (iSecBufferOut[0] == (byte) 'A') {
			//设置输出响应码，将成功的'A'转义为字符串"00"
			pmsg_out.reply_code[0] = 0x30;
			pmsg_out.reply_code[1] = 0x30;			
			
			//设置加密后的区域主密钥
			System.arraycopy(iSecBufferOut, 1, pmsg_out.pk_encrypted_bylmk,0,16);
			//设置校验值
			System.arraycopy(iSecBufferOut, 1+16, pmsg_out.check_value,0,8);
		} else {
			retCode[0] = iSecBufferOut[1];
			Hex2Str(retCode, pmsg_out.reply_code, 1);
			return HsmConst.T_FAIL;
		}
		return HsmConst.T_SUCCESS;
	}

	/**
	 * 将LMK加密的密钥转换为BMK加密<0X0552>
	 * @param hSession
	 * @param pmsg_in
	 * @param pmsg_out
	 * @return
	 */
	public int EncDec0552_Data(HsmSession hSession, HsmStruct.EncDec0552_Data.EncDec0552_DataIN pmsg_in,
			HsmStruct.EncDec0552_Data.EncDec0552_DataOUT pmsg_out) {
		int iSndLen = 0, nResult = 0;
		byte[] retCode = new byte[1];
		//命令类型0x05 + 命令0x52
		iSecBufferIn[0] = 0x05;
		iSecBufferIn[1] = 0x52;		
		//数据长度标志（预留）
		iSecBufferIn[2] = 0x00;
		iSndLen += 3;

		//区域主密钥索引号
		String temp = new String(pmsg_in.index);
		int i = 0;
		try{
			i = Integer.parseInt(temp);
		}catch(Exception e) {
			retCode[0] = (byte) HsmConst.EKEY_INVALID_BMK_INDEX;
			Hex2Str(retCode, pmsg_out.reply_code, 1);
			return HsmConst.T_FAIL;
		}
		/**将二进制转为十六进制*/
		iSecBufferIn[iSndLen++] = (byte) ((i >> 8) & 0xff);
		iSecBufferIn[iSndLen++] = (byte) (i & 0xff);
		
		//数据密钥
		System.arraycopy(pmsg_in.pk_encrypted_bylmk, 0, iSecBufferIn, iSndLen, pmsg_in.pk_encrypted_bylmk.length);
		iSndLen += pmsg_in.pk_encrypted_bylmk.length;

		logger.info("iSecBufferIn:" + HsmApp.byte2hex(iSecBufferIn));
		
		nResult = hSession.SndAndRcvData(iSecBufferIn, iSndLen, iSecBufferOut);
		
		logger.info("iSecBufferOut:" + HsmApp.byte2hex(iSecBufferOut));
		
		if (nResult != 0) {
			retCode[0] = (byte) nResult;
			Hex2Str(retCode, pmsg_out.reply_code, 1);
			return HsmConst.T_FAIL;
		}

		if (iSecBufferOut[0] == (byte) 'A') {
			//设置输出响应码，将成功的'A'转义为字符串"00"
			pmsg_out.reply_code[0] = 0x30;
			pmsg_out.reply_code[1] = 0x30;			
			
			//设置加密后的区域主密钥
			System.arraycopy(iSecBufferOut, 1, pmsg_out.pk_encrypted_byzmk,0,16);
		} else {
			retCode[0] = iSecBufferOut[1];
			Hex2Str(retCode, pmsg_out.reply_code, 1);
			return HsmConst.T_FAIL;
		}
		return HsmConst.T_SUCCESS;
	}
	
	/**
	 * 用输入密钥对数据加/解密<0X71>
	 * @param hSession
	 * @param pmsg_in
	 * @param pmsg_out
	 * @return
	 */
	public int EncDec71_Data(HsmSession hSession, HsmStruct.EncDec71_Data.EncDec71_DataIN pmsg_in,
			HsmStruct.EncDec71_Data.EncDec71_DataOUT pmsg_out) {
		int iSndLen = 0, nResult = 0;
		byte[] retCode = new byte[1];

		iSecBufferIn[0] = 0x71;
		iSndLen += 1;

		/** BMK INDEX **/
		String temp = new String(pmsg_in.index);
		int i = Integer.parseInt(temp);
		if (i > 999) {
			retCode[0] = (byte) HsmConst.EKEY_INVALID_BMK_INDEX;
			Hex2Str(retCode, pmsg_out.reply_code, 1);
			return HsmConst.T_FAIL;
		}
		iSecBufferIn[iSndLen++] = (byte) ((i >> 8) & 0xff);
		iSecBufferIn[iSndLen++] = (byte) (i & 0xff);

		/** key **/
//		byte tmpKey[] = new byte[16];
//		Str2Hex(pmsg_in.work_key, tmpKey, 32);
		System.arraycopy(pmsg_in.work_key, 0, iSecBufferIn, iSndLen, 16);
		iSndLen += 16;

		/** initial_vecotr **/
		byte tmpInitialVecotr[] = new byte[8];
		Str2Hex(pmsg_in.initial_vecotr, tmpInitialVecotr, 16);
		System.arraycopy(tmpInitialVecotr, 0, iSecBufferIn, iSndLen, 8);
		iSndLen += 8;
		
		/** encdec_flag **/
		iSecBufferIn[iSndLen++] = (byte) (pmsg_in.encdec_flag[0] - '0');
		
		/** algorithm_flag **/
		iSecBufferIn[iSndLen++] = (byte) (pmsg_in.algorithm_flag[0] - '0');
		
		/** data len **/
		temp = new String(pmsg_in.data_len);
		i = Integer.parseInt(temp.trim());
		iSecBufferIn[iSndLen++] = (byte) ((i >> 8) & 0xff);
		iSecBufferIn[iSndLen++] = (byte) (i & 0xff);

		/** data **/
		System.arraycopy(pmsg_in.data, 0, iSecBufferIn, iSndLen, i);
		iSndLen += i;

		logger.info("iSecBufferIn:" + HsmApp.byte2hex(iSecBufferIn));
		
		nResult = hSession.SndAndRcvData(iSecBufferIn, iSndLen, iSecBufferOut);
		
		logger.info("iSecBufferOut:" + HsmApp.byte2hex(iSecBufferOut));
		
		if (nResult != 0) {
			retCode[0] = (byte) nResult;
			Hex2Str(retCode, pmsg_out.reply_code, 1);
			return HsmConst.T_FAIL;
		}

		if (iSecBufferOut[0] == (byte) 'A') {
			//设置输出响应码，将成功的'A'转义为字符串"00"
			pmsg_out.reply_code[0] = 0x30;
			pmsg_out.reply_code[1] = 0x30;			
			
			//设置输出DATA的长度，转义为字符串
			i = ((iSecBufferOut[1]) << 8) | (iSecBufferOut[2] & 0xff);
			String tt = Integer.toString(i);
			System.arraycopy("00".getBytes(), 0, pmsg_out.data_len, 0, 2);
			System.arraycopy(tt.getBytes(), 0, pmsg_out.data_len,2 - tt.length(),tt.length());
			
			//分配输出DATA的所占字节数组，并进行设置
			pmsg_out.data = new byte[i];
			System.arraycopy(iSecBufferOut, 3, pmsg_out.data, 0, i);
		} else {
			retCode[0] = iSecBufferOut[1];
			Hex2Str(retCode, pmsg_out.reply_code, 1);
			return HsmConst.T_FAIL;
		}
		return HsmConst.T_SUCCESS;
	}
	
	/*
	 * ========================================================================== =========== 功能描述:
	 * 非对称转加密传统及互联网支付PinBlock 输入结构： 长度 类型 备注 命令类型 1 H 0xE0 命令 1 H 0x05 索引号 2 H
	 * 若使用内部保存的RSA密钥对索引号时可选择0—49；若使用用户公钥则index请填 ffff 私钥长度 2 H 当用户使用内部保存的索引号（index !=
	 * ffff）时prilen请填0 数据长度 2 H 填充方式 2 H 1：PKCS1填充方式 3：非填充方式 算法标志 1 H 0：REF格式的私钥明文 1：DER格式的私钥明文
	 * 2：私钥密文 密钥访问口令 8 H 当用户不使用内部保存的密钥对（index = ffff）时，该项为NULL 私钥数据 N H 当用户使用内部保存的密钥对（index !=
	 * ffff）时，该项为NULL PinBlock密文数据 N H 算法标志IN 1 H 1--不带主帐号，2--带主帐号（互联网支付PinBlock请填1） 主帐号IN 8 H
	 * 0000+主帐号最右12位（最后一位除外,当flagin = 1时不存在）
	 * 
	 * 银行密钥索引 2 H 000~999 PIK长度 1 H 0、8、16、24 PIK N H(piklen = 0 时不存在) 算法标志Out 1 H
	 * 01--不带主帐号，02--带主帐号（互联网支付PinBlock请填1） 主帐号Out 8 H 0000+主帐号最右12位（最后一位除外,当flagout = 1时不存在）
	 * 
	 * 输出结构：： 长度 类型 备注 应答码 1 A ‘A’ 数据长度 2 H 加密后的PinBlock长度，8或24 数据 N H; N = 8 or 24 或 应答码 1 A ‘E’
	 * 错误码 2 H ==================================================
	 * ====================================
	 */

	public int UnsymConvertPin(HsmSession hSession,
			HsmStruct.UnsymConvertPin.UnsymConvertPinIn hsmIn,
			HsmStruct.UnsymConvertPin.UnsymConvertPinOut hsmOut) {
		int iSndLen = 0, nResult = 0;
		byte[] retCode = new byte[1];
		int privateKeyLen = 0;
		boolean accessFlag = true;

		/** command code **/
		iSecBufferIn[0] = (byte) 0xe0;
		iSecBufferIn[1] = 0x13;
		iSndLen += 2;

		String temp;
		int i;

		if ((hsmIn.rsaIndex[0] == 0x66 && hsmIn.rsaIndex[1] == 0x66 && hsmIn.rsaIndex[2] == 0x66)
				|| (hsmIn.rsaIndex[0] == 0x46 && hsmIn.rsaIndex[1] == 0x46 && hsmIn.rsaIndex[2] == 0x46)) {
			/** RSA index **/
			iSecBufferIn[iSndLen++] = (byte) 0xff;
			iSecBufferIn[iSndLen++] = (byte) 0xff;

			/** private key length **/
			temp = new String(hsmIn.privateKeyLen);
			privateKeyLen = Integer.parseInt(temp);

			iSecBufferIn[iSndLen++] = (byte) ((privateKeyLen >> 8) & 0xff);
			iSecBufferIn[iSndLen++] = (byte) (privateKeyLen & 0xff);
			accessFlag = false;
		}

		else {
			/** RSA index **/
			temp = new String(hsmIn.rsaIndex);
			i = Integer.parseInt(temp);

			iSecBufferIn[iSndLen++] = (byte) ((i >> 8) & 0xff);
			iSecBufferIn[iSndLen++] = (byte) (i & 0xff);

			/** private key length **/
			iSecBufferIn[iSndLen++] = (byte) 0x00;
			iSecBufferIn[iSndLen++] = (byte) 0x00;

		}

		/** data length **/
		temp = new String(hsmIn.dataLen);
		int dataLen = Integer.parseInt(temp);

		iSecBufferIn[iSndLen++] = (byte) ((dataLen >> 8) & 0xff);
		iSecBufferIn[iSndLen++] = (byte) (dataLen & 0xff);

		/** padding mode **/
		iSecBufferIn[iSndLen++] = (byte) (hsmIn.padFlag[0] - 0x30);
		iSecBufferIn[iSndLen++] = (byte) (hsmIn.padFlag[1] - 0x30);

		/** decrypt mode **/
		iSecBufferIn[iSndLen++] = (byte) (hsmIn.athFlag[0] - 0x30);

		/** access password **/
		if (accessFlag == true) {
			/** RSA key access password **/
			if (hsmIn.passwd.length < 8) {
				retCode[0] = (byte) HsmConst.ERR_PASSWD;
				Hex2Str(retCode, hsmOut.reply_code, 1);
				return HsmConst.T_FAIL;
			}

			System.arraycopy(hsmIn.passwd, 0, iSecBufferIn, iSndLen, HsmConst.PWD_LEN);
			iSndLen += HsmConst.PWD_LEN;
		}

		/** private key **/
		System.arraycopy(hsmIn.privateKey, 0, iSecBufferIn, iSndLen, privateKeyLen);
		iSndLen += privateKeyLen;

		/** data **/
		System.arraycopy(hsmIn.decData, 0, iSecBufferIn, iSndLen, dataLen);
		iSndLen += dataLen;

		/** flagIn **/
		iSecBufferIn[iSndLen++] = (byte) (hsmIn.flagIn[1] - '0');

		/** acount in **/
		if (hsmIn.flagIn[1] == '2') {
			byte[] acco1 = new byte[12];
			byte[] hexacco1 = new byte[6];
			int j;
			for (j = 0; j < HsmConst.PAN_LEN; j++)
				if (hsmIn.accoIn[j] < '0' || hsmIn.accoIn[j] > '9')
					break;
			System.arraycopy(hsmIn.accoIn, j - 13, acco1, 0, 12);
			Str2Hex(acco1, hexacco1, 12);
			iSecBufferIn[iSndLen++] = (byte) 0x00;
			iSecBufferIn[iSndLen++] = (byte) 0x00;
			System.arraycopy(hexacco1, 0, iSecBufferIn, iSndLen, 6);
			iSndLen += 6;
		}

		/** BMK index **/
		temp = new String(hsmIn.bmkIndex);
		i = Integer.parseInt(temp);
		iSecBufferIn[iSndLen++] = (byte) ((i >> 8) & 0xff);
		iSecBufferIn[iSndLen++] = (byte) (i & 0xff);

		/** pik len **/
		temp = new String(hsmIn.pikLen);
		i = Integer.parseInt(temp);
		iSecBufferIn[iSndLen++] = (byte) (i & 0xff);

		/** PIK **/
		if (i > 0) {
			byte[] pik = new byte[24];
			Str2Hex(hsmIn.pik, pik, i * 2);
			System.arraycopy(pik, 0, iSecBufferIn, iSndLen, i);
			iSndLen += i;
		}

		/** flag Out **/
		iSecBufferIn[iSndLen++] = (byte) (hsmIn.flagOut[1] - '0');

		/** acount out **/
		if (hsmIn.flagOut[1] == '2') {
			byte[] acco2 = new byte[12];
			byte[] hexacco2 = new byte[6];
			int j;
			for (j = 0; j < HsmConst.PAN_LEN; j++)
				if (hsmIn.accoOut[j] < '0' || hsmIn.accoOut[j] > '9')
					break;
			System.arraycopy(hsmIn.accoOut, j - 13, acco2, 0, 12);
			Str2Hex(acco2, hexacco2, 12);
			iSecBufferIn[iSndLen++] = (byte) 0x00;
			iSecBufferIn[iSndLen++] = (byte) 0x00;
			System.arraycopy(hexacco2, 0, iSecBufferIn, iSndLen, 6);
			iSndLen += 6;
		}

		/*
		 * // send message nResult = hSession.SendData(iSecBufferIn, iSndLen); if (nResult != 0) {
		 * retCode[0] = (byte) nResult; Hex2Str(retCode, hsmOut.reply_code, 1); return
		 * HsmConst.T_FAIL; }
		 * 
		 * // receive message nResult = hSession.RecvData(iSecBufferOut); if (nResult != 0) {
		 * retCode[0] = (byte) nResult; Hex2Str(retCode, hsmOut.reply_code, 1); return
		 * HsmConst.T_FAIL; }
		 */
		nResult = hSession.SndAndRcvData(iSecBufferIn, iSndLen, iSecBufferOut);
		if (nResult != 0) {
			retCode[0] = (byte) nResult;
			Hex2Str(retCode, hsmOut.reply_code, 1);
			return HsmConst.T_FAIL;
		}

		hsmOut.reply_code[0] = 0x30;
		hsmOut.reply_code[1] = 0x30;

		/** received data length **/
		// byte[] tempChange = new byte[4];

		i = ((iSecBufferOut[1] << 8) & 0xff00) | (iSecBufferOut[2] & 0xff);
		String tt = Integer.toString(i);
		System.arraycopy("0000".getBytes(), 0, hsmOut.pinLen, 0, HsmConst.DATA_LEN);
		System.arraycopy(tt.getBytes(), 0, hsmOut.pinLen, HsmConst.DATA_LEN - tt.length(),
				tt.length());
		/** pinblock chiper data **/
		byte[] hexPin = new byte[24];
		System.arraycopy(iSecBufferOut, 3, hexPin, 0, i);
		Hex2Str(hexPin, hsmOut.pinblock, i);

		return HsmConst.T_SUCCESS;
	}
	
	
	/**
	 *用ZEK加密数据 E0
	 * 江南科友加密机E0指令
	 * add by lfr
	 * @param hSession
	 * @param pmsg_in
	 * @param pmsg_out
	 * @return
	 */
	public int EncDec_JNKY_E0_Data(HsmSession hSession, HsmStruct.EncDec_JNKY_E0_Data.EncDec_JNKY_E0_DataIN pmsg_in,
			HsmStruct.EncDec_JNKY_E0_Data.EncDec_JNKY_E0_DataOUT pmsg_out) {
		int iSndLen = 0, nResult = 0, iResultLen = 0 ;
		byte[] retCode = new byte[1];
		byte[] buf = new byte[SECBUF_MAX_SIZE];
		/* 消息头 */
		System.arraycopy(pmsg_in.message_header, 0, buf, iSndLen, HsmConst.E0_HEADER_LEN);
		iSndLen += HsmConst.E0_HEADER_LEN;
		/* 命令代码 */
		System.arraycopy(pmsg_in.command_code, 0, buf, iSndLen, HsmConst.E0_COMMAND_CODE_LEN);
		iSndLen += HsmConst.E0_COMMAND_CODE_LEN;
		/* 消息块号 */
		System.arraycopy(pmsg_in.message_block_no, 0, buf, iSndLen, HsmConst.E0_MESSAGE_BLOCK_NO_LEN);
		iSndLen += HsmConst.E0_MESSAGE_BLOCK_NO_LEN;
		/* 密钥模式 */
		System.arraycopy(pmsg_in.key_model, 0, buf, iSndLen, HsmConst.E0_KEY_MODEL_LEN);
		iSndLen += HsmConst.E0_KEY_MODEL_LEN;
		/* 密钥加密模式*/
		System.arraycopy(pmsg_in.key_encryption_mode, 0, buf, iSndLen, HsmConst.E0_KEY_ENCRYPTION_MODE_LEN);
		iSndLen += HsmConst.E0_KEY_ENCRYPTION_MODE_LEN;
		/* 密钥类型 */
		System.arraycopy(pmsg_in.key_type, 0, buf, iSndLen, HsmConst.E0_KEY_TYPE_LEN);
		iSndLen += HsmConst.E0_KEY_TYPE_LEN;
		/* 密钥 */
		System.arraycopy(pmsg_in.key, 0, buf, iSndLen, HsmConst.E0_KEY_LEN);
		iSndLen += HsmConst.E0_KEY_LEN;
		/* 输入消息类型 */
		System.arraycopy(pmsg_in.input_message_type, 0, buf, iSndLen, HsmConst.E0_INPUT_MESSAGE_TYPE_LEN);
		iSndLen += HsmConst.E0_INPUT_MESSAGE_TYPE_LEN;
		/* 输出消息类型*/
		System.arraycopy(pmsg_in.output_message_type, 0, buf, iSndLen, HsmConst.E0_OUTPUT_MESSAGE_TYPE_LEN);
		iSndLen += HsmConst.E0_OUTPUT_MESSAGE_TYPE_LEN;
		/* 填充模式 */
		System.arraycopy(pmsg_in.fill_pattern, 0, buf, iSndLen, HsmConst.E0_FILL_PATTERN_LEN);
		iSndLen += HsmConst.E0_FILL_PATTERN_LEN;
		/* 填充字符 */
		System.arraycopy(pmsg_in.fill_character, 0, buf, iSndLen, HsmConst.E0_FILL_CHARACTER_LEN);
		iSndLen += HsmConst.E0_FILL_CHARACTER_LEN;
		/* 填充类型 */
		System.arraycopy(pmsg_in.fill_type, 0, buf, iSndLen, HsmConst.E0_FILL_TYPE_LEN);
		iSndLen += HsmConst.E0_FILL_TYPE_LEN;
		/* 消息数据长度 */
		System.arraycopy(pmsg_in.data_length, 0, buf, iSndLen, HsmConst.E0_DATA_LENGTH_LEN);
		iSndLen += HsmConst.E0_DATA_LENGTH_LEN;
		int data_len = pmsg_in.data.length;
		/* 参与加解密的数据 */
		System.arraycopy(pmsg_in.data, 0, buf, iSndLen, data_len);
		iSndLen += data_len;
		/* 报文头（指令长度十六进制） */
		iSecBufferIn[0] = (byte) ((iSndLen/256 >> 8) & 0xff);
		iSecBufferIn[1] = (byte) ((iSndLen%256) & 0xff);

		System.arraycopy( buf, 0, iSecBufferIn, 2, iSndLen);
		iSndLen += 2;
		
		logger.info(new String(iSecBufferIn));
		
		nResult = hSession.SndAndRcvDataJNKY(iSecBufferIn, iSndLen, iSecBufferOut);
		hSession.HsmSessionEnd(); 
		logger.info("nResult:"+nResult);
		
		logger.info("iSecBufferOut:" + new String(iSecBufferOut));
		
		if (nResult != 0) {
			retCode[0] = (byte) nResult;
			Hex2Str(retCode, pmsg_out.reply_code, 1);
			return HsmConst.T_FAIL;
		}
		iResultLen += 2;
		/* 比较消息头 */
		System.arraycopy(iSecBufferOut, iResultLen, pmsg_out.message_header, 0, HsmConst.E0_HEADER_LEN);
		iResultLen += HsmConst.E0_HEADER_LEN;
		if(pmsg_in.message_header == pmsg_out.message_header){
			//消息头不一致
			System.arraycopy(HsmConst.E0_MESSAGE_HEADER_ERROR.getBytes(), 0, pmsg_out.reply_code, 0, 2);	
			return HsmConst.T_FAIL;
		}

		/* 响应代码 */
		System.arraycopy(iSecBufferOut, iResultLen, pmsg_out.response_code, 0, HsmConst.E0_RESPONSE_CODE_LEN);
		iResultLen += HsmConst.E0_RESPONSE_CODE_LEN;
		if("E1".getBytes()==(pmsg_out.response_code)){
			//响应代码不正确
			System.arraycopy(HsmConst.E0_RESPONSE_CODE_ERROR.getBytes(), 0, pmsg_out.reply_code, 0, 2);	
			return HsmConst.T_FAIL;
		}

		
		/* 返回码 */
		System.arraycopy(iSecBufferOut, iResultLen, pmsg_out.reply_code, 0, HsmConst.E0_REPLY_CODE_LEN);
		iResultLen += HsmConst.E0_REPLY_CODE_LEN;
		if(!HsmConst.E0_SUCC.equals(new String(pmsg_out.reply_code))){
			//响应代码不正确
			System.arraycopy(pmsg_out.reply_code, 0, pmsg_out.reply_code, 0, 2);	
			return HsmConst.T_FAIL;
		}

		
		/* 输出消息类型*/
		System.arraycopy(iSecBufferOut, iResultLen, pmsg_out.output_message_type, 0, HsmConst.E0_OUTPUT_MESSAGE_TYPE_LEN);
		iResultLen += HsmConst.E0_OUTPUT_MESSAGE_TYPE_LEN;

		
		/* 消息数据长度 */
		System.arraycopy(iSecBufferOut, iResultLen, pmsg_out.data_length, 0, HsmConst.E0_DATA_LENGTH_LEN);
		iResultLen += HsmConst.E0_DATA_LENGTH_LEN;
		byte[] byteDataLen = pmsg_out.data_length;

		
		/* 加解密后的数据 */
		byte[] data = new byte[HsmConst.E0_OUTPUT_DATE_LEN];
		System.arraycopy(iSecBufferOut, iResultLen, data, 0, HsmConst.E0_OUTPUT_DATE_LEN);
		
		
		pmsg_out.data = data;
		iResultLen += HsmConst.E0_DATA_LENGTH_LEN;
		
		return HsmConst.T_SUCCESS;
	}
	

	
}
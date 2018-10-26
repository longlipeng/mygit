package service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import basic.GetFileMD5;
import basic.InitCorpMsg;
import sun.misc.BASE64Encoder;

public class ReadFile {
	/**
	 * 从文件中读取文本
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public void readFromFile(String uniqueNo) throws Exception {
		CSBUtil st = new CSBUtil();
		// 字节输入流
		int length = 1024 * 1024 * 2;
		HashMap map = (HashMap) new InitCorpMsg().initCopInfo(uniqueNo).get(0);
		// 字节数组
		byte[] buffer = new byte[length];
		// TODO 测试文件路径，需根据实际情况修改
		String path = "E:/test.txt";
		File file = new File(path);
		// 创建文件
		InputStream ins = new BufferedInputStream(new FileInputStream(path));
		// 文件大小
		long fileLength = file.length();
		System.err.println("fileLength==" + fileLength);
		// 文件偏移量
		int offSet = 0;
		String gm = new GetFileMD5().getFileMD5(path);
		try {
			int bytesRead = 0;
			String endFileFlag = "";
			String flwno = "";
			// 第一次 读取文件
			bytesRead = ins.read(buffer);
			if (bytesRead < length) {
				endFileFlag = "Y";
			} else {
				// 设置结束标志
				endFileFlag = "N";
			}
			// 除去空格， BASE64转码
			BASE64Encoder encoder = new BASE64Encoder();
			byte[] destbuff = new byte[bytesRead]; // 读取文件的byte字节流
			System.arraycopy(buffer, 0, destbuff, 0, bytesRead);
			String aesBase64 = encoder.encode(destbuff).replaceAll("[\r\n]", "");

			// 拼接接送字符串
			String sendCardsJsonData = "{\"dataMap\":{\"fileMD5\":\"" + gm + "\",\"uniqueNo\":\"" + uniqueNo + "\","
					+ "\"fileSize\":\"" + fileLength + "\"," + "\"fileName\":\"link\"," + "\"fileOffset\":\"" + offSet
					+ "\"," + "\"uploadEnd\":\"" + endFileFlag + "\"," + "\"fileContent\":\"" + aesBase64 + "\"}}";
			// 调用csb方法
			flwno = st.sendCSB((String) map.get("ak"), (String) map.get("sk"), (String) map.get("url"),
					(String) map.get("privateKey"), (String) sendCardsJsonData, (String) map.get("uniqueNo"),
					"fileUploadDataService");
			// boolean sendEndFlag = false;
			// 从文件中按字节读取内容，到文件尾部时read方法将返回-1
			while ((bytesRead = ins.read(buffer)) != -1) {
				// 如果长度小于2M
				offSet = offSet + bytesRead;
				if (bytesRead < length) {
					// 设置结束标志
					endFileFlag = "Y";
					// sendEndFlag = true;
				} else {
					endFileFlag = "N";
				}
				// 除去出空格
				encoder = new BASE64Encoder();
				destbuff = new byte[bytesRead];
				System.arraycopy(buffer, 0, destbuff, 0, bytesRead);
				aesBase64 = encoder.encode(destbuff).replaceAll("[\r\n]", "");
				// 拼接json字符串
				sendCardsJsonData = "{\"dataMap\":{\"uniqueNo\":\"" + uniqueNo + "\"," + "\"uploadFlowNo\":\"" + flwno
						+ "\"," + "\"fileOffset\":\"" + offSet + "\"," + "\"uploadEnd\":\"" + endFileFlag + "\","
						+ "\"fileContent\":\"" + aesBase64 + "\"}}";
				// 调用连接csb的方法
				st.sendCSB((String) map.get("ak"), (String) map.get("sk"), (String) map.get("url"),
						(String) map.get("privateKey"), (String) sendCardsJsonData, (String) map.get("uniqueNo"),
						"fileUploadDataService");
			}
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			// 关闭 InputStream
			try {
				if (ins != null)
					ins.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}
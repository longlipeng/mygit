package service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import basic.GetFileMD5;
import basic.InitCorpMsg;
import sun.misc.BASE64Encoder;

public class ReadFile {
	private static Logger loggger=LoggerFactory.getLogger(ReadFile.class);
	/**
	 * 从文件中读取文本
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public String readFromFile(String uniqueNo,String path) throws Exception {
		CSBUtil st = new CSBUtil();
		// 字节输入流
		int length = 1024 * 1024 * 2;
		HashMap map = (HashMap) new InitCorpMsg().initCopInfo(uniqueNo).get(0);//初始化企业的信息map
		// 字节数组
		byte[] buffer = new byte[length];
		File file = new File(path);
		// 创建文件
		InputStream ins = new BufferedInputStream(new FileInputStream(path));
		// 文件大小
		long fileLength = file.length();//文件长度   
		//System.err.println("fileLength==" + fileLength);
		// 文件偏移量    就是从那里开始发的  文件的位置
		int offSet = 0;
		String gm = new GetFileMD5().getFileMD5(path);//MD5文件   根据文件内容判断是否是同一个文件
		String flwno = "";
		try {
			int bytesRead = 0;
			String endFileFlag = "";			
			// 第一次 读取文件
			bytesRead = ins.read(buffer);//返回实际读取的字节数
			if(bytesRead==-1){
				bytesRead=0;
			}
			if (bytesRead < length) {//如果实际读取的字节数 小于2M
				endFileFlag = "Y"; //设置为结束标志 为Y
			} else {
				// 设置结束标志
				endFileFlag = "N"; //设置为结束标志 为 false
			}
			// 除去空格， BASE64转码
			BASE64Encoder encoder = new BASE64Encoder();
			byte[] destbuff = new byte[bytesRead]; // 读取文件的byte字节流
			System.arraycopy(buffer, 0, destbuff, 0, bytesRead);
			String lineSeparator = System.getProperty("line.separator");
			String aesBase64 = encoder.encode(destbuff).replaceAll("[\r\n]", ""); //FIXME 下次上传测试 包含中文 先去除空格再上报？
			SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMMdd");
			String fileDate=dateFormat.format(new Date());
			// 拼接接送字符串
			String sendCardsJsonData = "{\"dataMap\":{\"fileMD5\":\"" + gm+ "\",\"uniqueNo\":\"" + uniqueNo + "\","
					+ "\"fileSize\":\"" + fileLength + "\"," + "\"fileName\":\"link\"," + "\"fileOffset\":\"" + offSet
					+ "\"," + "\"uploadEnd\":\"" + endFileFlag + "\"," + "\"fileDate\":\"" + fileDate + "\","+ "\"fileContent\":\"" + aesBase64 + "\"}}";
			// 调用csb方法
			flwno = st.sendCSB((String) map.get("ak"), (String) map.get("sk"), (String) map.get("url"),
					(String) map.get("privateKey"), (String) sendCardsJsonData, (String) map.get("uniqueNo"),
					"fileUploadDataService");
			// boolean sendEndFlag = false;
			// 从文件中按字节读取内容，到文件尾部时read方法将返回-1
			while ((bytesRead = ins.read(buffer)) != -1) {//如果大小超过2M  则继续读取传送
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
			loggger.error("没有找到文件>>>>>"+path);
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
		return flwno;
	}
}
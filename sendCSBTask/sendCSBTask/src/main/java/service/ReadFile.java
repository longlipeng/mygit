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
	 * ���ļ��ж�ȡ�ı�
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public String readFromFile(String uniqueNo,String path) throws Exception {
		CSBUtil st = new CSBUtil();
		// �ֽ�������
		int length = 1024 * 1024 * 2;
		HashMap map = (HashMap) new InitCorpMsg().initCopInfo(uniqueNo).get(0);//��ʼ����ҵ����Ϣmap
		// �ֽ�����
		byte[] buffer = new byte[length];
		File file = new File(path);
		// �����ļ�
		InputStream ins = new BufferedInputStream(new FileInputStream(path));
		// �ļ���С
		long fileLength = file.length();//�ļ�����   
		//System.err.println("fileLength==" + fileLength);
		// �ļ�ƫ����    ���Ǵ����￪ʼ����  �ļ���λ��
		int offSet = 0;
		String gm = new GetFileMD5().getFileMD5(path);//MD5�ļ�   �����ļ������ж��Ƿ���ͬһ���ļ�
		String flwno = "";
		try {
			int bytesRead = 0;
			String endFileFlag = "";			
			// ��һ�� ��ȡ�ļ�
			bytesRead = ins.read(buffer);//����ʵ�ʶ�ȡ���ֽ���
			if(bytesRead==-1){
				bytesRead=0;
			}
			if (bytesRead < length) {//���ʵ�ʶ�ȡ���ֽ��� С��2M
				endFileFlag = "Y"; //����Ϊ������־ ΪY
			} else {
				// ���ý�����־
				endFileFlag = "N"; //����Ϊ������־ Ϊ false
			}
			// ��ȥ�ո� BASE64ת��
			BASE64Encoder encoder = new BASE64Encoder();
			byte[] destbuff = new byte[bytesRead]; // ��ȡ�ļ���byte�ֽ���
			System.arraycopy(buffer, 0, destbuff, 0, bytesRead);
			String lineSeparator = System.getProperty("line.separator");
			String aesBase64 = encoder.encode(destbuff).replaceAll("[\r\n]", ""); //FIXME �´��ϴ����� �������� ��ȥ���ո����ϱ���
			SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMMdd");
			String fileDate=dateFormat.format(new Date());
			// ƴ�ӽ����ַ���
			String sendCardsJsonData = "{\"dataMap\":{\"fileMD5\":\"" + gm+ "\",\"uniqueNo\":\"" + uniqueNo + "\","
					+ "\"fileSize\":\"" + fileLength + "\"," + "\"fileName\":\"link\"," + "\"fileOffset\":\"" + offSet
					+ "\"," + "\"uploadEnd\":\"" + endFileFlag + "\"," + "\"fileDate\":\"" + fileDate + "\","+ "\"fileContent\":\"" + aesBase64 + "\"}}";
			// ����csb����
			flwno = st.sendCSB((String) map.get("ak"), (String) map.get("sk"), (String) map.get("url"),
					(String) map.get("privateKey"), (String) sendCardsJsonData, (String) map.get("uniqueNo"),
					"fileUploadDataService");
			// boolean sendEndFlag = false;
			// ���ļ��а��ֽڶ�ȡ���ݣ����ļ�β��ʱread����������-1
			while ((bytesRead = ins.read(buffer)) != -1) {//�����С����2M  �������ȡ����
				// �������С��2M
				offSet = offSet + bytesRead;
				if (bytesRead < length) {
					// ���ý�����־
					endFileFlag = "Y";
					// sendEndFlag = true;
				} else {
					endFileFlag = "N";
				}
				// ��ȥ���ո�
				encoder = new BASE64Encoder();
				destbuff = new byte[bytesRead];
				System.arraycopy(buffer, 0, destbuff, 0, bytesRead);
				aesBase64 = encoder.encode(destbuff).replaceAll("[\r\n]", "");
				// ƴ��json�ַ���
				sendCardsJsonData = "{\"dataMap\":{\"uniqueNo\":\"" + uniqueNo + "\"," + "\"uploadFlowNo\":\"" + flwno
						+ "\"," + "\"fileOffset\":\"" + offSet + "\"," + "\"uploadEnd\":\"" + endFileFlag + "\","
						+ "\"fileContent\":\"" + aesBase64 + "\"}}";
				// ��������csb�ķ���
				st.sendCSB((String) map.get("ak"), (String) map.get("sk"), (String) map.get("url"),
						(String) map.get("privateKey"), (String) sendCardsJsonData, (String) map.get("uniqueNo"),
						"fileUploadDataService");
			}
		} catch (FileNotFoundException ex) {
			loggger.error("û���ҵ��ļ�>>>>>"+path);
			ex.printStackTrace();
			
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			// �ر� InputStream
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
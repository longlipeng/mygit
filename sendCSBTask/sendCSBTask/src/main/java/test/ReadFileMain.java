package test;


import java.util.Date;

import org.apache.log4j.PropertyConfigurator;

import service.CopProPertiesUtil;
import service.ReadFile;
/**
 * 
 * ���Գ��������
 *
 */
public class ReadFileMain {
	public static void main(String[] args) throws Exception {
		String logPF="src/main/resources/log4j.properties";	
		PropertyConfigurator.configureAndWatch(logPF, 6000);
		System.out.println("��ʼʱ��---��" + new Date());
		//��ȡ�����ļ����������ݸ�ʽ��������CSB������ƽ̨�ӿ�
		ReadFile rf = new ReadFile();
		try {
			CopProPertiesUtil.init();
			//����Ϊ��������Ψһ��ʶ 310115O7913242100157  uniqueNo
			rf.readFromFile(CopProPertiesUtil.getProperties().getProperty("uniqueNo"),	"F:/zzz.txt");
			System.out.println("����ʱ��---��" + new Date());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

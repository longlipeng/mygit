package test;


import java.util.Date;

import org.apache.log4j.PropertyConfigurator;

import service.CopProPertiesUtil;
import service.ReadFile;
/**
 * 
 * 测试程序主入口
 *
 */
public class ReadFileMain {
	public static void main(String[] args) throws Exception {
		String logPF="src/main/resources/log4j.properties";	
		PropertyConfigurator.configureAndWatch(logPF, 6000);
		System.out.println("开始时间---：" + new Date());
		//读取本地文件并处理数据格式，并发送CSB请求监管平台接口
		ReadFile rf = new ReadFile();
		try {
			CopProPertiesUtil.init();
			//参数为联网发行唯一标识 310115O7913242100157  uniqueNo
			rf.readFromFile(CopProPertiesUtil.getProperties().getProperty("uniqueNo"),	"F:/zzz.txt");
			System.out.println("结束时间---：" + new Date());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

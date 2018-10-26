package test;


import java.util.Date;

import service.ReadFile;
/**
 * 
 * 测试程序主入口
 *
 */
public class ReadFileMain {
	public static void main(String[] args) throws Exception {
		System.out.println("开始时间---：" + new Date());
		//读取本地文件并处理数据格式，并发送CSB请求监管平台接口
		ReadFile rf = new ReadFile();
		try {
			//参数为联网发行唯一标识
			rf.readFromFile("310115O7907343100038");
			System.out.println("结束时间---：" + new Date());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

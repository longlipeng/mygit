package com.huateng.common.server;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class MyClient {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		// 这里先强迫用户输入用户名，调用client(str)方法。

		// String str =
		// "<?xml version='1.0' encoding='utf-8'?><Comm><Txn_Num>9009</Txn_Num><Ips_Mcht_Code>A00001</Ips_Mcht_Code><Term_Id>11001240</Term_Id><Inst_Date>20120418164743</Inst_Date><ReFe_Num></ReFe_Num><Txn_Type>1011</Txn_Type><Pan>6227596187094975</Pan></Comm>";
		String str = "<?xml version='1.0' encoding='utf-8'?><Comm><Txn_Num>9008</Txn_Num><Ips_Mcht_Code>pos033</Ips_Mcht_Code><Term_Id></Term_Id><Begin_Date></Begin_Date><End_Date></End_Date><ReFe_Num></ReFe_Num><Order_TrsNum></Order_TrsNum><Txn_Type>0000</Txn_Type><Txn_State>0000</Txn_State><Pan></Pan><Min_Amount></Min_Amount><Max_Amount></Max_Amount><Cup_Ssn>074769</Cup_Ssn><Page_Num>1</Page_Num><Count>10</Count></Comm>#END";
		try {

			while (str.length() < 1) {
				System.out.println("请输入您的名字");

				str = new DataInputStream(new BufferedInputStream(System.in))
						.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// for(int i=0;i<1;i++){
		MyClient.client(str);
		// }
	}

	public static void client(String userName) {
		try {
			Socket s = new Socket(InetAddress.getByName("192.168.5.246"), 19030);// 套接字的IP地址和端口号

			OutputStream os = s.getOutputStream();
			InputStream is = s.getInputStream();

			byte[] Name = userName.getBytes("ISO-8859-1"); // 这个是为了中文乱码问题
			os.write(Name);// 向服务器发送消息

			byte[] serverSay = new byte[1024 * 1024];
			int len = is.read(serverSay);// 接受服务器消息

			System.out.println(new String(serverSay, 0, len));// 客户端控制台显示服务器返回的信息

			is.close();
			os.close();
			s.close();// 记住一定要关闭这些输入，输出流和套接字

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

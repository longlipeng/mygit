package hsmj;

import org.apache.log4j.Logger;

public class SessionMonitor extends Thread {
	Logger logger = Logger.getLogger(SessionMonitor.class);
	private static ShareHandle[] sHandle;

	public void addHandle(ShareHandle[] aHandle) {
		sHandle = aHandle;
	}

	public void run() {
		int i, j = 0;

		while (true) {
			try {
				//十秒检查一次链路是否故障
				sleep(10000);
			} catch (Exception e) {
			}

			for (i = 0; i < sHandle.length; i++) {
				if (sHandle[i].isFault()) {
					//如果链路故障
					try {
						//初始化链路
						sHandle[i].releaseSocketHandle();
						sHandle[i].connect();
						logger.info((new StringBuffer(
								"HSM SessionMonitor.run() - reconnect the fault HandleID["))
								.append(i).append("]").toString());
					} catch (Throwable t) {
						logger.error("HSM SessionMonitor.run() - " + t.getMessage());
					}
				}
			}

			j++;
			//一个小时发送一次测试报文，检查链路是否可以正常工作
//			if (j == 6 * 60) // 10 second * 6 * 60 = 1 hour
			if (j == 6) // 10 second * 6  = 1 分钟
			{
				j = 0;
				for (i = 0; i < sHandle.length; i++) {
					if (sHandle[i].isUsable()) {
						sHandle[i].setUsed();
						try {
							sHandle[i].testCmdNC();
							sHandle[i].setNotused();
							logger.info("#####加密机链路"+i+"正常！");
						} catch (Exception e) {
							sHandle[i].setFault();
						} catch (Error err) {
							sHandle[i].setFault();
						}
					}
				}
			}
		}
	}
}

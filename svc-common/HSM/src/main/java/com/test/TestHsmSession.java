/**
 * Test.java - 2011-7-21
 *
 * Licensed Property to China UnionPay Co., Ltd.
 * 
 * (C) Copyright of China UnionPay Co., Ltd. 2010
 *     All Rights Reserved.
 *
 * Project Info: China UnionPay Internet Acquiring Project
 * 
 * Modification History:
 * =============================================================================
 *   Author         Date          Description
 *   ------------ ---------- ---------------------------------------------------
 *        
 * =============================================================================
 */
package com.test;

import org.apache.log4j.Logger;

/**
 * Description:
 * 
 * (C) Copyright of China UnionPay Co., Ltd. 2010.
 * 
 */
public class TestHsmSession {
	private  Logger logger = Logger.getLogger(TestHsmSession.class);
	private static int sHsmNumber = 2;
	private static int balance = 10;

	private static Handler[] sHandleVector = new Handler[sHsmNumber * balance];
	private static String[] hsm = new String[]{"A", "B", "C"};
	public static int sPreIndex = -1;

	public class Handler {
		String id;
		boolean usable = false;
		boolean used = false;
		boolean fault = false;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public boolean isUsable() {
			return !used && !fault;
		}

		public void setUsable(boolean usable) {
			this.usable = usable;
		}

		public boolean isUsed() {
			return used;
		}

		public void setUsed(boolean used) {
			this.used = used;
		}

		public boolean isFault() {
			return fault;
		}

		public void setFault(boolean fault) {
			this.fault = fault;
		}

		public Handler(String id) {
			this.id = id;
		}
	}

	public void init() {
		int i;
		int j;
		for (i = 0; i < balance; i++) {
			for (j = 0; j < sHsmNumber; j++) {
				sHandleVector[i * sHsmNumber + j] = new Handler(hsm[j]);
			}
		}
		sHandleVector[1].setFault(true);
		sHandleVector[2].setUsed(true);
		sHandleVector[13].setUsed(true);
	}

	public Handler getSession(int idx) {
		int i = 0;
		boolean tStatus = false;
		int tNumOfSession = sHsmNumber * balance;

		for (i = sPreIndex + 1; i < tNumOfSession; i++) {
			/** If is a retry request, switch to next balance HSM **/
			if (idx != -1 && ((idx % sHsmNumber) == (i % sHsmNumber) || sHandleVector[i].isUsed())) {
				if ((idx % sHsmNumber) == (i % sHsmNumber)) {
					continue;
				} else {
					i = i + sHsmNumber;
				}
			}
			if (sHandleVector[i].isUsable()) {
				sHandleVector[i].setUsed(true);
				sPreIndex = i;
				tStatus = true;
				break;
			}
		}

		if (!tStatus) {
			for (i = 0; i < sPreIndex; i++) {
				if (idx != -1
						&& ((idx % sHsmNumber) == (i % sHsmNumber) || sHandleVector[i].isUsed())) {
					if ((idx % sHsmNumber) == (i % sHsmNumber)) {
						continue;
					} else {
						i = i + sHsmNumber;
					}
				}
				if (sHandleVector[i].isUsable()) {
					sHandleVector[i].setUsed(true);
					sPreIndex = i;
					tStatus = true;
					break;
				}
			}
		}

		if (!tStatus) {
			i = -1;
			logger.info("HsmSession.getSession() fail to get a TCP Connection !"
					+ " E1".getBytes() + "  1");
		}
		logger.info("choosed:" + i);
		return sHandleVector[i];
	}

/*	public static void main(String[] args) {
		TestHsmSession test = new TestHsmSession();
		test.init();
		test.sPreIndex = -1;
		Handler h = test.getSession(0);
		logger.info(h.getId() + ":" + h.isUsed());
	}*/

}

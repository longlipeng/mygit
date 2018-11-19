package com.huateng.framework.listener.batch;

public interface BatchListener {
	
	boolean checkBatchState(String batchNo);
	
	String getBatchType();

}

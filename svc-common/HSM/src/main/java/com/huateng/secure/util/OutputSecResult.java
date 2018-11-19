package com.huateng.secure.util;

public class OutputSecResult {
	public String 	dataStr;
	public byte[] 	dataByte;
	public int 		dataLenInt;
	public byte[] 	dataLenByte;
	public byte[] 	signData;
	
	 /**
     * set data
     * 
     * @param aIndex
     * @param aData
     * @param aDataLen
     * @return
     */
	public void setData(byte[] tData) {
		
		dataByte = new byte[tData.length];		
		System.arraycopy(tData, 0, dataByte, 0, tData.length);
		
		dataStr = new String(tData);
	}
	
	/**
     * set data
     * 
     * @param aIndex
     * @param aData
     * @param aDataLen
     * @return
     */
	public void setDataLen(byte[] tDataLen)	{
		
		dataLenByte = new byte[tDataLen.length];
		System.arraycopy(tDataLen, 0, dataLenByte, 0, tDataLen.length);
		
		Integer tIneger = Integer.valueOf(Integer.parseInt(new String(tDataLen)));
		dataLenInt = tIneger.intValue();
	}	
}

package com.webservice.util;

import java.util.Map;

public class Singleton {
	//私有的默认构造子  
    private Singleton() {}  
    //已经自行实例化   
    private static final Singleton single = new Singleton();  
    //静态工厂方法   
    public static synchronized  Singleton getInstance() {  
        return single;  
    } 
    
    private Map<String, String> mapStr;
    
    private String jsonStr;
	
	private String biaoshi;
	
	
	
	

	public Map<String, String> getMapStr() {
		return mapStr;
	}

	public void setMapStr(Map<String, String> mapStr) {
		this.mapStr = mapStr;
	}

	public String getJsonStr() {
		return jsonStr;
	}

	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}

	public String getBiaoshi() {
		return biaoshi;
	}

	public void setBiaoshi(String biaoshi) {
		this.biaoshi = biaoshi;
	}


	

	
	
	

}

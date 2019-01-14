package com.huateng.framework.struts2;

import java.util.Map;


import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
/***
 * 
 * @author dawn
 *
 */
public class TranfMeaningInterceptor  extends AbstractInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		
		final Map<String, Object> parameters = invocation.getInvocationContext().getParameters();
		
		String[]  parameterArray= new String[2];	
		int indexLeft = 0;
		
		int indexRight =0;
		
		for (String key:parameters.keySet()){
			Object object = parameters.get(key);
			
			if(object instanceof String[] ){
				parameterArray= (String[])object;
				String[] newParameter = new String[parameterArray.length];
				int i=0;
				if(parameterArray!=null && parameterArray.length>0){
					for (String parameter: parameterArray){
							indexLeft = parameter.indexOf("<");
							if(indexLeft!=-1){
								parameter = parameter.replace("<", "&lt;");
							}
							indexRight = parameter.indexOf(">");
							if(indexRight!=-1){
								parameter = parameter.replace(">", "&gt;");
							}
							newParameter[i] = parameter;
							i++;
					}
				}
				parameters.put(key, newParameter);
			}
		}
		invocation.getInvocationContext().setParameters(parameters);
		String result = invocation.invoke();
		return result;
	}
}

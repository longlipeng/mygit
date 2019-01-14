package com.huateng.framework.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.WebServiceRef;



@WebService(name="ManageService")
@SOAPBinding(parameterStyle=SOAPBinding.ParameterStyle.WRAPPED)
public interface ManageService {
	@WebServiceRef(wsdlLocation= "http://localhost:8080/ShangQiGateWayService/manageService?wsdl")
	/**
	 * 
	 * @param ctx
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public String sendServece(String ctx, String req) ;
	
	/**
	 * 
	 * @param ctx
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@WebMethod
	public String sendMisService(
			@WebParam(name="ctx")
			String ctx, 
			@WebParam(name="req")
			String req) ;
}

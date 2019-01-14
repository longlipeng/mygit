package com.huateng.univer.hessian;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.webservice.ServiceTarget;



public class ManageHessianInvoke implements ApplicationContextAware {
	Logger logger = Logger.getLogger(ManageHessianInvoke.class);

	public static final String METHOD_SEPARATOR = "!";

	private String serviceName;

	private String serviceMethod;

	private ApplicationContext applicationContext;

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

	public Object excuteWebService(String serviceCode, Object o)
			throws BizServiceException {
		//限制Hessian接口只能调用同步信息功能
		if(!serviceCode.startsWith("X")){
			throw new BizServiceException("txnCode is not validation.");
		}
		// 利用交易码得到 对应 的SPRINGID和方法名.
		Map<String, String> service = ServiceTarget.getServiceTarget();
		String webServiceTarget = (String) service.get(serviceCode);
		setWebServiceTarget(webServiceTarget);
		
		// 得到service bean
		Object objService = applicationContext.getBean(serviceName);
		
		// 得到方法
		Method method = getTargetMethod(objService);
		
		// 返回对象
		Object obj;
		try {
			if (method.getParameterTypes().length == 1) {
				obj = method.invoke(objService, o);
			} else {
				obj = method.invoke(objService);
			}
		} catch (IllegalArgumentException e) {
			logger.error(e);
			throw new BizServiceException("系统异常");
		} catch (IllegalAccessException e) {
			logger.error(e);
			throw new BizServiceException("系统异常");
		} catch (InvocationTargetException e) {
			logger.error(e);
			if (e.getTargetException() instanceof BizServiceException) {
				throw (BizServiceException) e.getTargetException();
			}
			throw new BizServiceException("系统异常");
		}
		return obj;
	}

	private Method getTargetMethod(Object service) {
		return findMethod(service.getClass());
	}

	/**
	 * 找到方法 FIXME 注意.此方法不支持重载情况. 是否需要修改?
	 * 
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected Method findMethod(Class type) {
		Method methods[] = type.getMethods();
		for (int i = 0; i < methods.length; i++) {
			if (serviceMethod.equals(methods[i].getName())) {
				Class[] paramTypes = methods[i].getParameterTypes();
				if (null == paramTypes || 0 == paramTypes.length
						|| 1 == paramTypes.length) {
					return methods[i];
				} else {
					// 只调用参数个数为0或者1的方法，其他忽略
					continue;
				}
			}
		}
		throw new RuntimeException("Invalid target method " + serviceMethod);
	}

	/**
	 * 拆分对象(spring中的映射ID或name)与方法.
	 * 
	 * @param expression
	 */
	public void setWebServiceTarget(String expression) {
		try {
			String[] strs = expression.split(METHOD_SEPARATOR);
			serviceName = strs[0];
			serviceMethod = strs[1];
			if( strs[0].equals("")|| strs[1].equals("")){
				throw new Exception();
			}
		} catch (Exception e) {
			throw new IllegalArgumentException("Invalid target expression: "
					+ expression);
		}
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServiceMethod() {
		return serviceMethod;
	}

	public void setServiceMethod(String serviceMethod) {
		this.serviceMethod = serviceMethod;
	}
}

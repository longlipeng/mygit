package com.huateng.framework.webservice;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.allinfinance.framework.dto.OperationResult;
import com.huateng.framework.exception.BizServiceException;

/**
 * 
 * @author jianji.dai
 * 
 */
public class WebServiceInvoke implements ApplicationContextAware {
	private Logger logger = Logger.getLogger(WebServiceInvoke.class);
	public OperationResult excuteWebService(String serviceCode,
			Object operationRequest) throws BizServiceException {

		Map<String, String> service = ServiceTarget.getServiceTarget();
		String webServiceTarget = (String) service.get(serviceCode);

		setWebServiceTarget(webServiceTarget);

		Object objService = this.applicationContext.getBean(serviceName);

		Method method = this.getTargetMethod(objService);

		Object obj;
		try {
			if (method.getParameterTypes().length == 1) {
				obj = method.invoke(objService, operationRequest);
			} else {
				obj = method.invoke(objService);
			}
		} catch (IllegalArgumentException e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("系统异常");
		} catch (IllegalAccessException e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("系统异常");
		} catch (InvocationTargetException e) {
			this.logger.error(e.getMessage());
			if (e.getTargetException() instanceof BizServiceException) {
				throw (BizServiceException) e.getTargetException();
			}
			throw new BizServiceException("系统异常");
		}

		OperationResult oprRst = new OperationResult();

		oprRst.setDetailvo(obj);

		return oprRst;

	}

	public void setWebServiceTarget(String expression) {
		int idx = expression.lastIndexOf(METHOD_SEPARATOR);
		if (idx <= 0) {
			throw new IllegalArgumentException("Invalid target expression: "
					+ expression);
		}
		setServiceName(expression.substring(0, idx));
		setServiceMethod(expression.substring(idx + METHOD_SEPARATOR.length()));
	}

	/**
	 * 缓存Method，避免重复查询
	 */
	private transient Method targetMethod;

	private Method getTargetMethod(Object service) {
		// Method method = targetMethod;
		// // double check for thread safe
		// if (null == method) {
		// synchronized (this) {
		// if (null == targetMethod) {
		// targetMethod = findMethod(service.getClass());
		// }
		// }
		// method = targetMethod;
		// }
		// return method;
		return findMethod(service.getClass());
	}

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

	private ApplicationContext applicationContext;

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;

	}

	public void setBeanName(String name) {
		if (null == this.serviceName) {
			this.setWebServiceTarget(name);
		}
	}

	static final String METHOD_SEPARATOR = "!";

	private String serviceName;

	private String serviceMethod;

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public void setServiceMethod(String serviceMethod) {
		this.serviceMethod = serviceMethod;
	}
}

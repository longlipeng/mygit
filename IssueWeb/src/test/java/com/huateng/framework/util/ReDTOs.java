package com.huateng.framework.util;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.allinfinance.framework.dto.OperationResult;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.framework.dto.PageQueryDTO;
import com.huateng.framework.constant.Const;
import com.huateng.framework.exception.BizServiceException;

/**
 * 返回一个有值(需要根据具体问题修改其中一部分)的DTO getDTO 或者返回一个OperationResult getObj
 * 
 * @author wpf
 * 
 */
public class ReDTOs {
	private static Logger logger = Logger.getLogger(ReDTOs.class);
	/**
	 * 
	 * @param clazz
	 *            需要返回的DTO的class
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object getDTO(Class clazz) {
		return getDTO(clazz, false);
	}

	/**
	 * 
	 * @param clazz
	 *            需要返回的DTO的class
	 * @param superFileds
	 *            是否需要父类的属性 false不需要true需要
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object getDTO(Class clazz, boolean superFileds) {

		Object obj = null;
		try {
			// 创建对象
			obj = clazz.newInstance();
			// 返回属性集
			Field[] fields = clazz.getDeclaredFields();
			set(fields, obj);
			if (superFileds) {
				fields = clazz.getSuperclass().getDeclaredFields();
				set(fields, obj);
			}

		} catch (Exception ex) {
			logger.error(ex.getMessage());
			return null;

		}
		return obj;
	}

	/**
	 * 返回一个正常的OperationResult
	 * 
	 * @param obj
	 *            需要返回的DTO对象
	 * @return
	 */
	public static OperationResult getObj(Object obj) {
		return getObj(obj, null);
	}

	/**
	 * 返回一个OperationResult
	 * 
	 * @param obj
	 *            需要返回的DTO对象
	 * @param e
	 *            如果需要返回时抛出一个Exception
	 * @return
	 */
	public static OperationResult getObj(Object obj, BizServiceException e) {
		OperationResult oprRst = new OperationResult();
		if (e == null) {
			oprRst.setTxnstate(Const.RETURN_SUCC);
			oprRst.setDetailvo(obj);
		} else {
			oprRst.setErrMessage(e.getErrorMessage());
			oprRst.setTxnstate(Const.RETURN_FAILED);
		}

		return oprRst;
	}

	/**
	 * 自动加载DTO属性
	 * 
	 * @param fields
	 *            属性集合
	 * @param obj
	 *            DTO对象
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Object set(Field[] fields, Object obj) throws Exception {
		for (Field field : fields) {
			// 设置私有属性可修改
			field.setAccessible(true);
			// logger.info(field);
			// 取得属性类型
			Class clz = field.getType();
			// logger.info(clz.toString());
			// 非基本类型直接赋值 当为字符串时,加非空串.
			// logger.info(field.getType());
			if (field.getType().equals(String.class)) {
				field.set(obj, "ok");
			} else if (field.getType().equals(List.class)) {
				List l = new ArrayList();
				field.set(obj, l);
			} else if (field.getType().equals(List.class)) {
				List l = new ArrayList();
				field.set(obj, l);
			} else if (field.getType().equals(PageDataDTO.class)) {
				PageDataDTO pdd = new PageDataDTO();
				List l = new ArrayList();
				pdd.setData(l);
				pdd.setTotalRecord(0);
				pdd.setTotalRecord(0);
				field.set(obj, pdd);
			} else if (field.getType().equals(PageQueryDTO.class)) {
				PageQueryDTO pqd = new PageQueryDTO();
				pqd.setPageSize(0);
				field.set(obj, pqd);
			} else if (field.getType().isPrimitive()) {
				if (!"serialVersionUID".equals(field.getName())) {
					// 当为基础变量的时候赋值1 serialVersionUID序列化变量不可改
					field.set(obj, convert(1, field.getType()));
				}
			} else {
				// 接口时. 特殊处理.
				if (field.getType().isArray()) {
					field.set(obj, array(field.getType()));
				} else if (field.getType().isInterface()) {
					field.set(obj, null);
				} else {
					field.set(obj, clz.newInstance());
				}
			}
		}
		return obj;
	}

	/**
	 * 数组类型
	 * 
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public static Object array(Class<?> type) throws Exception {

		if (type.equals(int[].class)) {
			return new int[1];
		} else if (type.equals(short[].class)) {
			return new short[1];
		} else if (type.equals(float[].class)) {
			return new float[1];
		} else if (type.equals(double[].class)) {
			return new double[1];
		} else if (type.equals(long[].class)) {
			return new long[1];
		} else if (type.equals(char[].class)) {
			return new char[1];
		} else if (type.equals(byte[].class)) {
			return new byte[1];
		} else if (type.equals(boolean[].class)) {
			return new boolean[1];
		} else {
			String cls = type.toString().substring(
					type.toString().indexOf("[L") + 2,
					type.toString().length() - 1);
			return Array.newInstance(Class.forName(cls), 1);
		}

	}

	/**
	 * 处理基本类型
	 * 
	 * @param object
	 * @param type
	 * @return
	 */
	public static Object convert(Object object, Class<?> type) {

		if (object instanceof Number) {

			Number number = (Number) object;

			if (type.equals(byte.class) || type.equals(Byte.class)) {
				return number.byteValue();
			}
			if (type.equals(short.class) || type.equals(Short.class)) {
				return number.shortValue();
			}
			if (type.equals(int.class) || type.equals(Integer.class)) {
				return number.intValue();
			}
			if (type.equals(long.class) || type.equals(Long.class)) {
				return number.longValue();
			}
			if (type.equals(float.class) || type.equals(Float.class)) {
				return number.floatValue();
			}
			if (type.equals(double.class) || type.equals(Double.class)) {
				return number.doubleValue();
			}
			if (type.equals(boolean.class) || type.equals(Boolean.class)) {
				return false;
			}
			if (type.equals(char.class) || type.equals(Character.class)) {
				return 'a';
			}
		}
		return object;

	}

}
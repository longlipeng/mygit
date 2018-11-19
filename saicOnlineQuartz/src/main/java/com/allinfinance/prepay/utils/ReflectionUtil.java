package com.allinfinance.prepay.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;

public class ReflectionUtil {

	/**
	 * ��ȡָ�����������ֵ
	 * 
	 * @param source
	 * @param property
	 * @return ����ֵ
	 */
	public static Object getProperty(Object source, String property) {
		BeanWrapper sourceBw = new BeanWrapperImpl(source);
		return sourceBw.getPropertyValue(property);
	}

	/**
	 * ����ָ�����������
	 * 
	 * @param source
	 *            ����
	 * @param property
	 *            ����
	 * @param value
	 *            �µ�����ֵ
	 */
	public static void setProperty(Object source, String property, Object value) {
		BeanWrapper sourceBw = new BeanWrapperImpl(source);
		sourceBw.setPropertyValue(property, value);
	}

	/**
	 * ��src�е�����Copy��ָ��target
	 * 
	 * @param src
	 * @param target
	 */
	public static void copyProperties(Object src, Object target) {
		copyProperties(src, target, null);
	}

	/**
	 * ��src�е�����Copy��ָ��target�����Ǻ���ָ��������
	 * 
	 * @param src
	 * @param target
	 * @param ignoreProps
	 *            ָ�����Ե�����
	 */
	public static void copyProperties(Object source, Object target,
			String[] ignoreProperties) {
		if (source == null || target == null) {
			throw new IllegalArgumentException(
					"Source and target must not be null");
		}
		List<String> ignoreList = (ignoreProperties != null) ? Arrays
				.asList(ignoreProperties) : null;
		BeanWrapper sourceBw = new BeanWrapperImpl(source);
		BeanWrapper targetBw = new BeanWrapperImpl(target);
		MutablePropertyValues values = new MutablePropertyValues();
		for (int i = 0; i < sourceBw.getPropertyDescriptors().length; i++) {
			PropertyDescriptor sourceDesc = sourceBw.getPropertyDescriptors()[i];
			String name = sourceDesc.getName();
			try {
				PropertyDescriptor targetDesc = targetBw
						.getPropertyDescriptor(name);
				if (targetDesc.getWriteMethod() != null
						&& sourceDesc.getReadMethod() != null
						&& (ignoreProperties == null || (!ignoreList
								.contains(name)))) {
					values.addPropertyValue(new PropertyValue(name, sourceBw
							.getPropertyValue(name)));
				}
			} catch (BeansException e) {

			}
		}
		targetBw.setPropertyValues(values);
	}

	public static void copyPropertiesNotNull(Object source, Object target) {
		if (source == null || target == null) {
			throw new IllegalArgumentException(
					"Source and target must not be null");
		}

		BeanWrapper sourceBw = new BeanWrapperImpl(source);
		BeanWrapper targetBw = new BeanWrapperImpl(target);
		MutablePropertyValues values = new MutablePropertyValues();
		for (int i = 0; i < sourceBw.getPropertyDescriptors().length; i++) {
			PropertyDescriptor sourceDesc = sourceBw.getPropertyDescriptors()[i];
			String name = sourceDesc.getName();
			try {
				PropertyDescriptor targetDesc = targetBw
						.getPropertyDescriptor(name);
				if (targetDesc.getWriteMethod() != null
						&& sourceDesc.getReadMethod() != null) {
					if (sourceBw.getPropertyValue(name) != null)
						values.addPropertyValue(new PropertyValue(name,
								sourceBw.getPropertyValue(name)));
				}
			} catch (BeansException e) {

			}
		}
		targetBw.setPropertyValues(values);
	}

	/**
	 * ͨ��toString����,��һ��������������
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String toString(Object obj) {
		StringBuffer sb = new StringBuffer();
		try {
			Class c1 = obj.getClass();

			do {
				sb.append("[");
				Field[] fields = c1.getDeclaredFields();
				AccessibleObject.setAccessible(fields, true);
				for (int i = 0; i < fields.length; i++) {
					Field f = fields[i];
					sb.append(f.getName() + "=");
					Object val = f.get(obj);
					if (val != null)
						sb.append(val.toString());

					if (i < fields.length - 1)
						sb.append(",");
				}
				sb.append("]");
				c1 = c1.getSuperclass();
			} while (c1 != Object.class);
		} catch (Exception e) {
			sb.append(e.toString() + "����ת��Ϊ�ַ�������");
		}
		return sb.toString();
	}

	/**
	 * Check if the given class represents a "simple" property, i.e. a
	 * primitive, a String, a Class, or a corresponding array.
	 * 
	 */
	public static boolean isSimpleProperty(Class<?> clazz) {
		return clazz.isPrimitive() || isPrimitiveArray(clazz)
				|| isPrimitiveWrapperArray(clazz) || clazz.equals(String.class)
				|| clazz.equals(String[].class) || clazz.equals(Class.class)
				|| clazz.equals(Class[].class);
	}

	/**
	 * Check if the given class represents a primitive array, i.e. boolean,
	 * byte, char, short, int, long, float, or double.
	 */
	public static boolean isPrimitiveArray(Class<?> clazz) {
		return boolean[].class.equals(clazz) || byte[].class.equals(clazz)
				|| char[].class.equals(clazz) || short[].class.equals(clazz)
				|| int[].class.equals(clazz) || long[].class.equals(clazz)
				|| float[].class.equals(clazz) || double[].class.equals(clazz);
	}

	/**
	 * Check if the given class represents an array of primitive wrappers, i.e.
	 * Boolean, Byte, Character, Short, Integer, Long, Float, or Double.
	 */
	public static boolean isPrimitiveWrapperArray(Class<?> clazz) {
		return Boolean[].class.equals(clazz) || Byte[].class.equals(clazz)
				|| Character[].class.equals(clazz)
				|| Short[].class.equals(clazz) || Integer[].class.equals(clazz)
				|| Long[].class.equals(clazz) || Float[].class.equals(clazz)
				|| Double[].class.equals(clazz);
	}

}

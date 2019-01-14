package com.allinfinance.prepay.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface MessageField
{
	public enum FieldType{CHARACTER,NUMBER,HEXSTRING};
	
	int maxLength() default -1;
	
	int minLength() default -1;
	
	FieldType fieldType() default FieldType.CHARACTER;
	
	int fixedLength() default -1;
	
	boolean mandetory() default false;
}

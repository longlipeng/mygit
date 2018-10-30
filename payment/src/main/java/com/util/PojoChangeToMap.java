package com.util;

import org.apache.log4j.Logger;

import java.util.Date;
import java.util.HashMap;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by liuheli on 2017/5/11.
 */
public class PojoChangeToMap<T> {

//    private static Logger logger = Logger.getLogger(PojoChangeToMap.class);

    public HashMap<String, String> pojoSwapToMap(T object){
        HashMap<String, String> pojoMap = new HashMap<String, String>();
        try {
            Field[] filedArray = object.getClass().getDeclaredFields();
            for(Field filed : filedArray){
//                String methodName = "get"+file.getName();
//                Method getMethodName = model.getClass().getDeclaredMethod(methodName);
//                String value = getMethodName.invoke(model) == null ? null : getMethodName.invoke(model).toString();
                filed.setAccessible(true);
                String value = filed.get(object) == null? null : filed.get(object).toString();
                pojoMap.put(filed.getName(),value);
                filed.setAccessible(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pojoMap;
    }

    public T setRespodInfo(T requestInfo, T respondInfo){
        try {
            Field[] requestFiled = requestInfo.getClass().getDeclaredFields();
            Field[] respondFiled = respondInfo.getClass().getDeclaredFields();
            for(Field rpFiled : respondFiled){
                String rpName = rpFiled.getName();
                for(Field rqFiled : requestFiled){
                    if(rpName.equals(rqFiled.getName())){
                        rqFiled.setAccessible(true);
                        String value = rqFiled.get(requestInfo) == null? null : rqFiled.get(requestInfo).toString();
                        rqFiled.setAccessible(false);
                        rpFiled.setAccessible(true);
                        rpFiled.set(respondInfo,value);
                        rpFiled.setAccessible(false);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return respondInfo;
    }

}

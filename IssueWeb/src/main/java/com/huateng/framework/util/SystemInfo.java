package com.huateng.framework.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.allinfinance.univer.system.dictinfo.dto.DictInfoDTO;
import com.allinfinance.univer.system.dictinfo.dto.EntityDictInfoDTO;
import com.allinfinance.univer.system.sysparam.dto.EntitySystemParameterDTO;
import com.allinfinance.univer.system.sysparam.dto.SystemParameterDTO;

public class SystemInfo {
    /**
     * 字典数据
     */
    private static Map<String, List<DictInfoDTO>> dictInfo;
    /**
     * 系统参数数据
     */
    private static List<SystemParameterDTO> parameters;
    
    /***
     * 实体数据字典
     */
    private static Map<String,Map<String, List<EntityDictInfoDTO>>> entityDictInfo;
    
    /***
     * 实体系统参数 
     */
    
    private static Map<String,List<EntitySystemParameterDTO>> entityParameters;

    /**
     * 根据字典类型 返回字典数据列表
     * 
     * @param dictType
     * @return
     */
    public static List<DictInfoDTO> getDictList(String dictType) {
        return dictInfo.get(dictType);
    }

    /***
     * 根据实体ID
     * 字典类型
     * 返回字典数据列表
     * @param entityId
     * @param dictType
     * @return
     */
    public static List<EntityDictInfoDTO> getDictList(String entityId,String dictType) {
    
    	return entityDictInfo.get(entityId).get(dictType);
    
    }
    
    
    
    /**
     * 根据字典名称和字典类型取字典id
     * @param dictType
     * @param dictName
     * @return
     */
    public static String getDictCode(String dictType, String dictName) {
        String dictCode = null;
        List<DictInfoDTO> dictInfoDTOs = getDictList(dictType);
        for (int i = 0; i < dictInfoDTOs.size(); i++) {
            DictInfoDTO dictInfoDTO = dictInfoDTOs.get(i);
            if (dictInfoDTO.getDictName().equals(dictName) || dictInfoDTO.getDictShortName().equals(dictName)) {
            	dictCode = dictInfoDTO.getDictCode();
            }
        }
        return dictCode;
    }

    /***
     * 根据实体ID
     * 数据字典数据
     * 数据字典名称
     * @param dictType
     * @param dictName
     * @param entityId
     * @return
     */
    public static String getDictCode(String entityId,String dictType, String dictName){
    	 String dictCode = null;
         
    	 List<EntityDictInfoDTO> dictInfoDTOs = getDictList(entityId,dictType);
         
         for (int i = 0; i < dictInfoDTOs.size(); i++) {
        	 EntityDictInfoDTO dictInfoDTO = dictInfoDTOs.get(i);
             if (dictInfoDTO.getDictName().equals(dictName) || dictInfoDTO.getDictShortName().equals(dictName)) {
             	dictCode = dictInfoDTO.getDictCode();
             }
         }
         return dictCode;
    }
    
    
    
    
    
    
    /**
     * 根据字典类型和父字典id返回字典列表
     * 
     * @param dictType
     * @param fatherDictId
     * @return
     */
    public static List<DictInfoDTO> getSubDictList(String dictType, Integer fatherDictCode) {

        List<DictInfoDTO> dictInfos = dictInfo.get(dictType);
        List<DictInfoDTO> returnDictInfos = new ArrayList<DictInfoDTO>();
        for (int i = 0; i < dictInfos.size(); i++) {
            DictInfoDTO dictInfoDTO = dictInfos.get(i);
            if (dictInfoDTO.getFatherDictCode().equals(fatherDictCode.toString())) {
                returnDictInfos.add(dictInfoDTO);
            }
        }
        return returnDictInfos;
    }
    
    /**
     * 根据实体ID，数据字典类型
     * 父字典id返回字典列表
     * @param entityId
     * @param dictType
     * @param fatherDictCode
     * @return
     */
    public static List<EntityDictInfoDTO> getSubDictList(String entityId,String dictType, Integer fatherDictCode) {

        List<EntityDictInfoDTO> dictInfos = entityDictInfo.get(entityId).get(dictType);
       
        List<EntityDictInfoDTO> returnDictInfos = new ArrayList<EntityDictInfoDTO>();
        
        for (int i = 0; i < dictInfos.size(); i++) {
        	EntityDictInfoDTO dictInfoDTO = dictInfos.get(i);
            if (dictInfoDTO.getFatherDictCode().equals(fatherDictCode.toString())) {
                returnDictInfos.add(dictInfoDTO);
            }
        }
        return returnDictInfos;
    }

    
    
    
    
    /**
     * 取所有的字典数据
     * 
     * @return
     */

    public static Map<String, List<DictInfoDTO>> getDictInfo() {
        return dictInfo;
    }

    /**
     * 根据系统参数代码 返回系统参数的值
     * 
     * @param parameterCode
     * @return
     */

    public static String getParameterValue(String parameterCode) {
        String parameterValue = null;
        for (int i = 0; i < parameters.size(); i++) {
            SystemParameterDTO systemParameterDTO = parameters.get(i);
            if (parameterCode.equals(systemParameterDTO.getParameterCode())) {
                parameterValue = systemParameterDTO.getParameterValue();
            }
        }
        return parameterValue;
    }
    public static String getEntityParameterValue(String parameterCode,String entityId) {
        String parameterValue = null;
        
        List<EntitySystemParameterDTO> entityParameterList = entityParameters.get(entityId);
        
        if(entityParameterList!=null){
	        for (int i = 0; i < entityParameterList.size(); i++) {
	            EntitySystemParameterDTO entitySystemParameterDTO = entityParameterList.get(i);
	            if (parameterCode.equals(entitySystemParameterDTO.getParameterCode())) {
	                parameterValue = entitySystemParameterDTO.getParameterValue();
	            }
	        }
        }
        return parameterValue;
    }
    
    
    
    
    /**
     * 返回所有的系统参数
     * 
     * @return
     */
    public static List<SystemParameterDTO> getParameters() {
        return parameters;
    }

    public static void setDictInfo(Map<String, List<DictInfoDTO>> dictInfo) {
        SystemInfo.dictInfo = dictInfo;
    }

    public static void setParameters(List<SystemParameterDTO> parameters) {
        SystemInfo.parameters = parameters;
    }




	public static Map<String, Map<String, List<EntityDictInfoDTO>>> getEntityDictInfo() {
		return entityDictInfo;
	}




	public static void setEntityDictInfo(
			Map<String, Map<String, List<EntityDictInfoDTO>>> entityDictInfo) {
		SystemInfo.entityDictInfo = entityDictInfo;
	}

	public static Map<String, List<EntitySystemParameterDTO>> getEntityParameters() {
		return entityParameters;
	}

	public static void setEntityParameters(
			Map<String, List<EntitySystemParameterDTO>> entityParameters) {
		SystemInfo.entityParameters = entityParameters;
	}

	

		

}

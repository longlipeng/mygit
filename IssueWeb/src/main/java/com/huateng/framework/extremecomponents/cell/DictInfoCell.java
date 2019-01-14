package com.huateng.framework.extremecomponents.cell;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.cell.AbstractCell;
import org.extremecomponents.table.core.TableModel;

import com.allinfinance.univer.system.dictinfo.dto.DictInfoDTO;
import com.huateng.framework.util.SystemInfo;
/***
 * 用于数据字典
 * @author dawn
 */
public class DictInfoCell extends AbstractCell{
	/***
	 * 
	 */
	@Override
	protected String getCellValue(TableModel tableModel, Column column) {
		/***
		 * 通过别名指定数据典类型
		 */
		String dict_type_code= column.getAlias();
		
		List<DictInfoDTO> list = (List<DictInfoDTO>)SystemInfo.getDictInfo().get(dict_type_code);
		
		String value = column.getValueAsString();
		
		if (StringUtils.isBlank(value)){
			return "";
		}
		if(list!=null){
			for (DictInfoDTO d : list) {
				if (value.equals(String.valueOf(d.getDictCode()))) {
					value = d.getDictShortName();
					break;
				}
			}
		}
		return value;
	}

	
	
}

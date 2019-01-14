package com.huateng.framework.extremecomponents.cell;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.cell.AbstractCell;
import org.extremecomponents.table.core.TableModel;

import com.allinfinance.univer.system.dictinfo.dto.DictInfoDTO;
import com.huateng.framework.util.SystemInfo;

/**
 * 字典表商务区域(408)
 * @author wang.jiaomiao
 *
 */
public class DictAreaCell extends AbstractCell {

	@SuppressWarnings("unchecked")
	@Override
	protected String getCellValue(TableModel model, Column column) {

		List<DictInfoDTO> list = (List<DictInfoDTO>)SystemInfo.getDictInfo().get("408");
		String value = column.getValueAsString();
		if (StringUtils.isBlank(value))
			return "";
		
		for (DictInfoDTO d : list) {
			if (value.equals(String.valueOf(d.getDictCode()))) {
				value = d.getDictShortName();
				break;
			}
		}
		return value;
	}

}
package com.huateng.framework.extremecomponents.cell;

import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.cell.AbstractCell;
import org.extremecomponents.table.core.TableModel;

public class SelectCell extends AbstractCell{

	@Override
	protected String getCellValue(TableModel model, Column column) {
		String value = column.getValueAsString();
		if(value.equals("1")){
			return "有效";
		}else{
			return "无效";
		}
		
	}

}

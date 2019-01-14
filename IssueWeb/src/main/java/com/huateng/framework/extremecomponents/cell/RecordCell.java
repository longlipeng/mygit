package com.huateng.framework.extremecomponents.cell;

import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.cell.AbstractCell;
import org.extremecomponents.table.core.TableModel;

public class RecordCell extends AbstractCell{

	@Override
	protected String getCellValue(TableModel model, Column column) {
		String value = column.getValueAsString();
		if(value.equals("0")){
			return "删除";
		}else if(value.equals("1")){
			return "编辑";
		}else {
			return "添加";
		}
	}

}

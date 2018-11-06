package com.huateng.framework.extremecomponents.cell;

import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.cell.AbstractCell;
import org.extremecomponents.table.core.TableModel;

public class CardSelectCell  extends AbstractCell{

	@Override
	protected String getCellValue(TableModel model, Column column) {
		String value = column.getValueAsString();
		if(value.equals("00")){
			return "成功";
		}else{
			return "不成功";
		}
	}
		
}

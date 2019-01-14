package com.huateng.framework.extremecomponents.cell;

import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.cell.AbstractCell;
import org.extremecomponents.table.core.TableModel;

public class CusStateCell extends AbstractCell {

	@Override
	protected String getCellValue(TableModel arg0, Column arg1) {
		String value = arg1.getValueAsString();
		if("0".equals(value)){
			return "失效";
		}else if("1".equals(value)){
			return "已审核";
		}else if("2".equals(value)){
			return "未审核";
		}else if("4".equals(value)){
			return "审核中";
		}else{
			return "审核未通过";
		}
	}
}

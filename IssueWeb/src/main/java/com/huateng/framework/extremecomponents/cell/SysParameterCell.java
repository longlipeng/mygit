package com.huateng.framework.extremecomponents.cell;



import org.apache.commons.lang.StringUtils;
import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.cell.AbstractCell;
import org.extremecomponents.table.core.TableModel;



public class SysParameterCell extends AbstractCell {

	@Override
	protected String getCellValue(TableModel model, Column column) {

		
		
		String value = column.getValueAsString();
		if (StringUtils.isBlank(value))
			return "";
		if(value.equals("9F06")){
			value="AID";
		}else if(value.equals("DF01")){
			value="ASI";
		}else if(value.equals("9F08")){
			value="应用版本号";
		}else if(value.equals("DF11")){
			value="TAC-缺省";
		}else if(value.equals("DF12")){
			value="TAC-联机";
		}else if(value.equals("DF13")){
			value="TAC-拒绝";
		}else if(value.equals("DF18")){
			value="终端联机PIN支持";
		}
		return value;
	}

}

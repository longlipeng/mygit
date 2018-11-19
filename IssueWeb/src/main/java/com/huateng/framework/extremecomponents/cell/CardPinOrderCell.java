package com.huateng.framework.extremecomponents.cell;



import org.apache.commons.lang.StringUtils;
import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.cell.AbstractCell;
import org.extremecomponents.table.core.TableModel;



public class CardPinOrderCell extends AbstractCell {

	@Override
	protected String getCellValue(TableModel model, Column column) {

		
		
		String value = column.getValueAsString();
		if (StringUtils.isBlank(value))
			return "";
		if(value.equals("0")){
			value="取消";
		}else if(value.equals("1")){
			value="草稿";
		}else if(value.equals("2")){
			value="待确定";
		}else if(value.equals("3")){
			value="待打印";
		}else if(value.equals("4")){
			value="待入库";
		}else{
			value="完成";
		}
		
		return value;
	}

}

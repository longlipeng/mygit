package com.huateng.framework.extremecomponents.cell;

import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.cell.AbstractCell;
import org.extremecomponents.table.core.TableModel;
/**
 * 费率
 * @author dulei.wang
 *
 */
public class RateCell  extends AbstractCell{

    @Override
    protected String getCellValue(TableModel model, Column column) {
        String value = column.getValueAsString();
        if(value==null||"".equals(value.trim())){
            return "";
        }
       return value+"%";
    }
        
}

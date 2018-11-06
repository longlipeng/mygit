package com.huateng.framework.extremecomponents.cell;

import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.cell.AbstractCell;
import org.extremecomponents.table.core.TableModel;
/**
 * 金额
 * @author dulei.wang
 *
 */
public class AmtCell  extends AbstractCell{

    @Override
    protected String getCellValue(TableModel model, Column column) {
        String value = column.getValueAsString();
        if(value==null||"".equals(value.trim())){
            return "0.0";
        }
        String b=new Long(value).toString();
        if(b.length()<3&&b.length()>1){
            b= "0."+b;
        }else if(b.length()<2){
            b="0.0"+b;
        }else{
            b=b.substring(0, b.length()-2)+"."+b.substring(b.length()-2);
        }
            return b;
    }
        
}

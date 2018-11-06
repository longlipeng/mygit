package com.huateng.framework.extremecomponents.cell;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.cell.AbstractCell;
import org.extremecomponents.table.core.TableModel;

import com.allinfinance.univer.system.dictinfo.dto.DictInfoDTO;
import com.huateng.framework.constant.Const;
import com.huateng.framework.util.SystemInfo;

public class PayCell extends AbstractCell {

    @Override
    protected String getCellValue(TableModel model, Column column) {

        List<DictInfoDTO> list = (List<DictInfoDTO>)SystemInfo.getDictInfo().get(Const.DICT_PAY_TYPE_CODE);
        
        String value = column.getValueAsString();
        if (StringUtils.isBlank(value))
            return "";
        
        for (DictInfoDTO d : list) {
            if (value.equals(String.valueOf(d.getDictId()))) {
                value = d.getDictShortName();
                break;
            }
        }
        return value;
    }

}

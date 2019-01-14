/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: EntityDictInfoCell.java
 * Author:   12073942
 * Date:     2013-11-10 上午10:35:27
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.ectable.cell;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.cell.AbstractCell;
import org.extremecomponents.table.core.TableModel;
import org.springframework.security.context.SecurityContextHolder;

import com.allinfinance.univer.system.dictinfo.dto.EntityDictInfoDTO;
import com.huateng.framework.security.model.User;
import com.huateng.framework.util.SystemInfo;

/**
 * 实体数据字典单元格
 * 
 * @author LEO
 */
public class EntityDictInfoCell extends AbstractCell {

    @Override
    protected String getCellValue(TableModel tableModel, Column column) {

        // 通过别名指定实体和数据字典类型
        String dictTypeCode = column.getAlias();
        // 从登录用户获取实体
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String, List<EntityDictInfoDTO>> entityDictInfo = SystemInfo.getEntityDictInfo().get(user.getEntityId());
        List<EntityDictInfoDTO> list = entityDictInfo.get(dictTypeCode);
        String value = column.getValueAsString();
        if (StringUtils.isBlank(value)) {
            return "";
        }
        if (list != null) {
            for (EntityDictInfoDTO d : list) {
                if (value.equals(String.valueOf(d.getDictCode()))) {
                    value = d.getDictShortName();
                    return value;
                }
            }
        }
        return value;
    }

}

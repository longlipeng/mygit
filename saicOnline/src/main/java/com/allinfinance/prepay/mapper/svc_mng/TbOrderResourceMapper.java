package com.allinfinance.prepay.mapper.svc_mng;

import com.allinfinance.prepay.model.TbOrderResource;
import com.allinfinance.prepay.model.TbOrderResourceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbOrderResourceMapper {
    int countByExample(TbOrderResourceExample example);

    int deleteByExample(TbOrderResourceExample example);

    int deleteByPrimaryKey(String orderId);

    int insert(TbOrderResource record);

    int insertSelective(TbOrderResource record);

    List<TbOrderResource> selectByExample(TbOrderResourceExample example);

    TbOrderResource selectByPrimaryKey(String orderId);

    int updateByExampleSelective(@Param("record") TbOrderResource record, @Param("example") TbOrderResourceExample example);

    int updateByExample(@Param("record") TbOrderResource record, @Param("example") TbOrderResourceExample example);

    int updateByPrimaryKeySelective(TbOrderResource record);

    int updateByPrimaryKey(TbOrderResource record);
}
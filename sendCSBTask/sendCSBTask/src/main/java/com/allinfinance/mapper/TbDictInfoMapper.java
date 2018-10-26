package com.allinfinance.mapper;

import com.allinfinance.model.TbDictInfo;
import com.allinfinance.model.TbDictInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbDictInfoMapper {
    int countByExample(TbDictInfoExample example);

    int deleteByExample(TbDictInfoExample example);

    int insert(TbDictInfo record);

    int insertSelective(TbDictInfo record);

    List<TbDictInfo> selectByExample(TbDictInfoExample example);

    int updateByExampleSelective(@Param("record") TbDictInfo record, @Param("example") TbDictInfoExample example);

    int updateByExample(@Param("record") TbDictInfo record, @Param("example") TbDictInfoExample example);
}
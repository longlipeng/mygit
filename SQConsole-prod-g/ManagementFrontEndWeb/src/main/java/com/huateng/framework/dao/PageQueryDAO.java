package com.huateng.framework.dao;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.framework.dto.PageQueryDTO;

public interface PageQueryDAO {

	PageDataDTO query(String statement, Object parameter);

	PageDataDTO query(String statement, PageQueryDTO parameter);

	PageDataDTO query(String statement, PageQueryDTO parameter, boolean count);

}
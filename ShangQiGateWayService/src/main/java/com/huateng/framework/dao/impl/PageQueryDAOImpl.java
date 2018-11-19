package com.huateng.framework.dao.impl;

import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.framework.dto.PageQueryDTO;
import com.huateng.framework.dao.PageQueryDAO;
import com.ibatis.sqlmap.engine.mapping.statement.DefaultRowHandler;

public class PageQueryDAOImpl extends SqlMapClientDaoSupport implements
		PageQueryDAO {

	public PageDataDTO query(String statement, Object parameter) {

		PageDataDTO pageDataDTO = new PageDataDTO();

		return pageDataDTO;
	}

	public PageDataDTO query(String statement, PageQueryDTO parameter) {

		PageQueryDTO pageQuery = parameter;
		if (pageQuery == null) {
			pageQuery = new PageQueryDTO();
			pageQuery.setQueryAll(true);
		}
		return query(statement, pageQuery, pageQuery.isDoCount());
	}

	@SuppressWarnings("unchecked")
	public PageDataDTO query(String statement, PageQueryDTO parameter,
			boolean count) {

		PageQueryDTO pageQuery = parameter;
		if (pageQuery == null) {
			pageQuery = new PageQueryDTO();
			pageQuery.setQueryAll(true);
		}
		PageDataDTO pageDataDTO = new PageDataDTO();

		int totalRecord = 0;
		if (!pageQuery.isQueryAll() && count) {
			parameter.setDoCount(true);
			Map result = (Map) getSqlMapClientTemplate().queryForObject(
					statement, pageQuery);
			Number value = (Number) result.get(PageQueryDTO.COUNT_ALL_NAME);
			totalRecord = value.intValue();
		}
		
		// FIXME 这里并没有判断totalRecord是否等于0,如果等于0，则没有必要继续查询明细记录了。by zerui.wang
		//查找当前页数currentPage如果当前页数>最大页数，设置当前页数为最大页数
		int currentPage = parameter.getCurrentPage();
		int sumPage = ((totalRecord%parameter.getRowsDisplayed()) > 0?1:0) +
				totalRecord/parameter.getRowsDisplayed();
		if(currentPage>sumPage){
			parameter.setCurrentPage(sumPage);
		}
		
		pageQuery.setDoCount(false);
		DefaultRowHandler defaultRowHandler = new DefaultRowHandler();
		getSqlMapClientTemplate().queryWithRowHandler(statement, pageQuery,
				defaultRowHandler);
		pageDataDTO.setData(defaultRowHandler.getList());
		if (pageQuery.isQueryAll())
			totalRecord = defaultRowHandler.getList().size();
		pageDataDTO.setTotalRecord(totalRecord);
		return pageDataDTO;
	}
}

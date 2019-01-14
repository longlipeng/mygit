package com.suning.svc.coupon.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.suning.svc.ibatis.dao.InvoiceRequirementServiceDAO;
import com.suning.svc.ibatis.model.InvoiceRequirement;

public class InvoiceRequirementServiceDAOImpl extends SqlMapClientDaoSupport 
                 implements InvoiceRequirementServiceDAO {
	

	public List<InvoiceRequirement> paginationDisplay(){
		return null;
		
	}

}

package com.huateng.framework.ibatis.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.allinfinance.univer.seller.customer.CustomerDTO;
import com.allinfinance.univer.seller.invoice.dto.OrderInvoiceInfoDTO;
import com.huateng.framework.dao.BaseDAO;
import com.huateng.framework.exception.BizServiceException;

public class OrderInvoiceDAOImpl extends SqlMapClientDaoSupport implements OrderInvoiceDAO {
	Logger logger = Logger.getLogger(OrderInvoiceDAOImpl.class);
	private BaseDAO baseDAO;
	
	/**
	 * 返回所有销售人员的姓名
	 */
	@SuppressWarnings("unchecked")
    public List<CustomerDTO> querySaleManNameByEntityId(CustomerDTO customerDTO) throws  BizServiceException{
		try{
			List<CustomerDTO> customers = new ArrayList<CustomerDTO>();
			customers = baseDAO.queryForList("", customerDTO);
			return customers;
		} catch (Exception e){
			logger.error(e);
			throw new BizServiceException("查询销售人员名单失败!");
		}
	}
	/**
	 * 根据id返回发票和订单信息
	 */
	public OrderInvoiceInfoDTO queryInvoiceInfoById(
			OrderInvoiceInfoDTO orderInvoiceInfoDTO) throws BizServiceException {
		return (OrderInvoiceInfoDTO) getSqlMapClientTemplate().queryForObject("INVOICE.queryInvoiceForUpdate", orderInvoiceInfoDTO);
	}
	
    public BaseDAO getBaseDAO() {
        return baseDAO;
    }
    
    public void setBaseDAO(BaseDAO baseDAO) {
        this.baseDAO = baseDAO;
    }
	

}

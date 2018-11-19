package com.huateng.univer.issuer.account.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.issuer.dto.service.ServiceDTO;
import com.allinfinance.univer.issuer.dto.service.ServiceQueryDTO;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.dao.PageQueryDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.AcctypeContractDAO;
import com.huateng.framework.ibatis.dao.ProductServiceDAO;
import com.huateng.framework.ibatis.dao.ServiceDAO;
import com.huateng.framework.ibatis.model.AcctypeContractExample;
import com.huateng.framework.ibatis.model.ProductServiceExample;
import com.huateng.framework.ibatis.model.Service;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.ReflectionUtil;
import com.huateng.univer.issuer.account.dao.IssuerServiceDao;
import com.huateng.univer.issuer.account.service.AccountService;

public class AccountServiceImpl implements AccountService {

	Logger logger = Logger.getLogger(AccountServiceImpl.class);
	private IssuerServiceDao issuerServiceDAO;

	/**
	 * 服务信息DAO
	 */
	private ServiceDAO serviceDAO;

	/**
	 * 查询分页DAO
	 */
	private PageQueryDAO pageQueryDAO;
	/**
	 * 公共公具DAO
	 * 
	 */
	private CommonsDAO commonsDAO;

	private ProductServiceDAO productServiceDAO;
	private AcctypeContractDAO acctypeContractDAO;

	public AcctypeContractDAO getAcctypeContractDAO() {
		return acctypeContractDAO;
	}

	public void setAcctypeContractDAO(AcctypeContractDAO acctypeContractDAO) {
		this.acctypeContractDAO = acctypeContractDAO;
	}

	public ServiceDAO getServiceDAO() {
		return serviceDAO;
	}

	public void setServiceDAO(ServiceDAO serviceDAO) {
		this.serviceDAO = serviceDAO;
	}

	public ProductServiceDAO getProductServiceDAO() {
		return productServiceDAO;
	}

	public void setProductServiceDAO(ProductServiceDAO productServiceDAO) {
		this.productServiceDAO = productServiceDAO;
	}

	public PageQueryDAO getPageQueryDAO() {
		return pageQueryDAO;
	}

	public void setPageQueryDAO(PageQueryDAO pageQueryDAO) {
		this.pageQueryDAO = pageQueryDAO;
	}

	public CommonsDAO getCommonsDAO() {
		return commonsDAO;
	}

	public void setCommonsDAO(CommonsDAO commonsDAO) {
		this.commonsDAO = commonsDAO;
	}

	public IssuerServiceDao getIssuerServiceDAO() {
		return issuerServiceDAO;
	}

	public void setIssuerServiceDAO(IssuerServiceDao issuerServiceDAO) {
		this.issuerServiceDAO = issuerServiceDAO;
	}

	public void deleteAccType(ServiceDTO accTypeDTO) throws BizServiceException {
		try {
			List<Service> services = new ArrayList<Service>();
			for (ServiceDTO atd : accTypeDTO.getServiceDTOs()) {
				// ProdAcctypeExample example = new ProdAcctypeExample();
				// example.createCriteria().andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL).andAccTypeIdEqualTo(
				// atd.getAccTypeId());
				//
				// if (prodAcctypeDAO.selectByExample(example).size() > 0) {
				// throw new BizServiceException("编号为:" + atd.getAccTypeId() +
				// " 与产品相关联不能被删除!");
				// }
				//
				// MchntAcctypeContractExample mchntExample = new
				// MchntAcctypeContractExample();
				// mchntExample.createCriteria().andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL)
				// .andAccTypeIdEqualTo(atd.getAccTypeId());
				// if
				// (mchntAcctypeContractServiceDAO.selectByExample(mchntExample).size()
				// > 0) {
				// throw new BizServiceException("编号为:" + atd.getAccTypeId() +
				// " 与商户合同相关联不能被删除!");
				// }
				//

				ProductServiceExample example = new ProductServiceExample();
				example.createCriteria()
						.andServiceIdEqualTo(atd.getServiceId());

				if (productServiceDAO.selectByExample(example).size() > 0) {
					throw new BizServiceException("编号为:" + atd.getServiceId()
							+ " 与产品相关联不能被删除!");
				}

				AcctypeContractExample accExample = new AcctypeContractExample();
				accExample.createCriteria().andAcctypeIdEqualTo(
						atd.getServiceId());
				if (acctypeContractDAO.selectByExample(accExample).size() > 0) {
					throw new BizServiceException("编号为:" + atd.getServiceId()
							+ " 与商户合同相关联不能被删除!");
				}

				Service service = serviceDAO.selectByPrimaryKey(atd
						.getServiceId());
				service.setDataState(DataBaseConstant.DATA_STATE_DELETE);
				services.add(service);
			}
			commonsDAO.batchUpdate(
					"TB_SERVICE.abatorgenerated_updateByPrimaryKeySelective",
					services);
		} catch (BizServiceException e) {
			throw e;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("删除账户信息失败~！");
		}
	}

	public ServiceDTO initAccType() throws BizServiceException {
		
		return null;
	}

	public PageDataDTO inqueryAccType(ServiceQueryDTO accTypeQueryDTO)
			throws BizServiceException {

		try {// 查询的时候发行机构为默认的用户所带的发行机构，如果用户在查询时选择发行机构，那么应把默认的发行机构设为null,

			accTypeQueryDTO.setSort("desc");
			accTypeQueryDTO.setSortFieldName("serviceId");
			PageDataDTO pdd = pageQueryDAO.query("SERVICE.selectAccTypetest",
					accTypeQueryDTO);
			return pdd;

		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询信息失败~！");
		}
	}

	public List<ServiceQueryDTO> getIssuerInfoList(ServiceQueryDTO serviceDTO)
			throws BizServiceException {
		try {
			List<ServiceQueryDTO> issuerDTO = issuerServiceDAO
					.getIssuerInfoList(serviceDTO);
			return issuerDTO;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询发行机构失败~！");
		}
	}

	public void insertAccType(ServiceDTO accTypeDTO) throws BizServiceException {
		try {/*
			 * ServiceExample acctypeExample = new ServiceExample();
			 * acctypeExample
			 * .createCriteria().andDataStateEqualTo(DataBaseConstant
			 * .DATA_STATE_NORMAL)
			 * .andServiceNameEqualTo(accTypeDTO.getServiceName());
			 * List<Service> accTypes =
			 * serviceDAO.selectByExample(acctypeExample);
			 * 
			 * if (accTypes.size() > 0) { throw new
			 * BizServiceException(accTypeDTO.getServiceName() + "账户名称已存在"); }
			 */
			Service acctype = new Service();
			ReflectionUtil.copyProperties(accTypeDTO, acctype);
			if (null == acctype.getExpiryDate()
					|| acctype.getExpiryDate().equals("")) {
				acctype.setExpiryDate(DataBaseConstant.DEFAULT_EXPIRY_DATE);
			}
			acctype.setServiceId(commonsDAO
					.getNextValueOfSequence("TB_SERVICE"));
			acctype.setCreateTime(DateUtil.getCurrentTime());
			acctype.setModifyUser(accTypeDTO.getLoginUserId());
			acctype.setModifyTime(DateUtil.getCurrentTime());
			acctype.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
			serviceDAO.insert(acctype);
		} catch (BizServiceException e) {
			throw e;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("添加账户信息失败~！");
		}

	}

	public void modifyStateAccType(ServiceDTO accTypeDTO)
			throws BizServiceException {
		

	}

	public void updateAccType(ServiceDTO accTypeDTO) throws BizServiceException {
		try {
			Service acctype = serviceDAO.selectByPrimaryKey(accTypeDTO
					.getServiceId());
			ReflectionUtil.copyProperties(accTypeDTO, acctype);
			if (null == acctype.getExpiryDate()
					|| acctype.getExpiryDate().equals("")) {
				acctype.setExpiryDate(DataBaseConstant.DEFAULT_EXPIRY_DATE);
			}
			acctype.setCreateTime(DateUtil.getCurrentTime());
			acctype.setModifyTime(DateUtil.getCurrentTime());
			acctype.setModifyUser(accTypeDTO.getLoginUserId());
			acctype.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
			serviceDAO.updateByPrimaryKey(acctype);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("修改账户信息失败~！");
		}

	}

	public ServiceDTO viewAccType(ServiceDTO accTypeDTO)
			throws BizServiceException {
		try {

			Service acctype = serviceDAO.selectByPrimaryKey(accTypeDTO
					.getServiceId());
			ServiceDTO acctypeDto = new ServiceDTO();
			ReflectionUtil.copyProperties(acctype, acctypeDto);
			return acctypeDto;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("获取账户信息失败~！");
		}
	}

	public ServiceDTO EditViewAccType(ServiceDTO accTypeDTO)
			throws BizServiceException {
		try {
			ProductServiceExample example = new ProductServiceExample();
			example.createCriteria().andServiceIdEqualTo(
					accTypeDTO.getServiceId());
			if (productServiceDAO.selectByExample(example).size() > 0) {
				throw new BizServiceException("编号为:"
						+ accTypeDTO.getServiceId() + " 与产品相关联不能被编辑!");
			}
			AcctypeContractExample accExample = new AcctypeContractExample();
			accExample.createCriteria().andAcctypeIdEqualTo(
					accTypeDTO.getServiceId());
			if (acctypeContractDAO.selectByExample(accExample).size() > 0) {
				throw new BizServiceException("编号为:"
						+ accTypeDTO.getServiceId() + " 与商户合同相关联不能被编辑!");
			}
			Service acctype = serviceDAO.selectByPrimaryKey(accTypeDTO
					.getServiceId());
			ServiceDTO acctypeDto = new ServiceDTO();
			ReflectionUtil.copyProperties(acctype, acctypeDto);
			return acctypeDto;
		} catch (BizServiceException e) {
			throw e;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("获取账户信息失败~！");
		}
	}
}

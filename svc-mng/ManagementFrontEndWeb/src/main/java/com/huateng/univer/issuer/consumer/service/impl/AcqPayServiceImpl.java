package com.huateng.univer.issuer.consumer.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.entity.dto.BankDTO;
import com.allinfinance.univer.issuer.dto.consumer.AcqPayDTO;
import com.allinfinance.univer.issuer.dto.consumer.ConsumerQueryDTO;
import com.allinfinance.univer.issuer.dto.product.ProductDTO;
import com.huateng.framework.dao.BaseDAO;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.dao.PageQueryDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.ReflectionUtil;
import com.huateng.univer.issuer.consumer.dao.AcqPayDAO;
import com.huateng.univer.issuer.consumer.service.AcqPayService;

public class AcqPayServiceImpl implements AcqPayService {
	Logger logger = Logger.getLogger(ConsumerServiceImpl.class);
	private PageDataDTO pageDataDTO = new PageDataDTO();
	private PageQueryDAO pageQueryDAO;
	private BaseDAO baseDAO;
	private CommonsDAO commonsDAO;
	private AcqPayDAO acqPayDAO;

	public PageDataDTO inquery(ConsumerQueryDTO consumerQueryDTO)
			throws BizServiceException {
		try {
			consumerQueryDTO.setSort("desc");
			consumerQueryDTO.setSortFieldName("entityId");
			PageDataDTO pdd = pageQueryDAO.query("CONSUMER.selectAcqPay",
					consumerQueryDTO);
			return pdd;
		} catch (Exception e) {
			
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询收单网关信息失败~！");
		}

	}

	public PageDataDTO choose(ConsumerQueryDTO consumerQueryDTO)
			throws BizServiceException {
		try {
			consumerQueryDTO.setSort("desc");
			consumerQueryDTO.setSortFieldName("entityId");
			PageDataDTO pdd = pageQueryDAO.query("CONSUMER.choose",
					consumerQueryDTO);
			return pdd;
		} catch (Exception e) {
			
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询收单机构信息失败~！");
		}

	}

	public List<ProductDTO> selectProd(AcqPayDTO acqPayDTO)
			throws BizServiceException {
		try {
			List<ProductDTO> productDTOs = (List<ProductDTO>) baseDAO
					.queryForList("CONSUMER.selectProd", acqPayDTO);
			return productDTOs;
		} catch (Exception e) {
			
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询收单机构信息失败~！");
		}

	}

	public List<BankDTO> selectBank(AcqPayDTO acqPayDTO)
			throws BizServiceException {
		List<BankDTO> bankDTOs = (List<BankDTO>) baseDAO.queryForList(
				"CONSUMER.selectBank", acqPayDTO);
		return bankDTOs;
	}

	public PageDataDTO chooseBank(ConsumerQueryDTO consumerQueryDTO)
			throws BizServiceException {
		PageDataDTO pageDataDTO = new PageDataDTO();
		try {
			pageDataDTO = pageQueryDAO.query("CONSUMER.chooseBank",
					consumerQueryDTO);
		} catch (Exception e) {
			
			this.logger.error(e.getMessage());
		}
		return pageDataDTO;
	}

	public void addBank(AcqPayDTO acqPayDTO) throws BizServiceException {
		try {
			acqPayDTO.setCrtTs(DateUtil.getCurrentDateStr());
			acqPayDTO.setUpdTs(DateUtil.getCurrentDateStr());
			// 支付渠道
			acqPayDTO.setSeqId(commonsDAO
					.getNextValueOfSequenceBySequence("SEQ_ACQ_PAY_CHNL_CFG"));
			baseDAO.insert("CONSUMER.insertPayChnl", acqPayDTO);
		} catch (Exception e) {
			
			this.logger.error(e.getMessage());
		}
	}

	public void delBank(AcqPayDTO acqPayDTO) throws BizServiceException {
		try {
			acqPayDAO.delBank(acqPayDTO);
		} catch (Exception e) {
			
			this.logger.error(e.getMessage());
		}
	}

	public void insert(AcqPayDTO acqPayDTO) throws BizServiceException {
		List<AcqPayDTO> acqPayDTOs = new ArrayList<AcqPayDTO>();
		acqPayDTO.setCrtTs(DateUtil.getCurrentDateStr());
		acqPayDTO.setUpdTs(DateUtil.getCurrentDateStr());
		// 收单证书
		try {
			acqPayDTO.setSeqId(commonsDAO
					.getNextValueOfSequenceBySequence("SEQ_ACQ_CERT_INF"));
			baseDAO.insert("CONSUMER.insertCert", acqPayDTO);
			// 短信网关
			acqPayDTO.setSeqId(commonsDAO
					.getNextValueOfSequenceBySequence("SEQ_ACQ_SVR_CMS_CFG"));
			baseDAO.insert("CONSUMER.insertCmsCfg", acqPayDTO);
			// 系统配置
			acqPayDTO.setSeqId(commonsDAO
					.getNextValueOfSequenceBySequence("SEQ_ACQ_SYS_PARA"));
			baseDAO.insert("CONSUMER.insertSysPara", acqPayDTO);
			// 卡片关联产品
			if (null != acqPayDTO.getProductDTOs()
					&& acqPayDTO.getProductDTOs().size() > 0) {
				for (int i = 0; i < acqPayDTO.getProductDTOs().size(); i++) {
					AcqPayDTO a = new AcqPayDTO();
					ReflectionUtil.copyProperties(acqPayDTO, a);
					a
							.setSeqId(commonsDAO
									.getNextValueOfSequenceBySequence("SEQ_ACQ_CARDPRODUCT_RLT"));
					logger.info(a.getSeqId());
					a.setProductId(acqPayDTO.getProductDTOs().get(i)
							.getProductId());
					acqPayDTOs.add(a);
				}
				baseDAO
						.batchInsert("CONSUMER", "insertCardproduct",
								acqPayDTOs);
			} else {
				// throw new BizServiceException("ss");
			}
		} catch (BizServiceException b) {
			throw new BizServiceException("新增收单网关配置失败！！");
		} catch (Exception e) {
			
			this.logger.error(e.getMessage());
		}

	}

	public AcqPayDTO reEdit(AcqPayDTO acqPayDTO) throws BizServiceException {
		try {
			acqPayDTO = (AcqPayDTO) baseDAO.queryForObject(
					"CONSUMER.editAcqPay", acqPayDTO);
			int certi = acqPayDTO.getCerts().lastIndexOf("_");
			int certa = acqPayDTO.getCerts().lastIndexOf("/");
			int certb = acqPayDTO.getCerts().lastIndexOf(".");
			acqPayDTO.setCerts(acqPayDTO.getCerts().substring(certa + 1, certi)
					+ acqPayDTO.getCerts().substring(certb));
			int logob = acqPayDTO.getLogo().lastIndexOf(".");
			int logoi = acqPayDTO.getLogo().lastIndexOf("_");
			acqPayDTO.setLogo(acqPayDTO.getLogo().substring(0, logoi)
					+ acqPayDTO.getLogo().substring(logob));
			List<ProductDTO> productDTOs = selectProd(acqPayDTO);
			if (productDTOs != null && productDTOs.size() > 0) {
				acqPayDTO.setProductDTOs(productDTOs);
			}
			List<BankDTO> banks = selectBank(acqPayDTO);
			if (banks != null && banks.size() > 0) {
				acqPayDTO.setBanks(banks);
			}
		} catch (BizServiceException b) {
			throw new BizServiceException("查询收单网关配置失败！！");
		} catch (Exception e) {
			
			this.logger.error(e.getMessage());
		}
		return acqPayDTO;
	}

	public void edit(AcqPayDTO acqPayDTO) throws BizServiceException {
		try {
			acqPayDTO.setUpdTs(DateUtil.getCurrentDateStr());
			acqPayDAO.deleteCardProduct(acqPayDTO);
			baseDAO.update("CONSUMER.updateSysPara", acqPayDTO);
			baseDAO.update("CONSUMER.updateCert", acqPayDTO);
			baseDAO.update("CONSUMER.updateCms", acqPayDTO);

			List<ProductDTO> productDTOs = selectProd(acqPayDTO);
			List<AcqPayDTO> acqPayDTOs = new ArrayList<AcqPayDTO>();
			acqPayDTO.setCrtTs(DateUtil.getCurrentDateStr());
			if (productDTOs != null && productDTOs.size() > 0) {
				acqPayDTO.setProductDTOs(productDTOs);
				for (int i = 0; i < acqPayDTO.getProductDTOs().size(); i++) {
					AcqPayDTO a = new AcqPayDTO();
					ReflectionUtil.copyProperties(acqPayDTO, a);
					a
							.setSeqId(commonsDAO
									.getNextValueOfSequenceBySequence("SEQ_ACQ_CARDPRODUCT_RLT"));
					logger.info("Sequence " + a.getSeqId());
					a.setProductId(acqPayDTO.getProductDTOs().get(i)
							.getProductId());
					logger.info("productId " + a.getProductId());
					acqPayDTOs.add(a);
				}
				baseDAO
						.batchInsert("CONSUMER", "insertCardproduct",
								acqPayDTOs);
			}

		} catch (Exception e) {
			
			this.logger.error(e.getMessage());
		}

	}

	public AcqPayDTO view(AcqPayDTO acqPayDTO) throws BizServiceException {
		return acqPayDTO;

	}

	public void delete(AcqPayDTO acqPayDTO) throws BizServiceException {
		try {
			acqPayDAO.delete(acqPayDTO);
		} catch (Exception e) {
			
			this.logger.error(e.getMessage());
		}
	}

	public PageDataDTO getPageDataDTO() {
		return pageDataDTO;
	}

	public void setPageDataDTO(PageDataDTO pageDataDTO) {
		this.pageDataDTO = pageDataDTO;
	}

	public PageQueryDAO getPageQueryDAO() {
		return pageQueryDAO;
	}

	public void setPageQueryDAO(PageQueryDAO pageQueryDAO) {
		this.pageQueryDAO = pageQueryDAO;
	}

	public BaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	public CommonsDAO getCommonsDAO() {
		return commonsDAO;
	}

	public void setCommonsDAO(CommonsDAO commonsDAO) {
		this.commonsDAO = commonsDAO;
	}

	public AcqPayDAO getAcqPayDAO() {
		return acqPayDAO;
	}

	public void setAcqPayDAO(AcqPayDAO acqPayDAO) {
		this.acqPayDAO = acqPayDAO;
	}

}

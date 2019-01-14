package com.huateng.univer.servicefeerule.biz.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.servicefeerule.dto.CaclDspDTO;
import com.allinfinance.univer.servicefeerule.dto.CaclInfDTO;
import com.allinfinance.univer.servicefeerule.dto.CaclInfQueryDTO;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.dao.PageQueryDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.CaclDspDAO;
import com.huateng.framework.ibatis.dao.CaclInfDAO;
import com.huateng.framework.ibatis.dao.ConsumerDAO;
import com.huateng.framework.ibatis.dao.IssuerDAO;
import com.huateng.framework.ibatis.dao.SellerDAO;
import com.huateng.framework.ibatis.model.CaclDsp;
import com.huateng.framework.ibatis.model.CaclDspExample;
import com.huateng.framework.ibatis.model.CaclDspKey;
import com.huateng.framework.ibatis.model.CaclInf;
import com.huateng.framework.ibatis.model.CaclInfExample;
import com.huateng.framework.ibatis.model.Consumer;
import com.huateng.framework.ibatis.model.ConsumerExample;
import com.huateng.framework.ibatis.model.Issuer;
import com.huateng.framework.ibatis.model.IssuerExample;
import com.huateng.framework.ibatis.model.Seller;
import com.huateng.framework.ibatis.model.SellerExample;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.ReflectionUtil;
import com.huateng.framework.util.StringUtils;
import com.huateng.univer.servicefeerule.biz.service.CaclInfService;

public class CaclInfServiceImpl implements CaclInfService {
	Logger logger = Logger.getLogger(CaclInfServiceImpl.class);
	private PageDataDTO pageDataDTO;
	private CommonsDAO commonsDAO;
	private PageQueryDAO pageQueryDAO;
	private CaclInf caclInf = new CaclInf();
	private CaclInfDAO caclInfDAO;
	private CaclDspDAO caclDspDAO;

	// private CaclInfServiceDAO caclInfServiceDAO;
	private CaclDsp caclDsp = new CaclDsp();
	private CaclDspDTO caclDspDTO = new CaclDspDTO();
	private CaclInfQueryDTO caclInfQueryDTO = new CaclInfQueryDTO();
	private CaclInfDTO caclInfDTO = new CaclInfDTO();
	private IssuerDAO issuerDAO;
	private ConsumerDAO consumerDAO;
	private SellerDAO sellerDAO;

	public IssuerDAO getIssuerDAO() {
		return issuerDAO;
	}

	public void setIssuerDAO(IssuerDAO issuerDAO) {
		this.issuerDAO = issuerDAO;
	}

	public ConsumerDAO getConsumerDAO() {
		return consumerDAO;
	}

	public void setConsumerDAO(ConsumerDAO consumerDAO) {
		this.consumerDAO = consumerDAO;
	}

	public SellerDAO getSellerDAO() {
		return sellerDAO;
	}

	public void setSellerDAO(SellerDAO sellerDAO) {
		this.sellerDAO = sellerDAO;
	}

	public PageDataDTO getPageDataDTO() {
		return pageDataDTO;
	}

	public void setPageDataDTO(PageDataDTO pageDataDTO) {
		this.pageDataDTO = pageDataDTO;
	}

	public CommonsDAO getCommonsDAO() {
		return commonsDAO;
	}

	public void setCommonsDAO(CommonsDAO commonsDAO) {
		this.commonsDAO = commonsDAO;
	}

	public PageQueryDAO getPageQueryDAO() {
		return pageQueryDAO;
	}

	public void setPageQueryDAO(PageQueryDAO pageQueryDAO) {
		this.pageQueryDAO = pageQueryDAO;
	}

	public CaclInf getCaclInf() {
		return caclInf;
	}

	public void setCaclInf(CaclInf caclInf) {
		this.caclInf = caclInf;
	}

	public CaclInfDAO getCaclInfDAO() {
		return caclInfDAO;
	}

	public void setCaclInfDAO(CaclInfDAO caclInfDAO) {
		this.caclInfDAO = caclInfDAO;
	}

	public void delete(CaclInfDTO dto) throws BizServiceException {
		try {

			CaclInfExample caclInfexample = new CaclInfExample();
			caclInf.setDiscCd(dto.getDiscCd());
			caclInfexample.createCriteria().andDiscCdEqualTo(
					caclInf.getDiscCd());
			caclInfDAO.deleteByExample(caclInfexample);
		} catch (Exception e) {
			
			this.logger.error(e.getMessage());
			throw new BizServiceException("删除计算规则失败！");
		}

	}

	public CaclDspDTO insert(CaclDspDTO dto) throws BizServiceException {
		try {
			String discCd = commonsDAO.getNextValueOfSequence("TBL_CACL_DSP");
			logger.debug(discCd);
			dto.setDiscCd(discCd);
			CaclDspKey caclDspKey=new CaclDspKey();
			caclDspKey.setDiscCd(discCd);
			caclDspKey.setRecUpdUsrId(dto.getDefaultEntityId());
			
			caclDsp=caclDspDAO.selectByPrimaryKey(caclDspKey);
			if (caclDsp != null) {
				throw new BizServiceException("计算规则已存在！");
			}

			/*
			 * CaclDspExample caclDspExample=new CaclDspExample();
			 * caclDspExample
			 * .createCriteria().andCaclNameEqualTo(dto.getCaclName());
			 * List<CaclDsp>
			 * lstCaclDsps=caclDspDAO.selectByExample(caclDspExample);
			 * 
			 * if(null !=lstCaclDsps && lstCaclDsps.size()>0){ throw new
			 * BizServiceException("已经存在名称为:"+dto.getCaclName()+"的计算规则！"); }
			 */

			if (dto != null) {
				caclDsp = new CaclDsp();
				ReflectionUtil.copyProperties(dto, caclDsp);
				caclDspDAO.insert(caclDsp);
				ReflectionUtil.copyProperties(caclDsp, dto);
			}

		} catch (BizServiceException b) {
			throw b;
		} catch (Exception e) {
			
			this.logger.error(e.getMessage());
			throw new BizServiceException("添加计算规则失败！");
		}
		return dto;

	}

	public CaclInfDTO insertInf(CaclInfDTO dto) throws BizServiceException {
		try {
			if (dto != null) {
				caclInf = new CaclInf();
				logger.debug(dto.getDiscCd());
				dto.setRecCrtTs(DateUtil.getCurrentDate());
				dto.setRecUpdTs(DateUtil.getCurrentDate());
				ReflectionUtil.copyProperties(dto, caclInf);
				caclInfDAO.insert(caclInf);
				ReflectionUtil.copyProperties(caclInf, dto);
			}

		} catch (Exception e) {
			
			this.logger.error(e.getMessage());
			throw new BizServiceException("添加计算规则失败！");
		}
		return dto;

	}

	public PageDataDTO query(CaclInfQueryDTO dto) throws BizServiceException {
		try {
			dto.setRecUpdUsrId(StringUtils.rpad(dto.getRecUpdUsrId(),8," "));
			pageDataDTO = pageQueryDAO.query("CACLDSP.selectCaclDspMap", dto);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询计算规则失败！");
		}
		return pageDataDTO;
	}

	public void update(CaclInfQueryDTO dto) throws BizServiceException {
		try {
			// caclInfQueryDTO=queryBydiscCd(dto.getDiscCd());
			if (dto.getCaclInfDTO() != null) {
				ReflectionUtil.copyProperties(dto.getCaclInfDTO(), caclInf);
				caclInfDAO.updateByPrimaryKey(caclInf);
			}
			if (dto.getCaclDspDTO() != null) {
				ReflectionUtil.copyProperties(dto.getCaclDspDTO(), caclDsp);
				//机构号不足八位，补足八位
				String entityId = StringUtils.rpad(dto.getDefaultEntityId(),8," ");
				CaclDspExample dspExample = new CaclDspExample();
				dspExample.createCriteria().andDiscCdEqualTo(dto.getDiscCd())
						.andRecUpdUsrIdEqualTo(entityId);
				caclDspDAO.updateByExampleSelective(caclDsp, dspExample);
			}
		} catch (Exception e) {
			
			this.logger.error(e.getMessage());
			throw new BizServiceException("修改计算规则失败！");
		}

	}

	// 判断用户是哪个机构
	public int getByEntityId(String entityId) throws BizServiceException {
		// -5 收单 -6发卡 -7营销
		// 商业管理平台必须加入
		// TenantExample example0 = new TenantExample();
		// example0.createCriteria().andEntityIdEqualTo(entityId)
		// .andDataStateEqualTo("1");
		// List<Tenant> tenantList = tennatDAO.selectByExample(example0);
		// if (tenantList != null && tenantList.size() > 0) {
		// functionRoleId.add("1");
		// }
		IssuerExample example1 = new IssuerExample();
		example1.createCriteria().andEntityIdEqualTo(entityId)
				.andDataStateEqualTo("1");
		List<Issuer> issuerList = issuerDAO.selectByExample(example1);
		if (issuerList != null && issuerList.size() > 0) {
			return -6;
		}
		SellerExample example2 = new SellerExample();
		example2.createCriteria().andEntityIdEqualTo(entityId)
				.andDataStateEqualTo("1");
		List<Seller> sellerList = sellerDAO.selectByExample(example2);
		if (sellerList != null && sellerList.size() > 0) {
			return -7;
		}
		ConsumerExample example3 = new ConsumerExample();
		example3.createCriteria().andEntityIdEqualTo(entityId)
				.andDataStateEqualTo("1");
		List<Consumer> consumerList = consumerDAO.selectByExample(example3);
		if (consumerList != null && consumerList.size() > 0) {
			return -5;
		}

		return 0;
	}

	public String isHave(String name) throws BizServiceException {
		CaclDspExample example = new CaclDspExample();
		List<CaclDsp> caclDsps = new ArrayList<CaclDsp>();
		example.createCriteria().andCaclNameEqualTo(name)
				.andDataStatNotEqualTo("0");
		caclDsps = caclDspDAO.selectByExample(example);
		if (null == caclDsps) {
			throw new BizServiceException("查询失败");
		} else {
			if (caclDsps.size() > 0) {
				return "1";
			}
			return "0";
		}

	}

	public CaclInfQueryDTO queryBydiscCd(CaclInfQueryDTO caclInfQueryDTO)
			throws BizServiceException {

		List<CaclInf> list = new ArrayList<CaclInf>();
		List<CaclInfDTO> listDTO = new ArrayList<CaclInfDTO>();
		CaclInfExample example = new CaclInfExample();

		try {
			example.createCriteria().andDiscCdEqualTo(
					caclInfQueryDTO.getDiscCd());
			example.setOrderByClause("STEP_NO");
			list = caclInfDAO.selectByExample(example);
			if (null != list && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					caclInf = list.get(i);
					logger.debug("caclinf:" + caclInf);
					caclInfDTO = new CaclInfDTO();
					ReflectionUtil.copyProperties(caclInf, caclInfDTO);
					listDTO.add(caclInfDTO);
				}
			}
			caclInfQueryDTO.setCaclInfDTOs(listDTO);
			//机构号不足八位，补足八位
			String entityId = StringUtils.rpad(caclInfQueryDTO.getDefaultEntityId(),8," ");
			CaclDspExample dspExample = new CaclDspExample();
			dspExample.createCriteria().andDiscCdEqualTo(
					caclInfQueryDTO.getDiscCd()).andRecUpdUsrIdEqualTo(
							entityId);
			List<CaclDsp> caclDsps = caclDspDAO.selectByExample(dspExample);
			if (null != caclDsps && caclDsps.size() > 0) {
				ReflectionUtil.copyProperties(caclDsps.get(0), caclDspDTO);

				caclInfQueryDTO.setCaclDspDTO(caclDspDTO);
			}
		} catch (Exception e) {
			
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询计算规则失败！");
		}
		return caclInfQueryDTO;
	}

	/**
	 * 添加计算规则初始数据
	 * 
	 * 规则名称：标准比例1，计算起始金额：0，计算截止金额：9999999999.99，手续费下限：0，手续费上限100000000，费率计算数：100
	 * ； 规则名称：标准比例2，计算起始金额：0，计算截止金额：9999999999.99，手续费下限：0，手续费上限200000000，费率计算数：
	 * 200；
	 * 规则名称：标准比例3，计算起始金额：0，计算截止金额：9999999999.99，手续费下限：0，手续费上限300000000，费率计算数
	 * ：300；；
	 * 规则名称：标准比例4，计算起始金额：0，计算截止金额：9999999999.99，手续费下限：0，手续费上限400000000，费率计算数
	 * ：400；；
	 * 规则名称：标准比例5，计算起始金额：0，计算截止金额：9999999999.99，手续费下限：5，手续费上限500000000，费率计算数
	 * ：500。
	 */

	public void insertInit(String userId) throws BizServiceException {
		for (int i = 1; i <= 5; i++) {
			// 添加 tbl_cacl_dsp
			caclInfQueryDTO.getCaclDspDTO().setDiscCd("0000" + i);
			caclInfQueryDTO.getCaclDspDTO().setCaclName("标准比例" + i);
			caclInfQueryDTO.getCaclDspDTO().setDataStat("2");
			caclInfQueryDTO.getCaclDspDTO().setRecUpdUsrId(userId);
			caclInfQueryDTO.getCaclDspDTO().setRecCrtTs(
					DateUtil.getCurrentDate());
			caclInfQueryDTO.getCaclDspDTO().setRecUpdTs(
					DateUtil.getCurrentDate());
			caclDspDTO = caclInfQueryDTO.getCaclDspDTO();
			logger.info("cacldsp:" + caclDspDTO);
			caclDsp = new CaclDsp();
			ReflectionUtil.copyProperties(caclDspDTO, caclDsp);
			caclDspDAO.insert(caclDsp);
		}
	}

	// public List<CaclDspDTO> getCaclDspDTO(CaclInfQueryDTO dto){
	// List<CaclDspDTO> list = new ArrayList<CaclDspDTO>();
	// try {
	// list = caclInfServiceDAO.queryForList(dto);
	// } catch (Exception e) {
	// 
	// this.logger.error(e.getMessage());
	// }
	// return list;
	// }
	public CaclDspDAO getCaclDspDAO() {
		return caclDspDAO;
	}

	public void setCaclDspDAO(CaclDspDAO caclDspDAO) {
		this.caclDspDAO = caclDspDAO;
	}

	public CaclDsp getCaclDsp() {
		return caclDsp;
	}

	public void setCaclDsp(CaclDsp caclDsp) {
		this.caclDsp = caclDsp;
	}

	public CaclDspDTO getCaclDspDTO() {
		return caclDspDTO;
	}

	public void setCaclDspDTO(CaclDspDTO caclDspDTO) {
		this.caclDspDTO = caclDspDTO;
	}

	public CaclInfQueryDTO getCaclInfQueryDTO() {
		return caclInfQueryDTO;
	}

	public void setCaclInfQueryDTO(CaclInfQueryDTO caclInfQueryDTO) {
		this.caclInfQueryDTO = caclInfQueryDTO;
	}

	public CaclInfDTO getCaclInfDTO() {
		return caclInfDTO;
	}

	public void setCaclInfDTO(CaclInfDTO caclInfDTO) {
		this.caclInfDTO = caclInfDTO;
	}

	public CaclInfDTO insertInfo(CaclInfDTO dto) throws BizServiceException {

		List<String> serviceFeeRuleDTORuleStep = dto
				.getServiceFeeRuleDTORuleStep();
		List<String> serviceFeeRuleDTORateType = dto
				.getServiceFeeRuleDTORateType();
		List<String> serviceFeeRuleDTORateValue = dto
				.getServiceFeeRuleDTORateValue();
		List<String> serviceFeeRuleDTOFeeMin = dto.getServiceFeeRuleDTOFeeMin();
		List<String> serviceFeeRuleDTOFeeMax = dto.getServiceFeeRuleDTOFeeMax();
		List<String> serviceFeeRuleDTOAmountStart = dto
				.getServiceFeeRuleDTOAmountStart();
		List<String> serviceFeeRuleDTOAmountEnd = dto
				.getServiceFeeRuleDTOAmountEnd();
		String disccd = dto.getDiscCd();
		String userId = dto.getRecUpdUsrId();
		int size = serviceFeeRuleDTORuleStep.size(); // 3
		long splitProfit = dto.getSplitProfit();
		logger.debug("本金范围大小：" + size);
		for (int i = 1; i < serviceFeeRuleDTORuleStep.size() + 1; i++) {
			logger.debug("本金范围1");
			caclInfDTO.setStepNo(i - 1); // 0
			caclInfDTO.setOperRslt(++size); // 4
			logger.info(size);

			caclInfDTO.setOperand1(String.valueOf(Double
					.parseDouble(serviceFeeRuleDTOAmountStart.get(i)) * 100));

			logger.debug(i + ". start:" + serviceFeeRuleDTOAmountStart);
			caclInfDTO.setOperator1("L");
			caclInfDTO.setOperand2(Long.parseLong("-1"));
			caclInfDTO.setOperator2("L");

			caclInfDTO.setOperand3(String.valueOf(Double
					.parseDouble(serviceFeeRuleDTOAmountEnd.get(i)) * 100));

			logger.debug(i + ". end:" + serviceFeeRuleDTOAmountEnd);

			caclInfDTO.setDiscCd(disccd);
			caclInfDTO.setRecUpdUsrId(userId);
			this.insertInf(caclInfDTO);

			logger.debug("手续费:" + serviceFeeRuleDTORateValue);
			caclInfDTO.setStepNo(size); // 4
			logger.info(size);
			caclInfDTO.setOperRslt(-10);
			caclInfDTO.setOperand1("-1");
			caclInfDTO.setOperator1("*");

			caclInfDTO.setOperand2(Long.parseLong(serviceFeeRuleDTORateValue
					.get(i)));

			caclInfDTO.setOperator2("/");
			logger.info("type:" + serviceFeeRuleDTORateType.get(i));

			caclInfDTO.setOperand3(serviceFeeRuleDTORateType.get(i));

			caclInfDTO.setDiscCd(disccd);
			caclInfDTO.setRecUpdUsrId(userId);
			this.insertInf(caclInfDTO);
			logger.debug("手续费 最小值" + serviceFeeRuleDTOFeeMin);
			if (serviceFeeRuleDTOFeeMin.get(i) != null) {
				caclInfDTO.setStepNo(++size); // 5
				caclInfDTO.setOperRslt(-10);

				caclInfDTO.setOperand1(String.valueOf(Double
						.parseDouble(serviceFeeRuleDTOFeeMin.get(i)) * 100));

				caclInfDTO.setOperator1("<");
				caclInfDTO.setOperand2(Long.parseLong("-10"));
				caclInfDTO.setOperator2("<");

				caclInfDTO.setOperand3(String.valueOf(Double
						.parseDouble(serviceFeeRuleDTOFeeMin.get(i)) * 100));

				caclInfDTO.setDiscCd(disccd);
				caclInfDTO.setRecUpdUsrId(userId);
				this.insertInf(caclInfDTO);
			}
			logger.debug("手续费 最小值" + serviceFeeRuleDTOFeeMax);
			if (serviceFeeRuleDTOFeeMax.get(i) != null) {
				caclInfDTO.setStepNo(++size); // 6
				caclInfDTO.setOperRslt(-10);
				caclInfDTO.setOperand1(String.valueOf(Double
						.parseDouble(serviceFeeRuleDTOFeeMax.get(i)) * 100));
				caclInfDTO.setOperator1(">");
				caclInfDTO.setOperand2(Long.parseLong("-10"));
				caclInfDTO.setOperator2(">");
				caclInfDTO.setOperand3(String.valueOf(Double
						.parseDouble(serviceFeeRuleDTOFeeMax.get(i)) * 100));
				caclInfDTO.setDiscCd(disccd);
				caclInfDTO.setRecUpdUsrId(userId);
				this.insertInf(caclInfDTO);
			}
			logger.debug("splitProfit");
			caclInfDTO.setStepNo(++size);
			caclInfDTO.setOperRslt(this.getByEntityId(userId));
			caclInfDTO.setOperand1("-10");
			caclInfDTO.setOperator1("*");
			caclInfDTO.setOperand2(splitProfit);

			caclInfDTO.setOperator2("/");
			caclInfDTO.setOperand3("10000");
			caclInfDTO.setDiscCd(disccd);
			caclInfDTO.setRecUpdUsrId(userId);
			this.insertInf(caclInfDTO);
			logger.debug("00");
			caclInfDTO.setStepNo(++size); // 7
			caclInfDTO.setOperRslt(0);
			caclInfDTO.setOperand1("0");
			caclInfDTO.setOperator1("$");
			caclInfDTO.setOperand2(Long.parseLong("0"));
			caclInfDTO.setOperator2("0");
			caclInfDTO.setOperand3("0");
			caclInfDTO.setDiscCd(disccd);
			caclInfDTO.setRecUpdUsrId(userId);
			this.insertInf(caclInfDTO);

		}
		caclInfDTO.setStepNo(serviceFeeRuleDTORuleStep.size());
		caclInfDTO.setOperRslt(0);
		caclInfDTO.setOperand1("0");
		caclInfDTO.setOperator1("$");
		caclInfDTO.setOperand2(Long.parseLong("0"));
		caclInfDTO.setOperator2("0");
		caclInfDTO.setOperand3("0");
		caclInfDTO.setDiscCd(disccd);
		caclInfDTO.setRecUpdUsrId(userId);
		this.insertInf(caclInfDTO);
		return null;
	}

	// public CaclInfServiceDAO getCaclInfServiceDAO() {
	// return caclInfServiceDAO;
	// }
	//
	// public void setCaclInfServiceDAO(CaclInfServiceDAO caclInfServiceDAO) {
	// this.caclInfServiceDAO = caclInfServiceDAO;
	// }

}

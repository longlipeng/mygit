package com.huateng.univer.consumer.refund.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.consumer.merchant.dto.MerchantDTO;
import com.allinfinance.univer.refund.dto.RefundDTO;
import com.allinfinance.univer.refund.dto.RefundQueryDTO;
import com.huateng.framework.dao.BaseDAO;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.dao.PageQueryDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.MerchantDAO;
import com.huateng.framework.ibatis.dao.RefundInfDAO;
import com.huateng.framework.ibatis.model.Merchant;
import com.huateng.framework.ibatis.model.MerchantExample;
import com.huateng.framework.util.Amount;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.ReflectionUtil;
import com.huateng.hstserver.frameworkUtil.StringUtils;
import com.huateng.hstserver.gatewayService.Java2TXNBusinessServiceImpl;
import com.huateng.hstserver.model.TxnPackageDTO;
import com.huateng.univer.consumer.refund.RefundService;

public class RefundServiceImpl implements RefundService {

	private Logger logger = Logger.getLogger(RefundServiceImpl.class);

	private RefundInfDAO refundDAO;

	private PageQueryDAO pageQueryDAO;

	private MerchantDAO merchantDAO;

	private CommonsDAO commonsDAO;

	private BaseDAO baseDAO;
	
	private Java2TXNBusinessServiceImpl java2TXNBusinessService;


	public Java2TXNBusinessServiceImpl getJava2TXNBusinessService() {
		return java2TXNBusinessService;
	}

	public void setJava2TXNBusinessService(
			Java2TXNBusinessServiceImpl java2txnBusinessService) {
		java2TXNBusinessService = java2txnBusinessService;
	}

	// 错误码描述集合
	public static Map<String, String> codeSet;

	static {
		codeSet = new HashMap<String, String>();
		codeSet.put("00", "操作成功");
		codeSet.put("12", "商户或收单机构错误");
		codeSet.put("14", "无效卡号");
		codeSet.put("16", "卡未激活");
		codeSet.put("22", "原交易没有成功");
		codeSet.put("25", "未找到原交易");
		codeSet.put("54", "卡已过有效期");
		codeSet.put("55", "密码错误");
		codeSet.put("64", "金额错误");
		codeSet.put("74", "CVV2错误");
		codeSet.put("96", "后台系统错误");
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

	public MerchantDAO getMerchantDAO() {
		return merchantDAO;
	}

	public void setMerchantDAO(MerchantDAO merchantDAO) {
		this.merchantDAO = merchantDAO;
	}

	public PageQueryDAO getPageQueryDAO() {
		return pageQueryDAO;
	}

	public void setPageQueryDAO(PageQueryDAO pageQueryDAO) {
		this.pageQueryDAO = pageQueryDAO;
	}

	public RefundInfDAO getRefundDAO() {
		return refundDAO;
	}

	public void setRefundDAO(RefundInfDAO refundDAO) {
		this.refundDAO = refundDAO;
	}

	public PageDataDTO refundList(RefundQueryDTO dto)
			throws BizServiceException {
		
		PageDataDTO res;

		try {
			res = pageQueryDAO.query("REFUND.refundList", dto);
			if (res.getTotalRecord() > 0) {
				List dataList = res.getData();
				// 将ext_rsp_code翻译
				for(int i=0; i<dataList.size();i++){
					Map m = (Map) dataList.get(i);
					String code = (String) m.get("RESPCODE");
					if (code != null && code.trim().length() > 0){
						m.put("RESPCODE", getRespCodeDesc(code));
						dataList.set(i, m);						
					}					
				}
				res.setData(dataList);
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询退货信息失败！");
		}
		return res;
	}

	public RefundDTO refundDetail(RefundDTO refundDTO) throws BizServiceException {
		
		try {
			//检查是否有该交易
			refundDTO = checkTxn(refundDTO);

			// 检查该记录能否被审核
			if (!canVerify(refundDTO)) {
				logger.error("refundDetail:该笔交易不能进行退货审核");
				throw new BizServiceException("该笔交易不能进行退货审核");
			}

			// 设置审批状态
			setRuthRlt(refundDTO);
			return refundDTO;
		} catch (BizServiceException b){
			throw b;
		} catch (Exception e) {
			
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询详细信息失败");
		}
	}

	public List<MerchantDTO> findAllMerchant(String entityId)
			throws BizServiceException {
		
		MerchantExample ex = new MerchantExample();
		ex.createCriteria().andFatherEntityIdEqualTo(entityId);
		List<Merchant> list = merchantDAO.selectByExample(ex);

		if (list == null || list.size() <= 0) {
			logger.error("findAllMerchant:该收单机构下没有商户");
			throw new BizServiceException("该收单机构下没有商户");
		} else {
			List<MerchantDTO> res = new ArrayList<MerchantDTO>();
			for (Merchant m : list) {
				MerchantDTO dto = new MerchantDTO();
				ReflectionUtil.copyProperties(m, dto);
				res.add(dto);
			}

			return res;
		}
	}

	/**
	 * 更新退货审核状态
	 */
	public RefundDTO refundVerify1(RefundDTO dto) throws BizServiceException {
//
//		try {
//			
//
//			// 设置状态为已通过(1)
//			dto.setAuthRlt("1");
//			// 记录确认时间日期
//			dto.setConfirmDt(DateUtil.getCurrentDateStr());
//			dto.setConfirmTm(DateUtil.getCurrenTime24());
//
//			int rows = commonsDAO.updateRefundState(dto);
//			if (rows <= 0) {
//				logger.error("更新退货申请记录失败");
//				throw new BizServiceException("更新退货申请记录失败");
//			}
			return dto;
//		} catch (BizServiceException b){
//			throw b;
//		} catch (Exception e) {
//			
//			this.logger.error(e.getMessage());
//			throw new BizServiceException("更新退货申请记录失败");
//		}
	}

	/**
	 * 发送退货报文
	 */
	public RefundDTO refundVerify2(RefundDTO dto) throws BizServiceException {
		try{
			// 调用WS进行退货
			String txnSeq = StringUtils.fillString(String.valueOf(commonsDAO.getNextValueOfSequenceBySequence("SEQ_REFUND_INF_TXN_SEQ")), 8);
			String currDt=DateUtil.getCurrentDateStr();
			dto.setTxnSeq(currDt+txnSeq);		
			dto.setStrOrigTransAt(Amount.getDataBaseAmountForTwoFloat(dto.getStrOrigTransAt()));
			dto.setStrTransAt(Amount.getDataBaseAmountForTwoFloat(dto.getStrTransAt()));
			String respCode = wsRefund(dto);
	
			dto.setRespCode(respCode);
			dto.setRespCodeDesc(getRespCodeDesc(respCode));
			dto.setExtRspCd(respCode);
	
			// 设置状态为已通过(1)
			dto.setAuthRlt("1");
			// 记录确认时间日期
			dto.setConfirmDt(DateUtil.getCurrentDateStr());
			dto.setConfirmTm(DateUtil.getCurrenTime24());
			
			refundUpdate(dto);
	
			return dto;
			} catch (BizServiceException b){
				throw b;
			} catch (Exception e) {
				
				this.logger.error(e.getMessage());
				throw new BizServiceException("更新退货申请记录失败");
			}
		}

//	public RefundDTO refundVerify3(RefundDTO dto) throws BizServiceException {
//		
//		// 如果状态已为退货成功(4),则直接返回成功
//		String res;
//		if (!"4".equals(dto.getAuthRlt())) {
//			// dto.setAuthRlt("4");
//			res = refundUpdate(dto);
//		} else {
//			res = "00";
//		}
//		RefundDTO d = refundDetail(dto.getSeqId());
//		d.setRespCode(res);
//		return d;
//	}

	/**
	 * 拒绝退货
	 */
	public String refundRefuse(RefundDTO dto) throws BizServiceException {
		
		// 设置状态为已拒绝(2),操作返回码为00
		dto.setAuthRlt("2");
		dto.setExtRspCd("00");
		refundUpdate(dto);
		return "ok";
	}

	//检查是否有相关交易
	private RefundDTO checkTxn(RefundDTO refundDTO)throws BizServiceException{
		// 查询相关信息
		String seqId=refundDTO.getSeqId();
		int length = seqId.length();
		for (int i = 0; i + length < 16; i++){
			seqId += " ";
		}
		refundDTO.setSeqId(seqId);
		refundDTO=commonsDAO.selectBySeqIdFlag(refundDTO);
		if (null == refundDTO){
			logger.error("refundDetail:无相关的信息");
			throw new BizServiceException("无相关的信息");
		}
		return refundDTO;
	}
	
	public RefundDTO refundTransLogQuery(RefundDTO refundDTO)
			throws BizServiceException {
		try {
			//检查是否有相关交易
			refundDTO=checkTxn(refundDTO);
			String respCode = wsQuery(refundDTO);

			refundDTO.setRespCode(respCode);
			refundDTO.setRespCodeDesc(getRespCodeDesc(respCode));
			refundDTO.setExtRspCd(respCode);

			// 如果查询成功 更新状态
			if ("00".equals(respCode)) {
				refundUpdate(refundDTO);
			}

			setRuthRlt(refundDTO);
			return refundDTO;
		} catch (BizServiceException b){
			throw b;
		} catch (Exception e) {
			
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询失败");
		}
	}

	public String refundUpdate(RefundDTO dto) throws BizServiceException {
		int rows=0;
		//0:网上退货    1:pos退货
		if("0".equals(dto.getTransChnlFlag())){
			rows = commonsDAO.updateHsaRefundState(dto);
		}else if("1".equals(dto.getTransChnlFlag())){
			rows = commonsDAO.updateTxnRefundState(dto);
		}
		if (rows <= 0) {
			logger.error("更新退货申请记录失败");
			throw new BizServiceException("更新退货申请记录失败");
		}
		
//		RefundInf refund = new RefundInf();
//		ReflectionUtil.copyProperties(dto, refund);
//
//		int rows = refundDAO.updateByPrimaryKey(refund);
//		if (rows <= 0) {
//			logger.error("更新退货申请记录失败");
//			throw new BizServiceException("更新退货申请记录失败");
//		}
		return "00";
	}

	/**
	 * 设置审批状态
	 * 
	 * @param dto
	 */
	private void setRuthRlt(RefundDTO dto) {
		String auth = dto.getAuthRlt();
		String res = "";
		if (auth == null || auth.equals("0")) {
			res = "未审批";
		} else if (auth.equals("1")) {
			res = "已同意";
		} else if (auth.equals("2")) {
			res = "已拒绝";
		}

		dto.setStrAuthRlt(res);
	}

	/**
	 * 能不能进行退货确认
	 * 
	 * @param dto
	 * @return
	 */
	private boolean canVerify(RefundDTO dto) {
		boolean res = true;

		if ("2".equals(dto.getAuthRlt()))
			res = false;

		if ("00".equals(dto.getExtRspCd()))
			res = false;

		return res;
	}

	/**
	 * 获取错误码描述
	 * 
	 * @param code
	 * @return
	 */
	private String getRespCodeDesc(String code) {
		String res = "未知错误";
		String tmp = codeSet.get(code);
		if (tmp != null)
			res = tmp;
		return res;
	}

	/**
	 * WEBSERVICE 方式退款
	 * 
	 * @param dto
	 */
	private String wsRefund(RefundDTO dto) throws BizServiceException {

		logger.info("############支付网关退货############");
		TxnPackageDTO txnPackageDto=new TxnPackageDTO();
		
		
		// 组装报文
		/** 收单机构 **/
		txnPackageDto.setConsumerCode(dto.getAcqInsCd());
		/** 商户代码 **/
		txnPackageDto.setMerchantCode(dto.getMchntCd());
		/** 门店号的值取决于商户是否传过来，如果没传过来，则内部传特殊的门店号：999999。 **/
		/** 在这里并不传门店号 **/
		txnPackageDto.setShopCode("999999");
		/** 订单号 **/
		txnPackageDto.setOrderId( dto.getTxnSeq());
		/** 交易日期 **/
		txnPackageDto.setTxnDate( dto.getConfirmDt());
		/** 交易时间 **/
		txnPackageDto.setTxnTime(dto.getConfirmTm());
		/** 交易金额 **/
		if (dto.getTransAt() != null) {
			txnPackageDto.setAmount(dto.getStrTransAt());
		}
		/** 交易币种 **/
		txnPackageDto.setCurType("156");
		/** 原交易金额 **/
		txnPackageDto.setOrigAmount(dto.getStrOrigTransAt());
		/** 中心流水号 **/
		txnPackageDto.setSequenceNo(dto.getOrderId());
		//退货渠道标志  0是网上退货申请  1是pos退货申请
		txnPackageDto.setFlag(dto.getTransChnlFlag());
		
		try {
			txnPackageDto=java2TXNBusinessService.wsRefund(txnPackageDto);
		} catch (Exception e) {
			
			this.logger.error(e.getMessage());
		}
		
		String rspCode= txnPackageDto.getRspCode();
		logger.debug("respCode：" +rspCode);
		
			
		
/*		Map map = new HashMap();

		// 组装报文
		map.put(CFunctionConstant.TXN_TYPE, "G300");
		*//** 渠道号 **//*
		map.put(CFunctionConstant.CHANNEL, "70000001");
		*//** 收单机构 **//*
		map.put(CFunctionConstant.DATA_HEAD, dto.getAcqInsCd());
		*//** 商户代码 **//*
		map.put(CFunctionConstant.INNER_MERCHANT_NO, dto.getMchntCd());
		*//** 门店号的值取决于商户是否传过来，如果没传过来，则内部传特殊的门店号：999999。 **//*
		*//** 在这里并不传门店号 **//*
		map.put(CFunctionConstant.INNER_POS_NO, "999999");
		*//** 订单号 **//*
		map.put(CFunctionConstant.TRACK2, dto.getTxnSeq());
		*//** 交易日期 **//*
		map.put(CFunctionConstant.SWT_TXN_DATE, dto.getConfirmDt());
		*//** 交易时间 **//*
		map.put(CFunctionConstant.SWT_TXN_TIME, dto.getConfirmTm());
		*//** 交易金额 **//*
		if (dto.getTransAt() != null) {
			map.put(CFunctionConstant.TXN_AMOUNT, dto.getTransAt().toString());
		}
		*//** 交易币种 **//*
		map.put(CFunctionConstant.SWT_BATCH_NO, "156");

		*//** 原交易金额 **//*
		map.put(CFunctionConstant.ORI_TXN_AMOUNT, dto.getOrigTransAt()
				.toString());
		*//** 中心流水号 **//*
		map.put(CFunctionConstant.REFERENCE_NO, dto.getOrderId());

		OperationResult or = java2C.sendTpService("vTxn", map,
				Const.JAVA2C_NORMAL, false);

		HashMap map1 = (HashMap) or.getDetailvo();
		String rspCode = (String) map1.get(CFunctionConstant.RESP_CODE);*/
		
		return rspCode;
	}

	/**
	 * WEBSERVICE 方式查询退货
	 * 
	 * @param dto
	 * @return
	 * @throws BizServiceException
	 */
	private String wsQuery(RefundDTO dto) throws BizServiceException {
		logger.info("############支付网关交易查询############");
		
		TxnPackageDTO txnPackageDto=new TxnPackageDTO();
		
		// 组装报文
		/** 退货交易类型 **/
		txnPackageDto.setOldTxnType("G30");
		/** 收单机构 **/
		txnPackageDto.setConsumerCode(dto.getAcqInsCd());
		/** 商户代码 **/
		txnPackageDto.setMerchantCode(dto.getMchntCd());
		/** 门店号的值取决于商户是否传过来，如果没传过来，则内部传特殊的门店号：999999。 **/
		txnPackageDto.setShopCode("999999");
		/** 订单号 **/
		txnPackageDto.setOrderId(dto.getTxnSeq());
		/** 交易日期 **/
		txnPackageDto.setTxnDate(dto.getConfirmDt());
		/** 交易时间 **/
		txnPackageDto.setTxnTime(dto.getConfirmTm());
		
			
		try {
			txnPackageDto=java2TXNBusinessService.wsQuery(txnPackageDto);
		} catch (Exception e) {
			
			this.logger.error(e.getMessage());
		}
		
/*		
		Map map = new HashMap();

		// 组装报文
		map.put(CFunctionConstant.TXN_TYPE, "G100");
		*//** 渠道号 **//*
		map.put(CFunctionConstant.CHANNEL, "70000001");
		*//** 原交易类型 **//*
		map.put(CFunctionConstant.CVV2, "G30");
		*//** 收单机构 **//*
		map.put(CFunctionConstant.DATA_HEAD, dto.getAcqInsCd());
		*//** 商户代码 **//*
		map.put(CFunctionConstant.INNER_MERCHANT_NO, dto.getMchntCd());

		*//** 门店号的值取决于商户是否传过来，如果没传过来，则内部传特殊的门店号：999999。 **//*
		map.put(CFunctionConstant.INNER_POS_NO, "999999");
		*//** 订单号 **//*
		map.put(CFunctionConstant.TRACK2, dto.getTxnSeq());
		*//** 交易日期 **//*
		map.put(CFunctionConstant.SWT_TXN_DATE, dto.getConfirmDt());
		*//** 交易时间 **//*
		map.put(CFunctionConstant.SWT_TXN_TIME, dto.getConfirmTm());

		OperationResult or = java2C.sendTpService("vTxn", map,Const.JAVA2C_NORMAL, false);

		HashMap map1 = (HashMap) or.getDetailvo();*/
		String rspCode = txnPackageDto.getRspCode();
		logger.debug("rspCode:" + rspCode);
		return rspCode;
	}

	public RefundDTO refundVerify(RefundDTO dto) throws BizServiceException {
		try {
			// 判断此退货申请的状态能否继续
			if (!canVerify(dto)) {
				logger.error("refundVerify:该笔交易不能进行退货审核");
				throw new BizServiceException("该笔交易不能进行退货审核");
			}
			dto = this.refundVerify2(dto);
			if (dto == null) {
				throw new BizServiceException("超时或其他错误");
			}
		} catch (BizServiceException e) {
			throw e;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("退货审核失败");
		}
		return dto;
	}
}

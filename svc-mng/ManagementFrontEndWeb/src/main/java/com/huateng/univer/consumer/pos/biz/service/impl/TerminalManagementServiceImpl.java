package com.huateng.univer.consumer.pos.biz.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.consumer.outerPos.OuterPosInfoDTO;
import com.allinfinance.univer.consumer.pos.dto.PosBrandInfoDTO;
import com.allinfinance.univer.consumer.pos.dto.PosBrandInfoQueryDTO;
import com.allinfinance.univer.consumer.pos.dto.PosInfoDTO;
import com.allinfinance.univer.consumer.pos.dto.PosInfoQueryDTO;
import com.allinfinance.univer.system.sysparam.dto.EntitySystemParameterDTO;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.dao.BaseDAO;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.dao.PageQueryDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.OuterPosInfoDAO;
import com.huateng.framework.ibatis.dao.PosBrandInfoDAO;
import com.huateng.framework.ibatis.dao.PosInfoDAO;
import com.huateng.framework.ibatis.dao.PosParamenterDAO;
import com.huateng.framework.ibatis.dao.ShopPosDAO;
import com.huateng.framework.ibatis.model.OuterPosInfo;
import com.huateng.framework.ibatis.model.OuterPosInfoExample;
import com.huateng.framework.ibatis.model.OuterPosInfoKey;
import com.huateng.framework.ibatis.model.PosBrandInfo;
import com.huateng.framework.ibatis.model.PosBrandInfoExample;
import com.huateng.framework.ibatis.model.PosInfo;
import com.huateng.framework.ibatis.model.PosInfoExample;
import com.huateng.framework.ibatis.model.PosInfoKey;
import com.huateng.framework.ibatis.model.PosParamenter;
import com.huateng.framework.ibatis.model.PosParamenterExample;
import com.huateng.framework.ibatis.model.ShopPos;
import com.huateng.framework.ibatis.model.ShopPosExample;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.ReflectionUtil;
import com.huateng.univer.consumer.merchant.integration.dao.MerchantServiceDAO;
import com.huateng.univer.consumer.pos.biz.service.TerminalManagementService;
import com.huateng.univer.issuer.entityBaseInfo.dao.EntityBaseInfoServiceDao;
import com.huateng.univer.system.sysparam.biz.service.EntitySystemParameterService;

public class TerminalManagementServiceImpl implements TerminalManagementService {

	private PageQueryDAO pageQueryDAO;
	private PosInfoDAO posInfoDAO;
	private CommonsDAO commonsDAO;
	private BaseDAO baseDAO;
	private PosBrandInfoDAO posBrandInfoDAO;
	private PosParamenterDAO terParameterDAO;
	Logger logger = Logger.getLogger(TerminalManagementServiceImpl.class);
	private EntityBaseInfoServiceDao entityBaseInfoServiceDAO;
	private EntitySystemParameterService entitySystemParameterService;
	private MerchantServiceDAO merchantServiceDAO;
	private ShopPosDAO shopPosDAO;
	private OuterPosInfoDAO outerPosInfoDAO;

	public OuterPosInfoDAO getOuterPosInfoDAO() {
		return outerPosInfoDAO;
	}

	public void setOuterPosInfoDAO(OuterPosInfoDAO outerPosInfoDAO) {
		this.outerPosInfoDAO = outerPosInfoDAO;
	}

	public ShopPosDAO getShopPosDAO() {
		return shopPosDAO;
	}

	public void setShopPosDAO(ShopPosDAO shopPosDAO) {
		this.shopPosDAO = shopPosDAO;
	}

	public EntitySystemParameterService getEntitySystemParameterService() {
		return entitySystemParameterService;
	}

	public void setEntitySystemParameterService(
			EntitySystemParameterService entitySystemParameterService) {
		this.entitySystemParameterService = entitySystemParameterService;
	}

	public EntityBaseInfoServiceDao getEntityBaseInfoServiceDAO() {
		return entityBaseInfoServiceDAO;
	}

	public void setEntityBaseInfoServiceDAO(
			EntityBaseInfoServiceDao entityBaseInfoServiceDAO) {
		this.entityBaseInfoServiceDAO = entityBaseInfoServiceDAO;
	}

	public MerchantServiceDAO getMerchantServiceDAO() {
		return merchantServiceDAO;
	}

	public void setMerchantServiceDAO(MerchantServiceDAO merchantServiceDAO) {
		this.merchantServiceDAO = merchantServiceDAO;
	}

	public PageQueryDAO getPageQueryDAO() {
		return pageQueryDAO;
	}

	public void setPageQueryDAO(PageQueryDAO pageQueryDAO) {
		this.pageQueryDAO = pageQueryDAO;
	}

	public PageDataDTO inquery(PosInfoQueryDTO posInfoQueryDTO)
			throws BizServiceException {
		try {
			// MerchantExample merchantExample=new MerchantExample();
			// merchantExample.createCriteria().andFatherEntityIdEqualTo(posInfoQueryDTO.getDefaultEntityId());
			// List<Merchant> lstMerchants =
			// merchantServiceDAO.selectByExample(merchantExample);
			// //Merchant currentMerchant=new Merchant();
			// StringBuffer merchantCodeArray=new StringBuffer();
			// if(null != lstMerchants && lstMerchants.size()>0){
			// for(int i=0;i<lstMerchants.size();i++){
			// if(i==lstMerchants.size()-1){
			// merchantCodeArray.append("'"+lstMerchants.get(i).getMerchantCode()+"'");
			// }else{
			// merchantCodeArray.append("'"+lstMerchants.get(i).getMerchantCode()+"',");
			// }
			// }
			// }else{
			// return null;
			// }
			// String merchantCodeArrays=merchantCodeArray.toString();
			// logger.debug("shopid:"+posInfoQueryDTO.getShopId());
			// posInfoQueryDTO.setMerchantCodeArray(merchantCodeArrays);
			PageDataDTO pageDataDTO = pageQueryDAO.query(
					"POS_INF.selectPosInfByDTO", posInfoQueryDTO);
			PageDataDTO pageDataDTO1 = pageQueryDAO.query(
					"OUTER_POS_INF.selectOuterPosInfByDTO", posInfoQueryDTO);
			List posList = pageDataDTO.getData();
			List outerPosList = pageDataDTO1.getData();
			posList.addAll(outerPosList);
			pageDataDTO.setTotalRecord(posList.size());
			pageDataDTO.setData(posList);

			return pageDataDTO;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询终端信息失败!");
		}
	}

	public PageDataDTO inqueryPos(PosInfoQueryDTO posInfoQueryDTO)
			throws BizServiceException {
		PageDataDTO pageDataDTO = pageQueryDAO.query(
				"POS_INF.selectPosInfByDTO", posInfoQueryDTO);
		return pageDataDTO;
	}

	public PageDataDTO inqueryOuterpos(PosInfoQueryDTO posInfoQueryDTO)
			throws BizServiceException {
		PageDataDTO pageDataDTO = pageQueryDAO.query(
				"OUTER_POS_INF.selectOuterPosInfByDTO", posInfoQueryDTO);
		return pageDataDTO;
	}

	@SuppressWarnings("unchecked")
	public Map selectPrmVersion(PosInfoDTO posInfoDTO)
			throws BizServiceException {
		try {
			List<Map<String, String>> versionlist1 = baseDAO.queryForList(
					"TERPARAMETER", "selectVersion1", posInfoDTO);
			List<Map<String, String>> versionlist2 = baseDAO.queryForList(
					"TERPARAMETER", "selectVersion2", posInfoDTO);
			List<Map<String, String>> versionlist3 = baseDAO.queryForList(
					"TERPARAMETER", "selectVersion3", posInfoDTO);
			Map map = new HashMap();
			map.put("versionlist1", versionlist1);
			map.put("versionlist2", versionlist2);
			map.put("versionlist3", versionlist3);
			return map;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("加载版本信息失败");
		}
	}

	/**
	 * 查询终端厂商信息
	 * 
	 * @return
	 */

	public PageDataDTO inqueryPosBrand(PosBrandInfoQueryDTO posBrandInfQueryDTO)
			throws BizServiceException {
		try {
			PageDataDTO pageDataDTO = pageQueryDAO.query(
					"POSBRANDINF.selectPosBrandInfByDTO", posBrandInfQueryDTO);
			return pageDataDTO;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询终端厂商失败");
		}

	}

	/**
	 * 添加终端厂商
	 * 
	 * @param posBrandInfDTO
	 * @throws BizServiceException
	 */
	public void addPosBrand(PosBrandInfoDTO posBrandInfDTO)
			throws BizServiceException {
		try {
			PosBrandInfoExample example = new PosBrandInfoExample();
			example.createCriteria().andBrandNameEqualTo(
					posBrandInfDTO.getBrandName());
			List<PosBrandInfo> list = posBrandInfoDAO.selectByExample(example);
			if (list.size() > 0) {
				throw new BizServiceException(posBrandInfDTO.getBrandName()
						+ " 厂商名已存在!");
			}
			PosBrandInfo posBrandInf = new PosBrandInfo();
			ReflectionUtil.copyProperties(posBrandInfDTO, posBrandInf);
			String id = commonsDAO.getNextValueOfSequence("TB_POS_BRAND_INFO");
			posBrandInf.setTermBrandId(Integer.parseInt(id));
			posBrandInf.setInsIdCd(posBrandInfDTO.getDefaultEntityId());
			posBrandInfoDAO.insert(posBrandInf);
		} catch (BizServiceException e) {
			throw e;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("添加终端厂商失败");
		}
	}

	public PosBrandInfoDTO loadPosBrand(PosBrandInfoDTO posBrandInfDTO)
			throws BizServiceException {
		try {
			PosBrandInfo posBrandInf = posBrandInfoDAO
					.selectByPrimaryKey(posBrandInfDTO.getTermBrandId());
			ReflectionUtil.copyProperties(posBrandInf, posBrandInfDTO);
			return posBrandInfDTO;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("加载系统参数失败");
		}
	}

	public void updatePosBrand(PosBrandInfoDTO posBrandInfDTO)
			throws BizServiceException {
		try {
			PosBrandInfo PosBrandInf = new PosBrandInfo();
			ReflectionUtil.copyProperties(posBrandInfDTO, PosBrandInf);
			posBrandInfoDAO.updateByPrimaryKeySelective(PosBrandInf);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("更新系统参数失败");
		}
	}

	public void insertposInf(PosInfoDTO posInfDTO) throws BizServiceException {
		try {
			// check(posInfDTO);
			OuterPosInfoExample outerPosInfoExample = new OuterPosInfoExample();
			outerPosInfoExample.createCriteria().andTermIdEqualTo(
					posInfDTO.getTermId()).andMchntIdEqualTo(
					posInfDTO.getMchntId()).andDataStateEqualTo(
					DataBaseConstant.DATA_STATE_NORMAL);
			PosInfoExample example = new PosInfoExample();
			example.createCriteria().andTermIdEqualTo(posInfDTO.getTermId())
					.andMchntIdEqualTo(posInfDTO.getMchntId())
					.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);

			if (posInfoDAO.countByExample(example) > 0
					|| outerPosInfoDAO.countByExample(outerPosInfoExample) > 0) {
				throw new BizServiceException("同一个商户终端参数"
						+ posInfDTO.getTermId() + "已存在!");
			}
			PosInfoExample example1 = new PosInfoExample();
			example1.createCriteria().andTermIdEqualTo(posInfDTO.getTermId())
					.andMchntIdEqualTo(posInfDTO.getMchntId())
					.andDataStateEqualTo(DataBaseConstant.DATA_STATE_DELETE);
			if (posInfoDAO.countByExample(example1) > 0) {
				modifyposInf(posInfDTO);
			} else {
				PosInfo posInf = new PosInfo();
				ReflectionUtil.copyProperties(posInfDTO, posInf);
				String id = commonsDAO.getNextValueOfSequence("TB_POS_INFO");
				posInf.setId(Long.parseLong(id));
				posInf.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
				posInf.setTermBatchNo(1);
				posInf.setTermSigninStat(Short.parseShort("0"));
				posInf.setTermPik("0");
				posInf.setTermMak("0");
				posInf.setCurrTxnCnt(Long.parseLong("0"));
				posInf.setTermTmk("0");
				posInf.setTmkDownTime("0");
				posInf.setPrmType1(Integer.parseInt("10001"));
				posInf.setPrmType2(Integer.parseInt("10002"));
				posInf.setPrmType3(Integer.parseInt("10003"));
				posInf.setPrmChangeFlag1(Short.parseShort("0"));
				posInf.setPrmChangeFlag2(Short.parseShort("0"));
				posInf.setPrmChangeFlag3(Short.parseShort("0"));
				posInf.setIsValidateMac(Short.parseShort("1"));
				// posInf.setPrmChangeFlag4(Short.parseShort("0"));
				posInf.setDataState("1");
				posInf.setConsumerId(posInfDTO.getDefaultEntityId());
				// 密钥
				EntitySystemParameterDTO entitySystemParameterDTO = new EntitySystemParameterDTO();
				entitySystemParameterDTO
						.setEntityId(entitySystemParameterService
								.getFatherEntityId(posInfDTO));
				entitySystemParameterDTO.setParameterRole("1");
				List<EntitySystemParameterDTO> list = entityBaseInfoServiceDAO
						.getEntitySystemParameter(entitySystemParameterDTO);
				if (null != list && list.size() > 0) {
					for (EntitySystemParameterDTO sysparam : list) {
						if ("TMK_INDEX".equals(sysparam.getParameterCode())) {
							posInf.setTmkIndex(sysparam.getParameterValue());
						}
						if ("PIK_INDEX".equals(sysparam.getParameterCode())) {
							posInf.setPikIndex(sysparam.getParameterValue());
						}
						if ("MAK_INDEX".equals(sysparam.getParameterCode())) {
							posInf.setMakIndex(sysparam.getParameterValue());
						}
					}
				}

				// 记录shop pos 关联
				ShopPos shopPos = new ShopPos();

				shopPos.setCreateTime(DateUtil.getCurrentDateStr());
				shopPos.setModifyTime(DateUtil.getCurrentDateStr());
				shopPos.setCreateUser(posInfDTO.getLoginUserId());
				shopPos.setModifyUser(posInfDTO.getLoginUserId());
				shopPos.setShopId(posInf.getShopId().toString());
				shopPos.setPosId(posInf.getId().toString());
				shopPos.setPosBarcode(posInf.getTermId());
				shopPos.setDataState(posInf.getDataState());
				shopPosDAO.insert(shopPos);
				posInfoDAO.insert(posInf);
			}
		} catch (BizServiceException e) {
			throw e;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("添加终端信息失败!");

		}
	}

	public void insertOuterPosInf(OuterPosInfoDTO outerPosInfoDTO)
			throws BizServiceException {
		try {
			OuterPosInfoExample outerPosInfoExample = new OuterPosInfoExample();
			outerPosInfoExample.createCriteria().andTermIdEqualTo(
					outerPosInfoDTO.getTermId()).andMchntIdEqualTo(
					outerPosInfoDTO.getMchntId()).andDataStateEqualTo(
					DataBaseConstant.DATA_STATE_NORMAL);
			PosInfoExample example = new PosInfoExample();
			example.createCriteria().andTermIdEqualTo(
					outerPosInfoDTO.getTermId()).andMchntIdEqualTo(
					outerPosInfoDTO.getMchntId()).andDataStateEqualTo(
					DataBaseConstant.DATA_STATE_NORMAL);

			if (posInfoDAO.countByExample(example) > 0
					|| outerPosInfoDAO.countByExample(outerPosInfoExample) > 0) {
				throw new BizServiceException("同一个商户终端参数"
						+ outerPosInfoDTO.getTermId() + "已存在!");
			}

			OuterPosInfo outerPosInfo = new OuterPosInfo();
			ReflectionUtil.copyProperties(outerPosInfoDTO, outerPosInfo);
			outerPosInfo.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
			/** 数据状态为0的情况 **/
			OuterPosInfoExample outerPosInfoExample1 = new OuterPosInfoExample();
			outerPosInfoExample1.createCriteria().andTermIdEqualTo(
					outerPosInfoDTO.getTermId()).andMchntIdEqualTo(
					outerPosInfoDTO.getMchntId()).andDataStateEqualTo(
					DataBaseConstant.DATA_STATE_DELETE);

			if (outerPosInfoDAO.countByExample(outerPosInfoExample1) > 0) {
				outerPosInfoDAO.updateByExampleSelective(outerPosInfo,
						outerPosInfoExample1);
			} else {
				outerPosInfoDAO.insert(outerPosInfo);
			}

		} catch (BizServiceException e) {
			throw e;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("添加外部终端信息失败");
		}
	}

	public void check(PosInfoDTO posInfDTO) throws BizServiceException {

		/**
		 * 判断终端通迅参数版本中是否齐全
		 */
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(11, "pos终端应用类型");
		map.put(12, "超时时间");
		map.put(13, "重试次数");
		map.put(14, "电话号码之一");
		map.put(15, "电话号码之二");
		map.put(16, "主机地址一");
		map.put(17, "主机端口一");
		map.put(18, "主机地址二");
		map.put(19, "主机端口二");
		map.put(23, "交易重发次数");

		PosParamenterExample sysExample = new PosParamenterExample();
		sysExample.createCriteria().andPrmVersionEqualTo(
				posInfDTO.getPrmVersion1()).andPrmTypeEqualTo(10001)
				.andPrmStatEqualTo(Short.valueOf("0"));
		List<PosParamenter> sysList = terParameterDAO
				.selectByExample(sysExample);
		for (PosParamenter sysParameter : sysList) {
			map.remove(new Integer(sysParameter.getPrmId()));
		}
		if (map.size() > 0) {
			StringBuffer errorMessage = new StringBuffer();
			for (Integer key : map.keySet()) {
				errorMessage.append(map.get(key)).append(" ");
			}
			throw new BizServiceException("版本号为:" + posInfDTO.getPrmVersion1()
					+ "的终端通迅参数缺少" + errorMessage);
		}

		/**
		 * 判断IC卡参数是否齐全
		 */
		// Map<String, String> icCardMap = new HashMap<String, String>();
		// icCardMap.put("9F06", "AID");
		// icCardMap.put("9F08", "应用版本号");
		// icCardMap.put("DF01", "ASI");
		// icCardMap.put("DF11", "TAC-缺省");
		// icCardMap.put("DF12", "TAC-联机");
		// icCardMap.put("DF13", "TAC-拒绝");
		// icCardMap.put("DF18", "终端联机PIN支持");
		//
		// sysExample = new PosParamenterExample();
		// sysExample.createCriteria().andPrmVersionEqualTo(
		// posInfDTO.getPrmVersion3()).andPrmTypeEqualTo("10003")
		// .andPrmStatEqualTo("0");
		// sysList = terParameterDAO.selectByExample(sysExample);
		// for (PosParamenter sysParameter : sysList) {
		// icCardMap.remove(sysParameter.getPrmName());
		// }
		// if (icCardMap.size() > 0) {
		// StringBuffer errorMessage = new StringBuffer();
		// for (String key : icCardMap.keySet()) {
		// errorMessage.append(icCardMap.get(key)).append(" ");
		// }
		// throw new BizServiceException("版本号为:" + posInfDTO.getPrmVersion3()
		// + "的终端IC卡参数缺少" + errorMessage.toString());
		// }

	}

	public PosInfoDTO viewposInf(PosInfoDTO posInfDTO)
			throws BizServiceException {
		try {
			// /////////////////////////////////////////////////////////////
			PosInfoKey pkey = new PosInfoKey();
			pkey.setMchntId(posInfDTO.getMchntId());
			pkey.setTermId(posInfDTO.getTermId());
			// PosInfo
			// posInf=posInfoDAO.selectByPrimaryKey(posInfDTO.getTermId());
			PosInfo posInf = posInfoDAO.selectByPrimaryKey(pkey);
			ReflectionUtil.copyProperties(posInf, posInfDTO);
			posInfDTO.setTermBrandName(posBrandInfoDAO.selectByPrimaryKey(
					posInfDTO.getTermBrandId()).getBrandName());
			return posInfDTO;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查看终端信息失败");
		}
	}

	public OuterPosInfoDTO viewOuterposInf(OuterPosInfoDTO outerPosInfoDTO)
			throws BizServiceException {
		try {
			OuterPosInfoKey outKey = new OuterPosInfoKey();
			outKey.setMchntId(outerPosInfoDTO.getMchntId());
			outKey.setTermId(outerPosInfoDTO.getTermId());
			OuterPosInfo outerPosInfo = outerPosInfoDAO
					.selectByPrimaryKey(outKey);
			ReflectionUtil.copyProperties(outerPosInfo, outerPosInfoDTO);
			return outerPosInfoDTO;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查看终端信息失败");
		}
	}

	public void modifyposInf(PosInfoDTO posInfDTO) throws BizServiceException {
		try {

			check(posInfDTO);
			PosInfoKey pkey = new PosInfoKey();
			pkey.setMchntId(posInfDTO.getMchntId());
			pkey.setTermId(posInfDTO.getTermId());
			// PosInfo
			// posInf=posInfoDAO.selectByPrimaryKey(posInfDTO.getTermId());
			PosInfo posInf = posInfoDAO.selectByPrimaryKey(pkey);
			/**
			 * 如果参数版本修改则更新标志
			 */
			if (null != posInfDTO.getPrmVersion1()
					&& null != posInf.getPrmVersion1()) {
				if (!posInfDTO.getPrmVersion1().equals(
						posInf.getPrmVersion1().toString())) {
					posInfDTO.setPrmChangeFlag1("1");
				}
			}
			if (null != posInfDTO.getPrmVersion2()
					&& null != posInf.getPrmVersion2()) {
				if (!posInfDTO.getPrmVersion2().equals(
						posInf.getPrmVersion2().toString())) {
					posInfDTO.setPrmChangeFlag2("1");
				}
			}
			if (null != posInfDTO.getPrmVersion3()
					&& null != posInf.getPrmVersion3()) {
				if (!posInfDTO.getPrmVersion3().equals(
						posInf.getPrmVersion3().toString())) {
					posInfDTO.setPrmChangeFlag3("1");
				}
			}

			/**
			 * 如果消费允许标识修改,充值允许标志修改,重打印标志修改
			 * 则去更新公共参数版本标识,门店名称若被修改在shopSerivceIMpl中去修改标识
			 */

			// Annotate by Yifeng.Shi @2011-05-13
			// if(!posInfDTO.getConsumePerFlag().equals(posInf.getConsumePerFlag())||
			// !posInfDTO.getReloadPerFlag().equals(posInf.getReloadPerFlag())||
			// !posInfDTO.getReprintCtrlFlag().equals(posInf.getReprintCtrlFlag())||
			// !posInfDTO.getPointPerFlag().equals(posInf.getPointPerFlag())){
			if (!posInfDTO.getConsumePerFlag().equals(
					posInf.getConsumePerFlag().toString())
					|| !posInfDTO.getReloadPerFlag().equals(
							posInf.getReloadPerFlag().toString())
					|| !posInfDTO.getReprintCtrlFlag().equals(
							posInf.getReprintCtrlFlag().toString())) {
				posInfDTO.setPrmChangeFlag1("1");
			}

			posInfDTO.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
			ReflectionUtil.copyProperties(posInfDTO, posInf);
			posInf.setConsumerId(posInfDTO.getDefaultEntityId());
			posInfoDAO.updateByPrimaryKeySelective(posInf);
		} catch (BizServiceException e) {
			throw e;
		} catch (Exception e1) {
			logger.error(e1.getMessage());
			throw new BizServiceException("更新终端信息失败");
		}
	}

	public void modifyOuterposInf(OuterPosInfoDTO outerPosInfoDTO)
			throws BizServiceException {
		try {

			OuterPosInfoExample outerPosInfoExample = new OuterPosInfoExample();
			outerPosInfoExample.createCriteria().andTermIdEqualTo(
					outerPosInfoDTO.getTermId()).andMchntIdEqualTo(
					outerPosInfoDTO.getOldMchntId());
			OuterPosInfo outerPosInfo = new OuterPosInfo();
			outerPosInfo.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
			ReflectionUtil.copyProperties(outerPosInfoDTO, outerPosInfo);
			outerPosInfoDAO.updateByExample(outerPosInfo, outerPosInfoExample);
		}

		catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("更新外部终端信息失败");
		}
	}

	public void deleteposInf(PosInfoDTO posInfDTO) throws BizServiceException {
		try {
			/**
			 * add by yy, 如果终端发生过交易 包括密钥管理类交易 就不允许被删除
			 */
			PosInfoKey key = new PosInfoKey();
			key.setTermId(posInfDTO.getTermId());
			key.setMchntId(posInfDTO.getMchntId());
			PosInfo oriPosInfo = posInfoDAO.selectByPrimaryKey(key);
			if (!(oriPosInfo.getTermMak() == null
					|| oriPosInfo.getTermMak().trim().length() == 0 || oriPosInfo
					.getTermMak().trim().equals("0"))) {
				throw new BizServiceException("终端已经发生过交易，不允许被删除");
			}

			/**
			 * modified by yy, 删除由逻辑删除改成物理删除
			 */
			PosInfo posInf = new PosInfo();
			posInf.setTermId(posInfDTO.getTermId());
			posInf.setMchntId(posInfDTO.getMchntId());
			posInf.setDataState(DataBaseConstant.DATA_STATE_DELETE);
			// posInfoDAO.updateByPrimaryKeySelective(posInf);
			posInfoDAO.deleteByPrimaryKey(posInf);
			ShopPosExample shopPosExample = new ShopPosExample();
			shopPosExample.createCriteria().andPosBarcodeEqualTo(
					posInfDTO.getTermId());
			shopPosDAO.deleteByExample(shopPosExample);

		} catch (BizServiceException ex) {
			logger.error(ex.getMessage());
			throw ex;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("删除终端信息失败");
		}
	}

	public void deleteOuterposInf(OuterPosInfoDTO outerPosInfoDTO)
			throws BizServiceException {
		try {
			OuterPosInfo outerPosInfo = new OuterPosInfo();
			outerPosInfo.setTermId(outerPosInfoDTO.getTermId());
			outerPosInfo.setMchntId(outerPosInfoDTO.getMchntId());
			outerPosInfo.setDataState(DataBaseConstant.DATA_STATE_DELETE);
			outerPosInfoDAO.updateByPrimaryKeySelective(outerPosInfo);

		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("删除外部终端信息失败");
		}
	}

	public PosInfoDAO getPosInfoDAO() {
		return posInfoDAO;
	}

	public void setPosInfoDAO(PosInfoDAO posInfoDAO) {
		this.posInfoDAO = posInfoDAO;
	}

	public CommonsDAO getCommonsDAO() {
		return commonsDAO;
	}

	public void setCommonsDAO(CommonsDAO commonsDAO) {
		this.commonsDAO = commonsDAO;
	}

	public BaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	public PosBrandInfoDAO getPosBrandInfoDAO() {
		return posBrandInfoDAO;
	}

	public void setPosBrandInfoDAO(PosBrandInfoDAO posBrandInfoDAO) {
		this.posBrandInfoDAO = posBrandInfoDAO;
	}

	public PosParamenterDAO getTerParameterDAO() {
		return terParameterDAO;
	}

	public void setTerParameterDAO(PosParamenterDAO terParameterDAO) {
		this.terParameterDAO = terParameterDAO;
	}

}

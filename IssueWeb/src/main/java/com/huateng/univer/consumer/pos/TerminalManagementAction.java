package com.huateng.univer.consumer.pos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.consumer.merchant.dto.MerchantDTO;
import com.allinfinance.univer.consumer.merchant.dto.MerchantQueryDTO;
import com.allinfinance.univer.consumer.outerPos.OuterPosInfoDTO;
import com.allinfinance.univer.consumer.pos.dto.PosBrandInfoDTO;
import com.allinfinance.univer.consumer.pos.dto.PosBrandInfoQueryDTO;
import com.allinfinance.univer.consumer.pos.dto.PosInfoDTO;
import com.allinfinance.univer.consumer.pos.dto.PosInfoQueryDTO;
import com.allinfinance.univer.consumer.shop.dto.ShopDTO;
import com.allinfinance.univer.consumer.shop.dto.ShopQueryDTO;
import com.allinfinance.univer.system.dictinfo.dto.EntityDictInfoDTO;
import com.huateng.framework.action.BaseAction;
import com.huateng.framework.constant.SystemInfoConstants;
import com.huateng.framework.util.SystemInfo;

@SuppressWarnings("unchecked")
public class TerminalManagementAction extends BaseAction {

	private PosInfoQueryDTO posInfQueryDTO;
	private PageDataDTO pageDataDTO;
	private Integer totalRows;
	private List<Map<String, String>> versionlist1;

	private List<Map<String, String>> versionlist2;

	private List<Map<String, String>> versionlist3;
	private MerchantQueryDTO merchantQueryDTO;
	private ShopQueryDTO shopQueryDTO;
	private JSONObject city;
	private PosBrandInfoQueryDTO posBrandInfQueryDTO;
	private PosBrandInfoDTO posBrandInfDTO;
	private String[] choose;
	private PosInfoDTO posInfoDTO;
	private String prmType;
	private String displayInnerTerm;
	private OuterPosInfoDTO outerPosInfoDTO;
	private String termType;
	private String shopCode;
    private	ShopDTO shopDTO;
    private Map<String,String> tmk = new HashMap<String, String>();
	
	public String getTermType() {
		return termType;
	}

	public void setTermType(String termType) {
		this.termType = termType;
	}

	public OuterPosInfoDTO getOuterPosInfoDTO() {
		return outerPosInfoDTO;
	}

	public void setOuterPosInfoDTO(OuterPosInfoDTO outerPosInfoDTO) {
		this.outerPosInfoDTO = outerPosInfoDTO;
	}

	public String getDisplayInnerTerm() {
		return displayInnerTerm;
	}

	public void setDisplayInnerTerm(String displayInnerTerm) {
		this.displayInnerTerm = displayInnerTerm;
	}

	public String[] getChoose() {
		return choose;
	}

	public void setChoose(String[] choose) {
		this.choose = choose;
	}

	public PosBrandInfoDTO getPosBrandInfDTO() {
		return posBrandInfDTO;
	}

	public void setPosBrandInfDTO(PosBrandInfoDTO posBrandInfDTO) {
		this.posBrandInfDTO = posBrandInfDTO;
	}

	public PosBrandInfoQueryDTO getPosBrandInfQueryDTO() {
		return posBrandInfQueryDTO;
	}

	public void setPosBrandInfQueryDTO(PosBrandInfoQueryDTO posBrandInfQueryDTO) {
		this.posBrandInfQueryDTO = posBrandInfQueryDTO;
	}

	public JSONObject getCity() {
		return city;
	}

	public void setCity(JSONObject city) {
		this.city = city;
	}

	public String inquery() throws Exception {
		if (posInfQueryDTO == null) {
			posInfQueryDTO = new PosInfoQueryDTO();
		}
		ListPageInit(null, posInfQueryDTO);
		if (posInfQueryDTO.isQueryAll()) {
			posInfQueryDTO.setQueryAll(false);
			posInfQueryDTO
					.setRowsDisplayed(Integer
							.parseInt(SystemInfo
									.getParameterValue(SystemInfoConstants.EXPORT_DATA_MAXIMUM)));
		}
		pageDataDTO = (PageDataDTO) sendService(
				ConstCode.TERMINAL_MANAGEMENT_INQUERY_POS, posInfQueryDTO)
				.getDetailvo();
		if (pageDataDTO != null) {
			totalRows = pageDataDTO.getTotalRecord();
		}

		return "list";
	}

	public String inqueryOuterposOrPos() throws Exception {
		if (posInfQueryDTO == null) {
			posInfQueryDTO = new PosInfoQueryDTO();
		}
		if (null != termType && !"".equals(termType)) {
			posInfQueryDTO.setTermType(termType);

		}
		ListPageInit(null, posInfQueryDTO);
		if (posInfQueryDTO.isQueryAll()) {
			posInfQueryDTO.setQueryAll(false);
			posInfQueryDTO
					.setRowsDisplayed(Integer
							.parseInt(SystemInfo
									.getParameterValue(SystemInfoConstants.EXPORT_DATA_MAXIMUM)));
		}
		if (null != posInfQueryDTO.getTermType()
				&& !"".equals(posInfQueryDTO.getTermType())) {

			if (posInfQueryDTO.getTermType().equals("1")) {
				pageDataDTO = (PageDataDTO) sendService(
						ConstCode.TERMINAL_MANAGEMENT_INQUERY_POS,
						posInfQueryDTO).getDetailvo();
				if (pageDataDTO != null) {
					totalRows = pageDataDTO.getTotalRecord();
					return "list";
				}
			}

			if (posInfQueryDTO.getTermType().equals("0")) {

				pageDataDTO = (PageDataDTO) sendService(
						ConstCode.TERMINAL_MANAGEMENT_INQUERY, posInfQueryDTO)
						.getDetailvo();
				if (pageDataDTO != null) {
					totalRows = pageDataDTO.getTotalRecord();
				}
				return "list";
			}
		}
		if (posInfQueryDTO.getTermType().equals("2")) {
			pageDataDTO = (PageDataDTO) sendService(
					ConstCode.TERMINAL_MANAGEMENT_INQUERY_OUTER_POS,
					posInfQueryDTO).getDetailvo();
			if (pageDataDTO != null) {
				totalRows = pageDataDTO.getTotalRecord();
				displayInnerTerm = "none";
				return "list";
			}
		}

		return "list";
	}

	@SuppressWarnings("unchecked")
	public String redircetAdd() throws Exception {
		posInfoDTO = new PosInfoDTO();
		Map map = (Map) sendService(ConstCode.TERMINAL_POSINFO_INIT_VERSION,
				posInfoDTO).getDetailvo();
		versionlist1 = (List<Map<String, String>>) map.get("versionlist1");
		versionlist2 = (List<Map<String, String>>) map.get("versionlist2");
		versionlist3 = (List<Map<String, String>>) map.get("versionlist3");
		return "add";
	}

	public String selectMerchant() throws Exception {
		if (merchantQueryDTO == null) {
			merchantQueryDTO = new MerchantQueryDTO();
		}
		merchantQueryDTO.setFatherEntityId(getUser().getEntityId());
		merchantQueryDTO.setMerchantState("1");
		ListPageInit(null, merchantQueryDTO);
		Object ob = sendService("8008021801", merchantQueryDTO).getDetailvo();
		if (ob != null) {
			this.pageDataDTO = (PageDataDTO) ob;
		}
		if (pageDataDTO != null) {
			totalRows = pageDataDTO.getTotalRecord();
		}
		if (hasErrors()) {
			return "input";
		}
		return "list";

	}

	/**
	 * 
	 * 
	 * 门店信息
	 */

	public String inquiry() throws Exception {
		if (shopQueryDTO == null) {
			shopQueryDTO = new ShopQueryDTO();
		}
		String merchantId = getRequest().getParameter("merchantId");
		if (merchantId != null && !"".equals(merchantId)) {
			shopQueryDTO.setEntityId(merchantId);
			shopQueryDTO.setShopState("1");
		}
		//添加机构号
		shopQueryDTO.setConsumerId(getUser().getEntityId());
		ListPageInit(null, shopQueryDTO);
		Object ob = sendService("8008020000", shopQueryDTO).getDetailvo();
		if (ob != null) {
			pageDataDTO = (PageDataDTO) ob;
		}
		if (pageDataDTO != null) {
			totalRows = pageDataDTO.getTotalRecord();
		}
		// 获取级联的门店城、区域列表
		getCityList();
		if (hasErrors()) {
			return "input";
		}
		return "list";
	}

	public void getCityList() {
		// Map<String, List<DictInfoDTO>> salesRegions = new HashMap<String,
		// List<DictInfoDTO>>();
		// List<DictInfoDTO> dictInfoDTOList = SystemInfo.getDictList("405");
		// for (DictInfoDTO dictInfoDTO : dictInfoDTOList) {
		// salesRegions.put(dictInfoDTO.getDictId().toString(),
		// SystemInfo.getSubDictList("408", new
		// Integer(dictInfoDTO.getDictId())));
		// }
		Map<String, List<EntityDictInfoDTO>> salesRegions = new HashMap<String, List<EntityDictInfoDTO>>();
		List<EntityDictInfoDTO> dictInfoDTOList = SystemInfo.getDictList(
				getUser().getEntityId(), "405");
		if (dictInfoDTOList != null) {
			for (EntityDictInfoDTO dictInfoDTO : dictInfoDTOList) {
				salesRegions.put(dictInfoDTO.getDictCode(), SystemInfo
						.getSubDictList(getUser().getEntityId(), "408",
								new Integer(dictInfoDTO.getDictCode())));
			}
		}
		this.city = JSONObject.fromObject(salesRegions);
	}

	/**
	 * 以下为终端厂商管理
	 * 
	 * @return
	 */

	public String inqueryPosBrand() throws Exception {

		if (posBrandInfQueryDTO == null) {
			posBrandInfQueryDTO = new PosBrandInfoQueryDTO();
		}
		if (null != posBrandInfQueryDTO.getTermBrandId()
				&& !posBrandInfQueryDTO.getTermBrandId().equals("")) {
			String rex = "^[0-9]*[1-9][0-9]*$";
			if (!posBrandInfQueryDTO.getTermBrandId().matches(rex)) {
				addActionError("查询失败:终端厂商标识必须是正整数!");
				posBrandInfQueryDTO = new PosBrandInfoQueryDTO();
			}
		}
		posBrandInfQueryDTO.setInsIdCd(getUser().getEntityId());
		ListPageInit(null, posBrandInfQueryDTO);
		if (posBrandInfQueryDTO.isQueryAll()) {
			posBrandInfQueryDTO.setQueryAll(false);
			posBrandInfQueryDTO
					.setRowsDisplayed(Integer
							.parseInt(SystemInfo
									.getParameterValue(SystemInfoConstants.EXPORT_DATA_MAXIMUM)));
		}
		pageDataDTO = (PageDataDTO) sendService(
				ConstCode.TERMINAL_POS_BRAND_INF_INQUERY, posBrandInfQueryDTO)
				.getDetailvo();
		if (pageDataDTO != null) {
			totalRows = pageDataDTO.getTotalRecord();
		}
		return "list";
	}

	public String addPosBrandInf() throws Exception {
		posBrandInfDTO.setInsIdCd(getUser().getEntityId());
		sendService(ConstCode.TERMINAL_POS_BRAND_INF_ADD, posBrandInfDTO)
				.getDetailvo();
		if (this.hasErrors()) {
			return "failed";
		} else {
			this.addActionMessage("添加终端厂商信息成功!");
		}
		return inqueryPosBrand();
	}

	public String loadPosBrandInf() throws Exception {
		if (posBrandInfDTO == null) {
			posBrandInfDTO = new PosBrandInfoDTO();
		}
		// String[] merchantIds=choose.get(0).split(",");
		posBrandInfDTO.setTermBrandId(Integer.parseInt(choose[0]));
		posBrandInfDTO = (PosBrandInfoDTO) sendService(
				ConstCode.TERMINAL_POS_BRAND_INF_VIEW, posBrandInfDTO)
				.getDetailvo();
		return "edit";
	}

	public String updatePosBrandInf() throws Exception {
		sendService(ConstCode.TERMINAL_POS_BRAND_INF_UPDATE, posBrandInfDTO)
				.getDetailvo();
		if (this.hasErrors()) {
			return "list";
		} else {
			this.addActionMessage("修改终端厂商成功!");
		}
		return inqueryPosBrand();
	}

	public String choosePosBrandInf() throws Exception {
		if (posBrandInfQueryDTO == null) {
			posBrandInfQueryDTO = new PosBrandInfoQueryDTO();
		}
		posBrandInfQueryDTO.setInsIdCd(getUser().getEntityId());
		ListPageInit(null, posBrandInfQueryDTO);
		if (posBrandInfQueryDTO.isQueryAll()) {
			posBrandInfQueryDTO.setQueryAll(false);
			posBrandInfQueryDTO
					.setRowsDisplayed(Integer
							.parseInt(SystemInfo
									.getParameterValue(SystemInfoConstants.EXPORT_DATA_MAXIMUM)));
		}
		pageDataDTO = (PageDataDTO) sendService(
				ConstCode.TERMINAL_POS_BRAND_INF_INQUERY, posBrandInfQueryDTO)
				.getDetailvo();
		if (pageDataDTO != null) {
			totalRows = pageDataDTO.getTotalRecord();
		}
		return "list";
	}

	@SuppressWarnings("unchecked")
	public void validateAddposInf() {
		if (this.hasFieldErrors()) {
			Map map = (Map) sendService(
					ConstCode.TERMINAL_POSINFO_INIT_VERSION, posInfoDTO)
					.getDetailvo();
			versionlist1 = (List<Map<String, String>>) map.get("versionlist1");
			versionlist2 = (List<Map<String, String>>) map.get("versionlist2");
			versionlist3 = (List<Map<String, String>>) map.get("versionlist3");
		}
	}

	public String addposInf() throws Exception {
		posInfoDTO.setTmkIndex("0");
		posInfoDTO.setPikIndex("0");
		posInfoDTO.setMakIndex("0");
		posInfoDTO.setMaxTxnCnt(String.valueOf(posInfoDTO.getMaxTxnCntInt()));
		sendService(ConstCode.TERMINAL_POSINFO_ADD, posInfoDTO).getDetailvo();
		if (this.hasErrors()) {
			Map map = (Map) sendService(
					ConstCode.TERMINAL_POSINFO_INIT_VERSION, posInfoDTO)
					.getDetailvo();
			versionlist1 = (List<Map<String, String>>) map.get("versionlist1");
			versionlist2 = (List<Map<String, String>>) map.get("versionlist2");
			versionlist3 = (List<Map<String, String>>) map.get("versionlist3");
			return "add";
		} else {
			this.addActionMessage("添加终端信息成功!");
		}
		return inquery();
	}

	/**
	 *添加外部终端
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addOuterPosInf() throws Exception {

		return "outerPosadd";
	}

	public String insertOuterPosInf() throws Exception {
		if (null == outerPosInfoDTO) {
			outerPosInfoDTO = new OuterPosInfoDTO();

		}
		sendService(ConstCode.TERMINAL_OUTER_POSINFO_INIT_INSERT,
				outerPosInfoDTO);
		if (this.hasActionErrors()) {

			return "input";
		} else {

			this.addActionMessage("添加外部终端信息成功");
		}
		return inquery();

	}

	@SuppressWarnings("unchecked")
	public String loadposInf() throws Exception {
		String[] merchantIds = choose[0].split(",");
		MerchantDTO merchantDTO = new MerchantDTO();
		merchantDTO.setMerchantCode(merchantIds[1]);
		termType = merchantIds[2];
		merchantDTO.setFatherEntityId(getUser().getEntityId());
		merchantDTO = (MerchantDTO) sendService(ConstCode.MERCHANT_DTO,
				merchantDTO).getDetailvo();
		if (this.hasErrors()) {
			return "list";
		}

		if (null != termType && !"".equals(termType)) {

			if (termType.equals("1")) {// 内部终端编辑初始化
				if (posInfoDTO == null) {
					posInfoDTO = new PosInfoDTO();
				}
				posInfoDTO.setTermId(merchantIds[0]);
				posInfoDTO.setMchntId(merchantIds[1]);
				posInfoDTO = (PosInfoDTO) sendService(
						ConstCode.TERMINAL_POSINFO_VIEW, posInfoDTO)
						.getDetailvo();
				ShopDTO shopDTO = new ShopDTO();
				shopDTO.setShopId(posInfoDTO.getShopId());
				shopDTO = (ShopDTO) sendService("8008020002", shopDTO)
						.getDetailvo();
				posInfoDTO.setShopCode(shopDTO.getShopCode());
				posInfoDTO.setMchntEntityId(merchantDTO.getEntityId());
				
//				
				List<Map<String, String>> list = new ArrayList<Map<String,String>>();
				if(!"0".equals(posInfoDTO.getTmkDownTime())){
					tmk.put(posInfoDTO.getTmkDownTime().trim(), "不可下载");
					tmk.put("0", "可下载");
					list.add(tmk);
				}else{
					tmk.put("可下载", "0");
					list.add(tmk);
				}
				
				if (this.hasErrors()) {
					inquery();
					return "list";
				}
				PosInfoDTO posInfoDTO1 = new PosInfoDTO();
				Map map = (Map) sendService(
						ConstCode.TERMINAL_POSINFO_INIT_VERSION, posInfoDTO1)
						.getDetailvo();
				
				versionlist1 = (List<Map<String, String>>) map
						.get("versionlist1");
				versionlist2 = (List<Map<String, String>>) map
						.get("versionlist2");
				versionlist3 = (List<Map<String, String>>) map
						.get("versionlist3");

				if (this.hasErrors()) {
					inquery();
					return "list";
				}
				return "edit";

			} else if (termType.equals("2")) {// 外部终端编辑初始化
				if (null == outerPosInfoDTO) {
					outerPosInfoDTO = new OuterPosInfoDTO();

				}
				outerPosInfoDTO.setTermId(merchantIds[0]);
				outerPosInfoDTO.setMchntId(merchantIds[1]);

				outerPosInfoDTO = (OuterPosInfoDTO) sendService(
						ConstCode.TERMINAL_OUTER_POSINFO_VIEW, outerPosInfoDTO)
						.getDetailvo();
				outerPosInfoDTO.setOldMchntId(outerPosInfoDTO.getMchntId());
				if (this.hasErrors()) {
					return "editError";
				}
				return "editSucc";
			}
		}
		inquery();
		return "list";

	}

	public void validateUpdateposInf() {

		if (null != termType && !"".equals(termType)) {

			if (termType.equals("1")) {
				if (null == posInfoDTO.getMchntId()
						|| "".equals(posInfoDTO.getMchntId())) {
					this.addFieldError("posInfoDTO.mchntId", "商户编号不能为空");

				}
				if (null == posInfoDTO.getMchntName()
						|| "".equals(posInfoDTO.getMchntName())) {
					this.addFieldError("posInfoDTO.mchntName", "商户名称不能为空");

				}
				if (null == posInfoDTO.getShopId()
						|| "".equals(posInfoDTO.getShopId())) {
					this.addFieldError("posInfoDTO.shopCode", "门店号不能为空");
				}
				
				if (null == posInfoDTO.getTermBrandId()
						|| "".equals(posInfoDTO.getTermBrandId())) {
					this.addFieldError("posInfoDTO.termBrandId", "终端厂商标号不能为空");

				}
				if (null == posInfoDTO.getMaxTxnCnt()
						|| "".equals(posInfoDTO.getMaxTxnCnt())) {
					this.addFieldError("posInfoDTO.maxTxnCnt", "最大交易数不能为空");
				}
				if (!posInfoDTO.getMaxTxnCnt().matches("^[0-9]{1,5}$")){
					this.addFieldError("posInfoDTO.maxTxnCnt", "只能是5位以内数字");
				}
				
				
				Map map = (Map) sendService(
						ConstCode.TERMINAL_POSINFO_INIT_VERSION, posInfoDTO)
						.getDetailvo();
				versionlist1 = (List<Map<String, String>>) map
						.get("versionlist1");
				versionlist2 = (List<Map<String, String>>) map
						.get("versionlist2");
				versionlist3 = (List<Map<String, String>>) map
						.get("versionlist3");
			} else if (termType.equals("2")) {
				if (null == outerPosInfoDTO.getMchntId()
						|| "".equals(outerPosInfoDTO.getMchntId())) {
					this.addFieldError("outerPosInfoDTO.mchntId", "商户编号不能为空");

				}
				if (null == outerPosInfoDTO.getMchntName()
						|| "".equals(outerPosInfoDTO.getMchntName())) {
					this.addFieldError("outerPosInfoDTO.mchntName", "商户名称不能为空");

				}

			}
		}
	}

	public String viewposInf() throws Exception {
		if (null != termType && !"".equals(termType)) {

			if (termType.equals("2")) {
				if (null == outerPosInfoDTO) {
					outerPosInfoDTO = new OuterPosInfoDTO();

				}
				outerPosInfoDTO.setTermId(posInfoDTO.getTermId());
				outerPosInfoDTO.setMchntId(posInfoDTO.getMchntId());

				outerPosInfoDTO = (OuterPosInfoDTO) sendService(
						ConstCode.TERMINAL_OUTER_POSINFO_VIEW, outerPosInfoDTO)
						.getDetailvo();
				if (this.hasErrors()) {
					inquery();
					return "list";
				}
				return "outerPosView";

			} else if (termType.equals("1")) {// 内部终端编辑初始化
				posInfoDTO = (PosInfoDTO) sendService(
						ConstCode.TERMINAL_POSINFO_VIEW, posInfoDTO)
						.getDetailvo();
				shopDTO = new ShopDTO();
				shopDTO.setShopId(posInfoDTO.getShopId());
				shopDTO = (ShopDTO) sendService("8008020002", shopDTO)
						.getDetailvo();
				shopCode = shopDTO.getShopCode();				
				if (this.hasErrors()) {
					inquery();
					return "list";
				}
				return "posInfView";
			}

		}

		return null;

	}

	public String updateposInf() throws Exception {

		if (termType.equals("1")) {

			if (posInfoDTO == null) {
				posInfoDTO = new PosInfoDTO();
			}
			sendService(ConstCode.TERMINAL_POSINFO_UPDATE, posInfoDTO)
					.getDetailvo();
			
			if (this.hasErrors()) {
				loadposInf();
				return "input";
			} else {
				this.addActionMessage("更新终端信息成功!");
			}

		} else if (termType.equals("2")) {
			if (null == outerPosInfoDTO) {
				outerPosInfoDTO = new OuterPosInfoDTO();

			}
			sendService(ConstCode.TERMINAL_OUTER_POSINFO_UPDATE,
					outerPosInfoDTO).getDetailvo();

			if (this.hasErrors()) {
				loadposInf();
				return "fail";
			} else {
				this.addActionMessage("更新外部终端信息成功!");
			}
		}
		return inquery();
	}

	public String delposInf() throws Exception {
		for (int i = 0; i < choose.length; i++) {
			String[] merchantIds = choose[i].split(",");
			termType = merchantIds[2];

			if (termType.equals("1")) {
				if (null == posInfoDTO) {
					posInfoDTO = new PosInfoDTO();
				}
				posInfoDTO.setTermId(merchantIds[0]);
				posInfoDTO.setMchntId(merchantIds[1]);
				sendService(ConstCode.TERMINAL_POSINFO_DEL, posInfoDTO)
						.getDetailvo();
			} else if (termType.equals("2")) {

				if (null == outerPosInfoDTO) {
					outerPosInfoDTO = new OuterPosInfoDTO();

				}
				outerPosInfoDTO.setTermId(merchantIds[0]);
				outerPosInfoDTO.setMchntId(merchantIds[1]);
				sendService(ConstCode.TERMINAL_OUTER_POSINFO_DEL,
						outerPosInfoDTO).getDetailvo();
			}
		}
		if (this.hasErrors()) {
			//return "list";
			return inquery();
		} else {
			this.addActionMessage("删除信息成功!");
		}
		return inqueryOuterposOrPos();
	}

	/**
	 * 设置公共参数下载或卡BIN参数下载
	 * 
	 * @return
	 * @throws Exception
	 */
	public String updateParameter() throws Exception {

		if (posInfoDTO == null) {
			posInfoDTO = new PosInfoDTO();
		}
		for (int i = 0; i < choose.length; i++) {
			String[] termIds = choose[i].split(",");
			posInfoDTO.setTermId(termIds[0]);
			posInfoDTO.setMchntId(termIds[1]);
			posInfoDTO = (PosInfoDTO) sendService(
					ConstCode.TERMINAL_POSINFO_VIEW, posInfoDTO).getDetailvo();
			if (prmType.equals("10001")) {// 公共参数
				posInfoDTO.setPrmChangeFlag1("1");
			} else if (prmType.equals("10002")) {// 卡BIN
				posInfoDTO.setPrmChangeFlag2("1");
			}
			sendService(ConstCode.TERMINAL_POSINFO_UPDATE, posInfoDTO)
					.getDetailvo();
		}

		if (this.hasErrors()) {
			return "list";
		} else {
			this.addActionMessage("设置终端下载信息成功!");
		}

		return inquery();
	}

	public String redirectPosBrandInf() throws Exception {
		return "add";
	}

	public PosInfoQueryDTO getPosInfQueryDTO() {
		return posInfQueryDTO;
	}

	public void setPosInfQueryDTO(PosInfoQueryDTO posInfQueryDTO) {
		this.posInfQueryDTO = posInfQueryDTO;
	}

	public PageDataDTO getPageDataDTO() {
		return pageDataDTO;
	}

	public void setPageDataDTO(PageDataDTO pageDataDTO) {
		this.pageDataDTO = pageDataDTO;
	}

	public Integer getTotalRows() {
		if (null == pageDataDTO) {
			return 0;
		}
		return totalRows;
	}

	public void setTotalRows(Integer totalRows) {
		this.totalRows = totalRows;
	}

	public List<Map<String, String>> getVersionlist1() {
		return versionlist1;
	}

	public void setVersionlist1(List<Map<String, String>> versionlist1) {
		this.versionlist1 = versionlist1;
	}

	public List<Map<String, String>> getVersionlist2() {
		return versionlist2;
	}

	public void setVersionlist2(List<Map<String, String>> versionlist2) {
		this.versionlist2 = versionlist2;
	}

	public List<Map<String, String>> getVersionlist3() {
		return versionlist3;
	}

	public void setVersionlist3(List<Map<String, String>> versionlist3) {
		this.versionlist3 = versionlist3;
	}

	public MerchantQueryDTO getMerchantQueryDTO() {
		return merchantQueryDTO;
	}

	public void setMerchantQueryDTO(MerchantQueryDTO merchantQueryDTO) {
		this.merchantQueryDTO = merchantQueryDTO;
	}

	public ShopQueryDTO getShopQueryDTO() {
		return shopQueryDTO;
	}

	public void setShopQueryDTO(ShopQueryDTO shopQueryDTO) {
		this.shopQueryDTO = shopQueryDTO;
	}

	public PosInfoDTO getPosInfoDTO() {
		return posInfoDTO;
	}

	public void setPosInfoDTO(PosInfoDTO posInfoDTO) {
		this.posInfoDTO = posInfoDTO;
	}

	public void setPrmType(String prmType) {
		this.prmType = prmType;
	}

	public String getPrmType() {
		return prmType;
	}

	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}

	public String getShopCode() {
		return shopCode;
	}

	public ShopDTO getShopDTO() {
		return shopDTO;
	}

	public void setShopDTO(ShopDTO shopDTO) {
		this.shopDTO = shopDTO;
	}

	public Map<String, String> getTmk() {
		return tmk;
	}

	public void setTmk(Map<String, String> tmk) {
		this.tmk = tmk;
	}

}

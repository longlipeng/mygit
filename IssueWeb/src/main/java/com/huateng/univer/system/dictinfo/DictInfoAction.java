package com.huateng.univer.system.dictinfo;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.system.dictinfo.dto.DictInfoDTO;
import com.allinfinance.univer.system.dictinfo.dto.EntityDictInfoDTO;
import com.allinfinance.univer.system.dictinfo.dto.EntityDictInfoQueryDTO;
import com.huateng.framework.action.BaseAction;
import com.huateng.framework.constant.SystemInfoConstants;
import com.huateng.framework.util.SystemInfo;
import com.huateng.framework.webservice.service.SystemInfoService;

/**
 * 数据字典action
 */
public class DictInfoAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private PageDataDTO pageDataDTO;
	private Integer totalRows = 0;
	private EntityDictInfoQueryDTO dictInfoQueryDTO = new EntityDictInfoQueryDTO();
	private String key;
	private EntityDictInfoDTO dictInfoDTO;
	private SystemInfoService systemInfoService;
	private String[] choose;

	public String inquery() throws Exception {
		ListPageInit(null, dictInfoQueryDTO);
		if (dictInfoQueryDTO.isQueryAll()) {
			dictInfoQueryDTO.setQueryAll(false);
			dictInfoQueryDTO
					.setRowsDisplayed(Integer
							.parseInt(SystemInfo
									.getParameterValue(SystemInfoConstants.EXPORT_DATA_MAXIMUM)));
		}
		pageDataDTO = (PageDataDTO) sendService(ConstCode.ENTITY_DICT_INFO_INQUERY,
				dictInfoQueryDTO).getDetailvo();
		if (pageDataDTO != null) {
			totalRows = pageDataDTO.getTotalRecord();
		}
		return "list";
	}

	public String redirectAdd() throws Exception {
		return "add";
	}

	public String add() throws Exception {
		if("501".equals(dictInfoDTO.getDictTypeCode())||"502".equals(dictInfoDTO.getDictTypeCode())){
			if(dictInfoDTO.getDictCode()==null){
				addActionError("数据编号必须是正整数！");
				return INPUT;
			}else if(!dictInfoDTO.getDictCode().matches("^\\d")){
				addActionError("该数据类型的数据编号只能为1位！");
				return INPUT;
			}
		}
		if ("108".equals(dictInfoDTO.getDictTypeCode())) {
			dictInfoDTO.setFatherDictCode(dictInfoDTO.getShopfatherDictId());
		} else if ("408".equals(dictInfoDTO.getDictTypeCode())) {
			dictInfoDTO.setFatherDictCode(dictInfoDTO.getCityfatherDictId());
		}
		sendService(ConstCode.ENTITY_DICT_INFO_ADD, dictInfoDTO).getDetailvo();

		if (this.hasErrors()) {
			return INPUT;
		}
		this.addActionMessage("添加数据成功!");
		init();
		return this.inquery();
	}

	public String view() throws Exception {
		if (dictInfoDTO == null) {
			dictInfoDTO = new EntityDictInfoDTO();
		}
		String dictId = getRequest().getParameter("dictId");
		if (dictId != null && !"".equals(dictId)) {
			dictInfoDTO.setDictId(dictId);
		}
		dictInfoDTO = (EntityDictInfoDTO) sendService(ConstCode.ENTITY_DICT_INFO_VIEW,
				dictInfoDTO).getDetailvo();
		if (this.hasErrors()) {
			return INPUT;
		}
		if ("108".equals(dictInfoDTO.getDictTypeCode())) {
			return "shopView";
		}
		if ("408".equals(dictInfoDTO.getDictTypeCode())) {
			return "cityView";
		}

		return "view";
	}

	public String load() throws Exception {
		if (dictInfoDTO == null) {
			dictInfoDTO = new EntityDictInfoDTO();
		}

		String dictId = getRequest().getParameter("dictId");
		if (dictId != null && !"".equals(dictId)) {
			dictInfoDTO.setDictId(dictId);
		}

		dictInfoDTO = (EntityDictInfoDTO) sendService(ConstCode.ENTITY_DICT_INFO_VIEW,
				dictInfoDTO).getDetailvo();

		if (this.hasErrors()) {
			return INPUT;
		}
		/*
		 * if ("108".equals(dictInfoDTO.getDictTypeCode())) { return "shopEdit";
		 * } if ("408".equals(dictInfoDTO.getDictTypeCode())) { return
		 * "cityEdit"; }
		 */
		return "edit";
	}

	public String edit() throws Exception {
		sendService(ConstCode.ENTITY_DICT_INFO_EDIT, dictInfoDTO).getDetailvo();
		if (this.hasErrors()) {
			return INPUT;
		}
		this.addActionMessage("修改数据成功!");
		init();
		return this.inquery();
	}

	public String del() throws Exception {
		if (dictInfoDTO == null) {
			dictInfoDTO = new EntityDictInfoDTO();
		}
		dictInfoDTO.setDelStr(choose);
		sendService(ConstCode.ENTITY_DICT_INFO_DEL, dictInfoDTO).getDetailvo();
		if (!hasErrors()) {
			this.addActionMessage("删除数据成功!");
		}
		init();
		return this.inquery();
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public SystemInfoService getSystemInfoService() {
		return systemInfoService;
	}

	public void setSystemInfoService(SystemInfoService systemInfoService) {
		this.systemInfoService = systemInfoService;
	}

	public void init() throws Exception {
		//systemInfoService.initDictInfo();
		systemInfoService.initEntityDictInfo();
	}

	public String[] getChoose() {
		return choose;
	}

	public void setChoose(String[] choose) {
		this.choose = choose;
	}

	public EntityDictInfoDTO getDictInfoDTO() {
		return dictInfoDTO;
	}

	public void setDictInfoDTO(EntityDictInfoDTO dictInfoDTO) {
		this.dictInfoDTO = dictInfoDTO;
	}

	public Integer getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(Integer totalRows) {
		this.totalRows = totalRows;
	}

	public PageDataDTO getPageDataDTO() {
		return pageDataDTO;
	}

	public void setPageDataDTO(PageDataDTO pageDataDTO) {
		this.pageDataDTO = pageDataDTO;
	}

	public EntityDictInfoQueryDTO getDictInfoQueryDTO() {
		return dictInfoQueryDTO;
	}

	public void setDictInfoQueryDTO(EntityDictInfoQueryDTO dictInfoQueryDTO) {
		this.dictInfoQueryDTO = dictInfoQueryDTO;
	}
	

}

package com.huateng.univer.issuer.cardcompary;

import org.apache.log4j.Logger;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.issuer.dto.cardcompany.CardCompanyDTO;
import com.allinfinance.univer.issuer.dto.cardcompany.CardCompanyQueryDTO;
import com.huateng.framework.action.BaseAction;

/**
 * 制卡商action
 * 
 * @author xxl
 * 
 */
public class CardCompanyAction extends BaseAction {
	
	private Logger logger = Logger.getLogger(CardCompanyAction.class);

	private static final long serialVersionUID = -3671385775296760661L;
	private CardCompanyQueryDTO cardCompanyQueryDTO = new CardCompanyQueryDTO();
	private CardCompanyDTO cardCompanyDTO = new CardCompanyDTO();
	private PageDataDTO pageDataDTO = new PageDataDTO();

	private String[] cardCompanyIds;

	/** 数据总条数 */
	public int getTotalRows() {
		return pageDataDTO.getTotalRecord();
	}

	public String list() throws Exception {
		try {
			ListPageInit(null, cardCompanyQueryDTO);
			this.pageDataDTO = (PageDataDTO) sendService(
					ConstCode.CARD_COMPANY_SERVICE_INQUERY, cardCompanyQueryDTO)
					.getDetailvo();

		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return "list";
	}

	public String view() throws Exception {
		try {
			if (null == cardCompanyDTO.getCardCompanyId()
					|| "".equals(cardCompanyDTO.getCardCompanyId())) {
				cardCompanyDTO.setCardCompanyId(cardCompanyIds[0]);
			}
			this.cardCompanyDTO = (CardCompanyDTO) sendService(
					ConstCode.CARD_COMPANY_SERVICE_VIEW, cardCompanyDTO)
					.getDetailvo();
			if (hasErrors()) {
				return "input";
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return "view";
	}

	public String add() throws Exception {
		return "add";
	}

	public String insert() throws Exception {
		cardCompanyDTO.setEntityId(getUser().getEntityId());
		sendService(ConstCode.CARD_COMPANY_SERVICE_INSERT, cardCompanyDTO);
		if (hasErrors()) {
			return "input";
		}
		addActionMessage("添加制卡商成功！");
		return list();
	}

	/**
	 * 编辑制卡商
	 * 
	 * @return
	 * @throws Exception
	 */
	public String edit() throws Exception {
		this.view();
		if (hasErrors()) {
			return "input";
		}
		return "edit";
	}

	/**
	 * 更新制卡商
	 * 
	 * @return
	 * @throws Exception
	 */
	public String update() throws Exception {
		sendService(ConstCode.CARD_COMPANY_SERVICE_UPDATE, cardCompanyDTO);
		if (hasErrors()) {
			return "input";
		}
		addActionMessage("修改制卡商成功！");
		return list();
	}

	/**
	 * 修改制卡商状态
	 */
	public String modifyState() throws Exception {
		this.view();
		if (hasErrors()) {
			return "input";
		}
		return "modifyState";
	}

	public String updateState() throws Exception {
		cardCompanyDTO.setIsIcCard(null);
		cardCompanyDTO.setIsMsCard(null);
		sendService(ConstCode.CARD_COMPANY_SERVICE_MODIFYSTATE, cardCompanyDTO);
		if (hasErrors()) {
			return "input";
		}
		addActionMessage("修改制卡商状态成功！");
		return list();
	}

	private void checkFormat() {
		if ("1".equals(cardCompanyDTO.getIsIcCard())
				&& (cardCompanyDTO.getIcFileFormat() == null || ""
						.equals(cardCompanyDTO.getIcFileFormat().trim()))) {
			addFieldError("cardCompanyDTO.icFileFormat", "制卡文件格式不能为空");
		}
		if ("1".equals(cardCompanyDTO.getIsMsCard())
				&& (cardCompanyDTO.getMsFileFormat() == null || ""
						.equals(cardCompanyDTO.getMsFileFormat().trim()))) {
			addFieldError("cardCompanyDTO.msFileFormat", "制卡文件格式不能为空");
		}
		
		if(null!=cardCompanyDTO.getIsIcCard() || !"".equals(cardCompanyDTO.getIsIcCard())){
			if("1".equals(cardCompanyDTO.getIsIcCard())){
				int icFileFormat = cardCompanyDTO.getIcFileFormat().length();
				System.out.println("icFileFormat的值是："+cardCompanyDTO.getIcFileFormat()+"长度为："+icFileFormat);
				if(540!=icFileFormat && 280!=icFileFormat && 148!=icFileFormat)
					addFieldError("cardCompanyDTO.icFileFormat","ic卡公钥必须为540/280/148位");
			}
		}
		if(null!=cardCompanyDTO.getIsMsCard() || !"".equals(cardCompanyDTO.getIsMsCard())){
			if("1".equals(cardCompanyDTO.getIsMsCard())){
				int msFileFormat = cardCompanyDTO.getMsFileFormat().length();
				if(540!=msFileFormat && 280!=msFileFormat && 148!=msFileFormat)
					addFieldError("cardCompanyDTO.msFileFormat","磁条卡公钥必须为540/280/148位");
			}
		}
	}

	/**
	 * 添加制卡商时的校验
	 */
	public void validateInsert() throws Exception {
		checkFormat();
	}

	/**
	 * 修改制卡商时的校验
	 */
	public void validateUpdate() throws Exception {
		checkFormat();
	}

	public CardCompanyQueryDTO getCardCompanyQueryDTO() {
		return cardCompanyQueryDTO;
	}

	public void setCardCompanyQueryDTO(CardCompanyQueryDTO cardCompanyQueryDTO) {
		this.cardCompanyQueryDTO = cardCompanyQueryDTO;
	}

	public CardCompanyDTO getCardCompanyDTO() {
		return cardCompanyDTO;
	}

	public void setCardCompanyDTO(CardCompanyDTO cardCompanyDTO) {
		this.cardCompanyDTO = cardCompanyDTO;
	}

	public PageDataDTO getPageDataDTO() {
		return pageDataDTO;
	}

	public void setPageDataDTO(PageDataDTO pageDataDTO) {
		this.pageDataDTO = pageDataDTO;
	}

	public String[] getCardCompanyIds() {
		return cardCompanyIds;
	}

	public void setCardCompanyIds(String[] cardCompanyIds) {
		this.cardCompanyIds = cardCompanyIds;
	}

}

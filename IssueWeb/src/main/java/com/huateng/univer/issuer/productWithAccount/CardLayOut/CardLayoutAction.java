package com.huateng.univer.issuer.productWithAccount.CardLayOut;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.log4j.Logger;
import org.springframework.util.FileCopyUtils;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.issuer.dto.cardLayOut.CardLayoutDTO;
import com.allinfinance.univer.issuer.dto.cardLayOut.CardLayoutQueryDTO;
import com.huateng.framework.action.BaseAction;

public class CardLayoutAction extends BaseAction {
	private Logger logger = Logger.getLogger(CardLayoutAction.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = -1999391833868926362L;
	/**
	 * 卡面查询条件DTO
	 */
	private CardLayoutQueryDTO cardLayoutQueryDTO = new CardLayoutQueryDTO();
	/**
	 * 卡面信息DTO
	 */
	private CardLayoutDTO cardLayoutDTO = new CardLayoutDTO();
	/**
	 * 查询信息DTO
	 */
	private PageDataDTO pageDataDTO = new PageDataDTO();
	/**
	 * 查询ID
	 */
	private List<String> choose = new ArrayList<String>();

	/**
	 * 图片文件
	 */
	private File picture;
	
	
	private int totalRows=0;

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public CardLayoutQueryDTO getCardLayoutQueryDTO() {
		return cardLayoutQueryDTO;
	}

	public void setCardLayoutQueryDTO(CardLayoutQueryDTO cardLayoutQueryDTO) {
		this.cardLayoutQueryDTO = cardLayoutQueryDTO;
	}

	public CardLayoutDTO getCardLayoutDTO() {
		return cardLayoutDTO;
	}

	public void setCardLayoutDTO(CardLayoutDTO cardLayoutDTO) {
		this.cardLayoutDTO = cardLayoutDTO;
	}

	public PageDataDTO getPageDataDTO() {
		return pageDataDTO;
	}

	public void setPageDataDTO(PageDataDTO pageDataDTO) {
		this.pageDataDTO = pageDataDTO;
	}

	public List<String> getChoose() {
		return choose;
	}

	public void setChoose(List<String> choose) {
		this.choose = choose;
	}

	public File getPicture() {
		return picture;
	}

	public void setPicture(File picture) {
		this.picture = picture;
	}

	
   /**
    * 查询卡面信息
    * @return
    * @throws Exception
    */
	public String query() throws Exception {
		try{
			ListPageInit(null,cardLayoutQueryDTO);
			pageDataDTO =(PageDataDTO) sendService(ConstCode.CARDLAYOUT_SERVICE_SELETE,cardLayoutQueryDTO).getDetailvo();
			
			if(pageDataDTO!=null){
				totalRows = pageDataDTO.getTotalRecord();
			}
			if (hasErrors()) {
	            return INPUT;
	        }
		}catch(Exception e){
			this.logger.error(e.getMessage());
		}
		return "list";
	}

	/**
	 * 转向卡面添加页面
	 * @return
	 * @throws Exception
	 */
	public String layoutInsert()throws Exception{
		return "add";
	}
	
	/**
	 * 卡面信息添加
	 * @return
	 * @throws Exception
	 */
	public String layoutSave()throws Exception{
		if(picture!=null){
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(picture));
			byte[] b = FileCopyUtils.copyToByteArray(in);
			cardLayoutDTO.setPicture(b);
		}
		cardLayoutDTO.setCreateUser(getUser().getUserId());
		sendService(ConstCode.CARDLAYOUT_SERVICE_INSERT, cardLayoutDTO);
		if(this.hasErrors()){
			return INPUT;
		}else{
			addActionMessage("卡面信息添加成功！");
		}
		return query();
	}
	
	/**
	 * 卡面信息加载
	 * @return
	 * @throws Exception
	 */

	public String load()throws Exception{
		if(cardLayoutDTO.getCardLayoutId()==null){
			String id=choose.get(0);
			cardLayoutDTO=new CardLayoutDTO();
			cardLayoutDTO.setCardLayoutId(id);
		}
		cardLayoutDTO=(CardLayoutDTO)sendService(ConstCode.CARDLAYOUT_SERVICE_LOAD,cardLayoutDTO).getDetailvo();
		if(this.hasErrors()){
			return query();
		}
		return "edit";
	}
	
	/**
	 * 查看卡面信息
	 * @return
	 * @throws Exception
	 */
	public String view()throws Exception{
		String id=getRequest().getParameter("id");
		cardLayoutDTO=new CardLayoutDTO();
		cardLayoutDTO.setCardLayoutId(id);
		cardLayoutDTO=(CardLayoutDTO)sendService(ConstCode.CARDLAYOUT_SERVICE_VIEW,cardLayoutDTO).getDetailvo();
		if(this.hasErrors()){
			return "list";
		}
		return "view";
	}
	
	
	
	/**
	 * 图片显示
	 * @return
	 * @throws Exception
	 */

	public String getImg()throws Exception{
		String id=getRequest().getParameter("id");
		cardLayoutDTO=new CardLayoutDTO();
		cardLayoutDTO.setCardLayoutId(id);
		cardLayoutDTO=(CardLayoutDTO)sendService(ConstCode.CARDLAYOUT_SERVICE_VIEW,cardLayoutDTO).getDetailvo();
		getResponse().setContentType("image/jpeg");
		byte[] data =cardLayoutDTO.getPicture();
		if (data == null || data.length == 0) {
			getRequest().getRequestDispatcher("/images/icon/cardLayout.jpg")
					.forward(getRequest(), getResponse());
		} else {
			ServletOutputStream op = getResponse().getOutputStream();
			op.write(data, 0, data.length);
			op.close();
			op = null;
			getResponse().flushBuffer();
		}
		return null;
	}
	
	
	/**
	 * 卡面信息修改
	 * @return
	 * @throws Exception
	 */
	public String update()throws Exception{
		if(picture!=null){
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(picture));
			byte[] b = FileCopyUtils.copyToByteArray(in);
		
			cardLayoutDTO.setPicture(b);
		}
		cardLayoutDTO.setModifyUser(Integer.parseInt(getUser().getUserId()));
		sendService(ConstCode.CARDLAYOUT_SERVICE_UPDATE, cardLayoutDTO);
		if(this.hasErrors()){
			load();
			return "edit";
		}else{
			addActionMessage("卡面信息修改成功！");
		}
		return query();
	}
	
	/**
	 * 卡面信息删除
	 * @return
	 * @throws Exception
	 */
	public String delete()throws Exception{
		List<CardLayoutDTO> cardLayoutDTOs=new ArrayList<CardLayoutDTO>();
		for(String id:choose){
			cardLayoutDTO=new CardLayoutDTO();
			cardLayoutDTO.setCardLayoutId(id);
			cardLayoutDTOs.add(cardLayoutDTO);
		}
		cardLayoutDTO=new CardLayoutDTO();
		cardLayoutDTO.setCardLayoutDTO(cardLayoutDTOs);
		sendService(ConstCode.CARDLAYOUT_SERVICE_DELETE,cardLayoutDTO );
		if(this.hasErrors()){
			cardLayoutQueryDTO=new CardLayoutQueryDTO();
			return query();
		}else{
			addActionMessage("卡面信息删除成功！");
		}
		return query();
	}
	
}

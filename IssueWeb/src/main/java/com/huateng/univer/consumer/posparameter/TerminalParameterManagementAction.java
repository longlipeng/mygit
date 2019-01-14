package com.huateng.univer.consumer.posparameter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.consumer.pos.dto.PosParameterDTO;
import com.allinfinance.univer.consumer.pos.dto.PosParameterQueryDTO;
import com.allinfinance.univer.consumer.pos.dto.posParameterValueDTO;
import com.huateng.framework.action.BaseAction;
import com.huateng.framework.constant.SystemInfoConstants;
import com.huateng.framework.util.SystemInfo;




public class TerminalParameterManagementAction  extends BaseAction{
	Logger logger = Logger.getLogger(TerminalParameterManagementAction.class);

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	
	private static Map<String,String> map;
	static {
		if(map==null){
			map=new HashMap<String,String>();
			map.put("9F06","AID");
			map.put("9F08", "应用版本号");
			map.put("DF01", "ASI");
			map.put("DF11", "TAC-缺省");
			map.put("DF12", "TAC-联机");
			map.put("DF13", "TAC-拒绝");
			map.put("DF18", "终端联机PIN支持");
		}
	}
	
	private PosParameterQueryDTO posParameterQueryDTO;
	
	private PosParameterDTO posParameterDTO;
	
	private posParameterValueDTO posParameterValueDTO;
	public posParameterValueDTO getPosParameterValueDTO() {
		return posParameterValueDTO;
	}

	public void setPosParameterValueDTO(posParameterValueDTO posParameterValueDTO) {
		this.posParameterValueDTO = posParameterValueDTO;
	}

	private PageDataDTO pageDataDTO;
	
	
	private Integer totalRows=0;
	
	
	

	public static java.util.Map<String, String> getMap() {
		return map;
	}

	public static void setMap(java.util.Map<String, String> map) {
		TerminalParameterManagementAction.map = map;
	}

	public PosParameterQueryDTO getPosParameterQueryDTO() {
		return posParameterQueryDTO;
	}

	public void setPosParameterQueryDTO(PosParameterQueryDTO posParameterQueryDTO) {
		this.posParameterQueryDTO = posParameterQueryDTO;
	}

	public PosParameterDTO getPosParameterDTO() {
		return posParameterDTO;
	}

	public void setPosParameterDTO(PosParameterDTO posParameterDTO) {
		this.posParameterDTO = posParameterDTO;
	}

	public PageDataDTO getPageDataDTO() {
		return pageDataDTO;
	}

	public void setPageDataDTO(PageDataDTO pageDataDTO) {
		this.pageDataDTO = pageDataDTO;
	}

	public String inquery() throws Exception{
		if (posParameterQueryDTO == null) {
			posParameterQueryDTO= new PosParameterQueryDTO();
		}
		if(null != posParameterQueryDTO.getPrmId() && !posParameterQueryDTO.getPrmId().equals("")){
			String rex = "^[0-9]*[1-9][0-9]*$";
			if (!String.valueOf(posParameterQueryDTO.getPrmId()).matches(rex)) {
				addActionError("查询失败：终端厂商标识必须是正整数!");
				posParameterQueryDTO=new PosParameterQueryDTO();
			}
		}
		ListPageInit(null, posParameterQueryDTO);
		if(posParameterQueryDTO.isQueryAll()){
			posParameterQueryDTO.setQueryAll(false);
			posParameterQueryDTO.setRowsDisplayed(Integer.parseInt(SystemInfo.getParameterValue(SystemInfoConstants.EXPORT_DATA_MAXIMUM)));
		}
		pageDataDTO = (PageDataDTO) sendService(ConstCode.TERMINAL_PARAMETER_INQUERY,
				posParameterQueryDTO).getDetailvo();
		if(pageDataDTO!=null){   
			totalRows=pageDataDTO.getTotalRecord();
		}
		if(this.hasErrors()){
			return "list";
		}
		return "list";
		
		
	}
	
	/**
	 * 添加通讯参数
	 * @return
	 * @throws Exception
	 */
	public String redirectAdd() throws Exception{
		return "add";
	}
	
	/**
	 * 去查找当前版本下的通讯已添加了哪些
	 */
	public void remainingComPara() throws Exception{
	    HttpSession session=getRequest().getSession();
        if(session.isNew()){
            try {
                getResponse().getWriter().print("true");
                return;
            } catch (IOException e) {               
                this.logger.error(e.getMessage());
            }
        }
	    
	    
	    String prmType=getRequest().getParameter("prmType");
	    String prmVersion=getRequest().getParameter("prmVersion").toString();
	    posParameterDTO=new PosParameterDTO();
	    posParameterDTO.setPrmType(Integer.parseInt(prmType));
	    posParameterDTO.setPrmVersion(Integer.parseInt(prmVersion));
	  String message=(String)sendService(ConstCode.TERMINAL_PARAMETER_COMM_PRM,
	          posParameterDTO).getDetailvo();
	  if(this.hasErrors()){
	      message=(String)(this.getActionErrors().toArray())[0]; 
	  }
	  getResponse().setContentType("application/json; charset=utf-8");
      getResponse().setCharacterEncoding("utf-8");
      getResponse().getWriter().println(message);
      getResponse().getWriter().close();
	}
	
	
	
	public void validateAdd() {
	    
		if(posParameterDTO.getPrmId() == 11){
			if(!posParameterDTO.getPrmVal().matches("^[6][0]")){
				this.addFieldError("posParameterDTO.prmVal", posParameterDTO.getPrmName()+"的值必须是60");
			}
		}else if(posParameterDTO.getPrmId() == 12){
			if(!posParameterDTO.getPrmVal().matches("^[1-9][0-9]")){
				this.addFieldError("posParameterDTO.prmVal", posParameterDTO.getPrmName()+"的值必须是2位数字");
			}
		}else if(posParameterDTO.getPrmId() == 13){
			if(!posParameterDTO.getPrmVal().matches("^[0-9]")){
				this.addFieldError("posParameterDTO.prmVal", posParameterDTO.getPrmName()+"的值必须是1位数字");
			}
		}else if(posParameterDTO.getPrmId() == 14){
		    if(!posParameterDTO.getPrmVal().matches("^[0-9]{1,14}")){
			    this.addFieldError("posParameterDTO.prmVal", posParameterDTO.getPrmName()+"的值是14位内数字");
			}
		}else if(posParameterDTO.getPrmId() == 15){
		    if(!posParameterDTO.getPrmVal().matches("^[0-9]{1,14}")){
			    this.addFieldError("posParameterDTO.prmVal", posParameterDTO.getPrmName()+"的值是14位内数字");
			}
		}else if(posParameterDTO.getPrmId() == 16){
			if(!posParameterDTO.getPrmVal().matches("^(([1-9]|([1-9]\\d)|(1\\d\\d)|(2([0-4]\\d|5[0-5])))\\.)(([1-9]|([1-9]\\d)|(1\\d\\d)|(2([0-4]\\d|5[0-5])))\\.){2}([1-9]|([1-9]\\d)|(1\\d\\d)|(2([0-4]\\d|5[0-5])))$")){
				this.addFieldError("posParameterDTO.prmVal", posParameterDTO.getPrmName()+"的值必须是一个IP地址的格式");
			}	
		}else if(posParameterDTO.getPrmId() == 17){
			if(!posParameterDTO.getPrmVal().matches("^[1-9]{1}[0-9]{3,4}")){
				this.addFieldError("posParameterDTO.prmVal",posParameterDTO.getPrmName()+"的值必须是一个四或五位的数字");
			}
			
		}else if(posParameterDTO.getPrmId() == 18){
			if(!posParameterDTO.getPrmVal().matches("^(([1-9]|([1-9]\\d)|(1\\d\\d)|(2([0-4]\\d|5[0-5])))\\.)(([1-9]|([1-9]\\d)|(1\\d\\d)|(2([0-4]\\d|5[0-5])))\\.){2}([1-9]|([1-9]\\d)|(1\\d\\d)|(2([0-4]\\d|5[0-5])))$")){
				this.addFieldError("posParameterDTO.prmVal", posParameterDTO.getPrmName()+"的值必须是一个IP地址的格式");
			}		
		}else if(posParameterDTO.getPrmId() == 19){
			if(!posParameterDTO.getPrmVal().matches("^[1-9]{1}[0-9]{3,4}")){
				this.addFieldError("posParameterDTO.prmVal",posParameterDTO.getPrmName()+"的值必须是一个四或五位的数字");
			}
			
		}else if(posParameterDTO.getPrmId() == 23){
			if(!posParameterDTO.getPrmVal().matches("[0-9]")){
				this.addFieldError("posParameterDTO.prmVal",posParameterDTO.getPrmName()+"的值必须是一个1位的数字");
			}
		}
		else if(posParameterDTO.getPrmId() == 24){
			if(!posParameterDTO.getPrmVal().matches("^([1]|[0])")){
				this.addFieldError("posParameterDTO.prmVal",posParameterDTO.getPrmName()+"的值 0代表不控制 1代表控制");
			}
		}
		else{
			this.addFieldError("posParameterDTO.prmName","参数名称必须选择");
		}
	}
	
	
	public String add() throws Exception{
		posParameterDTO.setPrmVersion(posParameterDTO.getPrmVersionInt());
		sendService(ConstCode.TERMINAL_PARAMETER_ADD,
				posParameterDTO);
		if(this.hasErrors()){
			return "input";
		}else{
			this.addActionMessage("添加公共参数成功!");
		}
		inquery();
		return "list";
	}
	
	
	
	public String view() throws Exception{
		
		if(posParameterDTO==null){
			posParameterDTO=new PosParameterDTO();
		}
		String key=getRequest().getParameter("key");
		if(key!=null && !"".equals(key)){
			String[] temp=key.split(",");
			posParameterDTO.setPrmId(Integer.parseInt(temp[0]));
			posParameterDTO.setPrmType(Integer.parseInt(temp[1]));
			posParameterDTO.setPrmVersion(Integer.parseInt(temp[2]));
		}
		posParameterDTO=(PosParameterDTO)sendService(ConstCode.TERMINAL_PARAMETER_VIEW,
				posParameterDTO).getDetailvo();
		if(this.hasErrors()){
			return "failed";
		}
		return "view";
	}
	
	public String load() throws Exception{
		if(posParameterDTO==null){
			posParameterDTO=new PosParameterDTO();
		}
		String key=getRequest().getParameter("key");
		if(key!=null && !"".equals(key)){
			String[] temp=key.split(",");
			posParameterDTO.setPrmId(Integer.parseInt(temp[0]));
			posParameterDTO.setPrmType(Integer.parseInt(temp[1]));
			posParameterDTO.setPrmVersion(Integer.parseInt(temp[2]));
		}
		posParameterDTO=(PosParameterDTO)sendService(ConstCode.TERMINAL_PARAMETER_VIEW,
				posParameterDTO).getDetailvo();
		if(this.hasErrors()){
			return "failed";
		}
		return "edit";
	}
	
	public void validateUpdate() throws Exception{
		if(this.hasFieldErrors()){
		if(posParameterDTO.getPrmId() == 11){
			if(!posParameterDTO.getPrmVal().matches("^[6][0]")){
				this.addFieldError("posParameterDTO.prmVal", posParameterDTO.getPrmName()+"的值必须是60");
			}
		}else if(posParameterDTO.getPrmId() == 12){
			if(!posParameterDTO.getPrmVal().matches("^[1-9][0-9]")){
				this.addFieldError("posParameterDTO.prmVal", posParameterDTO.getPrmName()+"的值必须是2位数字");
			}
		}else if(posParameterDTO.getPrmId() == 13){
			if(!posParameterDTO.getPrmVal().matches("^[0-9]")){
				this.addFieldError("posParameterDTO.prmVal", posParameterDTO.getPrmName()+"的值必须是1位数字");
			}
		}else if(posParameterDTO.getPrmId() == 14){
			if(!posParameterDTO.getPrmVal().matches("^[0-9]{1,14}")){
			    this.addFieldError("posParameterDTO.prmVal", posParameterDTO.getPrmName()+"的值是14位内数字");
			}
		}else if(posParameterDTO.getPrmId() == 15){
			if(!posParameterDTO.getPrmVal().matches("^[0-9]{1,14}")){
			    this.addFieldError("posParameterDTO.prmVal", posParameterDTO.getPrmName()+"的值是14位内数字");
			}
		}else if(posParameterDTO.getPrmId() == 16){
			if(!posParameterDTO.getPrmVal().matches("^(([1-9]|([1-9]\\d)|(1\\d\\d)|(2([0-4]\\d|5[0-5])))\\.)(([1-9]|([1-9]\\d)|(1\\d\\d)|(2([0-4]\\d|5[0-5])))\\.){2}([1-9]|([1-9]\\d)|(1\\d\\d)|(2([0-4]\\d|5[0-5])))$")){
				this.addFieldError("posParameterDTO.prmVal", posParameterDTO.getPrmName()+"的值必须是一个IP地址的格式");
			}	
		}else if(posParameterDTO.getPrmId() == 17){
			if(!posParameterDTO.getPrmVal().matches("^[1-9][0-9][0-9][0-9]")){
				this.addFieldError("posParameterDTO.prmVal",posParameterDTO.getPrmName()+"的值必须是一个四位的数字");
			}
			
		}else if(posParameterDTO.getPrmId() == 18){
			if(!posParameterDTO.getPrmVal().matches("^(([1-9]|([1-9]\\d)|(1\\d\\d)|(2([0-4]\\d|5[0-5])))\\.)(([1-9]|([1-9]\\d)|(1\\d\\d)|(2([0-4]\\d|5[0-5])))\\.){2}([1-9]|([1-9]\\d)|(1\\d\\d)|(2([0-4]\\d|5[0-5])))$")){
				this.addFieldError("posParameterDTO.prmVal", posParameterDTO.getPrmName()+"的值必须是一个IP地址的格式");
			}		
		}else if(posParameterDTO.getPrmId() == 19){
			if(!posParameterDTO.getPrmVal().matches("^[1-9][0-9][0-9][0-9]")){
				this.addFieldError("posParameterDTO.prmVal",posParameterDTO.getPrmName()+"的值必须是一个4位的数字");
			}
			
		}else if(posParameterDTO.getPrmId() == 23){
			if(!posParameterDTO.getPrmVal().matches("[0-9]")){
				this.addFieldError("posParameterDTO.prmVal",posParameterDTO.getPrmName()+"的值必须是一个1位的数字");
			}
		}else if(posParameterDTO.getPrmId() == 24){
			if(!posParameterDTO.getPrmVal().matches("^([1]|[0])")){
				this.addFieldError("posParameterDTO.prmVal",posParameterDTO.getPrmName()+"的值 0代表不控制 1代表控制");
			}
		}else {
			this.addFieldError("posParameterDTO.prmName","参数名称必须选择");
		}
		}
		if(posParameterDTO.getPrmType() ==  10002){
			if(!posParameterDTO.getPrmVal().matches("[0-9]{2,8}")){
				this.addFieldError("posParameterDTO.prmVal",posParameterDTO.getPrmName()+"的值必须是一个2到8位的数字");
			}	
		}
			
			
		
	}
	
	
	public String update() throws Exception{
		sendService(ConstCode.TERMINAL_PARAMETER_EDIT,
				posParameterDTO).getDetailvo();
		if(this.hasErrors()){
			return "failed";
		}else{
			this.addActionMessage("更新终端参数成功");
		}
		return inquery();
	}
	
	/**
	 * 添加卡BIN参数，跳转JSP
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, String>> getCardBinList(String userId) {

		if(posParameterValueDTO == null){
			
			posParameterValueDTO=new posParameterValueDTO();
		}
		posParameterValueDTO.setUserId(getUser().getUserId());
//		sendService(ConstCode.TERMINAL_PARAMETER_CARD_BIN_LIST, posParameterDTO).getDetailvo();
		 List<posParameterValueDTO> pDTO=(List<posParameterValueDTO>) sendService(ConstCode.TERMINAL_PARAMETER_CARD_BIN_LIST, posParameterValueDTO).getDetailvo();
		 List<Map<String, String>> cardbinList= new ArrayList<Map<String, String>>();
		 for(posParameterValueDTO posParameterValue:pDTO){
			   Map<String, String> cardbin = new HashMap<String, String>();
			   cardbin.put("cardBin",formatLen8(posParameterValue.getCardBin()));
			   cardbinList.add(cardbin);
		 }
		 return cardbinList;
	    }
	
	public String formatLen8(String vCardBin){
		try{
			if (vCardBin == null || vCardBin.trim().length() == 0){
				logger.error("card bin is null!");
				return "";
			}
			
			for (int i = vCardBin.length(); i < 8; i++){
				vCardBin = vCardBin + "0";
			}
			
		}catch (Exception ex){
			logger.error("format card bin to length 8 error! " + ex.getMessage());
		}
		
		return vCardBin;
	}
	
	public String redirectAddkaBin() throws Exception{
		//by wld
		String userId=getUser().getEntityId();
		 cardBinList=getCardBinList(userId);
		
		return "kaBinAdd";
	}
	
	/**
 	 *添加IC卡参数，跳转JSP
	 * 
	 */
	public String redirectAddIcCard() throws Exception{
		return "IcCardAdd";
	}
	
	
	/**
	 * 添加卡BIN参数
	 * @throws Exception 
	 * 
	 */
	
	public void validateAddkaBin() throws Exception{
		if("".equals(posParameterDTO.getPrmVal())){
			this.addFieldError("posParameterDTO.prmVal", "卡BIN值不能为空");
		}
		if(posParameterDTO.getPrmDesc()!=null && posParameterDTO.getPrmDesc().length()>64){
			this.addFieldError("posParameterDTO.prmDesc", "不超过64位");
		}
		if(posParameterDTO.getPrmType().equals("10002")){
			if(!posParameterDTO.getPrmVal().matches("[0-9]{2,8}")){
				this.addFieldError("posParameterDTO.prmVal",posParameterDTO.getPrmName()+"的值必须是一个2到8位的数字");
			}	
		}
		if(hasErrors()){
			String userId=getUser().getEntityId();
			cardBinList=getCardBinList(userId);
		}
	}
	
	// 卡bin列表
	List<Map<String,String>> cardBinList = new ArrayList<Map<String,String>>();

	public List<Map<String, String>> getCardBinList() {
		return cardBinList;
	}

	public void setCardBinList(List<Map<String, String>> cardBinList) {
		this.cardBinList = cardBinList;
	}

	public String addkaBin() throws Exception{
		sendService(ConstCode.TERMINAL_PARAMETER_CARD_BIN_ADD,
				posParameterDTO);
		if(this.hasErrors()){
			String userId=getUser().getEntityId();
			cardBinList=getCardBinList(userId);
			return "input";
		}else{
			this.addActionMessage("添加卡BIN参数成功!");
		}
		inquery();
		return "list";
		
	}

	/**
	 * 添加IC卡参数
	 */
	public String addIcCard() throws Exception{
		sendService(ConstCode.TERMINAL_PARAMETER_IC_CARD_ADD,
				posParameterDTO);
		if(this.hasErrors()){
			return "input";
		}else{
			this.addActionMessage("添加IC卡参数成功!");
		}
		inquery();
		return "list";
	}
	
	
	
	/**
	 * 
	 *
	 * 
	 */
	public String check(String prmName){
		if(map.get(prmName)!=null){
			return map.get(prmName);
		}
		return prmName;
	}
	
	public String del() throws Exception{
		return "";
	}

	public Integer getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(Integer totalRows) {
		this.totalRows = totalRows;
	}
	
}

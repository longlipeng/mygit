package com.huateng.univer.seller.blacklist;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.seller.blacklist.dto.BlackListAreaDTO;
import com.allinfinance.univer.seller.blacklist.dto.BlackListBatchDTO;
import com.allinfinance.univer.seller.blacklist.dto.BlackListPerDTO;
import com.huateng.framework.action.BaseAction;
import com.huateng.framework.exception.BizServiceException;

public class BlackListAction extends BaseAction {

	private File batchFile; // 批量文件
	private BlackListBatchDTO blackListDTO=new BlackListBatchDTO();//  黑名单列表
	private PageDataDTO pageDataDTO=new PageDataDTO();
	private BlackListPerDTO blackListPerDTO=new BlackListPerDTO();//人员黑名单
	private BlackListAreaDTO blackListAreaDTO=new BlackListAreaDTO();//地区黑名单
	private Integer totalRows = 0;
	private String blackListFlag;
	 
	 

	

	public String inquery() throws Exception {
	
		if("2".equals(blackListDTO.getFlag())){//TODO 如果为2  地区黑名单查询   其他情况默认是持卡人黑名单查询
			ListPageInit(null, blackListAreaDTO);
			
			pageDataDTO=(PageDataDTO)this.sendService(ConstCode.BLACKLIST_INQUERY_AREA, blackListAreaDTO).getDetailvo();
			this.getRequest().setAttribute("balackListFlag", "area");
			if(pageDataDTO!=null){//设置  分页的总条数
				totalRows = pageDataDTO.getTotalRecord();
			}
			if (this.hasErrors()) {
				this.addActionError("查询失败！");	
				
			}
			
			return "view";
		}
		else {
		ListPageInit(null, blackListPerDTO);//  页面写id  也要赋值ID
		
		pageDataDTO=(PageDataDTO)this.sendService(ConstCode.BLACKLIST_INQUERY_PERSON, blackListPerDTO).getDetailvo();
		this.getRequest().setAttribute("balackListFlag", "person");// 持卡人黑名单为1
		if(pageDataDTO!=null){//设置  分页的总条数
			totalRows = pageDataDTO.getTotalRecord();
		}
		if (this.hasErrors()) {
			this.addActionError("查询失败！");				
		}
		return "view";
		}
		
		
	}

	// 导入黑名单文件
	public String toImportFile() throws BizServiceException {
		return "toImportFile";
	}

	// 批量操作     
	public String importBlackListFile() throws BizServiceException {

		List<String> blacklist = new ArrayList<String>();
		blacklist = importCsv(batchFile);// 文件list
		if (blacklist.size()<=0){
			this.addActionError("文件不能为空文件！");
			return INPUT;
		}
			
//		String p = cardList.get(0).trim().replaceAll(",", "");
		if(StringUtils.isEmpty(blacklist.get(0).trim())){
			this.addActionError("第1行表头不能为空，请检查文件！");
			return INPUT;
		}
		if((blacklist.get(0).split(",").length==3&&blackListDTO.getFlag().equals("1"))||
				(blacklist.get(0).split(",").length==11&&blackListDTO.getFlag().equals("2"))){
			this.addActionError("文件类型与所选文件不匹配");						
			return INPUT;	
			
		}

		try{
		
		//解析list里面的数据  根据操作类型 发送交易   
		if(blackListDTO.getFlag().equals("1")){ // 插入人员黑名单
			
			String regCode="[0-9]{0,32}";
			String regEntityType="[0-9]";
			Pattern codePattern=Pattern.compile(regCode);
			Pattern entityTypePattern=Pattern.compile(regEntityType);
			
			List<BlackListPerDTO> plist=new ArrayList<BlackListPerDTO>();
			for(int i=1;i<blacklist.size();i++){
				BlackListPerDTO pDTO=new BlackListPerDTO();
				String [] personArr=	blacklist.get(i).split(",");
				/*格式校验start*/
				
				for (String arr : personArr) {//检验是否包含特殊字符',' 以免影响切割
					if(arr.contains("\"")){
						this.addActionError("第"+i+"条不能包含特殊字符：英文符号','号和\"号等");						
						return INPUT;						
					}
					
				}
			
				
	
				for(int j=0;j<personArr.length;j++){
					 Matcher codeMacher=codePattern.matcher(personArr[0].trim());
					 boolean codeRs = codeMacher.matches();					
					 if(!codeRs){
						 this.addActionError("第"+i+"条：编号只能为数字且长度不超过32");				
							return INPUT;
					 }
					
					if(personArr[1].trim().equals("")||personArr[1].trim().length()>32||personArr[1]==null){
					this.addActionError("第"+i+"条名称不能为空或者长度不能超过32");
						return INPUT;
					}						
					pDTO.setName(personArr[1].trim());
					
					if(personArr[2].trim().length()>5){
						this.addActionError("第"+i+"条:性别信息长度错误,不超过5");
						return INPUT;
					}	
						pDTO.setSex(personArr[2].trim());
						
					
					
					if(personArr[3].trim().length()>16){
						this.addActionError("第"+i+"条出生日期长度超过限制不能超过16位");
						return INPUT;
					}
					pDTO.setBirthday(personArr[3].trim());
					if(personArr[4].trim().length()>32){
						this.addActionError("第"+i+"条国家长度超过限制不能超过32位");
						return INPUT;
					}
					
					pDTO.setCountry(personArr[4].trim());
					if(personArr[5].trim().equals("")||(personArr[5].trim().length()>256)){
						this.addActionError("第"+i+"条行证件不能为空或者长度超过256位");						
						return INPUT;		
					}
					pDTO.setIdnumbers(personArr[5].trim());
					if(personArr.length>=7){
						if((personArr[6].trim().length()>32)){
							this.addActionError("第"+i+"条名单类别长度超过32位");						
							return INPUT;
						}
						
						pDTO.setListOfType(personArr[6].trim());
					}
					
					if(personArr.length>=8){
						if((personArr[7].trim().length()>32)){
							this.addActionError("第"+i+"条名单来源长度超过32位");						
							return INPUT;
						}
						pDTO.setListOfSource(personArr[7].trim());
					}
					
					if(personArr.length>=9){
						 Matcher entityTypeMacher=entityTypePattern.matcher(personArr[8].trim());
						 boolean entityTypeRs = entityTypeMacher.matches();					
						 if(!entityTypeRs){
							 this.addActionError("第"+i+"条：实体类型只能为数字且长度为1");				
								return INPUT;
						 }
						pDTO.setEntityType(personArr[8].trim());
					}
					
					if(personArr.length>=10){
						if((personArr[9].trim().length()>128)){
							this.addActionError("第"+i+"条：地址长度超过128位");						
							return INPUT;
						}
						pDTO.setAddress(personArr[9].trim());
					}
					
					if(personArr.length>=11){
						if((personArr[10].trim().length()>256)){
							this.addActionError("第"+i+"条：描述信息超过256位");						
							return INPUT;
						}
						pDTO.setDescription(personArr[10].trim());
					}
					
					/*格式校验end*/				
				}
				plist.add(pDTO);
					
				}
			blackListDTO.setPersonList(plist);
			this.sendService(ConstCode.BLACKLIST_INSERT, blackListDTO);
			if (this.hasErrors()) {				
				return INPUT;
			}
		}
		if(blackListDTO.getFlag().equals("2")){// 插入人地区黑名单
			
			String regAreaType="[0-9]";
			String regAreaCode="[0-9A-Za-z]{0,6}";
			Pattern areaTypePattern=Pattern.compile(regAreaType);
			Pattern areaCodePattern=Pattern.compile(regAreaCode);
			
			List<BlackListAreaDTO> areaList=new ArrayList<BlackListAreaDTO>();
			for(int i=1;i<blacklist.size();i++){
				BlackListAreaDTO areaDTO=new BlackListAreaDTO();
				String [] areaArr=	blacklist.get(i).split(",");
				for (String arr : areaArr) {//检验是否包含特殊字符',' 以免影响切割
					if(arr.contains("\"")){
						this.addActionError("第"+i+"条不能包含特殊字符：英文符号','号和\"号等");						
						return INPUT;						
					}
					
				}
				if(areaArr.length<3){//  地区代码 地区名称不能为空
					this.addActionError("第"+i+"条缺少信息");						
					return INPUT;
					
				}
				
				for(int j=0;j<areaArr.length;j++){
					/*格式校验 start*/
					Matcher areaTypeMatcher=areaTypePattern.matcher(areaArr[0].trim());
					 boolean areaTypeRs = areaTypeMatcher.matches();
					 if(!areaTypeRs){
						 this.addActionError("第"+i+"条：地区类型只能为数字长度为1");				
							return INPUT;
					 }
					 Matcher areaCodeMacher=areaCodePattern.matcher(areaArr[1].trim());
					 boolean areaCodeRs = areaCodeMacher.matches();
					 if(!areaCodeRs){
						 this.addActionError("第"+i+"条：地区码只能为数字和字母且长度不超过6");				
							return INPUT;
					 }
					 if(areaArr[2].trim().length()>128){
						 this.addActionError("第"+i+"条：地区名称长度超过限制，不超过128");				
							return INPUT;
					 }
					 /*格式校验 end*/
					 
					areaDTO.setAreaType(areaArr[0].trim());
					areaDTO.setAreaCode(areaArr[1].trim());
					areaDTO.setAreaName(areaArr[2].trim());				
					
					
				}
				
				areaList.add(areaDTO);
				
			}
			for (BlackListAreaDTO blackListAreaDTO : areaList) {
				System.out.println(blackListAreaDTO);
			}
			blackListDTO.setAreaList(areaList);
			this.sendService(ConstCode.BLACKLIST_INSERT, blackListDTO);
			if (this.hasErrors()) {
				return INPUT;
			}
			

		}
		}catch(Exception e){
			this.addActionError("文件格式可能有误，请检查文件");
			return INPUT;
		}
		
		this.addActionMessage("导入成功");
		return SUCCESS;
	}
	

	public File getBatchFile() {
		return batchFile;
	}

	public void setBatchFile(File batchFile) {
		this.batchFile = batchFile;
	}
	public PageDataDTO getPageDataDTO() {
		return pageDataDTO;
	}

	public void setPageDataDTO(PageDataDTO pageDataDTO) {
		this.pageDataDTO = pageDataDTO;
	}
	

	public BlackListPerDTO getBlackListPerDTO() {
		return blackListPerDTO;
	}

	public void setBlackListPerDTO(BlackListPerDTO blackListPerDTO) {
		this.blackListPerDTO = blackListPerDTO;
	}
	public Integer getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(Integer totalRows) {
		this.totalRows = totalRows;
	}

	public BlackListBatchDTO getBlackListDTO() {
		return blackListDTO;
	}

	public void setBlackListDTO(BlackListBatchDTO blackListDTO) {
		this.blackListDTO = blackListDTO;
	}

	public BlackListAreaDTO getBlackListAreaDTO() {
		return blackListAreaDTO;
	}

	public void setBlackListAreaDTO(BlackListAreaDTO blackListAreaDTO) {
		this.blackListAreaDTO = blackListAreaDTO;
	}

	public String getBlackListFlag() {
		return blackListFlag;
	}

	public void setBlackListFlag(String blackListFlag) {
		this.blackListFlag = blackListFlag;
	}

	/**
	 * 读取文件 工具方法
	 */
	public static List<String> importCsv(File file) {
		List<String> dataList = new ArrayList<String>();

		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "GBK"));
			String line = "";
			while ((line = br.readLine()) != null) {
				dataList.add(line);
			}
		} catch (Exception e) {

		} finally {
			if (br != null) {
				try {
					br.close();
					br = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return dataList;
	}
	
}

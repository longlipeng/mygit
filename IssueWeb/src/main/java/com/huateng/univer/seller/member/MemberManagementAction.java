package com.huateng.univer.seller.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.seller.member.MemberQueryDTO;
import com.huateng.framework.util.DataChange;
import com.huateng.framework.util.DateUtil;
import com.huateng.univer.entity.EntityBaseAction;

public class MemberManagementAction extends EntityBaseAction{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(MemberManagementAction.class);
	private MemberQueryDTO memberQueryDTO = new MemberQueryDTO();
    private PageDataDTO pageDataDTO = new PageDataDTO();
    private int totalRows;
    
    public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public PageDataDTO getPageDataDTO() {
		return pageDataDTO;
	}

	public void setPageDataDTO(PageDataDTO pageDataDTO) {
		this.pageDataDTO = pageDataDTO;
	}

	public MemberQueryDTO getMemberQueryDTO() {
		return memberQueryDTO;
	}

	public void setMemberQueryDTO(MemberQueryDTO memberQueryDTO) {
		this.memberQueryDTO = memberQueryDTO;
	}

	@Override
	public void initEntityId() throws Exception {
		
		
	}

	@Override
	public void initNameSpace() throws Exception {
		
		
	}

	@Override
	public String reloadForEntity() throws Exception {
		
		return null;
	}
	 /**
     * 会员信息查询
     * 
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
	public String memberInfoInquery(){
		
        try {
			ListPageInit(null, memberQueryDTO);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
       
        memberQueryDTO.setEntityId(this.getUser().getEntityId());
        pageDataDTO = (PageDataDTO) sendService(ConstCode.CARDMANAGEMENT_SERVICE_INQUERYMEMBERINFO,
        		memberQueryDTO).getDetailvo();   
        /** 重组pageDataDTO中的data数据 **/
        if (pageDataDTO != null) {
			if (pageDataDTO.getData().size() > 0) {
				totalRows = pageDataDTO.getTotalRecord();
				List<Map> dataList;
				List<Map> newDataList = new ArrayList<Map>();
				dataList = (List<Map>) pageDataDTO.getData();

				for (int i = 0; i < dataList.size(); i++) {
					Map dataMap = dataList.get(i);
					String gener =(String) dataMap.get("memGener");
					if(gener.equals("1")){
						gener="男";						
					}
					else if(gener.equals("2")){
						gener="女";						
					}					
					String regCardTp = (String)dataMap.get("regCardTp");
					if(regCardTp.equals("01")){
						regCardTp="实体卡注册";						
					}
					else if(regCardTp.equals("02")){
						regCardTp="在线卡注册";						
					}
					
					String memBirthdaydata = (String)dataMap.get("memBirthday");
					Map newDataMap = new HashMap();
					newDataMap.put("memName", dataMap.get("memName"));
					newDataMap.put("memEmail", dataMap.get("memEmail"));
					newDataMap.put("memBirthday", DateUtil.dbFormatToDateFormat(memBirthdaydata));
					newDataMap.put("certNo", dataMap.get("certNo"));
					newDataMap.put("mobile", dataMap.get("mobile"));
					newDataMap.put("creatDate", dataMap.get("creatDate"));
					newDataMap.put("memGener", gener);
					newDataMap.put("certTp", dataMap.get("certTp"));
					newDataMap.put("regCardTp", regCardTp);
					newDataList.add(newDataMap);

				}
				pageDataDTO.setData(newDataList);
				
				
			} else {
				totalRows = 0;
			}
		} else {
			totalRows = 0;
		}

        return "query";
    }
	
    /**会员详细信息查询**/
    public String memberDetailInfoInquery(){
    	
    	try {
			memberQueryDTO=(MemberQueryDTO)sendService(ConstCode.CARDMANAGEMENT_SERVICE_INQUERY_MEMBERDETAILINFO,
					memberQueryDTO).getDetailvo();
			
			if(memberQueryDTO!=null){
			memberQueryDTO.setSexDiffer(DataChange.getGenderDesc(memberQueryDTO.getSex()));
			memberQueryDTO.setBirDay(DataChange.getBirthDay(memberQueryDTO.getBirthday()));
			memberQueryDTO.setBirMonth(DataChange.getBirthMonth(memberQueryDTO.getBirthday()));
			memberQueryDTO.setBirYear(DataChange.getBirthYear(memberQueryDTO.getBirthday()));
			String regCardTp = memberQueryDTO.getRegCardTp();
			if(regCardTp.equals("01")){
				regCardTp="实体卡注册";						
			}
			else if(regCardTp.equals("02")){
				regCardTp="在线卡注册";						
			}
			memberQueryDTO.setRegCardTp(regCardTp);
			if (memberQueryDTO.getCorpTel() != null){
				memberQueryDTO.setComPhoneSection(memberQueryDTO.getCorpTel().substring(0, memberQueryDTO.getCorpTel().indexOf("-")));
				memberQueryDTO.setComPhoneNo(memberQueryDTO.getCorpTel().substring(memberQueryDTO.getCorpTel().indexOf("-") + 1));
			}
			}
			List<MemberQueryDTO> memberQueryDTOs = new ArrayList<MemberQueryDTO>();
			memberQueryDTOs=(List<MemberQueryDTO>)sendService(ConstCode.CARDMANAGEMENT_SERVICE_INQUERY_MEMBERD_RLT_CARD,
					memberQueryDTO).getDetailvo();
			memberQueryDTO.setMemberQueryDTOs(memberQueryDTOs);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
    	
    	return "querySucc";
    }
}

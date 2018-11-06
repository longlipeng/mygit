package com.huateng.univer.servicefeerule;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.framework.dto.PageQueryDTO;
import com.allinfinance.univer.consumercontract.dto.ConsumerContractDTO;
import com.allinfinance.univer.servicefeerule.dto.CaclDspDTO;
import com.allinfinance.univer.servicefeerule.dto.CaclInfDTO;
import com.allinfinance.univer.servicefeerule.dto.CaclInfQueryDTO;
import com.allinfinance.univer.servicefeerule.dto.CaclInfView;
import com.huateng.framework.action.BaseAction;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.StringUtils;

public class CaclInfAction extends BaseAction {
	private Logger logger = Logger.getLogger(CaclInfAction.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CaclInfDTO caclInfDTO= new CaclInfDTO(); 
	private CaclDspDTO caclDspDTO= new CaclDspDTO(); 
	private PageQueryDTO pageQueryDTO = new PageQueryDTO();
	private PageDataDTO pageDataDTO;
	private int totalRows; 
	//0 编辑   1预览   2 启用  3删除
	private int state = 0;
	private int flag = 0;
	private  List<String> feeRuleIdList;
	private String consumerContractId="";
	private ConsumerContractDTO consumerContractDTO;
	String disccd;
	private CaclInfQueryDTO caclInfQueryDTO = new CaclInfQueryDTO();
	private List<String> serviceFeeRuleDTORuleStep;
	private List<String> serviceFeeRuleDTORateType;
	private List<String> serviceFeeRuleDTORateValue;
	private List<String> serviceFeeRuleDTOFeeMin;
	private List<String> serviceFeeRuleDTOFeeMax;
	private List<String> serviceFeeRuleDTOAmountStart;
	private List<String> serviceFeeRuleDTOAmountEnd;
	private String splitProfit ;
	private CaclInfView view;
	
	public ConsumerContractDTO getConsumerContractDTO() {
		return consumerContractDTO;
	}


	public void setConsumerContractDTO(ConsumerContractDTO consumerContractDTO) {
		this.consumerContractDTO = consumerContractDTO;
	}


	public String getConsumerContractId() {
		return consumerContractId;
	}


	public void setConsumerContractId(String consumerContractId) {
		this.consumerContractId = consumerContractId;
	}


	public int getFlag() {
		return flag;
	}


	public void setFlag(int flag) {
		this.flag = flag;
	}


	public CaclInfView getView() {
		return view;
	}


	public void setView(CaclInfView view) {
		this.view = view;
	}


	public String getSplitProfit() {
		return splitProfit;
	}


	public void setSplitProfit(String splitProfit) {
		this.splitProfit = splitProfit;
	}


	public String AddCaclInf() {
		caclInfQueryDTO.getCaclDspDTO().setDataStat("1");
		//机构号不足八位，补足八位
		String entityId = StringUtils.rpad(getUser().getEntityId(),8," ");
		caclInfQueryDTO.getCaclDspDTO().setRecUpdUsrId(entityId);
		caclInfQueryDTO.getCaclDspDTO().setRecCrtTs(DateUtil.getCurrentDate());
		caclInfQueryDTO.getCaclDspDTO().setRecUpdTs(DateUtil.getCurrentDate());
		caclDspDTO=caclInfQueryDTO.getCaclDspDTO();
		logger.info("cacldsp:"+caclDspDTO);
		caclDspDTO=(CaclDspDTO)sendService(ConstCode.CACLINF_INSERT, caclDspDTO).getDetailvo();
		if(hasErrors()){
			return "input";
		}
		else{
			
			this.addActionMessage("添加计算规则成功！");
		}
		caclInfQueryDTO.setCaclDspDTO(caclDspDTO);
		logger.info(caclInfQueryDTO.getCaclDspDTO().getDiscCd());
		disccd=caclInfQueryDTO.getCaclDspDTO().getDiscCd();
		addInf();
		if(flag==1){
			consumerContractId=consumerContractDTO.getConsumerContractId();
			return "editConsumerContract";
			
		}
		else{
			caclInfQueryDTO.getCaclDspDTO().setDiscCd(null);
		    caclInfQueryDTO.getCaclDspDTO().setDataStat(null);
		    listCaclInf();
		    return "list";
		}
		
	}

	
	public String addInf(){
	    if (null !=serviceFeeRuleDTORuleStep
		//if (!"".equals(serviceFeeRuleDTORuleStep)
				&& serviceFeeRuleDTORuleStep.size() > 0) {
			int size = serviceFeeRuleDTORuleStep.size(); // 3
			logger.debug("本金范围大小："+size);
			for (int i = 1; i < serviceFeeRuleDTORuleStep.size()+1; i++) {
				logger.debug("本金范围1");
				caclInfDTO.setStepNo(i-1); // 0
				caclInfDTO.setOperRslt(++size); // 4
				logger.info(size);
				
				caclInfDTO.setOperand1(String.valueOf(Double.parseDouble(serviceFeeRuleDTOAmountStart.get(i))*100));
				
				logger.debug(i+". start:"+serviceFeeRuleDTOAmountStart);
				caclInfDTO.setOperator1("L");
				caclInfDTO.setOperand2(Long.parseLong("-1"));
				caclInfDTO.setOperator2("L");
				
					caclInfDTO.setOperand3(String.valueOf(Double
						.parseDouble(serviceFeeRuleDTOAmountEnd.get(i))*100));
				
				logger.debug(i+". end:"+serviceFeeRuleDTOAmountEnd);
				caclInfQueryDTO.setCaclInfDTO(caclInfDTO);
				caclInfQueryDTO.getCaclInfDTO().setDiscCd(disccd);
				//机构号不足八位，补足八位
				String entityId = StringUtils.rpad(getUser().getEntityId(),8," ");
				caclInfQueryDTO.getCaclInfDTO().setRecUpdUsrId(entityId);
				sendService(ConstCode.CACLINF_INSERTINF, caclInfQueryDTO.getCaclInfDTO());
				if(hasErrors()){
					return "input";
				}
				logger.debug("手续费:"+serviceFeeRuleDTORateValue);
				caclInfDTO.setStepNo(size); // 4
				logger.info(size);
				caclInfDTO.setOperRslt(-10);
				caclInfDTO.setOperand1("-1");
				caclInfDTO.setOperator1("*");
				
					caclInfDTO.setOperand2(Long.parseLong(serviceFeeRuleDTORateValue.get(i)));
				
				caclInfDTO.setOperator2("/");
				logger.info("type:" + serviceFeeRuleDTORateType.get(i));
				
					caclInfDTO.setOperand3(serviceFeeRuleDTORateType.get(i));
				
				caclInfQueryDTO.setCaclInfDTO(caclInfDTO);
				caclInfQueryDTO.getCaclInfDTO().setDiscCd(disccd);
				
				caclInfQueryDTO.getCaclInfDTO().setRecUpdUsrId(entityId);
				sendService(ConstCode.CACLINF_INSERTINF, caclInfQueryDTO.getCaclInfDTO());
				if(hasErrors()){
					return "input";
				}
				logger.debug("手续费 最小值"+serviceFeeRuleDTOFeeMin);
				if (isNotEmpty(serviceFeeRuleDTOFeeMin.get(i))) {
					caclInfDTO.setStepNo(++size); // 5
					caclInfDTO.setOperRslt(-10);
					
					caclInfDTO.setOperand1(String.valueOf(Double
							.parseDouble(serviceFeeRuleDTOFeeMin.get(i))*100));
					
					caclInfDTO.setOperator1("<");
					caclInfDTO.setOperand2(Long.parseLong("-10"));
					caclInfDTO.setOperator2("<");
					
					caclInfDTO.setOperand3(String.valueOf(Double
							.parseDouble(serviceFeeRuleDTOFeeMin.get(i))*100));
					
					caclInfQueryDTO.setCaclInfDTO(caclInfDTO);
					caclInfQueryDTO.getCaclInfDTO().setDiscCd(disccd);
					caclInfQueryDTO.getCaclInfDTO().setRecUpdUsrId(entityId);
					sendService(ConstCode.CACLINF_INSERTINF, caclInfQueryDTO.getCaclInfDTO());
					if(hasErrors()){
						return "input";
					}
				}
				logger.debug("手续费 最小值"+serviceFeeRuleDTOFeeMax);
				if (isNotEmpty(serviceFeeRuleDTOFeeMax.get(i))) {
					caclInfDTO.setStepNo(++size); // 6
					caclInfDTO.setOperRslt(-10);
					caclInfDTO.setOperand1(String.valueOf(Double
							.parseDouble(serviceFeeRuleDTOFeeMax.get(i))*100));
					caclInfDTO.setOperator1(">");
					caclInfDTO.setOperand2(Long.parseLong("-10"));
					caclInfDTO.setOperator2(">");
					caclInfDTO.setOperand3(String.valueOf(Double
							.parseDouble(serviceFeeRuleDTOFeeMax.get(i))*100));
					caclInfQueryDTO.setCaclInfDTO(caclInfDTO);
					caclInfQueryDTO.getCaclInfDTO().setDiscCd(disccd);
					caclInfQueryDTO.getCaclInfDTO().setRecUpdUsrId(entityId);
					sendService(ConstCode.CACLINF_INSERTINF, caclInfQueryDTO.getCaclInfDTO());
					if(hasErrors()){
						return "input";
					}
				}
				logger.debug("splitProfit");
				caclInfDTO.setStepNo(++size);
				caclInfDTO.setOperRslt(Integer.parseInt(sendService(ConstCode.CACLINF_GETBYENTITYID, getUser().getEntityId()).getDetailvo().toString()));
				caclInfDTO.setOperand1("-10");
				caclInfDTO.setOperator1("*");
				if(splitProfit!=null&&!"".equals(splitProfit.trim())){
					caclInfDTO.setOperand2(Long.parseLong(splitProfit));
				}else{
					caclInfDTO.setOperand2(Long.parseLong("0"));
				}
				caclInfDTO.setOperator2("/");
				caclInfDTO.setOperand3("10000");
				caclInfQueryDTO.setCaclInfDTO(caclInfDTO);
				caclInfQueryDTO.getCaclInfDTO().setDiscCd(disccd);
				caclInfQueryDTO.getCaclInfDTO().setRecUpdUsrId(entityId);
				sendService(ConstCode.CACLINF_INSERTINF, caclInfQueryDTO.getCaclInfDTO());
				if(hasErrors()){
					return "input";
				}
				
				
				logger.debug("00");
				caclInfDTO.setStepNo(++size); // 7
				caclInfDTO.setOperRslt(0);
				caclInfDTO.setOperand1("0");
				caclInfDTO.setOperator1("$");
				caclInfDTO.setOperand2(Long.parseLong("0"));
				caclInfDTO.setOperator2("0");
				caclInfDTO.setOperand3("0");
				caclInfQueryDTO.setCaclInfDTO(caclInfDTO);
				caclInfQueryDTO.getCaclInfDTO().setDiscCd(disccd);
				caclInfQueryDTO.getCaclInfDTO().setRecUpdUsrId(entityId);
				sendService(ConstCode.CACLINF_INSERTINF, caclInfQueryDTO.getCaclInfDTO());
				if(hasErrors()){
					return "input";
				}

			}
			caclInfDTO.setStepNo(serviceFeeRuleDTORuleStep.size()); 
			caclInfDTO.setOperRslt(0);
			caclInfDTO.setOperand1("0");
			caclInfDTO.setOperator1("$");
			caclInfDTO.setOperand2(Long.parseLong("0"));
			caclInfDTO.setOperator2("0");
			caclInfDTO.setOperand3("0");
			caclInfQueryDTO.setCaclInfDTO(caclInfDTO);
			caclInfQueryDTO.getCaclInfDTO().setDiscCd(disccd);
			//机构号不足八位，补足八位
			String entityId = StringUtils.rpad(getUser().getEntityId(),8," ");
			caclInfQueryDTO.getCaclInfDTO().setRecUpdUsrId(entityId);
			sendService(ConstCode.CACLINF_INSERTINF, caclInfQueryDTO.getCaclInfDTO());
			if(hasErrors()){
				return "input";
			}
		}
		return "list";
	}
	
	
	public String add(){
		
		return "add";
	}
	public String listCaclInf() {
		logger.info("listcaclinf");
		try {
			logger.info(caclInfQueryDTO);
			//机构号不足八位，补足八位
			String entityId = StringUtils.rpad(getUser().getEntityId(),8," ");
			caclInfQueryDTO.setRecUpdUsrId(entityId);
			if (null != caclInfQueryDTO.getCaclDspDTO()) {
				if (null != caclInfQueryDTO.getCaclDspDTO().getDiscCd()
						 && !"".equals(caclInfQueryDTO.getCaclDspDTO().getDiscCd()
						.trim())
					 ) {
					caclInfQueryDTO.setDiscCd(caclInfQueryDTO.getCaclDspDTO()
							.getDiscCd().trim());
				}
				if (null != caclInfQueryDTO.getCaclDspDTO()
						.getDataStat() && !"".equals(caclInfQueryDTO.getCaclDspDTO().getDataStat())
						) {
					caclInfQueryDTO.setDataStat(caclInfQueryDTO.getCaclDspDTO().getDataStat());
				}
			}
			pageDataDTO = (PageDataDTO) sendService(ConstCode.CACLINF_QUERY,
					caclInfQueryDTO).getDetailvo();
			if(pageDataDTO!=null){
				totalRows=pageDataDTO.getTotalRecord();
			}else{
				totalRows=0;
			}
			if(hasErrors()){
				return "input";
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}

		return "list";
	}

	public String view() {
		
		try {
			
			if(null!=feeRuleIdList && feeRuleIdList.size()>0){
				for(int i=0;i<feeRuleIdList.size();i++){
					String[] splitArray=feeRuleIdList.get(0).split(",");
					disccd=splitArray[0];
				}
			}else{
				logger.info("disccd:"+disccd);
			disccd=caclInfQueryDTO.getCaclInfDTO().getDiscCd();
			logger.info("disccd:"+disccd);
			}
			caclInfQueryDTO.setDiscCd(disccd);
			caclInfQueryDTO.setLoginUserId(this.getUser().getEntityId());
			caclInfQueryDTO = (CaclInfQueryDTO) sendService(ConstCode.CACLINF_QUERYBYDISCCD,caclInfQueryDTO).getDetailvo();
			if(hasErrors()){
				return "input";
			}
			if (null != caclInfQueryDTO.getCaclInfDTOs()
					&& caclInfQueryDTO.getCaclInfDTOs().size() > 0) {
				logger.info(caclInfQueryDTO.getCaclInfDTOs().size());
				List<CaclInfView> list = new ArrayList<CaclInfView>();
				for (int i = 0; i < caclInfQueryDTO.getCaclInfDTOs().size(); i++) {
					CaclInfView caclInfView = new CaclInfView();
					caclInfDTO = caclInfQueryDTO.getCaclInfDTOs().get(i);
					if (caclInfDTO.getOperRslt() > 0) {
					logger.debug("Amount");
						caclInfView.setAmountStart(String.format("%20.2f", Double.parseDouble(caclInfDTO.getOperand1())/100).trim());
						caclInfView.setAmountEnd(String.format("%20.2f", Double.parseDouble(caclInfDTO.getOperand3())/100).trim());
						int step = caclInfDTO.getOperRslt();
						caclInfDTO = caclInfQueryDTO.getCaclInfDTOs().get(step);
						if (caclInfDTO.getOperRslt() < 0) {
							logger.debug("rate");
							caclInfView.setRateValue(caclInfDTO.getOperand2());
							caclInfView.setRateType(Double.parseDouble(caclInfDTO.getOperand3()));
							caclInfDTO = caclInfQueryDTO.getCaclInfDTOs().get(
									++step);
							if (caclInfDTO.getOperRslt() < 0 ) {
								if(caclInfDTO.getOperator1().trim().equals("<") || "<".equals(caclInfDTO.getOperator1())){
								caclInfView.setFeeMin(String.format("%20.2f", Double.parseDouble(caclInfDTO.getOperand1())/100).trim());
								}else if(caclInfDTO.getOperator1().equals(">") || ">".equals(caclInfDTO.getOperator1())){
								caclInfView.setFeeMax(String.format("%20.2f", Double.parseDouble(caclInfDTO.getOperand1())/100).trim());
								}else if("-10".equals(caclInfDTO.getOperand1())){
										if(caclInfDTO.getOperand2()!=null) {
										//if(caclInfDTO.getOperand2()!=null&&!"".equals(caclInfDTO.getOperand2())){
											caclInfQueryDTO.getCaclInfDTO().setSplitProfit(caclInfDTO.getOperand2());
										}
									}
								caclInfDTO = caclInfQueryDTO.getCaclInfDTOs().get(
										++step);
								if (caclInfDTO.getOperRslt() < 0 ) {
									if(caclInfDTO.getOperator1().trim().equals("<") || "<".equals(caclInfDTO.getOperator1())){
									caclInfView.setFeeMin(String.format("%20.2f", Double.parseDouble(caclInfDTO.getOperand1())/100).trim());
									}else if(caclInfDTO.getOperator1().equals(">") || ">".equals(caclInfDTO.getOperator1())){
									caclInfView.setFeeMax(String.format("%20.2f", Double.parseDouble(caclInfDTO.getOperand1())/100).trim());
									}else if("-10".equals(caclInfDTO.getOperand1())){
										if(caclInfDTO.getOperand2()!=null) {
										// if(caclInfDTO.getOperand2()!=null&&!"".equals(caclInfDTO.getOperand2())){
											caclInfQueryDTO.getCaclInfDTO().setSplitProfit(caclInfDTO.getOperand2());
										}
									}
									caclInfDTO = caclInfQueryDTO.getCaclInfDTOs().get(
											++step);
									if(caclInfDTO.getOperRslt()<0){
										if("-10".equals(caclInfDTO.getOperand1())){
											if(caclInfDTO.getOperand2()!=null) {
											//if(caclInfDTO.getOperand2()!=null&&!"".equals(caclInfDTO.getOperand2())){
												caclInfQueryDTO.getCaclInfDTO().setSplitProfit(caclInfDTO.getOperand2());
											}
										}
										caclInfDTO = caclInfQueryDTO.getCaclInfDTOs().get(
												++step);
										if (caclInfDTO.getOperRslt() == 0 ) {
											list.add(caclInfView);
											caclInfQueryDTO.setList(list);
											continue;
										}
									}
									
								}
								if (caclInfDTO.getOperRslt() == 0 ) {
									list.add(caclInfView);
									caclInfQueryDTO.setList(list);
									continue;
								}
							}
							if (caclInfDTO.getOperRslt() == 0 ) {
								list.add(caclInfView);
								caclInfQueryDTO.setList(list);
								continue;
							
						}
						if (caclInfDTO.getOperRslt() == 0) {
							list.add(caclInfView);
							caclInfQueryDTO.setList(list);
							continue;
						}

					}
					if (caclInfDTO.getOperRslt() == 0) {
						list.add(caclInfView);
						caclInfQueryDTO.setList(list);
						continue;
					}
				}
					if (caclInfDTO.getOperRslt() == 0) {
						break;
					}
				}
			}
			int i=caclInfQueryDTO.getList().size()-1;
			view=caclInfQueryDTO.getList().get(i);
//			caclInfQueryDTO.getList().get(i).setFeeMax(0);
//			caclInfQueryDTO.getList().get(i).setRateValue(null);
//			caclInfQueryDTO.getList().get(i).setFeeMax(0);
			caclInfQueryDTO.getList().remove(i);
			CaclInfView civ=new CaclInfView();
			civ.setAmountStart("0");
			caclInfQueryDTO.getList().add(0, civ);
			getRequest().setAttribute("size", caclInfQueryDTO.getList().size());
		} catch (Exception e) {
			
			this.logger.error(e.getMessage());
		}
		if (state == 0) {
			 return "EditView";
		} 
		else {
			return "view";
		}
	}

	public String update() {
		try {
			//状态 0删除  2 启用  1新建
			logger.info(state);
			String caclInfNameString=caclInfQueryDTO.getCaclDspDTO().getCaclName();
			String caclInfDspString=caclInfQueryDTO.getCaclDspDTO().getCaclDsp();
			String caclInfDisccd=caclInfQueryDTO.getCaclDspDTO().getDiscCd();
			caclInfQueryDTO.setDiscCd(caclInfDisccd);
			if(null!=feeRuleIdList && feeRuleIdList.size()>0 ){
				for(int i=0;i<feeRuleIdList.size();i++){
					String[] splitArray=feeRuleIdList.get(0).split(",");
					disccd=splitArray[0];
					caclInfQueryDTO.setDiscCd(disccd);
				}
			}
			
			if(state==2){
				caclInfQueryDTO=(CaclInfQueryDTO)sendService(ConstCode.CACLINF_QUERYBYDISCCD, caclInfQueryDTO).getDetailvo();
				if(hasErrors()){
					return "input";
				}
				caclInfQueryDTO.getCaclDspDTO().setDataStat("2");
				caclInfQueryDTO.setCaclInfDTO(null);
				sendService(ConstCode.CACLINF_UPDATE, caclInfQueryDTO);
				if(hasErrors()){
					return "input";
				}
			}else if(state==3){
				caclInfQueryDTO=(CaclInfQueryDTO)sendService(ConstCode.CACLINF_QUERYBYDISCCD, caclInfQueryDTO).getDetailvo();
				if(hasErrors()){
					return "input";
				}
				caclInfQueryDTO.getCaclDspDTO().setDataStat("0");
				caclInfQueryDTO.setCaclInfDTO(null);
				sendService(ConstCode.CACLINF_UPDATE, caclInfQueryDTO);
				if(hasErrors()){
					return "input";
				}
			}else{
				if(caclInfQueryDTO.getCaclInfDTO().getSplitProfit()!=0){
					splitProfit=String.valueOf(caclInfQueryDTO.getCaclInfDTO().getSplitProfit());
				}
				disccd=caclInfQueryDTO.getCaclDspDTO().getDiscCd();
				caclInfQueryDTO=(CaclInfQueryDTO)sendService(ConstCode.CACLINF_QUERYBYDISCCD, caclInfQueryDTO).getDetailvo();
				if(hasErrors()){
					return "input";
				}
				caclInfQueryDTO.getCaclInfDTO().setDiscCd(disccd);
				sendService(ConstCode.CACLINF_DELETE, caclInfQueryDTO.getCaclInfDTO());
				if(hasErrors()){
					return "input";
				}
				caclInfQueryDTO.getCaclDspDTO().setCaclName(caclInfNameString);
				caclInfQueryDTO.getCaclDspDTO().setCaclDsp(caclInfDspString);
				caclInfQueryDTO.getCaclDspDTO().setRecUpdTs(DateUtil.getCurrentDate());
				caclInfQueryDTO.setCaclInfDTO(null);
				sendService(ConstCode.CACLINF_UPDATE, caclInfQueryDTO);
				if(hasErrors()){
					return "input";
				}
				
				
				
				addInf();
				
//				if (!"".equals(serviceFeeRuleDTORuleStep)
//						&& serviceFeeRuleDTORuleStep.size() > 0) {
//					int size = serviceFeeRuleDTORuleStep.size(); // 3
//					for (int i = 0; i < serviceFeeRuleDTORuleStep.size(); i++) {
//						caclInfDTO.setStepNo(i); // 0
//						caclInfDTO.setOperRslt(++size); // 4
//						logger.info(size);
//						caclInfDTO.setOperand1(Double
//								.parseDouble(serviceFeeRuleDTOAmountStart.get(i)));
//						caclInfDTO.setOperator1("L");
//						caclInfDTO.setOperand2(Long.parseLong("-1"));
//						caclInfDTO.setOperator2("L");
//						caclInfDTO.setOperand3(Double
//								.parseDouble(serviceFeeRuleDTOAmountEnd.get(i)));
//						caclInfQueryDTO.setCaclInfDTO(caclInfDTO);
//						caclInfQueryDTO.getCaclInfDTO().setDiscCd(disccd);
//						caclInfQueryDTO.getCaclInfDTO().setRecUpdUsrId(getUser().getEntityId());
//						caclInfQueryDTO.setCaclDspDTO(null);
//						sendService(ConstCode.CACLINF_UPDATE, caclInfQueryDTO);
//						if(hasErrors()){
//							return "input";
//						}
//						caclInfDTO.setStepNo(size); // 4
//						logger.info(size);
//						caclInfDTO.setOperRslt(-10);
//						caclInfDTO.setOperand1(-1);
//						caclInfDTO.setOperator1("*");
//						caclInfDTO.setOperand2(Long.parseLong(serviceFeeRuleDTORateValue.get(i)));
//						caclInfDTO.setOperator2("/");
//						logger.info("type:" + serviceFeeRuleDTORateType.get(i));
//						caclInfDTO.setOperand3(100);
//						caclInfQueryDTO.setCaclInfDTO(caclInfDTO);
//						caclInfQueryDTO.getCaclInfDTO().setDiscCd(disccd);
//						caclInfQueryDTO.getCaclInfDTO().setRecUpdUsrId(getUser().getEntityId());
//						caclInfQueryDTO.setCaclDspDTO(null);
//						sendService(ConstCode.CACLINF_UPDATE, caclInfQueryDTO);
//						if(hasErrors()){
//							return "input";
//						}
//						if (isNotEmpty(serviceFeeRuleDTOFeeMin.get(i))) {
//							caclInfDTO.setStepNo(++size); // 5
//							caclInfDTO.setOperRslt(-10);
//							caclInfDTO.setOperand1(Double
//									.parseDouble(serviceFeeRuleDTOFeeMin.get(i)));
//							caclInfDTO.setOperator1("<");
//							caclInfDTO.setOperand2(Long.parseLong("-10"));
//							caclInfDTO.setOperator2("<");
//							caclInfDTO.setOperand3(Double
//									.parseDouble(serviceFeeRuleDTOFeeMin.get(i)));
//							caclInfQueryDTO.setCaclInfDTO(caclInfDTO);
//							caclInfQueryDTO.getCaclInfDTO().setDiscCd(disccd);
//							caclInfQueryDTO.getCaclInfDTO().setRecUpdUsrId(getUser().getEntityId());
//							caclInfQueryDTO.setCaclDspDTO(null);
//							sendService(ConstCode.CACLINF_UPDATE, caclInfQueryDTO);
//							if(hasErrors()){
//								return "input";
//							}
//						}
//						if (isNotEmpty(serviceFeeRuleDTOFeeMax.get(i))) {
//							caclInfDTO.setStepNo(++size); // 6
//							caclInfDTO.setOperRslt(-10);
//							caclInfDTO.setOperand1(Double
//									.parseDouble(serviceFeeRuleDTOFeeMax.get(i)));
//							caclInfDTO.setOperator1(">");
//							caclInfDTO.setOperand2(Long.parseLong("-10"));
//							caclInfDTO.setOperator2(">");
//							caclInfDTO.setOperand3(Double
//									.parseDouble(serviceFeeRuleDTOFeeMax.get(i)));
//							caclInfQueryDTO.setCaclInfDTO(caclInfDTO);
//							caclInfQueryDTO.getCaclInfDTO().setDiscCd(disccd);
//							caclInfQueryDTO.getCaclInfDTO().setRecUpdUsrId(getUser().getEntityId());
//							caclInfQueryDTO.setCaclDspDTO(null);
//							sendService(ConstCode.CACLINF_UPDATE, caclInfQueryDTO);
//							if(hasErrors()){
//								return "input";
//							}
//						}
//						caclInfDTO.setStepNo(++size); // 7
//						caclInfDTO.setOperRslt(0);
//						caclInfDTO.setOperand1(0);
//						caclInfDTO.setOperator1("$");
//						caclInfDTO.setOperand2(Long.parseLong("0"));
//						caclInfDTO.setOperator2("0");
//						caclInfDTO.setOperand3(0);
//						caclInfQueryDTO.setCaclInfDTO(caclInfDTO);
//						caclInfQueryDTO.getCaclInfDTO().setDiscCd(disccd);
//						caclInfQueryDTO.getCaclInfDTO().setRecUpdUsrId(getUser().getEntityId());
//						caclInfQueryDTO.setCaclDspDTO(null);
//						sendService(ConstCode.CACLINF_UPDATE, caclInfQueryDTO);
//						if(hasErrors()){
//							return "input";
//						}
//
//					}
//					caclInfDTO.setStepNo(serviceFeeRuleDTORuleStep.size()); 
//					caclInfDTO.setOperRslt(0);
//					caclInfDTO.setOperand1(0);
//					caclInfDTO.setOperator1("$");
//					caclInfDTO.setOperand2(Long.parseLong("0"));
//					caclInfDTO.setOperator2("0");
//					caclInfDTO.setOperand3(0);
//					caclInfQueryDTO.setCaclInfDTO(caclInfDTO);
//					caclInfQueryDTO.getCaclInfDTO().setDiscCd(disccd);
//					caclInfQueryDTO.getCaclInfDTO().setRecUpdUsrId(getUser().getEntityId());
//					caclInfQueryDTO.setCaclDspDTO(null);
//					sendService(ConstCode.CACLINF_UPDATE, caclInfQueryDTO);
//					if(hasErrors()){
//						return "input";
//					}
//			}
			if(!this.hasErrors()){
				this.addActionMessage("编辑计算规则信息成功！");
			}
			}
		} catch (Exception e) {
			
			this.logger.error(e.getMessage());
		}
	
		if(flag==1){
			consumerContractId=consumerContractDTO.getConsumerContractId();
			return "editConsumerContract";
			
		}
		
		else{
			caclInfQueryDTO.getCaclDspDTO().setDiscCd(null);
		    caclInfQueryDTO.getCaclDspDTO().setDataStat(null);
		    caclInfQueryDTO.setDiscCd(null);
		    listCaclInf();
			return "list";	
			}	
	}


	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public CaclInfDTO getCaclInfDTO() {
		return caclInfDTO;
	}

	public void setCaclInfDTO(CaclInfDTO caclInfDTO) {
		this.caclInfDTO = caclInfDTO;
	}

	public PageQueryDTO getPageQueryDTO() {
		return pageQueryDTO;
	}

	public void setPageQueryDTO(PageQueryDTO pageQueryDTO) {
		this.pageQueryDTO = pageQueryDTO;
	}

	public PageDataDTO getPageDataDTO() {
		return pageDataDTO;
	}

	public void setPageDataDTO(PageDataDTO pageDataDTO) {
		this.pageDataDTO = pageDataDTO;
	}

	public List<String> getServiceFeeRuleDTORuleStep() {
		return serviceFeeRuleDTORuleStep;
	}

	public void setServiceFeeRuleDTORuleStep(
			List<String> serviceFeeRuleDTORuleStep) {
		this.serviceFeeRuleDTORuleStep = serviceFeeRuleDTORuleStep;
	}

	public List<String> getServiceFeeRuleDTORateType() {
		return serviceFeeRuleDTORateType;
	}

	public void setServiceFeeRuleDTORateType(
			List<String> serviceFeeRuleDTORateType) {
		this.serviceFeeRuleDTORateType = serviceFeeRuleDTORateType;
	}

	public List<String> getServiceFeeRuleDTORateValue() {
		return serviceFeeRuleDTORateValue;
	}

	public void setServiceFeeRuleDTORateValue(
			List<String> serviceFeeRuleDTORateValue) {
		this.serviceFeeRuleDTORateValue = serviceFeeRuleDTORateValue;
	}

	public List<String> getServiceFeeRuleDTOFeeMin() {
		return serviceFeeRuleDTOFeeMin;
	}

	public void setServiceFeeRuleDTOFeeMin(List<String> serviceFeeRuleDTOFeeMin) {
		this.serviceFeeRuleDTOFeeMin = serviceFeeRuleDTOFeeMin;
	}

	public List<String> getServiceFeeRuleDTOFeeMax() {
		return serviceFeeRuleDTOFeeMax;
	}

	public void setServiceFeeRuleDTOFeeMax(List<String> serviceFeeRuleDTOFeeMax) {
		this.serviceFeeRuleDTOFeeMax = serviceFeeRuleDTOFeeMax;
	}

	public List<String> getServiceFeeRuleDTOAmountStart() {
		return serviceFeeRuleDTOAmountStart;
	}

	public void setServiceFeeRuleDTOAmountStart(
			List<String> serviceFeeRuleDTOAmountStart) {
		this.serviceFeeRuleDTOAmountStart = serviceFeeRuleDTOAmountStart;
	}

	public List<String> getServiceFeeRuleDTOAmountEnd() {
		return serviceFeeRuleDTOAmountEnd;
	}

	public void setServiceFeeRuleDTOAmountEnd(
			List<String> serviceFeeRuleDTOAmountEnd) {
		this.serviceFeeRuleDTOAmountEnd = serviceFeeRuleDTOAmountEnd;
	}

	public CaclInfQueryDTO getCaclInfQueryDTO() {
		return caclInfQueryDTO;
	}

	public void setCaclInfQueryDTO(CaclInfQueryDTO caclInfQueryDTO) {
		this.caclInfQueryDTO = caclInfQueryDTO;
	}

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public CaclDspDTO getCaclDspDTO() {
		return caclDspDTO;
	}

	public void setCaclDspDTO(CaclDspDTO caclDspDTO) {
		this.caclDspDTO = caclDspDTO;
	}

	public List<String> getFeeRuleIdList() {
		return feeRuleIdList;
	}

	public void setFeeRuleIdList(List<String> feeRuleIdList) {
		this.feeRuleIdList = feeRuleIdList;
	}

}

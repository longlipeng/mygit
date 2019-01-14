package com.allinfinance.prepay.processor.ipos;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allinfinance.prepay.mapper.svc_mng.CardHolderTmpMapper;
import com.allinfinance.prepay.message.BasicMessage;
import com.allinfinance.prepay.message.MessageSyncP002Req;
import com.allinfinance.prepay.message.MessageSyncP002Resp;
import com.allinfinance.prepay.model.CardHolderTmp;
import com.allinfinance.prepay.model.CardHolderTmpExample;
import com.allinfinance.prepay.processor.IProcessor;
import com.allinfinance.prepay.utils.CheckDataMethod;
import com.allinfinance.prepay.utils.Config;
import com.allinfinance.prepay.utils.DateUtil;
import com.allinfinance.prepay.utils.IDCardCheck;
import com.allinfinance.prepay.utils.ResponseCode;

@Service
public class SyncP002Processor implements IProcessor{
	
	@Autowired
	private CardHolderTmpMapper cardHolderTmpMapper;
	
	@Autowired
	private CheckDataMethod checkDataMethod;

	private Logger logger = Logger.getLogger(SyncP002Processor.class);
	@Override
	public BasicMessage process(BasicMessage req) throws Exception {
		// TODO Auto-generated method stub
		MessageSyncP002Req reqData = (MessageSyncP002Req)req;
		MessageSyncP002Resp resp =(MessageSyncP002Resp) reqData.createResp() ;
		CardHolderTmp cardHolderTmp=new CardHolderTmp();
		if(reqData.getCUSTOMER_TPYE()!=null&&!reqData.getCUSTOMER_TPYE().equals("")){
			if(!reqData.getCUSTOMER_TPYE().equals("0")){
				resp.setRESP_CODE(ResponseCode.ORDER_TYPE_ERROR);
				return resp;
			}
		}
		String channel=null;//渠道号
		String branch_Id=null;//网点
		
		
		if(reqData.getCHANNEL()!=null&&!reqData.getCHANNEL().trim().equals("")){
			channel=reqData.getCHANNEL().trim();
		}else{
			resp.setRESP_CODE(ResponseCode.ISNULL);
			return resp;
		}
		if(reqData.getBRANCH_ID()!=null&&!reqData.getBRANCH_ID().trim().equals("")){
			branch_Id=reqData.getBRANCH_ID().trim();
		}else{
			resp.setRESP_CODE(ResponseCode.ISNULL);
			return resp;
		}
		
		
		if(reqData.getISSUER_ID()!=null&&!reqData.getISSUER_ID().trim().equals("")){
			if(checkDataMethod.checkIssueId(reqData.getISSUER_ID().trim())){
				cardHolderTmp.setEntityId(reqData.getISSUER_ID().trim());
			}else{
				//没有找到机构号
				//resp.setRESP_TEXT("机构号有误！");
				resp.setRESP_CODE(ResponseCode.ISSUEID_ERROR);
				return resp;
			}
		}else{
			//resp.setRESP_TEXT("机构号不能为空！");
			resp.setRESP_CODE(ResponseCode.ISNULL);
			return resp;
		}
		cardHolderTmp.setCreateUser(Config.getUserId());
		cardHolderTmp.setCreateTime(DateUtil.getCurrentTime());
		cardHolderTmp.setModifyTime(DateUtil.getCurrentTime());
		cardHolderTmp.setModifyUser(Config.getUserId());
		/*姓名*/
		if(reqData.getFIRST_NAME()!=null&&!reqData.getFIRST_NAME().trim().equals("")){
			cardHolderTmp.setFirstName(reqData.getFIRST_NAME().trim());
		}else{
			//resp.setRESP_TEXT("持卡人姓名不能为空！");
			resp.setRESP_CODE(ResponseCode.ISNULL);
			return resp;
		}
		
		
		
		/*电话*/
		if(reqData.getCARDHOLDER_MOBILE()!=null&&!reqData.getCARDHOLDER_MOBILE().trim().equals("")){
			boolean isTrue=reqData.getCARDHOLDER_MOBILE().trim().matches("([1][3,4,5,8,7][0-9]{9})");
			if(isTrue){
				cardHolderTmp.setCardholderMobile(reqData.getCARDHOLDER_MOBILE().trim());
			}else{
				//手机号码格式不正确
				resp.setRESP_CODE(ResponseCode.MOBILE_ERROR);
				return resp;
			}
			
		}else{
			//resp.setRESP_TEXT("手机号码不能为空！");
			resp.setRESP_CODE(ResponseCode.ISNULL);
			return resp;
		}
		/*证件类型*/
		if(reqData.getID_TYPE()!=null&&!reqData.getID_TYPE().trim().equals("")){
			if(reqData.getID_TYPE().trim().equals("1")){
				cardHolderTmp.setIdType("1");
			}else if(reqData.getID_TYPE().trim().equals("2")){
				cardHolderTmp.setIdType("2");
			}else if(reqData.getID_TYPE().trim().equals("3")){
				cardHolderTmp.setIdType("3");
			}else{
				//没有这个证件类型
				resp.setRESP_CODE(ResponseCode.ID_TYPE_ERROR);
				//resp.setRESP_TEXT("证件类型有误！");
				return resp;
			}
		}else{
			//resp.setRESP_TEXT("证件类型不能为空！");
			resp.setRESP_CODE(ResponseCode.ISNULL);
			return resp;
		}
		
		/*证件号*/
		if(reqData.getID_NO()!=null&&!reqData.getID_NO().trim().equals("")){
			if(reqData.getID_TYPE().trim().equals("1")){
				//身份证
				String errMsg=IDCardCheck.IDCardValidate(reqData.getID_NO().toUpperCase());
				if(errMsg==null||errMsg.equals("")){
					cardHolderTmp.setIdNo(reqData.getID_NO().toUpperCase());
				}else{
					//resp.setRESP_TEXT("证件号有误！");
					resp.setRESP_CODE(ResponseCode.ID_NO_ERROR);
					return resp;
				}
				
			}else if(reqData.getID_TYPE().equals("2")){
				//护照
				boolean isTrue = reqData.getID_NO().trim().matches("([0-9a-zA-Z]*)");
	    		 if(isTrue==true){
	    			 cardHolderTmp.setIdNo(reqData.getID_NO().trim());
	    		 }else{
	    			 //resp.setRESP_TEXT("证件号有误！");
	    			 resp.setRESP_CODE(ResponseCode.ID_NO_ERROR);
	    			 return resp;
	    		 }
			}else if(reqData.getID_TYPE().trim().equals("3")){
				//其他
				cardHolderTmp.setIdNo(reqData.getID_NO().trim());
			}
		}else{
			//resp.setRESP_TEXT("证件号不能为空！");
			resp.setRESP_CODE(ResponseCode.ISNULL);
			return resp;
		}
		/*性别*/
		if(reqData.getCARDHOLDER_GENDER()!=null&&!reqData.getCARDHOLDER_GENDER().trim().equals("")){
			if(reqData.getCARDHOLDER_GENDER().trim().endsWith("1")||reqData.getCARDHOLDER_GENDER().trim().endsWith("2")){
				cardHolderTmp.setCardholderGender(reqData.getCARDHOLDER_GENDER().trim());
			}else{
				resp.setRESP_CODE(ResponseCode.GENDER_ERROR);
				return resp;
			}
		}else{
			//如果性别为空，判断证件类型
			if(reqData.getID_TYPE().trim().equals("1")){
				//18位身份证
				if(reqData.getID_NO().trim().length()==18){
					//截取身份证的第17位代表性别，奇数为男，偶数为女
					int num=Integer.parseInt(reqData.getID_NO().trim().substring(16,17));
					if(num%2==0){
						//偶数
						cardHolderTmp.setCardholderGender("2");
					}else{
						cardHolderTmp.setCardholderGender("1");
					}
				}
				//15位身份证
				if(reqData.getID_NO().length()==15){
					//截取身份证的第15位代表性别，奇数为男，偶数为女
					int num=Integer.parseInt(reqData.getID_NO().trim().substring(14,15));
					if(num%2==0){
						//偶数
						cardHolderTmp.setCardholderGender("2");
					}else{
						cardHolderTmp.setCardholderGender("1");
					}
				}
				
			}else{
				//返回错误，填写性别
				//resp.setRESP_TEXT("请填写性别！");
				resp.setRESP_CODE(ResponseCode.ISNULL);
				return resp;
			}
		}
		
		/*称谓*/
		if(cardHolderTmp.getCardholderGender().trim().equals("1")){
			cardHolderTmp.setCardholderSalutation("1");
		}
		else if(cardHolderTmp.getCardholderGender().trim().equals("2")){
			cardHolderTmp.setCardholderSalutation("3");//女士
		}
		/*地址*/
		
		cardHolderTmp.setMailingAddress(reqData.getDELIVERY_ADDRESS().trim());
//		/*生日*/
//		cardholderDTO.setCardholderBirthday("");
		/*车牌号*/
		cardHolderTmp.setPlateNumber(reqData.getPLATE_NUMBER().trim());
		/*车架号*/
		//cardholderDTO.setVId();
		/*驾驶证号*/
		cardHolderTmp.setDriverLicence(reqData.getDRIVER_LICENCE().trim());
		/*持卡人分类*/
		cardHolderTmp.setCardholderSegment("0");
		
		//持卡人备注
		cardHolderTmp.setCardholderComment(reqData.getREMARK().trim());
		/*持卡人信息默认字段*/
		cardHolderTmp.setCardholderFunction("1");
		cardHolderTmp.setCardholderState("1");
		cardHolderTmp.setChannel(channel);//渠道号
		cardHolderTmp.setTermId(reqData.getTERM_ID());//终端号
		cardHolderTmp.setBranchId(branch_Id);//服务网点号
		cardHolderTmp.setMchntCd(reqData.getMCHNT_CD());//商户号
		cardHolderTmp.setDataState("1");
		CardHolderTmpExample ex=new CardHolderTmpExample();
		ex.createCriteria().andIdNoEqualTo(cardHolderTmp.getIdNo())
			.andIdTypeEqualTo(cardHolderTmp.getIdType()).andDataStateEqualTo("1");
		try {
			List<CardHolderTmp>  list=cardHolderTmpMapper.selectByExample(ex);
			if(list.size()>0){
				cardHolderTmpMapper.updateByExampleSelective(cardHolderTmp, ex);
			}else{
				cardHolderTmpMapper.insertSelective(cardHolderTmp);
			}
		
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("["+reqData.getRESPSEQNO()+"]"+"新增或修改失败！");
			resp.setRESP_CODE(ResponseCode.SYSTEM_ERROR);
			return resp;
		}
		//成功返回
		resp.setRESP_CODE(ResponseCode.SUCCESS);
		return resp;
	}
	
	

}

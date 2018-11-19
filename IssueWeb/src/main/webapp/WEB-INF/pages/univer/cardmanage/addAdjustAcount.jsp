<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/ajax.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/commons/meta.jsp"%>
		<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
		<title>卡交易调整</title>
		<script type="text/javascript">
		function submitForm(){
			
			var reg =  /^\d{1,7}([\.]\d{1,2})?$/;
			var  cardIdString = document.getElementById('cardId').value;
			if(cardIdString.length>19||cardIdString.length<1){
				errorDisplay("卡号不能为空且位数不能大于19位");
				return;
			}
			
			var adjustMoneyString = document.getElementById('chargeAmt').value;
			//if(adjustMoneyString.length>12||adjustMoneyString.length<1){
			//	errorDisplay("调账金额不能为空且位数不能大于12位  ");
			//	return;
			//}
			//if(adjustMoneyString.indexOf("-")!=-1){
			//	errorDisplay("调账金额必须大于等于0");
			//	return;
			//}
			if(!reg.test(adjustMoneyString)){
				errorDisplay("调账金额必须为正数且小于1000000(精确到小数点后两位)");
				return;
			}
			if(parseFloat(adjustMoneyString)>1000000){
				errorDisplay("调账金额不能超过100万!");
				return;
			}
			
			var chargeMiscString = document.getElementById('chargeFee').value;
			//if(chargeMiscString.length>9||chargeMiscString.length<1){
			//	errorDisplay("调账手续费不能为空且位数不能大于9 ");
			//	return;
			//}
			//if(chargeMiscString.indexOf("-")!=-1){
			//	errorDisplay("调账手续费必须大于等于0");
			//	return;
			//}
			if(!reg.test(chargeMiscString)){
				errorDisplay("调账手续费必须为正数且小于1000000(精确到小数点后两位)");
				return;
			}
			if(parseFloat(chargeMiscString)>1000000){
				errorDisplay("调账手续费不能超过100万!");
				return;
			}
			var chargeMiscString = document.getElementById('chargeMisc').value;
			if(chargeMiscString.length>1024||chargeMiscString.length<1){
				errorDisplay("调账原因不能为空且位数不能大于1024 ");
				return;
			}
			 var transSeqNo1String = document.getElementById('transSeqNo1').value;
			if(transSeqNo1String.length>30||transSeqNo1String.length<1){
				errorDisplay("交易流水号不能为空且位数不能大于30 ");
				return;
			}
			var chargeStatString = document.getElementById('chargeStat').value;
			if(chargeStatString.length<1){
				errorDisplay("调账状态不能为空 ");
				return;
			}
			 var transSeqNo2String = document.getElementById('transSeqNo2').value;
			if(transSeqNo2String.length>30||transSeqNo2String.length<1){
				errorDisplay("交易流水号不能为空且位数不能大于30");
				return;
			} 
			var tmp = "确定调整该交易"+'</br>'+"上财流水号："+transSeqNo2String+'</br>'+"上汽流水号："+transSeqNo1String
			confirm(tmp,function(){maskDocAll();document.queryForm.submit();}) ;
			
			
		}
	 

</script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>卡交易调整</span>
		</div>
		<s:form id="queryForm" name="queryForm"  
			action="/cardManage/addAdjustAcount!addAcount.action" method="post">
			<s:token></s:token>           
			<div id="base"
				style="background-color: #FBFEFF; border: 1px solid #B9B9B9; margin: 5px 8px 0px;">
				<div id="baseTitle"
					style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td class="TableTitleFront">
								<span class="TableTop">卡交易信息</span>
							</td>
							<td class="TableTitleEnd">
								&nbsp;
							</td>
						</tr>
					</table>
				</div>
				<div id="baseTable"
					style="border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
					<table width="70%" style="table-layout: fixed; text-align:center;margin-left:90px;cellPadding:10px;cellSpacing:3px;">
						<tr>
							<td>
								<table style="text-align: right; width: 100%">
									<tr>
										<td style="width: 20%; text-align: right;">
											<span style="color: red">*</span>卡号：
										</td>
										<td style="width:20%; margin-left:10px;" >
											<s:textfield name="chargeTxnDTO.cardNo" id="cardId"
												 size="30" 	width="20%">
											</s:textfield>
											<s:fielderror>
												<s:param>chargeTxnDTO.cardNo</s:param>
											</s:fielderror>
										</td>
										
										<td style="width:15%; text-align: right;">
											<span style="color: red">*</span>调账金额：
										</td>
										<td style="width:20%; margin-left:10px;">
											<s:textfield name="chargeTxnDTO.chargeAmt" id="chargeAmt"
												 size="30" width="15%" >
											</s:textfield>
											<s:fielderror>
												<s:param>chargeTxnDTO.chargeAmt</s:param>
											</s:fielderror>
										</td>
										<td style="width: 10%; text-align: right;">
										
										</td>
										</tr>
										<tr>	
											<td style="width: 15%; text-align: right;">
											<span style="color: red">*</span>调账手续费：
										</td>
										<td style="width:20%; margin-left:10px;">
											<s:textfield name="chargeTxnDTO.chargeFee" id="chargeFee"
												 size="30" width="20%"
												>
											</s:textfield>
											<s:fielderror>
												<s:param>chargeTxnDTO.chargeFee</s:param>
											</s:fielderror>
										</td>
										
										<td style="width: 15%; text-align: right;">
											<span style="color: red">*</span>调账状态：
										</td>
										<td style="width:20%;margin-left:10px; ">
										     &nbsp;<select name="chargeTxnDTO.chargeStat" style="width:185" id="chargeStat" >
    		    								<option selected value="">--未选择--</option>
    											<option  value="1" >持卡人长款</option>
    		   								    <option  value="2" >持卡人短款</option>
     										  </select>
										</td>
										</tr>
											
									
										
										<tr>
										<td style="width: 15%; text-align: right;">
											<span style="color: red">*</span>上财流水号：
										</td>
										<td style="width:20%; margin-left:10px;">
											<s:textfield name="chargeTxnDTO.transSeqNo2" id="transSeqNo2"
												 size="30" 	width="15%">
											</s:textfield>
											<s:fielderror>
												<s:param>chargeTxnDTO.transSeqNo2</s:param>
											</s:fielderror>
										</td>
										
									<td style="width: 15%; text-align: right;">
											<span style="color: red">*</span>上汽流水号：
										</td>
										<td >
											<s:textfield name="chargeTxnDTO.transSeqNo1" id="transSeqNo1"
												 size="30" 	width="15%">
											</s:textfield>
											<s:fielderror>
												<s:param>chargeTxnDTO.transSeqNo1</s:param>
											</s:fielderror>
										</td> 
										</tr>
										
										<tr>
										
										<td style="width: 15%; text-align: right;" >
											<span style="color: red">*</span>调账原因：
										</td>
										
										<td style=" " colspan="3"	 >
											<s:textarea name="chargeTxnDTO.chargeMisc" id="chargeMisc"
												cssStyle=" size:200px; width:520px;height:90px; multiline:true;autoSize:left;"
												>
											</s:textarea>
											<s:fielderror>
												<s:param>chargeTxnDTO.chargeMisc</s:param>
											</s:fielderror>
										</td>
										
										</tr>
								</table>
	
							</td>
							

						</tr>
						
					</table>
				</div>
			</div>
			<div id="buttonDiv" style="margin: 5px 8px 0px;">
				<table border="0" cellpadding="0" cellspacing="0" width="90%">
					<tr>
						<td align="right">
							<table border="0" cellpadding="0" cellspacing="0">
								<tr height="35">
									
									    <td align="right" colspan="3">
											
											<input type="button" class="bt" style="margin: 5px" onclick="submitForm();" value="添加"/></td>
									</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
		</s:form>
	</body>
</html>
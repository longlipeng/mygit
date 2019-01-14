<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/commons/meta.jsp"%>
<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
<title>卡类别信息</title>
<script language="javaScript">
			 function sub0(){

				  	var cardBinBefore=document.getElementById('cardBinBefore').value; 
				  
				  	var cardBinBase=document.getElementById('cardBinBase').value;	
				  	
				    var customTypeList=document.getElementsByName("dictType.customType");
				  	var customType= customTypeList[0].value;
				  
				  	var cardLeverList=document.getElementsByName("dictType.cardLever");
				  	var  cardLever= cardLeverList[0].value;
				  	var cardBinAfter=cardBinBase+customType+cardLever;				  
				 	var cardBinBeforeLength = cardBinBefore.length;
				 	var cardBinAfterLength=cardBinAfter.length;
				 	var reg = /^\d{3}$/;
				 	if(!reg.test(cardBinBase)){
				 		errorDisplay("卡片基本位必须是三位数字");
				 		return;
				 	}
				 	//var dif = 10 - cardBinBeforeLength-cardBinAfterLength;
				 
					if (customType == ""){
				 		errorDisplay("请选择客户类别！");
					 	return;
				 	}
					if (cardLever == ""){
				 		errorDisplay("请选择卡片等级！");
					 	return;
				 	}
				 /* 	if(dif<0){
				 		errorDisplay("卡类别长度最多为10位");
					 	return;
				 	} */
				 	//if(cardBinAfter.search('^([0-9]{1,2})$')!=0){
					//	errorDisplay('请输入1-2位的数字卡BIN!');
					//	return false;
					//}
				 	var cardBin = cardBinBefore+"!"+cardBinAfter;
				    window.returnValue = cardBin;
				    window.close();
				 }
			 function sub1(){
				  	var cardBinBefore1=document.getElementById('cardBinBefore1').value; 	
			
				    var customTypeList=document.getElementsByName("dictType.customType1");
				  	var customType= customTypeList[0].value;
				  	var cardLeverList=document.getElementsByName("dictType.cardLever1");
				  	var  cardLever= cardLeverList[0].value;
				  	
				  	var cardBinAfter=customType+cardLever;
				 	var cardBinBeforeLength = cardBinBefore1.length;
				 	var cardBinAfterLength=cardBinAfter.length;
				 	var dif = 10 - cardBinBeforeLength-cardBinAfterLength;
				 	if (customType==""){
				 		errorDisplay("请选择客户类别！");
					 	return;
				 	}
					if (cardLever==""){
				 		errorDisplay("请选择卡片等级！");
					 	return;
				 	}
				 	if(dif<0){
				 		errorDisplay("卡类别长度最多为10位");
					 	return;
				 	}
				 	//if(cardBinAfter.search('^([0-9]{1,2})$')!=0){
					//	errorDisplay('请输入1-2位的数字卡BIN!');
					//	return false;
					//}
				 	var cardBin = cardBinBefore1+"!"+cardBinAfter;
				    window.returnValue = cardBin;
				    window.close();
				 }
			 
			 function sub3(){
				  	var cardBinBefore3=document.getElementById('cardBinBefore3').value; 	
			
				    var cardTypeList=document.getElementsByName("dictType.cardType");
				  	var cardType= cardTypeList[0].value;
				 
				 	if (cardType==""){
				 		errorDisplay("请选择卡片类别！");
					 	return;
				 	}
					
				 	//if(cardBinAfter.search('^([0-9]{1,2})$')!=0){
					//	errorDisplay('请输入1-2位的数字卡BIN!');
					//	return false;
					//}
				 	var cardBin = cardBinBefore3+"!"+cardType;
				    window.returnValue = cardBin;
				    window.close();
				 }
			 function radio(cardBin,binType){
				var productType = ${productType};
				 var binType0=document.getElementById("binType0");  
				 var binType1=document.getElementById("binType1");
				 var unSignProduct = document.getElementById("productType");
	             if(productType==2){
	            	 unSignProduct.style.display="none";
	            	 unSignProduct.style.display = "block";
            	     document.getElementById("cardBinBefore3").value=cardBin;
	             } else{
	            	 if(binType==0)
	                    {
	                	     binType1.style.display="none";
	                	     binType0.style.display = "block";
	                	     document.getElementById("cardBinBefore").value=cardBin;
	                    }else if(binType==1)
	                    {
		                	  binType0.style.display = "none";
		                	  binType1.style.display = "block";
		                	  document.getElementById("cardBinBefore1").value=cardBin;
	                    }
	             }
				
			 }

			 function loadPage(){
				 	var cardBinList = document.getElementsByName("cardBinList");
				 	for( i=0 ; i<cardBinList.length ; i++){
						if(cardBinList.item(i).checked){
						//document.getElementById("cardSerialNumberDTO.cardBin").value=cardBinList[i].value;
						var receiveCradMessage=cardBinList[i].value;
						
						 var receiveMessageArray=receiveCradMessage.split(",")
						 var cardBin=receiveMessageArray[0];
						 var binType=receiveMessageArray[1];
							 radio(cardBin,binType);
							return;
						}
					}
				}
		</script>
</head>
<body onload="loadPage()">
    <%@ include file="/commons/messages.jsp"%>
    <div class="TitleHref">
			<span>添加卡类别信息</span>
		</div>
		
		<div id="listTable"
			style="background-color: #FBFEFF; padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
			<div id="cardBinListTitle"
					style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td class="TableTitleFront">
								<span class="TableTop">卡BIN</span>
							</td>
							<td class="TableTitleEnd">
								&nbsp;
							</td>
						</tr>
					</table>
			</div>
			<div id="cardBinListTable"
					style="background-color: #FBFEFF; padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
			<s:form id="cardBinForm" name="cardBinForm" action="" method="post">
				<s:hidden name="issuerDTO.entityId"></s:hidden>
				<s:hidden name="issuerDTO.issuerCode"></s:hidden>
				<s:hidden name="cardSerialNumberDTO.cardBin" id="cardSerialNumberDTO.cardBin"/>
				<s:hidden name="cardSerialNumberDTO.binType" id="cardSerialNumberDTO.binType"/>
				<ec:table items="cardBinDTOs" var="map" width="100%"
					form="cardBinForm" action=""
					imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
					autoIncludeParameters="false" showPagination="false"
					sortable="false">
					<ec:row onclick="">
						<ec:column property="null" alias="" title="选择" width="5%"
							sortable="false">
							<input type="radio" name="cardBinList" onclick="radio('${map.cardBin}','${map.binType}');" value="${map.cardBin},${map.binType}" checked="checked"  />
						</ec:column>
						<ec:column property="cardBin" title="卡BIN" width="10%" />
						<ec:column property="serialNumber" title="卡流水号" width="10%" />
					</ec:row>
				</ec:table>


			</s:form>
			</div>
		</div>
         
       <div id="binType0" style="display:none;">
		<s:form id="cardPinForm" name="cardPinForm" action="" method="post">
			<s:hidden name="issuerDTO.entityId"></s:hidden>
			<s:hidden name="issuerDTO.issuerCode"></s:hidden>
			<s:hidden name="cardSerialNumberDTO.cardBin" id="cardSerialNumberDTO.cardBin"/>
			<div id="customerContact"
				style="border: 1px solid #B9B9B9; margin: 20px 8px 0px">
				<div id="customerContactTitle"
					style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td class="TableTitleFront">
								<span class="TableTop">卡类别信息</span>
							</td>
							<td class="TableTitleEnd">
								&nbsp;
							</td>
						</tr>
					</table>
				</div>
				<div id="customerContactTable"
					style="background-color: #FBFEFF; padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
					<table width="100%" style="table-layout: fixed;">
						<tr>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 110px; text-align: right;">
											卡BIN：
										</td>
										<td>
											<s:textfield name="cardBinBefore" id="cardBinBefore" disabled="true" />
										</td>
                                      <td style="width: 110px; text-align: right;">
											<span class="no-empty">*</span>卡基本位：
										</td>
										<td>
											<s:textfield name="cardBinBase"
												id="cardBinBase" maxlength="3" />
											<s:fielderror>
												<s:param>cardBinBase</s:param>
											</s:fielderror>
										</td>
										</tr>
										<tr>
                                        <td style="width: 90px; text-align: right;"><span class="no-empty">*</span>客户类别：
                                        </td>
                                    <td>
                                        <edl:entityDictList displayName="dictType.customType" dictValue="${dictType.customType}" dictType="501" tagType="2" defaultOption="true" />
                                    </td>
                                      <td style="width: 90px; text-align: right;"><span class="no-empty">*</span>卡片等级：</td>
                                    <td>
                                        <edl:entityDictList displayName="dictType.cardLever" dictValue="${dictType.cardLever}" dictType="502" tagType="2" defaultOption="true" />
                                    </td>   
									</tr>
								</table>
							</td>
					</table>
				</div>		
			</div>
			<div id="buttonDiv" style="margin: 10px 20px 0px;">
				<table border="0" cellpadding="0" cellspacing="0" width="100%">
					<tr>
						<td align="right">
							<table border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td>
										<input type="button" class="bt" style="margin: 5px"
											value="提 交" onclick="sub0();" />
									</td>
									<td>
										<input type="button" class="bt" style="margin: 5px"
											onclick="window.close()" value="返 回" />
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
		</s:form>
		</div>
		
		    
       <div id="binType1" style="display:none;">
		<s:form id="cardPinForm" name="cardPinForm" action="" method="post">
			<s:hidden name="issuerDTO.entityId"></s:hidden>
			<s:hidden name="issuerDTO.issuerCode"></s:hidden>
			<s:hidden name="cardSerialNumberDTO.cardBin" id="cardSerialNumberDTO.cardBin"/>
			<div id="customerContact"
				style="border: 1px solid #B9B9B9; margin: 20px 8px 0px">
				<div id="customerContactTitle"
					style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td class="TableTitleFront">
								<span class="TableTop">卡类别信息</span>
							</td>
							<td class="TableTitleEnd">
								&nbsp;
							</td>
						</tr>
					</table>
				</div>		
					<div 
					style="background-color: #FBFEFF; padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9; ">
					<table width="100%" style="table-layout: fixed;">
						<tr>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 110px; text-align: right;">
											卡BIN：
										</td>
										<td>
											<s:textfield name="cardBinBefore1" id="cardBinBefore1" disabled="true" />
										</td>
										</tr>
										<tr>
									  <td style="width: 90px; text-align: right;"><span class="no-empty">*</span>客户类别：
									  </td>
                                    <td>
                                        <edl:entityDictList displayName="dictType.customType1" dictValue="${dictType.customType}" dictType="501" tagType="2" defaultOption="true" />
                                    </td>
                                      <td style="width: 90px; text-align: right;"><span class="no-empty">*</span>卡片等级：
                                      </td>
                                    <td>
                                        <edl:entityDictList displayName="dictType.cardLever1" dictValue="${dictType.cardLever}" dictType="502" tagType="2" defaultOption="true" />
                                    </td>
                                                      
									</tr>
								</table>
							</td>
					</table>
				</div>
				
			</div>
			<div id="buttonDiv" style="margin: 10px 20px 0px;">
				<table border="0" cellpadding="0" cellspacing="0" width="100%">
					<tr>
						<td align="right">
							<table border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td>
										<input type="button" class="bt" style="margin: 5px"
											value="提 交" onclick="sub1();" />
									</td>
									<td>
										<input type="button" class="bt" style="margin: 5px"
											onclick="window.close()" value="返 回" />
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
		</s:form>
		</div>
    
    
      <div id="productType" style="display:none;">
		<s:form id="cardPinForm" name="cardPinForm" action="" method="post">
			<s:hidden name="issuerDTO.entityId"></s:hidden>
			<s:hidden name="issuerDTO.issuerCode"></s:hidden>
			<s:hidden name="cardSerialNumberDTO.cardBin" id="cardSerialNumberDTO.cardBin"/>
			<div id="customerContact"
				style="border: 1px solid #B9B9B9; margin: 20px 8px 0px">
				<div id="customerContactTitle"
					style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td class="TableTitleFront">
								<span class="TableTop">卡类别信息</span>
							</td>
							<td class="TableTitleEnd">
								&nbsp;
							</td>
						</tr>
					</table>
				</div>		
					<div 
					style="background-color: #FBFEFF; padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9; ">
					<table width="100%" style="table-layout: fixed;">
						<tr>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 110px; text-align: right;">
											卡BIN：
										</td>
										<td>
											<s:textfield name="cardBinBefore3" id="cardBinBefore3" disabled="true" />
										</td>
										
									  <td style="width: 90px; text-align: right;"><span class="no-empty">*</span>卡片类别：
									  </td>
                                    <td>
                                        <edl:entityDictList displayName="dictType.cardType" dictValue="${dictType.cardType}" dictType="503" tagType="2" defaultOption="true" />
                                    </td>                  
									</tr>
								</table>
							</td>
					</table>
				</div>
				
			</div>
			<div id="buttonDiv" style="margin: 10px 20px 0px;">
				<table border="0" cellpadding="0" cellspacing="0" width="100%">
					<tr>
						<td align="right">
							<table border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td>
										<input type="button" class="bt" style="margin: 5px"
											value="提 交" onclick="sub3();" />
									</td>
									<td>
										<input type="button" class="bt" style="margin: 5px"
											onclick="window.close()" value="返 回" />
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
		</s:form>
		</div>
    
    
</body>
</html>
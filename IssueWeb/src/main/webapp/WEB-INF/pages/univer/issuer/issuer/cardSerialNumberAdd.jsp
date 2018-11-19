<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/commons/meta.jsp"%>
		<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
		<script language="javaScript">
			 function sub(){
				 	var cardBinBefore=document.getElementById('cardBinBefore').value;				 	
				 	//var cardBinAfter=document.getElementById('cardBinAfter').value;
				 	var binType=document.getElementById('binType').value;
				 	var cardBinBeforeLength = cardBinBefore.length;
				 	//var cardBinAfterLength=cardBinAfter.length;
				 	var dif = 8 - cardBinBeforeLength;
				 	if(dif<0){
					 	alert("卡BIN码长度最多为8位");
					 	return;
				 	}
				 	//if(cardBinAfter=="" || cardBinAfter==null){
					//	errorDisplay('卡Bin不能为空!');
					//	return false;
					//}
				 	var cardBin = cardBinBefore;
				 	
					document.getElementById("cardSerialNumberDTO.cardBin").value = cardBin;
					document.getElementById("cardSerialNumberDTO.binType").value = binType;
					document.cardPinForm.action="${ctx}/insertCardBin.action";
					document.cardPinForm.submit(); 	 
				 }
			
			 function radio(cardBin){
				document.getElementById("cardBinBefore").value=cardBin;
			 }

			 function loadPage(){
				 	var cardBinList = document.getElementsByName("cardBinList");
				 	for( i=0 ; i<cardBinList.length ; i++){
						if(cardBinList.item(i).checked){
							document.getElementById("cardBinBefore").value=cardBinList[i].value;
							return;
						}
					}
				}
		</script>

		<base target="_self"></base>
		<title>添加卡BIN信息</title>
	</head>
	<body onload="loadPage();">
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>添加卡BIN信息</span>
		</div>
		
		<div id="listTable"
			style="background-color: #FBFEFF; padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
			<div id="customerContactTitle"
					style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td class="TableTitleFront">
								<span class="TableTop">卡BIN列表</span>
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
				<ec:table items="cardSerialNumberDTOs" var="map" width="100%"
					form="cardBinForm" action=""
					imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
					autoIncludeParameters="false" showPagination="false"
					sortable="false">
					<ec:row onclick="">
						<ec:column property="null" alias="" title="选择" width="5%"
							sortable="false">
							<input type="radio" name="cardBinList" onclick="radio('${map.cardBin}');" value="${map.cardBin}" checked="checked"  />
						</ec:column>
						<ec:column property="cardBin" title="卡BIN" width="10%" />
						<ec:column property="serialNumber" title="卡流水号" width="10%" />
					</ec:row>
				</ec:table>


			</s:form>
			</div>
		</div>

		<s:form id="cardPinForm" name="cardPinForm" action="" method="post">
			<s:hidden name="issuerDTO.entityId"></s:hidden>
			<s:hidden name="issuerDTO.issuerCode"></s:hidden>
			<s:hidden name="cardSerialNumberDTO.cardBin" id="cardSerialNumberDTO.cardBin"/>
			<s:hidden name="cardSerialNumberDTO.binType" id="cardSerialNumberDTO.binType"/>
			<div id="customerContact"
				style="border: 1px solid #B9B9B9; margin: 20px 8px 0px">
				<div id="customerContactTitle"
					style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td class="TableTitleFront">
								<span class="TableTop">卡BIN信息</span>
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
											<span class="no-empty">*</span>卡BIN：
										</td>
										<td>
										<s:if test="issuerDTO.defaultEntityId=='00000000'">
										<s:textfield name="cardBinBefore" id="cardBinBefore" />
										</s:if>
										<s:else>
										<s:textfield name="cardBinBefore" id="cardBinBefore" disabled="true" />
										</s:else>
										</td>
									
										<%-- <td style="width: 110px; text-align: right;">
											<span class="no-empty">*</span>卡BIN：
										</td>
										<td>
											<s:textfield name="cardBinAfter"
												id="cardBinAfter" maxlength="10" />
											<s:fielderror>
												<s:param>cardBinAfter</s:param>
											</s:fielderror>
										</td> --%>
										<td style="width: 110px; text-align: right;">
											<span class="no-empty">*</span>卡BIN类型：
										</td>
										<td>
											<s:select list="#{'0':'自动生成','1':'文件导入'} "  id="binType"></s:select>
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
											value="提 交" onclick="sub();" />
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
	</body>
</html>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<%@ include file="/commons/meta.jsp"%>
	<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
	
	<script type="text/javascript">
	function  submitForm(){
		var customerId;
		var customerType;
		var customerName;
		var cusType;
		var entityId;
		var checkboxs = document.getElementsByName('cardholderIds');		
		var count = 0;
		for (var i = 0; i < checkboxs.length; i++) {
	           if (checkboxs[i].checked) {
	           	var ids=checkboxs[i].value.split(",");
	           	customerId=ids[0];
	           	customerType=ids[1];
	           	customerName=ids[2];
	           	cusType=ids[3];
	           	entityId=ids[4];
	            count++;
	           }
	       }
		if(count<1){
        	errorDisplay("请选择一条记录操作！");
			return;
        }else if(count==1){
        	document.queryForm.action="${ctx}/cardholder/riskGRadeCrmInfo.action?customerId="+customerId+"&customerType="+customerType+"&customerName="+customerName+"&cusType="+cusType+"&entityId="+entityId;
			document.queryForm.submit(); 	
        }else{
        	errorDisplay("请选择一条记录操作！");
			return;
        }	 			
	}		
	
	</script>
	<title>客户风险等级同步</title>
	
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>客户风险等级同步</span>
		</div>
		<div id="query" style="border: 1px solid #B9B9B9; margin: 5px 8px 0px">
	        <div id="queryTitle" style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
	            <table width="100%" border="0" cellpadding="0" cellspacing="0">
	                <tr>
	                    <td class="TableTitleFront">
	                        <span class="TableTop">查询条件</span>
	                    </td>
	                    <td class="TableTitleEnd">
	                        &nbsp;
	                    </td>
	                </tr>
	            </table>
	        </div>
		    <div id="queryTable" style="width:100%; #FBFEFF; padding: 0px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
				<s:form id="queryForm" name="queryForm" action="cardholder/queryRiskGRade.action" method="post">
					<table width="80%" style="table-layout: fixed; text-align:center">
						<tr>
							<td>
								<table style="text-align: right; width: 55%">
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>证件号码：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="cardholderQueryDTO.IdNo" id="idNo" maxlength="50"/>
										</td>
									</tr>																										
								</table>
							</td>							
							<td align="right" colspan="2">
	                           	<table border="0" cellpadding="0" cellspacing="0">
	                                <tr>
	                                    <td>
	                                        <input type="button" class="bt" style="margin: 5px" onclick="queryForm.submit();" value="查 询"/>
	                                    </td>
	                                </tr>
	                           	</table>
	                       	</td>
						</tr>					
					</table>							
				</s:form>
			</div>
		</div>
		<div id="list" style="border: 1px solid #B9B9B9; margin: 20px 8px 0px">
			<div id="listTitle" style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
	            <table width="100%" border="0" cellpadding="0" cellspacing="0">
	                <tr>
	                    <td class="TableTitleFront">
	                        <span class="TableTop">记录列表</span>
	                    </td>
	                    <td class="TableTitleEnd">
	                        &nbsp;
	                    </td>
	                </tr>
	            </table>
        	</div>
        	<div id="listTable" style="background-color: #FBFEFF; padding: 0px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
	            <s:form id="cardholderForm" name="cardholderForm" action="cardholder/queryRiskGRade.action" method="post">
	            	<s:token></s:token>
	                <s:hidden name="judgeInforDTO.certID"></s:hidden>
	                <s:hidden name="judgeInforDTO.customerType"></s:hidden>
	                <s:hidden name="judgeInforDTO.CName"></s:hidden>
	                <s:hidden name="judgeInforDTO.entityID"></s:hidden>
	                <s:hidden name="judgeInforDTO.IdNo"></s:hidden>
	                 <ec:table items="pageDataDTO.data" var="map" width="100%"
	                    form="cardholderForm"
	                    action="${ctx}/cardholder/queryRiskGRade.action"
	                    imagePath="${ctx}/images/extremecomponents/*.gif"
	                    view="html"
	                    retrieveRowsCallback="limit"
	                    autoIncludeParameters="false">
	                    <ec:row>
	                        <ec:column property="null" alias="cardholderIds" title="选择" width="5%"   headerCell="selectAll">
	                            <input type="checkbox" name="cardholderIds" value="${map.customerId},${map.customerType},${map.customerName},${map.cusType},${map.entityId}" />
	                        </ec:column>
	                        <ec:column property="customerId" title="统一客户编码" width="11%" />
	                        <ec:column property="customerType" title="客户类型" width="11%" >
	                        	<s:property value="#attr['map'].customerType == 1 ? '个人' : '企业'"/>
	                        </ec:column>
	                        <ec:column property="customerName" title="客户名称" width="11%" />
	                        <ec:column property="entityId" title="客户编号" width="11%" />
	                        <ec:column property="customerIdNo" title="证件号码" width="11%" />                       
	                    </ec:row>
	                </ec:table>
	                <table border="0" cellpadding="0" cellspacing="0" width="100%">
	                    <tr>
	                        <td align="right">
	                            <table border="0" cellpadding="0" cellspacing="0">
	                                <tr>
										<td align="right">
											<table border="0" cellpadding="0" cellspacing="0">
												<tr height="35">								
												    <td align="right" colspan="3">
														 <input type="button" id="btn1"  class="bt" style="margin: 5px" onclick="submitForm();" value="客户风险等级批量同步" disabled="true"/></td>
												
													<td align="right" colspan="3">
														 <input type="button" id="btn" class="bt" style="margin: 5px" onclick="submitForm();" value="客户风险等级同步"/></td>
												</tr>
											</table>
										</td>
									</tr>
	                            </table>
	                        </td>
	                    </tr>
	                </table>
	            </s:form>
        	</div>
		</div>		
	</body>
	
	
</html>
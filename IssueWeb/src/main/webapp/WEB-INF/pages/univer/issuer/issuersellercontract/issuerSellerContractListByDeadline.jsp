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
		
	function submitForm(method) {
		if("add" == method){
			document.customerContractForm.action="${ctx}/issuerSellerConstract/add.action";
			document.customerContractForm.submit();
			return;
		}
		var flag = true;
		for(i = 0; i < document.getElementsByName("sellerContractIds").length; i++) {
			if (document.getElementsByName("sellerContractIds").item(i).checked) {
				if (method=="edit") {
					if (flag) {
						flag = false;
						
					} else {
						flag = true;
						errorDisplay("请选择一条记录操作！");
						return;
					}
				}
			}
		}
		if (method=="edit" && !flag) {
			document.customerContractForm.action="${ctx}/issuerSellerConstract/edit.action";
			document.customerContractForm.submit();
			return;
		}
		errorDisplay("请选择一条记录！");
	}
</script>
		<title>营销机构合同期限管理</title>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>营销机构合同期限管理</span>
		</div>
		<div id="query" style="border: 1px solid #B9B9B9; margin: 5px 8px 0px">
			<div id="queryTitle"
				style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
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
			<div id="queryTable"
				style="background-color: #FBFEFF; padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
				<s:form id="queryForm" name="queryForm"
					action="issuerSellerConstract/issuerSellerInqueryByDeadline.action"
					method="post">
					<s:hidden name="queryType"/>
					<table width="100%" style="table-layout: fixed;">
						<tr>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 110px; text-align: right;">
											营销机构合同号：
										</td>
										<td>
											<s:textfield name="sellerContractQueryDTO.sellContractId" />
											<s:fielderror>
												<s:param>sellerContractQueryDTO.sellContractId</s:param>
											</s:fielderror>
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 110px; text-align: right;">
											营销机构号：
										</td>
										<td>
											<s:textfield name="sellerContractQueryDTO.contractBuyer" />
											<s:fielderror>
												<s:param>sellerContractQueryDTO.contractBuyer</s:param>
											</s:fielderror>
										</td>
									</tr>
								</table>
							</td>


						</tr>
						 <tr>
                    	 <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 110px; text-align: right;">营销机构名称：</td>
                                    <td><s:textfield name="sellerContractQueryDTO.sellerName"/>
                                        <s:fielderror>
                                            <s:param>sellerContractQueryDTO.sellerName</s:param>
                                        </s:fielderror>
                                    </td>
                                </tr>
                            </table>
                        </td>
                         <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 110px; text-align: right;">结算周期名称：</td>
                                    <td><s:textfield name="sellerContractQueryDTO.ruleName"/>
                                        <s:fielderror>
                                            <s:param>sellerContractQueryDTO.ruleName</s:param>
                                        </s:fielderror>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
						<tr>
							<!--     <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 110px; text-align: right;">是否支持网上交易：</td>
                                    <td>
                                        <s:select name="sellerContractQueryDTO.webPayStat" list="#{'':'--请选择--', '1':'是', '0':'否'}"></s:select>
                                    </td>
                                </tr>
                            </table>
                        </td>-->
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 110px; text-align: right;">
											当前有效状态：
										</td>
										<td>
											<s:select name="sellerContractQueryDTO.contractState"
												list="#{'':'--请选择--', '1':'有效', '0':'无效'}"></s:select>
										</td>
									</tr>
								</table>
							</td>
							 <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 110px; text-align: right;">结算周期类型：</td>
                                    <td>
<!--                                    	<s:textfield name="sellerContractQueryDTO.ruleType"/>-->
										 <s:select  list="#{'':'--请选择--','1':'汇总结算'}"
												name="sellerContractQueryDTO.ruleType"
												id="sellerContractQueryDTO.ruleType" label="规则类型：" />
                                        <s:fielderror>
                                            <s:param>sellerContractQueryDTO.ruleType</s:param>
                                        </s:fielderror>
                                    </td>
                                </tr>
                            </table>
                        </td>
							</tr>
							<tr>
								<td align="right" colspan="2">
									<table border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td>
												<input type="button" class="bt" style="margin: 5px"
													onclick="queryForm.submit();" value="查 询" />
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
			<div id="listTitle"
				style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
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
			<div id="listTable"
				style="background-color: #FBFEFF; padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
				<s:form id="customerContractForm" name="customerContractForm"
					action="issuerSellerConstract/issuerSellerInquery.action"
					method="post">
					<s:hidden name="sellerContractQueryDTO.sellContractId"></s:hidden>
					<ec:table items="pageDataDTO.data" var="map" width="100%"
						form="customerContractForm"
						action="${ctx}/issuerSellerConstract/issuerSellerInquery.action"
						imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
						retrieveRowsCallback="limit" autoIncludeParameters="false">
						<ec:row>
							<ec:column property="null" alias="sellerContractIds" title="选择"
								width="10%"  headerCell="selectAll">
								<input type="checkbox" name="sellerContractIds"
									value="${map.sellContractId}" />
							</ec:column>
							<ec:column property="sellContractId" title="营销机构合同号" width="10%" >
								<a
									href="${ctx}/issuerSellerConstract/view.action?sellerContractDTO.sellContractId=${map.sellContractId}&queryType=${queryType}">
									<s:property value="#attr['map'].sellContractId" /> </a>
							</ec:column>
							<ec:column property="contractBuyer" title="营销机构号" width="10%"  />
							<ec:column property="sellerName" title="营销机构名称" width="10%" ></ec:column>
	                        <ec:column property="ruleName" title="结算周期名称" width="10%" />
	                        <ec:column property="clearTp" title="结算周期类型" width="10%" >
	                        	<s:if test="#attr['map'].clearTp==1">汇总结算</s:if>
	                        </ec:column>
							<ec:column property="expiryDate" title="失效时间" width="10%"
								cell="date" format="yyyy-MM-dd"  />
							<ec:column property="deliveryFee" title="快递费" width="5%" >${map.deliveryFee / 100}</ec:column>
							<ec:column viewsAllowed="false" property="webPayStat"
								title="是否支持网上交易" width="10%" >
								<s:if test="#attr['map'].webPayStat == 1">是</s:if>
								<s:else>否</s:else>
							</ec:column>
							<ec:column property="contractState" title="当前有效状态" width="10%" >
								<s:if test="#attr['map'].contractState == 1">有效</s:if>
								<s:else>无效</s:else>
							</ec:column>
						</ec:row>
					</ec:table>
					<table border="0" cellpadding="0" cellspacing="0" width="100%">
						<tr>
							<td align="right">
								<table border="0" cellpadding="0" cellspacing="0">
									<tr>
										<%--<td>
											<display:security urlId="30802">
												<input type="button" class="btn"
													style="width: 50px; height: 20px; background: url(${ctx}/images/icon/add.gif) no-repeat; margin: 2px 5px; text-align: right"
													onclick="submitForm('add')" value="添加" />
											</display:security>
										</td>  
										<td>
											<display:security urlId="409011">
												<input type="button" class="btn"
													style="width: 50px; height: 20px; background: url(${ctx}/images/icon/modify.gif) no-repeat; margin: 2px 5px; text-align: right"
													onclick="submitForm('edit');" value="编辑" />
											</display:security>
										</td>--%>
									<%--
                                        <td>
                                        	<display:security urlId="30804">
                                            <input type="button" class="btn" style="width: 50px; height: 20px; background: url(${ctx}/images/icon/delete.gif) no-repeat; margin: 2px 5px; text-align: right" onclick="submitForm('customerContractForm', '${ctx}/sellerConstract/delete.action', 'delete', 'sellerContractIds')" value="删除"/>
                                        	/display:security>
                                        </td>
                                        --%>
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
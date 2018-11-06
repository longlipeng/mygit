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
	    function choice() {
	        var ruleResult;
	        var sellerIdList = document.getElementsByName('sellerIdList');
	        for (var i=0; i<sellerIdList.length; i++) {
	            if (sellerIdList[i].checked) {
	                var ruleResult = sellerIdList[i].value;
	                break;
	            }
	        }
	        if (ruleResult != null) {
	            maskDocAll();
	            window.returnValue = ruleResult;
	            window.close();
	        } else {
	        	errorDisplay('请选择一个规则！');
	        }
	    }
		</script>
		<title>结算周期规则选择</title>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>结算周期规则选择</span>
		</div>
		<div id="query"
			style="border: 1px solid #B9B9B9; margin: 5px 8px 0px;">
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
					action="sellerConstract/settleRuleChoice.action" method="post">
				 <s:hidden name="type"></s:hidden>	
					<table width="100%" style="table-layout: fixed;">
						<tr>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 90px; text-align: right;">
											规则号：
										</td>
										<td>
											<s:textfield name="settlePeriodRuleQueryDTO.ruleNo" />
											<s:fielderror>
												<s:param>settlePeriodRuleQueryDTO.ruleNo</s:param>
											</s:fielderror>
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 120px; text-align: right;">
											规则名称：
										</td>
										<td>
											<s:textfield name="settlePeriodRuleQueryDTO.ruleName" />
											<s:fielderror>
												<s:param>settlePeriodRuleQueryDTO.ruleName</s:param>
											</s:fielderror>
										</td>
									</tr>
								</table>
							</td>
						</tr>
                        <tr>
<%--							<td>--%>
<%--								<table style="text-align: left; width: 100%">--%>
<%--									<tr>--%>
<%--										<td style="width: 90px; text-align: right;">--%>
<%--											规则状态 ：--%>
<%--										</td>--%>
<%--										<td>--%>
<%--											<s:select --%>
<%--                                            list="#{'1':'新建','2':'启用','0':'删除'}" --%>
<%--                                            name="settlePeriodRuleQueryDTO.state" --%>
<%--                                            emptyOption="false"--%>
<%--                                            label="state" --%>
<%--                                            headerKey="" --%>
<%--                                            headerValue="--请选择--"--%>
<%--                                           />--%>
<%--											<s:fielderror>--%>
<%--												<s:param>settlePeriodRuleQueryDTO.state</s:param>--%>
<%--											</s:fielderror>--%>
<%--										</td>--%>
<%--									</tr>--%>
<%--								</table>--%>
<%--							</td>--%>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 90px; text-align: right;">
											周期类型：
										</td>
										<td>
											<s:select 
                                            list="#{'1':'固定周期','0':'非固定周期','2':'混合周期'}" 
                                            name="settlePeriodRuleQueryDTO.ruleType" 
                                            emptyOption="false"
                                            label="state" 
                                            headerKey="" 
                                            headerValue="--请选择--"
                                           />
											<s:fielderror>
												<s:param>settlePeriodRuleQueryDTO.ruleType</s:param>
											</s:fielderror>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						
						<tr>
							<td>
							</td>
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
		<div id="list"
			style="border: 1px solid #B9B9B9; margin: 20px 8px 0px;">
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
			  <s:form id="ruleForm" name="ruleForm" action="sellerConstract/settleRuleChoice.action" method="post">
			  		
			  		<s:hidden name="settlePeriodRuleQueryDTO.ruleNo" />
			  		<s:hidden name="settlePeriodRuleQueryDTO.ruleName" />
			  		<s:hidden name="settlePeriodRuleQueryDTO.state" />
			  		<s:hidden name="settlePeriodRuleQueryDTO.periodType" />
			  
					<ec:table items="pageDataDTO.data" var="map" width="100%"
						form="ruleForm"
						action="${ctx}/sellerConstract/settleRuleChoice.action"
						imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
						retrieveRowsCallback="limit" autoIncludeParameters="false">
							<ec:exportXls fileName="settleRuleList.xls" tooltip="导出Excel" />
						<ec:row>
							<ec:column property="null" alias="sellerIdList" title="选择"
								width="5%" sortable="false" >	  
									<input type="radio" name="sellerIdList" value="${map.ruleNo},${map.ruleName}"/>
										<c:set var="ruleNo" value="${map.ruleNo}" scope="page"/>
							</ec:column>
							<ec:column property="ruleNo" title="规则号" width="10%" >
							<a href="${ctx}/sellerConstract/contractView.action?ruleNo=${map.ruleNo}">${map.ruleNo}</a>
							</ec:column>
							<ec:column property="ruleName" title="规则名称" width="20%" />
							<ec:column property="ruleType" title="周期类型" width="10%" />
							<ec:column property="state" title="规则状态" width="10%" />
							<ec:column property="modifyTime" title="操作时间" width="10%" />
						</ec:row>
					</ec:table>
				<table border="0" cellpadding="0" cellspacing="0" width="100%">
                    <tr>
                        <td align="right">
                            <table border="0" cellpadding="0" cellspacing="0">
                                <tr>
                                    <td>
                                        <input type="button" class="bt" style="margin: 5px" onclick="choice();" value="提 交"/>
                                    </td>
                                    <td>
                                        <input type="button" class="bt" style="margin: 5px" onclick="window.close();" value="返 回"/>
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
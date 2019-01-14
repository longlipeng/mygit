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
	    function choice(){
	        var flag = true;
	        var selectValue = "";
	        for(i = 0; i < document.getElementsByName("feeRuleIdList").length; i++) {
	            if (document.getElementsByName("feeRuleIdList").item(i).checked) {
	                if(flag){
	                    flag=false;
	                    selectValue=document.getElementsByName("feeRuleIdList").item(i).value;
	                }
	            }
	        }
	        if (flag) {
	        	errorDisplay("请选择一条记录操作！");
	            return;
	        } else {
	            window.returnValue=selectValue;
	            window.close();
	        }
	    }
	    function cacInf(data){
			window.showModalDialog('${ctx}/caclInf/view.action?state=1&caclInfQueryDTO.caclInfDTO.discCd='+data, 
				'_blank', 'center:yes;dialogHeight:300px;dialogWidth:700px;resizable:no');
		}
		</script>
		<title>账户计算规则设置</title>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>账户计算规则设置</span>
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
				<s:form id="queryForm" name="queryForm" action="sellerConstract/serviceRuleChoice.action" method="post">
				 <s:hidden name="type"></s:hidden>	
					<table width="100%" style="table-layout: fixed;">
						<tr>
										<td style="width: 120px; text-align: right;">
											规则号：
										</td>
										<td>
											<s:textfield name="caclInfQueryDTO.discCd" />
											<s:fielderror>
												<s:param>caclInfQueryDTO.discCd</s:param>
											</s:fielderror>
										</td>

										<td style="width: 120px; text-align: right;">
											规则名称：
										</td>
										<td>
											<s:textfield name="caclInfQueryDTO.caclName" />
											<s:fielderror>
												<s:param>caclInfQueryDTO.caclName</s:param>
											</s:fielderror>
										</td>
						</tr>
						
						<tr>
							<td>
							</td>
							<td align="right" colspan="3">
											<input type="button" class="bt" style="margin: 5px"
												onclick="queryForm.submit();" value="查 询" />
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
			  <s:form id="ruleForm" name="ruleForm" action="sellerConstract/serviceRuleChoice.action" method="post">
					<ec:table items="pageDataDTO.data" var="map" width="100%"
						form="ruleForm"
						action="${ctx}/sellerConstract/serviceRuleChoice.action"
						imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
						retrieveRowsCallback="limit" autoIncludeParameters="false">
						<ec:row>
							<ec:column property="null" alias="feeRuleIdList" title="选择" width="8%" sortable="false">	  
									<input type="radio" name="feeRuleIdList" value="${map.discCd}"/>
										<c:set var="discCd" value="${map.discCd}" scope="page"/>
							</ec:column>
							<ec:column property="discCd" title="规则号" width="10%" >
							<%-- <a href="${ctx}/caclInf/view.action?state=1&caclInfQueryDTO.caclInfDTO.discCd=${map.discCd}">${map.discCd}</a>--%> 
							<a href="javascript:cacInf('${map.discCd}')">${map.discCd}</a>
							</ec:column>
							<ec:column property="caclName" title="规则名称" width="10%" />
							<ec:column property="dataStat" title="规则状态" width="10%" />
							<ec:column property="recCrtTs" title="创建时间" width="15%" />
							<ec:column property="recUpdTs" title="操作时间" width="15%" />
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
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
		var box;
		var userChoice="";
		function checkedCount() {
        var checkboxs = document.getElementsByName('feeRuleIdList');
        var count = 0;
        for (var i = 0; i < checkboxs.length; i++) {
            if (checkboxs[i].checked) {               
                count++;
            }
        }
        return count;
    }
		   function enableRule(){
              var count = checkedCount();
              if (count < 1) {
              alert('请至少选择一条记录！');
              return;
          }
           confirm("确认要启用吗?",update);
          }
         function deleteRule(){
         	userChoice="delete";
           var count = checkedCount();
              if (count < 1) {
              alert('请至少选择一条记录！');
              return;
          }
           confirm("确认要删除吗?",update);
         }

	  function update(type){
		  var ruleForm = Ext.get("ruleForm").dom;
		  ruleForm['ec_eti'].disabled=true;
		if(type=="add"){
			ruleForm.action='${ctx}/caclInf/add.action';
		}else if(type=="edit"){
			var count = checkedCount();
              if (count < 1) {
              	alert('请至少选择一条记录！');
              	return;
              }
			ruleForm.action='${ctx}/caclInf/view.action?state=0';
		}else if(userChoice=='delete'){
			ruleForm.action='${ctx}/caclInf/update.action?state=3';
		}else{
			ruleForm.action='${ctx}/caclInf/update.action?state=2';
		}		
		ruleForm.submit();
	  }

		</script>
		<title>计算规则设置</title>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>计算规则设置</span>
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
					action="caclInf/listCaclInf.action" method="post">
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
											<s:textfield name="caclInfQueryDTO.caclDspDTO.discCd" />
											<s:fielderror>
												<s:param>caclInfQueryDTO.CaclDspDTO.discCd</s:param>
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
										<td style="width: 90px; text-align: right;">
											规则状态 ：
										</td>
										<td>
											<s:select list="#{'1':'新建','2':'启用','0':'删除'}"
												name="caclInfQueryDTO.caclDspDTO.dataStat"
												emptyOption="false" label="state" headerKey=""
												headerValue="--请选择--" />
											<s:fielderror>
												<s:param>caclInfQueryDTO.CaclDspDTO.dataStat</s:param>
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
										<td style="width: 90px; text-align: right;">
											规则名称：
										</td>
										<td>
											<s:textfield name="caclInfQueryDTO.caclName" />
											<s:fielderror>
												<s:param>caclInfQueryDTO.caclName</s:param>
											</s:fielderror>
										</td>
									</tr>
								</table>
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
				<s:form id="ruleForm" name="ruleForm"
					action="caclInf/listCaclInf.action" method="post">
					<s:token></s:token>
					<s:hidden name="caclInfQueryDTO.caclDspDTO.discCd"></s:hidden>
			        <s:hidden name="caclInfQueryDTO.caclDspDTO.dataStat"></s:hidden>
			        <s:hidden name="caclInfQueryDTO.caclName"></s:hidden>
					<ec:table items="pageDataDTO.data" var="map" width="100%"
						form="ruleForm" action="caclInf/listCaclInf.action"
						imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
						retrieveRowsCallback="limit" autoIncludeParameters="false">
						<ec:exportXls fileName="settleRuleList.xls" tooltip="导出Excel" />
						<ec:row>
							<ec:column property="null" alias="feeRuleIdList" title="选择"
								width="5%"  headerCell="selectAll">
								<s:if test="#attr['map'].dataStat=='新建'">
									<input type="checkbox" name="feeRuleIdList" value="${map.discCd}" />
								</s:if>
								<c:set var="discCd" value="${map.discCd}" scope="page" />
							</ec:column>
							<ec:column property="discCd" escapeAutoFormat="true" title="规则号" width="10%" >
								<a
									href="${ctx}/caclInf/view.action?state=1&caclInfQueryDTO.caclInfDTO.discCd=${map.discCd}">${map.discCd}</a>
							</ec:column>
							<ec:column property="caclName" title="规则名称" width="20%"  />
							<ec:column property="dataStat" title="规则状态" width="10%"  />
							<ec:column property="recCrtTs" title="操作时间" width="10%"  />
						</ec:row>
					</ec:table>
					<table border="0" cellpadding="0" cellspacing="0" width="100%">
						<tr>
							<td align="right">
								<table border="0" cellpadding="0" cellspacing="0">
									<tr>

										<td>
										<display:security urlId="60701">
											<input type="button" class="btn"
												style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/add.gif) no-repeat; text-align: right"
												onclick="update('add');" value="添加" />
										</display:security>
										</td>

										<td>
										<display:security urlId="60702">
											<input type="button" class="btn"
												style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/modify.gif) no-repeat; text-align: right"
												onclick="update('edit');" value="编辑" />
										</display:security>
										</td>
										<td>
										<display:security urlId="60703">
											<input type="button" class="btn"
												style="width: 70px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/modify.gif) no-repeat; text-align: right"
												onclick="enableRule();" value="启用" />
										</display:security>
										</td>

										<td>
										<display:security urlId="60704">
											<input type="button" class="btn"
												style="width: 70px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/modify.gif) no-repeat; text-align: right"
												onclick="deleteRule();" value="删除" />
										</display:security>
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
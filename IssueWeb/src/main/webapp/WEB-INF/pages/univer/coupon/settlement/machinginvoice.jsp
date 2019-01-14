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

function changeAmount(){
    	var checkboxs = document.getElementsByName('invoiceRequireIds');
		var cutdowns = document.getElementsByName('cutdown');
		var inputStr;
		var amountCount = 0;
		for ( var i = 0; i < checkboxs.length; i++) {
			if (checkboxs[i].checked) {
				//inputStr = checkboxs[i].value;
				//var vals = inputStr.split(',');
				var r = /^[0-9]*[1-9][0-9]*$/;
				if(!r.test(cutdowns[i].value)){
					amountCount = parseInt(amountCount);
				}else{
					amountCount = parseInt(amountCount) +parseInt(cutdowns[i].value);
				}
				
			}
		}
		var chooseAmount = document.getElementById('chooseAmount');
		chooseAmount.value = amountCount;
    }
    function checkedBox(){
    	var checkboxs = document.getElementsByName('invoiceRequireIds');
		var cutdowns = document.getElementsByName('cutdown');
		var inputStr;
		var amountCount = 0;
		for ( var i = 0; i < checkboxs.length; i++) {
			if (checkboxs[i].checked) {
				//inputStr = checkboxs[i].value;
				//var vals = inputStr.split(',');
				var r = /^[0-9]*[1-9][0-9]*$/;
				if(!r.test(cutdowns[i].value)){
					alert("请输入正整数");
					//messageDisplay("数值不对","请输入正整数");
					return false;
				};
				amountCount = parseInt(amountCount) +parseInt(cutdowns[i].value);
			}
		}
		var chooseAmount = document.getElementById('chooseAmount');
		chooseAmount.value = amountCount;
    }
	function doInvoice() {
		var inputStr;
		var customerCode = undefined;
		var checkboxs = document.getElementsByName('invoiceRequireIds');
		var cutdowns = document.getElementsByName('cutdown');
		var count = 0;
		for ( var i = 0; i < checkboxs.length; i++) {
			if (checkboxs[i].checked) {
				inputStr = checkboxs[i].value;
				var vals = inputStr.split(',');
				if(customerCode==undefined){
				customerCode = vals[2];}else{
					if(customerCode != vals[2]){
						alert('只能勾选同一个客户！');
						return false;
					}
				}
				var r = /^[0-9]*[1-9][0-9]*$/;
				if(!r.test(cutdowns[i].value)){
					alert("请输入正整数");
					return false;
				};
				if(cutdowns[i].value-vals[4]>0){
					alert('超出可开票金额！');
					return false;
				}
				checkboxs[i].value = vals[0] + ',' + vals[1] + ',' + vals[2] + ',' +vals[3] +','
						+ cutdowns[i].value;
				count++;
			}
		}
		var invoiceRequireForm = Ext.get("invoiceRequireForm").dom;
		if (count < 1) {
			alert('请至少选择一条记录！');
			return;
		}
		var waitIvcAmount = document.getElementById('waitIvcAmount').value;
		var chooseAmount = document.getElementById('chooseAmount').value;
		if(chooseAmount - waitIvcAmount > 0){
			alert("已勾金额超出结算单可开票金额");
				return false;
		}
		
		var returnValue = window.showModalDialog('${ctx}/settlement/invoiceProject.action?invoiceRequireMentDto.customerCode='+customerCode+'',
				'_blank',
				'center:yes;dialogHeight:400px;dialogWidth:600px;resizable:no');
		if(undefined != returnValue) {
			document.getElementById('invoiceRequireMentQueryDto.invoiceProject').value = returnValue;
			invoiceRequireForm.action = '${ctx}/settlement/addMachingInvoice.action';
			invoiceRequireForm.submit();
		}
		
	}
	function doDeleteInvoice() {
		var checkboxs = document.getElementsByName('invoiceTmpIds');
		var count = 0;
		for ( var i = 0; i < checkboxs.length; i++) {
			if (checkboxs[i].checked) {
				count++;
			}
		}
		var invoiceRequireForm = Ext.get("invoiceTempForm").dom;
		if (count < 1) {
			alert('请至少选择一条记录！');
			return;
		}
		confirm("确定执行删除操作?",function del(){
			invoiceRequireForm.action = '${ctx}/settlement/delInvoiceTemp.action';
			invoiceRequireForm.submit();
		});
		
	}
	function doSaveInvoice() {
		var amounts = document.getElementsByName('amounts');
		if (amounts.length <= 1) {
			alert('请先匹配发票！');
			return;
		}
		var waitIvcAmount = document.getElementById('waitIvcAmount').value;
		var sumAmount = document.getElementById('sumAmount').value;
		if(sumAmount - waitIvcAmount > 0){
			alert("发票小计超出结算单可开票金额");
				return false;
		}
		var invoiceTempForm = Ext.get("invoiceTempForm").dom;

		invoiceTempForm.action = '${ctx}/settlement/addInvoice.action';
		invoiceTempForm.submit();
	}
	function openSettlementPage(settleMentId) {
		var returnValue = window.showModalDialog('${ctx}/settlement/toCommonInvoice.action?settleMentId='+settleMentId+'',
				'_blank',
				'center:yes;dialogHeight:400px;dialogWidth:600px;resizable:no');
		//var queryForm = Ext.get("queryForm").dom;
		var queryForm = document.getElementById('queryForm');
		//alert(returnValue)
		if("1" == returnValue) {
			queryForm.submit();
		}
	}
</script>
<title>结算单匹配发票</title>
</head>
<body>
	<%@ include file="/commons/messages.jsp"%>
	<div id="showSettle" style="border: 1px solid #B9B9B9; margin: 5px 8px 0px;">
		<table width="100%" style="table-layout: fixed;">
				<tr height="35">
				    <td width="85" align=right><span>结算单号：</span>
					</td>
					<td width="160" ><s:label
							name="settlementInfoDto.settleNo" />
							 </td>
					<td width="85" align=right><span>结算单金额：</span>
					</td>
					<td width="160" ><s:label
							name="settlementInfoDto.totalAmount" />
							 </td>
					<td width="90" align=right><span>可开发票金额：</span>
					</td>
					<td width="160"><s:label
							name="settlementInfoDto.waitIvcAmount" /><s:hidden id="waitIvcAmount" name="settlementInfoDto.waitIvcAmount"></s:hidden></td>
				</tr></table>
	</div>
      <div id="query" style="border: 1px solid #B9B9B9; margin: 5px 8px 0px;">
		<div id="queryTitle"
			style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td class="TableTitleFront"><span class="TableTop">查询发票需求</span>
					</td>
					<td class="TableTitleEnd">&nbsp;</td>
				</tr>
			</table>
		</div>
	<div id="queryTable"
		style="background-color: #FBFEFF; padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
		<s:form id="queryForm" name="queryForm"
			action="/settlement/toMachingInvoice.action" method="post">
			<s:hidden name="settleMentId"></s:hidden>
			<table width="100%" style="table-layout: fixed;">
				<tr height="35">
					<td width="85" align=right><span>客户编码：</span>
					</td>
					<td width="160"><s:textfield
							name="invoiceRequireMentQueryDto.entityId" /> <s:fielderror>
							<s:param>InvoiceRequireMentQueryDto.entityId</s:param>
						</s:fielderror>
					</td>
					<!--					TODO					-->
					<td width="90" align=right><span>交易类型：</span>
					</td>
					<td width="160"><s:select id="orderType" name="orderType"
							list="#{'0':'充值'}"></s:select>
					</td>
				</tr>

				<tr height="35">
					<td width="85" align=right><span>客户名称：</span>
					</td>
					<td width="160"><s:textfield
							name="invoiceRequireMentQueryDto.customerName" /> <s:fielderror>
							<s:param>invoiceRequireMentQueryDto.customerName</s:param>
						</s:fielderror>
					</td>
					<td width="90" align=right><span>税号：</span>
					</td>

					<td width="160"><s:textfield
							name="invoiceRequireMentQueryDto.taxCode" /> <s:fielderror>
							<s:param>invoiceRequireMentQueryDto.taxCode</s:param>
						</s:fielderror>
					</td>
				</tr>

				<tr>
					<td width="85" align=right><span>起始日期：</span>
					</td>
					<td width="160"><s:textfield
							name="invoiceRequireMentQueryDto.startTime"
							id="invoiceRequireMentQueryDto.startTime" size="20"
							onfocus="dateClick(this)" cssClass="Wdate" /> <s:fielderror>
							<s:param>invoiceRequireMentQueryDto.startTime</s:param>
						</s:fielderror>
					</td>
					<td width="85" align=right><span>结束日期：</span>
					</td>
					<td width="160"><s:textfield
							name="invoiceRequireMentQueryDto.stopTime"
							id="invoiceRequireMentQueryDto.stopTime" size="20"
							onfocus="dateClick(this)" cssClass="Wdate" /> <s:fielderror>
							<s:param>orderInvoiceInfoQueryDTO.stopTime</s:param>
						</s:fielderror>
					</td>
				</tr>
				<tr>
					<td width="85" align=right></td>
					<td width="160"></td>
					<td align="center" colspan="2">
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td><input type="button" class="bt" style="margin: 5px"
									onclick="queryForm.submit();" value="查 询" />
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</s:form>
	</div>
	<div id="list" style="border: 1px solid #B9B9B9; margin: 20px 8px 0px;">
		<div id="listTitle"
			style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td class="TableTitleFront"><span class="TableTop">记录列表</span>
					</td>
					<td class="TableTitleEnd">&nbsp;</td>
				</tr>
			</table>
		</div>
		<div id="listTable"
			style="background-color: #FBFEFF; padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
			<s:form id="invoiceRequireForm" name="invoiceRequireForm"
				action="/settlement/toMachingInvoice.action" method="post">
				<s:hidden name="settleMentId"></s:hidden>
				<s:hidden name="invoiceRequireMentQueryDto.entityId"></s:hidden>
				<s:hidden name="invoiceRequireMentQueryDto.customerName"></s:hidden>
				<s:hidden name="invoiceRequireMentQueryDto.taxCode"></s:hidden>
				<s:hidden name="invoiceRequireMentQueryDto.startTime"></s:hidden>
				<s:hidden name="invoiceRequireMentQueryDto.stopTime"></s:hidden>
				<s:hidden id="invoiceRequireMentQueryDto.invoiceProject" name="invoiceRequireMentQueryDto.invoiceProject">
				</s:hidden>
				<ec:table items="result1" var="map" width="100%" tableId="table1"
					form="invoiceRequireForm"
					action="${ctx}/settlement/toMachingInvoice.action"
					imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
					retrieveRowsCallback="limit" autoIncludeParameters="false">
					<ec:row>

						<ec:column property="null" alias="invoiceRequireIds" title="选择"
							width="3%" sortable="false" headerCell="checkAll">
							<input type="checkbox" name="invoiceRequireIds" onclick="checkedBox()"
								value="${map.id},${map.fatherEntityId},${map.entityId},${map.customerName},${map.amount}" />
						</ec:column>
						<ec:column property="entityId" title="客户编码" width="6%">

						</ec:column>
						<ec:column property="customerName" title="客户名称" width="5%">

						</ec:column>
						<ec:column property="amount" title="金额(单位：分)" width="6%">

						</ec:column>
						<ec:column property="bankAcctCode" title="银行账号" width="6%" />
						<ec:column property="taxCode" title="税号" width="6%" />

						<ec:column property="invoiceType" title="发票种类" width="6%">
						    <c:choose>
								<c:when test="${map.invoiceType=='1'}">增值税专用发票</c:when>
								<c:when test="${map.invoiceType=='2'}">地税票</c:when>
							</c:choose>
						</ec:column>
						<ec:column property="createTime" title="创建日期" cell="date"
								format="yyyy-MM-dd"  width="10%" />
						<ec:column property="null" title="开票金额(单位：分)" sortable="false" width="16%">
							
						<input type="text" style="ime-mode: disabled;" name="cutdown" value="${map.amount}" onchange="checkedBox()" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')};changeAmount()" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
						</ec:column>
					</ec:row>
				</ec:table>
				<table border="0" cellpadding="0" cellspacing="0" width="100%">
					<tr>
						<td align="right">
							<table border="0" cellpadding="0" cellspacing="0">
								<tr>
								<td width="85" align=right>已勾选金额：
					            </td>
					        <td align=right><input type="text"
							 id="chooseAmount" value="0" readonly="readonly" style=" border:none;width: 80px;text-align: center"/>
							 </td>
									<td><input type="button" class="bt" style="margin: 5px"
										onclick="openSettlementPage(${settleMentId});" value="普票增加" />
									</td>
									<td><input type="button" class="bt" style="margin: 5px"
										onclick="doInvoice();" value="增值税票增加" />
									</td>


								</tr>
							</table>
						</td>
					</tr>
				</table>
			</s:form>
		</div>
		<div id="listTable2"
			style="background-color: #FBFEFF; padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
			<s:form id="invoiceRequireTempForm" name="invoiceRequireTempForm"
				action="/settlement/toMachingInvoice.action" method="post">
				<s:hidden name="settleMentId"></s:hidden>
				<s:hidden name="invoiceRequireMentQueryDto.entityId"></s:hidden>
				<s:hidden name="invoiceRequireMentQueryDto.customerName"></s:hidden>
				<s:hidden name="invoiceRequireMentQueryDto.taxCode"></s:hidden>
				<s:hidden name="invoiceRequireMentQueryDto.startTime"></s:hidden>
				<s:hidden name="invoiceRequireMentQueryDto.stopTime"></s:hidden>
				<ec:table items="result2" var="map" width="100%" tableId="table2"
					form="invoiceRequireTempForm"
					action="${ctx}/settlement/toMachingInvoice.action"
					imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
					retrieveRowsCallback="limit" autoIncludeParameters="false">
					<ec:row>

						<ec:column property="customerEntityId" title="客户编码" width="6%">

						</ec:column>
						<ec:column property="customerName" title="客户名称" width="5%">

						</ec:column>
						<ec:column property="amount" title="金额" width="6%" />
						<ec:column property="bankAcctCode" title="银行账号" width="6%" />
						<ec:column property="taxCode" title="税号" width="6%" />

						<ec:column property="invoiceType" title="发票种类" width="6%">
						    <c:choose>
								<c:when test="${map.invoiceType=='1'}">增值税专用发票</c:when>
								<c:when test="${map.invoiceType=='2'}">地税票</c:when>
							</c:choose>
						</ec:column>

					</ec:row>
				</ec:table>
			</s:form>
		</div>
		<div id="listTable3"
			style="background-color: #FBFEFF; padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
			<s:form id="invoiceTempForm" name="invoiceTempForm"
				action="/settlement/toMachingInvoice.action" method="post">
				<s:hidden name="settleMentId"></s:hidden>
				<s:hidden name="invoiceRequireMentQueryDto.entityId"></s:hidden>
				<s:hidden name="invoiceRequireMentQueryDto.customerName"></s:hidden>
				<s:hidden name="invoiceRequireMentQueryDto.taxCode"></s:hidden>
				<s:hidden name="invoiceRequireMentQueryDto.startTime"></s:hidden>
				<s:hidden name="invoiceRequireMentQueryDto.stopTime"></s:hidden>
				<ec:table items="result3" var="map" width="100%" tableId="table3"
					form="invoiceTempForm" action="${ctx}/settlement/toMachingInvoice.action"
					imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
					retrieveRowsCallback="limit" autoIncludeParameters="false">
					<ec:row>
						<input type="hidden" name="amounts" value="${map.amount}" />
						<ec:column property="null" alias="invoiceTmpIds" title="选择"
							width="3%" sortable="false" headerCell="selectAll">
							<input type="checkbox" name="invoiceTmpIds" value="${map.id}" />
						</ec:column>
						<ec:column property="customerEntityId" title="客户编码" width="6%">

						</ec:column>
						<ec:column property="customerName" title="客户名称" width="5%">

						</ec:column>
						<ec:column property="amount" title="金额" width="6%" />
						<ec:column property="invoiceProject" title="开票项目" width="6%" 
						cell="entityDictInfo" alias="120"/>
						<ec:column property="bankAcctCode" title="银行账号" width="6%" />
						<ec:column property="taxCode" title="税号" width="6%" />

						<ec:column property="invoiceType" title="发票种类" width="6%">
						    <c:choose>
								<c:when test="${map.invoiceType=='1'}">增值税专用发票</c:when>
								<c:when test="${map.invoiceType=='2'}">地税票</c:when>
							</c:choose>
						</ec:column>

					</ec:row>
				</ec:table>
				<table border="0" cellpadding="0" cellspacing="0" width="100%">
					<tr>
						<td align="right">
							<table border="0" cellpadding="0" cellspacing="0">
							    <tr>
							    <td><span>开票小计：</span></td>
					            <td><s:label name="sumAmount" />
					            <s:hidden id="sumAmount" name="sumAmount"></s:hidden></td>
							    </tr>
								<tr>

									<td><input type="button" class="bt" style="margin: 5px"
										onclick="doDeleteInvoice();" value="删除" />
									</td>
									<td><input type="button" class="bt" style="margin: 5px"
										onclick="doSaveInvoice();" value="保存" />
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
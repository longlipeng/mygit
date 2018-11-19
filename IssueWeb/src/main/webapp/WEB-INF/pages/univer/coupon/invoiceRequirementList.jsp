<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/commons/meta.jsp"%>

		<style>
		 #inhalt {  
		            position: absolute;  
		            height: 200px;  
		            width: 400px;  
		            margin: -100px 0px 0px -200px;  
		            top: 50%;  
		            left: 50%;  
		            text-align: left;  
		            padding: 0px;  
		            border: 1px dotted #000000;  
		            overflow: auto;  
		            background-color: #FFFFFF;
		        }  

        </style>
        
<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
<script type="text/javascript">
	function doInvoice() {
		var result = "";
		var invoiceIdList = document.getElementsByName("invoiceIdList");

		var tempFatherId="";
		var tempEntityId="";
		var tempMoney=0;
		var money=0;
		for ( var i = 0; i < invoiceIdList.length; i++) {

			var invoiceId = invoiceIdList[i];
			if (invoiceId.checked) {
				var splits = invoiceId.value.split(",");
				if (tempEntityId=="" || tempEntityId==""){
					tempEntityId = splits[1];
					tempFatherId = splits[2];
					tempMoney = splits[3];
				}
				if (tempEntityId != splits[1] || tempFatherId!=splits[2]){
					alert("勾选的选项中必须是同一个商户！");
					return ;
				}
				result = result + "," + splits[0];
				money= parseInt(money) + parseInt(tempMoney);
			}
		}
		var count = result.length;
		if (count == 0) {
			alert('请至少选择一条记录！');
			return;
		}
		document.getElementById("hidInvoiceId").value = result;
		document.getElementById("inhalt").style.display='';
		document.getElementById("showMoney").innerText = money;
	}

	function closeWin(){
		document.getElementById("inhalt").style.display='none';
	}
	
</script>

<title>发票需求界面</title>
</head>
<body>
<%@ include file="/commons/messages.jsp"%>
<div class="TitleHref"><span>发票需求界面</span></div>
<div id="query" style="border: 1px solid #B9B9B9; margin: 5px 8px 0px;">
<div id="queryTitle"
	style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td class="TableTitleFront"><span class="TableTop">查询条件</span></td>
		<td class="TableTitleEnd">&nbsp;</td>
	</tr>
</table>
</div>

<div id="queryTable"
	style="background-color: #FBFEFF; padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
<s:form id="queryForm" name="queryForm"
	action="/invoiceRequirement/list.action" method="post">
	<s:hidden name="type"></s:hidden>
	<table width="100%" style="table-layout: fixed;">
		<tr height="35">
			<td width="85" align=right><span>公司名称：</span></td>
			<td width="160"><s:textfield
				name="invoiceRequirementsDTO.invoiceName" /> <s:fielderror>
				<s:param>invoiceRequirementsDTO.invoiceName</s:param>
			</s:fielderror></td>
			<td width="90" align=right><span>发票状态：</span></td>
			<td width="160"><dl:dictList dictType="502" tagType="2"
				defaultOption="true" displayName="invoiceRequirementsDTO.status"
				dictValue="${invoiceRequirementsDTO.status}"></dl:dictList> <s:fielderror>
				<s:param>invoiceRequirementsDTO.status</s:param>
			</s:fielderror></td>
		</tr>
		<tr height="35">
			<td width="85" align=right><span>票据方向：</span></td>
			<td width="160">
			<s:select name="invoiceRequirementsDTO.aspect"
								list="#{'1':'正常票','0':'红票'}" theme="simple" headerKey=""
								headerValue="-请选择-" /></td>
			<td width="90" align=right><span>客户号：</span></td>

			<td width="160"><s:textfield
				name="invoiceRequirementsDTO.customerEntityId" /> <s:fielderror>
				<s:param>invoiceRequirementsDTO.customerEntityId</s:param>
			</s:fielderror></td>
		</tr>
		<tr>
			<td align="center" colspan="2">
			<table border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td><input type="button" class="bt" style="margin: 5px"
						onclick="queryForm.submit();" value="查 询" /></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</s:form></div>
</div>

<div id="list" style="border: 1px solid #B9B9B9; margin: 20px 8px 0px;">
<div id="listTitle"
	style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td class="TableTitleFront"><span class="TableTop">记录列表</span></td>
		<td class="TableTitleEnd">&nbsp;</td>
	</tr>
</table>
</div>
<div id="listTable"
	style="background-color: #FBFEFF; padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
<s:form id="invoiceRequirement.Form" name="orderInvoiceForm"
	action="/invoiceRequirement/list.action" method="post">
	<s:hidden name="invoiceRequirementsDTO.invoiceName"></s:hidden>
				<s:hidden name="invoiceRequirementsDTO.type"></s:hidden>
				<s:hidden name="invoiceRequirementsDTO.aspect"></s:hidden>
				<s:hidden name="invoiceRequirementsDTO.status"></s:hidden>
				<s:hidden name="invoiceRequirementsDTO.customerEntityId"></s:hidden>
	<ec:table items="invoiceList" var="map" width="100%"
		tableId="invoiceList" form="orderInvoiceForm"
		action="${ctx}/invoiceRequirement/list.action"
		imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
		retrieveRowsCallback="limit" autoIncludeParameters="false">
		<ec:row>
			<ec:column property="null" alias="invoiceIdList" title="选择"
				width="3%" sortable="false" headerCell="selectAll">
				<input type="checkbox" name="invoiceIdList" value="${map.id},${map.customerEntityId},${map.fatherEntityId},${map.waitAmount}" />
			</ec:column>
			<ec:column property="yetAmount" title="已开金额(分)" width="6%" />
			<ec:column property="waitAmount" title="未开金额(分)" width="6%" />
			<ec:column property="invoiceName" title="公司名称" width="6%" />
			<ec:column property="customerEntityId" title="客户号" width="5%" />
			<ec:column property="status" title="状态" width="5%" cell="dictInfo" alias="502" />
			<ec:column property="aspect" title="票据方向" width="6%" >
				<c:if test="${map.aspect=='0'}">红票</c:if>
			    <c:if test="${map.aspect=='1'}">正常票</c:if>
			</ec:column>
		
			<ec:column property="type" title="票据类型" cell="dictInfo" alias="182"
				width="6%" />
			<ec:column property="createdTime" title="创建时间"  cell="date" format="yyyy-MM-dd hh:mm:ss" width="10%" />
			<ec:column property="updatedTime" title="更新时间"  cell="date" format="yyyy-MM-dd hh:mm:ss" width="10%" />

		</ec:row>

	</ec:table>
	<table border="0" cellpadding="0" cellspacing="0" width="100%">
		<tr>
			<td align="right">
			<table border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td><display:security urlId="60702">
						<input type="button" class="btn"
							style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/modify.gif) no-repeat; text-align: right"
							onclick="doInvoice();" value="开票" />
					</display:security></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>

</s:form></div>

</div>

<div id="inhalt" style = "display: none">
<center>
	<form id="popupForm" action="invoiceRequirement/doInvoice.action">
	  <br/><br/><br/>
	  <table>
	  	<tr>
	  	<th>金额小计：</th>
	  	<td><label id="showMoney"></label>分<br/></td>
	  	</tr>
	  	<tr>
	  		<th>发票号：</th>
	  		<td><textarea name="invoiceNO" cols="50" rows="3"></textarea></td>
	  	</tr>
	  </table>
	   <br/><br/>
	  <input type="hidden" id="hidInvoiceId" name="invoiceId" value=""/>
	  <input type="submit" value=" 确 定 "/>
	  <input type="button" onclick="closeWin()" value="取 消 "/>
	</form>
</center>
</div>

</body>
</html>